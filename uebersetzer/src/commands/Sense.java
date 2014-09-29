package src.commands;


import src.main.Command;

public class Sense implements Command {

	private int direction;
	
	public Sense(int direction){
		this.direction = direction;
	}
	
	
		
		
	@Override
	public String toString(){
		return "sense " + String.valueOf(direction);
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Sense){
			Sense other = (Sense) o;
			if(other.direction == direction)
				return true;
		}
		return false;	
	}
}
