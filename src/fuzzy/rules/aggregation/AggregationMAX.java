package fuzzy.rules.aggregation;

public class AggregationMAX implements AggregationFuncInterface {

	@Override
	public double evaluate(double x, double y) {
		return Math.max(x, y);
	}

}
