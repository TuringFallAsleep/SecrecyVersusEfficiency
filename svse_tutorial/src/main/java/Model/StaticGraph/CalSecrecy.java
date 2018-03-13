package Model.StaticGraph;

import Model.NodeInformation.ProbabilityComparator;
import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.Viewer;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class CalSecrecy {

    private final static String ARREST_PROBABILITY =  "ArrestProbability";
    private final static String SHORTEST_DISTANCE_TO_CLOSEST_KEY_PLAYER = "ShortestDistanceToClosestKeyPlayer";
    private final static String ARRESTED = "Arrested";

    private Graph theGraph;
    private Node[] keyPlayers;
    private int graphSize;


    private String findKeyPlayersMethod;

    private int keyPlayersNum;
//    private NodeInfo[] nodeInfo;

    private Double arrestProbabilityStep;
    private Double keyPlayerArrestProbability;
    private String stepIncreaseMethod;
    private int destroySize;

    private Node[] oldKeyPlayers;
    private Boolean keyPlayersChanged;
    private double aveKeyPlayerArrestProbability;

    private int secrecyCount = 0;
    private int removedNodeCount = 0;

    private Sprite spriteSecrecy;

    public int CalSecrecyBy(Graph graph, String findKeyPlayerBy, int keyPlayersNumber, int maxSegmentSize, Double keyPlayerArrestProb, Double probStep, String increaseMethod, Boolean displaySecrecyProgress){

        Graphs copy = new Graphs();
        theGraph = copy.clone(graph); // Clone a given graph with same node/edge structure and same attributes.
        arrestProbabilityStep = probStep;
        keyPlayerArrestProbability = keyPlayerArrestProb;
        stepIncreaseMethod = increaseMethod;
        destroySize = maxSegmentSize;
        keyPlayersNum = keyPlayersNumber;
        findKeyPlayersMethod = findKeyPlayerBy;

        keyPlayersChanged = true;
        oldKeyPlayers = new Node[keyPlayersNumber];
        aveKeyPlayerArrestProbability = 0.0;

        SpriteManager smStart = new SpriteManager(theGraph);
        spriteSecrecy = smStart.addSprite("spriteSecrecy");

        spriteSecrecy.setPosition(StyleConstants.Units.PX, 80, 20, 0);
        spriteSecrecy.addAttribute("ui.style","fill-color: white;");
        spriteSecrecy.addAttribute("ui.label","Secrecy Steps Count: "+secrecyCount);

        theGraph.addAttribute("ui.stylesheet", "url('./secrecy.css')");
        if (displaySecrecyProgress){
            Viewer viewer = theGraph.display();
            viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
            sleep();
        }


        /* Return count steps of removing nodes and edges until the graph is destroyed */
//        int step = 0;

        for (Node node : theGraph.getEachNode()){
            node.setAttribute(ARREST_PROBABILITY, (Double)0.0);
            node.setAttribute(SHORTEST_DISTANCE_TO_CLOSEST_KEY_PLAYER, Double.MAX_VALUE);
            node.setAttribute("ui.label", node.getAttribute(ARREST_PROBABILITY)+"%");
            node.addAttribute("ui.class","other");
        }

        GetKeyPlayers getKeyPlayers = new GetKeyPlayers();
        getKeyPlayers.getKP(theGraph, keyPlayersNumber, findKeyPlayerBy);



        if (displaySecrecyProgress){
            for (Node node : theGraph.getEachNode()){
                if (!node.getAttribute("ui.class").equals("other")){
                    node.changeAttribute("ui.label", node.getId()+": "+node.getAttribute(ARREST_PROBABILITY)+"%");
                }else {
                    node.changeAttribute("ui.label", node.getAttribute(ARREST_PROBABILITY)+"%");

                }
            }
            sleep();
        }

        // Iterate
        while (!isDestroyed(destroySize)){

            graphSize = theGraph.getNodeCount();

            // Step 1: find key players

//            System.out.println("1");
            findKeyPlayers(keyPlayersNumber);


//            System.out.println("2");
            // Step 2.1: calculate the distance from every node to the closest key players
            calDistanceToClosestKeyPlayer();

            // Step 2.2: set probability based on the distance to the closest key players
            setArrestProbability(arrestProbabilityStep, keyPlayerArrestProbability,stepIncreaseMethod);
            for (Node node : theGraph.getEachNode()){
                if (!node.getAttribute("ui.class").equals("other")){
                    node.changeAttribute("ui.label", node.getId()+": "+node.getAttribute(ARREST_PROBABILITY)+"%");
                }else {
                    node.changeAttribute("ui.label", node.getAttribute(ARREST_PROBABILITY)+"%");

                }
            }
            if (displaySecrecyProgress){
                sleep();
            }

//            System.out.println("3");
            // Step 3: sort the probability from high to low and select a node to be removed
            sortProbabilityAndSelectNodeToRemove();
            if (displaySecrecyProgress){
                sleep();
            }

//            System.out.println("4");
            // Step 4: find the selected node's neighbours and increase their arrested probability
            increaseArrestedNodeNeighbourArrestProbability();
            if (displaySecrecyProgress){
                sleep();
            }


//            System.out.println("5");
            // Step 5: remove the arrested node
            removeArrestedNode();

            // Step 6: if the whole graph is destroyed
            secrecyCount++;
            spriteSecrecy.changeAttribute("ui.label","Attack Times Count: "+secrecyCount);

            if (displaySecrecyProgress){
                sleep();
            }
        }

        return secrecyCount;

    }


    private void findKeyPlayers(int keyPlayersNumber){ // sort nodeInfo
        keyPlayersNum = keyPlayersNumber;

//        System.out.println("Step 1");

        GetKeyPlayers getKeyPlayers = new GetKeyPlayers();
        getKeyPlayers.getKP(theGraph, keyPlayersNumber, findKeyPlayersMethod);

        for (Node node : theGraph.getEachNode()){
            if (node.hasAttribute(findKeyPlayersMethod+"KeyPlayer")){
                node.changeAttribute("ui.class", "keyPlayer");
            }else {
                node.changeAttribute("ui.class", "other");
            }
        }

        if (secrecyCount==0){
            int j = 0;
            for (Node node : theGraph.getEachNode()){
                if (node.hasAttribute(findKeyPlayersMethod+"KeyPlayer")){
                    oldKeyPlayers[j] = node;
                    j++;
                }
            }
        }


        keyPlayers = new Node[keyPlayersNum];
        int j = 0;
        for (Node node : theGraph.getEachNode()){
            if (node.hasAttribute(findKeyPlayersMethod +"KeyPlayer")){
                keyPlayers[j] = node;
                j++;
            }
        }

        int sameKeyPlayerNum = 0;
        for (int i=0; i<keyPlayersNum; i++){
            for (int k=0; k<keyPlayersNum; k++){
                if (oldKeyPlayers[i].equals(keyPlayers[k])){
                    sameKeyPlayerNum++;
                }
            }
        }

        aveKeyPlayerArrestProbability = 0.0;
        if (sameKeyPlayerNum == keyPlayersNum){
            keyPlayersChanged = false;
//            System.out.println("Key players not changed");
        }else {
            keyPlayersChanged = true;
            int l = 0;
            for (Node node : theGraph.getEachNode()){
                if (node.hasAttribute(findKeyPlayersMethod +"KeyPlayer")){
                    oldKeyPlayers[l] = node;
                    aveKeyPlayerArrestProbability += (Double) node.getAttribute(ARREST_PROBABILITY);
                    l++;
                }
            }
            aveKeyPlayerArrestProbability = aveKeyPlayerArrestProbability/keyPlayersNum;
        }
    }

    private void calDistanceToClosestKeyPlayer(){

//        System.out.println("Step 2.1");

        Dijkstra dijkstra = new Dijkstra();
        dijkstra.init(theGraph);


        for (Node key : keyPlayers){
            dijkstra.setSource(key);
            dijkstra.compute();

            for (Node node : theGraph.getEachNode()){
                Double pathLength = dijkstra.getPathLength(node);
                Double shortestPathLength = Math.min((Double) node.getAttribute(SHORTEST_DISTANCE_TO_CLOSEST_KEY_PLAYER), pathLength);
                node.changeAttribute(SHORTEST_DISTANCE_TO_CLOSEST_KEY_PLAYER, shortestPathLength);
            }
        }
    }

    private void setArrestProbability(Double step, Double lowest, String increaseMethod ){

//        System.out.println("Step 2.2");

        if (increaseMethod.equals("None")){
//            System.out.println("Choose None");
            for (Node node : theGraph.getEachNode()){
                if (keyPlayersChanged){
                    if (node.hasAttribute(findKeyPlayersMethod +"KeyPlayer")){
                        node.changeAttribute(ARREST_PROBABILITY, aveKeyPlayerArrestProbability);
                    }else {
                        node.changeAttribute(ARREST_PROBABILITY, aveKeyPlayerArrestProbability + (Double) node.getAttribute(SHORTEST_DISTANCE_TO_CLOSEST_KEY_PLAYER)*step);
                    }
                }else {
                    if (secrecyCount == 0){
                        node.changeAttribute(ARREST_PROBABILITY, lowest + (Double) node.getAttribute(SHORTEST_DISTANCE_TO_CLOSEST_KEY_PLAYER)*step);
                    }
                }
                if (!node.getAttribute("ui.class").equals("other")){
                    node.changeAttribute("ui.label", node.getId()+": "+node.getAttribute(ARREST_PROBABILITY)+"%");
                }else {
                    node.changeAttribute("ui.label", node.getAttribute(ARREST_PROBABILITY)+"%");
                }
            }
            

        }else if (increaseMethod.equals("Linear")){
            for (Node node : theGraph.getEachNode()){
                if (keyPlayersChanged){
                    if (node.hasAttribute(findKeyPlayersMethod +"KeyPlayer")){
                        node.changeAttribute(ARREST_PROBABILITY, aveKeyPlayerArrestProbability);
                    }else {
                        node.changeAttribute(ARREST_PROBABILITY, aveKeyPlayerArrestProbability + (Double) node.getAttribute(SHORTEST_DISTANCE_TO_CLOSEST_KEY_PLAYER)*((Double) node.getAttribute(SHORTEST_DISTANCE_TO_CLOSEST_KEY_PLAYER)+step));
                    }
                }else {
                    if (secrecyCount == 0){
                        node.changeAttribute(ARREST_PROBABILITY, lowest + (Double) node.getAttribute(SHORTEST_DISTANCE_TO_CLOSEST_KEY_PLAYER)*((Double) node.getAttribute(SHORTEST_DISTANCE_TO_CLOSEST_KEY_PLAYER)+step));
                    }
                }
                if (!node.getAttribute("ui.class").equals("other")){
                    node.changeAttribute("ui.label", node.getId()+": "+node.getAttribute(ARREST_PROBABILITY)+"%");
                }else {
                    node.changeAttribute("ui.label", node.getAttribute(ARREST_PROBABILITY)+"%");
                }
            }
        }
    }

    private void sortProbabilityAndSelectNodeToRemove(){

//        System.out.println("Step 3.1");
        Node[] allPlayers = new Node[graphSize+1];
        allPlayers[graphSize] = theGraph.getNode(graphSize-1);
        for (int num=0; num<graphSize; num++){
            allPlayers[num] = theGraph.getNode(num);
        }
        Arrays.sort(allPlayers, new ProbabilityComparator());
        for (Node node : allPlayers){
            if (node.getDegree() >= 1){
                Random random = new Random();
                if (!node.hasAttribute(ARRESTED) && (Double) node.getAttribute(ARREST_PROBABILITY) - random.nextDouble()*100.0 >= 0.0){
                    node.changeAttribute("ui.class", "arrested");
                    node.setAttribute(ARRESTED);
                    node.changeAttribute("ui.label", node.getId()+": "+node.getAttribute(ARREST_PROBABILITY)+"%");
                    break;
                }
            }
        }

    }


    private void increaseArrestedNodeNeighbourArrestProbability() {

        Iterator<Node> arrestedNodeNeighbours = null;

        for (Node node : theGraph.getEachNode()){
            if (node.hasAttribute(ARRESTED)){
                arrestedNodeNeighbours = node.getNeighborNodeIterator();
                break;
            }
        }

        if (arrestedNodeNeighbours != null){
            if (arrestedNodeNeighbours.hasNext()){
                while (arrestedNodeNeighbours.hasNext()){
                    Node neighbour = arrestedNodeNeighbours.next();
                    neighbour.changeAttribute(ARREST_PROBABILITY, (Double) neighbour.getAttribute(ARREST_PROBABILITY) + arrestProbabilityStep);
                    neighbour.changeAttribute("ui.class", "neighbour");
                    neighbour.changeAttribute("ui.label", neighbour.getId()+": "+neighbour.getAttribute(ARREST_PROBABILITY)+"%");
                }
            }
        }

        for (Node node : theGraph.getEachNode()){
            if (!node.getAttribute("ui.class").equals("neighbour") && !node.getAttribute("ui.class").equals("keyPlayer") && !node.hasAttribute(ARRESTED)){
                node.changeAttribute("ui.class", "other");
            }
        }
    }

    private void removeArrestedNode() {

        for (Node node : theGraph.getEachNode()){
            if (node.hasAttribute(ARRESTED)){
                theGraph.removeNode(node);
            }
        }
        graphSize = theGraph.getNodeCount();

    }


    private Boolean isDestroyed(int destroySize){

        Boolean isDestroyed;
        ConnectedComponents cc = new ConnectedComponents();
        cc.init(theGraph);

        if (cc.getConnectedComponentsCount(destroySize+1)>0){
            isDestroyed = false;
            cc.getConnectedComponentsCount();
        }else {
            isDestroyed = true;
        }
        return isDestroyed;
    }

    protected void sleep() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }
    }

}
