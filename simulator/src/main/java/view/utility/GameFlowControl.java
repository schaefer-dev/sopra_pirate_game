package view.utility;

import java.util.Timer;

import controller.Simulator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class GameFlowControl {

	private Timer timer;
	private int[] speeds = {1, 4, 10, 50, 100, 200, 500, 1000};
	private int currentSpeed;
	private int offset;
	
	public GameFlowControl(final Simulator sim, final Button play, final Button pause, final Button speedUp, final Button slowDown){
		this.currentSpeed = 4;
		this.offset = 1000;
		
		play.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				try{
					timer = new Timer(true);
					timer.schedule(new Run(sim), offset, speeds[currentSpeed]);
					play.setVisible(false);
					pause.setVisible(true);
				}
				catch(Exception e){}
			}
		});
		
		pause.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					timer.cancel();
					offset = 0;
					play.setVisible(true);
					pause.setVisible(false);
				} 
				catch (Exception e){}	
			}
		});
		
		speedUp.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try{
					if(currentSpeed > 0){
						currentSpeed--;
						timer.cancel();
						timer = new Timer(true);
						timer.schedule(new Run(sim), 0, speeds[currentSpeed]);
						play.setVisible(false);
						pause.setVisible(true);
					}
				}
				catch(Exception e){}
			}
		});
		
		slowDown.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try{
					if(currentSpeed < speeds.length - 1){
						currentSpeed++;
						timer.cancel();
						timer = new Timer(true);
						timer.schedule(new Run(sim), 0, speeds[currentSpeed]);
						play.setVisible(false);
						pause.setVisible(true);
					}
				}
				catch(Exception e){}
			}
		});	
	}
	
	public void close(){
		if(timer != null)
			timer.cancel();
	}
}
