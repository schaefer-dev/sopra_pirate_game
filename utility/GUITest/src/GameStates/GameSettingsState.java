package GameStates;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import Events.HoverEvent;
import Events.SliderListener;
import Events.SwitchState;
import Tests.GameState;
import Tests.SimulatorSettings;
import Tests.GUIController;

public class GameSettingsState implements GameState {

	private GUIController manager;
	private BorderPane root;
	private SliderListener tcListener, tdListener, scListener, kListener;

	@Override
	public void Entered(GUIController control) {
		manager = control;
		manager.getTitleText().setText("Game Settings");
		SimulatorSettings map = manager.getMap();

		Label treasureCount = new Label("Treasure Count");
		Slider treasureCountSlider = new Slider(SimulatorSettings.TREASURE_COUNT_MIN, SimulatorSettings.TREASURE_COUNT_MAX, map.getTreasureCount());
		treasureCountSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines the amount of gold per treasure chest"));
		treasureCountSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		treasureCountSlider.setMaxWidth(200);
		treasureCountSlider.setMajorTickUnit(4);
		treasureCountSlider.setMinorTickCount(2);
		treasureCountSlider.setSnapToTicks(true);
		Label treasureCountLabel = new Label(String.format("%.0f", treasureCountSlider.getValue()));
		tcListener = new SliderListener(treasureCountSlider, treasureCountLabel);
		
		Label treasureDensity = new Label("Treasure Density");
		Slider treasureDensitySlider = new Slider(SimulatorSettings.TREASURE_DENSITY_MIN, SimulatorSettings.TREASURE_DENSITY_MAX, map.getTreasureDensity());
		treasureDensitySlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines the amount of treasure chests on the map"));
		treasureDensitySlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		treasureDensitySlider.setMaxWidth(200);
		treasureDensitySlider.setMajorTickUnit(4);
		treasureDensitySlider.setMinorTickCount(2);
		treasureDensitySlider.setSnapToTicks(true);
		Label treasureDensityLabel = new Label(String.format("%.0f", treasureDensitySlider.getValue()));
		tdListener = new SliderListener(treasureDensitySlider, treasureDensityLabel);
		
		Label supplyCount = new Label("Supplies");
		Slider supplyCountSlider = new Slider(SimulatorSettings.TREASURE_COUNT_MIN, SimulatorSettings.TREASURE_COUNT_MAX, map.getSupplyDensity());
		supplyCountSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines the amount of supply on the map"));
		supplyCountSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		supplyCountSlider.setMaxWidth(200);
		supplyCountSlider.setMajorTickUnit(4);
		supplyCountSlider.setMinorTickCount(2);
		supplyCountSlider.setSnapToTicks(true);
		Label supplyCountLabel = new Label(String.format("%.0f", supplyCountSlider.getValue()));
		scListener = new SliderListener(supplyCountSlider, supplyCountLabel);
		
		Label kraken = new Label("Kraken");
		Slider krakenSlider = new Slider(SimulatorSettings.KRAKEN_COUNT_MIN, SimulatorSettings.KRAKEN_COUNT_MAX, map.getKrakenCount());
		krakenSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines the amount of deadly kraken on the map"));
		krakenSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		krakenSlider.setMaxWidth(200);
		krakenSlider.setMajorTickUnit(4);
		krakenSlider.setMinorTickCount(2);
		krakenSlider.setSnapToTicks(true);
		Label krakenLabel = new Label(String.format("%.0f", krakenSlider.getValue()));
		kListener = new SliderListener(krakenSlider, krakenLabel);
		
		GridPane grid = new GridPane();
		grid.getStyleClass().add("grid");
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
		
		
		Button back = new Button("< Map Settings");
		back.getStyleClass().add("menubutton");
		back.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Go back to map type selection"));
		back.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		back.setOnAction(new SwitchState(manager));
		
		Button next = new Button("Team Settings >");
		next.getStyleClass().add("menubutton");
		next.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Go to team settings"));
		next.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		next.setOnAction(new SwitchState(manager, new TeamSettingsState()));
		
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
		try{
			SimulatorSettings map = manager.getMap();
			map.setTreasureCount(tcListener.get());
			map.setTreasureDensity(tdListener.get());
			map.setSupplyDensity(scListener.get());
			map.setKrakenCount(kListener.get());
		}
		catch(Exception e){
			throw new IllegalStateException("You have to call Entered() before Existing");
		}
		
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
