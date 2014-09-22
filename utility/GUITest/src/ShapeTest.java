import java.awt.Point;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.ParallelCamera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PerspectiveCameraBuilder;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
 
public class ShapeTest extends Application {
 
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("ShapeTest");
        final Group root = new Group();
        final Canvas canvas = new Canvas(700, 700);
        //Canvas canvas2 = new Canvas(1000, 100);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);
       // root.getChildren().add(canvas2);
        root.getChildren().add(canvas);
        
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
	        public void handle(MouseEvent event) {
		        canvas.setTranslateX(event.getX() - 350);
		        canvas.setTranslateY(event.getY() - 350);
	        }
	    });
        

        Scene scene = new Scene(root);
        PerspectiveCamera cam = new PerspectiveCamera();
      
        scene.setCamera(cam);
        
        primaryStage.setScene(scene);

        primaryStage.show();
       // canvas.setScaleX(2);
       // canvas.setScaleY(2);
    }

    private void drawShapes(GraphicsContext gc){
    	for(int y = 0; y < 20; y++){
        	for(int x = 0; x < 20; x++){
        		int r = 20;
        		int stepX = (y%2 == 0) ? r : 0;
        		int stepY = y*(r/2);
        		
            	Hexagon hex = new Hexagon(new Point(x*2*r+stepX, y*2*r - stepY), r);
            	hex.draw(gc);		
        	}		
    	}
    }
}
