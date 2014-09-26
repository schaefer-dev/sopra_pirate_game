package view.events;

import view.utility.Camera;
import view.utility.Map;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class MouseDragEvent implements EventHandler<MouseEvent> {
	
	private Camera cam;
	private Map map;
	private GraphicsContext gc;
	
	
	public MouseDragEvent(Camera cam, Map map, GraphicsContext gc){
		this.cam = cam;
		this.map = map;
		this.gc = gc;
	}
	
	@Override
	public void handle(MouseEvent event) {
		double newX = event.getSceneX();
		double newY = event.getSceneY();
		
		double midX = (map.getMap().length/gc.getCanvas().getWidth())*newX;
		double midY = (map.getMap()[0].length/gc.getCanvas().getHeight())*newY;
		cam.setMid((int) midX, (int) midY);

		map.draw(gc);
	}
}
