package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.junit.Test;

import controller.Translator;

public class TranslatingUnit {
	Translator translator = new Translator();

	@Test
	public void test() {
		translator.setLabelized(true);
		InputStream in = getClass().getResourceAsStream("/shortRange.ship");
		translator.run(in);
	}

}
