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
		
		boolean contains = false;
		
		List<Buoy> buoys = ship.getPosition().getBuoys();
	
		//Buoy testbuoy = new Buoy(0, ship.getTeam(), type);
		
		if (buoys.size() == 0){ contains = true;}
		
		else {
			
		for (Buoy curr : buoys){
			if ((curr.getType() == type) && (curr.getTeam().equals(ship.getTeam()))){
				contains = true;
			}
		}
		
		if (contains){
			ship.getPosition().placeBuoy(type, ship.getTeam());
		}
		else {
		ship.getPosition().placeBuoy(0, null);}
		}
		System.out.println(contains);
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
