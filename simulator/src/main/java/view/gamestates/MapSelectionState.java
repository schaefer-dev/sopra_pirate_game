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
	private ListView<SelectionWindow> list;
	private String title = "Map Selection";
	
	@Override
	public void entered(GUIController control) {
		manager = control;
		manager.getTitleText().setText(title);	
		
		list = new ListView<SelectionWindow>();
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
		next.setOnAction(new SwitchState(manager, new TeamSettingsState(), true));
		
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
		manager.getConfiguration().removeMap();
	}

	@Override
	public void concealing() {
		manager.getRoot().setCenter(null);
		int rounds = list.getSelectionModel().getSelectedItem().getRounds();
		manager.getConfiguration().setRounds(rounds);
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
			stormingSea = new SelectionPreview(manager.getConfiguration(), "Storming Sea", "1000", "200x200",2, "easy", "stuff", null);
			somalia 	= new SelectionPreview(manager.getConfiguration(), "Somalia", "1000", "100x100",2, "semi easy", 
					"'Piracy off the coast of Somalia\nhas been a threat to international\nshipping since the second phase\nof the Somali Civil Warn in the\nearly 21st century'", null);
			bermudaTri  = new SelectionPreview(manager.getConfiguration(), "Bermuda Triangle", "1000", "100x100",2, "semi easy", 
					"'The Bermuda Triangle is a region \nin the North Atlantic Ocean, where\na number of aircraft and ships are\nsaid to have disappeared under\nmysterious circumstances'", null);
			deceivingT  = new SelectionPreview(manager.getConfiguration(), "Deceiving Tides", "1000", "100x100",2, "hard", "This region looks peacefull at first.\nBut don't let the rum go to your head.\nIf you don't pay attention the tides\nwill push you into the small canals\nand getting out again is diffcult,\nespecially when you're drunk.", null);
			riffNoRet   = new SelectionPreview(manager.getConfiguration(), "Reef Of No Return", "1000", "100x100",2, "semi easy", "The „Reef Of No Return“\nis the ultimate test for the\nnavigation skills of every captain.\nOnly the most advanced Captains\nwill even try to sail\nin these shallow waters.\nAnd most of them never return …", null);
			tortuga     = new SelectionPreview(manager.getConfiguration(), "Tortuga", "1000", "50x50",2, "semi easy", "On the epitome of all pirate islands,\nclaimed by the English, French and Spanish,\npiracy flourishs like nowhere else.\nIf you have never been to Tortuga,\nyou cannot be called a pirate. ", null);
			eyeOfStorm  = new SelectionPreview(manager.getConfiguration(), "Eye Of The Storm", "1000", "150x150",2, "very hard", "stuff", null);
			capeFear    = new SelectionPreview(manager.getConfiguration(), "Cape Fear", "1000", "100x100",2, "suicidially hard", "Just the name of this beautiful costal region\ncan spread terror among any group of sailors.\nAnd not without reason,\nfor among the picuresque coral reefs\nlurks the archenemy of every saillor: the kraken.\nAnd it is hungry, very hungry indeed.", null);
			goldenGoal  = new SelectionPreview(manager.getConfiguration(), "Golden Goal", "1000", "100x100",2, "normal", "Two Teams and whoever\nscores first will win.\nThis map guarantees tension\nuntil the very end.", null);
			jerseyShore = new SelectionPreview(manager.getConfiguration(), "Jersey Shore", "1000", "100x100",2, "spoiled", "This is the retirement dream of every pirate.\nMany islands, rich treasures.\nThe land of milk and honey, err,\n sorry, gold and rum.", null);	

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
			return new SelectionPreview(manager.getConfiguration(), "Empty", "-", "-", 2, "-", "                                   ", null);
		}
		catch(Exception e){
			return null;
		}	
	}
}
