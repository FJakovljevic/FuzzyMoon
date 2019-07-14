package fuzzy.terms;

public class TermFactory {
	
	public TermInterface createTerm(TermDistribution function, String name, String data) {
		
		switch (function) {
		case LINEAR: 
			return new TermLinear(name, data);
		default:
			return null;
		}
	}
}
