package fuzzy.readers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import fuzzy.rules.Consequence;
import fuzzy.rules.LogicalOperators;
import fuzzy.rules.Rule;
import fuzzy.rules.Statment;
import fuzzy.rules.StatmentComposite;
import fuzzy.rules.StatmentInterface;
import fuzzy.rules.StatmentNegation;
import fuzzy.sistem.FuzzySistem;
import fuzzy.sistem.Variable;
import fuzzy.terms.TermDistribution;
import fuzzy.terms.TermFactory;

public class JsonReader {

	public FuzzySistem read(String path) {

		try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream(new File("res/schema.json")));
			InputStream jsonInputStream = new BufferedInputStream(new FileInputStream(new File(path)));

			JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
			Schema schema = SchemaLoader.load(rawSchema);

			JSONObject input = new JSONObject(new JSONTokener(jsonInputStream));

			try {
				schema.validate(input);
			} catch (ValidationException e) {
				System.out.println(e.getAllMessages());
				return null;
			} 
			
			FuzzySistem fs = new FuzzySistem();
			TermFactory tf = new TermFactory();
			
			for (Object var : input.getJSONArray("variables")) {
				Variable variable = new Variable(((JSONObject) var).getString("name"));
				System.out.println(variable.getName());
				for (Object term : ((JSONObject) var).getJSONArray("terms")) {
					JSONObject x = (JSONObject) term;
					variable.addTerm(tf.createTerm(TermDistribution.valueOf(x.getString("function")), x.getString("name"), x.getString("data")) );
					System.out.println("\t" + x.getString("name"));
				}
				if (((JSONObject) var).get("var_type").equals("INPUT"))
					fs.addInputVar(variable);
				else
					fs.addOutputVar(variable);
				System.out.println();
			}
			
			for (Object r : input.getJSONArray("rules")) {
				JSONObject rule = (JSONObject) r;
				Rule javaRule = new Rule(createStatement(rule.getJSONObject("if")));
				
				for (Object t : rule.getJSONArray("then")) {
					JSONObject then = (JSONObject) t;
					javaRule.addConsequence(new Consequence(then.getString("var"), then.getString("value")));
				}
				fs.addRule(javaRule);
			}
			
			return fs;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private StatmentInterface createStatement(JSONObject st) { 
		
		
		
		if (!st.isNull("connector")) {
			StatmentInterface left = createStatement(st.getJSONObject("left"));
			StatmentInterface right = createStatement(st.getJSONObject("right"));
			
			if ( !st.isNull("negation") && st.getBoolean("negation"))
				return new StatmentNegation(new StatmentComposite(left, right, LogicalOperators.valueOf(st.getString("connector"))));
			else
				return new StatmentComposite(left, right, LogicalOperators.valueOf(st.getString("connector")));				
		}
			
		if ( !st.isNull("negation") && st.getBoolean("negation"))
			return new StatmentNegation(new Statment(st.getString("var"), st.getString("value")));
		else 
			return new Statment(st.getString("var"), st.getString("value"));
			
	}
}
