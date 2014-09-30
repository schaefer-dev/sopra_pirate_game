package commands;

import main.Command;
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
	
	
	@Override
	public String toString(){
		return "mark " + String.valueOf(type); 
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Mark){
			Mark other = (Mark) o;
			if(other.type == type)
				return true;
		}
		return false;	
	}

}
