# JndiApi

All URIs are relative to *http://www.solace.com/SEMP/v2/config*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createMsgVpnJndiConnectionFactory**](JndiApi.md#createMsgVpnJndiConnectionFactory) | **POST** /msgVpns/{msgVpnName}/jndiConnectionFactories | Create a JNDI Connection Factory object.
[**createMsgVpnJndiQueue**](JndiApi.md#createMsgVpnJndiQueue) | **POST** /msgVpns/{msgVpnName}/jndiQueues | Create a JNDI Queue object.
[**createMsgVpnJndiTopic**](JndiApi.md#createMsgVpnJndiTopic) | **POST** /msgVpns/{msgVpnName}/jndiTopics | Create a JNDI Topic object.
[**deleteMsgVpnJndiConnectionFactory**](JndiApi.md#deleteMsgVpnJndiConnectionFactory) | **DELETE** /msgVpns/{msgVpnName}/jndiConnectionFactories/{connectionFactoryName} | Delete a JNDI Connection Factory object.
[**deleteMsgVpnJndiQueue**](JndiApi.md#deleteMsgVpnJndiQueue) | **DELETE** /msgVpns/{msgVpnName}/jndiQueues/{queueName} | Delete a JNDI Queue object.
[**deleteMsgVpnJndiTopic**](JndiApi.md#deleteMsgVpnJndiTopic) | **DELETE** /msgVpns/{msgVpnName}/jndiTopics/{topicName} | Delete a JNDI Topic object.
[**getMsgVpnJndiConnectionFactories**](JndiApi.md#getMsgVpnJndiConnectionFactories) | **GET** /msgVpns/{msgVpnName}/jndiConnectionFactories | Get a list of JNDI Connection Factory objects.
[**getMsgVpnJndiConnectionFactory**](JndiApi.md#getMsgVpnJndiConnectionFactory) | **GET** /msgVpns/{msgVpnName}/jndiConnectionFactories/{connectionFactoryName} | Get a JNDI Connection Factory object.
[**getMsgVpnJndiQueue**](JndiApi.md#getMsgVpnJndiQueue) | **GET** /msgVpns/{msgVpnName}/jndiQueues/{queueName} | Get a JNDI Queue object.
[**getMsgVpnJndiQueues**](JndiApi.md#getMsgVpnJndiQueues) | **GET** /msgVpns/{msgVpnName}/jndiQueues | Get a list of JNDI Queue objects.
[**getMsgVpnJndiTopic**](JndiApi.md#getMsgVpnJndiTopic) | **GET** /msgVpns/{msgVpnName}/jndiTopics/{topicName} | Get a JNDI Topic object.
[**getMsgVpnJndiTopics**](JndiApi.md#getMsgVpnJndiTopics) | **GET** /msgVpns/{msgVpnName}/jndiTopics | Get a list of JNDI Topic objects.
[**replaceMsgVpnJndiConnectionFactory**](JndiApi.md#replaceMsgVpnJndiConnectionFactory) | **PUT** /msgVpns/{msgVpnName}/jndiConnectionFactories/{connectionFactoryName} | Replace a JNDI Connection Factory object.
[**replaceMsgVpnJndiQueue**](JndiApi.md#replaceMsgVpnJndiQueue) | **PUT** /msgVpns/{msgVpnName}/jndiQueues/{queueName} | Replace a JNDI Queue object.
[**replaceMsgVpnJndiTopic**](JndiApi.md#replaceMsgVpnJndiTopic) | **PUT** /msgVpns/{msgVpnName}/jndiTopics/{topicName} | Replace a JNDI Topic object.
[**updateMsgVpnJndiConnectionFactory**](JndiApi.md#updateMsgVpnJndiConnectionFactory) | **PATCH** /msgVpns/{msgVpnName}/jndiConnectionFactories/{connectionFactoryName} | Update a JNDI Connection Factory object.
[**updateMsgVpnJndiQueue**](JndiApi.md#updateMsgVpnJndiQueue) | **PATCH** /msgVpns/{msgVpnName}/jndiQueues/{queueName} | Update a JNDI Queue object.
[**updateMsgVpnJndiTopic**](JndiApi.md#updateMsgVpnJndiTopic) | **PATCH** /msgVpns/{msgVpnName}/jndiTopics/{topicName} | Update a JNDI Topic object.


<a name="createMsgVpnJndiConnectionFactory"></a>
# **createMsgVpnJndiConnectionFactory**
> MsgVpnJndiConnectionFactoryResponse createMsgVpnJndiConnectionFactory(msgVpnName, body, opaquePassword, select)

Create a JNDI Connection Factory object.

Create a JNDI Connection Factory object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  The message broker provides an internal JNDI store for provisioned Connection Factory objects that clients can access through JNDI lookups.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: connectionFactoryName|x|x|||| msgVpnName|x||x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.JndiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

JndiApi apiInstance = new JndiApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
MsgVpnJndiConnectionFactory body = new MsgVpnJndiConnectionFactory(); // MsgVpnJndiConnectionFactory | The JNDI Connection Factory object's attributes.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnJndiConnectionFactoryResponse result = apiInstance.createMsgVpnJndiConnectionFactory(msgVpnName, body, opaquePassword, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JndiApi#createMsgVpnJndiConnectionFactory");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **body** | [**MsgVpnJndiConnectionFactory**](MsgVpnJndiConnectionFactory.md)| The JNDI Connection Factory object&#39;s attributes. |
 **opaquePassword** | **String**| Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the &#x60;opaquePassword&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnJndiConnectionFactoryResponse**](MsgVpnJndiConnectionFactoryResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createMsgVpnJndiQueue"></a>
# **createMsgVpnJndiQueue**
> MsgVpnJndiQueueResponse createMsgVpnJndiQueue(msgVpnName, body, opaquePassword, select)

Create a JNDI Queue object.

Create a JNDI Queue object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  The message broker provides an internal JNDI store for provisioned Queue objects that clients can access through JNDI lookups.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x||x||| queueName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.JndiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

JndiApi apiInstance = new JndiApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
MsgVpnJndiQueue body = new MsgVpnJndiQueue(); // MsgVpnJndiQueue | The JNDI Queue object's attributes.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnJndiQueueResponse result = apiInstance.createMsgVpnJndiQueue(msgVpnName, body, opaquePassword, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JndiApi#createMsgVpnJndiQueue");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **body** | [**MsgVpnJndiQueue**](MsgVpnJndiQueue.md)| The JNDI Queue object&#39;s attributes. |
 **opaquePassword** | **String**| Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the &#x60;opaquePassword&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnJndiQueueResponse**](MsgVpnJndiQueueResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createMsgVpnJndiTopic"></a>
# **createMsgVpnJndiTopic**
> MsgVpnJndiTopicResponse createMsgVpnJndiTopic(msgVpnName, body, opaquePassword, select)

Create a JNDI Topic object.

Create a JNDI Topic object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  The message broker provides an internal JNDI store for provisioned Topic objects that clients can access through JNDI lookups.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x||x||| topicName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.JndiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

JndiApi apiInstance = new JndiApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
MsgVpnJndiTopic body = new MsgVpnJndiTopic(); // MsgVpnJndiTopic | The JNDI Topic object's attributes.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnJndiTopicResponse result = apiInstance.createMsgVpnJndiTopic(msgVpnName, body, opaquePassword, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JndiApi#createMsgVpnJndiTopic");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **body** | [**MsgVpnJndiTopic**](MsgVpnJndiTopic.md)| The JNDI Topic object&#39;s attributes. |
 **opaquePassword** | **String**| Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the &#x60;opaquePassword&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnJndiTopicResponse**](MsgVpnJndiTopicResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteMsgVpnJndiConnectionFactory"></a>
# **deleteMsgVpnJndiConnectionFactory**
> SempMetaOnlyResponse deleteMsgVpnJndiConnectionFactory(msgVpnName, connectionFactoryName)

Delete a JNDI Connection Factory object.

Delete a JNDI Connection Factory object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  The message broker provides an internal JNDI store for provisioned Connection Factory objects that clients can access through JNDI lookups.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.JndiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

JndiApi apiInstance = new JndiApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String connectionFactoryName = "connectionFactoryName_example"; // String | The name of the JMS Connection Factory.
try {
    SempMetaOnlyResponse result = apiInstance.deleteMsgVpnJndiConnectionFactory(msgVpnName, connectionFactoryName);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JndiApi#deleteMsgVpnJndiConnectionFactory");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **connectionFactoryName** | **String**| The name of the JMS Connection Factory. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteMsgVpnJndiQueue"></a>
# **deleteMsgVpnJndiQueue**
> SempMetaOnlyResponse deleteMsgVpnJndiQueue(msgVpnName, queueName)

Delete a JNDI Queue object.

Delete a JNDI Queue object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  The message broker provides an internal JNDI store for provisioned Queue objects that clients can access through JNDI lookups.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.JndiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

JndiApi apiInstance = new JndiApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String queueName = "queueName_example"; // String | The JNDI name of the JMS Queue.
try {
    SempMetaOnlyResponse result = apiInstance.deleteMsgVpnJndiQueue(msgVpnName, queueName);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JndiApi#deleteMsgVpnJndiQueue");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **queueName** | **String**| The JNDI name of the JMS Queue. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteMsgVpnJndiTopic"></a>
# **deleteMsgVpnJndiTopic**
> SempMetaOnlyResponse deleteMsgVpnJndiTopic(msgVpnName, topicName)

Delete a JNDI Topic object.

Delete a JNDI Topic object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  The message broker provides an internal JNDI store for provisioned Topic objects that clients can access through JNDI lookups.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.JndiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

JndiApi apiInstance = new JndiApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String topicName = "topicName_example"; // String | The JNDI name of the JMS Topic.
try {
    SempMetaOnlyResponse result = apiInstance.deleteMsgVpnJndiTopic(msgVpnName, topicName);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JndiApi#deleteMsgVpnJndiTopic");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **topicName** | **String**| The JNDI name of the JMS Topic. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnJndiConnectionFactories"></a>
# **getMsgVpnJndiConnectionFactories**
> MsgVpnJndiConnectionFactoriesResponse getMsgVpnJndiConnectionFactories(msgVpnName, count, cursor, opaquePassword, where, select)

Get a list of JNDI Connection Factory objects.

Get a list of JNDI Connection Factory objects.  The message broker provides an internal JNDI store for provisioned Connection Factory objects that clients can access through JNDI lookups.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: connectionFactoryName|x||| msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.JndiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

JndiApi apiInstance = new JndiApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnJndiConnectionFactoriesResponse result = apiInstance.getMsgVpnJndiConnectionFactories(msgVpnName, count, cursor, opaquePassword, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JndiApi#getMsgVpnJndiConnectionFactories");
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

[**MsgVpnJndiConnectionFactoriesResponse**](MsgVpnJndiConnectionFactoriesResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnJndiConnectionFactory"></a>
# **getMsgVpnJndiConnectionFactory**
> MsgVpnJndiConnectionFactoryResponse getMsgVpnJndiConnectionFactory(msgVpnName, connectionFactoryName, opaquePassword, select)

Get a JNDI Connection Factory object.

Get a JNDI Connection Factory object.  The message broker provides an internal JNDI store for provisioned Connection Factory objects that clients can access through JNDI lookups.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: connectionFactoryName|x||| msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.JndiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

JndiApi apiInstance = new JndiApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String connectionFactoryName = "connectionFactoryName_example"; // String | The name of the JMS Connection Factory.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnJndiConnectionFactoryResponse result = apiInstance.getMsgVpnJndiConnectionFactory(msgVpnName, connectionFactoryName, opaquePassword, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JndiApi#getMsgVpnJndiConnectionFactory");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **connectionFactoryName** | **String**| The name of the JMS Connection Factory. |
 **opaquePassword** | **String**| Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the &#x60;opaquePassword&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnJndiConnectionFactoryResponse**](MsgVpnJndiConnectionFactoryResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnJndiQueue"></a>
# **getMsgVpnJndiQueue**
> MsgVpnJndiQueueResponse getMsgVpnJndiQueue(msgVpnName, queueName, opaquePassword, select)

Get a JNDI Queue object.

Get a JNDI Queue object.  The message broker provides an internal JNDI store for provisioned Queue objects that clients can access through JNDI lookups.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| queueName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.JndiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

JndiApi apiInstance = new JndiApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String queueName = "queueName_example"; // String | The JNDI name of the JMS Queue.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnJndiQueueResponse result = apiInstance.getMsgVpnJndiQueue(msgVpnName, queueName, opaquePassword, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JndiApi#getMsgVpnJndiQueue");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **queueName** | **String**| The JNDI name of the JMS Queue. |
 **opaquePassword** | **String**| Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the &#x60;opaquePassword&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnJndiQueueResponse**](MsgVpnJndiQueueResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnJndiQueues"></a>
# **getMsgVpnJndiQueues**
> MsgVpnJndiQueuesResponse getMsgVpnJndiQueues(msgVpnName, count, cursor, opaquePassword, where, select)

Get a list of JNDI Queue objects.

Get a list of JNDI Queue objects.  The message broker provides an internal JNDI store for provisioned Queue objects that clients can access through JNDI lookups.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| queueName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.JndiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

JndiApi apiInstance = new JndiApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnJndiQueuesResponse result = apiInstance.getMsgVpnJndiQueues(msgVpnName, count, cursor, opaquePassword, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JndiApi#getMsgVpnJndiQueues");
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

[**MsgVpnJndiQueuesResponse**](MsgVpnJndiQueuesResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnJndiTopic"></a>
# **getMsgVpnJndiTopic**
> MsgVpnJndiTopicResponse getMsgVpnJndiTopic(msgVpnName, topicName, opaquePassword, select)

Get a JNDI Topic object.

Get a JNDI Topic object.  The message broker provides an internal JNDI store for provisioned Topic objects that clients can access through JNDI lookups.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| topicName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.JndiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

JndiApi apiInstance = new JndiApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String topicName = "topicName_example"; // String | The JNDI name of the JMS Topic.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnJndiTopicResponse result = apiInstance.getMsgVpnJndiTopic(msgVpnName, topicName, opaquePassword, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JndiApi#getMsgVpnJndiTopic");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **topicName** | **String**| The JNDI name of the JMS Topic. |
 **opaquePassword** | **String**| Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the &#x60;opaquePassword&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnJndiTopicResponse**](MsgVpnJndiTopicResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnJndiTopics"></a>
# **getMsgVpnJndiTopics**
> MsgVpnJndiTopicsResponse getMsgVpnJndiTopics(msgVpnName, count, cursor, opaquePassword, where, select)

Get a list of JNDI Topic objects.

Get a list of JNDI Topic objects.  The message broker provides an internal JNDI store for provisioned Topic objects that clients can access through JNDI lookups.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| topicName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.JndiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

JndiApi apiInstance = new JndiApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnJndiTopicsResponse result = apiInstance.getMsgVpnJndiTopics(msgVpnName, count, cursor, opaquePassword, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JndiApi#getMsgVpnJndiTopics");
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

[**MsgVpnJndiTopicsResponse**](MsgVpnJndiTopicsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="replaceMsgVpnJndiConnectionFactory"></a>
# **replaceMsgVpnJndiConnectionFactory**
> MsgVpnJndiConnectionFactoryResponse replaceMsgVpnJndiConnectionFactory(msgVpnName, connectionFactoryName, body, opaquePassword, select)

Replace a JNDI Connection Factory object.

Replace a JNDI Connection Factory object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  The message broker provides an internal JNDI store for provisioned Connection Factory objects that clients can access through JNDI lookups.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: connectionFactoryName|x|x|||| msgVpnName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.JndiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

JndiApi apiInstance = new JndiApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String connectionFactoryName = "connectionFactoryName_example"; // String | The name of the JMS Connection Factory.
MsgVpnJndiConnectionFactory body = new MsgVpnJndiConnectionFactory(); // MsgVpnJndiConnectionFactory | The JNDI Connection Factory object's attributes.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnJndiConnectionFactoryResponse result = apiInstance.replaceMsgVpnJndiConnectionFactory(msgVpnName, connectionFactoryName, body, opaquePassword, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JndiApi#replaceMsgVpnJndiConnectionFactory");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **connectionFactoryName** | **String**| The name of the JMS Connection Factory. |
 **body** | [**MsgVpnJndiConnectionFactory**](MsgVpnJndiConnectionFactory.md)| The JNDI Connection Factory object&#39;s attributes. |
 **opaquePassword** | **String**| Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the &#x60;opaquePassword&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnJndiConnectionFactoryResponse**](MsgVpnJndiConnectionFactoryResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="replaceMsgVpnJndiQueue"></a>
# **replaceMsgVpnJndiQueue**
> MsgVpnJndiQueueResponse replaceMsgVpnJndiQueue(msgVpnName, queueName, body, opaquePassword, select)

Replace a JNDI Queue object.

Replace a JNDI Queue object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  The message broker provides an internal JNDI store for provisioned Queue objects that clients can access through JNDI lookups.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x|x|||| queueName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.JndiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

JndiApi apiInstance = new JndiApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String queueName = "queueName_example"; // String | The JNDI name of the JMS Queue.
MsgVpnJndiQueue body = new MsgVpnJndiQueue(); // MsgVpnJndiQueue | The JNDI Queue object's attributes.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnJndiQueueResponse result = apiInstance.replaceMsgVpnJndiQueue(msgVpnName, queueName, body, opaquePassword, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JndiApi#replaceMsgVpnJndiQueue");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **queueName** | **String**| The JNDI name of the JMS Queue. |
 **body** | [**MsgVpnJndiQueue**](MsgVpnJndiQueue.md)| The JNDI Queue object&#39;s attributes. |
 **opaquePassword** | **String**| Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the &#x60;opaquePassword&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnJndiQueueResponse**](MsgVpnJndiQueueResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="replaceMsgVpnJndiTopic"></a>
# **replaceMsgVpnJndiTopic**
> MsgVpnJndiTopicResponse replaceMsgVpnJndiTopic(msgVpnName, topicName, body, opaquePassword, select)

Replace a JNDI Topic object.

Replace a JNDI Topic object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  The message broker provides an internal JNDI store for provisioned Topic objects that clients can access through JNDI lookups.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x|x|||| topicName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.JndiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

JndiApi apiInstance = new JndiApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String topicName = "topicName_example"; // String | The JNDI name of the JMS Topic.
MsgVpnJndiTopic body = new MsgVpnJndiTopic(); // MsgVpnJndiTopic | The JNDI Topic object's attributes.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnJndiTopicResponse result = apiInstance.replaceMsgVpnJndiTopic(msgVpnName, topicName, body, opaquePassword, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JndiApi#replaceMsgVpnJndiTopic");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **topicName** | **String**| The JNDI name of the JMS Topic. |
 **body** | [**MsgVpnJndiTopic**](MsgVpnJndiTopic.md)| The JNDI Topic object&#39;s attributes. |
 **opaquePassword** | **String**| Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the &#x60;opaquePassword&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnJndiTopicResponse**](MsgVpnJndiTopicResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateMsgVpnJndiConnectionFactory"></a>
# **updateMsgVpnJndiConnectionFactory**
> MsgVpnJndiConnectionFactoryResponse updateMsgVpnJndiConnectionFactory(msgVpnName, connectionFactoryName, body, opaquePassword, select)

Update a JNDI Connection Factory object.

Update a JNDI Connection Factory object. Any attribute missing from the request will be left unchanged.  The message broker provides an internal JNDI store for provisioned Connection Factory objects that clients can access through JNDI lookups.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: connectionFactoryName|x|x|||| msgVpnName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.JndiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

JndiApi apiInstance = new JndiApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String connectionFactoryName = "connectionFactoryName_example"; // String | The name of the JMS Connection Factory.
MsgVpnJndiConnectionFactory body = new MsgVpnJndiConnectionFactory(); // MsgVpnJndiConnectionFactory | The JNDI Connection Factory object's attributes.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnJndiConnectionFactoryResponse result = apiInstance.updateMsgVpnJndiConnectionFactory(msgVpnName, connectionFactoryName, body, opaquePassword, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JndiApi#updateMsgVpnJndiConnectionFactory");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **connectionFactoryName** | **String**| The name of the JMS Connection Factory. |
 **body** | [**MsgVpnJndiConnectionFactory**](MsgVpnJndiConnectionFactory.md)| The JNDI Connection Factory object&#39;s attributes. |
 **opaquePassword** | **String**| Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the &#x60;opaquePassword&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnJndiConnectionFactoryResponse**](MsgVpnJndiConnectionFactoryResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateMsgVpnJndiQueue"></a>
# **updateMsgVpnJndiQueue**
> MsgVpnJndiQueueResponse updateMsgVpnJndiQueue(msgVpnName, queueName, body, opaquePassword, select)

Update a JNDI Queue object.

Update a JNDI Queue object. Any attribute missing from the request will be left unchanged.  The message broker provides an internal JNDI store for provisioned Queue objects that clients can access through JNDI lookups.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x|x|||| queueName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.JndiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

JndiApi apiInstance = new JndiApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String queueName = "queueName_example"; // String | The JNDI name of the JMS Queue.
MsgVpnJndiQueue body = new MsgVpnJndiQueue(); // MsgVpnJndiQueue | The JNDI Queue object's attributes.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnJndiQueueResponse result = apiInstance.updateMsgVpnJndiQueue(msgVpnName, queueName, body, opaquePassword, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JndiApi#updateMsgVpnJndiQueue");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **queueName** | **String**| The JNDI name of the JMS Queue. |
 **body** | [**MsgVpnJndiQueue**](MsgVpnJndiQueue.md)| The JNDI Queue object&#39;s attributes. |
 **opaquePassword** | **String**| Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the &#x60;opaquePassword&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnJndiQueueResponse**](MsgVpnJndiQueueResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateMsgVpnJndiTopic"></a>
# **updateMsgVpnJndiTopic**
> MsgVpnJndiTopicResponse updateMsgVpnJndiTopic(msgVpnName, topicName, body, opaquePassword, select)

Update a JNDI Topic object.

Update a JNDI Topic object. Any attribute missing from the request will be left unchanged.  The message broker provides an internal JNDI store for provisioned Topic objects that clients can access through JNDI lookups.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x|x|||| topicName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.JndiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

JndiApi apiInstance = new JndiApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String topicName = "topicName_example"; // String | The JNDI name of the JMS Topic.
MsgVpnJndiTopic body = new MsgVpnJndiTopic(); // MsgVpnJndiTopic | The JNDI Topic object's attributes.
String opaquePassword = "opaquePassword_example"; // String | Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the `opaquePassword` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnJndiTopicResponse result = apiInstance.updateMsgVpnJndiTopic(msgVpnName, topicName, body, opaquePassword, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JndiApi#updateMsgVpnJndiTopic");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **topicName** | **String**| The JNDI name of the JMS Topic. |
 **body** | [**MsgVpnJndiTopic**](MsgVpnJndiTopic.md)| The JNDI Topic object&#39;s attributes. |
 **opaquePassword** | **String**| Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See that documentation for the &#x60;opaquePassword&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnJndiTopicResponse**](MsgVpnJndiTopicResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

