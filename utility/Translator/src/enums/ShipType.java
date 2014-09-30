package enums;

public enum ShipType {
	Friend("friend"),
	Enemy("enemy"),
	Undefined("whatever");
	
	private String shiptype;
	
	private ShipType(String kind){
		this.shiptype = kind;
	}
	
	public String toString(){
		return shiptype;
	}
}	
