# ReplayLogApi

All URIs are relative to *http://www.solace.com/SEMP/v2/action*

Method | HTTP request | Description
------------- | ------------- | -------------
[**doMsgVpnReplayLogTrimLoggedMsgs**](ReplayLogApi.md#doMsgVpnReplayLogTrimLoggedMsgs) | **PUT** /msgVpns/{msgVpnName}/replayLogs/{replayLogName}/trimLoggedMsgs | Trim (delete) messages from the Replay Log.
[**getMsgVpnReplayLog**](ReplayLogApi.md#getMsgVpnReplayLog) | **GET** /msgVpns/{msgVpnName}/replayLogs/{replayLogName} | Get a Replay Log object.
[**getMsgVpnReplayLogs**](ReplayLogApi.md#getMsgVpnReplayLogs) | **GET** /msgVpns/{msgVpnName}/replayLogs | Get a list of Replay Log objects.


<a name="doMsgVpnReplayLogTrimLoggedMsgs"></a>
# **doMsgVpnReplayLogTrimLoggedMsgs**
> SempMetaOnlyResponse doMsgVpnReplayLogTrimLoggedMsgs(msgVpnName, replayLogName, body)

Trim (delete) messages from the Replay Log.

Trim (delete) messages from the Replay Log.   Attribute|Required|Deprecated :---|:---:|:---: olderThanTime|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ReplayLogApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ReplayLogApi apiInstance = new ReplayLogApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String replayLogName = "replayLogName_example"; // String | The name of the Replay Log.
MsgVpnReplayLogTrimLoggedMsgs body = new MsgVpnReplayLogTrimLoggedMsgs(); // MsgVpnReplayLogTrimLoggedMsgs | The Trim Logged Messages action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnReplayLogTrimLoggedMsgs(msgVpnName, replayLogName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ReplayLogApi#doMsgVpnReplayLogTrimLoggedMsgs");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **replayLogName** | **String**| The name of the Replay Log. |
 **body** | [**MsgVpnReplayLogTrimLoggedMsgs**](MsgVpnReplayLogTrimLoggedMsgs.md)| The Trim Logged Messages action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnReplayLog"></a>
# **getMsgVpnReplayLog**
> MsgVpnReplayLogResponse getMsgVpnReplayLog(msgVpnName, replayLogName, select)

Get a Replay Log object.

Get a Replay Log object.  When the Message Replay feature is enabled, message brokers store persistent messages in a Replay Log. These messages are kept until the log is full, after which the oldest messages are removed to free up space for new messages.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| replayLogName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ReplayLogApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ReplayLogApi apiInstance = new ReplayLogApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String replayLogName = "replayLogName_example"; // String | The name of the Replay Log.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnReplayLogResponse result = apiInstance.getMsgVpnReplayLog(msgVpnName, replayLogName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ReplayLogApi#getMsgVpnReplayLog");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **replayLogName** | **String**| The name of the Replay Log. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnReplayLogResponse**](MsgVpnReplayLogResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnReplayLogs"></a>
# **getMsgVpnReplayLogs**
> MsgVpnReplayLogsResponse getMsgVpnReplayLogs(msgVpnName, count, cursor, where, select)

Get a list of Replay Log objects.

Get a list of Replay Log objects.  When the Message Replay feature is enabled, message brokers store persistent messages in a Replay Log. These messages are kept until the log is full, after which the oldest messages are removed to free up space for new messages.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| replayLogName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ReplayLogApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ReplayLogApi apiInstance = new ReplayLogApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnReplayLogsResponse result = apiInstance.getMsgVpnReplayLogs(msgVpnName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ReplayLogApi#getMsgVpnReplayLogs");
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

[**MsgVpnReplayLogsResponse**](MsgVpnReplayLogsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

