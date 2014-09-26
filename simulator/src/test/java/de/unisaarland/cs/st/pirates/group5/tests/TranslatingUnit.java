package de.unisaarland.cs.st.pirates.group5.tests;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.junit.Test;

import controller.Translator;

public class TranslatingUnit {
	Translator translator = new Translator();

	@Test
	public void test() {
		translator.setLabelized(false);
		InputStream in = getClass().getResourceAsStream("/dereferenzierteTaktik.log");
		translator.run(in);
	}

}
