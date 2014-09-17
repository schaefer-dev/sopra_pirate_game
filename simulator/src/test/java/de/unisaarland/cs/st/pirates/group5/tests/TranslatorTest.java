package de.unisaarland.cs.st.pirates.group5.tests;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import commands.Drop;
import commands.Flipzero;
import commands.Goto;
import commands.If;
import commands.IfAll;
import commands.IfAny;
import commands.Mark;
import commands.Move;
import commands.Pickup;
import commands.Refresh;
import commands.Repair;
import commands.Sense;
import commands.Turn;
import commands.Unmark;

import org.junit.Test;

import static org.junit.Assert.*;
import controller.*;
import model.CellType;
import model.Register;


public class TranslatorTest {
	
	private Translator translator = new Translator();
	private final static Operator LESS = Operator.Less;
	private final static Operator GREATER = Operator.Greater;
	private final static Operator EQUAL = Operator.Equal;
	private final static Operator UNEQUAL = Operator.Unequal;
	private List<String> lines;
	private List<Command> commands;

	public TranslatorTest()
	{
		ObjectConstructorTest constr = new ObjectConstructorTest();
		lines = constr.getStringList();
		commands = constr.getCommandList();
	}
	
	@Test
	public void testExampleTactic(){
		System.out.println("TEST EXAMPLE TACTIC");

		String tactic = lines.get(lines.size()-1); //das letzte element der liste ist die taktik im ganzen.
		InputStream in = stringToStream(tactic);
		List<Command> erg = translator.run(in);
		assertEquals("translation of the example tactic did not result in the correct list of commands",commands,erg);
	}
	
	@Test
	public void testNoSpacesInComparisons(){
		System.out.println("TEST testNoSpacesInComparisons".toUpperCase());

		String badTactic = "move else 1\n"
							+"if ship_load == 3 else 0";
		InputStream in = stringToStream(badTactic);
		try{
		translator.run(in);
		fail("Translator should not allow spaces within comparisons");		
		}
		catch(IllegalArgumentException e){}
	}
	
	@Test
	public void testToBigDirection()
	{
		System.out.println("TEST testToBigDirection".toUpperCase());

		String badTactic = lines.get(0)+lines.get(2)+lines.get(5);
		badTactic += "sense 7\n";
		badTactic += lines.get(13) + "move else 0\n" + lines.get(18);
		InputStream in = stringToStream(badTactic);
		try{
			translator.run(in);
			fail("Translator should not allow sense operations with directions greater than 6");
			}
			catch(IllegalArgumentException e){}
		
		
	}
	@Test
	public void testRepair()
	{
		System.out.println("TEST testRepair".toUpperCase());

		String tactic = lines.get(0) + "ifall ship_condition<3 sense_celltype==home else 3\n"
									+ "repair else 3\n"
									+"move else 5\n" + lines.get(7)
									+ lines.get(22) + lines.get(18);
		List<Command> sollErg = new LinkedList<Command>();
		sollErg.add(commands.get(0));
		Comparison comp = new IntComparison(LESS, Register.ship_condition, 3);
		List <Comparison> compsLs = new LinkedList<Comparison>();
		compsLs.add(comp);
		comp = new CellTypeComparison(EQUAL, Register.sense_celltype, CellType.Home);
		compsLs.add(comp);
		sollErg.add(new IfAll(compsLs, 3));
		sollErg.add(new Repair(3));
		sollErg.add(new Move(5));
		sollErg.add(commands.get(7));
		sollErg.add(commands.get(22));
		sollErg.add(commands.get(18));
		List<Command> erg = translator.run(stringToStream(tactic));
		assertEquals("Translation of the tactic repairing a ship in it's home did not give the correct result", sollErg, erg);
	}
	@Test
	public void testIfAny()
	{
		System.out.println("TEST testIfAny".toUpperCase());

		String tactic = lines.get(0) + "ifany sense_celltype==empty sense_celltype==home else 4\n"
						+ "move else 0\n" + lines.get(13) + lines.get(9) + lines.get(7);
		List<Command> sollErg = new LinkedList<Command>();
		sollErg.add(commands.get(0));
		Comparison comp = new CellTypeComparison(EQUAL, Register.sense_celltype, CellType.Empty);
		List <Comparison> compsLs = new LinkedList<Comparison>();
		compsLs.add(comp);
		comp = new CellTypeComparison(EQUAL, Register.sense_celltype, CellType.Home);
		compsLs.add(comp);
		sollErg.add(new IfAny(compsLs, 4));
		sollErg.add(new Move(0));
		sollErg.add(commands.get(13));
		sollErg.add(commands.get(9));
		sollErg.add(commands.get(7));
		List<Command> erg = translator.run(stringToStream(tactic));
		assertEquals("Translation of the tactic with an ifany command does not give the correct result", sollErg, erg);
	}
	@Test
	public void testBouys()
	{
		System.out.println("TEST testBouys".toUpperCase());

		String tactic = "sense 6\n"
						+ "if sense_shipdirection!=2 else 7\n"
						+ "pickup 6 else 7\n"
						+"if 2!=sense_shipdirection else 7\n"
						+ "mark 5\n" +lines.get(6) + lines.get(7)
						+"unmark 5\n"
						+lines.get(12)
						+ "goto 5";
		List<Command> sollErg = new LinkedList<Command>();
		sollErg.add(new Sense(6));
		Comparison comp = new IntComparison(UNEQUAL, Register.sense_shipdirection, 0);
		sollErg.add(new If(comp, 7));
		sollErg.add(new Pickup(6, 7));
		comp = new IntComparison(UNEQUAL, 0, Register.sense_shipdirection);
		sollErg.add(new If(comp, 7));
		sollErg.add(new Mark(5));
		sollErg.add(commands.get(6));
		sollErg.add(commands.get(7));
		sollErg.add(new Unmark(5));
		sollErg.add(commands.get(12));
		sollErg.add(new Goto(5));
		List<Command> erg = translator.run(stringToStream(tactic));
		assertEquals("Translation of the tactic setting bouys does not give the correct result", sollErg, erg);
		
	}
	@Test
	public void testMarkWrongBouy()
	{
		System.out.println("TEST testMarkWrongBouy".toUpperCase());

		String badTactic = lines.get(17) + "mark 6\n" + lines.get(18);
		InputStream in = stringToStream(badTactic);
		try{
			translator.run(in);
			fail("Translator should not allow bouys of type 6");
			}
			catch(IllegalArgumentException e){}
	}
	@Test
	public void testUnmarkWrongBouy()
	{
		System.out.println("TEST testUnmarkWrongBouy".toUpperCase());

		String badTactic = lines.get(17) + "unmark 6\n" + lines.get(18);
		InputStream in = stringToStream(badTactic);
		try{
			translator.run(in);
			fail("Translator should not allow bouys of type 6");
			}
			catch(IllegalArgumentException e){}
	}
	@Test
	public void testNonsense()
	{
		System.out.println("TEST testNonsense".toUpperCase());

		String noTactic = "sanmashfkasl";
		InputStream in = stringToStream(noTactic);
		try{
			translator.run(in);
			fail("Translator should not allow gibberish");
			}
			catch(IllegalArgumentException e){}
	}
	@Test
	public void testNoBoolComparison1()
	{
		System.out.println("TEST testNoBoolComparison1".toUpperCase());

		String badTactic = lines.get(0) + "ifall sense_treasure==true ship_load<4 else 0\n"+
							"goto 0";
		InputStream in = stringToStream(badTactic);
		
		try{
			translator.run(in);
			fail("Comparisons with true and false are not allowed");
			}
			catch(IllegalArgumentException e){}
	}
	@Test
	public void testNoBoolComparison2()
	{
		System.out.println("TEST testNoBoolComparison2".toUpperCase());

		String badTactic = lines.get(0) + "ifany sense_treasure==true ship_load<4 else 0\n"+
				"goto 0";
		InputStream in = stringToStream(badTactic);
		
		try{
			translator.run(in);
			fail("Comparisons with true and false are not allowed");
			}
			catch(IllegalArgumentException e){}
	}
	@Test
	public void testNoBoolComparison3()
	{
		System.out.println("TEST testNoBoolComparison3".toUpperCase());

		String badTactic = lines.get(0) + "if sense_treasure==false else 0\n"+
				"goto 0";
		InputStream in = stringToStream(badTactic);
		
		try{
			translator.run(in);
			fail("Comparisons with true and false are not allowed");
			}
			catch(IllegalArgumentException e){}
	}
	@Test
	public void testNoUndefined()
	{
		System.out.println("TEST testNoUndefined".toUpperCase());

		String badTactic = lines.get(0)+ "if sense_celltype==undefined else 0\n"
							+ lines.get(7);
		InputStream in = stringToStream(badTactic);
		
		try{
			translator.run(in);
			fail("Comparisons with undefined are not allowed");
			}
			catch(IllegalArgumentException e){}
	}
	@Test
	public void testRefresh()
	{
		System.out.println("TEST testRefresh".toUpperCase());

		String tactic = lines.get(0) + "ifall sense_supply ship_moral!=4 else 3\n"
									+ "refresh 0 else 3\n"
									+ "move else 5\n" + lines.get(10) + lines.get(9) + lines.get(10);
		List<Command> sollErg = new LinkedList<Command>();
		sollErg.add(commands.get(0));
		Comparison comp = new BoolComparison(Register.sense_supply, false);
		List <Comparison> compsLs = new LinkedList<Comparison>();
		compsLs.add(comp);
		comp = new IntComparison(UNEQUAL, Register.ship_moral, 4);
		compsLs.add(comp);
		sollErg.add(new IfAll(compsLs, 3));
		sollErg.add(new Refresh(0, 3));
		sollErg.add(new Move(5));
		sollErg.add(commands.get(10));
		sollErg.add(commands.get(9));
		sollErg.add(commands.get(10));
		List<Command> erg = translator.run(stringToStream(tactic));
		assertEquals("Translation of Refresh does not give correct result", sollErg, erg);
		
	}
	@Test
	public void testNoNewLine()
	{
		System.out.println("TEST testNoNewLine".toUpperCase());

		String badTactic = lines.get(26)+ lines.get(13);
		InputStream in = stringToStream(badTactic);
		try{
		translator.run(in);
		fail("Only one command in each line is allowed.");
		}
		catch(IllegalArgumentException e){}
		
		
	}
	
	@Test
	public void testIgnoreComments1()
	{
		System.out.println("TEST testIgnoreComments1".toUpperCase());

		String tactic = lines.get(0)
				+ "turn right; Eine Drehung nach Rechts!§\"s()€@#*~+-2,/*yY<>||^°$%}{[]\\`´;:dsadas²³?=\n"
				+ lines.get(7);
		List<Command> sollErg = new LinkedList<Command>();
		sollErg.add(commands.get(0));
		sollErg.add(commands.get(12)); // Dies ist ein turn-right
		sollErg.add(commands.get(7));
		InputStream in = stringToStream(tactic);
		assertEquals("Kommentare sollten ignoriert werden", sollErg, translator.run(in));
	}
	@Test
	public void testIgnoreComments2()
	{
		System.out.println("TEST testIgnoreComments2".toUpperCase());

		String tactic = lines.get(0)
				+ "turn right; turn left\n"
				+ lines.get(7);
		List<Command> sollErg = new LinkedList<Command>();
		sollErg.add(commands.get(0));
		sollErg.add(commands.get(12)); // Dies ist ein turn-right
		sollErg.add(commands.get(7));
		InputStream in = stringToStream(tactic);
		assertEquals("Kommentare sollten ignoriert werden", sollErg, translator.run(in));
	}
	@Test
	public void testCommentLine()
	{
		System.out.println("TEST testCommentLine".toUpperCase());

		String badTactic = "turn left\n"
							+";\n"
							+ lines.get(7);
		InputStream in = stringToStream(badTactic);
		try{
		translator.run(in);
		fail("A line must not only contain a comment");
		}
		catch(IllegalArgumentException e){}
	}
	@Test
	public void testCorrectLength()
	{
		System.out.println("TEST testCorrectLength".toUpperCase());

		String tactic = "";
		List<Command> sollErg = new LinkedList<Command>();
		for(int i = 0; i<2000; i++)
		{
			sollErg.add(commands.get(0));
			tactic += lines.get(0);
		}
		InputStream in = stringToStream(tactic);		
		assertEquals("Tactic length is 2000 i.e. OK.", sollErg, translator.run(in));
		
	}
	
	@Test
	public void testWrongLength()
	{
		System.out.println("TEST testCorrectLength".toUpperCase());

		String badTactic = "";
		List<Command> sollErg = new LinkedList<Command>();
		for(int i = 0; i<=2000; i++)
		{
			sollErg.add(commands.get(0));
			badTactic += lines.get(0);
		}
		InputStream in = stringToStream(badTactic);
		try{
		translator.run(in);
		fail("A tactic must not have 2001 instructions");
		}
		catch(IllegalArgumentException e){}
	}
	
	@Test
	public void testNoNegativNumber1()
	{
		System.out.println("TEST testNoNegativNumber1".toUpperCase());

		String badTactic = "move else -10\n"
							+"goto 0";
		InputStream in = stringToStream(badTactic);
		try{
		translator.run(in);
		fail("A tactic must not go to negative instructions.");
		}
		catch(IllegalArgumentException e){}
	}
	
	
	@Test
	public void testNoNegativNumber2()
	{
		System.out.println("TEST testNoNegativNumber2".toUpperCase());

		String badTactic = "move else 2\n"
							+ "pickup -1 else 0\n"
							+ lines.get(13);
							
		InputStream in = stringToStream(badTactic);
		try{
		translator.run(in);
		fail("There cannot be any negative directions.");
		}
		catch(IllegalArgumentException e){}
	}
	
	@Test
	public void testNoOneInFlipzero()
	{
		System.out.println("TEST testNoOneInFlipzero".toUpperCase());

		String badTactic = "move else 2\n"
							+"goto 0\n"
							+"flipzero 1 else 0";
		InputStream in = stringToStream(badTactic);
		try{
		translator.run(in);
		fail("Flipzero can only be called with values greater than one.");
		}
		catch(IllegalArgumentException e){}
	}
	@Test
	public void testNoJumpFurther1999()
	{
		System.out.println("TEST testNoJumpFurther1999".toUpperCase());

		String badTactic  = "move else 2\n"
				+"goto 0\n"
				+"flipzero 1 else 2000";
		InputStream in = stringToStream(badTactic);
		try{
		translator.run(in);
		fail("Probably jumps to lines greater than 1999 are not allowed in tacticfiles.");
		}
		catch(IllegalArgumentException e){}
	}
	@Test
	public void testCommandList()
	{
		System.out.println("TEST testCommandList".toUpperCase());

		assertEquals("List of commands is wrong",new Goto(14),commands.get(26));
		assertEquals("List of commands is wrong",new Sense(0),commands.get(14));
	}
	
	@Test
	public void testWrongTurn()
	{
		String badTactic = lines.get(0)
							+"turn links; this\n"
							+ lines.get(7);
		
		InputStream in = stringToStream(badTactic);
		try{
		translator.run(in);
		fail("Probably jumps to lines greater than 1999 are not allowed in tacticfiles.");
		}
		catch(IllegalArgumentException e){}
	}
	private InputStream stringToStream(String tactic)
	{

		byte[] temp = tactic.getBytes();
		InputStream in = new ByteArrayInputStream(temp);
		in = new BufferedInputStream(in);
		return in;
	}
}
