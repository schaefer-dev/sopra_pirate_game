package GameStates;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import Events.HoverEvent;
import Events.SwitchState;
import Tests.GameState;
import Tests.GUIController;

public class TeamInfoState implements GameState {

	private GUIController manager;
	@Override
	public void Entered(GUIController root) throws FileNotFoundException {
		manager = root;
		manager.getTitleText().setText("Team Info");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		GridPane forText = new GridPane();
		forText.setAlignment(Pos.TOP_CENTER);
		manager.getTitleText().setText("The Team");
		
		Button back = new Button("< Back");
		back.setAlignment(Pos.BOTTOM_LEFT);
		back.setOnMouseEntered(new HoverEvent(root.getHoverText(), "Go back to main menu"));
		back.setOnMouseExited(new HoverEvent(root.getHoverText(), ""));
		back.setOnAction(new SwitchState(manager, new MainMenuState()));
		
		Text text = new Text();
		text.setText("Pirates of the S-aarrrr-ibean is a game developed by Group 5 during the 2014 \"Softwarepraktikum\" at Saarland University Germany\n"
				+ "Group 5 consists of 5 people who are keen to present you the very best of Pirate adventures.\n"
				+ "We are proud to announce that we did not only manage to present you with the most silly pirate names and map creations.\n We also succeded in finding the most hidious designs for ships\n"
				+ "But enough of talking: This is your amazing team of game developers: Group 5\n");
				
		Text thanks = new Text();
		thanks.setText("We also want to thank our tutor Maximilian Zöllner");
		thanks.setFill(Color.RED);
		thanks.setFont(Font.font("UglyQua", 20));
		grid.add(back, 0, 0);
		forText.add(text, 0, 0);
		forText.add(thanks,0,2);
		text.setFill(Color.RED);
		text.setFont(Font.font("UglyQua", 20));
		ImageView v1 = new ImageView();
		Image picture1 = new Image("file:bildJan.jpg",100,100,true,true);
		v1.setImage(picture1);
		ImageView v2 = new ImageView();
		Image picture2 = new Image("file:bildJan.jpg",100,100,true,true);
		ImageView v3 = new ImageView();
		Image picture3 = new Image("file:bildJan.jpg",100,100,true,true);
		ImageView v4 = new ImageView();
		Image picture4 = new Image("file:bildJan.jpg",100,100,true,true);
		ImageView v5 = new ImageView();
		Image picture5 = new Image("file:bildJan.jpg",100,100,true,true);
		v2.setImage(picture2);
		v3.setImage(picture3);
		v4.setImage(picture4);
		v5.setImage(picture5);
		text.setTextAlignment(TextAlignment.CENTER);
		VBox one = new VBox(15);
		Text janna = new Text();
		janna.setText("Janna Herrmann");
		janna.setFont(Font.font("UglyQua", 20));
		one.setAlignment(Pos.CENTER);
		one.getChildren().add(janna);
		one.getChildren().add(v1);
		VBox two = new VBox(15);
		Text rafael = new Text();
		rafael.setText("Rafael Theis");
		rafael.setFont(Font.font("UglyQua", 20));
		two.setAlignment(Pos.CENTER);
		two.getChildren().add(rafael);
		two.getChildren().add(v2);
		VBox three = new VBox(15);
		Text jan = new Text();
		jan.setText("Jan Menz");
		jan.setFont(Font.font("UglyQua", 20));
		three.setAlignment(Pos.CENTER);
		three.getChildren().add(jan);
		three.getChildren().add(v3);
		VBox four = new VBox(15);
		Text andreas = new Text();
		andreas.setText("Andreas Meyer");
		andreas.setFont(Font.font("UglyQua", 20));
		four.setAlignment(Pos.CENTER);
		four.getChildren().add(andreas);
		four.getChildren().add(v4);
		VBox five = new VBox(15);
		Text daniel = new Text();
		daniel.setText("Daniel Schäfer");
		daniel.setFont(Font.font("UglyQua", 20));
		five.setAlignment(Pos.CENTER);
		five.getChildren().add(daniel);
		five.getChildren().add(v5);
		HBox pictures = new HBox(140);
		pictures.getChildren().addAll(one,two,three,four,five);
		forText.add(pictures, 0, 1);
		thanks.setTextAlignment(TextAlignment.CENTER);
		manager.getRoot().setBottom(grid);
		manager.getRoot().setCenter(forText);
	}

	@Override
	public void Exiting() {
		manager.getRoot().setCenter(null);
		manager.getRoot().setBottom(null);
	}

	@Override
	public void Concealing() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Revealed() {
		// TODO Auto-generated method stub
		
	}

}
