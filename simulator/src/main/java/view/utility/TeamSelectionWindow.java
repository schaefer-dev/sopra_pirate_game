package view.utility;

import java.io.File;

import view.GUIController;
import view.gamestates.TeamSettingsState;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

public class TeamSelectionWindow {
	
	public final String DEFAULT_NAME = "Your Captain";
	
	private GridPane box;
	private Rectangle color;
	private ComboBox<String> captainChooser;
	private Button openFile;
	private Button removeTeam;
	
	private Configuration config;
	private String currentValue;
	private String tactic;
	
	public TeamSelectionWindow(final GUIController manager, final TeamSettingsState state, boolean removable){
		this.config = manager.getConfiguration();
		this.currentValue = DEFAULT_NAME;
		
		color = new Rectangle();
		color.setWidth(15);
		color.setHeight(15);
		color.setFill(Color.WHITE);
		
		ObservableList<String> captains = FXCollections.observableArrayList(config.getCaptainNames());
		captainChooser = new ComboBox<String>(captains);
		captainChooser.setValue(DEFAULT_NAME);

		captainChooser.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if(newValue == null)
					captainChooser.setValue(oldValue);
				else if(oldValue != null && oldValue != DEFAULT_NAME)
					config.getCaptainNames().add(oldValue);
				
				config.getCurrentCaptainName().remove(currentValue);
				currentValue = newValue;
				
				int index = config.captainNames.indexOf(currentValue);
				if(index != -1){
					String colorString = MapPreview.teamColors[config.captainNames.indexOf(currentValue)];
					color.setFill(Color.web(colorString));
				}
				
				config.getCaptainNames().remove(newValue);
				config.getCurrentCaptainName().add(newValue);
			}
		});
		
		captainChooser.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				captainChooser.setItems(FXCollections.observableArrayList(config.getCaptainNames()));
				captainChooser.setValue(currentValue);
			}
		});
		
		final FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SHIP", "*.ship"));
		openFile = new Button("Choose Tactic");
		openFile.getStyleClass().add("teamwindowbutton");
        openFile.setOnAction(new EventHandler<ActionEvent>(){
        	
            @Override
            public void handle(final ActionEvent e) {
                File file = fileChooser.showOpenDialog(manager.getStage());
                if(file != null){
                	tactic = file.toString();
                	
                	String fileString = file.getName();
                	if(fileString.length() > 20){
                		fileString = fileString.substring(0, 9);
                		fileString += "...";
                	}
                	
                	openFile.setText(fileString);
                }	
            }
        });
        
        final TeamSelectionWindow wind = this;
        removeTeam = new Button("x");
        removeTeam.setAlignment(Pos.TOP_CENTER);
        removeTeam.getStyleClass().add("teamwindowbutton");
        removeTeam.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				state.getTeamWindows().remove(wind);
				state.getTeamSelections().getChildren().remove(getRoot());
				config.getCaptainNames().add(currentValue);
				config.getCurrentCaptainName().remove(currentValue);
			}
		});
        
        
        if(!removable)
        	removeTeam.setVisible(false);
        
        box = new GridPane();
        box.add(color, 0, 0);
        box.add(new Label("   "), 1, 0);
        box.add(captainChooser, 2, 0);
        box.add(new Label("      "), 3, 0);
        box.add(openFile, 4, 0);
        box.add(new Label("  "), 5, 0);
        box.add(removeTeam, 6, 0);
	}
	
	public Node getRoot(){
		return box;
	}
	
	public String getTactic(){
		return tactic;
	}
	
	public String getCaptainName(){
		return currentValue;
	}
	
	public Button getTacticButton(){
		return openFile;
	}
}
