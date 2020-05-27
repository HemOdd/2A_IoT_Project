int datas;
void setup() {
Serial.begin(115200);
}
 
void loop() {
   client.loop();
   if (Serial.available() > 0) {
     char bfr[501];
     memset(bfr,0, 501);
     Serial.readBytesUntil( '\n',bfr,500);
     publishSerialData(bfr);
   }
 }
