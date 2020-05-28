// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');

// The Firebase Admin SDK to access Cloud Firestore.
const admin = require('firebase-admin');
admin.initializeApp();

// Take the text parameter passed to this HTTP endpoint and insert it into 
// Cloud Firestore under the path /messages/:documentId/original
exports.sendNotification = functions.firestore
    .document('Log/{AlertId}')
    .onCreate((snap, context) => {
      // Get an object representing the document
     
      const newValue = snap.data();
      console.log(newValue)
      const payload = {notification: {
        title: "Alert",
        body: `From:${newValue.sensorType}, on: ${newValue.location}, val: ${newValue.payload}`
        }
    }; 
      
      

      // perform desired operations ...

    return admin.messaging().sendToTopic("notification",payload)
    .then(function(response){
         console.log('Notification sent successfully:',response);
         return null;
    }) 
    .catch(function(error){
         console.log('Notification sent failed:',error);
    });
    });

