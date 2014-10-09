package de.unisaarland.cs.st.pirates.group5.view.gamestates;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import de.unisaarland.cs.st.pirates.group5.controller.Simulator;
import de.unisaarland.cs.st.pirates.group5.model.FieldType;
import de.unisaarland.cs.st.pirates.group5.view.GUIController;
import de.unisaarland.cs.st.pirates.group5.view.GUITransaction;
import de.unisaarland.cs.st.pirates.group5.view.SimpleEntity;
import de.unisaarland.cs.st.pirates.group5.view.events.MouseEvents;
import de.unisaarland.cs.st.pirates.group5.view.utility.Camera;
import de.unisaarland.cs.st.pirates.group5.view.utility.Configuration;
import de.unisaarland.cs.st.pirates.group5.view.utility.Field;
import de.unisaarland.cs.st.pirates.group5.view.utility.GameFlowControl;
import de.unisaarland.cs.st.pirates.group5.view.utility.GameState;
import de.unisaarland.cs.st.pirates.group5.view.utility.Map;
import de.unisaarland.cs.st.pirates.group5.view.utility.Ressources;
import de.unisaarland.cs.st.pirates.group5.view.utility.Ship;
import de.unisaarland.cs.st.pirates.group5.view.utility.Team;
import de.unisaarland.cs.st.pirates.group5.view.utility.TeamPane;
import de.unisaarland.cs.st.pirates.logger.LogWriter;

public class InGameState implements GameState, LogWriter {

	private GUIController manager;
	private Canvas canvas;
	private Group root;
	private Label tooltip;
	private GraphicsContext gc;
	private Accordion teamWindow;
	private Button play;
	private Button pause;
	private Button next;
	private Button speedUp;
	private Button slowDown;
	private Label speed;
	
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
	
	private double sWidth;
	private double sHeight;
	private double buttonSize;
	
	public InGameState(Ressources res, Integer turns, Configuration config, GUIController manager) {
		this.config = config;
		this.manager = manager;
		ships = new ArrayList<Ship>();
		entities = new ArrayList<SimpleEntity>();
		maxRounds = turns;
		canvas = new Canvas(manager.getScene().getWidth(), manager.getScene().getWidth()*0.733);
		tooltip = new Label();
		tooltip.getStyleClass().add("tooltipi");
		tooltip.setVisible(false);
		addTooltipListener();
        canvas.getStyleClass().add("canvas");
        gc = canvas.getGraphicsContext2D();
        map = new Map(gc, res);
        rounds = 0;
        sWidth = manager.getScene().getWidth();
        sHeight = manager.getScene().getHeight();
        buttonSize = sWidth/40;
	}
	
	@Override
	public void entered(GUIController control) {
		manager = control;
		manager.getTitleText().setText("");
		
        cam = new Camera(fields);
        map.initMap(fields, cam);
        map.addMapDetails();
        map.drawMap();
       
        roundCounter = new Label(rounds.toString());
        roundCounter.getStyleClass().add("menulabel");
        roundCounter.setTranslateX(10);
        
		ImageView nextImage = new ImageView(manager.getRessources().getNextImage());
		nextImage.setFitHeight(buttonSize);
		nextImage.setFitWidth(buttonSize);
        next = new Button();
        next.setGraphic(nextImage);
        next.setTranslateY(sHeight/1.08);
        next.setTranslateX(sWidth/2.5);
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
		
		ImageView playImage = new ImageView(manager.getRessources().getPlayImage());
		playImage.setFitHeight(buttonSize);
		playImage.setFitWidth(buttonSize);
        play = new Button();
        play.setGraphic(playImage);
        play.setTranslateY(sHeight/1.08);
        play.setTranslateX(sWidth/2.3);
        play.getStyleClass().add("canvasbutton");
        
		ImageView pauseImage = new ImageView(manager.getRessources().getPauseImage());
		pauseImage.setFitHeight(buttonSize);
		pauseImage.setFitWidth(buttonSize);
        pause = new Button();
        pause.setGraphic(pauseImage);
        pause.setVisible(false);
        pause.setTranslateY(sHeight/1.08);
        pause.setTranslateX(sWidth/2.3);
        pause.getStyleClass().add("canvasbutton");
		
		ImageView slowDownImage = new ImageView(manager.getRessources().getSlowDownImage());
		slowDownImage.setFitHeight(buttonSize);
		slowDownImage.setFitWidth(buttonSize);
		slowDown = new Button();
		slowDown.setGraphic(slowDownImage);
		slowDown.setTranslateY(sHeight/1.08);
		slowDown.setTranslateX(sWidth/2.1);
		slowDown.getStyleClass().add("canvasbutton");
		
		ImageView speedUpImage = new ImageView(manager.getRessources().getSpeedUpImage());
		speedUpImage.setFitHeight(buttonSize);
		speedUpImage.setFitWidth(buttonSize);
		speedUp = new Button();
		speedUp.setGraphic(speedUpImage);
		speedUp.setTranslateY(sHeight/1.08);
		speedUp.setTranslateX(sWidth/1.95);
		speedUp.getStyleClass().add("canvasbutton");
		
		speed = new Label("10x");
		speed.setTranslateY(sHeight/1.075);
		speed.setTranslateX(sWidth/1.75);
		speed.getStyleClass().add("menulabel");
		
		this.control = new GameFlowControl(this, play, pause, speedUp, slowDown, speed);
		
        MouseEvents events = new MouseEvents(cam, map, gc, tooltip, this.control);
        events.addMouseDragEvent(canvas, true);
        events.addMouseScrollEvent(canvas);
        events.addMouseClickEvent(canvas);
		
		Button end = new Button("Main Menu");
		end.getStyleClass().add("inmenubutton");
		end.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				manager.switchState(new MainMenuState());
			}
		});
		
		Button restart = new Button("Restart");
		restart.getStyleClass().add("inmenubutton");
		restart.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				manager.removeState();
				manager.getRoot().setCenter(null);
			}
		});
		
		VBox menuselection = new VBox(2);
		menuselection.getChildren().addAll(restart, end);
		
        final TitledPane menu = new TitledPane("Menu", menuselection);
        menu.setExpanded(false);
        menu.setTranslateX(sWidth/1.26);  
        menu.setOnMouseExited(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				menu.setExpanded(false);
			}
		});
        
		
		teams.removeAll(Collections.singleton(null));
		teamWindow = new Accordion(); 
		for(final Team team: teams){
			final TeamPane pane = new TeamPane(team, new Text(), map);
			teamWindow.getPanes().add(pane);
		}
		
        teamWindow.expandedPaneProperty().addListener(new ChangeListener<TitledPane>() {
                    
        	public void changed(ObservableValue<? extends TitledPane> ov, TitledPane oldVal, TitledPane newVal) {
    			if(newVal != null){
    				TeamPane pane = (TeamPane) newVal;
    				pane.update();
    				Team team = pane.getTeam();
    				newVal.setStyle("-fx-text-fill: #" + team.getColorRGB());
    				
    				for(Ship ship: team.getShips())
    					ship.marked = true;
    				
    				map.drawMap();
    			}
    			
    			if(oldVal != null){
    				TeamPane pane = (TeamPane) oldVal;
    				Team team = pane.getTeam();
    				oldVal.setStyle("-fx-text-fill: Black");
    				oldVal.setStyle("-fx-mark-color: #"+team.getColorRGB());
    				
    				for(Ship ship: team.getShips())
    					ship.marked = false;
    				
    				map.drawMap();
    			}
            }
        });

        final TitledPane teamOpen = new TitledPane("Teams", teamWindow);
        teamOpen.setTranslateX(sWidth/1.15);        
		
		root = new Group();
		root.getChildren().addAll(canvas, roundCounter, play, pause, next, speedUp, slowDown, speed, menu, tooltip, teamOpen);
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
	
	@Override
	public void init(OutputStream arg0, String arg1, String... arg2) throws NullPointerException, IOException, ArrayIndexOutOfBoundsException {
		int i = arg1.indexOf('\n');
		String x = arg1.substring(0, i);
		String rest = arg1.substring(i);
		rest = rest.substring(1);
		i = rest.indexOf('\n');
		String y = rest.substring(0, i);
		fields = new Field[Integer.parseInt(x)][Integer.parseInt(y)];
		
		double mapRatio = (double)fields[0].length/(double)fields.length;
		canvas.setHeight(manager.getScene().getWidth()*(0.733*mapRatio));
		
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
		speed.setVisible(false);
		
		Team winner = teams.get(0);
		for(Team team: teams){
			if(team.getScore() > winner.getScore())
				winner = team;
		}
		
		int teamIndex = teams.indexOf(winner);
		TitledPane winnerPane = teamWindow.getPanes().get(teamIndex);
		winnerPane.setExpanded(true);
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
					fields[ship.getX()][ship.getY()].setShip(ship);
					break;
				case FLEET:
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
		teams.get(arg0).setScore(arg1);
		
		if(teamWindow != null){
			TeamPane pane = (TeamPane) teamWindow.getPanes().get(arg0);
			pane.update();
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
			if(teams.get(arg1) == null)
				teams.set(arg1, new Team(arg1, config));
			
			fields[arg2][arg3] = new Field(map, arg2, arg3, teams.get(arg1));
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
						entity.setFleet(teams.get(arg3[i]));
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
			
			Team team = ship.getFleet();
			team.deleteShip(ship);
			TeamPane pane = (TeamPane) teamWindow.getPanes().get(ship.getFleet().getID());
			pane.update();
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
