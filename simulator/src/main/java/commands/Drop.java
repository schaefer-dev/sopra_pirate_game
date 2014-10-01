package commands;

import model.Field;
import model.FieldType;
import model.Ship;
import model.Water;
import controller.Command;

/**
 * Drop-Command class, no attributes, only for execute method
 * @author danielschaefer
 *
 */
public class Drop implements Command {
	
	public Drop(){
		
	}
	/**
	 * sets shipload to zero and calls dropzero method in field, which does the rest except moral setting
	 * which has do be done here: Moral changes if value was actually dropped and when it was NOT dropped
	 * onto a base field (must be its own base if the ship is standing on a base field)
	 * 
	 * @throws IllegalStateException if ship is not on a water field
	 * @param ship	the ship which executed the drop
	 * 
	 */
	@Override
	public void execute(Ship ship) {
		Field field = ship.getPosition();
		if(!(field instanceof Water))
			throw new IllegalStateException("Ship is not postitioned on water field.");
		int value = ship.getLoad();
		ship.setLoad(0);
		field.dropTreasure(value);
		if(field.getFieldType() != FieldType.Base && value != 0)
		{
			ship.changeMoral(-2);
		}
	}
	@Override
	public boolean equals(Object o)
	{
		return o instanceof Drop;
	}
	@Override
	public int hashCode()
	{
		return 1;
	}

}
