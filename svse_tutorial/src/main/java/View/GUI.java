package View;


import Controller.Network.CovertNetwork;
import Controller.Network.FixedGraph;
import Controller.Network.RealGraph;
import Model.StaticGraph.GraphInfo;
import org.graphstream.graph.Graph;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Dictionary;
import java.util.Hashtable;

public class GUI extends JPanel implements ActionListener, ChangeListener{

    private Dimension panelSize = new Dimension(800,700);
    /* Generated graph*/
    private JLabel gen_label_data = new JLabel("Data: ");
    private JLabel gen_label_structure = new JLabel("Structure: ");
    private JLabel gen_label_node_number = new JLabel("Node number: ");
    private JLabel gen_data_node_number = new JLabel("      15");
    private JLabel gen_label_analysis = new JLabel("Analysis: ");
    private JLabel gen_label_hours_per_pass = new JLabel("Hours per pass: ");
    private JLabel gen_label_define_key_players_by = new JLabel("Define key players by: ");
    private JLabel gen_label_key_players_number = new JLabel("Key players number: ");
    private JLabel gen_label_segment_size = new JLabel("Max segment size:  ");
    private JLabel gen_label_arrest_probability = new JLabel("Arrest probability:  Key players: ");
    private JLabel gen_label_step = new JLabel("                              Step: ");
    private JLabel gen_label_step_increase = new JLabel("                              Step increase: ");
    private JLabel gen_label_show_result = new JLabel("Show Result: ");
    private JLabel gen_label_blank = new JLabel(" ");

    private JComboBox gen_comboBox_structure = new JComboBox();
    private JComboBox gen_comboBox_efficiency = new JComboBox();
    private JComboBox gen_comboBox_define_key_players_by = new JComboBox();
    private JComboBox gen_comboBox_step_increase = new JComboBox();

    private JSlider gen_slider_node_number = new JSlider(10,100,15);

    private JCheckBox gen_checkBox_efficiency = new JCheckBox("Efficiency: ");
    private JCheckBox gen_checkBox_secrecy = new JCheckBox("Secrecy: ");
    private JCheckBox gen_checkBox_network_graph = new JCheckBox("Network graph");
    private JCheckBox gen_checkbox_diameter_distribution = new JCheckBox("Diameter distribution");
    private JCheckBox gen_checkBox_degree_distribution = new JCheckBox("Degree distribution");
    private JCheckBox gen_checkBox_closness_distribution = new JCheckBox("Closeness distribution");
    private JCheckBox gen_checkBox_betweenness_distribution = new JCheckBox("Betweenness distribution");
    private JCheckBox gen_checkBox_save_selected_diagram = new JCheckBox("Save selected diagram(s)");
    private JCheckBox gen_checkBox_efficiency_progress = new JCheckBox("Efficiency progress");
    private JCheckBox gen_checkBox_secrecy_progress = new JCheckBox("Secrecy progress");

    Boolean boolean_gen_checkBox_efficiency = false;
    Boolean boolean_gen_checkBox_secrecy = false;
    Boolean boolean_gen_checkBox_network_graph = false;
    Boolean boolean_gen_checkbox_diameter_distribution = false;
    Boolean boolean_gen_checkBox_degree_distribution = false;
    Boolean boolean_gen_checkBox_closness_distribution = false;
    Boolean boolean_gen_checkBox_betweenness_distribution = false;
    Boolean boolean_gen_checkBox_save_selected_diagram = false;
    Boolean boolean_gen_checkBox_efficiency_progress = false;
    Boolean boolean_gen_checkBox_secrecy_progress = false;



    private JTextField gen_textField_hours_per_pass = new JTextField();
    private JTextField gen_textField_key_players_number = new JTextField();
    private JTextField gen_textField_segment_size = new JTextField();
    private JTextField gen_textField_arrest_probability_key_players = new JTextField("%");
    private JTextField gen_textField_step = new JTextField("%");

    private JButton gen_button_ok = new JButton("OK");


    /* Imported graph */
    private JLabel imp_label_data = new JLabel("Data: ");
    private JLabel imp_label_import_from_file = new JLabel("Import from file: ");
    private JLabel imp_label_analysis = new JLabel("Analysis: ");
    private JLabel imp_label_hours_per_pass = new JLabel("Hours per pass: ");
    private JLabel imp_label_define_key_players_by = new JLabel("Define key players by: ");
    private JLabel imp_label_key_players_number = new JLabel("Key players number: ");
    private JLabel imp_label_segment_size = new JLabel("Max segment size:  ");
    private JLabel imp_label_arrest_probability = new JLabel("Arrest probability:  Key players: ");
    private JLabel imp_label_step = new JLabel("                              Step: ");
    private JLabel imp_label_step_increase = new JLabel("                              Step increase: ");
    private JLabel imp_label_show_result = new JLabel("Show Result: ");
    private JLabel imp_label_blank = new JLabel(" ");


    private JComboBox imp_comboBox_efficiency = new JComboBox();
    private JComboBox imp_comboBox_define_key_players_by = new JComboBox();
    private JComboBox imp_comboBox_step_increase = new JComboBox();

    private JCheckBox imp_checkBox_efficiency = new JCheckBox("Efficiency: ");
    private JCheckBox imp_checkBox_secrecy = new JCheckBox("Secrecy: ");
    private JCheckBox imp_checkBox_network_graph = new JCheckBox("Network graph");
    private JCheckBox imp_checkbox_diameter_distribution = new JCheckBox("Diameter distribution");
    private JCheckBox imp_checkBox_degree_distribution = new JCheckBox("Degree distribution");
    private JCheckBox imp_checkBox_closness_distribution = new JCheckBox("Closeness distribution");
    private JCheckBox imp_checkBox_betweenness_distribution = new JCheckBox("Betweenness distribution");
    private JCheckBox imp_checkBox_save_selected_diagram = new JCheckBox("Save selected diagram(s)");
    private JCheckBox imp_checkBox_efficiency_progress = new JCheckBox("Efficiency progress");
    private JCheckBox imp_checkBox_secrecy_progress = new JCheckBox("Secrecy progress");

    private JTextField imp_textField_import_from_file = new JTextField();
    private JTextField imp_textField_hours_per_pass = new JTextField();
    private JTextField imp_textField_key_players_number = new JTextField();
    private JTextField imp_textField_segment_size = new JTextField();
    private JTextField imp_textField_arrest_probability_key_players = new JTextField("%");
    private JTextField imp_textField_step = new JTextField("%");

    private JButton imp_button_select = new JButton("Select");
    private JButton imp_button_ok = new JButton("OK");

    private JFileChooser imp_fileChooser = new JFileChooser();
    private File imp_file;

    /* Covert Network */

    private JLabel cov_label_data = new JLabel("Data: ");
    private JLabel cov_label_initial_graph = new JLabel("Initial graph: ");
    private JLabel cov_label_node_number = new JLabel("Node number: ");
    private JLabel cov_data_node_number = new JLabel("      15");
    private JLabel cov_label_algorithm = new JLabel("Algorithm: ");
    private JLabel cov_label_balance = new JLabel("Balance: ");
    private JLabel cov_label_construction = new JLabel("Construction: ");
    private JLabel cov_label_hours_per_pass = new JLabel("Hours per pass: ");
    private JLabel cov_label_define_key_players_by = new JLabel("Define key players by: ");
    private JLabel cov_label_key_players_number = new JLabel("Key players number: ");
    private JLabel cov_label_segment_size = new JLabel("Max segment size:  ");
    private JLabel cov_label_arrest_probability = new JLabel("Arrest probability:  Key players: ");
    private JLabel cov_label_step = new JLabel("                              Step: ");
    private JLabel cov_label_step_increase = new JLabel("                              Step increase: ");
    private JLabel cov_label_show_result = new JLabel("Show Result: ");
    private JLabel cov_label_blank = new JLabel(" ");

    private JComboBox cov_comboBox_initial_graph = new JComboBox();
    private JComboBox cov_comboBox_algorithm = new JComboBox();
    private JComboBox cov_comboBox_efficiency = new JComboBox();
    private JComboBox cov_comboBox_define_key_players_by = new JComboBox();
    private JComboBox cov_comboBox_step_increase = new JComboBox();

    private JSlider cov_slider_node_number = new JSlider(10,100,15);
    private JSlider cov_slider_balance = new JSlider(0,100,50);

    private JCheckBox cov_checkBox_efficiency = new JCheckBox("Efficiency: ");
    private JCheckBox cov_checkBox_secrecy = new JCheckBox("Secrecy: ");
    private JCheckBox cov_checkBox_covert_network_graph = new JCheckBox("Covert network graph");
    private JCheckBox cov_checkBox_initial_network_graph = new JCheckBox("Initial network graph");
    private JCheckBox cov_checkbox_diameter_distribution = new JCheckBox("Diameter distribution");
    private JCheckBox cov_checkBox_degree_distribution = new JCheckBox("Degree distribution");
    private JCheckBox cov_checkBox_closness_distribution = new JCheckBox("Closeness distribution");
    private JCheckBox cov_checkBox_betweenness_distribution = new JCheckBox("Betweenness distribution");
    private JCheckBox cov_checkBox_save_selected_diagram = new JCheckBox("Save selected diagram(s)");
    private JCheckBox cov_checkBox_efficiency_progress = new JCheckBox("Efficiency progress");
    private JCheckBox cov_checkBox_secrecy_progress = new JCheckBox("Secrecy progress");

    private JTextField cov_textField_hours_per_pass = new JTextField();
    private JTextField cov_textField_key_players_number = new JTextField();
    private JTextField cov_textField_segment_size = new JTextField();
    private JTextField cov_textField_arrest_probability_key_players = new JTextField("%");
    private JTextField cov_textField_step = new JTextField("%");

    private JButton cov_button_ok = new JButton("OK");

    private JFileChooser cov_fileChooser = new JFileChooser();
    private File cov_file;
    private Boolean cov_added_file = false;



    /* New UI version */

    public GUI (){

        JTabbedPane mainTabbedPane = new JTabbedPane();

        JComponent panel1 = makeGenPanel();
        mainTabbedPane.addTab("Generated Network", null, panel1,"Some build-in graphs");
        mainTabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        JComponent panel2 = makeImpPanel();
        mainTabbedPane.addTab("Imported Network", null, panel2,"Some imported graphs");
        mainTabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        JComponent panel3 = makeCovPanel();
        mainTabbedPane.addTab("Covert Network", null, panel3,"Some covert graphs");
        mainTabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        //Add the tabbed pane to this panel.
        add(mainTabbedPane);
        mainTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

    }

    private void initialiseGenComponents (){

        gen_textField_arrest_probability_key_players.setToolTipText("Please insert a number."+" The probability of arresting the key players for the reason that a more important member of a covert network always gets more protections");

        gen_comboBox_structure.addItem("Highly Centralised");
        gen_comboBox_structure.addItem("Highly Decentralised");
        gen_comboBox_structure.addItem("Bernoulli");
        gen_comboBox_structure.addItem("Preferential Attachment");
        gen_comboBox_structure.addItem("Preferential Attachment with Bernoulli");

        gen_slider_node_number.setMajorTickSpacing(5);
        gen_slider_node_number.setMinorTickSpacing(1);
        gen_slider_node_number.setPaintTicks(true);
        gen_slider_node_number.setPaintLabels(true);
        gen_slider_node_number.setSnapToTicks(true);
        gen_slider_node_number.addChangeListener(this);
//        gen_comboBox_structure.getMaximumSize();

        gen_comboBox_efficiency.addItem("Deliver message");

        gen_comboBox_define_key_players_by.addItem("Degree");
        gen_comboBox_define_key_players_by.addItem("Closeness");
        gen_comboBox_define_key_players_by.addItem("Betweenness");

        gen_comboBox_step_increase.addItem("None");
        gen_comboBox_step_increase.addItem("Linear");

        gen_button_ok.addActionListener(this);
    }

    private void initialiseImpComponents(){
        imp_comboBox_efficiency.addItem("Deliver message");

        imp_comboBox_define_key_players_by.addItem("Degree");
        imp_comboBox_define_key_players_by.addItem("Closeness");
        imp_comboBox_define_key_players_by.addItem("Betweenness");

        imp_comboBox_step_increase.addItem("None");
        imp_comboBox_step_increase.addItem("Linear");

        imp_textField_import_from_file.setEnabled(false);


        imp_button_select.addActionListener(this);
        imp_button_ok.addActionListener(this);
        imp_fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

    }

    private void initialiseCovComponents(){

        cov_comboBox_initial_graph.addItem("Highly Centralised");
        cov_comboBox_initial_graph.addItem("Highly Decentralised");
        cov_comboBox_initial_graph.addItem("Bernoulli");
        cov_comboBox_initial_graph.addItem("Preferential Attachment");
        cov_comboBox_initial_graph.addItem("Preferential Attachment with Bernoulli");
        cov_comboBox_initial_graph.addItem("Add from file...");

        cov_slider_node_number.setMajorTickSpacing(5);
        cov_slider_node_number.setMinorTickSpacing(1);
        cov_slider_node_number.setPaintTicks(true);
        cov_slider_node_number.setPaintLabels(true);
        cov_slider_node_number.setSnapToTicks(true);
        cov_slider_node_number.addChangeListener(this);

        cov_comboBox_algorithm.addItem("Fast method");
        cov_comboBox_algorithm.addItem("Faster method");



        cov_slider_balance.setPaintLabels(true);
        cov_slider_balance.setPaintTicks(true);
        cov_slider_balance.setMajorTickSpacing(20);
        cov_slider_balance.setMinorTickSpacing(5);

        Dictionary<Integer, Component> labelTable = new Hashtable<Integer, Component>();
        labelTable.put(0, new JLabel("Secrecy"));
        labelTable.put(50, new JLabel("0"));
        labelTable.put(25, new JLabel("50"));
        labelTable.put(75, new JLabel("50"));
        labelTable.put(100, new JLabel("Efficiency"));
        cov_slider_balance.setLabelTable(labelTable);

        cov_comboBox_efficiency.addItem("Deliver message");

        cov_comboBox_define_key_players_by.addItem("Degree");
        cov_comboBox_define_key_players_by.addItem("Closeness");
        cov_comboBox_define_key_players_by.addItem("Betweenness");

        cov_comboBox_step_increase.addItem("None");
        cov_comboBox_step_increase.addItem("Linear");

        cov_fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        cov_button_ok.addActionListener(this);
        cov_comboBox_initial_graph.addActionListener(this);

    }


    protected JComponent makeGenPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(panelSize);

        initialiseGenComponents();

        GroupLayout groupLayout = new GroupLayout(panel);
        panel.setLayout(groupLayout);

        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);


        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(gen_label_data)
                                .addComponent(gen_label_blank)
                                .addComponent(gen_label_blank)
                                .addComponent(gen_label_analysis)
                                .addComponent(gen_label_blank)
                                .addComponent(gen_label_blank)
                                .addComponent(gen_label_show_result))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(gen_label_structure)
                                .addComponent(gen_label_node_number)
                                .addComponent(gen_checkBox_efficiency)
                                .addComponent(gen_checkBox_secrecy)
                                .addComponent(gen_checkBox_network_graph)
                                .addComponent(gen_checkbox_diameter_distribution)
                                .addComponent(gen_checkBox_degree_distribution)
                                .addComponent(gen_checkBox_closness_distribution)
                                .addComponent(gen_checkBox_betweenness_distribution)
                                .addComponent(gen_checkBox_save_selected_diagram))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(gen_comboBox_structure)
                                .addComponent(gen_slider_node_number)
                                .addComponent(gen_comboBox_efficiency)
                                .addComponent(gen_label_hours_per_pass)
                                .addComponent(gen_label_define_key_players_by)
                                .addComponent(gen_label_key_players_number)
                                .addComponent(gen_label_segment_size)
                                .addComponent(gen_label_arrest_probability)
                                .addComponent(gen_label_step)
                                .addComponent(gen_label_step_increase)
                                .addComponent(gen_checkBox_efficiency_progress)
                                .addComponent(gen_checkBox_secrecy_progress))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(gen_data_node_number)
                                .addComponent(gen_textField_hours_per_pass)
                                .addComponent(gen_comboBox_define_key_players_by)
                                .addComponent(gen_textField_key_players_number)
                                .addComponent(gen_textField_segment_size)
                                .addComponent(gen_textField_arrest_probability_key_players)
                                .addComponent(gen_textField_step)
                                .addComponent(gen_comboBox_step_increase)
                                .addComponent(gen_button_ok))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(gen_label_data)
                                .addComponent(gen_label_structure)
                                .addComponent(gen_comboBox_structure))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(gen_label_node_number)
                                .addComponent(gen_slider_node_number)
                                .addComponent(gen_data_node_number))
                        .addComponent(gen_label_blank)
                        .addComponent(gen_label_blank)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(gen_label_analysis)
                                .addComponent(gen_checkBox_efficiency)
                                .addComponent(gen_comboBox_efficiency))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(gen_label_hours_per_pass)
                                .addComponent(gen_textField_hours_per_pass))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(gen_checkBox_secrecy)
                                .addComponent(gen_label_define_key_players_by)
                                .addComponent(gen_comboBox_define_key_players_by))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(gen_label_key_players_number)
                                .addComponent(gen_textField_key_players_number))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(gen_label_segment_size)
                                .addComponent(gen_textField_segment_size))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(gen_label_arrest_probability)
                                .addComponent(gen_textField_arrest_probability_key_players))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(gen_label_step)
                                .addComponent(gen_textField_step))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(gen_label_step_increase)
                                .addComponent(gen_comboBox_step_increase))
                        .addComponent(gen_label_blank)
                        .addComponent(gen_label_blank)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(gen_label_show_result)
                                .addComponent(gen_checkBox_network_graph)
                                .addComponent(gen_checkBox_efficiency_progress))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(gen_checkbox_diameter_distribution)
                                .addComponent(gen_checkBox_secrecy_progress))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(gen_checkBox_degree_distribution))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(gen_checkBox_closness_distribution))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(gen_checkBox_betweenness_distribution))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(gen_checkBox_save_selected_diagram))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(gen_button_ok))
        );

        return panel;
    }

    protected JComponent makeImpPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(panelSize);

        initialiseImpComponents();

        GroupLayout groupLayout = new GroupLayout(panel);
        panel.setLayout(groupLayout);

        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);


        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(imp_label_data)
                                .addComponent(imp_label_blank)
                                .addComponent(imp_label_blank)
                                .addComponent(imp_label_analysis)
                                .addComponent(imp_label_blank)
                                .addComponent(imp_label_blank)
                                .addComponent(imp_label_show_result))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(imp_label_import_from_file)
                                .addComponent(imp_checkBox_efficiency)
                                .addComponent(imp_checkBox_secrecy)
                                .addComponent(imp_checkBox_network_graph)
                                .addComponent(imp_checkbox_diameter_distribution)
                                .addComponent(imp_checkBox_degree_distribution)
                                .addComponent(imp_checkBox_closness_distribution)
                                .addComponent(imp_checkBox_betweenness_distribution)
                                .addComponent(imp_checkBox_save_selected_diagram))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(imp_textField_import_from_file)
                                .addComponent(imp_comboBox_efficiency)
                                .addComponent(imp_label_hours_per_pass)
                                .addComponent(imp_label_define_key_players_by)
                                .addComponent(imp_label_key_players_number)
                                .addComponent(imp_label_segment_size)
                                .addComponent(imp_label_arrest_probability)
                                .addComponent(imp_label_step)
                                .addComponent(imp_label_step_increase)
                                .addComponent(imp_checkBox_efficiency_progress)
                                .addComponent(imp_checkBox_secrecy_progress))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(imp_button_select)
                                .addComponent(imp_textField_hours_per_pass)
                                .addComponent(imp_comboBox_define_key_players_by)
                                .addComponent(imp_textField_key_players_number)
                                .addComponent(imp_textField_segment_size)
                                .addComponent(imp_textField_arrest_probability_key_players)
                                .addComponent(imp_textField_step)
                                .addComponent(imp_comboBox_step_increase)
                                .addComponent(imp_button_ok))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(imp_label_data)
                                .addComponent(imp_label_import_from_file)
                                .addComponent(imp_textField_import_from_file)
                                .addComponent(imp_button_select))
                        .addComponent(imp_label_blank)
                        .addComponent(imp_label_blank)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(imp_label_analysis)
                                .addComponent(imp_checkBox_efficiency)
                                .addComponent(imp_comboBox_efficiency))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(imp_label_hours_per_pass)
                                .addComponent(imp_textField_hours_per_pass))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(imp_checkBox_secrecy)
                                .addComponent(imp_label_define_key_players_by)
                                .addComponent(imp_comboBox_define_key_players_by))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(imp_label_key_players_number)
                                .addComponent(imp_textField_key_players_number))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(imp_label_segment_size)
                                .addComponent(imp_textField_segment_size))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(imp_label_arrest_probability)
                                .addComponent(imp_textField_arrest_probability_key_players))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(imp_label_step)
                                .addComponent(imp_textField_step))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(imp_label_step_increase)
                                .addComponent(imp_comboBox_step_increase))
                        .addComponent(imp_label_blank)
                        .addComponent(imp_label_blank)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(imp_label_show_result)
                                .addComponent(imp_checkBox_network_graph)
                                .addComponent(imp_checkBox_efficiency_progress))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(imp_checkbox_diameter_distribution)
                                .addComponent(imp_checkBox_secrecy_progress))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(imp_checkBox_degree_distribution))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(imp_checkBox_closness_distribution))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(imp_checkBox_betweenness_distribution))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(imp_checkBox_save_selected_diagram))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(imp_button_ok))
        );


        return panel;
    }


    protected JComponent makeCovPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(panelSize);

        initialiseCovComponents();

        GroupLayout groupLayout = new GroupLayout(panel);
        panel.setLayout(groupLayout);

        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);


        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(cov_label_data)
                                .addComponent(cov_label_blank)
                                .addComponent(cov_label_construction)
                                .addComponent(cov_label_blank)
                                .addComponent(cov_label_show_result))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(cov_label_initial_graph)
                                .addComponent(cov_label_node_number)
                                .addComponent(cov_label_algorithm)
                                .addComponent(cov_label_balance)
                                .addComponent(cov_checkBox_efficiency)
                                .addComponent(cov_checkBox_secrecy)
                                .addComponent(cov_checkBox_covert_network_graph)
                                .addComponent(cov_checkBox_initial_network_graph)
                                .addComponent(cov_checkbox_diameter_distribution)
                                .addComponent(cov_checkBox_degree_distribution)
                                .addComponent(cov_checkBox_closness_distribution)
                                .addComponent(cov_checkBox_betweenness_distribution)
                                .addComponent(cov_checkBox_save_selected_diagram))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(cov_comboBox_initial_graph)
                                .addComponent(cov_slider_node_number)
                                .addComponent(cov_comboBox_algorithm)
                                .addComponent(cov_slider_balance)
                                .addComponent(cov_comboBox_efficiency)
                                .addComponent(cov_label_hours_per_pass)
                                .addComponent(cov_label_define_key_players_by)
                                .addComponent(cov_label_key_players_number)
                                .addComponent(cov_label_segment_size)
                                .addComponent(cov_label_arrest_probability)
                                .addComponent(cov_label_step)
                                .addComponent(cov_label_step_increase)
                                .addComponent(cov_checkBox_efficiency_progress)
                                .addComponent(cov_checkBox_secrecy_progress))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(cov_data_node_number)
                                .addComponent(cov_textField_hours_per_pass)
                                .addComponent(cov_comboBox_define_key_players_by)
                                .addComponent(cov_textField_key_players_number)
                                .addComponent(cov_textField_segment_size)
                                .addComponent(cov_textField_arrest_probability_key_players)
                                .addComponent(cov_textField_step)
                                .addComponent(cov_comboBox_step_increase)
                                .addComponent(cov_button_ok))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(cov_label_data)
                                .addComponent(cov_label_initial_graph)
                                .addComponent(cov_comboBox_initial_graph))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(cov_label_node_number)
                                .addComponent(cov_slider_node_number)
                                .addComponent(cov_data_node_number))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(cov_label_algorithm)
                                .addComponent(cov_comboBox_algorithm))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(cov_label_balance)
                                .addComponent(cov_slider_balance))
                        .addComponent(cov_label_blank)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(cov_label_construction)
                                .addComponent(cov_checkBox_efficiency)
                                .addComponent(cov_comboBox_efficiency))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(cov_label_hours_per_pass)
                                .addComponent(cov_textField_hours_per_pass))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(cov_checkBox_secrecy)
                                .addComponent(cov_label_define_key_players_by)
                                .addComponent(cov_comboBox_define_key_players_by))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(cov_label_key_players_number)
                                .addComponent(cov_textField_key_players_number))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(cov_label_segment_size)
                                .addComponent(cov_textField_segment_size))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(cov_label_arrest_probability)
                                .addComponent(cov_textField_arrest_probability_key_players))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(cov_label_step)
                                .addComponent(cov_textField_step))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(cov_label_step_increase)
                                .addComponent(cov_comboBox_step_increase))
                        .addComponent(cov_label_blank)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(cov_label_show_result)
                                .addComponent(cov_checkBox_covert_network_graph)
                                .addComponent(cov_checkBox_efficiency_progress))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(cov_checkBox_initial_network_graph)
                                .addComponent(cov_checkBox_secrecy_progress))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(cov_checkbox_diameter_distribution))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(cov_checkBox_degree_distribution))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(cov_checkBox_closness_distribution))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(cov_checkBox_betweenness_distribution))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(cov_checkBox_save_selected_diagram))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(cov_button_ok))
        );


//        panel.add(filler);
        return panel;
    }

    public static void createAndShowGUI(){
        JFrame mainWindow = new JFrame();
        mainWindow.setVisible(true);
//        mainWindow.setSize(600,750);
        mainWindow.setTitle("SVSE");
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        Container container = ;
//        GroupLayout groupLayout = ;

        //Add content to the window.
        mainWindow.add(new GUI(), BorderLayout.CENTER);

        //Display the window.
        mainWindow.pack();
        mainWindow.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event){

        // Generated graph starts
        if (event.getSource().equals(gen_button_ok)){

            Double hoursPerPass;
            Integer keyPlayerNumber;
            Integer maxSegmentSize;
            Double keyPlayerArrestProbability;
            Double arrestProbabilityStep;
            String stepIncreaseMethod;

            FixedGraph genGraph = new FixedGraph();
            GraphInfo graphInfo = new GraphInfo();
            // generated graph
            if (gen_textField_hours_per_pass.getText().equals("")){
                hoursPerPass = 0.0;
            }else {
                hoursPerPass = Double.parseDouble(gen_textField_hours_per_pass.getText());
                try {
                    hoursPerPass = Double.parseDouble(gen_textField_hours_per_pass.getText());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    // TODO handle the error
                }
            }

            if (gen_textField_key_players_number.getText().equals("")){
                keyPlayerNumber = 1;
            }else {
                keyPlayerNumber = Integer.parseInt(gen_textField_key_players_number.getText());
                try {
                    keyPlayerNumber = Integer.parseInt(gen_textField_key_players_number.getText());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    // TODO handle the error
                }
            }

            if (gen_textField_segment_size.getText().equals("")){
                maxSegmentSize = 1;
            } else {
                maxSegmentSize = Integer.parseInt(gen_textField_segment_size.getText());
                try {
                    maxSegmentSize = Integer.parseInt(gen_textField_segment_size.getText());
                } catch (NumberFormatException e){
                    e.printStackTrace();
                    // TODO handle the error
                }
            }

            if (gen_textField_arrest_probability_key_players.getText().equals("") || gen_textField_arrest_probability_key_players.getText().equals("%")){
                keyPlayerArrestProbability = 0.0;
            } else {
                keyPlayerArrestProbability = Double.parseDouble(gen_textField_arrest_probability_key_players.getText());
                try{
                    keyPlayerArrestProbability = Double.parseDouble(gen_textField_arrest_probability_key_players.getText());
                } catch (NumberFormatException e){
                    e.printStackTrace();
                    // TODO handle the error
                }
            }

            if (gen_textField_step.getText().equals("") || gen_textField_step.getText().equals("%")){
                arrestProbabilityStep = 0.0;
            } else {
                arrestProbabilityStep = Double.parseDouble(gen_textField_step.getText());
                try{
                    arrestProbabilityStep = Double.parseDouble(gen_textField_step.getText());
                } catch (NumberFormatException e){
                    e.printStackTrace();
                    // TODO handle the error
                }
            }

            stepIncreaseMethod = gen_comboBox_step_increase.getSelectedItem().toString();

            if (gen_comboBox_structure.getSelectedItem().toString().equals("Highly Centralised")){
                graphInfo = genGraph.HighlyCentralised(gen_slider_node_number.getValue(),
                        hoursPerPass, gen_checkBox_efficiency.isSelected(),
                        gen_checkBox_secrecy.isSelected(), gen_comboBox_define_key_players_by.getSelectedItem().toString(), keyPlayerNumber, maxSegmentSize, keyPlayerArrestProbability, arrestProbabilityStep, stepIncreaseMethod,
                        gen_checkBox_network_graph.isSelected(), gen_checkbox_diameter_distribution.isSelected(), gen_checkBox_degree_distribution.isSelected(), gen_checkBox_closness_distribution.isSelected(), gen_checkBox_betweenness_distribution.isSelected(),
                        gen_checkBox_save_selected_diagram.isSelected(),
                        gen_checkBox_efficiency_progress.isSelected(), gen_checkBox_secrecy_progress.isSelected() );

            }else if(gen_comboBox_structure.getSelectedItem().toString().equals("Highly Decentralised")){
                genGraph.HighlyDecentralised(gen_slider_node_number.getValue(),
                        hoursPerPass, gen_checkBox_efficiency.isSelected(),
                        gen_checkBox_secrecy.isSelected(), gen_comboBox_define_key_players_by.getSelectedItem().toString(), keyPlayerNumber, maxSegmentSize, keyPlayerArrestProbability, arrestProbabilityStep, stepIncreaseMethod,
                        gen_checkBox_network_graph.isSelected(), gen_checkbox_diameter_distribution.isSelected(), gen_checkBox_degree_distribution.isSelected(), gen_checkBox_closness_distribution.isSelected(), gen_checkBox_betweenness_distribution.isSelected(),
                        gen_checkBox_save_selected_diagram.isSelected(),
                        gen_checkBox_efficiency_progress.isSelected(), gen_checkBox_secrecy_progress.isSelected());
            }else if (gen_comboBox_structure.getSelectedItem().toString().equals("Bernoulli")){
                genGraph.B(gen_slider_node_number.getValue(),
                        hoursPerPass, gen_checkBox_efficiency.isSelected(),
                        gen_checkBox_secrecy.isSelected(), gen_comboBox_define_key_players_by.getSelectedItem().toString(), keyPlayerNumber, maxSegmentSize, keyPlayerArrestProbability, arrestProbabilityStep, stepIncreaseMethod,
                        gen_checkBox_network_graph.isSelected(), gen_checkbox_diameter_distribution.isSelected(), gen_checkBox_degree_distribution.isSelected(), gen_checkBox_closness_distribution.isSelected(), gen_checkBox_betweenness_distribution.isSelected(),
                        gen_checkBox_save_selected_diagram.isSelected(),
                        gen_checkBox_efficiency_progress.isSelected(), gen_checkBox_secrecy_progress.isSelected());
            }else if(gen_comboBox_structure.getSelectedItem().toString().equals("Preferential Attachment")){
                genGraph.PA(gen_slider_node_number.getValue(),
                        hoursPerPass, gen_checkBox_efficiency.isSelected(),
                        gen_checkBox_secrecy.isSelected(), gen_comboBox_define_key_players_by.getSelectedItem().toString(), keyPlayerNumber, maxSegmentSize, keyPlayerArrestProbability, arrestProbabilityStep, stepIncreaseMethod,
                        gen_checkBox_network_graph.isSelected(), gen_checkbox_diameter_distribution.isSelected(), gen_checkBox_degree_distribution.isSelected(), gen_checkBox_closness_distribution.isSelected(), gen_checkBox_betweenness_distribution.isSelected(),
                        gen_checkBox_save_selected_diagram.isSelected(),
                        gen_checkBox_efficiency_progress.isSelected(), gen_checkBox_secrecy_progress.isSelected());
            }else if(gen_comboBox_structure.getSelectedItem().toString().equals("Preferential Attachment with Bernoulli")){
                genGraph.PAB(gen_slider_node_number.getValue(),
                        hoursPerPass, gen_checkBox_efficiency.isSelected(),
                        gen_checkBox_secrecy.isSelected(), gen_comboBox_define_key_players_by.getSelectedItem().toString(), keyPlayerNumber, maxSegmentSize, keyPlayerArrestProbability, arrestProbabilityStep, stepIncreaseMethod,
                        gen_checkBox_network_graph.isSelected(), gen_checkbox_diameter_distribution.isSelected(), gen_checkBox_degree_distribution.isSelected(), gen_checkBox_closness_distribution.isSelected(), gen_checkBox_betweenness_distribution.isSelected(),
                        gen_checkBox_save_selected_diagram.isSelected(),
                        gen_checkBox_efficiency_progress.isSelected(), gen_checkBox_secrecy_progress.isSelected());
            }
        } else // Generated graph ends

        // Imported graph starts
        if (event.getSource().equals(imp_button_select)){
            int returnVal = imp_fileChooser.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                imp_file = imp_fileChooser.getSelectedFile();
                System.out.println(imp_file);
                //This is where a real application would open the file.
                imp_textField_import_from_file.setText("Opening: " + imp_file.getName());
            } else {
                imp_textField_import_from_file.setText("Open command cancelled by user.");
            }
            imp_textField_import_from_file.setCaretPosition(imp_textField_import_from_file.getDocument().getLength());
        } else
        if (event.getSource().equals(imp_button_ok)){
            Double hoursPerPass;
            Integer keyPlayerNumber;
            Integer maxSegmentSize;
            Double keyPlayerArrestProbability;
            Double arrestProbabilityStep;
            String stepIncreaseMethod;

            RealGraph realGraph = new RealGraph(imp_file);


            if (imp_textField_hours_per_pass.getText().equals("")){
                hoursPerPass = 1.0;
            }else {
                hoursPerPass = Double.parseDouble(imp_textField_hours_per_pass.getText());
                try {
                    hoursPerPass = Double.parseDouble(imp_textField_hours_per_pass.getText());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    // TODO handle the error
                }
            }

            if (imp_textField_key_players_number.getText().equals("")){
                keyPlayerNumber = 2;
            }else {
                keyPlayerNumber = Integer.parseInt(imp_textField_key_players_number.getText());
                try {
                    keyPlayerNumber = Integer.parseInt(imp_textField_key_players_number.getText());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    // TODO handle the error
                }
            }

            if (imp_textField_segment_size.getText().equals("")){
                maxSegmentSize = 3;
            } else {
                maxSegmentSize = Integer.parseInt(imp_textField_segment_size.getText());
                try {
                    maxSegmentSize = Integer.parseInt(imp_textField_segment_size.getText());
                } catch (NumberFormatException e){
                    e.printStackTrace();
                    // TODO handle the error
                }
            }

            if (imp_textField_arrest_probability_key_players.getText().equals("") || imp_textField_arrest_probability_key_players.getText().equals("%")){
                keyPlayerArrestProbability = 100.0;
            } else {
                keyPlayerArrestProbability = Double.parseDouble(imp_textField_arrest_probability_key_players.getText());
                try{
                    keyPlayerArrestProbability = Double.parseDouble(imp_textField_arrest_probability_key_players.getText());
                } catch (NumberFormatException e){
                    e.printStackTrace();
                    // TODO handle the error
                }
            }

            if (imp_textField_step.getText().equals("") || imp_textField_step.getText().equals("%")){
                arrestProbabilityStep = 10.0;
            } else {
                arrestProbabilityStep = Double.parseDouble(imp_textField_step.getText());
                try{
                    arrestProbabilityStep = Double.parseDouble(imp_textField_step.getText());
                } catch (NumberFormatException e){
                    e.printStackTrace();
                    // TODO handle the error
                }
            }

            stepIncreaseMethod = imp_comboBox_step_increase.getSelectedItem().toString();

            realGraph.RG(hoursPerPass, imp_checkBox_efficiency.isSelected(),
                    imp_checkBox_secrecy.isSelected(), imp_comboBox_define_key_players_by.getSelectedItem().toString(), keyPlayerNumber, maxSegmentSize, keyPlayerArrestProbability, arrestProbabilityStep, stepIncreaseMethod,
                    imp_checkBox_network_graph.isSelected(), imp_checkbox_diameter_distribution.isSelected(), imp_checkBox_degree_distribution.isSelected(), imp_checkBox_closness_distribution.isSelected(), imp_checkBox_betweenness_distribution.isSelected(),
                    imp_checkBox_save_selected_diagram.isSelected(),
                    imp_checkBox_efficiency_progress.isSelected(), imp_checkBox_secrecy_progress.isSelected() );

        }else // Imported graph ends


            // Covert network starts
        if (cov_comboBox_initial_graph.getSelectedItem().equals("Add from file...") && !cov_added_file ){ // Get imported graph

            System.out.println("add file");
            cov_added_file = true;

            int returnVal = cov_fileChooser.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                cov_file = cov_fileChooser.getSelectedFile();
                System.out.println(cov_file);
            }
        }

        if (event.getSource().equals(cov_button_ok)){

            Double hoursPerPass;
            Integer keyPlayerNumber;
            Integer maxSegmentSize;
            Double keyPlayerArrestProbability;
            Double arrestProbabilityStep;
            String stepIncreaseMethod;

            Graph initialGraph;
            CovertNetwork covertNetwork = new CovertNetwork();
            GraphInfo graphInfo = new GraphInfo();

            // covert network
            if (cov_textField_hours_per_pass.getText().equals("")){
                hoursPerPass = 1.0;
            }else {
                hoursPerPass = Double.parseDouble(cov_textField_hours_per_pass.getText());
                try {
                    hoursPerPass = Double.parseDouble(cov_textField_hours_per_pass.getText());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    // TODO handle the error
                }
            }

            if (cov_textField_key_players_number.getText().equals("")){
                keyPlayerNumber = 2;
            }else {
                keyPlayerNumber = Integer.parseInt(cov_textField_key_players_number.getText());
                try {
                    keyPlayerNumber = Integer.parseInt(cov_textField_key_players_number.getText());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    // TODO handle the error
                }
            }

            if (cov_textField_segment_size.getText().equals("")){
                maxSegmentSize = 5;
            } else {
                maxSegmentSize = Integer.parseInt(cov_textField_segment_size.getText());
                try {
                    maxSegmentSize = Integer.parseInt(cov_textField_segment_size.getText());
                } catch (NumberFormatException e){
                    e.printStackTrace();
                    // TODO handle the error
                }
            }

            if (cov_textField_arrest_probability_key_players.getText().equals("") || cov_textField_arrest_probability_key_players.getText().equals("%")){
                keyPlayerArrestProbability = 100.0;
            } else {
                keyPlayerArrestProbability = Double.parseDouble(cov_textField_arrest_probability_key_players.getText());
                try{
                    keyPlayerArrestProbability = Double.parseDouble(cov_textField_arrest_probability_key_players.getText());
                } catch (NumberFormatException e){
                    e.printStackTrace();
                    // TODO handle the error
                }
            }

            if (cov_textField_step.getText().equals("") || cov_textField_step.getText().equals("%")){
                arrestProbabilityStep = 10.0;
            } else {
                arrestProbabilityStep = Double.parseDouble(cov_textField_step.getText());
                try{
                    arrestProbabilityStep = Double.parseDouble(cov_textField_step.getText());
                } catch (NumberFormatException e){
                    e.printStackTrace();
                    // TODO handle the error
                }
            }

            stepIncreaseMethod = cov_comboBox_step_increase.getSelectedItem().toString();

            if (cov_comboBox_initial_graph.getSelectedItem().toString().equals("Add from file...")){
                System.out.println("initial");
                initialGraph = covertNetwork.initialImpGraph(cov_file);
                covertNetwork.buildCovertNetwork( initialGraph, cov_comboBox_algorithm.getSelectedItem().toString(), cov_slider_balance.getValue(), cov_comboBox_define_key_players_by.getSelectedItem().toString(),keyPlayerNumber,maxSegmentSize,keyPlayerArrestProbability,arrestProbabilityStep,stepIncreaseMethod);
                cov_added_file = false;
            }else {
//                initialGraph = covertNetwork.initialGenGraph("Preferential Attachment with Bernoulli",30);
//                covertNetwork.buildCovertNetwork(initialGraph,"Accurate method", 0, "Betweenness",5,5,100.0,10.0,"None");

                initialGraph = covertNetwork.initialGenGraph(cov_comboBox_initial_graph.getSelectedItem().toString(),cov_slider_node_number.getValue());
                covertNetwork.buildCovertNetwork( initialGraph, cov_comboBox_algorithm.getSelectedItem().toString(), cov_slider_balance.getValue(), cov_comboBox_define_key_players_by.getSelectedItem().toString(),keyPlayerNumber,maxSegmentSize,keyPlayerArrestProbability,arrestProbabilityStep,stepIncreaseMethod);
            }
        }
    }


    @Override
    public void stateChanged(ChangeEvent e) {

        JSlider source = (JSlider)e.getSource();

        // generated network
        if (source.equals(gen_slider_node_number)){
            if (!source.getValueIsAdjusting()) {
                int nodeNumber = source.getValue();
                gen_data_node_number.setText("      "+nodeNumber);
            }
        }
        // covert network
        else if (source.equals(cov_slider_node_number)){
            if (!source.getValueIsAdjusting()) {
                int nodeNumber = source.getValue();
                cov_data_node_number.setText("      "+nodeNumber);
            }
        }

    }


} // View.GUI class
