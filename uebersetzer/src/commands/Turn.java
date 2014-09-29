package src.commands;

import src.main.Command;
/**
 * The Turn class
 *This class implements Command and describes the action of a ship turning left or right.
 *
 * @author Janna
 *lass extends Command and implements the action of a ship turning left or right.
 *
 */
public class Turn implements Command {

	/** Specifies if the ship should turn left or right*/
	private boolean left;
	
	/**Constructor
	 * 
	 * Creates a new object of this class.
	 * 
	 * @param left 		Specifies if the ship should turn left or right
	 */
	public Turn(boolean left){
		this.left = left;
	}
	
	@Override
	public String toString(){
		if(left)
			return "turn " + "left"; 
		else
			return "turn " + "right";
	}

}
