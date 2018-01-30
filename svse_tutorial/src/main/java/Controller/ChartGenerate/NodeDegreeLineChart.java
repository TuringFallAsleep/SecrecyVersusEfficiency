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
    private DefaultCategoryDataset dataSet = new DefaultCategoryDataset( );


    public NodeDegreeLineChart(String applicationTitle , String chartTitle, Double[] allNodesDegree, Graph theGraph ) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Node Degree","Number of Nodes",
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

            if (allNodesDeg.length<1/segment){
                dataSet.addValue(intValue(allNodesDeg[i]),theGraph.getId(), ""+i);
            }else{
                if (j < allNodesDeg.length*segment){
                    rangeSumValue += intValue(allNodesDeg[i]);
                    j++;
                    if (i == allNodesDeg.length){
                        dataSet.addValue(rangeSumValue,theGraph.getId(),""+i);
                        rangeSumValue = 0;
                    }
                }else {
                    dataSet.addValue(rangeSumValue,theGraph.getId(), ""+i);
                    j = 0;
                    rangeSumValue = 0;
                }
            }
        }
        return dataSet;
    }
}// class NodeDegreeLineChart

