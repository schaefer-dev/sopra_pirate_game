package de.unisaarland.cs.st.pirates.group5.model;

import java.util.LinkedList;
import java.util.List;

import de.unisaarland.cs.st.pirates.logger.LogWriter;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Entity;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Key;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Transaction;

/***
 * This class represents a single field on the map and holds information about it's position as well as all the entities that are currently
 * located on it. It provides game functionality that is directly linked to game fields like moving entities or placing buoys on it. 
 * However, it is only an abstract super class which is extended by all the concrete field classes. 
 * 
 * @author Rafael Theis
 * @see {@link Water}, {@link Island}, {@link ProvisionIsland}, {@link Base}
 */
public abstract class Field {

	protected Treasure treasure;
	protected Ship ship;
	protected Kraken kraken;
	protected List<Buoy> buoys;
	protected Map map;
	protected int x;
	protected int y;
	protected boolean hasLogWriter;
	
	
	/***
	 * @param map
	 * @param x
	 * @param y
	 * @param ship
	 * @throws NullPointerException if map is null
	 * @throws IllegalArgumentException if x,y are invalid coordinates(0 <= x,y <= 200)
	 */
	protected Field(Map map, int x, int y, Ship ship){
		if(map == null) throw new NullPointerException();
		if(x < 0 || x > 200 || y < 0 || y > 200) throw new IllegalArgumentException();
		
		this.x = x;
		this.y = y;
		this.map = map;
		this.ship = ship;
		this.buoys = new LinkedList<Buoy>();
		hasLogWriter = provideLogger() != null;
	}
	
	
	public Treasure getTreasure(){
		return treasure;
	}

	
	/***
	 * Adds or removes the committed value to the treasure on the field. If the value is positive, the method will either add the value or will
	 * create an new treasure. This depends on whether a treasure is already located on the field. If the value is negative, the method
	 * will either remove the value or will destroy the treasure(set to null). This depends on whether the abstracted value is equal to the value of the treasure.
	 * 
	 * @param value
	 * @throws IllegalArgumentException if -4 < value < 4 || - if you want to remove something and the treasure is already empty
	 * 														|| - if you want to remove more value than the treasure holds
	 * @return True, if exchange was successful || False, if exchange failed
	 */
	public boolean exchangeTreasure(int value){
		if(value < -4 || value > 4) throw new IllegalArgumentException("You can only change the treasure value by |4|");
		
		if(value == 0)
			return true;
		else if(value > 0){
			if(treasure == null){
				treasure = new Treasure(map.giveNewEntityID(), value);
				Key[] keys = {Key.X_COORD, Key.Y_COORD, Key.VALUE};
				int[] values = {x, y, value};
				if(hasLogWriter)
					provideLogger().create(Entity.TREASURE, treasure.id, keys, values);
			}
			else{
				treasure.changeValue(value);
				if(hasLogWriter)
					provideLogger().notify(Entity.TREASURE, treasure.id, Key.VALUE, treasure.getValue());
			}	
		}
		else{
			if(treasure == null) throw new IllegalArgumentException("You can't take loot from a nonexisting treasure");
			if((treasure.getValue() + value) < 0) throw new IllegalArgumentException("You are too greedy");
			
			if((treasure.getValue() + value) == 0){
				if(hasLogWriter)
					provideLogger().destroy(Entity.TREASURE, treasure.id);
				treasure = null;
			}
			else{
				treasure.changeValue(value);
				if(hasLogWriter)
					provideLogger().notify(Entity.TREASURE, treasure.id, Key.VALUE, treasure.getValue());
			}		
		}

		return true;
	}
	
	/**
	 * 
	 * 
	 * @param value
	 * @return	returns this.exchangeTreasure(value), only overwritten in base for being able to drop treasure on base if you hit a island for example
	 * @Author Daniel
	 */
	public boolean dropTreasure(int value) {
		return this.exchangeTreasure(value);
	}
	
	public Ship getShip(){
		return ship;
	}
	
	
	/***
	 * Tries to set a a ship on the field or remove it. If the committed ship isn't null and there is no ship on the field, the method
	 * will place the new ship on it. If the committed ship is null and there is a ship on the field, the method will remove the ship.
	 * 
	 * @param ship
	 * @return True, if action was successful || False, if action failed
	 */
	public boolean setShip(Ship ship){
		if(this.ship == null){
			this.ship = ship;
			this.ship.setField(this);
			return true;
		}
		else if(this.ship != null && ship == null)
			this.ship = null;
			
		return false;	
	}
	
	/***
	 * Tries to move the ship that is currently located on the field to the committed destination. It will call setShip(..) on the
	 * destination field and at success will remove the ship from the current field and log the changes of it's position. 
	 * 
	 * @param destination The destined field
	 * @throws IllegalStateException if ship is null
	 * @throws NullPointerException if destination is null
	 * @return True, if moving was successful || False, if moving failed
	 */
	public boolean moveShip(Field destination){
		if(destination == null) throw new NullPointerException();
		if(ship == null) throw new IllegalStateException();
		
		if(destination.setShip(ship)){
			if(hasLogWriter){
				Transaction trans = provideLogger().beginTransaction(Entity.SHIP, ship.getID());
				if(x != destination.getX())
					trans.set(Key.X_COORD, destination.getX());
				if(y != destination.getY())
					trans.set(Key.Y_COORD, destination.getY());			
				provideLogger().commitTransaction(trans);
			}
			
			ship = null;
			return true;
		}

		return false;
	}
	
	
	public Kraken getKraken() {
		return kraken;
	}
	
	
	/***
	 * Tries to set a a kraken on the field or remove it. If the committed kraken isn't null and there is no kraken on the field, the method
	 * will place the new kraken on it. If the committed kraken is null and there is a kraken on the field, the method will remove the kraken.
	 * 
	 * @param kraken
	 * @return True, if action was successful || False, if action failed
	 */
	public boolean setKraken(Kraken kraken){
		if(this.kraken == null){
			this.kraken = kraken;
			this.kraken.setField(this);
			return true;
		}
		else if(this.kraken != null && kraken == null)
			this.kraken = null;
		
		return false;
	}
	
	/***
	 * Tries to move the kraken that is currently located on the field to the committed destination. It will call setKraken(..) on the
	 * destination field and at success will remove the kraken from the current field and log the changes of it's position. 
	 * 
	 * @param destination The destined field
	 * @throws IllegalStateException if kraken is null
	 * @throws NullPointerException if destination is null
	 * @return True, if moving was successful || False, if moving failed
	 */
	public boolean moveKraken(Field destination){
		if(destination == null) throw new NullPointerException();
		if(kraken == null) throw new IllegalStateException();
		
		if(destination.setKraken(kraken)){
			Transaction trans = null;
			if(hasLogWriter)
				trans = provideLogger().beginTransaction(Entity.KRAKEN, kraken.getId());
			if(x != destination.getX() && hasLogWriter)
				trans.set(Key.X_COORD, destination.getX());
			if(y != destination.getY() && hasLogWriter)
				trans.set(Key.Y_COORD, destination.getY());
			if(hasLogWriter)
				provideLogger().commitTransaction(trans);
			
			kraken = null;
			return true;
		}
		
		return false;
	}
	
	
	public List<Buoy> getBuoys(){
		return buoys;
	}
	
	/***
	 * Tries to delete a buoy from the field. If the field is an Island, the methods does nothing. Otherwise, it will
	 * check if there is a buoy with same team and type located on the field. In that case, it will destroy that buoy, remove it from the
	 * buoy list and log it's destruction. 
	 * 
	 * @param type Type of the buoy
	 * @param team Team if the buoy
	 * @throws NullPointerExpcetion if team is null
	 * @throws IllegalArgumentException if 0 <= value < 6
	 */
	public abstract boolean placeBuoy(int type, Team team);

	/***
	 * Tries to place a buoy on the field. If the field is an Island, the methods does nothing expect returning false. Otherwise, it will
	 * check if there is no buoy with same team and type located on the field. In this case, it will create a new buoy, add it to the
	 * buoy list and log the event. 
	 * 
	 * @param type Type of the buoy
	 * @param team Team if the buoy
	 * @throws NullPointerExpcetion if team is null
	 * @throws IllegalArgumentException if 0 <= value < 6
	 */
	public abstract void deleteBuoy(Team team, int value);

	
	/***
	 * Returns the type of the field. This method has to be implemented by every concrete field type itself.
	 * 
	 * @returns fieldType
	 */
	public abstract FieldType getFieldType();
	
	
	/***
	 * Returns direct neighbor of the field according to given parameters. To do that, it simply calls the method with the same name
	 * of the superordinate map class.
	 * 
	 * @param field The initial field
	 * @param direction Direction of adjacent neighbor
	 * @return The adjacent field
	 */
	public Field getNeigbour(int direction){
		return map.getNeighbour(this, direction);
	}
	
	public LogWriter provideLogger(){
		return map.getLogWriter();
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Map getMap(){
		return map;
	}
}
