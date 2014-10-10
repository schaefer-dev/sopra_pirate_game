package de.unisaarland.cs.st.pirates.group5.commands;

import java.util.Random;

import de.unisaarland.cs.st.pirates.group5.controller.Command;
import de.unisaarland.cs.st.pirates.group5.model.Ship;

/***
 * This class implements the command interface and is responsible for executing the flipzero command.
 * 
 * @author Rafael Theis
 */
public class Flipzero implements Command {
	
	private int p;
	private int elsePC;
	
	/***
	 * @param p probapility 
	 * @param pc Programm counter
	 * @throws IllegalArugmentException - if the pc is invalid(0 <= pc < 2000)
	 */
	public Flipzero(int p, int elsePC){
		if(p < 1 || elsePC < 0 || elsePC > 1999) throw new IllegalArgumentException();
		
		this.p = p;
		this.elsePC = elsePC;
		
	}
	
	public int getRandom(){
		return p;
	}
	
	/***
	 * The method executes a single flipzero command. Therefore it will fetch the random object from the map class and then tries to generate
	 * a new integer with it's nextInt()-method and the class object p(probability) as the parameter. Then it will set the program counter
	 * of the committed ship to the value of the class object elsePC if the resulting integer is greater than 0.
	 * 
	 * @throws NullPointerException if ship is null
	 */
	@Override
	public void execute(Ship ship){
		if(ship == null) throw new NullPointerException();
		
		Random random = ship.getPosition().getMap().getRandom();
		if(random.nextInt(p) > 0)
			ship.setPC(elsePC);
	}
	
	@Override
	public boolean equals (Object o){
		if(o instanceof Flipzero){
			Flipzero other = (Flipzero) o;
			if(other.p == p && elsePC == other.elsePC)
				return true;
		}
		
		return false;
	}
	@Override
	public int hashCode()
	{
		return elsePC;
	}
}
