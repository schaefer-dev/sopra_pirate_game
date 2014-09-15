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
	
	public Field getNeighbour(Field field, int direction){
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
	
	public int giveNewActorID(){
		try{
			nextFreeActorID++;
		}
		catch(NumberFormatException e){
			//TODO: notify GUI
		}	
		
		return nextFreeActorID;
	}
	
	public int giveNewEntityID(){
		try{
			nextFreeEntityID++;
		}
		catch(NumberFormatException e){
			//TODO: notify GUI
		}	
		
		return nextFreeEntityID;
	}
	
	public LogWriter getLogWriter(){
		return logWriter;
	}
	
	public void setFirstShip(Ship ship){
		if(ship == null) throw new NullPointerException();
		
		firstShip = ship;
	}
	
	public Ship getFirstShip(){
		return firstShip;
	}
	
	public List<Kraken> getKraken(){
		return kraken;
	}
	
	public Random getRandom(){
		return random;
	}
	
	public void setMapValues(Field[][] field, List<Kraken> kraken){

		//if(field == null || kraken == null) throw new NullPointerException();
		//if(field.length < 2 || field[0].length < 2 || field.length > 200 || field[0].length > 200) throw new IllegalArgumentException();
		

		this.fields = field;
		this.kraken = kraken;
		
		width  = fields.length;
		height = fields[0].length;
	}
	
	private int mod(int a, int b){
		return (a%b + b) % b;
	}
}
