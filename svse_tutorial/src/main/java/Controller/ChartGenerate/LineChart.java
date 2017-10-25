package Controller.ChartGenerate;

import org.graphstream.graph.Graph;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import static oracle.jrockit.jfr.events.Bits.intValue;

public class LineChart extends ApplicationFrame {
    private DefaultCategoryDataset dataSet = new DefaultCategoryDataset( );


    public LineChart( String applicationTitle , String chartTitle, Double[] allNodesDegree, Graph theGraph ) {
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
    }


    private DefaultCategoryDataset createDataSet(Double[] allNodesDeg, Graph theGraph) {
        for (int i=0; i< allNodesDeg.length; i++){
            dataSet.addValue(intValue(allNodesDeg[i]),theGraph.getId(), ""+i);
        }
        return dataSet;
    }
}// class LineChart

