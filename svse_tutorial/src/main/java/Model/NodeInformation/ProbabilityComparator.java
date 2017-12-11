package Model.NodeInformation;

import java.util.Comparator;

public class ProbabilityComparator implements Comparator<NodeInfo> {
    @Override
    public int compare(NodeInfo n1, NodeInfo n2){
        return Double.compare(n2.getArrestProbability(), n1.getArrestProbability());
    }
}
