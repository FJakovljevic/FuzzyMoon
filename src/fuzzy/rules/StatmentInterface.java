package fuzzy.rules;

import fuzzy.sistem.FuzzySistem;

public interface StatmentInterface {

	public double calculateTruth(FuzzySistem fs);
	public String returnRule();
	
}
