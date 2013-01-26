package src;

public class EditDistance {
	
	private Cell d[][];
	private String s1;
	private String s2;
	
	private int i; //rows
	private int j; //columns
	
	public EditDistance(String s1, String s2){
		this.s1 = s1;
		this.s2 = s2;
		
		i = s2.length()+1;
		j = s1.length()+1;
		
		d = new Cell[i][j];
		for(int r=0; r<i; ++r)
			for(int c=0; c<j; ++c)
				d[r][c]= new Cell();
	}

	public void compute() {
		setUp();
		fillD();
	}
	
	//fill the first row with number from 0 to s1.length
	//fill the first column with number from 0 to s2.length
	private void setUp(){
		
		d[0][0].setValue(0);
		
		for(int r=1; r<i; ++r){
			d[r][0].setValue(r);
			d[r][0].setDirection('T');
			d[r][0].setEditOperation('D');
		}
		
		for(int c=1; c<j; ++c){
			d[0][c].setValue(c);
			d[0][c].setDirection('L');
			d[0][c].setEditOperation('I');
		}	
	}
	
	private void fillD(){
		for(int r=1; r<i; ++r){
			for(int c=1; c<j; ++c){
				//recursive equation
				d[r][c].setValue(
						min3(d[r-1][c].getValue() + 1, 
							 d[r][c-1].getValue() + 1, 
							 d[r-1][c-1].getValue() + (s1.charAt(c-1)==s2.charAt(r-1) ? 0 : 1)
							 ));
				
				int current_value=d[r][c].getValue();
				
				//insert direction and edit operation
				int top=d[r-1][c].getValue() + 1;
				if(current_value==top){
					d[r][c].setDirection('T');
					d[r][c].setEditOperation('D');
				}
				
				int left=d[r][c-1].getValue() + 1;
				if(current_value==left){
					d[r][c].setDirection('L');
					d[r][c].setEditOperation('I');
				}
				
				int diagonal=d[r-1][c-1].getValue() + (s1.charAt(c-1)==s2.charAt(r-1) ? 0 : 1);
				if(current_value==diagonal){
					d[r][c].setDirection('D');
					d[r][c].setEditOperation( s1.charAt(c-1)==s2.charAt(r-1) ? 'M' : 'R' );
				}
			}
		}
	}
	
	public int min3(int a, int b, int c){
		return (a <= b) ? ( (a <= c) ? a : c ) : ( (b <= c) ? b : c ); 
	}
	
	//print the matrix with numbers inside
	public void print(){
		System.out.print("\n\t\t");
		for(int c=1; c<j; ++c){
			System.out.print(s1.charAt(c-1) + "\t");
		}
		System.out.print("\n\n\t");
		for(int r=0; r<i; ++r){
			if(r!=0) System.out.print(s2.charAt(r-1)+"\t");
			for(int c=0; c<j; ++c){
				System.out.print(d[r][c].getValue() + "\t");
			}
			System.out.println("\n");
		}
	}
	
	//print the matrix with directions inside
	public void printDirections(){
		System.out.print("\n\t\t");
		for(int c=1; c<j; ++c){
			System.out.print(s1.charAt(c-1) + "\t");
		}
		System.out.print("\n\n\t");
		for(int r=0; r<i; ++r){
			if(r!=0) System.out.print(s2.charAt(r-1)+"\t");
			if(r==0) System.out.print("0");
			for(int c=0; c<j; ++c){
				for(int index=0; index<d[r][c].getMaxDirections(); ++index){
					System.out.print(  convertToArrow(d[r][c].getDirection(index))  );
				}
				System.out.print("\t");
			}
			System.out.println("\n");
		}
	}
	
	private String convertToArrow(char direction){
		switch (direction){
		case 'T':
			return "\u2191"; //↑
		case 'L':
			return "\u2190"; //←
		case 'D':
			return "\u2196"; //↖
		}
		return null;
	}
	
	//return an array with all the edit transcript
	public String[] getEditOperations(){
		return editOperationsHelp(i-1,j-1,"").split(":");
	}
	
	//use recursion to get all the edit transcript
	private String editOperationsHelp(int i, int j, String s){
		String ret="";
		if(i==0 && j==0){
			ret = new StringBuilder(s).reverse() + ":";
		}else{
			int max=d[i][j].getMaxDirections();
			
			for(int index=0; index<max; ++index){
				char current_direction=d[i][j].getDirection(index);
				char current_edit_op=d[i][j].getEditOperation(index);
				
				if(current_direction=='T'){
					ret += editOperationsHelp(i-1, j, s + current_edit_op);
				}
				if(current_direction=='L'){
					ret += editOperationsHelp(i, j-1, s + current_edit_op);
				}
				if(current_direction=='D'){
					ret += editOperationsHelp(i-1, j-1, s + current_edit_op);
				}
			}
		}
		return ret;
	}
	
	//print all the alignments 
	public void printAlignment(){
		String[] edit_ops=getEditOperations();
		
		for(int i=0; i<edit_ops.length; ++i){
			StringBuilder sb1 = new StringBuilder();
			StringBuilder sb2 = new StringBuilder(); 
			for(int j=0, is1=0, is2=0; j<edit_ops[i].length(); ++j){
				switch(edit_ops[i].charAt(j)){
				case 'R':{
					sb1.append(s1.charAt(is1));
					sb2.append(s2.charAt(is2));
					++is1; ++is2;
					break;
				}
				case 'I':{
					sb1.append(s1.charAt(is1));
					sb2.append("_");
					++is1;
					break;
				}
				case 'M':{
					sb1.append(s1.charAt(is1));
					sb2.append(s2.charAt(is2));
					++is1; ++is2;
					break;
				}
				case 'D':{
					sb1.append("_");
					sb2.append(s2.charAt(is2));
					++is2;
					break;
				}
				}
			}
			System.out.println(edit_ops[i]);
			System.out.println(sb1);
			System.out.println(sb2+"\n");
		}
		
	}

}






