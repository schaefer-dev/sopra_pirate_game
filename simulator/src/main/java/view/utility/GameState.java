package view.utility;

import view.GUIController;

public interface GameState {

	public void entered(GUIController root);
	public void exiting();
	public void concealing();
	public void revealed();
}
