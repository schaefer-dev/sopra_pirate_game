package de.unisaarland.cs.st.pirates.group5.model;
/**
 * The buoy class
 * Buoys could be placed on water by a ship. The placed buoy has a type between 0 and 5.
 * The team of the buoy is the same as the team of the ship which placed it. A buoy can also be removed but only by
 * a ship of the same team as the buoy belongs to. Also the exact type of the buoy can only be seen by ships of the same team as the buoy.
 *
 * @author Janna
 *
 */
public class Buoy extends Entity{

	/**Team that placed the buoy*/
	private Team team;
	
	/**Type of the buoy ( 0-5 ) */
	private int type;
	
	
	
	/**
	 * Constructor creates a new buoy and initialized its values.
	 * 
	 * @param id		The id the new buoy should have.
	 * @param team		The team the new buoy belongs to.
	 * @param type		The type of the new buoy (between 0 and 5)
	 * 
	 * @throws 			IllegalArgumentException if the type is not between 0 and 5
	 */
	public Buoy(int id, Team team, int type) {
		super(id);
		this.team=team;
		if (type >= 0 && type <=5){
		this.type = type;}
		else {
			throw new IllegalArgumentException("Buoytype must be between 0 and 5");
		}
	}
	
	
	/**
	 * Gets the type of the buoy. Its always between 0 and 5.
	 * 
	 * @return the type of the buoy
	 */
	public int getType(){
		return this.type;
	}
	
	
	/**
	 * Gets the team of the buoy.
	 * 
	 * @return the team this buoy belongs to.
	 */
	public Team getTeam(){
		return this.team;
	}
	
	
	@Override
	public boolean equals(final Object other) {
		if (other instanceof Buoy) {
			if ((this.team).equals(((Buoy)other).team))
			{
				if ((this.type)==(((Buoy)other).type))
					return true;
			}
		}
		return false;
	}
	@Override
	public int hashCode()
	{
		return type;
	}
	
}
