import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.SingleGraph;


import java.util.*;

public class FixedGraph {

    int numOfNode = 59;


    public Double[] HighlyCentralised() {

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


        Double[] graphResult = DegreeCal(graph); // Calculate degrees
//        AdjacencyCal(graph);

        graph.display();

        return graphResult;
    } // HighlyCentralised()

    public Double[] HighlyDecentralised() {

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

        Double[] graphResult = DegreeCal(graph); // Calculate degrees
//        AdjacencyCal(graph);

        graph.display();

        return graphResult;
    } // HighlyDecentralised()


    public Double[] B() {
        Graph graph = new SingleGraph("Bernoulli");

        Generator gen = new RandomGenerator(2);
        // Generate 60 nodes:
        gen.addSink(graph);
        gen.begin();
        for(int i=0; i<numOfNode; i++) {
            gen.nextEvents();
        }
        gen.end();

        Double[] graphResult = DegreeCal(graph); // Calculate degrees
//        AdjacencyCal(graph);

        graph.display();

        return graphResult;
    } // PAB()

    public Double[] PA() {
        Graph graph = new SingleGraph("Preferential Attachment");

        // Between 1 and 3 new links per node added.
        Generator gen = new BarabasiAlbertGenerator(1,false);

        // Generate 61 nodes:
        gen.addSink(graph);
        gen.begin();
        for(int i=0; i<numOfNode; i++) {
            gen.nextEvents();
        }
        gen.end();

        Double[] graphResult = DegreeCal(graph); // Calculate degrees
//        AdjacencyCal(graph);

        graph.display();

        return graphResult;
    } // PAB()



    public Double[] PAB() {
        Graph graph = new SingleGraph("Preferential Attachment with Bernoulli");

        // Between 1 and 3 new links per node added.
        Generator gen = new BarabasiAlbertGenerator(3,false);

        // Generate 61 nodes:
        gen.addSink(graph);
        gen.begin();
        for(int i=0; i<numOfNode; i++) {
            gen.nextEvents();
        }
        gen.end();

        Double[] graphResult = DegreeCal(graph); // Calculate degrees
//        AdjacencyCal(graph);

        graph.display();

        return graphResult;
    } // PAB()

    public Double[] RG() {

        // read from csv file
        CSVReader csvReader = new CSVReader();
        List<List<String>> networkData = csvReader.CSVReader();

        Graph graph = new DefaultGraph("9_11");
        graph.setStrict(false);
        graph.setAutoCreate(true);

        // iterate through the 2-dimensional array
        int lineNo = 0;
        String haveConnection = "1";
        for(List<String> line: networkData) {
            int columnNo = 0;
            for (String value: line) {
                if (haveConnection.equals(value)){
                    StringBuilder sb = new StringBuilder();
                    String node1 = networkData.get(lineNo).get(0);
                    sb.append(node1);
                    sb.append(" & ");
                    String node2 = networkData.get(0).get(columnNo);
                    sb.append(node2);
                    String graphId = sb.toString();

                    graph.addEdge(graphId,node1,node2);
                }
                columnNo++;
            }
            lineNo++;
        }

        Double[] graphResult = DegreeCal(graph); // Calculate degrees
//        AdjacencyCal(graph);

        graph.display();

        return graphResult;
    } // RG()




    private Double[] DegreeCal(Graph graph){

        GraphCal a = new GraphCal();
        a.init(graph);
        a.compute();

        System.out.println("Max degree: " + a.getMaxDegree());
        System.out.println("Min degree: " + a.getMinDegree());
        System.out.println("Ave degree: " + String.format("%.1f", a.getAvgDegree()));
        System.out.println("APSP: " + String.format("%.1f", a.getMaxMinLength()));

        Double[] graphResult = {(double)graph.getNodeCount(), a.getMaxDegree(), a.getMinDegree(), a.getAvgDegree(), a.getMaxMinLength()};

        // get each node, edge
//        for(Node n:graph){
//            System.out.println(n.getId());
//        }
//        for(Edge e:graph.getEachEdge()){
//            System.out.println(e.getId());
//        }
        return graphResult;
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
