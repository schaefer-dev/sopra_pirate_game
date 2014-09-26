package view.gamestates;

import view.utility.GameState;
import view.GUIController;

public class TeamInfoState implements GameState {

	private GUIController manager;
	@Override
	public void entered(GUIController root) {
		manager = root;
		manager.getTitleText().setText("Team Info");
		
	}

	@Override
	public void exiting() {
		manager.getRoot().setCenter(null);
	}

	@Override
	public void concealing() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void revealed() {
		// TODO Auto-generated method stub
		
	}

}
