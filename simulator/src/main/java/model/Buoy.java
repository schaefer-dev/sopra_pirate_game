package model;

public class Buoy extends Entity{

	private Team team;
	private int type;
	
	public Buoy(int id, Team team, int type) {
		super(id);
		this.team=team;
		this.type = type;
	}

	public int getType(){
		return this.type;
	}
	
	public Team getTeam(){
		return this.team;
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
