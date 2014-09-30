package view.gamestates;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import controller.Simulator;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import model.FieldType;
import de.unisaarland.cs.st.pirates.logger.LogWriter;
import view.GUIController;
import view.GUITransaction;
import view.SimpleEntity;
import view.events.MouseEvents;
import view.events.PlayEvents;
import view.utility.Camera;
import view.utility.Field;
import view.utility.GameState;
import view.utility.Generator;
import view.utility.Map;
import view.utility.Ressources;
import view.utility.Ship;

public class InGameState implements GameState, LogWriter {

	private GUIController manager;
	private final Canvas canvas;
	private BorderPane root;
	private GraphicsContext gc;
	
	private Map map;
	private Camera cam;
	private List<Ship> ships;				//TODO: maybe not needed
	private List<SimpleEntity> entities;	//TODO: maybe not needed
	private Field[][] fields;
	
	private Simulator sim;
	
	public InGameState(char[][] fieldChars, Ressources res) {
		ships = new ArrayList<Ship>();
		entities = new ArrayList<SimpleEntity>();
		
		canvas = new Canvas(1000, 600);
        canvas.getStyleClass().add("canvas");
        gc = canvas.getGraphicsContext2D();
        map = new Map(gc, res);
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

        
        Button play = new Button("Play");
		play.getStyleClass().add("menubutton");
		PlayEvents events1 = new PlayEvents(play, sim);
        
        root = new BorderPane();
		root.setCenter(canvas);
		root.setBottom(play);
		
        manager.getRoot().setCenter(root);
	}

	@Override
	public void exiting() {
		 manager.getRoot().setCenter(null);
	}

	@Override
	public void concealing() {
		manager.getRoot().setCenter(null);
	}

	@Override
	public void revealed() {
		manager.getTitleText().setText("");
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
		exiting();
	}
	
	
	public void step() throws IllegalStateException, IOException{
		sim.step();
		
	}
	
	
	public void setSimulator(Simulator sim){
		this.sim = sim;
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
		// TODO Auto-generated method stub
		
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
						break;
					case Y_COORD:
						y = arg3[i];
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
						break;
					case Y_COORD:
						y = arg3[i];
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
		
		if(arg0.equals(Entity.SHIP))
			ships.set(arg1, null);
		else
			entities.set(arg1, null);
			
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
			int x = (trans.getX() == null) ? ships.get(id).getX() : trans.getX();
			int y = (trans.getY() == null) ? ships.get(id).getY() : trans.getY();
			ships.get(id).setX(x);
			ships.get(id).setY(y);
			
			fields[ships.get(id).getX()][ships.get(id).getY()].setShip(null);
			fields[x][y].setShip(ships.get(id));
		}
		else if(entity == Entity.KRAKEN){
			int x = (trans.getX() == null) ? entities.get(id).getX() : trans.getX();
			int y = (trans.getY() == null) ? entities.get(id).getY() : trans.getY();
			entities.get(id).setX(x);
			entities.get(id).setY(y);
			
			fields[entities.get(id).getX()][entities.get(id).getY()].setKraken(null);
			fields[x][y].setKraken(entities.get(id));
		}		
		
		return this;
	}
	
	@Override
	public LogWriter addCustomHeaderData(String arg0) throws NullPointerException, ArrayIndexOutOfBoundsException {
		return this;
	}
}
