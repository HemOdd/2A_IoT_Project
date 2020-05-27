import paho.mqtt.client as mqtt
import json
import FirebaseInit
from asyncInputRecorder import *
import datetime

time = [None] * 2 # depends on the number of sensor

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

    value = json.loads(m_decode)

    if value['sensorid'] == 0:
        if value['distance'] < 5.00:
            type = "distance"
            log_File(value,30,type)

        elif value['temperature'] > 36.00:
            type = "temperature"
            log_File(value,120,type)
        elif value['humidity'] < 50.00:
            type = "humidity"
            log_File(value,120,type)


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

        client.subscribe("eclipse.org/FIT3162/Team2A/sensor1", 0)
        # client.subscribe("/team2A/esp32/sensor1", 0)
        FirebaseInit.initialize()
    else:
        print ("Status: User 1 - Bad Conncetion With Error Code: ",rc)

def log_File(value,delay,type):
    """
    This function check whether to log to firebase
    :param value: contatins json Format
    :param delay: time before next log
    :return: None
    """
    global time

    print("check logging file....")
    sensorId = value['sensorid']

    dTime = datetime.datetime.now()
    if time[sensorId] is None:
        time[sensorId] = dTime
        log_File_Helper(value,type)
    else:
        # print('original time: ',time[sensorId])
        temp = time[sensorId] + datetime.timedelta(seconds=delay)
        if datetime.datetime.now() > temp:
            time[sensorId] = sensorId
            log_File_Helper(value,type)



def log_File_Helper(value,type):
    """
    This function helps to store to database
    :param value: json format of the value recv from mqtt
    :return:
    """

    print("log file..")
    db = FirebaseInit.firestore.client()
    dTime = datetime.datetime.now()
    dateToStr = dTime.strftime("%d/%m/%Y")
    timeToStr = dTime.strftime("%H:%M:%S")

    doc_ref = db.collection("Alert").document()
    doc_ref.set({
        'payload': value[type],
        'dateStamp': dateToStr,
        'timeStamp': timeToStr,
        'sensorName': value['sensorid'],
        'location':value['location'],
        'sensorType': type
    })

if __name__ == "__main__":
    client = mqtt.Client("user1-8936")
    client.connect("mqtt.eclipse.org",1883, 60)
    client.on_connect = on_connect

    client.on_message = on_message

    client.loop_forever()
