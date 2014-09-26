package view.events;

import view.utility.MapPreview;
import view.utility.Generator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;

public class GenerateEvent implements EventHandler<ActionEvent> {
	
	private SliderListener mapSize, islandCount, islandSize;
	private GraphicsContext gc;
	
	public GenerateEvent(SliderListener mapSize, SliderListener islandCount, SliderListener islandSize, GraphicsContext gc) {
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
		
		Generator gen = new Generator(height, width, isCount, isSize);
		char[][] fields = gen.generateMap();
		
		MapPreview preview = new MapPreview(fields);
		preview.draw(gc);
	}

}
