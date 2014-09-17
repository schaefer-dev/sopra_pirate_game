package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Field;
import model.Map;
import model.CellType;
import model.Register;
import model.Ship;
import model.ShipType;
import model.Treasure;
import model.Kraken;
import model.Water;

import org.junit.Test;

import controller.BoolComparison;
import controller.CellTypeComparison;
import controller.Command;
import controller.IntComparison;
import controller.Operator;
import controller.ShipTypeComparison;
import controller.Translator;

public class BoostCoverageTest {
	
	
	private Translator translator = new Translator();
	private final static Operator LESS = Operator.Less;
	private final static Operator EQUAL = Operator.Equal;
	private final static Operator UNEQUAL = Operator.Unequal;
	private List<String> lines;
	private List<Command> commands;

	public BoostCoverageTest()
	{
		ObjectConstructorTest constr = new ObjectConstructorTest();
		lines = constr.getStringList();
		commands = constr.getCommandList();
	}

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
	
	@Test(expected = NullPointerException.class)
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
		testMap.getFirstShip();
		testMap.setFirstShip(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void BoostMapClass4(){
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
		testMap.setMapValues(null, kraken);
	}
	
	@Test (expected = NullPointerException.class)
	public void BoostFieldClass1(){
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
		Water testWater = new Water(null, 0, 0, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void BoostFieldClass2(){
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
		Water testWater = new Water(testMap, -5, 300, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void BoostFieldClass3(){
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
		fieldArray[1][1].exchangeTreasure(-10);
	}
	
	@Test (expected = IllegalStateException.class)
	public void BoostFieldClass4(){
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
		fieldArray[1][1].moveShip(fieldArray[2][2]);
	}
	
	@Test
	public void BoostFieldClass5(){
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
		Ship testShip = new Ship(null, null, 0, null);
		Ship testShip2 = new Ship(null, null, 2, null);
		fieldArray[1][1].setShip(testShip);
		fieldArray[2][2].setShip(testShip2);
		
		assertFalse("no error for wrong moveShip call", fieldArray[1][1].moveShip(fieldArray[2][2]));
	}
	
	@Test
	public void BoostFieldClass6(){
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
		Ship testShip = new Ship(null, null, 0, null);
		Ship testShip2 = new Ship(null, null, 2, null);
		Kraken testKraken1= new Kraken(0, fieldArray[1][1]);
		Kraken testKraken2 = new Kraken(0, fieldArray[2][2]);
		
		fieldArray[2][2].setKraken(testKraken2);
		fieldArray[1][1].setKraken(testKraken1);
		fieldArray[1][1].setKraken(testKraken2);
		assertEquals(testKraken1,fieldArray[1][1].getKraken());
	}
	
	@Test
	public void BoostFieldClass7(){
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
		Ship testShip = new Ship(null, null, 0, null);
		Ship testShip2 = new Ship(null, null, 2, null);
		Kraken testKraken1= new Kraken(0, fieldArray[1][1]);
		Kraken testKraken2 = new Kraken(0, fieldArray[2][2]);
		
		fieldArray[2][2].setKraken(testKraken2);
		fieldArray[1][1].setKraken(testKraken1);
		fieldArray[1][1].setKraken(null);
		assertEquals(null,fieldArray[1][1].getKraken());
	}
	
	@Test(expected = IllegalStateException.class)
	public void BoostFieldClass8(){
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
		Ship testShip = new Ship(null, null, 0, null);
		Ship testShip2 = new Ship(null, null, 2, null);
		Kraken testKraken1= new Kraken(0, fieldArray[1][1]);
		Kraken testKraken2 = new Kraken(0, fieldArray[2][2]);
		
		fieldArray[2][2].setKraken(testKraken2);
		fieldArray[1][1].setKraken(testKraken1);
		fieldArray[1][1].setKraken(null);
		fieldArray[1][1].moveKraken(fieldArray[2][2]);
	}
	
	@Test
	public void BoostTranslatorTools1(){
		String badTactic = "move else 2\n"
							+"goto 0\n"
							+"flipzero 1 else 0\n"
							+ "flipzero 10 else 12\n"
							+ "goto 13\n"
							+ "goto -12\n"
							+ "sense 12\n"
							+ "sense auto\n"
							+ "sense -14\n"
							+ "sense enemy\n"
							+ "pickup auto else enemy\n"
							+ "pickup ship\n"
							+ "pickup -12 else 13\n"
							+ "pickup 2 else 3\n"
							+ "pickup else 3\n"
							+ "else 15\n"
							+ "else -13\n "
							+ "if else else 5\n"
							+ "goto ship\n"
							+ "goto else\n"
							+ "goto pickup\n"
							+ "else 4\n"
							+ "else flipzero 5\n"
							+ "turn left\n"
							+ "turn right\n"
							+ "turn 4\n"
							+ "turn turn\n"
							+ "turn else\n"
							+ "turn pickup 3 else 5\n"
							+ "goto 12\n"
							+ "goto 3\n"
							+ "drop 3\n"
							+ "drop else 5\n"
							+ "drop left\n"
							+ "sense 1\n"
							+ "sense 3\n"
							+ "sense 15\n"
							+ "sense 0\n"
							+ "sense -5\n"
							+ "pickup sensedir else 5\n"
							+ "mark 1\n"
							+ "mark -1\n"
							+ "mark 13\n"
							+ "mark else 3\n"
							+ "mark else\n"
							+ "mark turn 3\n"
							+ "mark turn\n"
							+ "repair turn\n"
							+ "repair sense 0\n"
							+ "repair else -1\n"
							+ "repair else 15\n"
							+ "ifall repair else 15\n"
							+ "ifall 1=1 else 15\n"
							+ "ifany 2=2 else 19\n"
							+ "ifany auto=auto else 19\n"
							+ "ifany 1=a else 1\n"
							+ "ifany 1=1 else -5\n"
							+ "ifall auto=auto 1=1 else -20\n";
						
								
		InputStream in = stringToStream(badTactic);
		try{
		translator.run(in);
		}
		catch(IllegalArgumentException e){}
	}


	private InputStream stringToStream(String tactic){

		byte[] temp = tactic.getBytes();
		InputStream in = new ByteArrayInputStream(temp);
		in = new BufferedInputStream(in);
		return in;
	}
}
