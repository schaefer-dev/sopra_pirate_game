package Tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.util.EmptyStackException;
import java.util.Stack;

import GameStates.MainMenuState;
import GameStates.MapSelectionState;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayerBuilder;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUIController extends Application {

	public Resolution resolution = Resolution.HD;

	private Stage stage;
	private Scene scene;

	private SimulatorSettings mapSettings = new SimulatorSettings();
	private Text title = new Text();
	private Text hoverText = new Text();
	private BorderPane borderPane;

	private Stack<GameState> states = new Stack<GameState>();
	private GameState currentState = new MainMenuState();


    public static void main(String[] args) {
        launch(args);
    }

	public void setState(GameState state) throws FileNotFoundException{
		currentState.Exiting();
		currentState = state;
		currentState.Entered(this);
	}

	public void switchState(GameState state) throws FileNotFoundException{
		for(GameState current: states)
			current.Exiting();

		state.Entered(this);
		states = new Stack<GameState>();
		states.push(state);
	}

	public void addState(GameState state) throws FileNotFoundException{
		if(state == null) throw new NullPointerException();

		try{
			GameState current = states.peek();
			current.Concealing();
		}
		catch(EmptyStackException e){}

		state.Entered(this);
		states.push(state);
	}

	public GameState removeState(){
		GameState current;
		try{
			current = states.pop();
			current.Exiting();
		}
		catch(EmptyStackException e){
			throw e;
		}
		try{
			states.peek().Revealed();
		}
		catch(EmptyStackException e){}

		return current;
	}

	public GameState currentState(){
		try{
			return states.peek();
		}
		catch(EmptyStackException e){
			return null;
		}
	}



	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		stage.setTitle("Pirates of the Saaribean");
		title.setId("title");
		hoverText.setId("hover");

		GridPane bottom = new GridPane();
		bottom.setAlignment(Pos.CENTER);
		bottom.getChildren().add(hoverText);

		borderPane = new BorderPane();
		borderPane.setTop(title);
		BorderPane.setMargin(title, new Insets(25,25,25,25));
		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		BorderPane.setAlignment(bottom, Pos.BOTTOM_CENTER);
		borderPane.setBottom(bottom);

		addState(currentState);
		//currentState.Entered(this);
		scene = new Scene(borderPane, 1280, 720);
		stage.setScene(scene);
		//primaryStage.setResizable(false);
		stage.show();

		//setScreen(scene);							//TODO GUI-CSS switch
		addResizeListener(scene);
		addKeyListener(scene);
		File temp = new File("bin/ressources/GUIMusik.mp3");
		final Media media = new Media(temp.toURI().toString());
		final MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
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


	public SimulatorSettings getMap(){
		return mapSettings;
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
