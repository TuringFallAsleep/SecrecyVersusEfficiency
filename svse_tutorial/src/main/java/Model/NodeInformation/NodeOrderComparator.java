package Model.NodeInformation;

import java.util.Comparator;

public class NodeOrderComparator implements Comparator<NodeInfo> {
    @Override
    public int compare(NodeInfo n1, NodeInfo n2){
        return Double.compare(n1.getOrder(), n2.getOrder());

//        return Double.compare(Double.valueOf(n1.getNode().getIndex()), Double.valueOf(n2.getNode().getIndex()));
        // small to large

    }
}
