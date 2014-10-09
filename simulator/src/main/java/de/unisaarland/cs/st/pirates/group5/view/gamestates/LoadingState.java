package de.unisaarland.cs.st.pirates.group5.view.gamestates;


import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import de.unisaarland.cs.st.pirates.group5.controller.Simulator;
import de.unisaarland.cs.st.pirates.group5.view.GUIController;
import de.unisaarland.cs.st.pirates.group5.view.utility.Configuration;
import de.unisaarland.cs.st.pirates.group5.view.utility.GameState;

public class LoadingState implements GameState {

	private GUIController manager;
	
	private Random random;
	private List<String> shipFileList;
	private String mapFile;
	private Integer turns;
	
	@Override
	public void entered(GUIController control) {
		manager = control;
		manager.getTitleText().setText("");

		Configuration config = manager.getConfiguration();
		if(config.generate){
			config.placeObjectsOnMap();
			config.addTeamsToMap();
		}

		char[][] map = config.getMap();
		mapFile = writeToFile(map);
		
		random = new Random();
		shipFileList = manager.getConfiguration().getTactics();
		turns = manager.getConfiguration().getRounds();
		InGameState game = new InGameState(manager.getRessources(), turns, config, manager);
		initSimuator(game);
		manager.addState(game);
	}

	@Override
	public void exiting() {
		manager.getRoot().setCenter(null);
	}

	@Override
	public void concealing(){
		manager.getRoot().setCenter(null);
	}

	@Override
	public void revealed(){
		InGameState game = new InGameState(manager.getRessources(), turns, manager.getConfiguration(), manager);
		initSimuator(game);
		manager.addState(game);
	}

	
	private void initSimuator(InGameState game){
		try{
			String[] shipFiles = shipFileList.toArray(new String[shipFileList.size()]);
			Simulator sim = new Simulator(shipFiles, mapFile, random.nextInt(Integer.MAX_VALUE), null, turns, game);
			game.setSimulator(sim);
		}
		catch(Exception e){	
			Label errorIntro = new Label("Error");
			errorIntro.getStyleClass().add("errorlabel");
			Label error = new Label(e.getMessage());
			error.getStyleClass().add("menulabel");
			Label errorSmiley = new Label(":-(");
			errorSmiley.getStyleClass().add("errorlabel");
			
			VBox errorBox = new VBox();
			errorBox.getStyleClass().add("vbox");
			errorBox.getChildren().addAll(errorIntro, error, errorSmiley);
			
			Button back = new Button("Back To Menu");
			back.getStyleClass().add("menubutton");
			back.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
					manager.switchState(new MainMenuState());
				}
			});
			
			BorderPane.setAlignment(back, Pos.BOTTOM_CENTER);
			BorderPane pain = new BorderPane();
			pain.setCenter(errorBox);
			pain.setBottom(back);
			manager.getRoot().setCenter(pain);
			return;
		}
	}
	
	
	private String writeToFile(char[][] fields){
		Integer height = fields[0].length;
		Integer width = fields.length;
		
		try{
			PrintWriter out = new PrintWriter("map.map");
			out.write(width.toString() + '\n');
			out.write(height.toString() + '\n');
			
			for(int y = 0; y < height; y++){
				for(int x = 0; x < width; x++){
					String s = Character.toString(fields[x][y]);
					out.write(s + ' ');
				}	
				out.write('\n');
				
				if(y%2 == 0)
					out.write(' ');
			}
			out.close();
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("This will never happen");
		}
		
		return "map.map";
	}
}
