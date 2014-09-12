package de.unisaarland.cs.st.pirates.group5.tests;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Island;
import model.Map;
import model.Ship;
import model.Field;
import model.Kraken;
import model.Team;
import model.Water;

import org.junit.Before;
import org.junit.Test;

public class MapTests {

	Random random;
	Map map = new Map(random);
	Team a;
	Field[][] fields = new Field[4][4];
	Ship first = new Ship(a, null, 1, null);
	Ship second = new Ship(a, null, 2, first);
	Ship third = new Ship(a, null, 3, second);
	Kraken kraken = new Kraken(1, null);
	List<Kraken> krakens = new ArrayList<Kraken>();
	Water water = new Water(map, 0, 0, kraken);
	Island island = new Island(map, 1,0);
	Water water2 = new Water(map,0,1,null);
	Water water3 = new Water(map,3,1,null);
	Water water4 = new Water(map,3,0,null);
	Island island2 = new Island(map,0,3);
	Island island3 = new Island(map,3,3);
	
	
	
	
	@Before
	public void setUp(){
		krakens.add(kraken);
		
		fields[0][0] = water;
		fields[1][0] = island;
		fields[0][1] = water2;
		fields[3][1] = water3;
		fields[3][0] = water4;
		fields[0][3] = island2;
		fields[3][3] = island3;
		
		map.setMapValues(fields, 4, 2, first, krakens);
	}
	
	@Test
	public void test(){
		
		assertEquals("",map.getNeighbour(water, 0), island);
		assertEquals("",map.getNeighbour(water, 1), water2);
		assertEquals("",map.getNeighbour(water, 2), water3);
		assertEquals("",map.getNeighbour(water, 3), water4);
		assertEquals("",map.getNeighbour(water, 4), island2);
		assertEquals("",map.getNeighbour(water, 5), island3);

		assertEquals("",map.getFirstShip(), first);
			map.setFirstShip(second);
			assertEquals("",map.getFirstShip(), second);
		
		assertEquals("", map.getKraken().size(), 1);
		
		
		assertEquals("",map.giveNewActorID(),4);
			assertEquals("",map.giveNewActorID(),5);

		assertEquals("",map.giveNewEntityID(),2);
			assertEquals("",map.giveNewEntityID(),3);
	}
}
