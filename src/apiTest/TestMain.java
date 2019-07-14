package apiTest;

import fuzzy.rules.LogicalOperators;
import fuzzy.rules.Rule;
import fuzzy.rules.RuleComposite;
import fuzzy.sistem.FuzzySistem;
import fuzzy.sistem.Variable;
import fuzzy.terms.TermDistribution;
import fuzzy.terms.TermFactory;
import fuzzy.terms.TermInterface;

public class TestMain {
	
	private static final String regFloat = "[0-9]*\\.?[0-9]*";
	private static final String regPercent = "(1|0\\.?[0-9]*)";
	private static final String regPoitArr = "(\\("+regFloat+","+regPercent+"\\) *)+";

	public static void main(String[] args) {
		
		// pravljenje fuzzy sistema
		FuzzySistem fuzzySistem = new FuzzySistem();
		
		// pravljenje variabla za fuzzy sistem i dodadvanje u sistem
		Variable service = new Variable("service");
		Variable food = new Variable("food");
		fuzzySistem.addVar(service);
		fuzzySistem.addVar(food);		
		
		// pravljenje termina i pirpajanje terminima
		TermInterface service_poor = new TermFactory().createTerm(TermDistribution.LINEAR, "poor", "(0,1) (4,0)");
		TermInterface service_good = new TermFactory().createTerm(TermDistribution.LINEAR, "good", "(1,0) (4,1) (6,1) (9,0)");
		TermInterface service_excellent = new TermFactory().createTerm(TermDistribution.LINEAR, "excellent", "(6,0) (9,1)");
		service.addTerm(service_poor);
		service.addTerm(service_good);
		service.addTerm(service_excellent);
		
		TermInterface food_rancid = new TermFactory().createTerm(TermDistribution.LINEAR, "rancid", "(0,1) (1,1) (3,0)");
		TermInterface food_delicious = new TermFactory().createTerm(TermDistribution.LINEAR, "delicious", "(7,0) (9,1)");
		food.addTerm(food_rancid);
		food.addTerm(food_delicious);
		
		// pravljenje pravila i njihovo dodavanje u fuzzy sistem		
		Rule rule1_1 = new Rule("service", "poor");
		Rule rule1_2 = new Rule("food", "rancid");
		RuleComposite composite1 = new RuleComposite(rule1_1, rule1_2, LogicalOperators.OR);
//		System.out.println("IF " + composite1.returnRule() + " THEN ");
//		System.out.println(service_poor.calculateTruth(9) + " - " + food_rancid.calculateTruth(8.5));
//		System.out.println();
		
		Rule rule2 = new Rule("service", "good");
//		System.out.println("IF " + rule2.returnRule() + " THEN ");
//		System.out.println(service_good.calculateTruth(9));
//		System.out.println();
		
		Rule rule3_1 = new Rule("service", "excellent");
		Rule rule3_2 = new Rule("food", "delicious");
		RuleComposite composite3 = new RuleComposite(rule3_1, rule3_2, LogicalOperators.AND);
//		System.out.println("IF " + composite3.returnRule() + " THEN ");
//		System.out.println(service_excellent.calculateTruth(9) + " - " + food_delicious.calculateTruth(8.5));
//		System.out.println();
		
		fuzzySistem.addRule(composite1);
		fuzzySistem.addRule(rule2);
		fuzzySistem.addRule(composite3);
		
		
		// ubacivanje pocetnih vrednosti i racunanje prema pravilima
		fuzzySistem.addInput("service", 9);
		fuzzySistem.addInput("food", 8.5);
		fuzzySistem.calculateRules();
		
		
		
		
		
		// dobri
		System.out.println("(12,0)".matches(regPoitArr));
		System.out.println("(13,1)".matches(regPoitArr));
		System.out.println("(13.2342,0.323)".matches(regPoitArr));
		System.out.println("(13.2342,0.323)(13,0.7)(1,1)(1000,0.3)".matches(regPoitArr));
		System.out.println("(13.2342,0.323) (13,0.7) (1,1)  (1000,0.3)".matches(regPoitArr));
		
		//losi
		System.out.println("(13,12)".matches(regPoitArr));
		System.out.println("1(13,1)".matches(regPoitArr));
		System.out.println("(13,0.23.)".matches(regPoitArr));
		System.out.println("(-323,0.1)".matches(regPoitArr));
		System.out.println("(13.2342,0.323) (13,0.7) (1,1)  (1000,0.3) (13)".matches(regPoitArr));		
	}

}
