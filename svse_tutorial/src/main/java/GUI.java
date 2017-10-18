import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI  extends JFrame implements ActionListener{
    private JLabel nodeNum = new JLabel();
    private JLabel maxDeg = new JLabel();
    private JLabel minDeg = new JLabel();
    private JLabel aveDeg = new JLabel();
    private JLabel apsp = new JLabel(); // All-pair shortest paths lengths
    private JComboBox comboBox = new JComboBox();
    private JButton processJButton = new JButton("OK");

    public  GUI(){

//        JFrame frame = new JFrame();
        this.setTitle("SNA Simple Demo");
        this.setSize(500,500);

        Container contents = getContentPane();
        contents.setLayout(new GridLayout(7,2, 10, 20));
        contents.add(new JLabel("Module: "));


        comboBox.setBounds(10,20, 80, 25);
        comboBox.addItem("Highly Centralised");
        comboBox.addItem("Highly Decentralised");
        comboBox.addItem("Bernoulli");
        comboBox.addItem("Preferential Attachment");
        comboBox.addItem("Preferential Attachment with Bernoulli");
        comboBox.addItem("9_11 Graph");
        contents.add(comboBox);


        nodeNum.setText("");
        maxDeg.setText("");
        minDeg.setText("");
        aveDeg.setText("");
        apsp.setText("");

        contents.add(new JLabel("Node number: "));
        contents.add(nodeNum);
        contents.add(new JLabel("Max degree: "));
        contents.add(maxDeg);
        contents.add(new JLabel("Min degree: "));
        contents.add(minDeg);
        contents.add(new JLabel("Ave degree: "));
        contents.add(aveDeg);
        contents.add(new JLabel("Max distance: "));
        contents.add(apsp);



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
            Double[] graphResult = {};

            // decide which graph to display
            if (comboBox.getSelectedItem().toString().equals("Highly Centralised")){
                graphResult = fg.HighlyCentralised();
            }else if(comboBox.getSelectedItem().toString().equals("Highly Decentralised")){
                graphResult = fg.HighlyDecentralised();
            }else if (comboBox.getSelectedItem().toString().equals("Bernoulli")){
                graphResult = fg.B();
            }else if(comboBox.getSelectedItem().toString().equals("Preferential Attachment")){
                graphResult = fg.PA();
            }else if(comboBox.getSelectedItem().toString().equals("Preferential Attachment with Bernoulli")){
                graphResult = fg.PAB();
            }else if (comboBox.getSelectedItem().toString().equals("9_11 Graph")){
                graphResult = fg.RG();
            }

//            System.out.println("comboBoxValue is: " + comboBox.getSelectedItem().toString());

            nodeNum.setText(""+graphResult[0]); // node number
            maxDeg.setText(""+graphResult[1]); // max degree
            minDeg.setText(""+graphResult[2]); // min degree
            aveDeg.setText(""+String.format("%.1f", graphResult[3])); // average degree
            apsp.setText(""+graphResult[4]); // All-pair shortest paths lengths.
        } //else if ( )


    } // actionPerformed

} // GUI class
