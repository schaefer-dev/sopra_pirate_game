package de.unisaarland.cs.st.pirates.group5.tests;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import model.Map;

import org.junit.Before;
import org.junit.Test;

import controller.MapGenerator;
public class MapGeneratorTest {
	
	
	private InputStream stream;
	private MapGenerator mapgen;
	private Map map;
	
	@Before
	public void setUp() throws FileNotFoundException {
		stream = getClass().getResourceAsStream("/map2x2.txt");
		mapgen = new MapGenerator(null);
		
	}

	@Test
	public void mapGenTest(){
		map = mapgen.createMap(stream, null, null);
	}
	
}
