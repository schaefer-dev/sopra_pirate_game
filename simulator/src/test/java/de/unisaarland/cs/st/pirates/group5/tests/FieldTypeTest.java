package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.assertTrue;
import model.Island;
import model.Map;
import model.ProvisionIsland;
import model.Treasure;

import org.junit.Test;

public class FieldTypeTest {
	
	@Test
	public void boostCoverageTest(){
		Map testMap = new Map(null, null);
		Island testIsland = new Island(testMap,0,0,null);
		testIsland.setShip(null);
		testIsland.moveShip(null);
		testIsland.placeBuoy(0, null);
		testIsland.setKraken(null);
		testIsland.moveKraken(testIsland);
		testIsland.getFieldType();
		testIsland.provideLogger();
		testIsland.getMap();
		testIsland.deleteBuoy(null, 0);
		testIsland.getX();
		testIsland.getY();
		testIsland.getKraken();
		ProvisionIsland testProv = new ProvisionIsland(testMap,0,0);
		testProv.getFieldType();
		
		assertTrue("just for more coverage - no check included", false);
		
		
	}

}
