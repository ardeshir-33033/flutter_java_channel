package com.retroportalstudio.www.background_service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.JsonReader;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompat.Builder;

import com.google.gson.Gson;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class MyService extends Service {
    String tid = "no";
    private NotificationManager mNotificationManager;
    public static final String NOTIFICATION_CHANNEL_ID = "notifications";

    private HubConnection mHubConnection;
    private Handler mHandler; // to display Toast message
    String msg = "no message";

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        try{
            NotificationChannel notechannel = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                notechannel = new NotificationChannel("notifications","Notifications", NotificationManager.IMPORTANCE_DEFAULT);
                mNotificationManager = getSystemService(NotificationManager.class);
                mNotificationManager.createNotificationChannel(notechannel);
            }

            SharedPreferences prefs = getSharedPreferences("taskmanagerplugin", MODE_PRIVATE);
            tid = prefs.getString("tid", "no");

            mHubConnection = HubConnectionBuilder.create("https://messaging.dinavision.org/chatHub").build();

            mHubConnection = new hc().execute(mHubConnection).get();

            try{
                mHubConnection.invoke("SendMessageToAll", "start signalR", "ReceiveMessage");
            }catch (Exception Ex){
                //hubConnection.start().blockingAwait();
                shownotif(Ex.getMessage(), 8740087);
            }

        }catch (Exception ex){
            shownotif(ex.getMessage(), 58450000);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Builder builder = new Builder(this,"messages")
                    .setContentText("This is running in Background")
                    .setContentTitle("Flutter Background")
                    .setSmallIcon(R.drawable.ic_android_black_24dp);

            startForeground(101,builder.build());
        }
        else{
            shownotif("start background service");
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void shownotif(String m){
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);

        String msg = "no message";

        if (m != ""){
            msg = m;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            Builder builder = new Builder(this,"messages")
                    .setContentText(msg)
                    .setContentTitle("notification")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText("notification"))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(msg).setSummaryText("#hashtag"))
                    .setShowWhen(true)
                    .setSmallIcon(R.drawable.ic_android_black_24dp);

            notificationManager.notify(54646, builder.build());
        }
        else{
            Notification notification = new NotificationCompat.Builder(getBaseContext(),"notification_id")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("notification")
                    .setContentText(msg)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText("notification"))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(msg).setSummaryText("#hashtag"))
                    .setShowWhen(true)
                    .setDefaults(NotificationCompat.DEFAULT_SOUND)
                    .build();
            notificationManager.notify(0, notification);
        }

        Log.d("json", msg);
    }
    public void shownotif(String m, int tag){
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);

        String msg = "no message";

        if (m != ""){
            msg = m;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            Builder builder = new Builder(this,"messages")
                    .setContentText(msg)
                    .setContentTitle("notification")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText("notification"))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(msg).setSummaryText("#hashtag"))
                    .setShowWhen(true)
                    .setSmallIcon(R.drawable.ic_android_black_24dp);

            notificationManager.notify(tag, builder.build());
        }
        else{
            Notification notification = new NotificationCompat.Builder(getBaseContext(),"notification_id")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("notification")
                    .setContentText(msg)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText("notification"))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(msg).setSummaryText("#hashtag"))
                    .setShowWhen(true)
                    .setDefaults(NotificationCompat.DEFAULT_SOUND)
                    .build();
            notificationManager.notify(tag, notification);
        }

        Log.d("json", msg);
    }

    public void shownotif(NotificationBody m){
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);

        String msg = "no message";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            Builder builder = new Builder(this,"messages")
                    .setContentText(m.notification.body)
                    .setContentTitle(m.notification.title)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(m.notification.title))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(m.notification.body).setSummaryText("#hashtag"))
                    .setShowWhen(true)
                    .setDefaults(NotificationCompat.DEFAULT_SOUND)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(m.notification.body))
                    .setSmallIcon(R.drawable.ic_android_black_24dp);

            notificationManager.notify(54646, builder.build());
        }
        else{
            Notification notification = new NotificationCompat.Builder(getBaseContext(),"notification_id")
                    .setContentText(m.notification.body)
                    .setContentTitle(m.notification.title)
                    .setSmallIcon(R.drawable.ic_android_black_24dp)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(m.notification.title))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(m.notification.body).setSummaryText("#hashtag"))
                    .setShowWhen(true)
                    .setDefaults(NotificationCompat.DEFAULT_SOUND)
                    .build();
            notificationManager.notify(0, notification);
        }
    }

    //class HubConnectionTask extends AsyncTask<HubConnection, Void, Void> {
    //    @Override
    //    protected void onPreExecute() {
    //        super.onPreExecute();
    //    }

    //    @Override
    //    protected Void doInBackground(HubConnection... hubConnections) {
    //        try{
    //            HubConnection hubConnection = hubConnections[0];
    //            hubConnection.start().blockingAwait();

    //            msg = hubConnection.getConnectionState().name();
    //        }catch (Exception ex){
    //            msg = ex.getMessage();
    //        }

    //        return null;
    //    }
    //}

    class hc extends AsyncTask<HubConnection, Void, HubConnection>{

        @Override
        protected HubConnection doInBackground(HubConnection... hubConnections) {
            try{
                HubConnection hubConnection = hubConnections[0];

                hubConnection.on("ReceiveMessage", (message)-> {
                    try {
                        NotificationBody j = new Gson().fromJson(message, NotificationBody.class);

                        shownotif(j);
                    } catch (Exception e) {
                        e.printStackTrace();
                        shownotif(message);
                    }
                }, String.class);

                hubConnection.start().blockingAwait();

                shownotif(hubConnection.getConnectionId(), 60784854);
                shownotif("hc: " + hubConnection.getConnectionState().name(), 5786864);

                try{
                    hubConnection.invoke("SendMessageToAll", "start signalR", "ReceiveMessage");
                }catch (Exception Ex){
                    //hubConnection.start().blockingAwait();
                    shownotif("hc: " + Ex.getMessage(), 874034087);
                }

                return hubConnection;
            }catch (Exception ex){
                msg = ex.getMessage();
                shownotif(ex.getMessage());
                Log.d("error DO B", ex.getMessage());
            }

            return null;
        }
    }

    public static String getConnectivityStatusString(Context context) {
        String status = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status = "Wifi enabled";
                return status;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                status = "Mobile data enabled";
                return status;
            }
        } else {
            status = "No internet is available";
            return status;
        }
        return status;
    }
}
