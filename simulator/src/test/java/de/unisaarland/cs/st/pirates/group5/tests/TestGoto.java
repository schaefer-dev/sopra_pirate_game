package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import model.Ship;
import model.Team;

import org.junit.Before;
import org.junit.Test;

import commands.Goto;
import controller.Command;

public class TestGoto {
	int pc = 5;
	Goto go;
	List<Command> goList = new ArrayList<Command>();
	Team test;
	Ship ship;
	
	@Before
	
	public void setUp(){
	go = new Goto(pc);
	goList.add(go);
	test = new Team('a', goList);
	ship = new Ship(test,null,1,null);
	}

	@Test
	public void test() {
		assertEquals("blabla",ship.getPC(),pc);
	}

}
