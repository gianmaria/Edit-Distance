package test;

import src.EditDistance;

public class EditDistanceTest {

	public static void main(String[] args) {
		String s1="writers";
		String s2="vintner";
		
		if(args.length==2){
			s1 = args[0];
			s2 = args[1];
		}
		
		EditDistance d= new EditDistance(s1,s2);
		d.compute();
		
		d.print();
		System.out.println("");
		
		d.printDirections();
		System.out.println("");
		
//		String[] edit_op=d.getEditOperations();
//		System.out.println("Edit Transcript:");
//		for(String s : edit_op){
//			System.out.println(s);
//		}
		
		System.out.println("");
		d.printAlignment();
	}

}
