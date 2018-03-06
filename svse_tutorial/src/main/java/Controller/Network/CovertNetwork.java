package Controller.Network;

import Controller.FileReader.CSVReader;
import Controller.NetworkInformation.EdgeInfo;
import Model.NodeInformation.NodeInfo;
import Model.StaticGraph.CalEfficiency;
import Model.StaticGraph.CalSecrecy;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.view.Viewer;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class CovertNetwork {

    private Graph resultGraph_0;
    private Graph tempGraph;
            private int secrecyCount = Integer.MAX_VALUE;
            private int graphSize;
            private int edgeNum;
            private EdgeInfo edgeInfo[];
            private NodeInfo nodeInfo[];
            private Double reCalNum = 1.0; //2.0

            private String findKeyPlayerMethod;
            private Integer keyPlayerNum;
            private Integer destroySize;

            public Double arrestProbabilityStep;
            public Double keyPlayerArrestProbability;
            private String stepIncreaseMethod;

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

    // Imported Graph
    public Graph initialImpGraph(File file){
        System.out.println("initial graph: "+file.getName());

        // Step 1: Get initial tempGraph

        String filePath = file.toString();

        CSVReader csvReader = new CSVReader();


        List<List<String>> networkData = null;
        Graph realGraph_0 = null;


        // read the file
        networkData= csvReader.CSVReader(filePath);
        realGraph_0 = new DefaultGraph(file.getName());




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

        Graph realGraph = new MultiGraph("Covert Network ");
        realGraph.setStrict(false);
        realGraph.setAutoCreate(true);


        for (Edge e : realGraph_0.getEachEdge()){
            realGraph.addEdge(e.getId(),e.getNode0().getId(),e.getNode1().getId());
        }


        Graphs copy = new Graphs();
        resultGraph_0 = copy.clone(realGraph);
        collectGraphInfo();


//        Viewer viewer = realGraph.display();
//        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);


        return realGraph;
    }

    public void  buildCovertNetwork(Graph initialGraph, String algorithm, int balance, String defineKeyPlayersBy, Integer keyPlayerNumber, Integer maxSegmentSize, Double keyPlayerArrestProbability, Double arrestProbabilityStep, String stepIncreaseMethod){

        Double proportion = (2.0 * (double)balance - 100.0)/100.0; // -1 ~ 0: secrecy; 0 ~ 1: efficiency
        System.out.println("Balance = "+balance);
        System.out.println("Proportion = "+proportion);
        greedy(algorithm, initialGraph, proportion, defineKeyPlayersBy, keyPlayerNumber,maxSegmentSize,keyPlayerArrestProbability,arrestProbabilityStep,stepIncreaseMethod);

        if (algorithm.equals("Fast method")){
        }else if (algorithm.equals("Faster method")){
        }
    }


    public void greedy(String algorithm, Graph initialGraph, Double proportion, String defineKeyPlayersBy, Integer keyPlayerNumber, Integer maxSegmentSize, Double keyPlayerArrestProbability, Double arrestProbabilityStep, String stepIncreaseMethod){

        System.out.println("In greedy");
        findKeyPlayerMethod = defineKeyPlayersBy;
        keyPlayerNum = keyPlayerNumber;
        destroySize = maxSegmentSize;

        this.arrestProbabilityStep = arrestProbabilityStep;
        this.keyPlayerArrestProbability = keyPlayerArrestProbability;
        this.stepIncreaseMethod = stepIncreaseMethod;




        // Step 2: Move an edge from one place to another
//        moveEdges(proportion);


        CalSecrecy calSecrecy = new CalSecrecy();
        int se = calSecrecy.CalSecrecyBy(initialGraph,findKeyPlayerMethod,keyPlayerNum,destroySize,keyPlayerArrestProbability,arrestProbabilityStep,stepIncreaseMethod);
        CalEfficiency calEfficiency = new CalEfficiency();
        Double ef = calEfficiency.DeliverMessage(initialGraph,1.0,false);
        System.out.println("initialGraph Secrecy = "+se+", Efficiency = "+ef);


//        for (Edge e : initialGraph.getEachEdge()){
//            System.out.println("Initial graph, index: "+e.getIndex()+", Edge ID: "+ e.getId());
//        }


        Graph resultGraph = step2_moveEdges(algorithm, initialGraph, proportion);

        for (Node n : resultGraph.getEachNode()){
            if (n.getDegree() == 0)
                System.out.println("Node "+n.getIndex()+" has degree 0.");
        }

        Viewer viewer = initialGraph.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);

        Viewer viewer2 = resultGraph.display();
        viewer2.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);






//        tempGraph.display();
//        Viewer viewer = tempGraph.display();
//        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);

    }

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

//        Viewer viewer = tempGraph.display();
//        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);

        System.out.println("Covert network: resultGraph node num = "+ resultGraph_0.getNodeCount()+", edge num = "+ resultGraph_0.getEdgeCount());
//        System.out.println("tempGraph.getNodeCount() = "+ tempGraph.getNodeCount()+", edge num = "+ tempGraph.getEdgeCount());

    }




    private Graph step2_moveEdges(String algorithm, Graph initialGraph_0, Double proportion){

        Graph initialGraph;
        Graphs copy = new Graphs();
        initialGraph = copy.clone(initialGraph_0);

        Graph resultGraph;
        Graphs copy1 = new Graphs();
        resultGraph = copy1.clone(resultGraph_0);



        Double resultSecrecy;
        Double resultEfficiency;
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

        System.out.println("secrecyProportion = "+ secrecyProportion+", efficiencyProportion = "+efficiencyProportion);

        CalEfficiency calInitialEfficiency = new CalEfficiency();
        resultEfficiency = calInitialEfficiency.DeliverMessage(initialGraph,1.0,false);
        CalSecrecy calInitialSecrecy = new CalSecrecy();
        resultSecrecy = (double) calInitialSecrecy.CalSecrecyBy(initialGraph,findKeyPlayerMethod,keyPlayerNum,destroySize,keyPlayerArrestProbability,arrestProbabilityStep,stepIncreaseMethod);

//        System.out.println("Initial efficiency = "+resultEfficiency+", Initial secrecy = "+resultSecrecy);


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
//                                System.out.println("newEdgeID = "+newEdgeID+", Node0 = "+newEdge.getNode0()+", Node1 = "+newEdge.getNode1());
//                                System.out.println("newEdgeID = "+newEdgeID+", Node0 = "+oldEdgeNode0+", Node1 = "+newEdgeNode1);


//                                for (Edge e : resultGraph.getEachEdge()){
//                                    System.out.println("Before removing edges, index: "+e.getIndex()+", Edge ID: "+ e.getId());
//                                }

                                // remove last edge from resultGraph

//                                System.out.println("Before remove, edgeID = "+newEdge.getId()+" Node0 = "+newEdge.getNode0()+", Node1 = "+newEdge.getNode1());
//                                    System.out.println("lastEdge: "+lastEdge);
                                    resultGraph.removeEdge(lastEdge.getId());
//                                    System.out.println("Removed.");



//                                for (Edge e : resultGraph.getEachEdge()){
//                                    System.out.println("After removing edges, index: "+e.getIndex()+", Edge ID: "+ e.getId());
//                                }

                                // add the new edge

                                resultGraph.addEdge(newEdgeID,oldEdgeNode0,newEdgeNode1);
//                                System.out.println("Try to add new edge: "+newEdge);
//                                System.out.println("newEdgeID = "+newEdgeID+", Node0 = "+resultGraph.getEdge(newEdgeID).getNode0()+", Node1 = "+resultGraph.getEdge(newEdgeID).getNode1());


//                                for (Edge e : resultGraph.getEachEdge()){
//                                    System.out.println("After adding new edges, index: "+e.getIndex()+", Edge ID: "+ e.getId());
//                                }

                                // cal E&S
                                CalEfficiency calEfficiency = new CalEfficiency();
                                tempGraphEfficiency = calEfficiency.DeliverMessage(resultGraph,1.0,false);
//                                System.out.println("tempGraphEfficiency = "+tempGraphEfficiency);
                                CalSecrecy calSecrecy = new CalSecrecy();
                                tempGraphSecrecy = (double) calSecrecy.CalSecrecyBy(resultGraph,findKeyPlayerMethod,keyPlayerNum,destroySize,keyPlayerArrestProbability,arrestProbabilityStep,stepIncreaseMethod);
//                                System.out.println("tempGraphSecrecy = "+tempGraphSecrecy);

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
//                                    System.out.println("Remopve edge: " + removedEdge);
                                        //add last edge back
                                        oldEdgeNode0 = lastEdge.getNode0().getIndex();
                                        oldEdgeNode1 = lastEdge.getNode1().getIndex();

                                        resultGraph.addEdge(lastEdge.getId(),oldEdgeNode0,oldEdgeNode1);
//                                    System.out.println("Add lastEdge back: "+lastEdge+ ", Node0 = "+oldEdgeNode0+", Node1 = "+oldEdgeNode1);

//                                    System.out.println("Add oldEdge: "+oldEdgeID+" back");
                                    }else{
//                                    System.out.println("result = "+result+", tempResult = "+tempResult);
                                        lastEdge = newEdge;
//                                    System.out.println("lastEdge: "+lastEdge);
//                                    System.out.println("newEdge"+newEdge);

                                        resultSecrecy = tempGraphSecrecy;
                                        resultEfficiency = tempGraphEfficiency;

//                                    edgeInfo[lastEdge.getIndex()].setEdge(lastEdge);
//                                    edgeInfo[lastEdge.getIndex()].getNode0();

//                                    Viewer viewer = resultGraph.display();
//                                    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
                                        System.out.println("New edge: "+newEdge+" added!");
                                    }

//                                for (Edge e : resultGraph.getEachEdge()){
//                                    System.out.println("After one turn exchange, index: "+e.getIndex()+", Edge ID: "+ e.getId());
//                                }

                                    tempGraph.removeEdge(newEdge.getId());
                                }else if (algorithm.equals("Faster method")){
                                    if (result >= tempResult || newEdge.getNode0().getDegree()==0 || newEdge.getNode1().getDegree()==0){
                                        // the older is better
                                        //remove new edge
                                        Edge removedEdge = resultGraph.removeEdge(newEdge.getId());
//                                    System.out.println("Remopve edge: " + removedEdge);
                                        //add last edge back
                                        oldEdgeNode0 = lastEdge.getNode0().getIndex();
                                        oldEdgeNode1 = lastEdge.getNode1().getIndex();

                                        resultGraph.addEdge(lastEdge.getId(),oldEdgeNode0,oldEdgeNode1);
//                                    System.out.println("Add lastEdge back: "+lastEdge+ ", Node0 = "+oldEdgeNode0+", Node1 = "+oldEdgeNode1);

//                                    System.out.println("Add oldEdge: "+oldEdgeID+" back");
                                        tempGraph.removeEdge(newEdge.getId());
                                        break;
                                    }else{
//                                    System.out.println("result = "+result+", tempResult = "+tempResult);
                                        lastEdge = newEdge;
//                                    System.out.println("lastEdge: "+lastEdge);
//                                    System.out.println("newEdge"+newEdge);

                                        resultSecrecy = tempGraphSecrecy;
                                        resultEfficiency = tempGraphEfficiency;

//                                    edgeInfo[lastEdge.getIndex()].setEdge(lastEdge);
//                                    edgeInfo[lastEdge.getIndex()].getNode0();

//                                    Viewer viewer = resultGraph.display();
//                                    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
                                        System.out.println("New edge: "+newEdge+" added!");
                                        tempGraph.removeEdge(newEdge.getId());
                                    }

//                                for (Edge e : resultGraph.getEachEdge()){
//                                    System.out.println("After one turn exchange, index: "+e.getIndex()+", Edge ID: "+ e.getId());
//                                }


                                } // faster method

                            } // if edgeExited == false

                        }//  if edge wanted to be added is not existed
                    } // for loop
                    edgeNum++;
                } // while loop
            }
        }

//        for (Edge e : resultGraph.getEachEdge()){
//            System.out.println("Final result, index: "+e.getIndex()+", Edge ID: "+ e.getId() + ", Node0 = "+ e.getNode0()+ ", Node1 = "+ e.getNode1());
//        }
        resultGraph.addAttribute("ui.stylesheet","url('./efficiency.css')");





        resultGraph.getNode(1).addAttribute("ui.class","important");

//        for (Edge e : initialGraph.getEachEdge()){
//            System.out.println("Initial graph, index: "+e.getIndex()+", Edge ID: "+ e.getId() + ", Node0 = "+ e.getNode0()+ ", Node1 = "+ e.getNode1());
//        }


        System.out.println("resultGraph Secrecy = "+resultSecrecy+", resultGraph Efficiency = "+resultEfficiency);

        return resultGraph;
    }
}
