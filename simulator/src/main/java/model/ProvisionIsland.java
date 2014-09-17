package model;

public class ProvisionIsland extends Island {

	public ProvisionIsland(Map map, int x, int y) {
		super(map, x, y, null);
	}

	@Override
	public FieldType getFieldType() {
		return FieldType.ProvisionIsland;
	}
}
