package view.utility;

import java.io.File;
import java.io.IOException;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SelectionPreview extends SelectionWindow {

	private String name;
	private MapPreview preview;
	private Text mapSize;
	private Text teamCount;
	private Text description;
	
	public SelectionPreview(String name, String mapSize, String teamCount, String description, File mapFile) throws IOException{
		root = new VBox(20);
		root.getStyleClass().add("preview");
				
		if(mapFile == null){
			char[][] emptyMap = {{'.'}};
			this.preview = new MapPreview(emptyMap);
		}
		else
			this.preview = new MapPreview(mapFile);
		
		this.name = name;
		this.mapSize = new Text(mapSize);
		this.teamCount = new Text(teamCount);
		this.description = new Text(description);

		root.getChildren().addAll(this.mapSize, this.teamCount, this.description);
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		preview.draw(gc);
	}

	@Override
	public String toString() {
		return name;
	}
}