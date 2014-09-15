package model;

public class Base extends Water {

	private Team team;
	
	public Base(Map map, int x, int y, Team team, Ship ship) {
		// TODO Auto-generated constructor stub
		super(map, x, y, null);
		this.ship = ship;
		this.team=team;
	}

	@Override
	public boolean exchangeTreasure(int value){
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean setShip(Ship ship) {
		// TODO Auto-generated method stub
		this.ship=ship;
		return true;
	}
	
	@Override
	public FieldType getFieldType() {
		return FieldType.Water;
	}
	
	public Team getTeam(){
		return team;
	}
	
}
