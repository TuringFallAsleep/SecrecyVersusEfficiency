package Model.StaticGraph;

import org.graphstream.algorithm.APSP;
import org.graphstream.algorithm.Algorithm;
import org.graphstream.algorithm.BetweennessCentrality;
import org.graphstream.graph.*;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceDGS;

public class GraphCal implements Algorithm {
    Graph theGraph;
    Double minDegree, maxDegree, avgDegree;
    Double maxMinLength;
    Double[] allNodesDeg;
    int graphSize;
    public void init(Graph graph){
        theGraph = graph;
    }

    public void compute(){


        avgDegree = 0.0;
        minDegree = Double.MAX_VALUE;
        maxDegree = 0.0;

        maxMinLength = 0.0;
        FileSourceDGS source = new FileSourceDGS();
        source.addSink(theGraph);

        graphSize = theGraph.getNodeCount();
        allNodesDeg = new Double[graphSize+1];
        for (int i=0; i<allNodesDeg.length; i++){
            allNodesDeg[i] = 0.0;
        }
//        source.readAll(); ?????
        APSP apsp = new APSP();
        apsp.init(theGraph);
        apsp.setDirected(false);
        apsp.setWeightAttributeName("weight");
        apsp.compute();

        for (Node n : theGraph.getEachNode()){
            // compute degree
            int deg = n.getDegree();
//            System.out.println("deg is "+deg);
            minDegree = Math.min(minDegree,deg);
            maxDegree = Math.max(maxDegree,deg);
            avgDegree += deg;
            allNodesDeg[deg]++;


            APSP.APSPInfo info = n.getAttribute(APSP.APSPInfo.ATTRIBUTE_NAME);
//            System.out.println(info.getShortestPathTo("A"));
            for (Node m : theGraph.getEachNode()){
                if (info.getShortestPathTo(m.getId())==null){
//                    System.out.println("Isolated node");
                }else{
//                    String shortestPath = info.getShortestPathTo(m.getId()).toString();
                    Double shortestPathLength = (double)info.getShortestPathTo(m.getId()).size();
//                    System.out.println(shortestPath + " " + shortestPathLength);
                    maxMinLength = Math.max(maxMinLength,shortestPathLength);
                }

            }

        }

        avgDegree /= theGraph.getNodeCount();



    }

    public Double getMaxDegree() { return maxDegree; }
    public Double getMinDegree() {
        return minDegree;
    }
    public Double getAvgDegree() {
        return avgDegree;
    }
    public Double getMaxMinLength() { return maxMinLength-1; }
    public Double[] getAllNodesDeg() { return allNodesDeg; }

}
