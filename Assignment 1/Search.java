import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Search {
	Problem problem;
	
	public Search(Problem problem) { 
		this.problem = problem; 
	}
	
	//Tree-search methods
	public String BreadthFirstTreeSearch() {
		return TreeSearch(new FrontierFIFO());
	}
	
	public String DepthFirstTreeSearch() {
		return TreeSearch(new FrontierLIFO());
	}
	
	public String UniformCostTreeSearch() {
		return TreeSearch(new FrontierPriorityQueue(new ComparatorG()));
	}	

	public String GreedyBestFirstTreeSearch() {
		return TreeSearch(new FrontierPriorityQueue(new ComparatorH(problem)));
	}
	
	public String AstarTreeSearch() {
		return TreeSearch(new FrontierPriorityQueue(new ComparatorF(problem)));
	}
	
	//Graph-search methods
	public String BreadthFirstGraphSearch() {
		return GraphSearch(new FrontierFIFO());
	}
	
	public String DepthFirstGraphSearch() {
		return GraphSearch(new FrontierLIFO());
	}
	
	public String UniformCostGraphSearch() {
		return GraphSearch(new FrontierPriorityQueue(new ComparatorG()));
	}	

	public String GreedyBestFirstGraphSearch() {
		return GraphSearch(new FrontierPriorityQueue(new ComparatorH(problem)));
	}
	
	public String AstarGraphSearch() {
		return GraphSearch(new FrontierPriorityQueue(new ComparatorF(problem)));
	}
	
	public String IterativeDeepeningTreeSearch() {
		String result;
		for (int i = 0; i < Double.POSITIVE_INFINITY; i++) {
			result = TreeSearchDepthLimited(new FrontierLIFO(), i);
			if (result != null) {
				return result;
			}
		}
		return null;		
	}
	
	public String IterativeDeepeningGraphSearch() {
		String result;
		for (int i = 0; i < Double.POSITIVE_INFINITY; i++) {
			result = GraphSearchDepthLimited(new FrontierLIFO(), i);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	
	//For statistics purposes
	int cnt; //count expansions
	List<Node> node_list; //store all nodes ever generated
	Node initialNode; //initial node based on initial state
	
	private String TreeSearch(Frontier frontier) {
		cnt = 0; 
		node_list = new ArrayList<Node>();
		
		initialNode = MakeNode(problem.initialState); 
		node_list.add( initialNode );
		
		frontier.insert( initialNode );
		while(true) {
			
			if(frontier.isEmpty())
				return null;
			
			Node node = frontier.remove();
			
			if( problem.goal_test(node.state) ) {
				return Solution(node);
			}
			
			frontier.insertAll(Expand(node,problem));
			cnt++;
		}
	}

	private String GraphSearch(Frontier frontier) {
		cnt = 0; 
		node_list = new ArrayList<Node>();
		
		initialNode = MakeNode(problem.initialState); 
		node_list.add( initialNode );
		
		Set<Object> explored = new HashSet<Object>(); //empty set
		frontier.insert( initialNode );
		while(true) {
			
			if(frontier.isEmpty())
				return null;
			
			Node node = frontier.remove();
			
			if( problem.goal_test(node.state) ) {
				return Solution(node);
			}
			
			if( !explored.contains(node.state) ) {
				explored.add(node.state);
				frontier.insertAll(Expand(node,problem));
				cnt++;
			}
		}
	}
	
	private String TreeSearchDepthLimited(Frontier frontier, int limit) {
		cnt = 0; 
		node_list = new ArrayList<Node>();
		
		// initialize the frontier using the initial state of problem
		initialNode = MakeNode(problem.initialState); 
		node_list.add( initialNode );
		frontier.insert( initialNode );
		
		while(true) {
			// if the frontier is empty then return failure
			if(frontier.isEmpty()) {
				return null;
			}
			
			// remove n from the frontier
			Node node = frontier.remove();
			
			// if n contains a goal state then return the corresponding solution
			if( problem.goal_test(node.state) ) {
				return Solution(node);
			}
			
			// if the depth of n is less than limit then
			if (node.depth < limit) {
				// expand n adding the resulting nodes to the frontier
				frontier.insertAll(Expand(node,problem));
			}		
			
			cnt++;
		}
	}

	private String GraphSearchDepthLimited(Frontier frontier, int limit) {
		cnt = 0; 
		node_list = new ArrayList<Node>();
		
		// initialize the frontier using the initial state of problem
		initialNode = MakeNode(problem.initialState); 		
		node_list.add( initialNode );
		// initialize the explored set to be empty
		Set<Object> explored = new HashSet<Object>(); //empty set
		frontier.insert( initialNode );
		
		while(true) {
			// if the frontier is empty then return failure
			if(frontier.isEmpty()) {
				return null;
			}
			
			// remove n from the frontier
			Node node = frontier.remove();
			
			// if n contains a goal state then return the corresponding solution
			if( problem.goal_test(node.state) ) {
				return Solution(node);
			}
			
			// if the state of n is not in explored and the depth of n is less than limit
			if( !explored.contains(node.state) && node.depth < limit ) {
				// add the state of n to explored
				explored.add(node.state);
				// expand n adding the resulting nodes to the frontier
				frontier.insertAll(Expand(node,problem));
				cnt++;
			}			
		}	
	}

	private Node MakeNode(Object state) {
		Node node = new Node();
		node.state = state;
		node.parent_node = null;
		node.path_cost = 0;
		node.depth = 0;
		return node;
	}
	
	private Set<Node> Expand(Node node, Problem problem) {
		node.order = cnt;
		
		Set<Node> successors = new HashSet<Node>(); //empty set
		Set<Object> successor_states = problem.getSuccessors(node.state);
		
		for(Object result : successor_states) {
			Node s = new Node();
			s.state = result;
			s.parent_node = node;
			s.path_cost = node.path_cost + problem.step_cost(node.state, result); 
			s.depth = node.depth + 1; 
			successors.add(s);
			
			node_list.add( s );
		}
		
		return successors;
	}
	
	void printTree(Node node) {
		
		for (int i = 0; i <= node.depth; i++) {
			System.out.print("\t");
		}
		
		System.out.print(node.state);
		System.out.print(" (g=" + (double)node.path_cost + ", h=" + (double)problem.h(node.state) + ", f=" + (double)(node.path_cost + problem.h(node.state)) + ")");
		if (node.order != -1) {
			System.out.print(" order=" + node.order);
		}
		System.out.println();
		
		for (Node m : node_list) {
			if (m.parent_node == node) {
				printTree(m);
			}
		}		
	}
	
	//Create a string to print solution. 
	private String Solution(Node node) {
		
		String solution_str = "(cost=" + node.path_cost + ", expansions=" + cnt + ")\t";
		
		Deque<Object> solution = new ArrayDeque<Object>();
		do {
			solution.push(node.state);
			node = node.parent_node;
		} while(node != null);
		
		while(!solution.isEmpty())
			solution_str += solution.pop() + " ";
		
		// Uncomment below line to print statistics for question # 2
		// printTree(initialNode);
		return solution_str;
	}
}
