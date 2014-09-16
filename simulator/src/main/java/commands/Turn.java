package commands;

import model.Ship;
import controller.Command;

public class Turn implements Command {

	private boolean left;
	
	public Turn(boolean left){
		this.left = left;
	}
	
	@Override
	public void execute(Ship ship) {
		ship.changeDirection(left);

	}
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof Turn)
		{
			Turn other = (Turn) o;
			if(other.left == left)
				return true;
		}
		return false;
	}

}
