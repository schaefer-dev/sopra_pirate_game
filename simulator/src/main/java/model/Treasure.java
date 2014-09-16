package model;

public class Treasure extends Entity {

	private int value;
	
	public Treasure(int id, int val) {
		super(id);
		this.value = val;
	}

	public int getValue(){
		return this.value;
	}
	
	private void setValue(int newValue){
		if (newValue > 0){
		this.value = newValue;}
		else {
			throw new IllegalArgumentException ("value of treasure <=0, must have been deleted already in field");
		}
	}
	public void changeValue(int newValue){ 
		if ((value + newValue) > 0 ){
			setValue(value + newValue);
		}
		else {
		throw new IllegalArgumentException ("value of treasure <=0, must have been deleted already in field");
		}
		
		
	}
	
}
