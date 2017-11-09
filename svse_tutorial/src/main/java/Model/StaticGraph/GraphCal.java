package Model.StaticGraph;

import org.graphstream.algorithm.APSP;
import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.*;
import org.graphstream.stream.file.FileSourceDGS;

public class GraphCal implements Algorithm {
    Graph theGraph;
    Double minDegree, maxDegree, avgDegree;
    Double maxDiameter;
    Double[] allNodesDeg;
    Double[] allDiameters;
    int graphSize;
    public void init(Graph graph){
        theGraph = graph;
    }

    public void compute(){


        avgDegree = 0.0;
        minDegree = Double.MAX_VALUE;
        maxDegree = 0.0;

        maxDiameter = 0.0;
        FileSourceDGS source = new FileSourceDGS();
        source.addSink(theGraph);

        graphSize = theGraph.getNodeCount()+1;
        allNodesDeg = new Double[graphSize];
        for (int i=0; i<allNodesDeg.length; i++){
            allNodesDeg[i] = 0.0;
        }
        allDiameters = new Double[graphSize];
        for (int i=0; i<allDiameters.length; i++){
            allDiameters[i] = 0.0;
        }

//        source.readAll(); ?????
        APSP apsp = new APSP();
        apsp.init(theGraph);
        apsp.setDirected(false);
        apsp.setWeightAttributeName("weight");
        apsp.compute();

        int nodeNum = 0;
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
                    Double diameter = (double)info.getShortestPathTo(m.getId()).size()-1;
                    // When the node is itself, it will goes out then back, i.e. it will be 2.
                    allDiameters[nodeNum] = Math.max(allDiameters[nodeNum],diameter);
                }
            }
//            System.out.println("Node: " + nodeNum + " has diameter " + allDiameters[nodeNum]);
            nodeNum++;

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
    public Double getMaxDiameter() { return maxDiameter; }
    public Double[] getAllNodesDeg() { return allNodesDeg; }
    public Double[] getAllDiameters() { return allDiameters; }

}
