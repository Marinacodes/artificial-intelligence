import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CSPZebraProblem extends CSP {
	
	static Set<Object> varCol = new HashSet<Object>(Arrays.asList(new String[] {"green", "ivory", "red", "yellow"}));
	static Set<Object> varBlu = new HashSet<Object>(Arrays.asList(new String[] {"blue"}));
	static Set<Object> varDri = new HashSet<Object>(Arrays.asList(new String[] {"coffee", "orange juice", "tea", "water"}));
	static Set<Object> varMil = new HashSet<Object>(Arrays.asList(new String[] {"milk"}));
	static Set<Object> varNat = new HashSet<Object>(Arrays.asList(new String[] {"Englishman", "Japanese", "Spaniard", "Ukrainian"}));
	static Set<Object> varNor = new HashSet<Object>(Arrays.asList(new String[] {"Norwegian"}));
	static Set<Object> varPet = new HashSet<Object>(Arrays.asList(new String[] {"dog", "fox", "horse", "snails", "zebra"}));
	static Set<Object> varCig = new HashSet<Object>(Arrays.asList(new String[] {"Chesterfield", "Kools", "Lucky Strike", "Old Gold", "Parliament"}));
	
	public boolean isGood(Object X, Object Y, Object x, Object y) {
		// If X is not even mentioned in the constraints, just return true
		// as nothing can be violated
		if(!C.containsKey(X))
			return true;
		
		// Check to see if there is an arc between X and Y
		// If there isn't an arc, then no constraint, i.e. it is good
		if(!C.get(X).contains(Y))
			return true;
		
		// The Englishman lives in the red house
		if(X.equals("Englishman") && Y.equals("red") && !x.equals(y))
			return false;
		
		// The Spaniard owns a dog
		if(X.equals("Spaniard") && Y.equals("dog") && !x.equals(y))
			return false;
		
		// The coffee is drunk in the green house
		if(X.equals("coffee") && Y.equals("green") && !x.equals(y))
			return false;
		
		// The Ukrainian drinks tea
		if(X.equals("Ukrainian") && Y.equals("tea") && !x.equals(y))
			return false;
		
		// The Old Gold smoker owns snails
		if(X.equals("Old Gold") && Y.equals("snails") && !x.equals(y))
			return false;
		
		// Kools are being smoked in the yellow house
		if(X.equals("Kools") && Y.equals("yellow") && !x.equals(y))
			return false;
		
		// The Lucky Strike smoker drinks orange juice
		if(X.equals("Lucky Strike") && Y.equals("orange juice") && !x.equals(y))
			return false;
		
		// The Japanese smokes Parliament
		if(X.equals("Japanese") && Y.equals("Parliament") && !x.equals(y))
			return false;		
		
		// The green house is directly to the right of the ivory house
		if(X.equals("green") && Y.equals("ivory") && (int)x != (int)y + 1)
			return false;
		
		// The Chesterfield smoker lives next to the fox owner
		if(X.equals("Chesterfield") && Y.equals("fox") && ((int)x != (int)y + 1 || (int)x != (int)y - 1 || (int)y != (int)x + 1 || (int)y != (int)x - 1))
			return false;		
		
		// Kools are smoked in the house next to the house where the horse is kept
		if(X.equals("Kools") && Y.equals("horse") && ((int)x != (int)y + 1 || (int)x != (int)y - 1 || (int)y != (int)x + 1 || (int)y != (int)x - 1))
			return false;
			
		// Uniqueness constraints
		if(varCol.contains(X) && varCol.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;
		
		if(varDri.contains(X) && varDri.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;
		
		if(varNat.contains(X) && varNat.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;
		
		if(varPet.contains(X) && varPet.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;
		
		if(varCig.contains(X) && varCig.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;
		
		return true;
	}
		
	public static void main(String[] args) throws Exception {
		CSPZebraProblem csp = new CSPZebraProblem();
		
		Integer[] dom = {1, 2, 3, 4, 5};	
		Integer[] dom3 = {3};
		Integer[] domwo3 = {1, 2, 4, 5};
		Integer[] dom1 = {1};
		Integer[] domwo1 = {2, 3, 4, 5};
		Integer[] dom2 = {2};
		Integer[] domwo2 = {1, 3, 4, 5};		
		
		// Add domains
		for(Object X : varCol) 
			csp.addDomain(X, dom);
		
		// Unary constraint: milk is drunk in the middle house
		for(Object X : varDri)
			csp.addDomain(X, domwo3);
				
		for(Object X: varMil)
			csp.addDomain(X, dom3);
		
		// Unary constraint: The Norwegian lives in the first house on the left		
		for(Object X : varNat)
			csp.addDomain(X, domwo1);
				
		for(Object X: varNor)
			csp.addDomain(X, dom1);
		
		// Deduced constraint: blue house has to be 2nd since the Norwegian lives next to the blue house
		for(Object X : varCol)
			csp.addDomain(X, domwo2);
				
		for(Object X: varBlu)
			csp.addDomain(X, dom2);
		
		for(Object X : varPet)
			csp.addDomain(X, dom);
		
		for(Object X : varCig)
			csp.addDomain(X, dom);
		
		// Uniqueness constraints
		for(Object X : varCol)
			for(Object Y : varCol)
				csp.addBidirectionalArc(X,Y);
			
		for(Object X : varDri)
			for(Object Y : varDri)
				csp.addBidirectionalArc(X,Y);
			
		for(Object X : varNat)
			for(Object Y : varNat)
				csp.addBidirectionalArc(X,Y);
			
		for(Object X : varPet)
			for(Object Y : varPet)
				csp.addBidirectionalArc(X,Y);
			
		for(Object X : varCig)
			for(Object Y : varCig)
				csp.addBidirectionalArc(X,Y);
			
		// Binary constraints: add constraint arcs	
		csp.addBidirectionalArc("Englishman", "red");
		csp.addBidirectionalArc("Spaniard", "dog");
		csp.addBidirectionalArc("coffee", "green");
		csp.addBidirectionalArc("Ukrainian", "tea");
		csp.addBidirectionalArc("Old Gold", "snails");
		csp.addBidirectionalArc("Kools", "yellow");
		csp.addBidirectionalArc("Old Gold", "snails");		
		csp.addBidirectionalArc("Lucky Strike", "orange juice");
		csp.addBidirectionalArc("Japanese", "Parliament");		
		
		Search search = new Search(csp);
		System.out.println(search.BacktrackingSearch());
	}
}