package de.unisaarland.cs.st.pirates.group5.view.gamestates;

import de.unisaarland.cs.st.pirates.group5.view.GUIController;
import de.unisaarland.cs.st.pirates.group5.view.events.HoverEvent;
import de.unisaarland.cs.st.pirates.group5.view.events.SwitchState;
import de.unisaarland.cs.st.pirates.group5.view.utility.GameState;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainMenuState implements GameState {

	private GUIController manager;
	private VBox root;
	private String title = "Pirates of the S-aarrrr-ibean";
	
	@Override
	public void entered(GUIController control) {
		manager = control;
		manager.getTitleText().setText(title);
		manager.setConfiguration(manager.getRessources().getDefaultConfig());
		manager.resetRessources();
	
		Button quickGame = new Button(" Quick Game ");
		quickGame.getStyleClass().add("menubutton");
		quickGame.setOnMouseEntered(new HoverEvent(control.getHoverText(), "Start a new game immediately"));
		quickGame.setOnMouseExited(new HoverEvent(control.getHoverText(), ""));
		quickGame.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				manager.switchState(new LoadingState());
			}
		});
				
		Button customGame = new Button("Custom Game");
		customGame.getStyleClass().add("menubutton");
		customGame.setOnMouseEntered(new HoverEvent(control.getHoverText(), "Start a new game with custom settings"));
		customGame.setOnMouseExited(new HoverEvent(control.getHoverText(), ""));
		customGame.setOnAction(new SwitchState(manager, new CustomGameState(), false));
		
		Button team = new Button("Team");
		team.getStyleClass().add("menubutton");
		team.setOnMouseEntered(new HoverEvent(control.getHoverText(), "Learn about the magnificent minds behind this game"));
		team.setOnMouseExited(new HoverEvent(control.getHoverText(), ""));
		team.setOnAction(new SwitchState(manager, new TeamInfoState(), false));
		
		Button settings = new Button("Settings");
		settings.getStyleClass().add("menubutton");
		settings.setOnMouseEntered(new HoverEvent(control.getHoverText(), "Go to game settings"));
		settings.setOnMouseExited(new HoverEvent(control.getHoverText(), ""));
		settings.setOnAction(new SwitchState(manager, new SettingsState(), false));
		
		root = new VBox();
		root.getStyleClass().add("vbox");
		root.getChildren().addAll(quickGame, customGame, settings, team);
		
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
		manager.setConfiguration(manager.getRessources().getDefaultConfig());
	}

}
