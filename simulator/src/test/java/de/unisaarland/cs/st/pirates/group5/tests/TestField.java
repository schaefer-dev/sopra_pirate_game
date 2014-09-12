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

import commands.Move;

public class TestField {

	Random random;
	Map map = new Map(random);
	Field[][] fields = new Field[4][4];
	List<Command> tactics = new ArrayList<Command>();
	List<Command> btactics = new ArrayList<Command>();
	List<Kraken> krakens;
	Team b = new Team('b',btactics);
	Team a = new Team('a',tactics); 
	Move goAhead = new Move(0);
	Kraken kraken = new Kraken(0, null);
	
	Water water = new Water(map, 0, 0, kraken);
	Water geradeaus = new Water(map, 1,0,null);
	Water rechtsUntenVorne = new Base(map,0,1,b);
	Water rechtsUntenHinten = new Base(map,3,1,a);
	Water hinten = new Water(map,3,0,null);
	Island linksObenVorne = new Island(map,0,3);
	Island linksObenHinten = new ProvisionIsland(map,3,3);
	Water  elsewhere = new Water(map, 2,0,null);
	Water  nextToElseWhere = new Water(map, 1,1, null);
	
	Ship firstShip = new Ship(a,null,0,null);
	Ship alliedShip = new Ship(a,null,1,firstShip);
	Ship enemyShip = new Ship(b,null,2,alliedShip);
	Ship enemyShip2 = new Ship(b,null,3,enemyShip);
	Ship alliedShip2 = new Ship(a,null,1,enemyShip2);
	
	@Before
	public void setUp(){
		krakens.add(kraken);
		
		fields[0][0] = water;//ship
		fields[0][1] = rechtsUntenVorne;//EnemyBase, enemyShip
		fields[1][0] = geradeaus;//alliedsip
		fields[3][0] = hinten;//kraken, treasure
		fields[0][3] = linksObenVorne;
		fields[3][3] = linksObenHinten; //ProvisionIsland
		fields[3][1] = rechtsUntenHinten;//Base
		fields[2][0] = elsewhere; //enemyship2
		fields[1][1] = nextToElseWhere;//alliedship2
		
		map.setMapValues(fields, 1, 1, firstShip, krakens);
		
		tactics.add(goAhead);
		btactics.add(goAhead);

		kraken.setField(hinten);
		hinten.setKraken(kraken);
		hinten.exchangeTreasure(5);
		water.setShip(firstShip);
		
		firstShip.setField(water);
		firstShip.changeCondition(-3);
		firstShip.setLoad(3);
		firstShip.changeDirection(false);//firstship einmal um sich selbst drehen
		firstShip.changeDirection(false);
		firstShip.changeDirection(false);
		water.setShip(firstShip);
		
		alliedShip.setField(geradeaus);
		alliedShip.changeCondition(-3);
		alliedShip.setLoad(4);
		alliedShip.changeMoral(-2);
		alliedShip.changeDirection(false);//ebenso alliedship
		alliedShip.changeDirection(false);
		alliedShip.changeDirection(false);
		geradeaus.setShip(alliedShip);
		
		enemyShip.setField(rechtsUntenVorne);
		enemyShip.setLoad(2);
		enemyShip.changeDirection(true);//enemyShip soll nach 0,0 schauen
		rechtsUntenVorne.setShip(enemyShip);
		
		alliedShip2.setField(elsewhere);
		enemyShip2.changeDirection(false);
		elsewhere.setShip(alliedShip2);
		
		enemyShip2.setField(nextToElseWhere);
		enemyShip2.changeDirection(true);
		nextToElseWhere.setShip(enemyShip2);		
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
			
		assertEquals("FieldType broken",geradeaus.getFieldType(), FieldType.Water);
		assertEquals("FieldType broken",water.getFieldType(), FieldType.Water);
		assertEquals("FieldType broken",hinten.getFieldType(), FieldType.Water);
		assertEquals("FieldType broken",rechtsUntenVorne.getFieldType(), FieldType.Base);
		assertEquals("FieldType broken",rechtsUntenHinten.getFieldType(), FieldType.Base);
		assertEquals("FieldType broken",linksObenHinten.getFieldType(), FieldType.Island);
		assertEquals("FieldType broken",linksObenVorne.getFieldType(), FieldType.ProvisionIsland);
		
		//Fall1: Ein Schiff wird vom Kraken versenkt
		assertEquals("ExchangeTreasure broken.", hinten.getTreasure().getValue(),5);
			firstShip.act();
			assertEquals("ExchangeTreasure broken.", hinten.getTreasure().getValue(),8);
				
				//Fall2: Ein Schiff, das stand, versinkt
				alliedShip.act();
				enemyShip.act();
				assertEquals("ExchangeTreasure broken.", water.getTreasure().getValue(),2);
				assertEquals("ExchangeTreasure broken.", water.getShip().getLoad(),4);
				
				//Fall3: Ein Schiff, das sich bewegt hat, verliert,ohne zu versinken; und Schiff, 
				alliedShip2.setLoad(1);
				enemyShip2.changeCondition(-2);
				enemyShip2.setLoad(4);
				enemyShip2.act();				
				assertEquals("ExchangeTreasure broken.", elsewhere.getShip().getLoad(),4);
				assertEquals("ExchangeTreasure broken.", nextToElseWhere.getTreasure().getValue(),1);
				
				//Fall4: Ein Schiff, das sich nicht bewegt hat, verliert, ohne zu versinken. 
				alliedShip2.setLoad(4);
				alliedShip2.changeCondition(-2);
				enemyShip2.setLoad(0);
				enemyShip2.act();
				assertEquals("ExchangeTreasure broken.", nextToElseWhere.getShip().getLoad(),4);
				if(elsewhere.getTreasure() != null)	//Kein Schatz mit Value 0 darf entstehen.
					fail("Da sollte kein Schatz liegen");
				
							//Fall5: Ein Schiff, das sich bewegt hat, verliert und versinkt
							alliedShip2.setLoad(1);
							enemyShip2.changeCondition(-3);
							enemyShip2.setLoad(4);
							enemyShip2.act();
							assertEquals("ExchangeTreasure broken.", nextToElseWhere.getTreasure().getValue(),1);
							assertEquals("ExchangeTreasure broken.", elsewhere.getShip().getLoad(),4);
	}

}
