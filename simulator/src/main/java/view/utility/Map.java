package view.utility;

import model.FieldType;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class Map {

	private Camera cam;
	private GraphicsContext gc;
	private Field[][] map;
	private Ressources ressources;
	private int mapWidth;
	private int mapHeight;
	
	private boolean init = false;
	
	public Map(GraphicsContext gc, Ressources ressources){
		this.gc = gc;
		this.ressources = ressources;
	}
	
	public void initMap(Field[][] map, Camera cam){
		if(map == null || cam == null) throw new NullPointerException();
		
		init = true;
		this.cam = cam;
		this.map = map;
		this.mapWidth = map.length;
		this.mapHeight = map[0].length;
	}
	
	public void drawMap(){		
		gc.setFill(Color.web("5fb8b8"));
    	gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    	
    	for(int y = 0; y < cam.width(); y++){
        	for(int x = 0; x < cam.width(); x++){
        		int xx = mod(x+cam.a, mapWidth);
        		int yy = mod(y+cam.c, mapHeight);
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
		double stepX = ((y+cam.c)%2 == 0) ? 0 : radius;
		double stepY = y*(radius/2);
		
		boolean left = false;
		if(field.getX() == 0 && stepX == 0)
			left = true;
		boolean top = false;
		if(field.getY() == 0 && x%2 != 0)
			top = true;
		
		drawHex(new Point2D(x*2*radius+stepX, y*2*radius - stepY), radius, field, left, top);
	}
	
	public void drawField(Field field){
		int x = mod(field.getX() - cam.a, mapWidth);
    	int y = mod(field.getY() - cam.c, mapHeight);
    	drawField(field, x, y);
    	
		if(cam.b - cam.a > map.length && cam.d - cam.c <= map.length)
				drawField(field, x + map.length, y);
		else if(cam.b - cam.a <= map.length && cam.d - cam.c > map[0].length)
				drawField(field, x, y + map[0].length);
		else if(cam.b - cam.a > map.length && cam.d - cam.c > map.length){
			drawField(field, x + map.length, y);
			drawField(field, x, y + map[0].length);
			drawField(field, x + map.length, y + map[0].length);
		}
	}
	
	public void markField(Field field){		
		markField(field, "B2E097");
	}
	
	public void markField(Field field, String color){
		int x = mod(field.getX() - cam.a, mapWidth);
    	int y = mod(field.getY() - cam.c, mapHeight);
    	double radius = gc.getCanvas().getWidth()/cam.width()/2;
		double stepX = ((y+cam.c)%2 == 0) ? 0 : radius;
		double stepY = y*(radius/2);
		
		markHex(new Point2D(x*2*radius+stepX, y*2*radius - stepY), radius, Color.web(color), false);	
	}
	
	public boolean isVisible(Field field){
		if(!init)
			return false;
		
		return cam.intersects(field.getX(), field.getY());
	}

	private void drawHex(Point2D mid, double radius, Field field, boolean left, boolean top){
		Point2D up = new Point2D(mid.getX(), mid.getY() - radius);
		
		for(Image img: field.getImages())
			gc.drawImage(img, up.getX()-radius, up.getY(), 2*radius, 2*radius);
		if(field.getShipImage() != null){
			Ship ship = field.getShip();
			if(cam.zoomLevelAbsolute() > 1 && field.getFieldType() == FieldType.Water)
				gc.drawImage(ressources.getWaterFarImage(), up.getX()-radius, up.getY(), 2*radius, 2*radius);
			
			if(ship != null)
				drawRotatedImage(field.getShipImage(), 60*ship.getDirection(), up.getX()-radius, up.getY(), 2*radius, 2*radius);
			else
				drawRotatedImage(field.getShipImage(), 0, up.getX()-radius, up.getY(), 2*radius, 2*radius);
		}
		else{
			if(cam.zoomLevelAbsolute() > 2 && field.sailedAway){
				if(field.getFieldType() == FieldType.Water)
					gc.drawImage(ressources.getWaterFarImage(), up.getX()-radius, up.getY(), 2*radius, 2*radius);
				else if(field.getFieldType() == FieldType.Base){
					if(cam.zoomLevelAbsolute() > 4)
						gc.drawImage(ressources.getWaterFarImage(), up.getX()-radius, up.getY(), 2*radius, 2*radius);
					else
						gc.drawImage(field.getFieldImage(), up.getX()-radius, up.getY(), 2*radius, 2*radius);
				}
			}
		}	
		
		if(field.getShip() != null && field.getShip().marked)
			markHex(mid, radius/1.4, field.getShip().getFleet().getColor(), false);
		
		if(cam.zoomLevelAbsolute() < 1)
			markHex(mid, radius, Color.BLACK, true);
		if(left){
			Point2D upL = new Point2D(mid.getX() - radius, mid.getY() - radius/2);
			Point2D downL = new Point2D(mid.getX() - radius, mid.getY() + radius/2);
			gc.setStroke(Color.web("EDE1C9"));
			gc.setLineWidth(Math.abs(cam.zoomLevelAbsolute() - 5) + 2);
			gc.strokeLine(upL.getX(), upL.getY(), downL.getX(), downL.getY());
		}
		if(top){
			gc.setStroke(Color.web("EDE1C9"));
			gc.setLineWidth(Math.abs(cam.zoomLevelAbsolute() - 5) + 2);
			gc.strokeLine(up.getX() - radius/2, up.getY(), up.getX() + radius/2, up.getY());
		}
	}
	
	private void markHex(Point2D mid, double radius, Color color, boolean half){
		Point2D up = new Point2D(mid.getX(), mid.getY() - radius);
		Point2D down = new Point2D(mid.getX(), mid.getY() + radius);
		Point2D upL = new Point2D(mid.getX() - radius, mid.getY() - radius/2);
		Point2D downL = new Point2D(mid.getX() - radius, mid.getY() + radius/2);
		
		gc.setStroke(color);
		gc.setLineWidth(1);
		gc.strokeLine(up.getX(), up.getY(), upL.getX(), upL.getY());
		gc.strokeLine(upL.getX(), upL.getY(), downL.getX(), downL.getY());
		gc.strokeLine(downL.getX(), downL.getY(), down.getX(), down.getY());
		
		if(!half){
			Point2D upR = new Point2D(mid.getX() + radius, mid.getY() - radius/2);
			Point2D downR = new Point2D(mid.getX() + radius, mid.getY() + radius/2);
			gc.strokeLine(up.getX(), up.getY(), upR.getX(), upR.getY());
			gc.strokeLine(upR.getX(), upR.getY(), downR.getX(), downR.getY());
			gc.strokeLine(downR.getX(), downR.getY(), down.getX(), down.getY());	
		}
	}
	
	
	public void addMapDetails(){
		for(int y = 0; y < mapHeight; y++){
			for(int x = 0; x < mapWidth; x++){
				Field field = map[x][y];
				
				if(field.getFieldType() == FieldType.Water){
					if(hasNeigbor(field, FieldType.Island) || hasNeigbor(field, FieldType.ProvisionIsland))
						field.setFieldImage(ressources.getShoreWaterImage());
				}
			}
		}
	}
	
	public Field[][] getMap(){
		return map;
	}
	
	public Camera getCam(){
		return cam;
	}
	
	public Ressources getRessources(){
		return ressources;
	}
	
	public int getWidth(){
		return mapWidth;
	}
	
	public int getHeight(){
		return mapHeight;
	}
	
	
	private boolean hasNeigbor(Field field, FieldType type){
		int x = field.getX();
		int y = field.getY();
		
		int xx, zz;
		if(y%2 != 0){
			xx = mod(x-1, mapWidth);
			zz = mod(x+1, mapWidth);
		}
		else{
			xx = mod(x+1, mapWidth);
			zz = mod(x-1, mapWidth);
		}
			
		int yy = mod(y-1, mapHeight);
		if(map[xx][yy].getFieldType() == type)
			return true;
		yy = mod(y, mapHeight);
		if(map[xx][yy].getFieldType() == type)
			return true;
		if(map[zz][yy].getFieldType() == type)
			return true;
		yy = mod(y+1, mapHeight);
		if(map[xx][yy].getFieldType() == type)
			return true;
		xx = mod(x, mapWidth);
		yy = mod(y-1, mapHeight);
		if(map[xx][yy].getFieldType() == type)
			return true;
		yy = mod(y+1, mapHeight);
		if(map[xx][yy].getFieldType() == type)
			return true;
		
		return false;
	}
	
    private void drawRotatedImage(Image image, double angle, double x, double y, double width, double height) {
        gc.save();
        rotateGC(angle, x + width/2, y + height/2);
        gc.drawImage(image, x, y, width, height);
        gc.restore();
    }
	
    private void rotateGC(double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }
	
	private int mod(int a, int b){
		return (a%b + b) % b;
	}
}
