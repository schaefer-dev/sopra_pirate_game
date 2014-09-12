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

import commands.Pickup;

public class CommandPickupTests {
	/*
	 * pickup mit schiffladung =0 und Insel = 1
	 * 
	 * pickup auf falsches Feld (keine Island)
	 * 
	 * pickup mit Schiffladung = 4
	 * 
	 * pickup mit Schiffladung = 3 und insel = 2
	 * 
	 * pickup mit schiffladung = 0 und insel = 3
	 * 
	 * pickup mit schiffladung = 0 und insel = 4
	 * 
	 * pickup mit schiffladung = 0 und insel = 5
	 * 
	 * pickup mit schiffladung = 3 und insel = 0
	 * 
	 * pickup auf eigenem Feld (schatz im wasser)
	 * 
	 * lastPositiveActionCounter not important here, already tested in changemoral!
	 * 
	 * moraländerungen testen (+ keine wenn fehlschlägt)
	 */

	@Test
	public void pickup1Treasure3Moral(){
		
		Pickup testPickup = new Pickup(4, 10);
		
		Random testRandom = new Random(1);	
		Map testMap = new Map(testRandom);
		Treasure testTreasure = new Treasure(0,1);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Island(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, 0, 0, null, null);
		
		fieldArray[1][1]=new Water(testMap, 0, 0, null);
		
		fieldArray[0][0]=new Island(testMap,0,0,testTreasure);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(0);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-1);
		
		testPickup.execute(testShip);
		
		assertTrue(testShip.getLoad()==1);
		assertTrue(testShip.getMoral()==4);
		assertTrue(testShip.getPC()==5);				// PC already increased in act-method of ship
		assertTrue(fieldArray[0][0].getTreasure()==null);	
	}
	
	@Test
	public void pickup1Treasure1Moral(){
		
		Pickup testPickup = new Pickup(4, 10);
		
		Random testRandom = new Random(1);	
		Map testMap = new Map(testRandom);
		Treasure testTreasure = new Treasure(0,1);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Island(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, 0, 0, null, null);
		
		fieldArray[1][1]=new Water(testMap, 0, 0, null);
		
		fieldArray[0][0]=new Island(testMap,0,0,testTreasure);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(0);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-3);
		
		testPickup.execute(testShip);
		
		assertTrue(testShip.getLoad()==1);
		assertTrue(testShip.getMoral()==3);
		assertTrue(testShip.getPC()==5);				// PC already increased in act-method of ship
		assertTrue(fieldArray[0][0].getTreasure()==null);
	}
	
	@Test
	public void pickup5Treasure1Moral(){
		
		Pickup testPickup = new Pickup(4, 10);
		
		Random testRandom = new Random(1);	
		Map testMap = new Map(testRandom);
		Treasure testTreasure = new Treasure(0,5);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Island(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, 0, 0, null, null);
		
		fieldArray[1][1]=new Water(testMap, 0, 0, null);
		
		fieldArray[0][0]=new Island(testMap,0,0,testTreasure);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(0);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-3);
		
		testPickup.execute(testShip);
		
		assertTrue(testShip.getLoad()==4);
		assertTrue(testShip.getMoral()==3);
		assertTrue(testShip.getPC()==5);				// PC already increased in act-method of ship
		assertTrue(fieldArray[0][0].getTreasure().getValue()==1);
	}
	
	@Test
	public void pickup5Treasure1MoralAlreadyfull(){
		
		Pickup testPickup = new Pickup(4, 10);
		
		Random testRandom = new Random(1);	
		Map testMap = new Map(testRandom);
		Treasure testTreasure = new Treasure(0,5);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Island(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, 0, 0, null, null);
		
		fieldArray[1][1]=new Water(testMap, 0, 0, null);
		
		fieldArray[0][0]=new Island(testMap,0,0,testTreasure);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(4);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-3);
		
		testPickup.execute(testShip);
		
		assertTrue(testShip.getLoad()==4);
		assertTrue(testShip.getMoral()==1);
		assertTrue(testShip.getPC()==10);				// else Part
		assertTrue(fieldArray[0][0].getTreasure().getValue()==5);
	}
	
	@Test
	public void pickup0Treasure1Moral(){
		
		Pickup testPickup = new Pickup(4, 10);
		
		Random testRandom = new Random(1);	
		Map testMap = new Map(testRandom);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Island(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, 0, 0, null, null);
		
		fieldArray[1][1]=new Water(testMap, 0, 0, null);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(1);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-3);
		
		testPickup.execute(testShip);
		
		assertTrue(testShip.getLoad()==1);
		assertTrue(testShip.getMoral()==1);
		assertTrue(testShip.getPC()==10);				// else Part
		assertTrue(fieldArray[0][0].getTreasure().getValue()==5);
	}
	
	@Test
	public void pickup5Treasure1MoralInWater(){
		
		Pickup testPickup = new Pickup(6, 10);
		
		Random testRandom = new Random(1);	
		Map testMap = new Map(testRandom);
		Treasure testTreasure = new Treasure(0,5);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Island(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, 0, 0, null, null);
		
		fieldArray[1][1]=new Water(testMap, 0, 0, null);
		
		fieldArray[0][0]=new Water(testMap,0,0,null);
		
		assertTrue(fieldArray[0][0].getTreasure()==null);
		
		fieldArray[0][0].exchangeTreasure(5);
		
		assertTrue(fieldArray[0][0].getTreasure()!=null);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(1);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-3);
		
		testPickup.execute(testShip);
		
		assertTrue(testShip.getLoad()==4);
		assertTrue(testShip.getMoral()==3);
		assertTrue(testShip.getPC()==5);				// else Part
		assertTrue(fieldArray[1][1].getTreasure().getValue()==2);
	}
	
	@Test
	public void pickup1Treasure1MoralInWater(){
		
		Pickup testPickup = new Pickup(6, 10);
		
		Random testRandom = new Random(1);	
		Map testMap = new Map(testRandom);
		Treasure testTreasure = new Treasure(0,5);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Island(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, 0, 0, null, null);
		
		fieldArray[1][1]=new Water(testMap, 0, 0, null);
		
		fieldArray[0][0]=new Water(testMap,0,0,null);
		
		assertTrue(fieldArray[0][0].getTreasure()==null);
		
		fieldArray[0][0].exchangeTreasure(1);
		
		assertTrue(fieldArray[0][0].getTreasure()!=null);
		
		Ship testShip = new Ship(null, fieldArray[1][1], 0, null);
		
		fieldArray[1][1].setShip(testShip);
		
		testShip.setLoad(1);
		
		testShip.setPC(5);
		
		testShip.changeMoral(-3);
		
		testPickup.execute(testShip);
		
		assertTrue(testShip.getLoad()==2);
		assertTrue(testShip.getMoral()==3);
		assertTrue(testShip.getPC()==5);				// else Part
		assertTrue(fieldArray[1][1].getTreasure()==null);
	}
	
	
}
