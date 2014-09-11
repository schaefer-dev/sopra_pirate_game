package de.unisaarland.cs.st.pirates.group5.tests;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import model.Base;
import model.Buoy;
import model.Island;
import model.Kraken;
import model.Map;
import model.ProvisionIsland;
import model.Ship;
import model.Team;
import model.Treasure;
import model.Water;
 

public class TestObjectConstructor {
	
	public List<Base> testBases; 
	public List<Buoy> testBouys;
	public List<Island> testIslands;
	public List<Kraken> testKraken;
	public List<Map> testMaps; 
	public List<ProvisionIsland> testProvisionIsland;
	public List<Ship> testShips; 
	public List<Team> testTeams;
	public List<Treasure> testTreasures;
	public List<Water> testWater;
	
	
	public TestObjectConstructor(int seed) {
		Random randomGen = new Random(seed);
		this.testBases = new LinkedList();
		this.testBouys =new LinkedList();
		this.testIslands = new LinkedList();
		this.testKraken = new LinkedList();
		this.testMaps = new LinkedList();
		this.testProvisionIsland = new LinkedList();
		this.testShips = new LinkedList();
		this.testTeams = new LinkedList();
		this.testTreasures = new LinkedList();
		this.testWater = new LinkedList();	
	}
	
	private void generate(){
		
		
	}

	
	
}
