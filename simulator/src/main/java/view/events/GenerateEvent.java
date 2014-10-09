package view.events;

import view.utility.Configuration;
import view.utility.MapPreview;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;

public class GenerateEvent implements EventHandler<ActionEvent> {
	
	private SliderListener mapSize, islandCount, islandSize;
	private GraphicsContext gc;
	private Configuration config;
	
	
	public GenerateEvent(Configuration config, SliderListener mapSize, SliderListener islandCount, SliderListener islandSize, GraphicsContext gc) {
		this.config = config;
		this.mapSize = mapSize;
		this.islandCount = islandCount;
		this.islandSize = islandSize;
		this.gc = gc;
	}
	
	@Override
	public void handle(ActionEvent arg0) {
		
		Integer height = mapSize.get();
		Integer width  = mapSize.get();
		int isCount = islandCount.get();
		int isSize  = islandSize.get();
		isSize = 10 - isSize + 1;

		char[][] fields = config.generateMap(height, width, isCount, isSize);	
		MapPreview preview = new MapPreview(fields);
		preview.draw(gc);
	}
}
