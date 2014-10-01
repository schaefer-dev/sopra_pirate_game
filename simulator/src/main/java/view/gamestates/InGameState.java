package view.gamestates;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import controller.Simulator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.FieldType;
import de.unisaarland.cs.st.pirates.logger.LogWriter;
import view.GUIController;
import view.GUITransaction;
import view.SimpleEntity;
import view.events.MouseEvents;
import view.events.PlayPauseEvent;
import view.utility.Camera;
import view.utility.Field;
import view.utility.GameState;
import view.utility.Map;
import view.utility.Ressources;
import view.utility.Run;
import view.utility.Ship;

public class InGameState implements GameState, LogWriter {

	private GUIController manager;
	private final Canvas canvas;
	private BorderPane root;
	private GraphicsContext gc;
	
	private Map map;
	private Camera cam;
	private List<Ship> ships;				
	private List<SimpleEntity> entities;
	private Field[][] fields;
	private Integer rounds;
	private Integer maxRounds;
	private Label roundCounter;
	private Simulator sim;
	private PlayPauseEvent playPause;
	private boolean closed;
	
	public InGameState(char[][] fieldChars, Ressources res, Integer turns) {
		ships = new ArrayList<Ship>();
		entities = new ArrayList<SimpleEntity>();
		maxRounds = turns;
		canvas = new Canvas(950, 550);
        canvas.getStyleClass().add("canvas");
        gc = canvas.getGraphicsContext2D();
        map = new Map(gc, res);
        rounds = 0;
        closed = false;
	}
	
	@Override
	public void entered(GUIController control) {
		manager = control;
		manager.getTitleText().setText("");
		
        cam = new Camera(fields);
        map.initMap(fields, cam);
        map.drawMap();

        MouseEvents events = new MouseEvents(cam, map, gc);
        events.addMouseDragEvent(canvas, true);
        events.addMouseScrollEvent(canvas);
        events.addMouseClickEvent(canvas);
        
        roundCounter = new Label(rounds.toString());
        
        Button next = new Button("Next");
		next.getStyleClass().add("menubutton");
		next.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				try {
					sim.step();
				} 
				catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		
        Button play = new Button("Play");
        play.getStyleClass().add("menubutton");
        playPause = new PlayPauseEvent(this);
		play.setOnAction(playPause);

        HBox box = new HBox(20);
        box.getChildren().addAll(next, play);
        box.getStyleClass().add("hbox");		
        
        
        
        
        root = new BorderPane();
		root.setCenter(canvas);
		BorderPane.setAlignment(box, Pos.BOTTOM_CENTER);
		root.setBottom(box);
		
		BorderPane.setAlignment(roundCounter, Pos.TOP_CENTER);
		manager.getRoot().setTop(roundCounter);
        manager.getRoot().setCenter(root);
	}

	@Override
	public void exiting() {
		 manager.getRoot().setCenter(null);
		 playPause.close();
		 if(!closed){
			 try{
				 close();
			 }
			 catch(Exception e){}
		 }
	}

	@Override
	public void concealing() {
		manager.getRoot().setCenter(null);
	}

	@Override
	public void revealed() {
		manager.getTitleText().setText("");
	}
	
	
	
	public Simulator getSimulator(){
		return sim;
	}
	
	public void setSimulator(Simulator sim){
		this.sim = sim;
	}
		
	public Integer getRound(){
		return rounds;
	}
	
	public Integer getMaxRounds(){
		return maxRounds;
	}
	
	
	@Override
	public void init(OutputStream arg0, String arg1, String... arg2) throws NullPointerException, IOException, ArrayIndexOutOfBoundsException {
		int i = arg1.indexOf('\n');
		String x = arg1.substring(0, i);
		String rest = arg1.substring(i);
		rest = rest.substring(1);
		i = rest.indexOf('\n');
		String y = rest.substring(0, i);
		fields = new Field[Integer.parseInt(x)][Integer.parseInt(y)];
	}

	@Override
	public void close() throws IllegalStateException, IOException {
		closed = true;
		//exiting();
	}
	
	@Override
	public LogWriter notify(Entity arg0, int arg1, Key arg2, int arg3) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		if(arg0.equals(Entity.SHIP)){
			Ship ship = new Ship();
			
			switch(arg2){
				case CONDITION:
					ship.setCondition(arg3);
					break;
				case DIRECTION:
					ship.setDirection(arg3);
					break;
				case FLEET:
					ship.setFleet(arg3);
					break;
				case MORAL:
					ship.setMoral(arg3);
					break;
				case PC:
					ship.setPc(arg3);
					break;
				case RESTING:
					ship.setResting(arg3);
					break;
				case VALUE:
					ship.setLoad(arg3);
					break;
				case X_COORD:
				case Y_COORD:
					throw new IllegalStateException("X- & Y- coordinates should only be logged with transactions");
			}
		}
		else{
			SimpleEntity entity = new SimpleEntity();
			entity.setEntityType(arg0);

			switch(arg2){
				case FLEET:
					entity.setFleet(arg3);
					break;
				case VALUE:
					entity.setValue(arg3);
					break;
				case X_COORD:
				case Y_COORD:
					throw new IllegalStateException("X- & Y- coordinates should only be logged with transactions");
				default:
					throw new IllegalArgumentException();
			}
		}
		return this;
	}
	
	@Override
	public LogWriter fleetScore(int arg0, int arg1) throws IllegalArgumentException, IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void logStep() throws IllegalStateException, IOException {
		rounds++;
		//roundCounter.setText(rounds.toString());
	}
	
	@Override
	public LogWriter addCell(Cell arg0, Integer arg1, int arg2, int arg3) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException, IllegalStateException {
		
		FieldType type;
		switch(arg0){
			case ISLAND:
				type = FieldType.Island;
				break;
			case SUPPLY:
				type = FieldType.ProvisionIsland;
				break;
			case WATER:
				type = FieldType.Water;
				break;
			default:
				throw new IllegalArgumentException();
		}
		
		if(arg1 == null)
			fields[arg2][arg3] = new Field(map, arg2, arg3, type);
		else{
			fields[arg2][arg3] = new Field(map, arg2, arg3, arg1);
		}
		
		return this;
	}

	@Override
	public LogWriter create(Entity arg0, int arg1, Key[] arg2, int[] arg3) throws NullPointerException, IllegalArgumentException, ArrayIndexOutOfBoundsException, IllegalStateException {
		if(arg0.equals(Entity.SHIP)){
			Ship ship = new Ship();
			int x = 0;
			int y = 0;
			
			for(int i = 0; i < arg2.length; i++){
				switch(arg2[i]){
					case CONDITION:
						ship.setCondition(arg3[i]);
						break;
					case DIRECTION:
						ship.setDirection(arg3[i]);
						break;
					case FLEET:
						ship.setFleet(arg3[i]);
						break;
					case MORAL:
						ship.setMoral(arg3[i]);
						break;
					case PC:
						ship.setPc(arg3[i]);
						break;
					case RESTING:
						ship.setResting(arg3[i]);
						break;
					case VALUE:
						ship.setLoad(arg3[i]);
						break;
					case X_COORD:
						x = arg3[i];
						ship.setX(x);
						break;
					case Y_COORD:
						y = arg3[i];
						ship.setY(y);
						break;
				}
			}
			
			fields[x][y].setShip(ship);
			ships.add(ship);
		}
		else{
			SimpleEntity entity = new SimpleEntity();
			entity.setEntityType(arg0);
			int x = 0;
			int y = 0;
			
			for(int i = 0; i < arg2.length; i++){
				switch(arg2[i]){
					case FLEET:
						entity.setFleet(arg3[i]);
						break;
					case VALUE:
						entity.setValue(arg3[i]);
						break;
					case X_COORD:
						x = arg3[i];
						entity.setX(x);
						break;
					case Y_COORD:
						y = arg3[i];
						entity.setY(y);
						break;
					default:
						throw new IllegalArgumentException();
				}
				switch(arg0){
					case BUOY:
						fields[x][y].addBuoy(entity);
						break;
					case KRAKEN:
						fields[x][y].setKraken(entity);
						break;
					case TREASURE:
						fields[x][y].setTreasure(entity);
						break;
					default:
						break;
				}
				entities.add(entity);
			}
		}
		
		return this;
	}
	
	@Override
	public LogWriter destroy(Entity arg0, int arg1) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		if(arg0 == null) throw new NullPointerException();
		
		if(arg0.equals(Entity.SHIP)){
			Ship ship = ships.get(arg1);
			fields[ship.getX()][ship.getY()].setShip(null);
			ships.set(arg1, null);
		}
		else{
			SimpleEntity entity = entities.get(arg1);
			
			switch(entity.getEntityType()){
				case BUOY:
					fields[entity.getX()][entity.getY()].deleteBuoy(entity);
					break;
				case KRAKEN:
					fields[entity.getX()][entity.getY()].setKraken(null);
					break;
				case TREASURE:
					fields[entity.getX()][entity.getY()].setTreasure(null);
					break;
				default:
					break;
			}
			
			entities.set(arg1, null);
		}
			
		System.out.println(arg0.toString() + "(" + arg1 + ")");
		return this;
	}
	
	@Override
	public Transaction beginTransaction(Entity arg0, int arg1) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		return new GUITransaction(this, arg0, arg1);
	}
	
	@Override
	public LogWriter commitTransaction(Transaction arg0) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		GUITransaction trans = (GUITransaction) arg0;
		Entity entity = trans.getEntity();
		int id = trans.getID();
		
		if(entity == Entity.SHIP){
			Ship ship = ships.get(id);
			
			int x = (trans.getX() == null) ? ship.getX() : trans.getX();
			int y = (trans.getY() == null) ? ship.getY() : trans.getY();
			fields[ship.getX()][ship.getY()].setShip(null);
			
			ship.setX(x);
			ship.setY(y);
			fields[x][y].setShip(ship);
		}
		else if(entity == Entity.KRAKEN){
			SimpleEntity kraken = entities.get(id);
			
			int x = (trans.getX() == null) ? kraken.getX() : trans.getX();
			int y = (trans.getY() == null) ? kraken.getY() : trans.getY();
			fields[kraken.getX()][kraken.getY()].setKraken(null);
			
			kraken.setX(x);
			kraken.setY(y);		
			fields[x][y].setKraken(kraken);
		}		
		
		return this;
	}
	
	@Override
	public LogWriter addCustomHeaderData(String arg0) throws NullPointerException, ArrayIndexOutOfBoundsException {
		return this;
	}
}
