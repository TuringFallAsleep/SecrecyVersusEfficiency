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
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.*;
import org.graphstream.ui.view.Viewer;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class RealGraph {

    private Graph realGraph;
    private String filePath;
    private File theFile;

    private JFreeChart diameterLineChart;
    private JFreeChart nodeDegreeLineChart;
    private JFreeChart closenessLineChart;
    private JFreeChart betweennessLineChart;
    private JFreeChart barChart;

    private String extension = "";
    private String fileID = "";

    public RealGraph(File file){
        filePath = file.toString();
        theFile = file;

        String fileName = file.getName().toString();


        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
            fileID = fileName.substring(0, i);
            System.out.println("File ID: "+fileID);
        }

//        System.out.println("extension: "+extension);
    }


    /* Read data from a real network data set */
    public GraphInfo RG(Double hoursPerPass, Boolean calEfficiency, Boolean calSecrecy, String defineKeyPlayersBy,Integer keyPlayerNumber, Integer maxSegmentSize, Double keyPlayerArrestProbability, Double arrestProbabilityStep, String  stepIncreaseMethod, Boolean plotNetwork, Boolean plotDiameter, Boolean plotDegree, Boolean plotClosenness, Boolean plotBetweenness, Boolean saveResult, Boolean displayEfficiencyProgress, Boolean displaySecrecyProgress) {

//        System.out.println("In RG");

        if (extension.equals("csv")){
//            System.out.println("In csv");
            // read from csv file
            CSVReader csvReader = new CSVReader();

            List<List<String>> networkData = null;
            realGraph = null;

            // read the file
            networkData= csvReader.CSVReader(filePath);
            realGraph = new DefaultGraph(fileID); //


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
        }else if (extension.equals("gexf")){
            System.out.println("In gexf");
            realGraph = new DefaultGraph(theFile.getName());
            realGraph.setStrict(false);
            realGraph.setAutoCreate(true);

            FileSource fs = new FileSourceGEXF();




            fs.addSink(realGraph);

            try {
                fs.readAll(theFile.getPath());
            } catch( IOException e) {
                System.err.println("Caught IOException: " + e.getMessage());
            } finally {
                fs.removeSink(realGraph);
            }

        }else {
            System.out.println("Please input a .csv or .gexf file.");
            return null;
        }



        GraphInfo graphInfo = GraphInfoCal(realGraph, plotNetwork, plotDiameter, plotDegree, plotClosenness, plotBetweenness, saveResult); // Calculate degrees

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
            PlotBarChart(graphInfo,graph);
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
        lineChart.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

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

    public Graph getRealGraph() {
        return realGraph;
    }
} // Controller.Network.RealGraph class
