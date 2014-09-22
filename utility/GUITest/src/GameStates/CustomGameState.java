package GameStates;

import Events.HoverEvent;
import Events.SwitchState;
import Tests.GameState;
import Tests.StateManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class CustomGameState implements GameState {

	private StateManager manager;
	
	@Override
	public void Entered(StateManager root) {
		manager = root;
		manager.getTitleText().setText("Custom Game");

		VBox vBox = new VBox(20);
		vBox.setAlignment(Pos.TOP_CENTER);
		vBox.setPadding(new Insets(25,25,25,25));
		
		Button mapSelection = new Button("Map Selection");
		mapSelection.setOnMouseEntered(new HoverEvent(root.getHoverText(), "Play on one of our preset maps"));
		mapSelection.setOnMouseExited(new HoverEvent(root.getHoverText(), ""));
		mapSelection.setAlignment(Pos.CENTER);
		//TODO: add gameState

		Button mapGenerator = new Button("Map Generator");
		mapGenerator.setAlignment(Pos.CENTER);
		mapGenerator.setOnMouseEntered(new HoverEvent(root.getHoverText(), "Generate and play on your own map"));
		mapGenerator.setOnMouseExited(new HoverEvent(root.getHoverText(), ""));
		mapGenerator.setOnAction(new SwitchState(manager, new GeneratorState()));
		
		Button googleMaps = new Button("Google Maps");
		googleMaps.setAlignment(Pos.CENTER);
		googleMaps.setOnMouseEntered(new HoverEvent(root.getHoverText(), "Start a new game with custom settings"));
		googleMaps.setOnMouseExited(new HoverEvent(root.getHoverText(), ""));
		//TODO: add gameState
		
		Button back = new Button("< Back");
		back.setAlignment(Pos.CENTER);
		back.setOnMouseEntered(new HoverEvent(root.getHoverText(), "Start a new game with custom settings"));
		back.setOnMouseExited(new HoverEvent(root.getHoverText(), ""));
		back.setOnAction(new SwitchState(manager, new MainMenuState()));
		

		vBox.getChildren().addAll(mapSelection, mapGenerator, googleMaps, back);
		manager.getRoot().setCenter(vBox);
	}

	@Override
	public void Exiting() {
		manager.getRoot().setCenter(null);
	}

}
