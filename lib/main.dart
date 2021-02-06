import 'dart:io';
import 'package:background_service/xd/XD_iPhoneXXS11Pro1.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'LoginModel.dart';
import 'end_point_api.dart';
import 'request_api.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  final myuser = TextEditingController();
  final mypass = TextEditingController();

  Future<String> login() async {
    try {
      LoginModel _loginModel = LoginModel(
        password: mypass.text,
        userName: myuser.text,
      );
      String _responseData =
      await UserEndPoint().addParameter("").httpPost(
        _loginModel.toFormData(),
        HeaderTypeEnum.EmptyHeaderEnum,
        ResponseTypeEnum.ResponseModelEnum,
      );
      if (_responseData != "") {
        await setUserDataInSharePrefrences(_responseData);
        startServiceInPlatform(_responseData);
        return null;
      } else {
        return _responseData;
      }
    } catch (e) {
      print(e);
    }
  }

  Future<void> setUserDataInSharePrefrences(String id) async {
    try {
      final prefs = await SharedPreferences.getInstance();
      prefs.setString('taskmanagerID', id);
      return;
    } catch (e) {
      return;
    }
  }

  void startServiceInPlatform(String id) async {
    if(Platform.isAndroid){
      var methodChannel = MethodChannel("com.retroportalstudio.messages");
      String data = await methodChannel.invokeMethod("startService", <String, String>{
        'tid': id,
      });
      debugPrint(data);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      child: XD_iPhoneXXS11Pro1().build(context),
    );
//    return Scaffold(
//      body: Container(
//        color: Colors.white,
//        child: Center(
//          child: Row(
//            crossAxisAlignment: CrossAxisAlignment.center,
//            mainAxisAlignment: MainAxisAlignment.center,
//            children: <Widget>[
//              Flexible(
//                child: Padding(
//                  padding: EdgeInsets.all(50),
//                  child: Column(
//                    crossAxisAlignment: CrossAxisAlignment.center,
//                    mainAxisAlignment: MainAxisAlignment.center,
//                    children: <Widget>[
//                      Container(
//                        margin: EdgeInsets.symmetric(vertical: 3),
//                        child: Column(
//                          crossAxisAlignment: CrossAxisAlignment.start,
//                          children: <Widget>[
//                            Text(
//                              'user name',
//                              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 15),
//                            ),
//                            SizedBox(
//                              height: 10,
//                            ),
//                            TextFormField(
//                              controller: myuser,
//                              obscureText: false,
//                              keyboardType: TextInputType.text,
//                              decoration: InputDecoration(
//                                border: OutlineInputBorder(
//                                  borderRadius: const BorderRadius.all(
//                                    const Radius.circular(10.0),
//                                  ),
//                                  borderSide: BorderSide(color: Colors.grey),
//                                ),
//                                enabledBorder: OutlineInputBorder(
//                                  borderSide: new BorderSide(
//                                    color: Colors.grey,
//                                  ),
//                                  borderRadius: const BorderRadius.all(
//                                    const Radius.circular(10.0),
//                                  ),
//                                ),
//                                focusedBorder: OutlineInputBorder(
//                                  borderSide: BorderSide(color: Colors.grey),
//                                  borderRadius: BorderRadius.circular(10.0),
//                                ),
//                              ),
//                            )
//                          ],
//                        ),
//                      ),
//                      Container(
//                        margin: EdgeInsets.symmetric(vertical: 3),
//                        child: Column(
//                          crossAxisAlignment: CrossAxisAlignment.start,
//                          children: <Widget>[
//                            Text(
//                              'password',
//                              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 15),
//                            ),
//                            SizedBox(
//                              height: 10,
//                            ),
//                            TextFormField(
//                              controller: mypass,
//                              obscureText: true,
//                              keyboardType: TextInputType.text,
//                              decoration: InputDecoration(
//                                border: OutlineInputBorder(
//                                  borderRadius: const BorderRadius.all(
//                                    const Radius.circular(10.0),
//                                  ),
//                                  borderSide: BorderSide(color: Colors.grey),
//                                ),
//                                enabledBorder: OutlineInputBorder(
//                                  borderSide: new BorderSide(
//                                    color: Colors.grey,
//                                  ),
//                                  borderRadius: const BorderRadius.all(
//                                    const Radius.circular(10.0),
//                                  ),
//                                ),
//                                focusedBorder: OutlineInputBorder(
//                                  borderSide: BorderSide(color: Colors.grey),
//                                  borderRadius: BorderRadius.circular(10.0),
//                                ),
//                              ),
//                            )
//                          ],
//                        ),
//                      ),
//                      RaisedButton(
//                          child: Text("Start Background"),
//                          onPressed: (){
//                            login();
//                          }
//                      ),
//                    ],
//                  ),
//                ),
//              )
//            ],
//          ),
//        ),
//      ),
//    );
  }
}


