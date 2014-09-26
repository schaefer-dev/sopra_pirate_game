package view.utility;

import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;

public abstract class SelectionWindow {
	
	protected VBox root;
	
	public Node get(){
		return root;
	}
	
	public abstract void draw(GraphicsContext preview);
	
	@Override
	public abstract String toString();	
}
