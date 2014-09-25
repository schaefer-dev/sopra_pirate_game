package Tests;

import java.io.FileNotFoundException;


public interface GameState {

	public void Entered(GUIController root) throws FileNotFoundException;
	public void Exiting();
	public void Concealing();
	public void Revealed();
}
