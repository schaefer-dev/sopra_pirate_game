package de.unisaarland.cs.st.pirates.group5.view.gamestates;

import de.unisaarland.cs.st.pirates.group5.view.GUIController;
import de.unisaarland.cs.st.pirates.group5.view.events.GenerateGoogleMapEvent;
import de.unisaarland.cs.st.pirates.group5.view.events.HoverEvent;
import de.unisaarland.cs.st.pirates.group5.view.events.SliderListener;
import de.unisaarland.cs.st.pirates.group5.view.events.SwitchState;
import de.unisaarland.cs.st.pirates.group5.view.utility.Configuration;
import de.unisaarland.cs.st.pirates.group5.view.utility.GameState;
import de.unisaarland.cs.st.pirates.group5.view.utility.MapPreview;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class GoogleMapsState implements GameState {

	private GUIController manager;
	private BorderPane root;
	private String title = "Google Maps";
	
	@Override
	public void entered(GUIController control) {
		manager = control;
		manager.getTitleText().setText(title);	

		Canvas mapPreview = new Canvas(manager.getStage().getWidth()/3.7, manager.getStage().getWidth()/3.7);
		GraphicsContext gc = mapPreview.getGraphicsContext2D();
		
		try{
			char[][] emptyMap = {{'.'}};
			MapPreview p = new MapPreview(emptyMap);
			p.draw(gc);
		}
		catch(Exception e){}
		
		Label location = new Label("Location");
		location.getStyleClass().add("menulabel");
		final TextField locationField = new TextField("");
		locationField.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Type in a desired location. Don't be shy! "));
		locationField.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		
		Label zoom = new Label("Zoom");
		zoom.getStyleClass().add("menulabel");
		Slider zoomSlider = new Slider(1, 21, 5);
		zoomSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Zoom of the map"));
		zoomSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		zoomSlider.setMaxWidth(200);
		zoomSlider.setMajorTickUnit(2);
		zoomSlider.setSnapToTicks(true);
		Label zoomLabel = new Label(String.format("%.0f", zoomSlider.getValue()));
		zoomLabel.getStyleClass().add("menulabel");
		SliderListener zListener = new SliderListener(zoomSlider, zoomLabel);
		
		Label mapSize = new Label("Map Size");
		mapSize.getStyleClass().add("menulabel");
		Slider mapSizeSlider = new Slider(Configuration.MAP_SIZE_MIN, Configuration.MAP_SIZE_MAX, Configuration.MAP_SIZE_MIN);
		mapSizeSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines width & height of the map"));
		mapSizeSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		mapSizeSlider.setMaxWidth(200);
		mapSizeSlider.setMajorTickUnit(2);
		mapSizeSlider.setMinorTickCount(2);
		mapSizeSlider.setSnapToTicks(true);
		Label mapSizeLabel = new Label(String.format("%.0f", mapSizeSlider.getValue()));
		mapSizeLabel.getStyleClass().add("menulabel");
		SliderListener msListener = new SliderListener(mapSizeSlider, mapSizeLabel, true);
		
		Button generate = new Button("Generate");
		generate.getStyleClass().add("menubutton");
		generate.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Generate a map with given values"));
		generate.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		generate.setOnAction(new GenerateGoogleMapEvent(manager.getConfiguration(), locationField, msListener, zListener, gc, manager.getHoverText()));

		Label formattingHelper = new Label("             ");
		
		GridPane grid = new GridPane();
		grid.getStyleClass().add("grid");
		grid.add(location, 0, 0);
		grid.add(locationField, 1, 0);
		grid.add(zoom, 0, 1);
		grid.add(zoomSlider, 1, 1);
		grid.add(zoomLabel, 2, 1);
		grid.add(mapSize, 0, 2);
		grid.add(mapSizeSlider, 1, 2);
		grid.add(mapSizeLabel, 2, 2);
		grid.add(generate, 1, 3);
		grid.add(formattingHelper, 2, 3);
		
		HBox box = new HBox(20);
		box.setAlignment(Pos.CENTER);
		box.getChildren().addAll(grid, mapPreview);
		
		Button back = new Button("< Map Type");
		back.getStyleClass().add("menubutton");
		back.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Go back to map type selection"));
		back.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		back.setOnAction(new SwitchState(manager));
		
		Button next = new Button("Game Settings >");
		next.getStyleClass().add("menubutton");
		next.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Go to game settings"));
		next.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		next.setOnAction(new SwitchState(manager, new GameSettingsState(), true));
        		
		GridPane selection = new GridPane();
		selection.getStyleClass().add("grid");
		selection.add(back, 0, 0);
		selection.add(next, 6, 0);
		
		root = new BorderPane();
		root.setCenter(box);
		root.setBottom(selection);
		
		manager.getRoot().setCenter(root);
	}

	@Override
	public void exiting() {
		manager.getRoot().setCenter(null);
	}

	@Override
	public void concealing() {
		manager.getRoot().setCenter(null);
		manager.getConfiguration().generate = true;
	}

	@Override
	public void revealed() {
		manager.getTitleText().setText(title);
		manager.getRoot().setCenter(root);
	}
}
