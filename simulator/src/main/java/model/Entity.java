package model;

/**
 * This abstract class consists of the classes buoy and treasure. The id of a entity is unique. 
 * 
 * @author Janna
 *
 */

public abstract class Entity {
	
	/** Id of the entity*/
	protected final int id;
	
	
	/**
	 * Constructor sets id.
	 * 
	 * @param id 	The id the new entity should have.
	 * @throws 		IllegalArgumentEception if the id is < 0
	 */
	protected Entity(int id){
		if (id >= 0){
			this.id = id;
		}
		else{
			throw new IllegalArgumentException ("id must be greater 0");
		}
	}


	public int getId() {
		return id;
	}
	
	
}
