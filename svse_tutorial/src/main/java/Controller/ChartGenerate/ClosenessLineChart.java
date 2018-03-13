package Controller.ChartGenerate;

import org.graphstream.graph.Graph;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;

import static oracle.jrockit.jfr.events.Bits.intValue;

public class ClosenessLineChart extends JFrame {

    private JFreeChart lineChart;
    private DefaultCategoryDataset dataSet = new DefaultCategoryDataset( );




    public ClosenessLineChart(String applicationTitle , String chartTitle, Double[] allCloseness, Graph theGraph ) {
        super(applicationTitle);
        lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Single Node's Closeness","Number of nodes",
                createDataSet(allCloseness, theGraph),
                PlotOrientation.VERTICAL,
                true,true,false);

        CategoryPlot plot = lineChart.getCategoryPlot();
        // y axis to int
        NumberAxis yAxis = (NumberAxis)plot.getRangeAxis();
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }


    int rangeSumValue = 0;
    double segment = 0.05;
    int j = 0;
    private DefaultCategoryDataset createDataSet(Double[] allClo, Graph theGraph) {

        for (int i=0; i<allClo.length; i++){
            allClo[i] *= 1000.0;
        }

        // find betMax
        int cloMax = 0;
        for (int i=0; i<allClo.length;i++){
            if (cloMax<allClo[i]){
                cloMax = intValue(allClo[i]);
            }
        }




        int[] numOfNodeWithDiffClo = new int[cloMax+1];
        for (int i=0; i<allClo.length;i++){
            numOfNodeWithDiffClo[intValue(allClo[i])]++;
        }



        for (int i=0; i<numOfNodeWithDiffClo.length-1; i++){
            if (numOfNodeWithDiffClo.length<1.0/segment){
                dataSet.addValue(numOfNodeWithDiffClo[i],"Graph"+theGraph.getId()+" with "+theGraph.getNodeCount()+" nodes",""+(double)i/1000);
            }else {
                if (j<numOfNodeWithDiffClo.length*segment){
                    rangeSumValue += numOfNodeWithDiffClo[i];
                    j++;
                    if (i == numOfNodeWithDiffClo.length-2){
                        dataSet.addValue(rangeSumValue,"Graph"+theGraph.getId()+" with "+theGraph.getNodeCount()+" nodes",""+(double)i/1000);
                        rangeSumValue = 0;
                    }
                }else {
                    dataSet.addValue(rangeSumValue,"Graph"+theGraph.getId()+" with "+theGraph.getNodeCount()+" nodes",""+(double)i/1000);
                    j = 0;
                    rangeSumValue = 0;
                }
            }
        }



//        for (int i=0; i< allClo.length; i++){
//            dataSet.addValue(intValue(allClo[i]*1000.0),"Graph: "+theGraph.getId(), ""+i);
//        }
        return dataSet;
    }

    public JFreeChart getChart(){
        return lineChart;
    }

}// class ClosenessLineChart