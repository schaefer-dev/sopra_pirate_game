package de.unisaarland.cs.st.pirates.group5.tests;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import commands.Drop;
import commands.Flipzero;
import commands.Goto;
import commands.If;
import commands.IfAll;
import commands.Move;
import commands.Pickup;
import commands.Sense;
import commands.Turn;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import controller.*;
import model.CellType;
import model.Register;


public class TranslatorTest extends TestCase{
	
	private Translator translator = new Translator();
	private final static Operator LESS = Operator.Less;
	private final static Operator GREATER = Operator.Greater;
	private final static Operator EQUAL = Operator.Equal;
	private final static Operator UNEQUAL = Operator.Unequal;

	public TranslatorTest()
	{
		super("TranslatorTest");
	}
	
	@Test
	public void testExampleTactic(){
		String tactic = "sense 0\n"
						+"ifall sense_treasure ship_load<4 else 4\n"
						+"pickup 0 else 6\n"
						+"goto 14\n"
						+"move else 8\n"
						+"goto 0\n"
						+"flipzero 3 else 11\n"
						+"turn left\n"
						+"goto 0\n"
						+"flipzero 2 else 13\n"
						+"turn right\n"
						+"goto 0\n"
						+"sense 0\n"
						+"if sense_celltype==home else 19\n"
						+"move else 21\n"
						+"goto 14\n"
						+"flipzero 3 else 24\n"
						+"turn left\n"
						+"goto 14\n"
						+"flipzero 2 else 26\n"
						+"turn right\n"
						+"goto 14\n";
		
		byte[] temp = tactic.getBytes();
		InputStream in = new ByteArrayInputStream(temp);
		List<Command> erg = translator.run(in);
		List<Command> sollErg = new LinkedList<Command>();
		sollErg.add(new Sense(0));
		
		List<Comparison> comps = new LinkedList<Comparison>();
		Comparison comparison = new BoolComparison(Register.sense_treaure, false);
		comps.add(comparison);
		comparison = new IntComparison(LESS, Register.ship_load, 4);
		comps.add(comparison);
		sollErg.add(new IfAll(comps, 4));
		sollErg.add(new Pickup(0, 6));
		sollErg.add(new Goto(14));
		comparison = new IntComparison(GREATER, Register.ship_load, 3);
		sollErg.add(new If(comparison, 6));
		sollErg.add(new Goto(14));
		sollErg.add(new Move(8));
		sollErg.add(new Goto(0));
		sollErg.add(new Flipzero(3, 11));
		sollErg.add(new Turn(true));
		sollErg.add(new Goto(0));
		sollErg.add(new Flipzero(2, 13));
		sollErg.add(new Turn(false));
		sollErg.add(new Goto(0));
		sollErg.add(new Sense(0));
		comparison = new CellTypeComparison(EQUAL, Register.sense_celltype, CellType.Home);
		sollErg.add(new If(comparison, 19));
		sollErg.add(new Move(21));
		sollErg.add(new Drop());
		sollErg.add(new Goto(0));
		sollErg.add(new Move(21));
		sollErg.add(new Goto(14));
		sollErg.add(new Flipzero(3, 24));
		sollErg.add(new Turn(true));
		sollErg.add(new Goto(14));
		sollErg.add(new Flipzero(2, 26));
		sollErg.add(new Turn(false));
		sollErg.add(new Goto(14));
		
		assertEquals("Translaten der Beispieltaktik liefert falsche Commandliste",sollErg,erg);
	}
}
