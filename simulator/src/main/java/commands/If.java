package commands;

import model.Ship;
import controller.Command;
import controller.Comparison;

public class If implements Command {

	private Comparison comparison;
	private int elsePC;
	
	public If (Comparison comparison, int pc){
		this.comparison = comparison;
		this.elsePC = pc;
	}
	
	@Override
	public void execute(Ship ship) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof If)
		{
			If other = (If) o;
			if(other.elsePC == elsePC && other.comparison.equals(comparison))
				return true;
		}
		return false;
	}

}
