package gui_swing_events;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.GridLayout;

// enum all functions
enum Functions {
    AUTOSUM,
    AVERAGE,
    MAXIMUM,
    MINIMUM
}

public class SwingEventWindow extends JFrame implements ActionListener, ItemListener {

    JPanel mainPanel;
    JPanel lblMain;
    JPanel lblRequest;
    JPanel txtNum;
    JPanel radBtns;
    JPanel calcBtn;
    JPanel lblResult;

    JTextField txtNumField;
    JTextField txtResultField;

    Functions function;

    public class JFRadioButton extends JRadioButton {
        private Functions function;

        public JFRadioButton(String text, Functions function) {
            super(text);
            this.function = function;
        }

        public Functions getFunction() {
            return this.function;
        }
    }

    public class JTextPrompt extends JTextField implements FocusListener {
        private String paceHolder;

        public JTextPrompt(String paceHolder) {
            this.paceHolder = paceHolder;
            this.addFocusListener(this);
            this.setText(paceHolder);
            this.setForeground(Color.GRAY);
        }

        @Override
        public void focusGained(FocusEvent e) {
            if (this.getText().equals(this.paceHolder)) {
                this.setText("");
                this.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (this.getText().isEmpty()) {
                this.setText(this.paceHolder);
                this.setForeground(Color.GRAY);
            }
        }
    }

    public SwingEventWindow() {
        // Configure the Frame
        this.setTitle("Assignment 1 - Swing Events");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the main panel
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        this.mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 15, 50));
        this.add(this.mainPanel);

        // Initialize the panels
        this.lblMain = new JPanel();
        this.txtNum = new JPanel();
        this.radBtns = new JPanel();
        this.calcBtn = new JPanel();
        this.lblResult = new JPanel();

        // Add the panels to the main panel
        this.mainPanel.add(this.lblMain);
        this.mainPanel.add(this.txtNum);
        this.mainPanel.add(this.radBtns);
        this.mainPanel.add(this.calcBtn);
        this.mainPanel.add(this.lblResult);

        // Create the labels
        JLabel lblTitle = new JLabel("Simple Swing Window");
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        lblTitle.setFont(lblTitle.getFont().deriveFont(24.0f));
        this.lblMain.add(lblTitle);

        JLabel lblRequest = new JLabel("Enter a number: ");
        lblRequest.setHorizontalAlignment(JLabel.CENTER);
        this.lblMain.add(lblRequest);

        this.lblMain.setLayout(new GridLayout(2, 1));

        txtNumField = new JTextPrompt("Enter numbers separated by commas");
        txtNumField.setColumns(30);
        this.txtNum.add(txtNumField);

        // Create the radio buttons
        JRadioButton radBtn1 = new JFRadioButton("Autosum", Functions.AUTOSUM);
        // add value to the radio button
        JRadioButton radBtn2 = new JFRadioButton("Average", Functions.AVERAGE);
        JRadioButton radBtn3 = new JFRadioButton("Maximum", Functions.MAXIMUM);
        JRadioButton radBtn4 = new JFRadioButton("Minimum", Functions.MINIMUM);

        // Create a ButtonGroup to group the radio buttons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radBtn1);
        buttonGroup.add(radBtn2);
        buttonGroup.add(radBtn3);
        buttonGroup.add(radBtn4);

        // Add the radio buttons to the panel
        this.radBtns.add(radBtn1);
        this.radBtns.add(radBtn2);
        this.radBtns.add(radBtn3);
        this.radBtns.add(radBtn4);

        // add the action listener to the radio buttons
        radBtn1.addItemListener(this);
        radBtn2.addItemListener(this);
        radBtn3.addItemListener(this);
        radBtn4.addItemListener(this);

        // Create the button
        JButton btnCalc = new JButton("Calculate");
        btnCalc.setAlignmentX(JButton.CENTER_ALIGNMENT);
        btnCalc.addActionListener(this);
        this.calcBtn.add(btnCalc);

        // Create the label
        JLabel lblResult = new JLabel("Result: ");
        lblResult.setHorizontalAlignment(JLabel.CENTER);
        this.lblResult.add(lblResult);

        // add a text field to display the result
        txtResultField = new JTextField();
        txtResultField.setColumns(10);
        txtResultField.setEditable(false);
        this.lblResult.add(txtResultField);

        // Resize the frame to fit the components
        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Calculate")) {
            try {
                Excel excel = new Excel(this.txtNumField.getText());
                if (this.function == null) {
                    JOptionPane.showMessageDialog(this, "Please select a function", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Double result = 0d;
                switch (this.function) {
                    case AUTOSUM:
                        result = excel.findTotal();
                        break;
                    case AVERAGE:
                        result = excel.findAvg();
                        break;
                    case MAXIMUM:
                        result = excel.findMax();
                        break;
                    case MINIMUM:
                        result = excel.findMin();
                        break;
                    default:
                        break;
                }
                // only display 4 decimal places or less
                if (result % 1 == 0) {
                    txtResultField.setText(String.format("%.0f", result));
                } else if (result * 10 % 1 == 0) {
                    txtResultField.setText(String.format("%.1f", result));
                } else if (result * 100 % 1 == 0) {
                    txtResultField.setText(String.format("%.2f", result));
                } else if (result * 1000 % 1 == 0) {
                    txtResultField.setText(String.format("%.3f", result));
                } else {
                    txtResultField.setText(String.format("%.4f", result));
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
                txtNumField.setText("");
                return;
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            this.function = ((JFRadioButton) e.getItem()).getFunction();
        }
    }
}
