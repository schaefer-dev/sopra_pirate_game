package de.unisaarland.cs.st.pirates.group5.tests;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

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
import model.Field;


public class CommandMoveTest {
	

	
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
	
	private Team teamfriend;
	private Team teamenemy;
	
	private char a;
	private char b;
	
	private Kraken kraken;
	
	private Move moveit;
	
	private int mecondition;
	private int enemycondition;
	private int meload;

	

	@Before
	public void setUp(){
		
		fields = new Field[3][3];
		
		moveit = new Move(13);
		DummyLogWriter testLog = new DummyLogWriter();
		Random testRandom = new Random();
		map = new Map(testRandom, testLog);
		
		
		a = 0;
		b = 1;
		teamfriend = new Team(a, null);
		teamenemy = new Team(b, null);
		
		
		
		
		island1 = new Island(map, 0, 0, null);
		water1 = new Water(map, 1, 0, null);
		provision1 = new ProvisionIsland(map, 2, 0);
		baseenemy = new Base(map, 0, 1, teamenemy, null);
		waterme = new Water(map, 1, 1, null);

		waterkraken = new Water(map, 2, 1, kraken);

		basefriend = new Base(map, 0, 2, teamfriend, null);
		waterenemy = new Water(map, 1, 2, null);
		waterfriend = new Water(map, 2, 2, null);
		
		kraken = new Kraken(0, waterkraken);
		shipme = new Ship(teamfriend, waterme, 0, shipfriend);
		shipenemy = new Ship(teamenemy, waterenemy, 1, null);
		shipfriend = new Ship(teamfriend, waterfriend, 2, null);
		
		waterfriend.setShip(shipfriend);
		waterenemy.setShip(shipenemy);
		waterkraken.setKraken(kraken);
		waterme.setShip(shipme);
		
		fields[0][0] = island1;
		fields[1][0] = water1;
		fields[2][0] = provision1;
		fields[0][1] = baseenemy;
		fields[1][1] = waterme;
		fields[2][1] = waterkraken;
		fields[0][2] = basefriend;
		fields[1][2] = waterenemy;
		fields[2][2] = waterfriend;
		
		ArrayList<Kraken> krakenlist = new ArrayList<Kraken>();
		krakenlist.add(kraken);
		
		map.setMapValues(fields, krakenlist);
		
		mecondition = shipme.getCondition();
		enemycondition = shipenemy.getCondition();
		meload = shipme.getLoad();
		
				
	}

	@Test
	 public void testMoveWater(){
		shipme.changeDirection(true);
		shipme.changeDirection(true);
		
		moveit.execute(shipme);
		
		assertTrue ("move to water1", shipme.getPosition().equals(water1));
		assertNull("Ship must be deleted from old field",waterme.getShip());
		 
		 
	 }
	
	@Test
	public void testMoveShipFriend(){
		shipme.changeDirection(false);
		moveit.execute(shipme);
		
		assertTrue ("move must not be succesfull", shipme.getPosition().equals(waterme));
		assertTrue("ship must not move, still old field",waterme.getShip().equals(shipme));
		assertTrue("pc must be changed to else case",shipme.getPC() == 13);
	}
	
	@Test
	public void testMoveShipEnemyWinsurvive(){
		shipme.changeDirection(false);
		shipme.changeDirection(false);
		shipme.changeMoral(4);
		shipenemy.changeMoral(-4);
		
		moveit.execute(shipme);
		
		assertTrue ("ship must not changed fields",shipme.getPosition().equals(waterme));
		assertTrue("ship must not changed fields",waterme.getShip().equals(shipme));
		assertTrue ("ship must not changed fields",shipenemy.getPosition().equals(waterenemy));
		assertTrue("ship must not changed fields",waterenemy.getShip().equals(shipenemy));
		assertTrue ("winner ship must not changed condition",shipme.getCondition() == mecondition);
		assertTrue ("loser ship must loose condition",shipenemy.getCondition() == (enemycondition + 1));
		assertTrue("pc must changed to else case",shipme.getPC() == 13);
		
	}
	
	@Test
	public void testMoveShipEnemyWinNotsurvive(){
		shipme.changeDirection(false);
		shipme.changeDirection(false);
		shipme.changeMoral(4);
		shipenemy.changeMoral(-4);
		shipenemy.changeCondition(-2);
		
		moveit.execute(shipme);
		
		assertTrue ("move must be succesfull, ship must changed fieds",shipme.getPosition().equals(waterenemy));
		assertNull("move must be succesfull, ship must changed fieds",waterme.getShip());
		assertTrue("move must be succesfull, ship must changed fieds",waterenemy.getShip().equals(shipme));
		assertTrue ("winner ship must not changed condition",shipme.getCondition() == mecondition);
		assertTrue("pc not in else case",shipme.getPC() != 13);
		
	}
	
	@Test
	public void testMoveShipEnemyLooseSurvive(){
		shipme.changeDirection(false);
		shipme.changeDirection(false);
		shipme.changeMoral(-4);
		shipenemy.changeMoral(4);
		
		moveit.execute(shipme);
		
		assertTrue ("move not succesfull, fields must not changed",shipme.getPosition().equals(waterme));
		assertTrue("move not succesfull, fields must not changed",waterme.getShip().equals(shipme));
		assertTrue ("move not succesfull, fields must not changed",shipenemy.getPosition().equals(waterenemy));
		assertTrue("move not succesfull, fields must not changed",waterenemy.getShip().equals(shipenemy));
		assertTrue ("winner ship must not changed condition",shipenemy.getCondition() == enemycondition);
		assertTrue ("loser ship must loose condition",shipme.getCondition() == (mecondition + 1));
		assertTrue("pc must changed to else case",shipme.getPC() == 13);
	}
	@Test
	public void testMoveShipEnemyLooseNotSurvive(){
		shipme.changeDirection(false);
		shipme.changeDirection(false);
		shipme.changeMoral(-4);
		shipenemy.changeMoral(4);
		
		moveit.execute(shipme);
		
		assertNull("ship looses, must be destroyed",waterme.getShip());
		assertTrue ("ship must not changed fields",shipenemy.getPosition().equals(waterenemy));
		assertTrue("ship must not changed fields",waterenemy.getShip().equals(shipenemy));
		assertTrue ("winnership must not changed condition",shipenemy.getCondition() == enemycondition);
		
	}
	
	@Test
	public void testMoveKraken(){
		
		moveit.execute(shipme);
		
		assertTrue("move was succesfull",shipme.getPosition().equals(waterkraken));
		assertTrue ("move was succesfull",waterkraken.getShip().equals(shipme));
		assertTrue ("condition must changed", shipme.getCondition() == (mecondition+1));
		assertTrue("move was succesfull",shipme.getPC() != 13);
		
		
	}
	
	@Test
	public void testMoveBaseEnemy(){
		shipme.changeDirection(false);
		shipme.changeDirection(false);
		shipme.changeDirection(false);
		
		moveit.execute(shipme);
		
		assertTrue("move not succesfull",shipme.getPosition().equals(waterme));
		assertTrue ("must not loose condition", shipme.getCondition() == mecondition);
		assertTrue("move not succesfull, else case", shipme.getPC() == 13);
	}
	
	@Test
	public void testMoveBaseFriend(){
		shipenemy.changeDirection(true);
		shipenemy.changeDirection(true);
		
		moveit.execute(shipenemy);
		
		assertTrue("move succesfull", shipenemy.getPosition().equals(baseenemy));
		assertTrue("move succesfull", baseenemy.getShip().equals(shipenemy));
		assertTrue (" must not loose condition",shipenemy.getCondition() == enemycondition);
		assertTrue("move was succesfull, not else case", shipme.getPC() != 13);
		
		
	}
	
	@Test
	public void testMoveIsland(){
		
		shipme.changeDirection(true);
		shipme.changeDirection(true);
		
		moveit.execute(shipme);
		
		shipme.changeDirection(true);
		
		moveit.execute(shipme);
		
		assertTrue("move not succesfull", shipme.getPosition().equals(water1));
		assertTrue("must loose condition",shipme.getCondition() == (mecondition+1));
		assertTrue ("must loose treasure", shipme.getLoad() <= meload);
		assertTrue("move not succesfull, else case",shipme.getPC() == 13);
		
	}
	
	@Test
	public void testMoveProvisionIsland(){
		shipme.changeDirection(true);
		
		moveit.execute(shipme);
		
		assertTrue("move not succesfull", shipme.getPosition().equals(waterme));
		assertTrue("must loose condition",shipme.getCondition() == (mecondition+1));
		assertTrue (shipme.getLoad() <= meload); //???
		assertTrue("move not succesfull, else case",shipme.getPC() == 13);
		
	}
}
