package commands;

import model.Ship;
import controller.Command;

public class Goto implements Command {

	private int pc;
	
	public Goto(int pc) {
		if(pc < 0 || pc > 2000) throw new IllegalArgumentException();
		
		this.pc = pc;
	}
	
	@Override
	public void execute(Ship ship) {
		if(ship == null) throw new NullPointerException();
		
		ship.setPC(pc);
	}
	@Override
	public boolean equals (Object o){
		if(o instanceof Goto){
			if(((Goto) o).pc == pc)
				return true;
		}
		
		return false;
	}

}
