package fuzzy.rules;

import fuzzy.sistem.FuzzySistem;

public interface RuleInterface {

	public double calculateTruth(FuzzySistem fs);
	public String returnRule(FuzzySistem fs);
	
}
