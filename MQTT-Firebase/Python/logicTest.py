import json
import datetime

time = [[None for x in range(3)] for y in range(2)]
def on_message(msg):
    """
    This function listen to any incoming message is a simplified version of the on_message
    implemented on the mqttUser
    msg is in json format
    """
    value = json.loads(msg)
    if value['sensorid'] == 0:
        if value['distance'] < 5.00:
            type = 0
            # log_File(value,30,type)
            return 1
        if value['temperature'] > 36.00:
            type = 1
            return 2
            # log_File(value,120,type)
        if value['humidity'] < 50.00:
            type = 2
            return 3
            # log_File(value,120,type)
    return 0

def log_File(value,delay,type):
    """
    This function check whether to log to firebase
    :param value: contatins json Format
    :param delay: time before next log
    :return: None
    """
    global time

    # print("check logging file....")

    sensorId = value['sensorid']

    dTime = datetime.datetime.now()
    if time[sensorId][type] is None:
        time[sensorId][type] = dTime

    else:
        # print('original time: ',time[sensorId])
        temp = time[sensorId][type] + datetime.timedelta(seconds=delay)
        if datetime.datetime.now() > temp:
            time[sensorId][type] = dTime

    return time

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
        0:'distance sensor',
        1:'temperature sensor',
        2:'humidity sensor'
    }
    return switcher.get(type, "invalid value")


if __name__ == "__main__":
    payload = {"sensorid": 0, "distance": 80, "temperature": 35.9, "humidity": 49.9}
    payload = json.dumps(payload)
    print(log_File(payload, 30, 0))