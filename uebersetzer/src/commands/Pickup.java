package commands;

import main.Command;

/**
 * An instance of this class will perform a single try to pickup a treasure.
 * 
 * If there is no treasure, the ships PC will be set on elsePC.
 * If there is a treasure, but the ship is already fully loaded, the ship PC will be set to the elsePC.
 * If there is a treasure and the ship is not fully loaded, the ships load will either be set 4 or the
 * current load + the current value of the found treasure, as long the sum is less than 4. 
 * The command will communicate the field the taken value, what will do with this information, whatever 
 * it wants.
 *  
 * @author Andreas
 *
 */

public class Pickup implements Command {

	private int direction;
	private int elsePC;
	
	public Pickup (int dir, int pc){
		this.direction = dir;
		this.elsePC = pc;
	}
	
	
	@Override
	public String toString(){
		return "pickup " + String.valueOf(direction) + " else " + String.valueOf(elsePC);
	}

}
