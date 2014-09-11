package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;
import model.Ship;

import org.junit.Before;
import org.junit.Test;


public class ShipTests {
	
	private Ship ship;
	private Ship ship2;
	
	
	@Before
	protected void setUp(){
		ship = new Ship(null, null, 0, ship2);
	}
	
	@Test
	public void testChangeMoral1(){
		ship.changeMoral(-1);
		assert ship.getMoral() == 3 ;
	}

	@Test
	public void testChangeMoral2(){
		ship.changeMoral(3);
		assert ship.getMoral() == 4 ;
	}
	
	@Test
	public void testChengeMoral3(){
		ship.changeMoral(-7);
		assert ship.getMoral() == 0 ;
	}
	
	@Test
	public void testChangeCondition1(){
	ship.changeCondition(-1);
	assert ship.getCondition() == 2;
	}
	
	@Test
	public void testChangeCondition2(){
		ship.changeCondition(3);
		assert ship.getCondition() == 3;
	}
	
	@Test
	public void testChangeCondition3(){
		ship.changeCondition(-7);
		assert ship.getCondition() == 0;
	}
	

	@Test
	public void testChangePause1(){
		ship.changeCondition(0);
		assert ship.getCondition() == 0;
	}
	
	@Test
	public void testChangePause2(){
		ship.changeCondition(1);
		assert ship.getCondition() == 1;
	}
	
	@Test
	public void testChangePause3(){
		ship.changeCondition(9);
		assert ship.getCondition() == 8;
	}
	

	@Test
	public void testChangePause4(){
		ship.changeCondition(-9);
		assert ship.getCondition() == 0;
	}
	
	@Test
	public void testChangeDirection1(){
		
		ship.changeDirection(true);
		assert ship.getShipDirection() == 5;
		ship.changeDirection(true);
		assert  ship.getShipDirection() == 4;
		ship.changeDirection(true);
		assert ship.getShipDirection() == 3;
		ship.changeDirection(true);
		assert  ship.getShipDirection() == 2;
		ship.changeDirection(true);
		assert ship.getShipDirection() == 1;
		ship.changeDirection(true);
		assert  ship.getShipDirection() == 0;
		
	}
	
	@Test
	public void testChangeDirection2(){
		ship.changeDirection(false);
		assert ship.getShipDirection() == 1;
		ship.changeDirection(false);
		assert ship.getShipDirection() == 2;
		ship.changeDirection(false);
		assert ship.getShipDirection() == 3;
		ship.changeDirection(false);
		assert ship.getShipDirection() == 4;
		ship.changeDirection(false);
		assert ship.getShipDirection() == 5;
		ship.changeDirection(false);
		assert ship.getShipDirection() == 0;
	}
	
	@Test
	public void testRelativeToAbsolute(){
		assert ship.relativeToAbsoluteDirection(0) == 0;
		assert ship.relativeToAbsoluteDirection(1) == 1;
		assert ship.relativeToAbsoluteDirection(2) == 2;
		assert ship.relativeToAbsoluteDirection(3) == 3;
		assert ship.relativeToAbsoluteDirection(4) == 4;
		assert ship.relativeToAbsoluteDirection(5) == 5;
		assert ship.relativeToAbsoluteDirection(6) == 6;
		
		ship.changeDirection(false);
		assert ship.relativeToAbsoluteDirection(0) == 1;
		assert ship.relativeToAbsoluteDirection(1) == 2;
		assert ship.relativeToAbsoluteDirection(2) == 3;
		assert ship.relativeToAbsoluteDirection(3) == 4;
		assert ship.relativeToAbsoluteDirection(4) == 5;
		assert ship.relativeToAbsoluteDirection(5) == 0;
		assert ship.relativeToAbsoluteDirection(6) == 6;
		
		ship.changeDirection(false);
		assert ship.relativeToAbsoluteDirection(0) == 2;
		assert ship.relativeToAbsoluteDirection(1) == 3;
		assert ship.relativeToAbsoluteDirection(2) == 4;
		assert ship.relativeToAbsoluteDirection(3) == 5;
		assert ship.relativeToAbsoluteDirection(4) == 0;
		assert ship.relativeToAbsoluteDirection(5) == 1;
		assert ship.relativeToAbsoluteDirection(6) == 6;
		
		ship.changeDirection(false);
		assert ship.relativeToAbsoluteDirection(0) == 3;
		assert ship.relativeToAbsoluteDirection(1) == 4;
		assert ship.relativeToAbsoluteDirection(2) == 5;
		assert ship.relativeToAbsoluteDirection(3) == 0;
		assert ship.relativeToAbsoluteDirection(4) == 1;
		assert ship.relativeToAbsoluteDirection(5) == 2;
		assert ship.relativeToAbsoluteDirection(6) == 6;
		
		ship.changeDirection(false);
		assert ship.relativeToAbsoluteDirection(0) == 4;
		assert ship.relativeToAbsoluteDirection(1) == 5;
		assert ship.relativeToAbsoluteDirection(2) == 0;
		assert ship.relativeToAbsoluteDirection(3) == 1;
		assert ship.relativeToAbsoluteDirection(4) == 2;
		assert ship.relativeToAbsoluteDirection(5) == 3;
		assert ship.relativeToAbsoluteDirection(6) == 6;
		
		ship.changeDirection(false);
		assert ship.relativeToAbsoluteDirection(0) == 5;
		assert ship.relativeToAbsoluteDirection(1) == 0;
		assert ship.relativeToAbsoluteDirection(2) == 1;
		assert ship.relativeToAbsoluteDirection(3) == 2;
		assert ship.relativeToAbsoluteDirection(4) == 3;
		assert ship.relativeToAbsoluteDirection(5) == 4;
		assert ship.relativeToAbsoluteDirection(6) == 6;
		
		ship.changeDirection(false);
		assert ship.relativeToAbsoluteDirection(0) == 0;
		assert ship.relativeToAbsoluteDirection(1) == 1;
		assert ship.relativeToAbsoluteDirection(2) == 2;
		assert ship.relativeToAbsoluteDirection(3) == 3;
		assert ship.relativeToAbsoluteDirection(4) == 4;
		assert ship.relativeToAbsoluteDirection(5) == 5;
		assert ship.relativeToAbsoluteDirection(6) == 6;
	}
	@Test
	public void testDestroy(){
		// Problem destroy private
	}
		
}
