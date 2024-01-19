# DistributedCacheApi

All URIs are relative to *http://www.solace.com/SEMP/v2/action*

Method | HTTP request | Description
------------- | ------------- | -------------
[**doMsgVpnDistributedCacheClusterInstanceBackupCachedMsgs**](DistributedCacheApi.md#doMsgVpnDistributedCacheClusterInstanceBackupCachedMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/backupCachedMsgs | Backup cached messages of the Cache Instance to disk.
[**doMsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs**](DistributedCacheApi.md#doMsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/cancelBackupCachedMsgs | Cancel the backup of cached messages from the Cache Instance.
[**doMsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs**](DistributedCacheApi.md#doMsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/cancelRestoreCachedMsgs | Cancel the restore of cached messages to the Cache Instance.
[**doMsgVpnDistributedCacheClusterInstanceClearEvent**](DistributedCacheApi.md#doMsgVpnDistributedCacheClusterInstanceClearEvent) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/clearEvent | Clear an event for the Cache Instance so it can be generated anew.
[**doMsgVpnDistributedCacheClusterInstanceClearStats**](DistributedCacheApi.md#doMsgVpnDistributedCacheClusterInstanceClearStats) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/clearStats | Clear the statistics for the Cache Instance.
[**doMsgVpnDistributedCacheClusterInstanceDeleteMsgs**](DistributedCacheApi.md#doMsgVpnDistributedCacheClusterInstanceDeleteMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/deleteMsgs | Delete messages covered by the given topic in the Cache Instance.
[**doMsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs**](DistributedCacheApi.md#doMsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/restoreCachedMsgs | Restore cached messages for the Cache Instance from disk.
[**doMsgVpnDistributedCacheClusterInstanceStart**](DistributedCacheApi.md#doMsgVpnDistributedCacheClusterInstanceStart) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/start | Start the Cache Instance.
[**getMsgVpnDistributedCache**](DistributedCacheApi.md#getMsgVpnDistributedCache) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName} | Get a Distributed Cache object.
[**getMsgVpnDistributedCacheCluster**](DistributedCacheApi.md#getMsgVpnDistributedCacheCluster) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName} | Get a Cache Cluster object.
[**getMsgVpnDistributedCacheClusterInstance**](DistributedCacheApi.md#getMsgVpnDistributedCacheClusterInstance) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName} | Get a Cache Instance object.
[**getMsgVpnDistributedCacheClusterInstances**](DistributedCacheApi.md#getMsgVpnDistributedCacheClusterInstances) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances | Get a list of Cache Instance objects.
[**getMsgVpnDistributedCacheClusters**](DistributedCacheApi.md#getMsgVpnDistributedCacheClusters) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters | Get a list of Cache Cluster objects.
[**getMsgVpnDistributedCaches**](DistributedCacheApi.md#getMsgVpnDistributedCaches) | **GET** /msgVpns/{msgVpnName}/distributedCaches | Get a list of Distributed Cache objects.


<a name="doMsgVpnDistributedCacheClusterInstanceBackupCachedMsgs"></a>
# **doMsgVpnDistributedCacheClusterInstanceBackupCachedMsgs**
> SempMetaOnlyResponse doMsgVpnDistributedCacheClusterInstanceBackupCachedMsgs(msgVpnName, cacheName, clusterName, instanceName, body)

Backup cached messages of the Cache Instance to disk.

Backup cached messages of the Cache Instance to disk.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DistributedCacheApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DistributedCacheApi apiInstance = new DistributedCacheApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String cacheName = "cacheName_example"; // String | The name of the Distributed Cache.
String clusterName = "clusterName_example"; // String | The name of the Cache Cluster.
String instanceName = "instanceName_example"; // String | The name of the Cache Instance.
MsgVpnDistributedCacheClusterInstanceBackupCachedMsgs body = new MsgVpnDistributedCacheClusterInstanceBackupCachedMsgs(); // MsgVpnDistributedCacheClusterInstanceBackupCachedMsgs | The Backup Cached Messages action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnDistributedCacheClusterInstanceBackupCachedMsgs(msgVpnName, cacheName, clusterName, instanceName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DistributedCacheApi#doMsgVpnDistributedCacheClusterInstanceBackupCachedMsgs");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **cacheName** | **String**| The name of the Distributed Cache. |
 **clusterName** | **String**| The name of the Cache Cluster. |
 **instanceName** | **String**| The name of the Cache Instance. |
 **body** | [**MsgVpnDistributedCacheClusterInstanceBackupCachedMsgs**](MsgVpnDistributedCacheClusterInstanceBackupCachedMsgs.md)| The Backup Cached Messages action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs"></a>
# **doMsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs**
> SempMetaOnlyResponse doMsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs(msgVpnName, cacheName, clusterName, instanceName, body)

Cancel the backup of cached messages from the Cache Instance.

Cancel the backup of cached messages from the Cache Instance.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DistributedCacheApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DistributedCacheApi apiInstance = new DistributedCacheApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String cacheName = "cacheName_example"; // String | The name of the Distributed Cache.
String clusterName = "clusterName_example"; // String | The name of the Cache Cluster.
String instanceName = "instanceName_example"; // String | The name of the Cache Instance.
MsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs body = new MsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs(); // MsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs | The Cancel Backup Cached Messages action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs(msgVpnName, cacheName, clusterName, instanceName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DistributedCacheApi#doMsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **cacheName** | **String**| The name of the Distributed Cache. |
 **clusterName** | **String**| The name of the Cache Cluster. |
 **instanceName** | **String**| The name of the Cache Instance. |
 **body** | [**MsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs**](MsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs.md)| The Cancel Backup Cached Messages action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs"></a>
# **doMsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs**
> SempMetaOnlyResponse doMsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs(msgVpnName, cacheName, clusterName, instanceName, body)

Cancel the restore of cached messages to the Cache Instance.

Cancel the restore of cached messages to the Cache Instance.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DistributedCacheApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DistributedCacheApi apiInstance = new DistributedCacheApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String cacheName = "cacheName_example"; // String | The name of the Distributed Cache.
String clusterName = "clusterName_example"; // String | The name of the Cache Cluster.
String instanceName = "instanceName_example"; // String | The name of the Cache Instance.
MsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs body = new MsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs(); // MsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs | The Cancel Restore Cached Messages action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs(msgVpnName, cacheName, clusterName, instanceName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DistributedCacheApi#doMsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **cacheName** | **String**| The name of the Distributed Cache. |
 **clusterName** | **String**| The name of the Cache Cluster. |
 **instanceName** | **String**| The name of the Cache Instance. |
 **body** | [**MsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs**](MsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs.md)| The Cancel Restore Cached Messages action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnDistributedCacheClusterInstanceClearEvent"></a>
# **doMsgVpnDistributedCacheClusterInstanceClearEvent**
> SempMetaOnlyResponse doMsgVpnDistributedCacheClusterInstanceClearEvent(msgVpnName, cacheName, clusterName, instanceName, body)

Clear an event for the Cache Instance so it can be generated anew.

Clear an event for the Cache Instance so it can be generated anew.   Attribute|Required|Deprecated :---|:---:|:---: eventName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DistributedCacheApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DistributedCacheApi apiInstance = new DistributedCacheApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String cacheName = "cacheName_example"; // String | The name of the Distributed Cache.
String clusterName = "clusterName_example"; // String | The name of the Cache Cluster.
String instanceName = "instanceName_example"; // String | The name of the Cache Instance.
MsgVpnDistributedCacheClusterInstanceClearEvent body = new MsgVpnDistributedCacheClusterInstanceClearEvent(); // MsgVpnDistributedCacheClusterInstanceClearEvent | The Clear Event action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnDistributedCacheClusterInstanceClearEvent(msgVpnName, cacheName, clusterName, instanceName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DistributedCacheApi#doMsgVpnDistributedCacheClusterInstanceClearEvent");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **cacheName** | **String**| The name of the Distributed Cache. |
 **clusterName** | **String**| The name of the Cache Cluster. |
 **instanceName** | **String**| The name of the Cache Instance. |
 **body** | [**MsgVpnDistributedCacheClusterInstanceClearEvent**](MsgVpnDistributedCacheClusterInstanceClearEvent.md)| The Clear Event action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnDistributedCacheClusterInstanceClearStats"></a>
# **doMsgVpnDistributedCacheClusterInstanceClearStats**
> SempMetaOnlyResponse doMsgVpnDistributedCacheClusterInstanceClearStats(msgVpnName, cacheName, clusterName, instanceName, body)

Clear the statistics for the Cache Instance.

Clear the statistics for the Cache Instance.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DistributedCacheApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DistributedCacheApi apiInstance = new DistributedCacheApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String cacheName = "cacheName_example"; // String | The name of the Distributed Cache.
String clusterName = "clusterName_example"; // String | The name of the Cache Cluster.
String instanceName = "instanceName_example"; // String | The name of the Cache Instance.
MsgVpnDistributedCacheClusterInstanceClearStats body = new MsgVpnDistributedCacheClusterInstanceClearStats(); // MsgVpnDistributedCacheClusterInstanceClearStats | The Clear Stats action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnDistributedCacheClusterInstanceClearStats(msgVpnName, cacheName, clusterName, instanceName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DistributedCacheApi#doMsgVpnDistributedCacheClusterInstanceClearStats");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **cacheName** | **String**| The name of the Distributed Cache. |
 **clusterName** | **String**| The name of the Cache Cluster. |
 **instanceName** | **String**| The name of the Cache Instance. |
 **body** | [**MsgVpnDistributedCacheClusterInstanceClearStats**](MsgVpnDistributedCacheClusterInstanceClearStats.md)| The Clear Stats action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnDistributedCacheClusterInstanceDeleteMsgs"></a>
# **doMsgVpnDistributedCacheClusterInstanceDeleteMsgs**
> SempMetaOnlyResponse doMsgVpnDistributedCacheClusterInstanceDeleteMsgs(msgVpnName, cacheName, clusterName, instanceName, body)

Delete messages covered by the given topic in the Cache Instance.

Delete messages covered by the given topic in the Cache Instance.   Attribute|Required|Deprecated :---|:---:|:---: topic|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DistributedCacheApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DistributedCacheApi apiInstance = new DistributedCacheApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String cacheName = "cacheName_example"; // String | The name of the Distributed Cache.
String clusterName = "clusterName_example"; // String | The name of the Cache Cluster.
String instanceName = "instanceName_example"; // String | The name of the Cache Instance.
MsgVpnDistributedCacheClusterInstanceDeleteMsgs body = new MsgVpnDistributedCacheClusterInstanceDeleteMsgs(); // MsgVpnDistributedCacheClusterInstanceDeleteMsgs | The Delete Messages action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnDistributedCacheClusterInstanceDeleteMsgs(msgVpnName, cacheName, clusterName, instanceName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DistributedCacheApi#doMsgVpnDistributedCacheClusterInstanceDeleteMsgs");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **cacheName** | **String**| The name of the Distributed Cache. |
 **clusterName** | **String**| The name of the Cache Cluster. |
 **instanceName** | **String**| The name of the Cache Instance. |
 **body** | [**MsgVpnDistributedCacheClusterInstanceDeleteMsgs**](MsgVpnDistributedCacheClusterInstanceDeleteMsgs.md)| The Delete Messages action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs"></a>
# **doMsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs**
> SempMetaOnlyResponse doMsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs(msgVpnName, cacheName, clusterName, instanceName, body)

Restore cached messages for the Cache Instance from disk.

Restore cached messages for the Cache Instance from disk.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DistributedCacheApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DistributedCacheApi apiInstance = new DistributedCacheApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String cacheName = "cacheName_example"; // String | The name of the Distributed Cache.
String clusterName = "clusterName_example"; // String | The name of the Cache Cluster.
String instanceName = "instanceName_example"; // String | The name of the Cache Instance.
MsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs body = new MsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs(); // MsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs | The Restore Cached Messages action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs(msgVpnName, cacheName, clusterName, instanceName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DistributedCacheApi#doMsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **cacheName** | **String**| The name of the Distributed Cache. |
 **clusterName** | **String**| The name of the Cache Cluster. |
 **instanceName** | **String**| The name of the Cache Instance. |
 **body** | [**MsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs**](MsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs.md)| The Restore Cached Messages action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnDistributedCacheClusterInstanceStart"></a>
# **doMsgVpnDistributedCacheClusterInstanceStart**
> SempMetaOnlyResponse doMsgVpnDistributedCacheClusterInstanceStart(msgVpnName, cacheName, clusterName, instanceName, body)

Start the Cache Instance.

Start the Cache Instance.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DistributedCacheApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DistributedCacheApi apiInstance = new DistributedCacheApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String cacheName = "cacheName_example"; // String | The name of the Distributed Cache.
String clusterName = "clusterName_example"; // String | The name of the Cache Cluster.
String instanceName = "instanceName_example"; // String | The name of the Cache Instance.
MsgVpnDistributedCacheClusterInstanceStart body = new MsgVpnDistributedCacheClusterInstanceStart(); // MsgVpnDistributedCacheClusterInstanceStart | The Start Cache Instance action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnDistributedCacheClusterInstanceStart(msgVpnName, cacheName, clusterName, instanceName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DistributedCacheApi#doMsgVpnDistributedCacheClusterInstanceStart");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **cacheName** | **String**| The name of the Distributed Cache. |
 **clusterName** | **String**| The name of the Cache Cluster. |
 **instanceName** | **String**| The name of the Cache Instance. |
 **body** | [**MsgVpnDistributedCacheClusterInstanceStart**](MsgVpnDistributedCacheClusterInstanceStart.md)| The Start Cache Instance action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnDistributedCache"></a>
# **getMsgVpnDistributedCache**
> MsgVpnDistributedCacheResponse getMsgVpnDistributedCache(msgVpnName, cacheName, select)

Get a Distributed Cache object.

Get a Distributed Cache object.  A Distributed Cache is a collection of one or more Cache Clusters that belong to the same Message VPN. Each Cache Cluster in a Distributed Cache is configured to subscribe to a different set of topics. This effectively divides up the configured topic space, to provide scaling to very large topic spaces or very high cached message throughput.   Attribute|Identifying|Deprecated :---|:---:|:---: cacheName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DistributedCacheApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DistributedCacheApi apiInstance = new DistributedCacheApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String cacheName = "cacheName_example"; // String | The name of the Distributed Cache.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnDistributedCacheResponse result = apiInstance.getMsgVpnDistributedCache(msgVpnName, cacheName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DistributedCacheApi#getMsgVpnDistributedCache");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **cacheName** | **String**| The name of the Distributed Cache. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnDistributedCacheResponse**](MsgVpnDistributedCacheResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnDistributedCacheCluster"></a>
# **getMsgVpnDistributedCacheCluster**
> MsgVpnDistributedCacheClusterResponse getMsgVpnDistributedCacheCluster(msgVpnName, cacheName, clusterName, select)

Get a Cache Cluster object.

Get a Cache Cluster object.  A Cache Cluster is a collection of one or more Cache Instances that subscribe to exactly the same topics. Cache Instances are grouped together in a Cache Cluster for the purpose of fault tolerance and load balancing. As published messages are received, the message broker message bus sends these live data messages to the Cache Instances in the Cache Cluster. This enables client cache requests to be served by any of Cache Instances in the Cache Cluster.   Attribute|Identifying|Deprecated :---|:---:|:---: cacheName|x| clusterName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DistributedCacheApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DistributedCacheApi apiInstance = new DistributedCacheApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String cacheName = "cacheName_example"; // String | The name of the Distributed Cache.
String clusterName = "clusterName_example"; // String | The name of the Cache Cluster.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnDistributedCacheClusterResponse result = apiInstance.getMsgVpnDistributedCacheCluster(msgVpnName, cacheName, clusterName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DistributedCacheApi#getMsgVpnDistributedCacheCluster");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **cacheName** | **String**| The name of the Distributed Cache. |
 **clusterName** | **String**| The name of the Cache Cluster. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnDistributedCacheClusterResponse**](MsgVpnDistributedCacheClusterResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnDistributedCacheClusterInstance"></a>
# **getMsgVpnDistributedCacheClusterInstance**
> MsgVpnDistributedCacheClusterInstanceResponse getMsgVpnDistributedCacheClusterInstance(msgVpnName, cacheName, clusterName, instanceName, select)

Get a Cache Instance object.

Get a Cache Instance object.  A Cache Instance is a single Cache process that belongs to a single Cache Cluster. A Cache Instance object provisioned on the broker is used to disseminate configuration information to the Cache process. Cache Instances listen for and cache live data messages that match the topic subscriptions configured for their parent Cache Cluster.   Attribute|Identifying|Deprecated :---|:---:|:---: cacheName|x| clusterName|x| instanceName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DistributedCacheApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DistributedCacheApi apiInstance = new DistributedCacheApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String cacheName = "cacheName_example"; // String | The name of the Distributed Cache.
String clusterName = "clusterName_example"; // String | The name of the Cache Cluster.
String instanceName = "instanceName_example"; // String | The name of the Cache Instance.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnDistributedCacheClusterInstanceResponse result = apiInstance.getMsgVpnDistributedCacheClusterInstance(msgVpnName, cacheName, clusterName, instanceName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DistributedCacheApi#getMsgVpnDistributedCacheClusterInstance");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **cacheName** | **String**| The name of the Distributed Cache. |
 **clusterName** | **String**| The name of the Cache Cluster. |
 **instanceName** | **String**| The name of the Cache Instance. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnDistributedCacheClusterInstanceResponse**](MsgVpnDistributedCacheClusterInstanceResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnDistributedCacheClusterInstances"></a>
# **getMsgVpnDistributedCacheClusterInstances**
> MsgVpnDistributedCacheClusterInstancesResponse getMsgVpnDistributedCacheClusterInstances(msgVpnName, cacheName, clusterName, count, cursor, where, select)

Get a list of Cache Instance objects.

Get a list of Cache Instance objects.  A Cache Instance is a single Cache process that belongs to a single Cache Cluster. A Cache Instance object provisioned on the broker is used to disseminate configuration information to the Cache process. Cache Instances listen for and cache live data messages that match the topic subscriptions configured for their parent Cache Cluster.   Attribute|Identifying|Deprecated :---|:---:|:---: cacheName|x| clusterName|x| instanceName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DistributedCacheApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DistributedCacheApi apiInstance = new DistributedCacheApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String cacheName = "cacheName_example"; // String | The name of the Distributed Cache.
String clusterName = "clusterName_example"; // String | The name of the Cache Cluster.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnDistributedCacheClusterInstancesResponse result = apiInstance.getMsgVpnDistributedCacheClusterInstances(msgVpnName, cacheName, clusterName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DistributedCacheApi#getMsgVpnDistributedCacheClusterInstances");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **cacheName** | **String**| The name of the Distributed Cache. |
 **clusterName** | **String**| The name of the Cache Cluster. |
 **count** | **Integer**| Limit the count of objects in the response. See the documentation for the &#x60;count&#x60; parameter. | [optional] [default to 10]
 **cursor** | **String**| The cursor, or position, for the next page of objects. See the documentation for the &#x60;cursor&#x60; parameter. | [optional]
 **where** | [**List&lt;String&gt;**](String.md)| Include in the response only objects where certain conditions are true. See the the documentation for the &#x60;where&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnDistributedCacheClusterInstancesResponse**](MsgVpnDistributedCacheClusterInstancesResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnDistributedCacheClusters"></a>
# **getMsgVpnDistributedCacheClusters**
> MsgVpnDistributedCacheClustersResponse getMsgVpnDistributedCacheClusters(msgVpnName, cacheName, count, cursor, where, select)

Get a list of Cache Cluster objects.

Get a list of Cache Cluster objects.  A Cache Cluster is a collection of one or more Cache Instances that subscribe to exactly the same topics. Cache Instances are grouped together in a Cache Cluster for the purpose of fault tolerance and load balancing. As published messages are received, the message broker message bus sends these live data messages to the Cache Instances in the Cache Cluster. This enables client cache requests to be served by any of Cache Instances in the Cache Cluster.   Attribute|Identifying|Deprecated :---|:---:|:---: cacheName|x| clusterName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DistributedCacheApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DistributedCacheApi apiInstance = new DistributedCacheApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String cacheName = "cacheName_example"; // String | The name of the Distributed Cache.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnDistributedCacheClustersResponse result = apiInstance.getMsgVpnDistributedCacheClusters(msgVpnName, cacheName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DistributedCacheApi#getMsgVpnDistributedCacheClusters");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **cacheName** | **String**| The name of the Distributed Cache. |
 **count** | **Integer**| Limit the count of objects in the response. See the documentation for the &#x60;count&#x60; parameter. | [optional] [default to 10]
 **cursor** | **String**| The cursor, or position, for the next page of objects. See the documentation for the &#x60;cursor&#x60; parameter. | [optional]
 **where** | [**List&lt;String&gt;**](String.md)| Include in the response only objects where certain conditions are true. See the the documentation for the &#x60;where&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnDistributedCacheClustersResponse**](MsgVpnDistributedCacheClustersResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnDistributedCaches"></a>
# **getMsgVpnDistributedCaches**
> MsgVpnDistributedCachesResponse getMsgVpnDistributedCaches(msgVpnName, count, cursor, where, select)

Get a list of Distributed Cache objects.

Get a list of Distributed Cache objects.  A Distributed Cache is a collection of one or more Cache Clusters that belong to the same Message VPN. Each Cache Cluster in a Distributed Cache is configured to subscribe to a different set of topics. This effectively divides up the configured topic space, to provide scaling to very large topic spaces or very high cached message throughput.   Attribute|Identifying|Deprecated :---|:---:|:---: cacheName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DistributedCacheApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DistributedCacheApi apiInstance = new DistributedCacheApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnDistributedCachesResponse result = apiInstance.getMsgVpnDistributedCaches(msgVpnName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DistributedCacheApi#getMsgVpnDistributedCaches");
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

[**MsgVpnDistributedCachesResponse**](MsgVpnDistributedCachesResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

