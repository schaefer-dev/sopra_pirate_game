package src.commands;


import src.main.Command;

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
	 * @param type	the type of the guoy which should be deleted (between 0 and 5).
	 */
	public Unmark(int type){
		this.type = type;
	}
	
	@Override
	public String toString(){
		return "unmark " + String.valueOf(type); 
	}
}
