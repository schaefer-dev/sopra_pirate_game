package model;

import java.util.Random;

public class Kraken {

	private Field field;
	private final int id;
	
	public Kraken(int id, Field field){
		this.id = id;
		this.field=field;
		
	}
	
	public void move(){
		
		Random random = field.getMap().getRandom();
		int dir = random.nextInt(6);
		if (dir < 0 || dir > 5){
			throw new IllegalArgumentException ("seed is not allowed to be < 0 or > 5");
		}
		else{
		Field destination = field.getNeigbour(dir);
		field.moveKraken(destination);
		}
			
	}
	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public int getId() {
		return id;
	}
	
	
}
