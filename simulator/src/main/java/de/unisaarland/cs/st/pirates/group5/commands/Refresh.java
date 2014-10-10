package de.unisaarland.cs.st.pirates.group5.commands;

import de.unisaarland.cs.st.pirates.group5.controller.Command;
import de.unisaarland.cs.st.pirates.group5.model.Field;
import de.unisaarland.cs.st.pirates.group5.model.FieldType;
import de.unisaarland.cs.st.pirates.group5.model.Ship;

/**
 * The Refresh class
 * This class implements Command.class and represents the action of changing the shipmoral to 4.
 * This is only possible if the refresh direction points to a field with FieldType ProvisionIsland.
 * If this is not the case, the PC changes to the elsePC.
 * @author Janna
 *
 */

public class Refresh implements Command {

	private int direction;
	private int elsePC;
	
	/**
	 * Constructor
	 * 
	 * creates a new Object of this class.
	 * @param dir 	relative direction to point to the field to refresh (0-6)
	 * @param pc	elsePC if refresh is not successful
	 * @throws IllegalArgumentException if direction is not between 0 and 6.
	 */
	public Refresh(int dir, int pc){
		if(pc<0 || pc >= 2000 || dir> 6 || dir<0)
			throw new IllegalArgumentException("PC or refresh direction are out of range.");
		this.direction = dir;
		this.elsePC = pc;
	}
	
	/**
	 * Executes the command by changing the relative direction into an absolute direction and 
	 * getting the field in this direction. If the FieldType is ProvisionIsland, the moral of the 
	 * ship is changed to 4, else the pc is changed to the elsePC.
	 * @param ship 	the ship which should refresh
	 */
	@Override
	public void execute(Ship ship) {
		int dir = ship.relativeToAbsoluteDirection(direction);
		Field neighbour = ship.getPosition().getNeigbour(dir);
		if(neighbour.getFieldType() == FieldType.ProvisionIsland)
		{
			ship.changeMoral(4);
		}
		else
		{
			ship.setPC(elsePC);
		}

	}
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof Refresh)
		{
			Refresh other = (Refresh) o;
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
