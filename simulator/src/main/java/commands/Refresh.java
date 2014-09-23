package commands;

import model.Field;
import model.FieldType;
import model.Ship;
import controller.Command;

public class Refresh implements Command {

	private int direction;
	private int elsePC;
	
	public Refresh(int dir, int pc){
		if(pc<0 || pc >= 2000 || dir> 6 || dir<0)
			throw new IllegalArgumentException("PC or refresh direction are out of range.");
		this.direction = dir;
		this.elsePC = pc;
	}
	
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

}
