package com.wwh.sensor.color.input;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author wwh
 * @date 2016年1月15日 下午1:13:50
 *
 */
public class ProcessInputStream {
    public static void main(String[] args) {
        ProcessInputStream pis = new ProcessInputStream();
        pis.handle("123");
        pis.handle("456");
        pis.handle("789");
        pis.handle("0\naaa");
        pis.handle("123");
        pis.handle("456\n");
    }

    // 缓存区
    // 查找内容中的换行符号，逐个处理
    private StringBuffer dataBuffer = new StringBuffer();

    public void handle(String data) {

        System.out.println("receive data = " + data);
        // 把数据收集起来
        dataBuffer.append(data);

        for (int i = 0; i < dataBuffer.length(); i++) {
            if (dataBuffer.charAt(i) == '\n') {
                String subLine = dataBuffer.substring(0, i + 1);
                dataBuffer.delete(0, i + 1);
                processLine(subLine);
                i = 0;
            }
        }

    }

    private void processLine(String line) {
        System.out.println("the line = " + line);
    }
}
