package Model.StaticGraph;

import Model.NodeInformation.ClosenessComparator;
import Model.NodeInformation.DegreeComparator;
import Model.NodeInformation.NodeInfo;
import Model.NodeInformation.NodeOrderComparator;
import org.graphstream.algorithm.measure.ClosenessCentrality;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSourceDGS;

import java.util.Arrays;

import static org.graphstream.algorithm.measure.ClosenessCentrality.DEFAULT_ATTRIBUTE_KEY;

public class CalDegree {
    int graphSize;
    private NodeInfo[] nodeInfo;

    public void CalDegree(Graph theGraph){
        graphSize = theGraph.getNodeCount()+1;
        nodeInfo = new NodeInfo[graphSize];

        // Order start from 0
        for (int i=0; i<graphSize;i++){
            nodeInfo[i] = new NodeInfo();
            nodeInfo[i].init();
            nodeInfo[i].setOrder(i);
        }

        int nodeNum = 0;
        for (Node node : theGraph.getEachNode()){
            nodeInfo[nodeNum].setNode(node);
            nodeInfo[nodeNum].setDegree(node.getDegree());
            nodeNum++;
        }

    }

    public NodeInfo[] getKeyPlayers(int keyPlayersNumber) {
//        sortNodeInfo = nodeInfo;
        Arrays.sort(nodeInfo, new DegreeComparator());
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
