package view.events;

import java.util.Timer;

import view.gamestates.InGameState;
import view.utility.Run;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class PlayPauseEvent implements EventHandler<ActionEvent> {

	private InGameState state;
	private Timer timer;
	
	public PlayPauseEvent(InGameState state) {
		this.state = state;
	}
	
	@Override
	public void handle(ActionEvent arg0) {
		timer = new Timer();
        timer.schedule(new Run(state, timer), 1000, 100);
	}
	
	
	public void close(){
		timer.cancel();
	}

}
