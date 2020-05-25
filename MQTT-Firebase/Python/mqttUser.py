import paho.mqtt.client as mqtt
import time
import FirebaseInit
from asyncInputRecorder import *
from datetime import datetime

def my_callback(userInput):
    """
    This function is to send message to a particular topic
    :param userInput: is the message to be send to broker
    :return: None
    """
    #evaluate the keyboard input
    if userInput == "Q":
        client.loop_stop()
        client.disconnect()
        return -1
    else:
        client.publish("User 1", userInput)
        #log_File(userInput)


def on_message(client,userdata,msg):
    """
    This function listen to any incoming message
    :param client: the client instance for this callback
    :param userdata: <optional> the private user data as set in Client() or user_data_set()
    :param msg: is to be recv
    :return:
    """
    topic = msg.topic
    m_decode = str(msg.payload.decode("utf-8"))
    print("From: ", topic)
    print ("Message Received : ",m_decode)
    print()
    intValue = float(m_decode)
    #sensors to Firebase
    if intValue >= 100.0:
        log_File(m_decode,topic)

def on_connect(client,userdata,flags,rc):
    """

    :param client: the client instance for this callback
    :param userdata: the private user data as set in Client() or user_data_set()
    :param flags: response flags sent by the broker
    :param rc: the connection result
    :return:
    """
    if rc == 0:
        print ("Status: User 1 Connected")
        # start the Keyboard thread
        kthread = KeyboardThread(my_callback)
        client.subscribe("Sensor 1", 0)
        client.subscribe("Sensor 2", 0)
        FirebaseInit.initialize()
    else:
        print ("Status: User 1 - Bad Conncetion With Error Code: ",rc)

def log_File(value,topic):
    """
    This function is to log the file to firebase firestore
    :param value: value to be log
    :param topic: topic name
    :return: None
    """
    print("logging file....")
    db = FirebaseInit.firestore.client()
    dTime = datetime.now()
    dateToStr=dTime.strftime("%d-%m-%Y")
    timeToStr = dTime.strftime("%H:%M:%S")


    doc_ref = db.collection(topic).document()
    doc_ref.set({
            'temperature': value,
            'datestamp': dateToStr,
            'timestamp': timeToStr
        })

if __name__ == "__main__":
    client = mqtt.Client("user1-8936")
    client.connect("mqtt.eclipse.org",1883, 60)
    client.on_connect = on_connect

    client.on_message = on_message

    client.loop_forever()
