package fuzzy.rules;

import fuzzy.sistem.FuzzySistem;
import fuzzy.terms.TermInterface;

public class Rule implements RuleInterface {
	
	private String variable;
	private String value;
	
	public Rule(String variable, String value) {
		this.variable = variable;
		this.value = value;
	}
	
	@Override
	public double calculateTruth(FuzzySistem fs) {
		TermInterface term = fs.getVar(variable).getTerm(value);
		double input = fs.getInput(variable);
		return term.calculateTruth(input);
	}

	@Override
	public String returnRule(FuzzySistem fs) {
		return "(" + variable.toLowerCase() + " IS " + value.toLowerCase() + ")";
	}
}
