package Controller.Network;

import Controller.NetworkInformation.NetworkInfo;
import Model.StaticGraph.CalSecrecy;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class CovertNetwork {

    private Graph graph;
    private int numOfNode = 60;
    private int secrecyCount = Integer.MAX_VALUE;
    private int graphSize;
    private NetworkInfo networkInfo[];


    public void betweennessNet(){
        int secrecyTemp;


        // Step 1: Generate a Preferential Attachment with Bernoulli graph
        generateGraph();


        // Step 2: Move an edge from one place to another





        CalSecrecy calSecrecy  = new CalSecrecy();
        secrecyTemp = calSecrecy.secrecyByBetweenness(graph);

        secrecyCount = Math.min(secrecyCount,secrecyTemp);

        System.out.println(secrecyCount);

        graph.display();

    }

    public void generateGraph(){
        graph = new SingleGraph("Preferential Attachment with Bernoulli");
        // Between 1 and 3 new links per node added.
        Generator gen = new BarabasiAlbertGenerator(3,false);
        gen.addSink(graph);
        gen.begin();
        for(int i=0; i<numOfNode; i++) {
            gen.nextEvents();
        }
        gen.end();
        graphSize = graph.getNodeCount();

        networkInfo = new NetworkInfo[graphSize];
        for (int i=0; i<graphSize;i++){
            networkInfo[i] = new NetworkInfo();
            networkInfo[i].init();
            networkInfo[i].setOrder(i);
        }

        int nodeNum = 0;
        for (Node n : graph.getEachNode()){
            networkInfo[nodeNum].setNode(n);
            nodeNum++;
        }
    }
}
