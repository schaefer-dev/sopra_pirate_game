package model;

import java.util.LinkedList;
import java.util.List;

import controller.Command;

public class Team {

	private int score;
	private char name;
	private List<Command> commands;
	private int shipCount;
	private List<Ship> ships;
	
	
	public Team(char name, List<Command> commands){
		this.name = name;
		this.score = 0;
		this.commands = commands;
		this.shipCount = 0;
		ships = new LinkedList<Ship>();
	}
	
	public void addLoot(int i){ 
		if ((score + i) >= 0){
			score += i;
			//loggen
		}
		else {
			score = 0;
			//loggen
		}
	}
	
	public void addShip(Ship ship){
		ships.add(ship);
		shipCount += 1;
	}
	
	public void deleteShip(Ship ship){
		ships.remove(ship);
		//loggen
		shipCount -= 1;
	}
	
	public List<Command> getCommands(){
		return commands;
	}
	
	public int getScore() {
		return score;
	}

	public char getName() {
		return name;
	}

	public int getShipCount() {
		return shipCount;
	}
	
	public List<Ship> getShips()
	{
		return this.ships;
	}
	
	@Override
	public boolean equals(final Object other) {
		if (other instanceof Team) {
			if ((this.name)==((Team)other).name)
				{return true;}
		}
		return false;
	}
}
