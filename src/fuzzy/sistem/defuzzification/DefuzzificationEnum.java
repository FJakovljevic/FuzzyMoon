package fuzzy.sistem.defuzzification;

public enum DefuzzificationEnum {
	COG(new DefuzzifyCOG());
	
	private DefuzzificationInterface defuzz;

	private DefuzzificationEnum(DefuzzificationInterface defuzz) {
		this.defuzz = defuzz;
	}

	public DefuzzificationInterface getDefuzz() {
		return defuzz;
	}
}
