package at.schunker.javaswing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class App implements ActionListener, MouseListener {

    private JFrame mainFrame = null;
    private JPanel topPanel = null;
    private JPanel centerPanel = null;
    private JPanel bottomPanel = null;
    private JLabel mainLabel = null;

    private JTextField mainTextField = null;
    private JRadioButton option1RadioButton = null;
    private JRadioButton option2RadioButton = null;
    private JRadioButton option3RadioButton = null;
    //private JComboBox typeComboBox = null;

    private JList mainList = null;
    private DefaultListModel mainListModel = new DefaultListModel();

    private JButton addButton = null;
    private JButton deleteButton = null;
    private JButton clearAllButton = null;

    public static void main( String[] args ) {
        System.out.println( "==== JavaSwing ====" );
        App app = new App();
    }

    public App(){
        this.init();
    }

    public void init(){
        this.mainFrame = new JFrame("Java Swing");
        this.mainFrame.setSize(300, 300);
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //==== topPanel ====

        this.mainLabel = new JLabel("Enter the date (dd-mm-yyyy)");
        this.mainLabel.setHorizontalAlignment(JLabel.HORIZONTAL);
        this.mainLabel.setBounds(50,50,100,44);

        this.mainTextField = new JTextField();
        this.mainTextField.setHorizontalAlignment(JTextField.HORIZONTAL);
        this.mainTextField.addActionListener(this);

        this.option1RadioButton = new JRadioButton("Option 1");
        this.option1RadioButton.addActionListener(this);

        this.option2RadioButton = new JRadioButton("Option 2");
        this.option2RadioButton.addActionListener(this);

        this.option3RadioButton = new JRadioButton("Option 3");
        this.option3RadioButton.addActionListener(this);

        ButtonGroup typeButtonGroup = new ButtonGroup();
        typeButtonGroup.add(this.option1RadioButton);
        typeButtonGroup.add(this.option2RadioButton);
        typeButtonGroup.add(this.option3RadioButton);

        //this.outputLabel.setBounds(50,50,100,44);

        /**
         * JPanel with GridBagLayout
         this.topPanel = new JPanel(new GridBagLayout());
         GridBagConstraints constraints = new GridBagConstraints();
         constraints.anchor = GridBagConstraints.WEST;
         constraints.insets = new Insets(10, 10 , 10 , 10);
         constraints.gridx = 0;
         constraints.gridy = 0;
         constraints.gridx = 1;
         constraints.gridx = 0;
         constraints.gridy = 1;
         */

        /**
         * JPanel with BorderLayout
         this.topPanel = new JPanel(new BorderLayout());
         this.topPanel.add(this.mainLabel, BorderLayout.PAGE_START, 0);
         this.topPanel.add(this.mainTextField, BorderLayout.LINE_END, 1);
         */

        this.topPanel = new JPanel();   //FlowLayoutManager
        this.topPanel.setLayout(new BoxLayout(this.topPanel, BoxLayout.PAGE_AXIS));

        this.topPanel.add(this.mainLabel, 0);
        this.topPanel.add(this.mainTextField, BoxLayout.LINE_AXIS, 1);
        this.topPanel.add(this.option1RadioButton, 2);
        this.topPanel.add(this.option2RadioButton, 3);
        this.topPanel.add(this.option3RadioButton, 4);

        //==== centerPanel ====

        this.mainList = new JList(this.mainListModel);
        this.mainList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.mainList.addMouseListener(this);

        this.centerPanel = new JPanel();
        this.centerPanel.setLayout(new BorderLayout());

        this.centerPanel.add(this.mainList, 0);
        this.centerPanel.add(new JScrollPane(this.mainList));

        //==== bottomPanel ====

        this.addButton = new JButton("Add");
        this.addButton.addActionListener(this);

        this.deleteButton = new JButton("Delete");
        this.deleteButton.addActionListener(this);

        this.clearAllButton = new JButton("Clear");
        this.clearAllButton.addActionListener(this);

        this.bottomPanel = new JPanel();
        this.bottomPanel.add(this.addButton, 0);
        this.bottomPanel.add(this.deleteButton, 1);
        this.bottomPanel.add(this.clearAllButton, 2);

        //==== mainFrame ====

        this.mainFrame.add(this.topPanel, BorderLayout.PAGE_START);
        this.mainFrame.add(this.centerPanel, BorderLayout.CENTER);
        this.mainFrame.add(this.bottomPanel, BorderLayout.PAGE_END);
        this.mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == this.addButton){
            int size = this.mainListModel.getSize();
            String s = "";

            if(this.option1RadioButton.isSelected()){
                s = "Option 1";
            } else if(this.option2RadioButton.isSelected()){
                s = "Option 2";
            } else if(this.option3RadioButton.isSelected()){
                s = "Option 3";
            }

            if(!this.mainTextField.getText().trim().isEmpty()){
                System.out.println("text: [" + this.mainTextField.getText() + "]");
                s = this.mainTextField.getText();
            }

            s = s + " " + String.valueOf(size);

            this.mainListModel.addElement(s);
        }

        if(e.getSource() == this.deleteButton){
            ListSelectionModel selectionModel = this.mainList.getSelectionModel();
            int index = selectionModel.getMinSelectionIndex();
            System.out.println("index " + index);

            if(index < 0){
                return;
            }

            this.mainListModel.remove(index);
        }

        if(e.getSource() == this.clearAllButton){
            this.mainListModel.clear();
        }

        int rowCount = this.mainList.getVisibleRowCount();
        System.out.println(rowCount);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == this.mainList && e.getClickCount() == 2){
            System.out.println("mouseClicked source = mainList");
            Point p = e.getPoint();
            int index = this.mainList.locationToIndex(p);

            if(index == -1){
                return;
            }

            System.out.println("index " + index);
            Object item = this.mainListModel.getElementAt(index);
            String text = JOptionPane.showInputDialog("Change Item", item);
            text = text.trim();

            if(text.isEmpty()){
                return;
            }

            this.mainListModel.remove(index);
            this.mainListModel.add(index, text);
            ListSelectionModel selectionModel = this.mainList.getSelectionModel();
            selectionModel.setLeadSelectionIndex(index);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
