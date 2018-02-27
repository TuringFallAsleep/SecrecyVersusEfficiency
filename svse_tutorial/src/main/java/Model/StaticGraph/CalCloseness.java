package Model.StaticGraph;

import Model.NodeInformation.ClosenessComparator;
import Model.NodeInformation.NodeInfo;
import Model.NodeInformation.NodeOrderComparator;
import org.graphstream.algorithm.measure.ClosenessCentrality;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSourceDGS;

import java.util.Arrays;

import static org.graphstream.algorithm.measure.ClosenessCentrality.DEFAULT_ATTRIBUTE_KEY;

public class CalCloseness {
    int graphSize;
    Double[][] closenessResult;
    private NodeInfo[] nodeInfo;

    public Double[][] CalCloseness(Graph theGraph){
        //      Cal closeness

        FileSourceDGS source = new FileSourceDGS();
        source.addSink(theGraph);

        graphSize = theGraph.getNodeCount()+1;

        closenessResult = new Double[2][graphSize];
//        closenessResult[0][0]: the max diameter
//        closenessResult[1][i]: the distribution of diameter
        nodeInfo = new NodeInfo[graphSize];


        for (int i=0; i<closenessResult.length; i++){
            for (int j=0; j<closenessResult[0].length; j++){
                closenessResult[i][j] = 0.0;
            }
        }

        // Order start from 0
        for (int i=0; i<graphSize;i++){
            nodeInfo[i] = new NodeInfo();
            nodeInfo[i].init();
            nodeInfo[i].setOrder(i);
        }



        ClosenessCentrality closenessCentrality = new ClosenessCentrality();
//        betweennessCentrality.setWeightAttributeName("weight");
        closenessCentrality.init(theGraph);
        closenessCentrality.compute();

        int nodeNum = 0;
        for (Node n : theGraph.getEachNode()){

            Double closeness = n.getAttribute(DEFAULT_ATTRIBUTE_KEY);

//            System.out.println("Node "+n.getId()+" has closeness centrality "+String.format("%.5f", closeness));
            closenessResult[0][0] = Math.max(closenessResult[0][0],closeness);
            closenessResult[1][nodeNum] = closeness;

            nodeInfo[nodeNum].setNode(n);
            nodeInfo[nodeNum].setCloseness(closeness);
            nodeNum++;
        }


        return closenessResult;

    }

    public NodeInfo[] getKeyPlayers(int keyPlayersNumber) {
//        sortNodeInfo = nodeInfo;
        Arrays.sort(nodeInfo, new ClosenessComparator());
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
