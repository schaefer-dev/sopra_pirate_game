package de.unisaarland.cs.st.pirates.group5.main;

import java.io.IOException;
import java.net.URISyntaxException;

import controller.Simulator;

public class Main {
	
	private static boolean endGame = false;
	
	public static void main(String[] args) throws ArrayIndexOutOfBoundsException, NullPointerException, IOException, URISyntaxException {
		
		String mapFile = args[0];
		
		String[] tactics = new String[args.length - 1];
		for(int i = 1; i <= tactics.length; i++)
			tactics[i-1] = args[i];
	
		String logFile = System.getProperty("log");

		int turns;
		int seed;
		if(System.getProperty("turns") != null && System.getProperty("turns") != "")
			turns = Integer.parseInt(System.getProperty("turns"));
		else
			turns = 10000;
		if(System.getProperty("seed") != null && System.getProperty("seed") != "")
			seed  = Integer.parseInt(System.getProperty("seed"));
		else
			seed = 19580427;
		
		Simulator sim = new Simulator(tactics, mapFile, seed, logFile, turns);
		
		while(!endGame){
			if(!sim.canStep())
				break;
				
			sim.step();
		}
	}
	
	public static void endGame(){
		endGame = true;
	}
}

