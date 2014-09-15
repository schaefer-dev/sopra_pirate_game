package model;

public class Buoy extends Entity{

	private Team team;
	private int type;
	
	public Buoy(int id, Team team, int type) {
		super(id);
		// TODO Auto-generated method stub
	}

	public int getType(){
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Team getTeam(){
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean equals(final Object other) {
		if (other instanceof Buoy) {
			if ((this.team).equals(((Buoy)other).team))
				if ((this.id)==(((Buoy)other).id))
				return true;
		}
		return false;
	}
}
