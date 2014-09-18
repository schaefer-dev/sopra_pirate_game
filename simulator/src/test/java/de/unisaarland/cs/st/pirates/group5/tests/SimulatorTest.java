package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;

import controller.Simulator;

public class SimulatorTest {
	String[] ships = {"/TestShip.ship","/TestShip2.ship" ,"/TestShip3.ship" ,"/TestShip4.ship", "/TestShip5.ship"};
	String map;
	String logfile;
	int rounds;
	@Before
	public void setUp(){
		
		map = "/map.txt";
		logfile = "/log.log";
		rounds = 10000;
	}

	@Test

	public void test() throws URISyntaxException, ArrayIndexOutOfBoundsException, NullPointerException, IOException {
	//	DummyLogWriter log = new DummyLogWriter();

		Simulator sim;
			sim = new Simulator(ships,map, 5,logfile, rounds);
			sim.step(rounds);
			sim.end();


	}// catch (ArrayIndexOutOfBoundsException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NullPointerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		


}
