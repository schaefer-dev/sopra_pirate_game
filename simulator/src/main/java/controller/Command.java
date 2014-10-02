package controller;

import model.Ship;

public interface Command {

	void execute(Ship ship);
}
