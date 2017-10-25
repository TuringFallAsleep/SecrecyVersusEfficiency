package Controller.ChartGenerate;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;


public class BarChart extends ApplicationFrame {
//    String graphName;
//    Double maxDegree;
//    Double minDegree;
//    Double aveDegree;
//    Double maxDiameter;
//    Object[] chartData = {graphName, maxDegree, minDegree, aveDegree,maxDiameter};




    public BarChart(String applicationTitle , String chartTitle, String graphName, Double maxDe, Double minDe, Double aveDe, Double maxDi ) {
        super( applicationTitle );
        JFreeChart barChart = ChartFactory.createBarChart(
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

//    public  void getChartData(String gN, Double maxDe, Double minDe, Double aveDe, Double maxDi) {
//        this.graphName = gN;
//        this.maxDegree = maxDe;
//        this.minDegree = minDe;
//        this.aveDegree = aveDe;
//        this.maxDiameter = maxDi;
//
//        System.out.println("graph name is: " + graphName);
//    }

    private CategoryDataset createDataSet(String graphName, Double maxDe, Double minDe, Double aveDe, Double maxDi) {


        final DefaultCategoryDataset dataSet =
                new DefaultCategoryDataset();


        dataSet.addValue(maxDe,graphName, "maxDegree");
        dataSet.addValue(minDe,graphName,"minDegree");
        dataSet.addValue(aveDe,graphName,"aveDegree");
        dataSet.addValue(maxDi,graphName,"maxDiameter");

//        final String fiat = "FIAT";
//        final String audi = "AUDI";
//        final String ford = "FORD";
//        final String speed = "Speed";
//        final String millage = "Millage";
//        final String userrating = "User Rating";
//        final String safety = "safety";
//        dataSet.addValue( 1.0 , fiat , speed );
//        dataSet.addValue( 3.0 , fiat , userrating );
//        dataSet.addValue( 5.0 , fiat , millage );
//        dataSet.addValue( 5.0 , fiat , safety );
//
//        dataSet.addValue( 5.0 , audi , speed );
//        dataSet.addValue( 6.0 , audi , userrating );
//        dataSet.addValue( 10.0 , audi , millage );
//        dataSet.addValue( 4.0 , audi , safety );
//
//        dataSet.addValue( 4.0 , ford , speed );
//        dataSet.addValue( 2.0 , ford , userrating );
//        dataSet.addValue( 3.0 , ford , millage );
//        dataSet.addValue( 6.0 , ford , safety );

        return dataSet;
    }

}
