package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Register;
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
import controller.BoolComparison;
import controller.Comparison;

public class CommandEqualityTest {

	private BoolComparison comp = new BoolComparison(Register.sense_enemymarker, false);
	private List<Comparison> comps = new ArrayList<Comparison>();
	
	private Drop drop = new Drop();
	private Flipzero flip = new Flipzero(5, 5);
	private Flipzero flip2= new Flipzero(9, 5);
	private Goto go = new Goto(5);
	private If ifi = new If(comp, 8);
	private If ifi2 = new If(comp, 2);
	private IfAll ifall = new IfAll(comps, 6);
	private IfAll ifal2l = new IfAll(comps, 1);
	private IfAny ifany = new IfAny(comps, 4);
	private IfAny ifany2 = new IfAny(comps, 2);
	private Mark mark = new Mark(5);
	private Move move = new Move(5);
	private Move move2 = new Move(1);
	private Pickup pick = new Pickup(4, 3);
	private Refresh refresh = new Refresh(3, 2);
	private Repair repair = new Repair(0);
	private Repair rep2 = new Repair(1);
	private Sense sense = new Sense(3);
	private Sense sense2 = new Sense(1);
	private Turn turn = new Turn(false);
	private Unmark unm = new Unmark(3);
	
	@Before
	public void setUp(){
		comps.add(comp);
	}
	
	@Test
	public void equalityTest(){
		
		assertFalse(drop.equals(flip));
		assertFalse(flip.equals(go));
		assertFalse(go.equals(ifi));
		assertFalse(ifi.equals(ifall));
		assertFalse(ifall.equals(ifany));
		assertFalse(ifany.equals(mark));
		assertFalse(mark.equals(move));
		assertFalse(move.equals(pick));
		assertFalse(pick.equals(refresh));
		assertFalse(refresh.equals(repair));
		assertFalse(repair.equals(sense));
		assertFalse(sense.equals(turn));
		assertFalse(turn.equals(unm));
		assertFalse(unm.equals(drop));
	}
	
	@Test
	public void equalitySameCommandTest(){
		
		assertFalse(flip.equals(flip2));
		assertFalse(ifi.equals(ifi2));
		assertFalse(ifall.equals(ifal2l));
		assertFalse(ifany2.equals(ifany));
		assertFalse(move.equals(move2));
		assertFalse(repair.equals(rep2));
	    assertFalse(sense.equals(sense2));
	}
	
}
