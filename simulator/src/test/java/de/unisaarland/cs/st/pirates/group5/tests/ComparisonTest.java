package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;
import model.CellType;
import model.Register;
import model.ShipType;

import org.junit.Test;

import controller.BoolComparison;
import controller.CellTypeComparison;
import controller.IntComparison;
import controller.Operator;
import controller.ShipTypeComparison;

public class ComparisonTest {

	private IntComparison intCo;
	private ShipTypeComparison shiCo;
	private BoolComparison boCo;
	private CellTypeComparison ceCo;
	
	@Test
	public void WrongRegisterTest(){
		
		String message = "Didn't throw an error because of wrong registers in constructor";
		
		try{
			intCo = new IntComparison(Operator.Equal, Register.ship_condition, Register.sense_marker0);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
		
		try{
			intCo = new IntComparison(Operator.Equal, Register.sense_celltype, Register.ship_condition);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
		
		try{
			shiCo = new ShipTypeComparison(Operator.Equal, Register.sense_celltype, ShipType.Enemy);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
		
		try{
			boCo = new BoolComparison(Register.ship_moral, true);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
		
		try{
			ceCo = new CellTypeComparison(Operator.Equal, Register.sense_enemymarker, CellType.EnemyHome);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
	}
	
	@Test
	public void NullTest(){
		
		String message = "Didn't recognize empty parameters";
		
		try{
			intCo = new IntComparison(null, Register.ship_condition, 2);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
		
		try{
			intCo = new IntComparison(Operator.Equal, null, 2);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
		
		try{
			intCo = new IntComparison(Operator.Equal, 2, null);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
		
		try{
			intCo = new IntComparison(Operator.Equal, null, null);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
		
		try{
			shiCo = new ShipTypeComparison(Operator.Equal, Register.sense_shiptype, null);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
		
		try{
			shiCo = new ShipTypeComparison(Operator.Equal, null, ShipType.Friend);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
		
		try{
			shiCo = new ShipTypeComparison(null, Register.sense_shiptype, null);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
		
		try{
			boCo = new BoolComparison(null, true);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
		
		try{
			ceCo = new CellTypeComparison(null, Register.sense_celltype, CellType.EnemyHome);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
		
		try{
			ceCo = new CellTypeComparison(Operator.Equal, null, CellType.EnemyHome);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
		
		try{
			ceCo = new CellTypeComparison(Operator.Equal, Register.sense_celltype, null);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
		
		try{
			intCo = new IntComparison(Operator.Equal, 3, Register.ship_load);
			intCo.eval(null);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}	
		
		try{
			shiCo = new ShipTypeComparison(Operator.Equal, Register.sense_shiptype, ShipType.Enemy);
			shiCo.eval(null);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}	
		
		try{
			boCo = new BoolComparison(Register.sense_marker1, true);
			boCo.eval(null);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
		
		
		try{
			ceCo = new CellTypeComparison(Operator.Equal, Register.sense_celltype, CellType.Empty);
			ceCo.eval(null);
			fail(message);
		}
		catch(Exception e){
			assertTrue(true);
		}
	}
	
	@Test
	public void equalityTest(){
		
		
	}
	
}
