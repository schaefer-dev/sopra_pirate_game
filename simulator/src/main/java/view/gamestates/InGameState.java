package view.gamestates;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

import controller.Simulator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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
import view.utility.Ship;
import view.utility.Team;

public class InGameState implements GameState, LogWriter {

	private GUIController manager;
	private final Canvas canvas;
	private Group root;
	private Rectangle tooltip;
	private GraphicsContext gc;
	private Accordion teamWindow;
	private Button teamClosed;
	private VBox teamOpened;
	
	private Map map;
	private Camera cam;
	private List<Team> teams;
	private List<Ship> ships;				
	private List<SimpleEntity> entities;
	private Field[][] fields;
	private Integer rounds;
	private Integer maxRounds;
	private Label roundCounter;
	private Simulator sim;
	private PlayPauseEvent playPause;
	private Timer timer;
	
	private boolean closed;
	
	public InGameState(char[][] fieldChars, Ressources res, Integer turns) {
		ships = new ArrayList<Ship>();
		entities = new ArrayList<SimpleEntity>();
		maxRounds = turns;
		timer = new Timer();
		canvas = new Canvas(1280, 939);
		tooltip = new Rectangle(20, 20, 200, 200);
		tooltip.setStroke(Color.GRAY);
		tooltip.setFill(Color.WHITE);
		tooltip.setVisible(false);
		addTooltipListener();
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
        map.addMapDetails();
        map.drawMap();
        
        MouseEvents events = new MouseEvents(cam, map, gc, tooltip);
        events.addMouseDragEvent(canvas, true);
        events.addMouseScrollEvent(canvas);
        events.addMouseClickEvent(canvas);
        
        roundCounter = new Label(rounds.toString());
        roundCounter.setTranslateX(10);
        
        Button next = new Button("Next");
        next.setTranslateY(665);
        next.setTranslateX(75);
		next.getStyleClass().add("canvasbutton");
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
        play.setTranslateY(665);
        play.getStyleClass().add("canvasbutton");
        playPause = new PlayPauseEvent(this, timer);
		play.setOnAction(playPause);
		
		Button teamClose = new Button("Close >");
		teamClose.setVisible(true);
		teamClose.getStyleClass().add("canvasbutton");
		teamClose.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				teamOpened.setVisible(false);
				teamClosed.setVisible(true);
			}
		});
		
		teams.removeAll(Collections.singleton(null));
		teamWindow = new Accordion(); 
		for(Team team: teams)
			teamWindow.getPanes().add(new TitledPane(team.toString(), new Text(giveTeamText(team))));
		
		teamOpened = new VBox();
		teamOpened.setVisible(false);
		teamOpened.setTranslateX(1140);
		teamOpened.getChildren().addAll(teamClose, teamWindow);
		
		teamClosed = new Button("< Teams");
		teamClosed.setTranslateX(1140);
		teamClosed.getStyleClass().add("canvasbutton");
		teamClosed.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				teamOpened.setVisible(true);
				teamClosed.setVisible(false);
			}
		});
		
		
		root = new Group();
		root.getChildren().addAll(canvas, roundCounter, play, next, tooltip, teamClosed, teamOpened);
		manager.getScene().setRoot(root);
	}

	@Override
	public void exiting() {
		 playPause.close();
		 if(!closed){
			 try{
				 close();
			 }
			 catch(Exception e){}
		 }
		// manager.getScene().setRoot(manager.getRoot());
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
	
	public Rectangle getTooltip(){
		return tooltip;
	}
	
	private String giveTeamText(Team team){
		String score = "Score: " + team.getScore();
		String ships = "Ships: " + team.getShipCount();
		
		return score + "\n" + ships;
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
		
		teams = new ArrayList<Team>();
		if(arg2.length == 1){
			for(int j = 0; j < 26; j++)
				teams.add(null);
		}
		else{
			for(int j = 0; j < arg2.length; j++)
				teams.add(null);	
		}
	}

	@Override
	public void close() throws IllegalStateException, IOException {
		closed = true;
		exiting();
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
		if(teams.get(arg0) == null)
			teams.set(arg0, new Team(arg0));
		teams.get(arg0).setScore(arg1);
		
		if(teamWindow != null)
			teamWindow.getPanes().get(arg0).setContent(new Text(giveTeamText(teams.get(arg0))));
		
		return this;
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
						if(teams.get(arg3[i]) == null)
							teams.set(arg3[i], new Team(arg3[i]));
						teams.get(arg3[i]).addShip(ship);
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
			
			Team team = teams.get(ship.getFleet());
			team.deleteShip(ship);
			teamWindow.getPanes().get(ship.getFleet()).setContent(new Text(giveTeamText(team)));
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
	
	private void addTooltipListener(){
		tooltip.setOnMouseExited(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				tooltip.setVisible(false);	
			}
        });
        tooltip.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	
			@Override
			public void handle(MouseEvent arg0) {
				tooltip.setVisible(false);
			}
		});
	}
}
