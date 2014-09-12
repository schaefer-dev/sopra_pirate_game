package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;
import model.Field;
import model.Ship;
import model.Team;
import model.Water;

import org.junit.Before;
import org.junit.Test;

import commands.Mark;
import commands.Turn;
import commands.Unmark;

public class CommandUnMarkTests {
	
	@Test
	public void TestUnMarkBuoy(){
		Team teamA = new Team('a', null);
		
		Field waterField = new Water(null, 0,0, null);
		waterField.placeBuoy(0, teamA);
		
		Ship shipA = new Ship(teamA, waterField, 0, null);
		Unmark testUnmark = new Unmark(0);
		testUnmark.execute(shipA);
		
		assertTrue(waterField.getBuoys().size()==0);	
	}
	
	@Test
	public void TestUnMark2Buoys01(){
		Team teamA = new Team('a', null);
		Team teamB = new Team('b', null);
		Team teamC = new Team('c', null);
		
		Field waterField = new Water(null, 0,0, null);
		waterField.placeBuoy(0, teamA);
		waterField.placeBuoy(2, teamB);
		waterField.placeBuoy(0, teamC);
		
		Ship shipB = new Ship(teamB, waterField, 0, null);
		Unmark testUnmark = new Unmark(2);
		testUnmark.execute(shipB);
		assertTrue(waterField.getBuoys().size()==2);	
	}
	
	@Test
	public void TestUnMark2Buoys02(){
		Team teamA = new Team('a', null);
		Team teamB = new Team('b', null);
		Team teamC = new Team('c', null);
		
		Field waterField = new Water(null, 0,0, null);
		waterField.placeBuoy(0, teamA);
		waterField.placeBuoy(2, teamB);
		waterField.placeBuoy(0, teamC);
		Ship shipB = new Ship(teamB, waterField, 0, null);
		Ship shipC = new Ship(teamC, waterField, 0, null);
		Unmark testUnmark = new Unmark(1);
		testUnmark.execute(shipB);
		testUnmark.execute(shipC);
		assertTrue(waterField.getBuoys().size()==3);	
	}
	
	@Test
	public void TestUnMark2Buoys03(){
		Team teamA = new Team('a', null);
		Team teamB = new Team('b', null);
		Team teamC = new Team('c', null);
		
		Field waterField = new Water(null, 0,0, null);
		waterField.placeBuoy(0, teamA);
		waterField.placeBuoy(2, teamB);
		waterField.placeBuoy(2, teamC);
		waterField.placeBuoy(0, teamA);
		Ship shipA = new Ship(teamA, waterField, 0, null);
		Ship shipB = new Ship(teamB, waterField, 0, null);
		Ship shipC = new Ship(teamC, waterField, 0, null);
		Unmark testUnmark = new Unmark(0);
		Unmark testUnmark2 = new Unmark(2);
		testUnmark2.execute(shipB);
		testUnmark2.execute(shipC);
		testUnmark.execute(shipA);
		assertTrue(waterField.getBuoys().size()==0);	
	}
	
}
