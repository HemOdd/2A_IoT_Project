#include "NewPing.h"

#define TRIG_PIN 10
#define ECHO_PIN 12
#define MAX_DIST 400

NewPing sonar(TRIG_PIN, ECHO_PIN, MAX_DIST);

float distance, duration;

int iterations = 5;
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  duration = sonar.ping();
  Serial.print(duration);
  //duration = sonar.ping_median(iterations);

  distance = (duration / 2) * 0.0343;
  
  Serial.print("Distance = ");

  if (distance >= 400 || distance <= 2)
  {
    Serial.println("Out of Range");  
  }
  else
  {
    Serial.print(distance);
    Serial.println(" cm");  
    delay(500);
  }
  delay(500);
}
