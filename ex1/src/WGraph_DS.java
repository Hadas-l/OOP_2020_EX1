package ex1.src;

import java.io.Serializable;
import java.util.*;

public class WGraph_DS implements weighted_graph, Serializable {
		
	private static final long serialVersionUID = 1L;
	
	private HashMap<Integer, node_info> vertexes; 
	private HashMap<Integer, HashMap<Integer, Double>> weights;
	private HashMap<Integer, List<node_info>> neighbors;
	
	private int mc;
	private int ec;
	
	public class node implements node_info, Comparable<node_info>, Serializable  {
		
		private static final long serialVersionUID = 1L;
		private int key;
		private String info;
		private Double tag;
		
		public node(int key) {
			// TODO Auto-generated constructor stub
			this.key = key;
			this.info = "";
			this.tag = 0.0;
		}
		
	    /* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			//result = prime * result + getOuterType().hashCode();
			return Objects.hash(info, key, tag);
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof node)) {
				return false;
			}
			node other = (node) obj;
			return Objects.equals(info, other.info) && key == other.key && Objects.equals(tag, other.tag);
		}

		/**
	     * Return the key (id) associated with this node.
	     * Note: each node_data should have a unique key.
	     * @return
	     */
		@Override
		public int getKey() {
			return this.key;
		}

	    /**
	     * return the remark (meta data) associated with this node.
	     * @return
	     */
		@Override
		public String getInfo() {
			return info;
		}

	    /**
	     * Allows changing the remark (meta data) associated with this node.
	     * @param s
	     */
		@Override
		public void setInfo(String s) {
			this.info = s;
		}

	    /**
	     * Temporal data (aka distance, color, or state)
	     * which can be used be algorithms
	     * @return
	     */
		@Override
		public double getTag() {
			return this.tag;
		}

	    /**
	     * Allow setting the "tag" value for temporal marking an node - common
	     * practice for marking by algorithms.
	     * @param t - the new value of the tag
	     */
		@Override
		public void setTag(double t) {
			this.tag = t;
		}

		@Override
		public String toString() {
			return "";
		}

		@Override
		public int compareTo(node_info o) {
			Double w1 = this.getTag();
			Double w2 = o.getTag();
			
			return w1 > w2 ? 1 : w1 < w2 ? -1 : 0;

		}
		
	}

	public WGraph_DS() {
		this.vertexes = new HashMap<>();
		this.weights = new HashMap<>();
		this.neighbors = new HashMap<>();
		
		this.ec = 0;
		this.mc = 0;
	}
	
    /**
     * return the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
	@Override
	public node_info getNode(int key) {
		return this.vertexes.get(key); 
	}

	
    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(ec, mc, vertexes, weights);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			
			return false;
		}
		if (!(obj instanceof weighted_graph)) {
			
			return false;
		}
		
		WGraph_DS other = (WGraph_DS) obj;
		return ec == (other.ec) && mc == other.mc && Objects.equals(vertexes, other.vertexes)
			&& Objects.equals(weights, other.weights) && Objects.equals(neighbors, other.neighbors);
	}

	/**
     * return true iff (if and only if) there is an edge between node1 and node2
     * Note: this method should run in O(1) time.
     * @param node1
     * @param node2
     * @return
     */
	@Override
	public boolean hasEdge(int node1, int node2) {
		
		if (this.vertexes.containsKey(node1) && this.vertexes.containsKey(node2)) { //o(1) contains		
			if (node1 != node2) {
				
				if (this.vertexes.get(node1) != null && this.vertexes.get(node2) != null)
					
					return this.neighbors.get(node1).contains(getNode(node2));
			}
		}
		
		return false;
	}

    /**
     * return the weight if the edge (node1, node2) exist. In case
     * there is no such edge - should return -1
     * Note: this method should run in O(1) time.
     * @param node1
     * @param node2
     * @return
     */
	@Override
	public double getEdge(int node1, int node2) {
		
		if (this.vertexes.containsKey(node1) && this.vertexes.containsKey(node2)) { //o(1) contains		
			if (node1 != node2) //same node
				if (this.vertexes.get(node1) != null || this.vertexes.get(node2) != null) {
					
					if (hasEdge(node1, node2)) {
						
						this.mc++;
						
						return this.weights.get(node1).get(node2);
						
					}
					
				}
			
		}
		
		return -1;
	}

    /**
     * add a ****new node**** to the graph with the given key.
     * Note: this method should run in O(1) time.
     * Note2: if there is already a node with such a key -> no action should be performed.
     * @param key
     */
	@Override
	public void addNode(int key) {
		if (!this.vertexes.containsKey(key)) {
			

			this.vertexes.put(key, new node(key));
			this.neighbors.put(key, new ArrayList<node_info>());
			this.weights.put(key, new HashMap<Integer, Double>());
			
			this.mc++;
		}
	}
	
	
    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * Note: this method should run in O(1) time.
     * Note2: if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     */
	@Override
	public void connect(int node1, int node2, double w) {
		if (w >= 0) {
			
			if (this.vertexes.containsKey(node1) && this.vertexes.containsKey(node2)) { 	
				if (node1 != node2) { 
					if (this.vertexes.get(node1) != null && this.vertexes.get(node2) != null) {
						
						if (!hasEdge(node1, node2) && !hasEdge(node2, node1)) { 
							
							this.weights.get(node1).put(node2, w);
							this.weights.get(node2).put(node1, w); 
							
							this.neighbors.get(node1).add(this.vertexes.get(node2));
							this.neighbors.get(node2).add(this.vertexes.get(node1)); 
							
							this.ec++; 
							this.mc++;
							
						}
						else { 
							
							this.weights.get(node1).put(node2, w);
							this.weights.get(node2).put(node1, w); 
							
						}
						
					}
				}
			}
			
		}
	}

    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * Note: this method should run in O(1) tim
     * @return Collection<node_data>
     */
	@Override
	public Collection<node_info> getV() {
		return this.vertexes.values();
	}

    /**
    *
    * This method returns a Collection containing all the
    * nodes connected to node_id
    * Note: this method can run in O(k) time, k - being the degree of node_id.
    * @return Collection<node_info>
    */
	@Override
	public Collection<node_info> getV(int node_id) {
		
		return this.neighbors.get(node_id); // o(1)
		
	}

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(n), |V|=n, as all the edges should be removed.
     * @return the data of the removed node (null if none).
     * @param key
     */
	@Override
	public node_info removeNode(int key) {
		
		if (this.vertexes.containsKey(key)) {
			
			for (node_info neighbor : getV(key)) {
		
				this.neighbors.get(neighbor.getKey()).remove(getNode(key));
				this.weights.get(neighbor.getKey()).remove(key);
				this.ec--;
				this.mc++;
				
			}

			this.weights.remove(key);
			this.neighbors.remove(key);
			
			return this.vertexes.remove(key);
			
		}
		
		return null;
	}

    /**
     * Delete the edge from the graph,
     * Note: this method should run in O(1) time.
     * @param node1
     * @param node2
     */
	@Override
	public void removeEdge(int node1, int node2) {
		
		if (this.vertexes.containsKey(node1) && this.vertexes.containsKey(node2)) {
			
			if (node1 != node2) {
				
				if (hasEdge(node1, node2) && hasEdge(node2, node1)) {
				
					this.neighbors.get(node1).remove(getNode(node2));
					this.neighbors.get(node2).remove(getNode(node1)); 
					
					this.weights.get(node1).remove(node2);
					this.weights.get(node2).remove(node1); 
					
					this.mc++;
					this.ec--;
				}
			}
		}
		
	}

    /** return the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     * @return
     */
	@Override
	public int nodeSize() {
		return this.vertexes.size();
	}

    /**
     * return the number of edges (undirectional graph).
     * Note: this method should run in O(1) time.
     * @return
     */
	@Override
	public int edgeSize() {
		return this.ec;
	}

    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     * @return
     */
	@Override
	public int getMC() {
		return this.mc;
	}

}

