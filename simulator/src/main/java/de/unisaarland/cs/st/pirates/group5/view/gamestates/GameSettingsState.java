package de.unisaarland.cs.st.pirates.group5.view.gamestates;

import de.unisaarland.cs.st.pirates.group5.view.GUIController;
import de.unisaarland.cs.st.pirates.group5.view.events.HoverEvent;
import de.unisaarland.cs.st.pirates.group5.view.events.SliderListener;
import de.unisaarland.cs.st.pirates.group5.view.events.SwitchState;
import de.unisaarland.cs.st.pirates.group5.view.utility.Configuration;
import de.unisaarland.cs.st.pirates.group5.view.utility.GameState;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class GameSettingsState implements GameState {

	private GUIController manager;
	private String title = "Game Settings";
	private BorderPane root;
	private SliderListener tdListener, scListener, kListener, sListener, rListener;

	@Override
	public void entered(GUIController control) {
		manager = control;
		manager.getTitleText().setText(title);
		Configuration map = manager.getConfiguration();

		Label treasureDensity = new Label("Treasure Density");
		treasureDensity.getStyleClass().add("menulabel");
		Slider treasureDensitySlider = new Slider(Configuration.TREASURE_DENSITY_MIN, Configuration.TREASURE_DENSITY_MAX, map.getTreasureDensity());
		treasureDensitySlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines the amount of treasure chests on the map"));
		treasureDensitySlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		treasureDensitySlider.setMaxWidth(200);
		treasureDensitySlider.setMajorTickUnit(4);
		treasureDensitySlider.setMinorTickCount(2);
		treasureDensitySlider.setSnapToTicks(true);
		Label treasureDensityLabel = new Label(String.format("%.0f", treasureDensitySlider.getValue()));
		treasureDensityLabel.getStyleClass().add("menulabel");
		tdListener = new SliderListener(treasureDensitySlider, treasureDensityLabel);
		
		Label supplyCount = new Label("Supply Density");
		supplyCount.getStyleClass().add("menulabel");
		Slider supplyCountSlider = new Slider(Configuration.SUPPLY_DENSITY_MIN, Configuration.SUPPLY_DENSITY_MAX, map.getSupplyDensity());
		supplyCountSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines the amount of supply on the map"));
		supplyCountSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		supplyCountSlider.setMaxWidth(200);
		supplyCountSlider.setMajorTickUnit(4);
		supplyCountSlider.setMinorTickCount(2);
		supplyCountSlider.setSnapToTicks(true);
		Label supplyCountLabel = new Label(String.format("%.0f", supplyCountSlider.getValue()));
		supplyCountLabel.getStyleClass().add("menulabel");
		scListener = new SliderListener(supplyCountSlider, supplyCountLabel);
		
		Label kraken = new Label("Kraken");
		kraken.getStyleClass().add("menulabel");
		Slider krakenSlider = new Slider(Configuration.KRAKEN_COUNT_MIN, Configuration.KRAKEN_COUNT_MAX, map.getKrakenCount());
		krakenSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines the amount of deadly kraken on the map"));
		krakenSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		krakenSlider.setMaxWidth(200);
		krakenSlider.setMajorTickUnit(4);
		krakenSlider.setMinorTickCount(2);
		krakenSlider.setSnapToTicks(true);
		Label krakenLabel = new Label(String.format("%.0f", krakenSlider.getValue()));
		krakenLabel.getStyleClass().add("menulabel");
		kListener = new SliderListener(krakenSlider, krakenLabel);
		
		Label ships = new Label("Ships");
		ships.getStyleClass().add("menulabel");
		Slider shipsSlider = new Slider(Configuration.SHIP_COUNT_MIN, Configuration.SHIP_COUNT_MAX, map.getShipCount());
		shipsSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines the amount of ships per team"));
		shipsSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		shipsSlider.setMaxWidth(200);
		shipsSlider.setMajorTickUnit(4);
		shipsSlider.setMinorTickCount(2);
		shipsSlider.setSnapToTicks(true);
		Label shipsLabel = new Label(String.format("%.0f", shipsSlider.getValue()));
		shipsLabel.getStyleClass().add("menulabel");
		sListener = new SliderListener(shipsSlider, shipsLabel);
		
		Label rounds = new Label("Rounds");
		rounds.getStyleClass().add("menulabel");
		Slider roundSlider = new Slider(Configuration.ROUNDS_MIN, Configuration.ROUNDS_MAX, map.getRounds());
		roundSlider.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Value determines the amount of played rounds"));
		roundSlider.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		roundSlider.setMaxWidth(200);
		roundSlider.setMajorTickUnit(4);
		roundSlider.setMinorTickCount(2);
		roundSlider.setSnapToTicks(true);
		Label roundsLabel = new Label(String.format("%.0f", roundSlider.getValue()));
		roundsLabel.getStyleClass().add("menulabel");
		rListener = new SliderListener(roundSlider, roundsLabel);
		
		Label formattingHelper = new Label("                  ");
		
		GridPane grid = new GridPane();
		grid.getStyleClass().add("grid");
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
		grid.add(formattingHelper, 3, 6);
		
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
