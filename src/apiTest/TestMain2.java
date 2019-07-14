package apiTest;

import fuzzy.rules.LogicalOperators;
import fuzzy.rules.Rule;
import fuzzy.rules.RuleComposite;
import fuzzy.sistem.FuzzySistem;
import fuzzy.sistem.Variable;
import fuzzy.terms.TermDistribution;
import fuzzy.terms.TermFactory;
import fuzzy.terms.TermInterface;

public class TestMain2 {
	
	private static final String regFloat = "[0-9]*\\.?[0-9]*";
	private static final String regPercent = "(1|0\\.?[0-9]*)";
	private static final String regPoitArr = "(\\("+regFloat+","+regPercent+"\\) *)+";

	public static void main(String[] args) {
		
		// pravljenje fuzzy sistema
		FuzzySistem fuzzySistem = new FuzzySistem();
		
		// pravljenje variabla za fuzzy sistem i dodadvanje u sistem
		Variable povrsina = new Variable("povrsina");
		Variable udaljenost_od_centra = new Variable("udaljenost_od_centra");
		fuzzySistem.addVar(povrsina);
		fuzzySistem.addVar(udaljenost_od_centra);		
		
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
		
		// pravljenje pravila i njihovo dodavanje u fuzzy sistem		
		Rule rule1 = new Rule("udaljenost_od_centra", "centar");
		Rule rule2 = new Rule("povrsina", "mala");
		Rule rule3 = new Rule("povrsina", "srednja");
		Rule rule4 = new Rule("povrsina", "velika");
		Rule rule5 = new Rule("udaljenost_od_centra", "periferija");
		
		fuzzySistem.addRule(rule1);
		fuzzySistem.addRule(rule2);
		fuzzySistem.addRule(rule3);
		fuzzySistem.addRule(rule4);
		fuzzySistem.addRule(rule5);
		
		
		// ubacivanje pocetnih vrednosti i racunanje prema pravilima
		fuzzySistem.addInput("povrsina", 51);
		fuzzySistem.addInput("udaljenost_od_centra", 2.1);
		fuzzySistem.calculateRules();
	}
}
