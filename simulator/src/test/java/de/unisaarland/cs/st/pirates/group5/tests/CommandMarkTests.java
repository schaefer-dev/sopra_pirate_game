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
		
		assertTrue(waterField.getBuoys().get(0).getTeam().equals(teamA));	
	}
	
}
