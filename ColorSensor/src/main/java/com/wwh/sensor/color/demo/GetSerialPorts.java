package com.wwh.sensor.color.demo;


import gnu.io.CommPortIdentifier;

import java.util.Enumeration;

public class GetSerialPorts {

    public void listPortChoices() {
        CommPortIdentifier portId;
        Enumeration en = CommPortIdentifier.getPortIdentifiers();
        // iterate through the ports.
        while (en.hasMoreElements()) {
            portId = (CommPortIdentifier) en.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                System.out.println(portId.getName());
            }
        }

    }

    public static void main(String[] args) {

        GetSerialPorts GSP = new GetSerialPorts();
        GSP.listPortChoices();

    }

}
