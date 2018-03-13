package Model.StaticGraph;

import Model.NodeInformation.BetweennessComparator;
import Model.NodeInformation.NodeInfo;
import Model.NodeInformation.NodeOrderComparator;
import org.graphstream.algorithm.BetweennessCentrality;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSourceDGS;

import java.util.Arrays;

public class CalBetweenness {

    private int graphSize;
    private Double[][] betweennessResult;
    private NodeInfo[] nodeInfo;
//    private NodeInfo[] sortNodeInfo;



//    public void keyPlayersNum(int keyPlayersNum){
//        keyPlayersNumber = keyPlayersNum;
//    }

    public Double[][] CalBetweenness(Graph theGraph){

        //      Cal betweenness

//        FileSourceDGS source = new FileSourceDGS();
//        source.addSink(theGraph);

        graphSize = theGraph.getNodeCount();

        betweennessResult = new Double[2][graphSize];
//        betweenessResult[0][0]: the max diameter
//        betweenessResult[1][i]: the distribution of diameter
        nodeInfo = new NodeInfo[graphSize];

        for (int i=0; i<betweennessResult.length; i++){
            for (int j=0; j<betweennessResult[0].length; j++){
                betweennessResult[i][j] = 0.0;
            }
        }


        // Order start from 0
        for (int i=0; i<graphSize;i++){
            nodeInfo[i] = new NodeInfo();
            nodeInfo[i].init();
            nodeInfo[i].setOrder(i);
        }
//        sortNodeInfo = nodeInfo;


        BetweennessCentrality betweennessCentrality = new BetweennessCentrality();
        betweennessCentrality.setWeightAttributeName("weight");
        for (Edge edge : theGraph.getEachEdge()){
            betweennessCentrality.setWeight(edge.getNode0(),edge.getNode1(),1);
        }

        betweennessCentrality.init(theGraph);
        betweennessCentrality.compute();

        int nodeNum = 0;
        for (Node n : theGraph.getEachNode()){

            Double betweenness = n.getAttribute("Cb");

            betweennessResult[0][0] = Math.max(betweennessResult[0][0],betweenness);
            betweennessResult[1][nodeNum] = betweenness;


            nodeInfo[nodeNum].setNode(n);
            nodeInfo[nodeNum].setBetweenness(betweenness);

            n.setAttribute("Betweenness", betweenness);
//            System.out.println("Node["+nodeNum+"], Betweenness: "+betweenness);
            nodeNum++;
        }


        return betweennessResult;

    }


    public NodeInfo[] getNodeInfo() {
        return nodeInfo;
    }
}
