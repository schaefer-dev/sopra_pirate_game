package GameStates;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import Events.HoverEvent;
import Events.SwitchState;
import Tests.ButtonListCell;
import Tests.GameState;
import Tests.GUIController;

public class MapSelectionState implements GameState {

	private GUIController manager;
	private BorderPane root;
	
	@Override
	public void Entered(GUIController control) {
		manager = control;
		manager.getTitleText().setText("Map Selection");	
		
		ListView<String> list = new ListView<String>();

		/*
		list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {

			@Override
			public ListCell<String> call(ListView<String> arg0) {
				return new ButtonListCell();
			}
		});*/
		
		ObservableList<String> items =FXCollections.observableArrayList (
		   "Storming Sea", "Bermuda Triangle", "Somalia", "Deceiving Tides", "Riff Of No Return", "Eye Of The Storm",
		   "Call Of Booty", "Tortuga", "Cape Fear", "Golden Goal", "Jersey Shore");
		
		list.setItems(items);
		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        System.out.println(newValue);
		    }
		});
		
		
		VBox preview = new VBox();
		
		Button back = new Button("< Map Type");
		back.getStyleClass().add("menubutton");
		back.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Go back to map type selection"));
		back.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		back.setOnAction(new SwitchState(manager));
		
		Button next = new Button("Team Settings >");
		next.getStyleClass().add("menubutton");
		next.setOnMouseEntered(new HoverEvent(manager.getHoverText(), "Go to team settings"));
		next.setOnMouseExited(new HoverEvent(manager.getHoverText(), ""));
		next.setOnAction(new SwitchState(manager, new TeamSettingsState()));
		
		GridPane selection = new GridPane();
		selection.getStyleClass().add("grid");
		selection.add(back, 0, 0);
		selection.add(next, 6, 0);
		
		root = new BorderPane();
		root.setCenter(list);
		root.setBottom(selection);
		root.setRight(preview);
		
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
