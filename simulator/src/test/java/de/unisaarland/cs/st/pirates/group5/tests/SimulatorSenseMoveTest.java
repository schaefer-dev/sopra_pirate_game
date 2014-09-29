package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;

import controller.Simulator;

public class SimulatorSenseMoveTest {
	String[] ships = {"/sensemove01.ship","/sensemove02doNothing.ship"};
	String map;
	String logfile;
	int rounds;
	@Before
	public void setUp(){
		map = "/map4x4sensemove1.txt";
		logfile = "/log.log";
		rounds = 200;
	}

	
	/*
	@Test (expected = IllegalArgumentException.class)
	public void test() throws URISyntaxException, ArrayIndexOutOfBoundsException, NullPointerException, IOException {
		String[] ships2 = {"/TestShip.ship","/TestShip2.ship","/TestShip3.ship"};
		Simulator sim = new Simulator(ships2,map, 5,logfile, rounds);
	}
	*/
	
	@Test
	public void test3() throws ArrayIndexOutOfBoundsException, NullPointerException, IOException, URISyntaxException{
		Simulator sim = new Simulator(ships,map, 5,logfile, rounds);
		sim.step(rounds);
		//sim.end();
	}
}
