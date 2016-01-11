package com.wwh.sensor.color.demo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;
import javax.sql.rowset.serial.SerialException;

public class Read {

    public static void main(String[] args) {

        CommPortIdentifier portId = null;

        try {

            portId = CommPortIdentifier.getPortIdentifier("COM4");

        } catch (NoSuchPortException e1) {

            System.out.println("串口不存在！");

            e1.printStackTrace();

        }

        try {

            SerialPort sPort = (SerialPort) portId.open("串口读取程序", 60);

            try {

                sPort.setSerialPortParams(9600, SerialPort.DATABITS_7, SerialPort.STOPBITS_1, SerialPort.PARITY_EVEN);

            }

            // 波特率9600bps,7位数据位,1停止位 ,偶校验

            catch (UnsupportedCommOperationException e) {
            }

            // 用于对串口写数据

            // 用于从串口读数据

            InputStream is = null;

            try {

                is = new BufferedInputStream(sPort.getInputStream());

            } catch (IOException e) {

                System.out.println("读取串口失败！");

                e.printStackTrace();

            }

            int PacketLength = 40;// PacketLength为缓冲数组长度

            int newData;

            while (true) {

                char[] msgPack = new char[PacketLength];

                for (int i = 0; i < PacketLength; i++) {

                    try {

                        if ((newData = is.read()) != -1) {

                            if (newData == 10)

                                break;

                            msgPack[i] = (char) newData;

                            System.out.println(msgPack[i]);

                        }

                    } catch (IOException e) {

                        // TODO Auto-generated catch block

                        e.printStackTrace();

                    }

                }

                String string = String.valueOf(msgPack).trim();

                String[] dataArray = string.split(",");

                for (int i = 0; i < dataArray.length; i++)

                {

                    System.out.print(i + ":" + dataArray[i] + "n");

                }

            }

        } catch (PortInUseException e) {

            try {

                throw new SerialException(e.getMessage());

            } catch (SerialException e1) {

                System.out.println("串口被占用！");

                e1.printStackTrace();

            }

        }

    }

}