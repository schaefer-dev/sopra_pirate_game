package view.utility;

import java.util.LinkedList;
import java.util.List;

public class Team {

	private String name;
	private int score;
	private List<Ship> ships;
	
	public Team(Integer number){
		name = "Team " + number.toString();
		ships = new LinkedList<Ship>();
		score = 0;
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
		ships.add(ship);
	}
	
	public void deleteShip(Ship ship){
		ships.remove(ship);
	}
	
	public int getShipCount(){
		return ships.size();
	}
	
	@Override
	public String toString(){
		return name;
	}
}
