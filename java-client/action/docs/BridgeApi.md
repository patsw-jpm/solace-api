# BridgeApi

All URIs are relative to *http://www.solace.com/SEMP/v2/action*

Method | HTTP request | Description
------------- | ------------- | -------------
[**doMsgVpnBridgeClearEvent**](BridgeApi.md#doMsgVpnBridgeClearEvent) | **PUT** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/clearEvent | Clear an event for the Bridge so it can be generated anew.
[**doMsgVpnBridgeClearStats**](BridgeApi.md#doMsgVpnBridgeClearStats) | **PUT** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/clearStats | Clear the statistics for the Bridge.
[**doMsgVpnBridgeDisconnect**](BridgeApi.md#doMsgVpnBridgeDisconnect) | **PUT** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/disconnect | Disconnect the Bridge.
[**getMsgVpnBridge**](BridgeApi.md#getMsgVpnBridge) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter} | Get a Bridge object.
[**getMsgVpnBridges**](BridgeApi.md#getMsgVpnBridges) | **GET** /msgVpns/{msgVpnName}/bridges | Get a list of Bridge objects.


<a name="doMsgVpnBridgeClearEvent"></a>
# **doMsgVpnBridgeClearEvent**
> SempMetaOnlyResponse doMsgVpnBridgeClearEvent(msgVpnName, bridgeName, bridgeVirtualRouter, body)

Clear an event for the Bridge so it can be generated anew.

Clear an event for the Bridge so it can be generated anew.   Attribute|Required|Deprecated :---|:---:|:---: eventName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.BridgeApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

BridgeApi apiInstance = new BridgeApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String bridgeName = "bridgeName_example"; // String | The name of the Bridge.
String bridgeVirtualRouter = "bridgeVirtualRouter_example"; // String | The virtual router of the Bridge.
MsgVpnBridgeClearEvent body = new MsgVpnBridgeClearEvent(); // MsgVpnBridgeClearEvent | The Clear Event action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnBridgeClearEvent(msgVpnName, bridgeName, bridgeVirtualRouter, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BridgeApi#doMsgVpnBridgeClearEvent");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **bridgeName** | **String**| The name of the Bridge. |
 **bridgeVirtualRouter** | **String**| The virtual router of the Bridge. |
 **body** | [**MsgVpnBridgeClearEvent**](MsgVpnBridgeClearEvent.md)| The Clear Event action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnBridgeClearStats"></a>
# **doMsgVpnBridgeClearStats**
> SempMetaOnlyResponse doMsgVpnBridgeClearStats(msgVpnName, bridgeName, bridgeVirtualRouter, body)

Clear the statistics for the Bridge.

Clear the statistics for the Bridge.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.BridgeApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

BridgeApi apiInstance = new BridgeApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String bridgeName = "bridgeName_example"; // String | The name of the Bridge.
String bridgeVirtualRouter = "bridgeVirtualRouter_example"; // String | The virtual router of the Bridge.
MsgVpnBridgeClearStats body = new MsgVpnBridgeClearStats(); // MsgVpnBridgeClearStats | The Clear Stats action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnBridgeClearStats(msgVpnName, bridgeName, bridgeVirtualRouter, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BridgeApi#doMsgVpnBridgeClearStats");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **bridgeName** | **String**| The name of the Bridge. |
 **bridgeVirtualRouter** | **String**| The virtual router of the Bridge. |
 **body** | [**MsgVpnBridgeClearStats**](MsgVpnBridgeClearStats.md)| The Clear Stats action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnBridgeDisconnect"></a>
# **doMsgVpnBridgeDisconnect**
> SempMetaOnlyResponse doMsgVpnBridgeDisconnect(msgVpnName, bridgeName, bridgeVirtualRouter, body)

Disconnect the Bridge.

Disconnect the Bridge.    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.BridgeApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

BridgeApi apiInstance = new BridgeApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String bridgeName = "bridgeName_example"; // String | The name of the Bridge.
String bridgeVirtualRouter = "bridgeVirtualRouter_example"; // String | The virtual router of the Bridge.
MsgVpnBridgeDisconnect body = new MsgVpnBridgeDisconnect(); // MsgVpnBridgeDisconnect | The Disconnect action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnBridgeDisconnect(msgVpnName, bridgeName, bridgeVirtualRouter, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BridgeApi#doMsgVpnBridgeDisconnect");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **bridgeName** | **String**| The name of the Bridge. |
 **bridgeVirtualRouter** | **String**| The virtual router of the Bridge. |
 **body** | [**MsgVpnBridgeDisconnect**](MsgVpnBridgeDisconnect.md)| The Disconnect action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnBridge"></a>
# **getMsgVpnBridge**
> MsgVpnBridgeResponse getMsgVpnBridge(msgVpnName, bridgeName, bridgeVirtualRouter, select)

Get a Bridge object.

Get a Bridge object.  Bridges can be used to link two Message VPNs so that messages published to one Message VPN that match the topic subscriptions set for the bridge are also delivered to the linked Message VPN.   Attribute|Identifying|Deprecated :---|:---:|:---: bridgeName|x| bridgeVirtualRouter|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.BridgeApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

BridgeApi apiInstance = new BridgeApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String bridgeName = "bridgeName_example"; // String | The name of the Bridge.
String bridgeVirtualRouter = "bridgeVirtualRouter_example"; // String | The virtual router of the Bridge.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnBridgeResponse result = apiInstance.getMsgVpnBridge(msgVpnName, bridgeName, bridgeVirtualRouter, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BridgeApi#getMsgVpnBridge");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **bridgeName** | **String**| The name of the Bridge. |
 **bridgeVirtualRouter** | **String**| The virtual router of the Bridge. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnBridgeResponse**](MsgVpnBridgeResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnBridges"></a>
# **getMsgVpnBridges**
> MsgVpnBridgesResponse getMsgVpnBridges(msgVpnName, count, cursor, where, select)

Get a list of Bridge objects.

Get a list of Bridge objects.  Bridges can be used to link two Message VPNs so that messages published to one Message VPN that match the topic subscriptions set for the bridge are also delivered to the linked Message VPN.   Attribute|Identifying|Deprecated :---|:---:|:---: bridgeName|x| bridgeVirtualRouter|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.BridgeApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

BridgeApi apiInstance = new BridgeApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnBridgesResponse result = apiInstance.getMsgVpnBridges(msgVpnName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BridgeApi#getMsgVpnBridges");
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

[**MsgVpnBridgesResponse**](MsgVpnBridgesResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

