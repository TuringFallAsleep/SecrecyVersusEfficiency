package Model.NodeInformation;

import java.util.Comparator;

public class DegreeComparator implements Comparator<NodeInfo> {
    @Override
    public int compare(NodeInfo n1, NodeInfo n2){
        return Double.compare(n2.getDegree(), n1.getDegree());
        // large to small
    }
}
