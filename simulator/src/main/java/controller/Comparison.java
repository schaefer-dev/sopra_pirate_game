package controller;

import model.Ship;
/**
 * A <code>Comparison</code> can be used in any of the Classes <code>If</code>, <code>IfAll</code> and <code>IfAny</code>.
 * It uses registers of a ship, which are either compared to another value using an operator, or are depending on the operator
 * evaluated by themselves, if the register is a BoolRegister.
 * @author Jan
 *
 */
public interface Comparison {
/**
 * Evaluates a comparison
 * @param ship The ship which owns the compared Register(s).
 * @return a boolean representing the evaluation. Always returns false if a Registers value is undefined.
 */
	boolean eval(Ship ship);
}


