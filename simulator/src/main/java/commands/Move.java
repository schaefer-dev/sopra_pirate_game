package commands;

import model.Ship;
import controller.Command;

public class Move implements Command {

	private int elsePC;
	
	public Move(int pc){
		this.elsePC = pc;
	}
	
	@Override
	public void execute(Ship ship) {
		// TODO Auto-generated method stub

	}
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof Move)
		{
			Move other = (Move) o;
			if(other.elsePC == elsePC)
				return true;
		}
		return false;
	}

}
