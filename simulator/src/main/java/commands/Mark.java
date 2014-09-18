package commands;

import model.Ship;
import controller.Command;
/**
 * The Mark class
 * This class implements Command and describes the action of a ship placing a buoy of a special type(0-5) 
 * and of the same team as the ship itself on a field (water or own base).
 * @author Janna
 *
 */
public class Mark implements Command {

	/**The type(0-5) of the buoy which should be placed.*/
	private int type;
	
	/**
	 * Constructor
	 * Creates a new object of this class.
	 * @param type	The type(0-5) of the buoy which should be placed.
	 */
	public Mark(int type){
		this.type = type;
	}
	
	/**
	 * Executes the command by calling the method "placeBuoy(Type type, Team team)" in the class field.
	 * The check if the buoy could be placed there happens in field.
	 * @param ship 	The ship which should place a buoy on its current field.
	 */
	@Override
	public void execute(Ship ship) {
		ship.getPosition().placeBuoy(type, ship.getTeam());
		
	}
	
	
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof Mark)
		{
			Mark other = (Mark) o;
			if(other.type == type)
				return true;
		}
		return false;
	}

}
