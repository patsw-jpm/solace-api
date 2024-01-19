# TransactionApi

All URIs are relative to *http://www.solace.com/SEMP/v2/action*

Method | HTTP request | Description
------------- | ------------- | -------------
[**doMsgVpnTransactionCommit**](TransactionApi.md#doMsgVpnTransactionCommit) | **PUT** /msgVpns/{msgVpnName}/transactions/{xid}/commit | Commit the Transaction.
[**doMsgVpnTransactionDelete**](TransactionApi.md#doMsgVpnTransactionDelete) | **PUT** /msgVpns/{msgVpnName}/transactions/{xid}/delete | Delete the Transaction.
[**doMsgVpnTransactionRollback**](TransactionApi.md#doMsgVpnTransactionRollback) | **PUT** /msgVpns/{msgVpnName}/transactions/{xid}/rollback | Rollback the Transaction.
[**getMsgVpnTransaction**](TransactionApi.md#getMsgVpnTransaction) | **GET** /msgVpns/{msgVpnName}/transactions/{xid} | Get a Replicated Local Transaction or XA Transaction object.
[**getMsgVpnTransactions**](TransactionApi.md#getMsgVpnTransactions) | **GET** /msgVpns/{msgVpnName}/transactions | Get a list of Replicated Local Transaction or XA Transaction objects.


<a name="doMsgVpnTransactionCommit"></a>
# **doMsgVpnTransactionCommit**
> SempMetaOnlyResponse doMsgVpnTransactionCommit(msgVpnName, xid, body)

Commit the Transaction.

Commit the Transaction.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.12.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.TransactionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

TransactionApi apiInstance = new TransactionApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String xid = "xid_example"; // String | The identifier (ID) of the Transaction.
MsgVpnTransactionCommit body = new MsgVpnTransactionCommit(); // MsgVpnTransactionCommit | The Commit action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnTransactionCommit(msgVpnName, xid, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TransactionApi#doMsgVpnTransactionCommit");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **xid** | **String**| The identifier (ID) of the Transaction. |
 **body** | [**MsgVpnTransactionCommit**](MsgVpnTransactionCommit.md)| The Commit action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnTransactionDelete"></a>
# **doMsgVpnTransactionDelete**
> SempMetaOnlyResponse doMsgVpnTransactionDelete(msgVpnName, xid, body)

Delete the Transaction.

Delete the Transaction.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.12.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.TransactionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

TransactionApi apiInstance = new TransactionApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String xid = "xid_example"; // String | The identifier (ID) of the Transaction.
MsgVpnTransactionDelete body = new MsgVpnTransactionDelete(); // MsgVpnTransactionDelete | The Delete action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnTransactionDelete(msgVpnName, xid, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TransactionApi#doMsgVpnTransactionDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **xid** | **String**| The identifier (ID) of the Transaction. |
 **body** | [**MsgVpnTransactionDelete**](MsgVpnTransactionDelete.md)| The Delete action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnTransactionRollback"></a>
# **doMsgVpnTransactionRollback**
> SempMetaOnlyResponse doMsgVpnTransactionRollback(msgVpnName, xid, body)

Rollback the Transaction.

Rollback the Transaction.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.12.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.TransactionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

TransactionApi apiInstance = new TransactionApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String xid = "xid_example"; // String | The identifier (ID) of the Transaction.
MsgVpnTransactionRollback body = new MsgVpnTransactionRollback(); // MsgVpnTransactionRollback | The Rollback action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnTransactionRollback(msgVpnName, xid, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TransactionApi#doMsgVpnTransactionRollback");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **xid** | **String**| The identifier (ID) of the Transaction. |
 **body** | [**MsgVpnTransactionRollback**](MsgVpnTransactionRollback.md)| The Rollback action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnTransaction"></a>
# **getMsgVpnTransaction**
> MsgVpnTransactionResponse getMsgVpnTransaction(msgVpnName, xid, select)

Get a Replicated Local Transaction or XA Transaction object.

Get a Replicated Local Transaction or XA Transaction object.  Transactions can be used to group a set of Guaranteed messages to be published or consumed or both as an atomic unit of work.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| xid|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.TransactionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

TransactionApi apiInstance = new TransactionApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String xid = "xid_example"; // String | The identifier (ID) of the Transaction.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnTransactionResponse result = apiInstance.getMsgVpnTransaction(msgVpnName, xid, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TransactionApi#getMsgVpnTransaction");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **xid** | **String**| The identifier (ID) of the Transaction. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnTransactionResponse**](MsgVpnTransactionResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnTransactions"></a>
# **getMsgVpnTransactions**
> MsgVpnTransactionsResponse getMsgVpnTransactions(msgVpnName, count, cursor, where, select)

Get a list of Replicated Local Transaction or XA Transaction objects.

Get a list of Replicated Local Transaction or XA Transaction objects.  Transactions can be used to group a set of Guaranteed messages to be published or consumed or both as an atomic unit of work.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| xid|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.TransactionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

TransactionApi apiInstance = new TransactionApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnTransactionsResponse result = apiInstance.getMsgVpnTransactions(msgVpnName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TransactionApi#getMsgVpnTransactions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **count** | **Integer**| Limit the count of objects in the response. See the documentation for the &#x60;count&#x60; parameter. | [optional] [default to 10]
 **cursor** | **String**| The cursor, or position, for the next page of objects. See the documentation for the &#x60;cursor&#x60; parameter. | [optional]
 **where** | [**List&lt;String&gt;**](String.md)| Include in the response only objects where certain conditions are true. See the the documentation for the &#x60;where&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnTransactionsResponse**](MsgVpnTransactionsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

