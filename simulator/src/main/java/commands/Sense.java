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

}
