# ColorSensor

####软件截图：  
![image](https://github.com/wangwen135/ColorSensor/blob/master/ColorSensor/src/main/resources/image/%E8%BD%AF%E4%BB%B6%E6%88%AA%E5%9B%BE.jpg)

---

    通过图形界面控制传感器，在界面上直接显示颜色，在控制台显示串口返回的各项数值
    Java Swing 做界面
    通过 javacomm(32位) RXTXcomm(64位) 连接串口
    PS：只实现了64位系统
    
    颜色传感器（TCS3200） --> Arduino --> 电脑

**Arduino 代码:**  
https://github.com/wangwen135/ColorSensor/blob/master/ColorSensor/src/main/resources/arduino/colorSensorMasterDebug.ino
  
**颜色传感器连接图：**



---

#### java连串口说明：  
32位系统使用 comm.jar  
64位系统使用 RXTXcomm.jar  

#####32位：
复制 win32com.dll 到 JAVA_HOME/bin  
复制 javax.comm.properties 到 JAVA_HOME/lib  
复制 comm.jar 到 JAVA_HOME/lib/ext  

#####64位：
复制 rxtxSerial.dll 到 JAVA_HOME/bin  
复制 RXTXcomm.jar 到 JAVA_HOME/lib/ext  

######打包运行则复制到jre的对应目录
---
**Arduino 默认串口配置**  
>数据位 8  
>校验位 无  
>停止位 1  



