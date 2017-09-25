import java.util.HashSet;
import java.util.Set;

public class ProblemWaterJugs extends Problem {
	
    static final int threeGallonJug = 0;
    static final int eightGallonJug = 1;
    static final int twelveGallonJug = 2;
	
	static final int threeGallons = 3;
	static final int eightGallons = 8;
	static final int twelveGallons = 12;
    
	boolean goal_test(Object state) {
        StateWaterJugs jug_state = (StateWaterJugs) state;
        
        if (jug_state.jugsArray[threeGallonJug] == 1 || jug_state.jugsArray[eightGallonJug] == 1 || jug_state.jugsArray[twelveGallonJug] == 1) {
            return true;
		} else {
			return false;
		}
	}
  
    Set<Object> getSuccessors(Object state) {
    	
		Set<Object> set = new HashSet<Object>();
		StateWaterJugs jugs_state = (StateWaterJugs) state;
        
		//Let's create without any constraint, then remove the illegal ones
		StateWaterJugs successor_state;
		
		//fill 3 gallon jug
		successor_state = new StateWaterJugs(jugs_state);
		if (successor_state.jugsArray[threeGallonJug] < threeGallons)
			successor_state.jugsArray[threeGallonJug] = threeGallons;
		if (isValid(successor_state)) set.add(successor_state);
		
		//fill 8 gallon jug
		successor_state = new StateWaterJugs(jugs_state);
		if (successor_state.jugsArray[eightGallonJug] < eightGallons)
			successor_state.jugsArray[eightGallonJug] = eightGallons;
		if (isValid(successor_state)) set.add(successor_state);
		
		//fill 12 gallon jug
		successor_state = new StateWaterJugs(jugs_state);
		if (successor_state.jugsArray[twelveGallonJug] < twelveGallons)
			successor_state.jugsArray[twelveGallonJug] = twelveGallons;
		if (isValid(successor_state)) set.add(successor_state);
        
		//pour 3 gallons from 3 gallon jug onto the ground
		successor_state = new StateWaterJugs(jugs_state);
		if (successor_state.jugsArray[threeGallonJug] > 0)
			successor_state.jugsArray[threeGallonJug] = 0;
		if (isValid(successor_state)) set.add(successor_state);
		
		//pour 8 gallons from 8 gallon jug onto the ground
		successor_state = new StateWaterJugs(jugs_state);
		if (successor_state.jugsArray[threeGallonJug] > 8)
			successor_state.jugsArray[threeGallonJug] = 8;
		if (isValid(successor_state)) set.add(successor_state);
		
		//pour 12 gallons from 12 gallon jug onto the ground
		successor_state = new StateWaterJugs(jugs_state);
		if (successor_state.jugsArray[twelveGallonJug] > 0)
			successor_state.jugsArray[twelveGallonJug] = 0;
		if (isValid(successor_state)) set.add(successor_state);
		
		//pour from 3 gallon jug to 8 gallon jug until the former is empty or the latter is full
		successor_state = new StateWaterJugs(jugs_state);		
		while (true) {
			//System.out.println("while 1");
			successor_state.jugsArray[threeGallonJug]--;
			successor_state.jugsArray[eightGallonJug]++;
			if (successor_state.jugsArray[threeGallonJug] < 0 || successor_state.jugsArray[eightGallonJug] == eightGallons) {
				break;
			}
		}		
		if (isValid(successor_state)) set.add(successor_state);
		
		//pour from 3 gallon jug to 12 gallon jug until the former is empty or the latter is full
		successor_state = new StateWaterJugs(jugs_state);		
		while (true) {
			//System.out.println("while 2");
			successor_state.jugsArray[threeGallonJug]--;
			successor_state.jugsArray[twelveGallonJug]++;
			if (successor_state.jugsArray[threeGallonJug] < 0 || successor_state.jugsArray[twelveGallonJug] == twelveGallons) {
				break;
			}
		}		
		if (isValid(successor_state)) set.add(successor_state);
		
		//pour from 8 gallon jug to 3 gallon jug until the former is empty or the latter is full
		successor_state = new StateWaterJugs(jugs_state);		
		while (true) {
			//System.out.println("while 3");
			successor_state.jugsArray[eightGallonJug]--;
			successor_state.jugsArray[threeGallonJug]++;
			if (successor_state.jugsArray[eightGallonJug] < 0 || successor_state.jugsArray[threeGallonJug] == threeGallons) {
				break;
			}
		}		
		if (isValid(successor_state)) set.add(successor_state);
		
		//pour from 8 gallon jug to 12 gallon jug until the former is empty or the latter is full
		successor_state = new StateWaterJugs(jugs_state);		
		while (true) {
			//System.out.println("while 4");
			successor_state.jugsArray[eightGallonJug]--;
			successor_state.jugsArray[twelveGallonJug]++;
			if (successor_state.jugsArray[eightGallonJug] < 0 || successor_state.jugsArray[twelveGallonJug] == twelveGallons) {
				break;
			}
		}		
		if (isValid(successor_state)) set.add(successor_state);
		
		//pour from 12 gallon jug to 3 gallon jug until the former is empty or the latter is full
		successor_state = new StateWaterJugs(jugs_state);		
		while (true) {
			//System.out.println("while 5");
			successor_state.jugsArray[twelveGallonJug]--;
			successor_state.jugsArray[threeGallonJug]++;
			if (successor_state.jugsArray[twelveGallonJug] < 0 || successor_state.jugsArray[threeGallonJug] == threeGallons) {
				break;
			}
		}		
		if (isValid(successor_state)) set.add(successor_state);
		
		//pour from 12 gallon jug to 8 gallon jug until the former is empty or the latter is full
		successor_state = new StateWaterJugs(jugs_state);		
		while (true) {
			//System.out.println("while 6");
			successor_state.jugsArray[twelveGallonJug]--;
			successor_state.jugsArray[eightGallonJug]++;
			if (successor_state.jugsArray[twelveGallonJug] < 0 || successor_state.jugsArray[eightGallonJug] == eightGallons) {
				break;
			}
		}		
		if (isValid(successor_state)) set.add(successor_state);		
		
		return set;
    }
    
    private boolean isValid(StateWaterJugs state) {   
	
		//Checking to see if any element of the array is negative 
        for (int i = 0; i < 3; i++) {
            if (state.jugsArray[i] < 0) {
				return false;	
			}
		}
		
        //Checking to see if jug capacities are more than they should be 
		if (state.jugsArray[threeGallonJug] > threeGallons || state.jugsArray[eightGallonJug] > eightGallons || state.jugsArray[twelveGallonJug] > twelveGallons) {
			return false;
		}  
		
		return true;
    }
	
	double step_cost(Object fromState, Object toState) { 
		return 1; 
	}

	public double h(Object state) {
		return 0; 
	}

	public static void main(String[] args) throws Exception {
		ProblemWaterJugs problem = new ProblemWaterJugs();
		int[] jugsArray = {0, 0, 0};
		problem.initialState = new StateWaterJugs(jugsArray); 
		
		Search search  = new Search(problem);
		
		System.out.println("BreadthFirstTreeSearch:\t\t" + search.BreadthFirstTreeSearch());
		System.out.println("BreadthFirstGraphSearch:\t" + search.BreadthFirstGraphSearch());
		
		//System.out.println("DepthFirstTreeSearch:\t\t" + search.DepthFirstTreeSearch());		
		System.out.println("DepthFirstGraphSearch:\t\t" + search.DepthFirstGraphSearch());
		
		System.out.println("UniformCostTreeSearch:\t\t" + search.UniformCostTreeSearch());		
		System.out.println("UniformCostGraphSearch):\t" + search.UniformCostGraphSearch());
		
		//System.out.println("GreedyBestFirstTreeSearch:\t" + search.GreedyBestFirstTreeSearch());		
		System.out.println("GreedyBestFirstGraphSearch:\t" + search.GreedyBestFirstGraphSearch());
		
		System.out.println("AstarTreeSearch:\t\t" + search.AstarTreeSearch());		
		System.out.println("AstarGraphSearch:\t\t" + search.AstarGraphSearch());
		
		System.out.println("IterativeDeepeningTreeSearch:\t" + search.IterativeDeepeningTreeSearch());		
		System.out.println("IterativeDeepeningGraphSearch:\t" + search.IterativeDeepeningGraphSearch());
	}
}
