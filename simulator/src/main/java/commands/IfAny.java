package commands;

import java.util.List;

import model.Ship;
import controller.Command;
import controller.Comparison;

public class IfAny implements Command {

	private List<Comparison> clauses;
	private int elsePC;
	
	public IfAny(List<Comparison> clauses,int pc){
		this.clauses = clauses;
		this.elsePC = pc;
	}
	
	@Override
	public void execute(Ship ship) {
		// TODO Auto-generated method stub

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
