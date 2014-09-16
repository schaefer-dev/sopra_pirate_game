package model;

public class Kraken {

	private Field field;
	private final int id;
	
	public Kraken(int id, Field field){
		this.id = id;
		this.field=field;
		// TODO Auto-generated method stub
	}
	
	public void move(){
		// TODO Auto-generated method stub
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
