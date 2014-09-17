package commands;

import java.util.List;

import model.Ship;
import controller.Command;
import controller.Comparison;

public class IfAll implements Command {

	private List<Comparison> clauses;
	private int elsePC;
	
	public IfAll(List<Comparison> clauses, int pc){
		if(pc<0 || pc> 1999)
		{
			throw new IllegalArgumentException("PC cannot be set to " + pc);
		}
		if(clauses == null || clauses.size() == 0)
			throw new IllegalArgumentException("IfAll needs at least one comparison to work.");
		this.clauses = clauses;
		this.elsePC = pc;
	}
	
	@Override
	public void execute(Ship ship) {
		for(Comparison comp : clauses)
		{
			if(!comp.eval(ship))
			{
				ship.setPC(elsePC);
				return;
			}
		}
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
