package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.util.Random;

import model.Base;
import model.Field;
import model.Island;
import model.Kraken;
import model.Map;
import model.ProvisionIsland;
import model.Team;
import model.Water;

import org.junit.Test;

public class KrakenTests {

	/*
	 * move Test
	 */
	
	@Test
	public void moveKrakenWaterWithoutKraken(){
		
		/* no kraken on field */
		Random testRandom = new Random(12);	// ergebnis des ersten Randoms ist 1!
		Map testMap = new Map(testRandom);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, 0, 0, null, null);
		
		Kraken testKraken = new Kraken(0,fieldArray[1][1]);
		
		testKraken.move();
		assertTrue(testKraken.getField().getX()==2);
		assertTrue(testKraken.getField().getY()==2);
	}
	
	@Test
	public void moveKrakenWaterWithKraken(){
		
		/* with kraken on field */
		Random testRandom = new Random(12);	// ergebnis des ersten Randoms ist 1!
		Map testMap = new Map(testRandom);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		Kraken errorKraken = new Kraken(0,fieldArray[1][1]);
		fieldArray[2][2] = new Water(testMap,2,2,errorKraken);
		
		testMap.setMapValues(fieldArray, 0, 0, null, null);
		
		Kraken testKraken = new Kraken(0,fieldArray[1][1]);
		
		testKraken.move();
		assertTrue(testKraken.getField().getX()==1);
		assertTrue(testKraken.getField().getY()==1);
	}
	
	
	@Test
	public void moveKrakenBase01(){
		Random testRandom = new Random(12);	// ergebnis des ersten Randoms ist 1!
		Map testMap = new Map(testRandom);
		Team testTeam = new Team('a',null);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Base(testMap,z,i,testTeam);
			}
		}	
		Kraken testKraken = new Kraken(0,fieldArray[1][1]);
		fieldArray[1][1]=new Water(testMap,1,1,testKraken);
		
		testMap.setMapValues(fieldArray, 0, 0, null, null);
		
		testKraken.move();
		assertTrue(testKraken.getField().getX()==1);
		assertTrue(testKraken.getField().getY()==1);
	}
	
	@Test
	public void moveKrakenIsland01(){
		Random testRandom = new Random(12);	// ergebnis des ersten Randoms ist 1!
		Map testMap = new Map(testRandom);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Island(testMap,z,i);
			}
		}	
		Kraken testKraken = new Kraken(0,fieldArray[1][1]);
		fieldArray[1][1]=new Water(testMap,1,1,testKraken);
		
		testMap.setMapValues(fieldArray, 0, 0, null, null);
		
		testKraken.move();
		assertTrue(testKraken.getField().getX()==1);
		assertTrue(testKraken.getField().getY()==1);
	}
	
	@Test
	public void moveKrakenProvisionIsland01(){
		Random testRandom = new Random(12);	// ergebnis des ersten Randoms ist 1!
		Map testMap = new Map(testRandom);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new ProvisionIsland(testMap,z,i);
			}
		}	
		Kraken testKraken = new Kraken(0,fieldArray[1][1]);
		fieldArray[1][1]=new Water(testMap,1,1,testKraken);
		
		testMap.setMapValues(fieldArray, 0, 0, null, null);
		
		testKraken.move();
		assertTrue(testKraken.getField().getX()==1);
		assertTrue(testKraken.getField().getY()==1);
	}
	
	
}
