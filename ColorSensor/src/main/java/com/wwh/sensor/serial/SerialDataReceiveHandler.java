package com.wwh.sensor.serial;

/**
 * <pre>
 * 串口数据接收处理器
 * </pre>
 *
 * @author wwh
 *
 */
public interface SerialDataReceiveHandler {

    void handle(String data);
}
