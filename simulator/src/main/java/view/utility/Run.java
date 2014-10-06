package view.utility;

import java.util.TimerTask;

import javafx.application.Platform;
import controller.Simulator;

public class Run extends TimerTask {

	private Simulator sim;
	
	public Run(Simulator sim){
		this.sim = sim;
	}
	
	@Override
	public void run() {
		Platform.runLater(new Runnable() {
            public void run() {
        		try{
        			sim.step();
        		}
        		catch(Exception e){
        			e.printStackTrace();
        		}
            }
        });
	}
}
