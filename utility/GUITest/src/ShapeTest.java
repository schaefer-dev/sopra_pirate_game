import java.awt.Point;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
 
public class ShapeTest extends Application {
 
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();
        Canvas canvas = new Canvas(700, 700);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void drawShapes(GraphicsContext gc) {
    	
    	for(int y = 0; y < 3; y++){
        	for(int x = 0; x < 10; x++){
        		int r = 15;
        		int stepx= 0;
        		int stepY = 0;
        		if(y%2 == 0){
        			stepx += r;
        			stepY += r;
        		}
        		else{
        			stepx = 0;
        			
        		}
        		
            	Hexagon hex = new Hexagon(new Point(x*2*r+stepx, y*2*r+stepY), r);
            	hex.draw(gc);		
        	}		
    	}
    	
    	

    }
    
    
}
