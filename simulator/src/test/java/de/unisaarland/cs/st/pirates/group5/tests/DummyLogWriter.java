package de.unisaarland.cs.st.pirates.group5.tests;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import de.unisaarland.cs.st.pirates.logger.LogWriter;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Cell;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Entity;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Key;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Transaction;

public class DummyLogWriter implements LogWriter {

	public List<Entity> entities = new LinkedList<Entity>();
	public List<Integer> values = new LinkedList<Integer>();
	public List<String> what = new LinkedList<String>();
	public List<Cell> cells = new LinkedList<Cell>();

	@Override
	public LogWriter addCell(Cell arg0, Integer arg1, int arg2, int arg3)
			throws NullPointerException, ArrayIndexOutOfBoundsException,
			IllegalArgumentException, IllegalStateException {
		what.add("addCell");
		cells.add(arg0);
		values.add(arg1);
		values.add(arg2);
		values.add(arg3);
		return this;
	}

	@Override
	public LogWriter addCustomHeaderData(String arg0)
			throws NullPointerException, ArrayIndexOutOfBoundsException {
		what.add("addCustomHeaderData: "+ arg0);
		return this;
	}

	@Override
	public Transaction beginTransaction(Entity arg0, int arg1)
			throws NullPointerException, IllegalArgumentException,
			IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setEntities(List<Entity> els) {
		this.entities = els;
	}

	public void setValues(List<Integer> values) {
		this.values = values;
	}

	public void setWhat(List<String> what) {
		this.what = what;
	}

	@Override
	public void close() throws IllegalStateException, IOException {
		this.what.add("close");
		
	}

	@Override
	public LogWriter commitTransaction(Transaction arg0)
			throws NullPointerException, IllegalArgumentException,
			IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LogWriter create(Entity arg0, int arg1, Key[] arg2, int[] arg3)
			throws NullPointerException, IllegalArgumentException,
			ArrayIndexOutOfBoundsException, IllegalStateException {
		System.out.println(arg1);
		this.what.add("create");
		this.entities.add(arg0);
		this.values.add(arg1);
		for(Key k : arg2)
		{
			values.add(k.ordinal());
		}
		System.out.println(arg3.toString());
		for(Integer i: arg3)
		{
			values.add(i);
			System.out.println(i);
		}
		return this;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public List<Integer> getValues() {
		return values;
	}

	public List<String> getWhat() {
		return what;
	}

	@Override
	public LogWriter destroy(Entity arg0, int arg1){
			what.add("destroy");
			entities.add(arg0);
			values.add(arg1);
			return this;
	}

	@Override
	public LogWriter fleetScore(int arg0, int arg1)
			throws IllegalArgumentException, IllegalStateException {
		values.add(arg0);
		values.add(arg1);
		what.add("changeScore");
		return this;
	}

	@Override
	public void init(OutputStream arg0, String arg1, String... arg2)
			throws NullPointerException, IOException,
			ArrayIndexOutOfBoundsException {
		this.what.add("init");
		this.what.add(arg1);
		for(String s: arg2)
		{
			what.add(s);
		}
		
	}

	@Override
	public void logStep() throws IllegalStateException, IOException {
		what.add("logStep");
		
	}

	@Override
	public LogWriter notify(Entity arg0, int arg1, Key arg2, int arg3)
			throws NullPointerException, IllegalArgumentException,
			IllegalStateException {
		entities.add(arg0);
		values.add(arg1);
		values.add(arg2.ordinal());
		values.add(arg3);
		what.add("notify");
		return this;
	}
	
	public void emptyLists()
	{
		what.clear();
		entities.clear();
		cells.clear();
		values.clear();
	}
	
}
