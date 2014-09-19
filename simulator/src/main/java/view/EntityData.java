package view;

import java.util.LinkedList;
import java.util.List;

import de.unisaarland.cs.st.pirates.logger.LogWriter.Entity;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Key;

public class EntityData {

	public Entity entity;
	public List<Key> keys = new LinkedList<Key>();
	public List<Integer> values = new LinkedList<Integer>();
	
	public EntityData(Entity entity, List<Key> keys, List<Integer> values){
		this.entity = entity;
		this.keys = keys;
		this.values = values;
	}
	
}
