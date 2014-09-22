package Tests;

import GameStates.MainMenuState;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StateManager extends Application {

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
		
		primaryStage.setTitle("Pirates of the Saaribean");
		title.setId("title");
		hoverText.setId("text");
		
		Button team = new Button("Team");
		team.setAlignment(Pos.BOTTOM_LEFT);
		team.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		Button exit = new Button("Exit");
		exit.setAlignment(Pos.BOTTOM_RIGHT);
		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Platform.exit();
			}
		});
		
		GridPane bottom = new GridPane();
		bottom.setAlignment(Pos.CENTER);
		bottom.getChildren().add(hoverText);
		
		borderPane = new BorderPane();
		
		borderPane.setTop(title);
		BorderPane.setMargin(title, new Insets(25,25,25,25));
		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		
		borderPane.setRight(exit);
		BorderPane.setAlignment(exit, Pos.BOTTOM_RIGHT);
		BorderPane.setAlignment(bottom, Pos.BOTTOM_CENTER);
		
		borderPane.setBottom(bottom);
		
		borderPane.setLeft(team);
		BorderPane.setAlignment(team, Pos.BOTTOM_LEFT);
		
		currentState.Entered(this);
		Scene scene = new Scene(borderPane, 900, 550);
		//scene.getStylesheets().add(getClass().getResource("teststyle.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		
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
	
}
