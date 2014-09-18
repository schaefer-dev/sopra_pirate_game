package model;

/**
 * The Kraken class
 * 
 * This class implements the object Kraken. A  kraken has a current field, an id and the ability to move every 20 rounds in a random direction.
 * If a ship moves on a field which is taken by a kraken the ship looses condition. Nevertheless the move is successful and both be now located
 * on the same field. Otherwise if a kraken moves on a field where a ship already is, nothing happens and both are now on the same field.
 * On the same field there can only be one object of the class Kraken.
 * @author Janna
 *
 */
public class Kraken {

	private Field field;
	private final int id;
	
	/**
	 * Constructor
	 * 
	 * Creates a new object of this class.
	 * @param id		The id this kraken should have.
	 * @param field		The field on which the kraken is placed.
	 */
	public Kraken(int id, Field field){
		this.id = id;
		this.field=field;
		
	}
	
	/**
	 * This method implements the random move of a kraken. This happens every 20 rounds.
	 * The random direction is created by the method "getRandom().nextInt(6)" in the class map.
	 * This direction must be between 0 and 5."getNeighbour(int i)" in field returns the detination field.
	 * This field must be kind of water. With "moveKraken(field detination)" in the class field the current kraken moves.
	 * If the detination field was not kind of water, nothing happens.
	 * @throws IllegalArgumentException if the random direction is not between 0 and 5.
	 */
	public void move(){
		
		int dir = field.getMap().getRandom().nextInt(6);
		
		if (dir < 0 || dir > 5){
			throw new IllegalArgumentException ("seed is not allowed to be < 0 or > 5");
		}
		else{
		Field destination = field.getNeigbour(dir);
		
		switch (destination.getFieldType()){
		
		case Water:
			if (destination.getKraken() == null){ //no kraken on that field so kraken can move
				field.moveKraken(destination);
			}
			
		case Base:
		
		case Island: 
			
		case ProvisionIsland:
			
		}
			
		}
			
	}
	
	/**
	 * Gets the current field of the kraken.
	 * @return	the current field of the kraken.
	 */
	public Field getField() {
		return field;
	}

	/**
	 * Sets the field of the kraken.
	 * @param field the new field of the kraken
	 */
	public void setField(Field field) {
		this.field = field;
	}

	/**
	 * Returns the id of the kraken.
	 * @return id The id of this kraken.
	 */
	public int getId() {
		return id;
	}
	
	
}
