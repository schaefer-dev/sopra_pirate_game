package model;

public class Kraken {

	private Field field;
	private final int id;
	
	public Kraken(int id, Field field){
		this.id = id;
		this.field=field;
		
	}
	
	public void move(){
		
		int dir = field.getMap().getRandom().nextInt(6);
		
		if (dir < 0 || dir > 5){
			throw new IllegalArgumentException ("seed is not allowed to be < 0 or > 5");
		}
		else{
		Field destination = field.getNeigbour(dir);
		
		switch (destination.getFieldType()){
		
		case Water:
			if (destination.getKraken() == null){ //no kraken on that field so kraken can move
				field.moveKraken(destination);
			}
			
		case Base:
		
		case Island: 
			
		case ProvisionIsland:
			
		}
			
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
