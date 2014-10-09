package view.utility;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
	private Team team;
	private FieldType type;
	
	private Image fieldImage;
	private Image detailImage;
	
	private Image shipImage;
	private Image treasureImage;
	private Image buoysImage;
	private Image krakenImage;
	
	private boolean farAway;
	public boolean marked = false;
	public boolean sailedAway = false;
	
	public Field(Map map, int x, int y, FieldType type){
		this.x = x;
		this.y = y;
		this.map = map;
		this.type = type;
		buoys = new LinkedList<SimpleEntity>();
		farAway = false;
		
		switch(type){
			case Base:
				break;
			case Water:
				fieldImage = map.getRessources().getWaterImage();
				break;
			case Island:
				fieldImage = map.getRessources().getIslandImage();
				Random random = new Random();
				if(random.nextInt(10) == 0){
					List<Image> detailImages = map.getRessources().getIslandDetailImages();
					detailImage = detailImages.get(random.nextInt(detailImages.size()));
				}
				break;
			case ProvisionIsland:
				fieldImage = map.getRessources().getProvisionImage();
				break;
		}
	}
	
	
	public Field(Map map, int x, int y, Team affiliation){
		this.x = x;
		this.y = y;
		this.map = map;
		this.type = FieldType.Base;
		this.team = affiliation;
		this.buoys = new LinkedList<SimpleEntity>();
		fieldImage = map.getRessources().getBaseImage(team.getID());
		farAway = false;
	}
	
	
	private void redraw(){
		if(map.isVisible(this))
			map.drawField(this);
	}
	
	public List<Image> getImages(){
		int zoom =  map.getCam().zoomLevelAbsolute();
		List<Image> images = new LinkedList<Image>();
		
		switch(type){
		case Water:
			if(zoom < 2)
				images.add(fieldImage);
			break;
		case Base:
			if(zoom < 5)
				images.add(fieldImage);
			break;

		case ProvisionIsland:
			if(zoom > 4)
				images.add(map.getRessources().getIslandImage());
			else if(zoom > 2)
				images.add(map.getRessources().getShoreIslandImage());
			else
				images.add(fieldImage);
			break;
		case Island:
			images.add(fieldImage);
			break;
		}

		if(farAway){
			farAway = false;
			images.add(map.getRessources().getWaterFarImage());
		}
		
		if(detailImage != null && zoom < 2)
			images.add(detailImage);
		if(buoysImage != null && zoom < 1)
			images.add(buoysImage);
		if(krakenImage != null && zoom < 3){
			if(ship == null)
				images.add(krakenImage);
			else
				images.add(map.getRessources().getKrakenImage());
		}
		if(treasureImage != null && zoom < 3)
			images.add(treasureImage);

		return images;
	}
	
	public Image getShipImage(){
		int zoom =  map.getCam().zoomLevelAbsolute();
		
		if(shipImage != null && zoom < 6)
			return shipImage;
		
		return null;
	}
	
	public Image getFieldImage(){
		return fieldImage;
	}
	
	public void setFieldImage(Image fieldImage){
		this.fieldImage = fieldImage;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Team getTeam(){
		return team;
	}
	
	public FieldType getFieldType(){
		return type;
	}
	
	public Ship getShip(){
		return ship;
	}
	
	public void setShip(Ship ship){
		if(ship == null){
			sailedAway = true;
			this.shipImage = null;
			if(map.getCam().zoomLevelAbsolute() > 1 && type == FieldType.Water)
				farAway = true;
		}
		else
			this.shipImage = map.getRessources().getShipImage(ship.getFleet().getID());

		this.ship = ship;
		redraw();
	}
	
	public SimpleEntity getTreasure(){
		return treasure;
	}
	
	public void setTreasure(SimpleEntity treasure){
		if(type == FieldType.Water || type == FieldType.Base)
			treasureImage = (treasure == null) ? null : map.getRessources().getWaterTreasureImage();
		else if(type == FieldType.Island)
			treasureImage = (treasure == null) ? null : map.getRessources().getIslandTreasureImage();
		
		if(type == FieldType.Island && detailImage != null)
			detailImage = null;
		
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
		Collections.sort(buoys);
	}
	
	public void deleteBuoy(SimpleEntity buoy){
		buoys.remove(buoy);
		
		if(buoys.size() == 0){
			buoysImage = null;
			redraw();
		}	
	}

	public SimpleEntity getKraken(){
		return kraken;
	}

	public void setKraken(SimpleEntity kraken){
		if(kraken == null){
			krakenImage = null;
			if(map.getCam().zoomLevelAbsolute() > 1 && type == FieldType.Water)
				farAway = true;
		}
		else
			krakenImage = map.getRessources().getKrakenFarImage();
			
		this.kraken = kraken;
		redraw();
	}	
	
	public String giveTooltipText(){
		String intro;
		if(team == null)
			intro = type.toString() + " (" + x + "," + y + ")\n\n";
		else
			intro = team.toString() + "'s Base (" + x + "," + y + ")\n\n";
		
		String treasureInfo = "";
		if(treasure != null)
			treasureInfo = "Treasure(#" + treasure.getId() + ") \n"
										+ "   Value: " + treasure.getValue() + "\n";
		
		String krakenInfo = "";
		if(kraken != null)
			krakenInfo = "Kraken(#" + kraken.getId() + ")\n";
		
		String shipInfo = "";
		if(ship != null)
			shipInfo = "Ship(#" + ship.getID() + ")\n"
								+ "  Fleet: " + ship.getFleet()  + "\n"
								+ "  Condition: " + ship.getCondition() + "\n"
								+ "  Resting: " + ship.getResting() + "\n"
								+ "  Moral: " + ship.getMoral() + "\n"
								+ "  Load: " + ship.getLoad() + "\n"
								+ "  PC: " + ship.getPc() + "\n";
		
		String buoyInfo = "";
		if(buoys.size() > 0){
			buoyInfo  = "Buoys: \n";
			String buoyDetails = "";
			for(SimpleEntity buoy: buoys)
				buoyDetails += "   " + buoy.getValue() + "(" + buoy.getFleet() + ")\n";
			buoyInfo += buoyDetails;
		}
		
		return intro + treasureInfo + krakenInfo + shipInfo + buoyInfo;
	}
}
