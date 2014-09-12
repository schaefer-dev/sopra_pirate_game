package view;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import de.unisaarland.cs.st.pirates.logger.LogWriter;

public class Log implements LogWriter {

	private List<LogWriter> loggers = new LinkedList<LogWriter>();
	
	public void addLogger(LogWriter l){
		loggers.add(l);
	}
	
	public void deleteLogger(LogWriter l){
		loggers.remove(l);
	}
	
	@Override
	public LogWriter addCell(Cell arg0, Integer arg1, int arg2, int arg3) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException, IllegalStateException {
		for(LogWriter log: loggers)
			log.addCell(arg0, arg1, arg2, arg3);
		
		return this;
	}

	@Override
	public LogWriter addCustomHeaderData(String arg0) throws NullPointerException, ArrayIndexOutOfBoundsException {
		for(LogWriter log: loggers)
			log.addCustomHeaderData(arg0);
		
		return this;
	}

	@Override
	public Transaction beginTransaction(Entity arg0, int arg1) throws NullPointerException, IllegalArgumentException, IllegalStateException {		
		throw new NullPointerException("Don't call this method");
	}

	@Override
	public void close() throws IllegalStateException, IOException {
		for(LogWriter log: loggers)
			log.close();
	}

	@Override
	public LogWriter commitTransaction(Transaction arg0) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		throw new NullPointerException("Don't call this method");
	}

	@Override
	public LogWriter create(Entity arg0, int arg1, Key[] arg2, int[] arg3) throws NullPointerException, IllegalArgumentException, ArrayIndexOutOfBoundsException, IllegalStateException {
		for(LogWriter log: loggers)
			log.create(arg0, arg1, arg2, arg3);
		
		return this;
	}

	@Override
	public LogWriter destroy(Entity arg0, int arg1)throws NullPointerException, IllegalArgumentException, IllegalStateException {
		for(LogWriter log: loggers)
			log.destroy(arg0, arg1);
		
		return this;
	}

	@Override
	public LogWriter fleetScore(int arg0, int arg1) throws IllegalArgumentException, IllegalStateException {
		for(LogWriter log: loggers)
			log.fleetScore(arg0, arg1);
		
		return this;
	}

	@Override
	public void init(OutputStream arg0, String arg1, String... arg2) throws NullPointerException, IOException, ArrayIndexOutOfBoundsException {
		for(LogWriter log: loggers)
			log.init(arg0, arg1, arg2);
	}

	@Override
	public void logStep() throws IllegalStateException, IOException {
		for(LogWriter log: loggers)
			log.logStep();
	}

	@Override
	public LogWriter notify(Entity arg0, int arg1, Key arg2, int arg3) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		for(LogWriter log: loggers)
			log.notify(arg0, arg1, arg2, arg3);
		
		return this;
	}
}
