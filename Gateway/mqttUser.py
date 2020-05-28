"""
created by Hemad
modified by Nicholas Jensky Winata
reference https://pypi.org/project/paho-mqtt/
"""
import paho.mqtt.client as mqtt
import json
import FirebaseInit
from asyncInputRecorder import *
import datetime

time = [[None for x in range(3)] for y in range(2)] # depends on the number of sensor

def my_callback(userInput):
    """
    This function is to seAnd message to a particular topic
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
        return userInput


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

    print(value)
    if value['sensorid'] == 0:
        if value['distance'] < 5.00 and value['distance'] > 0:
            type = 0
            log_File(value,30,type)

        if value['temperature'] > 60.00:
            type = 1
            log_File(value,120,type)

        if value['humidity'] < 20.00:
            type = 2
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
        client.subscribe("/team2A/esp32/sensor1", 0)
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
    if time[sensorId][type] is None:
        time[sensorId][type] = dTime
        log_File_Helper(value,type)
    else:
        # print('original time: ',time[sensorId])
        temp = time[sensorId][type] + datetime.timedelta(seconds=delay)
        if datetime.datetime.now() > temp:
            time[sensorId][type] = dTime
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
    print('here',value[typeConverter(type)])

    doc_ref = db.collection("Log").document()
    doc_ref.set({
        'payload': value[typeConverter(type)],
        'dateStamp': dateToStr,
        'timeStamp': timeToStr,
        'sensorId': value['sensorid'],
        'location':value['location'],
        'sensorType': typeConverter(type)
    })

    alert_ref = db.collection("Alert").document("sensor")
    alert_ref.update({
        typeConverter(type): True
    })

def typeConverter(type):
    """
    transform to more readable form
    to map 0 to distance
           1 to temperature
           2 to humidity
    :param type:
    :return: String of the corresponding type
    """
    switcher={
        0:'distance',
        1:'temperature',
        2:'humidity'
    }
    return switcher.get(type, "invalid value")


if __name__ == "__main__":
    client = mqtt.Client()
    client.connect("mqtt.eclipse.org",1883, 60)
    client.on_connect = on_connect
    client.on_message = on_message
    client.loop_forever()




