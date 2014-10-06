package view.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public class Ressources {

	private Image water;
	private Image waterFar;
	private Image island;
	private Image base;
	private Image provision;
	private Image ship;
	private Image treasureWaterImage;
	private Image treasureIslandImage;
	private Image buoyImage;
	private Image krakenImage;
	private Image shoreIsland;
	private Image shoreWater;
	
	private List<Image> islandDetails = new ArrayList<Image>();
	
	private Configuration defaultConfiguration;
	
	
	public Ressources(){
		try{
	    	File file = new File("src/main/ressources/island2.png");
	    	InputStream stream = new FileInputStream(file);
	    	island = new Image(stream);

	    	file = new File("src/main/ressources/water2.png");
	    	stream = new FileInputStream(file);
	    	water = new Image(stream);
	    	
	    	file = new File("src/main/ressources/water_far.png");
	    	stream = new FileInputStream(file);
	    	waterFar = new Image(stream);
	    	
	    	file = new File("src/main/ressources/shoreIsland.png");
	    	stream = new FileInputStream(file);
	    	provision = new Image(stream);
	    	
	    	file = new File("src/main/ressources/base.png");
	    	stream = new FileInputStream(file);
	    	base = new Image(stream);
	    	
	    	file = new File("src/main/ressources/ship.png");
	    	stream = new FileInputStream(file);
	    	ship = new Image(stream);
	    	
	    	file = new File("src/main/ressources/Kraken2.png");
	    	stream = new FileInputStream(file);
	    	krakenImage = new Image(stream);
	    	
	    	file = new File("src/main/ressources/islandCoin.png");
	    	stream = new FileInputStream(file);
	    	treasureIslandImage = new Image(stream);

	    	file = new File("src/main/ressources/waterCoin.png");
	    	stream = new FileInputStream(file);
	    	treasureWaterImage = new Image(stream);
	    	
	    	file = new File("src/main/ressources/shore2.png");
	    	stream = new FileInputStream(file);
	    	shoreWater = new Image(stream);
	    	
	    	file = new File("src/main/ressources/shoreIsland.png");
	    	stream = new FileInputStream(file);
	    	shoreIsland = new Image(stream);
	    	
	    	file = new File("src/main/ressources/buoy.png");
	    	stream = new FileInputStream(file);
	    	buoyImage = new Image(stream);
	    	
	    	file = new File("src/main/ressources/bush1.png");
	    	stream = new FileInputStream(file);
	    	islandDetails.add(new Image(stream));
	    	
	    	file = new File("src/main/ressources/house1.png");
	    	stream = new FileInputStream(file);
	    	islandDetails.add(new Image(stream));
	    	
	    	stream.close();
	    	createDefaultConfig();
    	}
    	catch(Exception e){
    		System.out.println("Couldn't read ressource files");
    	}
	}
	
	public Image getWaterImage(){
		return water;
	}
	
	public Image getWaterFarImage(){
		return waterFar;
	}
	
	public Image getIslandImage(){
		return island;
	}
	
	public Image getBaseImage(){
		return base;
	}
	
	public Image getProvisionImage(){
		return provision;
	}
	
	public Image getShipImage(){
		return ship;
	}
	
	public Image getWaterTreasureImage(){
		return treasureWaterImage;
	}
	
	public Image getIslandTreasureImage(){
		return treasureIslandImage;
	}
	
	public Image getBuoyImage(){
		return buoyImage;
	}
	
	public Image getKrakenImage(){
		return krakenImage;
	}
	
	public Image getShoreWaterImage(){
		return shoreWater;
	}
	
	public Image getShoreIslandImage(){
		return shoreIsland;
	}
	
	public List<Image> getIslandDetailImages(){
		return islandDetails;
	}
	
	public Configuration getDefaultConfig(){
		return defaultConfiguration;
	}
	
	public void createDefaultConfig() throws IOException{
		defaultConfiguration = new Configuration();
		defaultConfiguration.setTreasureDensity(1);
		defaultConfiguration.setSupplyDensity(2);
		defaultConfiguration.setKrakenCount(100);
		defaultConfiguration.setTeamCount(3);
		defaultConfiguration.setShipCount(10);

		defaultConfiguration.setRounds(10000);
		defaultConfiguration.getTactics().add("src/main/ressources/alternative2.ship");
		
		File file = new File("src/main/ressources/Large.map");
		MapPreview preview = new MapPreview(file);
		char[][] map = preview.getMap();
		defaultConfiguration.setMap(map, false);
	}
}
