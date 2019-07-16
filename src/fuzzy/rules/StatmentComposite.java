package fuzzy.rules;

import fuzzy.sistem.FuzzySistem;

public class StatmentComposite implements StatmentInterface {
	
	private StatmentInterface leftSide;
	private StatmentInterface rightSide;
	private LogicalOperators logicalOperator;
	
	public StatmentComposite(StatmentInterface leftSide, StatmentInterface rightSide, LogicalOperators logicalOperator) {
		this.leftSide = leftSide;
		this.rightSide = rightSide;
		this.logicalOperator = logicalOperator;
	}

	
	// TODO Napraviti da moze da se menja da nije samo MIN
	@Override
	public double calculateTruth(FuzzySistem fs) {
		return fs.resolveAggregation(logicalOperator, leftSide.calculateTruth(fs), rightSide.calculateTruth(fs));
	}

	@Override
	public String returnRule() {
		return "[" + leftSide.returnRule() + " " + logicalOperator.toString() + " " + rightSide.returnRule() + "]";
	}

}
