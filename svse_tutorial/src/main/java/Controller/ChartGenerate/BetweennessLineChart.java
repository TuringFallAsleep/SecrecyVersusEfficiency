package Controller.ChartGenerate;

import org.graphstream.graph.Graph;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;

import java.text.NumberFormat;

import static oracle.jrockit.jfr.events.Bits.intValue;

public class BetweennessLineChart extends JFrame {
    private JFreeChart lineChart;
    private DefaultCategoryDataset dataSet = new DefaultCategoryDataset( );


    public BetweennessLineChart(String applicationTitle , String chartTitle, Double[] allBetweenness, Graph theGraph ) {
        super(applicationTitle);
//        Double[] normalisedAllBetweenness = normalise(allBetweenness, maxBetweenness);

        lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Normalised Single Node's Betweenness","Number of Nodes",
                createDataSet(allBetweenness, theGraph),
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

    private Double[] normalise(Double[] allBetweenness, Double maxBetweenness){
        Double[] normalisedAllBetweenness = new Double[allBetweenness.length];
        for (int i=0; i<allBetweenness.length; i++){
            normalisedAllBetweenness[i] = allBetweenness[i]/maxBetweenness;
            System.out.println("normalisedAllBetweenness ["+i+"] = "+normalisedAllBetweenness);
        }

        return normalisedAllBetweenness;
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

        for (int i=0; i<numOfNodeWithDiffBet.length-1; i++){
            if (numOfNodeWithDiffBet.length<1.0/segment){
                dataSet.addValue(numOfNodeWithDiffBet[i],"Graph"+theGraph.getId()+" with "+theGraph.getNodeCount()+" nodes", String.format("%.2f", (double)i/betMax));
            }else {
                if (j<numOfNodeWithDiffBet.length*segment){
                    rangeSumValue += numOfNodeWithDiffBet[i];
                    j++;
                    if (i == numOfNodeWithDiffBet.length-2){
                        dataSet.addValue(rangeSumValue,"Graph"+theGraph.getId()+" with "+theGraph.getNodeCount()+" nodes",String.format("%.2f", (double)i/betMax));
                        rangeSumValue = 0;
                    }
                }else {
                    dataSet.addValue(rangeSumValue,"Graph"+theGraph.getId()+" with "+theGraph.getNodeCount()+" nodes",String.format("%.2f", (double)i/betMax));
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

