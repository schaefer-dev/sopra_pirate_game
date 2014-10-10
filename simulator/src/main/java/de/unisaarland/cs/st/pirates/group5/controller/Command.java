package de.unisaarland.cs.st.pirates.group5.controller;

import de.unisaarland.cs.st.pirates.group5.model.Ship;

public interface Command {

	void execute(Ship ship);
}
