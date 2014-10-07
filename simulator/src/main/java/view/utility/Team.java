package view.utility;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.paint.Color;

public class Team {

	private String name;
	private int id;
	private int score;
	private List<Ship> ships;
	
	private String colorString;
	private Color color;
	
	public Team(Integer number, Configuration config){
		if(config.getFinalCaptainNames().size() == 0)
			name = config.captainNames.get(number);
		else
			name = config.getFinalCaptainNames().get(number);
		
		id = number;
		ships = new LinkedList<Ship>();
		score = 0;
		
		int index = config.captainNames.indexOf(name);
		colorString = MapPreview.teamColors[index];
		color = Color.web(colorString);
	}
	
	public int getScore(){
		return score;
	}
	
	public void setScore(int score){
		this.score = score;
	}
	
	public List<Ship> getShips(){
		return ships;
	}
	
	public void addShip(Ship ship){
		ship.setFleet(this);
		ships.add(ship);
	}
	
	public void deleteShip(Ship ship){
		ships.remove(ship);
	}
	
	public int getShipCount(){
		return ships.size();
	}
	
	public int getID(){
		return id;
	}
	
	public Color getColor(){
		return color;
	}
	
	public String getColorRGB(){
		return colorString;
	}
	
	@Override
	public String toString(){
		return name;
	}
}
