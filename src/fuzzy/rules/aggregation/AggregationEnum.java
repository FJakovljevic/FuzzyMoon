package fuzzy.rules.aggregation;

public enum AggregationEnum {
	MIN(new AggregationMIN(), new AggregationMAX()), 
	MAX(new AggregationMAX(), new AggregationMIN());
	
	private AggregationFuncInterface func;
	private AggregationFuncInterface inverse;
	
	private AggregationEnum(AggregationFuncInterface func, AggregationFuncInterface inverse) {
		this.func = func;
		this.inverse = inverse;
	}

	public AggregationFuncInterface getFunc() {
		return func;
	}

	public AggregationFuncInterface getInverse() {
		return inverse;
	}
}
