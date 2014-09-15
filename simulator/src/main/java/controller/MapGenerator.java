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
import model.Field;
import model.Island;
import model.Kraken;
import model.Map;
import model.ProvisionIsland;
import model.Team;
import model.Treasure;
import model.Water;

public class MapGenerator {

	private Field[][] fields;

	
	@SuppressWarnings("resource")
	public Map createMap(InputStream stream, List<Team> teams, LogWriter log, Random random) throws IOException{
		
		Map map = new Map(random, log);
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		int x, y;
		
		try{
			x = Integer.parseInt(reader.readLine());
			y = Integer.parseInt(reader.readLine());
			fields = new Field[x][y];
		}
		catch(NumberFormatException e){
			throw new IllegalArgumentException("Submitted coordinates are not valid numbers");
		}
		
		if(x < 2 || x > 200 || y < 2 || y > 200) 
			throw new IllegalArgumentException("Coordinates are not in range (2,2)...(200,200)");

		int lineNumber = 0;
		String line;
		
		while((line = reader.readLine()) != null){
			
			line = line.replaceAll(" ", "");
			if(line.length() != x || lineNumber >= y) throw new IllegalArgumentException();
			
			for(int i = 0; i < line.length(); i++){
				char c = line.charAt(i);
				Field field;
				
				if(c == '.'){
					field = new Water(map, i, lineNumber, null);
					log.addCell(Cell.WATER, null, i, lineNumber);
				}
				else if(c == '#'){
					field = new Island(map, i, lineNumber, null);
					log.addCell(Cell.ISLAND, null, i, lineNumber);
				}
				else if(c == '$'){
					field = new ProvisionIsland(map, i, lineNumber);
					log.addCell(Cell.SUPPLY, null, i, lineNumber);
				}
				else if(c == '&'){
					Kraken k = new Kraken(map.giveNewEntityID(), null);
					Key[] keys = {Key.X_COORD, Key.Y_COORD};
					int[] values = {i, lineNumber};
					log.create(Entity.KRAKEN, k.getId(), keys, values);
					kraken.add(k);
					
					field = new Water(map, i, lineNumber, k);
					log.addCell(Cell.WATER, null, i, lineNumber);
				}
				else if(isLetter(c, teams.size())){
					

				}
				else if(isDigit(c)){
					Treasure t = new Treasure(map.giveNewEntityID(), c - '0');
					Key[] keys = {Key.VALUE, Key.X_COORD, Key.Y_COORD};
					int[] values = {t.getValue(), i, lineNumber};
					//TODO: log.create(Entity.TREASURE, , keys, values);
					
					field = new Island(map, i, lineNumber, t);
					log.addCell(Cell.ISLAND, null, i, lineNumber);
				}
				else
					throw new IllegalArgumentException("Invalid character in map file at position (" + i + "," + lineNumber + ")");
				
				//fields[i][lineNumber] = field;
			}
			
			lineNumber++;
		}
		
		if(lineNumber != y) throw new IllegalArgumentException();
		reader.close();
		
		return null;
	}
	
	private boolean isLetter(char c, int max){
		if(c >= 'a' && c < ('a' + max))
			return true;
		
		return false;
	}
	
	private boolean isDigit(char c){
		if(c >= '1' && c <= '9')
			return true;
		
		return false;
	}
}
