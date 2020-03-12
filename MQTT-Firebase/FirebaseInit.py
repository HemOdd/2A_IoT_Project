import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore

def initialize():
    cred = credentials.Certificate('./ServiceAccountKey.json')
    firebase_admin.initialize_app(cred,{
        "apiKey": "AIzaSyCzfAt1eLmwZw3-DFP-i78g2AO-HwDyNug",
        "authDomain": "st-arduino-proj.firebaseapp.com",
        "databaseURL": "https://st-arduino-proj.firebaseio.com",
        "storageBucket": "st-arduino-proj.appspot.com"

    })
'''
db=firestore.client()

exit = 0

while exit == 0:
    value = int(input("Temperature: ?"))

    doc_ref = db.collection('Sensor 1').document('Temperature')
    doc_ref.set({
        'temperature': value
    })

    exit = int(input("1 to exit; other key to continue")) '''

