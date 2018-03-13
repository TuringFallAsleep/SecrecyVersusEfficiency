package Controller.Network;

import Controller.ChartGenerate.*;
import Controller.FileReader.CSVReader;
import Controller.NetworkInformation.EdgeInfo;
import Model.NodeInformation.NodeInfo;
import Model.StaticGraph.CalEfficiency;
import Model.StaticGraph.CalSecrecy;
import Model.StaticGraph.GraphCal;
import Model.StaticGraph.GraphInfo;
import View.ShowSecrecyAndEfficiency;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSinkGEXF;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceGEXF;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.Viewer;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class CovertNetwork {

    private Graph resultGraph_0;
    private Graph tempGraph;
    private int graphSize;
    private int edgeNum;
    private EdgeInfo edgeInfo[];
    private NodeInfo nodeInfo[];

    private String findKeyPlayerMethod;
    private Integer keyPlayerNum;
    private Integer destroySize;

    private Double arrestProbabilityStep;
    private Double keyPlayerArrestProbability;
    private String stepIncreaseMethod;

    private Boolean plotCovertNetwork = false;
    private Boolean plotInitialNetwork = false;
    private Boolean plotDiameter = false;
    private Boolean plotDegree = false;
    private Boolean plotCloseness = false;
    private Boolean plotBetweenness = false;
    private Boolean saveResult = false;
    private Boolean showEfficiencyProgress = false;
    private Boolean showSecrecyProgress = false;

    // Things to plot
    private Graph resultGraph;
    private Graph startGraph;
    private GraphInfo initialGraphInfo;
    private GraphInfo resultGraphInfo;

    private JFreeChart init_diameterLineChart;
    private JFreeChart init_nodeDegreeLineChart;
    private JFreeChart init_closenessLineChart;
    private JFreeChart init_betweennessLineChart;

    private JFreeChart result_diameterLineChart;
    private JFreeChart result_nodeDegreeLineChart;
    private JFreeChart result_closenessLineChart;
    private JFreeChart result_betweennessLineChart;

    private double initSecrecy;
    private double initEfficiency;
    private double resultSecrecy;
    private double resultEfficiency;
    private double hourPerPass;

    public void setOptions(Boolean plotCovertNetwork, Boolean plotInitialNetwork, Boolean plotDiameter, Boolean plotDegree, Boolean plotCloseness, Boolean plotBetweenness, Boolean saveResult, Boolean showEfficiencyProgress, Boolean showSecrecyProgress){
        this.plotCovertNetwork = plotCovertNetwork;
        this.plotInitialNetwork = plotInitialNetwork;
        this.plotDiameter = plotDiameter;
        this.plotDegree = plotDegree;
        this.plotCloseness = plotCloseness;
        this.plotBetweenness = plotBetweenness;
        this.saveResult = saveResult;
        this.showEfficiencyProgress = showEfficiencyProgress;
        this.showSecrecyProgress = showSecrecyProgress;
    }

    // Generated Graph
    public Graph initialGenGraph(String graphType, int nodeNum){

        // Step 1: Get initial tempGraph
        Graph genGraph = null;

        if (graphType.equals("Highly Centralised")){

            genGraph = new MultiGraph("Highly Centralised");
            genGraph.setStrict(false);
            genGraph.setAutoCreate(true);
            for (int i=0; i<nodeNum-1; i++){
                genGraph.addEdge(0+"_"+i,""+0,""+i);
            }

        }else if (graphType.equals("Highly Decentralised")){

            genGraph = new MultiGraph("Highly Decentralised");
            genGraph.setStrict(false);
            genGraph.setAutoCreate(true);

            for (int i=0; i<nodeNum-1; i++){
                genGraph.addEdge(i+"_"+(i+1),""+i, ""+(i+1));
            }
            genGraph.addEdge((nodeNum-1)+"_"+"0",""+(nodeNum-1),"0");

        }else if (graphType.equals("Bernoulli")){

            genGraph = new MultiGraph("Bernoulli");

            Generator gen = new RandomGenerator(2);
            gen.addSink(genGraph);
            gen.begin();
            for(int i=0; i<nodeNum-3; i++) {
                gen.nextEvents();
            }
            gen.end();

        }else if (graphType.equals("Preferential Attachment")){

            genGraph = new MultiGraph("Preferential Attachment");

            // Between 1 and 3 new links per node added.
            Generator gen = new BarabasiAlbertGenerator(1,false);

            // Generate 60 nodes:
            gen.addSink(genGraph);
            gen.begin();
            for(int i=0; i<nodeNum-2; i++) {
                gen.nextEvents();
            }
            gen.end();

        }else if (graphType.equals("Preferential Attachment with Bernoulli")){

            genGraph = new MultiGraph("Preferential Attachment with Bernoulli");

            // Between 1 and 3 new links per node added.
            Generator gen = new BarabasiAlbertGenerator(3,false);


            gen.addSink(genGraph);
            gen.begin();
            for(int i=0; i<nodeNum-2; i++) {
                gen.nextEvents();
            }
            gen.end();
        }

        Graphs copy = new Graphs();
        resultGraph_0 = copy.clone(genGraph);

        collectGraphInfo();

        return genGraph;

    }

    private String extension = "";
    private String fileID = "";
    private File theFile;

    // Imported Graph
    public Graph initialImpGraph(File file){
        System.out.println("initial graph(imported): "+file.getName());


        // Step 1: Get initial tempGraph

        String filePath = file.toString();
        theFile = file;

        String fileName = file.getName().toString();

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
            fileID = fileName.substring(0, i);
//            System.out.println("File ID: "+fileID);
        }

        Graph realGraph_0 = null;

        if (extension.equals("csv")){
            CSVReader csvReader = new CSVReader();

            List<List<String>> networkData = null;


            // read the file
            networkData= csvReader.CSVReader(filePath);
            realGraph_0 = new DefaultGraph(theFile.getName());


            realGraph_0.setStrict(false);
            realGraph_0.setAutoCreate(true);

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

                        realGraph_0.addEdge(graphId,node1,node2);
                    }
                    columnNo++;
                }
                lineNo++;
            }
        }else if (extension.equals("gexf")){
            System.out.println("In gexf");
            realGraph_0 = new DefaultGraph(theFile.getName());
            realGraph_0.setStrict(false);
            realGraph_0.setAutoCreate(true);

            FileSource fs = new FileSourceGEXF();

            fs.addSink(realGraph_0);

            try {
                fs.readAll(theFile.getPath());
            } catch( IOException e) {
                System.err.println("Caught IOException: " + e.getMessage());
            } finally {
                fs.removeSink(realGraph_0);
            }

        }else {
            System.out.println("Please input a .csv or .gexf file.");
            return null;
        }


        Graph realGraph = new MultiGraph("Covert Network: "+theFile.getName());
        realGraph.setStrict(false);
        realGraph.setAutoCreate(true);


        for (Edge e : realGraph_0.getEachEdge()){
            realGraph.addEdge(e.getId(),e.getNode0().getId(),e.getNode1().getId());
        }

        Graphs copy = new Graphs();
        resultGraph_0 = copy.clone(realGraph);
        collectGraphInfo();

        return realGraph;
    }

    public void  buildCovertNetwork(Graph initialGraph, String algorithm, int balance, Double hoursPerPass, String defineKeyPlayersBy, Integer keyPlayerNumber, Integer maxSegmentSize, Double keyPlayerArrestProbability, Double arrestProbabilityStep, String stepIncreaseMethod){

        startGraph = initialGraph;
        this.hourPerPass = hoursPerPass;

        Double proportion = (2.0 * (double)balance - 100.0)/100.0; // -1 ~ 0: secrecy; 0 ~ 1: efficiency
        System.out.println("Balance = "+balance);
        System.out.println("Proportion = "+proportion);
        greedy(algorithm, initialGraph, proportion, defineKeyPlayersBy, keyPlayerNumber,maxSegmentSize,keyPlayerArrestProbability,arrestProbabilityStep,stepIncreaseMethod);
    }


    public void greedy(String algorithm, Graph initialGraph, Double proportion, String defineKeyPlayersBy, Integer keyPlayerNumber, Integer maxSegmentSize, Double keyPlayerArrestProbability, Double arrestProbabilityStep, String stepIncreaseMethod){

        findKeyPlayerMethod = defineKeyPlayersBy;
        keyPlayerNum = keyPlayerNumber;
        destroySize = maxSegmentSize;

        this.arrestProbabilityStep = arrestProbabilityStep;
        this.keyPlayerArrestProbability = keyPlayerArrestProbability;
        this.stepIncreaseMethod = stepIncreaseMethod;


        // Step 2: Move an edge from one place to another
//        moveEdges(proportion);


        CalSecrecy calSecrecy = new CalSecrecy();
        initSecrecy = (double) calSecrecy.CalSecrecyBy(initialGraph,findKeyPlayerMethod,keyPlayerNum,destroySize,keyPlayerArrestProbability,arrestProbabilityStep,stepIncreaseMethod, false);
        CalEfficiency calEfficiency = new CalEfficiency();
        initEfficiency = calEfficiency.DeliverMessage(initialGraph,1.0,false);
        System.out.println("initialGraph Secrecy = "+initSecrecy+", Efficiency = "+initEfficiency);


        resultGraph = step2_moveEdges(algorithm, initialGraph, proportion);

        startGraph.addAttribute("ui.stylesheet", "url('./covert.css')");
        resultGraph.addAttribute("ui.stylesheet", "url('./covert.css')");


        if (algorithm.equals("Accurate method")){
            System.out.println("Using Accurate");
            for (int i=1; i<graphSize; i++){
                resultGraph = step2_moveEdges("Fast method", resultGraph, proportion);
            }
        }


        for (Node n : resultGraph.getEachNode()){
            if (n.getDegree() == 0)
                System.out.println("Node "+n.getIndex()+" has degree 0.");
        }


    }// calculating ends

    private void collectGraphInfo(){

        graphSize = resultGraph_0.getNodeCount();
        edgeNum = resultGraph_0.getEdgeCount();

        edgeInfo = new EdgeInfo[edgeNum];
        for (int i=0; i<edgeNum;i++){
            edgeInfo[i] = new EdgeInfo();
            edgeInfo[i].init();
            edgeInfo[i].setOrder(i);
        }

        nodeInfo = new NodeInfo[graphSize];
        for (int i=0; i<graphSize; i++){
            nodeInfo[i] = new NodeInfo();
            nodeInfo[i].init();
            nodeInfo[i].setOrder(i);
        }

        int nodeNum = 0;
        for (Node n : resultGraph_0.getEachNode()){
            nodeInfo[nodeNum].setNode(n);
            nodeInfo[nodeNum].setOrder(nodeNum);
            nodeNum++;
        }

        int edgeNum = 0;
        for (Edge e : resultGraph_0.getEachEdge()){
            edgeInfo[edgeNum].setEdge(e);
            edgeInfo[edgeNum].setNode0(e.getNode0());
            edgeInfo[edgeNum].setNode1(e.getNode1());
            edgeNum++;
        }

        tempGraph = new MultiGraph("Covert Network");
        for(int i=0; i<graphSize; i++) {
            tempGraph.addNode(i+"");
        }

        System.out.println("Covert network: resultGraph node num = "+ resultGraph_0.getNodeCount()+", edge num = "+ resultGraph_0.getEdgeCount());
    }



    private Graph step2_moveEdges(String algorithm, Graph initialGraph_0, Double proportion){

        Graph initialGraph;
        Graphs copy = new Graphs();
        initialGraph = copy.clone(initialGraph_0);

        Graph resultGraph;
        Graphs copy1 = new Graphs();
        resultGraph = copy1.clone(resultGraph_0);

        Double tempGraphSecrecy;
        Double tempGraphEfficiency;
        Iterator<Edge> edgesLinkedToCurrentNode = null;

        Double secrecyProportion;
        Double efficiencyProportion;
        if (proportion<0.0){ // -1 ~ 0: secrecy; 0 ~ 1: efficiency
            secrecyProportion = -0.5 * proportion + 0.5;
            efficiencyProportion = 1.0 - secrecyProportion;
        }else {
            efficiencyProportion = 0.5 * proportion + 0.5;
            secrecyProportion = 1.0 - efficiencyProportion;
        }

        CalEfficiency calInitialEfficiency = new CalEfficiency();
        resultEfficiency = calInitialEfficiency.DeliverMessage(initialGraph,1.0,false);
        CalSecrecy calInitialSecrecy = new CalSecrecy();
        resultSecrecy = (double) calInitialSecrecy.CalSecrecyBy(initialGraph,findKeyPlayerMethod,keyPlayerNum,destroySize,keyPlayerArrestProbability,arrestProbabilityStep,stepIncreaseMethod, false);

        Edge oldEgde = null;
        int oldEdgeNode0 = 0;
        int oldEdgeNode1 = 0;

        Edge newEdge = null;
        int newEdgeNode0;
        int newEdgeNode1;

        int edgeNum = 0;
        for (int fixedNode = 0; fixedNode < graphSize; fixedNode++){
            edgesLinkedToCurrentNode = resultGraph.getNode(fixedNode).getEdgeIterator();
            if (edgesLinkedToCurrentNode!=null){
                while(edgesLinkedToCurrentNode.hasNext()){

                    oldEgde = edgesLinkedToCurrentNode.next();
                    if (oldEgde!=null){
                        oldEdgeNode0 = oldEgde.getNode0().getIndex();
                        oldEdgeNode1 = oldEgde.getNode1().getIndex();
                    }else{
                        break;
                    }

                    Edge lastEdge = oldEgde;
                    for (int replacedNode=0; replacedNode<graphSize; replacedNode++){
//                        System.out.println("Replaced Node = "+replacedNode);

                        if (replacedNode!=oldEdgeNode1 && replacedNode!=oldEdgeNode0){
                            String oldEdgeID = oldEdgeNode0+""+oldEdgeNode1;

                            // create an new edge which has different Node1
                            newEdgeNode0 = fixedNode; // same as oldEdgeNode0
                            newEdgeNode1 = replacedNode;
                            String newEdgeID = oldEdgeNode0+"_"+newEdgeNode1;
                            String newEdgeIDInverse = newEdgeNode1+"_"+oldEdgeNode0;

//                            String newEdge_ID = oldEdgeNode0+"_"+newEdgeNode1;
//                            String newEdge_IDInverse = newEdgeNode1+"_"+oldEdgeNode0;
                            Boolean edgeExisted = false;

                            for (Edge e : resultGraph.getEachEdge()){
                                if (e.getId().equals(newEdgeID) || e.getId().equals(newEdgeIDInverse)){
                                    edgeExisted = true;
                                    break;
                                }
                            }

                            if (!edgeExisted){
                                tempGraph.addEdge(newEdgeID,oldEdgeNode0,newEdgeNode1);
                                newEdge = tempGraph.getEdge(newEdgeID);

                                resultGraph.removeEdge(lastEdge.getId());

                                // add the new edge

                                Edge resultNewEdge = resultGraph.addEdge(newEdgeID,oldEdgeNode0,newEdgeNode1);

                                // cal E&S
                                CalEfficiency calEfficiency = new CalEfficiency();
                                tempGraphEfficiency = calEfficiency.DeliverMessage(resultGraph,1.0,false);
                                CalSecrecy calSecrecy = new CalSecrecy();
                                tempGraphSecrecy = (double) calSecrecy.CalSecrecyBy(resultGraph,findKeyPlayerMethod,keyPlayerNum,destroySize,keyPlayerArrestProbability,arrestProbabilityStep,stepIncreaseMethod, false);

                                Double result = secrecyProportion * resultSecrecy - efficiencyProportion * resultEfficiency;
                                Double tempResult = secrecyProportion * tempGraphSecrecy - efficiencyProportion * tempGraphEfficiency;

                                if (algorithm.equals("Fast method")){
                                    if (result >= tempResult || newEdge.getNode0().getDegree()==0 || newEdge.getNode1().getDegree()==0){
                                        // the older is better
                                        if (newEdge.getNode0().getDegree()==0){
                                            System.out.println("Forbid node: "+newEdge.getNode0());
                                        }
                                        if (newEdge.getNode1().getDegree()==0){
                                            System.out.println("Forbid node: "+newEdge.getNode1());
                                        }
                                        //remove new edge
                                        Edge removedEdge = resultGraph.removeEdge(newEdge.getId());
//                                        removedEdge.setAttribute("ui.class", "moved");
                                        //add last edge back
                                        oldEdgeNode0 = lastEdge.getNode0().getIndex();
                                        oldEdgeNode1 = lastEdge.getNode1().getIndex();

                                        resultGraph.addEdge(lastEdge.getId(),oldEdgeNode0,oldEdgeNode1);

                                    }else{
                                        lastEdge = newEdge;
                                        resultSecrecy = tempGraphSecrecy;
                                        resultEfficiency = tempGraphEfficiency;
//                                        resultNewEdge.setAttribute("ui.class", "moved");
                                        System.out.println("New edge: "+newEdge+" added!");
                                    }

                                    tempGraph.removeEdge(newEdge.getId());
                                }else if (algorithm.equals("Faster method")){
                                    if (result >= tempResult || newEdge.getNode0().getDegree()==0 || newEdge.getNode1().getDegree()==0){
                                        // the older is better
                                        //remove new edge
                                        Edge removedEdge = resultGraph.removeEdge(newEdge.getId());
//                                        removedEdge.setAttribute("ui.class", "moved");
//                                    System.out.println("Remopve edge: " + removedEdge);
                                        //add last edge back
                                        oldEdgeNode0 = lastEdge.getNode0().getIndex();
                                        oldEdgeNode1 = lastEdge.getNode1().getIndex();

                                        resultGraph.addEdge(lastEdge.getId(),oldEdgeNode0,oldEdgeNode1);

                                        tempGraph.removeEdge(newEdge.getId());
                                        break;
                                    }else{
                                        lastEdge = newEdge;

                                        resultSecrecy = tempGraphSecrecy;
                                        resultEfficiency = tempGraphEfficiency;

                                        System.out.println("New edge: "+newEdge+" added!");
//                                        resultNewEdge.setAttribute("ui.class","moved");
                                    }
                                        tempGraph.removeEdge(newEdge.getId()); // here or above?

                                } // faster method

                            } // if edgeExited == false

                        }//  if edge wanted to be added is not existed
                    } // for loop
                    edgeNum++;
                } // while loop
            }
        }

        resultGraph.addAttribute("ui.stylesheet","url('./covert.css')");



        System.out.println("resultGraph Secrecy = "+resultSecrecy+", resultGraph Efficiency = "+resultEfficiency);


        return resultGraph;
    }

    // cal graph info

    public GraphInfo graphInfoCal(Graph graph){

        GraphCal a = new GraphCal();
        a.init(graph);
        a.compute();

        GraphInfo graphInfo = new GraphInfo();
        graphInfo.init(graph.getNodeCount());

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

        return graphInfo;
    } // GraphInfoCal()


    public void showResult(){

        if (plotCovertNetwork && plotInitialNetwork){
            Boolean isResult;

            initialGraphInfo = graphInfoCal(startGraph);
            resultGraphInfo = graphInfoCal(resultGraph);

            SpriteManager smStart = new SpriteManager(startGraph);
            Sprite spriteStart = smStart.addSprite("spriteStart");
            spriteStart.setPosition(StyleConstants.Units.PX, 80, 20, 0);
//            spriteStart.addAttribute("ui.stylesheet","url('./efficiency.css')");
//            spriteStart.addAttribute("ui.class","label");
            spriteStart.addAttribute("ui.style","fill-color: white;");
            spriteStart.addAttribute("ui.label","Initial Network");

            SpriteManager smResult = new SpriteManager(resultGraph);
            Sprite spriteResult = smResult.addSprite("spriteResult");
            spriteResult.setPosition(StyleConstants.Units.PX, 80, 20,0);
            spriteResult.addAttribute("ui.style","fill-color: white;");
            spriteResult.addAttribute("ui.label","Result Network");

            for (Node node : startGraph.getEachNode()){
                node.setAttribute("ui.label", node.getId()+"");
            }

            for (Node node : resultGraph.getEachNode()){
                node.setAttribute("ui.label", node.getId()+"");
            }

            // Initial graph
            Viewer viewer1 = startGraph.display();
            viewer1.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);

            // Covert
            Viewer viewer2 = resultGraph.display();
            viewer2.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);

            if (plotDiameter){
                isResult = false;
                init_diameterLineChart = PlotDiameterLineChart(isResult, initialGraphInfo,startGraph);
                isResult = true;
                result_diameterLineChart = PlotDiameterLineChart(isResult, resultGraphInfo,resultGraph);
            }

            if (plotDegree){
                isResult = false;
                init_nodeDegreeLineChart = PlotNodeDegreeLineChart(isResult, initialGraphInfo,startGraph);
                isResult = true;
                result_nodeDegreeLineChart = PlotNodeDegreeLineChart(isResult, resultGraphInfo,resultGraph);

            }

            if (plotCloseness){
                isResult = false;
                init_closenessLineChart = PlotClosenessLineChart(isResult, initialGraphInfo,startGraph);
                isResult = true;
                result_closenessLineChart = PlotClosenessLineChart(isResult, resultGraphInfo,resultGraph);

            }

            if (plotBetweenness){
                isResult = false;
                init_betweennessLineChart = PlotBetweennessLineChart(isResult, initialGraphInfo,startGraph);
                isResult = true;
                result_betweennessLineChart = PlotBetweennessLineChart(isResult, resultGraphInfo,resultGraph);

            }

            if (saveResult){
                isResult = false;
                saveResults(isResult, startGraph, plotCovertNetwork, plotDiameter, plotDegree, plotCloseness, plotBetweenness, init_diameterLineChart, init_nodeDegreeLineChart, init_closenessLineChart, init_betweennessLineChart);
                isResult = true;
                saveResults(isResult, resultGraph, plotCovertNetwork, plotDiameter, plotDegree, plotCloseness, plotBetweenness, result_diameterLineChart, result_nodeDegreeLineChart, result_closenessLineChart, result_betweennessLineChart);

            }

            if (showEfficiencyProgress){
                CalEfficiency calEfficiency = new CalEfficiency();
                initEfficiency = calEfficiency.DeliverMessage(startGraph,hourPerPass,true);

                CalEfficiency calInitialEfficiency = new CalEfficiency();
                resultEfficiency = calInitialEfficiency.DeliverMessage(resultGraph,hourPerPass,true);

            }

            if (showSecrecyProgress){
                CalSecrecy calSecrecy = new CalSecrecy();
                initSecrecy = (double) calSecrecy.CalSecrecyBy(startGraph,findKeyPlayerMethod,keyPlayerNum,destroySize,keyPlayerArrestProbability,arrestProbabilityStep,stepIncreaseMethod, true);

                CalSecrecy calInitialSecrecy = new CalSecrecy();
                resultSecrecy = (double) calInitialSecrecy.CalSecrecyBy(resultGraph,findKeyPlayerMethod,keyPlayerNum,destroySize,keyPlayerArrestProbability,arrestProbabilityStep,stepIncreaseMethod, true);
            }
        }else{

            if (plotInitialNetwork){
                Boolean isResult = false;
                initialGraphInfo = graphInfoCal(startGraph);

                Viewer viewer = startGraph.display();
                viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);

                if (plotDiameter){
                    init_diameterLineChart = PlotDiameterLineChart(isResult, initialGraphInfo,startGraph);
                }

                if (plotDegree){
                    init_nodeDegreeLineChart = PlotNodeDegreeLineChart(isResult, initialGraphInfo,startGraph);
                }

                if (plotCloseness){
                    init_closenessLineChart = PlotClosenessLineChart(isResult, initialGraphInfo,startGraph);
                }

                if (plotBetweenness){
                    init_betweennessLineChart = PlotBetweennessLineChart(isResult, initialGraphInfo,startGraph);
                }

                if (saveResult){
                    saveResults(isResult, startGraph, plotCovertNetwork, plotDiameter, plotDegree, plotCloseness, plotBetweenness, init_diameterLineChart, init_nodeDegreeLineChart, init_closenessLineChart, init_betweennessLineChart);


                }

                if (showEfficiencyProgress){
                    CalEfficiency calEfficiency = new CalEfficiency();
                    initEfficiency = calEfficiency.DeliverMessage(startGraph, hourPerPass,true);
                }

                if (showSecrecyProgress){
                    CalSecrecy calSecrecy = new CalSecrecy();
                    initSecrecy = (double) calSecrecy.CalSecrecyBy(startGraph,findKeyPlayerMethod,keyPlayerNum,destroySize,keyPlayerArrestProbability,arrestProbabilityStep,stepIncreaseMethod, true);
                }
            }

            if (plotCovertNetwork){

                Boolean isResult = true;
                resultGraphInfo = graphInfoCal(resultGraph);

                Viewer viewer = resultGraph.display();
                viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);

                if (plotDiameter){
                    result_diameterLineChart = PlotDiameterLineChart(isResult, resultGraphInfo,resultGraph);
                }

                if (plotDegree){
                    result_nodeDegreeLineChart = PlotNodeDegreeLineChart(isResult, resultGraphInfo,resultGraph);
                }

                if (plotCloseness){
                    result_closenessLineChart = PlotClosenessLineChart(isResult, resultGraphInfo,resultGraph);
                }

                if (plotBetweenness){
                    result_betweennessLineChart = PlotBetweennessLineChart(isResult, resultGraphInfo,resultGraph);
                }

                if (saveResult){
                    saveResults(isResult, resultGraph, plotCovertNetwork, plotDiameter, plotDegree, plotCloseness, plotBetweenness, result_diameterLineChart, result_nodeDegreeLineChart, result_closenessLineChart, result_betweennessLineChart);
                }

                if (showEfficiencyProgress){
                    CalEfficiency calEfficiency = new CalEfficiency();
                    initEfficiency = calEfficiency.DeliverMessage(resultGraph,hourPerPass,true);
                }

                if (showSecrecyProgress){
                    CalSecrecy calInitialSecrecy = new CalSecrecy();
                    resultSecrecy = (double) calInitialSecrecy.CalSecrecyBy(resultGraph,findKeyPlayerMethod,keyPlayerNum,destroySize,keyPlayerArrestProbability,arrestProbabilityStep,stepIncreaseMethod, false);

                }


            }

        }

        // Show secrecy and efficiency comparison
        ShowSecrecyAndEfficiency show = new ShowSecrecyAndEfficiency(initSecrecy,initEfficiency,resultSecrecy,resultEfficiency);
        show.showInitAndResDialogWindow();

    }// show result




    // plot diagrams

    private JFreeChart PlotDiameterLineChart(Boolean isResult, GraphInfo graphInfo, Graph graph){
        String additionalId = "";
        if (isResult){
            additionalId = "Covert Network: ";
        }

        DiameterLineChart lineChart = new DiameterLineChart(
                "SVSE" ,
                additionalId+"Diameter Distribution", graphInfo.getAllDiameter(),graph);


        lineChart.pack();
        RefineryUtilities.centerFrameOnScreen(lineChart);
        lineChart.setVisible(true);
        lineChart.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        return lineChart.getChart();
    }

    private JFreeChart PlotNodeDegreeLineChart(Boolean isResult, GraphInfo graphInfo, Graph graph){
        String additionalId = "";
        if (isResult){
            additionalId = "Covert Network: ";
        }

        NodeDegreeLineChart lineChart = new NodeDegreeLineChart(
                "SVSE" ,
                additionalId+"Degree Distribution", graphInfo.getAllDegree(),graph);

        lineChart.pack();
        RefineryUtilities.centerFrameOnScreen(lineChart);
        lineChart.setVisible(true);
        lineChart.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        return lineChart.getChart();
    }


    private JFreeChart PlotClosenessLineChart(Boolean isResult, GraphInfo graphInfo, Graph graph){
        String additionalId = "";
        if (isResult){
            additionalId = "Covert Network: ";
        }

        ClosenessLineChart lineChart = new ClosenessLineChart(
                "SVSE",
                additionalId+"Closeness Distribution", graphInfo.getAllCloseness(),graph);

        lineChart.pack();
        RefineryUtilities.centerFrameOnScreen(lineChart);
        lineChart.setVisible(true);
        lineChart.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        return lineChart.getChart();
    }

    private JFreeChart PlotBetweennessLineChart(Boolean isResult, GraphInfo graphInfo, Graph graph){
        String additionalId = "";
        if (isResult){
            additionalId = "Covert Network: ";
        }

        BetweennessLineChart lineChart = new BetweennessLineChart(
                "SVSE",
                additionalId+"Betweenness Distribution", graphInfo.getAllBetweenness(),graph);

        lineChart.pack();
        RefineryUtilities.centerFrameOnScreen(lineChart);
        lineChart.setVisible(true);
        lineChart.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        return lineChart.getChart();
    }



    protected void saveResults(Boolean isResult, Graph graph, Boolean plotNetwork, Boolean plotDiameter, Boolean plotDegree, Boolean plotClosenness, Boolean plotBetweenness, JFreeChart diameterLineChart, JFreeChart nodeDegreeLineChart, JFreeChart closenessLineChart, JFreeChart betweennessLineChart) {
        String additionalId = "";
        if (isResult){
            additionalId = "Covert Network of ";
        }

        if (plotNetwork){
            FileSinkGEXF fs = new FileSinkGEXF();
            try {
                fs.writeAll(graph, "./Result/"+additionalId+graph.getId()+".gexf");
            } catch (IOException e) {
                e.printStackTrace();
            }

            FileSinkImages fileSinkImages = new FileSinkImages(FileSinkImages.OutputType.PNG, FileSinkImages.Resolutions.VGA);
            try {

                fileSinkImages.setLayoutPolicy(FileSinkImages.LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);

                fileSinkImages.writeAll(graph, "./Result/"+additionalId+graph.getId()+" Image.png");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (plotDiameter){
            try {
                ChartUtilities.saveChartAsPNG(new File("./Result/"+additionalId+graph.getId()+" Diameter.png"), diameterLineChart, 400, 300);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (plotDegree){
            try {
                ChartUtilities.saveChartAsPNG(new File("./Result/"+additionalId+graph.getId()+" Degree.png"), nodeDegreeLineChart, 400, 300);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (plotClosenness){
            try {
                ChartUtilities.saveChartAsPNG(new File("./Result/"+additionalId+graph.getId()+" Closeness.png"), closenessLineChart, 400, 300);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (plotBetweenness){
            try {
                ChartUtilities.saveChartAsPNG(new File("./Result/"+additionalId+graph.getId()+" Betweenness.png"), betweennessLineChart, 400, 300);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
