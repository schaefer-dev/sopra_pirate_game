package view.utility;

import javafx.scene.canvas.Canvas;

public class Camera {

	private int minX;
	private int maxX;
	private int minY;
	private int maxY;

	public int a, b, c, d;	
	
	private boolean scrollable;
	
	public Camera(Canvas canvas){
		minX = a = 0;
		minY = c = 0;
		maxX = b = (int) canvas.getWidth();
		maxY = d = (int) canvas.getHeight();
		scrollable = true;
	}
	
	public Camera(Field[][] map){
		minX = a = 0;
		minY = c = 0;
		maxX = b = (int) map.length;
		maxY = d = (int) map[0].length;
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
		if(scrollable || a + factor < b){
			a += factor;
			b -= factor;
		}
		if(scrollable || c + factor < d){
			c += factor;
			d -= factor;
		}
	}
	
	public void zoomOut(int factor){
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
	
	public boolean intersects(int x, int y){
		if(x >= minX && x < maxX && y >= minY && y < maxY)
			return true;
		//TODO: broken
		return false;
	}
}
