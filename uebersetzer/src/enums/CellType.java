package enums;

public enum CellType {
	Empty("empty"),
	Island("island"),
	Home("home"),
	EnemyHome("enemyhome"),
	Undefined("doesntMatter");
	
	private String name;
	
	private CellType(String name){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
}
