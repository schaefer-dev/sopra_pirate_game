package model;

import java.util.LinkedList;
import java.util.List;

import de.unisaarland.cs.st.pirates.logger.LogWriter;

public abstract class Field {

	protected Treasure treasure;
	protected Ship ship;
	protected Kraken kraken;
	protected List<Buoy> buoys = new LinkedList<Buoy>();
	protected Map map;
	protected int x;
	protected int y;
	
	protected Field(Map map, int x, int y, Ship ship){
		// TODO Auto-generated method stub
		this.map=map;
		this.x=x;
		this.y=y;
		this.ship=ship;
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
		throw new IllegalStateException();
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
		// TODO Auto-generated method stub
		return this.map.getLogWriter();
	}
	
	public Ship getShip(){
		// TODO Auto-generated method stub
		return this.ship;
	}
	
	public Map getMap(){
		// TODO Auto-generated method stub
		return this.map;
	}
	
	public abstract FieldType getFieldType();
	
	public Treasure getTreasure(){
		// TODO Auto-generated method stub
		return this.treasure;
	}
	
	public List<Buoy> getBuoys(){
		// TODO Auto-generated method stub
		return this.buoys;
	}
	public void deleteBuoy(Team team, int value){
		// TODO Auto-generated method stub
		return;
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
