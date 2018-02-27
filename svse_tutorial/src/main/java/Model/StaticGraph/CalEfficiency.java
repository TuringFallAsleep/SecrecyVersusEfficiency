package Model.StaticGraph;

import Model.NodeInformation.NodeInfo;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.Viewer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class CalEfficiency {
    private Double efficiency;
    private int pass;
    private int graphSize;
    private NodeInfo[] nodeInfo;
    private Graph theGraph;


    HashSet<Integer> removed = new HashSet<Integer>();

    public Double DeliverMessage(Graph graph, Double hoursPerPass,Boolean displayEfficiencyProgress){

        if (displayEfficiencyProgress){
            efficiencyProgress(graph);
            // todo dynamic graph
        }

        int selectedNode;
        theGraph = graph;
        efficiency = 0.0;
        pass = 0;
        if (graph==null){
            System.out.println("In DeliverMessage: NULL graph");
        }
        graphSize = theGraph.getNodeCount();

        nodeInfo = new NodeInfo[graphSize];

        removed.clear();

        // 1. Select a node, give it a piece of message
        selectedNode = SelectNode();
        // 2. Pass the message to its neighbour, pass time++
        LinkedList<Integer> toVisit = new LinkedList<Integer>();
        toVisit.add(selectedNode);
        if (!toVisit.isEmpty()){
            PassMessage(toVisit);
        }

        // 3. Do 2. until all nodes receive that message, calculate the time

        // check whether all nodes received the message

        efficiency = pass * hoursPerPass;
//        System.out.println("All nodes received message, which cost "+efficiency+" hours");



        return efficiency;
    }


    private int SelectNode() {
        for (int i=0; i<graphSize; i++){
            nodeInfo[i] = new NodeInfo();
            nodeInfo[i].init();
            nodeInfo[i].setOrder(i);
        }

        int nodeNum = 0;
        for (Node n : theGraph.getEachNode()){
            nodeInfo[nodeNum].setNode(n);
            nodeNum++;
        }

        int randomNum = ThreadLocalRandom.current().nextInt(0, graphSize);

        nodeInfo[randomNum].setReceivedMessage(true);
//        System.out.println("Node "+randomNum+" received message.");

        pass++;
//        System.out.println(pass+" run finish");


        return randomNum;
    }

    private void PassMessage(LinkedList<Integer> toVisit) {
        int selectedNode = toVisit.poll();
        int isolatedSize = 0;
        Iterator<Node> neighbour = nodeInfo[selectedNode].getNode().getNeighborNodeIterator();
//        toVisit.clear();

        Boolean passContinue = false;
        for (NodeInfo n : nodeInfo){

            Iterator<Node> iterator = n.getNode().getNeighborNodeIterator();
            while (iterator.hasNext()){ // It is not an isolated node
                iterator.next();
                isolatedSize++;
            }
            if (isolatedSize > 5 && !n.getReceivedMessage()){
                passContinue = true;
                break;
            }

        }


        // pass message to current node's neighbours
        if (neighbour != null){
            if (neighbour.hasNext() && passContinue){
                Boolean doPass = false;
                while (neighbour.hasNext()){
                    Node node = neighbour.next();
                    if (nodeInfo[node.getIndex()].getReceivedMessage() == false){
                        nodeInfo[node.getIndex()].setReceivedMessage(true);
                        toVisit.add(node.getIndex());
                        doPass = true;
//                        System.out.println("Node "+node.getIndex()+" received message.");
                    }
                }
                if (doPass){
                    pass++;
//                    System.out.println(pass+" run finish");
                }
                if (!toVisit.isEmpty()){
                    PassMessage(toVisit);
                }

            }
        }


    }

    private void efficiencyProgress(Graph graph){
//        graph.addAttribute("ui.stylesheet", "graph { fill-color: red; }");

        for (Node n:graph.getEachNode()){
            n.addAttribute("ui.class", "important");
        }
        graph.getNode(0).addAttribute("ui.style", "size: 15px;");
        graph.getNode(2).addAttribute("ui.hide");
        graph.getNode(2).getEdgeBetween(0).addAttribute("ui.hide");
        graph.getNode(3).addAttribute("ui.class", "important");

        System.out.println("efficient progress");
        Viewer viewer = graph.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    }

}


