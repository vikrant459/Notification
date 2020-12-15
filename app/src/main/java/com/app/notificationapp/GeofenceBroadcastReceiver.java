package com.app.notificationapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        GeofencingEvent geofencingEvent=GeofencingEvent.fromIntent(intent);
        final NotificationHelper notificationHelper=new NotificationHelper(context);

        if(geofencingEvent.hasError()){
            Toast.makeText(context, "error reveiving geofence event", Toast.LENGTH_SHORT).show();
            return;
        }

       List<Geofence> geofenceList=geofencingEvent.getTriggeringGeofences();
        for(Geofence geofence:geofenceList)
        {
            Toast.makeText(context, "in list:"+geofence.toString(), Toast.LENGTH_SHORT).show();
        }
      //  Location location=geofencingEvent.getTriggeringLocation();


        int transType=geofencingEvent.getGeofenceTransition();
        switch(transType)
        {
            case Geofence.GEOFENCE_TRANSITION_ENTER: {
                Toast.makeText(context, "enter", Toast.LENGTH_SHORT).show();
                break;
            }
            case Geofence.GEOFENCE_TRANSITION_EXIT: {
                        Toast.makeText(notificationHelper, "exit", Toast.LENGTH_SHORT).show();
                         break;
            }
            case Geofence.GEOFENCE_TRANSITION_DWELL: {
                        notificationHelper.sendHighPriorityNotification("GEOFENCE_TRANSITION_DWELL","you are dwelling inside 3 km range",MainActivity2.class);

                break;
            }
        }

    }
}
