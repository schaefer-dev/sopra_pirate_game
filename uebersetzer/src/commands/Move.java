package commands;

import main.Command;

/**
 * move Class implements the Move Command which is used in tactics. The class holds all functionality which
 * is done by that command. Its called in the act() of a ship during a step() in the simulator class
 * 
 * 
 * @author danielschaefer
 *
 */
public class Move implements Command {

	private int elsePC;
	
	public Move(int pc){
		this.elsePC = pc;
	}
	
	@Override
	public String toString(){
		return "move " + " else "+ String.valueOf(elsePC);
	}

}
