package Model.StaticGraph;

import Model.NodeInformation.NodeInfo;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.graph.implementations.SingleGraph;
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
    private int layerEnd;


    HashSet<Integer> removed = new HashSet<Integer>();

    public Double DeliverMessage(Graph graph, Double hoursPerPass,Boolean displayEfficiencyProgress){

        int selectedNode;
        Graphs copy = new Graphs();
        theGraph = copy.clone(graph);
        efficiency = 0.0;
        pass = 0;
        if (graph==null){
            System.out.println("In DeliverMessage: NULL graph");
        }
        graphSize = theGraph.getNodeCount();

        nodeInfo = new NodeInfo[graphSize];

        removed.clear();


        if (displayEfficiencyProgress){
            theGraph.addAttribute("ui.stylesheet", "url('./efficiency.css')");

            Viewer viewer = theGraph.display();
            viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
            // todo dynamic graph

        }

        // 1. Select a node, give it a piece of message
        selectedNode = SelectNode(displayEfficiencyProgress);
        theGraph.getNode(selectedNode).addAttribute("ui.class", "selected");
        if (displayEfficiencyProgress){
            sleep();
        }
        if (theGraph.getNode(selectedNode).getDegree()==0){
            pass = pass + 10000;
        }
        // 2. Pass the message to its neighbour, pass time++
        LinkedList<Integer> toVisit = new LinkedList<Integer>();
        toVisit.add(selectedNode);
        if (!toVisit.isEmpty()){
            layerEnd = selectedNode;
            PassMessage(toVisit, displayEfficiencyProgress);
        }

        // 3. Do 2. until all nodes receive that message, calculate the time

        // check whether all nodes received the message

        efficiency = pass * hoursPerPass;
        System.out.println("All nodes received message, which cost "+efficiency+" hours");



        return efficiency;
    }


    private int SelectNode(Boolean displayEfficiencyProgress) {

        if (displayEfficiencyProgress){
            sleep();
        }

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

    private void PassMessage(LinkedList<Integer> toVisit, Boolean displayEfficiencyProgress) {

        Boolean allReceived = true;
        for (NodeInfo n : nodeInfo){
            if (n.getReceivedMessage()==false){
                allReceived = false;
            }
        }

        if (allReceived==false){
            Boolean oneLayerFinish = false;

//            System.out.print("toVisit: ");
//            for (int i : toVisit){
//                System.out.print(i+", ");
//            }
//            System.out.println("");

            Iterator<Node> findLayerLast = nodeInfo[toVisit.getFirst()].getNode().getNeighborNodeIterator();

            if (layerEnd == toVisit.getFirst()){
                pass++;
//                System.out.println("Pass = "+pass);
//                System.out.println("In if(): layerEnd = "+layerEnd+", toVisit.getFirst() = "+toVisit.getFirst());
                while (findLayerLast.hasNext()){
                    Node next = findLayerLast.next();
                    if (nodeInfo[next.getIndex()].getReceivedMessage() == false){
                        layerEnd = next.getIndex();
//                        System.out.println("In while: layerEnd = "+layerEnd);
                        oneLayerFinish = true;
                    }else {
//                        System.out.println("Node "+next.getIndex()+" has been visited.");
                    }


                }
//                System.out.println("After while: layerEnd = "+layerEnd);
//                System.out.println("Added node: "+toVisit.getFirst()+", Sleep");
                if (displayEfficiencyProgress && layerEnd == toVisit.getFirst()){
                    sleep();
                }

            }

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

                    while (neighbour.hasNext()){
                        Node node = neighbour.next();
//                        System.out.println("Current node: "+node.getIndex() );
                        if (nodeInfo[node.getIndex()].getReceivedMessage() == false){
                            nodeInfo[node.getIndex()].setReceivedMessage(true);
                            theGraph.getNode(node.getId()).addAttribute("ui.class","received");
                            toVisit.add(node.getIndex()); // to visit current node's neighbours

//                            System.out.println("Node "+node.getIndex()+" received message.");
                        }
                    }



                    if (oneLayerFinish){
                        if (displayEfficiencyProgress){
                            sleep();
//                            System.out.println("Neighbours added, Sleep.");
                        }

                    }


                    if (!toVisit.isEmpty()){
                        PassMessage(toVisit,displayEfficiencyProgress);
                    }



                }
            }
        }




    }


    protected void sleep() {
        try { Thread.sleep(2000); } catch (Exception e) {}
    }

}


