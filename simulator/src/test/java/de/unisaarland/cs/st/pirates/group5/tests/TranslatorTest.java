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
		byte[] temp = tactic.getBytes();
		InputStream in = new ByteArrayInputStream(temp);
		List<Command> erg = translator.run(in);
		
		assertEquals("Translaten der Beispieltaktik liefert falsche Commandliste",commands,erg);
	}
}
