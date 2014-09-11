package commands;

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
		// TODO Auto-generated method stub

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
