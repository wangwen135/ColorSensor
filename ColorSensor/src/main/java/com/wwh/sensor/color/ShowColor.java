package com.wwh.sensor.color;

import gnu.io.CommPortIdentifier;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.wwh.sensor.serial.SerialConfig;
import com.wwh.sensor.serial.SerialDataReceiveHandler;
import com.wwh.sensor.serial.SerialReadAndWriteHandler;

/**
 * <pre>
 * 读取并显示颜色
 * </pre>
 *
 * @author wwh
 * @date 2016年1月11日 下午8:41:24
 *
 */
public class ShowColor extends JFrame implements SerialDataReceiveHandler {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_send;
	private JPanel panel_color;
	private JTextArea txta_console;
	private JComboBox<String> cmb_serialPort;
	private JComboBox<Integer> cmb_baudRate;
	private JComboBox<Integer> cmb_dataBits;
	private JComboBox<String> cmb_parity;
	private JComboBox<String> cmb_stopBits;
	private JComboBox<String> cmb_flowControl;
	private JPanel panel_colorRed;
	private JPanel panel_colorGreen;
	private JPanel panel_colorBlue;

	private SerialReadAndWriteHandler sRWHandler;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		String windows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		try {
			UIManager.setLookAndFeel(windows);
		} catch (Exception e1) {
			//
		}
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
		setBounds(100, 100, 700, 650);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel_console = new JPanel();
		panel_console.setBorder(new TitledBorder(null, "\u63A7\u5236\u53F0",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_console.setPreferredSize(new Dimension(10, 180));
		contentPane.add(panel_console, BorderLayout.CENTER);
		panel_console.setLayout(new BorderLayout(0, 0));

		JPanel panel_send = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_send.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_console.add(panel_send, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel("发送数据：");
		panel_send.add(lblNewLabel);

		txt_send = new JTextField();
		panel_send.add(txt_send);
		txt_send.setColumns(60);

		JButton btn_send = new JButton("发送");
		btn_send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});
		panel_send.add(btn_send);

		JScrollPane scrollPane = new JScrollPane();
		panel_console.add(scrollPane, BorderLayout.CENTER);

		txta_console = new JTextArea();
		scrollPane.setViewportView(txta_console);

		JPanel panel_top = new JPanel();
		panel_top.setPreferredSize(new Dimension(10, 270));
		contentPane.add(panel_top, BorderLayout.NORTH);
		panel_top.setLayout(new BorderLayout(0, 0));

		JPanel panel_left = new JPanel();
		panel_top.add(panel_left, BorderLayout.WEST);
		panel_left.setBorder(new TitledBorder(null, "\u4E32\u53E3\u8BBE\u7F6E",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_left.setPreferredSize(new Dimension(180, 10));
		GridBagLayout gbl_panel_left = new GridBagLayout();
		gbl_panel_left.columnWidths = new int[] { 60, 32, 0 };
		gbl_panel_left.rowHeights = new int[] { 0, 21, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_left.columnWeights = new double[] { 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_panel_left.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_left.setLayout(gbl_panel_left);

		JButton btn_refreshSerial = new JButton("刷新串口");
		GridBagConstraints gbc_btn_refreshSerial = new GridBagConstraints();
		gbc_btn_refreshSerial.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn_refreshSerial.insets = new Insets(0, 0, 5, 0);
		gbc_btn_refreshSerial.gridx = 1;
		gbc_btn_refreshSerial.gridy = 0;
		panel_left.add(btn_refreshSerial, gbc_btn_refreshSerial);
		btn_refreshSerial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshSerial();
			}
		});

		JLabel label = new JLabel("串  口");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 1;
		panel_left.add(label, gbc_label);

		cmb_serialPort = new JComboBox<String>();
		GridBagConstraints gbc_cmb_serialPort = new GridBagConstraints();
		gbc_cmb_serialPort.insets = new Insets(0, 0, 5, 0);
		gbc_cmb_serialPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmb_serialPort.anchor = GridBagConstraints.NORTH;
		gbc_cmb_serialPort.gridx = 1;
		gbc_cmb_serialPort.gridy = 1;
		panel_left.add(cmb_serialPort, gbc_cmb_serialPort);

		JLabel label_1 = new JLabel("波特率");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 2;
		panel_left.add(label_1, gbc_label_1);

		cmb_baudRate = new JComboBox<Integer>();
		cmb_baudRate.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {
				9600, 19200, 38400 }));
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 1;
		gbc_comboBox_1.gridy = 2;
		panel_left.add(cmb_baudRate, gbc_comboBox_1);

		JLabel label_2 = new JLabel("数据位");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 0;
		gbc_label_2.gridy = 3;
		panel_left.add(label_2, gbc_label_2);

		cmb_dataBits = new JComboBox<Integer>();
		cmb_dataBits.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {
				8, 7, 6, 5 }));
		GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
		gbc_comboBox_2.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_2.gridx = 1;
		gbc_comboBox_2.gridy = 3;
		panel_left.add(cmb_dataBits, gbc_comboBox_2);

		JLabel label_3 = new JLabel("校验位");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 0;
		gbc_label_3.gridy = 4;
		panel_left.add(label_3, gbc_label_3);

		cmb_parity = new JComboBox<String>();
		cmb_parity.setModel(new DefaultComboBoxModel<String>(new String[] {
				"NONE", "EVEN", "ODD", "MARK", "SPACE" }));
		GridBagConstraints gbc_comboBox_3 = new GridBagConstraints();
		gbc_comboBox_3.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_3.gridx = 1;
		gbc_comboBox_3.gridy = 4;
		panel_left.add(cmb_parity, gbc_comboBox_3);

		JLabel label_4 = new JLabel("停止位");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 0;
		gbc_label_4.gridy = 5;
		panel_left.add(label_4, gbc_label_4);

		cmb_stopBits = new JComboBox<String>();
		cmb_stopBits.setModel(new DefaultComboBoxModel<String>(new String[] {
				"1", "2", "1.5" }));
		GridBagConstraints gbc_comboBox_4 = new GridBagConstraints();
		gbc_comboBox_4.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_4.gridx = 1;
		gbc_comboBox_4.gridy = 5;
		panel_left.add(cmb_stopBits, gbc_comboBox_4);

		JLabel label_5 = new JLabel("流  控");
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.insets = new Insets(0, 0, 5, 5);
		gbc_label_5.gridx = 0;
		gbc_label_5.gridy = 6;
		panel_left.add(label_5, gbc_label_5);

		cmb_flowControl = new JComboBox<String>();
		cmb_flowControl.setModel(new DefaultComboBoxModel<String>(
				new String[] { "NONE", "RTSCTS_IN", "RTSCTS_OUT", "XONXOFF_IN",
						"XONXOFF_OUT" }));
		GridBagConstraints gbc_comboBox_5 = new GridBagConstraints();
		gbc_comboBox_5.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_5.gridx = 1;
		gbc_comboBox_5.gridy = 6;
		panel_left.add(cmb_flowControl, gbc_comboBox_5);

		JButton btn_openSerial = new JButton("打开串口");
		GridBagConstraints gbc_btn_openSerial = new GridBagConstraints();
		gbc_btn_openSerial.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn_openSerial.insets = new Insets(0, 0, 5, 0);
		gbc_btn_openSerial.gridx = 1;
		gbc_btn_openSerial.gridy = 7;
		panel_left.add(btn_openSerial, gbc_btn_openSerial);
		btn_openSerial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openSerial();
			}
		});

		JButton btn_closeSerial = new JButton("关闭串口");
		btn_closeSerial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeSerial();
			}
		});
		GridBagConstraints gbc_btn_closeSerial = new GridBagConstraints();
		gbc_btn_closeSerial.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn_closeSerial.gridx = 1;
		gbc_btn_closeSerial.gridy = 8;
		panel_left.add(btn_closeSerial, gbc_btn_closeSerial);

		JPanel panel_center = new JPanel();
		panel_center.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "\u64CD\u4F5C\u533A",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_top.add(panel_center, BorderLayout.CENTER);
		panel_center.setLayout(null);

		JButton btn_whiteBlance = new JButton("设置白平衡");
		btn_whiteBlance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendDataEndOfLF("0");
			}
		});
		btn_whiteBlance.setBounds(86, 97, 93, 23);
		panel_center.add(btn_whiteBlance);

		JButton btn_getColor = new JButton("取色");
		btn_getColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendDataEndOfLF("1");
			}
		});
		btn_getColor.setBounds(86, 130, 93, 23);
		panel_center.add(btn_getColor);

		JButton btn_closeLED = new JButton("关闭LED");
		btn_closeLED.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendDataEndOfLF("11");
			}
		});
		btn_closeLED.setBounds(12, 45, 93, 23);
		panel_center.add(btn_closeLED);

		JButton btn_openLED = new JButton("开启LED");
		btn_openLED.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendDataEndOfLF("12");
			}
		});
		btn_openLED.setBounds(12, 20, 93, 23);
		panel_center.add(btn_openLED);

		JButton btn_reboot = new JButton("重启");
		btn_reboot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btn_reboot.setBounds(389, 20, 93, 23);
		panel_center.add(btn_reboot);

		JButton btn_scaling2 = new JButton("比例2%");
		btn_scaling2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendDataEndOfLF("30");
			}
		});
		btn_scaling2.setBounds(187, 64, 93, 23);
		panel_center.add(btn_scaling2);

		JButton btn_scaling20 = new JButton("比例20%");
		btn_scaling20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendDataEndOfLF("31");
			}
		});
		btn_scaling20.setBounds(290, 64, 93, 23);
		panel_center.add(btn_scaling20);

		JButton btn_scaling100 = new JButton("比例100%");
		btn_scaling100.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendDataEndOfLF("32");
			}
		});
		btn_scaling100.setBounds(389, 64, 93, 23);
		panel_center.add(btn_scaling100);

		JButton btn_redColor = new JButton("只取红色");
		btn_redColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendDataEndOfLF("2");
			}
		});
		btn_redColor.setForeground(Color.RED);
		btn_redColor.setBounds(86, 163, 93, 23);
		panel_center.add(btn_redColor);

		JButton btn_greenColor = new JButton("只取绿色");
		btn_greenColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendDataEndOfLF("3");
			}
		});
		btn_greenColor.setForeground(Color.GREEN);
		btn_greenColor.setBounds(86, 196, 93, 23);
		panel_center.add(btn_greenColor);

		JButton btn_blueColor = new JButton("只取蓝色");
		btn_blueColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendDataEndOfLF("4");
			}
		});
		btn_blueColor.setForeground(Color.BLUE);
		btn_blueColor.setBounds(86, 229, 93, 23);
		panel_center.add(btn_blueColor);

		panel_color = new JPanel();
		panel_color.setBorder(new TitledBorder(null, "\u989C\u8272",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_color.setBackground(Color.WHITE);
		panel_color.setBounds(189, 97, 173, 158);
		panel_center.add(panel_color);

		panel_colorRed = new JPanel();
		panel_colorRed.setBackground(Color.RED);
		panel_colorRed.setBorder(new TitledBorder(null, "\u7EA2",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_colorRed.setBounds(372, 97, 110, 46);
		panel_center.add(panel_colorRed);

		panel_colorGreen = new JPanel();
		panel_colorGreen.setBackground(Color.GREEN);
		panel_colorGreen.setBorder(new TitledBorder(null, "\u7EFF",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_colorGreen.setBounds(372, 153, 110, 46);
		panel_center.add(panel_colorGreen);

		panel_colorBlue = new JPanel();
		panel_colorBlue.setBackground(Color.BLUE);
		panel_colorBlue.setBorder(new TitledBorder(null, "\u84DD",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_colorBlue.setBounds(372, 209, 110, 46);
		panel_center.add(panel_colorBlue);
	}

	/**
	 * <pre>
	 * 刷新串口
	 * </pre>
	 */
	private void refreshSerial() {
		CommPortIdentifier portId;
		List<String> ports = new ArrayList<String>();
		Enumeration<?> en = CommPortIdentifier.getPortIdentifiers();
		while (en.hasMoreElements()) {
			portId = (CommPortIdentifier) en.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				System.out.println(portId.getName());
				ports.add(portId.getName());
			}
		}

		cmb_serialPort.setModel(new DefaultComboBoxModel<String>(ports
				.toArray(new String[0])));
	}

	/**
	 * 关闭串口
	 */
	private void closeSerial() {
		if (sRWHandler != null) {
			sRWHandler.close();
			sRWHandler = null;

			txta_console.append("\n串口已关闭\n");
		}
	}

	/**
	 * <pre>
	 *  打开串口
	 *  先全部用默认参数
	 * </pre>
	 */
	private void openSerial() {
		String portName = (String) this.cmb_serialPort.getSelectedItem();
		if (portName == null || "".equals(portName)) {
			JOptionPane.showMessageDialog(this, "请先选择串口");
			return;
		}
		txta_console.setText("");
		if (sRWHandler != null) {
			sRWHandler.close();
		}
		try {

			SerialConfig config = new SerialConfig(portName);
			// 波特率
			config.setBaudRate((int) cmb_baudRate.getSelectedItem());
			// 数据位
			config.setDataBits((int) cmb_dataBits.getSelectedItem());
			// 校验位
			config.setParity(cmb_parity.getSelectedIndex());
			// 停止位 1,2,3
			config.setStopBits(cmb_stopBits.getSelectedIndex() + 1);
			// 流控
			config.setFlowControl(cmb_flowControl.getSelectedIndex());

			this.sRWHandler = new SerialReadAndWriteHandler(config, this);
			txta_console.setText("打开串口：" + portName + "\n");
			// 应该发一个什么指令，让其重启

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "打开串口异常", "错误",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * 发送
	 */
	private void send() {
		String data = txt_send.getText();
		if (sendDataEndOfLF(data))
			// 清空
			txt_send.setText("");
	}

	/**
	 * <pre>
	 * 发送数据，以换行符结尾
	 *  "回车"（carriage return）
	 *  "换行"（line feed）
	 * </pre>
	 * 
	 * @param data
	 * @return
	 */
	private boolean sendDataEndOfLF(String data) {
		return sendData(data + '\n');
	}

	/**
	 * <pre>
	 * 发送数据到串口
	 * </pre>
	 * 
	 * @param data
	 *            数据
	 * @return 发送结果
	 */
	private boolean sendData(String data) {
		if (sRWHandler != null) {
			try {
				sRWHandler.writeData(data.getBytes());
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "数据发送失败", "错误",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "请先打开串口");
		}

		return false;
	}

	/**
	 * 处理接收到的每一行数据
	 * 
	 * @param line
	 */
	private void processLine(String line) {
		// 判断读到的数据是否为颜色
		// the color : 92 , 57 , 58
		if (line.startsWith("the color : ")) {
			line = line.substring(line.indexOf(":") + 1);
			String[] color = line.split(",", 3);
			int r = Integer.parseInt(color[0].trim());
			int g = Integer.parseInt(color[1].trim());
			int b = Integer.parseInt(color[2].trim());
			// 设置颜色
			panel_color.setBackground(new Color(r, g, b));
			// 分别设置红绿蓝
			panel_colorRed.setBackground(new Color(r, 0, 0));
			panel_colorGreen.setBackground(new Color(0, g, 0));
			panel_colorBlue.setBackground(new Color(0, 0, b));
		}
	}

	/**
	 * 将接收的数据缓冲起来<br>
	 */
	private StringBuffer dataBuffer = new StringBuffer();

	@Override
	public void handle(String data) {
		// 显示串口返回的数据
		txta_console.append(data);
		txta_console.setSelectionEnd(txta_console.getText().length());

		// 每次只返回部分数据
		// 把数据收集起来
		dataBuffer.append(data);

		for (int i = 0; i < dataBuffer.length(); i++) {
			if (dataBuffer.charAt(i) == '\n') {// 查找数据中的换行符号
				String subLine = dataBuffer.substring(0, i + 1);
				dataBuffer.delete(0, i + 1);
				processLine(subLine);
				i = 0;
			}
		}
	}
}
