package view.events;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import view.utility.Camera;
import view.utility.Field;
import view.utility.GameFlowControl;
import view.utility.Map;

public class MouseEvents {

	private GameFlowControl control;
	private Camera cam;
	private Map map;
	private GraphicsContext gc;
	private Label tip;
		
	private double previousX = 0;
	private double previousY = 0;
	private boolean paused = false;
	
	public MouseEvents(Camera cam, Map map, GraphicsContext gc, Label tip, GameFlowControl control){
		this.cam = cam;
		this.map = map;
		this.gc = gc;
		this.tip = tip;
		this.control = control;
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
				tip.setVisible(false);
				
				if(control.getCurrentSpeed() < 2 && !paused){
					control.pause();
					paused = true;
				}
					
			}
		});
		
		root.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				previousX = 0;
				previousY = 0;
				
				if(paused){
					control.play();
					paused = false;
				}
			}
		});
	}
	
	public void addMouseScrollEvent(Node root){
		root.setOnScroll(new EventHandler<ScrollEvent>() {

			@Override
			public void handle(ScrollEvent event) {
				int zoomMagnitude = cam.zoomLevelAbsolute() + 1;
				if(event.getDeltaY() > 0)
					cam.zoomIn(zoomMagnitude);
				else
					cam.zoomOut(zoomMagnitude);
				
				map.drawMap();
				tip.setVisible(false);
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
		        		
		        		double canvasWidth = gc.getCanvas().getWidth();
		        		double canvasHeight = gc.getCanvas().getHeight();
		        		//double actualCamHeight = (cam.height()/canvasWidth)*canvasHeight;
		        		
		    			double midX = (cam.width()/canvasWidth)*newX + cam.a;
		    			double midY = (cam.height()/canvasHeight)*(newY) + cam.c;
		    			midX = mod(midX, map.getWidth());
		    			midY = mod(midY, map.getHeight());

						if(event.getClickCount() == 2){		
			    			cam.setMid((int) midX, (int) midY);
			    			map.drawMap();
			            }
						else if(event.getClickCount() == 1 && event.isStillSincePress()){
		    				Field[][] fields = map.getMap();
		    				Field field = fields[(int)midX][(int)midY];
		    				map.drawMap();
							map.markField(field);
			
							tip.setText(field.giveTooltipText());
							
							if(newX + tip.getWidth() > canvasWidth)
								tip.setTranslateX(newX - tip.getWidth());
							else
								tip.setTranslateX(newX);
							
							if(newY + tip.getHeight() > canvasHeight)
								tip.setTranslateX(newY - tip.getHeight());
							else
								tip.setTranslateY(newY);
							
							tip.setVisible(true);
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
