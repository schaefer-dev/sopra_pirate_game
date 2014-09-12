package de.unisaarland.cs.st.pirates.group5.tests;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.InputStream;

import model.Field;
import model.Island;
import model.Map;
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
	
	
	@Before
	public void setUp() throws FileNotFoundException {
		stream = getClass().getResourceAsStream("/map2x2.txt");
		mapgen = new MapGenerator(null);
		field = new Island(map, 0, 0, null);
		field2 = new Water(map, 1, 0, null);
		
	}

	@Test
	public void mapGenTest(){
		map = mapgen.createMap(stream, null, null);
		assertTrue (map.giveNewEntityID() == 0);
		assertTrue (map.giveNewActorID() == 1);
		assertNull (map.getKraken());
		assertFalse (map.getFirstShip() == null);
		assertEquals (map.getNeighbour(field, 0), field2);
	}
	
}
