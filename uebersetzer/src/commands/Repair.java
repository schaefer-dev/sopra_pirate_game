package src.commands;

import src.main.Command;

/**
 * The Repair class
 * 
 * This class implements Command and describes the action of repairing a ship. To repair a ship, the teamscore of this ship must be >=2
 * because repairing a ship costs 2 points. Repairing means setting the shipcondition to the highest number (3).
 * @author Janna
 *
 */
public class Repair implements Command {

	private int elsePC;
	
	/**
	 * Constructor
	 * Creates an object of this class. If repairing is not successful the pc of the ship is set to the value of the parameter.
	 * @param pc the new pc of the ship if repairing is not successful.
	 */
	public Repair(int pc){
		this.elsePC = pc;
	}
	
	
	
	@Override
	public String toString(){
		return "repair" + " else " + String.valueOf(elsePC);
	}

}
