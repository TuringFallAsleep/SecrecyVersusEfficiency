package Model.StaticGraph;

import org.graphstream.algorithm.APSP;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSourceDGS;

public class CalDiameter {

    int graphSize;

    public Double[][] CalDiameter(Graph theGraph){
        //      Cal diameter

        FileSourceDGS source = new FileSourceDGS();
        source.addSink(theGraph);

        graphSize = theGraph.getNodeCount()+1;

        Double[][] diameterResult = new Double[2][graphSize];
//        diameterResult[0][0]: the max diameter
//        diameterResult[1][i]: the distribution of diameter

//        System.out.println("diameterResult[0].length = "+diameterResult[0].length);

        for (int i=0; i<diameterResult.length; i++){
            for (int j=0; j<diameterResult[0].length; j++){
                diameterResult[i][j] = 0.0;
            }
        }

        APSP apsp = new APSP();
        apsp.init(theGraph);
        apsp.setDirected(false);
//        apsp.setWeightAttributeName("weight");
        apsp.compute();

        int nodeNum = 0;
        for (Node n : theGraph.getEachNode()){

            APSP.APSPInfo info = n.getAttribute(APSP.APSPInfo.ATTRIBUTE_NAME);

            for (Node m : theGraph.getEachNode()){
                if (info.getShortestPathTo(m.getId())==null){
//                    System.out.println("Isolated node");
                }else{
//                    String shortestPath = info.getShortestPathTo(m.getId()).toString();
                    Double diameter = (double)info.getShortestPathTo(m.getId()).size()-1;
                    // When the node is itself, it will goes out then back, i.e. it will be 2.
                    diameterResult[1][nodeNum] = Math.max(diameterResult[1][nodeNum],diameter);
//                    System.out.println(shortestPath + " " + shortestPathLength);
                    diameterResult[0][0] = Math.max(diameterResult[0][0],diameter);
                }
            }
//            System.out.println("Node: " + nodeNum + " has diameter " + allDiameters[nodeNum]);
            nodeNum++;

        }


        return diameterResult;

    }
}
