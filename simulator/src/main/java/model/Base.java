package model;

public class Base extends Water {

	private Team team;
	
	public Base(Map map, int x, int y, Team team, Ship ship) {
		super(map, x, y, null);
		this.ship = ship;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean exchangeTreasure(int value){
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean setShip(Ship ship) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public FieldType getFieldType() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Team getTeam(){
		return team;
	}
	
}
