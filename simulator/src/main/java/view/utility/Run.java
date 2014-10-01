package view.utility;

import java.util.Timer;
import java.util.TimerTask;

import view.gamestates.InGameState;
import controller.Simulator;

public class Run extends TimerTask {

	private Simulator sim;
	private InGameState state;
	private Timer timer;
	
	public Run(InGameState state, Timer timer){
		this.state = state;
		this.sim = state.getSimulator();
	}
	
	@Override
	public void run() {
		try{
			if(state.getRound() < state.getMaxRounds())
				sim.step();
			else
				timer.cancel();
			
			
			System.out.println(state.getRound());
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
