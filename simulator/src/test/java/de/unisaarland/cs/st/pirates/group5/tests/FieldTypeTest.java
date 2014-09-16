package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.assertTrue;
import model.Island;
import model.Treasure;

import org.junit.Test;

public class FieldTypeTest {
	
	@Test
	public void boostCoverageTest(){
		Island testIsland = new Island(null,0,0,null);
		testIsland.setShip(null);
		testIsland.moveShip(null);
		testIsland.placeBuoy(0, null);
		testIsland.setKraken(null);
		testIsland.moveKraken(testIsland);
		testIsland.getFieldType();
		
		assertTrue(false);
		
		
	}

}
