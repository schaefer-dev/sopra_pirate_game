package view.gamestates;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import view.events.HoverEvent;
import view.events.SwitchState;
import view.utility.Configuration;
import view.utility.GameState;
import view.GUIController;

public class TeamSettingsState implements GameState {

	private final String DEFAULT_NAME = "Choose Your Captain";
	
	private VBox teamSelection;
	private GUIController manager;
	private Configuration config;
	private BorderPane root;
	private String title = "Team Settings";
	private List<ComboBox<String>> choosers;
	
	private Button newTeam;
	
	@Override
	public void entered(GUIController control) {
		manager = control;
		manager.getTitleText().setText(title);
		config = manager.getConfiguration();
		choosers = new ArrayList<ComboBox<String>>();
		
		teamSelection = new VBox();
		teamSelection.getStyleClass().add("vbox");
		
		if(config.getTeamCountMax() != 26){
			if(config.getTeamConfigurations() != null)
				teamSelection.getChildren().addAll(config.getTeamConfigurations());
			else{
				for(int i = 0; i < config.getTeamCountMax(); i++)
					teamSelection.getChildren().add(giveNewTeamNode(false));
			}
		}
		else{
			if(config.getTeamConfigurations() != null)
				teamSelection.getChildren().addAll(config.getTeamConfigurations());
			
			newTeam = new Button("+");
			newTeam.getStyleClass().add("selectionbutton");
			newTeam.setAlignment(Pos.BOTTOM_LEFT);
			newTeam.setOnAction(new EventHandler<ActionEvent>() {
	
				@Override
				public void handle(ActionEvent arg0) {
					ObservableList<Node> teams = teamSelection.getChildren();
					teams.add(teams.size() - 1, giveNewTeamNode(true));
					manager.getConfiguration().addTeam();
					
					if(teamSelection.getChildren().size() > config.getTeamCountMax())
						newTeam.setVisible(false);
				}
			});
			teamSelection.getChildren().add(newTeam);
		}
		
		ScrollPane teamWindow = new ScrollPane();
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
		next.setOnAction(new SwitchState(manager, new LoadingState(), false));
		
		GridPane selection = new GridPane();
		selection.getStyleClass().add("grid");
		selection.add(back, 0, 0);
		selection.add(next, 6, 0);
		
		root = new BorderPane();
		root.setCenter(teamWindow);
		root.setBottom(selection);
		
		manager.getRoot().setCenter(root);
	}

	@Override
	public void exiting() {
		manager.getRoot().setCenter(null);
		manager.getConfiguration().getTactics().clear();
		manager.getConfiguration().removeAllTeams();
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
	
	public HBox giveNewTeamNode(boolean removable){
		
		final HBox box = new HBox(10);
		ColorPicker picker = new ColorPicker();
		picker.setStyle("-fx-color-label-visible: false;");	//Didn't work directly in css file for unknown reason

		ObservableList<String> captains = FXCollections.observableArrayList(manager.getConfiguration().getCaptainNames());
		final ComboBox<String> captainChooser = new ComboBox<String>(captains);
		captainChooser.setValue(DEFAULT_NAME);
		choosers.add(captainChooser);
				
		
		captainChooser.valueProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldV, String newV){
				if(oldV == null || newV == null || newV.equals(DEFAULT_NAME) || oldV.equals("") || newV.equals(""))
					return;
				
				List<String> captainNames = config.getCaptainNames();

				captainNames.remove(newV);
				
				if(!oldV.equals(DEFAULT_NAME))
					captainNames.add(oldV);
				
				for(ComboBox<String> c: choosers){
					if(!c.equals(captainChooser)){
						String text = c.getValue();
						c.setItems(FXCollections.observableArrayList(captainNames));
						c.setValue(text);
					}	
				}
			}
		});
		
		final FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SHIP", "*.ship"));
		final Button openFile = new Button("Choose Tactic");
		openFile.getStyleClass().add("selectionbutton");
		openFile.setStyle("-fx-font-size: 15px;");
        openFile.setOnAction(new EventHandler<ActionEvent>(){
        	
            @Override
            public void handle(final ActionEvent e) {
                File file = fileChooser.showOpenDialog(manager.getStage());
                if(file != null){
                	config.getTactics().add(file.toString());
                	openFile.setText(file.getName());
                }	
            }
        });
        
        if(removable){
	        Button removeTeam = new Button("x");
	        removeTeam.setAlignment(Pos.TOP_CENTER);
	        removeTeam.getStyleClass().add("deletebutton");
	        removeTeam.setStyle("-fx-font-size: 15px;");
	        removeTeam.setOnAction(new EventHandler<ActionEvent>(){
	
				@Override
				public void handle(ActionEvent arg0){
					teamSelection.getChildren().remove(box);
					//config.getTeamConfigurations().remove(box);
					Node child = box.getChildren().get(1);
					config.removeTeam();
					
					if(child instanceof ComboBox<?>){
						ComboBox<?> cBox = (ComboBox<?>) child;
						String captainName = (String) cBox.getValue();
						manager.getConfiguration().getCaptainNames().add(captainName);
					}
					
					if(teamSelection.getChildren().size() <= config.getTeamCountMax())
						newTeam.setVisible(true);
				}
			});
			
			box.getChildren().addAll(picker, captainChooser, openFile, removeTeam);
        }
        else
        	box.getChildren().addAll(picker, captainChooser, openFile);
        	
		return box;
	}
}
