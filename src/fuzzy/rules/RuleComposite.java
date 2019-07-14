package fuzzy.rules;

import fuzzy.sistem.FuzzySistem;

public class RuleComposite implements RuleInterface {
	
	private RuleInterface leftSide;
	private RuleInterface rightSide;
	private LogicalOperators logicalOperator;
	
	public RuleComposite(RuleInterface leftSide, RuleInterface rightSide, LogicalOperators logicalOperator) {
		this.leftSide = leftSide;
		this.rightSide = rightSide;
		this.logicalOperator = logicalOperator;
	}

	
	// TODO Napraviti da moze da se menja da nije samo MIN
	@Override
	public double calculateTruth(FuzzySistem fs) {
		return Math.min(leftSide.calculateTruth(fs), rightSide.calculateTruth(fs));
	}

	@Override
	public String returnRule(FuzzySistem fs) {
		return leftSide.returnRule(fs) + " " + logicalOperator.toString() + " " + rightSide.returnRule(fs);
	}

}
