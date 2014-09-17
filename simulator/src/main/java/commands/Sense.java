package commands;

import model.Base;
import model.BoolWert;
import model.Buoy;
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
		CellType cell = null;
		
		ship.setSenseRegister(Register.sense_supply, BoolWert.False.ordinal());
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
			
			int direction = otherShip.getSenseRegister(Register.sense_shipdirection);
			ship.setSenseRegister(Register.sense_shipdirection, direction);
			
			int condition = otherShip.getSenseRegister(Register.sense_shipcondition);
			ship.setSenseRegister(Register.sense_shipcondition, condition);
		}
		else{
			ship.setSenseRegister(Register.sense_shiptype, ShipType.Undefined.ordinal());
			ship.setSenseRegister(Register.sense_shipdirection, Ship.undefinedInt);
			ship.setSenseRegister(Register.sense_shipcondition, Ship.undefinedInt);
			ship.setSenseRegister(Register.sense_shiploaded, BoolWert.False.ordinal());
		}

		
		ship.setSenseRegister(Register.sense_marker0, BoolWert.False.ordinal());
		ship.setSenseRegister(Register.sense_marker1, BoolWert.False.ordinal());
		ship.setSenseRegister(Register.sense_marker2, BoolWert.False.ordinal());
		ship.setSenseRegister(Register.sense_marker3, BoolWert.False.ordinal());
		ship.setSenseRegister(Register.sense_marker4, BoolWert.False.ordinal());
		ship.setSenseRegister(Register.sense_marker5, BoolWert.False.ordinal());
		ship.setSenseRegister(Register.sense_enemymarker, BoolWert.False.ordinal());
		
		for(Buoy buoy: field.getBuoys()){
			if(buoy.getTeam().equals(ship.getTeam()))
				ship.setSenseRegister(Register.values()[Register.sense_marker0.ordinal() + buoy.getType()], BoolWert.True.ordinal());
			else
				ship.setSenseRegister(Register.sense_enemymarker, BoolWert.True.ordinal());
		}
	}
	
	@Override
	public boolean equals (Object o){
		if(o instanceof Sense){
			Sense other = (Sense) o;
			if(other.direction == direction)
				return true;
		}
		return false;
	}

}
