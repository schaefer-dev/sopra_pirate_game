package view.gamestates;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import view.GUIController;
import view.events.HoverEvent;
import view.events.SwitchState;
import view.utility.Configuration;
import view.utility.GameState;
import view.utility.TeamSelectionWindow;

public class TeamSettingsState implements GameState {

	private GUIController manager;
	private VBox teamSelection;
	private BorderPane root;
	
	private Configuration config;
	private List<TeamSelectionWindow> teamWindows;
	private boolean removable;
	
	private String title = "Team Settings";
	private boolean oneTactic = false;
	
	@Override
	public void entered(final GUIController control) {
		manager = control;
		manager.getTitleText().setText(title);
		config = manager.getConfiguration();
		teamWindows = new ArrayList<TeamSelectionWindow>();
		
		teamSelection = new VBox();
		teamSelection.getStyleClass().add("vbox");
		
		
		if(config.getTeamCount() > Configuration.TEAM_COUNT_MIN){
			for(int i = 0; i < config.getTeamCount(); i++){
				TeamSelectionWindow tSelect = new TeamSelectionWindow(manager, this , false);
				teamWindows.add(tSelect);
				teamSelection.getChildren().add(tSelect.getRoot());
				tSelect.getRoot().getStyleClass().add("teamwindow");
			}
			removable = false;
		}
		else
			removable = true;
		
		final TeamSettingsState state = this;
		Button add = new Button("Add");
		add.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Add a new team to the game"));
		add.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		add.getStyleClass().add("teamselectionbutton");
		add.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				if(teamWindows.size() < 26){
					TeamSelectionWindow tSelect = new TeamSelectionWindow(manager, state, true);
					teamWindows.add(tSelect);
					teamSelection.getChildren().add(tSelect.getRoot());
					tSelect.getRoot().getStyleClass().add("teamwindow");
					
					if(oneTactic)
						tSelect.getTacticButton().setDisable(true);
				}
			}
		});
		
		ToggleButton tactic = new ToggleButton("One Tactic");
		tactic.getStyleClass().add("teamselectionbutton");
		tactic.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Play with one tactic for every team"));
		tactic.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		tactic.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0){
				if(!oneTactic){
					oneTactic = true;
					for(TeamSelectionWindow window : teamWindows)
						window.getTacticButton().setDisable(true);
				}
				else{
					oneTactic = false;
					for(TeamSelectionWindow window : teamWindows)
						window.getTacticButton().setDisable(false);
				}
			}
		});
				
		HBox top = new HBox(20);
		top.setAlignment(Pos.TOP_CENTER);
		top.getChildren().addAll(add, tactic);
		
		if(!removable)
			add.setDisable(true);
		
		ScrollPane teamWindow = new ScrollPane();
		teamWindow.setStyle("-fx-background-color: #F2F2F2");
		teamWindow.getStyleClass().add("teamselection");
		teamWindow.setContent(teamSelection);
		
		Button back = new Button("< Game Settings");
		back.getStyleClass().add("menubutton");
		back.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Go back to whereever you came from"));
		back.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		back.setOnAction(new SwitchState(manager));
		
		Button next = new Button("Set Sails >");
		next.getStyleClass().add("menubutton");
		next.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Start the game"));
		next.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		next.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				if(teamWindows.size() < 1){
					manager.getHoverText().setText("At least one captain has to be brave enough to encounter the dangerous seas !");
					return;
				}
				
				for(TeamSelectionWindow window: teamWindows){
					if(!oneTactic){
						if(window.getTactic() == null){
							manager.getHoverText().setText("Not every crew has a tactic.");
							return;
						}
					}
					if(window.getCaptainName() == null || window.getCaptainName().equals(window.DEFAULT_NAME)){
						manager.getHoverText().setText("Not every crew has captain. How will they survice the horrors of the ocean ?");
						return;
					}
				}
				
				if(oneTactic){
					final FileChooser fileChooser = new FileChooser();
					fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SHIP", "*.ship"));
					File tacticFile = fileChooser.showOpenDialog(control.getStage());
					
					if(tacticFile == null)
						return;
					
					config.getTactics().add(tacticFile.toString());
					for(TeamSelectionWindow window: teamWindows)
						config.getFinalCaptainNames().add(window.getCaptainName());
				}
				else{
					for(TeamSelectionWindow window: teamWindows){
						config.getTactics().add(window.getTactic());
						config.getFinalCaptainNames().add(window.getCaptainName());
					}
				}
				
				config.setTeamCount(teamWindows.size());
				manager.switchState(new LoadingState());
			}
		});
		
		GridPane selection = new GridPane();
		selection.getStyleClass().add("grid");
		selection.add(back, 0, 0);
		selection.add(next, 6, 0);
		
		root = new BorderPane();
		root.setTop(top);
		
		Label formattingFixer1 = new Label();
		formattingFixer1.getStyleClass().add("formattingfixer");
		Label formattingFixer2 = new Label();
		formattingFixer2.getStyleClass().add("formattingfixer");
		
		root.setRight(formattingFixer1);
		root.setLeft(formattingFixer2);
		root.setCenter(teamWindow);
		root.setBottom(selection);
		
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
	
	
	public List<TeamSelectionWindow> getTeamWindows(){
		return teamWindows;
	}
	
	public VBox getTeamSelections(){
		return teamSelection;
	}
}
