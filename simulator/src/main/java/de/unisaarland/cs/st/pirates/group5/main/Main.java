package de.unisaarland.cs.st.pirates.group5.main;

import controller.Simulator;

public class Main {
	
	private static boolean endGame = false;
	
	public static void main(String[] args) {
		
		int turns = 10000;
		int counter = 0;
		
		Simulator sim;
		
		while(!endGame){
			if(counter >= turns)
				endGame = true;
			
			//sim.step();
		
		}
	}
	
	public static void endGame(){
		endGame = true;
	}
}

