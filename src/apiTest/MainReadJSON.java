package apiTest;

import fuzzy.readers.JsonReader;
import fuzzy.sistem.FuzzySistem;

public class MainReadJSON {
//	
//	private static final String regFloat = "[0-9]*\\.?[0-9]*";
//	private static final String regPercent = "(1|0\\.?[0-9]*)";
//	private static final String regPoitArr = "(\\("+regFloat+","+regPercent+"\\) *)+";
//	
//	private static final String T = "[A-Za-z0-9]+ +(IS|IS NOT) +[A-Za-z0-9]+";
//	private static final String CTT = T + " +(AND|OR) +" + T;
//	private static final String CCT = "(" + CTT + "|" + T + ")" + " +(AND|OR) +" + "(" + CTT + "|" + T + ")";
//	private static final String COMPLEX = "(" + CCT + "|" + CTT + "|" + T + ")" + " +(AND|OR) +" + "(" + CCT + "|" + CTT + "|" + T + ")";


	public static void main(String[] args) {
		
//		System.out.println("food IS rancid".matches(T));
//		System.out.println("food IS rancid AND service IS excellent".matches(CTT));
//		System.out.println("food IS rancid AND service IS excellent OR service IS bad".matches(CCT));
//		System.out.println("food IS rancid AND service IS excellent OR service IS bad OR service IS great".matches(CCT));
//		System.out.println("food IS rancid AND service IS excellent OR service IS bad OR service IS great AND food IS bad".matches(COMPLEX));
//		
//		System.out.println();
		
		FuzzySistem fs = new JsonReader().read("res/fuzzyTest.json");
		fs.addInput("povrsina", 51);
		fs.addInput("udaljenost_od_centra", 2.3);
		fs.calculateRules();
		
	}
}
