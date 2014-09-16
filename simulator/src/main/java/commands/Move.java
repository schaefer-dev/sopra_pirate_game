package commands;

import model.Field;
import model.Ship;
import controller.Command;

public class Move implements Command {

	private int elsePC;
	
	public Move(int pc){
		this.elsePC = pc;
	}
	
	@Override
	public void execute(Ship ship) {
		// TODO Auto-generated method stub
		Field shipField = ship.getPosition();
		Field targetField = shipField.getNeigbour(ship.relativeToAbsoluteDirection(0));
		
		switch(targetField.getFieldType()){
		
		case Water:
			if (targetField.getShip()==null){
				/* no ship found so ship can move + check for Kraken */
				if (targetField.getKraken()!=null)
					ship.changeCondition(-1);
				shipField.moveShip(targetField);		//TODO careful! moveShip in Field has to check if the ship is still there		
			}
			else{
				/* TODO ship on targetField -> fight + auch kraken nicht vergessen*/
			}
			break;
			
		case Base:
			
			break;
			
		case Island: 
			
			break;
			
		case ProvisionIsland:
			
			break;		
		}
	}
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof Move)
		{
			Move other = (Move) o;
			if(other.elsePC == elsePC)
				return true;
		}
		return false;
	}

}
