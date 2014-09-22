package Events;

import Tests.GameState;
import Tests.StateManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SwitchState implements EventHandler<ActionEvent> {

	private StateManager manager;
	private GameState state;
	
	public SwitchState(StateManager manager, GameState state){
		this.manager = manager;
		this.state = state;
	}
	
	@Override
	public void handle(ActionEvent arg0) {
		manager.setState(state);
	}
}
