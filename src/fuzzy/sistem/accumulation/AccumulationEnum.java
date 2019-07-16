package fuzzy.sistem.accumulation;

public enum AccumulationEnum { 
	MAX(new AccumulationMax());
	
	private AccumulationInterface func;
	
	private AccumulationEnum(AccumulationInterface func) {
		this.func = func;
	}

	public AccumulationInterface getFunc() {
		return func;
	}
}
