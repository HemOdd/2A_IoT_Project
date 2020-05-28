int id=99;
#include <SoftwareSerial.h>

SoftwareSerial sw(2,3); // RX, TX
int datas = 1;
void setup() {
 Serial.begin(9600);
 Serial.println("Interfacfing arduino with nodemcu");
 sw.begin(9600);
}

void loop() {
 
 sw.print(id);
 delay(5000);
 Serial.println("Sending data to nodemcu");
 Serial.print("99");
 
}
