package de.unisaarland.cs.st.pirates.group5.view.utility;

import de.unisaarland.cs.st.pirates.group5.view.GUIController;

public interface GameState {

	void entered(GUIController root);
	void exiting();
	void concealing();
	void revealed();
}
