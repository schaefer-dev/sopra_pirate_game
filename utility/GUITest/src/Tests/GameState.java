package Tests;


public interface GameState {

	public void Entered(GUIController root);
	public void Exiting();
	public void Concealing();
	public void Revealed();
}
