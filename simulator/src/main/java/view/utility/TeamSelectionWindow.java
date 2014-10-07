package view.utility;

import java.io.File;

import view.GUIController;
import view.gamestates.TeamSettingsState2;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

public class TeamSelectionWindow {
	
	public final String DEFAULT_NAME = "Your Captain";
	
	private HBox box;
	private Rectangle color;
	private ComboBox<String> captainChooser;
	private Button openFile;
	private Button removeTeam;
	
	private Configuration config;
	private String currentValue;
	private String tactic;
	
	public TeamSelectionWindow(final GUIController manager, final TeamSettingsState2 state, boolean removable){
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
		openFile.getStyleClass().add("selectionbutton");
		openFile.setStyle("-fx-font-size: 20px;");
        openFile.setOnAction(new EventHandler<ActionEvent>(){
        	
            @Override
            public void handle(final ActionEvent e) {
                File file = fileChooser.showOpenDialog(manager.getStage());
                if(file != null){
                	tactic = file.toString();
                	openFile.setText(file.getName());
                }	
            }
        });
        
        final TeamSelectionWindow wind = this;
        removeTeam = new Button("x");
        removeTeam.setAlignment(Pos.TOP_CENTER);
        removeTeam.getStyleClass().add("deletebutton");
        removeTeam.setStyle("-fx-font-size: 20px;");
        removeTeam.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				state.getTeamWindows().remove(wind);
				state.getTeamSelections().getChildren().remove(getRoot());
			}
		});
        
        
        if(!removable)
        	removeTeam.setVisible(false);

        box = new HBox(20);
        box.getChildren().addAll(color, captainChooser, openFile, removeTeam);
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
