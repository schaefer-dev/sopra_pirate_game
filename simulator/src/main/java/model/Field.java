package model;

import java.util.LinkedList;
import java.util.List;

import de.unisaarland.cs.st.pirates.logger.LogWriter;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Entity;

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
		if(x < 2 || x > 200 || y < 2 || y > 200) throw new IllegalArgumentException();
		
		this.x = x;
		this.y = y;
		this.map = map;
		this.ship = ship;
	}
	
	public boolean exchangeTreasure(int value){
		// TODO Auto-generated method stub
		return false;
	}
	
	public abstract boolean setShip(Ship ship);
	
	public abstract boolean placeBuoy(int type, Team team);
	
	public boolean moveShip(Field destination){
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean setKraken(Kraken kraken)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean moveKraken(Field destination){
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public Field getNeigbour(int direction){
		// TODO Auto-generated method stub
		return map.getNeighbour(this, direction);
	}
	
	public LogWriter provideLogger(){
		return map.getLogWriter();
	}
	
	public Ship getShip(){
		return ship;
	}
	
	public Map getMap(){
		return map;
	}
	
	public abstract FieldType getFieldType();
	
	public Treasure getTreasure(){
		return treasure;
	}
	
	public List<Buoy> getBuoys(){
		return buoys;
	}
	
	public void deleteBuoy(Team team, int value){
		if(team == null) throw new NullPointerException();
		if(value < 0 || value > 6) throw new IllegalArgumentException();
		
		for(Buoy buoy: buoys){
			if(buoy.getTeam().equals(team) && buoy.id == value){
				buoys.remove(buoy);
				map.getLogWriter().destroy(Entity.BUOY, buoy.id);
			}
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Kraken getKraken() {
		return kraken;
	}
	
	
	
}
