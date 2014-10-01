package view.utility;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.image.Image;
import view.SimpleEntity;
import model.FieldType;

public class Field {

	private int x, y;
	private Map map;
	private List<SimpleEntity> buoys;
	private SimpleEntity treasure;
	private SimpleEntity kraken;
	private Ship ship;
	private Integer affiliation;
	private FieldType type;
	
	private Image fieldImage;
	private Image shipImage;
	private Image treasureImage;
	private Image buoysImage;
	private Image krakenImage;
	
	public Field(Map map, int x, int y, FieldType type){
		this.x = x;
		this.y = y;
		this.map = map;
		this.type = type;
		buoys = new LinkedList<SimpleEntity>();
		
		switch(type){
			case Base:
				fieldImage = map.getRessources().getBaseImage();
				break;
			case Water:
				fieldImage = map.getRessources().getWaterImage();
				break;
			case Island:
				fieldImage = map.getRessources().getIslandImage();
				break;
			case ProvisionIsland:
				fieldImage = map.getRessources().getProvisionImage();
				break;
		}
	}
	
	
	public Field(Map map, int x, int y, Integer affiliation){
		this.x = x;
		this.y = y;
		this.map = map;
		this.type = FieldType.Base;
		this.affiliation = affiliation;
		this.buoys = new LinkedList<SimpleEntity>();
		fieldImage = map.getRessources().getBaseImage();
	}
	
	
	private void redraw(){
		if(map.isVisible(this))
			map.drawField(this);
	}
	
	public List<Image> getImages(){
		List<Image> images = new LinkedList<Image>();
		images.add(fieldImage);
		
		if(buoysImage != null)
			images.add(buoysImage);
		if(krakenImage != null)
			images.add(krakenImage);
		if(treasureImage != null)
			images.add(treasureImage);
		if(shipImage != null)
			images.add(shipImage);
		
		//System.out.println(type);
		
		return images;
	}
	
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Integer getAffiliation() {
		return affiliation;
	}
	
	
	public FieldType getFieldType(){
		return type;
	}
	
	public Ship getShip(){
		return ship;
	}
	
	public void setShip(Ship ship){
		this.shipImage = (ship == null) ? null : map.getRessources().getShipImage();
		this.ship = ship;
		redraw();
	}
	
	public SimpleEntity getTreasure(){
		return treasure;
	}
	
	public void setTreasure(SimpleEntity treasure){
		if(type == FieldType.Water)
			treasureImage = (treasure == null) ? null : map.getRessources().getWaterTreasureImage();
		else if(type == FieldType.Island)
			treasureImage = (treasure == null) ? null : map.getRessources().getIslandTreasureImage();
		
		this.treasure = treasure;
		redraw();
	}
	
	public List<SimpleEntity> getBuoys(){
		return buoys;
	}
	
	public void addBuoy(SimpleEntity buoy){
		if(buoys.size() == 0){
			buoysImage = map.getRessources().getBuoyImage();
			redraw();
		}
		
		buoys.add(buoy);
	}
	
	public void deleteBuoy(SimpleEntity buoy){
		buoys.remove(buoy);
		
		if(buoys.size() == 0){
			buoysImage = null;
			redraw();
		}	
	}

	public SimpleEntity getKraken() {
		return kraken;
	}

	public void setKraken(SimpleEntity kraken) {
		krakenImage = (kraken == null) ? null : map.getRessources().getKrakenImage();
		
		this.kraken = kraken;
		redraw();
	}	
}
