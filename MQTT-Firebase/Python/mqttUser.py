import paho.mqtt.client as mqtt
import time
import FirebaseInit
from asyncInputRecorder import *

def my_callback(userInput):
    #evaluate the keyboard input
    if userInput == "Q":
        client.loop_stop()
        client.disconnect()
        return -1
    else:
        client.publish("User 1", userInput)
        #log_File(userInput)
#start the Keyboard thread
kthread = KeyboardThread(my_callback)

def on_message(client,userdata,msg):
    topic = msg.topic
    m_decode = str(msg.payload.decode("utf-8"))
    print("From: ", topic)
    print ("Message Received : ",m_decode)
    print()
    if m_decode == "100":
        log_File(m_decode,topic)
def on_connect(client,userdata,flags,rc):
    if rc == 0:
        print ("Status: User 1 Connected")
        client.subscribe("Sensor 1", 0)
        client.subscribe("Sensor 2", 0)
        FirebaseInit.initialize()
    else:
        print ("Status: User 1 - Bad Conncetion With Error Code: ",rc)

def log_File(value,topic):
    print("logging file....")
    db = FirebaseInit.firestore.client()
    doc_ref = db.collection('Sensors').document(topic)
    doc_ref.set({
        'temperature': value
    })

client = mqtt.Client("user1-8936")
client.connect("mqtt.eclipse.org",1883, 60)
client.on_connect = on_connect

client.on_message = on_message

client.loop_forever()
