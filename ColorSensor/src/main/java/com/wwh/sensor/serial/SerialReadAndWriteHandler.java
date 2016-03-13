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

/**
 * <pre>
 * 串口读写处理器
 * </pre>
 *
 * @author wwh
 *
 */
public class SerialReadAndWriteHandler implements SerialPortEventListener {

    private SerialConfig config;

    private CommPortIdentifier commPortId;

    private SerialPort serialPort;

    private InputStream inputStream;

    private OutputStream outputStream;

    private SerialDataReceiveHandler receiveHandler;

    /**
     * <pre>
     * 构造方法
     * </pre>
     *
     * @param config
     *            需要一个串口配置参数
     * @param handler
     *            接收数据处理器
     * @throws PortInUseException
     * @throws IOException
     * @throws TooManyListenersException
     * @throws UnsupportedCommOperationException
     */
    public SerialReadAndWriteHandler(SerialConfig config, SerialDataReceiveHandler handler) throws PortInUseException, IOException, TooManyListenersException,
            UnsupportedCommOperationException {
        this.config = config;

        this.receiveHandler = handler;

        Enumeration<?> portList = CommPortIdentifier.getPortIdentifiers();
        CommPortIdentifier portId;
        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if (portId.getName().equals(config.getName())) {
                    commPortId = portId;
                }
            }
        }
        if (commPortId == null) {
            throw new IllegalArgumentException("错误的串口名称");
        }
        // 打开串口
        serialPort = (SerialPort) commPortId.open("colorSensor ", 2000);
        // 获得输入输出流
        inputStream = serialPort.getInputStream();
        outputStream = serialPort.getOutputStream();
        // 为串口添加监听器
        serialPort.addEventListener(this);

        serialPort.notifyOnDataAvailable(true);

        // 配置串口---默认
        serialPort.setSerialPortParams(config.getBaudRate(),// 波特率9600bps
                config.getDataBits(),// 数据位8
                config.getStopBits(),// 停止位1
                config.getParity());// 校验位无

    }

    /**
     * <pre>
     * 关闭
     * </pre>
     */
    public void close() {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                    this.receiveHandler.handle(rd);
                }

            } catch (IOException e) {
                e.printStackTrace();
                this.receiveHandler.exception(e);
            }

            break;

        }

    }

    /**
     * 获取 config
     *
     * @return the config
     */
    public SerialConfig getConfig() {
        return config;
    }

    /**
     * 获取 commPortId
     *
     * @return the commPortId
     */
    public CommPortIdentifier getCommPortId() {
        return commPortId;
    }

    /**
     * 获取 serialPort
     *
     * @return the serialPort
     */
    public SerialPort getSerialPort() {
        return serialPort;
    }

    /**
     * 获取 inputStream
     *
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * 获取 outputStream
     *
     * @return the outputStream
     */
    public OutputStream getOutputStream() {
        return outputStream;
    }

    /**
     * 获取 receiveHandler
     *
     * @return the receiveHandler
     */
    public SerialDataReceiveHandler getReceiveHandler() {
        return receiveHandler;
    }

}
