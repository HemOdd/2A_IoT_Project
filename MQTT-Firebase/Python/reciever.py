import paho.mqtt.client as mqtt


# This is the Subscriber

def on_connect(client, userdata, flags, rc):
    print("Connected with result code " + str(rc))
    client.subscribe("topic/test")


def on_message(client, userdata, msg):
    if msg.payload.decode() == "hello world":
        m_decode = str(msg.payload.decode("utf-8"))
        print("Message Received: ", m_decode)
        #client.disconnect()


client = mqtt.Client("r1")
client.connect("mqtt.eclipse.org", 1883, 60)

client.on_connect = on_connect
client.on_message = on_message

client.loop_forever()