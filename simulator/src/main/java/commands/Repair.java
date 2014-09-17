package commands;

import model.FieldType;
import model.Ship;
import controller.Command;

public class Repair implements Command {

	private int elsePC;
	
	public Repair(int pc){
		this.elsePC = pc;
	}
	
	@Override
	public void execute(Ship ship) {
		if(ship.getPosition().getFieldType() == (FieldType.Base)){
			if(ship.getTeam().getScore() >= 2){
				ship.changeCondition(3);
				ship.getPosition().exchangeTreasure(-2);
			}else ship.setPC(elsePC);
		}else
			ship.setPC(elsePC);
	}
	
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof Repair)
		{
			Repair other = (Repair) o;
			if(elsePC == other.elsePC)
				return true;
		}
		return false;
	}

}
