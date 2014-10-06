package view.utility;

import javafx.scene.canvas.Canvas;

public class Camera {

	private int minX;
	private int maxX;
	private int minY;
	private int maxY;

	public int a, b, c, d;	
	
	private int maxZoom, minZoom;
	private boolean scrollable;
	
	public Camera(Canvas canvas){
		minX = a = 0;
		minY = c = 0;
		maxX = b = (int) canvas.getWidth();
		maxY = d = (int) canvas.getHeight();
		minZoom = 20;
		maxZoom = maxX/2;
		scrollable = true;
	}
	
	public Camera(Field[][] map){
		minX = a = 0;
		minY = c = 0;
		maxX = b = (int) map.length;
		maxY = d = (int) map[0].length;
		minZoom = 20;
		maxZoom = maxX/2;
		scrollable = true;
	}
	
	public void setScollable(boolean scollable){
		this.scrollable = scollable;
	}
	
	public int width(){
		return Math.abs(b-a);
	}
	
	public int height(){
		return Math.abs(d-c);
	}
	
	public void setMid(int x, int y){
		int width  = b - a;
		int height = d - c;
		
		int aTemp = x - width/2;
		int bTemp = x + width/2;
		int cTemp = y - height/2;
		int dTemp = y + height/2;
		
		if(!scrollable){
			if(aTemp >= minX && bTemp <= maxX){
				a = aTemp;
				b = bTemp;
			}
			if(cTemp >= minY && dTemp <= maxY){
				c = cTemp;
				d = dTemp;
			}	
		}
		else{
			a = aTemp;
			b = bTemp;
			c = cTemp;
			d = dTemp;
		}
	}
	
	public void moveUp(int factor){
		if(!scrollable && c - factor <= minY){
			d = minY + (d - c);
			c = minY;
		}
		else{
			c -= factor;
			d -= factor;
		}
	}
	
	public void moveDown(int factor){
		if(!scrollable && d + factor >= maxY){
			c = maxY - (d - c);
			d = maxY;
		}
		else{
			c += factor;
			d += factor;
		}
	}
	
	public void moveLeft(int factor){
		if(!scrollable && a - factor <= minX){
			b = minX + (b - a);
			a = minX;
		}
		else{
			a -= factor;
			b -= factor;
		}
	}
	
	public void moveRight(int factor){		
		if(!scrollable && b + factor >= maxX){
			a = maxX - (b - a);
			b = maxX;
		}
		else{
			a += factor;
			b += factor;
		}
	}
	
	public void zoomIn(int factor){	
		if(b-a > minZoom){
			if(scrollable || a + factor < b){
				a += factor;
				b -= factor;
			}
			if(scrollable || c + factor < d){
				c += factor;
				d -= factor;
			}
		}
	}
	
	public void zoomOut(int factor){
		if(b - a < maxX + maxZoom){
			if(scrollable){
				a -= factor;
				b += factor;
				c -= factor;
				d += factor;
			}
			else{
				a = (a - factor <= minX) ? minX : a - factor;
				b = (b + factor >= maxX) ? maxX : b + factor;
				c = (c - factor <= minY) ? minY : c - factor;
				d = (d + factor >= maxY) ? maxY : d + factor;
			}
		}
	}
	
	public int zoomLevelRelative(){
		int zoom = maxX - (b-a);
		if(zoom < 0)
			return 5;
		if(zoom < maxX/5d)
			return 4;
		if(zoom < maxX/3d)
			return 3;
		if(zoom < maxX/2d)
			return 2;
		if(zoom < maxX/1d)
			return 1;
		if(zoom >= maxX)
			return 0;
		return 0;
	}
	
	public int zoomLevelAbsolute(){
		int zoom = b - a;
		
		if(zoom <= 25)
			return 0;
		if(zoom <= 55)
			return 1;
		if(zoom <= 70)
			return 2;
		if(zoom <= 100)
			return 3;
		if(zoom <= 130)
			return 4;
		else
			return 5;
	}
	
	public boolean intersects(int x, int y){
		if(mod(x, maxX) >= minX && mod(x, maxX) < maxX && mod(y, maxY) >= minY && mod(y, maxY) < maxY)
			return true;
	
		return false;
	}
	
	private int mod(int a, int b){
		return (a%b + b) % b;
	}
}
