package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.unisaarland.cs.st.pirates.logger.LogWriter;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Cell;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Entity;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Key;
import model.Base;
import model.Field;
import model.Island;
import model.Kraken;
import model.Map;
import model.ProvisionIsland;
import model.Ship;
import model.Team;
import model.Treasure;
import model.Water;

public class MapGenerator {

	private Field[][] fields;
	private int x;
	private int y;

	
	public Map createMap(InputStream stream, List<Team> teams, LogWriter log, Random random) throws IOException{
		if(stream == null || teams == null || log == null || random == null) throw new NullPointerException();
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
			
				char c = line.charAt(i);
				Field field;
				
				if(c == '.'){
					field = new Water(map, x, y, null);
					log.addCell(Cell.WATER, null, x, y);
				}
				else if(c == '#'){
					field = new Island(map, x, y, null);
					log.addCell(Cell.ISLAND, null, x, y);
				}
				else if(c == '$'){
					field = new ProvisionIsland(map, x, y);
					log.addCell(Cell.SUPPLY, null, x, y);
				}
				else if(c == '&'){
					Kraken k = new Kraken(map.giveNewEntityID(), null);
					Key[] keys = {Key.X_COORD, Key.Y_COORD};
					int[] values = {x, y};
					log.create(Entity.KRAKEN, k.getId(), keys, values);
					kraken.add(k);
					
					field = new Water(map, x, y, k);
					log.addCell(Cell.WATER, null, x, y); // null for the team
				}
				else if(isTeamLetter(c, teams.size())){
					int teamNumber = c - 'a';
					Team team;
					
					try{
						team = teams.get(teamNumber);
					}
					catch(ArrayIndexOutOfBoundsException e){
						throw new ArrayIndexOutOfBoundsException("Base on map has no related team");
					}
					
					Ship ship = new Ship(team, null, map.giveNewActorID(), previousShip);
					teams.get(teamNumber).addShip(ship);
					Key[] keys = {Key.DIRECTION, Key.CONDITION, Key.FLEET, Key.MORAL, Key.PC, Key.RESTING, Key.VALUE, Key.X_COORD, Key.Y_COORD};
					int[] values = {0, 3, teamNumber, 4, 0, 0, 0, x, y};
					log.create(Entity.SHIP, ship.getID(), keys, values);
					
					if(previousShip == null)
						map.setFirstShip(ship);
					previousShip = ship;
					
					field = new Base(map, x, y, team, ship);
					log.addCell(Cell.WATER, teamNumber, x, y);
				}
				else if(isDigit(c)){
					int id = map.giveNewEntityID();
					Treasure t = new Treasure(id, c - '0');
					Key[] keys = {Key.VALUE, Key.X_COORD, Key.Y_COORD};
					int[] values = {t.getValue(), x, y};
					log.create(Entity.TREASURE, id, keys, values);
					
					field = new Island(map, x, y, t);
					log.addCell(Cell.ISLAND, null, x, y);
				}
				else
					throw new IllegalArgumentException("Invalid character " + c + " in map file at position (" + x + "," + y + ")");
				
				fields[x][y] = field;
				incrementXY(width, height);
			}
		}
		
		if(y != height) throw new IllegalArgumentException();
		reader.close();
		
		if(teams.size() == 1) {
			if(teams.get(0).getShipCount() <= 0)
				throw new IllegalArgumentException("Not every team has bases/ships on the map");
			
			log.fleetScore(teams.get(0).getName(), 0);
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
						log.fleetScore(team.getName() - 'a', 0);
						lastShip = true;
					}
				}
			}
			else{
				for(Team team: teams){
					log.fleetScore(team.getName() - 'a', 0);
					
					if(team.getShipCount() <= 0)
						throw new IllegalArgumentException("Not every team has bases/ships on the map");
				}
			}
		}
				
		map.setMapValues(fields, kraken);
		return map;
	}
	
	
	private boolean isTeamLetter(char c, int max){
		if(c >= 'a' && c < ('a' + max))
			return true;
		
		return false;
	}
	
	private boolean isDigit(char c){
		if(c >= '1' && c <= '9')
			return true;
		
		return false;
	}
	
	private void incrementXY(int width, int height) {
		x++;
		if(x >= width)
			y++;
		x = x % width;
	}
	
}
