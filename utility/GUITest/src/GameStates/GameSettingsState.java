package GameStates;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import Events.HoverEvent;
import Events.SliderListener;
import Events.SwitchState;
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
		treasureCountSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines the amount of treasures on the map"));
		treasureCountSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		treasureCountSlider.setMaxWidth(200);
		treasureCountSlider.setMajorTickUnit(4);
		treasureCountSlider.setMinorTickCount(2);
		treasureCountSlider.setSnapToTicks(true);
		Label treasureCountLabel = new Label(String.format("%.0f", treasureCountSlider.getValue()));
		SliderListener tcListener = new SliderListener(treasureCountSlider, treasureCountLabel);
		
		Label treasureDensity = new Label("Treasure Density");
		Slider treasureDensitySlider = new Slider(10, 198, 10);
		treasureDensitySlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines the amount of gold per treasure chest"));
		treasureDensitySlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		treasureDensitySlider.setMaxWidth(200);
		treasureDensitySlider.setMajorTickUnit(4);
		treasureDensitySlider.setMinorTickCount(2);
		treasureDensitySlider.setSnapToTicks(true);
		Label treasureDensityLabel = new Label(String.format("%.0f", treasureDensitySlider.getValue()));
		SliderListener tdListener = new SliderListener(treasureDensitySlider, treasureDensityLabel);
		
		Label supplyCount = new Label("Supplies");
		Slider supplyCountSlider = new Slider(10, 198, 10);
		supplyCountSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines the amount of supply on the map"));
		supplyCountSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		supplyCountSlider.setMaxWidth(200);
		supplyCountSlider.setMajorTickUnit(4);
		supplyCountSlider.setMinorTickCount(2);
		supplyCountSlider.setSnapToTicks(true);
		Label supplyCountLabel = new Label(String.format("%.0f", treasureDensitySlider.getValue()));
		SliderListener scListener = new SliderListener(supplyCountSlider, supplyCountLabel);
		
		Label kraken = new Label("Kraken");
		Slider krakenSlider = new Slider(10, 198, 10);
		krakenSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines the amount of deadly kraken on the map"));
		krakenSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		krakenSlider.setMaxWidth(200);
		krakenSlider.setMajorTickUnit(4);
		krakenSlider.setMinorTickCount(2);
		krakenSlider.setSnapToTicks(true);
		Label krakenLabel = new Label(String.format("%.0f", treasureDensitySlider.getValue()));
		SliderListener kListener = new SliderListener(krakenSlider, krakenLabel);
		
		Button back = new Button("< Map Settings");
		back.setAlignment(Pos.BOTTOM_LEFT);
		back.setOnMouseEntered(new HoverEvent(root.getHoverText(), "Go back to map type selection"));
		back.setOnMouseExited(new HoverEvent(root.getHoverText(), ""));
		back.setOnAction(new SwitchState(manager, new CustomGameState()));
		
		Button next = new Button("Team Settings >");
		next.setAlignment(Pos.BOTTOM_RIGHT);
		next.setOnMouseEntered(new HoverEvent(root.getHoverText(), "Go to team settings"));
		next.setOnMouseExited(new HoverEvent(root.getHoverText(), ""));
		next.setOnAction(new SwitchState(manager, new TeamSettingsState()));

		grid.add(treasureCount, 1, 0);
		grid.add(treasureCountSlider, 2, 0);
		grid.add(treasureCountLabel, 3, 0);
		grid.add(treasureDensity, 1, 1);
		grid.add(treasureDensitySlider, 2, 1);
		grid.add(treasureDensityLabel, 3, 1);
		grid.add(supplyCount, 1, 2);
		grid.add(supplyCountSlider, 2, 2);
		grid.add(supplyCountLabel, 3, 2);
		grid.add(kraken, 1, 3);
		grid.add(krakenSlider, 2, 3);
		grid.add(krakenLabel, 3, 3);
		grid.add(back, 0, 4);
		grid.add(next, 4, 4);

		manager.getRoot().setCenter(grid);
	}

	@Override
	public void Exiting() {
		manager.getRoot().setCenter(null);
	}
}
