package view.gamestates;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import view.events.HoverEvent;
import view.events.SwitchState;
import view.utility.GameState;
import view.GUIController;

public class TeamInfoState implements GameState {
	private double prevWid;
	private GUIController manager;
	@Override
	public void entered(GUIController root) {
		manager = root;
		manager.getTitleText().setText("Team Info");
		List<Text> texts = new LinkedList<Text>();
		Text thanks = new Text("                                                        We also want to thank our tutor Maximilian Zöllner");
		texts.add(thanks);
		GridPane forText = new GridPane();
		forText.setAlignment(Pos.TOP_CENTER);
		manager.getTitleText().setText("The Team");
		Button back = new Button("< Back");
		back.setAlignment(Pos.BOTTOM_CENTER);
		back.setOnMouseEntered(new HoverEvent(root.getHoverText(), "Go back to main menu"));
		back.setOnMouseExited(new HoverEvent(root.getHoverText(), ""));
		back.setOnAction(new SwitchState(manager));
		back.getStyleClass().add("menubutton");
		Text text = new Text();
		text.setText("Pirates of the S-aarrrr-ibean is a game developed by Group 5 during the 2014 \"Softwarepraktikum\" at Saarland University Germany\n"
				+ "Group 5 consists of 5 people who are keen to present you the very best of Pirate adventures.\n"
				+ "We are proud to announce that we did not only manage to present you with the most silly pirate names and map creations.\n We also succeded in finding the most hidious designs for ships\n"
				+ "But enough of talking: This is your amazing team of game developers: Group 5\n");
		text.setFill(Color.RED);
		texts.add(text);
		thanks.setTextAlignment(TextAlignment.CENTER);
		thanks.setFill(Color.RED);
		forText.add(text, 0, 0);
		forText.add(new Text(""), 0,2);
		forText.add(new Text(""), 0,1);
		forText.add(thanks,0,3);
		HBox button = new HBox();
		button.getChildren().add(back);
		button.setAlignment(Pos.BOTTOM_CENTER);
		forText.add(button, 0, 5);
		
		int imageSize = 150;
		if(manager.getStage().getHeight() < 720 /*resolution HD */)
		{
			imageSize = 80;
		
		}
		ImageView v1 = new ImageView();
		File temp = new File("src/main/ressources/bildJanna.jpg");
		Image picture1 = new Image(temp.toURI().toString(),imageSize,imageSize,true,true);
		v1.setImage(picture1);
		ImageView v2 = new ImageView();
		temp = new File("src/main/ressources/bildJan.jpg");
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
		texts.add(janna);
		janna.setText("Janna Herrmann");
		one.setAlignment(Pos.CENTER);
		one.getChildren().add(janna);
		one.getChildren().add(v1);
		one.setOnMouseEntered(new HoverEvent(root.getHoverText(), "The creator of our beautiful kraken"));
		one.setOnMouseExited(new HoverEvent(root.getHoverText(), ""));
		VBox two = new VBox(15);
		Text rafael = new Text();
		rafael.setText("Rafael Theis");
		rafael.setFont(Font.font("UglyQua", 18));
		texts.add(rafael);
		two.setAlignment(Pos.CENTER);
		two.getChildren().add(rafael);
		two.getChildren().add(v2);
		two.setOnMouseEntered(new HoverEvent(root.getHoverText(), "The mastermind behind this magnificent GUI"));
		two.setOnMouseExited(new HoverEvent(root.getHoverText(), ""));
		VBox three = new VBox(15);
		Text jan = new Text();
		jan.setText("Jan Menz");
		texts.add(jan);
		three.setAlignment(Pos.CENTER);
		three.getChildren().add(jan);
		three.getChildren().add(v3);
		three.setOnMouseEntered(new HoverEvent(root.getHoverText(), "The creator of this informative Team page"));
		three.setOnMouseExited(new HoverEvent(root.getHoverText(), ""));
		VBox four = new VBox(15);
		Text andreas = new Text();
		andreas.setText("Andreas Meyer");
		texts.add(andreas);
		four.setAlignment(Pos.CENTER);
		four.getChildren().add(andreas);
		four.getChildren().add(v4);
		four.setOnMouseEntered(new HoverEvent(root.getHoverText(), "Without his translator your tactic files would merely be gibberish"));
		four.setOnMouseExited(new HoverEvent(root.getHoverText(), ""));
		VBox five = new VBox(15);
		Text daniel = new Text();
		daniel.setText("Daniel Schäfer");
		texts.add(daniel);
		five.setAlignment(Pos.CENTER);
		five.getChildren().add(daniel);
		five.getChildren().add(v5);
		five.setOnMouseEntered(new HoverEvent(root.getHoverText(), "His ship drawings are matched by no other"));
		five.setOnMouseExited(new HoverEvent(root.getHoverText(), ""));
		HBox pictures = new HBox(manager.getStage().getWidth()/15);
		pictures.getChildren().addAll(one,two,three,four,five);
		pictures.setAlignment(Pos.CENTER);
		InnerShadow innerShadow = new InnerShadow();
		innerShadow.setColor(Color.BLACK);
		if(manager.getStage().getWidth() < 1280){
			
			for(Text akt: texts)
			{
				akt.setFont(Font.font("UglyQua", 13));
				akt.setEffect(innerShadow);
			}
		}
		else
		{
			for(Text akt: texts)
			{
				akt.setFont(Font.font("UglyQua", 18));
				akt.setEffect(innerShadow);

			}
		}
		forText.add(pictures, 0, 1);		
		manager.getRoot().setCenter(forText);
	}

	@Override
	public void exiting() {
		manager.getRoot().setCenter(null);
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
