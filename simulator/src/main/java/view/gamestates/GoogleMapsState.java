package view.gamestates;

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
import view.events.GenerateGoogleMapEvent;
import view.events.HoverEvent;
import view.events.SliderListener;
import view.events.SwitchState;
import view.GUIController;
import view.utility.GameState;
import view.utility.MapPreview;
import view.utility.Configuration;

public class GoogleMapsState implements GameState {

	private GUIController manager;
	private BorderPane root;
	private String title = "Google Maps";
	
	@Override
	public void entered(GUIController control) {
		manager = control;
		manager.getTitleText().setText(title);	

		Canvas mapPreview = new Canvas(350, 350);
		GraphicsContext gc = mapPreview.getGraphicsContext2D();
		
		try{
			char[][] emptyMap = {{'.'}};
			MapPreview p = new MapPreview(emptyMap);
			p.draw(gc);
		}
		catch(Exception e){}
		
		Label location = new Label("Location");
		final TextField locationField = new TextField("");
		
		Label zoom = new Label("Zoom");
		Slider zoomSlider = new Slider(1, 21, 5);
		zoomSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Zoom of the map"));
		zoomSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		zoomSlider.setMaxWidth(200);
		zoomSlider.setMajorTickUnit(2);
		zoomSlider.setSnapToTicks(true);
		Label zoomLabel = new Label(String.format("%.0f", zoomSlider.getValue()));
		SliderListener zListener = new SliderListener(zoomSlider, zoomLabel);
		
		Label mapSize = new Label("Map Size");
		Slider mapSizeSlider = new Slider(Configuration.MAP_SIZE_MIN, Configuration.MAP_SIZE_MAX, Configuration.MAP_SIZE_MIN);
		mapSizeSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines width & height of the map"));
		mapSizeSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		mapSizeSlider.setMaxWidth(200);
		mapSizeSlider.setMajorTickUnit(2);
		mapSizeSlider.setMinorTickCount(2);
		mapSizeSlider.setSnapToTicks(true);
		Label mapSizeLabel = new Label(String.format("%.0f", mapSizeSlider.getValue()));
		SliderListener msListener = new SliderListener(mapSizeSlider, mapSizeLabel, true);
		
		Button generate = new Button("Generate");
		generate.getStyleClass().add("menubutton");
		generate.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Generate a map with given values"));
		generate.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		generate.setOnAction(new GenerateGoogleMapEvent(manager.getConfiguration(), locationField, msListener, zListener, gc, manager.getHoverText()));

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
		manager.getConfiguration().removeMap();
	}

	@Override
	public void concealing() {
		manager.getRoot().setCenter(null);
	}

	@Override
	public void revealed() {
		manager.getTitleText().setText(title);
		manager.getRoot().setCenter(root);
	}
}
