#define led 7 //led
#define S0    6   //物体表面的反射光越强，TCS3002D内置振荡器产生的方波频率越高，
#define S1    5  //S0和S1的组合决定输出信号频率比例因子，比例因子为2%
//比率因子为TCS3200传感器OUT引脚输出信号频率与其内置振荡器频率之比
#define S2     4   //S2和S3的组合决定让红、绿、蓝，哪种光线通过滤波器
#define S3     3
#define OUT    2

int   g_count = 0;    // count the frequecy
int   g_array[3];     // store the RGB value
float g_SF[3];        // save the RGB Scale factor


//初始化
void TSC_Init()
{
  pinMode(led, OUTPUT);
  pinMode(S0, OUTPUT);
  pinMode(S1, OUTPUT);
  pinMode(S2, OUTPUT);
  pinMode(S3, OUTPUT);
  pinMode(OUT, INPUT);

  //比例
  //S0 L S1 H 2%
  //S0 H S1 L 20%
  //S0 H S1 H 100%
  changScaling(0);
}

//选择颜色过滤
//滤波类型
//S2 L S3 L 红色
//S2 L S3 H 蓝色
//S2 H S3 L 无
//S2 H S3 H 绿色
void TSC_FilterColor(int Level01, int Level02)
{
  if (Level01 != 0)
    Level01 = HIGH;

  if (Level02 != 0)
    Level02 = HIGH;

  digitalWrite(S2, Level01);
  digitalWrite(S3, Level02);
}

//频率加一
void TSC_Count() {
  g_count ++ ;
}

//读取频率
int readFrequecy(int type)
{
  switch (type)
  {
    case 0:
      Serial.print("->Frequency RED = ");
      g_count = 0;
      TSC_FilterColor(LOW, LOW);//Filter without Red
      delay(1000);
      //保存起来
      g_array[0] = g_count;
      Serial.println(g_array[0]);
      return g_array[0];
    case 1:
      Serial.print("->Frequency GREEN = ");
      g_count = 0;
      TSC_FilterColor(HIGH, HIGH);//Filter without Green
      delay(1000);
      //保存起来
      g_array[1] = g_count;
      Serial.println(g_array[1]);
      return g_array[1];
    case 2:
      Serial.print("->Frequency BLUE = ");
      g_count = 0;
      TSC_FilterColor(LOW, HIGH);
      delay(1000);
      //保存起来
      g_array[2] = g_count;
      Serial.println(g_array[2]);
      return g_array[2];
    default:
      Serial.println("----- No Filter -----");
      g_count = 0;
      TSC_FilterColor(HIGH, LOW);             //Clear(no filter)
      return 0;
  }
}

//读取红绿蓝三个频率
void readAllFrequecy() {
  readFrequecy(0);
  readFrequecy(1);
  readFrequecy(2);
  readFrequecy(3);
}

//改变比例
void changScaling(int type) {
  if (type == 1) {
    Serial.println("Change Scaling 20%");
    //设置为20%
    digitalWrite(S0, HIGH);
    digitalWrite(S1, LOW);
  } else if (type == 2 ) {
    Serial.println("Change Scaling 100%");
    //设置为100
    digitalWrite(S0, HIGH);
    digitalWrite(S1, HIGH);
  } else {
    Serial.println("Change Scaling 2%");
    //设置为2%
    digitalWrite(S0, LOW);
    digitalWrite(S1, HIGH);
  }
}


//白平衡校验
void whiteBlance() {
  readAllFrequecy();

  g_SF[0] = 255.0 / g_array[0];    //R Scale factor
  g_SF[1] = 255.0 / g_array[1] ;   //G Scale factor
  g_SF[2] = 255.0 / g_array[2] ;   //B Scale factor

  Serial.print("RED Scale factor :");
  Serial.println(g_SF[0]);
  Serial.print("GREEN Scale factor :");
  Serial.println(g_SF[1]);
  Serial.print("BLUE Scale factor :");
  Serial.println(g_SF[2]);
}

//读取颜色
void readColor() {
  readAllFrequecy();
  Serial.print("the color : ");
  for (int i = 0; i < 3; i++) {
    int v = int(g_array[i] * g_SF[i]);
    Serial.print(v > 255 ? 255 : v);
    if (i < 2)
      Serial.print(" , ");
  }
  Serial.println("");
}

/**
   获取红色
*/
void getRed() {
  int v = readFrequecy(0);
  readFrequecy(3);
  int red = int(v * g_SF[0]);
  Serial.print("the color : ");
  Serial.print(red > 255 ? 255 : red);
  Serial.println(" , 0 , 0");
}

/**
   获取绿色
*/
void getGreen() {
  int v = readFrequecy(1);
  readFrequecy(3);
  int green = int(v * g_SF[1]);
  Serial.print("the color : 0 , ");
  Serial.print(green > 255 ? 255 : green);
  Serial.println(" , 0");
}

/**
   获取蓝色
*/
void getBlue() {
  int v = readFrequecy(2);
  readFrequecy(3);
  int blue = int(v * g_SF[2]);
  Serial.print("the color : 0 , 0 , ");
  Serial.print(blue > 255 ? 255 : blue);
  Serial.println("");
}

void( *resetFunc) (void) = 0;

void setup()
{
  TSC_Init();
  Serial.begin(9600);

  attachInterrupt(0, TSC_Count, RISING);

  for (int i = 0; i < 3; i++) {
    g_SF[i] = 1;
  }
}

void loop()
{
  //通过串口控制
  while (Serial.available() > 0) {
    int v = Serial.parseInt();
    Serial.read();
    Serial.print("read value = ");
    Serial.println(v);

    //根据读到的值进行操作
    switch (v)  {
      case 0:
        //进行白平衡
        whiteBlance();
        break;
      case 1:
        //读取颜色
        readColor();
        break;
      case 2:
        //只取红色
        getRed();
        break;
      case 3:
        //只取绿色
        getGreen();
        break;
      case 4:
        //只取蓝色
        getBlue();
        break;
      case 11://关灯
        Serial.println("led low");
        digitalWrite(led, LOW);
        break;
      case 12: //开灯
        Serial.println("led high");
        digitalWrite(led, HIGH);
        break;
      case 30:// 2% 的比例
        changScaling(0);
        break;
      case 31: // 20% 的比例
        changScaling(1);
        break;
      case 32: // 100% 的比例
        changScaling(2);
        break;
      default:
        Serial.println("unknown command");
    }
  }

}

