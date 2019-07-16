package fuzzy.rules;

public class Consequence {

	private String variable;
	private String value;
	private double support = 0;
	
	public Consequence(String variable, String value) {
		this.variable = variable;
		this.value = value;
	}

	public String getVariable() {
		return variable;
	}

	public String getValue() {
		return value;
	}
	
	public String returnRule() {
		return variable.toLowerCase() + " IS " + value.toLowerCase();
	}

	public double getSupport() {
		return support;
	}

	public void setSupport(double support) {
		this.support = support;
	}
}
