package Controller.NetworkInformation;

import org.graphstream.graph.Node;

public class NetworkInfo {
    private int order;
    private Node node;

    public void init (){
        order = 0;
        node = null;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }
}
