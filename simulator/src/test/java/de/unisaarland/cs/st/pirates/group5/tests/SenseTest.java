package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Base;
import model.BoolWert;
import model.CellType;
import model.Field;
import model.Island;
import model.Map;
import model.ProvisionIsland;
import model.Ship;
import model.ShipType;
import model.Team;
import model.Treasure;
import model.Water;
import model.Register;

import org.junit.Before;
import org.junit.Test;

import view.SimpleLogWriter;
import commands.Goto;
import commands.Move;
import commands.Sense;
import controller.Command;

public class SenseTest {
	
	Sense sense1 = new Sense(0);
	Sense sense2 = new Sense(1);
	Sense sense3 = new Sense(2);
	Sense sense4 = new Sense(3);
	Sense sense5 = new Sense(4);
	Sense sense6 = new Sense(5);
	Sense sense7 = new Sense(6);
	Move  move   = new Move(0);
	Goto  goto7  = new Goto(7);
	
	Treasure treasure = new Treasure(15,9);

	List<Command> senses = new ArrayList<Command>();
	Team a = new Team('a',senses);
	Team b;
	
	Field[][] fields = new Field[4][4];
	Random random;
	SimpleLogWriter simpleLog = new SimpleLogWriter();
	Map map = new Map(random,simpleLog);
	
	Water water = new Water(map, 0, 3,null);
	ProvisionIsland provision = new ProvisionIsland(map,0,1);
	Island island = new Island(map,1,0, treasure);
	Water enemyship = new Water(map,0,3,null);
	Ship ship = new Ship(a,water,1, null);
	Ship alienShip1 = new Ship(b,enemyship,2,ship);
	Base enemyBase = new Base(map,3,1,b, alienShip1);
	Water alliedship = new Water(map,3,3,null);
	
	Base homeBase = new Base(map,0,0,a, ship);	
	
	Ship alienShip2 = new Ship(a,alliedship,3,alienShip1);
		
		@Before
		public void setUp(){
			senses.add(sense1);
			senses.add(sense2);
			senses.add(sense3);
			senses.add(sense4);
			senses.add(sense5);
			senses.add(sense6);
			senses.add(sense7);
			senses.add(move);
			senses.add(goto7);
			alliedship.setShip(alienShip2);//3,3
			alliedship.placeBuoy(3, a);
			alliedship.placeBuoy(4, a);
			enemyship.setShip(alienShip1);//1,0
			enemyship.placeBuoy(5, a);
			water.placeBuoy(1, a);//0,3
			water.placeBuoy(1, b);
			water.placeBuoy(4, a);
			water.placeBuoy(2, a);
			
			ship.setLoad(3);
	
			alienShip1.setLoad(3);
			alienShip1.changeMoral(-3);
			alienShip1.changeCondition(-3);
	
			fields[0][0] = homeBase;
			fields[0][1] = provision;
			fields[3][1] = island;
			fields[3][0] = enemyBase;
			fields[3][3] = alliedship;
			fields[1][0] = enemyship;
			fields[0][3] = water;
	
			map.setMapValues(fields, null);
		}



		@Test (expected=Exception.class)
		public void test() {
			assertEquals("",ship.getSenseRegister(Register.sense_celltype),CellType.Undefined);
		
			ship.act();//schaut nach 1,0. Hat: water, enemyship, eigene boje 5, (3)
			assertEquals("",ship.getSenseRegister(Register.sense_celltype),CellType.Empty);
			assertEquals("",ship.getSenseRegister(Register.sense_marker5),BoolWert.True);
			assertEquals("",ship.getSenseRegister(Register.sense_shiptype),ShipType.Enemy);
			assertEquals("",ship.getSenseRegister(Register.sense_marker1),BoolWert.Undefined);
			assertEquals("",ship.getSenseRegister(Register.sense_marker2),BoolWert.Undefined);
			assertEquals("",ship.getSenseRegister(Register.sense_marker3),BoolWert.Undefined);
			assertEquals("",ship.getSenseRegister(Register.sense_marker4),BoolWert.Undefined);
			assertEquals("",ship.getSenseRegister(Register.sense_enemymarker),BoolWert.Undefined);
		
			ship.act();//schaut nach 0,1. Hat: island, supply. (2)
			assertEquals("",ship.getSenseRegister(Register.sense_celltype),CellType.Island);
			assertEquals("",ship.getSenseRegister(Register.sense_supply), BoolWert.True);
			assertEquals("",ship.getSenseRegister(Register.sense_shiptype),ShipType.Undefined);
		
			ship.act();//schaut nach 3,1. Hat: island. (1)
			assertEquals("",ship.getSenseRegister(Register.sense_celltype),CellType.Island);
			assertEquals("",ship.getSenseRegister(Register.sense_treasure),BoolWert.False); 
			assertEquals("",ship.getSenseRegister(Register.sense_marker5),BoolWert.False);
			assertEquals("",ship.getSenseRegister(Register.sense_supply), BoolWert.False);


			ship.act();//schaut nach 3,0. Hat:  EnemyBase (1)
			assertEquals("",ship.getSenseRegister(Register.sense_celltype),CellType.EnemyHome);
	
			ship.act();//schaut nach 3,3. Hat: water, verbuendetes Schiff (2)
			assertEquals("",ship.getSenseRegister(Register.sense_celltype),CellType.Empty);
			assertEquals("",ship.getSenseRegister(Register.sense_shiptype),ShipType.Friend);
		
			ship.act();//schaut nach 0,3. Hat: water, treasure, eigene boje1,2,4, enemybouy.(6)
			assertEquals("",ship.getSenseRegister(Register.sense_celltype),CellType.Empty);
			assertEquals("",ship.getSenseRegister(Register.sense_shiptype),ShipType.Undefined);
			assertEquals("",ship.getSenseRegister(Register.sense_marker1),BoolWert.True);
			assertEquals("",ship.getSenseRegister(Register.sense_marker2),BoolWert.True);
			assertEquals("",ship.getSenseRegister(Register.sense_marker4),BoolWert.True);
			assertEquals("",ship.getSenseRegister(Register.sense_enemymarker),BoolWert.True);
			assertEquals("",ship.getSenseRegister(Register.sense_treasure),BoolWert.True);
		
			ship.act();//schaut auf 0,0. Hat: water, HomeBase.(1)
			assertEquals("",ship.getSenseRegister(Register.sense_celltype),CellType.Home);
			assertEquals("",ship.getSenseRegister(Register.sense_marker1),BoolWert.False);
			assertEquals("",ship.getSenseRegister(Register.sense_marker2),BoolWert.False);
			assertEquals("",ship.getSenseRegister(Register.sense_marker4),BoolWert.False);
			assertEquals("",ship.getSenseRegister(Register.sense_enemymarker),BoolWert.False);
			assertEquals("",ship.getSenseRegister(Register.sense_treasure),BoolWert.False);	
		}
		@Test (expected=Exception.class)
		public void testaftercombat(){
			ship.act();//Kaempft gegen enemyship
			ship.act();//springt auf sensecommand 7
			ship.act();//schaut auf 1,0
			assertEquals("",ship.getSenseRegister(Register.sense_treasure),BoolWert.True);
			assertEquals("",ship.getSenseRegister(Register.sense_celltype),CellType.Empty);
			assertEquals("",ship.getSenseRegister(Register.sense_marker5),BoolWert.True);
			assertEquals("",ship.getSenseRegister(Register.sense_shiptype),ShipType.Undefined);
			assertEquals("",ship.getSenseRegister(Register.sense_marker1),BoolWert.False);
			assertEquals("",ship.getSenseRegister(Register.sense_marker2),BoolWert.False);
			assertEquals("",ship.getSenseRegister(Register.sense_marker3),BoolWert.False);
			assertEquals("",ship.getSenseRegister(Register.sense_marker4),BoolWert.False);
		}

}
