package view;

import java.io.IOException;
import java.io.OutputStream;

import de.unisaarland.cs.st.pirates.logger.LogWriter;

public class GUIController implements LogWriter {	
	
	@Override
	public void init(OutputStream arg0, String arg1, String... arg2) throws NullPointerException, IOException, ArrayIndexOutOfBoundsException {
		
	}
	
	@Override
	public void close() throws IllegalStateException, IOException {

	}
	
	
	@Override
	public LogWriter create(Entity arg0, int arg1, Key[] arg2, int[] arg3) throws NullPointerException, IllegalArgumentException, ArrayIndexOutOfBoundsException, IllegalStateException {
		
		return this;
	}
	
	@Override
	public LogWriter destroy(Entity arg0, int arg1) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public LogWriter addCustomHeaderData(String arg0) throws NullPointerException, ArrayIndexOutOfBoundsException {
		
		return this;
	}
	
	@Override
	public LogWriter notify(Entity arg0, int arg1, Key arg2, int arg3) throws NullPointerException, IllegalArgumentException, IllegalStateException {

		return this;
	}
	
	@Override
	public void logStep() throws IllegalStateException, IOException {
	}
	
	@Override
	public LogWriter fleetScore(int arg0, int arg1) throws IllegalArgumentException, IllegalStateException {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public LogWriter addCell(Cell arg0, Integer arg1, int arg2, int arg3) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException, IllegalStateException {
		// TODO Auto-generated method stub
		return this;
	}



	@Override
	public Transaction beginTransaction(Entity arg0, int arg1) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LogWriter commitTransaction(Transaction arg0) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}
}
