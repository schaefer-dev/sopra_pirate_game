package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Field;
import model.Map;
import model.CellType;
import model.Register;
import model.ShipType;
import model.Treasure;
import model.Kraken;
import model.Water;

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
	
	@Test(expected = IllegalArgumentException.class)
	public void BoostMapClass1(){
		Random testRandom = new Random();
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom,testLog);
		
		Field[][] fieldArray=new Field[3][3];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<3); i++){
			for (int z=0; (z<3); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		testMap.getNeighbour(fieldArray[1][1], 7);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void BoostMapClass2(){
		Random testRandom = new Random();
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom,testLog);
		
		Field[][] fieldArray=new Field[3][3];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<3); i++){
			for (int z=0; (z<3); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		testMap.getNeighbour(fieldArray[1][1], 7);
	}
	
	@Test(expected = NumberFormatException.class)
	public void BoostMapClass3(){
		Random testRandom = new Random();
		DummyLogWriter testLog = new DummyLogWriter();
		Map testMap = new Map(testRandom,testLog);
		
		Field[][] fieldArray=new Field[3][3];
		List<Kraken> kraken = new ArrayList<Kraken>();
		
		for (int i=0; (i<3); i++){
			for (int z=0; (z<3); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, kraken);
		while(true){
			testMap.giveNewActorID();
		}
	}
}
