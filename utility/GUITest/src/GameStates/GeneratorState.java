package GameStates;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import Events.GenerateEvent;
import Events.HoverEvent;
import Events.SliderListener;
import Events.SwitchState;
import Tests.GameState;
import Tests.StateManager;

public class GeneratorState implements GameState {

	private StateManager manager;
	
	@Override
	public void Entered(StateManager root) {
		manager = root;
		manager.getTitleText().setText("Custom Map");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Label mapSize = new Label("Map Size");
		Slider mapSizeSlider = new Slider(10, 198, 10);
		mapSizeSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines width & height of the map"));
		mapSizeSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		mapSizeSlider.setMaxWidth(200);
		mapSizeSlider.setMajorTickUnit(2);
		mapSizeSlider.setMinorTickCount(2);
		mapSizeSlider.setSnapToTicks(true);
		Label mapSizeLabel = new Label(String.format("%.0f", mapSizeSlider.getValue()));
		SliderListener msListener = new SliderListener(mapSizeSlider, mapSizeLabel);
		
		Label islandCount = new Label("Island Count");
		Slider islandCountSlider = new Slider(1, 80, 1);
		islandCountSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Quantity of the islands"));
		islandCountSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		islandCountSlider.setMaxWidth(200);
		islandCountSlider.setMajorTickUnit(2);
		islandCountSlider.setSnapToTicks(true);
		Label islandCountLabel = new Label(String.format("%.0f", islandCountSlider.getValue()));
		SliderListener icListener = new SliderListener(islandCountSlider, islandCountLabel);
		
		Label islandSize = new Label("Island Size");
		Slider islandSizeSlider = new Slider(2, 10, 2);
		islandSizeSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Average size of the islands"));
		islandSizeSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		islandSizeSlider.setMaxWidth(200);
		islandSizeSlider.setMajorTickUnit(2);
		islandSizeSlider.setSnapToTicks(true);
		Label islandSizeLabel = new Label(String.format("%.0f", islandCountSlider.getValue()));
		SliderListener isListener = new SliderListener(islandSizeSlider, islandSizeLabel);
		
		Button generate = new Button("Generate");
		generate.setAlignment(Pos.CENTER);
		generate.setOnMouseEntered(new HoverEvent(root.getHoverText(), "Generate a map with given values"));
		generate.setOnMouseExited(new HoverEvent(root.getHoverText(), ""));
		generate.setOnAction(new GenerateEvent(msListener, icListener, isListener));
		
		Button back = new Button("< Map Type");
		back.setAlignment(Pos.BOTTOM_LEFT);
		back.setOnMouseEntered(new HoverEvent(root.getHoverText(), "Go back to map type selection"));
		back.setOnMouseExited(new HoverEvent(root.getHoverText(), ""));
		back.setOnAction(new SwitchState(manager, new CustomGameState()));
		
		Button next = new Button("Game Settings >");
		next.setAlignment(Pos.BOTTOM_RIGHT);
		next.setOnMouseEntered(new HoverEvent(root.getHoverText(), "Go to game settings"));
		next.setOnMouseExited(new HoverEvent(root.getHoverText(), ""));
		next.setOnAction(new SwitchState(manager, new GameSettingsState()));
        		
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
		grid.add(back, 0, 7);
		grid.add(next, 4, 7);
		
		manager.getRoot().setCenter(grid);
	}

	@Override
	public void Exiting() {
		manager.getRoot().setCenter(null);
	}
}
