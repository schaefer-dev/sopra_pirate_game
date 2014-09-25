package view;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import de.unisaarland.cs.st.pirates.logger.LogWriter;


/**
 * This class implements the LogWriter interface and writes the logged data formatted in a textfile that is specified as a parameter in
 * the init(..) method
 * 
 * 
 * @author Rafael THeis
 *
 */
public class SimpleLogWriter implements LogWriter {
	
	public enum LogType{
		HEADER,
		FOOTER,
		_Field,
		Create,
		Values,
		Update,
		Error,
		Debug,
	}
	
	private PrintWriter out;
	private Integer round;
	private Long startTime;
	private int teamCount;
	private int[] teamScores;
	
	private boolean init;
	private boolean headerEnd;
	
	private String border = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
	
	@Override
	public void init(OutputStream arg0, String arg1, String... arg2) throws NullPointerException, IOException, ArrayIndexOutOfBoundsException {
		if(/*arg0 == null ||*/arg1 == null || arg2 == null) throw new NullPointerException();
		if(arg2.length == 0) throw new ArrayIndexOutOfBoundsException();
		
		if (arg0 == null){
			File file = new File("src/test/resources/log.log");
			out = new PrintWriter(file);
		}
		else
			out = new PrintWriter(arg0);
			
		init = true;
		round = 0;
		
		if(arg2.length == 1)
			teamCount = 26;
		else
			teamCount = arg2.length;
		
		teamScores = new int[teamCount];	
		startTime = System.nanoTime();
		//Read map size
		int i = arg1.indexOf('\n');
		String first = arg1.substring(0, i);
		String rest = arg1.substring(i);
		rest = rest.substring(1);
		i = rest.indexOf('\n');
		String second = rest.substring(0, i);
		
		//Read teams
		String teams = "Teams: {";
		for(int j = 0; j < arg2.length; j++)
			teams += teamName(j) + ",";
		teams = teams.substring(0, teams.length()-1);
		teams += "}";
		
		try{
			out.write("\n");
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
			addFooterData();
			out.flush();
			out.close();	
		}
		catch(Exception e){
			throw new IOException();
		}
	}
	
	
	private void write(LogType type, String message, boolean showTime){
		if(showTime) checkBorder(type);
		
		String step = (round > 0) ? round.toString() : "#";
		String time = (showTime) ? "  (" + elapsedTime() + "s)" : "";
			
		out.write("[" + step + "]" + "[" + type + "] " + message + time + "\n");
	}
	
	private void write(LogType type, String prefix, String object, String bridge, String value, boolean showTime){
		if(showTime) checkBorder(type);
		
		prefix = "[" + prefix + "]";
		String time = (showTime) ? "  (" + elapsedTime() + "s)" : "";
		
		object = String.format("%-15s", object).replace(' ', ' ');
		bridge = String.format("%-15s", bridge).replace(' ', ' ');
		value  = String.format("%-10s", value).replace(' ', ' ');
		
		out.write(prefix + "[" + type + "] " + object + bridge + value + time + "\n");
	}
	
	private void write(LogType type, String object, String bridge, String value, boolean showTime){
		write(type, round.toString(), object, bridge, value, showTime);
	}

	@Override
	public LogWriter addCustomHeaderData(String arg0) throws NullPointerException, ArrayIndexOutOfBoundsException {
		if(arg0 == null) throw new NullPointerException();
		if(arg0.length() > 1e6) throw new ArrayIndexOutOfBoundsException();
		if(!(init && round == 0)) throw new IllegalStateException();
		
		write(LogType.HEADER, arg0, false);
		return this;
	}
	
	
	public void addFooterData(){
		out.write("\nFinished " + border);
		
		int winner = 0;
		for(int i = 1; i < teamScores.length; i++){
			if(teamScores[i] > teamScores[winner])
				winner = i;
		}
		Integer winnerPoints = teamScores[winner];
		write(LogType.FOOTER, "#", "Winner:", "Team " + teamName(winner), winnerPoints.toString() + " points" , false);
		
		write(LogType.FOOTER, "#", "Duration:", elapsedTime() + "s", "", false);
	}
	
	@Override
	public LogWriter addCell(Cell arg0, Integer arg1, int arg2, int arg3) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException, IllegalStateException {
		if(arg0 == null) throw new NullPointerException();
		if(!(init && round == 0)) throw new IllegalStateException();
		if(arg2 < 0 || arg3 < 0) throw new IllegalArgumentException();
		/*
		if(teamCount > 1 && arg1 != null){
			if(arg1 > teamCount)
				throw new ArrayIndexOutOfBoundsException();
		}*/
		
		if(arg1 == null)
			write(LogType._Field, "#", arg0.toString(), "at", "(" + arg2 + "," + arg3 + ")", true);
		else
			write(LogType._Field, "#", "BASE" + "(" + teamName(arg1) + ")", "at", "(" + arg2 + "," + arg3 + ")", true);
	
		return this;
	}

	@Override
	public void logStep() throws IllegalStateException, IOException {
		if(!init) throw new IllegalStateException();
		round++;
		try{
			out.write("\nRound " + round.toString() + "  " + border);
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
		
		String prefix = (round > 0) ? round.toString() : "#";
		write(LogType.Create, prefix, entityName(arg0, arg1), "", "", true);
		for(int i = 0; i < arg2.length; i++){
			if(!validKey(arg0, arg2[i])) throw new IllegalArgumentException();
			
			Integer arg = arg3[i];
			write(LogType.Values, prefix, "", arg2[i].toString() + ":", arg.toString(), false);
		}
		
		return this;
	}

	@Override
	public LogWriter destroy(Entity arg0, int arg1) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		if(arg0 == null) throw new IllegalArgumentException();
		if(arg1 < 0) throw new IllegalArgumentException();
		if(round == 0) throw new IllegalStateException();

		write(LogType.Update, entityName(arg0, arg1), "destroyed", "", true);		
		return this;
	}

	@Override
	public LogWriter fleetScore(int arg0, int arg1) throws IllegalArgumentException, IllegalStateException {
		if(arg0 < 0 || arg1 < 0) throw new IllegalArgumentException();
		//if(teamCount != 1 && arg0 > teamCount) throw new ArrayIndexOutOfBoundsException();
		if(!init) throw new IllegalStateException();
		
		Integer score = arg1;
		teamScores[arg0] = score;
		String prefix = (round > 0) ? round.toString() : "#";
		write(LogType.Update, prefix, "TEAM " + teamName(arg0), "Score:", "->" + score.toString(), true);
		
		return this;
	}

	@Override
	public LogWriter notify(Entity arg0, int arg1, Key arg2, int arg3) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		if(arg0 == null | arg2 == null) throw new NullPointerException();
		if(arg1 < 0) throw new IllegalArgumentException();
		//if(round == 0) throw new IllegalStateException();
		if(!validKey(arg0,  arg2)) throw new IllegalArgumentException();
		
		Integer value = arg3;
		write(LogType.Update, entityName(arg0, arg1), arg2.toString() + ":", "->" + value.toString(), true);
		
		return this;
	}
	
	@Override
	public Transaction beginTransaction(Entity arg0, int arg1) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		if(arg0 == null) throw new NullPointerException();
		if(arg1 < 0) throw new IllegalArgumentException();
		if(!init) throw new IllegalStateException();
		
		return new SimpleTransaction(entityName(arg0, arg1), arg0, this);
	}

	@Override
	public LogWriter commitTransaction(Transaction arg0) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		if(arg0 == null) throw new NullPointerException();
		if(arg0.toString().length() == 0) throw new IllegalArgumentException();
		if(round == 0) throw new IllegalStateException();
		
		SimpleTransaction trans = (SimpleTransaction) arg0;
		write(LogType.Update, trans.getIntro(), "", "", true);
		
		for(List<String> key: trans.getTransactions())
			write(LogType.Values, key.get(0), key.get(1), "->" + key.get(2), false);
			
		return this;
	}
	
	
	private String elapsedTime(){
		Long t = System.nanoTime() - startTime;		
		Double d = t.doubleValue()/1e9;
		d = Math.floor(d * 1e4)/1e4;
		String time = d.toString();
		
		return time;
	}
	
	private void checkBorder(LogType type){
		if(!headerEnd){
			if(type.equals(LogType.Create) || type.equals(LogType._Field )){
				out.write("\nCreation " + border + "\n");
				headerEnd = true;
			}
		}
	}
	
	private String entityName(Entity entity, int id){
		Integer myId = id;
		return entity.toString() + "(#" + myId.toString() + ")";
	}
	
	private String teamName(int i){
		char x = ((char)('A' + i));
		return "" + x;
	}
	
	public boolean validKey(Entity e, Key k){
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
