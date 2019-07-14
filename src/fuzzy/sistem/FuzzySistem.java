package fuzzy.sistem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fuzzy.rules.RuleInterface;

public class FuzzySistem {
	
	private HashMap<String, Variable> variables = new HashMap<String, Variable>();
	private HashMap<String, Double> inputs = new HashMap<String, Double>();
	private List<RuleInterface> rules = new ArrayList<RuleInterface>();
	
	public void addVar(Variable var) {
		variables.put(var.getName(), var);
	}
	
	public Variable getVar(String var) {
		Variable ret = variables.get(var);
		if (ret == null)
			throw new IllegalArgumentException("Fuzzy sistem has no variable " + var.toUpperCase() + " registered!");
		return ret;
	}
	
	public void addInput(Variable var, double input) {
		inputs.put(var.getName(), input);
	}
	
	public void addInput(String var, double input) {
		inputs.put(var, input);
	}
	
	public Double getInput(String var) {
		Double ret = inputs.get(var);
		if (ret == null)
			throw new IllegalArgumentException("Value for variable " + var.toUpperCase() + " is not defined!");
		return ret.doubleValue();
	}
	
	public void addRule(RuleInterface rule) {
		rules.add(rule);
	}
	
	public void calculateRules() {
		for (int i = 0; i < rules.size(); i++) {
			double result = rules.get(i).calculateTruth(this);
		    System.out.println("RULE " + (i+1) + ": IF " + rules.get(i).returnRule(this) + " THEN ---> RESULT: " + result);
		}
	}
	

}
