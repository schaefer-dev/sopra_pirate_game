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
	Island geradeaus = new Island(map, 1,0);
	Water rechtsUntenVorne = new Water(map,0,1,null);
	Water rechtsUntenHinten = new Water(map,3,1,null);
	Water hinten = new Water(map,3,0,null);
	Island rechtsOben = new Island(map,0,3);
	Island linksOben = new Island(map,3,3);
	
	
	
	
	@Before
	public void setUp(){
		krakens.add(kraken);
		
		fields[0][0] = water;
		fields[1][0] = geradeaus;
		fields[0][1] = rechtsUntenVorne;
		fields[3][1] = rechtsUntenHinten;
		fields[3][0] = hinten;
		fields[0][3] = rechtsOben;
		fields[3][3] = linksOben;
		
		map.setMapValues(fields, 4, 2, first, krakens);
	}
	
	@Test
	public void test(){
		
		assertEquals("getNeighbour fail!",map.getNeighbour(water, 0), geradeaus);
		assertEquals("getNeighbour fail!",map.getNeighbour(water, 1), rechtsUntenVorne);
		assertEquals("getNeighbour fail!",map.getNeighbour(water, 2), rechtsUntenHinten);
		assertEquals("getNeighbour fail!",map.getNeighbour(water, 3), hinten);
		assertEquals("getNeighbour fail!",map.getNeighbour(water, 4), rechtsOben);
		assertEquals("getNeighbour fail!",map.getNeighbour(water, 5), linksOben);
		assertEquals("getNeighbour fail!",map.getNeighbour(geradeaus, 3), water);


		assertEquals("Konstukter Set FirstShip fail!",map.getFirstShip(), first);
			map.setFirstShip(second);
			assertEquals("SetFirstShip fail!",map.getFirstShip(), second);
		
		assertEquals("Map/Kraken Relation broken!", map.getKraken().size(), 1);
			assertTrue("Ultra sinnloser Test.",map.getKraken().contains(kraken));
		
		assertEquals("Not next free ActorID.",map.giveNewActorID(),4);
			assertEquals("Not next free ActorID.",map.giveNewActorID(),5);

		assertEquals("Not next free EntityID.",map.giveNewEntityID(),2);
			assertEquals("Not next free EntityID.",map.giveNewEntityID(),3);
	}
}
