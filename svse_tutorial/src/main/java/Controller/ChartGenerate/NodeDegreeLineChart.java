package Controller.ChartGenerate;

import org.graphstream.graph.Graph;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;

import static oracle.jrockit.jfr.events.Bits.intValue;

public class NodeDegreeLineChart extends JFrame {
    private JFreeChart lineChart;
    private DefaultCategoryDataset dataSet = new DefaultCategoryDataset( );


    public NodeDegreeLineChart(String applicationTitle , String chartTitle, Double[] allNodesDegree, Graph theGraph ) {
        super(applicationTitle);
        lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Single Node's Degree","Number of Nodes",
                createDataSet(allNodesDegree, theGraph),
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    int rangeSumValue = 0;
    double segment = 0.05;
    int j = 0;
    private DefaultCategoryDataset createDataSet(Double[] allNodesDeg, Graph theGraph) {
        for (int i=0; i< allNodesDeg.length; i++){

            if (allNodesDeg.length<1.0/segment){
                dataSet.addValue(intValue(allNodesDeg[i]),"Graph: " + theGraph.getId() + " with " + theGraph.getNodeCount() + " nodes", ""+i);
            }else{
                if (j < allNodesDeg.length*segment){
                    rangeSumValue += intValue(allNodesDeg[i]);
                    j++;
                    if (i == allNodesDeg.length){
                        dataSet.addValue(rangeSumValue,"Graph: " + theGraph.getId() + " with " + theGraph.getNodeCount() + " nodes",""+i);
                        rangeSumValue = 0;
                    }
                }else {
                    dataSet.addValue(rangeSumValue,"Graph: " + theGraph.getId() + " with " + theGraph.getNodeCount() + " nodes", ""+i);
                    j = 0;
                    rangeSumValue = 0;
                }
            }
        }
        return dataSet;
    }

    public JFreeChart getChart(){
        return lineChart;
    }

}// class NodeDegreeLineChart

