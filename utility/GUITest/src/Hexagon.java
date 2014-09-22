import java.awt.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Hexagon {

	private Point up;
	private Point upL;
	private Point upR;
	private Point down;
	private Point downL;
	private Point downR;
	
	private Point mid;
	private int radius;
	
	public Hexagon(Point newMid, int newRadius){
		mid = newMid;
		radius = newRadius;
		
		computePoints();
	}
	
	public void draw(GraphicsContext gc){
		gc.setFill(Color.BLACK);
		gc.setLineWidth(1);
		
		gc.strokeLine(up.x, up.y, upR.x, upR.y);
		gc.strokeLine(up.x, up.y, upL.x, upL.y);
		gc.strokeLine(upL.x, upL.y, downL.x, downL.y);
		gc.strokeLine(upR.x, upR.y, downR.x, downR.y);
		gc.strokeLine(downL.x, downL.y, down.x, down.y);
		gc.strokeLine(downR.x, downR.y, down.x, down.y);
		
		gc.fillText("#", mid.x, mid.y);
		
	}
	
	private void computePoints(){
		
		up = new Point(mid.x, mid.y - radius);
		down = new Point(mid.x, mid.y + radius);
		
		upL = new Point(mid.x - radius, mid.y - radius/2);
		upR = new Point(mid.x + radius, mid.y - radius/2);
		
		downL = new Point(mid.x - radius, mid.y + radius/2);
		downR = new Point(mid.x + radius, mid.y + radius/2);
	}
}
