package Controller.Network;

import org.graphstream.stream.SourceBase;
import sun.rmi.rmic.BatchEnvironment;
import sun.rmi.rmic.Generator;
import sun.rmi.rmic.Main;
import sun.tools.java.ClassDefinition;

import java.io.File;

public class MyFullGenerator extends SourceBase implements Generator {

    int currentIndex = 0;
    int edgeId = 0;

    public void begin() {
        addNode();
    }

    public boolean nextEvents() {
        addNode();
        return true;
    }

    public void end() {
        // Nothing to do
    }

    protected void addNode() {
        sendNodeAdded(sourceId, Integer.toString(currentIndex));

        for(int i = 0; i < currentIndex; i++)
            sendEdgeAdded(sourceId, Integer.toString(edgeId++),
                    Integer.toString(i), Integer.toString(currentIndex), false);

        currentIndex++;
    }

    public boolean parseArgs(String[] strings, Main main) {
        return false;
    }

    public void generate(BatchEnvironment batchEnvironment, ClassDefinition classDefinition, File file) {

    }
}
