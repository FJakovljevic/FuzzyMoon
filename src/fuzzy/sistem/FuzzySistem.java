package fuzzy.sistem;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fuzzy.rules.Consequence;
import fuzzy.rules.LogicalOperators;
import fuzzy.rules.Rule;
import fuzzy.rules.aggregation.AggregationEnum;
import fuzzy.rules.aggregation.AggregationFuncInterface;
import fuzzy.sistem.accumulation.AccumulationInterface;
import fuzzy.sistem.accumulation.AccumulationMax;

public class FuzzySistem {

	private HashMap<String, Variable> inVariables = new HashMap<String, Variable>();
	private HashMap<String, Double> inputs = new HashMap<String, Double>();
	private HashMap<String, Variable> outVariables = new HashMap<String, Variable>();
	private HashMap<String, Double> outputs = new HashMap<String, Double>();

	private List<Rule> rules = new ArrayList<Rule>();

	private HashMap<LogicalOperators, AggregationFuncInterface> aggregation = new HashMap<LogicalOperators, AggregationFuncInterface>();
	private AccumulationInterface accumulationMethod = new AccumulationMax();

	public FuzzySistem() {
		changeLogicalOperation(LogicalOperators.AND, AggregationEnum.MIN);
	}

	public void changeLogicalOperation(LogicalOperators op, AggregationEnum func) {
		if (op.equals(LogicalOperators.AND)) {
			aggregation.put(LogicalOperators.AND, func.getFunc());
			aggregation.put(LogicalOperators.OR, func.getInverse());
			return;
		}
		
		aggregation.put(LogicalOperators.AND, func.getInverse());
		aggregation.put(LogicalOperators.OR, func.getFunc());
	}
	
	public void changeLogicalOperation(LogicalOperators op, AggregationFuncInterface func) {
		aggregation.put(op, func);
	}

	public void addInputVar(Variable var) {
		inVariables.put(var.getName(), var);
	}

	public Variable getInputVar(String var) {
		Variable ret = inVariables.get(var);
		if (ret == null)
			throw new IllegalArgumentException(
					"Fuzzy sistem has no input variable " + var.toUpperCase() + " registered!");
		return ret;
	}

	public void addOutputVar(Variable var) {
		outVariables.put(var.getName(), var);
	}

	public Variable getOutputVar(String var) {
		Variable ret = outVariables.get(var);
		if (ret == null)
			throw new IllegalArgumentException(
					"Fuzzy sistem has no output variable " + var.toUpperCase() + " registered!");
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

	public void addRule(Rule rule) {
		rules.add(rule);
	}

	public void calculateRules() {
		HashMap<String, Double> varTerm = new HashMap<String, Double>();
		
		System.out.println("Aggregation of rules:");
		for (int i = 0; i < rules.size(); i++) {
			System.out.println("\t RULE " + (i + 1) + ":" + rules.get(i).returnRuleCalculated(this));
			
			for (Consequence con : rules.get(i).getCalculatedConsequence(this)) {
				String varTermName = con.getVariable() + "-" + con.getValue();
				if (varTerm.containsKey(varTermName))
					varTerm.put(varTermName, accumulationMethod.evaluate(varTerm.get(varTermName), con.getSupport()));
				else
					varTerm.put(varTermName, con.getSupport());
			}
			
		}
		
		System.out.println("Accumulation of rules:");
		for (String key : varTerm.keySet()) {
			System.out.println("\t " + key + ": " + varTerm.get(key));
		}
		
		System.out.println("Defuzzification:");
		for (Variable var : outVariables.values()) {
			var.defuzzify(varTerm);
		}
		
		
	}

	public double resolveAggregation(LogicalOperators operation, double x, double y) {
		return aggregation.get(operation).evaluate(x, y);
	}

}
