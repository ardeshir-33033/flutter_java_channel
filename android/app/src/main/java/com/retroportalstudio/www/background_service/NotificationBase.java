package com.retroportalstudio.www.background_service;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationBase {
}

class WPNotification {
    String url =
            "https://messaging.dinavision.org/api/v1/Messaging/SendByUserName";
    String username;
    NotificationBody data;

    WPNotification(){

    }

    WPNotification(String un, NotificationBody nb){
        username = un;
        data = nb;
    }

    WPNotification fromJson(JSONObject jsn) {
        try{
            this.url = jsn.getString("url");
            this.username = jsn.getString("username");
            this.data = new NotificationBody().fromJson(jsn.getJSONObject("data"));

            return this;
        }catch (Exception Ex){
            return this;
        }
    }
}

class NotificationBody {
    WebPushNotification notification;

    NotificationBody(){

    }

    NotificationBody(WebPushNotification not){
        notification = not;
    }

    NotificationBody fromJson(JSONObject jsn) {
        try{
            this.notification = new WebPushNotification().fromJson(jsn.getJSONObject("notification"));

            return this;
        }catch (Exception Ex){
            Log.d("error", Ex.getMessage());
        }

        return this;
    }
}

class WebPushNotification {
    String title;
    String body;
    String tag;
    String icon;
    String image;
    String badge;
    String vibrate;
    String sound;
    String dir;
    Boolean renotify = true;
    Boolean requireInteraction;
    Boolean silent;
    ArrayList<WebNotificationAction> actions;
    int timestamp;
    WebPushNotificationData data;

    WebPushNotification fromJson(JSONObject jsn){
        try{
            this.title = jsn.getString("title");
            this.body = jsn.getString("body");
            this.tag = jsn.getString("tag");
            this.icon = jsn.getString("icon");
            this.image = jsn.getString("image");
            this.badge = jsn.getString("badge");
            this.vibrate = jsn.getString("vibrate");
            this.sound = jsn.getString("sound");
            this.dir = jsn.getString("dir");
            this.renotify = jsn.getBoolean("renotify");
            this.requireInteraction = jsn.getBoolean("requireInteraction");
            this.silent = jsn.getBoolean("silent");
            this.actions = new WebNotificationAction().listFromJson(jsn.getJSONArray("actions"));
            this.timestamp = Integer.parseInt(jsn.getString("timestamp"));
            this.data = new WebPushNotificationData().fromJson(jsn.getJSONObject("data"));

            return this;
        }catch (Exception Ex){
            Log.d("error", Ex.getMessage());
        }

        return this;
    }
}

class WebPushNotificationData {
    String time;
    String message;

    WebPushNotificationData(){

    }

    WebPushNotificationData(String tm, String msg){
        message = msg;
        time = tm;
    }

    WebPushNotificationData fromJson(JSONObject jsn){
        try{
            this.message = jsn.getString("message");
            this.time = jsn.getString("time");
        }catch (Exception Ex){
            Log.d("error", Ex.getMessage());
        }
        
        return this;
    }
}

class WebNotificationAction {
    String action;
    String title;
    String icon;

    WebNotificationAction(){

    }

    WebNotificationAction(String ic, String ttl, String act){
        action = act;
        title = ttl;
        icon = ic;
    }

    WebNotificationAction fromJson(JSONObject jsn){
        try{
            this.icon = jsn.getString("icon");
            this.title = jsn.getString("title");
            this.action = jsn.getString("action");
            
            return this;
        }catch (Exception Ex){
            Log.d("error", Ex.getMessage());
        }

        return this;
    }

    ArrayList<WebNotificationAction> listFromJson(JSONArray jsn){
        try{
            ArrayList<WebNotificationAction> list = new ArrayList<WebNotificationAction>();
            if(jsn != null)
            {
                for (int i=0;i<jsn.length();i++){
                    list.add(new WebNotificationAction().fromJson(jsn.getJSONObject(i)));
                }
            }

            return list;
        }catch (Exception Ex){
            Log.d("error", Ex.getMessage());
        }

        return null;
    }
}

class NotificationResult {
    Boolean isSuccess;
    String statusCode;
    String message;
}
