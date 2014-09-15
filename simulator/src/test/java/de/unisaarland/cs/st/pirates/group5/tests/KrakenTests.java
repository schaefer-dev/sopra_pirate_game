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

import view.SimpleLogWriter;

public class KrakenTests {

	/*
	 * move Test
	 */
	
	@Test
	public void moveKrakenWaterWithoutKraken01(){
		
		/* no kraken on field */
		Random testRandom = new Random(12);	// ergebnis des ersten Randoms ist 1!
		SimpleLogWriter testLog = new SimpleLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		testMap.setMapValues(fieldArray, null);
		
		Kraken testKraken = new Kraken(0,fieldArray[1][1]);
		
		testKraken.move();
		assertTrue("KrakenX wrong", testKraken.getField().getX()==2);
		assertTrue("KrakenY wrong", testKraken.getField().getY()==2);
		assertTrue("Kraken not deleted correctly", testMap.getNeighbour(fieldArray[1][1], 1).getKraken()==null);
		assertTrue("Kraken vanished", testMap.getNeighbour(fieldArray[2][2], 1).getKraken()!=null);
		assertTrue("no correct Kraken found", testMap.getNeighbour(fieldArray[2][2], 1).getKraken()==testKraken);
	}
	
	@Test
	public void moveKrakenWaterWithKraken01(){
		
		/* with kraken on field */
		Random testRandom = new Random(12);	// ergebnis des ersten Randoms ist 1!
		SimpleLogWriter testLog = new SimpleLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Water(testMap,z,i,null);
			}
		}	
		Kraken errorKraken = new Kraken(0,fieldArray[1][1]);
		fieldArray[2][2] = new Water(testMap,2,2,errorKraken);
		
		testMap.setMapValues(fieldArray, null);
		
		Kraken testKraken = new Kraken(0,fieldArray[1][1]);
		
		testKraken.move();
		assertTrue("KrakenX wrong", testKraken.getField().getX()==2);
		assertTrue("KrakenY wrong", testKraken.getField().getY()==2);
		assertTrue("Kraken not found where he should be", testMap.getNeighbour(fieldArray[1][1], 1).getKraken()!=null);
		assertTrue("correct Kraken not found where he should be", testMap.getNeighbour(fieldArray[1][1], 1).getKraken()==testKraken);
		assertTrue("Kraken which was hit not found correctly", testMap.getNeighbour(fieldArray[2][2], 1).getKraken()==errorKraken);
	}
	
	
	@Test
	public void moveKrakenBase01(){
		Random testRandom = new Random(12);	// ergebnis des ersten Randoms ist 1!
		SimpleLogWriter testLog = new SimpleLogWriter();
		Map testMap = new Map(testRandom, testLog);
		Team testTeam = new Team('a',null);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Base(testMap,z,i,testTeam);
			}
		}	
		Kraken testKraken = new Kraken(0,fieldArray[1][1]);
		fieldArray[1][1]=new Water(testMap,1,1,testKraken);
		
		testMap.setMapValues(fieldArray, null);
		
		testKraken.move();
		assertTrue("KrakenX wrong", testKraken.getField().getX()==1);
		assertTrue("KrakenY wrong", testKraken.getField().getY()==1);
		assertTrue("Kraken found on wrong field",testMap.getNeighbour(fieldArray[2][2], 1).getKraken()==null);
		assertTrue("No Kraken found where it should be", testMap.getNeighbour(fieldArray[1][1], 1).getKraken()!=null);
		assertTrue("No correct Kraken found where it should be", testMap.getNeighbour(fieldArray[1][1], 1).getKraken()==testKraken);
	}
	
	@Test
	public void moveKrakenIsland01(){
		Random testRandom = new Random(12);	// ergebnis des ersten Randoms ist 1!
		SimpleLogWriter testLog = new SimpleLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new Island(testMap,z,i,null);
			}
		}	
		Kraken testKraken = new Kraken(0,fieldArray[1][1]);
		fieldArray[1][1]=new Water(testMap,1,1,testKraken);
		
		testMap.setMapValues(fieldArray, null);
		
		testKraken.move();
		assertTrue("KrakenX wrong", testKraken.getField().getX()==1);
		assertTrue("KrakenY wrong", testKraken.getField().getY()==1);
		assertTrue("Kraken moved but he should not!",testMap.getNeighbour(fieldArray[2][2], 1).getKraken()==null);
		assertTrue("Kraken vanished", testMap.getNeighbour(fieldArray[1][1], 1).getKraken()!=null);
		assertTrue("Kraken not found where it should be", testMap.getNeighbour(fieldArray[1][1], 1).getKraken()==testKraken);
		
	}
	
	@Test
	public void moveKrakenProvisionIsland01(){
		Random testRandom = new Random(12);	// ergebnis des ersten Randoms ist 1!
		SimpleLogWriter testLog = new SimpleLogWriter();
		Map testMap = new Map(testRandom, testLog);
		
		Field[][] fieldArray=new Field[3][3];
		
		for (int i=0; (i<2); i++){
			for (int z=0; (z<2); z++){
				fieldArray[z][i]=new ProvisionIsland(testMap,z,i);
			}
		}	
		Kraken testKraken = new Kraken(0,fieldArray[1][1]);
		fieldArray[1][1]=new Water(testMap,1,1,testKraken);
		
		testMap.setMapValues(fieldArray, null);
		
		testKraken.move();
		assertTrue("KrakenX wrong", testKraken.getField().getX()==1);
		assertTrue("KrakenY wrong", testKraken.getField().getY()==1);
		assertTrue("Kraken moved but he should not!",testMap.getNeighbour(fieldArray[2][2], 1).getKraken()==null);
		assertTrue("Kraken vanished", testMap.getNeighbour(fieldArray[1][1], 1).getKraken()!=null);
		assertTrue("Kraken not found where it should be", testMap.getNeighbour(fieldArray[1][1], 1).getKraken()==testKraken);
	}
	
	
}
