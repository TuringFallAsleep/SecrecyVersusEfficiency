package Model.StaticGraph;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.Viewer;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class CalEfficiency {

    private Double efficiency;
    private int pass;
    private int graphSize;
    private Graph theGraph;

    private Sprite spriteEfficiency;

    private static final String RECEIVED = "Received";
    private static final String RENDERED = "Rendered";

    public Double DeliverMessage(Graph graph, Double hoursPerPass,Boolean displayEfficiencyProgress){

        int selectedNode;
        Graphs copy = new Graphs();
        theGraph = copy.clone(graph);
        efficiency = 0.0;
        pass = 0;
        if (graph==null){
            System.out.println("In DeliverMessage: NULL graph");
        }
        graphSize = theGraph.getNodeCount();

        SpriteManager smStart = new SpriteManager(theGraph);
        spriteEfficiency = smStart.addSprite("spriteEfficiency");

        spriteEfficiency.setPosition(StyleConstants.Units.PX, 80, 20, 0);
        spriteEfficiency.addAttribute("ui.style","fill-color: white;");
        spriteEfficiency.addAttribute("ui.label","Efficiency Steps Count: "+pass);

        if (displayEfficiencyProgress){
            theGraph.addAttribute("ui.stylesheet", "url('./efficiency.css')");
            Viewer viewer = theGraph.display();
            viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);

        }

        // 1. Select a node, give it a piece of message
        selectedNode = SelectNode(displayEfficiencyProgress);

        theGraph.getNode(selectedNode).addAttribute("ui.class", "selected");
        theGraph.getNode(selectedNode).setAttribute(RENDERED);
        theGraph.getNode(selectedNode).changeAttribute("Layer", 0);
        theGraph.getNode(selectedNode).setAttribute(RECEIVED);
        if (displayEfficiencyProgress){
            sleep();
        }
        if (theGraph.getNode(selectedNode).getDegree()==0){
            pass = pass + 10000;
        }

        // 2. Pass the message to its neighbour, pass time++, until all nodes receive that message
        PassMessageToNeighbour(selectedNode, (Integer) theGraph.getNode(selectedNode).getAttribute("Layer"));

        int renderLayer = 1;
        Boolean allRendered;
        do {
            allRendered = true;
            for (Node node : theGraph.getEachNode()){
                if (node.getAttribute("Layer").equals(renderLayer)){
                    node.addAttribute("ui.class", "received");
                    node.setAttribute(RENDERED);
                }
            }
            for (Node node : theGraph.getEachNode()){
                if (!node.hasAttribute(RENDERED)){
                    allRendered = false;
                }
            }
            renderLayer++;
            pass++;
            spriteEfficiency.changeAttribute("ui.label","Efficiency Steps Count: "+pass);
            if (displayEfficiencyProgress){
                sleep();
            }
        }while (!allRendered);

        // 3. calculate the time
        efficiency = pass * hoursPerPass;
//        System.out.println("All nodes received message, which cost "+efficiency+" hours");

        return efficiency;
    }


    private int SelectNode(Boolean displayEfficiencyProgress) {

        if (displayEfficiencyProgress){
            sleep();
        }

        for (Node n : theGraph.getEachNode()){
            n.setAttribute("Layer",Integer.MAX_VALUE);
        }

        int randomNum = ThreadLocalRandom.current().nextInt(0, graphSize);

        return randomNum;
    }


    private void PassMessageToNeighbour(int currentNode, int currentLayer){

        Iterator<Node> neighborNodeIterator = theGraph.getNode(currentNode).getNeighborNodeIterator();
        int nextLayer = currentLayer + 1;

        while (neighborNodeIterator.hasNext()){
            Node currentNeighbour = neighborNodeIterator.next();
            if ((Integer) currentNeighbour.getAttribute("Layer") > nextLayer){ // if (currentNeighbour's layer is larger, then give it new layer)
                currentNeighbour.setAttribute(RECEIVED);
                currentNeighbour.changeAttribute("Layer",nextLayer);
            }
        } // first layer neighbour

        for (Node node : theGraph.getEachNode()){
            if (node.hasAttribute(RECEIVED)){ // current node's neighbour
                if ((Integer)node.getAttribute("Layer") == nextLayer){
                    PassMessageToNeighbour(node.getIndex(), nextLayer);
                }
            }
        }
    }


    protected void sleep() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {

        }
    }

}


