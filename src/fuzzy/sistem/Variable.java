package fuzzy.sistem;

import java.util.HashMap;

import fuzzy.terms.TermInterface;

public class Variable {

	private String name;
	private HashMap<String, TermInterface> terms = new HashMap<String, TermInterface>();
	
	public Variable(String name) {
		this.name = name;
	}
	
	public void addTerm(TermInterface term) {
		terms.put(term.getName(), term);
	}
	
	public TermInterface getTerm(String term) {
		TermInterface ret = terms.get(term);
		if (ret == null)
			throw new IllegalArgumentException("Variable " + name.toUpperCase() + "has no term " + term.toUpperCase() + "!");
		return ret;
	}
	
	public String getName() {
		return name;
	}
	
}
