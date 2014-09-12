package model;

import controller.Command;

public class Ship {

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
		// TODO Auto-generated method stub
	}
	
	public void act(){
		// TODO Auto-generated method stub
	}
	
	public int getPC(){
		return this.pc;
	}
	
	public Team getTeam(){
		// TODO Auto-generated method stub
		return null;
	}
	
	public Field getPosition(){
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setField(Field field){
		// TODO Auto-generated method stub
	}
	
	public void setPC(int i){
		// TODO Auto-generated method stub
	}
	
	public Command getCommand(){
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getID(){
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int getShipDirection(){
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void changeDirection(boolean left){
		// TODO Auto-generated method stub
	}
	
	public int relativeToAbsoluteDirection(int relDirection){
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int getMoral(){
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void changeMoral(int i){
		// TODO Auto-generated method stub
	}
	
	public int getCondition(){
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void changeCondition(int i){
		// TODO Auto-generated method stub
	}
	
	private void setCondition(int i){
		// TODO Auto-generated method stub
	}
	
	public int getPause(){
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void changePause(int i){
		// TODO Auto-generated method stub
	}
	
	public int getLoad(){
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void setLoad(int i){
		// TODO Auto-generated method stub
	}
	
	public int getSenseRegister(Register reg){
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void setSenseRegister(Register reg, int value){
		// TODO Auto-generated method stub
	}
	
	public Ship getNextShip(){
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setNextShip(Ship next){
		// TODO Auto-generated method stub
	}
	
	private void destroy(){
		// TODO Auto-generated method stub
	}
	
	
}
