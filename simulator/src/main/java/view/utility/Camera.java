package view.utility;

import javafx.scene.canvas.Canvas;

public class Camera {

	private int minX;
	private int maxX;
	private int minY;
	private int maxY;

	public int a, b, c, d;	
	
	public Camera(Canvas canvas){
		minX = a = 0;
		minY = c = 0;
		maxX = b = (int) canvas.getWidth();
		maxY = d = (int) canvas.getHeight();
	}
	
	public Camera(char[][] map){
		minX = a = 0;
		minY = c = 0;
		maxX = b = (int) map.length;
		maxY = d = (int) map[0].length;
	}
	
	
	public void setMid(int x, int y){
		int width  = b - a;
		int height = d - c;
		
		int aTemp = x - width/2;
		int bTemp = x + width/2;
		int cTemp = y - height/2;
		int dTemp = y + height/2;
		
		if(aTemp >= minX && bTemp <= maxX){
			a = aTemp;
			b = bTemp;
		}
		if(cTemp >= minY && dTemp <= maxY){
			c = cTemp;
			d = dTemp;
		}
	}
	
	public void moveUp(int factor){
		if(c - factor <= minY){
			d = minY + (d - c);
			c = minY;
		}
		else{
			c -= factor;
			d -= factor;
		}
	}
	
	public void moveDown(int factor){
		if(d + factor >= maxY){
			c = maxY - (d - c);
			d = maxY;
		}
		else{
			c += factor;
			d += factor;
		}
	}
	
	public void moveLeft(int factor){
		if(a - factor <= minX){
			b = minX + (b - a);
			a = minX;
		}
		else{
			a -= factor;
			b -= factor;
		}
	}
	
	public void moveRight(int factor){
		if(b + factor >= maxX){
			a = maxX - (b - a);
			b = maxX;
		}
		else{
			a += factor;
			b += factor;
		}
	}
	
	public void zoomIn(int factor){
		if(a + factor < b){
			a += factor;
			b -= factor;
		}
		if(c + factor < d){
			c += factor;
			d -= factor;
		}
	}
	
	public void zoomOut(int factor){
		a = (a - factor <= minX) ? minX : a - factor;
		b = (b + factor >= maxX) ? maxX : b + factor;
		c = (c - factor <= minY) ? minY : c - factor;
		d = (d + factor >= maxY) ? maxY : d + factor;
	}
}
