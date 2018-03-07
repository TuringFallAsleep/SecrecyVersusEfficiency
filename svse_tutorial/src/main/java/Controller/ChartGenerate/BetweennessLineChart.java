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

public class BetweennessLineChart extends JFrame {
    private JFreeChart lineChart;
    private DefaultCategoryDataset dataSet = new DefaultCategoryDataset( );


    public BetweennessLineChart(String applicationTitle , String chartTitle, Double[] allBetweenness, Graph theGraph ) {
        super(applicationTitle);
        lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Single Node's Betweenness","Number of Nodes",
                createDataSet(allBetweenness, theGraph),
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
    private DefaultCategoryDataset createDataSet(Double[] allBet, Graph theGraph) {
        // find betMax
        int betMax = 0;
        for (int i=0; i<allBet.length;i++){
            if (betMax<allBet[i]){
                betMax = intValue(allBet[i]);
            }
        }

        if (betMax>1000)
            segment = 0.07;
        else if (betMax>2000)
            segment = 0.1;

        int[] numOfNodeWithDiffBet = new int[betMax+1];
        for (int i=0; i<allBet.length;i++){
            numOfNodeWithDiffBet[intValue(allBet[i])]++;
        }

        for (int i=0; i<numOfNodeWithDiffBet.length; i++){
            if (numOfNodeWithDiffBet.length<1.0/segment){
                dataSet.addValue(numOfNodeWithDiffBet[i],"Graph"+theGraph.getId()+" with "+theGraph.getNodeCount()+" nodes",""+i);
            }else {
                if (j<numOfNodeWithDiffBet.length*segment){
                    rangeSumValue += numOfNodeWithDiffBet[i];
                    j++;
                    if (i == numOfNodeWithDiffBet.length-1){
                        dataSet.addValue(rangeSumValue,"Graph"+theGraph.getId()+" with "+theGraph.getNodeCount()+" nodes",""+i);
                        rangeSumValue = 0;
                    }
                }else {
                    dataSet.addValue(rangeSumValue,"Graph"+theGraph.getId()+" with "+theGraph.getNodeCount()+" nodes",""+i);
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

}// class BetweennessLineChart

