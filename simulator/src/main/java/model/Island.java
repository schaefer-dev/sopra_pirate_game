package model;

public class Island extends Field {

	public Island(Map map, int x, int y, Treasure treasure) {
		super(map, x, y, null);
		
		this.treasure = treasure;
	}

	
	@Override
	public boolean setShip(Ship ship) {
		return false;
	}
	
	@Override
	public boolean moveShip(Field destination) {
		return false;
	}

	@Override
	public boolean placeBuoy(int type, Team team) {
		return false;
	}

	@Override
	public boolean setKraken(Kraken kraken) {
		return false;
	}

	@Override
	public boolean moveKraken(Field destination) {
		return false;
	}

	@Override
	public FieldType getFieldType() {
		return FieldType.Island;
	}
}
