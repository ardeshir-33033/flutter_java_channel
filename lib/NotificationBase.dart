class WPNotification {
  String url =
      "https://messaging.dinavision.org/api/v1/Messaging/SendByUserName";
  String username;
  NotificationBody data;

  WPNotification({this.data, this.username});

  WPNotification fromJson(dynamic jsn) {
    this.url = jsn["url"].toString();
    this.username = jsn["username"].toString();
    this.data = NotificationBody().fromJson(jsn["data"]);

    return this;
  }
}

class NotificationBody {
  WebPushNotification notification;

  NotificationBody({this.notification});

  NotificationBody fromJson(dynamic jsn) {
    this.notification = jsn["notification"];

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
  bool renotify = true;
  bool requireInteraction;
  bool silent;
  List<WebNotificationAction> actions;
  int timestamp;
  WebPushNotificationData data;

  WebPushNotification fromJson(dynamic jsn){
    this.title = jsn["title"].toString();
    this.body = jsn["body"].toString();
    this.tag = jsn["tag"].toString();
    this.icon = jsn["icon"].toString();
    this.image = jsn["image"].toString();
    this.badge = jsn["badge"].toString();
    this.vibrate = jsn["vibrate"].toString();
    this.sound = jsn["sound"].toString();
    this.dir = jsn["dir"].toString();
    this.renotify = jsn["renotify"];
    this.requireInteraction = jsn["requireInteraction"];
    this.silent = jsn["silent"];
    this.actions = WebNotificationAction().listFromJson(jsn["actions"]);
    this.timestamp = int.parse(jsn["timestamp"].toString());
    this.data = WebPushNotificationData().fromJson(jsn["data"]);

    return this;
  }
}

class WebPushNotificationData {
  String time;
  String message;

  WebPushNotificationData({
    this.time,
    this.message
});

  WebPushNotificationData fromJson(dynamic jsn){
    this.message = jsn["message"].toString();
    this.time = jsn["time"].toString();

    return this;
  }
}

class WebNotificationAction {
  String action;
  String title;
  String icon;

  WebNotificationAction({this.title, this.action, this.icon});

  WebNotificationAction fromJson(dynamic jsn){
    this.icon = jsn["icon"].toString();
    this.title = jsn["title"].toString();
    this.action = jsn["action"].toString();

    return this;
  }

  List<WebNotificationAction> listFromJson(dynamic jsn){
    if(jsn != null)
    {
      return jsn.map((data) => WebNotificationAction().fromJson(data))
          .toList();
    }

    return null;
  }
}

class NotificationResult {
  bool isSuccess;
  String statusCode;
  String message;
}
