package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import view.Log;
import model.Kraken;
import model.Register;
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

	private boolean endGame;


	public Simulator(String[] shipFiles, String mapFile, int seed, String logFile, int turns) throws ArrayIndexOutOfBoundsException, NullPointerException, IOException, URISyntaxException {
		if(shipFiles == null || mapFile == null) throw new NullPointerException("No shipFiles or MapFile specified");
		if(shipFiles.length < 1 || shipFiles.length > 26 || turns > 10000 || turns <1) throw new IllegalArgumentException("To few or to many shipFiles or illegal Number of rounds");
		endGame = false;
		Translator translator = new Translator();
		teams = new ArrayList<Team>();
		if(shipFiles.length == 1){
			InputStream shipStream = getClass().getResourceAsStream(shipFiles[0]);
			if(shipStream == null)
				shipStream = new FileInputStream(shipFiles[0]);
			List<Command> tactic = translator.run(shipStream);
			for(int i = 0; i < 26; i++)
				teams.add(new Team((char)('a' + i), tactic));
			shipStream.close();
		}
		else{
			for(int i = 0; i < shipFiles.length; i++){
				InputStream shipStream = getClass().getResourceAsStream(shipFiles[i]);
				if(shipStream == null)
					shipStream = new FileInputStream(shipFiles[i]);
				List<Command> tactic = translator.run(shipStream);
				Team team = new Team((char)('a' + i), tactic);
				teams.add(team);
				shipStream.close();
			}
		}
		for(int i = 0; i< shipFiles.length; i++)
		{
			InputStream shipStream = getClass().getResourceAsStream(shipFiles[i]);
			if(shipStream == null)
				shipStream = new FileInputStream(shipFiles[i]);
			Scanner scanner = new Scanner(shipStream);
			String shipString = "";
			while(scanner.hasNextLine())
				shipString += scanner.nextLine() + "\n";
			scanner.close();
			shipFiles[i] = shipString;
		}
		InputStream mapStream = getClass().getResourceAsStream(mapFile);
		if(mapStream == null)
			mapStream= new FileInputStream(mapFile);
		Scanner scanner = new Scanner(mapStream);
		String mapString = "";
		while(scanner.hasNextLine())
			mapString += scanner.nextLine() + "\n";
		scanner.close();
		if(logFile != null)
		{
			logWriter = new Log();
			//logWriter.addLogger(new SimpleLogWriter());		//TODO log enable/disable here
			// Please do not include the adding of the of the logWriters again, since this is done in MapGenerator now.
		}

		MapGenerator generator = new MapGenerator();
		InputStream inMap = getClass().getResourceAsStream(mapFile);
		if(inMap == null)
			inMap = new FileInputStream(mapFile);
		map = generator.createMap(inMap, teams, logWriter, new Random(seed), mapString, shipFiles, logFile);
		kraken = map.getKraken();
		if(logFile!= null)
			logWriter.logStep();

		roundCounter = 1;
		roundMax = turns + 1;
	}


	public void step() throws IllegalStateException, IOException{
		if(roundCounter > roundMax) throw new IllegalStateException(); // roundMax = turns + 1

		Ship ship = map.getFirstShip();

		if(ship == null){
			end();
			return;
		}

		if((roundCounter % 20) == 0){
			for(Kraken k: kraken)
				k.move();
		}

		while(ship != null){
			ship.act();
			ship = ship.getNextShip();
		}
		if(logWriter != null)
			logWriter.logStep();
		roundCounter++;

		if(roundCounter == roundMax){
			end();
			return;
		}
	}

	public void step(int rounds) throws IllegalStateException, IOException{
		if((rounds + roundCounter) > roundMax) throw new IllegalStateException();

		for(int i = 0; i < rounds; i++){
			if(!endGame)
				step();
			else
				break;
		}
	}

	private void end() throws IllegalStateException, IOException{
		endGame = true;
		if(logWriter != null)
			logWriter.close();
		int maxScore = -1;
		boolean same = false;
		for(Team team: teams)
		{
			if(team.getScore() == maxScore)
				same = true;
			else
			{
				if(team.getScore() > maxScore)
				{
					maxScore = team.getScore();
					same = false;
				}
			}
		}
		for(Team team: teams){
			int load = 0;
			for(Ship ship: team.getShips())
				load += ship.getSenseRegister(Register.ship_load);
			if(same || teams.size() == 1)
				System.out.println(team.getScore() + "(" +  load + "," + team.getShipCount() + "," + team.getCommands().size() + ")");
			else
				System.out.println(team.getScore());
		}
	}
}
