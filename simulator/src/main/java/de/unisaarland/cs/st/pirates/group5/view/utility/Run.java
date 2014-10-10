package de.unisaarland.cs.st.pirates.group5.view.utility;

import java.util.TimerTask;

import javafx.application.Platform;
import de.unisaarland.cs.st.pirates.group5.controller.Simulator;
import de.unisaarland.cs.st.pirates.group5.view.gamestates.InGameState;

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
