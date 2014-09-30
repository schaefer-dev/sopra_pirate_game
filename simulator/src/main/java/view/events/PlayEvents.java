package view.events;

import java.io.IOException;

import controller.Simulator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class PlayEvents {
	
	//private boolean playing;
	
	public PlayEvents(Button play, final Simulator sim){
		
		play.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {

				try {
					sim.step();
				} 
				catch (IllegalStateException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*
				while(true){
					try {
						sim.step();
						Thread.sleep(1000);
					} 
					catch(Exception e) {}					
				}
				*/
			}
		});
	}
}
