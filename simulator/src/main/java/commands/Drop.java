package commands;

import model.Ship;
import controller.Command;

public class Drop implements Command {
	
	public Drop(){
		
	}
	
	@Override
	public void execute(Ship ship) {
		// TODO Auto-generated method stub

	}
	@Override
	public boolean equals(Object o)
	{
		return o instanceof Drop;
	}

}
