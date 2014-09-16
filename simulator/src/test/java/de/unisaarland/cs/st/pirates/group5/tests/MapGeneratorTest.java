package de.unisaarland.cs.st.pirates.group5.tests;
import static org.junit.Assert.*;

import java.awt.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import model.Base;
import model.Field;
import model.Island;
import model.Map;
import model.ProvisionIsland;
import model.Ship;
import model.Team;
import model.Water;

import org.junit.Before;
import org.junit.Test;

import view.Log;
import controller.MapGenerator;
import de.unisaarland.cs.st.pirates.logger.LogWriter;
public class MapGeneratorTest {
	
	
	
	private InputStream stream;
	private MapGenerator mapgen;
	private Map map;
	private Field field;
	private Field field2;
	private Field field3;
	private Field field4;
	private Ship ship;
	private Team team;
	ArrayList<Team> teamlist = new ArrayList<Team>();
	char a = 0;
	Random random = new Random();
	
	@Before
	public void setUp() throws FileNotFoundException {
		
		stream = getClass().getResourceAsStream("/map2x2.txt");
		mapgen = new MapGenerator();
		team = new Team(a, null);
		teamlist.add(team);
		field = new Island(map, 0, 0, null);
		field2 = new Water(map, 1, 0, null);
		ship = new Ship(team, null, 0, null);
		field3 = new Base(map, 0, 1, null, ship);
		ship.setField(field3);
		field4 = new ProvisionIsland(map, 1, 1);
		
	}

	@Test
	public void mapGenTest() throws IOException {
		LogWriter log = new DummyLogWriter();
		map = mapgen.createMap(stream, teamlist, log, random);
		assertTrue (map.giveNewEntityID() == 0);
		assertTrue (map.giveNewActorID() == 1);
		assertTrue (map.getKraken().size() == 0);
		assertFalse (map.getFirstShip() == null);
		
		
		assertTrue (map.getNeighbour(field, 0).getFieldType().equals(field2.getFieldType()));
		assertTrue (map.getNeighbour(field, 1).getFieldType().equals(field3.getFieldType()));
		assertTrue (map.getNeighbour(field3, 0).getFieldType().equals(field4.getFieldType()));
		
		assertTrue (map.getNeighbour(field, 0).getBuoys().equals(field2.getBuoys()));
		assertTrue (map.getNeighbour(field, 1).getBuoys().equals(field3.getBuoys()));
		assertTrue (map.getNeighbour(field3, 0).getBuoys().equals(field4.getBuoys()));
		
		assertTrue (map.getNeighbour(field, 0).getKraken().equals(field2.getKraken()));
		assertTrue (map.getNeighbour(field, 1).getKraken().equals(field3.getKraken()));
		assertTrue (map.getNeighbour(field3, 0).getKraken().equals(field4.getKraken()));
		
		assertTrue (map.getNeighbour(field, 0).getShip().equals(field2.getShip()));
		assertTrue (map.getNeighbour(field, 1).getShip().equals(field3.getShip()));
		assertTrue (map.getNeighbour(field, 0).getShip().equals(field4.getShip()));
		
		assertTrue (map.getNeighbour(field, 0).getX() == (field2.getX()));
		assertTrue (map.getNeighbour(field, 1).getX() == (field3.getX()));
		assertTrue (map.getNeighbour(field, 0).getX() == (field4.getX()));
		
		assertTrue (map.getNeighbour(field, 0).getY() == (field2.getY()));
		assertTrue (map.getNeighbour(field, 1).getY() == (field3.getY()));
		assertTrue (map.getNeighbour(field, 0).getY() == (field4.getY()));
	}
	
}
