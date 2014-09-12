package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;
import model.BoolWert;
import model.Register;
import model.Ship;

import org.junit.Before;
import org.junit.Test;

import controller.BoolComparison;

public class BoolComparisonTest {

	private BoolComparison compare;
	private Ship ship;
	
	@Before
	public void setUp(){
		ship = new Ship(null, null, 0, null);
	}
	
	@Test
	public void BoolComparisonTest(){
		
		compare = new BoolComparison(Register.sense_marker0, false);
		ship.setSenseRegister(Register.sense_marker0, BoolWert.True.ordinal());
		assertTrue(compare.eval(ship));
		
		compare = new BoolComparison(Register.sense_marker1, true);
		ship.setSenseRegister(Register.sense_marker1, BoolWert.False.ordinal());
		assertTrue(compare.eval(ship));
		
		compare = new BoolComparison(Register.sense_marker2, false);
		ship.setSenseRegister(Register.sense_marker2, BoolWert.False.ordinal());
		assertFalse(compare.eval(ship));
		
		compare = new BoolComparison(Register.sense_marker3, false);
		ship.setSenseRegister(Register.sense_marker3, BoolWert.Undefined.ordinal());
		assertFalse(compare.eval(ship));
		
		compare = new BoolComparison(Register.sense_marker4, true);
		ship.setSenseRegister(Register.sense_marker4, BoolWert.Undefined.ordinal());
		assertFalse(compare.eval(ship));
		
		compare = new BoolComparison(Register.sense_marker5, false);
		ship.setSenseRegister(Register.sense_marker5, BoolWert.True.ordinal());
		assertTrue(compare.eval(ship));
		
		compare = new BoolComparison(Register.sense_enemymarker, true);
		ship.setSenseRegister(Register.sense_enemymarker, BoolWert.False.ordinal());
		assertTrue(compare.eval(ship));
		
		compare = new BoolComparison(Register.sense_enemymarker, true);
		ship.setSenseRegister(Register.sense_enemymarker, BoolWert.True.ordinal());
		assertFalse(compare.eval(ship));
		
		compare = new BoolComparison(Register.sense_shiploaded, true);
		ship.setSenseRegister(Register.sense_shiploaded, BoolWert.False.ordinal());
		assertTrue(compare.eval(ship));
		
		compare = new BoolComparison(Register.sense_supply, true);
		ship.setSenseRegister(Register.sense_supply, BoolWert.False.ordinal());
		assertTrue(compare.eval(ship));	
	}
}
