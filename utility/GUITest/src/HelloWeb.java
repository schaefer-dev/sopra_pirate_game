import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class HelloWeb extends Application {

    public static void main(String[] args) {
        launch(args);
    }
	@Override
	public void start(Stage stage) throws Exception {

		WebView   browser = new WebView();
		WebEngine webkit  = browser.getEngine();
		webkit.load("http://maps.google.de");

		StackPane root = new StackPane();
		root.getChildren().add(browser);

		Scene scene = new Scene(root, 300, 300);

		stage.setScene(scene);
		stage.show();
		
	}

	
}
