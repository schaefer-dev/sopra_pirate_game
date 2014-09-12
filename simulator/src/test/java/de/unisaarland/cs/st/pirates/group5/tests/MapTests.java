package de.unisaarland.cs.st.pirates.group5.tests;


import model.Map;
import model.Ship;
import model.Field;
import model.Kraken;

import org.junit.Before;
import org.junit.Test;

public class MapTests {

	private Map map;
	@Before
	public void setUp(){
		
	}
	
	@Test
	public void test(){
		map.getFirstShip();
		map.getKraken();
		map.getLogWriter();
		map.getNeighbour(field, direction);
		map.giveNewActorID();
		map.giveNewEntityID();
		map.setFirstShip(ship);
		map.setMapValues(fields, nextFreeActorId, nextFreeEntityId, firstShip, kraken);
	}
}
