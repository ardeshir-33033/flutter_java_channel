import 'request_api.dart';

class UserEndPoint extends RequestApi {
  @override
  String baseName = 'GetUser';
  @override
  String baseUrl = 'https://dinavision.org/Account/GetUser';
}

class UserOldEndPoint extends RequestApi {
  @override
  String baseName = 'Users';
  @override
  String baseUrl = 'https://token.dinavision.org/api/v1';
}

class BranchEndPoint extends RequestApi {
  @override
  String baseName = 'Branch';
  @override
  String baseUrl = 'https://qc.dinavision.org/api/v1';
}

class InvoiceEndPoint extends RequestApi {
  @override
  String baseName = 'Invoice';
  @override
  String baseUrl = 'https://qc.dinavision.org/api/v1';
}

class CommisionTransactionEndPoint extends RequestApi {
  @override
  String baseName = 'CommisionTransaction';
  @override
  String baseUrl = 'https://qc.dinavision.org/api/v1';
}
