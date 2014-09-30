package view.utility;

import model.FieldType;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Map {

	private Camera cam;
	private GraphicsContext gc;
	private Field[][] map;
	
	private Ressources ressources;
	
	public Map(GraphicsContext gc, Ressources ressources){
		this.gc = gc;
		this.ressources = ressources;
	}
	
	public void initMap(Field[][] map, Camera cam){
		if(map == null || cam == null) throw new NullPointerException();
		
		this.cam = cam;
		this.map = map;
	}
	
	
	public Field[][] getMap(){
		return map;
	}
	
	public Ressources getRessources(){
		return ressources;
	}
	
	public void drawMap(){		
    	gc.setFill(Color.web("76A6A6"));
    	//gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    	gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    	
    	for(int y = 0; y < cam.height(); y++){
        	for(int x = 0; x < cam.width(); x++){
        		int xx = mod(x+cam.a, map.length);
        		int yy = mod(y+cam.c, map[0].length);
        		drawField(map[xx][yy], x, y);
        	}		
    	}
	}
	
	
	public void drawFieldSimple(Field field, int x, int y){
    	double scale = gc.getCanvas().getWidth()/cam.width();
    	
		Color color = null;
		switch(field.getFieldType()){
			case Water:
				color = Color.web("76A6A6");
				break;
			case Island:
				color = Color.web("FCDA69");
				break;
			case Base:
			case ProvisionIsland:
				break;
		}
		
		double step = ((y + cam.c) % 2 != 0) ? scale/2 : 0;
		
		gc.setFill(color);
		gc.fillRect(scale*x + step, scale*y, scale, scale);
		
		if(field.getX() == 0 && step == 0){
			gc.setStroke(Color.web("EDE1C9"));
			gc.setLineWidth(2);
			gc.strokeLine(scale*x + step, scale*y, scale*x + step, scale*y + scale);
		}
		
		if(field.getY() == 0 && x%2 == 0){
			gc.setStroke(Color.web("EDE1C9"));
			gc.setLineWidth(2);
			gc.strokeLine(scale*x + step, scale*y, scale*x + step + scale, scale*y);
		}	
	}
	
	
	private void drawField(Field field, int x, int y){		
    	double radius = gc.getCanvas().getWidth()/cam.width()/2;
		double stepX = ((y+cam.c)%2 == 0) ? radius : 0;
		double stepY = y*(radius/2);
		
		boolean left = false;
		if(field.getX() == 0 && stepX == 0)
			left = true;
		boolean top = false;
		if(field.getY() == 0 && x%2 != 0)
			top = true;
		
		drawHex(new Point2D(x*2*radius+stepX, y*2*radius - stepY), radius, field, left, top);
	}
	
	public void markField(Field field){		
		int x = mod(field.getX() - cam.a, map.length);
    	int y = mod(field.getY() - cam.c, map[0].length);
    	double radius = gc.getCanvas().getWidth()/cam.width()/2;
		double stepX = ((y+cam.c)%2 == 0) ? radius : 0;
		double stepY = y*(radius/2);
		
		drawMap();
		markHex(new Point2D(x*2*radius+stepX, y*2*radius - stepY), radius);
	}
	
	
	public void drawField(Field field){
		int x = mod(field.getX() - cam.a, map.length);
    	int y = mod(field.getY() - cam.c, map[0].length);
    	drawField(field, x, y);
	}
	
	public boolean isVisible(Field field){
		return cam.intersects(field.getX(), field.getY());
	}

	private void drawHex(Point2D mid, double radius, Field field, boolean left, boolean top){
		Point2D up = new Point2D(mid.getX(), mid.getY() - radius);
		Image image = field.getImage();
		
		if(field.getFieldType() == FieldType.Water){
			if(cam.zoomLevel() < 2)
				gc.drawImage(image, up.getX()-radius, up.getY(), 2*radius, 2*radius);
		}
		else
			gc.drawImage(image, up.getX()-radius, up.getY(), 2*radius, 2*radius);
		
		if(left){
			Point2D upL = new Point2D(mid.getX() - radius, mid.getY() - radius/2);
			Point2D downL = new Point2D(mid.getX() - radius, mid.getY() + radius/2);
			gc.setStroke(Color.web("EDE1C9"));
			gc.setLineWidth(2);
			gc.strokeLine(upL.getX(), upL.getY(), downL.getX(), downL.getY());
		}
		if(top){
			gc.setStroke(Color.web("EDE1C9"));
			gc.setLineWidth(2);
			gc.strokeLine(up.getX() - radius/2, up.getY(), up.getX() + radius/2, up.getY());
		}
	}
	
	private void markHex(Point2D mid, double radius){
		Point2D up = new Point2D(mid.getX(), mid.getY() - radius);
		Point2D down = new Point2D(mid.getX(), mid.getY() + radius);
		Point2D upL = new Point2D(mid.getX() - radius, mid.getY() - radius/2);
		Point2D upR = new Point2D(mid.getX() + radius, mid.getY() - radius/2);
		Point2D downL = new Point2D(mid.getX() - radius, mid.getY() + radius/2);
		Point2D downR = new Point2D(mid.getX() + radius, mid.getY() + radius/2);
		
		gc.setStroke(Color.web("B2E097"));
		gc.setLineWidth(1);
		gc.strokeLine(up.getX(), up.getY(), upR.getX(), upR.getY());
		gc.strokeLine(up.getX(), up.getY(), upL.getX(), upL.getY());
		gc.strokeLine(upL.getX(), upL.getY(), downL.getX(), downL.getY());
		gc.strokeLine(upR.getX(), upR.getY(), downR.getX(), downR.getY());
		gc.strokeLine(downL.getX(), downL.getY(), down.getX(), down.getY());
		gc.strokeLine(downR.getX(), downR.getY(), down.getX(), down.getY());
	}
	
	private int mod(int a, int b){
		return (a%b + b) % b;
	}
}
