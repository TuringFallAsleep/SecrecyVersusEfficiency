package Controller.Network;

import Controller.ChartGenerate.*;
import Controller.FileReader.CSVReader;
import Model.StaticGraph.CalSecrecy;
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
    private Graph highlyCentralisedGraph;
    private Graph highlyDecentralisedGraph;
    private Graph bernoulliGraph;
    private Graph preferentialAttachmentGraph;
    private Graph preferentialAttachmentWithBernoulliGraph;
    private Graph realGraph;


    public Double[][] HighlyCentralised() {

        // Describe the graph
        highlyCentralisedGraph = new SingleGraph("Highly Centralised");
        highlyCentralisedGraph.setStrict(false);
        highlyCentralisedGraph.setAutoCreate(true);
        highlyCentralisedGraph.addEdge("AB", "A", "B");
        highlyCentralisedGraph.addEdge("AC", "A", "C");
        highlyCentralisedGraph.addEdge("AD", "A", "D");
        highlyCentralisedGraph.addEdge("AE", "A", "E");
        highlyCentralisedGraph.addEdge("AF", "A", "F");
        highlyCentralisedGraph.addEdge("AG", "A", "G");
        highlyCentralisedGraph.addEdge("AH", "A", "H");
        highlyCentralisedGraph.addEdge("AI", "A", "I");
        highlyCentralisedGraph.addEdge("AJ", "A", "J");
        highlyCentralisedGraph.addEdge("AK", "A", "K");
        highlyCentralisedGraph.addEdge("AL", "A", "L");
        highlyCentralisedGraph.addEdge("AM", "A", "M");
        highlyCentralisedGraph.addEdge("AN", "A", "N");


        Double[][] graphResult = DegreeCal(highlyCentralisedGraph); // Calculate degrees
//        AdjacencyCal(graph);


        PlotChart(graphResult, highlyCentralisedGraph);

        return graphResult;
    } // HighlyCentralised()

    public Double[][] HighlyDecentralised() {

        // Describe the graph
        highlyDecentralisedGraph = new SingleGraph("Highly Decentralised");
        highlyDecentralisedGraph.setStrict(false);
        highlyDecentralisedGraph.setAutoCreate(true);
        highlyDecentralisedGraph.addEdge("AB", "A", "B");
        highlyDecentralisedGraph.addEdge("BC", "B", "C");
        highlyDecentralisedGraph.addEdge("CD", "C", "D");
        highlyDecentralisedGraph.addEdge("DE", "D", "E");
        highlyDecentralisedGraph.addEdge("EF", "E", "F");
        highlyDecentralisedGraph.addEdge("FG", "F", "G");
        highlyDecentralisedGraph.addEdge("GH", "G", "H");
        highlyDecentralisedGraph.addEdge("HI", "H", "I");
        highlyDecentralisedGraph.addEdge("IJ", "I", "J");
        highlyDecentralisedGraph.addEdge("JK", "J", "K");
        highlyDecentralisedGraph.addEdge("KL", "K", "L");
        highlyDecentralisedGraph.addEdge("LM", "L", "M");
        highlyDecentralisedGraph.addEdge("MN", "M", "N");
        highlyDecentralisedGraph.addEdge("NA", "N", "A");

        Double[][] graphResult = DegreeCal(highlyDecentralisedGraph); // Calculate degrees
//        AdjacencyCal(graph);

        PlotChart(graphResult, highlyDecentralisedGraph);


        return graphResult;
    } // HighlyDecentralised()


    public Double[][] B() {
        bernoulliGraph = new SingleGraph("Bernoulli");

        Generator gen = new RandomGenerator(2);
        // Generate 60 nodes:
        gen.addSink(bernoulliGraph);
        gen.begin();
        for(int i=0; i<numOfNode-2; i++) {
            gen.nextEvents();
        }
        gen.end();

        Double[][] graphResult = DegreeCal(bernoulliGraph); // Calculate degrees
//        AdjacencyCal(graph);

        PlotChart(graphResult, bernoulliGraph);


        return graphResult;
    } // PAB()

    public Double[][] PA() {
        preferentialAttachmentGraph = new SingleGraph("Preferential Attachment");

        // Between 1 and 3 new links per node added.
        Generator gen = new BarabasiAlbertGenerator(1,false);

        // Generate 60 nodes:
        gen.addSink(preferentialAttachmentGraph);
        gen.begin();
        for(int i=0; i<numOfNode-1; i++) {
            gen.nextEvents();
        }
        gen.end();

        Double[][] graphResult = DegreeCal(preferentialAttachmentGraph); // Calculate degrees
//        AdjacencyCal(graph);

        PlotChart(graphResult, preferentialAttachmentGraph);


        return graphResult;
    } // PAB()





    public Double[][] PAB() {
        preferentialAttachmentWithBernoulliGraph = new SingleGraph("Preferential Attachment with Bernoulli");

        // Between 1 and 3 new links per node added.
        Generator gen = new BarabasiAlbertGenerator(3,false);

        // Generate 61 nodes:
        gen.addSink(preferentialAttachmentWithBernoulliGraph);
        gen.begin();
        for(int i=0; i<numOfNode-1; i++) {
            gen.nextEvents();
        }
        gen.end();

        Double[][] graphResult = DegreeCal(preferentialAttachmentWithBernoulliGraph); // Calculate degrees
//        AdjacencyCal(graph);

        PlotChart(graphResult, preferentialAttachmentWithBernoulliGraph);


        return graphResult;
    } // PAB()

    /* Read data from a real network data set */
    public Double[][] RG(String resource) {

        // read from csv file
        CSVReader csvReader = new CSVReader();
        String filePath;
        List<List<String>> networkData = null;
        realGraph = null;
        if (resource.equals("9_11 Graph")){
            filePath = "/Users/yangboyin/Downloads/CODE/ThirdYearProject/SecrecyVersusEfficiency/svse_tutorial/src/main/resources/9_11_HIJACKERS_ASSOCIATES.csv";
            networkData= csvReader.CSVReader(filePath);
            realGraph = new DefaultGraph("9_11");
        }else if (resource.equals("Suffragettes Inner Circle")){
            filePath = "/Users/yangboyin/Downloads/CODE/ThirdYearProject/SecrecyVersusEfficiency/svse_tutorial/src/main/resources/50_EPANKHURST_INNER_CIRCLE.csv";
            networkData= csvReader.CSVReader(filePath);
            realGraph = new DefaultGraph("Suffragettes Inner Circle");
        }


        realGraph.setStrict(false);
        realGraph.setAutoCreate(true);

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

                    realGraph.addEdge(graphId,node1,node2);
                }
                columnNo++;
            }
            lineNo++;
        }

        Double[][] graphResult = DegreeCal(realGraph); // Calculate degrees
//        AdjacencyCal(graph);


        PlotChart(graphResult, realGraph);


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




    public Double[][] DegreeCal(Graph graph){

        GraphCal a = new GraphCal();
        a.init(graph);
        a.compute();

        CalSecrecy secrecy = new CalSecrecy();
        secrecy.secrecyByBetweenness(graph);

        System.out.println("Max degree: " + a.getMaxDegree());
        System.out.println("Min degree: " + a.getMinDegree());
        System.out.println("Ave degree: " + String.format("%.1f", a.getAvgDegree()));
        System.out.println("Max diameter: " + String.format("%.1f", a.getMaxDiameter()));
        System.out.println("nodes degree length: "+ a.getAllNodesDeg().length);
        System.out.println("Max betweenness: "+String.format("%.1f",a.getMaxBetweenness()));
        System.out.println("Max closeness*1000: "+String.format("%.1f",a.getMaxCloseness()*1000));

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

    public Graph getBernoulliGraph() {
        return bernoulliGraph;
    }

    public Graph getHighlyCentralisedGraph() {
        return highlyCentralisedGraph;
    }

    public Graph getHighlyDecentralisedGraph() {
        return highlyDecentralisedGraph;
    }

    public Graph getPreferentialAttachmentGraph() {
        return preferentialAttachmentGraph;
    }

    public Graph getPreferentialAttachmentWithBernoulliGraph() {
        return preferentialAttachmentWithBernoulliGraph;
    }

    public Graph getRealGraph() {
        return realGraph;
    }

} // Controller.Network.FixedGraph class
