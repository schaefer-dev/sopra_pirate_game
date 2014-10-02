package view.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javafx.scene.image.Image;

public class Ressources {

	private Image water;
	private Image island;
	private Image base;
	private Image provision;
	private Image ship;
	private Image treasureWaterImage;
	private Image treasureIslandImage;
	private Image buoyImage;
	private Image krakenImage;
	
	private Configuration defaultConfiguration;
	
	
	public Ressources(){
		try{
	    	File file = new File("src/main/ressources/he.png");
	    	InputStream stream = new FileInputStream(file);
	    	island = new Image(stream);
	    	stream.close();
	    	
	    	file = new File("src/main/ressources/water.png");
	    	stream = new FileInputStream(file);
	    	water = new Image(stream);
	    	stream.close();
	    	
	    	file = new File("src/main/ressources/provision.png");
	    	stream = new FileInputStream(file);
	    	provision = new Image(stream);
	    	stream.close();
	    	
	    	file = new File("src/main/ressources/base.png");
	    	stream = new FileInputStream(file);
	    	base = new Image(stream);
	    	stream.close();
	    	
	    	file = new File("src/main/ressources/ship100px.png");
	    	stream = new FileInputStream(file);
	    	ship = new Image(stream);
	    	stream.close();
	    	
	    	file = new File("src/main/ressources/islandCoin.png");
	    	stream = new FileInputStream(file);
	    	treasureIslandImage = new Image(stream);
	    	stream.close();
	    	
	    	file = new File("src/main/ressources/waterCoin.png");
	    	stream = new FileInputStream(file);
	    	treasureWaterImage = new Image(stream);
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
	
	
	public Configuration getDefaultConfig(){
		return defaultConfiguration;
	}
	
	public void createDefaultConfig() throws IOException{
		defaultConfiguration = new Configuration();
		defaultConfiguration.setTreasureCount(3);
		defaultConfiguration.setTreasureDensity(1);
		defaultConfiguration.setSupplyDensity(2);
		defaultConfiguration.setKrakenCount(10);
		defaultConfiguration.setTeamCount(2);
		defaultConfiguration.setShipCount(10);
		defaultConfiguration.setRounds(10000);
		defaultConfiguration.getTactics().add("src/main/ressources/alternative3.ship");
		
		File file = new File("src/main/ressources/default.map");
		MapPreview preview = new MapPreview(file);
		char[][] map = preview.getMap();
		defaultConfiguration.setMap(map, false);
	}
	
}
