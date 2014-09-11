package commands;

import model.Ship;
import controller.Command;

public class Goto implements Command {

	private int pc;
	
	public Goto(int pc){
		this.pc = pc;
	}
	
	@Override
	public void execute(Ship ship) {
		// TODO Auto-generated method stub

	}
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof Goto)
		{
			if(((Goto) o).pc == pc)
				return true;
		}
		return false;
	}

}
