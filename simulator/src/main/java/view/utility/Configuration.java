package view.utility;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javafx.scene.control.ComboBox;

public class Configuration {

	public final static int MAP_SIZE_MIN = 40;
	public final static int MAP_SIZE_MAX = 198;
	public final static int ISLAND_COUNT_MIN = 0;
	public final static int ISLAND_COUNT_MAX = 50;
	public final static int ISLAND_SIZE_MIN = 1;
	public final static int ISLAND_SIZE_MAX = 10;
	public final static int ZOOM_MIN = 0;
	public final static int ZOOM_MAX = 10;
	
	public final static int TREASURE_DENSITY_MIN = 0;
	public final static int TREASURE_DENSITY_MAX = 50;
	private int treasureDensity = TREASURE_DENSITY_MIN;

	public final static int SUPPLY_DENSITY_MIN = 0;
	public final static int SUPPLY_DENSITY_MAX = 50;
	private int supplyDensity = SUPPLY_DENSITY_MIN;
	
	public final static int KRAKEN_COUNT_MIN = 0;
	public final static int KRAKEN_COUNT_MAX = 2000;
	private int krakenCount = KRAKEN_COUNT_MIN;
	
	public final static int TEAM_COUNT_MIN = 1;
	private int teamCountMax = 26;
	private int teamCount = 0;
	
	public final static int SHIP_COUNT_MIN = 1;
	public final static int SHIP_COUNT_MAX = 40;
	private int shipCount = SHIP_COUNT_MIN;
	
	public final static int ROUNDS_MIN = 1;
	public final static int ROUNDS_MAX = 10000;
	private int rounds = ROUNDS_MIN;
	
	private List<String> tactics = new LinkedList<String>();
	private final List<String>  captainNames = new LinkedList<String>(Arrays.asList(
			"Captain Blaubaer", "Black Beard", "Red Beard", "Henry Morgan", "Francis Drake", "Stoertebeker", "Captain Hook", 
			"Monkey D. Ruffy", "LeChuck"));
	
	private List<String> finalCaptainNames = new LinkedList<String>();
	private List<String> currentFreeNames = captainNames;
	private Set<String> currentReservedNames = new HashSet<String>();
	private List<ComboBox<String>> teamConfigs = new LinkedList<ComboBox<String>>();
	
	public boolean generate = true;

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
		generator.setKraken(krakenCount);
	}
	
	public void addTeamsToMap(){
		generator.addTeamInformation(teamCount, shipCount);
		generator.setBases();
	}
	
	public char[][] getMap(){
		return map;
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
	
	public List<ComboBox<String>> getTeamConfigurations(){
		return teamConfigs;
	}
	
	public List<String> getFinalCaptainNames(){
		return finalCaptainNames;
	}
	
	public List<String> getCaptainNames(){
		return currentFreeNames;
	}
	
	public Set<String> getCurrentCaptainName(){
		return currentReservedNames;
	}
	
	public int getCaptainNumber(String captainName){
		int counter = 0;
		for(String name: captainNames){
			if(name.equals(captainName))
				return counter;
			counter++;
		}
						
		return -1;
	}

	public List<String> getTactics(){
		return tactics;
	}

	public int getRounds() {
		return rounds;
	}

	public void setRounds(int rounds) {
		if(rounds < ROUNDS_MIN || rounds > ROUNDS_MAX)
			throw new IllegalArgumentException();
		
		this.rounds = rounds;
	}
}
