package Model.NodeInformation;

import java.util.Comparator;

public class ClosenessComparator implements Comparator<NodeInfo> {
    @Override
    public int compare(NodeInfo n1, NodeInfo n2){
        return Double.compare(n2.getCloseness(), n1.getCloseness());
        // large to small
    }
}
