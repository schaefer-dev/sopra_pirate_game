package de.unisaarland.cs.st.pirates.group5.commands;

import de.unisaarland.cs.st.pirates.group5.controller.Command;
import de.unisaarland.cs.st.pirates.group5.model.Ship;

/***
 * This class implements the command interface and is responsible for executing the goto command.
 * 
 * @author Rafael Theis
 */
public class Goto implements Command {

	private int pc;
	
	
	/***
	 * @param pc Programm counter
	 * @throws IllegalArugmentException - if the pc is invalid(0 <= pc < 2000)
	 */
	public Goto(int pc) {
		if(pc < 0 || pc > 2000) throw new IllegalArgumentException();
		
		this.pc = pc;
	}
	
	/***
	 * The method executes a single goto command. It will set the programm counter of the committed ship to the value of the
	 * class variable pc.
	 * 
	 * @throws NullPointerException if ship is null
	 */
	@Override
	public void execute(Ship ship) {
		if(ship == null) throw new NullPointerException();
		
		ship.setPC(pc);
	}
	@Override
	public boolean equals (Object o){
		if(o instanceof Goto && ((Goto) o).pc == pc){
			return true;
		}
		
		return false;
	}
	@Override
	public int hashCode()
	{
		return pc;
	}

}
