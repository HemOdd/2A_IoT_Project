#include <SoftwareSerial.h>

SoftwareSerial s(16,17);
int datas;
void setup() {
s.begin(9600);
Serial.begin(9600);
}
 
void loop() {
  Serial.print("here");
  if (s.available()>0)
  {
    Serial.print("here");
    datas=s.read();
    Serial.println(datas);
  }
 
 delay(5000);
}
