package fuzzy.sistem.defuzzification;

import java.util.HashMap;

import fuzzy.terms.TermInterface;

public interface DefuzzificationInterface {
	
	public double defuzzify(HashMap<String, Double> mapAcc, HashMap<String, TermInterface> terms, String name, int numberOfSteps);

}
