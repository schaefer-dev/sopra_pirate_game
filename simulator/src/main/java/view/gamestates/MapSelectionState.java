package view.gamestates;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import view.events.HoverEvent;
import view.events.SwitchState;
import view.utility.GameState;
import view.GUIController;
import view.utility.SelectionFile;
import view.utility.SelectionPreview;
import view.utility.SelectionWindow;

public class MapSelectionState implements GameState {

	private GUIController manager;
	private BorderPane root;
	private String title = "Map Selection";
	
	@Override
	public void entered(GUIController control) {
		manager = control;
		manager.getTitleText().setText(title);	
		
		ListView<SelectionWindow> list = new ListView<SelectionWindow>();
		list.getStyleClass().add("maplist-view");
		
		Canvas mapPreview = new Canvas(250, 250);
		final GraphicsContext gc = mapPreview.getGraphicsContext2D();
		
		final VBox preview = new VBox(20);
		preview.getChildren().add(mapPreview);
		
		
		SelectionWindow empty = giveEmptyElement();
		preview.getChildren().add(empty.get());
		empty.draw(gc);
	
		list.setItems(giveElementList(gc));
		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SelectionWindow>() {

			@Override
			public void changed(ObservableValue<? extends SelectionWindow> arg0, SelectionWindow arg1, SelectionWindow arg2) {
				if(preview.getChildren().size() > 1)
					preview.getChildren().remove(preview.getChildren().size()-1);
					
				preview.getChildren().add(arg2.get());
				arg2.draw(gc);
			}
		});
		
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
	
	private ObservableList<SelectionWindow> giveElementList(GraphicsContext gc){
		SelectionWindow ownMap, stormingSea, somalia, bermudaTri, deceivingT, riffNoRet, tortuga, eyeOfStorm, capeFear, goldenGoal, jerseyShore;
		try{
			ownMap		= new SelectionFile(manager, gc);
			stormingSea = new SelectionPreview("Storming Sea", "200x200", "easy", "stuff", null);
			somalia 	= new SelectionPreview("Somalia", "100x100", "semi easy", 
					"'Piracy off the coast of Somalia \nhas been a threat to international \nshipping since the second phase \nof the Somali Civil Warn in the\n early 21st century'", null);
			bermudaTri  = new SelectionPreview("Bermuda Triangle", "100x100", "semi easy", 
					"'The Bermuda Triangle is a region \nin the North Atlantic Ocean, where \na number of aircraft and ships are \nsaid to have disappeared under \nmysterious circumstances'", null);
			deceivingT  = new SelectionPreview("Deceiving Tides", "100x100", "hard", "stuff", null);
			riffNoRet   = new SelectionPreview("Riff Of No Return", "100x100", "semi easy", "stuff", null);
			tortuga     = new SelectionPreview("Tortuga", "50x50", "semi easy", "stuff", null);
			eyeOfStorm  = new SelectionPreview("Eye Of The Storm", "150x150", "very hard", "stuff", null);
			capeFear    = new SelectionPreview("Cape Fear", "100x100", "suicidially hard", "stuff", null);
			goldenGoal  = new SelectionPreview("Golden Goal", "100x100", "normal", "stuff", null);
			jerseyShore = new SelectionPreview("Jersey Shore", "100x100", "spoiled", "stuff", null);	
		}
		catch(Exception e){
			ownMap = stormingSea = somalia = bermudaTri = deceivingT = riffNoRet = tortuga = eyeOfStorm = capeFear = goldenGoal = jerseyShore = null;
		}
		
		ObservableList<SelectionWindow> items = FXCollections.observableArrayList (
				ownMap, stormingSea, somalia, bermudaTri, deceivingT, riffNoRet, tortuga, eyeOfStorm, capeFear, goldenGoal, jerseyShore);
		
		return items;
	}
	
	private SelectionWindow giveEmptyElement(){
		try{
			return new SelectionPreview("Empty", "-", "-", "                                   ", null);
		}
		catch(Exception e){
			return null;
		}	
	}
}
