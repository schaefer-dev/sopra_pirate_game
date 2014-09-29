package view.events;

import view.utility.GameState;
import view.GUIController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SwitchState implements EventHandler<ActionEvent> {

	private GUIController manager;
	private GameState state;
	private boolean conditioned;
	
	public SwitchState(GUIController manager, GameState state, boolean conditioned){
		this.manager = manager;
		this.state = state;
		this.conditioned = conditioned;
	}
	
	public SwitchState(GUIController manager){
		this.manager = manager;
	}
	
	@Override
	public void handle(ActionEvent arg0) {
		if(conditioned){
			if(manager.getConfiguration().getMap() == null){
				manager.getHoverText().setText("You need to generate a map to proceed");
				return;
			}
		}
		
		if(state == null)
			manager.removeState();
		else
			manager.addState(state);
	}
}
