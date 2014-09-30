package view.utility;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Configuration {

	public final static int MAP_SIZE_MIN = 10;
	public final static int MAP_SIZE_MAX = 198;
	public final static int ISLAND_COUNT_MIN = 0;
	public final static int ISLAND_COUNT_MAX = 50;
	public final static int ISLAND_SIZE_MIN = 1;
	public final static int ISLAND_SIZE_MAX = 10;
	public final static int ZOOM_MIN = 0;
	public final static int ZOOM_MAX = 10;
	
	public final static int TREASURE_COUNT_MIN = 1;
	public final static int TREASURE_COUNT_MAX = 10;
	private int treasureCount = TREASURE_COUNT_MIN;
	
	public final static int TREASURE_DENSITY_MIN = 0;
	public final static int TREASURE_DENSITY_MAX = 50;
	private int treasureDensity = TREASURE_DENSITY_MIN;

	public final static int SUPPLY_DENSITY_MIN = 0;
	public final static int SUPPLY_DENSITY_MAX = 50;
	private int supplyDensity = SUPPLY_DENSITY_MIN;
	
	public final static int KRAKEN_COUNT_MIN = 0;
	public final static int KRAKEN_COUNT_MAX = 50;
	private int krakenCount = KRAKEN_COUNT_MIN;
	
	public final static int TEAM_COUNT_MIN = 1;
	private int teamCountMax = 26;
	private int teamCount = 0;
	
	public final static int SHIP_COUNT_MIN = 1;
	public final static int SHIP_COUNT_MAX = 100;
	public int shipCount = SHIP_COUNT_MIN;
	
	private List<String> tactics = new LinkedList<String>();	//list.toArray(new String[list.size()]);
	private List<String>  captainNames = new LinkedList<String>(Arrays.asList(
			"Captain Blaubaer", "Black Beard", "Red Beard", "Henry Morgan", "Francis Drake", "Stoertebeker", "Guybrush Threepwood", "Captain Hook", 
			"Monkey D. Ruffy", "LeChuck"));

	private char[][] map;
	private Generator generator;
	
	
	public void setMap(char[][] map, boolean smooth){
		this.map = map;
		generator = new Generator(map);
		
		if(smooth)
			generator.smoothIslands(5);
	}
	
	public char[][] generateMap(Integer height, Integer width, int isCount, int isSize){
		generator = new Generator(height, width, isCount, isSize);
		map = generator.generateMap();
		
		return map;
	}
	
	public void removeMap(){
		generator = null;
		map = null;
	}
	
	public void placeObjectsOnMap(){
		generator.setProvision(0.1 * supplyDensity);
		generator.setTreasure(0.1 * treasureDensity);
		//TODO: add treasure count
		generator.setKraken(krakenCount);
	}
	
	public void addTeamsToMap(){
		generator.addTeamInformation(teamCount, shipCount);
		generator.setBases();
	}
	
	public char[][] getMap(){
		return map;
	}
	
	public int getTreasureCount(){
		return treasureCount;
	}
		
	public void setTreasureCount(int treasureCount){
		if(treasureCount < TREASURE_COUNT_MIN || treasureCount > TREASURE_COUNT_MAX)
			throw new IllegalArgumentException();
		
		this.treasureCount = treasureCount;
	}
	
	public int getTreasureDensity(){
		return treasureDensity;
	}
	
	public void setTreasureDensity(int treasureDensity){
		if(treasureDensity < TREASURE_DENSITY_MIN || treasureDensity > TREASURE_DENSITY_MAX)
			throw new IllegalArgumentException();
		
		this.treasureDensity = treasureDensity;
	}
	
	public int getSupplyDensity(){
		return supplyDensity;
	}
	
	public void setSupplyDensity(int supplyDensity){
		if(supplyDensity < SUPPLY_DENSITY_MIN || supplyDensity > SUPPLY_DENSITY_MAX)
			throw new IllegalArgumentException();
		
		this.supplyDensity = supplyDensity;
	}
	
	public int getKrakenCount(){
		return krakenCount;
	}
	
	public void setKrakenCount(int krakenCount){
		if(krakenCount < KRAKEN_COUNT_MIN || krakenCount > KRAKEN_COUNT_MAX)
			throw new IllegalArgumentException();
		
		this.krakenCount = krakenCount;
	}
	
	public int getTeamCount(){
		return teamCount;
	}
	
	public void setTeamCount(int teamCount){
		if(teamCount < TEAM_COUNT_MIN || teamCount > teamCountMax)
			throw new IllegalArgumentException();
		
		this.teamCount = teamCount;
	}
	
	public void addTeam(){
		++teamCount;
	}
	
	public void removeTeam(){
		if(teamCount > 0)
			--teamCount;
	}
	
	public void removeAllTeams(){
		teamCount = TEAM_COUNT_MIN;
	}
	
	public int getTeamCountMax(){
		return teamCountMax;
	}
	
	public void setTeamCountMax(int max){
		if(max < TEAM_COUNT_MIN || max > 26)
			throw new IllegalArgumentException();
		
		teamCountMax = max;
	}
	
	public int getShipCount(){
		return shipCount;
	}
	
	public void setShipCount(int shipCount){
		if(shipCount < SHIP_COUNT_MIN || shipCount > SHIP_COUNT_MAX)
			throw new IllegalArgumentException();
		
		this.shipCount = shipCount;
	}
	
	public List<String> getCaptainNames(){
		return captainNames;
	}

	public List<String> getTactics(){
		return tactics;
	}
}
