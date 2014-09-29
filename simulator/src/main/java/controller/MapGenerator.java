package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import view.GUIController;
import view.SimpleLogWriter;
import de.unisaarland.cs.st.pirates.logger.LogProvider;
import de.unisaarland.cs.st.pirates.logger.LogWriter;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Cell;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Entity;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Key;
import model.Base;
import model.Field;
import model.FieldType;
import model.Island;
import model.Kraken;
import model.Map;
import model.ProvisionIsland;
import model.Ship;
import model.Team;
import model.Treasure;
import model.Water;
import view.Log;
/**
 * The <code>MapGenerator</code> class is only used to generate an instance of the <code>Map</code> class from a map file via a call of the createMap method. 
 * @author Rafael & Jan
 *
 */
public class MapGenerator {

	private Field[][] fields;
	private int x;
	private int y;
	OutputStream logStream;

	/**
	 * A call of this method creates a new <code>Map</code> from an InputStream of a map file. It also adds the external LogWriter to the instance of the <code>Log</code> class and logs, initialises the LogWriters and logs the creation of the map.
	 * Importantly the creation of the map is only logged after the map has been created, since possible mistakes in the map file must result in an exception befor a log file has been created.
	 * @param stream The InputStream of the map file. Used to read out the map.
	 * @param teams A list of <code>Team</code>s. The nth <code>Team</code> in the list is the Team associated to the Base depicted on the map by the nth letter of the alphabet.
	 * @param log A logWriter, should usually be an instance of the <code>Log</code> class. If it isn't the external logger won't be added and used during the running of the program.
	 * @param random A instance of the <code>Random</code> class. Is passed on to the newly created <code>Map</code> for later usage in the <code>Flipzero</code> and <code>Move</code> commands and in the movement of the kraken.
	 * @param mapString A string representation of the map file. Passed on to the <code>LogWriter</code> log when log.init() is called.
	 * @param shipFiles A String array containing a String representation of each Team's tactics file. Passed on to the logger when log.init() is called. Contains only one String if one tactics file is used for all Teams. Otherwise the number of Strings in the array should be identical to the number of teams on the map.
	 * @param logFile A String giving the path to the LogFile.
	 * @return the newly created <code>Map</code>
	 * @throws IOException when there are problems reading or writing to to the logFile.
	 * @throws URISyntaxException
	 * @throws NullPointerException If stream, teams or random is null.
	 * @throws IllegalArgumentException If the map represented by the InputSteam stream does not fullfill the specifications or if there are more <code>Team</code>s in teams than there are bases for different Teams on the map.
	 * @throws ArrayIndexOutOfBoundsException If there is a base on the map not correlating to a <code>Team</code> in teams.
	 * @throws IllegalStateException If there is a fieldType on the newly created Map which is neither an Island, ProvisionIsland or Base nor a Water field.
	 */
	public Map createMap(InputStream stream, List<Team> teams, LogWriter log, Random random, String mapString, String[] shipFiles, String logFile) throws IOException, URISyntaxException, NullPointerException, IllegalArgumentException, ArrayIndexOutOfBoundsException, IllegalStateException{
		if(stream == null || teams == null || random == null) throw new NullPointerException();
		if(teams.size() < 1 || teams.size() > 26) throw new IllegalArgumentException();
		
		Map map = new Map(random, log);
		List<Kraken> kraken = new ArrayList<Kraken>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		int width, height;
		
		try{
			width = Integer.parseInt(reader.readLine());
			height = Integer.parseInt(reader.readLine());
			fields = new Field[width][height];
		}
		catch(NumberFormatException e){
			throw new IllegalArgumentException("Submitted coordinates are not valid numbers");
		}
		
		if(width < 2 || width > 200 || height < 2 || height > 200)
			throw new IllegalArgumentException("Coordinates are not in range (2,2)...(200,200)");
		if(height%2 != 0)
			throw new IllegalArgumentException("Map must have an even number auf lines");

		Ship previousShip = null;
		String line;
		
		while((line = reader.readLine()) != null){
			
			line.trim();
			line = line.replaceAll(" ", "");

			for(int i = 0; i < line.length(); i++){
				if(y >= height) throw new IllegalArgumentException("Map is bigger than was specified in the first two lines.");
			
				char glyph = line.charAt(i);
				Field field;
				
				if(glyph == '.'){
					field = new Water(map, x, y, null);
				}
				else if(glyph == '#'){
					field = new Island(map, x, y, null);
				}
				else if(glyph == '$'){
					field = new ProvisionIsland(map, x, y);
				}
				else if(glyph == '&'){
					Kraken k = new Kraken(map.giveNewEntityID(), null);
					kraken.add(k);
					field = new Water(map, x, y, k);
				}
				else if(isTeamLetter(glyph, 26)){
					int teamNumber = glyph - 'a';
					Team team;
					
					try{
						team = teams.get(teamNumber);
					}
					catch(ArrayIndexOutOfBoundsException e){
						throw new ArrayIndexOutOfBoundsException("Base on map has no related team");
					}
					Ship ship = new Ship(team, null, map.giveNewActorID(), previousShip);
					teams.get(teamNumber).addShip(ship);					
					if(previousShip == null)
						map.setFirstShip(ship);
					previousShip = ship;
					field = new Base(map, x, y, team, ship);
				}
				else if(isDigit(glyph)){
					int id = map.giveNewEntityID();
					Treasure t = new Treasure(id, glyph - '0');					
					field = new Island(map, x, y, t);
				}
				else
					throw new IllegalArgumentException("Invalid character " + glyph + " in map file at position (" + x + "," + y + ")");
				
				fields[x][y] = field;
				incrementXY(width, height);
			}
		}
		
		if(y != height) throw new IllegalArgumentException();
		reader.close();
		
		if(teams.size() == 1) {
			if(teams.get(0).getShipCount() <= 0)
				throw new IllegalArgumentException("Not every team has bases/ships on the map");
		}
		else{
			List<Command> tactic1 = teams.get(0).getCommands();
			List<Command> tactic2 = teams.get(1).getCommands();
			//Either all teams have a different tactic or all teams have the same one
			//If the first 2 teams have the same tactic => all teams have the same tactic
			if(tactic1 == tactic2){	
				boolean lastShip = false;
				for(int i = teams.size() - 1; i >= 0; i--){
					Team team = teams.get(i);
					if(team.getShipCount() <= 0){
						if(lastShip)
							throw new IllegalArgumentException("Not every team has bases/ships on the map");
						else
							teams.remove(team);
					}			
					else{
						lastShip = true;
					}
				}
			}
			else{
				for(Team team: teams){	
					if(team.getShipCount() <= 0)
						throw new IllegalArgumentException("Not every team has bases/ships on the map");
				}
			}
		}
				
		map.setMapValues(fields, kraken);
		if(log != null)
		{
			if(logFile!= null)
			{
				if(getClass().getResource(logFile) != null){
					URL temp = getClass().getResource(logFile);
					File file = new File(temp.toURI());
					logStream = new FileOutputStream(file);					// then state is never reached, except for test reasons!
				}
				else
					logStream = new FileOutputStream(logFile);
			}
			if(log instanceof Log)
				((Log) log).addLogger(LogProvider.createInstance("DEFAULT"));
		//	LogProvider.register("BYPASS",SimpleLogWriter.class);				
		//	((Log) log).addLogger(new GUIController());
			log.init(logStream, mapString, shipFiles);
			for(Field[] row : fields)
			{
				for(Field field : row)
				{
					switch (field.getFieldType())
					{
					case Base:
						log.addCell(Cell.WATER, ((Base) field).getTeam().getName()-'a', field.getX(), field.getY());
						break;
					case Island:
						log.addCell(Cell.ISLAND, null, field.getX(), field.getY());
						break;
					case ProvisionIsland:
						log.addCell(Cell.SUPPLY, null, field.getX(), field.getY());
						break;
					case Water:
						log.addCell(Cell.WATER, null, field.getX(), field.getY());
						break;
					default:
						throw new IllegalStateException("Unknown fieldType in map");
					
					}
				}
			}
			for(Field[] row: fields)
			{
				for(Field field : row)
				{
					if(field.getKraken() != null)
					{
						Key[] keys = {Key.X_COORD, Key.Y_COORD};
						int[] values = {field.getX(), field.getY()};
						log.create(Entity.KRAKEN, field.getKraken().getId(), keys, values);
					}
					if(field.getShip() != null)
					{
						assert(field.getFieldType() == FieldType.Base);
						Ship ship = field.getShip();
						assert(ship.getShipDirection() == 0);
						assert(ship.getCondition() == 3);
						assert(ship.getMoral() == 4);
						assert(ship.getPC() == 0);
						assert(ship.getPause() == 0);
						assert(ship.getLoad() == 0);
						Key[] keys = { Key.CONDITION, Key.DIRECTION, Key.FLEET, Key.MORAL, Key.PC, Key.RESTING, Key.VALUE, Key.X_COORD, Key.Y_COORD};
						int[] values = { ship.getCondition(),ship.getShipDirection(), ship.getTeam().getName()-'a', ship.getMoral(), ship.getPC(), ship.getPause(), ship.getLoad(), field.getX(), field.getY()};
						log.create(Entity.SHIP, ship.getID(), keys, values);
					}
					if(field.getTreasure() != null)
					{
						assert(field.getTreasure().getValue() < 10);
						Key[] keys = {Key.VALUE, Key.X_COORD, Key.Y_COORD};
						int[] values = {field.getTreasure().getValue(), field.getX(), field.getY()};
						log.create(Entity.TREASURE, field.getTreasure().getId(), keys, values);
					}
				}
			}
			for(Team team: teams)
			{
				assert(team.getScore() == 0);
				log.fleetScore(team.getName() - 'a', team.getScore());
			}
		}
		return map;
	}
	
	/**
	 * A helping method testing whether a char on the map is letter associated with one of the Teams.
	 * @param c the char to be tested
	 * @param max the maximal number of Teams which could be on the map.
	 * @return true or false
	 */
	private boolean isTeamLetter(char c, int max){
		if(c >= 'a' && c < ('a' + max))
			return true;
		
		return false;
	}
	/**
	 * Tests if a given char is a digit between 1 and 9
	 * @param c the char to be tested
	 * @return true or false
	 */
	private boolean isDigit(char c){
		if(c >= '1' && c <= '9')
			return true;
		
		return false;
	}
	/**
	 * Increments the values of the variables x and y, so that they represent the coordinates of the next field.
	 * This means that generally x is incremented and only if the current values of x and y represent a coordinate on the right side of a line.
	 * @param width the width of the map
	 * @param height the height of the map.
	 */
	private void incrementXY(int width, int height) {
		x++;
		if(x >= width)
			y++;
		x = x % width;
	}
	
}
