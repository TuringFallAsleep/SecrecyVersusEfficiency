package Model.StaticGraph;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.*;
import org.graphstream.stream.file.FileSourceDGS;

public class GraphCal implements Algorithm {
    Graph theGraph;
    Double minDegree, maxDegree, avgDegree;
    Double maxDiameter;
    Double maxBetweenness;
    Double[] allNodesDeg;
    Double[] allDiameters;
    Double[] allBetweenness;
    Double[][] diameterResult;
    Double[][] betweennessResult;
    int graphSize;
    public void init(Graph graph){
        theGraph = graph;
    }

    public void compute(){


        avgDegree = 0.0;
        minDegree = Double.MAX_VALUE;
        maxDegree = 0.0;

//        maxDiameter = 0.0;
        FileSourceDGS source = new FileSourceDGS();
        source.addSink(theGraph);

        graphSize = theGraph.getNodeCount()+1;
        allNodesDeg = new Double[graphSize];
        diameterResult = new Double[2][graphSize];
        betweennessResult = new Double[2][graphSize];

        for (int i=0; i<allNodesDeg.length; i++){
            allNodesDeg[i] = 0.0;
        }

        for (Node n : theGraph.getEachNode()){
            // compute degree
            int deg = n.getDegree();
//            System.out.println("deg is "+deg);
            minDegree = Math.min(minDegree,deg);
            maxDegree = Math.max(maxDegree,deg);
            avgDegree += deg;
            allNodesDeg[deg]++;

        }

        CalDiameter calDia = new CalDiameter();
        diameterResult = calDia.CalDiameter(theGraph);

        maxDiameter = diameterResult[0][0];
        allDiameters = diameterResult[1];


        CalBetweenness calBet = new CalBetweenness();
        betweennessResult = calBet.CalBetweenness(theGraph);

        maxBetweenness = betweennessResult[0][0];
        allBetweenness = betweennessResult[1];


        avgDegree /= theGraph.getNodeCount();

    }

    public Double getMaxDegree() { return maxDegree; }
    public Double getMinDegree() {
        return minDegree;
    }
    public Double getAvgDegree() {
        return avgDegree;
    }
    public Double getMaxDiameter() { return maxDiameter; }
    public Double getMaxBetweenness() { return maxBetweenness; }

    public Double[] getAllNodesDeg() { return allNodesDeg; }
    public Double[] getAllDiameters() { return allDiameters; }
    public Double[] getAllBetweenness() { return allBetweenness; }
}
