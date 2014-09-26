package view.gamestates;

import view.events.HoverEvent;
import view.events.SwitchState;
import view.utility.GameState;
import view.GUIController;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class CustomGameState implements GameState {

	private GUIController manager;
	private VBox root;
	private String title = "Custom Game";
	
	@Override
	public void entered(GUIController control) {
		manager = control;
		manager.getTitleText().setText(title);

		Button mapSelection = new Button("Map Selection");
		mapSelection.getStyleClass().add("menubutton");
		mapSelection.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Play on one of our preset maps"));
		mapSelection.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		mapSelection.setOnAction(new SwitchState(manager, new MapSelectionState()));

		Button mapGenerator = new Button("Map Generator");
		mapGenerator.getStyleClass().add("menubutton");
		mapGenerator.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Generate and play on your own map"));
		mapGenerator.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		mapGenerator.setOnAction(new SwitchState(manager, new GeneratorState()));
		
		Button googleMaps = new Button("Google Maps");
		googleMaps.getStyleClass().add("menubutton");
		googleMaps.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Start a new game with custom settings"));
		googleMaps.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		googleMaps.setOnAction(new SwitchState(manager, new GoogleMapsState()));
		
		Button back = new Button("< Back");
		back.getStyleClass().add("menubutton");
		back.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Start a new game with custom settings"));
		back.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		back.setOnAction(new SwitchState(manager));
		
		root = new VBox(20);
		root.getStyleClass().add("vbox");
		root.getChildren().addAll(mapSelection, mapGenerator, googleMaps, back);
		
		manager.getRoot().setCenter(root);
	}

	@Override
	public void exiting() {
		manager.getRoot().setCenter(null);
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
