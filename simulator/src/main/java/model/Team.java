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
		// TODO Auto-generated method stub
	}
	
	public void addShip(Ship ship){
		// TODO Auto-generated method stub
	}
	
	public void deleteShip(Ship ship){
		// TODO Auto-generated method stub
	}
	
	public List<Command> getCommands(){
		// TODO Auto-generated method stub
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
