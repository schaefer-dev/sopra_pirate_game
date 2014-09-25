package GameStates;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import Events.HoverEvent;
import Events.SwitchState;
import Tests.GameState;
import Tests.GUIController;

public class TeamInfoState implements GameState {

	private GUIController manager;
	@Override
	public void Entered(GUIController root) {
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
		text.setText("Pirates of the S-aarrrr-ibean is a game developed by Group 5\nduring the 2014 \"Softwarepraktikum\" at Saarland University Germany\n"
				+ "Group 5 consists of 5 people who are keen to present you the very\n best of Pirate adventures.\n"
				+ "We are proud to announce that we did not only manage to present you\n with the most silly pirate names and map creations.\n We also succeded in finding the most hidious designs for ships\n"
				+ "But enough of talking: This is your amazing team of game developers: Group 5\n"
				+ "Janna Herrmann            Rafael Theis            Andreas Meyer            Jan Menz            Daniel Schäfer\n");
				
		Text thanks = new Text();
		thanks.setText("We also want to thank our tutor Maximilian Zöllner");
		thanks.setFill(Color.RED);
		thanks.setFont(Font.font("UglyQua", 20));
		grid.add(back, 0, 2);
		forText.add(text, 0, 0);
		forText.add(thanks,0,2);
		text.setFill(Color.RED);
		text.setFont(Font.font("UglyQua", 20));
		ImageView v1 = new ImageView();
		
		Image picture1 = new Image("file:bildJan.jpg",100,100,true,true, true);
		v1.setImage(picture1);
		HBox pictures = new HBox();
		pictures.getChildren().add(v1);
		forText.add(pictures, 0, 1);
		
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
