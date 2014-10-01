package main;

public class Main {

	public static void main(String[] args) {
		CheckTactics check = new CheckTactics(args[0], args[1]);
		check.printTactics();
		check.printErrors();
	}

}
