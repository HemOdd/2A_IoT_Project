import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore

def initialize():
    """
    This function contain credentials of the database this file is confidential
    :return: none
    """
    cred = credentials.Certificate('./ServiceAccountKey.json')
    firebase_admin.initialize_app(cred,{
        "apiKey": "AIzaSyCzfAt1eLmwZw3-DFP-i78g2AO-HwDyNug",
        "authDomain": "st-arduino-proj.firebaseapp.com",
        "databaseURL": "https://st-arduino-proj.firebaseio.com",
        "storageBucket": "st-arduino-proj.appspot.com"

    })
