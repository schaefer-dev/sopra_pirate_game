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

public class CommandMarkTests {
	
	@Test
	public void TestMarkBuoy(){
		Team teamA = new Team('a', null);
		Team teamB = new Team('b', null);
		Team teamC = new Team('c', null);
		Team teamD = new Team('d', null);
		
		Field waterField = new Water(null, 0,0, null);
		waterField.placeBuoy(0, teamA);
		
		assertTrue(waterField.getBuoys().get(0).getTeam().equals(teamA));	// assuming that we insert in the same order we got the placeBuoys
		assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamB));	
		assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamC));	
		assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamD));	
	}
	
	@Test
	public void TestMark2Buoys01(){
		Team teamA = new Team('a', null);
		Team teamB = new Team('b', null);
		Team teamC = new Team('c', null);
		Team teamD = new Team('d', null);
		
		Field waterField = new Water(null, 0,0, null);
		waterField.placeBuoy(0, teamA);
		waterField.placeBuoy(0, teamB);
		
		assertTrue(waterField.getBuoys().get(0).getTeam().equals(teamA));	
		assertTrue(waterField.getBuoys().get(1).getTeam().equals(teamB));	// assuming that we insert in the same order we got the placeBuoys
		assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamC));	
		assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamD));	
	}
	
	@Test
	public void TestMark2Buoys02(){
		Team teamA = new Team('a', null);
		Team teamB = new Team('b', null);
		Team teamC = new Team('c', null);
		Team teamD = new Team('d', null);
		
		Field waterField = new Water(null, 0,0, null);
		waterField.placeBuoy(0, teamA);
		waterField.placeBuoy(0, teamA);
		
		assertTrue(waterField.getBuoys().get(0).getTeam().equals(teamA));	// assuming that we insert in the same order we got the placeBuoys
		assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamB));	
		assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamC));	
		assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamD));	
		assertTrue(waterField.getBuoys().size()==1);
	}
	
	@Test
	public void TestMark2Buoys03(){
		Team teamA = new Team('a', null);
		Team teamB = new Team('b', null);
		Team teamC = new Team('c', null);
		Team teamD = new Team('d', null);
		
		Field waterField = new Water(null, 0,0, null);
		waterField.placeBuoy(0, teamA);
		waterField.placeBuoy(0, teamA);
		waterField.placeBuoy(3, teamB);
		
		assertTrue(waterField.getBuoys().get(0).getTeam().equals(teamA));	// assuming that we insert in the same order we got the placeBuoys
		assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamB));	
		assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamC));	
		assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamD));	
		assertFalse(waterField.getBuoys().get(1).getTeam().equals(teamA));	// assuming that we insert in the same order we got the placeBuoys
		assertTrue(waterField.getBuoys().get(1).getTeam().equals(teamB));	
		assertFalse(waterField.getBuoys().get(1).getTeam().equals(teamC));	
		assertFalse(waterField.getBuoys().get(1).getTeam().equals(teamD));	
		assertTrue(waterField.getBuoys().size()==2);
	}
	
	@Test
	public void TestMark2Buoys04(){
		Team teamA = new Team('a', null);
		Team teamB = new Team('b', null);
		Team teamC = new Team('c', null);
		Team teamD = new Team('d', null);
		
		Field waterField = new Water(null, 0,0, null);
		waterField.placeBuoy(0, teamA);
		waterField.placeBuoy(0, teamA);
		waterField.placeBuoy(3, teamB);
		waterField.placeBuoy(0, teamA);
		waterField.placeBuoy(0, teamC);
		
		assertTrue(waterField.getBuoys().get(0).getTeam().equals(teamA));	// assuming that we insert in the same order we got the placeBuoys
		assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamB));	
		assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamC));	
		assertFalse(waterField.getBuoys().get(0).getTeam().equals(teamD));	
		assertFalse(waterField.getBuoys().get(1).getTeam().equals(teamA));	// assuming that we insert in the same order we got the placeBuoys
		assertTrue(waterField.getBuoys().get(1).getTeam().equals(teamB));	
		assertFalse(waterField.getBuoys().get(1).getTeam().equals(teamC));	
		assertFalse(waterField.getBuoys().get(1).getTeam().equals(teamD));	
		assertFalse(waterField.getBuoys().get(2).getTeam().equals(teamA));	// assuming that we insert in the same order we got the placeBuoys
		assertFalse(waterField.getBuoys().get(2).getTeam().equals(teamB));	
		assertTrue(waterField.getBuoys().get(2).getTeam().equals(teamC));	
		assertFalse(waterField.getBuoys().get(2).getTeam().equals(teamD));	
		assertTrue(waterField.getBuoys().size()==2);
	}
	
}
