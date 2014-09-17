package commands;

import model.Field;
import model.Ship;
import controller.Command;

public class Pickup implements Command {

	private int direction;
	private int elsePC;
	
	public Pickup (int dir, int pc){
		this.direction = dir;
		this.elsePC = pc;
	}
	
	@Override
	public void execute(Ship ship) {
		Field field= ship.getPosition().getNeigbour(ship.relativeToAbsoluteDirection(direction));
		if (field.getTreasure() != null){
				int loaded = ship.getLoad();
				int value = 4 - loaded;
				field.exchangeTreasure(value);
				if(field.getTreasure().getValue() >= value)
					ship.setLoad(value + loaded);
				else
					ship.setLoad(loaded + field.getTreasure().getValue());
		}else ship.setPC(elsePC);
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

}
