package model;

import java.util.List;
import java.util.Random;

import de.unisaarland.cs.st.pirates.logger.LogWriter;

public class Map {

	private int height;
	private int width;
	private Field[] fields;
	private int nextFreeActorID;
	private int nextFreeEntityID;
	private LogWriter log;
	private Ship firstShip;
	private List<Kraken> kraken;
	
	public final Random random;
	
	public Map(Random newRandom){
		random = newRandom;
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
}
