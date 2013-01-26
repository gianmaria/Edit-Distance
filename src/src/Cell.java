package src;

public class Cell {
	
	public static final int MAX = 3; //max number of indications: Top, Left and Diagonal
	
	private int value;
	private char[] direction;
	private char[] edit_operation;
	private int choices;
	
	public Cell(){
		value=0;
		direction = new char[MAX];
		edit_operation = new char[MAX];
		choices=0;
		
		for(int i=0; i<MAX; i++){
			direction[i]='0';
			edit_operation[i]='0';
		}
	}
	
	public void setDirection(char dir) {
		direction[choices]=dir;
	}
	
	public char getDirection(int index) {
		return direction[index];
	}
	
	public void setEditOperation(char edit_op) {
		edit_operation[choices]=edit_op;
		++choices;
	}
	
	public char getEditOperation(int index) {
		return edit_operation[index];
	}
	
	public int getMaxDirections() {
		return choices;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int val) {
		value=val;
	}

}
