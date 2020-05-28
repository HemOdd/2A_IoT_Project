import paho.mqtt.client as mqtt
import time
from asyncInputRecorder import *

def my_callback(userInput):
    #evaluate the keyboard input
    if userInput == "Q":
        client.loop_stop()
        client.disconnect()
        return -1
    else:

        client.publish("eclipse.org/FIT3162/Team2A/sensor1", userInput)

def on_message(client,userdata,msg):
    topic = msg.topic

    m_decode = str(msg.payload.decode("utf-8"))
    print("From: ", topic)
    print ("Message Received: ",m_decode)
    print()

def on_connect(client,userdata,flags,rc):

    if rc == 0:
        print ("Status: Sensor 1 Connected")
        # start the Keyboard thread
        kthread = KeyboardThread(my_callback)
        client.subscribe("User 1",0)
       # client.subscribe("Sensor 2",0)
    else:
        print ("Status: Sensor 1 - Bad Conncetion With Error Code: ",rc)

if __name__ == "__main__":

    client = mqtt.Client()

    client.on_connect = on_connect

    client.on_message = on_message
    client.connect("mqtt.eclipse.org",1883, 60) #connect to server,port, timeout

    client.loop_forever()
