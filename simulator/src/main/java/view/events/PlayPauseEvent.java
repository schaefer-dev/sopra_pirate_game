package view.events;

import java.util.Timer;

import view.gamestates.InGameState;
import view.utility.Run;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class PlayPauseEvent implements EventHandler<ActionEvent> {

	private Timer timer;
	private Run run;
	
	public PlayPauseEvent(InGameState state, Timer timer) {
		this.timer = timer;
		this.run = new Run(state, timer);
	}
	
	@Override
	public void handle(ActionEvent arg0) {
		try{
			timer.schedule(run, 1000, 100);
		}
		catch(Exception e){}
        
	}
	
	
	public void close(){
		if(timer != null)
			timer.cancel();
	}

}
