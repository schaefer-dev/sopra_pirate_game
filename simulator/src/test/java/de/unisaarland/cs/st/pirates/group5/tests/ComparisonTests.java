package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;
import model.Register;
import model.Ship;

import org.junit.Test;

import controller.IntComparison;
import controller.Operator;

public class ComparisonTests {

	@Test
	public void IntComparisonEqualityTest(){
		
		IntComparison compare;
		Ship ship = new Ship(null, null, 0, null);
		
		
		compare = new IntComparison(Operator.Equal, Register.ship_direction, 2);
		//ship.changeDirection(2);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_direction, 5);
		//ship.changeDirection(5);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_direction, 0);
		//ship.changeDirection(0);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_direction, 4);
		//ship.changeDirection(1);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_direction, 144);
		//ship.changeDirection(4);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_moral, 3);
		ship.changeMoral(3);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_moral, 1);
		ship.changeMoral(1);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_moral, 0);
		ship.changeMoral(0);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_moral, 5);
		ship.changeMoral(2);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_moral, -3);
		ship.changeMoral(3);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_moral, 666);
		ship.changeMoral(4);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_load, 1);
		ship.setLoad(1);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_moral, 0);
		//ship.changeDirection(0);
		assertTrue(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_moral, 5);
		//ship.changeDirection(2);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_moral, -3);
		//ship.changeDirection(3);
		assertFalse(compare.eval(ship));
		
		compare = new IntComparison(Operator.Equal, Register.ship_moral, 666);
		//ship.changeDirection(4);
		assertFalse(compare.eval(ship));
		
	}
}
