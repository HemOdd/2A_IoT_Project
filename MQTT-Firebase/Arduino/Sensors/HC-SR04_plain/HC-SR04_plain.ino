

#define transmitPin 10
#define recieverPin 12

float duration, distance;


void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(transmitPin, OUTPUT);
  pinMode(recieverPin, INPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
  //creates 10 microseconds pulse 
  //the first tranmitPin is just to make sure we start off has no effect
  digitalWrite(transmitPin, LOW);
  delayMicroseconds(2);
  digitalWrite(transmitPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(transmitPin,LOW);

  //measure respnose
  duration = pulseIn(recieverPin, HIGH);
  Serial.print("Duration: ");
  Serial.println(duration);
  

  //determine distance from duration
  //Use 343 m/s as speed of sounds

  distance = (duration/2) * 0.0343;

  //output result to serial monitor

  Serial.print("Distance = ");

  if (distance >= 400 || distance <= 2) 
  {
    //MAX RANGE OF THIS SENSORS IS 2CM TO 400 CM
     Serial.println("OUT OF RANGE");
   }
   else
   {
     Serial.print(distance);
     Serial.println(" cm");
     delay(500);
    }
    delay(500);
  
}
