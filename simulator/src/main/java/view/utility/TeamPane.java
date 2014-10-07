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
		
		int stell = 1;
		int x = team.getScore();
		while(x/10 > 0)
			stell++;
		int offset = 20 - stell;
		
		title = String.format("%-"+offset+"s", title).replace(' ', ' ');
		title += String.valueOf(team.getScore());
		
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
