package ex1.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import ex1.src.weighted_graph_algorithms;

public class Algo_Test {

    private weighted_graph test_graph() {
    	
    	/*
    	 * 12 nodes
    	 * 15 edges
    	 * 
    	 * 0 -> {1, 2, 3, 4}
    	 * 1 -> {0, 6, 7, 8}
    	 * 2 -> {0, 11}
    	 * 3 -> {0}
    	 * 4 -> {0, 5, 9, 10}
    	 * 5 -> {4, 11}
    	 * 6 -> {1, 11}
    	 * 7 -> {1, 9}
    	 * 8 -> {1}
    	 * 9 -> {4, 7, 11}
    	 * 10 -> {4}
    	 * 11 -> {2, 5, 6, 9}
    	 */
    	
    	
        weighted_graph g0 = new WGraph_DS();
        
        g0.addNode(0);
        g0.addNode(1);
        g0.addNode(2);
        g0.addNode(3);
        g0.addNode(4);
        g0.addNode(5);
        g0.addNode(6);
        g0.addNode(7);
        g0.addNode(8);
        g0.addNode(9);
        g0.addNode(10);
        g0.addNode(11);
        
        g0.connect(0,1,1);
        g0.connect(0,2,10);
        g0.connect(0,3,5);
        g0.connect(0,4,2);
        
        g0.connect(1,6,7);
        g0.connect(1,7,1);
        g0.connect(1,8,15);
              
        g0.connect(2,11,2);
        
        g0.connect(4,5,3);
        g0.connect(4,9,4);
        g0.connect(4,10,55);
        
        g0.connect(5,11,5);
        
        g0.connect(6,11,2);
        
        g0.connect(7,9,1);
        
        g0.connect(9,11,6);

        return g0;
    }
	
    @Test
    public void copy() {
        weighted_graph g0 = test_graph();
        System.out.println("copy, node_size: " + g0.nodeSize());
        System.out.println("copy, edge_size: " + g0.edgeSize());
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        
        weighted_graph g1 = ag0.copy();
        
        assertEquals(g0.nodeSize(),g1.nodeSize());
        g0.removeNode(0);
        assertNotEquals(g0.nodeSize(),g1.nodeSize());
    }
    
    @Test
    public void isConnected() {
        weighted_graph g0 = test_graph();
        System.out.println("isConnected, node_size: " + g0.nodeSize());
        System.out.println("isConnected, edge_size: " + g0.edgeSize());
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());
  
        g0.removeEdge(10, 4);
        System.out.println("isConnected, node_size: " + g0.nodeSize());
        System.out.println("isConnected, edge_size: " + g0.edgeSize());
        assertFalse(ag0.isConnected());
        
        g0.connect(10, 11, 2);
        assertTrue(ag0.isConnected());
    }

   @Test
    public void shortestPathDist() {
       weighted_graph g0 = test_graph();
       System.out.println("shortestPathDist, node_size: " + g0.nodeSize());
       System.out.println("shortestPathDist, edge_size: " + g0.edgeSize());
       weighted_graph_algorithms ag0 = new WGraph_Algo();
       ag0.init(g0);
       
       assertTrue(ag0.isConnected());
       
       double d = ag0.shortestPathDist(0,11);
       assertEquals(d, 9, 0.0001);
    }

    @Test
    public void shortestPath() {
        weighted_graph g0 = test_graph();
        System.out.println("shortestPath, node_size: " + g0.nodeSize());
        System.out.println("shortestPath, edge_size: " + g0.edgeSize());
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        List<node_info> sp = ag0.shortestPath(0,11);
        
        int[] checkKey = {0, 1, 7, 9, 11};
        int i = 0;
        for(node_info n: sp) {
        	assertEquals(n.getKey(), checkKey[i]);
        	i++;
        }
    }
    
    @Test
    public void save_load() {
        weighted_graph g0 = WGraph_DSTest.graph_creator(10,30,1);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        String str = "g0.obj";
        ag0.save(str);
        weighted_graph g1 = WGraph_DSTest.graph_creator(10,30,1);
        ag0.load(str);
        assertEquals(g0,g1);
        g0.removeNode(0);
        assertNotEquals(g0,g1);
    }

}