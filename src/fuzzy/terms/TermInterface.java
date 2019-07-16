package fuzzy.terms;

public interface TermInterface {
	
	public double calculateTruth(double value);
	public double returnStepSize(int x);
	public double returnFirstPoint();
	public String getName();
}
