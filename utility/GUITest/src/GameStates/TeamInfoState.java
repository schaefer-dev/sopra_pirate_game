package GameStates;

import Tests.GameState;
import Tests.GUIController;

public class TeamInfoState implements GameState {

	private GUIController manager;
	@Override
	public void Entered(GUIController root) {
		manager = root;
		manager.getTitleText().setText("Team Info");
		
	}

	@Override
	public void Exiting() {
		manager.getRoot().setCenter(null);
	}

	@Override
	public void Concealing() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Revealed() {
		// TODO Auto-generated method stub
		
	}

}
