# SessionApi

All URIs are relative to *http://www.solace.com/SEMP/v2/action*

Method | HTTP request | Description
------------- | ------------- | -------------
[**doSessionDelete**](SessionApi.md#doSessionDelete) | **PUT** /sessions/{sessionUsername},{sessionId}/delete | Delete the session.
[**getSession**](SessionApi.md#getSession) | **GET** /sessions/{sessionUsername},{sessionId} | Get a Session object.
[**getSessions**](SessionApi.md#getSessions) | **GET** /sessions | Get a list of Session objects.


<a name="doSessionDelete"></a>
# **doSessionDelete**
> SempMetaOnlyResponse doSessionDelete(sessionUsername, sessionId, body)

Delete the session.

Delete the session.    A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been available since 2.21.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.SessionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

SessionApi apiInstance = new SessionApi();
String sessionUsername = "sessionUsername_example"; // String | The username used for authorization.
String sessionId = "sessionId_example"; // String | The unique identifier for the session.
SessionDelete body = new SessionDelete(); // SessionDelete | The Delete action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doSessionDelete(sessionUsername, sessionId, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SessionApi#doSessionDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **sessionUsername** | **String**| The username used for authorization. |
 **sessionId** | **String**| The unique identifier for the session. |
 **body** | [**SessionDelete**](SessionDelete.md)| The Delete action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSession"></a>
# **getSession**
> SessionResponse getSession(sessionUsername, sessionId, select)

Get a Session object.

Get a Session object.  Administrative sessions for configuration and monitoring.   Attribute|Identifying|Deprecated :---|:---:|:---: sessionId|x| sessionUsername|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.21.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.SessionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

SessionApi apiInstance = new SessionApi();
String sessionUsername = "sessionUsername_example"; // String | The username used for authorization.
String sessionId = "sessionId_example"; // String | The unique identifier for the session.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    SessionResponse result = apiInstance.getSession(sessionUsername, sessionId, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SessionApi#getSession");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **sessionUsername** | **String**| The username used for authorization. |
 **sessionId** | **String**| The unique identifier for the session. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**SessionResponse**](SessionResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSessions"></a>
# **getSessions**
> SessionsResponse getSessions(count, cursor, where, select)

Get a list of Session objects.

Get a list of Session objects.  Administrative sessions for configuration and monitoring.   Attribute|Identifying|Deprecated :---|:---:|:---: sessionId|x| sessionUsername|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.21.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.SessionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

SessionApi apiInstance = new SessionApi();
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    SessionsResponse result = apiInstance.getSessions(count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SessionApi#getSessions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **count** | **Integer**| Limit the count of objects in the response. See the documentation for the &#x60;count&#x60; parameter. | [optional] [default to 10]
 **cursor** | **String**| The cursor, or position, for the next page of objects. See the documentation for the &#x60;cursor&#x60; parameter. | [optional]
 **where** | [**List&lt;String&gt;**](String.md)| Include in the response only objects where certain conditions are true. See the the documentation for the &#x60;where&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**SessionsResponse**](SessionsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

