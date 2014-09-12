package view;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import de.unisaarland.cs.st.pirates.logger.LogWriter;

public class SimpleLogWriter implements LogWriter {
	
	public enum LogType{
		HEADER,
		Create,
		Update,
		Error,
		Debug,
	}
	
	private PrintWriter out;
	private Integer round;
	private Date now;
	private Long startTime;
	private int teamCount;
	private String transStart;
	
	private boolean init;
	private boolean headerEnd;
	
	@Override
	public void init(OutputStream arg0, String arg1, String... arg2) throws NullPointerException, IOException, ArrayIndexOutOfBoundsException {
		if(arg0 == null || arg1 == null || arg2 == null) throw new NullPointerException();
		if(arg2.length == 0) throw new ArrayIndexOutOfBoundsException();
		
		init = true;
		round = 0;
		out = new PrintWriter(arg0);
		teamCount = arg2.length;
		now = new Date();
		startTime = now.getTime();
		
		int i = arg1.indexOf(' ');		
		String first = arg1.substring(0, i);
		String rest = arg1.substring(i);
		rest = rest.substring(1);
		i = rest.indexOf(' ');
		String second = rest.substring(0, i);
		
		String teams = "Teams on map {";
		for(int j = 0; j < arg2.length; j++)
			teams += teamName(j) + ",";
		teams = teams.substring(0, teams.length()-1);
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
		if(round == 0) throw new IllegalStateException();
		try{
			now = new Date();
			Long time = now.getTime() - startTime;		
			out.write("\nterminated after " + time.toString() + " seconds");
			out.flush();
			out.close();	
		}
		catch(Exception e){
			throw new IOException();
		}
	}
	
	private void write(LogType type, String message, boolean showTime){
		now = new Date();
		Long t = now.getTime() - startTime;		
		String time = (showTime) ? "  (" + t.toString() + "s)" : "";
		String step = (round > 0) ? round.toString() : "#";
		
		if(!headerEnd && type.equals(LogType.Create)){
			out.write("\n");
			headerEnd = true;
		}
		
		out.write("[" + step + "]"
					  + "[" + type + "] "
				      + message 
				      + time + "\n");
	}

	@Override
	public LogWriter addCustomHeaderData(String arg0) throws NullPointerException, ArrayIndexOutOfBoundsException {
		if(arg0 == null) throw new NullPointerException();
		if(arg0.length() > 1000000) throw new ArrayIndexOutOfBoundsException();
		if(!(init && round == 0)) throw new IllegalStateException();
		
		write(LogType.HEADER, arg0, false);
		return this;
	}
	
	@Override
	public LogWriter addCell(Cell arg0, Integer arg1, int arg2, int arg3) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException, IllegalStateException {
		if(arg0 == null) throw new NullPointerException();
		if(!(init && round == 0)) throw new IllegalStateException();
		if(arg2 < 0 || arg3 < 0) throw new IllegalArgumentException();
		if(teamCount > 1 && arg1 != null){
			if(arg1 > teamCount)
				throw new ArrayIndexOutOfBoundsException();
		}
		
		if(arg1 == null)
			write(LogType.Create, "Cell: " + arg0.toString() + " at (" + arg2 + "," + arg3 + ")", true);
		else
			write(LogType.Create, "Cell: " + "BASE" + "(" + teamName(arg1) + ")" + " at (" + arg2 + "," + arg3 + ")", true);
		
		return this;
	}

	@Override
	public void logStep() throws IllegalStateException, IOException {
		if(!init) throw new IllegalStateException();
		
		round++;
		try{
			out.write("[" + round.toString() + "][nRound] . . . . . . . . . . . . . . . . . . . .\n");
		}
		catch(Exception e){
			throw new IOException();
		}
	}
	
	@Override
	public LogWriter create(Entity arg0, int arg1, Key[] arg2, int[] arg3) throws NullPointerException, IllegalArgumentException, ArrayIndexOutOfBoundsException, IllegalStateException {
		if(arg0 == null || arg2 == null || arg3 == null) throw new NullPointerException();
		if(arg1 < 0) throw new IllegalArgumentException();
		if(!init) throw new IllegalStateException();
		if(arg2.length != arg3.length) throw new ArrayIndexOutOfBoundsException();
		
		
		Integer id = arg1;
		write(LogType.Create, "Obj : " + arg0.toString() + "(" + id.toString() + ")", true);
		for(int i = 0; i < arg2.length; i++){
			if(!validKey(arg0, arg2[i])) throw new IllegalArgumentException();
			
			Integer arg = arg3[i];
			write(LogType.Create, "	  ->" + arg2[i].toString() + ": " + arg.toString(), false);
		}
		
		return this;
	}

	@Override
	public LogWriter destroy(Entity arg0, int arg1) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		if(arg0 == null) throw new IllegalArgumentException();
		if(arg1 < 0) throw new IllegalArgumentException();
		if(round == 0) throw new IllegalStateException();

		Integer id = arg1;
		write(LogType.Update, arg0.toString() + "(" + id.toString() + ") destroyed", true);
		return this;
	}

	@Override
	public LogWriter fleetScore(int arg0, int arg1) throws IllegalArgumentException, IllegalStateException {
		if(arg0 < 0 || arg1 < 0) throw new IllegalArgumentException();
		if(teamCount != 1 && arg0 > teamCount) throw new ArrayIndexOutOfBoundsException();
		if(!init) throw new IllegalStateException();
		
		Integer score = arg1;
		write(LogType.Update, "TEAM(" + teamName(arg0) + ")   has score: " + score.toString(), true);
		return this;
	}

	@Override
	public LogWriter notify(Entity arg0, int arg1, Key arg2, int arg3) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		if(arg0 == null | arg2 == null) throw new NullPointerException();
		if(arg1 < 0) throw new IllegalArgumentException();
		if(round == 0) throw new IllegalStateException();
		if(!validKey(arg0,  arg2)) throw new IllegalArgumentException();
		
		Integer id = arg1;
		Integer value = arg3;
		write(LogType.Update, arg0.toString() + "(" + id.toString() + ") " + arg2.toString() + " changed to " + value.toString(), true);
		return this;
	}
	
	@Override
	public Transaction beginTransaction(Entity arg0, int arg1) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		if(arg0 == null) throw new NullPointerException();
		if(arg1 < 0) throw new IllegalArgumentException();
		if(!init) throw new IllegalStateException();
		
		Integer id = arg1;
		transStart = arg0.toString() + "(" + id.toString() + "):\n";
		
		return new Transaction() {
			
			private String transaction = "";
			
			@Override
			public Transaction set(Key arg0, int arg1) throws NullPointerException, IllegalArgumentException {
				if(arg0 == null) throw new NullPointerException();
				//TODO: missing exception
				
				Integer value = arg1;
				transaction += "                  ->" + arg0.toString() + " changed to " + value.toString();
				transaction += "\n";
				return this;
			}		
				
			@Override
			public String toString(){
				return transaction;
			}
		};
	}

	@Override
	public LogWriter commitTransaction(Transaction arg0) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		if(arg0 == null) throw new NullPointerException();
		if(arg0.toString().length() == 0) throw new IllegalArgumentException();
		if(round == 0) throw new IllegalStateException();
		
		String body = arg0.toString();
		body = body.substring(0, body.length() - 2);
		write(LogType.Update, transStart + body, true);	
		return this;
	}
	
	
	private String teamName(int i){
		char x = ((char) (97 + i));
		return "" + x;
	}
	
	private boolean validKey(Entity e, Key k){
		if(e.equals(Entity.BUOY)){
			switch(k){
				case FLEET:
				case VALUE:
				case X_COORD:
				case Y_COORD:
					return true;
				default:
					return false;
			}	
		}
		else if(e.equals(Entity.TREASURE)){
			switch(k){
				case VALUE:
				case X_COORD:
				case Y_COORD:
					return true;
				default:
					return false;
			}		
		}
		else if(e.equals(Entity.SHIP)){
			switch(k){
				case DIRECTION:
				case CONDITION:
				case FLEET:
				case MORAL:
				case PC:
				case RESTING:
				case VALUE:
				case X_COORD:
				case Y_COORD:
					return true;
				default:
					return false;
			}			
		}
		else if(e.equals(Entity.KRAKEN)){
			switch(k){
				case PC:
				case X_COORD:
				case Y_COORD:
					return true;
				default:
					return false;
			}				
		}
		
		return false;
	}
}
