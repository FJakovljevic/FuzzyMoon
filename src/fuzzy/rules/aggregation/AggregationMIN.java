package fuzzy.rules.aggregation;

public class AggregationMIN implements AggregationFuncInterface {

	@Override
	public double evaluate(double x, double y) {
		return Math.min(x, y);
	}

}
