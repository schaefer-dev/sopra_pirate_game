package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.awt.List;
import java.util.Random;

import model.Map;
import model.CellType;
import model.Register;
import model.ShipType;
import model.Treasure;
import model.Kraken;

import org.junit.Test;

import controller.BoolComparison;
import controller.CellTypeComparison;
import controller.IntComparison;
import controller.Operator;
import controller.ShipTypeComparison;

public class BoostCoverageTest {

	@Test
	public void BoostComparisonTest(){
		
		BoolComparison testBool = new BoolComparison(Register.sense_marker4,true);
		CellTypeComparison testCell = new CellTypeComparison(Operator.Unequal,Register.sense_celltype, CellType.EnemyHome);
		ShipTypeComparison testShip = new ShipTypeComparison(Operator.Unequal,Register.sense_shiptype,ShipType.Friend);
		IntComparison testInt = new IntComparison(Operator.Greater,Register.ship_moral,Register.sense_shipcondition);
		
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
	
	@Test(expected = IllegalArgumentException.class)
	public void BoostTreasureClass(){
		Treasure testTreasure = new Treasure(0, 2);
		testTreasure.changeValue(-10);
	}
	
	@Test
	public void BoostMapClass(){
		Random testRandom = new Random();
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom,testLog);
		
		List krakenList = new List();
		testMap.setMapValues(field, krakenList);
	}
}
