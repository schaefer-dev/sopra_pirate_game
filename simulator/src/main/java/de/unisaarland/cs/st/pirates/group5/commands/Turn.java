package de.unisaarland.cs.st.pirates.group5.commands;

import de.unisaarland.cs.st.pirates.group5.controller.Command;
import de.unisaarland.cs.st.pirates.group5.model.Ship;

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
	
	/**
	 * Executes the command by calling the method "changeDirection(boolean left)" in the ship class.
	 *@param ship 	The ship which should turn left or right.
	 */
	@Override
	public void execute(Ship ship) {
		ship.changeDirection(left);

	}
	
	
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof Turn)
		{
			Turn other = (Turn) o;
			if(other.left == left)
				return true;
		}
		return false;
	}
	
	@Override
	public int hashCode()
	{
		if(left)
			return 1;
		else
			return 2;
	}


}
