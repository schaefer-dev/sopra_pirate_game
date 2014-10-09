package de.unisaarland.cs.st.pirates.group5.commands;

import de.unisaarland.cs.st.pirates.group5.controller.Command;
import de.unisaarland.cs.st.pirates.group5.model.Field;
import de.unisaarland.cs.st.pirates.group5.model.Ship;

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
	public void execute(Ship ship) {
		if (direction < 7){
			Field field= ship.getPosition().getNeigbour(ship.relativeToAbsoluteDirection(direction));
				if (field.getTreasure() != null){
					int value = field.getTreasure().getValue();
					int load = ship.getLoad();
					if(load < 4)
						ship.changeMoral(2);
					if(load == 4){
						ship.setPC(elsePC);
						return;
					}
					if(value + load >= 4){
						ship.setLoad(4);
						field.exchangeTreasure(-(4-load));
					}else{
						ship.setLoad(value + load);
						field.exchangeTreasure(-value);
					}
				}else ship.setPC(elsePC);
		}
		else
			throw new IllegalStateException("direction pickup not valid");
	}
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof Pickup)
		{
			Pickup other = (Pickup) o;
			if(other.direction == direction && elsePC == other.elsePC)
				return true;
		}
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return elsePC;
	}


}
