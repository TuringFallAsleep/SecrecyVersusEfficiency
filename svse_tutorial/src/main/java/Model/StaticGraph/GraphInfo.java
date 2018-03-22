package Model.StaticGraph;

public class GraphInfo {
    private int nodeNum;
    private Double[] allDegree;
    private Double[] allDiameter;
    private Double[] allCloseness;
    private Double[] allBetweenness;
    private Double maxDiameter;
    private Double maxDegree;
    private Double minDegree;
    private Double aveDegree;
    private Double maxCloseness;
    private Double maxBetweenness;
    private Double efficiency;
    private Integer secrecy;

    public void init(int size){
        nodeNum = size;

        allDegree = new Double[size+1];
        allDiameter = new Double[size+1];
        allCloseness = new Double[size+1];
        allBetweenness = new Double[size+1];

        maxDiameter = 0.0;

        maxDegree = 0.0;
        minDegree = 0.0;
        aveDegree = 0.0;

        maxDiameter = 0.0;
        maxCloseness = 0.0;
        maxBetweenness = 0.0;

        efficiency = 0.0;
        secrecy = 0;
    }



    public int getNodeNum() {
        return nodeNum;
    }

    public void setAllDegree(Double[] allDegree) {
        this.allDegree = allDegree;
    }

    public Double[] getAllDegree() {
        return allDegree;
    }

    public void setAllDiameter(Double[] allDiameter) {
        this.allDiameter = allDiameter;
    }

    public Double[] getAllDiameter() {
        return allDiameter;
    }

    public void setAllCloseness(Double[] allCloseness) {
        this.allCloseness = allCloseness;
    }

    public Double[] getAllCloseness() {
        return allCloseness;
    }

    public void setAllBetweenness(Double[] allBetweenness) {
        this.allBetweenness = allBetweenness;
    }

    public void setMaxDiameter(Double maxDiameter) {
        this.maxDiameter = maxDiameter;
    }

    public Double[] getAllBetweenness() {
        return allBetweenness;
    }

    public void setMaxDegree(Double maxDegree) {
        this.maxDegree = maxDegree;
    }

    public void setAveDegree(Double aveDegree) {
        this.aveDegree = aveDegree;
    }

    public void setMinDegree(Double minDegree) {
        this.minDegree = minDegree;
    }

    public void setMaxCloseness(Double maxCloseness) {
        this.maxCloseness = maxCloseness;
    }

    public void setMaxBetweenness(Double maxBetweenness) {
        this.maxBetweenness = maxBetweenness;
    }

    public Double getMaxDiameter() {
        return maxDiameter;
    }

    public Double getMaxDegree() {
        return maxDegree;
    }

    public Double getMinDegree() {
        return minDegree;
    }

    public Double getAveDegree() {
        return aveDegree;
    }

    public Double getMaxCloseness() {
        return maxCloseness;
    }

    public Double getMaxBetweenness() {
        return maxBetweenness;
    }

    public void setEfficiency(Double efficiency) {
        this.efficiency = efficiency;
    }

    public Double getEfficiency() {
        return efficiency;
    }

    public void setSecrecy(Integer secrecy) {
        this.secrecy = secrecy;
    }

    public Integer getSecrecy() {
        return secrecy;
    }
}
