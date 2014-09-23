package GameStates;

import Tests.GameState;
import Tests.StateManager;

public class MapSelectionState implements GameState {

	private StateManager manager;
	
	@Override
	public void Entered(StateManager root) {
		manager = root;
		manager.getTitleText().setText("Team Selection");

	}

	@Override
	public void Exiting() {
		manager.getRoot().setCenter(null);
	}
}
