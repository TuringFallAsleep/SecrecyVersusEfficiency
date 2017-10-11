import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.Iterator;

public class FixedGraph {


    public int[] HighlyCentralised() {

        // Describe the graph
        Graph graph = new SingleGraph("Highly Centralised");
        graph.setStrict(false);
        graph.setAutoCreate(true);
        graph.addEdge("AB", "A", "B");
        graph.addEdge("AC", "A", "C");
        graph.addEdge("AD", "A", "D");
        graph.addEdge("AE", "A", "E");
        graph.addEdge("AF", "A", "F");
        graph.addEdge("AG", "A", "G");
        graph.addEdge("AH", "A", "H");
        graph.addEdge("AI", "A", "I");
        graph.addEdge("AJ", "A", "J");
        graph.addEdge("AK", "A", "K");
        graph.addEdge("AL", "A", "L");
        graph.addEdge("AM", "A", "M");
        graph.addEdge("AN", "A", "N");

        // Calculate degrees
        int[] resultDeg = DegreeCal(graph);
//        AdjacencyCal(graph);

        graph.display();

        return resultDeg;
    } // HighlyCentralised()

    public int[] HighlyDecentralised() {

        // Describe the graph
        Graph graph = new SingleGraph("Highly Decentralised");
        graph.setStrict(false);
        graph.setAutoCreate(true);
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CD", "C", "D");
        graph.addEdge("DE", "D", "E");
        graph.addEdge("EF", "E", "F");
        graph.addEdge("FG", "F", "G");
        graph.addEdge("GH", "G", "H");
        graph.addEdge("HI", "H", "I");
        graph.addEdge("IJ", "I", "J");
        graph.addEdge("JK", "J", "K");
        graph.addEdge("KL", "K", "L");
        graph.addEdge("LM", "L", "M");
        graph.addEdge("MN", "M", "N");
        graph.addEdge("NA", "N", "A");

        // Calculate degrees
        int[] resultDeg = DegreeCal(graph);
//        AdjacencyCal(graph);

        graph.display();

        return resultDeg;
    } // HighlyDecentralised()

    private int[] DegreeCal(Graph graph){

        DegreeAlgorithm a = new DegreeAlgorithm();
        a.init(graph);
        a.compute();

        System.out.println("Max degree: " + a.getMaxDegree());
        System.out.println("Min degree: " + a.getMinDegree());
        System.out.println("Ave degree: " + a.getAvgDegree());

        int[] resultDeg = {a.getMaxDegree(), a.getMinDegree(), a.getAvgDegree()};

        // get each node, edge
//        for(Node n:graph){
//            System.out.println(n.getId());
//        }
//        for(Edge e:graph.getEachEdge()){
//            System.out.println(e.getId());
//        }
        return resultDeg;
    } // DegreeCal()

    private void AdjacencyCal(Graph graph){
        // two method to get node from the graph
//        Collection<Node> nodes = graph.getNodeSet();

//        Iterator<? extends Node> nodes = graph.getNodeIterator();
//        while(nodes.hasNext()){
//            Node node = nodes.next();
//            System.out.println("now: "+node.getId());
//        }

        int n = graph.getNodeCount();
        System.out.println("count num: " + n);
        int adjacencyMatrix[][] = new int[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < i; j++){
                adjacencyMatrix[i][j] = graph.getNode(i).hasEdgeBetween(j) ? 1 : 0;
                System.out.println("adjacencyMatrix ("+ i + ","+ j + "): " + adjacencyMatrix[i][j]);
            }

        }

        int i = graph.getNode("A").getIndex();
        String id = graph.getNode(0).getId();
        System.out.println("index: " + i +", id: " + id);
    } // AdjacencyCal()


} // FixedGraph class
