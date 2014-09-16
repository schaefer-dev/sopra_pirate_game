package model;

public abstract class Entity {

	protected final int id;
	
	protected Entity(int id){
		if (id >= 0){
			this.id = id;
		}
		else{
			throw new IllegalArgumentException ("id must be greater 0");
		}
	}
}
