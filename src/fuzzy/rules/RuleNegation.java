package fuzzy.rules;

import fuzzy.sistem.FuzzySistem;

public class RuleNegation implements RuleInterface {
	
	private RuleInterface rule;
	
	public RuleNegation(RuleInterface rule) {
		this.rule = rule;
	}
	
	@Override
	public double calculateTruth(FuzzySistem fs) {
		return 1 - rule.calculateTruth(fs);
	}

	@Override
	public String returnRule(FuzzySistem fs) {
		return "NOT [" + rule.returnRule(fs) + "]";
	}

}
