package Model.StaticGraph;

import org.graphstream.algorithm.BetweennessCentrality;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSourceDGS;

public class CalBetweenness {
    int graphSize;

    public Double[][] CalBetweenness(Graph theGraph){
        //      Cal betweenness

        FileSourceDGS source = new FileSourceDGS();
        source.addSink(theGraph);

        graphSize = theGraph.getNodeCount()+1;

        Double[][] betweenessResult = new Double[2][graphSize];
//        betweenessResult[0][0]: the max diameter
//        betweenessResult[1][i]: the distribution of diameter


        for (int i=0; i<betweenessResult.length; i++){
            for (int j=0; j<betweenessResult[0].length; j++){
                betweenessResult[i][j] = 0.0;
            }
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
            betweenessResult[0][0] = Math.max(betweenessResult[0][0],betweenness);
            betweenessResult[1][nodeNum] = betweenness;

            nodeNum++;
        }


        return betweenessResult;

    }
}
