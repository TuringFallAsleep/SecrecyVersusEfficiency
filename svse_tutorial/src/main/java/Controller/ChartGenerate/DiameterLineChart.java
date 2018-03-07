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

public class DiameterLineChart extends JFrame {
    private JFreeChart lineChart;
    private DefaultCategoryDataset dataSet = new DefaultCategoryDataset( );


    public DiameterLineChart(String applicationTitle , String chartTitle, Double[] allDiameters, Graph theGraph ) {
        super(applicationTitle);
        lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Single Node's Diameter","Number of nodes",
                createDataSet(allDiameters, theGraph),
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private DefaultCategoryDataset createDataSet(Double[] allDia, Graph theGraph) {
        int maxDia = 0;
        for (int i=0; i< allDia.length; i++){
            maxDia = intValue(Math.max(maxDia,allDia[i]));
        }

        int[] numOfNodeWithDiffDia = new int[maxDia+1];
        for (int i=0; i<numOfNodeWithDiffDia.length; i++){
            numOfNodeWithDiffDia[i] = 0;
        }

        for (int i=0; i< allDia.length-1; i++){
            numOfNodeWithDiffDia[intValue(allDia[i])]++;
//            System.out.println("Node: " + i + " has diameter " + allDia[i]);
        }

        // Plot with sum of nodes number with various diameters as x-axis
        for (int i=0; i< numOfNodeWithDiffDia.length; i++){
            dataSet.addValue(numOfNodeWithDiffDia[i], "Graph: " + theGraph.getId() + " with " + theGraph.getNodeCount() + " nodes", ""+i);
        }

//        for (int i=0; i< allDia.length; i++){
//            dataSet.addValue(allDia[i], "Graph: " + theGraph.getId(), ""+i);
//        }

        return dataSet;
    }

    public JFreeChart getChart(){
        return lineChart;
    }

}// class DiameterLineChart
