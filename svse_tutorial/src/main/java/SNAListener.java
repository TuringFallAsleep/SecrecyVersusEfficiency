import scala.util.parsing.combinator.testing.Str;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SNAListener implements ActionListener {
    private final JLabel maxDeg;
    private final JLabel minDeg;
    private final JLabel aveDeg;
    private final Object comboBoxValue;

    public SNAListener(JLabel maxDegree, JLabel minDegree, JLabel aveDegree, Object comboValue){
        maxDeg = maxDegree;
        minDeg = minDegree;
        aveDeg = aveDegree;
        comboBoxValue = comboValue;
    }

//    public Object getComboBoxValue(){
//        return comboBoxValue;
//    }

    public void actionPerformed(ActionEvent event){

        FixedGraph fg = new FixedGraph();
        int[] resultDeg = {};

        // decide which graph to display
        if (comboBoxValue.toString().equals("Highly Centralised")){
            resultDeg = fg.HighlyCentralised();
        }else if(comboBoxValue.toString().equals("Highly Decentralised")){
            resultDeg = fg.HighlyDecentralised();
        }

        System.out.println("comboBoxValue is: " + comboBoxValue.toString());

        maxDeg.setText(""+resultDeg[0]); // max degree
        minDeg.setText(""+resultDeg[1]); // min degree
        aveDeg.setText(""+resultDeg[2]); // average degree
    } // actionPerformed



}// class SNAListener
