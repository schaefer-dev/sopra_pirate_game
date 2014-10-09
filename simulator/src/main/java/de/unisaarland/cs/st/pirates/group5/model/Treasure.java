package de.unisaarland.cs.st.pirates.group5.model;

/**
 * The Treasure class
 *This class extends Entity. Treasure has an id and a value. The value can change during the game because of interaction with a ship.
 *If a ship drops its load a new treasure accrues and gets a new id.
 *
 * @author Janna
 *
 */
public class Treasure extends Entity {

	/** value of the treasure*/
	private int value;
	
	public Treasure(int id, int val) {
		super(id);
		this.value = val;
	}

	/**
	 * Gets the value of the treasure. This is always positive.
	 * @return the value of this treasure
	 */
	public int getValue(){
		return this.value;
	}
	
	/**
	 * Sets the value of this treasure. This must always be greater 0.
	 * @param newValue
	 */
	private void setValue(int newValue){
		this.value = newValue;
	}
	/**
	 * Changes the value of the treasure.
	 * 
	 * @param newValue		Specifies the modification of the old value of this treasure and can be positive or negative.
	 * @throws 				IllegalArgumentException if the value new value would be <= 0
	 */
	public void changeValue(int newValue){ 
		if ((value + newValue) > 0 ){
			setValue(value + newValue);
		}
		else {
		throw new IllegalArgumentException ("value of treasure <=0, must have been deleted already in field");
		}
		
		
	}
	
}
