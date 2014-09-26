package view.events;

import view.utility.GameState;
import view.GUIController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SwitchState implements EventHandler<ActionEvent> {

	private GUIController manager;
	private GameState state;
	
	public SwitchState(GUIController manager, GameState state){
		this.manager = manager;
		this.state = state;
	}
	
	public SwitchState(GUIController manager){
		this.manager = manager;
	}
	
	@Override
	public void handle(ActionEvent arg0) {
		if(state == null)
			manager.removeState();
		else
			manager.addState(state);
	}
}
