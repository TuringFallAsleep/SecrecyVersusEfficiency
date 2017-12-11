package Model.NodeInformation;

import java.util.Comparator;

public class PathLengthComparator implements Comparator<NodeInfo> {
    @Override
    public int compare(NodeInfo n1, NodeInfo n2){
        return Double.compare(n1.getShortestDistanceToClosestKeyPlayer(), n2.getShortestDistanceToClosestKeyPlayer());
        // small to large
    }
}
