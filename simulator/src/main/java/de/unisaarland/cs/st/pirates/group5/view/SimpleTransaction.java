package de.unisaarland.cs.st.pirates.group5.view;

import java.util.LinkedList;
import java.util.List;

import de.unisaarland.cs.st.pirates.logger.LogWriter.Entity;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Key;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Transaction;

public class SimpleTransaction implements Transaction {

	private String intro;
	private List<List<String>> keys = new LinkedList<List<String>>();
	
	private SimpleLogWriter writer;
	private Entity entity;
	
	
	public SimpleTransaction(String intro, Entity entity, SimpleLogWriter writer) {
		this.intro  = intro;
		this.entity = entity;
		this.writer = writer;
	}
	
	@Override
	public Transaction set(Key arg0, int arg1) throws NullPointerException, IllegalArgumentException {
		if(arg0 == null) throw new NullPointerException();
		if(!writer.validKey(entity, arg0)) throw new IllegalArgumentException();
		
		Integer value = arg1;
		List<String> key = new LinkedList<String>();
		key.add("");
		key.add(arg0.toString() + ":");
		key.add(value.toString());
		keys.add(key);
		
		return this;
	}
	
	public String getIntro(){
		return intro;
	}
	
	public List<List<String>> getTransactions(){
		return keys;
	}
}
