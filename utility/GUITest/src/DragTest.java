import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.EllipseBuilder;
import javafx.stage.Stage;

public class DragTest extends Application {
	
	public static void main(String[] args) {
	    launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	    Pane p = new Pane();
	
	    final Ellipse ellipse = EllipseBuilder.create().radiusX(10).radiusY(10).fill(Color.RED).build();
	    p.getChildren().add(ellipse);
	
	    p.setOnMouseDragged(new EventHandler<MouseEvent>() {
	        public void handle(MouseEvent event) {
	            ellipse.setCenterX(event.getX());
	            ellipse.setCenterY(event.getY());
	        }
	    });
	
	    Scene scene = SceneBuilder.create().root(p).width(1024d).height(768d).build();
	    primaryStage.setScene(scene);
	
	    primaryStage.show();
	}
}