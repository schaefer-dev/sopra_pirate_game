package de.unisaarland.cs.st.pirates.group5.tests;
import model.Island;
import model.Map;
import model.ProvisionIsland;
import model.Ship;
import model.Team;
import model.Water;

import org.junit.Before;
import org.junit.Test;

import commands.Move;
import controller.MapGenerator;
import model.Field;


public class MoveTests {
	

	
	Field water;
	Field island; 
	Map map;
	Field provision;
	Ship ship1;
	MapGenerator mapgen;
	

	@Before
	protected void setUp(){
		map = new Map(null);
		mapgen = new MapGenerator(null);
		mapgen.createMap(null, null, null); // input stream schreiben !!
		water = new Water(map, 0, 0, null);
		island = new Island(map, 0, 0);
		provision = new ProvisionIsland(map, 0, 0);
		
	}

	@Test
	 public void testMoveWater(){
		
		 
		 
	 }
	
	@Test
	public void testMoveShipFriend(){
		
	}
	
	@Test
	public void testMoveShipEnemyWin(){
		
	}
	
	@Test
	public void testMoveShipEnemyLoose(){
		
	}
	
	@Test
	public void testMoveKraken(){
		
	}
	
	@Test
	public void testMoveBaseEnemy(){
		
	}
	
	@Test
	public void testMoveBaseFriend(){
		
	}
	
	@Test
	public void testMoveIsland(){
		
	}
	
	@Test
	public void testMoveProvisionIsland(){
		
	}
}
