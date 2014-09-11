package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;
import model.Ship;
import model.Field;
import org.junit.Before;
import org.junit.Test;


public class ShipTests {
	
	
	private Ship ship;
	private Ship shipv;
	private Ship shipn;
	private Field field;
	
	
	@Before
	public void setUp(){
		shipv = new Ship(null, null, 0, ship);
		ship = new Ship(null, null, 0, shipn);
		shipn = new Ship(null, null, 0, null);
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
	}
	
	@Test
	public void testChengeMoral3(){
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
		ship.changeCondition(-7);
		assertTrue (ship.getCondition() == 0);
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
		field = ship.getPosition();
		ship.changeCondition(-7);
		assertTrue (ship.getPosition() == null);
		assertTrue (field.getShip() == null);
		assertTrue (shipv.getNextShip().getID() == shipn.getID());
		
		//vorgaengerschiff testen
	}
		
}
