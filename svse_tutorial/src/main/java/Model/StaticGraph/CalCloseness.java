package Model.StaticGraph;

import org.graphstream.algorithm.measure.ClosenessCentrality;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSourceDGS;

import static org.graphstream.algorithm.measure.ClosenessCentrality.DEFAULT_ATTRIBUTE_KEY;

public class CalCloseness {
    int graphSize;

    public Double[][] CalCloseness(Graph theGraph){
        //      Cal closeness

        FileSourceDGS source = new FileSourceDGS();
        source.addSink(theGraph);

        graphSize = theGraph.getNodeCount()+1;

        Double[][] closenessResult = new Double[2][graphSize];
//        closenessResult[0][0]: the max diameter
//        closenessResult[1][i]: the distribution of diameter


        for (int i=0; i<closenessResult.length; i++){
            for (int j=0; j<closenessResult[0].length; j++){
                closenessResult[i][j] = 0.0;
            }
        }


        ClosenessCentrality closenessCentrality = new ClosenessCentrality();
//        betweennessCentrality.setWeightAttributeName("weight");
        closenessCentrality.init(theGraph);
        closenessCentrality.compute();

        int nodeNum = 0;
        for (Node n : theGraph.getEachNode()){

            Double closeness = n.getAttribute(DEFAULT_ATTRIBUTE_KEY);

//            System.out.println("Node "+n.getId()+" has closeness centrality "+String.format("%.5f", closeness));
            closenessResult[0][0] = Math.max(closenessResult[0][0],closeness);
            closenessResult[1][nodeNum] = closeness;

            nodeNum++;
        }


        return closenessResult;

    }
}
