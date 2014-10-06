package view.utility;

import java.io.File;

import view.GUIController;
import view.events.HoverEvent;
import view.events.SliderListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

        root.getChildren().addAll(openFile, box);
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		try{
			MapPreview preview = new MapPreview(mapFile);
			preview.draw(gc);
			control.getConfiguration().setMap(preview.getMap(), false);
		}
		catch(Exception e){
			control.getHoverText().setText("Please try select a valid file");
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
