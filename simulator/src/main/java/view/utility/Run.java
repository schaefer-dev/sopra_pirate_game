package view.utility;

import java.util.TimerTask;

import view.gamestates.InGameState;
import javafx.application.Platform;
import controller.Simulator;

public class Run extends TimerTask {

	private InGameState state;
	private Simulator sim;
	
	public Run(InGameState state){
		this.state =state;
		this.sim = state.getSimulator();
	}
	
	@Override
	public void run() {
		Platform.runLater(new Runnable() {
            public void run() {
        		try{
        			if(state.getRound() < state.getMaxRounds())
        				sim.step();
        		}
        		catch(Exception e){
        			e.printStackTrace();
        		}
            }
        });
	}
}
