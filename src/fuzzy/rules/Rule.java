package fuzzy.rules;

import java.util.ArrayList;
import java.util.List;

import fuzzy.sistem.FuzzySistem;

public class Rule {

	private StatmentInterface statment;
	private List<Consequence> consequence = new ArrayList<Consequence>();
	
	public Rule(StatmentInterface statment) {
		this.statment = statment;
	}

	public StatmentInterface getStatment() {
		return statment;
	}

	public List<Consequence> getConsequence() {
		return consequence;
	}

	public void addConsequence(Consequence consequence) {
		this.consequence.add(consequence);
	}
	
	public String returnRuleCalculated(FuzzySistem fs) {
		String res = "\t" + statment.calculateTruth(fs) + "%\t";
		String ifPart = "IF " + statment.returnRule() + " THEN ";
		String thenPart = "";
		for (Consequence con : consequence)
			thenPart += con.returnRule() + ", ";
		thenPart = thenPart.substring(0, thenPart.length()-2)+ ";";
		
		return res + ifPart + thenPart;
	}
	
	public List<Consequence> getCalculatedConsequence(FuzzySistem fs) {
		double support = statment.calculateTruth(fs);
		for (Consequence con : consequence)
			con.setSupport(support);
		
		return consequence;
	}
}
