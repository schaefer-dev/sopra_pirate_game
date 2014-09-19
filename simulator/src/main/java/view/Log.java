package view;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import de.unisaarland.cs.st.pirates.logger.LogWriter;

/**
 * This class implements the LogWriter interface and additionally serves as a compound for all other used objects that implement
 * this interface as well. Calling the methods of this class will result in calls of the same methods of the subordinate
 * LogWriters. 
 * 
 * The class itself has no logging functionality, this is completely done by its subordinates
 * 
 * @author Rafael Theis
 * @see de.uni-saarland.cs.st.pirates.logger
 */
public class Log implements LogWriter {

	private List<LogWriter> loggers = new LinkedList<LogWriter>();
	private boolean init = false;
	
	/**
	 * Adds a LogWriter object to the compound whose functionality
	 * will then be completely wrapped by this class.
	 * 
	 * New LogWriters can only be added before init(..) is called
	 * 
	 * @param logWriter The to be added LogWriter
	 * @throws NullPointerException when the logWriter is null
	 * @throws IllegalStateException when init(..) has been called already
	 */
	public void addLogger(LogWriter logWriter){
		if(logWriter == null) throw new NullPointerException();
		if(init) throw new IllegalStateException();
		loggers.add(logWriter);
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
		List<Transaction> transactions = new LinkedList<Transaction>();
		for(LogWriter log: loggers)
			transactions.add(log.beginTransaction(arg0, arg1));	
		
		return new CompTransaction(transactions);
	}

	@Override
	public void close() throws IllegalStateException, IOException {
		for(LogWriter log: loggers)
			log.close();
	}

	@Override
	public LogWriter commitTransaction(Transaction arg0) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		CompTransaction trans = (CompTransaction) arg0;
		for(int i = 0; i < loggers.size(); i++)
			loggers.get(i).commitTransaction(trans.getTranscation(i));
	
		return this;
	}

	@Override
	public LogWriter create(Entity arg0, int arg1, Key[] arg2, int[] arg3) throws NullPointerException, IllegalArgumentException, ArrayIndexOutOfBoundsException, IllegalStateException {
		for(LogWriter log: loggers)
			log.create(arg0, arg1, arg2, arg3);
		
		return this;
	}

	@Override
	public LogWriter destroy(Entity arg0, int arg1) throws NullPointerException, IllegalArgumentException, IllegalStateException {
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
		if(loggers.size() == 0) throw new IllegalThreadStateException("No loggers added");
		
		init = true;
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
