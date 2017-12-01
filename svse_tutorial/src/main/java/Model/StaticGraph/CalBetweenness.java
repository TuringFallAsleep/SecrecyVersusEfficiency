package Model.StaticGraph;

import org.graphstream.algorithm.BetweennessCentrality;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSourceDGS;

import java.util.Arrays;

public class CalBetweenness {

    int graphSize;
    Double[][] betweennessResult;
    Double[] sortResultSToL;
    Double[] sortResultLToS;
    String[] nodeID;
    String[] sortNodeID;
    Node[] keyPlayers;
    int keyPlayersNumber;
    Graph graph;



//    public void keyPlayersNum(int keyPlayersNum){
//        keyPlayersNumber = keyPlayersNum;
//    }

    public Double[][] CalBetweenness(Graph theGraph){
        graph = theGraph;
        //      Cal betweenness

        FileSourceDGS source = new FileSourceDGS();
        source.addSink(theGraph);

        graphSize = theGraph.getNodeCount();

        betweennessResult = new Double[2][graphSize];
//        betweenessResult[0][0]: the max diameter
//        betweenessResult[1][i]: the distribution of diameter
        sortResultSToL = new Double[graphSize];
        nodeID = new String[graphSize];
        sortNodeID = new String[graphSize];


        for (int i=0; i<betweennessResult.length; i++){
            for (int j=0; j<betweennessResult[0].length; j++){
                betweennessResult[i][j] = 0.0;
            }
        }

        for (int i=0; i<nodeID.length;i++){
            nodeID[i] = "";
            sortResultSToL[i] = 0.0;
        }


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
            nodeID[nodeNum] = n.getId().toString();

            sortResultSToL[nodeNum] = betweenness;
            nodeNum++;
        }


//        getSortResult(3);
//        getSortNodeID();


        return betweennessResult;

    }

    public String[] getNodeID() {
        return nodeID;
    }

    public Double[][] getBetweenessResult(){
        return betweennessResult;
    }

    public Double[] getSortResult(int keyPlayersNum) {
        keyPlayersNumber = keyPlayersNum;
        Arrays.sort(sortResultSToL);
        sortResultLToS = new Double[keyPlayersNum];
        int j = 0;
        for (int i = sortResultSToL.length - 1; i >= 0; i--){
            if (j<keyPlayersNum){
                sortResultLToS[j] = sortResultSToL[i];
                j++;
            }else{
                break;
            }
        }
        System.out.println("sort large to small: "+Arrays.toString(sortResultLToS));
        return sortResultLToS;
    }

    public String[] getSortNodeID() {
        // use it after getSortResult(keyPlayersNum)
        sortNodeID = new String[keyPlayersNumber];
        keyPlayers = new Node[keyPlayersNumber];

        for (int i = 0; i< keyPlayersNumber; i++) {
            int j = 0;
            for (Node n : graph.getEachNode()){
                if (sortResultLToS[i].equals(betweennessResult[1][j])){
                    sortNodeID[i] = n.getId().toString();
                    keyPlayers[i] = n;
//                    System.out.println("keyPlayers: "+keyPlayers[i].getId().toString());
                }
//                System.out.println("j = "+ j+ " ID: "+ n.getId());
                j++;
            }
        }
        System.out.println("Sort node ID: "+Arrays.toString(sortNodeID));
//        System.out.println(betweennessResult[1][46]); // this confirm the betweennessResult doesn't ordered by original order
        return sortNodeID;
    }

    public Node[] getKeyPlayers() {
        return keyPlayers;
    }
}
