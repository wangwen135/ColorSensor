package com.wwh.sensor.serial;

/**
 * <pre>
 * 串口配置参数
 * </pre>
 *
 * @author wwh
 *
 */
public class SerialConfig {

    public static final int DATABITS_5 = 5;

    public static final int DATABITS_6 = 6;

    public static final int DATABITS_7 = 7;

    public static final int DATABITS_8 = 8;

    public static final int PARITY_NONE = 0;

    public static final int PARITY_ODD = 1;

    public static final int PARITY_EVEN = 2;

    public static final int PARITY_MARK = 3;

    public static final int PARITY_SPACE = 4;

    public static final int STOPBITS_1 = 1;

    public static final int STOPBITS_2 = 2;

    public static final int STOPBITS_1_5 = 3;

    public static final int FLOWCONTROL_NONE = 0;

    public static final int FLOWCONTROL_RTSCTS_IN = 1;

    public static final int FLOWCONTROL_RTSCTS_OUT = 2;

    public static final int FLOWCONTROL_XONXOFF_IN = 4;

    public static final int FLOWCONTROL_XONXOFF_OUT = 8;

    /**
     * 串口名称
     */
    private String name;

    /**
     * 波特率
     */
    private int baudRate = 9600;

    /**
     * 数据位
     */
    private int dataBits = DATABITS_8;

    /**
     * 校验位
     */
    private int parity = PARITY_NONE;

    /**
     * 停止位
     */
    private int stopBits = STOPBITS_1;

    /**
     * 流控
     */
    private int flowControl = FLOWCONTROL_NONE;

    /**
     * <pre>
     * 构造方法
     * </pre>
     *
     */
    public SerialConfig() {
    }

    /**
     * <pre>
     * 构造方法
     * </pre>
     *
     * @param serialPortName
     */
    public SerialConfig(String serialPortName) {
        this.name = serialPortName;
    }

    /**
     * 获取 name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置 name
     *
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取 baudRate
     *
     * @return the baudRate
     */
    public int getBaudRate() {
        return baudRate;
    }

    /**
     * 设置 baudRate
     *
     * @param baudRate
     *            the baudRate to set
     */
    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }

    /**
     * 获取 dataBits
     *
     * @return the dataBits
     */
    public int getDataBits() {
        return dataBits;
    }

    /**
     * 设置 dataBits
     *
     * @param dataBits
     *            the dataBits to set
     */
    public void setDataBits(int dataBits) {
        this.dataBits = dataBits;
    }

    /**
     * 获取 parity
     *
     * @return the parity
     */
    public int getParity() {
        return parity;
    }

    /**
     * 设置 parity
     *
     * @param parity
     *            the parity to set
     */
    public void setParity(int parity) {
        this.parity = parity;
    }

    /**
     * 获取 stopBits
     *
     * @return the stopBits
     */
    public int getStopBits() {
        return stopBits;
    }

    /**
     * 设置 stopBits
     *
     * @param stopBits
     *            the stopBits to set
     */
    public void setStopBits(int stopBits) {
        this.stopBits = stopBits;
    }

    /**
     * 获取 flowControl
     *
     * @return the flowControl
     */
    public int getFlowControl() {
        return flowControl;
    }

    /**
     * 设置 flowControl
     *
     * @param flowControl
     *            the flowControl to set
     */
    public void setFlowControl(int flowControl) {
        this.flowControl = flowControl;
    }

}
