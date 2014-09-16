package commands;

import java.util.List;

import model.Ship;
import controller.Command;
import controller.Comparison;

public class IfAny implements Command {

	private List<Comparison> clauses;
	private int elsePC;
	
	public IfAny(List<Comparison> clauses,int pc){
		if(pc<0 || pc> 1999)
		{
			throw new IllegalArgumentException("PC cannot be set to " + pc);
		}
		this.clauses = clauses;
		this.elsePC = pc;
	}
	
	@Override
	public void execute(Ship ship) {
		boolean b = false;
		for(Comparison comp : clauses)
		{
			if(comp.eval(ship))
			{
				b = true;
				break;
			}
		}
		if(!b)
		{
			ship.setPC(elsePC);
		}

	}
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof IfAny)
		{
			IfAny other = (IfAny) o;
			if(other.elsePC == elsePC && other.clauses.equals(clauses))
				return true;
		}
		return false;
	}

}
