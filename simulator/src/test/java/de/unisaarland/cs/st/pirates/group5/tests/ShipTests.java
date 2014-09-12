package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;
import model.Register;
import model.Ship;
import model.Field;
import model.Water;

import org.junit.Before;
import org.junit.Test;


public class ShipTests {
	
	
	private Ship ship;
	private Ship shipv;
	private Ship shipn;
	private Field field1;
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
		shipv = new Ship(null, null, 0, null);
		ship = new Ship(null, null, 0, shipv);
		shipn = new Ship(null, null, 0, ship);
		field = new Water(null, 0, 0, null);
		field.setShip(ship);
		sship = new Ship(null, field, 0, shipn);
		
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
	public void testChangeMoral1(){
		ship.changeMoral(-1);
		assertTrue (ship.getMoral() == 3) ;
	}

	@Test
	public void testChangeMoral2(){
		ship.changeMoral(3);
		assertTrue (ship.getMoral() == 4) ;
		assertTrue (ship.getNoPositivActionCounter() == 0);
	}
	
	@Test
	public void testChangeMoral3(){
		ship.changeMoral(-7);
		assertTrue (ship.getMoral() == 0) ;
	}
	
	@Test
	public void testChangeCondition1(){
	ship.changeCondition(-1);
	assertTrue (ship.getCondition() == 2);
	}
	
	@Test
	public void testChangeCondition2(){
		ship.changeCondition(3);
		assertTrue (ship.getCondition() == 3);
	}
	
	@Test
	public void testChangeCondition3(){
		ship.changeCondition(-1);
		ship.changeCondition(-1);
		ship.changeCondition(-1);
		assertNull (field.getShip());
	}
	

	@Test
	public void testChangePause1(){
		ship.changeCondition(0);
		assertTrue (ship.getCondition() == 0);
	}
	
	@Test
	public void testChangePause2(){
		ship.changeCondition(1);
		assertTrue (ship.getCondition() == 1);
	}
	
	@Test
	public void testChangePause3(){
		ship.changeCondition(9);
		assertTrue (ship.getCondition() == 8);
	}
	

	@Test
	public void testChangePause4(){
		ship.changeCondition(-9);
		assertTrue (ship.getCondition() == 0);
	}
	
	@Test
	public void testChangeDirection1(){
		
		ship.changeDirection(true);
		assertTrue( ship.getShipDirection() == 5);
		ship.changeDirection(true);
		assertTrue  (ship.getShipDirection() == 4);
		ship.changeDirection(true);
		assertTrue (ship.getShipDirection() == 3);
		ship.changeDirection(true);
		assertTrue  (ship.getShipDirection() == 2);
		ship.changeDirection(true);
		assertTrue (ship.getShipDirection() == 1);
		ship.changeDirection(true);
		assertTrue  (ship.getShipDirection() == 0);
		
	}
	
	@Test
	public void testChangeDirection2(){
		ship.changeDirection(false);
		assertTrue (ship.getShipDirection() == 1);
		ship.changeDirection(false);
		assertTrue (ship.getShipDirection() == 2);
		ship.changeDirection(false);
		assertTrue (ship.getShipDirection() == 3);
		ship.changeDirection(false);
		assertTrue (ship.getShipDirection() == 4);
		ship.changeDirection(false);
		assertTrue (ship.getShipDirection() == 5);
		ship.changeDirection(false);
		assertTrue (ship.getShipDirection() == 0);
	}
	
	@Test
	public void testRelativeToAbsolute(){
		assertTrue (ship.relativeToAbsoluteDirection(0) == 0);
		assertTrue (ship.relativeToAbsoluteDirection(1) == 1);
		assertTrue (ship.relativeToAbsoluteDirection(2) == 2);
		assertTrue (ship.relativeToAbsoluteDirection(3) == 3);
		assertTrue (ship.relativeToAbsoluteDirection(4) == 4);
		assertTrue (ship.relativeToAbsoluteDirection(5) == 5);
		assertTrue (ship.relativeToAbsoluteDirection(6) == 6);
		
		ship.changeDirection(false);
		assertTrue (ship.relativeToAbsoluteDirection(0) == 1);
		assertTrue (ship.relativeToAbsoluteDirection(1) == 2);
		assertTrue (ship.relativeToAbsoluteDirection(2) == 3);
		assertTrue (ship.relativeToAbsoluteDirection(3) == 4);
		assertTrue (ship.relativeToAbsoluteDirection(4) == 5);
		assertTrue (ship.relativeToAbsoluteDirection(5) == 0);
		assertTrue (ship.relativeToAbsoluteDirection(6) == 6);
		
		ship.changeDirection(false);
		assertTrue (ship.relativeToAbsoluteDirection(0) == 2);
		assertTrue (ship.relativeToAbsoluteDirection(1) == 3);
		assertTrue (ship.relativeToAbsoluteDirection(2) == 4);
		assertTrue (ship.relativeToAbsoluteDirection(3) == 5);
		assertTrue (ship.relativeToAbsoluteDirection(4) == 0);
		assertTrue (ship.relativeToAbsoluteDirection(5) == 1);
		assertTrue (ship.relativeToAbsoluteDirection(6) == 6);
		
		ship.changeDirection(false);
		assertTrue (ship.relativeToAbsoluteDirection(0) == 3);
		assertTrue (ship.relativeToAbsoluteDirection(1) == 4);
		assertTrue (ship.relativeToAbsoluteDirection(2) == 5);
		assertTrue (ship.relativeToAbsoluteDirection(3) == 0);
		assertTrue (ship.relativeToAbsoluteDirection(4) == 1);
		assertTrue (ship.relativeToAbsoluteDirection(5) == 2);
		assertTrue (ship.relativeToAbsoluteDirection(6) == 6);
		
		ship.changeDirection(false);
		assertTrue (ship.relativeToAbsoluteDirection(0) == 4);
		assertTrue (ship.relativeToAbsoluteDirection(1) == 5);
		assertTrue (ship.relativeToAbsoluteDirection(2) == 0);
		assertTrue (ship.relativeToAbsoluteDirection(3) == 1);
		assertTrue (ship.relativeToAbsoluteDirection(4) == 2);
		assertTrue (ship.relativeToAbsoluteDirection(5) == 3);
		assertTrue (ship.relativeToAbsoluteDirection(6) == 6);
		
		ship.changeDirection(false);
		assertTrue (ship.relativeToAbsoluteDirection(0) == 5);
		assertTrue (ship.relativeToAbsoluteDirection(1) == 0);
		assertTrue (ship.relativeToAbsoluteDirection(2) == 1);
		assertTrue (ship.relativeToAbsoluteDirection(3) == 2);
		assertTrue (ship.relativeToAbsoluteDirection(4) == 3);
		assertTrue (ship.relativeToAbsoluteDirection(5) == 4);
		assertTrue (ship.relativeToAbsoluteDirection(6) == 6);
		
		ship.changeDirection(false);
		assertTrue (ship.relativeToAbsoluteDirection(0) == 0);
		assertTrue (ship.relativeToAbsoluteDirection(1) == 1);
		assertTrue (ship.relativeToAbsoluteDirection(2) == 2);
		assertTrue (ship.relativeToAbsoluteDirection(3) == 3);
		assertTrue (ship.relativeToAbsoluteDirection(4) == 4);
		assertTrue (ship.relativeToAbsoluteDirection(5) == 5);
		assertTrue (ship.relativeToAbsoluteDirection(6) == 6);
	}
	@Test
	public void testDestroy(){
		field1  = ship.getPosition();
		ship.changeCondition(-7);
		assertNull (ship.getPosition());
		assertNull (field1.getShip());
		assertTrue (shipv.getNextShip().getID() == shipn.getID());
		
		//vorgaengerschiff testen
	}
	
	@Test
	public void testSenseRegistershipdirection1(){
		sship.setSenseRegister(shipdirection, 5);
		assertTrue (sship.getSenseRegister(shipdirection) == 5);
		
		sship.setSenseRegister(shipdirection, 0);
		assertTrue (sship.getSenseRegister(shipdirection) == 0);
	}
	

	@Test (expected = AssertionError.class)
	public void testSenseRegistershipdirection2(){
		sship.setSenseRegister(shipdirection, -1);
		assertTrue (sship.getSenseRegister(shipdirection) == -1);
		
		sship.setSenseRegister(shipdirection, 6);
		assertTrue (sship.getSenseRegister(shipdirection) == 6);
	}
	
	@Test
	public void testSenseRegistershipload1(){
		sship.setSenseRegister(shipload, 0);
		assertTrue (sship.getSenseRegister(shipload) == 0);
		
		sship.setSenseRegister(shipload, 4);
		assertTrue (sship.getSenseRegister(shipload) == 4);
	}
	

	@Test (expected = AssertionError.class)
	public void testSenseRegistershipload2(){
		sship.setSenseRegister(shipload, -1);
		assertTrue (sship.getSenseRegister(shipload) == -1);
		
		sship.setSenseRegister(shipload, 5);
		assertTrue (sship.getSenseRegister(shipload) == 5);
	}
	
	@Test
	public void testSenseRegistershipmoral(){
		sship.setSenseRegister(shipmoral, 0);
		assertTrue (sship.getSenseRegister(shipmoral) == 0);
		
		sship.setSenseRegister(shipmoral, 4);
		assertTrue (sship.getSenseRegister(shipmoral) == 4);
	}
	

	@Test (expected = AssertionError.class)
	public void testSenseRegistershipmoral2(){
		sship.setSenseRegister(shipmoral, -1);
		assertTrue (sship.getSenseRegister(shipmoral) == -1);
		
		sship.setSenseRegister(shipmoral, 5);
		assertTrue (sship.getSenseRegister(shipmoral) == 5);
	}
		
	@Test
	public void testSenseRegistershipcondition1(){
		sship.setSenseRegister(shipcondition, 3);
		assertTrue (sship.getSenseRegister(shipcondition) == 3);
		
		sship.setSenseRegister(shipcondition, 0);
		assertTrue (sship.getSenseRegister(shipcondition) == 0);
	}
	

	@Test (expected = AssertionError.class)
	public void testSenseRegistershipcondition2(){
		sship.setSenseRegister(shipcondition, -1);
		assertTrue (sship.getSenseRegister(shipcondition) == -1);
		
		sship.setSenseRegister(shipcondition, 4);
		assertTrue (sship.getSenseRegister(shipcondition) == 4);
	}
	
	@Test
	public void testSenseRegistersensecelltype1(){
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
		sship.setSenseRegister(sensecelltype, -1);
		assertTrue (sship.getSenseRegister(sensecelltype) == -1);
		
		sship.setSenseRegister(sensecelltype, 4);
		assertTrue (sship.getSenseRegister(sensecelltype) == 4);
	}
	
	@Test
	public void testSenseRegistersense1(){
		sship.setSenseRegister(sensetreasure, 0);
		assertTrue (sship.getSenseRegister(sensetreasure) == 0);
		
		sship.setSenseRegister(sensetreasure, 1);
		assertTrue (sship.getSenseRegister(sensetreasure) == 1);
		
	}
	

	@Test (expected = AssertionError.class)
	public void testSenseRegistersensetreasure2(){
		sship.setSenseRegister(sensetreasure, -1);
		assertTrue (sship.getSenseRegister(sensetreasure) == -1);
		
		sship.setSenseRegister(sensetreasure, 2);
		assertTrue (sship.getSenseRegister(sensetreasure) == 2);
	}
	
	@Test
	public void testSenseRegistersensesupply1(){
		sship.setSenseRegister(sensesupply, 0);
		assertTrue (sship.getSenseRegister(sensesupply) == 0);
		
		sship.setSenseRegister(sensesupply, 1);
		assertTrue (sship.getSenseRegister(sensesupply) == 1);
		
	}
	

	@Test (expected = AssertionError.class)
	public void testSenseRegistersensesupply2(){
		sship.setSenseRegister(sensesupply, -1);
		assertTrue (sship.getSenseRegister(sensesupply) == -1);
		
		sship.setSenseRegister(sensesupply, 2);
		assertTrue (sship.getSenseRegister(sensesupply) == 2);
	}
	
	@Test
	public void testSenseRegistersenseenemymarker(){
		sship.setSenseRegister(senseenemymarker, 0);
		assertTrue (sship.getSenseRegister(senseenemymarker) == 0);
		
		sship.setSenseRegister(senseenemymarker, 1);
		assertTrue (sship.getSenseRegister(senseenemymarker) == 1);
		
	}
	

	@Test (expected = AssertionError.class)
	public void testSenseRegistersenseenemymarker2(){
		sship.setSenseRegister(senseenemymarker, -1);
		assertTrue (sship.getSenseRegister(senseenemymarker) == -1);
		
		sship.setSenseRegister(senseenemymarker, 2);
		assertTrue (sship.getSenseRegister(senseenemymarker) == 2);
	}
	
	@Test
	public void testSenseRegistersenseshiptype1(){
		sship.setSenseRegister(senseshiptype, 0);
		assertTrue (sship.getSenseRegister(senseshiptype) == 0);
		
		sship.setSenseRegister(senseshiptype, 1);
		assertTrue (sship.getSenseRegister(senseshiptype) == 1);
		
	}
	

	@Test (expected = AssertionError.class)
	public void testSenseRegistersenseshiptype2(){
		sship.setSenseRegister(senseshiptype, -1);
		assertTrue (sship.getSenseRegister(senseshiptype) == -1);
		
		sship.setSenseRegister(senseshiptype, 2);
		assertTrue (sship.getSenseRegister(senseshiptype) == 2);
	}
	

	@Test
	public void testSenseRegistersenseshiploaded1(){
		sship.setSenseRegister(senseloaded, 0);
		assertTrue (sship.getSenseRegister(senseloaded) == 0);
		
		sship.setSenseRegister(senseloaded, 1);
		assertTrue (sship.getSenseRegister(senseloaded) == 1);
		
	}
	

	@Test (expected = AssertionError.class)
	public void testSenseRegistersenseshiploaded2(){
		sship.setSenseRegister(senseloaded, -1);
		assertTrue (sship.getSenseRegister(senseloaded) == -1);
		
		sship.setSenseRegister(senseloaded, 2);
		assertTrue (sship.getSenseRegister(senseloaded) == 2);
	}
	
	@Test
	public void testSenseRegistersenseshipdirection1(){
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
		sship.setSenseRegister(sensedirection, -1);
		assertTrue (sship.getSenseRegister(sensedirection) == -1);
		
		sship.setSenseRegister(sensedirection, 6);
		assertTrue (sship.getSenseRegister(sensedirection) == 6);
	}
	
	@Test
	public void testSenseRegistersensesmarker11(){
		sship.setSenseRegister(sensemarker1, 0);
		assertTrue (sship.getSenseRegister(sensemarker1) == 0);
		
		sship.setSenseRegister(sensemarker1, 1);
		assertTrue (sship.getSenseRegister(sensemarker1) == 1);
			
	}
	

	@Test (expected = AssertionError.class)
	public void testSenseRegistersensesensemarker12(){
		sship.setSenseRegister(sensemarker1, -1);
		assertTrue (sship.getSenseRegister(sensemarker1) == -1);
		
		sship.setSenseRegister(sensemarker1, 2);
		assertTrue (sship.getSenseRegister(sensemarker1) == 2);
	}
	
	@Test
	public void testSenseRegistersensesmarker21(){
		sship.setSenseRegister(sensemarker2, 0);
		assertTrue (sship.getSenseRegister(sensemarker2) == 0);
		
		sship.setSenseRegister(sensemarker2, 1);
		assertTrue (sship.getSenseRegister(sensemarker2) == 1);
			
	}
	

	@Test (expected = AssertionError.class)
	public void testSenseRegistersensesensemarker22(){
		sship.setSenseRegister(sensemarker2, -1);
		assertTrue (sship.getSenseRegister(sensemarker2) == -1);
		
		sship.setSenseRegister(sensemarker2, 2);
		assertTrue (sship.getSenseRegister(sensemarker2) == 2);
	}
	
	@Test
	public void testSenseRegistersensesmarker31(){
		sship.setSenseRegister(sensemarker3, 0);
		assertTrue (sship.getSenseRegister(sensemarker3) == 0);
		
		sship.setSenseRegister(sensemarker3, 1);
		assertTrue (sship.getSenseRegister(sensemarker3) == 1);
			
	}
	@Test (expected = AssertionError.class)
	public void testSenseRegistersensesensemarker32(){
		sship.setSenseRegister(sensemarker3, -1);
		assertTrue (sship.getSenseRegister(sensemarker3) == -1);
		
		sship.setSenseRegister(sensemarker3, 2);
		assertTrue (sship.getSenseRegister(sensemarker3) == 2);
	}
	

	@Test 
	public void testSenseRegistersensesensemarker41(){
		sship.setSenseRegister(sensemarker4, 0);
		assertTrue (sship.getSenseRegister(sensemarker4) == 0);
		
		sship.setSenseRegister(sensemarker4, 1);
		assertTrue (sship.getSenseRegister(sensemarker4) == 1);
	}
	
	@Test (expected = AssertionError.class)
	public void testSenseRegistersensesensemarker42(){
		sship.setSenseRegister(sensemarker4, -1);
		assertTrue (sship.getSenseRegister(sensemarker4) == -1);
		
		sship.setSenseRegister(sensemarker4, 2);
		assertTrue (sship.getSenseRegister(sensemarker4) == 2);
	}
	
	@Test
	public void testSenseRegistersensesmarker51(){
		sship.setSenseRegister(sensemarker5, 0);
		assertTrue (sship.getSenseRegister(sensemarker5) == 0);
		
		sship.setSenseRegister(sensemarker5, 1);
		assertTrue (sship.getSenseRegister(sensemarker5) == 1);
			
	}
	@Test (expected = AssertionError.class)
	public void testSenseRegistersensesensemarker52(){
		sship.setSenseRegister(sensemarker5, -1);
		assertTrue (sship.getSenseRegister(sensemarker5) == -1);
		
		sship.setSenseRegister(sensemarker5, 2);
		assertTrue (sship.getSenseRegister(sensemarker5) == 2);
	}
	

	@Test
	public void testSenseRegistersensesmarker01(){
		sship.setSenseRegister(sensemarker0, 0);
		assertTrue (sship.getSenseRegister(sensemarker0) == 0);
		
		sship.setSenseRegister(sensemarker0, 1);
		assertTrue (sship.getSenseRegister(sensemarker0) == 1);
			
	}
	@Test (expected = AssertionError.class)
	public void testSenseRegistersensesensemarker02(){
		sship.setSenseRegister(sensemarker0, -1);
		assertTrue (sship.getSenseRegister(sensemarker0) == -1);
		
		sship.setSenseRegister(sensemarker0, 2);
		assertTrue (sship.getSenseRegister(sensemarker0) == 2);
	}
	
	
	
	
}
