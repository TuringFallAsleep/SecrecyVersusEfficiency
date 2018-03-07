package Controller.Network;

import View.ShowSecrecyAndEfficiency;

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
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.*;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.RefineryUtilities;


import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class FixedGraph {

    private Graph highlyCentralisedGraph;
    private Graph highlyDecentralisedGraph;
    private Graph bernoulliGraph;
    private Graph preferentialAttachmentGraph;
    private Graph preferentialAttachmentWithBernoulliGraph;

    private JFreeChart diameterLineChart;
    private JFreeChart nodeDegreeLineChart;
    private JFreeChart closenessLineChart;
    private JFreeChart betweennessLineChart;
    private JFreeChart barChart;

    public GraphInfo HighlyCentralised (int nodeNum, Double hoursPerPass, Boolean calEfficiency, Boolean calSecrecy, String defineKeyPlayersBy, Integer keyPlayerNumber, Integer maxSegmentSize, Double keyPlayerArrestProbability, Double arrestProbabilityStep, String  stepIncreaseMethod, Boolean plotNetwork, Boolean plotDiameter, Boolean plotDegree, Boolean plotClosenness, Boolean plotBetweenness, Boolean saveResult, Boolean displayEfficiencyProgress, Boolean displaySecrecyProgress) {

        // Describe the graph
        highlyCentralisedGraph = new SingleGraph("Highly Centralised");
        highlyCentralisedGraph.setStrict(false);
        highlyCentralisedGraph.setAutoCreate(true);
        for (int i=1; i<nodeNum; i++){
            highlyCentralisedGraph.addEdge(""+0+i,""+0,""+i);
        }

        GraphInfo graphInfo = GraphInfoCal(highlyCentralisedGraph, plotNetwork, plotDiameter, plotDegree, plotClosenness, plotBetweenness, saveResult); // Calculate degrees

        if (calEfficiency || calSecrecy){
            if (calEfficiency){
                CalEfficiency efficiency = new CalEfficiency();
                graphInfo.setEfficiency(efficiency.DeliverMessage(highlyCentralisedGraph,hoursPerPass,displayEfficiencyProgress));
            }


            if (calSecrecy){
                CalSecrecy secrecy = new CalSecrecy();
                graphInfo.setSecrecy(secrecy.CalSecrecyBy(highlyCentralisedGraph,defineKeyPlayersBy,keyPlayerNumber,maxSegmentSize,keyPlayerArrestProbability,arrestProbabilityStep,stepIncreaseMethod));
            }

            ShowSecrecyAndEfficiency show = new ShowSecrecyAndEfficiency(graphInfo.getSecrecy(),graphInfo.getEfficiency());
            show.showDialogWindow();
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

        GraphInfo graphInfo = GraphInfoCal(highlyDecentralisedGraph, plotNetwork, plotDiameter, plotDegree, plotClosenness, plotBetweenness, saveResult); // Calculate degrees

        if (calEfficiency || calSecrecy){
            if (calEfficiency){
                CalEfficiency efficiency = new CalEfficiency();
                graphInfo.setEfficiency(efficiency.DeliverMessage(highlyDecentralisedGraph,hoursPerPass,displayEfficiencyProgress));
            }


            if (calSecrecy){
                CalSecrecy secrecy = new CalSecrecy();
                graphInfo.setSecrecy(secrecy.CalSecrecyBy(highlyDecentralisedGraph,defineKeyPlayersBy,keyPlayerNumber,maxSegmentSize,keyPlayerArrestProbability,arrestProbabilityStep,stepIncreaseMethod));
            }

            ShowSecrecyAndEfficiency show = new ShowSecrecyAndEfficiency(graphInfo.getSecrecy(),graphInfo.getEfficiency());
            show.showDialogWindow();
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

        GraphInfo graphInfo = GraphInfoCal(bernoulliGraph, plotNetwork, plotDiameter, plotDegree, plotClosenness, plotBetweenness, saveResult); // Calculate degrees


        if (calEfficiency || calSecrecy){
            if (calEfficiency){
                CalEfficiency efficiency = new CalEfficiency();
                graphInfo.setEfficiency(efficiency.DeliverMessage(bernoulliGraph,hoursPerPass,displayEfficiencyProgress));
            }


            if (calSecrecy){
                CalSecrecy secrecy = new CalSecrecy();
                graphInfo.setSecrecy(secrecy.CalSecrecyBy(bernoulliGraph,defineKeyPlayersBy,keyPlayerNumber,maxSegmentSize,keyPlayerArrestProbability,arrestProbabilityStep,stepIncreaseMethod));
            }

            ShowSecrecyAndEfficiency show = new ShowSecrecyAndEfficiency(graphInfo.getSecrecy(),graphInfo.getEfficiency());
            show.showDialogWindow();
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

        GraphInfo graphInfo = GraphInfoCal(preferentialAttachmentGraph, plotNetwork, plotDiameter, plotDegree, plotClosenness, plotBetweenness, saveResult); // Calculate degrees


        if (calEfficiency || calSecrecy){
            if (calEfficiency){
                CalEfficiency efficiency = new CalEfficiency();
                graphInfo.setEfficiency(efficiency.DeliverMessage(preferentialAttachmentGraph,hoursPerPass,displayEfficiencyProgress));
            }


            if (calSecrecy){
                CalSecrecy secrecy = new CalSecrecy();
                graphInfo.setSecrecy(secrecy.CalSecrecyBy(preferentialAttachmentGraph,defineKeyPlayersBy,keyPlayerNumber,maxSegmentSize,keyPlayerArrestProbability,arrestProbabilityStep,stepIncreaseMethod));
            }

            ShowSecrecyAndEfficiency show = new ShowSecrecyAndEfficiency(graphInfo.getSecrecy(),graphInfo.getEfficiency());
            show.showDialogWindow();
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

        GraphInfo graphInfo = GraphInfoCal(preferentialAttachmentWithBernoulliGraph, plotNetwork, plotDiameter, plotDegree, plotClosenness, plotBetweenness, saveResult); // Calculate degrees
//        AdjacencyCal(graph);

        if (calEfficiency || calSecrecy){
            if (calEfficiency){
                CalEfficiency efficiency = new CalEfficiency();
                graphInfo.setEfficiency(efficiency.DeliverMessage(preferentialAttachmentWithBernoulliGraph,hoursPerPass,displayEfficiencyProgress));
            }


            if (calSecrecy){
                CalSecrecy secrecy = new CalSecrecy();
                graphInfo.setSecrecy(secrecy.CalSecrecyBy(preferentialAttachmentWithBernoulliGraph,defineKeyPlayersBy,keyPlayerNumber,maxSegmentSize,keyPlayerArrestProbability,arrestProbabilityStep,stepIncreaseMethod));
            }

            ShowSecrecyAndEfficiency show = new ShowSecrecyAndEfficiency(graphInfo.getSecrecy(),graphInfo.getEfficiency());
            show.showDialogWindow();
        }

        System.out.println("Graph: "+preferentialAttachmentWithBernoulliGraph.getId()+"'s secrecy level is "+ graphInfo.getSecrecy() + " by using "+ defineKeyPlayersBy+" as key players defining method.");


        return graphInfo;
    } // PAB()



    public GraphInfo GraphInfoCal(Graph graph, Boolean plotNetwork, Boolean plotDiameter, Boolean plotDegree, Boolean plotClosenness, Boolean plotBetweenness, Boolean saveResult){

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
            diameterLineChart = PlotDiameterLineChart(graphInfo,graph);
        }

        if (plotDegree){
            nodeDegreeLineChart = PlotNodeDegreeLineChart(graphInfo,graph);
        }

        if (plotClosenness){
            closenessLineChart = PlotClosenessLineChart(graphInfo,graph);
        }

        if (plotBetweenness){
            betweennessLineChart = PlotBetweennessLineChart(graphInfo,graph);
        }


        if (false){
            barChart = PlotBarChart(graphInfo,graph);
        }

        if (saveResult){
            saveResults(graph, plotNetwork, plotDiameter, plotDegree, plotClosenness, plotBetweenness, diameterLineChart, nodeDegreeLineChart, closenessLineChart, betweennessLineChart, barChart);
        }


        return graphInfo;
    } // GraphInfoCal()


    private JFreeChart PlotDiameterLineChart(GraphInfo graphInfo, Graph graph){
        DiameterLineChart lineChart = new DiameterLineChart(
                "SVSE" ,
                "Diameter Distribution", graphInfo.getAllDiameter(),graph);

        lineChart.pack();
        RefineryUtilities.centerFrameOnScreen(lineChart);
        lineChart.setVisible(true);

        return lineChart.getChart();
    }

    private JFreeChart PlotNodeDegreeLineChart(GraphInfo graphInfo, Graph graph){
        NodeDegreeLineChart lineChart = new NodeDegreeLineChart(
                "SVSE" ,
                "Degree Distribution", graphInfo.getAllDegree(),graph);

        lineChart.pack();
        RefineryUtilities.centerFrameOnScreen(lineChart);
        lineChart.setVisible(true);
        lineChart.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        return lineChart.getChart();
    }


    private JFreeChart PlotClosenessLineChart(GraphInfo graphInfo, Graph graph){
        ClosenessLineChart lineChart = new ClosenessLineChart(
                "SVSE",
                "Closeness Distribution", graphInfo.getAllCloseness(),graph);

        lineChart.pack();
        RefineryUtilities.centerFrameOnScreen(lineChart);
        lineChart.setVisible(true);
        lineChart.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        return lineChart.getChart();
    }

    private JFreeChart PlotBetweennessLineChart(GraphInfo graphInfo, Graph graph){
        BetweennessLineChart lineChart = new BetweennessLineChart(
                "SVSE",
                "Betweenness Distribution", graphInfo.getAllBetweenness(),graph);

        lineChart.pack();
        RefineryUtilities.centerFrameOnScreen(lineChart);
        lineChart.setVisible(true);
        lineChart.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        return lineChart.getChart();
    }


    private JFreeChart PlotBarChart(GraphInfo graphInfo, Graph graph){
        BarChart chart = new BarChart("SNA Result",
                "Static Graph", graph.getId(),graphInfo.getMaxDegree(),graphInfo.getMinDegree(),graphInfo.getAveDegree(),graphInfo.getMaxDiameter());
        chart.pack();
        RefineryUtilities.centerFrameOnScreen( chart );

//        graph.display();

        chart.setVisible( true );
        chart.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        return chart.getChart();
    }

    protected void saveResults(Graph graph, Boolean plotNetwork, Boolean plotDiameter, Boolean plotDegree, Boolean plotClosenness, Boolean plotBetweenness, JFreeChart diameterLineChart, JFreeChart nodeDegreeLineChart, JFreeChart closenessLineChart, JFreeChart betweennessLineChart, JFreeChart barChart) {
        if (plotNetwork){
            FileSinkGEXF fs = new FileSinkGEXF();
            try {
                fs.writeAll(graph, "./Result/"+graph.getId()+".gexf");
            } catch (IOException e) {
                e.printStackTrace();
            }

            FileSinkImages fileSinkImages = new FileSinkImages(FileSinkImages.OutputType.PNG, FileSinkImages.Resolutions.VGA);
            try {

                fileSinkImages.setLayoutPolicy(FileSinkImages.LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);

                fileSinkImages.writeAll(graph, "./Result/"+graph.getId()+" Image.png");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (plotDiameter){
            try {
                ChartUtilities.saveChartAsPNG(new File("./Result/"+graph.getId()+" Diameter.png"), diameterLineChart, 400, 300);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (plotDegree){
            try {
                ChartUtilities.saveChartAsPNG(new File("./Result/"+graph.getId()+" Degree.png"), nodeDegreeLineChart, 400, 300);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (plotClosenness){
            try {
                ChartUtilities.saveChartAsPNG(new File("./Result/"+graph.getId()+" Closeness.png"), closenessLineChart, 400, 300);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (plotBetweenness){
            try {
                ChartUtilities.saveChartAsPNG(new File("./Result/"+graph.getId()+" Betweenness.png"), betweennessLineChart, 400, 300);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (false){
            try {
                ChartUtilities.saveChartAsPNG(new File("./Result/"+graph.getId()+" Bar.png"), barChart, 400, 300);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


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
