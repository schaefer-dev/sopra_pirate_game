package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import model.Field;
import model.Ship;
import model.Team;
import model.Water;

import org.junit.Test;

import commands.Drop;
import commands.Move;
import commands.Unmark;
import controller.Command;

public class TeamTests {

	/*
	 * test addLoot for pointinc i
	 * 
	 * test addShip/deleteShip in ShipList
	 * 
	 * test getCommand which jumps into Tactic[pc] and gives Command
	 * 
	 * test Constructor
	 */
	

	@Test
	public void TestAddLoot01(){
		Team teamA = new Team('a', null);
		Team teamB = new Team('b', null);
		Team teamC = new Team('c', null);
		Team teamD = new Team('d', null);
		
		teamA.addLoot(0);
		teamA.addLoot(3);
		
		assertTrue(teamA.getScore()==3);	
	}
	
	public void TestAddLoot02(){
		Team teamA = new Team('a', null);
		
		teamA.addLoot(2);
		teamA.addLoot(3);
		
		assertTrue(teamA.getScore()==5);	
	}
	public void TestAddLoot03(){
		Team teamA = new Team('a', null);
		
		teamA.addLoot(4);
		teamA.addLoot(3);
		teamA.addLoot(0);
		teamA.addLoot(3);
		teamA.addLoot(3);
		teamA.addLoot(3);
		teamA.addLoot(3);
		teamA.addLoot(20);
		teamA.addLoot(1);
		
		assertTrue(teamA.getScore()==40);	
	}
	
	@Test(expected=Exception.class)
	public void TestAddLootExceptionNegative01(){
		Team teamA = new Team('a', null);
		
		teamA.addLoot(1);
		teamA.addLoot(-2);
	}
	
	@Test(expected=Exception.class)
	public void TestAddLootExceptionNegative02(){
		Team teamA = new Team('a', null);
		
		teamA.addLoot(-2);
	}
	
	@Test(expected=Exception.class)
	public void TestAddLootExceptionNegative03(){
		Team teamA = new Team('a', null);
		
		teamA.addLoot(1);
		teamA.addLoot(1);
		teamA.addLoot(1);
		teamA.addLoot(1);
		teamA.addLoot(1);
		teamA.addLoot(-2);
		teamA.addLoot(-2);
		teamA.addLoot(-2);
	}
	
	@Test(expected=Exception.class)
	public void TestAddLootExceptionNegative04(){
		Team teamA = new Team('a', null);
		
		teamA.addLoot(1);
		teamA.addLoot(1);
		teamA.addLoot(-2);
		teamA.addLoot(1);
		teamA.addLoot(-2);
		teamA.addLoot(1);
		teamA.addLoot(1);
	}
	
	@Test
	public void TestShipAddDel01(){
		Team teamA = new Team('a', null);
		teamA.addShip();
		teamA.addShip();
		teamA.addShip();
		teamA.addShip();
		
		assertTrue(teamA.getShipCount()==4);
	}
	
	@Test
	public void TestShipAddDel02(){
		Team teamA = new Team('a', null);
		teamA.addShip();
		teamA.addShip();
		teamA.addShip();
		teamA.deleteShip();
		
		assertTrue(teamA.getShipCount()==3);
	}
	
	@Test
	public void TestShipAddDel03(){
		Team teamA = new Team('a', null);
		teamA.addShip();
		teamA.addShip();
		teamA.addShip();
		teamA.deleteShip();
		teamA.addShip();
		teamA.addShip();
		
		assertTrue(teamA.getShipCount()==5);
	}
	
	@Test (expected=Exception.class)
	public void TestShipDelException01(){
		Team teamA = new Team('a', null);
		teamA.deleteShip();
		
	}
	
	@Test (expected=Exception.class)
	public void TestShipDelException02(){
		Team teamA = new Team('a', null);
		teamA.addShip();
		teamA.deleteShip();
		teamA.deleteShip();
		teamA.addShip();
		
	}
	
	@Test
	public void TestCreation01(){
		Team teamA = new Team('a', null);
		
		assertTrue(teamA.getScore()==0);	
		assertTrue(teamA.getName()=='a');
		assertTrue(teamA.getShipCount()==0);
	}
	@Test
	public void TestCreation02(){
		Command commandA= new Move(12);
		ArrayList<Command> commandListA=new ArrayList<Command>();
		commandListA.add(commandA);
		
		Team teamA = new Team('a', commandListA);
		
		assertTrue(teamA.getScore()==0);	
		assertTrue(teamA.getName()=='a');
		assertTrue(teamA.getShipCount()==0);
		assertTrue(teamA.getCommands().size()==1);
		assertTrue(teamA.getCommands().get(0).equals(commandA));
	}
	@Test
	public void TestCreation03(){
		Command commandA= new Move(12);
		Command commandB= new Unmark(3);
		Command commandC= new Drop();
		ArrayList<Command> commandListA=new ArrayList<Command>();
		commandListA.add(commandA);
		commandListA.add(commandB);
		commandListA.add(commandC);
		
		Team teamA = new Team('a', commandListA);
		
		assertTrue(teamA.getScore()==0);	
		assertTrue(teamA.getName()=='a');
		assertTrue(teamA.getShipCount()==0);
		assertTrue(teamA.getCommands().size()==3);
		assertTrue(teamA.getCommands().get(0).equals(commandA));
		assertTrue(teamA.getCommands().get(1).equals(commandB));
		assertTrue(teamA.getCommands().get(2).equals(commandC));
	}
}
