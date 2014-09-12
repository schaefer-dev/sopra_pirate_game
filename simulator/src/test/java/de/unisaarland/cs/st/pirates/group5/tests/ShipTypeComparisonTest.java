package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;
import model.Register;
import model.Ship;
import model.ShipType;

import org.junit.Before;
import org.junit.Test;

import controller.Operator;
import controller.ShipTypeComparison;

public class ShipTypeComparisonTest {

	private ShipTypeComparison compare;
	private Ship ship;
	
	@Before
	public void setUp(){
		ship = new Ship(null, null, 0, null);
	}
	
	@Test
	public void ShipTypeTest0r(){
		
		compare = new ShipTypeComparison(Operator.Equal, Register.sense_shiptype, ShipType.Enemy);
		ship.setSenseRegister(Register.sense_shiptype, ShipType.Friend.ordinal());
		assertFalse(compare.eval(ship));
		
		compare = new ShipTypeComparison(Operator.Equal, Register.sense_shiptype, ShipType.Undefined);
		ship.setSenseRegister(Register.sense_shiptype, ShipType.Friend.ordinal());
		assertFalse(compare.eval(ship));
		
		compare = new ShipTypeComparison(Operator.Equal, Register.sense_shiptype, ShipType.Undefined);
		ship.setSenseRegister(Register.sense_shiptype, ShipType.Enemy.ordinal());
		assertFalse(compare.eval(ship));
		
		compare = new ShipTypeComparison(Operator.Equal, Register.sense_shiptype, ShipType.Undefined);
		ship.setSenseRegister(Register.sense_shiptype, ShipType.Undefined.ordinal());
		assertTrue(compare.eval(ship));
		
		compare = new ShipTypeComparison(Operator.Equal, Register.sense_shiptype, ShipType.Friend);
		ship.setSenseRegister(Register.sense_shiptype, ShipType.Friend.ordinal());
		assertTrue(compare.eval(ship));
		
		compare = new ShipTypeComparison(Operator.Equal, Register.sense_shiptype, ShipType.Enemy);
		ship.setSenseRegister(Register.sense_shiptype, ShipType.Enemy.ordinal());
		assertTrue(compare.eval(ship));
		
		compare = new ShipTypeComparison(Operator.Unequal, Register.sense_shiptype, ShipType.Enemy);
		ship.setSenseRegister(Register.sense_shiptype, ShipType.Enemy.ordinal());
		assertFalse(compare.eval(ship));
		
		compare = new ShipTypeComparison(Operator.Unequal, Register.sense_shiptype, ShipType.Undefined);
		ship.setSenseRegister(Register.sense_shiptype, ShipType.Enemy.ordinal());
		assertTrue(compare.eval(ship));
	}
	
	@Test
	public void WrongOperatorTest(){
		
		String message = "Operator undefined for ShipTypeComparison";
		
		try{
			compare = new ShipTypeComparison(Operator.Less, Register.sense_shiptype, ShipType.Enemy);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
		
		try{
			compare = new ShipTypeComparison(Operator.Greater, Register.sense_shiptype, ShipType.Enemy);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
	}
}
