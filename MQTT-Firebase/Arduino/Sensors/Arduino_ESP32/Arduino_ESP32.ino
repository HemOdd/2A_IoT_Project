int id=99;
#include <SoftwareSerial.h>

SoftwareSerial sw(0, 3); // RX, TX

void setup() {
 Serial.begin(115200);
 Serial.println("Interfacfing arduino with nodemcu");
 sw.begin(115200);
}

void loop() {
 Serial.println("Sending data to nodemcu");
 int adc=analogRead(A0);
 String location = "hall way lv2";
 
 sw.print("{\"sensorid\":");
 sw.print(id);//sensor id
 sw.print(",");
 sw.print("\"distance\":");
 sw.print(adc);//offset
 sw.print(",");
  sw.print("\"temperature\":");
 sw.print(adc);//offset
 sw.print(",");
  sw.print("\"humidity\":");
 sw.print(adc);//offset
 sw.print(",");
 sw.print("\"location\":");
 sw.print(location);
 sw.print("}");
 sw.println();

 Serial.print("{\"sensorid\":");
 Serial.print(id);//sensor id
 Serial.print(",");
 Serial.print("\"distance\":");
 Serial.print(adc);//
 Serial.print(",");
 Serial.print("\"temperature\":");
 Serial.print(adc);//
 Serial.print(",");
 Serial.print("\"humidity\":");
 Serial.print(adc);//
 Serial.print(",");
 Serial.print("\"location\":");
 Serial.print(location);
 Serial.print("}");
 Serial.println();
 delay(5000);
}
