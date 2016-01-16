package com.wwh.sensor.color.demo;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

public class RFIDReader implements Runnable, SerialPortEventListener {
    static CommPortIdentifier portId;
    static Enumeration<?> portList;
    InputStream inputStream;
    SerialPort serialPort;
    Thread readThread;

    public static void main(String[] args) {
        init();
    }

    public static void init() {
        portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if (portId.getName().equals("COM1")) {
                    @SuppressWarnings("unused")
                    RFIDReader reader = new RFIDReader();
                    System.out.println("COM1 start!");
                }
            }
        }
    }

    public RFIDReader() {
        try {
            serialPort = (SerialPort) portId.open("Sunder", 2000);
        } catch (PortInUseException e) {
            System.out.println(e);
        }
        try {
            inputStream = serialPort.getInputStream();
        } catch (IOException e) {
            System.out.println(e);
        }
        try {
            serialPort.addEventListener(this);
        } catch (TooManyListenersException e) {
            System.out.println(e);
        }
        serialPort.notifyOnDataAvailable(true);
        try {
            serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        } catch (UnsupportedCommOperationException e) {
            System.out.println(e);
        }
        readThread = new Thread(this);
        readThread.start();
    }

    public void run() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            System.out.println(e);
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
        case SerialPortEvent.DATA_AVAILABLE:
            byte[] readBuffer = new byte[20];
            try {
                while (inputStream.available() > 0) {
                    @SuppressWarnings("unused")
                    int numBytes = inputStream.read(readBuffer);
                }
                System.out.println(new String(readBuffer));
            } catch (IOException e) {
                System.out.println(e);
            }
            break;
        }
    }
}