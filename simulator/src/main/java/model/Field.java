package model;

import java.util.List;
import de.unisaarland.cs.st.pirates.logger.LogWriter;

public abstract class Field {

	protected Treasure treasure;
	protected Ship ship;
	protected Kraken kraken;
	protected List<Buoy> buoys;
	protected Map map;
	protected int x;
	protected int y;
	
	protected Field(Map map, int x, int y, Ship ship){
		// TODO Auto-generated method stub
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
	
	public abstract boolean setKraken(Kraken kraken);
	
	public boolean moveKraken(Field destination){
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public Field getNeigbour(int direction){
		// TODO Auto-generated method stub
		return null;
	}
	
	public LogWriter provideLogger(){
		// TODO Auto-generated method stub
		return null;
	}
	
	public Ship getShip(){
		// TODO Auto-generated method stub
		return null;
	}
	
	public Map getMap(){
		// TODO Auto-generated method stub
		return null;
	}
	
	public abstract FieldType getFieldType();
	
	public Treasure getTreasure(){
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Buoy> getBuoys(){
		// TODO Auto-generated method stub
		return null;
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
