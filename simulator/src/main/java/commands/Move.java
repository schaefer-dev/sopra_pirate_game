package commands;

import model.Base;
import model.Field;
import model.Island;
import model.ProvisionIsland;
import model.Ship;
import model.Water;
import controller.Command;

public class Move implements Command {

	private int elsePC;
	
	public Move(int pc){
		this.elsePC = pc;
	}
	
	@Override
	public void execute(Ship ship) {
		// TODO Auto-generated method stub
		Field shipField = ship.getPosition();
		Field targetField = shipField.getNeigbour(ship.relativeToAbsoluteDirection(0));
		int load = ship.getLoad();
		
		switch(targetField.getFieldType()){
		
		case Water:
			Water targetWater=(Water)targetField;
			if (targetWater.getShip()==null){
				/* no ship found so ship can move + check for Kraken */
				shipField.moveShip(targetWater);
				if (targetWater.getKraken()!=null)
					ship.changeCondition(-1);	
			}
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
					}
				}
				
				if (shipMoral > targetMoral){
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
						ship.setLoad(loadLater);
						if ((targetLoad - loadLater) > 0)
							targetShip.getPosition().exchangeTreasure(targetLoad - loadLater); // drop additional value to ground
						Field savedShipPosition = ship.getPosition();
						ship.changeMoral(-1);
						ship.changeCondition(-1);
						targetShip.getPosition().moveShip(savedShipPosition); 	//no move because moved on ship won
					}
					else{
						ship.changeMoral(-1);
						ship.changeCondition(-1);
					}
				}
				
				if (shipMoral == targetMoral){
					
					if (targetCondition > shipCondition){
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
						}
					}
					
					if (shipCondition > targetCondition){
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
							ship.setLoad(loadLater);
							if ((targetLoad - loadLater) > 0)
								targetShip.getPosition().exchangeTreasure(targetLoad - loadLater); // drop additional value to ground
							Field savedShipPosition = ship.getPosition();
							ship.changeMoral(-1);
							ship.changeCondition(-1);
							targetShip.getPosition().moveShip(savedShipPosition); 	//no move because moved on ship won
						}
						else{
							ship.changeMoral(-1);
							ship.changeCondition(-1);
						}
					}
					
					if (shipCondition == targetCondition){
						int z = ship.getPosition().getMap().getRandom().nextInt(2);
						
						if (z == 0){// TargetShip wins
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
							}
						}
						
						if (z == 1){	// Ship wins
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
								ship.setLoad(loadLater);
								if ((targetLoad - loadLater) > 0)
									targetShip.getPosition().exchangeTreasure(targetLoad - loadLater); // drop additional value to ground
								Field savedShipPosition = ship.getPosition();
								ship.changeMoral(-1);
								ship.changeCondition(-1);
								targetShip.getPosition().moveShip(savedShipPosition); 	//no move because moved on ship won
							}
							else{
								ship.changeMoral(-1);
								ship.changeCondition(-1);
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
				ship.changeMoral(4);
			}
			else{
				ship.setPC(elsePC);	
			}
			break;
			
		case Island: 
			Island targetIsland=(Island)targetField;
			ship.changeCondition(-1);
			ship.changeMoral(-1);
			if (load >= 1){
				ship.setLoad(load-1);
				shipField.exchangeTreasure(+1);
			}		
			break;
			
		case ProvisionIsland:
			ProvisionIsland targetProvIsland=(ProvisionIsland)targetField;
			ship.changeCondition(-1);
			ship.changeMoral(-1);
			if (load >= 1){
				ship.setLoad(load-1);
				shipField.exchangeTreasure(+1);
			}		
			break;		
		}
	}
	
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
			ship.setLoad(loadLater);
			if ((targetLoad - loadLater) > 0)
				targetShip.getPosition().exchangeTreasure(targetLoad - loadLater); // drop additional value to ground
			Field savedShipPosition = ship.getPosition();
			ship.changeMoral(-1);
			ship.changeCondition(-1);
			targetShip.getPosition().moveShip(savedShipPosition); 	//no move because moved on ship won
		}
		else{
			ship.changeMoral(-1);
			ship.changeCondition(-1);
		}
		
	}
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
