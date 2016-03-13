# ColorSensor


    通过图形界面控制传感器，在界面上直接显示颜色，在控制台显示串口返回的各项数值
    Java Swing 做界面
    通过 RXTXcomm 连接串口
    
    颜色传感器（TCS3200） --> Arduino --> 电脑


[下载程序](https://github.com/wangwen135/ColorSensor/raw/master/ColorSensor/release/ColorSensor-0.1.zip)
---

####软件截图  
![image](https://github.com/wangwen135/ColorSensor/blob/master/ColorSensor/image/%E8%BD%AF%E4%BB%B6%E6%88%AA%E5%9B%BE.jpg)

---

####Arduino代码  
[colorSensorMasterDebug.ino](https://github.com/wangwen135/ColorSensor/blob/master/ColorSensor/arduino/colorSensorMasterDebug.ino)
  
**颜色传感器连接**  
>传感器&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; pin  
>LED&nbsp;&nbsp;&nbsp;&nbsp;==>&nbsp;&nbsp;&nbsp; 7  
>S0 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;==>&nbsp;&nbsp;&nbsp;&nbsp; 6  
>S1 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;==>&nbsp;&nbsp;&nbsp;&nbsp; 5  
>S2 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;==>&nbsp;&nbsp;&nbsp;&nbsp; 4  
>S3 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;==>&nbsp;&nbsp;&nbsp;&nbsp; 3  
>OUT&nbsp;&nbsp;&nbsp;&nbsp;==>&nbsp;&nbsp;&nbsp; 2  

**Arduino 默认串口配置**  
>数据位 8  
>校验位 无  
>停止位 1  

---

####java连串口说明：  
选择对应系统的dll或so文件，文件在[rxtx-2.2pre2-bins.zip](https://github.com/wangwen135/ColorSensor/raw/master/ColorSensor/rxtx/rxtx-2.2pre2-bins.zip)中

复制 rxtxSerial.dll(或librxtxSerial.so) 到 JAVA_HOME/bin  
复制 RXTXcomm.jar 到 JAVA_HOME/lib/ext  

######打包运行则复制到jre的对应目录，或指定：-Djava.library.path
---


