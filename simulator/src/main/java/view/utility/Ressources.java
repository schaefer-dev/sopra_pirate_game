package view.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javafx.scene.image.Image;

public class Ressources {

	private Image water;
	private Image island;
	
	
	
	public Ressources(){
		try{
	    	File file = new File("src/main/ressources/he.png");
	    	InputStream stream = new FileInputStream(file);
	    	island = new Image(stream);
	    	stream.close();
	    	
	    	file = new File("src/main/ressources/hexagon2.png");
	    	stream = new FileInputStream(file);
	    	water = new Image(stream);
	    	stream.close();
    	}
    	catch(Exception e){}
	}
	
	public Image getWaterImage(){
		return water;
	}
	
	public Image getIslandImage(){
		return island;
	}
	
	
	
}
