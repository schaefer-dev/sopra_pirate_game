package commands;

import model.Field;
import model.FieldType;
import model.Ship;
import model.Water;
import controller.Command;

public class Drop implements Command {
	
	public Drop(){
		
	}
	
	@Override
	public void execute(Ship ship) {
		Field field = ship.getPosition();
		if(!(field instanceof Water))
			throw new IllegalStateException("Ship is not postitioned on water field.");
		int value = ship.getLoad();
		ship.setLoad(0);
		field.exchangeTreasure(value);
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

}
