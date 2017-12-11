package Model.NodeInformation;

import java.util.Comparator;

public class BenweennessComparator implements Comparator<NodeInfo> {
    @Override
    public int compare(NodeInfo n1, NodeInfo n2){
        return Double.compare(n2.getBetweenness(), n1.getBetweenness());
        // large to small
    }
}
