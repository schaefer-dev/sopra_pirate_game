package de.unisaarland.cs.st.pirates.group5.view.utility;

import java.io.File;
import java.io.IOException;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SelectionPreview extends SelectionWindow {

	private String name;
	private MapPreview preview;
	private Text rounds;
	private Text mapSize;
	private Text description;
	private Configuration config;
	private int teamCount;
	
	public SelectionPreview(Configuration config, String name, String rounds, String mapSize, int teamCount, String description, File mapFile) throws IOException{
		root = new VBox(20);
		root.getStyleClass().add("preview");
				
		if(mapFile == null){
			char[][] emptyMap = {{'.'}};
			this.preview = new MapPreview(emptyMap);
		}
		else
			this.preview = new MapPreview(mapFile);
		
		this.config = config;
		this.name = name;
		this.rounds = new Text(rounds);
		this.mapSize = new Text(mapSize);
		this.description = new Text(description);
		this.teamCount = teamCount;

		root.getChildren().addAll(this.mapSize, this.description);
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		config.setTeamCount(teamCount);
		config.setMap(preview.getMap(), false);
		preview.draw(gc);
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int getRounds() {
		return Integer.parseInt(rounds.getText());
	}
}