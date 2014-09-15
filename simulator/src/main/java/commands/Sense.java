package commands;

import model.Base;
import model.BoolWert;
import model.CellType;
import model.Field;
import model.Register;
import model.Ship;
import model.ShipType;
import controller.Command;

public class Sense implements Command {

	private int direction;
	
	public Sense(int direction){
		this.direction = direction;
	}
	
	@Override
	public void execute(Ship ship) {
		if(ship == null) throw new NullPointerException();
		
		Field field = ship.getPosition().getNeigbour(direction);
		
		ship.setSenseRegister(Register.sense_supply, BoolWert.False.ordinal());
		
		CellType cell = null;
		switch(field.getFieldType()){
			case Water:
				cell = CellType.Empty;
				break;
			case ProvisionIsland:
				ship.setSenseRegister(Register.sense_supply, BoolWert.True.ordinal());
			case Island:
				cell = CellType.Island;
				break;
			case Base:
				Base base = (Base)field;
				if(base.getTeam().equals(ship.getTeam()))
					cell = CellType.Home;
				else
					cell = CellType.EnemyHome;
				break;
		}
		ship.setSenseRegister(Register.sense_celltype, cell.ordinal());
		
		if(field.getTreasure() != null)
			ship.setSenseRegister(Register.sense_treasure, BoolWert.True.ordinal());
		else
			ship.setSenseRegister(Register.sense_treasure, BoolWert.False.ordinal());
		
		Ship otherShip;
		if((otherShip = field.getShip()) != null){
			if(otherShip.getTeam().equals(ship.getTeam()))
				ship.setSenseRegister(Register.sense_shiptype, ShipType.Friend.ordinal());	
			else
				ship.setSenseRegister(Register.sense_shiptype, ShipType.Enemy.ordinal());
			
			
		}
		else{
			ship.setSenseRegister(Register.sense_shiptype, ShipType.Undefined.ordinal());
			ship.setSenseRegister(Register.sense_shipdirection, Ship.undefined);
		}
			
		

	}
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof Sense)
		{
			Sense other = (Sense) o;
			if(other.direction == direction)
				return true;
		}
		return false;
	}

}
