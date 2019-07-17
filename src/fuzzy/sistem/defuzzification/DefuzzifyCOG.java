package fuzzy.sistem.defuzzification;

import java.util.HashMap;

import fuzzy.terms.TermInterface;

public class DefuzzifyCOG implements DefuzzificationInterface {

	@Override
	public double defuzzify(HashMap<String, Double> mapAcc, HashMap<String, TermInterface> terms, String name, int numberOfSteps) {
		
		double upperFraction = 0;
		double botFraction = 0;
		for (String key : terms.keySet()) {
			Double accVal = mapAcc.get(name + "-" + key);
			if (accVal != null && accVal != 0) {
				TermInterface term = terms.get(key);
				double step = term.returnStepSize(numberOfSteps);
				double x1 = term.returnFirstPoint();

				double top = 0;
				double bot = 0;
				for (int i = 0; i < numberOfSteps; i++) {
					double x = x1 + step * i;
					double mass = term.calculateTruth(x);
					top += x * mass;
					bot += mass;
				}
				upperFraction += (top / bot) * accVal;
				botFraction += accVal;
			}
		}

		return upperFraction/botFraction;
	}

}
