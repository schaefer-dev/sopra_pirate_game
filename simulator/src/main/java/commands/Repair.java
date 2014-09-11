package commands;

import model.Ship;
import controller.Command;

public class Repair implements Command {

	private int elsePC;
	
	public Repair(int pc){
		this.elsePC = pc;
	}
	
	@Override
	public void execute(Ship ship) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof Repair)
		{
			Repair other = (Repair) o;
			if(elsePC == other.elsePC)
				return true;
		}
		return false;
	}

}
