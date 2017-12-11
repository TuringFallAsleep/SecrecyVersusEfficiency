package Model.NodeInformation;

import org.graphstream.graph.Node;

public class NodeInfo {
    private int order;
    private Node node;
    private Double arrestProbability;
    private Boolean isArrested;
    private Boolean isKeyPlayer;
    private Boolean isRemoved;
    private Double betweenness;
    private Double shortestDistanceToClosestKeyPlayer;

    public void init (){
        order = 0;
        node = null;
        arrestProbability = 0.0;
        isArrested = false;
        isKeyPlayer = false;
        isRemoved = false;
        betweenness = 0.0;
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



    public void setArrestProbability(Double arrestProbability) {
        this.arrestProbability = arrestProbability;
    }

    public Double getArrestProbability() {
        return arrestProbability;
    }



    public void setArrested(Boolean arrested) {
        isArrested = arrested;
    }

    public Boolean isArrested() {
        return isArrested;
    }



    public void setKeyPlayer(Boolean keyPlayer) {
        isKeyPlayer = keyPlayer;
    }

    public Boolean isKeyPlayer() {
        return isKeyPlayer;
    }


    public void setRemoved(Boolean removed) { isRemoved = removed; }

    public Boolean isRemoved() { return isRemoved; }



    public void setBetweenness(Double betweenness) {
        this.betweenness = betweenness;
    }

    public Double getBetweenness() {
        return betweenness;
    }

    public void setShortestDistanceToClosestKeyPlayer(Double shortestDistanceToClosestKeyPlayer) {
        this.shortestDistanceToClosestKeyPlayer = shortestDistanceToClosestKeyPlayer;
    }

    public Double getShortestDistanceToClosestKeyPlayer() {
        return shortestDistanceToClosestKeyPlayer;
    }
}
