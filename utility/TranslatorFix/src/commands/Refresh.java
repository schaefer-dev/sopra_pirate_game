package commands;

import translator.Command;

public class Refresh implements Command {

	private int direction;
	private int elsePC;
	
	public Refresh(int dir, int pc){
		if(pc<0 || pc >= 2000 || dir> 6 || dir<0)
			throw new IllegalArgumentException("PC or refresh direction are out of range.");
		this.direction = dir;
		this.elsePC = pc;
	}
	
	
	@Override
	public String toString(){
		return "refresh " + String.valueOf(direction) + " else " + String.valueOf(elsePC);
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Refresh){
			Refresh other = (Refresh) o;
			if(other.direction == direction && other.elsePC == elsePC)
				return true;
		}
		return false;	
	}
}
