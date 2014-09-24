package commands;

import model.Field;
import model.Ship;
import controller.Command;

public class Pickup implements Command {

	private int direction;
	private int elsePC;
	
	public Pickup (int dir, int pc){
		this.direction = dir;
		this.elsePC = pc;
	}
	
	@Override
	public void execute(Ship ship) {
		if (direction < 7){
			Field field= ship.getPosition().getNeigbour(ship.relativeToAbsoluteDirection(direction));
				if (field.getTreasure() != null){
					int value = field.getTreasure().getValue();
					int load = ship.getLoad();
					if(load < 4)
						ship.changeMoral(2);
					if(load == 4){
						ship.setPC(elsePC);
						return;
					}
					if(value + load >= 4){
						ship.setLoad(4);
						field.exchangeTreasure(-(4-load));
					}else{
						ship.setLoad(value + load);
						field.exchangeTreasure(-value);
					}
				}else ship.setPC(elsePC);
		}
		else
			throw new IllegalStateException("direction pickup not valid");
	}
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof Pickup)
		{
			Pickup other = (Pickup) o;
			if(other.direction == direction && elsePC == other.elsePC)
				return true;
		}
		return false;
	}

}
