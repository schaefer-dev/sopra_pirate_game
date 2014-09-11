package commands;

import java.util.List;

import model.Ship;
import controller.Command;
import controller.Comparison;

public class IfAny implements Command {

	private List<Comparison> clauses;
	private int elsePC;
	
	@Override
	public void execute(Ship ship) {
		// TODO Auto-generated method stub

	}

}
