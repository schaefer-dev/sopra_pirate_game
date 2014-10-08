package view;

import view.utility.Team;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Entity;

public class SimpleEntity implements Comparable<SimpleEntity> {

	private int id;
	private int x;
	private int y;
	private int value;
	private Team fleet;
	private Entity entityType;
	
	public SimpleEntity(int id){
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public Team getFleet() {
		return fleet;
	}
	
	public void setFleet(Team fleet) {
		this.fleet = fleet;
	}
	
	public Entity getEntityType() {
		return entityType;
	}
	
	public void setEntityType(Entity entityType) {
		this.entityType = entityType;
	}

	@Override
	public int compareTo(SimpleEntity o) {
		if(fleet.getID() < o.getFleet().getID())
			return -1;
		if(fleet.getID() == o.getFleet().getID())
			return 0;
		else
			return 1;
	}
}
