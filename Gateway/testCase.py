"""
Created by Nicholas jensky Winata
"""
import unittest
import mqttUser
import logicTest
import datetime
import json
import time


class UnittestMQTT(unittest.TestCase):
    """
    Note that we are not testing the mqtt paho library
    mqtt paho library is already tested by eclipse.org and available at
    https://github.com/eclipse/paho.mqtt.python
    Here the unittest is solely test if all function can be executed
    and receive correct value
    """


    def test_1(self):
        # the first test is just to test unittest import
        self.assertFalse(False)
        print("test oke")
    def test_onconnect(self):
        client1 = mqttUser.mqtt.Client()
        # if 0 means successfully connected
        self.assertEqual(client1.connect("mqtt.eclipse.org", 1883, 3), 0)
        client1.disconnect()
        print("connect oke")


class UnittestLogic(unittest.TestCase):
    """
    test the logic of the function
    """

    def test_onMessage_logic(self):
        payload = {"sensorid": 0, "distance": 100 , "temperature": 0, "humidity": 100}
        payload = json.dumps(payload)
        result = logicTest.on_message(payload)

        # this is because nothing reach tress hold
        self.assertEqual(result, 0)
        print("not all oke")

        payload = {"sensorid": 0, "distance": 4.99, "temperature": 0, "humidity": 100}
        payload = json.dumps(payload)
        result = logicTest.on_message(payload)

        # distance triggered
        self.assertEqual(result, 1)
        print("distance oke")

        payload = {"sensorid": 0, "distance": 80, "temperature": 36.1, "humidity": 100}
        payload = json.dumps(payload)
        result = logicTest.on_message(payload)

        # temperature triggered
        self.assertEqual(result, 2)
        print("temperature oke")

        payload = {"sensorid": 0, "distance": 80, "temperature": 35.9, "humidity": 49.9}
        payload = json.dumps(payload)
        result = logicTest.on_message(payload)

        # humidity triggered
        self.assertEqual(result, 3)
        print("humidity oke")

    def test_logFile(self):
        payload = {"sensorid": 0, "distance": 20, "temperature": 35.9, "humidity": 49.9}
        payload = json.dumps(payload)
        payload = json.loads(payload)
        result =logicTest.log_File(payload,30,0)

        # make sure only correct place is filled
        self.assertTrue(result[0][0] is not None)
        self.assertFalse(result[0][1] is not None)
        self.assertFalse(result[1][0] is not None)

        # test delta
        result =logicTest.log_File(payload,30,0)

        # check only after delay time should log data again to server
        self.assertTrue(result[0][0] >= datetime.datetime.now())

        # check if delay parameter is working as it supposed to
        result = logicTest.log_File(payload, 3, 0)
        time.sleep(3)
        self.assertTrue(result[0][0] < datetime.datetime.now())

    def test_convertType(self):

        #0 convert oke
        result = logicTest.typeConverter(0)
        self.assertEqual(result,"distance")

        result = logicTest.typeConverter(1)
        self.assertEqual(result, 'temperature')


        result = logicTest.typeConverter(2)
        self.assertEqual(result, "humidity")

        result = logicTest.typeConverter(3)
        self.assertEqual(result, "invalid value")


if __name__ == "__main__":
    unittest.main()