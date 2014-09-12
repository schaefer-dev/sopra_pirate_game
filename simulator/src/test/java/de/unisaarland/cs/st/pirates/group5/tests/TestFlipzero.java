package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import model.Team;
import model.Ship;

import org.junit.Before;
import org.junit.Test;

import commands.Flipzero;
import commands.Goto;
import controller.Command;

public class TestFlipzero {
	
	Flipzero flip  = new Flipzero(3,2);
	Goto 	 goto0 = new Goto(0);
	List<Command> flips = new ArrayList<Command>();
	Team	 a 	   = new Team('a',flips);
	Ship ship = new Ship(a, null, 1, null);
	
	@Before
	public void setUp(){
		flips.add(flip);
		flips.add(goto0);
		flips.add(goto0);
		
	}
	
	@Test
	public void Test(){
		int check = flip.getRandom();
		if (check <= 2 || check > 0)
			assertEquals("",ship.getPC(),0); 
		else if (check == 0)
			assertEquals("", ship.getPC(),2);
		else if (check > 2 || check < 0)
			fail("Die erzeugte RandomZahl ist außerhalb des Intervalls");
	}
	
	
}
