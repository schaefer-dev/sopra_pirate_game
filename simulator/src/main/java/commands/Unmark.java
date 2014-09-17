package commands;

import java.util.List;

import model.Buoy;
import model.Ship;
import controller.Command;

public class Unmark implements Command {

	private int type;
	
	public Unmark(int type){
		this.type = type;
	}
	
	@Override
	public void execute(Ship ship) {

		boolean contains = false;
		
		List<Buoy> buoys = ship.getPosition().getBuoys();
		
		if (buoys.size() == 0){ contains = false;}
		
		else {
			
		for (Buoy curr : buoys){
			if ((curr.getType() == type) && (curr.getTeam().equals(ship.getTeam()))){
				contains = true;
			}
		}
		}
		
		
		
		if (contains){
			ship.getPosition().deleteBuoy(ship.getTeam(), type);
		}
		//else {
		//	ship.getPosition().deleteBuoy(null, 0);}

	}
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof Unmark)
		{
			Unmark other = (Unmark) o;
			if(other.type == type)
				return true;
		}
		return false;
	}

}
