package de.unisaarland.cs.st.pirates.group5.view.utility;

import java.io.File;

import de.unisaarland.cs.st.pirates.group5.view.GUIController;
import de.unisaarland.cs.st.pirates.group5.view.events.HoverEvent;
import de.unisaarland.cs.st.pirates.group5.view.events.SliderListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class SelectionFile extends SelectionWindow {

	private File mapFile;
	private GUIController control;
	private SliderListener rListener;
	
	public SelectionFile(final GUIController control, final GraphicsContext gc) {
		this.control = control;
		root = new VBox(20);
		root.getStyleClass().add("preview");
		
		final FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MAP", "*.map"));
		final Button openFile = new Button("-> File Explorer");
		openFile.getStyleClass().add("openbutton");
        openFile.setOnAction(new EventHandler<ActionEvent>(){
        	
            @Override
            public void handle(final ActionEvent e) {
                mapFile = fileChooser.showOpenDialog(control.getStage());
                if(mapFile != null)
                	draw(gc);
            }
        });
        
		Label rounds = new Label("Rounds");
		rounds.getStyleClass().add("menulabel");
		Slider roundSlider = new Slider(Configuration.ROUNDS_MIN, Configuration.ROUNDS_MAX, Configuration.ROUNDS_MIN);
		roundSlider.setOnMouseEntered(new HoverEvent(control.getHoverText(), "Value determines the amount played rounds"));
		roundSlider.setOnMouseExited(new HoverEvent(control.getHoverText(), ""));
		roundSlider.setMaxWidth(100);
		roundSlider.setMajorTickUnit(4);
		roundSlider.setMinorTickCount(2);
		roundSlider.setSnapToTicks(true);
		Label roundsLabel = new Label(String.format("%.0f", roundSlider.getValue()));
		roundsLabel.getStyleClass().add("menulabel");
		rListener = new SliderListener(roundSlider, roundsLabel);
		
		HBox box = new HBox(8);
		box.getChildren().addAll(rounds, roundSlider, roundsLabel);

        root.getChildren().addAll(openFile, box, new Text("\n\n"));
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		try{
			MapPreview preview = new MapPreview(mapFile);
			preview.draw(gc);
			char[][] map = preview.getMap();
			int[] teams = new int[26];
			
			for(int y = 0; y < map[0].length; y++){
				for(int x = 0; x < map.length; x++){
					char field = map[x][y];
					if(field >= 'a' && field <= 'z')
						teams[field - 'a'] = 1;
				}
			}
			
			int teamCounter = 0;
			for(int i = 0; i < teams.length; i++){
				if(teams[i] == 1)
					teamCounter++;
			}
			
			control.getConfiguration().setTeamCount(teamCounter);
			control.getConfiguration().setMap(map, false);
		}
		catch(Exception e){
			//control.getHoverText().setText("Please try select a valid file");
		}
	}
	
	@Override
	public int getRounds() {
		return rListener.get();
	}

	@Override
	public String toString() {
		return "From File >";
	}
}
