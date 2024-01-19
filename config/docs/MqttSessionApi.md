# MqttSessionApi

All URIs are relative to *http://www.solace.com/SEMP/v2/config*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createMsgVpnMqttSession**](MqttSessionApi.md#createMsgVpnMqttSession) | **POST** /msgVpns/{msgVpnName}/mqttSessions | Create an MQTT Session object.
[**createMsgVpnMqttSessionSubscription**](MqttSessionApi.md#createMsgVpnMqttSessionSubscription) | **POST** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter}/subscriptions | Create a Subscription object.
[**deleteMsgVpnMqttSession**](MqttSessionApi.md#deleteMsgVpnMqttSession) | **DELETE** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter} | Delete an MQTT Session object.
[**deleteMsgVpnMqttSessionSubscription**](MqttSessionApi.md#deleteMsgVpnMqttSessionSubscription) | **DELETE** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter}/subscriptions/{subscriptionTopic} | Delete a Subscription object.
[**getMsgVpnMqttSession**](MqttSessionApi.md#getMsgVpnMqttSession) | **GET** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter} | Get an MQTT Session object.
[**getMsgVpnMqttSessionSubscription**](MqttSessionApi.md#getMsgVpnMqttSessionSubscription) | **GET** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter}/subscriptions/{subscriptionTopic} | Get a Subscription object.
[**getMsgVpnMqttSessionSubscriptions**](MqttSessionApi.md#getMsgVpnMqttSessionSubscriptions) | **GET** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter}/subscriptions | Get a list of Subscription objects.
[**getMsgVpnMqttSessions**](MqttSessionApi.md#getMsgVpnMqttSessions) | **GET** /msgVpns/{msgVpnName}/mqttSessions | Get a list of MQTT Session objects.
[**replaceMsgVpnMqttSession**](MqttSessionApi.md#replaceMsgVpnMqttSession) | **PUT** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter} | Replace an MQTT Session object.
[**replaceMsgVpnMqttSessionSubscription**](MqttSessionApi.md#replaceMsgVpnMqttSessionSubscription) | **PUT** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter}/subscriptions/{subscriptionTopic} | Replace a Subscription object.
[**updateMsgVpnMqttSession**](MqttSessionApi.md#updateMsgVpnMqttSession) | **PATCH** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter} | Update an MQTT Session object.
[**updateMsgVpnMqttSessionSubscription**](MqttSessionApi.md#updateMsgVpnMqttSessionSubscription) | **PATCH** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter}/subscriptions/{subscriptionTopic} | Update a Subscription object.


<a name="createMsgVpnMqttSession"></a>
# **createMsgVpnMqttSession**
> MsgVpnMqttSessionResponse createMsgVpnMqttSession(msgVpnName, body, opaquePassword, select)

Create an MQTT Session object.

Create an MQTT Session object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  An MQTT Session object is a virtual representation of an MQTT client connection. An MQTT session holds the state of an MQTT client (that is, it is used to contain a client&#39;s QoS 0 and QoS 1 subscription sets and any undelivered QoS 1 messages).   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: mqttSessionClientId|x|x|||| mqttSessionVirtualRouter|x|x|||| msgVpnName|x||x|||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.MqttSessionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

MqttSessionApi apiInstance = new MqttSessionApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
MsgVpnMqttSession body = new MsgVpnMqttSession(); // MsgVpnMqttSession | The MQTT Session object's attributes.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnMqttSessionResponse result = apiInstance.createMsgVpnMqttSession(msgVpnName, body, opaquePassword, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MqttSessionApi#createMsgVpnMqttSession");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **body** | [**MsgVpnMqttSession**](MsgVpnMqttSession.md)| The MQTT Session object&#39;s attributes. |
 **opaquePassword** | **String**| Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the &#x60;opaquePassword&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnMqttSessionResponse**](MsgVpnMqttSessionResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createMsgVpnMqttSessionSubscription"></a>
# **createMsgVpnMqttSessionSubscription**
> MsgVpnMqttSessionSubscriptionResponse createMsgVpnMqttSessionSubscription(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, body, opaquePassword, select)

Create a Subscription object.

Create a Subscription object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  An MQTT session contains a client&#39;s QoS 0 and QoS 1 subscription sets. On creation, a subscription defaults to QoS 0.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: mqttSessionClientId|x||x||| mqttSessionVirtualRouter|x||x||| msgVpnName|x||x||| subscriptionTopic|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.MqttSessionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

MqttSessionApi apiInstance = new MqttSessionApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String mqttSessionClientId = "mqttSessionClientId_example"; // String | The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet.
String mqttSessionVirtualRouter = "mqttSessionVirtualRouter_example"; // String | The virtual router of the MQTT Session.
MsgVpnMqttSessionSubscription body = new MsgVpnMqttSessionSubscription(); // MsgVpnMqttSessionSubscription | The Subscription object's attributes.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnMqttSessionSubscriptionResponse result = apiInstance.createMsgVpnMqttSessionSubscription(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, body, opaquePassword, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MqttSessionApi#createMsgVpnMqttSessionSubscription");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **mqttSessionClientId** | **String**| The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet. |
 **mqttSessionVirtualRouter** | **String**| The virtual router of the MQTT Session. |
 **body** | [**MsgVpnMqttSessionSubscription**](MsgVpnMqttSessionSubscription.md)| The Subscription object&#39;s attributes. |
 **opaquePassword** | **String**| Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the &#x60;opaquePassword&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnMqttSessionSubscriptionResponse**](MsgVpnMqttSessionSubscriptionResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteMsgVpnMqttSession"></a>
# **deleteMsgVpnMqttSession**
> SempMetaOnlyResponse deleteMsgVpnMqttSession(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter)

Delete an MQTT Session object.

Delete an MQTT Session object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  An MQTT Session object is a virtual representation of an MQTT client connection. An MQTT session holds the state of an MQTT client (that is, it is used to contain a client&#39;s QoS 0 and QoS 1 subscription sets and any undelivered QoS 1 messages).  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.MqttSessionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

MqttSessionApi apiInstance = new MqttSessionApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String mqttSessionClientId = "mqttSessionClientId_example"; // String | The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet.
String mqttSessionVirtualRouter = "mqttSessionVirtualRouter_example"; // String | The virtual router of the MQTT Session.
try {
    SempMetaOnlyResponse result = apiInstance.deleteMsgVpnMqttSession(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MqttSessionApi#deleteMsgVpnMqttSession");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **mqttSessionClientId** | **String**| The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet. |
 **mqttSessionVirtualRouter** | **String**| The virtual router of the MQTT Session. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteMsgVpnMqttSessionSubscription"></a>
# **deleteMsgVpnMqttSessionSubscription**
> SempMetaOnlyResponse deleteMsgVpnMqttSessionSubscription(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, subscriptionTopic)

Delete a Subscription object.

Delete a Subscription object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  An MQTT session contains a client&#39;s QoS 0 and QoS 1 subscription sets. On creation, a subscription defaults to QoS 0.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.MqttSessionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

MqttSessionApi apiInstance = new MqttSessionApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String mqttSessionClientId = "mqttSessionClientId_example"; // String | The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet.
String mqttSessionVirtualRouter = "mqttSessionVirtualRouter_example"; // String | The virtual router of the MQTT Session.
String subscriptionTopic = "subscriptionTopic_example"; // String | The MQTT subscription topic.
try {
    SempMetaOnlyResponse result = apiInstance.deleteMsgVpnMqttSessionSubscription(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, subscriptionTopic);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MqttSessionApi#deleteMsgVpnMqttSessionSubscription");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **mqttSessionClientId** | **String**| The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet. |
 **mqttSessionVirtualRouter** | **String**| The virtual router of the MQTT Session. |
 **subscriptionTopic** | **String**| The MQTT subscription topic. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnMqttSession"></a>
# **getMsgVpnMqttSession**
> MsgVpnMqttSessionResponse getMsgVpnMqttSession(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, opaquePassword, select)

Get an MQTT Session object.

Get an MQTT Session object.  An MQTT Session object is a virtual representation of an MQTT client connection. An MQTT session holds the state of an MQTT client (that is, it is used to contain a client&#39;s QoS 0 and QoS 1 subscription sets and any undelivered QoS 1 messages).   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: mqttSessionClientId|x||| mqttSessionVirtualRouter|x||| msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.MqttSessionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

MqttSessionApi apiInstance = new MqttSessionApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String mqttSessionClientId = "mqttSessionClientId_example"; // String | The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet.
String mqttSessionVirtualRouter = "mqttSessionVirtualRouter_example"; // String | The virtual router of the MQTT Session.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnMqttSessionResponse result = apiInstance.getMsgVpnMqttSession(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, opaquePassword, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MqttSessionApi#getMsgVpnMqttSession");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **mqttSessionClientId** | **String**| The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet. |
 **mqttSessionVirtualRouter** | **String**| The virtual router of the MQTT Session. |
 **opaquePassword** | **String**| Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the &#x60;opaquePassword&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnMqttSessionResponse**](MsgVpnMqttSessionResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnMqttSessionSubscription"></a>
# **getMsgVpnMqttSessionSubscription**
> MsgVpnMqttSessionSubscriptionResponse getMsgVpnMqttSessionSubscription(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, subscriptionTopic, opaquePassword, select)

Get a Subscription object.

Get a Subscription object.  An MQTT session contains a client&#39;s QoS 0 and QoS 1 subscription sets. On creation, a subscription defaults to QoS 0.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: mqttSessionClientId|x||| mqttSessionVirtualRouter|x||| msgVpnName|x||| subscriptionTopic|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.MqttSessionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

MqttSessionApi apiInstance = new MqttSessionApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String mqttSessionClientId = "mqttSessionClientId_example"; // String | The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet.
String mqttSessionVirtualRouter = "mqttSessionVirtualRouter_example"; // String | The virtual router of the MQTT Session.
String subscriptionTopic = "subscriptionTopic_example"; // String | The MQTT subscription topic.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnMqttSessionSubscriptionResponse result = apiInstance.getMsgVpnMqttSessionSubscription(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, subscriptionTopic, opaquePassword, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MqttSessionApi#getMsgVpnMqttSessionSubscription");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **mqttSessionClientId** | **String**| The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet. |
 **mqttSessionVirtualRouter** | **String**| The virtual router of the MQTT Session. |
 **subscriptionTopic** | **String**| The MQTT subscription topic. |
 **opaquePassword** | **String**| Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the &#x60;opaquePassword&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnMqttSessionSubscriptionResponse**](MsgVpnMqttSessionSubscriptionResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnMqttSessionSubscriptions"></a>
# **getMsgVpnMqttSessionSubscriptions**
> MsgVpnMqttSessionSubscriptionsResponse getMsgVpnMqttSessionSubscriptions(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, count, cursor, opaquePassword, where, select)

Get a list of Subscription objects.

Get a list of Subscription objects.  An MQTT session contains a client&#39;s QoS 0 and QoS 1 subscription sets. On creation, a subscription defaults to QoS 0.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: mqttSessionClientId|x||| mqttSessionVirtualRouter|x||| msgVpnName|x||| subscriptionTopic|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.MqttSessionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

MqttSessionApi apiInstance = new MqttSessionApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String mqttSessionClientId = "mqttSessionClientId_example"; // String | The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet.
String mqttSessionVirtualRouter = "mqttSessionVirtualRouter_example"; // String | The virtual router of the MQTT Session.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnMqttSessionSubscriptionsResponse result = apiInstance.getMsgVpnMqttSessionSubscriptions(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, count, cursor, opaquePassword, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MqttSessionApi#getMsgVpnMqttSessionSubscriptions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **mqttSessionClientId** | **String**| The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet. |
 **mqttSessionVirtualRouter** | **String**| The virtual router of the MQTT Session. |
 **count** | **Integer**| Limit the count of objects in the response. See the documentation for the &#x60;count&#x60; parameter. | [optional] [default to 10]
 **cursor** | **String**| The cursor, or position, for the next page of objects. See the documentation for the &#x60;cursor&#x60; parameter. | [optional]
 **opaquePassword** | **String**| Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the &#x60;opaquePassword&#x60; parameter. | [optional]
 **where** | [**List&lt;String&gt;**](String.md)| Include in the response only objects where certain conditions are true. See the the documentation for the &#x60;where&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnMqttSessionSubscriptionsResponse**](MsgVpnMqttSessionSubscriptionsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnMqttSessions"></a>
# **getMsgVpnMqttSessions**
> MsgVpnMqttSessionsResponse getMsgVpnMqttSessions(msgVpnName, count, cursor, opaquePassword, where, select)

Get a list of MQTT Session objects.

Get a list of MQTT Session objects.  An MQTT Session object is a virtual representation of an MQTT client connection. An MQTT session holds the state of an MQTT client (that is, it is used to contain a client&#39;s QoS 0 and QoS 1 subscription sets and any undelivered QoS 1 messages).   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: mqttSessionClientId|x||| mqttSessionVirtualRouter|x||| msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.MqttSessionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

MqttSessionApi apiInstance = new MqttSessionApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnMqttSessionsResponse result = apiInstance.getMsgVpnMqttSessions(msgVpnName, count, cursor, opaquePassword, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MqttSessionApi#getMsgVpnMqttSessions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **count** | **Integer**| Limit the count of objects in the response. See the documentation for the &#x60;count&#x60; parameter. | [optional] [default to 10]
 **cursor** | **String**| The cursor, or position, for the next page of objects. See the documentation for the &#x60;cursor&#x60; parameter. | [optional]
 **opaquePassword** | **String**| Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the &#x60;opaquePassword&#x60; parameter. | [optional]
 **where** | [**List&lt;String&gt;**](String.md)| Include in the response only objects where certain conditions are true. See the the documentation for the &#x60;where&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnMqttSessionsResponse**](MsgVpnMqttSessionsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="replaceMsgVpnMqttSession"></a>
# **replaceMsgVpnMqttSession**
> MsgVpnMqttSessionResponse replaceMsgVpnMqttSession(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, body, opaquePassword, select)

Replace an MQTT Session object.

Replace an MQTT Session object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  An MQTT Session object is a virtual representation of an MQTT client connection. An MQTT session holds the state of an MQTT client (that is, it is used to contain a client&#39;s QoS 0 and QoS 1 subscription sets and any undelivered QoS 1 messages).   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: mqttSessionClientId|x|x|||| mqttSessionVirtualRouter|x|x|||| msgVpnName|x|x|||| owner||||x||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.MqttSessionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

MqttSessionApi apiInstance = new MqttSessionApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String mqttSessionClientId = "mqttSessionClientId_example"; // String | The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet.
String mqttSessionVirtualRouter = "mqttSessionVirtualRouter_example"; // String | The virtual router of the MQTT Session.
MsgVpnMqttSession body = new MsgVpnMqttSession(); // MsgVpnMqttSession | The MQTT Session object's attributes.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnMqttSessionResponse result = apiInstance.replaceMsgVpnMqttSession(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, body, opaquePassword, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MqttSessionApi#replaceMsgVpnMqttSession");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **mqttSessionClientId** | **String**| The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet. |
 **mqttSessionVirtualRouter** | **String**| The virtual router of the MQTT Session. |
 **body** | [**MsgVpnMqttSession**](MsgVpnMqttSession.md)| The MQTT Session object&#39;s attributes. |
 **opaquePassword** | **String**| Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the &#x60;opaquePassword&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnMqttSessionResponse**](MsgVpnMqttSessionResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="replaceMsgVpnMqttSessionSubscription"></a>
# **replaceMsgVpnMqttSessionSubscription**
> MsgVpnMqttSessionSubscriptionResponse replaceMsgVpnMqttSessionSubscription(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, subscriptionTopic, body, opaquePassword, select)

Replace a Subscription object.

Replace a Subscription object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  An MQTT session contains a client&#39;s QoS 0 and QoS 1 subscription sets. On creation, a subscription defaults to QoS 0.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: mqttSessionClientId|x|x|||| mqttSessionVirtualRouter|x|x|||| msgVpnName|x|x|||| subscriptionTopic|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.MqttSessionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

MqttSessionApi apiInstance = new MqttSessionApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String mqttSessionClientId = "mqttSessionClientId_example"; // String | The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet.
String mqttSessionVirtualRouter = "mqttSessionVirtualRouter_example"; // String | The virtual router of the MQTT Session.
String subscriptionTopic = "subscriptionTopic_example"; // String | The MQTT subscription topic.
MsgVpnMqttSessionSubscription body = new MsgVpnMqttSessionSubscription(); // MsgVpnMqttSessionSubscription | The Subscription object's attributes.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnMqttSessionSubscriptionResponse result = apiInstance.replaceMsgVpnMqttSessionSubscription(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, subscriptionTopic, body, opaquePassword, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MqttSessionApi#replaceMsgVpnMqttSessionSubscription");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **mqttSessionClientId** | **String**| The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet. |
 **mqttSessionVirtualRouter** | **String**| The virtual router of the MQTT Session. |
 **subscriptionTopic** | **String**| The MQTT subscription topic. |
 **body** | [**MsgVpnMqttSessionSubscription**](MsgVpnMqttSessionSubscription.md)| The Subscription object&#39;s attributes. |
 **opaquePassword** | **String**| Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the &#x60;opaquePassword&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnMqttSessionSubscriptionResponse**](MsgVpnMqttSessionSubscriptionResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateMsgVpnMqttSession"></a>
# **updateMsgVpnMqttSession**
> MsgVpnMqttSessionResponse updateMsgVpnMqttSession(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, body, opaquePassword, select)

Update an MQTT Session object.

Update an MQTT Session object. Any attribute missing from the request will be left unchanged.  An MQTT Session object is a virtual representation of an MQTT client connection. An MQTT session holds the state of an MQTT client (that is, it is used to contain a client&#39;s QoS 0 and QoS 1 subscription sets and any undelivered QoS 1 messages).   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: mqttSessionClientId|x|x|||| mqttSessionVirtualRouter|x|x|||| msgVpnName|x|x|||| owner||||x||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.MqttSessionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

MqttSessionApi apiInstance = new MqttSessionApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String mqttSessionClientId = "mqttSessionClientId_example"; // String | The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet.
String mqttSessionVirtualRouter = "mqttSessionVirtualRouter_example"; // String | The virtual router of the MQTT Session.
MsgVpnMqttSession body = new MsgVpnMqttSession(); // MsgVpnMqttSession | The MQTT Session object's attributes.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnMqttSessionResponse result = apiInstance.updateMsgVpnMqttSession(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, body, opaquePassword, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MqttSessionApi#updateMsgVpnMqttSession");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **mqttSessionClientId** | **String**| The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet. |
 **mqttSessionVirtualRouter** | **String**| The virtual router of the MQTT Session. |
 **body** | [**MsgVpnMqttSession**](MsgVpnMqttSession.md)| The MQTT Session object&#39;s attributes. |
 **opaquePassword** | **String**| Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the &#x60;opaquePassword&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnMqttSessionResponse**](MsgVpnMqttSessionResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateMsgVpnMqttSessionSubscription"></a>
# **updateMsgVpnMqttSessionSubscription**
> MsgVpnMqttSessionSubscriptionResponse updateMsgVpnMqttSessionSubscription(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, subscriptionTopic, body, opaquePassword, select)

Update a Subscription object.

Update a Subscription object. Any attribute missing from the request will be left unchanged.  An MQTT session contains a client&#39;s QoS 0 and QoS 1 subscription sets. On creation, a subscription defaults to QoS 0.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: mqttSessionClientId|x|x|||| mqttSessionVirtualRouter|x|x|||| msgVpnName|x|x|||| subscriptionTopic|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.MqttSessionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

MqttSessionApi apiInstance = new MqttSessionApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String mqttSessionClientId = "mqttSessionClientId_example"; // String | The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet.
String mqttSessionVirtualRouter = "mqttSessionVirtualRouter_example"; // String | The virtual router of the MQTT Session.
String subscriptionTopic = "subscriptionTopic_example"; // String | The MQTT subscription topic.
MsgVpnMqttSessionSubscription body = new MsgVpnMqttSessionSubscription(); // MsgVpnMqttSessionSubscription | The Subscription object's attributes.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnMqttSessionSubscriptionResponse result = apiInstance.updateMsgVpnMqttSessionSubscription(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, subscriptionTopic, body, opaquePassword, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MqttSessionApi#updateMsgVpnMqttSessionSubscription");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **mqttSessionClientId** | **String**| The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet. |
 **mqttSessionVirtualRouter** | **String**| The virtual router of the MQTT Session. |
 **subscriptionTopic** | **String**| The MQTT subscription topic. |
 **body** | [**MsgVpnMqttSessionSubscription**](MsgVpnMqttSessionSubscription.md)| The Subscription object&#39;s attributes. |
 **opaquePassword** | **String**| Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the &#x60;opaquePassword&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnMqttSessionSubscriptionResponse**](MsgVpnMqttSessionSubscriptionResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

