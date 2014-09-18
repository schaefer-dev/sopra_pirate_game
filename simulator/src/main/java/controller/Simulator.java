package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import de.unisaarland.cs.st.pirates.group5.main.Main;
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
	private List<Team> teams;
	private List<Kraken> kraken;
	
	
	public Simulator(String[] shipFiles, String mapFile, int seed, String logFile, int turns) throws ArrayIndexOutOfBoundsException, NullPointerException, IOException, URISyntaxException {
		if(shipFiles == null || mapFile == null || logFile == null) throw new NullPointerException();
		if(shipFiles.length < 1 || shipFiles.length > 26 || turns > 1e4) throw new IllegalArgumentException();

		Translator translator = new Translator();
		System.out.println(logFile);
		teams = new ArrayList<Team>();
		if(shipFiles.length == 1){
			List<Command> tactic = translator.run(getClass().getResourceAsStream(shipFiles[0]));
			String[]tempFiles = new String[26];
			Arrays.fill(tempFiles, shipFiles[0]);
			shipFiles = tempFiles;
			for(int i = 0; i < 26; i++)
				teams.add(new Team((char)('a' + i), tactic));
		}
		else{
			for(int i = 0; i < shipFiles.length; i++){
				List<Command> tactic = translator.run(getClass().getResourceAsStream(shipFiles[i]));
				Team team = new Team((char)('a' + i), tactic);
				teams.add(team);
			}
		}
		
		FileOutputStream stream;
		if(getClass().getResource(logFile) != null)
		{
			URL temp = getClass().getResource(logFile);
			File file = new File(temp.toURI());
			stream = new FileOutputStream(file);
		}
		else
		{
			stream = new FileOutputStream(logFile);
		}
		InputStream in = getClass().getResourceAsStream(mapFile);
		
		Scanner scanner = new Scanner(in);
		String mapString = "";
		while(scanner.hasNextLine())
			mapString += scanner.nextLine() + "\n";
		scanner.close();
		logWriter = new Log();
	    logWriter.addLogger(new SimpleLogWriter());
		logWriter.addLogger(new GUIController());
		logWriter.init(stream, mapString, shipFiles);
		
		MapGenerator generator = new MapGenerator();
		map = generator.createMap(getClass().getResourceAsStream(mapFile), teams, logWriter, new Random(seed));
		kraken = map.getKraken();
		
		logWriter.logStep();
		
		roundCounter = 1;
		roundMax = turns + 1;
	}
	
	
	public void step() throws IllegalStateException, IOException{
		if(roundCounter > roundMax) throw new IllegalStateException();
		
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
		
		logWriter.logStep();
		roundCounter++;
	}
	
	public void step(int rounds) throws IllegalStateException, IOException{
		if((rounds + roundCounter) > roundMax) throw new IllegalStateException();
		
		for(int i = 0; i < rounds; i++)
			step();	
	}
	
	public void end() throws IllegalStateException, IOException{
		logWriter.close();
		Main.endGame();
	}
	
	public boolean canStep(){
		return roundCounter <= roundMax;
	}
}
