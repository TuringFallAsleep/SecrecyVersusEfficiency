package Controller.Network;

import Controller.ChartGenerate.*;
import Controller.FileReader.CSVReader;
import Model.StaticGraph.CalEfficiency;
import Model.StaticGraph.CalSecrecy;
import Model.StaticGraph.GraphCal;
import Model.StaticGraph.GraphInfo;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class RealGraph {

    private Graph realGraph;
    private String filePath;
    private File theFile;

    public RealGraph(File file){
        filePath = file.toString();
        theFile = file;
    }


    /* Read data from a real network data set */
    public GraphInfo RG(Double hoursPerPass, Boolean calEfficiency, Boolean calSecrecy, String defineKeyPlayersBy,Integer keyPlayerNumber, Integer maxSegmentSize, Double keyPlayerArrestProbability, Double arrestProbabilityStep, String  stepIncreaseMethod, Boolean plotNetwork, Boolean plotDiameter, Boolean plotDegree, Boolean plotClosenness, Boolean plotBetweenness, Boolean saveResult, Boolean displayEfficiencyProgress, Boolean displaySecrecyProgress) {

        // read from csv file
        CSVReader csvReader = new CSVReader();

        List<List<String>> networkData = null;
        realGraph = null;

            // read the file
        networkData= csvReader.CSVReader(filePath);
        realGraph = new DefaultGraph(theFile.getName());


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

        GraphInfo graphInfo = GraphInfoCal(realGraph, plotNetwork, plotDiameter, plotDegree, plotClosenness, plotBetweenness); // Calculate degrees

        if (calEfficiency){
            CalEfficiency efficiency = new CalEfficiency();
            graphInfo.setEfficiency(efficiency.DeliverMessage(realGraph,hoursPerPass,displayEfficiencyProgress));
        }

        if (calSecrecy){
            CalSecrecy secrecy = new CalSecrecy();
            graphInfo.setSecrecy(secrecy.CalSecrecyBy(realGraph,defineKeyPlayersBy,keyPlayerNumber, maxSegmentSize, keyPlayerArrestProbability, arrestProbabilityStep, stepIncreaseMethod));
        }

        System.out.println("Graph: "+realGraph.getId()+"'s secrecy level is "+ graphInfo.getSecrecy() + " by using "+ defineKeyPlayersBy+" as key players defining method.");


        return graphInfo;
    } // RG()


    public GraphInfo GraphInfoCal(Graph graph, Boolean plotNetwork, Boolean plotDiameter, Boolean plotDegree, Boolean plotClosenness, Boolean plotBetweenness){

        GraphCal a = new GraphCal();
        a.init(graph);
        a.compute();

        GraphInfo graphInfo = new GraphInfo();
        graphInfo.init(graph.getNodeCount());

//        CalSecrecy secrecy = new CalSecrecy();
//
//        if (calSecrecy){
//            secrecy.CalSecrecyBy(graph);
//        }


        System.out.println("Max degree: " + a.getMaxDegree());
        System.out.println("Min degree: " + a.getMinDegree());
        System.out.println("Ave degree: " + String.format("%.1f", a.getAvgDegree()));
        System.out.println("Max diameter: " + String.format("%.1f", a.getMaxDiameter()));
        System.out.println("nodes degree length: "+ a.getAllNodesDeg().length);
        System.out.println("Max betweenness: "+String.format("%.1f",a.getMaxBetweenness()));
        System.out.println("Max closeness*1000: "+String.format("%.1f",a.getMaxCloseness()*1000));

        graphInfo.setAllDiameter(a.getAllDiameters());
        graphInfo.setAllDegree(a.getAllNodesDeg());
        graphInfo.setAllCloseness(a.getAllCloseness());
        graphInfo.setAllBetweenness(a.getAllBetweenness());

        graphInfo.setMaxDiameter(a.getMaxDiameter());
        graphInfo.setMaxDegree(a.getMaxDegree());
        graphInfo.setMinDegree(a.getMinDegree());
        graphInfo.setAveDegree(a.getAvgDegree());
        graphInfo.setMaxCloseness(a.getMaxCloseness());
        graphInfo.setMaxBetweenness(a.getMaxBetweenness());

        if (plotNetwork){
            Viewer viewer = graph.display();
            viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
        }

        if (plotDiameter){
            PlotDiameterLineChart(graphInfo,graph);
        }

        if (plotDegree){
            PlotNodeDegreeLineChart(graphInfo,graph);
        }

        if (plotClosenness){
            PlotClosenessLineChart(graphInfo,graph);
        }

        if (plotBetweenness){
            PlotBetweennessLineChart(graphInfo,graph);
        }


        if (false){
            PlotBarChart(graphInfo,graph);
        }


        return graphInfo;
    } // GraphInfoCal()


    private void PlotDiameterLineChart(GraphInfo graphInfo, Graph graph){
        DiameterLineChart lineChart = new DiameterLineChart(
                "SVSE" ,
                "Diameter Distribution", graphInfo.getAllDiameter(),graph);


        lineChart.pack();
        RefineryUtilities.centerFrameOnScreen(lineChart);
        lineChart.setVisible(true);
    }

    private void PlotNodeDegreeLineChart(GraphInfo graphInfo, Graph graph){
        NodeDegreeLineChart lineChart = new NodeDegreeLineChart(
                "SVSE" ,
                "Degree Distribution", graphInfo.getAllDegree(),graph);

        lineChart.pack();
        RefineryUtilities.centerFrameOnScreen(lineChart);
        lineChart.setVisible(true);
        lineChart.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }


    private void PlotClosenessLineChart(GraphInfo graphInfo, Graph graph){
        ClosenessLineChart lineChart = new ClosenessLineChart(
                "SVSE",
                "Closeness Distribution", graphInfo.getAllCloseness(),graph);

        lineChart.pack();
        RefineryUtilities.centerFrameOnScreen(lineChart);
        lineChart.setVisible(true);
    }

    private void PlotBetweennessLineChart(GraphInfo graphInfo, Graph graph){
        BetweennessLineChart lineChart = new BetweennessLineChart(
                "SVSE",
                "Betweenness Distribution", graphInfo.getAllBetweenness(),graph);

        lineChart.pack();
        RefineryUtilities.centerFrameOnScreen(lineChart);
        lineChart.setVisible(true);
    }


    private void PlotBarChart(GraphInfo graphInfo, Graph graph){
        BarChart chart = new BarChart("SNA Result",
                "Static Graph", graph.getId(),graphInfo.getMaxDegree(),graphInfo.getMinDegree(),graphInfo.getAveDegree(),graphInfo.getMaxDiameter());
        chart.pack();
        RefineryUtilities.centerFrameOnScreen( chart );

//        graph.display();

        chart.setVisible( true );
    }

    public Graph getRealGraph() {
        return realGraph;
    }
} // Controller.Network.RealGraph class
