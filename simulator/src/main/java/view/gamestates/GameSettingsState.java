package view.gamestates;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import view.events.HoverEvent;
import view.events.SliderListener;
import view.events.SwitchState;
import view.utility.GameState;
import view.utility.Configuration;
import view.GUIController;

public class GameSettingsState implements GameState {

	private GUIController manager;
	private String title = "Game Settings";
	private BorderPane root;
	private SliderListener tcListener, tdListener, scListener, kListener, sListener, rListener;

	@Override
	public void entered(GUIController control) {
		manager = control;
		manager.getTitleText().setText(title);
		Configuration map = manager.getConfiguration();

		Label treasureCount = new Label("Treasure Count");
		Slider treasureCountSlider = new Slider(Configuration.TREASURE_COUNT_MIN, Configuration.TREASURE_COUNT_MAX, map.getTreasureCount());
		treasureCountSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines the amount of gold per treasure chest"));
		treasureCountSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		treasureCountSlider.setMaxWidth(200);
		treasureCountSlider.setMajorTickUnit(4);
		treasureCountSlider.setMinorTickCount(2);
		treasureCountSlider.setSnapToTicks(true);
		Label treasureCountLabel = new Label(String.format("%.0f", treasureCountSlider.getValue()));
		tcListener = new SliderListener(treasureCountSlider, treasureCountLabel);
		
		Label treasureDensity = new Label("Treasure Density");
		Slider treasureDensitySlider = new Slider(Configuration.TREASURE_DENSITY_MIN, Configuration.TREASURE_DENSITY_MAX, map.getTreasureDensity());
		treasureDensitySlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines the amount of treasure chests on the map"));
		treasureDensitySlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		treasureDensitySlider.setMaxWidth(200);
		treasureDensitySlider.setMajorTickUnit(4);
		treasureDensitySlider.setMinorTickCount(2);
		treasureDensitySlider.setSnapToTicks(true);
		Label treasureDensityLabel = new Label(String.format("%.0f", treasureDensitySlider.getValue()));
		tdListener = new SliderListener(treasureDensitySlider, treasureDensityLabel);
		
		Label supplyCount = new Label("Supplies");
		Slider supplyCountSlider = new Slider(Configuration.TREASURE_COUNT_MIN, Configuration.TREASURE_COUNT_MAX, map.getSupplyDensity());
		supplyCountSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines the amount of supply on the map"));
		supplyCountSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		supplyCountSlider.setMaxWidth(200);
		supplyCountSlider.setMajorTickUnit(4);
		supplyCountSlider.setMinorTickCount(2);
		supplyCountSlider.setSnapToTicks(true);
		Label supplyCountLabel = new Label(String.format("%.0f", supplyCountSlider.getValue()));
		scListener = new SliderListener(supplyCountSlider, supplyCountLabel);
		
		Label kraken = new Label("Kraken");
		Slider krakenSlider = new Slider(Configuration.KRAKEN_COUNT_MIN, Configuration.KRAKEN_COUNT_MAX, map.getKrakenCount());
		krakenSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines the amount of deadly kraken on the map"));
		krakenSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		krakenSlider.setMaxWidth(200);
		krakenSlider.setMajorTickUnit(4);
		krakenSlider.setMinorTickCount(2);
		krakenSlider.setSnapToTicks(true);
		Label krakenLabel = new Label(String.format("%.0f", krakenSlider.getValue()));
		kListener = new SliderListener(krakenSlider, krakenLabel);
		
		Label ships = new Label("Ships");
		Slider shipsSlider = new Slider(Configuration.SHIP_COUNT_MIN, Configuration.SHIP_COUNT_MAX, map.getShipCount());
		shipsSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines the amount of ships per team"));
		shipsSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		shipsSlider.setMaxWidth(200);
		shipsSlider.setMajorTickUnit(4);
		shipsSlider.setMinorTickCount(2);
		shipsSlider.setSnapToTicks(true);
		Label shipsLabel = new Label(String.format("%.0f", shipsSlider.getValue()));
		sListener = new SliderListener(shipsSlider, shipsLabel);
		
		Label rounds = new Label("Rounds");
		Slider roundSlider = new Slider(Configuration.ROUNDS_MIN, Configuration.ROUNDS_MAX, map.getShipCount());
		roundSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines the amount of ships per team"));
		roundSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		roundSlider.setMaxWidth(200);
		roundSlider.setMajorTickUnit(4);
		roundSlider.setMinorTickCount(2);
		roundSlider.setSnapToTicks(true);
		Label roundsLabel = new Label(String.format("%.0f", shipsSlider.getValue()));
		rListener = new SliderListener(roundSlider, roundsLabel);
		
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
		grid.add(ships, 1, 4);
		grid.add(shipsSlider, 2, 4);
		grid.add(shipsLabel, 3, 4);
		grid.add(rounds, 1, 5);
		grid.add(roundSlider, 2, 5);
		grid.add(roundsLabel, 3, 5);
		
		
		Button back = new Button("< Map Settings");
		back.getStyleClass().add("menubutton");
		back.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Go back to map type selection"));
		back.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		back.setOnAction(new SwitchState(manager));
		
		Button next = new Button("Team Settings >");
		next.getStyleClass().add("menubutton");
		next.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Go to team settings"));
		next.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		next.setOnAction(new SwitchState(manager, new TeamSettingsState(), false));
		
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
	public void exiting() {
		try{
			Configuration map = manager.getConfiguration();
			map.setTreasureCount(tcListener.get());
			map.setTreasureDensity(tdListener.get());
			map.setSupplyDensity(scListener.get());
			map.setKrakenCount(kListener.get());
			map.setShipCount(sListener.get());
			map.setRounds(rListener.get());
		}
		catch(Exception e){
			throw new IllegalStateException("You have to call Entered() before Existing");
		}
		
		manager.getRoot().setCenter(null);
	}

	@Override
	public void concealing() {
		exiting();
	}

	@Override
	public void revealed() {
		manager.getTitleText().setText(title);
		manager.getRoot().setCenter(root);
	}
}
