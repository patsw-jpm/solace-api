# ConfigSyncRemoteNodeApi

All URIs are relative to *http://www.solace.com/SEMP/v2/monitor*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getMsgVpnConfigSyncRemoteNode**](ConfigSyncRemoteNodeApi.md#getMsgVpnConfigSyncRemoteNode) | **GET** /msgVpns/{msgVpnName}/configSyncRemoteNodes/{remoteNodeName} | Get a Config Sync Remote Node object.
[**getMsgVpnConfigSyncRemoteNodes**](ConfigSyncRemoteNodeApi.md#getMsgVpnConfigSyncRemoteNodes) | **GET** /msgVpns/{msgVpnName}/configSyncRemoteNodes | Get a list of Config Sync Remote Node objects.


<a name="getMsgVpnConfigSyncRemoteNode"></a>
# **getMsgVpnConfigSyncRemoteNode**
> MsgVpnConfigSyncRemoteNodeResponse getMsgVpnConfigSyncRemoteNode(msgVpnName, remoteNodeName, select)

Get a Config Sync Remote Node object.

Get a Config Sync Remote Node object.  A Config Sync Remote Node object contains information about the status of the table for this Message VPN with respect to a remote node.   Attribute|Identifying|Deprecated :---|:---:|:---: lastMsgRxTime||x msgVpnName|x|x remoteNodeName|x|x role||x stale||x state||x timeInState||x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.22. This attribute has been deprecated.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ConfigSyncRemoteNodeApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ConfigSyncRemoteNodeApi apiInstance = new ConfigSyncRemoteNodeApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String remoteNodeName = "remoteNodeName_example"; // String | The name of the Config Sync Remote Node.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnConfigSyncRemoteNodeResponse result = apiInstance.getMsgVpnConfigSyncRemoteNode(msgVpnName, remoteNodeName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfigSyncRemoteNodeApi#getMsgVpnConfigSyncRemoteNode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **remoteNodeName** | **String**| The name of the Config Sync Remote Node. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnConfigSyncRemoteNodeResponse**](MsgVpnConfigSyncRemoteNodeResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnConfigSyncRemoteNodes"></a>
# **getMsgVpnConfigSyncRemoteNodes**
> MsgVpnConfigSyncRemoteNodesResponse getMsgVpnConfigSyncRemoteNodes(msgVpnName, count, cursor, where, select)

Get a list of Config Sync Remote Node objects.

Get a list of Config Sync Remote Node objects.  A Config Sync Remote Node object contains information about the status of the table for this Message VPN with respect to a remote node.   Attribute|Identifying|Deprecated :---|:---:|:---: lastMsgRxTime||x msgVpnName|x|x remoteNodeName|x|x role||x stale||x state||x timeInState||x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.22. This attribute has been deprecated.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ConfigSyncRemoteNodeApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ConfigSyncRemoteNodeApi apiInstance = new ConfigSyncRemoteNodeApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnConfigSyncRemoteNodesResponse result = apiInstance.getMsgVpnConfigSyncRemoteNodes(msgVpnName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfigSyncRemoteNodeApi#getMsgVpnConfigSyncRemoteNodes");
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

[**MsgVpnConfigSyncRemoteNodesResponse**](MsgVpnConfigSyncRemoteNodesResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

