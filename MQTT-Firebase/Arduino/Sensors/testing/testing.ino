#include <SoftwareSerial.h>

SoftwareSerial s(16,17);
int data;
void setup() {
s.begin(9600);
Serial.begin(9600);
}
 
void loop() {
  s.write("s");
  if (s.available()>0)
  {
    data=s.read();
    Serial.println(data);
  }
 
 
}
