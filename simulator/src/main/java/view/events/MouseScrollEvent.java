package view.events;

import view.utility.Camera;
import view.utility.Map;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.ScrollEvent;

public class MouseScrollEvent implements EventHandler<ScrollEvent> {

	private Camera cam;
	private Map map;
	private GraphicsContext gc;
	
	public MouseScrollEvent(Camera cam, Map map, GraphicsContext gc){
		this.cam = cam;
		this.map = map;
		this.gc = gc;
	}

	@Override
	public void handle(ScrollEvent event) {
		if(event.getDeltaY() > 0)
			cam.zoomIn(4);
		else
			cam.zoomOut(4);
		
		map.draw(gc);
	}
}
