package commands;


import main.Command;

public class Sense implements Command {

	private int direction;
	
	public Sense(int direction){
		this.direction = direction;
	}
	
	
		
		
	@Override
	public String toString(){
		return "sense " + String.valueOf(direction);
	}
}
