package view.utility;

public class Ship {

	private int id;
	private int pc;
	private int resting;
	private int direction;
	private int load;
	private int moral;
	private int condition;
	private int fleet;
	private Field field;
	
	public Ship(int id){
		this.id = id;
	}
	
	public int getID(){
		return id;
	}
	
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		if(field != null)
			field.setShip(this);
			
		this.direction = direction;
	}

	public int getLoad() {
		return load;
	}

	public void setLoad(int load) {
		this.load = load;
	}

	public int getMoral() {
		return moral;
	}

	public void setMoral(int moral) {
		this.moral = moral;
	}

	public int getCondition() {
		return condition;
	}

	public void setCondition(int condition) {
		this.condition = condition;
	}

	public int getFleet() {
		return fleet;
	}

	public void setFleet(int fleet) {
		this.fleet = fleet;
	}

	public int getPc() {
		return pc;
	}

	public void setPc(int pc) {
		this.pc = pc;
	}

	public int getResting() {
		return resting;
	}

	public void setResting(int resting) {
		this.resting = resting;
	}

	public int getX() {
		return field.getX();
	}

	public int getY() {
		return field.getY();
	}

	public void setField(Field field){
		this.field = field;
	}
}
