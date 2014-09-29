package commands;

import main.Command;

public class Goto implements Command{

		int pc;
		
		public Goto(int pc){
			this.pc = pc;
		}
		
		public String toString(){
			String res = "goto " + String.valueOf(pc);
			return res;
		}
}
