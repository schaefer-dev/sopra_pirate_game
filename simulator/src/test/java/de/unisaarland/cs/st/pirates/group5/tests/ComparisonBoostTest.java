package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import controller.BoolComparison;
import controller.CellTypeComparison;
import controller.IntComparison;
import controller.ShipTypeComparison;

public class ComparisonBoostTest {

	@Test
	public void BoostTest(){
		
		BoolComparison testBool = new BoolComparison(null,true);
		CellTypeComparison testCell = new CellTypeComparison(null, null, null);
		ShipTypeComparison testShip = new ShipTypeComparison(null,null,null);
		IntComparison testInt = new IntComparison(null,null,null);
		
		
		
		
		assertTrue(testInt.equals(testInt));
		assertFalse(testInt.equals(null));
		assertFalse(testInt.equals(testBool));
		assertTrue(testBool.equals(testBool));
		assertFalse(testBool.equals(testCell));
		assertTrue(testCell.equals(testCell));
		assertFalse(testCell.equals(testBool));
		assertTrue(testShip.equals(testShip));
		assertFalse(testShip.equals(testCell));
		
	}
}
