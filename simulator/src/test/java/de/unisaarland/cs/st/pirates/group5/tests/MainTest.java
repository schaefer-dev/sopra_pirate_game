package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import de.unisaarland.cs.st.pirates.group5.main.Main;

public class MainTest {

	
	@Test
	public void runTest(){
		try{
			Main m = new Main();
			Main.main(null);
		}
		catch(Exception e){
			fail();
		}
		
	}
	
	
	
}
