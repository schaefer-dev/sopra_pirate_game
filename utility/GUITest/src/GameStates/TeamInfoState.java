package GameStates;

import Tests.GameState;
import Tests.StateManager;

public class TeamInfoState implements GameState {

	private StateManager manager;
	@Override
	public void Entered(StateManager root) {
		manager = root;
		manager.getTitleText().setText("Team Info");
		
	}

	@Override
	public void Exiting() {
		manager.getRoot().setCenter(null);
	}

}
