package Model.StaticGraph;

import Model.NodeInformation.NodeInfo;
import Model.NodeInformation.NodeOrderComparator;
import org.graphstream.algorithm.measure.ClosenessCentrality;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSourceDGS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
            node.setAttribute("Degree", node.getDegree());
            nodeNum++;
        }
    }




    public NodeInfo[] getNodeInfo() {
        return nodeInfo;
    }
}