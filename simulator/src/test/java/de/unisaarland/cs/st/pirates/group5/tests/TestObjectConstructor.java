package de.unisaarland.cs.st.pirates.group5.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
 

public class TestObjectConstructor {
	
	private List<String> tacticStringList;
	private List<String> tacticLineStringList;
	private Random randomGen;
	
	
	public TestObjectConstructor(int seed) {
		
		this.randomGen = new Random(seed);
		this.tacticLineStringList = new ArrayList<String>();
		this.tacticStringList = new ArrayList<String>();
		
		
		this.generateTacticLineStringList();
		this.generateTacticList();
	}
	
	private void generateTacticLineStringList(){
		/* creating single TacticLines in tacticStringList for later usage */
		tacticLineStringList.add("sense 0\n");
		tacticStringList.add("ifall sense_treasure ship_load<4 else 4\n");
		tacticStringList.add("pickup 0 else 6\n");
		tacticStringList.add("goto 14\n");
		tacticStringList.add("if ship_load>3 else 6\n");
		tacticStringList.add("goto 14\n");
		tacticStringList.add("move else 8\n");
		tacticStringList.add("goto 0\n");
		tacticStringList.add("flipzero 3 else 11\n");
		tacticStringList.add("turn left\n");
		tacticStringList.add("goto 0\n");
		tacticStringList.add("sense 0\n");
		tacticStringList.add("if sense_celltype==home else 19\n");
		tacticStringList.add("move else 21\n");
		tacticStringList.add("drop\n");
		tacticStringList.add("goto 0\n");
		tacticStringList.add("move else 21\n");
		tacticStringList.add("drop\n");
		tacticStringList.add("goto 0\n");
		tacticStringList.add("move else 21\n");
		tacticStringList.add( "goto 14\n");
		tacticStringList.add("flipzero 3 else 24\n");
		tacticStringList.add("turn left\n");
		tacticStringList.add("goto 14\n");
		tacticStringList.add("flipzero 2 else 26\n");
		tacticStringList.add("turn right\n");
		tacticStringList.add("goto 14\n");
	}
	
	private void generateTacticList(){
		/* generates Tactic for every single Line in tacticStringList and a Tactic which contains all the tacticLines together in a big file*/
		List<String> shipTactics = new ArrayList<String>() ;
		String bigTactic = "";
		for (int i=0; i<tacticStringList.size(); i++){		//connects commandLines into big TacticString
			bigTactic += tacticStringList.get(i);
			shipTactics.add(tacticLineStringList.get(i));
		}
		shipTactics.add(bigTactic);		
	}	
	
}
