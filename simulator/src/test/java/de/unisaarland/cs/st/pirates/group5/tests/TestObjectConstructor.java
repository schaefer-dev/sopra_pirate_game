package de.unisaarland.cs.st.pirates.group5.tests;

import java.util.ArrayList;
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
	
	private List<String> tacticStringList;
	
	
	public TestObjectConstructor(int seed) {
		
		Random randomGen = new Random(seed);
		List<String> tacticStringList = new ArrayList();
		
	}
	
	private void generateTacticLines(){
		tacticStringList.add("sense 0");
		tacticStringList.add("ifall sense_treasure ship_load<4 else 4");
		tacticStringList.add("pickup 0 else 6");
		tacticStringList.add("goto 14");
		tacticStringList.add("if ship_load>3 else 6");
		tacticStringList.add("goto 14");
		tacticStringList.add("move else 8");
		tacticStringList.add("goto 0");
		tacticStringList.add("flipzero 3 else 11");
		tacticStringList.add("turn left");
		tacticStringList.add("goto 0");
		tacticStringList.add("sense 0");
		tacticStringList.add("if sense_celltype==home else 19");
		tacticStringList.add("move else 21");
		tacticStringList.add("drop");
		tacticStringList.add("goto 0");
		tacticStringList.add("move else 21");
		tacticStringList.add("drop");
		tacticStringList.add("goto 0");
		tacticStringList.add("move else 21");
		tacticStringList.add( "goto 14");
		tacticStringList.add("flipzero 3 else 24");
		tacticStringList.add("turn left");
		tacticStringList.add("goto 14");
		tacticStringList.add("flipzero 2 else 26");
		tacticStringList.add("turn right");
		tacticStringList.add("goto 14");
	}
	
	private void generateSimulator(){
		
		List<String> shipTaktiks = new ArrayList() ;
		shipTaktiks.add("sense 0"
				+ "ifall sense_treasure ship_load<4 else 4"
				+ "pickup 0 else 6"
				+ "goto 14"
				+ "if ship_load>3 else 6"
				+ "goto 14"
				+ "move else 8"
				+ "goto 0"
				+ "flipzero 3 else 11"
				+ "turn left"
				+ "goto 0"
				+ "sense 0"
				+ "if sense_celltype==home else 19"
				+ "move else 21"
				+ "drop"
				+ "goto 0"
				+ "move else 21"
				+ "drop"
				+ "goto 0"
				+ "move else 21"
				+ "goto 14"
				+ "flipzero 3 else 24"
				+ "turn left"
				+ "goto 14"
				+ "flipzero 2 else 26"
				+ "turn right"
				+ "goto 14");
		
		
	}

	
	
}
