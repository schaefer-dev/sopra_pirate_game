package commands;

import model.Ship;
import controller.Command;

public class Sense implements Command {

	private int direction;
	
	public Sense(int dir){
		this.direction = dir;
	}
	
	@Override
	public void execute(Ship ship) {
		// TODO Auto-generated method stub

	}
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof Sense)
		{
			Sense other = (Sense) o;
			if(other.direction == direction)
				return true;
		}
		return false;
	}

}
