package model;

import java.util.List;

import de.unisaarland.cs.st.pirates.logger.LogWriter;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Entity;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Key;

public class Water extends Field {

	public Water(Map map, int x, int y, Kraken kraken) {
		super(map, x, y, null);
		this.setKraken(kraken);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean setShip(Ship ship) {
		// TODO Auto-generated method stub
		this.ship=ship;
		return false;
	}

	@Override
	public boolean placeBuoy(int type, Team team) {
		
		boolean contains = false;
		
		List<Buoy> buoys = this.getBuoys();
		
		if (buoys.size() == 0) { contains = false;}
		
		else {
			
		for (Buoy curr : buoys){
			if ((curr.getType() == type) && (curr.getTeam().equals(team))){
				contains = true;
			}
		}
		}
		
		if (contains){
			return false;
		}
		else {
			Buoy newbuoy = new Buoy(this.getMap().giveNewEntityID(), team, type);
			this.buoys.add(newbuoy);
			Key[] keyarray = new Key[4];
			int[] typearray = new int[4];
			keyarray[0] = Key.X_COORD;
			keyarray[1] = Key.Y_COORD;
			keyarray[2] = Key.FLEET;
			keyarray[3] = Key.VALUE;
			typearray[0] = this.x;
			typearray[1] = this.y;
			typearray[2] = newbuoy.getTeam().getName() - 'a';
			typearray[3] = newbuoy.getType();
			
			map.getLogWriter().create(Entity.BUOY, newbuoy.id, keyarray, typearray);
			return true;}
		
	}

	@Override
	public boolean setKraken(Kraken kraken) {
		// TODO Auto-generated method stub
		this.kraken=kraken;
		return false;
	}

	@Override
	public FieldType getFieldType() {
		// TODO Auto-generated method stub
		return FieldType.Water;
	}
}
