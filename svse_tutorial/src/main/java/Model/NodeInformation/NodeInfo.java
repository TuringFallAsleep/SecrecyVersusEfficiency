package Model.NodeInformation;

import org.graphstream.graph.Node;

public class NodeInfo {
    // secrecy
    private int order;
    private Node node;
    private Double arrestProbability;
    private Boolean isArrested;
    private Boolean isKeyPlayer;
    private Boolean isRemoved;
    private Integer degree;
    private Double closeness;
    private Double betweenness;
    private Double shortestDistanceToClosestKeyPlayer;

    // efficiency
    private Boolean receivedMessage;


    public void init (){
        // secrecy
        order = 0;
        node = null;
        arrestProbability = 0.0;
        isArrested = false;
        isKeyPlayer = false;
        isRemoved = false;
        degree = 0;
        closeness = 0.0;
        betweenness = 0.0;

        // efficiency
        receivedMessage = false;

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


    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setCloseness(Double closeness) {
        this.closeness = closeness;
    }

    public Double getCloseness() {
        return closeness;
    }

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



    // efficiency


    public void setReceivedMessage(Boolean receivedMessage) {
        this.receivedMessage = receivedMessage;
    }

    public Boolean getReceivedMessage() {
        return receivedMessage;
    }
}
