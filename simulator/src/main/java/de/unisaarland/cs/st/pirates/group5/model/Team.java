package de.unisaarland.cs.st.pirates.group5.model;

import java.util.LinkedList;
import java.util.List;

import de.unisaarland.cs.st.pirates.group5.controller.Command;
/**
 * The Team class
 * 
 * This class represents the teams of the ships. Every ship belongs to an object of this class. Significant for every object is the name,
 * the score, a list of commands (the tactic) and a list of its ships and the number of ships.
 * @author Janna
 *
 */
public class Team {

	private int score;
	private char name;
	private List<Command> commands;
	private int shipCount;
	private List<Ship> ships;
	
	/**
	 * Constructor
	 * Creates an object of the team class with a name, score, a tactic as list of commands and a shiplist. At the beginning,
	 * score and the number of ships are 0.
	 * @param name		the name of the new team
	 * @param commands	the tactic of this team as a list of commands
	 */
	public Team(char name, List<Command> commands){
		this.name = name;
		this.score = 0;
		this.commands = commands;
		this.shipCount = 0;
		ships = new LinkedList<Ship>();
	}
	
	/**
	 * Adds Loot which is dropped by a ship on its base to the teamscore or it decreases the score because a ship repairs itself.
	 * @param i	number to increase or decrease the score
	 * @throws IllegalArgumentException 
	 */
	public void addLoot(int i){ 
		if ((score + i) >= 0){
			score += i;
			
		}
		else {
			throw new IllegalArgumentException ("score must not be < 0");
		}
	}
	
	/**
	 * Adds a ship to the shiplist.
	 * @param ship	ship which should be added
	 * @throw NullPointerException if the ship is null
	 */
	public void addShip(Ship ship){
		if (ship == null){
			throw new NullPointerException ("tried to add null to shiplist");
		}
		else {
		ships.add(ship);
		shipCount += 1;
		}
	}
	
	/**
	 * Deletes a ship from the shiplist
	 * @param ship	 ship which should be deleted
	 * @throws NullPointerException if the ship or the shiplist is null
	 */
	public void deleteShip(Ship ship){
		if (ships.isEmpty()){
			throw new NullPointerException ("tried to delete from an empty shiplist");
		}
		if (ship == null){
			throw new NullPointerException ("tried to delete null from shiplist");
		}
		else {
		if (ships.remove(ship)){
		
			shipCount = shipCount -1;
		}
		
		}
	}
	
	/**
	 * Gets the list of commands of this team (tactic).
	 * @return	list of commands of this team
	 */
	public List<Command> getCommands(){
		return commands;
	}
	
	/**
	 * Gets the score of this team.
	 * 
	 * @return the score of this team
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Gets the name of this team.
	 * @return name of this team
	 */
	public char getName() {
		return name;
	}

	/**
	 * Gets the number of ships which belong to this team.
	 * @return number of ships
	 */
	public int getShipCount() {
		return shipCount;
	}
	
	/**
	 * Gets the list of ships of this team.
	 * @return list of ships
	 */
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
	
	@Override
	public int hashCode()
	{
		return name;
	}
}
