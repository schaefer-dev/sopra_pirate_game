package Tests;

import GameStates.MainMenuState;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StateManager extends Application {

	public Resolution resolution = Resolution.HD;
	
	private Stage stage;
	private Scene scene;
	
	private Text title = new Text();
	private Text hoverText = new Text();
	private BorderPane borderPane;
	private GameState currentState = new MainMenuState();
	
	private char[][] map;
	
    public static void main(String[] args) {
        launch(args);
    }
    
	public void setState(GameState state){
		currentState.Exiting();
		currentState = state;
		currentState.Entered(this);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		stage.setTitle("Pirates of the Saaribean");
		title.setId("title");
		hoverText.setId("text");
		

		
		GridPane bottom = new GridPane();
		bottom.setAlignment(Pos.CENTER);
		bottom.getChildren().add(hoverText);
		
		borderPane = new BorderPane();
		borderPane.setTop(title);
		BorderPane.setMargin(title, new Insets(25,25,25,25));
		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		BorderPane.setAlignment(bottom, Pos.BOTTOM_CENTER);
		borderPane.setBottom(bottom);

		
		currentState.Entered(this);
		scene = new Scene(borderPane, 1280, 720);

		stage.setScene(scene);
		primaryStage.setResizable(false);
		stage.show();
		
		//setScreen(scene);
		addResizeListener(scene);
		addKeyListener(scene);
	}
	
	public Text getTitleText(){
		return title;
	}
	
	public Text getHoverText(){
		return hoverText;
	}
	
	public BorderPane getRoot(){
		return borderPane;
	}
	
	
	public void setMap(char[][] map){
		this.map = map;
	}
	
	
	public char[][] getMap(){
		return map;
	}
	
	public Scene getScene(){
		return scene;
	}
	
	public Stage getStage(){
		return stage;
	}
	
	private void setScreen(Scene scene){		
		int height = (int) scene.getHeight();
		
		scene.getStylesheets().clear();
		scene.getStylesheets().add(getClass().getResource("common.css").toExternalForm());
		
		if(height < 700)
			scene.getStylesheets().add(getClass().getResource("480p.css").toExternalForm());
		else if(height < 1000)
			scene.getStylesheets().add(getClass().getResource("720p.css").toExternalForm());
		else
			scene.getStylesheets().add(getClass().getResource("1080p.css").toExternalForm());
	}
	
	public Resolution getResolution(){
		return resolution;
	}
	
	public void setResolution(Resolution resolution){
		this.resolution = resolution;
	}
	
	
	private void addResizeListener(final Scene scene){
		
		scene.heightProperty().addListener(new ChangeListener<Number>() {
			
		    @Override 
		    public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
		        setScreen(scene);
		    }
		});
	}
	
	private void addKeyListener(final Scene scene){
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent arg0) {
				if(arg0.getCode().equals(KeyCode.ESCAPE));
					Platform.exit();	
			}
		});
	}
}
