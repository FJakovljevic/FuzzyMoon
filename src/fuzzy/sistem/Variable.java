package fuzzy.sistem;

import java.util.HashMap;

import fuzzy.terms.TermInterface;

public class Variable {

	private String name;
	private HashMap<String, TermInterface> terms = new HashMap<String, TermInterface>();
	
	public Variable(String name) {
		this.name = name;
	}
	
	public void addTerm(TermInterface term) {
		terms.put(term.getName(), term);
	}
	
	public TermInterface getTerm(String term) {
		TermInterface ret = terms.get(term);
		if (ret == null)
			throw new IllegalArgumentException("Variable " + name.toUpperCase() + "has no term " + term.toUpperCase() + "!");
		return ret;
	}
	
	public String getName() {
		return name;
	}
	
	public double defuzzify(HashMap<String, Double> mapAcc) {
		
		double upperFraction = 0;
		double botFraction = 0;
		for (String key : terms.keySet()) {
			Double accVal = mapAcc.get(name + "-" + key);
//			System.out.println(name + "-" + key + "\t" + accVal);
			if (accVal != null && accVal != 0) {
				TermInterface term = terms.get(key);
				int numberOfSteps = 1000;
				double step = term.returnStepSize(numberOfSteps);
				double x1 = term.returnFirstPoint();
//				System.out.println(x1 + "\t" + step);
				
				double top = 0;
				double bot = 0;
				for (int i = 0; i < numberOfSteps; i++) {
					double x = x1+step*i;
					double mass = term.calculateTruth(x);
					top += x*mass;
					bot += mass; 
				}
//				System.out.println(top/bot);
				upperFraction += (top/bot)*accVal;
				botFraction += accVal;
			}
		}
		
		System.out.println("\t " + name + " --> " + upperFraction/botFraction);
		
		return 0;
	}
	
}
