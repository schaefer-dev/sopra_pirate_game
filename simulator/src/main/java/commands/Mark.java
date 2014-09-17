package commands;

import java.util.List;

import model.Buoy;
import model.Ship;
import controller.Command;

public class Mark implements Command {

	private int type;
	
	public Mark(int type){
		this.type = type;
	}
	
	@Override
	public void execute(Ship ship) {
		ship.getPosition().placeBuoy(type, ship.getTeam());
		
	}
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof Mark)
		{
			Mark other = (Mark) o;
			if(other.type == type)
				return true;
		}
		return false;
	}

}
