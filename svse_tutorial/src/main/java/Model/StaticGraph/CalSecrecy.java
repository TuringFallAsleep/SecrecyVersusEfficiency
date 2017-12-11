package Model.StaticGraph;

import Model.NodeInformation.NodeInfo;
import Model.NodeInformation.NodeOrderComparator;
import Model.NodeInformation.ProbabilityComparator;
import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.GraphicGraph;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class CalSecrecy {

    private Graph theGraph;
    private Node[] keyPlayers;
    private CalBetweenness calBetweenness;
    private int graphSize;

    public int keyPlayersNum = 5;
    private NodeInfo[] nodeInfo;

    public Double arrestProbabilitySteps;
    public Double lowestArrestProbability;
    public int destroySize;

    private int secrecyCount = 0;

    public int secrecyByBetweenness(Graph graph){
        Graphs copy = new Graphs();
        theGraph = copy.clone(graph); // Clone a given graph with same node/edge structure and same attributes.
        graphSize = theGraph.getNodeCount();
        arrestProbabilitySteps = 0.1;
        lowestArrestProbability = 0.1;
        destroySize = 10;


        /* Return count steps of removing nodes and edges until the graph is destroyed */
//        int step = 0;


        calBetweenness = new CalBetweenness();
        calBetweenness.CalBetweenness(theGraph);
        nodeInfo = calBetweenness.getNodeInfo();

//        System.out.println("node info: ");
//        for (int i=0; i<graphSize; i++){
//            System.out.println(" ID: "+nodeInfo[i].getNode().getId()+" is key player: "+nodeInfo[i].isKeyPlayer()+", ");
//        }


//        // Step 1: find key players
//        findKeyPlayers(3);
//
//        // Step 2.1: calculate the distance from every node to the closest key players
//        calDistanceToClosestKeyPlayer();
//        // Step 2.2: set probability based on the distance to the closest key players

//        setArrestProbability(arrestProbabilitySteps,lowestArrestProbability);
//
//        // Step 3.1: sort the probability from high to low
//        sortProbability();
//        // Step 3.2: select a node to be removed
//        selectNodeToRemove();
//
//        // Step 4: find the selected node's neighbours and increase their arrested probability
//        increaseArrestedNodeNeighbourArrestProbability();
//
//        // Step 5: remove the arrested node
//        removeArrestedNode();
//
//        // Step 6: if the whole graph is destroyed
//        isDestroyed(destroySize);

        // Iterate

        while (!isDestroyed(destroySize)){
            // Step 1
            findKeyPlayers(3);
            // Step 2
            calDistanceToClosestKeyPlayer();
            setArrestProbability(arrestProbabilitySteps,lowestArrestProbability);
            // Step 3
            sortProbability();
            selectNodeToRemove();
            // Step 4
            increaseArrestedNodeNeighbourArrestProbability();
            // Step 5
            removeArrestedNode();
            // Step 6
            isDestroyed(destroySize);

            secrecyCount++;
            System.out.println("Secrecy: "+secrecyCount);
        }

        return secrecyCount;

    }


    private void findKeyPlayers(int keyPlayersNumber){ // sort nodeInfo
        keyPlayersNum = keyPlayersNumber;
        nodeInfo = calBetweenness.getKeyPlayers(keyPlayersNum);
        nodeInfo = calBetweenness.getNodeInfo();
    }

    private void calDistanceToClosestKeyPlayer(){
        Dijkstra dijkstra = new Dijkstra();
        dijkstra.init(theGraph);

        keyPlayers = new Node[keyPlayersNum];

        int j = 0;
        for (int i=0; i<graphSize; i++){
            if (nodeInfo[i].isKeyPlayer() && !nodeInfo[i].isRemoved()){
                keyPlayers[j] = nodeInfo[i].getNode();
                j++;
            }
        }

        System.out.println();
        System.out.println("Key players: ");
        for (int i=0; i<keyPlayersNum; i++){
            System.out.print(keyPlayers[i].getId()+", ");
        }
        System.out.println();

        System.out.println("Key players' betweenness: ");
        for (int i=0; i<graphSize; i++){
            if (nodeInfo[i].isKeyPlayer() && !nodeInfo[i].isRemoved())
                System.out.print("ID: "+nodeInfo[i].getNode().getId()+"betweenness: "+nodeInfo[i].getBetweenness()+", ");
        }
        System.out.println();

        for (int i=0; i<graphSize; i++){
            nodeInfo[i].setShortestDistanceToClosestKeyPlayer(Double.MAX_VALUE);
        }
        for (Node key : keyPlayers){
            dijkstra.setSource(key);
            dijkstra.compute();
            int i = 0;
            for (Node node : theGraph){
                Double pathLength = dijkstra.getPathLength(node);
                nodeInfo[i].setShortestDistanceToClosestKeyPlayer(Math.min(nodeInfo[i].getShortestDistanceToClosestKeyPlayer(), pathLength));
                i++;
            }
        }

        System.out.println("shortest distance: ");
        for (int i=0; i<graphSize; i++){
            if (!nodeInfo[i].isRemoved()) {
                System.out.print(" ID: " + nodeInfo[i].getNode().getId() + " distance: " + nodeInfo[i].getShortestDistanceToClosestKeyPlayer() + ", ");
            }
        }
        System.out.println();
    }

    private void setArrestProbability(Double step, Double lowest ){

        for (int i=0; i<graphSize; i++){
            if (nodeInfo[i].getShortestDistanceToClosestKeyPlayer()<graphSize){
                nodeInfo[i].setArrestProbability(nodeInfo[i].getShortestDistanceToClosestKeyPlayer()/(100*step)+lowest);
            }
        }

        System.out.println("arrest probability: ");
        for (int i=0; i<graphSize; i++){
            if (!nodeInfo[i].isRemoved()){
                System.out.print(" ID: "+nodeInfo[i].getNode().getId()+" prob: "+nodeInfo[i].getArrestProbability()+", ");
            }
        }
        System.out.println();
    }

    private void sortProbability(){
        Arrays.sort(nodeInfo, new ProbabilityComparator());
        System.out.println("sorted probability: ");
        for (int i=0; i<graphSize; i++){
            if (!nodeInfo[i].isRemoved()) {
                System.out.print(" ID: " + nodeInfo[i].getNode().getId() + " prob: " + nodeInfo[i].getArrestProbability() + ", ");
            }
        }
        System.out.println();
        Arrays.sort(nodeInfo, new NodeOrderComparator());
    }

    private void selectNodeToRemove(){
        for (int i=0; i<graphSize;i++){
            Random random = new Random();
            if (!nodeInfo[i].isArrested() && nodeInfo[i].getArrestProbability() - random.nextDouble() >= 0 && !nodeInfo[i].isRemoved()){
                // select the node
                nodeInfo[i].setArrested(true);
                System.out.println("Arrest node: "+nodeInfo[i].getNode().getId() + ", Prob: "+nodeInfo[i].getArrestProbability());
                break;
            }
        }
//        Arrays.sort(nodeInfo, new NodeOrderComparator());
//        System.out.println("selected node: ");
//        for (int i=0; i<graphSize; i++){
//            if (nodeInfo[i].isArrested()){
//                System.out.print(" ID: "+nodeInfo[i].getNode().getId()+" prob: "+nodeInfo[i].getArrestProbability()+", ");
//            }
//        }
//        System.out.println();
    }

    private void increaseArrestedNodeNeighbourArrestProbability() {
        Iterator<Node> arrestedNodeNeighbours = null;
        for (int i=0; i<graphSize; i++){
            if (!nodeInfo[i].isRemoved() && nodeInfo[i].isArrested()){
                arrestedNodeNeighbours = nodeInfo[i].getNode().getNeighborNodeIterator();
                break;
//                System.out.print(" ID: "+nodeInfo[i].getNode().getId()+" prob: "+nodeInfo[i].getArrestProbability()+", ");
            }
        }
        if (arrestedNodeNeighbours.hasNext()){
            while (arrestedNodeNeighbours.hasNext()){
                Node neighbour = arrestedNodeNeighbours.next();
                for (int i=0; i<graphSize; i++){
                    if (nodeInfo[i].getNode().equals(neighbour) && !nodeInfo[i].isRemoved()){
                        nodeInfo[i].setArrestProbability(nodeInfo[i].getArrestProbability()+arrestProbabilitySteps);
                        System.out.println("neighbour: "+nodeInfo[i].getNode().getId()+", probability: "+nodeInfo[i].getArrestProbability());
                    }
                }
            }
        }else {
            System.out.println("no neighbour");
        }

        System.out.println("new probability: ");
        for (int i=0; i<graphSize; i++){
//            if (nodeInfo[i].isArrested()){
            if (!nodeInfo[i].isRemoved()){
                System.out.print(" ID: "+nodeInfo[i].getNode().getId()+" prob: "+nodeInfo[i].getArrestProbability()+", ");
            }
        }
        System.out.println();
    }


    private void removeArrestedNode() {
        for (int i=0; i<graphSize; i++){
            if (nodeInfo[i].isArrested() && !nodeInfo[i].isRemoved()){
                theGraph.removeNode(nodeInfo[i].getNode());
                nodeInfo[i].setRemoved(true);
                System.out.println("Remove node: "+ nodeInfo[i].getNode().getId());
            }

        }
    }

    private Boolean isDestroyed(int destroySize){
        Boolean isDestroyed;
        ConnectedComponents cc = new ConnectedComponents();
        cc.init(theGraph);
//        isDestroyed = isKVertexConnected(theGraph, destroySize);
        System.out.println(cc.getConnectedComponentsCount(destroySize+1) + " component(s) with size larger than "+(destroySize+1) + ". ");
        System.out.println(cc.getConnectedComponentsCount(0,3) + " component(s) with size less than " + destroySize + ". ");
        if (cc.getConnectedComponentsCount(destroySize+1)>0){
            isDestroyed = false;
        }else {
            isDestroyed = true;
        }
//        System.out.println("graph size: "+theGraph.getNodeCount());
        return isDestroyed;
    }

}
