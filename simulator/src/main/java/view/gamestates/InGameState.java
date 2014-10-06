package view.gamestates;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import javafx.scene.text.Text;
import model.FieldType;
import de.unisaarland.cs.st.pirates.logger.LogWriter;
import view.GUIController;
import view.GUITransaction;
import view.SimpleEntity;
import view.events.MouseEvents;
import view.utility.Camera;
import view.utility.Configuration;
import view.utility.Field;
import view.utility.GameFlowControl;
import view.utility.GameState;
import view.utility.Map;
import view.utility.Ressources;
import view.utility.Ship;
import view.utility.Team;

public class InGameState implements GameState, LogWriter {

	private GUIController manager;
	private final Canvas canvas;
	private Group root;
	private Label tooltip;
	private GraphicsContext gc;
	private Accordion teamWindow;
	private Button teamClosed;
	private Button play;
	private Button pause;
	private Button next;
	private Button speedUp;
	private Button slowDown;
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
	private GameFlowControl control;
	private Configuration config;
	
	public InGameState(char[][] fieldChars, Ressources res, Integer turns, Configuration config) {
		this.config = config;
		ships = new ArrayList<Ship>();
		entities = new ArrayList<SimpleEntity>();
		maxRounds = turns;
		canvas = new Canvas(1280, 939);
		tooltip = new Label();
		tooltip.getStyleClass().add("tooltipi");
		tooltip.setVisible(false);
		addTooltipListener();
        canvas.getStyleClass().add("canvas");
        gc = canvas.getGraphicsContext2D();
        map = new Map(gc, res);
        rounds = 0;
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
        roundCounter.getStyleClass().add("menulabel");
        roundCounter.setTranslateX(10);
        
        next = new Button("Next");
        next.setTranslateY(665);
       
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
		
        play = new Button("Play");
        play.setTranslateY(665);
        play.setTranslateX(75);
        play.getStyleClass().add("canvasbutton");
        
        pause = new Button("Pause");
        pause.setVisible(false);
        pause.setTranslateY(665);
        pause.setTranslateX(75);
        pause.getStyleClass().add("canvasbutton");
		
		slowDown = new Button("-");
		slowDown.setTranslateY(665);
		slowDown.setTranslateX(145);
		slowDown.getStyleClass().add("canvasbutton");
		
		speedUp = new Button("+");
		speedUp.setTranslateY(663);
		speedUp.setTranslateX(170);
		speedUp.getStyleClass().add("canvasbutton");
		
		this.control = new GameFlowControl(sim, play, pause, speedUp, slowDown);
		
		Button end = new Button("End");
		end.getStyleClass().add("canvasbutton");
		end.setTranslateY(665);
		end.setTranslateX(1190);
		end.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				manager.switchState(new MainMenuState());
			}
		});
		
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
		for(final Team team: teams){
			
			final TitledPane pane = new TitledPane(team.toString(), new Text(giveTeamText(team)));
			pane.setOnMouseClicked(new EventHandler<MouseEvent>(){

				@Override
				public void handle(MouseEvent arg0) {
					map.drawMap();
					for(Ship ship: team.getShips()){
						Field field = fields[ship.getX()][ship.getY()];
						map.markField(field);
					}
				}
			});
			
			teamWindow.getPanes().add(pane);
		}
		
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
		root.getChildren().addAll(canvas, roundCounter, play, pause, next, slowDown, speedUp, end, tooltip, teamClosed, teamOpened);
		manager.getScene().setRoot(root);
	}

	@Override
	public void exiting() {
		 control.close();
		 manager.getScene().setRoot(manager.getRoot());
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
	
	public Label getTooltip(){
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
		control.close();
		
		roundCounter.setText(roundCounter.getText() + "  (Game Over)");
		play.setVisible(false);
		pause.setVisible(false);
		next.setVisible(false);
		slowDown.setVisible(false);
		speedUp.setVisible(false);
	}
	
	@Override
	public LogWriter notify(Entity arg0, int arg1, Key arg2, int arg3) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		if(arg0.equals(Entity.SHIP)){
			Ship ship = ships.get(arg1);
			
			switch(arg2){
				case CONDITION:
					ship.setCondition(arg3);
					break;
				case DIRECTION:
					ship.setDirection(arg3);
					fields[ship.getX()][ship.getY()].rotateShip(arg3);
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
			SimpleEntity entity = entities.get(arg1);
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
			teams.set(arg0, new Team(arg0, config));
		teams.get(arg0).setScore(arg1);
		
		if(teamWindow != null){
			TitledPane pane = teamWindow.getPanes().get(arg0);
			//pane.setText(pane.getText() + " " + teams.get(arg0).getScore());
			pane.setContent(new Text(giveTeamText(teams.get(arg0))));
		}
			
		return this;
	}

	@Override
	public void logStep() throws IllegalStateException, IOException {
		rounds++;
		roundCounter.setText(rounds.toString());
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
			Ship ship = new Ship(arg1);
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
							teams.set(arg3[i], new Team(arg3[i], config));
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
						break;
					case Y_COORD:
						y = arg3[i];
						break;
				}
			}
			fields[x][y].setShip(ship);
			ship.setField(fields[x][y]);
			ships.add(ship);
		}
		else{
			SimpleEntity entity = new SimpleEntity(arg1);
			entity.setEntityType(arg0);
			
			for(int i = 0; i < arg2.length; i++){
				switch(arg2[i]){
					case FLEET:
						entity.setFleet(arg3[i]);
						break;
					case VALUE:
						entity.setValue(arg3[i]);
						break;
					case X_COORD:
						entity.setX(arg3[i]);
						break;
					case Y_COORD:				
						entity.setY(arg3[i]);
						break;
					default:
						throw new IllegalArgumentException();
				}
			}
			switch(arg0){
				case BUOY:
					fields[entity.getX()][entity.getY()].addBuoy(entity);
					break;
				case KRAKEN:
					fields[entity.getX()][entity.getY()].setKraken(entity);
					break;
				case TREASURE:
					fields[entity.getX()][entity.getY()].setTreasure(entity);
					break;
				default:
					break;
			}
			entities.add(entity);
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
			
			ship.setField(fields[x][y]);
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
