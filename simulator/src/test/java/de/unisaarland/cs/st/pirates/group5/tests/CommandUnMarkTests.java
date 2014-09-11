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

public class CommandUnMarkTests {
	
	@Test
	public void TestUnMarkBuoy(){
		Team teamA = new Team('a', null);
		Team teamB = new Team('b', null);
		Team teamC = new Team('c', null);
		Team teamD = new Team('d', null);
		
		Field waterField = new Water(null, 0,0, null);
		waterField.placeBuoy(0, teamA);
		waterField.deleteBuoy(teamA,0);
		
		assertTrue(waterField.getBuoys().size()==1);	
	}
	
	@Test
	public void TestUnMark2Buoys01(){
		Team teamA = new Team('a', null);
		Team teamB = new Team('b', null);
		Team teamC = new Team('c', null);
		Team teamD = new Team('d', null);
		
		Field waterField = new Water(null, 0,0, null);
		waterField.placeBuoy(0, teamA);
		waterField.placeBuoy(2, teamB);
		waterField.placeBuoy(0, teamC);
		waterField.deleteBuoy(teamC,0);
		waterField.deleteBuoy(teamA,0);
		assertTrue(waterField.getBuoys().size()==1);	
	}
	
	@Test
	public void TestUnMark2Buoys02(){
		Team teamA = new Team('a', null);
		Team teamB = new Team('b', null);
		Team teamC = new Team('c', null);
		Team teamD = new Team('d', null);
		
		Field waterField = new Water(null, 0,0, null);
		waterField.placeBuoy(0, teamA);
		waterField.placeBuoy(2, teamB);
		waterField.placeBuoy(0, teamC);
		waterField.deleteBuoy(teamC,2);
		waterField.deleteBuoy(teamA,2);
		assertTrue(waterField.getBuoys().size()==3);	
	}
	
	@Test
	public void TestUnMark2Buoys03(){
		Team teamA = new Team('a', null);
		Team teamB = new Team('b', null);
		Team teamC = new Team('c', null);
		Team teamD = new Team('d', null);
		
		Field waterField = new Water(null, 0,0, null);
		waterField.placeBuoy(0, teamA);
		waterField.placeBuoy(2, teamB);
		waterField.placeBuoy(2, teamC);
		waterField.placeBuoy(0, teamA);
		waterField.deleteBuoy(teamC,2);
		waterField.deleteBuoy(teamA,0);
		assertTrue(waterField.getBuoys().size()==1);	
	}
	
}
