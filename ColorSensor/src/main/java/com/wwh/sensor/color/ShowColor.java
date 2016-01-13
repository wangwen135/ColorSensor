package com.wwh.sensor.color;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import javax.swing.border.TitledBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author wwh
 * @date 2016年1月11日 下午8:41:24
 *
 */
public class ShowColor extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ShowColor frame = new ShowColor();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public ShowColor() {
        setTitle("颜色识别");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 698, 493);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel_top = new JPanel();
        FlowLayout flowLayout_1 = (FlowLayout) panel_top.getLayout();
        flowLayout_1.setAlignment(FlowLayout.LEFT);
        contentPane.add(panel_top, BorderLayout.NORTH);

        JButton btn_refreshSerial = new JButton("刷新串口");
        btn_refreshSerial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        panel_top.add(btn_refreshSerial);

        JButton btn_openSerial = new JButton("打开串口");
        btn_openSerial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        panel_top.add(btn_openSerial);

        JPanel panel_left = new JPanel();
        panel_left.setBorder(new TitledBorder(null, "\u4E32\u53E3\u8BBE\u7F6E", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_left.setPreferredSize(new Dimension(180, 10));
        contentPane.add(panel_left, BorderLayout.WEST);
        GridBagLayout gbl_panel_left = new GridBagLayout();
        gbl_panel_left.columnWidths = new int[] { 60, 32, 0 };
        gbl_panel_left.rowHeights = new int[] { 21, 0, 0, 0, 0, 0, 0 };
        gbl_panel_left.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl_panel_left.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        panel_left.setLayout(gbl_panel_left);

        JLabel label = new JLabel("串  口");
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.insets = new Insets(0, 0, 5, 5);
        gbc_label.gridx = 0;
        gbc_label.gridy = 0;
        panel_left.add(label, gbc_label);

        JComboBox comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[] { "COM1", "COM2", "COM3" }));
        GridBagConstraints gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.insets = new Insets(0, 0, 5, 0);
        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox.anchor = GridBagConstraints.NORTH;
        gbc_comboBox.gridx = 1;
        gbc_comboBox.gridy = 0;
        panel_left.add(comboBox, gbc_comboBox);

        JLabel label_1 = new JLabel("波特率");
        GridBagConstraints gbc_label_1 = new GridBagConstraints();
        gbc_label_1.insets = new Insets(0, 0, 5, 5);
        gbc_label_1.gridx = 0;
        gbc_label_1.gridy = 1;
        panel_left.add(label_1, gbc_label_1);

        JComboBox comboBox_1 = new JComboBox();
        comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "9600", "19200", "38400" }));
        GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
        gbc_comboBox_1.insets = new Insets(0, 0, 5, 0);
        gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox_1.gridx = 1;
        gbc_comboBox_1.gridy = 1;
        panel_left.add(comboBox_1, gbc_comboBox_1);

        JLabel label_2 = new JLabel("数据位");
        GridBagConstraints gbc_label_2 = new GridBagConstraints();
        gbc_label_2.insets = new Insets(0, 0, 5, 5);
        gbc_label_2.gridx = 0;
        gbc_label_2.gridy = 2;
        panel_left.add(label_2, gbc_label_2);

        JComboBox comboBox_2 = new JComboBox();
        comboBox_2.setModel(new DefaultComboBoxModel(new String[] { "5", "6", "7", "8" }));
        comboBox_2.setSelectedIndex(3);
        GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
        gbc_comboBox_2.insets = new Insets(0, 0, 5, 0);
        gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox_2.gridx = 1;
        gbc_comboBox_2.gridy = 2;
        panel_left.add(comboBox_2, gbc_comboBox_2);

        JLabel label_3 = new JLabel("校验位");
        GridBagConstraints gbc_label_3 = new GridBagConstraints();
        gbc_label_3.insets = new Insets(0, 0, 5, 5);
        gbc_label_3.gridx = 0;
        gbc_label_3.gridy = 3;
        panel_left.add(label_3, gbc_label_3);

        JComboBox comboBox_3 = new JComboBox();
        comboBox_3.setModel(new DefaultComboBoxModel(new String[] { "None", "Even", "Odd", "Mark", "Space" }));
        GridBagConstraints gbc_comboBox_3 = new GridBagConstraints();
        gbc_comboBox_3.insets = new Insets(0, 0, 5, 0);
        gbc_comboBox_3.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox_3.gridx = 1;
        gbc_comboBox_3.gridy = 3;
        panel_left.add(comboBox_3, gbc_comboBox_3);

        JLabel label_4 = new JLabel("停止位");
        GridBagConstraints gbc_label_4 = new GridBagConstraints();
        gbc_label_4.insets = new Insets(0, 0, 5, 5);
        gbc_label_4.gridx = 0;
        gbc_label_4.gridy = 4;
        panel_left.add(label_4, gbc_label_4);

        JComboBox comboBox_4 = new JComboBox();
        comboBox_4.setModel(new DefaultComboBoxModel(new String[] { "1", "1.5", "2" }));
        GridBagConstraints gbc_comboBox_4 = new GridBagConstraints();
        gbc_comboBox_4.insets = new Insets(0, 0, 5, 0);
        gbc_comboBox_4.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox_4.gridx = 1;
        gbc_comboBox_4.gridy = 4;
        panel_left.add(comboBox_4, gbc_comboBox_4);

        JLabel label_5 = new JLabel("流  控");
        GridBagConstraints gbc_label_5 = new GridBagConstraints();
        gbc_label_5.insets = new Insets(0, 0, 0, 5);
        gbc_label_5.gridx = 0;
        gbc_label_5.gridy = 5;
        panel_left.add(label_5, gbc_label_5);

        JComboBox comboBox_5 = new JComboBox();
        comboBox_5.setModel(new DefaultComboBoxModel(new String[] { "None" }));
        GridBagConstraints gbc_comboBox_5 = new GridBagConstraints();
        gbc_comboBox_5.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox_5.gridx = 1;
        gbc_comboBox_5.gridy = 5;
        panel_left.add(comboBox_5, gbc_comboBox_5);

        JPanel panel_center = new JPanel();
        contentPane.add(panel_center, BorderLayout.CENTER);
        panel_center.setLayout(null);

        JButton button = new JButton("白平衡");
        button.setBounds(10, 10, 69, 23);
        panel_center.add(button);

        JButton button_1 = new JButton("取色");
        button_1.setBounds(10, 65, 93, 23);
        panel_center.add(button_1);

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setBounds(10, 98, 173, 119);
        panel_center.add(panel);
        
        JButton btnTest = new JButton("test");
        btnTest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnTest.setBounds(131, 10, 93, 23);
        panel_center.add(btnTest);

        JPanel panel_console = new JPanel();
        panel_console.setBorder(new TitledBorder(null, "\u63A7\u5236\u53F0", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_console.setPreferredSize(new Dimension(10, 180));
        contentPane.add(panel_console, BorderLayout.SOUTH);
        panel_console.setLayout(new BorderLayout(0, 0));

        JPanel panel_send = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel_send.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        panel_console.add(panel_send, BorderLayout.NORTH);

        JLabel lblNewLabel = new JLabel("发送数据：");
        panel_send.add(lblNewLabel);

        textField = new JTextField();
        panel_send.add(textField);
        textField.setColumns(80);

        JButton btnNewButton = new JButton("发送");
        panel_send.add(btnNewButton);

        JScrollPane scrollPane = new JScrollPane();
        panel_console.add(scrollPane, BorderLayout.CENTER);

        JTextArea textArea = new JTextArea();
        scrollPane.setViewportView(textArea);

    }
}
