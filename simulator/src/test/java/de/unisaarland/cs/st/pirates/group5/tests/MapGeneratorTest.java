package de.unisaarland.cs.st.pirates.group5.tests;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import model.Base;
import model.Field;
import model.Island;
import model.Map;
import model.ProvisionIsland;
import model.Ship;
import model.Water;

import org.junit.Before;
import org.junit.Test;

import controller.MapGenerator;
public class MapGeneratorTest {
	
	
	private InputStream stream;
	private MapGenerator mapgen;
	private Map map;
	private Field field;
	private Field field2;
	private Field field3;
	private Field field4;
	private Ship ship;
	
	
	@Before
	public void setUp() throws FileNotFoundException {
		stream = getClass().getResourceAsStream("/map2x2.txt");
		mapgen = new MapGenerator();
		field = new Island(map, 0, 0, null);
		field2 = new Water(map, 1, 0, null);
		field3 = new Base(map, 0, 1, null, ship);
		ship.setField(field3);
		field4 = new ProvisionIsland(map, 1, 1);
		
	}

	@Test
	public void mapGenTest2x2() throws IOException {
		map = mapgen.createMap(stream, null, null, null);
		assertTrue (map.giveNewEntityID() == 0);
		assertTrue (map.giveNewActorID() == 1);
		assertNull (map.getKraken());
		assertFalse (map.getFirstShip() == null);
		assertEquals (map.getNeighbour(field, 0), field2);
		assertTrue (map.getNeighbour(field, 0).equals(field2));
		assertTrue (map.getNeighbour(field, 1).equals(field3));
		assertTrue (map.getNeighbour(field3, 0).equals(field4));
	}
	
}
