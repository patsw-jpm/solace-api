# ConfigSyncLocalDatabaseRowApi

All URIs are relative to *http://www.solace.com/SEMP/v2/monitor*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getConfigSyncLocalDatabaseRow**](ConfigSyncLocalDatabaseRowApi.md#getConfigSyncLocalDatabaseRow) | **GET** /configSyncLocalDatabaseRows/{type},{name} | Get a Config Sync Local Database object.
[**getConfigSyncLocalDatabaseRows**](ConfigSyncLocalDatabaseRowApi.md#getConfigSyncLocalDatabaseRows) | **GET** /configSyncLocalDatabaseRows | Get a list of Config Sync Local Database objects.


<a name="getConfigSyncLocalDatabaseRow"></a>
# **getConfigSyncLocalDatabaseRow**
> ConfigSyncLocalDatabaseRowResponse getConfigSyncLocalDatabaseRow(type, name, select)

Get a Config Sync Local Database object.

Get a Config Sync Local Database object.  Config Sync Local Database Rows contains information about the status of the table for this Broker or a local Message VPN.   Attribute|Identifying|Deprecated :---|:---:|:---: name|x| type|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation.  This has been available since 2.22.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ConfigSyncLocalDatabaseRowApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ConfigSyncLocalDatabaseRowApi apiInstance = new ConfigSyncLocalDatabaseRowApi();
String type = "type_example"; // String | The type of the row. Can be one of \"router\" or \"vpn\". There is one \"router\" row and one row for each configured \"vpn\". Each row represents a table of information that is synchronized between Config Sync and replication mates.
String name = "name_example"; // String | The name is \"site\" when the row type is \"router\", otherwise it is the Message VPN name.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    ConfigSyncLocalDatabaseRowResponse result = apiInstance.getConfigSyncLocalDatabaseRow(type, name, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfigSyncLocalDatabaseRowApi#getConfigSyncLocalDatabaseRow");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **type** | **String**| The type of the row. Can be one of \&quot;router\&quot; or \&quot;vpn\&quot;. There is one \&quot;router\&quot; row and one row for each configured \&quot;vpn\&quot;. Each row represents a table of information that is synchronized between Config Sync and replication mates. |
 **name** | **String**| The name is \&quot;site\&quot; when the row type is \&quot;router\&quot;, otherwise it is the Message VPN name. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**ConfigSyncLocalDatabaseRowResponse**](ConfigSyncLocalDatabaseRowResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getConfigSyncLocalDatabaseRows"></a>
# **getConfigSyncLocalDatabaseRows**
> ConfigSyncLocalDatabaseRowsResponse getConfigSyncLocalDatabaseRows(count, cursor, where, select)

Get a list of Config Sync Local Database objects.

Get a list of Config Sync Local Database objects.  Config Sync Local Database Rows contains information about the status of the table for this Broker or a local Message VPN.   Attribute|Identifying|Deprecated :---|:---:|:---: name|x| type|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation.  This has been available since 2.22.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ConfigSyncLocalDatabaseRowApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ConfigSyncLocalDatabaseRowApi apiInstance = new ConfigSyncLocalDatabaseRowApi();
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    ConfigSyncLocalDatabaseRowsResponse result = apiInstance.getConfigSyncLocalDatabaseRows(count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfigSyncLocalDatabaseRowApi#getConfigSyncLocalDatabaseRows");
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

[**ConfigSyncLocalDatabaseRowsResponse**](ConfigSyncLocalDatabaseRowsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

