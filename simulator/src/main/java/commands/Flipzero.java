package commands;

import java.util.Random;

import controller.Command;
import model.Ship;

public class Flipzero implements Command {
	
	private int p;
	private int elsePC;
	
	public Flipzero(int p, int pc){
		if(p < 1 || pc < 0 || pc > 1999) throw new IllegalArgumentException();
		
		this.p = p;
		this.elsePC = pc;
	}
	
	public int getRandom(){
		return p;
	}
	
	@Override
	public void execute(Ship ship){
		if(ship == null) throw new NullPointerException();
		
		Random random = ship.getPosition().getMap().getRandom();
		if(random.nextInt(p) > 0)
			ship.setPC(elsePC);
	}
	@Override
	public boolean equals (Object o){
		if(o instanceof Flipzero){
			Flipzero other = (Flipzero) o;
			if(other.p == p && elsePC == other.elsePC)
				return true;
		}
		
		return false;
	}
}
