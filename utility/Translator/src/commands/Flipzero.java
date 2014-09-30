package commands;

import main.Command;


public class Flipzero implements Command {
	
	private int p;
	private int elsePC;
	
	public Flipzero(int p, int pc){
		if(p < 1 || pc < 0 || pc > 1999) throw new IllegalArgumentException();
		
		this.p = p;
		this.elsePC = pc;
		
	}
	
	@Override
	public String toString(){
		String res = "flipzero " + String.valueOf(p) + " else " + String.valueOf(elsePC);
		return res;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Flipzero){
			Flipzero other = (Flipzero) o;
			if(other.p == p && other.elsePC == elsePC)
				return true;
		}
		return false;
	}
		
	
}
