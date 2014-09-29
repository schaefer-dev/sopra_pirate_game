package src.enums;

public enum Register {
	ship_direction("ship_direction"),
	ship_load("ship_load"),
	ship_moral("ship_moral"),
	ship_condition("ship_condition"),
	sense_celltype("sense_celltype"),
	sense_treasure("sense_treasure"),
	//Bitte Reihenfolge der Marker nicht mehr ändern bzw. nur nach Absprache
	sense_marker0("sense_marker0"),
	sense_marker1("sense_marker1"),
	sense_marker2("sense_marker2"),
	sense_marker3("sense_marker3"),
	sense_marker4("sense_marker4"),
	sense_marker5("sense_marker5"),
	sense_enemymarker("sense_enemymarker"),
	sense_shiptype("sense_shiptype"),
	sense_shipdirection("sense_shipdirection"),
	sense_shiploaded("sense_shiploaded"),
	sense_supply("sense_supply"),
	sense_shipcondition("sense_shipcondition");
	
	private String registername;
	
	private Register(String name){
		this.registername = name;
	}
	
	public String toString(){
		return registername;
	}
}
