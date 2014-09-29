package src.commands;

import src.main.Command;


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
		
	
}
