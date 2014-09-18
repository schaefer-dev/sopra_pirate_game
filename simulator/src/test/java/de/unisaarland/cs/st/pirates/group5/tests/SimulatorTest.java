package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import controller.Simulator;

public class SimulatorTest {
	String[] ships = new String[1];
	String map;
	String logfile;
	
	@Before
	public void setUp(){
		ships[0] = "/TestShip.ship";
		map = "/testMap.map";
		logfile = "src/test/resources/log.log";
	}

	@Test
	public void test() throws ArrayIndexOutOfBoundsException, NullPointerException, IOException {
		Simulator sim;
			sim = new Simulator(ships,map, 5,logfile, 2);
			sim.step(2);


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
