package model;

import java.util.LinkedList;
import java.util.List;

import de.unisaarland.cs.st.pirates.logger.LogWriter;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Entity;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Key;

public abstract class Field {

	protected Treasure treasure;
	protected Ship ship;
	protected Kraken kraken;
	protected List<Buoy> buoys = new LinkedList<Buoy>();
	protected Map map;
	protected int x;
	protected int y;
	
	protected Field(Map map, int x, int y, Ship ship){
		if(map == null) throw new NullPointerException();
		if(x < 0 || x > 200 || y < 0 || y > 200) throw new IllegalArgumentException();
		
		this.x = x;
		this.y = y;
		this.map = map;
		this.ship = ship;
	}
	
	
	public Treasure getTreasure(){
		return treasure;
	}

	public boolean exchangeTreasure(int value){
		if(value < -4 || value > 4) throw new IllegalArgumentException("You can only change the treasure value by |4|");
		
		if(value == 0)
			return true;
		else if(value > 0){
			if(treasure == null){
				treasure = new Treasure(map.giveNewEntityID(), value);
				Key[] keys = {Key.X_COORD, Key.Y_COORD, Key.VALUE};
				int[] values = {x, y, value};
				map.getLogWriter().create(Entity.TREASURE, treasure.id, keys, values);
			}
			else{
				treasure.changeValue(value);
				map.getLogWriter().notify(Entity.TREASURE, treasure.id, Key.VALUE, treasure.getValue());
			}	
		}
		else{
			if(treasure == null) throw new IllegalArgumentException("You can't take loot from a nonexisting treasure");
			if((treasure.getValue() + value) < 0) throw new IllegalArgumentException("You are too greedy");
			
			if((treasure.getValue() + value) == 0){
				map.getLogWriter().destroy(Entity.TREASURE, treasure.id);
				treasure = null;
			}
			else{
				treasure.changeValue(value);
				map.getLogWriter().notify(Entity.TREASURE, treasure.id, Key.VALUE, treasure.getValue());
			}		
		}

		return true;
	}
	
	
	public Ship getShip(){
		return ship;
	}
	
	public boolean setShip(Ship ship){
		if(this.ship == null){
			System.out.print("setship reached\n");
			this.ship = ship;
			this.ship.setField(this);
			return true;
		}
		else if(this.ship != null && ship == null)
			this.ship = null;
			
		return false;	
	}
	
	public boolean moveShip(Field destination){
		if(ship == null) throw new IllegalStateException();
		
		if(destination.setShip(ship)){
			if(x != destination.getX())
				map.getLogWriter().notify(Entity.SHIP, ship.getID(), Key.X_COORD, destination.getX());
			if(y != destination.getY())
				map.getLogWriter().notify(Entity.SHIP, ship.getID(), Key.Y_COORD, destination.getY());
			
			ship = null;
			return true;
		}

		return false;
	}
	
	
	public Kraken getKraken() {
		return kraken;
	}
	
	public boolean setKraken(Kraken kraken){
		if(this.kraken == null){
			this.kraken = kraken;
			this.kraken.setField(this);
			return true;
		}
		
		return false;
	}
	
	public boolean moveKraken(Field destination){
		if(kraken == null) throw new IllegalStateException();
		
		if(destination.setKraken(kraken)){
			
			if(x != destination.getX())
				map.getLogWriter().notify(Entity.KRAKEN, kraken.getId(), Key.X_COORD, destination.getX());
			if(y != destination.getY())
				map.getLogWriter().notify(Entity.KRAKEN, kraken.getId(), Key.Y_COORD, destination.getY());
			
			kraken = null;
			return true;
		}
		
		return false;
	}
	
	
	public List<Buoy> getBuoys(){
		return buoys;
	}
	
	public abstract boolean placeBuoy(int type, Team team);

	public void deleteBuoy(Team team, int value){
		if(team == null) throw new NullPointerException();
		if(value < 0 || value > 6) throw new IllegalArgumentException();
		
		for(Buoy buoy: buoys){
			if(buoy.getTeam().equals(team) && buoy.getType() == value){
				buoys.remove(buoy);
				map.getLogWriter().destroy(Entity.BUOY, buoy.id);
				break;
			}
		}
	}

	
	public abstract FieldType getFieldType();
	
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
