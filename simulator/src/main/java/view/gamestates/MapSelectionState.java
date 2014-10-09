package view.gamestates;

import java.io.File;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
		list.getSelectionModel().select(1);
		
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
		
		Label formattingFixer1 = new Label();
		formattingFixer1.getStyleClass().add("formattingfixer2");
		
		GridPane mid = new GridPane();
		mid.add(formattingFixer1, 0, 0);
		mid.add(list, 1, 0);
		mid.add(preview, 2, 0);
		root = new BorderPane();
		root.setCenter(mid);
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
		int rounds = list.getSelectionModel().getSelectedItem().getRounds();
		manager.getConfiguration().setRounds(rounds);
		manager.getConfiguration().generate = false;
	}

	@Override
	public void revealed() {
		manager.getTitleText().setText(title);	
		manager.getRoot().setCenter(root);
	}
	
	private ObservableList<SelectionWindow> giveElementList(GraphicsContext gc){
		SelectionWindow ownMap, CrazyHexagon, somalia, bermudaTri, deceivingT, riffNoRet, tortuga, BullsEye, capeFear, goldenGoal, jerseyShore;
		try{
			ownMap		= new SelectionFile(manager, gc);
			CrazyHexagon = new SelectionPreview(manager.getConfiguration(), "Crazy Hexagon", "10000", "200x200", 3, 
					"This test, set up to test important \nnavigation skills, has to be passed \nby every pirate apprentice before \nhe is allowed to command his own ship.\n\n", new File("src/main/ressources/maps/CrazyHexagon.map"));
			somalia 	= new SelectionPreview(manager.getConfiguration(), "Somalia", "10000", "170x170", 5, 
					"'Piracy off the coast of Somalia has \nbeen a threat to international shipping \nsince the second phase of the Somali \nCivil War in the early 21st century'\n\n", new File("src/main/ressources/maps/somalia.map"));
			bermudaTri  = new SelectionPreview(manager.getConfiguration(), "Bermuda Triangle", "1000", "100x100", 2, 
					"'The Bermuda Triangle is a region \nin the North Atlantic Ocean, where\na number of aircraft and ships are\nsaid to have disappeared under\nmysterious circumstances'\n", null);
			deceivingT  = new SelectionPreview(manager.getConfiguration(), "Deceiving Tides", "10000", "135x128", 6, "This region looks peacefull at first.\nBut don't let the rum go to your head.\nIf you don't pay attention the tides\nwill push you into the small canals\nand getting out again is diffcult,\nespecially when you're drunk.", new File("src/main/ressources/maps/deceivingTides.map"));
			riffNoRet   = new SelectionPreview(manager.getConfiguration(), "Reef Of No Return", "10000", "148x148", 4, 
					"The „Reef Of No Return“ is the \nultimate test for the navigation skills \nof every captain. Only the most advanced \nCaptains will even try to sail in these \nshallow waters. And most of them \nnever return …", new File("src/main/ressources/maps/ReefOfNoReturn.map"));
			tortuga     = new SelectionPreview(manager.getConfiguration(), "Tortuga", "1000", "50x50", 2,
					"On the epitome of all pirate islands,\nclaimed by the English, French and Spanish,\npiracy flourishs like nowhere else.\nIf you have never been to Tortuga,\nyou cannot be called a pirate.\n", null);
			BullsEye  = new SelectionPreview(manager.getConfiguration(), "Bulls Eye", "8000", "94x94", 2,
					"In this map two Teams struggle\nfor the pityfull gold ressources found \nin the Bulls eye in the middle of the \nmap.\n\n", new File("src/main/ressources/maps/bullseye.map"));
			capeFear    = new SelectionPreview(manager.getConfiguration(), "Cape Fear", "10000", "180x180", 3,
					"Just the name of this beautiful costal region\ncan spread terror among any group of sailors.\nAnd not without reason, for among the picuresque \ncoral reefs\nlurks the archenemy of every saillor: the kraken.\nAnd it is hungry, very hungry indeed.", new File("src/main/ressources/maps/CapeFear.map"));
			goldenGoal  = new SelectionPreview(manager.getConfiguration(), "Golden Goal", "1000", "100x100", 2,
					"Two Teams and whoever\nscores first will win.\nThis map guarantees tension\nuntil the very end.\n\n", null);
			jerseyShore = new SelectionPreview(manager.getConfiguration(), "Jersey Shore", "1000", "100x100", 2, 
					"This is the retirement dream of every pirate.\nMany islands, rich treasures.\nThe land of milk and honey, err,\n sorry, gold and rum.\n\n", null);	

		}
		catch(Exception e){
			ownMap = CrazyHexagon = somalia = bermudaTri = deceivingT = riffNoRet = tortuga = BullsEye = capeFear = goldenGoal = jerseyShore = null;
		}
		
		ObservableList<SelectionWindow> items = FXCollections.observableArrayList (
				ownMap, CrazyHexagon, somalia, bermudaTri, deceivingT, riffNoRet, tortuga, BullsEye, capeFear, goldenGoal, jerseyShore);
		
		return items;
	}
	
	private SelectionWindow giveEmptyElement(){
		try{
			return new SelectionPreview(manager.getConfiguration(), "Empty", "", "", 2, "", null);
		}
		catch(Exception e){
			return null;
		}	
	}
}
