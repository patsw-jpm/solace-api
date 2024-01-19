# ClientApi

All URIs are relative to *http://www.solace.com/SEMP/v2/action*

Method | HTTP request | Description
------------- | ------------- | -------------
[**doMsgVpnClientClearEvent**](ClientApi.md#doMsgVpnClientClearEvent) | **PUT** /msgVpns/{msgVpnName}/clients/{clientName}/clearEvent | Clear an event for the Client so it can be generated anew.
[**doMsgVpnClientClearStats**](ClientApi.md#doMsgVpnClientClearStats) | **PUT** /msgVpns/{msgVpnName}/clients/{clientName}/clearStats | Clear the statistics for the Client.
[**doMsgVpnClientDisconnect**](ClientApi.md#doMsgVpnClientDisconnect) | **PUT** /msgVpns/{msgVpnName}/clients/{clientName}/disconnect | Disconnect the Client.
[**doMsgVpnClientTransactedSessionDelete**](ClientApi.md#doMsgVpnClientTransactedSessionDelete) | **PUT** /msgVpns/{msgVpnName}/clients/{clientName}/transactedSessions/{sessionName}/delete | Delete the Transacted Session.
[**getMsgVpnClient**](ClientApi.md#getMsgVpnClient) | **GET** /msgVpns/{msgVpnName}/clients/{clientName} | Get a Client object.
[**getMsgVpnClientTransactedSession**](ClientApi.md#getMsgVpnClientTransactedSession) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/transactedSessions/{sessionName} | Get a Client Transacted Session object.
[**getMsgVpnClientTransactedSessions**](ClientApi.md#getMsgVpnClientTransactedSessions) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/transactedSessions | Get a list of Client Transacted Session objects.
[**getMsgVpnClients**](ClientApi.md#getMsgVpnClients) | **GET** /msgVpns/{msgVpnName}/clients | Get a list of Client objects.


<a name="doMsgVpnClientClearEvent"></a>
# **doMsgVpnClientClearEvent**
> SempMetaOnlyResponse doMsgVpnClientClearEvent(msgVpnName, clientName, body)

Clear an event for the Client so it can be generated anew.

Clear an event for the Client so it can be generated anew.   Attribute|Required|Deprecated :---|:---:|:---: eventName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ClientApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ClientApi apiInstance = new ClientApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String clientName = "clientName_example"; // String | The name of the Client.
MsgVpnClientClearEvent body = new MsgVpnClientClearEvent(); // MsgVpnClientClearEvent | The Clear Event action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnClientClearEvent(msgVpnName, clientName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ClientApi#doMsgVpnClientClearEvent");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **clientName** | **String**| The name of the Client. |
 **body** | [**MsgVpnClientClearEvent**](MsgVpnClientClearEvent.md)| The Clear Event action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnClientClearStats"></a>
# **doMsgVpnClientClearStats**
> SempMetaOnlyResponse doMsgVpnClientClearStats(msgVpnName, clientName, body)

Clear the statistics for the Client.

Clear the statistics for the Client.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ClientApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ClientApi apiInstance = new ClientApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String clientName = "clientName_example"; // String | The name of the Client.
MsgVpnClientClearStats body = new MsgVpnClientClearStats(); // MsgVpnClientClearStats | The Clear Stats action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnClientClearStats(msgVpnName, clientName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ClientApi#doMsgVpnClientClearStats");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **clientName** | **String**| The name of the Client. |
 **body** | [**MsgVpnClientClearStats**](MsgVpnClientClearStats.md)| The Clear Stats action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnClientDisconnect"></a>
# **doMsgVpnClientDisconnect**
> SempMetaOnlyResponse doMsgVpnClientDisconnect(msgVpnName, clientName, body)

Disconnect the Client.

Disconnect the Client.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ClientApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ClientApi apiInstance = new ClientApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String clientName = "clientName_example"; // String | The name of the Client.
MsgVpnClientDisconnect body = new MsgVpnClientDisconnect(); // MsgVpnClientDisconnect | The Disconnect action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnClientDisconnect(msgVpnName, clientName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ClientApi#doMsgVpnClientDisconnect");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **clientName** | **String**| The name of the Client. |
 **body** | [**MsgVpnClientDisconnect**](MsgVpnClientDisconnect.md)| The Disconnect action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnClientTransactedSessionDelete"></a>
# **doMsgVpnClientTransactedSessionDelete**
> SempMetaOnlyResponse doMsgVpnClientTransactedSessionDelete(msgVpnName, clientName, sessionName, body)

Delete the Transacted Session.

Delete the Transacted Session.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ClientApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ClientApi apiInstance = new ClientApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String clientName = "clientName_example"; // String | The name of the Client.
String sessionName = "sessionName_example"; // String | The name of the Transacted Session.
MsgVpnClientTransactedSessionDelete body = new MsgVpnClientTransactedSessionDelete(); // MsgVpnClientTransactedSessionDelete | The Delete action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnClientTransactedSessionDelete(msgVpnName, clientName, sessionName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ClientApi#doMsgVpnClientTransactedSessionDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **clientName** | **String**| The name of the Client. |
 **sessionName** | **String**| The name of the Transacted Session. |
 **body** | [**MsgVpnClientTransactedSessionDelete**](MsgVpnClientTransactedSessionDelete.md)| The Delete action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnClient"></a>
# **getMsgVpnClient**
> MsgVpnClientResponse getMsgVpnClient(msgVpnName, clientName, select)

Get a Client object.

Get a Client object.  Applications or devices that connect to message brokers to send and/or receive messages are represented as Clients.   Attribute|Identifying|Deprecated :---|:---:|:---: clientName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ClientApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ClientApi apiInstance = new ClientApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String clientName = "clientName_example"; // String | The name of the Client.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnClientResponse result = apiInstance.getMsgVpnClient(msgVpnName, clientName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ClientApi#getMsgVpnClient");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **clientName** | **String**| The name of the Client. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnClientResponse**](MsgVpnClientResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnClientTransactedSession"></a>
# **getMsgVpnClientTransactedSession**
> MsgVpnClientTransactedSessionResponse getMsgVpnClientTransactedSession(msgVpnName, clientName, sessionName, select)

Get a Client Transacted Session object.

Get a Client Transacted Session object.  Transacted Sessions enable clients to group multiple message send and/or receive operations together in single, atomic units known as local transactions.   Attribute|Identifying|Deprecated :---|:---:|:---: clientName|x| msgVpnName|x| sessionName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ClientApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ClientApi apiInstance = new ClientApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String clientName = "clientName_example"; // String | The name of the Client.
String sessionName = "sessionName_example"; // String | The name of the Transacted Session.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnClientTransactedSessionResponse result = apiInstance.getMsgVpnClientTransactedSession(msgVpnName, clientName, sessionName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ClientApi#getMsgVpnClientTransactedSession");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **clientName** | **String**| The name of the Client. |
 **sessionName** | **String**| The name of the Transacted Session. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnClientTransactedSessionResponse**](MsgVpnClientTransactedSessionResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnClientTransactedSessions"></a>
# **getMsgVpnClientTransactedSessions**
> MsgVpnClientTransactedSessionsResponse getMsgVpnClientTransactedSessions(msgVpnName, clientName, count, cursor, where, select)

Get a list of Client Transacted Session objects.

Get a list of Client Transacted Session objects.  Transacted Sessions enable clients to group multiple message send and/or receive operations together in single, atomic units known as local transactions.   Attribute|Identifying|Deprecated :---|:---:|:---: clientName|x| msgVpnName|x| sessionName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ClientApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ClientApi apiInstance = new ClientApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String clientName = "clientName_example"; // String | The name of the Client.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnClientTransactedSessionsResponse result = apiInstance.getMsgVpnClientTransactedSessions(msgVpnName, clientName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ClientApi#getMsgVpnClientTransactedSessions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **clientName** | **String**| The name of the Client. |
 **count** | **Integer**| Limit the count of objects in the response. See the documentation for the &#x60;count&#x60; parameter. | [optional] [default to 10]
 **cursor** | **String**| The cursor, or position, for the next page of objects. See the documentation for the &#x60;cursor&#x60; parameter. | [optional]
 **where** | [**List&lt;String&gt;**](String.md)| Include in the response only objects where certain conditions are true. See the the documentation for the &#x60;where&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnClientTransactedSessionsResponse**](MsgVpnClientTransactedSessionsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnClients"></a>
# **getMsgVpnClients**
> MsgVpnClientsResponse getMsgVpnClients(msgVpnName, count, cursor, where, select)

Get a list of Client objects.

Get a list of Client objects.  Applications or devices that connect to message brokers to send and/or receive messages are represented as Clients.   Attribute|Identifying|Deprecated :---|:---:|:---: clientName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ClientApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ClientApi apiInstance = new ClientApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnClientsResponse result = apiInstance.getMsgVpnClients(msgVpnName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ClientApi#getMsgVpnClients");
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

[**MsgVpnClientsResponse**](MsgVpnClientsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

