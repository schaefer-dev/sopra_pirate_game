package src.enums;

public enum Operator {
	Less("<"),
	Greater(">"),
	Equal("=="),
	Unequal("!=");
	
	private String op;
	
	private Operator(String op){
		this.op = op;
	}
	
	public String toString(){
		return op;
	}
}
