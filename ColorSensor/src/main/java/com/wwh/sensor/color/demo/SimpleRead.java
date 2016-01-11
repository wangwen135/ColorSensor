package com.wwh.sensor.color.demo;

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

public class SimpleRead implements Runnable, SerialPortEventListener {

    static CommPortIdentifier portId;

    static Enumeration portList;

    InputStream inputStream;

    OutputStream outputStream;

    SerialPort serialPort;

    Thread readThread;

    public static void main(String[] args) {

        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {

            portId = (CommPortIdentifier) portList.nextElement();

            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {

                if (portId.getName().equals("COM1")) {

                    SimpleRead reader = new SimpleRead();

                }

            }

        }

    }

    public SimpleRead() {

        try {// 打开串口COM1

            serialPort = (SerialPort) portId.open("SimpleReadApp ", 2000);

        } catch (PortInUseException e) {
        }

        try {// 获得输入输出流

            inputStream = serialPort.getInputStream();

            outputStream = serialPort.getOutputStream();

        } catch (IOException e) {
        }

        try {// 为串口添加监听器

            serialPort.addEventListener(this);

        } catch (TooManyListenersException e) {
        }

        serialPort.notifyOnDataAvailable(true);

        try {// 配置串口

            serialPort.setSerialPortParams(9600,// 波特率9600bps

                    SerialPort.DATABITS_7,// 7位数据位

                    SerialPort.STOPBITS_1,// 1位停止位

                    SerialPort.PARITY_EVEN);// 偶校验

        } catch (UnsupportedCommOperationException e) {
        }

        readThread = new Thread(this);// 新建一线程

        readThread.start();// 启动线程

    }

    public void run() {

        try {

            Thread.sleep(1);

        } catch (InterruptedException e) {
        }

    }

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

                while (inputStream.available() > 0) {

                    // 读取一个字节到readBuffer

                    int numBytes = inputStream.read(readBuffer);

                }

                System.out.print(new String(readBuffer));

            } catch (IOException e) {
            }

            break;

        }

    }

}
