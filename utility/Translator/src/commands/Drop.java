package commands;

import main.Command;


public class Drop implements Command {
	
	@Override
	
	public String toString(){
		return "drop";
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Drop ;
	}


}
