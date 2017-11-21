package Controller.ChartGenerate;

import org.graphstream.graph.Graph;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import static oracle.jrockit.jfr.events.Bits.intValue;

public class DiameterLineChart extends ApplicationFrame {
    private DefaultCategoryDataset dataSet = new DefaultCategoryDataset( );

    public DiameterLineChart(String applicationTitle , String chartTitle, Double[] allDiameters, Graph theGraph ) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Diameter","Number of Nodes",
                createDataSet(allDiameters, theGraph),
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
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

        for (int i=0; i< numOfNodeWithDiffDia.length; i++){
            dataSet.addValue(numOfNodeWithDiffDia[i], theGraph.getId(), ""+i);
        }

        return dataSet;
    }
}// class DiameterLineChart
