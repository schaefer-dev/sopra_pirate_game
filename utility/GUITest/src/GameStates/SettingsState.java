package GameStates;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import Events.HoverEvent;
import Events.SwitchState;
import Tests.GameState;
import Tests.Resolution;
import Tests.StateManager;

public class SettingsState implements GameState {	
	
	private StateManager manager;
	
	@Override
	public void Entered(StateManager root) {
		manager = root;
		manager.getTitleText().setText("Settings");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		ObservableList<String> supportedResolutions = FXCollections.observableArrayList(Resolution.FULLSCREEN.toString(), Resolution.SD.toString());
		if(Screen.getPrimary().getVisualBounds().getMaxY() > 700)
			supportedResolutions.add(Resolution.HD.toString());
		if(Screen.getPrimary().getVisualBounds().getMaxY() > 1000)
			supportedResolutions.add(Resolution.FHD.toString());
		
		Label resolutionLabel = new Label("Resolution");
		final ComboBox<String> resolutionBox = new ComboBox<String>(supportedResolutions);
		
		Button back = new Button("< Back");
		back.setAlignment(Pos.BOTTOM_LEFT);
		back.setOnMouseEntered(new HoverEvent(root.getHoverText(), "Go back to main menu"));
		back.setOnMouseExited(new HoverEvent(root.getHoverText(), ""));
		back.setOnAction(new SwitchState(manager, new MainMenuState()));

		resolutionBox.setValue(manager.getResolution().toString());
		addResolutionListener(resolutionBox);
		
		grid.add(resolutionLabel, 0, 0);
		grid.add(resolutionBox, 1, 0);
		grid.add(back, 0, 2);
		
		manager.getRoot().setCenter(grid);
	}

	@Override
	public void Exiting() {
		manager.getRoot().setCenter(null);
	}
	
	private void addResolutionListener(ComboBox<String> resolution){
		resolution.valueProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				
				Stage stage = manager.getStage();
				double midX = Screen.getPrimary().getVisualBounds().getMaxX()/2;
				double midY = Screen.getPrimary().getVisualBounds().getMaxY()/2;
				
				double width = 854;
				double height = 480;
				
				switch(arg2){
					case "Auto (Fullscreen)":
						width  = Screen.getPrimary().getVisualBounds().getMaxX();
						height = Screen.getPrimary().getVisualBounds().getMaxY();
						stage.setFullScreen(true);
						manager.setResolution(Resolution.FULLSCREEN);
						break;
					case "480p (Window)":
						width  = 854;
						height = 480;
						stage.setFullScreen(false);
						manager.setResolution(Resolution.SD);
						break;
					case "720p (Window)":
						width  = 1280;
						height = 720;
						stage.setFullScreen(false);
						manager.setResolution(Resolution.HD);
						break;
					case "1080p (Window)":
						width  = 1980;
						height = 1080;
						stage.setFullScreen(false);
						manager.setResolution(Resolution.FHD);
						break;
				}
				
				stage.setX(midX - width/2);
				stage.setY(midY - height/2);
				stage.setHeight(height);
				stage.setWidth(width);
			}	
		});
	}
}
