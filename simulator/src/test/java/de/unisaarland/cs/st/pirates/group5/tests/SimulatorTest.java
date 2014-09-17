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
		map = "/map2x2.txt";
		logfile = "/log.log";
	}

	@Test
	public void test() {
	//	DummyLogWriter log = new DummyLogWriter();
		Simulator sim;
		try {
			sim = new Simulator(ships,map, 5,logfile, 2);
			sim.step(2);
//			if(log.getWhat().size() < 6)
//				fail("Not enough logger calls");
//			if(log.getWhat().size() > 6)
//				fail("Too many logger calls");
//			if(log.getWhat().size() == 6 )
//				assertEquals("Wrong output @ close", log.getWhat().get(3), "close");
//				assertEquals("Wrong output @ init", log.getWhat().get(0),"init");
//				assertEquals("Wrong output @ map init", log.getWhat().get(1),"haha");
//				assertEquals("Wrong output @ ships init", log.getWhat().get(2),"ship");
//				assertEquals("Wrong output @ step1",log.getWhat().get(3),"logStep" );
//				assertEquals("Wrong output @ step2",log.getWhat().get(4),"logStep" );

		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
