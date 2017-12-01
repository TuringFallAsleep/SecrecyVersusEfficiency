package Model.StaticGraph;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.Arrays;
import java.util.Random;

public class CalSecrecy {

    /* It may be time consuming */

    private Graph theGraph;
    private Double[][] betweenessResult;
    private Double[] sortResult;
    private String[] nodeID;
    private String[] sortNodeID;
    private Node[] keyPlayers;
    private CalBetweenness calBetweenness;
    public int keyPlayersNum = 3;
    private Double[] shortestDistance;
    private Double[] arrestProbability;
    public Double arrestProbabilitySteps;
    public Double lowestArrestProbability;

    public int secrecyByBetweenness(Graph graph){
        theGraph = graph;
        /* Return count steps of removing nodes and edges until the graph is destroyed */
        int step = 0;

        calBetweenness = new CalBetweenness();
        betweenessResult = calBetweenness.CalBetweenness(theGraph);
        nodeID = calBetweenness.getNodeID();

        // Step 1: find key players
        findKeyPlayers(keyPlayersNum);

        // Step 2.1: calculate the distance from every node to the closest key players
        calDistanceToClosestKeyPlayer();
        // Step 2.2: set probability based on the distance to the closest key players
        arrestProbabilitySteps = 0.1;
        lowestArrestProbability = 0.1;
        setArrestProbability(arrestProbabilitySteps,lowestArrestProbability);

        // Step 3.1: sort the probability from high to low
        sortProbability();
        // Step 3.2: select a node to be removed





        return step;

    }


    private void findKeyPlayers(int keyPlayersNum){
        sortResult = calBetweenness.getSortResult(keyPlayersNum);
        sortNodeID = calBetweenness.getSortNodeID();
    }

    private void calDistanceToClosestKeyPlayer(){
        Dijkstra dijkstra = new Dijkstra();
        dijkstra.init(theGraph);
        keyPlayers = calBetweenness.getKeyPlayers();
        shortestDistance = new Double[theGraph.getNodeCount()];
        for (int i=0; i<theGraph.getNodeCount(); i++){
            shortestDistance[i] = Double.MAX_VALUE;
        }
        for (Node key : keyPlayers){
            dijkstra.setSource(key);
            dijkstra.compute();
            int i = 0;
            for (Node node : theGraph){
                Double pathLength = dijkstra.getPathLength(node);
                shortestDistance[i] = Math.min(shortestDistance[i], pathLength);
                i++;
            }
        }
        System.out.println("shortest distance: " + Arrays.toString(shortestDistance));
    }

    private void setArrestProbability(Double step, Double lowest ){
        arrestProbability = new Double[theGraph.getNodeCount()];
//        CalDiameter diameter = new CalDiameter();
//        Double maxDistance = diameter.getMaxDiameter();
        for (int i=0; i<arrestProbability.length; i++){
            arrestProbability[i] = shortestDistance[i]/(100*step)+lowest;
        }

        System.out.println("arrest probability: " + Arrays.toString(arrestProbability));
    }

    private String[] sortProbability(int size, String[] source){
        String[] result = new String[size];

        // to do a quick sort algorithm here
        result = source;

        return result;
    }

    private void sortProbability(){
        nodeID = calBetweenness.getNodeID();

        // I need a "struct" here
        sortNodeID = sortProbability(theGraph.getNodeCount(),nodeID);
        sortResult = arrestProbability;
    }

    private void selectNodeToRemove(){
        for (int i=0; i<theGraph.getNodeCount();i++){
            Random random = new Random();
            if (sortResult[i] - random.nextDouble() >= 0){
                // select the node
                break;
            }
        }
    }

}
