package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;
import model.Team;
import model.Treasure;
import model.Buoy;

import org.junit.*;

public class EntityTests {
	
	/*
	 * simple Tests for Entity types
	 * 
	 * test Constructors + getters and setters in Buoy and Treasure
	 */
	
	@Test
	public void testCreateTreasure(){
		Treasure testTreasure = new Treasure(1,5);
		assertTrue(testTreasure.getValue()==5);
	}
	
	@Test
	public void testChangeTreasureValue01(){
		Treasure testTreasure = new Treasure(1,5);
		testTreasure.changeValue(3);
		assertTrue(testTreasure.getValue()==8);
	}
	
	@Test
	public void testChangeTreasureValue02(){
		Treasure testTreasure = new Treasure(1,5);
		testTreasure.changeValue(-2);
		assertTrue(testTreasure.getValue()==8);
	}
	
	@Test
	public void testCreateBuoy(){
		Team teamA = new Team('a',null);
		Buoy testBuoy = new Buoy(0,teamA,3);
		assertTrue(testBuoy.getTeam().equals(teamA));
		assertTrue(testBuoy.getType()==3);
	}
	@Test (expected=Exception.class)
	public void testCreateBuoyInvalid01(){
		Team teamA = new Team('a',null);
		Buoy testBuoy = new Buoy(0,teamA,6);
		assertTrue(testBuoy.getTeam().equals(teamA));
		assertTrue(testBuoy.getType()==6);
	}
	@Test (expected=Exception.class)
	public void testCreateBuoyInvalid02(){
		Team teamA = new Team('a',null);
		Buoy testBuoy = new Buoy(0,teamA,-1);
		assertTrue(testBuoy.getTeam().equals(teamA));
		assertTrue(testBuoy.getType()==-1);
	}
	
}
