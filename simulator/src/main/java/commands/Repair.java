package commands;

import model.Base;
import model.FieldType;
import model.Ship;
import controller.Command;

/**
 * The Repair class
 * 
 * This class implements Command and describes the action of repairing a ship. To repair a ship, the teamscore of this ship must be >=2
 * because repairing a ship costs 2 points. Repairing means setting the shipcondition to the highest number (3).
 * @author Janna
 *
 */
public class Repair implements Command {

	private int elsePC;
	
	/**
	 * Constructor
	 * Creates an object of this class. If repairing is not successful the pc of the ship is set to the value of the parameter.
	 * @param pc the new pc of the ship if repairing is not successful.
	 */
	public Repair(int pc){
		this.elsePC = pc;
	}
	
	/**
	 * Executes the Command. First this method checks if the field of the ship is a base. Next it checks if the teamscore is >=2
	 * and if the base is the same team as the ship.
	 * If these conditions are fulfilled the teamscore is decreased (-2) and the condition of the ship is changed to 3. Also the shippause
	 * is increased (+4).
	 * If there not fulfilled the pc is set to the new value.
	 * 
	 */
	@Override
	public void execute(Ship ship) {
		if(ship.getPosition().getFieldType() == (FieldType.Base)){
			Base currbase = (Base) ship.getPosition();
			if((ship.getTeam().getScore() >= 2)&&(ship.getTeam().equals(currbase.getTeam()))){
				ship.changeCondition(3);
				ship.getPosition().dropTreasure(-2);
				ship.changePause(4);
			}
			else{
				ship.setPC(elsePC);
			}
		}
		else
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
