package GameStates;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import Events.GenerateEvent;
import Events.HoverEvent;
import Events.SliderListener;
import Events.SwitchState;
import Tests.GameState;
import Tests.SimulatorSettings;
import Tests.GUIController;

public class GeneratorState implements GameState {

	private GUIController manager;
	private BorderPane root;
	
	@Override
	public void Entered(GUIController control) {
		manager = control;
		manager.getTitleText().setText("Custom Map");
	
		Label mapSize = new Label("Map Size");
		Slider mapSizeSlider = new Slider(SimulatorSettings.MAP_SIZE_MIN, SimulatorSettings.MAP_SIZE_MAX, SimulatorSettings.MAP_SIZE_MIN);
		mapSizeSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines width & height of the map"));
		mapSizeSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		mapSizeSlider.setMaxWidth(200);
		mapSizeSlider.setMajorTickUnit(2);
		mapSizeSlider.setMinorTickCount(2);
		mapSizeSlider.setSnapToTicks(true);
		Label mapSizeLabel = new Label(String.format("%.0f", mapSizeSlider.getValue()));
		SliderListener msListener = new SliderListener(mapSizeSlider, mapSizeLabel);
		
		Label islandCount = new Label("Island Count");
		Slider islandCountSlider = new Slider(SimulatorSettings.ISLAND_COUNT_MIN, SimulatorSettings.ISLAND_COUNT_MAX, SimulatorSettings.ISLAND_COUNT_MIN);
		islandCountSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Quantity of the islands"));
		islandCountSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		islandCountSlider.setMaxWidth(200);
		islandCountSlider.setMajorTickUnit(2);
		islandCountSlider.setSnapToTicks(true);
		Label islandCountLabel = new Label(String.format("%.0f", islandCountSlider.getValue()));
		SliderListener icListener = new SliderListener(islandCountSlider, islandCountLabel);
		
		Label islandSize = new Label("Island Size");
		Slider islandSizeSlider = new Slider(SimulatorSettings.ISLAND_SIZE_MIN, SimulatorSettings.ISLAND_SIZE_MAX, SimulatorSettings.ISLAND_SIZE_MIN);
		islandSizeSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Average size of the islands"));
		islandSizeSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		islandSizeSlider.setMaxWidth(200);
		islandSizeSlider.setMajorTickUnit(2);
		islandSizeSlider.setSnapToTicks(true);
		Label islandSizeLabel = new Label(String.format("%.0f", islandCountSlider.getValue()));
		SliderListener isListener = new SliderListener(islandSizeSlider, islandSizeLabel);
		
		Button generate = new Button("Generate");
		generate.getStyleClass().add("menubutton");
		generate.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Generate a map with given values"));
		generate.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		generate.setOnAction(new GenerateEvent(msListener, icListener, isListener));

		GridPane grid = new GridPane();
		grid.getStyleClass().add("grid");
		grid.add(mapSize, 0, 0);
		grid.add(mapSizeSlider, 1, 0);
		grid.add(mapSizeLabel, 2, 0);
		grid.add(islandCount, 0, 1);
		grid.add(islandCountSlider, 1, 1);
		grid.add(islandCountLabel, 2, 1);
		grid.add(islandSize, 0, 2);
		grid.add(islandSizeSlider, 1, 2);
		grid.add(islandSizeLabel, 2, 2);
		grid.add(generate, 1, 3);
		
		
		Button back = new Button("< Map Type");
		back.getStyleClass().add("menubutton");
		back.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Go back to map type selection"));
		back.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		back.setOnAction(new SwitchState(manager));
		
		Button next = new Button("Game Settings >");
		next.getStyleClass().add("menubutton");
		next.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Go to game settings"));
		next.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		next.setOnAction(new SwitchState(manager, new GameSettingsState()));
        		
		GridPane selection = new GridPane();
		selection.getStyleClass().add("grid");
		selection.add(back, 0, 0);
		selection.add(next, 6, 0);
		
		
		root = new BorderPane();
		root.setCenter(grid);
		root.setBottom(selection);
		
		manager.getRoot().setCenter(root);
	}

	@Override
	public void Exiting() {
		manager.getRoot().setCenter(null);
	}

	@Override
	public void Concealing() {
		manager.getRoot().setCenter(null);
	}

	@Override
	public void Revealed() {
		manager.getRoot().setCenter(root);
	}
}
