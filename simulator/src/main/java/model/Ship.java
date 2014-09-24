package model;

import controller.Command;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Entity;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Key;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Transaction;


/**
 * The Ship class
 *
 * An instance of this class will perform a single act().
 *
 * Every Ship basically owns its own programcounter, which describes in which line of the
 * CommandList (which represents the teams tactics) the ship currently is. Ships are called
 * from the simulator.class to perform an act if they are allowed to do so (break = 0).
 *
 * Additionally the ship holds the described Registers which describe its condition,
 * position and so on.
 *
 * If one ship performed an act, the next ship is called via ship.getNextShip(), so
 * firstShip in Map basically is an initial node of a whole linked List of ships, due to
 * the improved ability to delete single ships efficiently and easy.
 *
 * The Ship starts the execution of its next command and is saving its condition after
 * executing this exact command. The execution itself takes place in the Command.execute
 * methods of the various command classes. The ship is not only able to act actively but
 * also passively (for example destroying itself with deleting itself from the team, map,
 * field and so on if an enemy ship destroys it by winning a fight).
 *
 * Ship also has the ability to convert relative directions used in tactics and the ship
 * to absolute directions on the map to avoid confusion by only working with absolute
 * directions outside the ship.class
 *
 *
 * @author danielschaefer
 */
public class Ship {

	public static final int undefinedInt = 6;

	private Team team;
	private int pc;
	private int pause;
	private final int id;
	private Field field;
	private int noPositivActionCounter;
	private int[] registers = new int[18];
	private Ship previousShip;
	private Ship nextShip;
	private boolean hasLogWriter;



	/**
	 * Constructor for Ship.class
	 *
	 * @param team
	 * @param field
	 * @param id
	 * @param previous
	 *
	 */
	public Ship(Team team, Field field, int id, Ship previous){
		this.id = id;
		this.field=field;						// field should always be null when called properly (outside tests)!
		this.team=team;
		if(field != null)
			hasLogWriter = (field.provideLogger() != null);
		else
			hasLogWriter = false;
		this.previousShip=previous;
		if (previous!=null)
			previousShip.nextShip=this;
		registers[Register.ship_moral.ordinal()]=4;
		registers[Register.ship_condition.ordinal()]=3;
		registers[Register.ship_direction.ordinal()]=0;
		registers[Register.ship_load.ordinal()]=0;
		registers[Register.sense_celltype.ordinal()]=CellType.Undefined.ordinal();
		registers[Register.sense_enemymarker.ordinal()]=BoolWert.Undefined.ordinal();
		registers[Register.sense_marker0.ordinal()]=BoolWert.Undefined.ordinal();
		registers[Register.sense_marker1.ordinal()]=BoolWert.Undefined.ordinal();
		registers[Register.sense_marker2.ordinal()]=BoolWert.Undefined.ordinal();
		registers[Register.sense_marker3.ordinal()]=BoolWert.Undefined.ordinal();
		registers[Register.sense_marker4.ordinal()]=BoolWert.Undefined.ordinal();
		registers[Register.sense_marker5.ordinal()]=BoolWert.Undefined.ordinal();
		registers[Register.sense_shipcondition.ordinal()]=undefinedInt;
		registers[Register.sense_shipdirection.ordinal()]=undefinedInt;
		registers[Register.sense_shiploaded.ordinal()]=BoolWert.Undefined.ordinal();
		registers[Register.sense_shiptype.ordinal()]=ShipType.Undefined.ordinal();
		registers[Register.sense_supply.ordinal()]=BoolWert.Undefined.ordinal();
		registers[Register.sense_treasure.ordinal()]=BoolWert.Undefined.ordinal();
		this.pc=0;
		this.pause=0;
		this.nextShip=null;
		this.noPositivActionCounter=0;
	}

	/**
	 * Executes the next command in the commandList at index pc
	 * logs PC changing in the end if he changed (compared to oldpc)
	 *
	 */
	public void act(){
		if (pc+1 > this.getTeam().getCommands().size())
			this.destroy();
		else{
			if (pause == 0){
				pc = pc +1;
				int oldpc=pc-1;
				noPositivActionCounter+=1;

				team.getCommands().get(oldpc).execute(this);
				if (this.field!=null){
					if (noPositivActionCounter==40){
						this.changeMoral(-1);
						noPositivActionCounter=0;
					}
				//	if (pc!=oldpc && hasLogWriter)
			//		{
						field.provideLogger().notify(Entity.SHIP, id, Key.PC, 3);
				//	}
				}
			}
			else
				changePause(-1);
		}
	}


	/**
	 * Getter for noPositiveActionCounter
	 *
	 * @return 	noPositiveActionCounter which describes the amout of turn the ship
	 * has not done a "positive" action (see project description).
	 */
	public int getNoPositivActionCounter() {
		return noPositivActionCounter;
	}


	/**
	 * simple getter for pc
	 *
	 * @return 	programcounter
	 */
	public int getPC(){
		return this.pc;
	}


	/**
	 * simple getter for Team of a ship
	 *
	 * @return 	the team of the ship instance
	 */
	public Team getTeam(){
		return this.team;
	}


	/**
	 * simple getter for the Field of a ship
	 *
	 * @return	the Field the ship instance is currently standing on
	 */
	public Field getPosition(){
		return this.field;
	}


	/**
	 * simple setter for the Field of a ship
	 *
	 * @param field		the Field the ship instance is currently standing on
	 */
	public void setField(Field field){
		//no logging!
		this.field=field;
		if(field != null)
			hasLogWriter = (field.provideLogger() != null);
		else
			hasLogWriter = false;
	}


	/**
	 * simple setter for the pc of a ship
	 *
	 * @param i			the pc at which the ship currently is
	 */
	public void setPC(int i){
		// no logging!
		this.pc=i;
	}


	/**
	 * simple fetter for the next Command
	 *
	 * @return	the next Command, so just the command at index pc of the commandList
	 */
	public Command getCommand(){
		return team.getCommands().get(pc);
	}

	public int getID(){
		return this.id;
	}

	public int getShipDirection(){
		return registers[Register.ship_direction.ordinal()];
	}

	public void changeDirection(boolean left){
		int dir = registers[Register.ship_direction.ordinal()];
		if (left){
			if (dir==0){
				registers[Register.ship_direction.ordinal()]=5;
				if(hasLogWriter)
					field.provideLogger().notify(Entity.SHIP, id, Key.DIRECTION, 5);
				return;
			}
			else{
				registers[Register.ship_direction.ordinal()]=dir-1;
				if(hasLogWriter)
					field.provideLogger().notify(Entity.SHIP, id, Key.DIRECTION, dir-1);
				return;
			}
		}
		else{
			if (dir==5){
				registers[Register.ship_direction.ordinal()]=0;
				if(hasLogWriter)
					field.provideLogger().notify(Entity.SHIP, id, Key.DIRECTION, 0);
				return;
			}
			else{
				registers[Register.ship_direction.ordinal()]=dir+1;
				if(hasLogWriter)
					field.provideLogger().notify(Entity.SHIP, id, Key.DIRECTION, dir+1);
				return;
			}
		}
	}


	/**
	 * calculates the relative sight direction into the ships absolute direction to return the
	 * absolute watchdirection of the ship
	 *
	 * @param relDirection	relative sight direction
	 * @return				absolute watch direction of the ship
	 */
	public int relativeToAbsoluteDirection(int relDirection){
		if (relDirection>6)
			throw new IllegalArgumentException("no direction > 6 allowed");
		if (relDirection == 6)
			return 6;

		if (relDirection < 0)
			throw new IllegalArgumentException();
		int dir = this.getShipDirection();

		return ((dir+relDirection)%6);

	}


	/**
	 * simple getter for shipmoral
	 *
	 * @return	shipmoral
	 */
	public int getMoral(){
		return registers[Register.ship_moral.ordinal()];
	}


	/**
	 * calculates i to the ships Moral. Also checks that the moral wont go higher than 4 or less than 0
	 * increasing the ships moral also resets the noPositiveActionCounter to zero. If the moral changes
	 * these changes are logged here as well
	 *
	 * @param i	change moral value, can be >0 and <0
	 */
	public void changeMoral(int i){
		int moral = this.getMoral();

		if (i>0)
			noPositivActionCounter=0;

		if ((moral>=4)&&(i>=0))
			return;

		if ((moral<1)&&(i<=0))
			return;

		if ((moral+i)<=0){
			registers[Register.ship_moral.ordinal()]=0;
			if(hasLogWriter)
				field.provideLogger().notify(Entity.SHIP, id, Key.MORAL, 0);
			return;
		}

		if ((moral+i)>=4){
			registers[Register.ship_moral.ordinal()]=4;
			if(hasLogWriter)
				field.provideLogger().notify(Entity.SHIP, id, Key.MORAL, 4);
			return;
		}

		registers[Register.ship_moral.ordinal()]=moral+i;
		if(hasLogWriter)
			field.provideLogger().notify(Entity.SHIP, id, Key.MORAL, moral+i);
	}


	/**
	 * simple getter for shipcondition
	 *
	 * @return shipcondition
	 */
	public int getCondition(){
		return registers[Register.ship_condition.ordinal()];
	}


	/**
	 * calculates i to the ships Condition. Also checks that the condition wont go higher than 3 or less
	 * than 0. If the condition changes these changes are logged here as well. Also checks that condition
	 * can't ever be <=0 when calling this method.
	 *
	 * @param i	change ship condition value
	 */
	public void changeCondition(int i){
		int condition = this.getCondition();
		if (condition<=0)
			throw new IllegalStateException("ship was already destroyed, should not loose moral again!");

		if ((condition>=3)&&(i>=0))
			return;

		if ((condition+i)<=0){
			registers[Register.ship_condition.ordinal()]=0;
			if(hasLogWriter)
				field.provideLogger().notify(Entity.SHIP, id, Key.CONDITION, 0);
			this.destroy();
			return;
		}
		if ((condition+i)>=3){
			registers[Register.ship_condition.ordinal()]=3;
			if(hasLogWriter)
				field.provideLogger().notify(Entity.SHIP, id, Key.CONDITION, 3);
			return;
		}
		
		registers[Register.ship_condition.ordinal()]=condition+i;
		if(hasLogWriter)
			field.provideLogger().notify(Entity.SHIP, id, Key.CONDITION, condition+i);
	}


	/**
	 * simple getter for pause
	 *
	 * @return	current pause of the ship
	 */
	public int getPause(){
		return this.pause;
	}


	/**
	 * changes the pause of the ship with value i. If Pause is already >0 and i>0 throw IllegalStateException
	 * also logs changes if the pause really changes. Pause can never go below 0
	 *
	 * @param i	pause change value
	 */
	public void changePause(int i){
		int pauseBefore = getPause();
		if ((pauseBefore > 0) && (i > 0))
			throw new IllegalStateException("pause increase while pause has not even reached 0");
		if (i == 0)
			return;

		pause += i;
		if (pause <= 0)
			pause = 0;
		if (pauseBefore != pause && hasLogWriter)
			field.provideLogger().notify(Entity.SHIP, id, Key.RESTING, pause);

	}


	/**
	 * simple getter for shipload
	 *
	 * @return	currecnt shipload
	 */
	public int getLoad(){
		return registers[Register.ship_load.ordinal()];
	}


	/**
	 * simple setter for shipload. Will throw exception for everything but 0<=i<=4. Logs changes
	 *
	 * @param i	new load value
	 */
	public void setLoad(int i){
		//TODO need good tests because its setted immediately without checking again what was inside before, has to be called carefully!
		if ((i>4)||(i<0))
			throw new IllegalArgumentException("load can not be setted to value not between 0-4");
		registers[Register.ship_load.ordinal()]=i;
		
		if (hasLogWriter == false )throw new IllegalStateException ("hasLogWriter in field is false") ;
		if (field == null) throw new IllegalStateException ("Field in ship is null!");
		if (field.provideLogger() == null) throw new IllegalStateException("field.provideLogger is null!");
		
		if(hasLogWriter)
			field.provideLogger().notify(Entity.SHIP, id, Key.VALUE, i);
	}


	/**
	 * simple getter for getting the value inside the register reg. Calls reg.ordinal() for correct index
	 *
	 * @param reg	the register which value should be returned
	 * @return		the value of register reg (int)
	 */
	public int getSenseRegister(Register reg){
		return registers[reg.ordinal()];
	}


	/**
	 * simple setter for setting the value inside the register (should only be used for sense registers)
	 * checks the register enum, cases and checks if value is valid for this field before setting it.
	 * If its not valid, throw IllegalArgumentException
	 *
	 * @param reg		the register which value should be setted to a new value
	 * @param value		the new value for the register reg (int which mostly represents the various enums)
	 * @throws IllegalArgumentException	when value is not matching the register
	 */
	public void setSenseRegister(Register reg, int value){

		/* 6 is always a valid value because it represents undefined register */
		switch (reg){
			case ship_load:
				if ((value<0)||(value>3))
					if (value!=undefinedInt)
					throw new IllegalArgumentException("shipload only defined between 0 and 3");
				break;

			case ship_direction:
				if ((value<0)||(value>4))
					if (value!=undefinedInt)
					throw new IllegalArgumentException("shipdirection only defined between 0 and 4");
				break;

			case ship_moral:
				if ((value<0)||(value>3))
					if (value!=undefinedInt)
					throw new IllegalArgumentException("shipmoral only defined between 0 and 3");
				break;

			case ship_condition:
				if ((value<0)||(value>2))
					if (value!=undefinedInt)
					throw new IllegalArgumentException("shipcondition only defined between 0 and 2");
				break;

			case sense_celltype:
				if ((value<0)||(value>3))
					if (value!=CellType.Undefined.ordinal())
					throw new IllegalArgumentException("senseCelltype only defined between 0 and 3 (empty,island,home,enemyhome,undefined)");
				break;

			case sense_supply:
				if ((value<0)||(value>1))
					if (value!=BoolWert.Undefined.ordinal())
					throw new IllegalArgumentException("senseSupply only defined between 0 and 1 (true, false, undefined)");
				break;

			case sense_treasure:
				if ((value<0)||(value>1))
					if (value!=BoolWert.Undefined.ordinal())
					throw new IllegalArgumentException("senseTreasure only defined between 0 and 1 (true, false, undefined)");

			case sense_marker0:
				if ((value<0)||(value>1))
					if (value!=BoolWert.Undefined.ordinal())
					throw new IllegalArgumentException("senseMarker only defined between 0 and 1 (true, false, undefined)");
				break;

			case sense_marker1:
				if ((value<0)||(value>1))
					if (value!=BoolWert.Undefined.ordinal())
					throw new IllegalArgumentException("senseMarker only defined between 0 and 1 (true, false, undefined)");
				break;

			case sense_marker2:
				if ((value<0)||(value>1))
					if (value!=BoolWert.Undefined.ordinal())
					throw new IllegalArgumentException("senseMarker only defined between 0 and 1 (true, false, undefined)");
				break;

			case sense_marker3:
				if ((value<0)||(value>1))
					if (value!=BoolWert.Undefined.ordinal())
					throw new IllegalArgumentException("senseMarker only defined between 0 and 1 (true, false, undefined)");
				break;

			case sense_marker4:
				if ((value<0)||(value>1))
					if (value!=BoolWert.Undefined.ordinal())
					throw new IllegalArgumentException("senseMarker only defined between 0 and 1 (true, false, undefined)");
				break;

			case sense_marker5:
				if ((value<0)||(value>1))
					if (value!=BoolWert.Undefined.ordinal())
					throw new IllegalArgumentException("senseMarker only defined between 0 and 1 (true, false, undefined)");
				break;

			case sense_enemymarker:
				if ((value<0)||(value>2))
					if (value!=BoolWert.Undefined.ordinal())
					throw new IllegalArgumentException("senseEnemyMarker only defined between 0 and 1 (true, false, undefined)");
				break;

			case sense_shiptype:
				if ((value<0)||(value>1))
					if (value!=ShipType.Undefined.ordinal())
					throw new IllegalArgumentException("senseShiptype only defined between 0 and 1 (friend, enemy, undefined)");
				break;

			case sense_shipdirection:
				if ((value<0)||(value>5))
					if (value!=undefinedInt)
					throw new IllegalArgumentException("senseShipDirection only defined between 0 and 5 (directions+undefined)");
				break;

			case sense_shiploaded:
				if ((value<0)||(value>1))
					if (value!=BoolWert.Undefined.ordinal())
					throw new IllegalArgumentException("senseShipLoaded only defined between 0 and 1 (true, false, undefined)");
				break;

			case sense_shipcondition:
				if ((value<1)||(value>3))
					if (value!=undefinedInt)
					throw new IllegalArgumentException("senseShipCondition only defined between 1 and 3 (1, 2, 3, undefined)");
				break;

		}
		registers[reg.ordinal()]=value;
	}


	/**
	 * simple getter for nextShip
	 *
	 * @return	nextShip
	 */
	public Ship getNextShip(){
		return nextShip;
	}

	/**
	 * simple setter for nextShip
	 *
	 * @param next	nextShip
	 */
	public void setNextShip(Ship next){
		this.nextShip=next;
	}


	/**
	 * private helpmethod which manages all stuff related to destroying a ship. Setting the ship value on the field
	 * null, deleting the ship in the team, changing the nextShip previousShip values and maybe even the
	 * firstShip in Map.class. Logs the destruction afterwards and finally sets field=null (last step to
	 * avoid nullPointerExceptions)
	 *
	 */
	private void destroy(){
		if (previousShip==null){
			field.setShip(null);
			team.deleteShip(this);
			field.getMap().setFirstShip(nextShip);
			if (nextShip != null)
				nextShip.previousShip=null;
			if(hasLogWriter)
				field.provideLogger().destroy(Entity.SHIP, id);
			field = null;			// important for checks in Move if ship is still alive
		}
		else{
			field.setShip(null);
			team.deleteShip(this);
			previousShip.setNextShip(nextShip);
			if (nextShip != null)
				nextShip.previousShip=previousShip;
			if(hasLogWriter)
				field.provideLogger().destroy(Entity.SHIP, id);
			field = null;
		}

	}

}
