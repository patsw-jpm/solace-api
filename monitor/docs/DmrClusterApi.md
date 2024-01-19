# DmrClusterApi

All URIs are relative to *http://www.solace.com/SEMP/v2/monitor*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getDmrCluster**](DmrClusterApi.md#getDmrCluster) | **GET** /dmrClusters/{dmrClusterName} | Get a Cluster object.
[**getDmrClusterLink**](DmrClusterApi.md#getDmrClusterLink) | **GET** /dmrClusters/{dmrClusterName}/links/{remoteNodeName} | Get a Link object.
[**getDmrClusterLinkChannel**](DmrClusterApi.md#getDmrClusterLinkChannel) | **GET** /dmrClusters/{dmrClusterName}/links/{remoteNodeName}/channels/{msgVpnName} | Get a Cluster Link Channels object.
[**getDmrClusterLinkChannels**](DmrClusterApi.md#getDmrClusterLinkChannels) | **GET** /dmrClusters/{dmrClusterName}/links/{remoteNodeName}/channels | Get a list of Cluster Link Channels objects.
[**getDmrClusterLinkRemoteAddress**](DmrClusterApi.md#getDmrClusterLinkRemoteAddress) | **GET** /dmrClusters/{dmrClusterName}/links/{remoteNodeName}/remoteAddresses/{remoteAddress} | Get a Remote Address object.
[**getDmrClusterLinkRemoteAddresses**](DmrClusterApi.md#getDmrClusterLinkRemoteAddresses) | **GET** /dmrClusters/{dmrClusterName}/links/{remoteNodeName}/remoteAddresses | Get a list of Remote Address objects.
[**getDmrClusterLinkTlsTrustedCommonName**](DmrClusterApi.md#getDmrClusterLinkTlsTrustedCommonName) | **GET** /dmrClusters/{dmrClusterName}/links/{remoteNodeName}/tlsTrustedCommonNames/{tlsTrustedCommonName} | Get a Trusted Common Name object.
[**getDmrClusterLinkTlsTrustedCommonNames**](DmrClusterApi.md#getDmrClusterLinkTlsTrustedCommonNames) | **GET** /dmrClusters/{dmrClusterName}/links/{remoteNodeName}/tlsTrustedCommonNames | Get a list of Trusted Common Name objects.
[**getDmrClusterLinks**](DmrClusterApi.md#getDmrClusterLinks) | **GET** /dmrClusters/{dmrClusterName}/links | Get a list of Link objects.
[**getDmrClusterTopologyIssue**](DmrClusterApi.md#getDmrClusterTopologyIssue) | **GET** /dmrClusters/{dmrClusterName}/topologyIssues/{topologyIssue} | Get a Cluster Topology Issue object.
[**getDmrClusterTopologyIssues**](DmrClusterApi.md#getDmrClusterTopologyIssues) | **GET** /dmrClusters/{dmrClusterName}/topologyIssues | Get a list of Cluster Topology Issue objects.
[**getDmrClusters**](DmrClusterApi.md#getDmrClusters) | **GET** /dmrClusters | Get a list of Cluster objects.


<a name="getDmrCluster"></a>
# **getDmrCluster**
> DmrClusterResponse getDmrCluster(dmrClusterName, select)

Get a Cluster object.

Get a Cluster object.  A Cluster is a provisioned object on a message broker that contains global DMR configuration parameters.   Attribute|Identifying|Deprecated :---|:---:|:---: dmrClusterName|x| tlsServerCertEnforceTrustedCommonNameEnabled||x    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DmrClusterApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DmrClusterApi apiInstance = new DmrClusterApi();
String dmrClusterName = "dmrClusterName_example"; // String | The name of the Cluster.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    DmrClusterResponse result = apiInstance.getDmrCluster(dmrClusterName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DmrClusterApi#getDmrCluster");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **dmrClusterName** | **String**| The name of the Cluster. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**DmrClusterResponse**](DmrClusterResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getDmrClusterLink"></a>
# **getDmrClusterLink**
> DmrClusterLinkResponse getDmrClusterLink(dmrClusterName, remoteNodeName, select)

Get a Link object.

Get a Link object.  A Link connects nodes (either within a Cluster or between two different Clusters) and allows them to exchange topology information, subscriptions and data.   Attribute|Identifying|Deprecated :---|:---:|:---: dmrClusterName|x| remoteNodeName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DmrClusterApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DmrClusterApi apiInstance = new DmrClusterApi();
String dmrClusterName = "dmrClusterName_example"; // String | The name of the Cluster.
String remoteNodeName = "remoteNodeName_example"; // String | The name of the node at the remote end of the Link.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    DmrClusterLinkResponse result = apiInstance.getDmrClusterLink(dmrClusterName, remoteNodeName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DmrClusterApi#getDmrClusterLink");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **dmrClusterName** | **String**| The name of the Cluster. |
 **remoteNodeName** | **String**| The name of the node at the remote end of the Link. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**DmrClusterLinkResponse**](DmrClusterLinkResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getDmrClusterLinkChannel"></a>
# **getDmrClusterLinkChannel**
> DmrClusterLinkChannelResponse getDmrClusterLinkChannel(dmrClusterName, remoteNodeName, msgVpnName, select)

Get a Cluster Link Channels object.

Get a Cluster Link Channels object.  A Channel is a connection between this broker and a remote node in the Cluster.   Attribute|Identifying|Deprecated :---|:---:|:---: dmrClusterName|x| msgVpnName|x| remoteNodeName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DmrClusterApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DmrClusterApi apiInstance = new DmrClusterApi();
String dmrClusterName = "dmrClusterName_example"; // String | The name of the Cluster.
String remoteNodeName = "remoteNodeName_example"; // String | The name of the node at the remote end of the Link.
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    DmrClusterLinkChannelResponse result = apiInstance.getDmrClusterLinkChannel(dmrClusterName, remoteNodeName, msgVpnName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DmrClusterApi#getDmrClusterLinkChannel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **dmrClusterName** | **String**| The name of the Cluster. |
 **remoteNodeName** | **String**| The name of the node at the remote end of the Link. |
 **msgVpnName** | **String**| The name of the Message VPN. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**DmrClusterLinkChannelResponse**](DmrClusterLinkChannelResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getDmrClusterLinkChannels"></a>
# **getDmrClusterLinkChannels**
> DmrClusterLinkChannelsResponse getDmrClusterLinkChannels(dmrClusterName, remoteNodeName, count, cursor, where, select)

Get a list of Cluster Link Channels objects.

Get a list of Cluster Link Channels objects.  A Channel is a connection between this broker and a remote node in the Cluster.   Attribute|Identifying|Deprecated :---|:---:|:---: dmrClusterName|x| msgVpnName|x| remoteNodeName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DmrClusterApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DmrClusterApi apiInstance = new DmrClusterApi();
String dmrClusterName = "dmrClusterName_example"; // String | The name of the Cluster.
String remoteNodeName = "remoteNodeName_example"; // String | The name of the node at the remote end of the Link.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    DmrClusterLinkChannelsResponse result = apiInstance.getDmrClusterLinkChannels(dmrClusterName, remoteNodeName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DmrClusterApi#getDmrClusterLinkChannels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **dmrClusterName** | **String**| The name of the Cluster. |
 **remoteNodeName** | **String**| The name of the node at the remote end of the Link. |
 **count** | **Integer**| Limit the count of objects in the response. See the documentation for the &#x60;count&#x60; parameter. | [optional] [default to 10]
 **cursor** | **String**| The cursor, or position, for the next page of objects. See the documentation for the &#x60;cursor&#x60; parameter. | [optional]
 **where** | [**List&lt;String&gt;**](String.md)| Include in the response only objects where certain conditions are true. See the the documentation for the &#x60;where&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**DmrClusterLinkChannelsResponse**](DmrClusterLinkChannelsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getDmrClusterLinkRemoteAddress"></a>
# **getDmrClusterLinkRemoteAddress**
> DmrClusterLinkRemoteAddressResponse getDmrClusterLinkRemoteAddress(dmrClusterName, remoteNodeName, remoteAddress, select)

Get a Remote Address object.

Get a Remote Address object.  Each Remote Address, consisting of a FQDN or IP address and optional port, is used to connect to the remote node for this Link. Up to 4 addresses may be provided for each Link, and will be tried on a round-robin basis.   Attribute|Identifying|Deprecated :---|:---:|:---: dmrClusterName|x| remoteAddress|x| remoteNodeName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DmrClusterApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DmrClusterApi apiInstance = new DmrClusterApi();
String dmrClusterName = "dmrClusterName_example"; // String | The name of the Cluster.
String remoteNodeName = "remoteNodeName_example"; // String | The name of the node at the remote end of the Link.
String remoteAddress = "remoteAddress_example"; // String | The FQDN or IP address (and optional port) of the remote node. If a port is not provided, it will vary based on the transport encoding: 55555 (plain-text), 55443 (encrypted), or 55003 (compressed).
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    DmrClusterLinkRemoteAddressResponse result = apiInstance.getDmrClusterLinkRemoteAddress(dmrClusterName, remoteNodeName, remoteAddress, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DmrClusterApi#getDmrClusterLinkRemoteAddress");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **dmrClusterName** | **String**| The name of the Cluster. |
 **remoteNodeName** | **String**| The name of the node at the remote end of the Link. |
 **remoteAddress** | **String**| The FQDN or IP address (and optional port) of the remote node. If a port is not provided, it will vary based on the transport encoding: 55555 (plain-text), 55443 (encrypted), or 55003 (compressed). |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**DmrClusterLinkRemoteAddressResponse**](DmrClusterLinkRemoteAddressResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getDmrClusterLinkRemoteAddresses"></a>
# **getDmrClusterLinkRemoteAddresses**
> DmrClusterLinkRemoteAddressesResponse getDmrClusterLinkRemoteAddresses(dmrClusterName, remoteNodeName, where, select)

Get a list of Remote Address objects.

Get a list of Remote Address objects.  Each Remote Address, consisting of a FQDN or IP address and optional port, is used to connect to the remote node for this Link. Up to 4 addresses may be provided for each Link, and will be tried on a round-robin basis.   Attribute|Identifying|Deprecated :---|:---:|:---: dmrClusterName|x| remoteAddress|x| remoteNodeName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DmrClusterApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DmrClusterApi apiInstance = new DmrClusterApi();
String dmrClusterName = "dmrClusterName_example"; // String | The name of the Cluster.
String remoteNodeName = "remoteNodeName_example"; // String | The name of the node at the remote end of the Link.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    DmrClusterLinkRemoteAddressesResponse result = apiInstance.getDmrClusterLinkRemoteAddresses(dmrClusterName, remoteNodeName, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DmrClusterApi#getDmrClusterLinkRemoteAddresses");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **dmrClusterName** | **String**| The name of the Cluster. |
 **remoteNodeName** | **String**| The name of the node at the remote end of the Link. |
 **where** | [**List&lt;String&gt;**](String.md)| Include in the response only objects where certain conditions are true. See the the documentation for the &#x60;where&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**DmrClusterLinkRemoteAddressesResponse**](DmrClusterLinkRemoteAddressesResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getDmrClusterLinkTlsTrustedCommonName"></a>
# **getDmrClusterLinkTlsTrustedCommonName**
> DmrClusterLinkTlsTrustedCommonNameResponse getDmrClusterLinkTlsTrustedCommonName(dmrClusterName, remoteNodeName, tlsTrustedCommonName, select)

Get a Trusted Common Name object.

Get a Trusted Common Name object.  The Trusted Common Names for the Link are used by encrypted transports to verify the name in the certificate presented by the remote node. They must include the common name of the remote node&#39;s server certificate or client certificate, depending upon the initiator of the connection.   Attribute|Identifying|Deprecated :---|:---:|:---: dmrClusterName|x|x remoteNodeName|x|x tlsTrustedCommonName|x|x    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.18. Common Name validation has been replaced by Server Certificate Name validation.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DmrClusterApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DmrClusterApi apiInstance = new DmrClusterApi();
String dmrClusterName = "dmrClusterName_example"; // String | The name of the Cluster.
String remoteNodeName = "remoteNodeName_example"; // String | The name of the node at the remote end of the Link.
String tlsTrustedCommonName = "tlsTrustedCommonName_example"; // String | The expected trusted common name of the remote certificate.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    DmrClusterLinkTlsTrustedCommonNameResponse result = apiInstance.getDmrClusterLinkTlsTrustedCommonName(dmrClusterName, remoteNodeName, tlsTrustedCommonName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DmrClusterApi#getDmrClusterLinkTlsTrustedCommonName");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **dmrClusterName** | **String**| The name of the Cluster. |
 **remoteNodeName** | **String**| The name of the node at the remote end of the Link. |
 **tlsTrustedCommonName** | **String**| The expected trusted common name of the remote certificate. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**DmrClusterLinkTlsTrustedCommonNameResponse**](DmrClusterLinkTlsTrustedCommonNameResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getDmrClusterLinkTlsTrustedCommonNames"></a>
# **getDmrClusterLinkTlsTrustedCommonNames**
> DmrClusterLinkTlsTrustedCommonNamesResponse getDmrClusterLinkTlsTrustedCommonNames(dmrClusterName, remoteNodeName, where, select)

Get a list of Trusted Common Name objects.

Get a list of Trusted Common Name objects.  The Trusted Common Names for the Link are used by encrypted transports to verify the name in the certificate presented by the remote node. They must include the common name of the remote node&#39;s server certificate or client certificate, depending upon the initiator of the connection.   Attribute|Identifying|Deprecated :---|:---:|:---: dmrClusterName|x|x remoteNodeName|x|x tlsTrustedCommonName|x|x    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.18. Common Name validation has been replaced by Server Certificate Name validation.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DmrClusterApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DmrClusterApi apiInstance = new DmrClusterApi();
String dmrClusterName = "dmrClusterName_example"; // String | The name of the Cluster.
String remoteNodeName = "remoteNodeName_example"; // String | The name of the node at the remote end of the Link.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    DmrClusterLinkTlsTrustedCommonNamesResponse result = apiInstance.getDmrClusterLinkTlsTrustedCommonNames(dmrClusterName, remoteNodeName, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DmrClusterApi#getDmrClusterLinkTlsTrustedCommonNames");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **dmrClusterName** | **String**| The name of the Cluster. |
 **remoteNodeName** | **String**| The name of the node at the remote end of the Link. |
 **where** | [**List&lt;String&gt;**](String.md)| Include in the response only objects where certain conditions are true. See the the documentation for the &#x60;where&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**DmrClusterLinkTlsTrustedCommonNamesResponse**](DmrClusterLinkTlsTrustedCommonNamesResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getDmrClusterLinks"></a>
# **getDmrClusterLinks**
> DmrClusterLinksResponse getDmrClusterLinks(dmrClusterName, count, cursor, where, select)

Get a list of Link objects.

Get a list of Link objects.  A Link connects nodes (either within a Cluster or between two different Clusters) and allows them to exchange topology information, subscriptions and data.   Attribute|Identifying|Deprecated :---|:---:|:---: dmrClusterName|x| remoteNodeName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DmrClusterApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DmrClusterApi apiInstance = new DmrClusterApi();
String dmrClusterName = "dmrClusterName_example"; // String | The name of the Cluster.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    DmrClusterLinksResponse result = apiInstance.getDmrClusterLinks(dmrClusterName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DmrClusterApi#getDmrClusterLinks");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **dmrClusterName** | **String**| The name of the Cluster. |
 **count** | **Integer**| Limit the count of objects in the response. See the documentation for the &#x60;count&#x60; parameter. | [optional] [default to 10]
 **cursor** | **String**| The cursor, or position, for the next page of objects. See the documentation for the &#x60;cursor&#x60; parameter. | [optional]
 **where** | [**List&lt;String&gt;**](String.md)| Include in the response only objects where certain conditions are true. See the the documentation for the &#x60;where&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**DmrClusterLinksResponse**](DmrClusterLinksResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getDmrClusterTopologyIssue"></a>
# **getDmrClusterTopologyIssue**
> DmrClusterTopologyIssueResponse getDmrClusterTopologyIssue(dmrClusterName, topologyIssue, select)

Get a Cluster Topology Issue object.

Get a Cluster Topology Issue object.  A Cluster Topology Issue indicates incorrect or inconsistent configuration within the DMR network. Such issues will cause messages to be misdelivered or lost.   Attribute|Identifying|Deprecated :---|:---:|:---: dmrClusterName|x| topologyIssue|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DmrClusterApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DmrClusterApi apiInstance = new DmrClusterApi();
String dmrClusterName = "dmrClusterName_example"; // String | The name of the Cluster.
String topologyIssue = "topologyIssue_example"; // String | The topology issue discovered in the Cluster. A topology issue indicates incorrect or inconsistent configuration within the DMR network. Such issues will cause messages to be misdelivered or lost.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    DmrClusterTopologyIssueResponse result = apiInstance.getDmrClusterTopologyIssue(dmrClusterName, topologyIssue, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DmrClusterApi#getDmrClusterTopologyIssue");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **dmrClusterName** | **String**| The name of the Cluster. |
 **topologyIssue** | **String**| The topology issue discovered in the Cluster. A topology issue indicates incorrect or inconsistent configuration within the DMR network. Such issues will cause messages to be misdelivered or lost. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**DmrClusterTopologyIssueResponse**](DmrClusterTopologyIssueResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getDmrClusterTopologyIssues"></a>
# **getDmrClusterTopologyIssues**
> DmrClusterTopologyIssuesResponse getDmrClusterTopologyIssues(dmrClusterName, count, cursor, where, select)

Get a list of Cluster Topology Issue objects.

Get a list of Cluster Topology Issue objects.  A Cluster Topology Issue indicates incorrect or inconsistent configuration within the DMR network. Such issues will cause messages to be misdelivered or lost.   Attribute|Identifying|Deprecated :---|:---:|:---: dmrClusterName|x| topologyIssue|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DmrClusterApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DmrClusterApi apiInstance = new DmrClusterApi();
String dmrClusterName = "dmrClusterName_example"; // String | The name of the Cluster.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    DmrClusterTopologyIssuesResponse result = apiInstance.getDmrClusterTopologyIssues(dmrClusterName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DmrClusterApi#getDmrClusterTopologyIssues");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **dmrClusterName** | **String**| The name of the Cluster. |
 **count** | **Integer**| Limit the count of objects in the response. See the documentation for the &#x60;count&#x60; parameter. | [optional] [default to 10]
 **cursor** | **String**| The cursor, or position, for the next page of objects. See the documentation for the &#x60;cursor&#x60; parameter. | [optional]
 **where** | [**List&lt;String&gt;**](String.md)| Include in the response only objects where certain conditions are true. See the the documentation for the &#x60;where&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**DmrClusterTopologyIssuesResponse**](DmrClusterTopologyIssuesResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getDmrClusters"></a>
# **getDmrClusters**
> DmrClustersResponse getDmrClusters(count, cursor, where, select)

Get a list of Cluster objects.

Get a list of Cluster objects.  A Cluster is a provisioned object on a message broker that contains global DMR configuration parameters.   Attribute|Identifying|Deprecated :---|:---:|:---: dmrClusterName|x| tlsServerCertEnforceTrustedCommonNameEnabled||x    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.DmrClusterApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DmrClusterApi apiInstance = new DmrClusterApi();
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    DmrClustersResponse result = apiInstance.getDmrClusters(count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DmrClusterApi#getDmrClusters");
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

[**DmrClustersResponse**](DmrClustersResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

