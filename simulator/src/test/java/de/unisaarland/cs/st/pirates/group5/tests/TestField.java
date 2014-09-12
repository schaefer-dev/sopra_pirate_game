package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Map;
import model.Team;
import model.Ship;
import model.Field;
import model.Water;
import model.Island; 
import model.ProvisionIsland;
import model.FieldType;
import model.Kraken;
import model.Base;

import controller.Command;

import org.junit.Before;
import org.junit.Test;

public class TestField {

	Random random;
	Map map = new Map(random);
	Field[][] fields = new Field[4][4];
	List<Command> tactics = new ArrayList<Command>();
	List<Kraken> krakens;
	Team b;
	Team a = new Team('a',tactics); 
	Kraken kraken = new Kraken(0, null);
	Water water = new Water(map, 0, 0, kraken);
	Island geradeaus = new Island(map, 1,0);
	Water rechtsUntenVorne = new Base(map,0,1,b);
	Water rechtsUntenHinten = new Base(map,3,1,a);
	Water hinten = new Water(map,3,0,null);
	Island linksObenVorne = new Island(map,0,3);
	Island linksObenHinten = new ProvisionIsland(map,3,3);
	Ship firstShip = new Ship(a,null,0,null);
	
	@Before
	public void setUp(){
		krakens.add(kraken);
		
		fields[0][0] = water;//ship
		fields[0][1] = rechtsUntenVorne;//EnemyBase
		fields[1][0] = geradeaus;
		fields[3][0] = hinten;//kraken, treasure
		fields[0][3] = linksObenVorne;
		fields[3][3] = linksObenHinten; //ProvisionIsland
		fields[3][1] = rechtsUntenHinten;//Base
		
		kraken.setField(hinten);
		hinten.setKraken(kraken);
		
		map.setMapValues(fields, 1, 1, firstShip, krakens);
		
	}
	
	@Test
	public void test() {
		
		assertEquals("getNeighbour fail!",water.getNeigbour(0),geradeaus);
		assertEquals("getNeighbour fail!",water.getNeigbour(1),rechtsUntenVorne);
		assertEquals("getNeighbour fail!",water.getNeigbour(2),rechtsUntenHinten);
		assertEquals("getNeighbour fail!",water.getNeigbour(3),hinten);
		assertEquals("getNeighbour fail!",water.getNeigbour(4),linksObenHinten);
		assertEquals("getNeighbour fail!",water.getNeigbour(5),linksObenVorne);
			assertEquals("getNeighbour fail!",hinten.getNeigbour(0),water);
			assertEquals("getNeighbour fail!",linksObenHinten.getNeigbour(1),water);
			assertEquals("getNeighbour fail!",linksObenHinten.getNeigbour(0),linksObenVorne);
			
		assertEquals("FieldType broken",geradeaus.getFieldType(), FieldType.Island);
		assertEquals("FieldType broken",water.getFieldType(), FieldType.Water);
		assertEquals("FieldType broken",hinten.getFieldType(), FieldType.Water);
		assertEquals("FieldType broken",rechtsUntenVorne.getFieldType(), FieldType.Base);
		assertEquals("FieldType broken",rechtsUntenHinten.getFieldType(), FieldType.Base);
		assertEquals("FieldType broken",linksObenHinten.getFieldType(), FieldType.Island);
		assertEquals("FieldType broken",linksObenVorne.getFieldType(), FieldType.ProvisionIsland);
	}

}
