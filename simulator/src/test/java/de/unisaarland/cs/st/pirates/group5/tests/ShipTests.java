package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import model.Register;
import model.Ship;
import model.Field;
import model.Team;
import model.Water;

import org.junit.Before;
import org.junit.Test;

import commands.Drop;
import commands.Goto;
import controller.Command;


public class ShipTests {
	
	
	private Ship ship;
	private Ship shipv;
	private Ship shipn;
	
	private Field field;
	private Ship sship;
	
	private Register shipdirection;
	private Register shipload;
	private Register shipmoral;
	private Register shipcondition;
	private Register sensecelltype;
	private Register sensetreasure;
	private Register sensemarker0;
	private Register sensemarker1;
	private Register sensemarker2;
	private Register sensemarker3;
	private Register sensemarker4;
	private Register sensemarker5;
	private Register senseenemymarker;
	private Register senseshiptype;
	private Register sensedirection;
	private Register senseloaded;
	private Register sensesupply;
	
	@Before
	public void setUp(){
	
		shipdirection = Register.ship_direction;
		shipload = Register.ship_load;
		shipmoral = Register.ship_moral;
		shipcondition = Register.ship_direction;
		sensecelltype = Register.sense_celltype;
		sensetreasure = Register.sense_treasure;
		sensemarker0 = Register.sense_marker0;
		sensemarker1 = Register.sense_marker1;
		sensemarker2 = Register.sense_marker2;
		sensemarker3 = Register.sense_marker3;
		sensemarker4 = Register.sense_marker4;
		sensemarker5 = Register.sense_marker5;
		senseenemymarker = Register.sense_enemymarker;
		senseshiptype = Register.sense_shiptype;
		sensedirection = Register.sense_shipdirection;
		senseloaded = Register.sense_shiploaded;
		sensesupply = Register.sense_supply;
	}
	
	@Test
	public void testgetPosition(){
		Field water = new Water(null, 0, 0, null);
		ship = new Ship(null, water, 0, null);
		
		assertTrue(ship.getPosition().equals(water));
	
	}
	
	@Test
	public void testAct(){
		List<Command> commands = new ArrayList<Command>();
		Drop drops = new Drop();
		Goto go= new Goto(0);
		commands.add(drops);
		commands.add(go);
		char a = 0;
		Team team = new Team(a, commands);
		ship = new Ship(team, null, 0, null);
		
		ship.act();
		assertTrue (ship.getPC() == 1);
		
		ship.act();
		assertTrue(ship.getPC() == 0);
		
	}
	
	@Test
	public void testgetID(){
		ship = new Ship(null, null, 666, null);
		assertTrue(ship.getID() == 666);
		
	}
	
	@Test
	public void testgetPC(){
		ship = new Ship(null, null, 666, null);
		ship.setPC(13);
		assertTrue(ship.getPC() == 13);
		
	}
	@Test
	public void testNextShip(){
		shipv = new Ship(null, null, 0, null);
		ship = new Ship(null, null, 0, null);
		shipn = new Ship(null, null, 0, null);
		
		shipv.setNextShip(ship);
		assertTrue (shipv.getNextShip().equals(ship));
		
		ship.setNextShip(shipn);
		assertTrue (ship.getNextShip().equals(shipn));
		
		shipn.setNextShip(null);
		assertNull (shipn.getNextShip());
		
	}
	
	@Test
	public void testPC(){
		
		ship = new Ship(null, null, 0, null);
		
		ship.setPC(13);
		assertTrue (ship.getPC() == 13);
		//Loggen?!
		
	}
	
	@Test
	public void testgetTeam(){
		char a = 0;
		Team t = new Team(a, null);
		ship = new Ship(t, null, 0, null);
		
		assertTrue (ship.getTeam().equals(t));
		
	}
	
	@Test
	public void testChangeMoral1(){
		
		ship = new Ship(null, null, 0, shipv);
		
		ship.changeMoral(-1);
		assertTrue ("ChangeMoral",ship.getMoral() == 3) ;
	}

	@Test
	public void testChangeMoral2(){
		
		ship = new Ship(null, null, 0, shipv);
		ship.changeMoral(3);
		
		assertTrue ("ChangeMoral bigger 4",ship.getMoral() == 4) ;
		assertTrue ("NoPositivActionCounter not 0 after positiv action", ship.getNoPositivActionCounter() == 0);
	}
	
	@Test
	public void testChangeMoral3(){
		
		ship = new Ship(null, null, 0, shipv);
		ship.changeMoral(-7);
		
		assertTrue ("ChangeMoral must not < 0", ship.getMoral() == 0) ;
	}
	
	@Test
	public void testChangeCondition1(){
		
		ship = new Ship(null, null, 0, shipv);
		
	ship.changeCondition(-1);
	assertTrue ("ChangeCondition down",ship.getCondition() == 2);
	
	ship.changeCondition(1);
	assertTrue ("ChangeCondition up",ship.getCondition() == 3);
	}
	
	@Test
	public void testChangeCondition2(){
		
		ship = new Ship(null, null, 0, shipv);
		ship.changeCondition(3);
		
		assertTrue ("ChangeCondition: Condition must not be bigger 3", ship.getCondition() == 3);
	}
	
	@Test
	public void testChangeCondition3(){
		
		ship = new Ship(null, null, 0, shipv);
		ship.changeCondition(-1);
		ship.changeCondition(-1);
		ship.changeCondition(-1);
		
		assertNull ("ChangeCondition == 0 : ship must be deleted", field.getShip());
	}
	
	@Test
	public void testChangeCondition4(){
		
		ship = new Ship(null, null, 0, shipv);
		ship.changeCondition(0);
		
		assertTrue ("ChangeCondition: Condition must be 3 at the beginning", ship.getCondition() == 3);
	}

	@Test
	public void testChangePause1(){
		
		ship = new Ship(null, null, 0, shipv);
		ship.changePause(0);
		
		assertTrue ("ChangePause: Pause must be 0 at the beginning", ship.getPause() == 0);
	}
	
	@Test
	public void testChangePause2(){
		
		ship = new Ship(null, null, 0, shipv);
		ship.changePause(1);
		
		assertTrue ("ChangePause up", ship.getPause() == 1);
	}
	
	@Test
	public void testChangePause3(){
		
		ship = new Ship(null, null, 0, shipv);
		ship.changePause(9);
		
		assertTrue ("ChangePause" ,ship.getPause() == 9);
	}
	

	@Test
	public void testChangePause4(){
		
		ship = new Ship(null, null, 0, shipv);
		ship.changePause(-9);
		
		assertTrue ("ChangePause: Pause must not be < 0", ship.getPause() == 0);
	}
	
	@Test
	public void testChangeDirectionleft(){
		
		ship = new Ship(null, null, 0, shipv);
		
		ship.changeDirection(true);
		assertTrue("changeDirection left first time ", ship.getShipDirection() == 5);
		ship.changeDirection(true);
		assertTrue  ("changeDirection left second time ", ship.getShipDirection() == 4);
		ship.changeDirection(true);
		assertTrue ("changeDirection left 3. time ", ship.getShipDirection() == 3);
		ship.changeDirection(true);
		assertTrue  ("changeDirection left 4. time ", ship.getShipDirection() == 2);
		ship.changeDirection(true);
		assertTrue ("changeDirection left 5. time ", ship.getShipDirection() == 1);
		ship.changeDirection(true);
		assertTrue  ("changeDirection left 6. time ", ship.getShipDirection() == 0);
		
	}
	
	@Test
	public void testChangeDirectionright(){
		
		ship = new Ship(null, null, 0, shipv);
		
		ship.changeDirection(false);
		assertTrue ("changeDirection right 1. time ", ship.getShipDirection() == 1);
		ship.changeDirection(false);
		assertTrue ("changeDirection right 2. time ", ship.getShipDirection() == 2);
		ship.changeDirection(false);
		assertTrue ("changeDirection right 3. time ", ship.getShipDirection() == 3);
		ship.changeDirection(false);
		assertTrue ("changeDirection right 4. time ", ship.getShipDirection() == 4);
		ship.changeDirection(false);
		assertTrue ("changeDirection right 5. time ", ship.getShipDirection() == 5);
		ship.changeDirection(false);
		assertTrue ("changeDirection right 6. time ", ship.getShipDirection() == 0);
	}
	
	@Test
	public void testRelativeToAbsolute(){
		
		ship = new Ship(null, null, 0, shipv);
		
		assertTrue ("relativetoabsolutedir 0 rel = abs", ship.relativeToAbsoluteDirection(0) == 0);
		assertTrue ("relativetoabsolutedir 1 rel = abs",ship.relativeToAbsoluteDirection(1) == 1);
		assertTrue ("relativetoabsolutedir 2 rel = abs",ship.relativeToAbsoluteDirection(2) == 2);
		assertTrue ("relativetoabsolutedir 3 rel = abs",ship.relativeToAbsoluteDirection(3) == 3);
		assertTrue ("relativetoabsolutedir 4 rel = abs",ship.relativeToAbsoluteDirection(4) == 4);
		assertTrue ("relativetoabsolutedir 5 rel = abs",ship.relativeToAbsoluteDirection(5) == 5);
		assertTrue ("relativetoabsolutedir 6 rel = abs",ship.relativeToAbsoluteDirection(6) == 6);
		
		ship.changeDirection(false);
		assertTrue ("relativetoabsolutedir turned right one time",ship.relativeToAbsoluteDirection(0) == 1);
		assertTrue ("relativetoabsolutedir turned right one time",ship.relativeToAbsoluteDirection(1) == 2);
		assertTrue ("relativetoabsolutedir turned right one time",ship.relativeToAbsoluteDirection(2) == 3);
		assertTrue ("relativetoabsolutedir turned right one time",ship.relativeToAbsoluteDirection(3) == 4);
		assertTrue ("relativetoabsolutedir turned right one time",ship.relativeToAbsoluteDirection(4) == 5);
		assertTrue ("relativetoabsolutedir turned right one time",ship.relativeToAbsoluteDirection(5) == 0);
		assertTrue ("relativetoabsolutedir turned right one time",ship.relativeToAbsoluteDirection(6) == 6);
		
		ship.changeDirection(false);
		assertTrue ("relativetoabsolutedir turned right two times",ship.relativeToAbsoluteDirection(0) == 2);
		assertTrue ("relativetoabsolutedir turned right two times",ship.relativeToAbsoluteDirection(1) == 3);
		assertTrue ("relativetoabsolutedir turned right two times",ship.relativeToAbsoluteDirection(2) == 4);
		assertTrue ("relativetoabsolutedir turned right two times",ship.relativeToAbsoluteDirection(3) == 5);
		assertTrue ("relativetoabsolutedir turned right two times",ship.relativeToAbsoluteDirection(4) == 0);
		assertTrue ("relativetoabsolutedir turned right two times",ship.relativeToAbsoluteDirection(5) == 1);
		assertTrue ("relativetoabsolutedir turned right two times",ship.relativeToAbsoluteDirection(6) == 6);
		
		ship.changeDirection(false);
		assertTrue ("relativetoabsolutedir turned right three times",ship.relativeToAbsoluteDirection(0) == 3);
		assertTrue ("relativetoabsolutedir turned right three times",ship.relativeToAbsoluteDirection(1) == 4);
		assertTrue ("relativetoabsolutedir turned right three times",ship.relativeToAbsoluteDirection(2) == 5);
		assertTrue ("relativetoabsolutedir turned right three times",ship.relativeToAbsoluteDirection(3) == 0);
		assertTrue ("relativetoabsolutedir turned right three times",ship.relativeToAbsoluteDirection(4) == 1);
		assertTrue ("relativetoabsolutedir turned right three times",ship.relativeToAbsoluteDirection(5) == 2);
		assertTrue ("relativetoabsolutedir turned right three times",ship.relativeToAbsoluteDirection(6) == 6);
		
		ship.changeDirection(false);
		assertTrue ("relativetoabsolutedir turned right four times",ship.relativeToAbsoluteDirection(0) == 4);
		assertTrue ("relativetoabsolutedir turned right four times",ship.relativeToAbsoluteDirection(1) == 5);
		assertTrue ("relativetoabsolutedir turned right four times",ship.relativeToAbsoluteDirection(2) == 0);
		assertTrue ("relativetoabsolutedir turned right four times",ship.relativeToAbsoluteDirection(3) == 1);
		assertTrue ("relativetoabsolutedir turned right four times",ship.relativeToAbsoluteDirection(4) == 2);
		assertTrue ("relativetoabsolutedir turned right four times",ship.relativeToAbsoluteDirection(5) == 3);
		assertTrue ("relativetoabsolutedir turned right four times",ship.relativeToAbsoluteDirection(6) == 6);
		
		ship.changeDirection(false);
		assertTrue ("relativetoabsolutedir turned right 5 times",ship.relativeToAbsoluteDirection(0) == 5);
		assertTrue ("relativetoabsolutedir turned right 5 times",ship.relativeToAbsoluteDirection(1) == 0);
		assertTrue ("relativetoabsolutedir turned right 5 times",ship.relativeToAbsoluteDirection(2) == 1);
		assertTrue ("relativetoabsolutedir turned right 5 times",ship.relativeToAbsoluteDirection(3) == 2);
		assertTrue ("relativetoabsolutedir turned right 5 times",ship.relativeToAbsoluteDirection(4) == 3);
		assertTrue ("relativetoabsolutedir turned right 5 times",ship.relativeToAbsoluteDirection(5) == 4);
		assertTrue ("relativetoabsolutedir turned right 5 times",ship.relativeToAbsoluteDirection(6) == 6);
		
		ship.changeDirection(false);
		assertTrue ("relativetoabsolutedir turned right 6 times rel = abs",ship.relativeToAbsoluteDirection(0) == 0);
		assertTrue ("relativetoabsolutedir turned right 6 times rel = abs",ship.relativeToAbsoluteDirection(1) == 1);
		assertTrue ("relativetoabsolutedir turned right 6 times rel = abs",ship.relativeToAbsoluteDirection(2) == 2);
		assertTrue ("relativetoabsolutedir turned right 6 times rel = abs",ship.relativeToAbsoluteDirection(3) == 3);
		assertTrue ("relativetoabsolutedir turned right 6 times rel = abs",ship.relativeToAbsoluteDirection(4) == 4);
		assertTrue ("relativetoabsolutedir turned right 6 times rel = abs",ship.relativeToAbsoluteDirection(5) == 5);
		assertTrue ("relativetoabsolutedir turned right 6 times rel = abs",ship.relativeToAbsoluteDirection(6) == 6);
	}
	@Test
	public void testDestroy(){
		
		shipv = new Ship(null, null, 0, null);
		ship = new Ship(null, null, 0, shipv);
		shipn = new Ship(null, null, 0, ship);
		field = new Water(null, 0, 0, null);
		field.setShip(ship);
		ship.changeCondition(-3);
		
		assertNull ("Destroyed because condition 0, field.getShip() must be null", field.getShip());
		assertTrue ("Destroyed because condition 0, ship must be deleted out of the previous/next ship ",shipv.getNextShip().getID() == shipn.getID());
		
	}
	
	@Test
	public void testSenseRegistershipdirection1(){
		
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(shipdirection, 5);
		assertTrue (sship.getSenseRegister(shipdirection) == 5);
		
		sship.setSenseRegister(shipdirection, 0);
		assertTrue (sship.getSenseRegister(shipdirection) == 0);
	}
	

	@Test (expected = AssertionError.class)
	public void testSenseRegistershipdirection2(){
		
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(shipdirection, -1);
		assertTrue ("ShipDirection not <0 ",sship.getSenseRegister(shipdirection) == -1);
		
		sship.setSenseRegister(shipdirection, 6);
		assertTrue ("ShipDirection not >5 ",sship.getSenseRegister(shipdirection) == 6);
	}
	
	@Test
	public void testSenseRegistershipload1(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(shipload, 0);
		assertTrue (sship.getSenseRegister(shipload) == 0);
		
		sship.setSenseRegister(shipload, 4);
		assertTrue (sship.getSenseRegister(shipload) == 4);
	}
	

	@Test (expected = AssertionError.class)
	public void testSenseRegistershipload2(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(shipload, -1);
		assertTrue ("Shipload not <0", sship.getSenseRegister(shipload) == -1);
		
		sship.setSenseRegister(shipload, 5);
		assertTrue ("Shipload not > 4", sship.getSenseRegister(shipload) == 5);
	}
	
	@Test
	public void testSenseRegistershipmoral(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(shipmoral, 0);
		assertTrue (sship.getSenseRegister(shipmoral) == 0);
		
		sship.setSenseRegister(shipmoral, 4);
		assertTrue (sship.getSenseRegister(shipmoral) == 4);
	}
	

	@Test (expected = AssertionError.class)
	public void testSenseRegistershipmoral2(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(shipmoral, -1);
		assertTrue ("Shipmoral not <0", sship.getSenseRegister(shipmoral) == -1);
		
		sship.setSenseRegister(shipmoral, 5);
		assertTrue ("Shipmoral not > 4", sship.getSenseRegister(shipmoral) == 5);
	}
		
	@Test
	public void testSenseRegistershipcondition1(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(shipcondition, 3);
		assertTrue (sship.getSenseRegister(shipcondition) == 3);
		
		sship.setSenseRegister(shipcondition, 0);
		assertTrue (sship.getSenseRegister(shipcondition) == 0);
	}
	

	@Test (expected = AssertionError.class)
	public void testSenseRegistershipcondition2(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(shipcondition, -1);
		assertTrue ("Shipcondition not <0", sship.getSenseRegister(shipcondition) == -1);
		
		sship.setSenseRegister(shipcondition, 4);
		assertTrue ("Shipcondition not > 3", sship.getSenseRegister(shipcondition) == 4);
	}
	
	@Test
	public void testSenseRegistersensecelltype1(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensecelltype, 0);
		assertTrue (sship.getSenseRegister(sensecelltype) == 0);
		
		sship.setSenseRegister(sensecelltype, 1);
		assertTrue (sship.getSenseRegister(sensecelltype) == 1);
		
		sship.setSenseRegister(sensecelltype, 2);
		assertTrue (sship.getSenseRegister(sensecelltype) == 2);
		
		sship.setSenseRegister(sensecelltype, 3);
		assertTrue (sship.getSenseRegister(sensecelltype) == 3);
	}
	

	@Test (expected = AssertionError.class)
	public void testSenseRegistersensecelltype2(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensecelltype, -1);
		assertTrue ("Sensecelltype not < 0", sship.getSenseRegister(sensecelltype) == -1);
		
		sship.setSenseRegister(sensecelltype, 4);
		assertTrue ("Sensecelltype not > 3", sship.getSenseRegister(sensecelltype) == 4);
	}
	
	@Test
	public void testSenseRegistersense1(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensetreasure, 0);
		assertTrue (sship.getSenseRegister(sensetreasure) == 0);
		
		sship.setSenseRegister(sensetreasure, 1);
		assertTrue (sship.getSenseRegister(sensetreasure) == 1);
		
	}
	

	@Test (expected = AssertionError.class)
	public void testSenseRegistersensetreasure2(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensetreasure, -1);
		assertTrue ("Sensetreasure not < 0", sship.getSenseRegister(sensetreasure) == -1);
		
		sship.setSenseRegister(sensetreasure, 2);
		assertTrue ("Sensetreasure not > 1", sship.getSenseRegister(sensetreasure) == 2);
	}
	
	@Test
	public void testSenseRegistersensesupply1(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensesupply, 0);
		assertTrue (sship.getSenseRegister(sensesupply) == 0);
		
		sship.setSenseRegister(sensesupply, 1);
		assertTrue (sship.getSenseRegister(sensesupply) == 1);
		
	}
	

	@Test (expected = AssertionError.class)
	public void testSenseRegistersensesupply2(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensesupply, -1);
		assertTrue ("Sensesupply not <0", sship.getSenseRegister(sensesupply) == -1);
		
		sship.setSenseRegister(sensesupply, 2);
		assertTrue ("Sensesupply not >1", sship.getSenseRegister(sensesupply) == 2);
	}
	
	@Test
	public void testSenseRegistersenseenemymarker(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(senseenemymarker, 0);
		assertTrue (sship.getSenseRegister(senseenemymarker) == 0);
		
		sship.setSenseRegister(senseenemymarker, 1);
		assertTrue (sship.getSenseRegister(senseenemymarker) == 1);
		
	}
	

	@Test (expected = AssertionError.class)
	public void testSenseRegistersenseenemymarker2(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(senseenemymarker, -1);
		assertTrue ("Senseenemymarker not <0", sship.getSenseRegister(senseenemymarker) == -1);
		
		sship.setSenseRegister(senseenemymarker, 2);
		assertTrue ("Senseenemymarker not > 1", sship.getSenseRegister(senseenemymarker) == 2);
	}
	
	@Test
	public void testSenseRegistersenseshiptype1(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(senseshiptype, 0);
		assertTrue (sship.getSenseRegister(senseshiptype) == 0);
		
		sship.setSenseRegister(senseshiptype, 1);
		assertTrue (sship.getSenseRegister(senseshiptype) == 1);
		
	}
	

	@Test (expected = AssertionError.class)
	public void testSenseRegistersenseshiptype2(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(senseshiptype, -1);
		assertTrue ("Senseshiptype not <0", sship.getSenseRegister(senseshiptype) == -1);
		
		sship.setSenseRegister(senseshiptype, 2);
		assertTrue ("Senseshiptype not >1",sship.getSenseRegister(senseshiptype) == 2);
	}
	

	@Test
	public void testSenseRegistersenseshiploaded1(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(senseloaded, 0);
		assertTrue (sship.getSenseRegister(senseloaded) == 0);
		
		sship.setSenseRegister(senseloaded, 1);
		assertTrue (sship.getSenseRegister(senseloaded) == 1);
		
	}
	

	@Test (expected = AssertionError.class)
	public void testSenseRegistersenseshiploaded2(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(senseloaded, -1);
		assertTrue ("Senseloaded not <0", sship.getSenseRegister(senseloaded) == -1);
		
		sship.setSenseRegister(senseloaded, 2);
		assertTrue ("Senseloaded not >1", sship.getSenseRegister(senseloaded) == 2);
	}
	
	@Test
	public void testSenseRegistersenseshipdirection1(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensedirection, 0);
		assertTrue (sship.getSenseRegister(sensedirection) == 0);
		
		sship.setSenseRegister(sensedirection, 1);
		assertTrue (sship.getSenseRegister(sensedirection) == 1);
		
		sship.setSenseRegister(sensedirection, 2);
		assertTrue (sship.getSenseRegister(sensedirection) == 2);
		
		sship.setSenseRegister(sensedirection, 3);
		assertTrue (sship.getSenseRegister(sensedirection) == 3);
		
		sship.setSenseRegister(sensedirection, 4);
		assertTrue (sship.getSenseRegister(sensedirection) == 4);
		
		sship.setSenseRegister(sensedirection, 5);
		assertTrue (sship.getSenseRegister(sensedirection) == 5);
		
	}
	

	@Test (expected = AssertionError.class)
	public void testSenseRegistersenseshipcondition2(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensedirection, -1);
		assertTrue ("Sensedirection not <0", sship.getSenseRegister(sensedirection) == -1);
		
		sship.setSenseRegister(sensedirection, 6);
		assertTrue ("Sensedirection not >5", sship.getSenseRegister(sensedirection) == 6);
	}
	

	@Test
	public void testSenseRegistersensesmarker01(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensemarker0, 0);
		assertTrue (sship.getSenseRegister(sensemarker0) == 0);
		
		sship.setSenseRegister(sensemarker0, 1);
		assertTrue (sship.getSenseRegister(sensemarker0) == 1);
			
	}
	@Test (expected = AssertionError.class)
	public void testSenseRegistersensesensemarker02(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensemarker0, -1);
		assertTrue ("Sensemarker not < 0", sship.getSenseRegister(sensemarker0) == -1);
		
		sship.setSenseRegister(sensemarker0, 2);
		assertTrue ("Sensemarker not >1", sship.getSenseRegister(sensemarker0) == 2);
	}
	
	@Test
	public void testSenseRegistersensesmarker11(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensemarker1, 0);
		assertTrue (sship.getSenseRegister(sensemarker1) == 0);
		
		sship.setSenseRegister(sensemarker1, 1);
		assertTrue (sship.getSenseRegister(sensemarker1) == 1);
			
	}
	

	@Test (expected = AssertionError.class)
	public void testSenseRegistersensesensemarker12(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensemarker1, -1);
		assertTrue ("Sensemarker1 not < 0", sship.getSenseRegister(sensemarker1) == -1);
		
		sship.setSenseRegister(sensemarker1, 2);
		assertTrue ("Sensemarker1 not >1", sship.getSenseRegister(sensemarker1) == 2);
	}
	
	@Test
	public void testSenseRegistersensesmarker21(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensemarker2, 0);
		assertTrue (sship.getSenseRegister(sensemarker2) == 0);
		
		sship.setSenseRegister(sensemarker2, 1);
		assertTrue (sship.getSenseRegister(sensemarker2) == 1);
			
	}
	

	@Test (expected = AssertionError.class)
	public void testSenseRegistersensesensemarker22(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensemarker2, -1);
		assertTrue ("Sensemarker2 not < 0", sship.getSenseRegister(sensemarker2) == -1);
		
		sship.setSenseRegister(sensemarker2, 2);
		assertTrue ("Sensemarker2 not >1", sship.getSenseRegister(sensemarker2) == 2);
	}
	
	@Test
	public void testSenseRegistersensesmarker31(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensemarker3, 0);
		assertTrue (sship.getSenseRegister(sensemarker3) == 0);
		
		sship.setSenseRegister(sensemarker3, 1);
		assertTrue (sship.getSenseRegister(sensemarker3) == 1);
			
	}
	@Test (expected = AssertionError.class)
	public void testSenseRegistersensesensemarker32(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensemarker3, -1);
		assertTrue ("Sensemarker3 not < 0", sship.getSenseRegister(sensemarker3) == -1);
		
		sship.setSenseRegister(sensemarker3, 2);
		assertTrue ("Sensemarker3 not >1", sship.getSenseRegister(sensemarker3) == 2);
	}
	

	@Test 
	public void testSenseRegistersensesensemarker41(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensemarker4, 0);
		assertTrue (sship.getSenseRegister(sensemarker4) == 0);
		
		sship.setSenseRegister(sensemarker4, 1);
		assertTrue (sship.getSenseRegister(sensemarker4) == 1);
	}
	
	@Test (expected = AssertionError.class)
	public void testSenseRegistersensesensemarker42(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensemarker4, -1);
		assertTrue ("Sensemarker4 not < 0", sship.getSenseRegister(sensemarker4) == -1);
		
		sship.setSenseRegister(sensemarker4, 2);
		assertTrue ("Sensemarker4 not >1", sship.getSenseRegister(sensemarker4) == 2);
	}
	
	@Test
	public void testSenseRegistersensesmarker51(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensemarker5, 0);
		assertTrue (sship.getSenseRegister(sensemarker5) == 0);
		
		sship.setSenseRegister(sensemarker5, 1);
		assertTrue (sship.getSenseRegister(sensemarker5) == 1);
			
	}
	@Test (expected = AssertionError.class)
	public void testSenseRegistersensesensemarker52(){
		sship = new Ship(null, field, 0, shipn);
		
		sship.setSenseRegister(sensemarker5, -1);
		assertTrue ("Sensemarker5 not < 0", sship.getSenseRegister(sensemarker5) == -1);
		
		sship.setSenseRegister(sensemarker5, 2);
		assertTrue ("Sensemarker5 not >1", sship.getSenseRegister(sensemarker5) == 2);
	}
		
	
}
