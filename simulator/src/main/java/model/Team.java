package model;

import java.util.List;

import controller.Command;

public class Team {

	private int score;
	private char name;
	private List<Command> commands;
	private int shipCount;
	
	public Team(char name, List<Command> commands){
		// TODO Auto-generated method stub
	}
	
	public void addLoot(int i){
		// TODO Auto-generated method stub
	}
	
	public void addShip(){
		// TODO Auto-generated method stub
	}
	
	public void deleteShip(){
		// TODO Auto-generated method stub
	}
	
	public List<Command> getCommands(){
		// TODO Auto-generated method stub
		return null;
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
	
	@Override
	public boolean equals(final Object other) {
		if (other instanceof Team) {
			if ((this.name)==((Team)other).name)
				return true;
		}
		return false;
	}
}
