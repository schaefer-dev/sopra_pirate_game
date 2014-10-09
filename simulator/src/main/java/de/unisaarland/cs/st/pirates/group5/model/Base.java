package de.unisaarland.cs.st.pirates.group5.model;

/***
 * This class represents a base on the map and holds information about it's affiliation, position as well as ships that are currently
 * located on it. It provides game functionality that is directly linked to bases like dropping treasures. 
 * It extends the abstract class Water
 * 
 * @author Rafael Theis
 * @see {@link Field}, {@link Water}
 */
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

	/**
	 * Drops Treasure onto base -> gives points to team
	 * 
	 * @param value the amout of treasure to drop
	 * @returns	true
	 * @Autor Daniel
	 */
	@Override
	public boolean dropTreasure(int value){
		if(value == 0)
			return true;
		else if(value == -2){
			if((team.getScore() + value) < 0) throw new IllegalArgumentException();
			
			team.addLoot(value);
			if(hasLogWriter)
				provideLogger().fleetScore(team.getName() - 'a', team.getScore());
		}
		else if(value >= 1 && value <= 4){
			team.addLoot(value);
			if(hasLogWriter)
				provideLogger().fleetScore(team.getName() - 'a', team.getScore());
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
	
	@Override
	public FieldType getFieldType() {
		return FieldType.Base;
	}
	
	public Team getTeam(){
		return team;
	}
}
