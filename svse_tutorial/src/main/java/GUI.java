import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI  extends JFrame implements ActionListener{
    private JLabel maxDeg = new JLabel();
    private JLabel minDeg = new JLabel();
    private JLabel aveDeg = new JLabel();
    private JComboBox comboBox = new JComboBox();
    private JButton processJButton = new JButton("OK");

    public  GUI(){

//        JFrame frame = new JFrame();
        this.setTitle("SNA Simple Demo");
        this.setSize(500,500);

        Container contents = getContentPane();
        contents.setLayout(new GridLayout(5,2, 10, 20));
        contents.add(new JLabel("Module: "));


        comboBox.setBounds(10,20, 80, 25);
        comboBox.addItem("Highly Centralised");
        comboBox.addItem("Highly Decentralised");
        contents.add(comboBox);


        maxDeg.setText("");
        minDeg.setText("");
        aveDeg.setText("");

        contents.add(new JLabel("Max degree: "));
        contents.add(maxDeg);
        contents.add(new JLabel("Min degree: "));
        contents.add(minDeg);
        contents.add(new JLabel("Ave degree: "));
        contents.add(aveDeg);


        contents.add(processJButton);

        comboBox.addActionListener(this);

//        SNAListener listener = new SNAListener(maxDeg,minDeg,aveDeg,comboBox.getSelectedItem());
        processJButton.addActionListener(this);


        setDefaultCloseOperation(EXIT_ON_CLOSE);  // EXIT_ON_CLOSE, DISPOSE_ON_CLOSE
        pack();
    } // createGui

    public void actionPerformed(ActionEvent event){
        if (event.getSource() == processJButton){
            FixedGraph fg = new FixedGraph();
            int[] resultDeg = {};

            // decide which graph to display
            if (comboBox.getSelectedItem().toString().equals("Highly Centralised")){
                resultDeg = fg.HighlyCentralised();
            }else if(comboBox.getSelectedItem().toString().equals("Highly Decentralised")){
                resultDeg = fg.HighlyDecentralised();
            }

            System.out.println("comboBoxValue is: " + comboBox.getSelectedItem().toString());

            maxDeg.setText(""+resultDeg[0]); // max degree
            minDeg.setText(""+resultDeg[1]); // min degree
            aveDeg.setText(""+resultDeg[2]); // average degree
        } //else if ( )


    } // actionPerformed

} // GUI class
