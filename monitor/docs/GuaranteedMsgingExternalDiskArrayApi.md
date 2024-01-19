# GuaranteedMsgingExternalDiskArrayApi

All URIs are relative to *http://www.solace.com/SEMP/v2/monitor*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getGuaranteedMsgingExternalDiskArray**](GuaranteedMsgingExternalDiskArrayApi.md#getGuaranteedMsgingExternalDiskArray) | **GET** /guaranteedMsgingExternalDiskArrays/{wwn} | Get an External Disk Array object.
[**getGuaranteedMsgingExternalDiskArrays**](GuaranteedMsgingExternalDiskArrayApi.md#getGuaranteedMsgingExternalDiskArrays) | **GET** /guaranteedMsgingExternalDiskArrays | Get a list of External Disk Array objects.


<a name="getGuaranteedMsgingExternalDiskArray"></a>
# **getGuaranteedMsgingExternalDiskArray**
> GuaranteedMsgingExternalDiskArrayResponse getGuaranteedMsgingExternalDiskArray(wwn, select)

Get an External Disk Array object.

Get an External Disk Array object.  A customer-provided external disk storage array which can be  configured for guaranteed messaging.   Attribute|Identifying|Deprecated :---|:---:|:---: wwn|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.18.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.GuaranteedMsgingExternalDiskArrayApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

GuaranteedMsgingExternalDiskArrayApi apiInstance = new GuaranteedMsgingExternalDiskArrayApi();
String wwn = "wwn_example"; // String | The World-Wide Name (WWN) of the disk array to use for storing the guaranteed messaging spool. The disk array must be accessible by this broker and must not already be owned by another broker (unless in an HA pair with this broker).
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    GuaranteedMsgingExternalDiskArrayResponse result = apiInstance.getGuaranteedMsgingExternalDiskArray(wwn, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GuaranteedMsgingExternalDiskArrayApi#getGuaranteedMsgingExternalDiskArray");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **wwn** | **String**| The World-Wide Name (WWN) of the disk array to use for storing the guaranteed messaging spool. The disk array must be accessible by this broker and must not already be owned by another broker (unless in an HA pair with this broker). |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**GuaranteedMsgingExternalDiskArrayResponse**](GuaranteedMsgingExternalDiskArrayResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getGuaranteedMsgingExternalDiskArrays"></a>
# **getGuaranteedMsgingExternalDiskArrays**
> GuaranteedMsgingExternalDiskArraysResponse getGuaranteedMsgingExternalDiskArrays(where, select)

Get a list of External Disk Array objects.

Get a list of External Disk Array objects.  A customer-provided external disk storage array which can be  configured for guaranteed messaging.   Attribute|Identifying|Deprecated :---|:---:|:---: wwn|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.18.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.GuaranteedMsgingExternalDiskArrayApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

GuaranteedMsgingExternalDiskArrayApi apiInstance = new GuaranteedMsgingExternalDiskArrayApi();
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    GuaranteedMsgingExternalDiskArraysResponse result = apiInstance.getGuaranteedMsgingExternalDiskArrays(where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GuaranteedMsgingExternalDiskArrayApi#getGuaranteedMsgingExternalDiskArrays");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **where** | [**List&lt;String&gt;**](String.md)| Include in the response only objects where certain conditions are true. See the the documentation for the &#x60;where&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**GuaranteedMsgingExternalDiskArraysResponse**](GuaranteedMsgingExternalDiskArraysResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

