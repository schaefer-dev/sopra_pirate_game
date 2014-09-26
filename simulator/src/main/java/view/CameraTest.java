package view;

import view.utility.Generator;
import view.utility.Camera;
import view.utility.Map;
import view.events.MouseDragEvent;
import view.events.MouseScrollEvent;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;


public class CameraTest extends Application {

	public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Camera Test");
        final Group root = new Group();
        final Canvas canvas = new Canvas(800, 800);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
 
        Generator gen = new Generator(198, 198, 40, 4);
		char[][] fields = gen.generateMap();
		
        Camera cam = new Camera(fields);
        Map map = new Map(fields, cam);
        map.draw(gc);
        
        Scene scene = new Scene(root);  
        scene.setOnMouseDragged(new MouseDragEvent(cam, map, gc));
        scene.setOnScroll(new MouseScrollEvent(cam, map, gc));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
