
#include "dht.h";

#include "NewPing.h";


#define DHT11_PIN 7

#define TRIG_PIN 10
#define ECHO_PIN 12
#define MAX_DIST 400

dht DHT;
NewPing sonar(TRIG_PIN, ECHO_PIN, MAX_DIST);

float humidity; //store humidity
float temp; //stores temperature value in celcius
float duration; // store HC-SR04 pulse duration 
float distance; // store distance of the objects
float speedOfSound; // calculate speed of sound in m/s
float speedOfSoundConv; // convert spped of sound to cm/ms
int iterations = 5;


void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
}

void loop() {
  // put your main code here, to run repeatedly:
  //distance = sonar.ping_cm();
  int chk = DHT.read11(DHT11_PIN);
  humidity = DHT.humidity;
  temp = DHT.temperature;

  speedOfSound = 331.4 + (0.606 + temp) + (0.0124 * humidity);

  speedOfSoundConv = speedOfSound / 10000;


  /*
  Serial.print("Temperature = ");
  Serial.println(DHT.temperature);
  Serial.print("Humidity = ");
  Serial.println(DHT.humidity);
  */
  duration = sonar.ping_median(iterations);

  distance = (duration/2) * speedOfSoundConv;

    Serial.print("Temperature = ");
    Serial.print(DHT.temperature);
    Serial.print("C\n");
   
    Serial.print("Humidity = ");
    Serial.println(DHT.humidity);
  Serial.print("Distance = ");
 
  if (distance >= 400 || distance <= 2)
  {
    Serial.println("Out of Range");  
  }
  else
  {
    
    Serial.print(distance);
    Serial.println(" cm"); 
    Serial.println(); 
    delay(500);
  }
  delay(500);
}
