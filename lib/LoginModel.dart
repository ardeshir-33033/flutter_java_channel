class LoginModel {
  String userName;
  String password;

  LoginModel({
    this.userName,
    this.password,
  });

  Map<String, dynamic> toFormData() => {
    "Email": userName,
    "Password": password,
  };

  LoginModel fromJson(dynamic jsn){
    this.userName = jsn["Email"].toString();
    this.password = jsn["Password"].toString();

    return this;
  }
}