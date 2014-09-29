package view.gamestates;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import model.FieldType;
import de.unisaarland.cs.st.pirates.logger.LogWriter;
import view.GUIController;
import view.SimpleEntity;
import view.events.MouseEvents;
import view.utility.Camera;
import view.utility.Field;
import view.utility.GameState;
import view.utility.Generator;
import view.utility.Map;
import view.utility.Ship;

public class InGameState implements GameState, LogWriter {

	private GUIController manager;
	
	private Map map;
	private Camera cam;
	private List<Ship> ships;
	private List<SimpleEntity> entities;
	private Field[][] fields;
	private boolean init;
	
	public InGameState() {
		ships = new ArrayList<Ship>();
	}
	
	@Override
	public void entered(GUIController control) {
		manager = control;
		manager.getTitleText().setText("");
		
		ships = new ArrayList<Ship>();
		entities = new ArrayList<SimpleEntity>();
		
        final Canvas canvas = new Canvas(1000, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
 
        Generator gen = new Generator(198, 198, 40, 4);
		char[][] fields = gen.generateMap();
		Map map = new Map(gc);
		
		this.fields = new Field[fields.length][fields[0].length];
		for(int y = 0; y < fields[0].length; y++){
        	for(int x = 0; x < fields.length; x++){

        		char field = fields[x][y];
        		
        		if(field == '.')
        			this.fields[x][y] = new Field(map, x, y, FieldType.Water);
        		else
        			this.fields[x][y] = new Field(map, x, y, FieldType.Island);
        	}		
    	}
		
        cam = new Camera(this.fields);
        map.initMap(this.fields, cam);
        map.drawMap();
        
        MouseEvents events = new MouseEvents(cam, map, gc);
        events.addMouseDragEvent(canvas, true);
        events.addMouseScrollEvent(canvas);
        events.addMouseClickEvent(canvas);

        manager.getRoot().setCenter(canvas);
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
		// TODO Auto-generated method stub
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
	
	
	@Override
	public LogWriter notify(Entity arg0, int arg1, Key arg2, int arg3) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		// TODO Auto-generated method stub
		return null;
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
		else
			fields[arg2][arg3] = new Field(map, arg2, arg3, arg1);
		
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
	public LogWriter addCustomHeaderData(String arg0) throws NullPointerException, ArrayIndexOutOfBoundsException {
		return this;
	}
	
	@Override
	public Transaction beginTransaction(Entity arg0, int arg1) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		return null;
	}

	@Override
	public LogWriter commitTransaction(Transaction arg0) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		return null;
	}
}
