package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;
import model.CellType;
import model.Register;
import model.Ship;
import org.junit.Before;
import org.junit.Test;
import controller.CellTypeComparison;
import controller.Operator;

public class CellTypeComparisonTest {

	private CellTypeComparison compare;
	private Ship ship;
	
	@Before
	public void setUp(){
		ship = new Ship(null, null, 0, null);
	}
	
	@Test
	public void CellTypeComparisonTest0r(){
		
		compare = new CellTypeComparison(Operator.Equal, Register.sense_celltype, CellType.Empty);
		ship.setSenseRegister(Register.sense_celltype, CellType.Empty.ordinal());
		assertTrue(compare.eval(ship));
		
		compare = new CellTypeComparison(Operator.Equal, Register.sense_celltype, CellType.EnemyHome);
		ship.setSenseRegister(Register.sense_celltype, CellType.EnemyHome.ordinal());
		assertTrue(compare.eval(ship));
		
		compare = new CellTypeComparison(Operator.Equal, Register.sense_celltype, CellType.Island);
		ship.setSenseRegister(Register.sense_celltype, CellType.Island.ordinal());
		assertTrue(compare.eval(ship));
		
		compare = new CellTypeComparison(Operator.Unequal, Register.sense_celltype, CellType.Home);
		ship.setSenseRegister(Register.sense_celltype, CellType.Home.ordinal());
		assertFalse(compare.eval(ship));
		
		compare = new CellTypeComparison(Operator.Unequal, Register.sense_celltype, CellType.Undefined);
		ship.setSenseRegister(Register.sense_celltype, CellType.Undefined.ordinal());
		assertFalse(compare.eval(ship));
		
		compare = new CellTypeComparison(Operator.Unequal, Register.sense_celltype, CellType.EnemyHome);
		ship.setSenseRegister(Register.sense_celltype, CellType.Home.ordinal());
		assertTrue(compare.eval(ship));
		
		compare = new CellTypeComparison(Operator.Unequal, Register.sense_celltype, CellType.Island);
		ship.setSenseRegister(Register.sense_celltype, CellType.Empty.ordinal());
		assertTrue(compare.eval(ship));
		
		compare = new CellTypeComparison(Operator.Equal, Register.sense_celltype, CellType.Undefined);
		ship.setSenseRegister(Register.sense_celltype, CellType.EnemyHome.ordinal());
		assertFalse(compare.eval(ship));
		
		compare = new CellTypeComparison(Operator.Equal, Register.sense_celltype, CellType.Empty);
		ship.setSenseRegister(Register.sense_celltype, CellType.Undefined.ordinal());
		assertFalse(compare.eval(ship));
	}
	
	@Test
	public void WrongOperatorTest(){
		
		String message = "Operator undefined for CellTypeComparison";
		
		try{
			compare = new CellTypeComparison(Operator.Less, Register.sense_celltype, CellType.Empty);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
		
		try{
			compare = new CellTypeComparison(Operator.Greater, Register.sense_celltype, CellType.Empty);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
	}
	
}
