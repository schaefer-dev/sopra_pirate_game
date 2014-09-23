package GameStates;

import Events.HoverEvent;
import Events.SwitchState;
import Tests.GameState;
import Tests.StateManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainMenuState implements GameState {

	private StateManager manager;
	
	@Override
	public void Entered(StateManager root) {
		manager = root;
		manager.getTitleText().setText("Pirates of the S-aarrrr-ibean");
		
		VBox vBox = new VBox(20);
		vBox.setAlignment(Pos.TOP_CENTER);
		vBox.setPadding(new Insets(25,25,25,25));
		
		Button quickGame = new Button(" Quick Game ");
		quickGame.setOnMouseEntered(new HoverEvent(root.getHoverText(), "Start a new game immediately"));
		quickGame.setOnMouseExited(new HoverEvent(root.getHoverText(), ""));
		quickGame.setAlignment(Pos.CENTER);
				
		Button customGame = new Button("Custom Game");
		customGame.setAlignment(Pos.CENTER);
		customGame.setOnMouseEntered(new HoverEvent(root.getHoverText(), "Start a new game with custom settings"));
		customGame.setOnMouseExited(new HoverEvent(root.getHoverText(), ""));
		customGame.setOnAction(new SwitchState(manager, new CustomGameState()));
		
		Button team = new Button("Team");
		team.setAlignment(Pos.CENTER);
		team.setOnMouseEntered(new HoverEvent(root.getHoverText(), "Learn about the magnificent minds behind this game"));
		team.setOnMouseExited(new HoverEvent(root.getHoverText(), ""));
		team.setOnAction(new SwitchState(manager, new TeamInfoState()));
		
		Button settings = new Button("Settings");
		settings.setAlignment(Pos.CENTER);
		settings.setOnAction(new SwitchState(manager, new SettingsState()));
		
		vBox.getChildren().addAll(quickGame, customGame, settings, team);
		manager.getRoot().setCenter(vBox);
	}

	@Override
	public void Exiting() {
		manager.getRoot().setCenter(null);
	}

}
