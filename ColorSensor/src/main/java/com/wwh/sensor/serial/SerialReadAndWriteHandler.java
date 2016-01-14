package com.wwh.sensor.serial;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

public class SerialReadAndWriteHandler implements SerialPortEventListener {
	// private String serialPortName;

	private CommPortIdentifier commPortId;

	private SerialPort serialPort;

	private InputStream inputStream;

	private OutputStream outputStream;

	private SerialDataReceiveHandler receiveHandler;

	// 需要一个串口配置参数
	public SerialReadAndWriteHandler(String serialPortName,
			SerialDataReceiveHandler handler) throws PortInUseException,
			IOException, TooManyListenersException,
			UnsupportedCommOperationException {
		// this.serialPortName = serialPortName;

		this.receiveHandler = handler;

		Enumeration<?> portList = CommPortIdentifier.getPortIdentifiers();
		CommPortIdentifier portId;
		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				if (portId.getName().equals(serialPortName)) {
					commPortId = portId;
				}
			}
		}
		if (commPortId == null) {
			throw new IllegalArgumentException("错误的串口名称");
		}
		// 打开串口
		serialPort = (SerialPort) commPortId.open("SimpleReadApp ", 2000);
		// 获得输入输出流
		inputStream = serialPort.getInputStream();
		outputStream = serialPort.getOutputStream();
		// 为串口添加监听器
		serialPort.addEventListener(this);

		serialPort.notifyOnDataAvailable(true);

		// 配置串口---默认
		serialPort.setSerialPortParams(9600,// 波特率9600bps
				SerialPort.DATABITS_8,// 数据位8
				SerialPort.STOPBITS_1,// 停止位1
				SerialPort.PARITY_NONE);// 校验位无

	}

	/**
	 * <pre>
	 * 关闭
	 * </pre>
	 */
	public void close() {
		serialPort.close();
	}

	/**
	 * <pre>
	 * 写数据
	 * </pre>
	 * 
	 * @param bytes
	 * @throws IOException
	 */
	public void writeData(byte[] bytes) throws IOException {
		outputStream.write(bytes);
	}

	@Override
	public void serialEvent(SerialPortEvent event) {

		switch (event.getEventType()) {

		case SerialPortEvent.BI:

		case SerialPortEvent.OE:

		case SerialPortEvent.FE:

		case SerialPortEvent.PE:

		case SerialPortEvent.CD:

		case SerialPortEvent.CTS:

		case SerialPortEvent.DSR:

		case SerialPortEvent.RI:

		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:

			break;

		case SerialPortEvent.DATA_AVAILABLE:// 如果有数据到达

			byte[] readBuffer = new byte[200];

			try {
				String rd;
				while (inputStream.available() > 0) {
					int numBytes = inputStream.read(readBuffer);
					rd = new String(readBuffer, 0, numBytes);
					System.out.print(new String(rd));
					this.receiveHandler.handle(rd);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			break;

		}

	}
}
