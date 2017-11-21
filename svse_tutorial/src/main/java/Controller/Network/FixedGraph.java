package Controller.Network;

import Controller.ChartGenerate.*;
import Controller.FileReader.CSVReader;
import Model.StaticGraph.GraphCal;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.jfree.ui.RefineryUtilities;


import java.util.*;

public class FixedGraph {

    static int numOfNode = 59;


    public Double[][] HighlyCentralised() {

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


        Double[][] graphResult = DegreeCal(graph); // Calculate degrees
//        AdjacencyCal(graph);


        PlotChart(graphResult, graph);

        return graphResult;
    } // HighlyCentralised()

    public Double[][] HighlyDecentralised() {

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

        Double[][] graphResult = DegreeCal(graph); // Calculate degrees
//        AdjacencyCal(graph);

        PlotChart(graphResult, graph);


        return graphResult;
    } // HighlyDecentralised()


    public Double[][] B() {
        Graph graph = new SingleGraph("Bernoulli");

        Generator gen = new RandomGenerator(2);
        // Generate 60 nodes:
        gen.addSink(graph);
        gen.begin();
        for(int i=0; i<numOfNode-2; i++) {
            gen.nextEvents();
        }
        gen.end();

        Double[][] graphResult = DegreeCal(graph); // Calculate degrees
//        AdjacencyCal(graph);

        PlotChart(graphResult, graph);


        return graphResult;
    } // PAB()

    public Double[][] PA() {
        Graph graph = new SingleGraph("Preferential Attachment");

        // Between 1 and 3 new links per node added.
        Generator gen = new BarabasiAlbertGenerator(1,false);

        // Generate 60 nodes:
        gen.addSink(graph);
        gen.begin();
        for(int i=0; i<numOfNode-1; i++) {
            gen.nextEvents();
        }
        gen.end();

        Double[][] graphResult = DegreeCal(graph); // Calculate degrees
//        AdjacencyCal(graph);

        PlotChart(graphResult, graph);


        return graphResult;
    } // PAB()





    public Double[][] PAB() {
        Graph graph = new SingleGraph("Preferential Attachment with Bernoulli");

        // Between 1 and 3 new links per node added.
        Generator gen = new BarabasiAlbertGenerator(3,false);

        // Generate 61 nodes:
        gen.addSink(graph);
        gen.begin();
        for(int i=0; i<numOfNode-1; i++) {
            gen.nextEvents();
        }
        gen.end();

        Double[][] graphResult = DegreeCal(graph); // Calculate degrees
//        AdjacencyCal(graph);

        PlotChart(graphResult, graph);


        return graphResult;
    } // PAB()

    /* Read data from a real network data set */
    public Double[][] RG(String resource) {

        // read from csv file
        CSVReader csvReader = new CSVReader();
        String filePath;
        List<List<String>> networkData = null;
        Graph graph = null;
        if (resource.equals("9_11 Graph")){
            filePath = "/Users/yangboyin/Downloads/CODE/ThirdYearProject/SecrecyVersusEfficiency/svse_tutorial/src/main/resources/9_11_HIJACKERS_ASSOCIATES.csv";
            networkData= csvReader.CSVReader(filePath);
            graph = new DefaultGraph("9_11");
        }else if (resource.equals("Suffragettes Inner Circle")){
            filePath = "/Users/yangboyin/Downloads/CODE/ThirdYearProject/SecrecyVersusEfficiency/svse_tutorial/src/main/resources/50_EPANKHURST_INNER_CIRCLE.csv";
            networkData= csvReader.CSVReader(filePath);
            graph = new DefaultGraph("Suffragettes Inner Circle");
        }




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

        Double[][] graphResult = DegreeCal(graph); // Calculate degrees
//        AdjacencyCal(graph);


        PlotChart(graphResult, graph);


        return graphResult;
    } // RG()


    private void PlotChart(Double[][] graphResult, Graph graph){
        PlotBarChart(graphResult,graph);
        PlotNodeDegreeLineChart(graphResult,graph);
        PlotDiameterLineChart(graphResult,graph);
        PlotBetweennessLineChart(graphResult,graph);
        PlotClosenessLineChart(graphResult,graph);
    }

    private void PlotNodeDegreeLineChart(Double[][] graphResult, Graph graph){
        NodeDegreeLineChart lineChart = new NodeDegreeLineChart(
                "Node Degree Distribution" ,
                "Num of Nodes on different degrees", graphResult[5],graph);

        lineChart.pack();
        RefineryUtilities.centerFrameOnScreen(lineChart);
        lineChart.setVisible(true);

    }

    private void PlotDiameterLineChart(Double[][] graphResult, Graph graph){
        DiameterLineChart lineChart = new DiameterLineChart(
                "Diameter Distribution" ,
                "Efficiency by diameter", graphResult[6],graph);


        lineChart.pack();
        RefineryUtilities.centerFrameOnScreen(lineChart);
        lineChart.setVisible(true);

    }

    private void PlotBetweennessLineChart(Double[][] graphResult, Graph graph){
        BetweennessLineChart lineChart = new BetweennessLineChart(
                "Betweenness Distribution",
                "The importance of nodes", graphResult[8],graph);

        lineChart.pack();
        RefineryUtilities.centerFrameOnScreen(lineChart);
        lineChart.setVisible(true);
    }

    private void PlotClosenessLineChart(Double[][] graphResult, Graph graph){
        ClosenessLineChart lineChart = new ClosenessLineChart(
                "Closeness Distribution",
                "The importance of nodes", graphResult[10],graph);

        lineChart.pack();
        RefineryUtilities.centerFrameOnScreen(lineChart);
        lineChart.setVisible(true);
    }

    private void PlotBarChart(Double[][] graphResult, Graph graph){
        BarChart chart = new BarChart("SNA Result",
                "Static Graph", graph.getId(),graphResult[1][0],graphResult[2][0],graphResult[3][0],graphResult[4][0]);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen( chart );

        graph.display();
        chart.setVisible( true );
    }




    private Double[][] DegreeCal(Graph graph){

        GraphCal a = new GraphCal();
        a.init(graph);
        a.compute();

        System.out.println("Max degree: " + a.getMaxDegree());
        System.out.println("Min degree: " + a.getMinDegree());
        System.out.println("Ave degree: " + String.format("%.1f", a.getAvgDegree()));
        System.out.println("Max diameter: " + String.format("%.1f", a.getMaxDiameter()));
        System.out.println("nodes degree length: "+ a.getAllNodesDeg().length);
        System.out.println("Max betweenness: "+String.format("%.1f",a.getMaxBetweenness()));
        System.out.println("Max closeness*1000: "+String.format("%.1f",a.getMaxBetweenness()*1000));

        Double[][] graphResult = new Double[20][a.getAllNodesDeg().length+1];

        graphResult[0][0] = (double)graph.getNodeCount();
        graphResult[1][0] = a.getMaxDegree();
        graphResult[2][0] = a.getMinDegree();
        graphResult[3][0] = a.getAvgDegree();
        graphResult[4][0] = a.getMaxDiameter();
        graphResult[5] = a.getAllNodesDeg();
        graphResult[6] = a.getAllDiameters();
        graphResult[7][0] = a.getMaxBetweenness();
        graphResult[8] = a.getAllBetweenness();
        graphResult[9][0] = a.getMaxCloseness();
        graphResult[10] = a.getAllCloseness();
//        for (int i=0; i<graphResult[6].length;i++)
//            System.out.println("graphResult[6] ["+i+"] "+graphResult[6][i]);

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



} // Controller.Network.FixedGraph class
