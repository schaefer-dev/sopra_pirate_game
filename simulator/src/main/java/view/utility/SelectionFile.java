package view.utility;

import java.io.File;

import view.GUIController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class SelectionFile extends SelectionWindow {

	private File mapFile;
	private GUIController control;
	
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
        
        root.getChildren().add(openFile);
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		try{
			MapPreview preview = new MapPreview(mapFile);
			preview.draw(gc);
			control.getConfiguration().setMap(preview.getMap(), false);
		}
		catch(Exception e){
			control.getHoverText().setText("Invalid map. Please try another file.");
		}
	}

	@Override
	public String toString() {
		return "From File >";
	}
}
