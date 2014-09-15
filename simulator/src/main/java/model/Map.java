package model;

import java.util.List;
import java.util.Random;

import de.unisaarland.cs.st.pirates.logger.LogWriter;

public class Map {

	private int height;
	private int width;
	private Field[][] fields;
	private int nextFreeActorID;
	private int nextFreeEntityID;
	private LogWriter logWriter;
	private Ship firstShip;
	private List<Kraken> kraken;
	
	public final Random random;
	
	public Map(Random random, LogWriter logWriter){
		this.random = random;
		this.logWriter = logWriter;
	}
	
	public Field getNeighbour(Field field, int direction){
		// TODO Auto-generated method stub
		return null;
	}
	
	public int giveNewActorID(){
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int giveNewEntityID(){
		// TODO Auto-generated method stub
		return 0;
	}
	
	public LogWriter getLogWriter(){
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setFirstShip(Ship ship){
		// TODO Auto-generated method stub
	}
	
	public Ship getFirstShip(){
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Kraken> getKraken(){
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setMapValues(Field[][] fields, int nextFreeActorId, int nextFreeEntityId,
												Ship firstShip, List<Kraken> kraken){
		// TODO Auto-generated method stub
	}
}
