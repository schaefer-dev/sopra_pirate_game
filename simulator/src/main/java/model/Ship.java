package model;

import controller.Command;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Entity;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Key;

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
	
	public Ship(Team team, Field field, int id, Ship previous){
		this.id = id;
		this.field=field;						// field should always be null when called properly (outside tests)!
		this.team=team;
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
	
	public void act(){
		
		if (pause == 0){
			int oldpc=pc;
			pc+=1;
			noPositivActionCounter+=1;
			
			team.getCommands().get(oldpc).execute(this);
			if (noPositivActionCounter==40){
				this.changeMoral(-1);
				noPositivActionCounter=0;
			}
			if (pc!=oldpc)
				field.getMap().getLogWriter().notify(Entity.SHIP, id, Key.PC, pc);
		}
		else
			changePause(-1);
	}

	
	public int getNoPositivActionCounter() {
		return noPositivActionCounter;
	}

	public int getPC(){
		return this.pc;
	}
	
	public Team getTeam(){
		return this.team;
	}
	
	public Field getPosition(){
		return this.field;
	}
	
	public void setField(Field field){
		//no logging!
		this.field=field;
	}
	
	public void setPC(int i){
		// no logging!
		this.pc=i;
	}
	
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
				field.getMap().getLogWriter().notify(Entity.SHIP, id, Key.DIRECTION, 5);
				return;
			}
			else{
				registers[Register.ship_direction.ordinal()]=dir-1;
				field.getMap().getLogWriter().notify(Entity.SHIP, id, Key.DIRECTION, dir-1);
				return;
			}
		}
		else{
			if (dir==5){
				registers[Register.ship_direction.ordinal()]=0;
				field.getMap().getLogWriter().notify(Entity.SHIP, id, Key.DIRECTION, 0);
				return;
			}
			else{
				registers[Register.ship_direction.ordinal()]=dir+1;
				field.getMap().getLogWriter().notify(Entity.SHIP, id, Key.DIRECTION, dir+1);
				return;
			}
		}
	}
	
	public int relativeToAbsoluteDirection(int relDirection){
		if (relDirection == 6)
			return 6;
		
		if (relDirection < 0)
			throw new IllegalArgumentException();
		int dir = this.getShipDirection();
		
		return ((dir+relDirection)%6);
		
	}
	
	public int getMoral(){
		return registers[Register.ship_moral.ordinal()];
	}
	
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
			field.getMap().getLogWriter().notify(Entity.SHIP, id, Key.MORAL, 0);
			return;
		}
		
		if ((moral+i)>=4){
			registers[Register.ship_moral.ordinal()]=4;
			field.getMap().getLogWriter().notify(Entity.SHIP, id, Key.MORAL, 4);
			return;
		}
		
		registers[Register.ship_moral.ordinal()]=moral+i;
		field.getMap().getLogWriter().notify(Entity.SHIP, id, Key.MORAL, moral+i);
	}
	
	public int getCondition(){
		return registers[Register.ship_condition.ordinal()];
	}
	
	public void changeCondition(int i){
		int condition = this.getCondition();
		if (condition<=0)
			throw new IllegalStateException("ship was already destroyed, should not loose moral again!");
		
		if ((condition>=3)&&(i>=0))
			return;
			
		if ((condition+i)<=0){
			registers[Register.ship_condition.ordinal()]=0;
			field.getMap().getLogWriter().notify(Entity.SHIP, id, Key.CONDITION, 0);
			this.destroy();
			return;
		}
		if ((condition+i)>=3){
			registers[Register.ship_condition.ordinal()]=3;
			field.getMap().getLogWriter().notify(Entity.SHIP, id, Key.CONDITION, 3);
			return;
		}
		registers[Register.ship_condition.ordinal()]=condition+i;
		field.getMap().getLogWriter().notify(Entity.SHIP, id, Key.CONDITION, condition+1);
	}
	
	public int getPause(){
		return this.pause;
	}
	
	public void changePause(int i){
		if (pause>0)
			throw new IllegalStateException("pause increase while pause has not even reached 0");
		if (i == 0)
			return;
		
		pause += i;
		field.getMap().getLogWriter().notify(Entity.SHIP, id, Key.RESTING, pause);
		
	}
	
	public int getLoad(){
		return registers[Register.ship_load.ordinal()];
	}
		
	public void setLoad(int i){
		//TODO need good tests because its setted immediately without checking again what was inside before, has to be called carefully!
		if ((i>4)||(i<0))
			throw new IllegalArgumentException("load can not be setted to value not between 0-4");
		registers[Register.ship_load.ordinal()]=i;
		field.getMap().getLogWriter().notify(Entity.SHIP, id, Key.VALUE, i);
	}
	
	public int getSenseRegister(Register reg){
		return registers[reg.ordinal()];
	}
	
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
	
	public Ship getNextShip(){
		return nextShip;
	}
	
	public void setNextShip(Ship next){
		this.nextShip=next;
	}
	
	
	private void destroy(){
		if (previousShip==null){
			field.setShip(null);
			team.deleteShip(this);
			this.field.getMap().setFirstShip(nextShip);
			nextShip.previousShip=null;		
			field.getMap().getLogWriter().destroy(Entity.SHIP, id);
			field = null;			// important for checks in Move if ship is still alive
		}
		else{
			field.setShip(null);
			team.deleteShip(this);
			previousShip.nextShip.setNextShip(nextShip);
			if (nextShip != null)
				nextShip.previousShip=previousShip;	
			field.getMap().getLogWriter().destroy(Entity.SHIP, id);
			field = null;
		}
			
	}
		
}
