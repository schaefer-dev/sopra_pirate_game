package view;

import de.unisaarland.cs.st.pirates.logger.LogWriter.Entity;

public class SimpleEntity {

	private int id;
	private int x;
	private int y;
	private int value;
	private int fleet;
	private Entity entityType;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
	
	public int getFleet() {
		return fleet;
	}
	
	public void setFleet(int fleet) {
		this.fleet = fleet;
	}
	
	public Entity getEntityType() {
		return entityType;
	}
	
	public void setEntityType(Entity entityType) {
		this.entityType = entityType;
	}
}
