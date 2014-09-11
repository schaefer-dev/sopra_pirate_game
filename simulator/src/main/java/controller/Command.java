package controller;

import model.Ship;

public interface Command {

	public void execute(Ship ship);
}
