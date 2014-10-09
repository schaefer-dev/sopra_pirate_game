package view;

import java.io.File;
import java.io.FileInputStream;
import java.util.EmptyStackException;
import java.util.Stack;

import view.gamestates.MainMenuState;
import view.utility.Configuration;
import view.utility.GameState;
import view.utility.Resolution;
import view.utility.Ressources;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUIController extends Application {

	public Resolution resolution = Resolution.HD;
	
	private Stage stage;
	private Scene scene;
	private MediaPlayer player;
	private Ressources res = new Ressources();
	private Configuration config = res.getDefaultConfig();
	private Text title = new Text();
	private Text hoverText = new Text();
	private BorderPane borderPane;
	
	private Stack<GameState> states = new Stack<GameState>();
	
	
    public static void main(String[] args) {
        launch(args);
    }
	
	public void switchState(GameState state){
		if(state == null) throw new NullPointerException();
		
		for(GameState current: states)
			current.exiting();
		
		states = new Stack<GameState>();
		states.push(state);
		state.entered(this);
	}
	
	public void addState(GameState state){
		if(state == null) throw new NullPointerException();
		
		try{
			GameState current = states.peek();
			current.concealing();
		}
		catch(EmptyStackException e){}
		state.entered(this);
		states.push(state);
	}
	
	public GameState removeState(){
		GameState current;
		try{
			current = states.pop();
			current.exiting();
		}
		catch(EmptyStackException e){
			throw e;
		}
		try{
			states.peek().revealed();
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
		File uglyFile = new File("src/main/ressources/Fonts/UglyQua.tff");
		Font.loadFont(uglyFile.toURI().toString(), 10);
		File monoFile = new File("src/main/ressources/Fonts/UbuntuMono.tff");
		Font.loadFont(monoFile.toURI().toString(), 10);
		
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

		scene = new Scene(borderPane, 1280, 720);
		stage.setScene(scene);
		//primaryStage.setResizable(false);
		stage.show();
		addState(new MainMenuState());
		
		setScreen(scene);
		addResizeListener(scene);
		addKeyListener(scene);
		
		try{
			File temp = new File("src/main/ressources/GUIMusik.mp3");
			final Media media = new Media(temp.toURI().toString());
			final MediaPlayer mediaPlayer = new MediaPlayer(media);
			player = mediaPlayer;
			mediaPlayer.play();
			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		}
		catch(Exception e){}
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
		
	public Configuration getConfiguration(){
		return config;
	}
	
	public void setConfiguration(Configuration config){
		this.config = config;
	}
	
	public Ressources getRessources(){
		return res;
	}
	
	public void resetRessources(){
		res = new Ressources();
	}
	
	public Scene getScene(){
		return scene;
	}
	
	public Stage getStage(){
		return stage;
	}
	
	private void setScreen(Scene scene){		
		int height = (int) scene.getHeight();
		
		File file = new File("src/main/ressources/common.css");
		scene.getStylesheets().clear();
		scene.getStylesheets().add(file.toURI().toString());
		
		if(height < 680){
			file = new File("src/main/ressources/480p.css");
			scene.getStylesheets().add(file.toURI().toString());
		}	
		else if(height < 1000){
			file = new File("src/main/ressources/720p.css");
			scene.getStylesheets().add(file.toURI().toString());		
		}
		else{
			file = new File("src/main/ressources/1080p.css");
			scene.getStylesheets().add(file.toURI().toString());
		}		
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
				if(arg0.getCode().equals(KeyCode.ESCAPE)){
					Platform.exit();	
					System.exit(0);
				}
			}
		});
	}
	
	public MediaPlayer getPlayer()
	{
		return player;
	}
}
