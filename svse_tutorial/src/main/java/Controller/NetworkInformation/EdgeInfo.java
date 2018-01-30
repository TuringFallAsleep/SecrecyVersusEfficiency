package Controller.NetworkInformation;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

public class EdgeInfo {
    private int order;
    private Edge edge;
    private Node node0;
    private Node node1;
    private Boolean hasBeenMoved;

    public void init (){
        order = 0;
        edge = null;
        node0 = null;
        node1 = null;
        hasBeenMoved = false;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    public Edge getEdge() {
        return edge;
    }

    public void setNode0(Node node0) {
        this.node0 = node0;
    }

    public Node getNode0() {
        return node0;
    }

    public void setNode1(Node node1) {
        this.node1 = node1;
    }

    public Node getNode1() {
        return node1;
    }

    public void setHasBeenMoved(Boolean hasBeenMoved) {
        this.hasBeenMoved = hasBeenMoved;
    }

    public Boolean getHasBeenMoved() {
        return hasBeenMoved;
    }
}
