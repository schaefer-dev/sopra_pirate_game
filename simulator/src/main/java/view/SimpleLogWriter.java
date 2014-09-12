package view;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;

import de.unisaarland.cs.st.pirates.logger.LogWriter;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Key;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Transaction;

public class SimpleLogWriter implements LogWriter {
	
	public enum LogType{
		Info,
		Warning,
		Error,
		Debug,
		Init,
		None
	}
	
	private PrintWriter out;
	private Integer round;
	private Date now;
	private boolean init;
	private int teamCount;
	private static String transcation = "";
	
	@Override
	public void init(OutputStream arg0, String arg1, String... arg2) throws NullPointerException, IOException, ArrayIndexOutOfBoundsException {
		if(arg0 == null || arg1 == null || arg2 == null) throw new NullPointerException();
		if(arg2.length == 0) throw new ArrayIndexOutOfBoundsException();
		
		init = true;
		round = 0;
		out = new PrintWriter(arg0);
		teamCount = arg2.length;
		
		int i = arg1.indexOf(' ');		
		String first = arg1.substring(0, i);
		String rest = arg1.substring(i);
		rest = rest.substring(1);
		i = rest.indexOf(' ');
		String second = rest.substring(0, i);
		
		String teams = "Teams on map {";
		for(int j = 0; j < arg2.length; j++)
			teams += teamName(j) + ",";
		teams = teams.substring(0, arg2.length-1);
		teams += "}";
		
		try{
			addCustomHeaderData("Map has size " + first + "x" + second);
			addCustomHeaderData(teams);
		}
		catch(Exception e){
			throw new IOException();
		}
	}

	@Override
	public void close() throws IllegalStateException, IOException {
		out.flush();
		out.close();
	}
	
	private void write(LogType type, String message){
		now = new Date();
		Long time = now.getTime();
		String intro = (round > 0) ? " Round "+round.toString()+": " : "";
		
		if(type.equals(LogType.None)){
			out.write("[" + time.toString() + "]"
					  + intro
				      + message);
		}
		else{
			out.write("[" + time.toString() + "]"
					  + intro
				      + message 
				      + "("+ type.toString() +")");
		}
		out.write("\n");
	}

	@Override
	public LogWriter addCustomHeaderData(String arg0) throws NullPointerException, ArrayIndexOutOfBoundsException {
		if(arg0 == null) throw new NullPointerException();
		if(arg0.length() > 1000000) throw new ArrayIndexOutOfBoundsException();
		if(!(init && round == 0)) throw new IllegalStateException();
		
		
		write(LogType.Init, "HEADER: " + arg0);
		return this;
	}
	
	@Override
	public LogWriter addCell(Cell arg0, Integer arg1, int arg2, int arg3) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException, IllegalStateException {
		if(arg0 == null) throw new NullPointerException();
		if(!(init && round == 0)) throw new IllegalStateException();
		if(teamCount > 1 && (arg1 > teamCount)) throw new ArrayIndexOutOfBoundsException();
		if(arg2 < 0 || arg3 < 0) throw new IllegalArgumentException();
		
		if(arg1 == null)
			write(LogType.Init, "Cell: " + arg0.toString() + " at (" + arg2 + "," + arg3 + ")");
		else
			write(LogType.Init, "Cell: " + arg0.toString() + "(" + teamName(arg1) + ")" + " at (" + arg2 + "," + arg3 + ")");
		
		return this;
	}

	@Override
	public void logStep() throws IllegalStateException, IOException {
		if(!init) throw new IllegalStateException("Logger hasn't been initialised");
		
		round++;
		write(LogType.Info, "====================");
	}
	@Override
	public LogWriter create(Entity arg0, int arg1, Key[] arg2, int[] arg3) throws NullPointerException, IllegalArgumentException, ArrayIndexOutOfBoundsException, IllegalStateException {
		if(arg0 == null || arg2 == null || arg3 == null) throw new NullPointerException();
		if(arg1 < 0) throw new IllegalArgumentException();
		if(!init) throw new IllegalStateException();
		if(arg2.length != arg3.length) throw new ArrayIndexOutOfBoundsException();
		//TODO: missing exception
		
		Integer id = arg1;
		write(LogType.None, "Create: " + arg0.toString() + "(" + id.toString() + "):");
		for(int i = 0; i < arg2.length; i++){
			Integer arg = arg3[i];
			write(LogType.Init, "Create: " + arg2[i].toString() + ": " + arg.toString());
		}
		
		return this;
	}

	@Override
	public LogWriter destroy(Entity arg0, int arg1) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		if(arg0 == null) throw new IllegalArgumentException();
		if(arg1 < 0) throw new IllegalArgumentException();
		if(round == 0) throw new IllegalStateException();

		Integer id = arg1;
		write(LogType.None, "Destroy: " + arg0.toString() + "(" + id.toString() + ")");
		return this;
	}

	@Override
	public LogWriter fleetScore(int arg0, int arg1) throws IllegalArgumentException, IllegalStateException {
		if(arg0 < 0 || arg1 < 0) throw new IllegalArgumentException();
		if(teamCount != 1 && arg0 > teamCount) throw new ArrayIndexOutOfBoundsException();
		if(!init) throw new IllegalStateException();
		
		Integer score = arg1;
		write(LogType.Info, "Score of team " + teamName(arg1) + " is:" + score.toString());
		return this;
	}

	@Override
	public LogWriter notify(Entity arg0, int arg1, Key arg2, int arg3) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		if(arg0 == null | arg2 == null) throw new NullPointerException();
		if(arg1 < 0) throw new IllegalArgumentException();
		if(round == 0) throw new IllegalStateException();
		//TODO: missing exception
		
		Integer id = arg1;
		Integer value = arg3;
		write(LogType.Info, arg0.toString() + "(" + id.toString() + ") " + arg2.toString() + " changed to " + value.toString());
		return this;
	}
	
	@Override
	public Transaction beginTransaction(Entity arg0, int arg1) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		
		
		return new Transaction() {
			
			@Override
			public Transaction set(Key arg0, int arg1) throws NullPointerException,
					IllegalArgumentException {
				
				transcation += "asdgsdg";
				return this;
			}
		};
	}

	@Override
	public LogWriter commitTransaction(Transaction arg0) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		write(LogType.Info, transcation);
		transcation = "";
		
		return this;
	}
	
	private String teamName(int i){
		return "" + (i + 'a');
	}
}
