package fuzzy.rules;

import fuzzy.sistem.FuzzySistem;

public class StatmentNegation implements StatmentInterface {
	
	private StatmentInterface rule;
	
	public StatmentNegation(StatmentInterface rule) {
		this.rule = rule;
	}
	
	@Override
	public double calculateTruth(FuzzySistem fs) {
		return 1 - rule.calculateTruth(fs);
	}

	@Override
	public String returnRule() {
		return "NOT [" + rule.returnRule() + "]";
	}

}
