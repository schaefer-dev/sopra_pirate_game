package de.unisaarland.cs.st.pirates.group5.tests;
import static org.junit.Assert.*;

import java.io.InputStream;

import model.Base;
import model.Island;
import model.Kraken;
import model.Map;
import model.ProvisionIsland;
import model.Ship;
import model.Team;
import model.Water;

import org.junit.Before;
import org.junit.Test;

import commands.Move;
import controller.MapGenerator;
import model.Field;


public class MoveTests {
	

	
	private Map map;
	
	private Field[][] fields;
	
	private Field island1;
	private Field water1;
	private Field provision1;
	private Field baseenemy;
	private Field waterme;
	private Field waterkraken;
	private Field basefriend;
	private Field waterenemy;
	private Field waterfriend;
	
	private Ship shipme;
	private Ship shipfriend;
	private Ship shipenemy;
	
	private Team tf;
	private Team te;
	
	private char a;
	private char b;
	
	private Kraken kraken;
	
	private Move moveit;
	
	private int mecon;
	private int enemycon;
	private int meload;

	

	@Before
	public void setUp(){
		
		fields = new Field[3][3];
		
		moveit = new Move(13);
		map = new Map(null);
		a = 0;
		b = 1;
		tf = new Team(a, null);
		te = new Team(b, null);
		
		kraken = new Kraken(0, waterkraken);
		
		shipme = new Ship(tf, waterme, 0, shipfriend);
		shipenemy = new Ship(te, waterenemy, 1, null);
		shipfriend = new Ship(tf, waterfriend, 2, null);
		
		island1 = new Island(map, 0, 0, null);
		water1 = new Water(map, 1, 0, null);
		provision1 = new ProvisionIsland(map, 2, 0);
		baseenemy = new Base(map, 0, 1, te);
		waterme = new Water(map, 1, 1, null);
		waterkraken = new Water(map, 2, 1, kraken);
		basefriend = new Base(map, 0, 2, tf);
		waterenemy = new Water(map, 1, 2, null);
		waterenemy.setShip(shipenemy);
		waterfriend = new Water(map, 2, 2, null);
		waterfriend.setShip(shipfriend);
		
		fields[0][0] = island1;
		fields[1][0] = water1;
		fields[2][0] = provision1;
		fields[0][1] = baseenemy;
		fields[1][1] = waterme;
		fields[2][1] = waterkraken;
		fields[0][2] = basefriend;
		fields[1][2] = waterenemy;
		fields[2][2] = waterfriend;
		
		mecon = shipme.getCondition();
		enemycon = shipenemy.getCondition();
		meload = shipme.getLoad();
		
				
	}

	@Test
	 public void testMoveWater(){
		shipme.changeDirection(true);
		shipme.changeDirection(true);
		
		moveit.execute(shipme);
		assertTrue (shipme.getPosition().equals(water1));
		assertNull(waterme.getShip());
		 
		 
	 }
	
	@Test
	public void testMoveShipFriend(){
		shipme.changeDirection(false);
		moveit.execute(shipme);
		assertTrue (shipme.getPosition().equals(waterme));
		assertTrue(waterme.getShip().equals(shipme));
		assertTrue(shipme.getPC() == 13);
	}
	
	@Test
	public void testMoveShipEnemyWinsurvive(){
		shipme.changeDirection(false);
		shipme.changeDirection(false);
		shipme.changeMoral(4);
		shipenemy.changeMoral(-4);

		
		moveit.execute(shipme);
		
		assertTrue (shipme.getPosition().equals(waterme));
		assertTrue(waterme.getShip().equals(shipme));
		assertTrue (shipenemy.getPosition().equals(waterenemy));
		assertTrue(waterenemy.getShip().equals(shipenemy));
		
		assertTrue (shipme.getCondition() == mecon);
		assertTrue (shipenemy.getCondition() == (enemycon + 1));
		
		assertTrue(shipme.getPC() == 13);
		
	}
	
	@Test
	public void testMoveShipEnemyWinNotsurvive(){
		shipme.changeDirection(false);
		shipme.changeDirection(false);
		shipme.changeMoral(4);
		shipenemy.changeMoral(-4);
		shipenemy.changeCondition(-2);
		
		moveit.execute(shipme);
		
		assertTrue (shipme.getPosition().equals(waterenemy));
		assertNull(waterme.getShip());
		assertTrue(waterenemy.getShip().equals(shipme));
		
		assertTrue (shipme.getCondition() == mecon);
		assertTrue (shipenemy.getCondition() == 0);
		
		assertTrue(shipme.getPC() != 13);
		
	}
	
	@Test
	public void testMoveShipEnemyLooseSurvive(){
		shipme.changeDirection(false);
		shipme.changeDirection(false);
		shipme.changeMoral(-4);
		shipenemy.changeMoral(4);
		
		moveit.execute(shipme);
		
		assertTrue (shipme.getPosition().equals(waterme));
		assertTrue(waterme.getShip().equals(shipme));
		assertTrue (shipenemy.getPosition().equals(waterenemy));
		assertTrue(waterenemy.getShip().equals(shipenemy));
		
		assertTrue (shipenemy.getCondition() == enemycon);
		assertTrue (shipme.getCondition() == (mecon + 1));
		assertTrue(shipme.getPC() == 13);
	}
	@Test
	public void testMoveShipEnemyLooseNotSurvive(){
		shipme.changeDirection(false);
		shipme.changeDirection(false);
		shipme.changeMoral(-4);
		shipenemy.changeMoral(4);
		
		moveit.execute(shipme);
		
		
		assertNull(waterme.getShip());
		assertTrue (shipenemy.getPosition().equals(waterenemy));
		assertTrue(waterenemy.getShip().equals(shipenemy));
		
		assertTrue (shipme.getCondition() == 0);
		assertTrue (shipenemy.getCondition() == enemycon);
		assertTrue(shipme.getPC() != 13);
	}
	
	@Test
	public void testMoveKraken(){
		
		moveit.execute(shipme);
		
		assertTrue(shipme.getPosition().equals(waterkraken));
		assertTrue (waterkraken.getShip().equals(shipme));
		
		assertTrue (shipme.getCondition() == (mecon+1));
		
		assertTrue(shipme.getPC() != 13);
		
		
	}
	
	@Test
	public void testMoveBaseEnemy(){
		shipme.changeDirection(false);
		shipme.changeDirection(false);
		shipme.changeDirection(false);
		
		moveit.execute(shipme);
		
		assertTrue(shipme.getPosition().equals(waterme));
		assertTrue (shipme.getCondition() == mecon);
		
		assertTrue(shipme.getPC() == 13);
	}
	
	@Test
	public void testMoveBaseFriend(){
		shipenemy.changeDirection(true);
		shipenemy.changeDirection(true);
		
		moveit.execute(shipenemy);
		
		assertTrue(shipenemy.getPosition().equals(baseenemy));
		assertTrue(baseenemy.getShip().equals(shipenemy));
		
		assertTrue (shipenemy.getCondition() == enemycon);
		
		assertTrue(shipme.getPC() != 13);
		
		
	}
	
	@Test
	public void testMoveIsland(){
		
		shipme.changeDirection(true);
		shipme.changeDirection(true);
		
		moveit.execute(shipme);
		
		shipme.changeDirection(true);
		
		moveit.execute(shipme);
		
		assertTrue(shipme.getPosition().equals(water1));
		assertTrue(shipme.getCondition() == (mecon+1));
		assertTrue (shipme.getLoad() <= meload);
		
		assertTrue(shipme.getPC() == 13);
		
	}
	
	@Test
	public void testMoveProvisionIsland(){
		shipme.changeDirection(true);
		
		moveit.execute(shipme);
		
		assertTrue(shipme.getPosition().equals(waterme));
		assertTrue(shipme.getCondition() == (mecon+1));
		assertTrue (shipme.getLoad() <= meload);
		
		assertTrue(shipme.getPC() == 13);
		
	}
}
