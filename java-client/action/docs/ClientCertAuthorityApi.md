# ClientCertAuthorityApi

All URIs are relative to *http://www.solace.com/SEMP/v2/action*

Method | HTTP request | Description
------------- | ------------- | -------------
[**doClientCertAuthorityRefreshCrl**](ClientCertAuthorityApi.md#doClientCertAuthorityRefreshCrl) | **PUT** /clientCertAuthorities/{certAuthorityName}/refreshCrl | Refresh the CRL file for the Client Certificate Authority.
[**getClientCertAuthorities**](ClientCertAuthorityApi.md#getClientCertAuthorities) | **GET** /clientCertAuthorities | Get a list of Client Certificate Authority objects.
[**getClientCertAuthority**](ClientCertAuthorityApi.md#getClientCertAuthority) | **GET** /clientCertAuthorities/{certAuthorityName} | Get a Client Certificate Authority object.


<a name="doClientCertAuthorityRefreshCrl"></a>
# **doClientCertAuthorityRefreshCrl**
> SempMetaOnlyResponse doClientCertAuthorityRefreshCrl(certAuthorityName, body)

Refresh the CRL file for the Client Certificate Authority.

Refresh the CRL file for the Client Certificate Authority.    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.19.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ClientCertAuthorityApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ClientCertAuthorityApi apiInstance = new ClientCertAuthorityApi();
String certAuthorityName = "certAuthorityName_example"; // String | The name of the Certificate Authority.
ClientCertAuthorityRefreshCrl body = new ClientCertAuthorityRefreshCrl(); // ClientCertAuthorityRefreshCrl | The Refresh CRL action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doClientCertAuthorityRefreshCrl(certAuthorityName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ClientCertAuthorityApi#doClientCertAuthorityRefreshCrl");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **certAuthorityName** | **String**| The name of the Certificate Authority. |
 **body** | [**ClientCertAuthorityRefreshCrl**](ClientCertAuthorityRefreshCrl.md)| The Refresh CRL action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getClientCertAuthorities"></a>
# **getClientCertAuthorities**
> ClientCertAuthoritiesResponse getClientCertAuthorities(count, cursor, where, select)

Get a list of Client Certificate Authority objects.

Get a list of Client Certificate Authority objects.  Clients can authenticate with the message broker over TLS by presenting a valid client certificate. The message broker authenticates the client certificate by constructing a full certificate chain (from the client certificate to intermediate CAs to a configured root CA). The intermediate CAs in this chain can be provided by the client, or configured in the message broker. The root CA must be configured on the message broker.   Attribute|Identifying|Deprecated :---|:---:|:---: certAuthorityName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.19.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ClientCertAuthorityApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ClientCertAuthorityApi apiInstance = new ClientCertAuthorityApi();
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    ClientCertAuthoritiesResponse result = apiInstance.getClientCertAuthorities(count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ClientCertAuthorityApi#getClientCertAuthorities");
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

[**ClientCertAuthoritiesResponse**](ClientCertAuthoritiesResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getClientCertAuthority"></a>
# **getClientCertAuthority**
> ClientCertAuthorityResponse getClientCertAuthority(certAuthorityName, select)

Get a Client Certificate Authority object.

Get a Client Certificate Authority object.  Clients can authenticate with the message broker over TLS by presenting a valid client certificate. The message broker authenticates the client certificate by constructing a full certificate chain (from the client certificate to intermediate CAs to a configured root CA). The intermediate CAs in this chain can be provided by the client, or configured in the message broker. The root CA must be configured on the message broker.   Attribute|Identifying|Deprecated :---|:---:|:---: certAuthorityName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.19.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ClientCertAuthorityApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ClientCertAuthorityApi apiInstance = new ClientCertAuthorityApi();
String certAuthorityName = "certAuthorityName_example"; // String | The name of the Certificate Authority.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    ClientCertAuthorityResponse result = apiInstance.getClientCertAuthority(certAuthorityName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ClientCertAuthorityApi#getClientCertAuthority");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **certAuthorityName** | **String**| The name of the Certificate Authority. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**ClientCertAuthorityResponse**](ClientCertAuthorityResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

