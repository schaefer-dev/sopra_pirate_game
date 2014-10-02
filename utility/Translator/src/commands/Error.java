package commands;

import main.Command;

public class Error implements Command{
	int absoluteRow;
	String error;
	
	public Error(int absRow, String error){
		this.absoluteRow = absRow;
		this.error = error;
	}
	
	public String toString(){
		return "ERROR IN ROW: " + absoluteRow + " " + error; 
	}
}
