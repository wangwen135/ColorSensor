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

    /**
     * 处理接受到的数据
     * 
     * @param data
     */
    void handle(String data);

    /**
     * 处理异常
     * 
     * @param e
     */
    void exception(Exception e);
}
