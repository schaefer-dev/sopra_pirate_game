package model;

import de.unisaarland.cs.st.pirates.logger.LogWriter;

public class Water extends Field {

	public Water(Map map, int x, int y, Kraken kraken) {
		super(map, x, y, null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean setShip(Ship ship) {
		// TODO Auto-generated method stub
		this.ship=ship;
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
		this.kraken=kraken;
		return false;
	}

	@Override
	public FieldType getFieldType() {
		// TODO Auto-generated method stub
		return FieldType.Water;
	}

}
