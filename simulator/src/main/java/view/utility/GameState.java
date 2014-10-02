package view.utility;

import view.GUIController;

public interface GameState {

	void entered(GUIController root);
	void exiting();
	void concealing();
	void revealed();
}
