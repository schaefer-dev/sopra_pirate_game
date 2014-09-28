package view.events;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import view.utility.Camera;
import view.utility.Map;

public class MouseEvents {

	private Camera cam;
	private Map map;
	private GraphicsContext gc;
		
	private double previousX = 0;
	private double previousY = 0;
	
	public MouseEvents(Camera cam, Map map, GraphicsContext gc){
		this.cam = cam;
		this.map = map;
		this.gc = gc;
	}
	
	public void addMouseDragEvent(Node root, final boolean invert){
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				double newX = event.getX();
				double newY = event.getY();
				
				if(previousX != 0){
					double diffX = newX - previousX;
					double diffY = newY - previousY;
					int inv = invert ? 1 : -1;
					
					if(diffX < 0)
						cam.moveLeft((int)diffX/2*inv);
					else
						cam.moveRight((int)-diffX/2*inv);
					if(diffY < 0)
						cam.moveUp((int)diffY/2*inv);
					else
						cam.moveDown((int)-diffY/2*inv);
				}
				
				previousX = newX;
				previousY = newY;
				
				map.drawMap();
			}
		});
		
		root.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				previousX = 0;
				previousY = 0;
			}
		});
	}
	
	public void addMouseScrollEvent(Node root){
		root.setOnScroll(new EventHandler<ScrollEvent>() {

			@Override
			public void handle(ScrollEvent event) {
				if(event.getDeltaY() > 0)
					cam.zoomIn(4);
				else
					cam.zoomOut(4);
				
				map.drawMap();
				
			}
		});
	}
	
	public void addMouseClickEvent(Node root){
		root.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY)){
		            if(event.getClickCount() == 2){
		        		double newX = event.getX();
		        		double newY = event.getY();
		        		
		    			double midX = (map.getMap().length/gc.getCanvas().getWidth())*newX;
		    			double midY = (map.getMap()[0].length/gc.getCanvas().getHeight())*newY;
		    			
		    			cam.setMid((int) midX, (int) midY);
		    			
		    			map.drawMap();
		            }
		        }
			}
		});
	}
	
}
