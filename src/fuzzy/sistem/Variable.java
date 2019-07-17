package fuzzy.sistem;

import java.util.HashMap;

import fuzzy.sistem.defuzzification.DefuzzificationEnum;
import fuzzy.sistem.defuzzification.DefuzzificationInterface;
import fuzzy.sistem.defuzzification.DefuzzifyCOG;
import fuzzy.terms.TermInterface;

public class Variable {

	private String name;
	private HashMap<String, TermInterface> terms = new HashMap<String, TermInterface>();
	private DefuzzificationInterface defuzzy = new DefuzzifyCOG();
	private int numberOfSteps = 1000;
	
	public Variable(String name) {
		this.name = name;
	}
	
	public Variable(String name, DefuzzificationEnum defuzzy) {
		this.name = name;
		this.defuzzy = defuzzy.getDefuzz();
	}
	
	public Variable(String name, DefuzzificationEnum defuzzy, int numberOfSteps) {
		this.name = name;
		this.defuzzy = defuzzy.getDefuzz();
		this.numberOfSteps = numberOfSteps;
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
		return defuzzy.defuzzify(mapAcc, terms, name, numberOfSteps);
	}
	
}
