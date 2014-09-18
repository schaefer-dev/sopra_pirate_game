package model;

public class Base extends Water {

	private Team team;
	
	public Base(Map map, int x, int y, Team team, Ship ship) {
		super(map, x, y, null);
		
		if(ship != null){
			this.ship = ship;
			this.ship.setField(this);
		}
		
		this.team = team;
	}

	
	@Override
	public boolean exchangeTreasure(int value){
		
		if(value == 0)
			return true;
		else if(value == -2){
			if((team.getScore() + value) < 0) throw new IllegalArgumentException();
			
			team.addLoot(value);
			map.getLogWriter().fleetScore(team.getName() - 'a', team.getScore());
		}
		else if(value >= 1 && value <= 4){
			team.addLoot(value);
			map.getLogWriter().fleetScore(team.getName() - 'a', team.getScore());
		}
		else
			throw new IllegalArgumentException();
		
		return true;
	}
	
	@Override
	public boolean setKraken(Kraken kraken){
		return false;
	}
	
	@Override
	public boolean setShip(Ship ship){
		if(ship == null || ship.getTeam().equals(team))
			return super.setShip(ship);
		
		return false;
	}
	/* 
	 * no override because its allowed?
	@Override
	public boolean placeBuoy(int type, Team team){
		return false;
	}
	
	@Override
	public void deleteBuoy(Team team, int value){
		return;
	}
	*/
	
	@Override
	public FieldType getFieldType() {
		return FieldType.Base;
	}
	
	public Team getTeam(){
		return team;
	}
}
