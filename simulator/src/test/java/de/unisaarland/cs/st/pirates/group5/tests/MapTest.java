package de.unisaarland.cs.st.pirates.group5.tests;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Island;
import model.Map;
import model.ProvisionIsland;
import model.Ship;
import model.Field;
import model.Kraken;
import model.Team;
import model.Treasure;
import model.Water;

import org.junit.Before;
import org.junit.Test;

import view.SimpleLogWriter;

public class MapTest {


	Random random;
	Map map = new Map(random, new SimpleLogWriter());

	Team a;
	Field[][] fields = new Field[4][4];
	Ship first = new Ship(a, null, 1, null);
	Ship second = new Ship(a, null, 2, first);
	Ship third = new Ship(a, null, 3, second);
	Kraken kraken = new Kraken(1, null);
	List<Kraken> krakens = new ArrayList<Kraken>();
	Water water = new Water(map, 0, 0, kraken);
	Island geradeaus = new Island(map, 1,0,null);
	Water rechtsUntenVorne = new Water(map,0,1,null);
	Water rechtsUntenHinten = new Water(map,3,1,null);
	Water hinten = new Water(map,3,0,null);
	ProvisionIsland linksObenVorne = new ProvisionIsland(map,0,3);
	Treasure treasure = new Treasure(0, 9);
	Island linksObenHinten = new Island(map,3,3,treasure);	
	
	@Before
	public void setUp(){
		krakens.add(kraken);
		
		fields[0][0] = water;
		fields[1][0] = geradeaus;
		fields[0][1] = rechtsUntenVorne;
		fields[3][1] = rechtsUntenHinten;
		fields[3][0] = hinten;
		fields[0][3] = linksObenVorne;
		fields[3][3] = linksObenHinten;
		
		map.setMapValues(fields, krakens);
	}
	
	@Test (expected=Exception.class)
	public void testGetNeighbour(){
		
		assertEquals("getNeighbour fail!",map.getNeighbour(water, 0), geradeaus);
		assertEquals("getNeighbour fail!",map.getNeighbour(water, 1), rechtsUntenVorne);
		assertEquals("getNeighbour fail!",map.getNeighbour(water, 2), rechtsUntenHinten);
		assertEquals("getNeighbour fail!",map.getNeighbour(water, 3), hinten);
		assertEquals("getNeighbour fail!",map.getNeighbour(water, 4), linksObenVorne);
		assertEquals("getNeighbour fail!",map.getNeighbour(water, 5), linksObenHinten);
		assertEquals("getNeighbour fail!",map.getNeighbour(geradeaus, 3), water);
	}
	@Test 
	public void testGetFirstShip(){

		assertEquals("Konstukter Set FirstShip fail!",map.getFirstShip(), first);
			map.setFirstShip(second);
			assertEquals("SetFirstShip fail!",map.getFirstShip(), second);
	}
	@Test 
	public void testGetKraken(){	
		assertEquals("Map/Kraken relation broken!", map.getKraken().size(), 1);
			assertTrue("Ultra sinnloser Test.",map.getKraken().contains(kraken));
	}
	@Test (expected=Exception.class)
	public void testGetIDs(){
		assertEquals("Not next free ActorID.",map.giveNewActorID(),0);
			assertEquals("Not next free ActorID.",map.giveNewActorID(),1);

		assertEquals("Not next free EntityID.",map.giveNewEntityID(),0);
			assertEquals("Not next free EntityID.",map.giveNewEntityID(),1);
	}
	
	@Test (expected=Exception.class)
	public void testGetTreasure(){		
		if(linksObenHinten.getTreasure() == null)
			fail("da muss ein Schatz liegen");
		
		if(geradeaus.getTreasure() != null )
			fail("da darf kein Schatz liegen");
				
	}
}
