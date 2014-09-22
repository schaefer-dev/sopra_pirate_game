package GameStates;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import Events.HoverEvent;
import Tests.GameState;
import Tests.StateManager;

public class GameSettingsState implements GameState {

	private StateManager manager;

	@Override
	public void Entered(StateManager root) {
		manager = root;
		manager.getTitleText().setText("Game Settings");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Label treasureCount = new Label("Treasure Count");
		Slider treasureCountSlider = new Slider(10, 198, 10);
		treasureCountSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines width & height of the map"));
		treasureCountSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		treasureCountSlider.setMaxWidth(200);
		treasureCountSlider.setMajorTickUnit(4);
		treasureCountSlider.setMinorTickCount(2);
		treasureCountSlider.setSnapToTicks(true);
		Label treasureCountLabel = new Label(String.format("%.0f", treasureCountSlider.getValue()));
		
		Label mapSize = new Label("Map Size");
		Slider mapSizeSlider = new Slider(10, 198, 10);
		mapSizeSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines width & height of the map"));
		mapSizeSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		mapSizeSlider.setMaxWidth(200);
		mapSizeSlider.setMajorTickUnit(4);
		mapSizeSlider.setMinorTickCount(2);
		mapSizeSlider.setSnapToTicks(true);
		Label mapSizeLabel = new Label(String.format("%.0f", mapSizeSlider.getValue()));
		

		grid.add(treasureCount, 0, 0);
		grid.add(treasureCountSlider, 1, 0);
		grid.add(treasureCountLabel, 2, 0);
		grid.add(mapSize, 0, 1);
		grid.add(mapSizeSlider, 1, 1);
		grid.add(mapSizeLabel, 2, 1);


		manager.getRoot().setCenter(grid);
	}

	@Override
	public void Exiting() {
		// TODO Auto-generated method stub
		
	}

}
