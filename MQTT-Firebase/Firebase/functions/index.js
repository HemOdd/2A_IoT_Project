const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);
exports.sendAdminNotification = functions.database.ref('/Message/{pushId}').onWrite((snap,context) => {
    if (snap.before.exists()) {
        return null;
      }
      // Exit when the data is deleted.
      if (!snap.after.exists()) {
        return null;
      }
     const smthg = context.params.pushId;
     console.log(`New message ${smthg}`);

     const news = snap.after.val()
     
     const payload = {notification: {
         title: `${news.username}`,
         body: `${news.message}`
         }
     };
return admin.messaging().sendToTopic("notification",payload)
    .then(function(response){
         console.log('Notification sent successfully:',response);
         return null;
    }) 
    .catch(function(error){
         console.log('Notification sent failed:',error);
    });
    
});

