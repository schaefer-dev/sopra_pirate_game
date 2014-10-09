package view.gamestates;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.events.HoverEvent;
import view.events.MusicSliderListener;
import view.events.SwitchState;
import view.utility.GameState;
import view.utility.Resolution;
import view.GUIController;

public class SettingsState implements GameState {	
	
	private GUIController manager;
	private GridPane root;
	private String title = "Settings";
	
	@Override
	public void entered(GUIController control) {
		manager = control;
		manager.getTitleText().setText(title);
		
		ObservableList<Resolution> supportedResolutions = FXCollections.observableArrayList(Resolution.FULLSCREEN, Resolution.SD);
		if(Screen.getPrimary().getVisualBounds().getMaxY() > 700)
			supportedResolutions.add(Resolution.HD);
		if(Screen.getPrimary().getVisualBounds().getMaxY() >= 1040)
			supportedResolutions.add(Resolution.FHD);
		
		Label resolutionLabel = new Label("Resolution");
		resolutionLabel.getStyleClass().add("menulabel");
		final ComboBox<Resolution> resolutionBox = new ComboBox<Resolution>(supportedResolutions);
		resolutionBox.setValue(manager.getResolution());
		addResolutionListener(resolutionBox);
		
		Button back = new Button("Back");
		back.getStyleClass().add("menulabel");
		back.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Go back to main menu"));
		back.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		back.setOnAction(new SwitchState(manager));
		Slider volume = new Slider(0, 100, manager.getPlayer().getVolume());
		volume.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Average size of the islands"));
		volume.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		volume.setMaxWidth(100);
		volume.setMajorTickUnit(2);
		volume.setSnapToTicks(true);
		Label volumeLabel = new Label(String.format("%.0f", manager.getPlayer().getVolume()*100));
		volumeLabel.getStyleClass().add("menulabel");
		
		volume.setValue(Integer.parseInt(volumeLabel.getText()));
		Label volumeText = new Label("Volume");
		volumeText.getStyleClass().add("menulabel");
		new MusicSliderListener(volume, volumeLabel, manager.getPlayer());
		
		root = new GridPane();
		root.getStyleClass().add("grid");
		root.add(resolutionLabel, 0, 1);
		root.add(resolutionBox, 1, 1);
		root.add(volumeText, 0, 0);
		root.add(volume, 1, 0);
		root.add(volumeLabel, 2, 0);
		root.add(new Text("            "), 1, 1);
		root.add(back, 1, 2);
		root.add(new Text("            "), 2, 2);
		
		manager.getRoot().setCenter(root);
	}

	@Override
	public void exiting() {
		manager.getRoot().setCenter(null);
	}
	
	@Override
	public void concealing() {
		exiting();
	}

	@Override
	public void revealed() {
		manager.getTitleText().setText(title);
		manager.getRoot().setCenter(root);
	}
	
	private void addResolutionListener(ComboBox<Resolution> resolution){
		resolution.valueProperty().addListener(new ChangeListener<Resolution>(){

			@Override
			public void changed(ObservableValue<? extends Resolution> arg0, Resolution arg1, Resolution arg2) {
				Stage stage = manager.getStage();
				double midX = Screen.getPrimary().getVisualBounds().getMaxX()/2;
				double midY = Screen.getPrimary().getVisualBounds().getMaxY()/2;
				
				double width = 854;
				double height = 480;
				
				switch(arg2){
					case FULLSCREEN:
						width  = Screen.getPrimary().getVisualBounds().getMaxX();
						height = Screen.getPrimary().getVisualBounds().getMaxY();
						stage.setFullScreen(true);
						manager.setResolution(Resolution.FULLSCREEN);
						break;
					case SD:
						width  = 854;
						height = 480;
						stage.setFullScreen(false);
						manager.setResolution(Resolution.SD);
						break;
					case HD:
						width  = 1280;
						height = 720;
						stage.setFullScreen(false);
						manager.setResolution(Resolution.HD);
						break;
					case FHD:
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
