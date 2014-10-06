package view.gamestates;


import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

import controller.Simulator;
import view.utility.Configuration;
import view.utility.GameState;
import view.GUIController;

public class LoadingState implements GameState {

	private GUIController manager;
	
	@Override
	public void entered(GUIController control) {
		manager = control;
		manager.getTitleText().setText("");

		Configuration config = manager.getConfiguration();
		config.placeObjectsOnMap();
		config.addTeamsToMap();
		char[][] map = config.getMap();
		String mapFile = writeToFile(map);
		
		Random random = new Random();
		List<String> shipFileList = manager.getConfiguration().getTactics();
		String[] shipFiles = shipFileList.toArray(new String[shipFileList.size()]);
		Integer turns = manager.getConfiguration().getRounds();
		InGameState game = new InGameState(map, manager.getRessources(), turns);
		
		try{
			Simulator sim = new Simulator(shipFiles, mapFile, random.nextInt(Integer.MAX_VALUE), null, turns, game);
			game.setSimulator(sim);
		}
		catch(Exception e){	
			e.printStackTrace();
			manager.switchState(new MainMenuState());
			manager.getHoverText().setText("Something went terribly wrong");
			return;
		}
		
		manager.switchState(game);
	}

	@Override
	public void exiting() {
		manager.getRoot().setCenter(null);
	}

	@Override
	public void concealing(){}

	@Override
	public void revealed(){}

	private String writeToFile(char[][] fields){
		Integer height = fields.length;
		Integer width = fields[0].length;
		
		try{
			PrintWriter out = new PrintWriter("map.map");
			out.write(height.toString() + '\n');
			out.write(width.toString() + '\n');
			
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
			System.out.println("This will never happen");
		}
		
		return "map.map";
	}
}
