package Model.NodeInformation;


import org.graphstream.graph.Node;

import java.util.Comparator;

public class ProbabilityComparator implements Comparator<Node> {
    @Override
    public int compare(Node n1, Node n2){
        return Double.compare((Double) n2.getAttribute("ArrestProbability"), (Double) n1.getAttribute("ArrestProbability"));
    }
}
