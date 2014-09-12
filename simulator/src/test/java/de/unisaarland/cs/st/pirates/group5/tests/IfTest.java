package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import model.Register;
import model.Ship;

import org.junit.Before;
import org.junit.Test;

import commands.If;
import commands.IfAll;
import commands.IfAny;
import controller.BoolComparison;
import controller.Comparison;
import controller.IntComparison;
import controller.Operator;

public class IfTest {

	private If ifCom;
	private IfAll ifAll;
	private IfAny ifAny;
	private Ship ship;
	
	private IntComparison intComp = new IntComparison(Operator.Equal, 4, Register.ship_moral);
	private BoolComparison boComp = new BoolComparison(Register.sense_enemymarker, true);
	private BoolComparison buComp = new BoolComparison(Register.sense_enemymarker, false);
	
	@Before
	public void setUp(){
		ship = new Ship(null, null, 0, null);	
	}
	
	@Test
	public void SimpleIfTest(){
		int i = ship.getPC();
		ifCom = new If(intComp, 5);
		int j = ship.getPC();
		assertTrue(j == i + 1);
	}
	
	@Test
	public void SimpleIfTest2(){
		ifCom = new If(intComp, 5);
		ship.changeMoral(-2);
		int j = ship.getPC();
		assertTrue(j == 5);
		
	}
	
	@Test
	public void SimpleIfAllTest(){
		List<Comparison> comps = new LinkedList<Comparison>();
		comps.add(boComp);
		comps.add(boComp);
		comps.add(boComp);
		comps.add(boComp);
		int i = ship.getPause();
		ifAll = new IfAll(comps, 10);
		int j = ship.getPC();
		assertTrue(j == i + 1);
	}
	
	@Test
	public void SimpleIfAllTest2(){
		List<Comparison> comps = new LinkedList<Comparison>();
		comps.add(boComp);
		comps.add(boComp);
		comps.add(buComp);
		comps.add(boComp);
		ifAll = new IfAll(comps, 10);
		int j = ship.getPC();
		assertTrue(j == 10);
	}
	
	@Test
	public void SimpleIfAnyTest(){
		List<Comparison> comps = new LinkedList<Comparison>();
		comps.add(boComp);
		comps.add(boComp);
		comps.add(boComp);
		comps.add(boComp);
		int i = ship.getPause();
		ifAny = new IfAny(comps, 15);
		int j = ship.getPC();
		assertTrue(j == i + 1);
	}
	
	@Test
	public void SimpleIfAnyTest2(){
		List<Comparison> comps = new LinkedList<Comparison>();
		comps.add(boComp);
		comps.add(buComp);
		comps.add(buComp);
		comps.add(buComp);
		int i = ship.getPause();
		ifAny = new IfAny(comps, 15);
		int j = ship.getPC();
		assertTrue(j == i + 1);
	}
	
	@Test
	public void SimpleIfAnyTest3(){
		List<Comparison> comps = new LinkedList<Comparison>();
		comps.add(buComp);
		comps.add(buComp);
		comps.add(buComp);
		comps.add(buComp);
		int i = ship.getPause();
		ifAny = new IfAny(comps, 15);
		int j = ship.getPC();
		assertTrue(j == 15);
	}
	
	@Test
	public void NullTest(){
		
		String message = "Empty parameters aren't handled";
		
		try{
			ifCom = new If(null, 5);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
		
		try{
			ifAll = new IfAll(null, 5);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
		
		try{
			ifAny = new IfAny(null, 5);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}	
	}
	
	@Test
	public void InvalidPCTest(){
		
		String message = "Invalid PC isn't handled";
		
		try{
			ifCom = new If(null, -1);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
		
		try{
			ifAll = new IfAll(null, -1);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
		
		try{
			ifAny = new IfAny(null, -1);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}	
	}
}
