package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.util.Random;

import model.Base;
import model.Field;
import model.Island;
import model.Map;
import model.ProvisionIsland;
import model.Ship;
import model.Team;
import model.Treasure;
import model.Water;

import org.junit.Test;

import view.SimpleLogWriter;
import commands.Drop;
import commands.Pickup;

public class CommandDropTests {
	/*
	 * drop auf Basis = kein minus 2 Moral
	 * 
	 * drop auf Feld wenn tatsaechlich gedroppt wurde = minus 2 Moral
	 * 
	 * drop mit schiffladung =0 und Wasserschatz = 1
	 * 
	 * drop mit Schiffladung = 4 und leerem Wasser
	 * 
	 * drop mit Schiffladung = 3 und insel = 10
	 * 
	 * drop mit schiffladung = 0 und insel = 0
	 */

	@Test
	public void drop0Treasure3Moral(){
		
		Drop testDrop = new Drop();
		
		Random testRandom = new Random(1);	
		SimpleLogWriter testLog = new SimpleLogWriter();
		Map testMap = new Map(testRandom, testLog);
		Treasure testTreasure = new Treasure(0,1);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap, z, i, null);
			}
		}	
		testMap.setMapValues(fieldArray, null);
		
		fieldArray[1][1]=new Water(testMap, 0, 0, null);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(0);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-1);
		
		testDrop.execute(testShip);
		
		assertTrue(testShip.getLoad()==0);
		assertTrue(testShip.getMoral()==3); // No Load so moral does not change.
		assertTrue(testShip.getPC()==5);				// PC already increased in act-method of ship
		assertTrue(fieldArray[1][1].getTreasure()==null);	
	}
	
	@Test
	public void drop4Treasure3Moral(){
		
		Drop testDrop = new Drop();
		
		Random testRandom = new Random(1);	
		SimpleLogWriter testLog = new SimpleLogWriter();
		Map testMap = new Map(testRandom, testLog);
		Treasure testTreasure = new Treasure(0,1);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap, z, i, null);
			}
		}	
		testMap.setMapValues(fieldArray, null);
		
		fieldArray[1][1]=new Water(testMap, 0, 0, null);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(4);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-1);
		
		testDrop.execute(testShip);
		
		assertTrue(testShip.getLoad()==0);
		assertTrue(testShip.getMoral()==1);
		assertTrue(testShip.getPC()==5);				// PC already increased in act-method of ship
		assertTrue(fieldArray[1][1].getTreasure()!=null);	
		assertTrue(fieldArray[1][1].getTreasure().getValue()==4);	
	}
	
	@Test
	public void drop4Treasure1Moral(){
		
		Drop testDrop = new Drop();
		
		Random testRandom = new Random(1);	
		SimpleLogWriter testLog = new SimpleLogWriter();
		Map testMap = new Map(testRandom, testLog);
		Treasure testTreasure = new Treasure(0,1);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap, z, i, null);
			}
		}	
		testMap.setMapValues(fieldArray, null);
		
		fieldArray[1][1]=new Water(testMap, 0, 0, null);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		assertTrue(fieldArray[1][1].getTreasure()==null);
		
		fieldArray[1][1].exchangeTreasure(5);
		
		assertTrue(fieldArray[1][1].getTreasure()!=null);
		
		testShip.setLoad(4);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-3);
		
		testDrop.execute(testShip);
		
		assertTrue(testShip.getLoad()==0);
		assertTrue(testShip.getMoral()==0);
		assertTrue(testShip.getPC()==5);				// PC already increased in act-method of ship
		assertTrue(fieldArray[1][1].getTreasure()!=null);	
		assertTrue(fieldArray[1][1].getTreasure().getValue()==9);	
	}
	
	@Test
	public void drop3TreasureBase(){
		
		Drop testDrop = new Drop();
		
		Random testRandom = new Random(1);	
		SimpleLogWriter testLog = new SimpleLogWriter();
		Map testMap = new Map(testRandom, testLog);
		Treasure testTreasure = new Treasure(0,1);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap, z, i, null);
			}
		}	
		testMap.setMapValues(fieldArray, null);
		
		Team testTeam = new Team('a', null);
		
		fieldArray[1][1]=new Water(testMap, 0, 0, null);
		
		Ship testShip = new Ship(testTeam, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		assertTrue(fieldArray[0][0].getTreasure()==null); // TODO: Strange order of asserts
		
		fieldArray[1][1]=new Base(testMap, 1, 1, testTeam, testShip);
		
		assertTrue(fieldArray[0][0].getTreasure()!=null); // TODO: stange order of asserts
		
		testShip.setLoad(3);
		
		testShip.setPC(5);
		
		testShip.changeMoral(+4);
		
		testDrop.execute(testShip);
		
		assertTrue(testShip.getLoad()==0);
		assertTrue(testShip.getMoral()==4);
		assertTrue(testShip.getPC()==5);				// PC already increased in act-method of ship
		assertTrue(fieldArray[1][1].getTreasure()==null);	
		assertTrue(testTeam.getScore()==3);
	}
	
	
}
