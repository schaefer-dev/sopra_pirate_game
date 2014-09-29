package main;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

	public static void main(String[] args) throws ArrayIndexOutOfBoundsException, NullPointerException, IOException, URISyntaxException{
		CheckTactics parsed = new CheckTactics(args[0], args[1], args[2]);
		parsed.printTactics();
		parsed.printErrors();
	}	
}
