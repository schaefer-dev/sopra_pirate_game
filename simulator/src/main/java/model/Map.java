package model;

import java.util.List;
import java.util.Random;

import de.unisaarland.cs.st.pirates.logger.LogWriter;

/***
 * This class holds information about all the fields, ships and kraken on the map. Additionally, it holds a
 * reference to the used LogWriter class, which is needed by the subordinate fields and provides functionality
 * to give new ships and other entities on the map a unique ID.
 * 
 * @author Rafael Theis
 *
 */
public class Map {

	private int height;
	private int width;
	private Field[][] fields;
	private int nextFreeActorID;
	private int nextFreeEntityID;
	protected LogWriter logWriter;
	private Ship firstShip;
	private List<Kraken> kraken;
	
	public final Random random;
	
	public Map(Random random, LogWriter logWriter){
		this.random = random;
		this.logWriter = logWriter;
		
		nextFreeActorID = 0;
		nextFreeEntityID = 0;
	}
	
	/***
	 * Returns direct neighbor of a field according to given parameters
	 * 
	 * @param field The initial field
	 * @param direction Direction of adjacent neighbor
	 * @throws NullPointerException field is null
	 * @throws IllegalArgumentException direction is  smaller than 0 or greater than 6
	 * @return The adjacent field
	 */
	public Field getNeighbour(Field field, int direction){		
		if(field == null) throw new NullPointerException();
		
		int x = field.getX();
		int y = field.getY();
		
		if((y % 2) == 1)
			x++;
		
		switch(direction){
			case 0:
				return fields[mod(field.getX()+1, width)][mod(y, height)];
			case 1:
				return fields[mod(x, width)][mod(y+1, height)];
			case 2:
				return fields[mod(x-1, width)][mod(y+1, height)];
			case 3:
				return fields[mod(field.getX()-1, width)][mod(y, height)];
			case 4:
				return fields[mod(x-1, width)][mod(y-1, height)];
			case 5:
				return fields[mod(x, width)][mod(y-1, height)];
			case 6:
				return field;
			default:
				throw new IllegalArgumentException();
		}
	}
	
	/***
	 * Gives a unique ID for new ships
	 * @return new ship ID
	 */
	public int giveNewActorID(){	
		return nextFreeActorID++;	
	}
	
	/***
	 * Gives a unique ID for new treasures, buoys and kraken
	 * @return new entity ID
	 */
	public int giveNewEntityID(){
		return nextFreeEntityID++;	
	}
	
	public LogWriter getLogWriter(){
		return logWriter;
	}
	
	/***
	 * The ships on the map are represented in the form of a linked list where the ships always hold information about their predecessors and successors.
	 * This method sets the first ship of this list and should only be called once. Adding and removing new ships is possible in the ship class
	 * itself and is done by the MapGenerator class.
	 * @param ship Ship that shall become first element of the ship list
	 * @see MapGenerator, Ship
	 */
	public void setFirstShip(Ship ship){
		firstShip = ship;
	}
	
	public Ship getFirstShip(){
		return firstShip;
	}
	
	public List<Kraken> getKraken(){
		return kraken;
	}
	
	/***
	 * Returns a random object which was built with the seed that was committed to the main class initially. It is the only object in the project
	 * with which random generations should be implemented (e.g. in Flipzero)
	 */
	public Random getRandom(){
		return random;
	}
	
	/***
	 * This method is used to initialize the map. This can't be done directly in the constructor because the committed fields already need
	 * a reference to this class during their construction. Therefore creation and initialization of the map need to be time-displaced.
	 * @param field Array of all the fields of the map
	 * @param kraken List of all the kraken on the map
	 * @throws NullPointerException field or kraken are null
	 * @throws IllegalArgumentException width or height of field array are smaller than 2 or greater than 200
	 */
	public void setMapValues(Field[][] field, List<Kraken> kraken){
		if(field == null || kraken == null) throw new NullPointerException();
		if(field.length < 2 || field[0].length < 2 || field.length > 200 || field[0].length > 200) throw new IllegalArgumentException();
		
		this.fields = field;
		this.kraken = kraken;
		
		width  = fields.length;
		height = fields[0].length;
	}
	
	private int mod(int a, int b){
		return (a%b + b) % b;
	}
}
