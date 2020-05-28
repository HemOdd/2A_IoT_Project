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
        "apiKey": "AIzaSyA0uv2MNoeoCwbBt4O350a8Dsm_9IwfVGE",
        "databaseURL": "https://team-2a-security.firebaseio.com/",

    })
