package fuzzy.sistem.accumulation;

public class AccumulationMax implements AccumulationInterface {

	@Override
	public double evaluate(double x, double y) {
		return Math.max(x, y);
	}

}
