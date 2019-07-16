package apiTest;

import fuzzy.rules.Consequence;
import fuzzy.rules.LogicalOperators;
import fuzzy.rules.Rule;
import fuzzy.rules.Statment;
import fuzzy.rules.StatmentComposite;
import fuzzy.rules.StatmentNegation;
import fuzzy.rules.aggregation.AggregationEnum;
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
		Variable tip = new Variable("tip");
		fuzzySistem.addInputVar(service);
		fuzzySistem.addInputVar(food);	
		fuzzySistem.addOutputVar(tip);
		
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
		
		TermInterface tip_cheap = new TermFactory().createTerm(TermDistribution.LINEAR, "cheap", "(0,0) (5,1) (10,0)");
		TermInterface tip_avrage = new TermFactory().createTerm(TermDistribution.LINEAR, "avrage", "(10,0) (15,1) (20,0)");
		TermInterface tip_generous = new TermFactory().createTerm(TermDistribution.LINEAR, "generous", "(20,0) (25,1) (30,0)");
		tip.addTerm(tip_cheap);
		tip.addTerm(tip_avrage);
		tip.addTerm(tip_generous);
		
		
		// pravljenje pravila i njihovo dodavanje u fuzzy sistem		
		Statment rule1_1 = new Statment("service", "poor");
		Statment rule1_2 = new Statment("food", "rancid");
		StatmentComposite composite1 = new StatmentComposite(rule1_1, rule1_2, LogicalOperators.OR);
		Consequence con1 = new Consequence("tip", "cheap");
		Rule rule1 = new Rule(composite1);
		rule1.addConsequence(con1);
		fuzzySistem.addRule(rule1);

		
		Statment rule2_1 = new Statment("service", "good");
		Consequence con2 = new Consequence("tip", "avrage");
		Rule rule2 = new Rule(rule2_1);
		rule2.addConsequence(con2);
		fuzzySistem.addRule(rule2);

		
		Statment rule3_1 = new Statment("service", "excellent");
		Statment rule3_2 = new Statment("food", "delicious");
		StatmentComposite composite3 = new StatmentComposite(rule3_1, rule3_2, LogicalOperators.AND);
		Consequence con3 = new Consequence("tip", "generous");
		Rule rule3 = new Rule(composite3);
		rule3.addConsequence(con3);
		fuzzySistem.addRule(rule3);
		
		Statment rule4_1 = new Statment("service", "poor");
		Statment rule4_2 = new Statment("food", "delicious");
		StatmentComposite composite4_1 = new StatmentComposite(rule4_1, rule4_2, LogicalOperators.AND);
		Statment rule4_3 = new Statment("service", "excellent");
		Statment rule4_4 = new Statment("food", "rancid");
		StatmentComposite composite4_2 = new StatmentComposite(rule4_3, rule4_4, LogicalOperators.AND);
		StatmentComposite composite4 = new StatmentComposite(composite4_1, composite4_2, LogicalOperators.OR);
		Consequence con4 = new Consequence("tip", "avrage");
		Rule rule4 = new Rule(composite4);
		rule4.addConsequence(con4);
		fuzzySistem.addRule(rule4);
		
		StatmentNegation rule5_1 = new StatmentNegation(new Statment("service", "poor"));
		StatmentNegation rule5_2 = new StatmentNegation(new Statment("food", "rancid"));
		StatmentComposite composite5 = new StatmentComposite(rule5_1, rule5_2, LogicalOperators.AND);
		Consequence con5_1 = new Consequence("tip", "avrage");
		Consequence con5_2 = new Consequence("tip", "generous");
		Rule rule5 = new Rule(composite5);
		rule5.addConsequence(con5_1);
		rule5.addConsequence(con5_2);
		fuzzySistem.addRule(rule5);
		
		
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
