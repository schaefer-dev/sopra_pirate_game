package de.unisaarland.cs.st.pirates.group5.tests;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
		TestObjectConstructor constr = new TestObjectConstructor();
		lines = constr.getStringList();
		commands = constr.getCommandList();
	}
	
	@Test
	public void testExampleTactic(){
		String tactic = lines.get(lines.size()-1);
		InputStream in = stringToStream(tactic);
		List<Command> erg = translator.run(in);
		
		assertEquals("translation of the example tactic did not result in the correct list of commands",commands,erg);
	}
	
	@Test
	public void testNoSpacesInComparisons(){
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
	
	public void testRepair()
	{
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
	
	public void testIfAny()
	{
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
	
	public void testBouys()
	{
		String tactic = "sense 6\n"
						+ "if sense_treasure!=0 else 7\n"
						+ "pickup 6 else 7\n"
						+"if 0!=sense_treasure else 7\n"
						+ "mark 5\n" +lines.get(6) + lines.get(7)
						+"unmark 5\n"
						+lines.get(12)
						+ "goto 5";
		List<Command> sollErg = new LinkedList<Command>();
		sollErg.add(new Sense(6));
		Comparison comp = new IntComparison(UNEQUAL, Register.sense_treasure, 0);
		sollErg.add(new If(comp, 7));
		sollErg.add(new Pickup(6, 7));
		comp = new IntComparison(UNEQUAL, 0, Register.sense_treasure);
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
	
	public void testMarkWrongBouy()
	{
		String badTactic = lines.get(17) + "mark 6\n" + lines.get(18);
		InputStream in = stringToStream(badTactic);
		try{
			translator.run(in);
			fail("Translator should not allow bouys of type 6");
			}
			catch(IllegalArgumentException e){}
	}
	
	public void testUnmarkWrongBouy()
	{
		String badTactic = lines.get(17) + "unmark 6\n" + lines.get(18);
		InputStream in = stringToStream(badTactic);
		try{
			translator.run(in);
			fail("Translator should not allow bouys of type 6");
			}
			catch(IllegalArgumentException e){}
	}
	
	public void testNonsense()
	{
		String noTactic = "sanmashfkasl";
		InputStream in = stringToStream(noTactic);
		try{
			translator.run(in);
			fail("Translator should not allow gibberish");
			}
			catch(IllegalArgumentException e){}
	}
	
	public void testNoBoolComparison1()
	{
		String badTactic = lines.get(0) + "ifall sense_treasure==true ship_load<4 else 0\n"+
							"goto 0";
		InputStream in = stringToStream(badTactic);
		
		try{
			translator.run(in);
			fail("Comparisons with true and false are not allowed");
			}
			catch(IllegalArgumentException e){}
	}
	public void testNoBoolComparison2()
	{
		String badTactic = lines.get(0) + "ifany sense_treasure==true ship_load<4 else 0\n"+
				"goto 0";
		InputStream in = stringToStream(badTactic);
		
		try{
			translator.run(in);
			fail("Comparisons with true and false are not allowed");
			}
			catch(IllegalArgumentException e){}
	}
	
	public void testNoBoolComparison3()
	{
		String badTactic = lines.get(0) + "if sense_treasure==true else 0\n"+
				"goto 0";
		InputStream in = stringToStream(badTactic);
		
		try{
			translator.run(in);
			fail("Comparisons with true and false are not allowed");
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
