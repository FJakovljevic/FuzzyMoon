package apiTest;

import fuzzy.readers.JsonReader;
import fuzzy.sistem.FuzzySistem;
import fuzzy.sistem.Variable;
import fuzzy.terms.TermDistribution;
import fuzzy.terms.TermFactory;
import fuzzy.terms.TermInterface;

public class TestMain2 {
//	
//	private static final String regFloat = "[0-9]*\\.?[0-9]*";
//	private static final String regPercent = "(1|0\\.?[0-9]*)";
//	private static final String regPoitArr = "(\\("+regFloat+","+regPercent+"\\) *)+";
	
	private static final String T = "[A-Za-z0-9]+ +(IS|IS NOT) +[A-Za-z0-9]+";
	private static final String CTT = T + " +(AND|OR) +" + T;
	private static final String CCT = "(" + CTT + "|" + T + ")" + " +(AND|OR) +" + "(" + CTT + "|" + T + ")";
	private static final String COMPLEX = "(" + CCT + "|" + CTT + "|" + T + ")" + " +(AND|OR) +" + "(" + CCT + "|" + CTT + "|" + T + ")";


	public static void main(String[] args) {
		
		// pravljenje fuzzy sistema
		FuzzySistem fuzzySistem = new FuzzySistem();
		
		// pravljenje variabla za fuzzy sistem i dodadvanje u sistem
		Variable povrsina = new Variable("povrsina");
		Variable udaljenost_od_centra = new Variable("udaljenost_od_centra");
		fuzzySistem.addInputVar(povrsina);
		fuzzySistem.addInputVar(udaljenost_od_centra);		
		
		// pravljenje termina i pirpajanje terminima
		TermInterface mala = new TermFactory().createTerm(TermDistribution.LINEAR, "mala", "(20,1) (30,1) (40,0)");
		TermInterface srednja = new TermFactory().createTerm(TermDistribution.LINEAR, "srednja", "(35,0) (50,1) (65,0)");
		TermInterface velika = new TermFactory().createTerm(TermDistribution.LINEAR, "velika", "(60,0) (70,1) (80,1)");
		povrsina.addTerm(mala);
		povrsina.addTerm(srednja);
		povrsina.addTerm(velika);
		
		TermInterface centar = new TermFactory().createTerm(TermDistribution.LINEAR, "centar", "(0,1) (2.5,0)");
		TermInterface periferija = new TermFactory().createTerm(TermDistribution.LINEAR, "periferija", "(2,0) (5,1) (10,1)");
		udaljenost_od_centra.addTerm(centar);
		udaljenost_od_centra.addTerm(periferija);
		

		
		System.out.println("food IS rancid".matches(T));
		System.out.println("food IS rancid AND service IS excellent".matches(CTT));
		System.out.println("food IS rancid AND service IS excellent OR service IS bad".matches(CCT));
		System.out.println("food IS rancid AND service IS excellent OR service IS bad OR service IS great".matches(CCT));
		System.out.println("food IS rancid AND service IS excellent OR service IS bad OR service IS great AND food IS bad".matches(COMPLEX));
		
		System.out.println();
		
		FuzzySistem fs = new JsonReader().read("res/fuzzyTest.json");
		fs.addInput("povrsina", 51);
		fs.addInput("udaljenost_od_centra", 2.3);
		fs.calculateRules();
		
	}
}
