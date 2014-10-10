package de.unisaarland.cs.st.pirates.group5.view.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.media.Media;

public class Ressources {

	private Image water;
	private Image waterFar;
	private Image island;
	private Image provision;
	private Image ship;
	private Image shipDestroyed;
	private Image treasureWaterImage;
	private Image treasureIslandImage;
	private Image buoyImage;
	private Image krakenImage;
	private Image krakenFarImage;
	private Image shoreIsland;
	private Image shoreWater;
	private Image cupImage;
	
	private Image playImage;
	private Image pauseImage;
	private Image nextImage;
	private Image speedUpImage;
	private Image slowDownImage;
	
	private Media inGameMusic;
	private Media menuMusic;
	private Media windSound;
	
	private List<Image> islandDetails = new ArrayList<Image>();
	private List<Image> shipImages = new ArrayList<Image>();
	private List<Image> baseImages = new ArrayList<Image>();
	
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
	    	
	    	file = new File("src/main/ressources/proviant.png");
	    	stream = new FileInputStream(file);
	    	provision = new Image(stream);
	    		    	
	    	file = new File("src/main/ressources/ship.png");
	    	stream = new FileInputStream(file);
	    	ship = new Image(stream);
	    	
	    	file = new File("src/main/ressources/ships/shipdestroyed.png");
	    	stream = new FileInputStream(file);
	    	shipDestroyed = new Image(stream);
	    	
	    	file = new File("src/main/ressources/kraken3.png");
	    	stream = new FileInputStream(file);
	    	krakenImage = new Image(stream);
	    	
	    	file = new File("src/main/ressources/krakenfaraway.png");
	    	stream = new FileInputStream(file);
	    	krakenFarImage = new Image(stream);
	    	
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
	    	
	    	file = new File("src/main/ressources/cup.png");
	    	stream = new FileInputStream(file);
	    	cupImage = new Image(stream);
	    	
	    	
	    	for(int i = 1; i < 6; i++){
		    	file = new File("src/main/ressources/IslandDetails/"+i+".png");
		    	stream = new FileInputStream(file);
		    	islandDetails.add(new Image(stream));
	    	}
	    	
	    	for(int i = 1; i <= 26; i++){
	    		file = new File("src/main/ressources/ships/"+i+".png");
	    		stream = new FileInputStream(file);
	    		shipImages.add(new Image(stream));
	    	}
	    	
	    	for(int i = 1; i <= 26; i++){
	    		file = new File("src/main/ressources/Bases/Base ("+i+").png");
	    		stream = new FileInputStream(file);
	    		baseImages.add(new Image(stream));
	    	}
	    	
	    	file = new File("src/main/ressources/Buttons/play.png");
	    	stream = new FileInputStream(file);
	    	playImage = new Image(stream);
	    	
	    	file = new File("src/main/ressources/Buttons/pause.png");
	    	stream = new FileInputStream(file);
	    	pauseImage = new Image(stream);
	    	
	    	file = new File("src/main/ressources/Buttons/next.png");
	    	stream = new FileInputStream(file);
	    	nextImage = new Image(stream);
	    	
	    	file = new File("src/main/ressources/Buttons/speedup.png");
	    	stream = new FileInputStream(file);
	    	speedUpImage = new Image(stream);
	    	
	    	file = new File("src/main/ressources/Buttons/slowdown.png");
	    	stream = new FileInputStream(file);
	    	slowDownImage = new Image(stream);
	    	
			file = new File("src/main/ressources/Music/InGameMusic.mp3");
			inGameMusic = new Media(file.toURI().toString());
			
			file = new File("src/main/ressources/Music/MenuMusic2.mp3");
			menuMusic = new Media(file.toURI().toString());
			
			file = new File("src/main/ressources/Music/wind.mp3");
			windSound = new Media(file.toURI().toString());
	    	
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
	
	public Image getProvisionImage(){
		return provision;
	}
	
	public Image getShipImage(){
		return ship;
	}
	
	public Image getDestroyedShipImage(){
		return shipDestroyed;
	}
	
	public Image getShipImage(int fleet){
		return shipImages.get(fleet);
	}
	
	public Image getBaseImage(int fleet){
		return baseImages.get(fleet);
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
	
	public Image getKrakenFarImage(){
		return krakenFarImage;
	}
	
	public Image getShoreWaterImage(){
		return shoreWater;
	}
	
	public Image getShoreIslandImage(){
		return shoreIsland;
	}
	
	public Image getCupImage(){
		return cupImage;
	}
	
	public List<Image> getIslandDetailImages(){
		return islandDetails;
	}
	
	
	public Image getPlayImage(){
		return playImage;
	}
	
	public Image getPauseImage(){
		return pauseImage;
	}
	
	public Image getNextImage(){
		return nextImage;
	}
	
	public Image getSpeedUpImage(){
		return speedUpImage;
	}
	
	public Image getSlowDownImage(){
		return slowDownImage;
	}
	
	public Media getInGameMusic(){
		return inGameMusic;
	}
	
	public Media getMenuMusic(){
		return menuMusic;
	}
	
	public Media getWindSound(){
		return windSound;
	}
	
	
	public Configuration getDefaultConfig(){
		return defaultConfiguration;
	}
	
	public void createDefaultConfig() throws IOException{
		defaultConfiguration = new Configuration();
		defaultConfiguration.generate = true;
		defaultConfiguration.setTreasureDensity(60);
		defaultConfiguration.setSupplyDensity(20);
		defaultConfiguration.setKrakenCount(20);
		defaultConfiguration.setTeamCount(10);
		defaultConfiguration.setShipCount(10);
		defaultConfiguration.setRounds(10000);
		defaultConfiguration.getTactics().add("src/main/ressources/guybrush.ship");
		
		File file = new File("src/main/ressources/default.map");
		MapPreview preview = new MapPreview(file);
		char[][] map = preview.getMap();
		defaultConfiguration.setMap(map, false);
	}
}
