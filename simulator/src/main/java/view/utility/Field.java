package view.utility;

import java.util.LinkedList;
import java.util.List;

import view.SimpleEntity;
import model.FieldType;

public class Field {

	private List<SimpleEntity> buoys;
	private SimpleEntity treasure;
	private SimpleEntity kraken;
	private Ship ship;
	private Integer affiliation;
	private FieldType type;
	
	
	public Field(FieldType type){
		this.type = type;
		buoys = new LinkedList<SimpleEntity>();
	}
	
	
	public Field(Integer affiliation){
		this.type = FieldType.Base;
		this.affiliation = affiliation;
		buoys = new LinkedList<SimpleEntity>();
	}
	
	
	
	public FieldType getFieldType(){
		return type;
	}
	
	public Ship getShip(){
		return ship;
	}
	
	public void setShip(Ship ship){
		this.ship = ship;
	}
	
	public SimpleEntity getTreasure(){
		return treasure;
	}
	
	public void setTreasure(SimpleEntity treasure){
		this.treasure = treasure;
	}
	
	public List<SimpleEntity> getBuoys(){
		return buoys;
	}
	
	public void addBuoy(SimpleEntity buoy){
		buoys.add(buoy);
	}
	
	public void deleteBuiy(SimpleEntity buoy){
		buoys.remove(buoy);
	}


	public SimpleEntity getKraken() {
		return kraken;
	}


	public void setKraken(SimpleEntity kraken) {
		this.kraken = kraken;
	}


	public Integer getAffiliation() {
		return affiliation;
	}	
	
}
