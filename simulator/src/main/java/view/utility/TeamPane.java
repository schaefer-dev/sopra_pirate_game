package view.utility;

import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;

public class TeamPane extends TitledPane {

	private Team team;
	
	
	public TeamPane(final Team team, Node content, final Map map){
		this.setStyle("-fx-mark-color: #"+team.getColorRGB());
		this.team = team;
		update();
	}	
	
	public void update(){
		String title = " " + team.toString();
		String score = String.valueOf(team.getScore());
		int offset = 20 - score.length();
		title = String.format("%-"+offset+"s", title).replace(' ', ' ');
		title += score;
		
		this.setText(title);
		this.setContent(new Text(giveTeamText()));
	}
	
	private String giveTeamText(){
		String score = "Score: " + team.getScore();
		String ships = "Ships: " + team.getShipCount();
		
		return score + "\n" + ships;
	}
	
	public Team getTeam(){
		return team;
	}
}
