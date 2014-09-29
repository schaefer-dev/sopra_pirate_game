package model;

import java.util.List;

import de.unisaarland.cs.st.pirates.logger.LogWriter.Entity;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Key;

/***
 * This class represents a single water field on the map and holds information about it's position as well as all the entities that are currently
 * located on it. It provides game functionality that is directly linked to water like placing buoys. 
 * It extends the abstract class Field
 * 
 * @author Rafael Theis
 * @see {@link Field}
 */
public class Water extends Field {

	public Water(Map map, int x, int y, Kraken kraken) {
		super(map, x, y, null);
		
		if(kraken != null){
			this.kraken = kraken;
			kraken.setField(this);
		}
	}

	@Override
	public boolean placeBuoy(int type, Team team) {
		
		boolean contains = false;
		
		List<Buoy> buoys = this.getBuoys();
		
		if (buoys.size() == 0) { contains = false;}
		
		else {
			for (Buoy curr : buoys){
				if ((curr.getType() == type) && (curr.getTeam().equals(team)))
					contains = true;
			}
		}
		
		if (contains)
			return false;
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
			
			provideLogger().create(Entity.BUOY, newbuoy.id, keyarray, typearray);
			return true;
		}
	}
	
	
	@Override
	public void deleteBuoy(Team team, int value){
		if(team == null) throw new NullPointerException();
		if(value < 0 || value > 6) throw new IllegalArgumentException();
		
		for(Buoy buoy: buoys){
			if(buoy.getTeam().equals(team) && buoy.getType() == value){
				buoys.remove(buoy);
				provideLogger().destroy(Entity.BUOY, buoy.id);
				break;
			}
		}
	}

	@Override
	public FieldType getFieldType() {
		return FieldType.Water;
	}
}
