package Controller.ChartGenerate;

import org.graphstream.graph.Graph;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import static oracle.jrockit.jfr.events.Bits.intValue;

public class BetweennessLineChart extends ApplicationFrame {
    private DefaultCategoryDataset dataSet = new DefaultCategoryDataset( );


    public BetweennessLineChart(String applicationTitle , String chartTitle, Double[] allBetweenness, Graph theGraph ) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Node No.","Betweeness",
                createDataSet(allBetweenness, theGraph),
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
    }

    private DefaultCategoryDataset createDataSet(Double[] allBet, Graph theGraph) {
        for (int i=0; i< allBet.length; i++){
            dataSet.addValue(intValue(allBet[i]),theGraph.getId(), ""+i);
        }
        return dataSet;
    }
}// class BetweennessLineChart

