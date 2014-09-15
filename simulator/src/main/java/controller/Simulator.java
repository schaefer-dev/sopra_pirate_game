package controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import view.GUIController;
import view.Log;
import view.SimpleLogWriter;
import model.Kraken;
import model.Ship;
import model.Team;
import model.Map;

public class Simulator {

	private int roundCounter;
	private int roundMax;
	
	private Map map;
	private Log logWriter;
	private List<Kraken> kraken;
	
	
	public Simulator(String[] shipFiles, String mapFile, int seed, String logFile, int turns) throws ArrayIndexOutOfBoundsException, NullPointerException, IOException {
		if(shipFiles == null || mapFile == null || logFile == null) throw new NullPointerException();
		if(shipFiles.length > 26 || turns > 1e4) throw new IllegalArgumentException();
		
		Translator translator = new Translator();
		
		List<Team> teams = new ArrayList<Team>();
		if(shipFiles.length == 1){
			List<Command> tactic = translator.run(new ByteArrayInputStream(shipFiles[0].getBytes()));
			for(int i = 0; i < 26; i++)
				teams.add(new Team((char)('a' + i), tactic));
		}
		else{
			for(int i = 0; i < shipFiles.length; i++){
				List<Command> tactic = translator.run(new ByteArrayInputStream(shipFiles[i].getBytes()));
				Team team = new Team((char)('a' + i), tactic);
				teams.add(team);
			}
		}
		
		File file = new File(logFile);
		FileOutputStream stream = new FileOutputStream(file);
		
		logWriter = new Log();
		logWriter.addLogger(new SimpleLogWriter());
		logWriter.addLogger(new GUIController());
		logWriter.init(stream, mapFile, shipFiles);
		
		MapGenerator generator = new MapGenerator(new Random(seed));
		map = generator.createMap(/*new ByteArrayInputStream(mapFile.getBytes())*/mapFile, teams, logWriter);
		kraken = map.getKraken();
		
		roundCounter = 1;
		roundMax = turns;
	}
	
	
	public void step() throws IllegalStateException, IOException{
		if(roundCounter > roundMax) throw new IllegalStateException();
		
		logWriter.logStep();
		
		if((roundCounter % 20) == 0){
			for(Kraken k: kraken)
				k.move();
		}
		
		Ship ship = map.getFirstShip();
		
		if(ship == null){
			end();
			return;
		}
		
		while(ship != null){
			ship.act();
			ship = ship.getNextShip();
		}
		
		roundCounter++;
	}
	
	public void step(int rounds) throws IllegalStateException, IOException{
		if((rounds + roundCounter) > roundMax) throw new IllegalStateException();
		
		for(int i = 0; i < rounds; i++)
			step();
	}
	
	public void end() throws IllegalStateException, IOException{
		logWriter.close();
	}
}
