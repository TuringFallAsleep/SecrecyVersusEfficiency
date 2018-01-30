package Controller.Network;

import Controller.NetworkInformation.EdgeInfo;
import Model.NodeInformation.NodeInfo;
import Model.StaticGraph.CalSecrecy;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.implementations.SingleNode;
import org.graphstream.ui.view.Viewer;

public class CovertNetwork {

    private Graph graph;
    private Graph tempGraph;
    private int numOfNode = 50;
    private int secrecyCount = Integer.MAX_VALUE;
    private int graphSize;
    private int edgeNum;
    private EdgeInfo edgeInfo[];
    private NodeInfo nodeInfo[];
    private Double reCalNum = 2.0;


    public void betweennessNet(){



        // Step 1: Generate a Preferential Attachment with Bernoulli graph
        generateGraph();


        // Step 2: Move an edge from one place to another
        moveEdges();



//        CalSecrecy calSecrecy  = new CalSecrecy();
//        secrecyTemp = calSecrecy.secrecyByBetweenness(graph);
//
//        secrecyCount = Math.min(secrecyCount,secrecyTemp);

        System.out.println(secrecyCount);

//        graph.display();
        Viewer viewer = graph.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);

    }

    private void generateGraph(){
        tempGraph = new SingleGraph("Preferential Attachment with Bernoulli");
        // Between 1 and 3 new links per node added.
        Generator gen = new BarabasiAlbertGenerator(3,false);
        gen.addSink(tempGraph);
        gen.begin();
        for(int i=0; i<numOfNode-2; i++) {
            gen.nextEvents();
        }
        gen.end();

        graphSize = tempGraph.getNodeCount();
        edgeNum = tempGraph.getEdgeCount();

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
        for (Node n : tempGraph.getEachNode()){
            nodeInfo[nodeNum].setNode(n);
            nodeInfo[nodeNum].setOrder(nodeNum);
            nodeNum++;
        }

        int edgeNum = 0;
        for (Edge e : tempGraph.getEachEdge()){
            edgeInfo[edgeNum].setEdge(e);
            edgeInfo[edgeNum].setNode0(e.getNode0());
            edgeInfo[edgeNum].setNode1(e.getNode1());
            edgeNum++;
        }

        graph = new MultiGraph("Covert Network");
        for(int i=0; i<numOfNode; i++) {
            graph.addNode(i+"");
        }
//        graph.display();

        System.out.println("tempGraph.getNodeCount() = "+tempGraph.getNodeCount());
        System.out.println("graph.getNodeCount() = "+graph.getNodeCount());

    }

    private void moveEdges(){
//        Graphs temp = new Graphs();
//        Graph tempGraph = Graphs.clone(graph); // Clone a given graph with same node/edge structure and same attributes.
        Double resultSecrecy;
        Double tempGraphSecrecy;


//        tempGraph.removeEdge(5);
//        for (Edge e : tempGraph.getEachEdge()){
//            System.out.println("tempGraph Edge"+e.getIndex()+": "+e);
//        }
//
//        graph.removeEdge(5);
//        for (Edge e : graph.getEachEdge()){
//            System.out.println("graph Edge"+e.getIndex()+": "+e);
//        }


        for (int i=0; i<edgeNum; i++){
            // measure the secrecy before change
            System.out.println("i = "+i);


            resultSecrecy = 0.0;
            for (int re=0; re<reCalNum; re++){
                CalSecrecy calSecrecy  = new CalSecrecy();
                Graph copyGraph = Graphs.clone(tempGraph);
                resultSecrecy += Double.valueOf(calSecrecy.secrecyByBetweenness(copyGraph));
            }
            resultSecrecy = resultSecrecy/reCalNum;


            for (int j=0; j<graphSize; j++){
                Node node0Before;
                Node node1Before;
                Node node1After;

                System.out.println("j = "+j);
                System.out.println("nodeInfo[j].getNode() = "+nodeInfo[j].getNode()+", edgeInfo[i].getNode0() = "+edgeInfo[i].getNode0());
                System.out.println("nodeInfo[j].getNode() = "+nodeInfo[j].getNode()+", edgeInfo[i].getNode1() = "+edgeInfo[i].getNode1());
                if (!nodeInfo[j].getNode().equals(edgeInfo[i].getNode0()) &&
                        !nodeInfo[j].getNode().equals(edgeInfo[i].getNode1())){ // newNode1 != node0, newNode1 != node1
                    node0Before = edgeInfo[i].getNode0();
                    node1Before = edgeInfo[i].getNode1();

                    // remove old edge

                    Edge oldEdge = tempGraph.removeEdge(node0Before.getId(), node1Before.getId());
                    System.out.println("Remove edge: "+edgeInfo[i].getNode0()+"_"+edgeInfo[i].getNode1());
                    for (Edge e : graph.getEachEdge()){
                        System.out.println("graph Edge"+e.getIndex()+": "+e);
                    }


                    // set a new edge
                    edgeInfo[i].setNode1(nodeInfo[j].getNode());
                    node1After = edgeInfo[i].getNode1();


                    String id = node0Before+"_"+node1After;
                    String idInverse = node1After+"_"+node0Before;

                    // check if the edge exists
                    if (tempGraph.getEdge(id)==null && tempGraph.getEdge(idInverse)==null){
                        // apply the new edge
                        tempGraph.addEdge(id,node0Before,node1After);
                        System.out.println("Set new edge: node "+node0Before+ "_"+node1After);

                        // measure secrecy again
                        tempGraphSecrecy = 0.0;
                        for (int re=0; re<reCalNum; re++){
                            CalSecrecy calTempSecrecy  = new CalSecrecy();
                            Graphs copy = new Graphs();
                            Graph copyTempGraph = copy.clone(tempGraph);
                            tempGraphSecrecy += calTempSecrecy.secrecyByBetweenness(copyTempGraph);
                        }
                        tempGraphSecrecy = tempGraphSecrecy/reCalNum;

                        // compare the two, keep the structure with highest secrecy
                        System.out.println("graphSecrecy = "+resultSecrecy+", tempGraphSecrecy = "+tempGraphSecrecy);
                        if (resultSecrecy<tempGraphSecrecy && graph.getEdge(id)==null && graph.getEdge(idInverse)==null){
                            // restructure
                            System.out.println("Restructure: node0Before: "+node0Before.getId()+" node1After: " + node1After.getId());

                            graph.addEdge(id,node0Before.getId(),node1After.getId());
                            resultSecrecy = tempGraphSecrecy;
                        }
                    }else{
                        // put old edge back
                        tempGraph.addEdge(oldEdge.getNode0()+"_"+oldEdge.getNode1(),oldEdge.getNode0(),oldEdge.getNode1());
                        System.out.println("Put old edge back: "+oldEdge.getNode0()+"_"+oldEdge.getNode1());
                    }

                    int edgeNum = 0;
                    for (Edge e : tempGraph.getEachEdge()){
                        edgeInfo[edgeNum].setEdge(e);
                        edgeInfo[edgeNum].setNode0(e.getNode0());
                        edgeInfo[edgeNum].setNode1(e.getNode1());
                        edgeNum++;
                    }

//                    for (Edge e : tempGraph.getEachEdge()){
//                        System.out.println("tempGraph Edge"+e.getIndex()+": "+e);
//                    }
//
                    for (Edge e : graph.getEachEdge()){
                        System.out.println("graph Edge"+e.getIndex()+": "+e);
                    }


                }
            }

        }



//        graph.display();
//        Viewer viewer = graph.display();
//        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);

    }
}
