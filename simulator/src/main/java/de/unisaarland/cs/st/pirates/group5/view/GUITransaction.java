package de.unisaarland.cs.st.pirates.group5.view;

import de.unisaarland.cs.st.pirates.logger.LogWriter;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Entity;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Key;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Transaction;

public class GUITransaction implements Transaction {

	private LogWriter log;
	private Entity entity;
	private int id;
	
	private Integer x;
	private Integer y;
	
	public GUITransaction(LogWriter log, Entity entity, int id){
		this.log = log;
		this.entity = entity;
		this.id = id;
	}
	
	@Override
	public Transaction set(Key arg0, int arg1) throws NullPointerException, IllegalArgumentException {
		switch(arg0){
			case X_COORD:
				x = arg1;
				break;
			case Y_COORD:
				y = arg1;
				break;
			default:
				log.notify(entity, id, arg0, arg1);
				break;
		}
		return this;
	}
	
	public Integer getX(){
		return x;
	}
	
	public Integer getY(){
		return y;
	}
	
	public Entity getEntity(){
		return entity;
	}
	
	public int getID(){
		return id;
	}
}
