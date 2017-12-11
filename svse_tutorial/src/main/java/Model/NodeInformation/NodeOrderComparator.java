package Model.NodeInformation;

import java.util.Comparator;

public class NodeOrderComparator implements Comparator<NodeInfo> {
    @Override
    public int compare(NodeInfo n1, NodeInfo n2){
        return Double.compare(n1.getOrder(), n2.getOrder());
        // small to large
    }
}
