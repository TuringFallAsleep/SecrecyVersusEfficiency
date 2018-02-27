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
import java.util.*;

public class FixedGraph {

    private Graph highlyCentralisedGraph;
    private Graph highlyDecentralisedGraph;
    private Graph bernoulliGraph;
    private Graph preferentialAttachmentGraph;
    private Graph preferentialAttachmentWithBernoulliGraph;

    public GraphInfo HighlyCentralised(int nodeNum, Double hoursPerPass, Boolean calEfficiency, Boolean calSecrecy, String defineKeyPlayersBy, Integer keyPlayerNumber, Integer maxSegmentSize, Double keyPlayerArrestProbability, Double arrestProbabilityStep, String  stepIncreaseMethod, Boolean plotNetwork, Boolean plotDiameter, Boolean plotDegree, Boolean plotClosenness, Boolean plotBetweenness, Boolean saveResult, Boolean displayEfficiencyProgress, Boolean displaySecrecyProgress) {

        // Describe the graph
        highlyCentralisedGraph = new SingleGraph("Highly Centralised");
        highlyCentralisedGraph.setStrict(false);
        highlyCentralisedGraph.setAutoCreate(true);
        for (int i=1; i<nodeNum; i++){
            highlyCentralisedGraph.addEdge(""+0+i,""+0,""+i);
        }

        GraphInfo graphInfo = GraphInfoCal(highlyCentralisedGraph, plotNetwork, plotDiameter, plotDegree, plotClosenness, plotBetweenness ); // Calculate degrees

        if (calEfficiency){
            CalEfficiency efficiency = new CalEfficiency();
            graphInfo.setEfficiency(efficiency.DeliverMessage(highlyCentralisedGraph,hoursPerPass,displayEfficiencyProgress));
        }

        if (calSecrecy){
            CalSecrecy secrecy = new CalSecrecy();
            graphInfo.setSecrecy(secrecy.CalSecrecyBy(highlyCentralisedGraph,defineKeyPlayersBy,keyPlayerNumber,maxSegmentSize,keyPlayerArrestProbability,arrestProbabilityStep,stepIncreaseMethod));
        }

        System.out.println("Graph: "+highlyCentralisedGraph.getId()+"'s secrecy level is "+ graphInfo.getSecrecy() + " by using "+ defineKeyPlayersBy+" as key players defining method.");

        return graphInfo;
    } // HighlyCentralised()

    public GraphInfo HighlyDecentralised(int nodeNum, Double hoursPerPass, Boolean calEfficiency, Boolean calSecrecy, String defineKeyPlayersBy, Integer keyPlayerNumber, Integer maxSegmentSize, Double keyPlayerArrestProbability, Double arrestProbabilityStep, String  stepIncreaseMethod, Boolean plotNetwork, Boolean plotDiameter, Boolean plotDegree, Boolean plotClosenness, Boolean plotBetweenness, Boolean saveResult, Boolean displayEfficiencyProgress, Boolean displaySecrecyProgress) {

        // Describe the graph
        highlyDecentralisedGraph = new SingleGraph("Highly Decentralised");
        highlyDecentralisedGraph.setStrict(false);
        highlyDecentralisedGraph.setAutoCreate(true);

        for (int i=1; i<nodeNum; i++){
            highlyDecentralisedGraph.addEdge(""+i+(i+1),""+i, ""+(i+1));
        }
        highlyDecentralisedGraph.addEdge(""+nodeNum+"1",""+nodeNum,"1");

        GraphInfo graphInfo = GraphInfoCal(highlyDecentralisedGraph, plotNetwork, plotDiameter, plotDegree, plotClosenness, plotBetweenness); // Calculate degrees

        if (calEfficiency){
            CalEfficiency efficiency = new CalEfficiency();
            graphInfo.setEfficiency(efficiency.DeliverMessage(highlyDecentralisedGraph,hoursPerPass,displayEfficiencyProgress));
        }

        if (calSecrecy){
            CalSecrecy secrecy = new CalSecrecy();
            graphInfo.setSecrecy(secrecy.CalSecrecyBy(highlyDecentralisedGraph, defineKeyPlayersBy, keyPlayerNumber, maxSegmentSize,keyPlayerArrestProbability,arrestProbabilityStep,stepIncreaseMethod));
        }

        System.out.println("Graph: "+highlyDecentralisedGraph.getId()+"'s secrecy level is "+ graphInfo.getSecrecy() + " by using "+ defineKeyPlayersBy+" as key players defining method.");



        return graphInfo;
    } // HighlyDecentralised()


    public GraphInfo B(int nodeNum, Double hoursPerPass, Boolean calEfficiency, Boolean calSecrecy, String defineKeyPlayersBy, Integer keyPlayerNumber, Integer maxSegmentSize, Double keyPlayerArrestProbability, Double arrestProbabilityStep, String  stepIncreaseMethod, Boolean plotNetwork, Boolean plotDiameter, Boolean plotDegree, Boolean plotClosenness, Boolean plotBetweenness, Boolean saveResult, Boolean displayEfficiencyProgress, Boolean displaySecrecyProgress) {
        bernoulliGraph = new SingleGraph("Bernoulli");

        Generator gen = new RandomGenerator(2);
        gen.addSink(bernoulliGraph);
        gen.begin();
        for(int i=1; i<nodeNum-2; i++) {
            gen.nextEvents();
        }
        gen.end();

        GraphInfo graphInfo = GraphInfoCal(bernoulliGraph, plotNetwork, plotDiameter, plotDegree, plotClosenness, plotBetweenness); // Calculate degrees


        if (calEfficiency){
            CalEfficiency efficiency = new CalEfficiency();
            graphInfo.setEfficiency(efficiency.DeliverMessage(bernoulliGraph,hoursPerPass,displayEfficiencyProgress));
        }

        if (calSecrecy){
            CalSecrecy secrecy = new CalSecrecy();
            graphInfo.setSecrecy(secrecy.CalSecrecyBy(bernoulliGraph,defineKeyPlayersBy,keyPlayerNumber, maxSegmentSize,keyPlayerArrestProbability,arrestProbabilityStep,stepIncreaseMethod));
        }

        System.out.println("Graph: "+bernoulliGraph.getId()+"'s secrecy level is "+ graphInfo.getSecrecy() + " by using "+ defineKeyPlayersBy+" as key players defining method.");


        return graphInfo;
    } // PAB()

    public GraphInfo PA(int nodeNum, Double hoursPerPass, Boolean calEfficiency, Boolean calSecrecy, String defineKeyPlayersBy, Integer keyPlayerNumber, Integer maxSegmentSize, Double keyPlayerArrestProbability, Double arrestProbabilityStep, String  stepIncreaseMethod, Boolean plotNetwork, Boolean plotDiameter, Boolean plotDegree, Boolean plotClosenness, Boolean plotBetweenness, Boolean saveResult, Boolean displayEfficiencyProgress, Boolean displaySecrecyProgress) {
        preferentialAttachmentGraph = new SingleGraph("Preferential Attachment");

        // Between 1 and 3 new links per node added.
        Generator gen = new BarabasiAlbertGenerator(1,false);


        gen.addSink(preferentialAttachmentGraph);
        gen.begin();
        for(int i=1; i<nodeNum-1; i++) {
            gen.nextEvents();
        }
        gen.end();

        GraphInfo graphInfo = GraphInfoCal(preferentialAttachmentGraph, plotNetwork, plotDiameter, plotDegree, plotClosenness, plotBetweenness); // Calculate degrees


        if (calEfficiency){
            CalEfficiency efficiency = new CalEfficiency();
            graphInfo.setEfficiency(efficiency.DeliverMessage(preferentialAttachmentGraph,hoursPerPass,displayEfficiencyProgress));
        }

        if (calSecrecy){
            CalSecrecy secrecy = new CalSecrecy();
            graphInfo.setSecrecy(secrecy.CalSecrecyBy(preferentialAttachmentGraph,defineKeyPlayersBy, keyPlayerNumber, maxSegmentSize,keyPlayerArrestProbability,arrestProbabilityStep,stepIncreaseMethod));
        }

        System.out.println("Graph: "+preferentialAttachmentGraph.getId()+"'s secrecy level is "+ graphInfo.getSecrecy() + " by using "+ defineKeyPlayersBy+" as key players defining method.");


        return graphInfo;
    } // PAB()





    public GraphInfo PAB(int nodeNum, Double hoursPerPass, Boolean calEfficiency, Boolean calSecrecy, String defineKeyPlayersBy, Integer keyPlayerNumber, Integer maxSegmentSize, Double keyPlayerArrestProbability, Double arrestProbabilityStep, String  stepIncreaseMethod, Boolean plotNetwork, Boolean plotDiameter, Boolean plotDegree, Boolean plotClosenness, Boolean plotBetweenness, Boolean saveResult, Boolean displayEfficiencyProgress, Boolean displaySecrecyProgress) {
        preferentialAttachmentWithBernoulliGraph = new SingleGraph("Preferential Attachment with Bernoulli");

        // Between 1 and 3 new links per node added.
        Generator gen = new BarabasiAlbertGenerator(3,false);

        // Generate 61 nodes:
        gen.addSink(preferentialAttachmentWithBernoulliGraph);
        gen.begin();
        for(int i=1; i<nodeNum-1; i++) {
            gen.nextEvents();
        }
        gen.end();

        GraphInfo graphInfo = GraphInfoCal(preferentialAttachmentWithBernoulliGraph, plotNetwork, plotDiameter, plotDegree, plotClosenness, plotBetweenness); // Calculate degrees
//        AdjacencyCal(graph);

        if (calEfficiency){
            CalEfficiency efficiency = new CalEfficiency();
            graphInfo.setEfficiency(efficiency.DeliverMessage(preferentialAttachmentWithBernoulliGraph,hoursPerPass,displayEfficiencyProgress));
        }

        if (calSecrecy){
            CalSecrecy secrecy = new CalSecrecy();
            graphInfo.setSecrecy(secrecy.CalSecrecyBy(preferentialAttachmentWithBernoulliGraph,defineKeyPlayersBy,keyPlayerNumber, maxSegmentSize,keyPlayerArrestProbability,arrestProbabilityStep,stepIncreaseMethod));
        }

        System.out.println("Graph: "+preferentialAttachmentWithBernoulliGraph.getId()+"'s secrecy level is "+ graphInfo.getSecrecy() + " by using "+ defineKeyPlayersBy+" as key players defining method.");


        return graphInfo;
    } // PAB()



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

} // Controller.Network.FixedGraph class
