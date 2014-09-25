package GameStates;

import Events.HoverEvent;
import Events.SwitchState;
import Tests.GameState;
import Tests.GUIController;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainMenuState implements GameState {

	private GUIController manager;
	private VBox root;
	
	@Override
	public void Entered(GUIController control) {
		manager = control;
		manager.getTitleText().setText("Pirates of the S-aarrrr-ibean");
	
		Button quickGame = new Button(" Quick Game ");
		quickGame.getStyleClass().add("menubutton");
		quickGame.setOnMouseEntered(new HoverEvent(control.getHoverText(), "Start a new game immediately"));
		quickGame.setOnMouseExited(new HoverEvent(control.getHoverText(), ""));
		quickGame.setOnAction(new SwitchState(manager, new LoadingState()));
				
		Button customGame = new Button("Custom Game");
		customGame.getStyleClass().add("menubutton");
		customGame.setOnMouseEntered(new HoverEvent(control.getHoverText(), "Start a new game with custom settings"));
		customGame.setOnMouseExited(new HoverEvent(control.getHoverText(), ""));
		customGame.setOnAction(new SwitchState(manager, new CustomGameState()));
		
		Button team = new Button("Team");
		team.getStyleClass().add("menubutton");
		team.setOnMouseEntered(new HoverEvent(control.getHoverText(), "Learn about the magnificent minds behind this game"));
		team.setOnMouseExited(new HoverEvent(control.getHoverText(), ""));
		team.setOnAction(new SwitchState(manager, new TeamInfoState()));
		
		Button settings = new Button("Settings");
		settings.getStyleClass().add("menubutton");
		settings.setOnAction(new SwitchState(manager, new SettingsState()));
		
		root = new VBox();
		root.getStyleClass().add("vbox");
		root.getChildren().addAll(quickGame, customGame, settings, team);
		
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
