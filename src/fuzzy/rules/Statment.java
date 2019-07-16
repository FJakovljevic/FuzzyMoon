package fuzzy.rules;

import fuzzy.sistem.FuzzySistem;
import fuzzy.terms.TermInterface;

public class Statment implements StatmentInterface {
	
	private String variable;
	private String value;
	
	public Statment(String variable, String value) {
		this.variable = variable;
		this.value = value;
	}
	
	@Override
	public double calculateTruth(FuzzySistem fs) {
		TermInterface term = fs.getInputVar(variable).getTerm(value);
		double input = fs.getInput(variable);
		return term.calculateTruth(input);
	}

	@Override
	public String returnRule() {
		return "(" + variable.toLowerCase() + " IS " + value.toLowerCase() + ")";
	}
}
