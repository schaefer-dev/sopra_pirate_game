package view.gamestates;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.sun.scenario.effect.Effect;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.InnerShadow;
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
import view.events.HoverEvent;
import view.events.SwitchState;
import view.utility.GameState;
import view.GUIController;
import view.utility.Resolution;

public class TeamInfoState implements GameState {
	private double prevWid;
	private GUIController manager;
	@Override
	public void entered(GUIController root) {
		manager = root;
		manager.getTitleText().setText("Team Info");
		Text thanks = new Text("                                                        We also want to thank our tutor Maximilian Zöllner");
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		GridPane forText = new GridPane();
		ScrollPane scroll = new ScrollPane();
		forText.setAlignment(Pos.TOP_CENTER);
		manager.getTitleText().setText("The Team");
		Button back = new Button("< Back");
		back.setAlignment(Pos.BOTTOM_LEFT);
		back.setOnMouseEntered(new HoverEvent(root.getHoverText(), "Go back to main menu"));
		back.setOnMouseExited(new HoverEvent(root.getHoverText(), ""));
		back.setOnAction(new SwitchState(manager, new MainMenuState()));
		back.getStyleClass().add("menubutton");
		Text text = new Text();
		text.setText("Pirates of the S-aarrrr-ibean is a game developed by Group 5 during the 2014 \"Softwarepraktikum\" at Saarland University Germany\n"
				+ "Group 5 consists of 5 people who are keen to present you the very best of Pirate adventures.\n"
				+ "We are proud to announce that we did not only manage to present you with the most silly pirate names and map creations.\n We also succeded in finding the most hidious designs for ships\n"
				+ "But enough of talking: This is your amazing team of game developers: Group 5\n");
		text.setFill(Color.RED);
		text.setFont(Font.font("UglyQua", 18));
		
		thanks.setFont(Font.font("UglyQua", 18));
		thanks.setTextAlignment(TextAlignment.CENTER);
		thanks.setFill(Color.RED);
		scroll.setContent(text);
		scroll.setPrefSize(manager.getStage().getWidth()-50, manager.getStage().getHeight() - 250);

		if(manager.getStage().getWidth() < 1280){
			text.setFont(Font.font("UglyQua", 14));
			thanks.setFont(Font.font("UglyQua", 14));
		}
		else
		{
			text.setFont(Font.font("UglyQua", 19));
			thanks.setFont(Font.font("UglyQua", 19));
		}
			
		InnerShadow innerShadow = new InnerShadow();
		 innerShadow.setColor(Color.BLACK);
		text.setEffect(innerShadow);
		grid.add(back, 0, 0);
		forText.add(scroll, 0, 0);
		forText.add(new Text(""), 0,2);
		forText.add(new Text(""), 0,3);
		forText.add(new Text(""), 0,1);
		forText.add(thanks,0,4);
		
		int imageSize = 100;
		if(manager.getStage().getHeight() < 720 /*resolution HD */)
		{
			imageSize = 50;
		
		}
		ImageView v1 = new ImageView();
		File temp = new File("src/main/ressources/bildJan.jpg");
		Image picture1 = new Image(temp.toURI().toString(),imageSize,imageSize,true,true);
		v1.setImage(picture1);
		ImageView v2 = new ImageView();
		Image picture2 = new Image(temp.toURI().toString(),imageSize,imageSize,true,true);
		ImageView v3 = new ImageView();
		Image picture3 = new Image(temp.toURI().toString(),imageSize,imageSize,true,true);
		ImageView v4 = new ImageView();
		Image picture4 = new Image(temp.toURI().toString(),imageSize,imageSize,true,true);
		ImageView v5 = new ImageView();
		Image picture5 = new Image(temp.toURI().toString(),imageSize,imageSize,true,true);
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
		HBox pictures = new HBox(manager.getStage().getWidth()/15);
		pictures.getChildren().addAll(one,two,three,four,five);
		pictures.setAlignment(Pos.CENTER);
		forText.add(pictures, 0, 1);
		manager.getRoot().setBottom(grid);
		manager.getRoot().setCenter(forText);
	}

	@Override
	public void exiting() {
		manager.getRoot().setCenter(null);
		manager.getRoot().setBottom(null);
		if(prevWid != 0)
			manager.getStage().setWidth(prevWid);
	}

	@Override
	public void concealing() {
		this.exiting();
		
	}

	@Override
	public void revealed() {
		// TODO Auto-generated method stub
		
	}

}
