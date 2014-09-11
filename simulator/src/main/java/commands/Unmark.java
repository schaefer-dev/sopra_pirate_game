package commands;

import model.Ship;
import controller.Command;

public class Unmark implements Command {

	private int type;
	
	public Unmark(int type){
		this.type = type;
	}
	
	@Override
	public void execute(Ship ship) {
		// TODO Auto-generated method stub

	}
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof Unmark)
		{
			Unmark other = (Unmark) o;
			if(other.type == type)
				return true;
		}
		return false;
	}

}
