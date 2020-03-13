#include <WiFi.h>
#include <PubSubClient.h>
 
const char* ssid = "SunwayNet WIFI";
//const char* password =  "yourNetworkPassword";
const char* mqttServer = "mqtt.eclipse.org";
const int mqttPort =  1883;
//const char* mqttUser = "ddvmzjsx";
//const char* mqttPassword = "yourMQTTpasswordYwH6IdE0fE-H";
WiFiClient espClient;
PubSubClient client(espClient);
String message = "10";
char pubMsg[50];

void callback(char* topic, byte* payload, unsigned int length) {

  String messageTemp;
  Serial.print("Message arrived in topic: ");
  Serial.println(topic);
 
  Serial.print("Message:");
  for (int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
    messageTemp += (char)payload[i];

  }
 
  Serial.println();
  Serial.println("-----------------------");
  if (String(topic) == "User 1") {
    Serial.print("Changing output to ");
    Serial.println(messageTemp);
    //Serial.println(" 20");
    //message = 20;
    
    if(messageTemp == "100"){
      Serial.println("100");
      message = 100;
      //digitalWrite(ledPin, HIGH);
    }
    /*
    else if(messageTemp == "off"){
      Serial.println("off");
      digitalWrite(ledPin, LOW);
    }*/
  }
}
 
void setup() {
 
  Serial.begin(115200);
 
  WiFi.begin(ssid);
 
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.println("Connecting to WiFi..");
  }
  Serial.println("Connected to the WiFi network");
 
  client.setServer(mqttServer, mqttPort);
  client.setCallback(callback);
 
  while (!client.connected()) {
    Serial.println("Connecting to MQTT...");
 
    if (client.connect("ESP32Client8907890")) {
 
      Serial.println("connected");  
 
    } else {
 
      Serial.print("failed with state ");
      Serial.print(client.state());
      delay(2000);
 
    }
  }
 
  client.subscribe("User 1");
 
}

void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Attempt to connect
    if (client.connect("ESP32Client8907890")) {
      Serial.println("connected");
      // Subscribe
      client.subscribe("User 1");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}
 
void loop() {
  
  message.toCharArray(pubMsg, message.length()+1);
  client.publish("Sensor 1", pubMsg);
  Serial.print("message publish to sensor 2 with payload ");
  Serial.println(message);
  delay(1000);
  client.loop();
}
