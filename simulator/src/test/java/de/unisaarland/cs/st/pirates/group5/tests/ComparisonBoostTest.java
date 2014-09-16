package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import controller.BoolComparison;
import controller.CellTypeComparison;
import controller.ShipTypeComparison;

public class ComparisonBoostTest {

	@Test
	public void BoostTest(){
		
		BoolComparison testBool = new BoolComparison(null,true);
		CellTypeComparison testCell = new CellTypeComparison(null, null, null);
		ShipTypeComparison testShip = new ShipTypeComparison(null,null,null);
		
		
		
		
		
		assertTrue(testBool.equals(testBool));
		assertTrue(testCell.equals(testCell));
		assertTrue(testShip.equals(testShip));
		
	}
}
