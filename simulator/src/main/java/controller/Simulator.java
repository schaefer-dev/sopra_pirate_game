package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import de.unisaarland.cs.st.pirates.logger.LogWriter;
import view.Log;
import view.SimpleLogWriter;
import model.Kraken;
import model.Register;
import model.Ship;
import model.Team;
import model.Map;
/**
 * 
 * @author Rafael & Jan
 * The <code>Simulator</code> simulates the running of a game. The constructor initialises the game. And the step methodes simulate one or n rounds of the game.
 */
public class Simulator {

	private int roundCounter;
	private int roundMax;

	private Map map;
	private Log logWriter;
	private List<Team> teams;
	private List<Kraken> kraken;

	private boolean endGame;

	/**
	 * Initializes the game. Starts the <code>Translator</code> which translates the ship's tactics files to a list of commands. Creates the <code>Team</code>s. Creates an instance of the <code>Log</code> class without calling init or adding the external logger.
	 * Calls createMap from a map Generator and saves the map in map. Saves the list of kraken in kraken and calls logStep in the <code>LogWriter</code> for the first time after the creation of the map.
	 *  
	 * @param shipFiles A String array containing the paths to the .ship files. They are overwritten after the creation of the Teams with the content of the file as a String.
	 * @param mapFile A String giving the path to the map file.
	 * @param seed The seed to be used for the instance of the <code>Random</code> class.
	 * @param logFile A String representing the path in which the log is to be written.
	 * @param turns The number of rounds to be played.
	 * @throws ArrayIndexOutOfBoundsException if thrown by MapGenerator
	 * @throws NullPointerException if shipFiles or mapFile is null
	 * @throws IOException If reading or writing in the specified files does not work
	 * @throws URISyntaxException
	 * @throws IllegalArgumentException if the number of shipFiles or turns is invalid. (ShipFiles < 1 or > 26, turns <1 or > 10000).
	 */
	public Simulator(String[] shipFiles, String mapFile, int seed, String logFile, int turns, LogWriter guiController) throws ArrayIndexOutOfBoundsException, NullPointerException, IOException, URISyntaxException, IllegalArgumentException {
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
		if(logFile != null || guiController != null){
			logWriter = new Log();
		//	logWriter.addLogger(new SimpleLogWriter());
			if(guiController != null)
				logWriter.addLogger(guiController);
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
	
	public Simulator(String[] shipFiles, String mapFile, int seed, String logFile, int turns) throws ArrayIndexOutOfBoundsException, NullPointerException, IllegalArgumentException, IOException, URISyntaxException{
		this(shipFiles, mapFile, seed, logFile, turns, null);
	}

	/**
	 * Simulates one round of a game. Mainly calls act on all remaining ships. Also moves the kraken every 40 rounds. Logs each step and calls end when the last round is simulated.
	 * @throws IllegalStateException If the roundCounter is greater than roundMax
	 * @throws IOException If logging does not work properly
	 */
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
	/**
	 * Simulates n round of a game. Basically only calls step() n times. Stops if endGame is true.
	 * @param rounds the number of rounds to be simulated.
	 * @throws IllegalStateException If the roundCounter is greater than roundMax
	 * @throws IOException If logging does not work properly
	 */
	public void step(int rounds) throws IllegalStateException, IOException{
		if((rounds + roundCounter) > roundMax) throw new IllegalStateException();

		for(int i = 0; i < rounds; i++){
			if(!endGame)
				step();
			else
				break;
		}
	}
	/**
	 * Ends the simulation. Closes the logWriter and prints out the results. Results are the points for each team in the respective order. If more than one team has the maximum number of points,
	 * or if there is only one team the number of Treasures on the remaining ships, the number of remaining ships and the length of the tactics file is printed as well in parentheses.
	 * @throws IllegalStateException If the closing of the logger fails.
	 * @throws IOException If the closing of the logger fails.
	 */
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
