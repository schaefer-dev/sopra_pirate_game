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
		@Override
		public boolean equals(Object o){
			if(o instanceof Goto){
				Goto other = (Goto) o;
				if(other.pc == pc)
					return true;
			}
			return false;	
		}
}
