package de.unisaarland.cs.st.pirates.group5.tests;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import commands.Repair;
import model.Base;
import model.Field;
import model.Map;
import model.Ship;
import model.Team;
import model.Water;

import org.junit.Test;

import controller.Command;
import de.unisaarland.cs.st.pirates.logger.LogWriter;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Entity;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Key;
import static org.junit.Assert.*;

public class CommandRepairTest {
	
	private class DummyLogWriter implements LogWriter
	{
		
		public List<Entity> entities = new LinkedList<Entity>();
		public List<Integer> values = new LinkedList<Integer>();
		public List<String> what = new LinkedList<String>();

		@Override
		public LogWriter addCell(Cell arg0, Integer arg1, int arg2, int arg3)
				throws NullPointerException, ArrayIndexOutOfBoundsException,
				IllegalArgumentException, IllegalStateException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LogWriter addCustomHeaderData(String arg0)
				throws NullPointerException, ArrayIndexOutOfBoundsException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Transaction beginTransaction(Entity arg0, int arg1)
				throws NullPointerException, IllegalArgumentException,
				IllegalStateException {
			// TODO Auto-generated method stub
			return null;
		}

		public void setEntities(List<Entity> els) {
			this.entities = els;
		}

		public void setValues(List<Integer> values) {
			this.values = values;
		}

		public void setWhat(List<String> what) {
			this.what = what;
		}

		@Override
		public void close() throws IllegalStateException, IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public LogWriter commitTransaction(Transaction arg0)
				throws NullPointerException, IllegalArgumentException,
				IllegalStateException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LogWriter create(Entity arg0, int arg1, Key[] arg2, int[] arg3)
				throws NullPointerException, IllegalArgumentException,
				ArrayIndexOutOfBoundsException, IllegalStateException {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Entity> getEntities() {
			return entities;
		}

		public List<Integer> getValues() {
			return values;
		}

		public List<String> getWhat() {
			return what;
		}

		@Override
		public LogWriter destroy(Entity arg0, int arg1){
				return null;
		}

		@Override
		public LogWriter fleetScore(int arg0, int arg1)
				throws IllegalArgumentException, IllegalStateException {
			values.add(arg0);
			values.add(arg1);
			what.add("changeScore");
			return this;
		}

		@Override
		public void init(OutputStream arg0, String arg1, String... arg2)
				throws NullPointerException, IOException,
				ArrayIndexOutOfBoundsException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void logStep() throws IllegalStateException, IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public LogWriter notify(Entity arg0, int arg1, Key arg2, int arg3)
				throws NullPointerException, IllegalArgumentException,
				IllegalStateException {
			entities.add(arg0);
			values.add(arg1);
			values.add(arg2.ordinal());
			values.add(arg3);
			what.add("notify");
			return this;
		}
		
	}
	
	private class DummyMap extends Map{
		
		LogWriter log;

		public DummyMap(LogWriter log) {
			super(new Random());
			this.log = log;
		}
		
		public LogWriter getLogWriter(){
			return log;
		
	}
	}

	public CommandRepairTest()
	{
	}
	@Test
	public void testWorkingRepair()
	{
		Team teamA = new Team('a', null);
		Ship ship = new Ship(teamA, null, 0, null);
		teamA.addShip();
		DummyLogWriter log = new DummyLogWriter();
		DummyMap map = new DummyMap(log);
		Field field = new Base(map,0,0,teamA);
		ship.setField(field);
		Command repair = new Repair(12);
		teamA.addLoot(2);
		ship.changeCondition(-2);
		assertEquals("changeCondition did not work properly", 1, ship.getCondition());
		log.entities.clear();
		log.values.clear();
		log.what.clear();
		repair.execute(ship);
		assertEquals("repair does not pay the price from the teams loot", 0, teamA.getScore());
		assertEquals("repair did not repair the ship properly. The ship's condition is not 3.", 3, ship.getCondition());
		assertEquals("ship does not pause for 4 rounds after beeing repaired",4,ship.getPause());
		assertEquals("Programm counter should not have been changed.", 0, ship.getPC());
		assertTrue("not the right logger Methods where called",log.what.size()==3 && log.what.remove("changeScore") && log.what.remove("notify") && log.what.remove("notify"));
		assertTrue("during logging the wrong entity was passed on.", log.entities.size()==2 && log.entities.remove(Entity.SHIP) && log.entities.remove(Entity.SHIP));
		List<Integer> vals = log.getValues();
		Integer nul = new Integer (0);
		assertTrue(vals.remove(nul) && vals.remove(nul) && vals.remove(nul) && vals.remove(nul) && vals.remove(new Integer(3)) && vals.remove(new Integer(Key.CONDITION.ordinal())) && vals.remove(new Integer(Key.RESTING.ordinal())) && vals.remove(new Integer(4))); // tests if the ids of Team and Ship and the Ship again as well as the Key.ordinal for Condition and Resting and the new values of score and condition and resting where logged
	}
	@Test
	public void testNonWorkingRepair()
	{
		Team teamA = new Team('a', null);
		Ship ship = new Ship(teamA, null, 0, null);
		teamA.addShip();
		DummyLogWriter log = new DummyLogWriter();
		DummyMap map = new DummyMap(log);
		Field field = new Base(map,0,0,teamA);
		ship.setField(field);
		Command repair = new Repair(12);
		teamA.addLoot(1);
		ship.changeCondition(-2);
		assertEquals("changeCondition did not work properly", 1, ship.getCondition());
		log.entities.clear();
		log.values.clear();
		log.what.clear();
		repair.execute(ship);
		assertEquals("Team can't pay for repair, so the Score should not have changed, but did.", 1, teamA.getScore());
		assertEquals("Condition of ship should not have changed.", 1, ship.getCondition());
		assertEquals("Ship should not have to pause because repair failed.",0,ship.getPause());
		assertEquals("Programm counter should have been changed to 12", 12, ship.getPC());
		assertTrue("not the right logger Methods where called",log.what.size()==1 &&  log.what.remove("notify"));
		assertTrue("during logging the wrong entity was passed on.", log.entities.size()==1 && log.entities.remove(Entity.SHIP));
		List<Integer> vals = log.getValues();
		Integer nul = new Integer (0);
		assertTrue(vals.remove(nul) && vals.remove(new Integer(12)) && vals.remove(new Integer(Key.PC.ordinal())));
	}
	@Test
	public void testWrongField()
	{
		Team teamA = new Team('a', null);
		Ship ship = new Ship(teamA, null, 0, null);
		teamA.addShip();
		DummyLogWriter log = new DummyLogWriter();
		DummyMap map = new DummyMap(log);
		Field field = new Water(map, 0, 0, null);
		ship.setField(field);
		Command repair = new Repair(12);
		teamA.addLoot(4);
		log.entities.clear();
		log.values.clear();
		log.what.clear();
		repair.execute(ship);
		assertEquals("Team payed for repair which should not have happend.", 4, teamA.getScore());
		assertEquals("Condition of ship should not have changed.", 1, ship.getCondition());
		assertEquals("Ship should not have to pause because repair failed.",0,ship.getPause());
		assertEquals("Programm counter should have been changed to 12", 12, ship.getPC());
		assertTrue("not the right logger Methods where called",log.what.size()==1 &&  log.what.remove("notify"));
		assertTrue("during logging the wrong entity was passed on.", log.entities.size()==1 && log.entities.remove(Entity.SHIP));
		List<Integer> vals = log.getValues();
		Integer nul = new Integer (0);
		assertTrue(vals.remove(nul) && vals.remove(new Integer(12)) && vals.remove(new Integer(Key.PC.ordinal())));
	}
	
	@Test
	public void testWorkingRepairOfUndamagedShip()
	{
		Team teamA = new Team('a', null);
		Ship ship = new Ship(teamA, null, 0, null);
		teamA.addShip();
		DummyLogWriter log = new DummyLogWriter();
		DummyMap map = new DummyMap(log);
		Field field = new Base(map,0,0,teamA);
		ship.setField(field);
		Command repair = new Repair(12);
		teamA.addLoot(3);
		assertEquals("At the begin ship should have condition 3", 3, ship.getCondition());
		log.entities.clear();
		log.values.clear();
		log.what.clear();
		repair.execute(ship);
		assertEquals("repair does not pay the price from the teams loot", 1, teamA.getScore());
		assertEquals("Repair should not have changed ship's condition, since ship was undamaged.", 3, ship.getCondition());
		assertEquals("ship does not pause for 4 rounds after beeing repaired",4,ship.getPause());
		assertEquals("Programm counter should not have been changed.", 0, ship.getPC());
		assertTrue("not the right or to many or to few logger Methods where called",log.what.size()==2 && log.what.remove("changeScore")  && log.what.remove("notify"));
		assertTrue("during logging the wrong entity was passed on.", log.entities.size()==1 && log.entities.remove(Entity.SHIP));
		List<Integer> vals = log.getValues();
		Integer nul = new Integer (0);
		assertTrue(vals.remove(nul) && vals.remove(nul) && vals.remove(new Integer(1)) && vals.remove(new Integer(Key.RESTING.ordinal())) && vals.remove(new Integer(4))); // tests if the ids of Team and the ship as well as the Key.ordinal for Resting and the new values of score and resting where logged
	}
	
}
