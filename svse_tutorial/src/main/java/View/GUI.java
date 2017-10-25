package View;

import Controller.Network.FixedGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI  extends JFrame implements ActionListener{
    private JLabel genNodeNum = new JLabel();
    private JLabel genMaxDeg = new JLabel();
    private JLabel genMinDeg = new JLabel();
    private JLabel genAveDeg = new JLabel();
    private JLabel genMaxDia = new JLabel(); // mix diameter
    private JLabel realNodeNum = new JLabel();
    private JLabel realMaxDeg = new JLabel();
    private JLabel realMinDeg = new JLabel();
    private JLabel realAveDeg = new JLabel();
    private JLabel realMaxDia = new JLabel(); // mix diameter
    private JComboBox genComboBox = new JComboBox();
    private JComboBox realComboBox = new JComboBox();

    private JButton genProcessBtn = new JButton("OK");
    private JButton realProcessBtn = new JButton("OK");

    public  GUI(){

        JFrame frame = new JFrame();

        JPanel genInfoPanel = new JPanel();
        JPanel genDataPanel = new JPanel();
        JPanel genBtnPanel = new JPanel();

        JPanel realInfoPanel = new JPanel();
        JPanel realDataPanel = new JPanel();
        JPanel realBtnPanel = new JPanel();

        Dimension d = new Dimension(15,10);



        frame.setTitle("SNA Simple Demo");
        frame.setSize(800,500);

        Container container = getContentPane();
        container.setLayout(new GridLayout(1,0,20,20));

        JPanel genPanel = new JPanel();
        container.add(genPanel);
        genPanel.setLayout(new GridLayout(0,1));

        JPanel realPanel = new JPanel();
        container.add(realPanel);
        realPanel.setLayout(new GridLayout(0,1));


        /*Set up Generated Graph*/

        /*Generated Graph Info*/
        genPanel.add(genInfoPanel);
        genInfoPanel.setLayout(new GridLayout(0,1,5, 5));

        genInfoPanel.add(new JLabel("Generated Graph"));

        genComboBox.setBounds(10,10, 80, 25);
        genComboBox.addItem("Highly Centralised");
        genComboBox.addItem("Highly Decentralised");
        genComboBox.addItem("Bernoulli");
        genComboBox.addItem("Preferential Attachment");
        genComboBox.addItem("Preferential Attachment with Bernoulli");
        genInfoPanel.add(genComboBox);
        genComboBox.addActionListener(this);

        /*Generated Graph Data*/
        genPanel.add(genDataPanel);
        genDataPanel.setLayout(new GridLayout(0,2,10,20));
        genNodeNum.setText("");
        genMaxDeg.setText("");
        genMinDeg.setText("");
        genAveDeg.setText("");
        genMaxDia.setText("");

        genDataPanel.add(new JLabel("Node number: "));
        genDataPanel.add(genNodeNum);
        genDataPanel.add(new JLabel("Max degree: "));
        genDataPanel.add(genMaxDeg);
        genDataPanel.add(new JLabel("Min degree: "));
        genDataPanel.add(genMinDeg);
        genDataPanel.add(new JLabel("Ave degree: "));
        genDataPanel.add(genAveDeg);
        genDataPanel.add(new JLabel("Max distance: "));
        genDataPanel.add(genMaxDia);


        /*Generated Graph Btn*/
        genPanel.add(genBtnPanel);
        genBtnPanel.setLayout(new GridLayout(0,1,10,20));
        genProcessBtn.setPreferredSize(d);
        genBtnPanel.add(new JLabel(""));
        genBtnPanel.add(genProcessBtn);
        genBtnPanel.add(new JLabel(""));
        genProcessBtn.addActionListener(this);


        /*Set up Real Graph*/

        /*Real Graph Info*/
        realPanel.add(realInfoPanel);
        realInfoPanel.setLayout(new GridLayout(0,1, 5, 5));
        realInfoPanel.add(new JLabel("Real Graph"));

        realComboBox.setBounds(10,10, 80, 25);
        realComboBox.addItem("9_11 Graph");
        realComboBox.addItem("Suffragettes Inner Circle");
        realInfoPanel.add(realComboBox);
        realComboBox.addActionListener(this);


        /*Real Graph Data*/
        realPanel.add(realDataPanel);
        realDataPanel.setLayout(new GridLayout(0,2,10,20));
        realNodeNum.setText("");
        realMaxDeg.setText("");
        realMinDeg.setText("");
        realAveDeg.setText("");
        realMaxDia.setText("");

        realDataPanel.add(new JLabel("Node number: "));
        realDataPanel.add(realNodeNum);
        realDataPanel.add(new JLabel("Max degree: "));
        realDataPanel.add(realMaxDeg);
        realDataPanel.add(new JLabel("Min degree: "));
        realDataPanel.add(realMinDeg);
        realDataPanel.add(new JLabel("Ave degree: "));
        realDataPanel.add(realAveDeg);
        realDataPanel.add(new JLabel("Max distance: "));
        realDataPanel.add(realMaxDia);


        /*Real Graph Btn*/
        realPanel.add(realBtnPanel);
        realBtnPanel.setLayout(new GridLayout(0,1,10,20));
        realProcessBtn.setPreferredSize(d);
        realBtnPanel.add(new JLabel(""));
        realBtnPanel.add(realProcessBtn);
        realBtnPanel.add(new JLabel(""));

        realProcessBtn.addActionListener(this);

        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);  // EXIT_ON_CLOSE, DISPOSE_ON_CLOSE
        pack();
    } // createGui

    public void actionPerformed(ActionEvent event){
        if (event.getSource() == genProcessBtn){
            FixedGraph genGraph = new FixedGraph();
            Double[][] graphResult = {};

            // decide which graph to display
            if (genComboBox.getSelectedItem().toString().equals("Highly Centralised")){
                graphResult = genGraph.HighlyCentralised();
            }else if(genComboBox.getSelectedItem().toString().equals("Highly Decentralised")){
                graphResult = genGraph.HighlyDecentralised();
            }else if (genComboBox.getSelectedItem().toString().equals("Bernoulli")){
                graphResult = genGraph.B();
            }else if(genComboBox.getSelectedItem().toString().equals("Preferential Attachment")){
                graphResult = genGraph.PA();
            }else if(genComboBox.getSelectedItem().toString().equals("Preferential Attachment with Bernoulli")){
                graphResult = genGraph.PAB();
            }

            genNodeNum.setText(""+graphResult[0][0]); // node number
            genMaxDeg.setText(""+graphResult[1][0]); // max degree
            genMinDeg.setText(""+graphResult[2][0]); // min degree
            genAveDeg.setText(""+String.format("%.1f", graphResult[3][0])); // average degree
            genMaxDia.setText(""+graphResult[4][0]); // All-pair shortest paths lengths.
        } else if (event.getSource() == realProcessBtn){
            FixedGraph realGraph = new FixedGraph();
            Double[][] graphResult = realGraph.RG(realComboBox.getSelectedItem().toString());

            realNodeNum.setText(""+graphResult[0][0]); // node number
            realMaxDeg.setText(""+graphResult[1][0]); // max degree
            realMinDeg.setText(""+graphResult[2][0]); // min degree
            realAveDeg.setText(""+String.format("%.1f", graphResult[3][0])); // average degree
            realMaxDia.setText(""+graphResult[4][0]); // All-pair shortest paths lengths.

        }


    } // actionPerformed

} // View.GUI class
