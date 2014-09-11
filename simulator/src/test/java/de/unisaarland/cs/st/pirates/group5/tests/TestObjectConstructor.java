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
	private List<String> tacticLineStringList;
	private Random randomGen;
	
	
	public TestObjectConstructor(int seed) {
		
		this.randomGen = new Random(seed);
		this.tacticLineStringList = new ArrayList();
		this.tacticStringList = new ArrayList();
		
		
		this.generateTacticLineStringList();
		this.generateTacticList();
	}
	
	private void generateTacticLineStringList(){
		/* creating single TacticLines in tacticStringList for later usage */
		tacticLineStringList.add("sense 0");
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
	
	private void generateTacticList(){
		/* generates Tactic for every single Line in tacticStringList and a Tactic which contains all the tacticLines together in a big file*/
		List<String> shipTactics = new ArrayList() ;
		String tactic = "";
		String bigTactic = "";
		for (int i=0; i<tacticStringList.size(); i++){		//connects commandLines into big TacticString
			bigTactic += tacticStringList.get(i);
			shipTactics.add(tacticLineStringList.get(i));
		}
		shipTactics.add(bigTactic);		
	}

	
	
}
