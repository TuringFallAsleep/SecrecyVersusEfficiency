package View;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Dictionary;
import java.util.Hashtable;

public class GUI extends JPanel implements ActionListener, ChangeListener{

    private Dimension panelSize = new Dimension(800,670);
    /* Generated graph*/
    private JLabel gen_label_data = new JLabel("Data: ");
    private JLabel gen_label_structure = new JLabel("Structure: ");
    private JLabel gen_label_node_number = new JLabel("Node number: ");
    private JLabel gen_data_node_number = new JLabel("      15");
    private JLabel gen_label_analysis = new JLabel("Analysis: ");
    private JLabel gen_label_hours_per_pass = new JLabel("Hours per pass: ");
    private JLabel gen_label_define_key_players_by = new JLabel("Define key players by: ");
    private JLabel gen_label_key_players_number = new JLabel("Key players number: ");
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

    private JTextField gen_textField_hours_per_pass = new JTextField();
    private JTextField gen_textField_key_players_number = new JTextField();
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
    private JTextField imp_textField_arrest_probability_key_players = new JTextField("%");
    private JTextField imp_textField_step = new JTextField("%");

    private JButton imp_button_select = new JButton("Select");
    private JButton imp_button_ok = new JButton("OK");

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
    private JTextField cov_textField_arrest_probability_key_players = new JTextField("%");
    private JTextField cov_textField_step = new JTextField("%");

    private JButton cov_button_ok = new JButton("OK");



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
        gen_comboBox_structure.addItem("Covert Network Model (based on betweenness)");

        gen_slider_node_number.setMajorTickSpacing(5);
        gen_slider_node_number.setMinorTickSpacing(1);
        gen_slider_node_number.setPaintTicks(true);
        gen_slider_node_number.setPaintLabels(true);
        gen_slider_node_number.setSnapToTicks(true);
        gen_slider_node_number.addChangeListener(this);
//        gen_comboBox_structure.getMaximumSize();

        gen_comboBox_efficiency.addItem("Distribution of diameter");

        gen_comboBox_define_key_players_by.addItem("Degree");
        gen_comboBox_define_key_players_by.addItem("Closeness");
        gen_comboBox_define_key_players_by.addItem("Betweenness");

        gen_comboBox_step_increase.addItem("None");
        gen_comboBox_step_increase.addItem("Linear");
    }

    private void initialiseImpComponents(){
        imp_comboBox_efficiency.addItem("Distribution of diameter");

        imp_comboBox_define_key_players_by.addItem("Degree");
        imp_comboBox_define_key_players_by.addItem("Closeness");
        imp_comboBox_define_key_players_by.addItem("Betweenness");

        imp_comboBox_step_increase.addItem("None");
        imp_comboBox_step_increase.addItem("Linear");

    }

    private void initialiseCovComponents(){

        cov_comboBox_initial_graph.addItem("Highly Centralised");
        cov_comboBox_initial_graph.addItem("Highly Decentralised");
        cov_comboBox_initial_graph.addItem("Bernoulli");
        cov_comboBox_initial_graph.addItem("Preferential Attachment");
        cov_comboBox_initial_graph.addItem("Preferential Attachment with Bernoulli");
        cov_comboBox_initial_graph.addItem("Covert Network Model (based on betweenness)");
        cov_comboBox_initial_graph.addItem("Add from file...");

        cov_slider_node_number.setMajorTickSpacing(5);
        cov_slider_node_number.setMinorTickSpacing(1);
        cov_slider_node_number.setPaintTicks(true);
        cov_slider_node_number.setPaintLabels(true);
        cov_slider_node_number.setSnapToTicks(true);
        cov_slider_node_number.addChangeListener(this);

        cov_comboBox_algorithm.addItem("Accurate method");
        cov_comboBox_algorithm.addItem("Fast method");



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

        cov_comboBox_efficiency.addItem("Distribution of diameter");

        cov_comboBox_define_key_players_by.addItem("Degree");
        cov_comboBox_define_key_players_by.addItem("Closeness");
        cov_comboBox_define_key_players_by.addItem("Betweenness");

        cov_comboBox_step_increase.addItem("None");
        cov_comboBox_step_increase.addItem("Linear");

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




////      Old version
//    private String networkType = new String();
//    private int nodeNum;
//    private TextField textField = new TextField ();
//
//    private JLabel genNodeNum = new JLabel();
//    private JLabel genMaxDeg = new JLabel();
//    private JLabel genMinDeg = new JLabel();
//    private JLabel genAveDeg = new JLabel();
//    private JLabel genMaxDia = new JLabel(); // max diameter
//    private JLabel genMaxBet = new JLabel(); // max betweenness
//    private JLabel genMaxClo = new JLabel(); // max closeness
//    private JLabel genSercey = new JLabel();
//
//    private JLabel realNodeNum = new JLabel();
//    private JLabel realMaxDeg = new JLabel();
//    private JLabel realMinDeg = new JLabel();
//    private JLabel realAveDeg = new JLabel();
//    private JLabel realMaxDia = new JLabel(); // max diameter
//    private JLabel realMaxBet = new JLabel(); // max betweenness
//    private JLabel realMaxClo = new JLabel(); // max closeness
//    private JLabel realSecrecy = new JLabel();
//
//    private JComboBox genComboBox = new JComboBox();
//    private JComboBox realComboBox = new JComboBox();
//
//    private JComboBox networkTypeCombobox = new JComboBox();
//
//    private JButton genProcessBtn = new JButton("OK");
//    private JButton realProcessBtn = new JButton("OK");
//
//
//
//    public  GUI(){
//
//
//
//        JFrame frame = new JFrame();
//
//        JPanel genInfoPanel = new JPanel();
//        JPanel genDataPanel = new JPanel();
//        final JPanel genNodeNumPanel = new JPanel();
//        JPanel genBtnPanel = new JPanel();
//
//        JPanel realInfoPanel = new JPanel();
//        JPanel realDataPanel = new JPanel();
//        JPanel realBtnPanel = new JPanel();
//
//        Dimension d = new Dimension(15,10);
//
//
//
//        frame.setTitle("Secrecy VS Efficiency");
//        frame.setSize(800,500);
//
//        Container container = getContentPane();
//        container.setLayout(new GridLayout(1,1,20,10));
//
//        final JPanel graphPanel = new JPanel();
//        container.add(graphPanel);
//        graphPanel.setLayout(new GridLayout(0,1));
//
//        JPanel realPanel = new JPanel();
//        container.add(realPanel);
//        realPanel.setLayout(new GridLayout(0,1));
//
////        HBox hBox = new HBox();
////        hBox.
//
//        /*Set up Generated Graph*/
//        networkTypeCombobox.addItem("Generated Network");
//        networkTypeCombobox.addItem("Real Network");
//        networkTypeCombobox.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                if (e.getStateChange() == ItemEvent.SELECTED){
//                    networkType = e.getItem().toString();
//
//                    System.out.println(networkType);
//
//                    if (networkType.equals("Real Network")){
//                        genNodeNumPanel.setVisible(true);
//                        nodeNum = Integer.parseInt(textField.getText());
//                    }else{
//                        genNodeNumPanel.setVisible(false);
//                    }
//                }
//            }
//        });
//
//
//        graphPanel.add(networkTypeCombobox);
////        System.out.println("Network type: "+networkTypeCombobox.getSelectedItem());
//
//        graphPanel.add(genNodeNumPanel);
//        genNodeNumPanel.setLayout(new GridLayout(1,2,0,0));
//        genNodeNumPanel.add(new JLabel("Node number: "));
//        textField.setText("14");
//        genNodeNumPanel.add(textField);
//        genNodeNumPanel.setVisible(false);
//
//
//            /*Generated Graph Info*/
//            graphPanel.add(genInfoPanel);
//            genInfoPanel.setLayout(new GridLayout(0,1,5, 5));
//
//            genInfoPanel.add(new JLabel("Generated Graph"));
//
//            genComboBox.setBounds(10,10, 80, 25);
//            genComboBox.addItem("Highly Centralised");
//            genComboBox.addItem("Highly Decentralised");
//            genComboBox.addItem("Bernoulli");
//            genComboBox.addItem("Preferential Attachment");
//            genComboBox.addItem("Preferential Attachment with Bernoulli");
//            genComboBox.addItem("Covert Network Model (based on betweenness)");
//            genInfoPanel.add(genComboBox);
//            genComboBox.addActionListener(this);
//
//        /*Generated Graph Data*/
//            graphPanel.add(genDataPanel);
//            genDataPanel.setLayout(new GridLayout(0,2,10,20));
//            genNodeNum.setText("");
//            genMaxDeg.setText("");
//            genMinDeg.setText("");
//            genAveDeg.setText("");
//            genMaxDia.setText("");
//            genMaxBet.setText("");
//            genMaxClo.setText("");
//            genSercey.setText("");
//
//            genDataPanel.add(new JLabel("Node number: "));
//            genDataPanel.add(genNodeNum);
//            genDataPanel.add(new JLabel("Max degree: "));
//            genDataPanel.add(genMaxDeg);
//            genDataPanel.add(new JLabel("Min degree: "));
//            genDataPanel.add(genMinDeg);
//            genDataPanel.add(new JLabel("Ave degree: "));
//            genDataPanel.add(genAveDeg);
//            genDataPanel.add(new JLabel("Max diameter: "));
//            genDataPanel.add(genMaxDia);
//            genDataPanel.add(new JLabel("Max betweenness: "));
//            genDataPanel.add(genMaxBet);
//            genDataPanel.add(new JLabel("Max closeness(*1000): "));
//            genDataPanel.add(genMaxClo);
//            genDataPanel.add(new JLabel("Secrecy: "));
//
//
//        /*Generated Graph Btn*/
//            graphPanel.add(genBtnPanel);
//            genBtnPanel.setLayout(new GridLayout(0,1,10,20));
//            genProcessBtn.setPreferredSize(d);
//            genBtnPanel.add(new JLabel(""));
//            genBtnPanel.add(genProcessBtn);
//            genBtnPanel.add(new JLabel(""));
//            genProcessBtn.addActionListener(this);
//
//
//        /*Set up Real Graph*/
//
//        /*Real Graph Info*/
//            realPanel.add(realInfoPanel);
//            realInfoPanel.setLayout(new GridLayout(0,1, 5, 5));
//            realInfoPanel.add(new JLabel("Real Graph"));
//
//            realComboBox.setBounds(10,10, 80, 25);
//            realComboBox.addItem("9_11 Graph");
//            realComboBox.addItem("Suffragettes Inner Circle");
//            realComboBox.addItem("Add file from this computer...");
//            realInfoPanel.add(realComboBox);
//            realComboBox.addActionListener(this);
//
//
//        /*Real Graph Data*/
//            realPanel.add(realDataPanel);
//            realDataPanel.setLayout(new GridLayout(0,2,10,20));
//            realNodeNum.setText("");
//            realMaxDeg.setText("");
//            realMinDeg.setText("");
//            realAveDeg.setText("");
//            realMaxDia.setText("");
//            realMaxBet.setText("");
//            realMaxClo.setText("");
//            realSecrecy.setText("");
//
//
//            realDataPanel.add(new JLabel("Node number: "));
//            realDataPanel.add(realNodeNum);
//            realDataPanel.add(new JLabel("Max degree: "));
//            realDataPanel.add(realMaxDeg);
//            realDataPanel.add(new JLabel("Min degree: "));
//            realDataPanel.add(realMinDeg);
//            realDataPanel.add(new JLabel("Ave degree: "));
//            realDataPanel.add(realAveDeg);
//            realDataPanel.add(new JLabel("Max distance: "));
//            realDataPanel.add(realMaxDia);
//            realDataPanel.add(new JLabel("Max betweenness: "));
//            realDataPanel.add(realMaxBet);
//            realDataPanel.add(new JLabel("Max closeness(*1000): "));
//            realDataPanel.add(realMaxClo);
//            realDataPanel.add(new JLabel("Secrecy"));
//            realDataPanel.add(realSecrecy);
//
//
//        /*Real Graph Btn*/
//            realPanel.add(realBtnPanel);
//            realBtnPanel.setLayout(new GridLayout(0,1,10,20));
//            realProcessBtn.setPreferredSize(d);
//            realBtnPanel.add(new JLabel(""));
//            realBtnPanel.add(realProcessBtn);
//            realBtnPanel.add(new JLabel(""));
//
//            realProcessBtn.addActionListener(this);
//
//            setVisible(true);
//
//
//
//        frame.setDefaultCloseOperation(HIDE_ON_CLOSE);  // EXIT_ON_CLOSE, DISPOSE_ON_CLOSE
//        pack();
//    } // createGui
//
//
//    public void actionPerformed(ActionEvent event){
//        if (event.getSource() == genProcessBtn){
//            FixedGraph genGraph = new FixedGraph();
//            CovertNetwork covNet = new CovertNetwork();
//            Double[][] graphResult = {};
//
//            // decide which graph to display
//            if (genComboBox.getSelectedItem().toString().equals("Highly Centralised")){
//                graphResult = genGraph.HighlyCentralised();
//            }else if(genComboBox.getSelectedItem().toString().equals("Highly Decentralised")){
//                graphResult = genGraph.HighlyDecentralised();
//            }else if (genComboBox.getSelectedItem().toString().equals("Bernoulli")){
//                graphResult = genGraph.B();
//            }else if(genComboBox.getSelectedItem().toString().equals("Preferential Attachment")){
//                graphResult = genGraph.PA();
//            }else if(genComboBox.getSelectedItem().toString().equals("Preferential Attachment with Bernoulli")){
//                graphResult = genGraph.PAB();
//            }else if(genComboBox.getSelectedItem().toString().equals("Covert Network Model (based on betweenness)")){
//                covNet.betweennessNet();
//            }
//
//            if (!genComboBox.getSelectedItem().toString().equals("Covert Network Model (based on betweenness)")) {
//                genNodeNum.setText("" + graphResult[0][0]); // node number
//                genMaxDeg.setText("" + graphResult[1][0]); // max degree
//                genMinDeg.setText("" + graphResult[2][0]); // min degree
//                genAveDeg.setText("" + String.format("%.1f", graphResult[3][0])); // average degree
//                genMaxDia.setText("" + graphResult[4][0]); // All-pair shortest paths lengths.
//                genMaxBet.setText("" + graphResult[7][0]); // max betweenness
//                genMaxClo.setText("" + String.format("%.5f", graphResult[9][0] * 1000)); // max closeness
//
//            }
//
//        } else if (event.getSource() == realProcessBtn){
//            FixedGraph realGraph = new FixedGraph();
//            if (realComboBox.getSelectedItem().toString().equals("Add file from this computer...")){
//                System.out.println("Read from file");
//            } else {
//                Double[][] graphResult = realGraph.RG(realComboBox.getSelectedItem().toString());
//                realNodeNum.setText(""+graphResult[0][0]); // node number
//                realMaxDeg.setText(""+graphResult[1][0]); // max degree
//                realMinDeg.setText(""+graphResult[2][0]); // min degree
//                realAveDeg.setText(""+String.format("%.1f", graphResult[3][0])); // average degree
//                realMaxDia.setText(""+graphResult[4][0]); // All-pair shortest paths lengths.
//                realMaxBet.setText(""+String.format("%.1f",graphResult[7][0])); // max betweenness
//                realMaxClo.setText(""+String.format("%.5f",graphResult[9][0]*1000)); // max closeness
//            }
//
//
//
//        }
//
//
//    } // actionPerformed



} // View.GUI class
