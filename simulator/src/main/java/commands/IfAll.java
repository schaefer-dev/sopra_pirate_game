package commands;

import java.util.List;

import model.Ship;
import controller.Command;
import controller.Comparison;

public class IfAll implements Command {

	private List<Comparison> clauses;
	private int elsePC;
	
	public IfAll(List<Comparison> clauses, int pc){
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
		if(o instanceof IfAll)
		{
			IfAll other = (IfAll) o;
			if(other.elsePC == elsePC && other.clauses.equals(clauses))
				return true;
		}
		return false;
	}

}
