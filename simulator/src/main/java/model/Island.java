package model;

/***
 * This class represents a island on the map and holds information about it's position as well as all the entities that are currently
 * located on it. It provides game functionality that is directly linked to islands. 
 * It extends the abstract class Field
 * 
 * @author Rafael Theis
 * @see {@link Field}
 */
public class Island extends Field {

	public Island(Map map, int x, int y, Treasure treasure) {
		super(map, x, y, null);
		
		this.treasure = treasure;
	}

	
	@Override
	public boolean setShip(Ship ship) {
		return false;
	}
	
	@Override
	public boolean moveShip(Field destination) {
		return false;
	}

	@Override
	public boolean placeBuoy(int type, Team team) {
		if(team == null) throw new NullPointerException();
		if(type < 0 || type > 6) throw new IllegalArgumentException();
		
		return false;
	}
	
	@Override
	public void deleteBuoy(Team team, int value){
		if(team == null) throw new NullPointerException();
		if(value < 0 || value > 6) throw new IllegalArgumentException();
		
		return;
	}

	@Override
	public boolean setKraken(Kraken kraken) {
		return false;
	}

	@Override
	public boolean moveKraken(Field destination) {
		return false;
	}

	@Override
	public FieldType getFieldType() {
		return FieldType.Island;
	}
}
