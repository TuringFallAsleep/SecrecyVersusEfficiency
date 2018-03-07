package Model.StaticGraph;

import Model.NodeInformation.BetweennessComparator;
import Model.NodeInformation.NodeInfo;
import Model.NodeInformation.NodeOrderComparator;
import org.graphstream.algorithm.BetweennessCentrality;
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

        FileSourceDGS source = new FileSourceDGS();
        source.addSink(theGraph);

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
//        betweennessCentrality.setWeightAttributeName("weight");
        betweennessCentrality.init(theGraph);
        betweennessCentrality.compute();

        int nodeNum = 0;
        for (Node n : theGraph.getEachNode()){

            Double betweenness = n.getAttribute("Cb");
//            String.format("%.1f", betweenness))

//            System.out.println("Node "+n.getId()+" has betweenness centrality "+String.format("%.1f", betweenness));
            betweennessResult[0][0] = Math.max(betweennessResult[0][0],betweenness);
            betweennessResult[1][nodeNum] = betweenness;


            nodeInfo[nodeNum].setNode(n);
            nodeInfo[nodeNum].setBetweenness(betweenness);
//            System.out.println("Node["+nodeNum+"], Betweenness: "+betweenness);
            nodeNum++;
        }

//        NodeInfo[] sortNode = getKeyPlayers(3);
//        System.out.println();
//        System.out.println("betweenness sort: ");
//        for (int i=0; i<graphSize; i++){
//            if (sortNode[i].isKeyPlayer()){
//                System.out.print(" ID: "+sortNode[i].getNode().getId()+" bet: "+sortNode[i].getBetweenness()+", ");
//            }
//        }


        return betweennessResult;

    }

    public NodeInfo[] getKeyPlayers(int keyPlayersNumber) {
//        sortNodeInfo = nodeInfo;
        Arrays.sort(nodeInfo, new BetweennessComparator());
        int i = 0;
        int count = 0;
        while(count<keyPlayersNumber){
            if (!nodeInfo[i].isRemoved() && !nodeInfo[i].isArrested()){
                nodeInfo[i].setKeyPlayer(true);
//                System.out.println("This is key player: "+ nodeInfo[i].getNode().getId());
                i++;
                count++;
            }else{
                nodeInfo[i].setKeyPlayer(false);
                i++;
            }
        }
//        for (int i=0; i<keyPlayersNumber;i++){
//            nodeInfo[i].setKeyPlayer(true);
//        }
        Arrays.sort(nodeInfo, new NodeOrderComparator());
        return nodeInfo;
    }

    public NodeInfo[] getNodeInfo() {
        return nodeInfo;
    }
}
