package main;

import translator.CheckTactics;

public class Main {

	public static void main(String[] args) {
		if (args.length!=2)
			throw new IllegalArgumentException("first Argument is tactic to parse, second argument is file where the tactic should be parsed");
		CheckTactics check = new CheckTactics(args[0], args[1]);
		check.printTactics();
		check.printErrors();
	}

}