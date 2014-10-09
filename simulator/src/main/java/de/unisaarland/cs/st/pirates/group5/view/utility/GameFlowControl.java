package de.unisaarland.cs.st.pirates.group5.view.utility;

import java.util.Timer;

import de.unisaarland.cs.st.pirates.group5.view.gamestates.InGameState;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GameFlowControl {

	private Timer timer;
	private int[] speeds = {1, 4, 10, 50, 100, 200, 500, 1000};
	private int currentSpeed;
	private int offset;
	private Label speed;
	
	private InGameState state;
	
	public GameFlowControl(final InGameState state, final Button play, final Button pause, final Button speedUp, final Button slowDown, final Label speed){
		this.currentSpeed = 4;
		this.offset = 1000;
		this.speed = speed;
		this.state = state;
		
		play.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				try{
					timer = new Timer(true);
					timer.schedule(new Run(state), offset, speeds[currentSpeed]);
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
						setLabel();
						timer.cancel();
						timer = new Timer(true);
						timer.schedule(new Run(state), 0, speeds[currentSpeed]);
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
						setLabel();
						timer.cancel();
						timer = new Timer(true);
						timer.schedule(new Run(state), 0, speeds[currentSpeed]);
						play.setVisible(false);
						pause.setVisible(true);
					}
				}
				catch(Exception e){}
			}
		});	
	}
	
	private void setLabel(){
		double i = 1d/(speeds[currentSpeed]/1000d);
		int j = (int) i;
		speed.setText(j + "x");
	}
	
	public void pause(){
		try {
			timer.cancel();
			offset = 0;
		} 
		catch (Exception e){}	
	}
	
	public void play(){
		try{
			timer = new Timer(true);
			timer.schedule(new Run(state), offset, speeds[currentSpeed]);
		}
		catch(Exception e){}
	}
	
	public void close(){
		if(timer != null)
			timer.cancel();
	}
	
	public int getCurrentSpeed(){
		return currentSpeed;
	}
}
