package Controller.ChartGenerate;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.awt.*;

import static java.awt.FlowLayout.RIGHT;


public class BarChart extends JFrame {

    private JFreeChart barChart;


    public BarChart(String applicationTitle , String chartTitle, String graphName, Double maxDe, Double minDe, Double aveDe, Double maxDi ) {
        super( applicationTitle );
        JFrame f = new JFrame(applicationTitle);
        f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        barChart = ChartFactory.createBarChart(
                chartTitle,
                "Category",
                "Number",
                createDataSet(graphName, maxDe, minDe, aveDe, maxDi),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel( barChart );
        chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );

    }


    private CategoryDataset createDataSet(String graphName, Double maxDe, Double minDe, Double aveDe, Double maxDi) {


        final DefaultCategoryDataset dataSet =
                new DefaultCategoryDataset();


        dataSet.addValue(maxDe,graphName, "maxDegree");
        dataSet.addValue(minDe,graphName,"minDegree");
        dataSet.addValue(aveDe,graphName,"aveDegree");
        dataSet.addValue(maxDi,graphName,"maxDiameter");


        return dataSet;
    }

    public JFreeChart getChart(){
        return barChart;
    }

}
