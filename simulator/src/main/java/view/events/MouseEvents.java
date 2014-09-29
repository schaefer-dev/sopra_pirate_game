package view.events;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import view.utility.Camera;
import view.utility.Field;
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
					if(event.getClickCount() > 0){
		        		double newX = event.getX();
		        		double newY = event.getY();
		        		
		    			double midX = (cam.width()/gc.getCanvas().getWidth())*newX + cam.a;
		    			double midY = (cam.height()/gc.getCanvas().getHeight())*newY + cam.c;
		    			//midY += midY*(gc.getCanvas().getWidth()/cam.width()/4);
							
		    			if(event.getClickCount() == 1){
		    				Field[][] fields = map.getMap();
		    				Field field = fields[(int) mod(midX, fields.length)][(int) mod(midY, fields[0].length)];
							map.markField(field);
		    			}
						else if(event.getClickCount() == 2){			    			
			    			cam.setMid((int) midX, (int) midY);
			    			map.drawMap();
			            }
					}
		        }
			}
		});
	}
	
	private double mod(double a, double b){
		return (a%b + b) % b;
	}
}
