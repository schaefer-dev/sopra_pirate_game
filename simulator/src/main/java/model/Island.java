package model;

import de.unisaarland.cs.st.pirates.logger.LogWriter;

public class Island extends Field {

	public Island(Map map, int x, int y) {
		super(map, x, y, null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean setShip(Ship ship) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean moveShip(Field destination) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean placeBuoy(int type, Team team) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setKraken(Kraken kraken) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveKraken(Field destination) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FieldType getFieldType() {
		// TODO Auto-generated method stub
		return null;
	}

}