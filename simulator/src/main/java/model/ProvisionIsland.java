package model;

/***
 * This class represents a provision island on the map and holds information about it's position as well as all the entities that are currently
 * located on it. It provides game functionality that is directly linked to provision islands. 
 * It extends the class Island
 * 
 * @author Rafael Theis
 * @see Field, Island
 */
public class ProvisionIsland extends Island {

	public ProvisionIsland(Map map, int x, int y) {
		super(map, x, y, null);
	}

	@Override
	public FieldType getFieldType() {
		return FieldType.ProvisionIsland;
	}
}
