package commands;


import model.Ship;
import controller.Command;

/**
 * The Unmark class
 * This class implements Command and describes the action of deleting a buoy of the same team from a field.
 * @author Janna
 *
 */
public class Unmark implements Command {

	/** the type of the buoy which should me deleted (between 0 and 5)*/
	private int type;
	
	/** Constructor
	 * Creates a new object of this class.
	 * @param type	the type of the buoy which should be deleted (between 0 and 5).
	 */
	public Unmark(int type){
		this.type = type;
	}
	
	/**
	 * Executes the command by calling the method "deleteBuoy(Team team, Type type)" in the class field.
	 * The check if the buoy is on this field and can be deleted happens in this method in field.
	 * @param ship 	The ship which should delete a buoy.
	 */
	@Override
	public void execute(Ship ship) {

	ship.getPosition().deleteBuoy(ship.getTeam(), type);
		

	}
	
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof Unmark)
		{
			Unmark other = (Unmark) o;
			if(other.type == type)
				return true;
		}
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return type;
	}


}
