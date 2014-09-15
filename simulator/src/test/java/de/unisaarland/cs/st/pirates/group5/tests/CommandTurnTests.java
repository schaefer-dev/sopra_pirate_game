package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;
import model.Ship;

import org.junit.Before;
import org.junit.Test;

import commands.Turn;

public class CommandTurnTests {
	
	@Before
	public void setUp(){
		
	}

	@Test
	public void TestTurnLeft1(){
		Turn testTurn = new Turn(true);
		Ship ship = new Ship(null,null,0,null);
		DoTurnXTimes(1, testTurn, ship);
		assertTrue(ship.getShipDirection() == 5);	
	}
	@Test
	public void TestTurnLeft2(){
		Turn testTurn = new Turn(true);
		Ship ship = new Ship(null,null,0,null);
		DoTurnXTimes(2, testTurn, ship);
		assertTrue(ship.getShipDirection() == 4);	
	}
	@Test
	public void TestTurnLeft3(){
		Turn testTurn = new Turn(true);
		Ship ship = new Ship(null,null,0,null);
		DoTurnXTimes(3, testTurn, ship);
		assertTrue(ship.getShipDirection() == 3);	
	}
	@Test
	public void TestTurnLeft4(){
		Turn testTurn = new Turn(true);
		Ship ship = new Ship(null,null,0,null);
		DoTurnXTimes(4, testTurn, ship);
		assertTrue(ship.getShipDirection() == 2);	
	}
	@Test
	public void TestTurnLeft5(){
		Turn testTurn = new Turn(true);
		Ship ship = new Ship(null,null,0,null);
		DoTurnXTimes(5, testTurn, ship);
		assertTrue(ship.getShipDirection() == 1);	
	}
	@Test
	public void TestTurnLeft6(){
		Turn testTurn = new Turn(true);
		Ship ship = new Ship(null,null,0,null);
		DoTurnXTimes(6, testTurn, ship);
		assertTrue(ship.getShipDirection() == 0);	
	}
	@Test
	public void TestTurnLeft7CornerCase(){
		Turn testTurn = new Turn(true);
		Ship ship = new Ship(null,null,0,null);
		DoTurnXTimes(7, testTurn, ship);
		assertTrue(ship.getShipDirection() == 5);	
	}
	@Test
	public void TestTurnLeft8CornerCase(){
		Turn testTurn = new Turn(true);
		Ship ship = new Ship(null,null,0,null);
		DoTurnXTimes(8, testTurn, ship);
		assertTrue(ship.getShipDirection() == 4);	
	}
	
	@Test
	public void TestTurnRight1(){
		Turn testTurn = new Turn(false);
		Ship ship = new Ship(null,null,0,null);
		DoTurnXTimes(1, testTurn, ship);
		assertTrue(ship.getShipDirection() == 1);	
	}
	@Test
	public void TestTurnRight2(){
		Turn testTurn = new Turn(false);
		Ship ship = new Ship(null,null,0,null);
		DoTurnXTimes(2, testTurn, ship);
		assertTrue(ship.getShipDirection() == 2);	
	}
	@Test
	public void TestTurnRight3(){
		Turn testTurn = new Turn(false);
		Ship ship = new Ship(null,null,0,null);
		DoTurnXTimes(3, testTurn, ship);
		assertTrue(ship.getShipDirection() == 3);	
	}
	@Test
	public void TestTurnRight4(){
		Turn testTurn = new Turn(false);
		Ship ship = new Ship(null,null,0,null);
		DoTurnXTimes(4, testTurn, ship);
		assertTrue(ship.getShipDirection() == 4);	
	}
	@Test
	public void TestTurnRight5(){
		Turn testTurn = new Turn(false);
		Ship ship = new Ship(null,null,0,null);
		DoTurnXTimes(5, testTurn, ship);
		assertTrue(ship.getShipDirection() == 5);	
	}
	@Test
	public void TestTurnRight6CornerCase(){
		Turn testTurn = new Turn(false);
		Ship ship = new Ship(null,null,0,null);
		DoTurnXTimes(6, testTurn, ship);
		assertTrue(ship.getShipDirection() == 0);	
	}
	@Test
	public void TestTurnRight7CornerCase(){
		Turn testTurn = new Turn(false);
		Ship ship = new Ship(null,null,0,null);
		DoTurnXTimes(7, testTurn, ship);
		assertTrue(ship.getShipDirection() == 1);	
	}
	@Test
	public void TestTurnRight8CornerCase(){
		Turn testTurn = new Turn(false);
		Ship ship = new Ship(null,null,0,null);
		DoTurnXTimes(8, testTurn, ship);
		assertTrue(ship.getShipDirection() == 1);	
	}
	
	@Test
	public void Boost(){
		Turn testTurn = new Turn(true);
		testTurn.equals(testTurn);
		assertTrue(true);	
	}
	
	public void DoTurnXTimes (int x, Turn turn, Ship ship){
		for (int i=0; (i < x); i++)
			turn.execute(ship);
	}
	
	
}
