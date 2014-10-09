package de.unisaarland.cs.st.pirates.group5.view.events;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import de.unisaarland.cs.st.pirates.group5.view.utility.Configuration;
import de.unisaarland.cs.st.pirates.group5.view.utility.MapPreview;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class GenerateGoogleMapEvent implements EventHandler<ActionEvent> {

	private TextField locationField;
	private SliderListener mapSizeSlider, zoomSlider;
	private GraphicsContext gc;
	private Text errorText;
	private Configuration config;
	
	
	public GenerateGoogleMapEvent(Configuration config, TextField location, SliderListener mapSize, SliderListener zoom, GraphicsContext gc, Text errorField) {
		this.config = config;
		this.locationField = location;
		this.mapSizeSlider = mapSize;
		this.zoomSlider = zoom;
		this.gc = gc;
		this.errorText = errorField;
	}
	
	@Override
	public void handle(ActionEvent arg0) {
		String location = locationField.getText();
		location = location.replaceAll(" ", "");
		Integer height = mapSizeSlider.get();
		Integer width  = mapSizeSlider.get();
		Integer zoom  = zoomSlider.get();
		
		BufferedImage image = null;
		
		try{
			BufferedImage googleImage = ImageIO.read(new URL(
					"http://maps.googleapis.com/maps/api/staticmap?center="+location+"+&zoom="+zoom.toString()+"&scale=false&size=600x600&maptype=terrain&sensor=false&format=png&visual_refresh=true"));
		
	        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	        Graphics2D g2d = image.createGraphics();
	        g2d.drawImage(googleImage, 0, 0, width, height, null);
	        g2d.dispose();
		} 
		catch(Exception e) {
			errorText.setText("Invalid Input. Please try another location.");
			return;
		}
		
		char water = '.';
		char island = '#';
		char[][] map = new char[image.getWidth()][image.getHeight()];

		for(int y = 0; y < image.getHeight(); y++){
			for(int x = 0; x < image.getWidth(); x++){
				Color color = new Color(image.getRGB(x, y));
				int r = color.getRed();
				int g = color.getGreen();
				int b = color.getBlue();
				int black = 120;
				
				if((r < b) && (g < b))
					map[x][y] = water;
				else if((r < black) && (g < black) && (b < black))
					map[x][y] = water;
				else if(b < 50)
					map[x][y] = water;
				else
					map[x][y] = island;
			}
		}
		
		config.setMap(map, true);
		MapPreview preview = new MapPreview(config.getMap());
		preview.draw(gc);
	}

}
