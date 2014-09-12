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
import commands.Move;
import commands.Pickup;
import commands.Repair;
import commands.Sense;
import commands.Turn;

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
	private InputStream stringToStream(String tactic)
	{
		byte[] temp = tactic.getBytes();
		InputStream in = new ByteArrayInputStream(temp);
		in = new BufferedInputStream(in);
		return in;
	}
}
