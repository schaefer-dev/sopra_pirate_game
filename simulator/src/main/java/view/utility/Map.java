package view.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import model.FieldType;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class Map {

	private Camera cam;
	private GraphicsContext gc;
	private Field[][] map;
	private int width;
	private int height;
	
	private Image image, image2;
	
	public Map(GraphicsContext gc){
		this.gc = gc;
		
    	try{
	    	File file = new File("src/main/ressources/hexagon.png");
	    	InputStream stream = new FileInputStream(file);
	    	image = new Image(stream);
	    	stream.close();
	    	
	    	File file2 = new File("src/main/ressources/hexagon2.png");
	    	InputStream stream2 = new FileInputStream(file2);
	    	image2 = new Image(stream2);
	    	stream2.close();
    	}
    	catch(Exception e){}
	}
	
	public void initMap(Field[][] map, Camera cam){
		if(map == null || cam == null) throw new NullPointerException();
		
		this.cam = cam;
		this.map = map;
		width = map.length;
		height = map[0].length;
	}
	
	
	public Field[][] getMap(){
		return map;
	}
	
	public void drawMap(){
		width  = Math.abs(cam.b - cam.a);
		height = Math.abs(cam.d - cam.c);
		
    	gc.setFill(Color.web("76A6A6"));
    	gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    	
    	for(int y = 0; y < height; y++){
        	for(int x = 0; x < width; x++){
        		int xx = mod(x+cam.a, map.length);
        		int yy = mod(y+cam.c, map[0].length);
        		drawField(map[xx][yy], x, y);
        	}		
    	}
	}
	
	
	private void drawFieldSimple(Field field, int x, int y){
		width  = Math.abs(cam.b - cam.a);
		height = Math.abs(cam.d - cam.c);
    	double scale = gc.getCanvas().getWidth()/width;
    	
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
		width  = Math.abs(cam.b - cam.a);
		height = Math.abs(cam.d - cam.c);
		
    	double radius = gc.getCanvas().getWidth()/width/2;
		double stepX = ((y+cam.c)%2 == 0) ? radius : 0;
		double stepY = y*(radius/2);
		
		boolean left = false;
		if(field.getX() == 0 && stepX == 0)
			left = true;
		boolean top = false;
		if(field.getY() == 0 && x%2 != 0)
			top = true;
		
		if(field.getFieldType() == FieldType.Island)
			drawHex(new Point2D(x*2*radius+stepX, y*2*radius - stepY), radius, image, left, top);
		else
			drawHex(new Point2D(x*2*radius+stepX, y*2*radius - stepY), radius, image2, left, top);
	}
	
	
	public void drawField(Field field){
		int x = mod(field.getX() - cam.a, map.length);
    	int y = mod(field.getY() - cam.c, map[0].length);
    	drawField(field, x, y);
	}
	
	public boolean isVisible(Field field){
		return cam.intersects(field.getX(), field.getY());
	}
	
	private int mod(int a, int b){
		return (a%b + b) % b;
	}
	
	private void drawHex(Point2D mid, double radius, Image image, boolean left, boolean top){
		
		Point2D up = new Point2D(mid.getX(), mid.getY() - radius);
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
	
	
	public void drawMapPixelwise(){
		width  = Math.abs(cam.b - cam.a);
		height = Math.abs(cam.d - cam.c);
    	
    	double scaleW = gc.getCanvas().getWidth()/width;
    	double scaleH = gc.getCanvas().getHeight()/height;
    	double scaleX = gc.getCanvas().getWidth()/map.length;
    	double scaleY = gc.getCanvas().getHeight()/map[0].length;
    	
    	PixelWriter pixi = gc.getPixelWriter();
    	gc.setFill(Color.web("76A6A6"));
    	gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    	
    	for(double y = 0; y < height; y += scaleH){
        	for(double x = 0; x < width; x += scaleW){
        		
        		double xx = x + cam.a;
        		double yy = y + cam.c;
        		
        		int getX = (int)(xx/scaleX);
        		int getY = (int)(yy/scaleY);
        		
        		Field field = map[getX][getY];
        		double step = ((y + cam.c) % 2 != 0) ? scaleW/2 : 0;
        		
        		if(field.getFieldType() == FieldType.Island){	
            		int textX = (int)(scaleW*x + step);
            		int textY = (int)(scaleH*y);
            		
            		pixi.setColor(textX, textY, Color.web("FCDA69"));
        		}
        	}		
    	}
	}
}
