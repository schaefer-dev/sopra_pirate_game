package commands;

import model.Ship;
import controller.Command;

public class Mark implements Command {

	private int type;
	
	public Mark(int type){
		this.type = type;
	}
	
	@Override
	public void execute(Ship ship) {
		// TODO Auto-generated method stub

	}

}
