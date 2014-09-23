package commands;

import model.Base;
import model.Field;
import model.Island;
import model.ProvisionIsland;
import model.Ship;
import model.Water;
import controller.Command;

/**
 * move Class implements the Move Command which is used in tactics. The class holds all functionality which
 * is done by that command. Its called in the act() of a ship during a step() in the simulator class
 * 
 * 
 * @author danielschaefer
 *
 */
public class Move implements Command {

	private int elsePC;
	
	public Move(int pc){
		this.elsePC = pc;
	}
	
	/**
	 * execute Method which executes a single move command of a ship 
	 * 
	 * it differences between the different cases depending on the attributes of the field on which the ship
	 * tries to move. All relating sideeffects of the (tried) move are done in this method. For example 
	 * loosing condition and or even treasures when hitting an island, or moving on a ship with the following
	 * fight, the possible destruction of the loosing ship and the overtaking of the destroyed ships treasures
	 * the results of the fight (setters of new treasures, new fields and so on) run in the following private
	 * methods of the ship class. These methods are only called in execute and are just there to save
	 * codeduplication in execute. The calcAndSetShipPause method is also just there to save codeduplication
	 * because the pauseCalculation is called everytime when the ship actually moves, which happens in various
	 * cases of execute.
	 * 
	 * @param ship	the ship which moves
	 * 
	 */
	@Override
	public void execute(Ship ship) {
		// TODO Auto-generated method stub
		Field shipField = ship.getPosition();
		Field targetField = shipField.getNeigbour(0);
		int load = ship.getLoad();
		
		switch(targetField.getFieldType()){
		
		case Water:
			Water targetWater=(Water)targetField;
			if (targetWater.getShip()==null){
				/* no ship found so ship can move + check for Kraken */
				shipField.moveShip(targetWater);
				calcAndSetShipPause(ship);
				if (targetWater.getKraken()!=null)
					ship.changeCondition(-1);	
			}
			else{
				if (targetField.getShip().getTeam().equals(ship.getTeam()))
					ship.setPC(elsePC);
				else{
				
					// Kampf findet statt
					Ship targetShip = targetField.getShip();
					int shipMoral = ship.getMoral();
					int targetMoral = targetShip.getMoral();
					int shipCondition = ship.getCondition();
					int targetCondition = targetShip.getCondition();
					int shipLoad = ship.getLoad();
					int targetLoad = targetShip.getLoad();
					
					if (targetMoral > shipMoral){
						executeTargetShipWins(ship,targetShip);
					}
					
					if (shipMoral > targetMoral){
						executeShipWins(ship,targetShip);
					}
					
					if (shipMoral == targetMoral){
						
						if (targetCondition > shipCondition){
							executeTargetShipWins(ship,targetShip);
						}
						
						if (shipCondition > targetCondition){
							executeShipWins(ship,targetShip);
						}
						
						if (shipCondition == targetCondition){
							int z = ship.getPosition().getMap().getRandom().nextInt(2);
							
							if (z == 0){// TargetShip wins
								executeTargetShipWins(ship,targetShip);
							}
							
							if (z == 1){	// Ship wins
								executeShipWins(ship,targetShip);
							}
						}
					}
				}
			}
			break;
			
		case Base:
			Base targetBase=(Base)targetField;
			if (targetBase.getTeam().equals(ship.getTeam())){
				shipField.moveShip(targetBase);
				calcAndSetShipPause(ship);
				ship.changeMoral(4);
			}
			else{
				ship.setPC(elsePC);	
			}
			break;
			
		case Island: 
			Island targetIsland=(Island)targetField;
			ship.changeMoral(-1);
			ship.changeCondition(-1);
			ship.setPC(elsePC);
			if (load >= 1){
				ship.setLoad(load-1);
				shipField.exchangeTreasure(+1);
			}		
			break;
			
		case ProvisionIsland:
			ProvisionIsland targetProvIsland=(ProvisionIsland)targetField;
			ship.changeMoral(-1);
			ship.changeCondition(-1);
			ship.setPC(elsePC);
			if (load >= 1){
				ship.setLoad(load-1);
				shipField.exchangeTreasure(+1);
			}		
			break;		
		}
	}

	/**
	 * private helpmethod to calculate and set the ships new pause, depending on its moral and load.
	 * 
	 * @param ship
	 */
	private void calcAndSetShipPause(Ship ship){
		int pause = 4;
		if (ship.getMoral()==0)
			pause += 2;
		if (ship.getLoad() >= 1)
			pause += 2;
		ship.changePause(pause);
	}
	
	/**
	 * private helpmethod to do all the stuff when the ship which moves wins the fight. It checks if ships
	 * are actually destroyed if new treasures are created, if the ship gets any additional treasure and how
	 * much falls on the ground of the field. targetShip.destroy will delete the ship afterwards if its out
	 * of condition (also out of his team, map ,maybe firstShip in map and the linkedList implemented via 
	 * firstShip pointer in map.
	 * 
	 * @param ship
	 * @param targetShip
	 */
	private void executeShipWins(Ship ship, Ship targetShip){
		int targetCondition = targetShip.getCondition();
		int shipLoad = ship.getLoad();
		int targetLoad = targetShip.getLoad();
		
		if (targetCondition == 1){
			int spareRoom = 4-shipLoad;
			int loadLater = shipLoad;
			while ((spareRoom > 0)&&(targetLoad > 0)){
				targetLoad -= 1;
				spareRoom -= 1;
				loadLater += 1;
			}
			if (loadLater > 4)	//Debugging
				throw new IllegalStateException("you should not be here... go away!");
			calcAndSetShipPause(ship);
			ship.setLoad(loadLater);
			if ((targetShip.getLoad() - (loadLater-shipLoad)) > 0)
				targetShip.getPosition().exchangeTreasure(targetShip.getLoad() - (loadLater-shipLoad)); // drop additional value to ground
			Field savedShipPosition = targetShip.getPosition();
			targetShip.changeMoral(-1);
			targetShip.changeCondition(-1);
			ship.getPosition().moveShip(savedShipPosition);
		}
		else{
			targetShip.changeMoral(-1);
			targetShip.changeCondition(-1);
			ship.setPC(elsePC);
		}
		
	}
	
	
	/**
	 * private helpmethod to do all the stuff when the ship which was moved on wins the fight. It checks if ships
	 * are actually destroyed, if new treasures are created, if the targetShip gets any additional treasure and how
	 * much falls on the ground of the field. targetShip.destroy will delete the ship afterwards if its out
	 * of condition (also out of his team, map ,maybe firstShip in map and the linkedList implemented via 
	 * firstShip pointer in map.
	 * 
	 * @param ship
	 * @param targetShip
	 */
	private void executeTargetShipWins(Ship ship, Ship targetShip){
		int shipCondition = ship.getCondition();
		int shipLoad = ship.getLoad();
		int targetLoad = targetShip.getLoad();
		
		if (shipCondition == 1){
			int spareRoom = 4-targetLoad;
			int loadLater = targetLoad;
			while ((spareRoom > 0)&&(shipLoad > 0)){
				shipLoad -= 1;
				spareRoom -= 1;
				loadLater += 1;
			}
			if (loadLater > 4)	//Debugging
				throw new IllegalStateException("you should not be here... go away!");
			targetShip.setLoad(loadLater);
			if ((shipLoad - loadLater) > 0)
				ship.getPosition().exchangeTreasure(shipLoad - loadLater); // drop additional value to ground
			ship.changeMoral(-1);
			ship.changeCondition(-1);
		}
		else{
			ship.changeMoral(-1);
			ship.changeCondition(-1);
			ship.setPC(elsePC);
		}
		
	}
	
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof Move)
		{
			Move other = (Move) o;
			if(other.elsePC == elsePC)
				return true;
		}
		return false;
	}

}
