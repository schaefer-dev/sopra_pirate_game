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

import commands.Drop;
import commands.Pickup;
import commands.Refresh;


public class CommandRefreshTests {
	
	@Test
	public void refreshProvisionIsland01(){
		
		Refresh testRefresh = new Refresh(4, 10);
		
		Random testRandom = new Random(1);	
		Map testMap = new Map(testRandom);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap, z, i, null);
			}
		}	
		testMap.setMapValues(fieldArray, 0, 0, null, null);
		
		fieldArray[1][0]=new ProvisionIsland(testMap, 1, 0);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(0);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-4);
		
		testRefresh.execute(testShip);
		
		assertTrue(testShip.getLoad()==0);
		assertTrue(testShip.getMoral()==4);
		assertTrue(testShip.getPC()==5);				// PC already increased in act-method of ship
	}
	
	@Test
	public void refreshProvisionIsland02(){
		
		Refresh testRefresh = new Refresh(4, 10);
		
		Random testRandom = new Random(1);	
		Map testMap = new Map(testRandom);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap, z, i, null);
			}
		}	
		testMap.setMapValues(fieldArray, 0, 0, null, null);
		
		//fieldArray[1][0]=new ProvisionIsland(testMap, 1, 0);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(0);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-4);
		
		testRefresh.execute(testShip);
		
		assertTrue(testShip.getLoad()==0);
		assertTrue(testShip.getMoral()==0);
		assertTrue(testShip.getPC()==5);				// PC already increased in act-method of ship
	}

	@Test
	public void refreshProvisionIsland(){
		
		Refresh testRefresh = new Refresh(4, 10);
		
		Random testRandom = new Random(1);	
		Map testMap = new Map(testRandom);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap, z, i, null);
			}
		}	
		testMap.setMapValues(fieldArray, 0, 0, null, null);
		
		fieldArray[1][0]=new Base(testMap, 1, 0, null);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(0);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-4);
		
		testRefresh.execute(testShip);
		
		assertTrue(testShip.getLoad()==0);
		assertTrue(testShip.getMoral()==0);
		assertTrue(testShip.getPC()==5);				// PC already increased in act-method of ship
	}
	
	@Test
	public void refreshProvisionIsland03(){
		
		Refresh testRefresh = new Refresh(4, 10);
		
		Random testRandom = new Random(1);	
		Map testMap = new Map(testRandom);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap, z, i, null);
			}
		}	
		testMap.setMapValues(fieldArray, 0, 0, null, null);
		
		fieldArray[1][0]=new Island(testMap, 1, 0, null);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(0);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-4);
		
		testRefresh.execute(testShip);
		
		assertTrue(testShip.getLoad()==0);
		assertTrue(testShip.getMoral()==0);
		assertTrue(testShip.getPC()==5);				// PC already increased in act-method of ship
	}
}
