long randNumber;
String comdata = "";

void setup() {
  Serial.begin(9600);

  randomSeed(analogRead(0));
}

void loop() {
  while (Serial.available() > 0)
  {
    comdata += char(Serial.read());
    delay(2);
  }
  if (comdata.length() > 0)
  {
    Serial.println(comdata);
    comdata = "";
  }

  randNumber = random(255);
  Serial.println(randNumber);

  delay(1000);
}