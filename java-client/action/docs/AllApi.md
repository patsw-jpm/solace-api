# AllApi

All URIs are relative to *http://www.solace.com/SEMP/v2/action*

Method | HTTP request | Description
------------- | ------------- | -------------
[**doAboutUserLogout**](AllApi.md#doAboutUserLogout) | **PUT** /about/user/logout | Logout of the current session.
[**doCertAuthorityRefreshCrl**](AllApi.md#doCertAuthorityRefreshCrl) | **PUT** /certAuthorities/{certAuthorityName}/refreshCrl | Refresh the CRL file for the Certificate Authority.
[**doClientCertAuthorityRefreshCrl**](AllApi.md#doClientCertAuthorityRefreshCrl) | **PUT** /clientCertAuthorities/{certAuthorityName}/refreshCrl | Refresh the CRL file for the Client Certificate Authority.
[**doConfigSyncAssertLeaderMsgVpn**](AllApi.md#doConfigSyncAssertLeaderMsgVpn) | **PUT** /configSyncAssertLeaderMsgVpn | Assert leadership of the specified Config Sync table, forcing any other leader&#39;s content to be overwritten with our own. Use whenever a High Availability pair fall out of sync. Config Sync must be a leader for the selected table.
[**doConfigSyncAssertLeaderRouter**](AllApi.md#doConfigSyncAssertLeaderRouter) | **PUT** /configSyncAssertLeaderRouter | Assert leadership of the specified Config Sync table, forcing any other leader&#39;s content to be overwritten with our own. Use whenever a High Availability pair fall out of sync. Config Sync must be a leader for the selected table.
[**doConfigSyncResyncFollowerMsgVpn**](AllApi.md#doConfigSyncResyncFollowerMsgVpn) | **PUT** /configSyncResyncFollowerMsgVpn | Resync the selected Config Sync table, forcing this follower&#39;s content to be overwritten with that from a leader. Config Sync must be a follower for the selected table.
[**doConfigSyncResyncLeaderMsgVpn**](AllApi.md#doConfigSyncResyncLeaderMsgVpn) | **PUT** /configSyncResyncLeaderMsgVpn | Resync the selected Config Sync table, forcing this leader&#39;s content to be overwritten with that from a leader. Config Sync must be a leader for the selected table.
[**doConfigSyncResyncLeaderRouter**](AllApi.md#doConfigSyncResyncLeaderRouter) | **PUT** /configSyncResyncLeaderRouter | Resync the selected Config Sync table, forcing this leader&#39;s content to be overwritten with that from a leader. Config Sync must be a leader for the selected table.
[**doGuaranteedMsgingDefragmentMsgSpoolFilesStart**](AllApi.md#doGuaranteedMsgingDefragmentMsgSpoolFilesStart) | **PUT** /guaranteedMsgingDefragmentMsgSpoolFilesStart | Start a spool file defragmentation run.
[**doGuaranteedMsgingDefragmentMsgSpoolFilesStop**](AllApi.md#doGuaranteedMsgingDefragmentMsgSpoolFilesStop) | **PUT** /guaranteedMsgingDefragmentMsgSpoolFilesStop | Stop a spool file defragmentation run.
[**doMsgVpnAuthenticationOauthProviderClearStats**](AllApi.md#doMsgVpnAuthenticationOauthProviderClearStats) | **PUT** /msgVpns/{msgVpnName}/authenticationOauthProviders/{oauthProviderName}/clearStats | Clear the statistics for the OAuth Provider.
[**doMsgVpnBridgeClearEvent**](AllApi.md#doMsgVpnBridgeClearEvent) | **PUT** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/clearEvent | Clear an event for the Bridge so it can be generated anew.
[**doMsgVpnBridgeClearStats**](AllApi.md#doMsgVpnBridgeClearStats) | **PUT** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/clearStats | Clear the statistics for the Bridge.
[**doMsgVpnBridgeDisconnect**](AllApi.md#doMsgVpnBridgeDisconnect) | **PUT** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/disconnect | Disconnect the Bridge.
[**doMsgVpnClearMsgSpoolStats**](AllApi.md#doMsgVpnClearMsgSpoolStats) | **PUT** /msgVpns/{msgVpnName}/clearMsgSpoolStats | Clear the message spool statistics for the Message VPN.
[**doMsgVpnClearReplicationStats**](AllApi.md#doMsgVpnClearReplicationStats) | **PUT** /msgVpns/{msgVpnName}/clearReplicationStats | Clear the replication statistics for the Message VPN.
[**doMsgVpnClearServiceStats**](AllApi.md#doMsgVpnClearServiceStats) | **PUT** /msgVpns/{msgVpnName}/clearServiceStats | Clear the service statistics for the Message VPN.
[**doMsgVpnClearStats**](AllApi.md#doMsgVpnClearStats) | **PUT** /msgVpns/{msgVpnName}/clearStats | Clear the client statistics for the Message VPN.
[**doMsgVpnClientClearEvent**](AllApi.md#doMsgVpnClientClearEvent) | **PUT** /msgVpns/{msgVpnName}/clients/{clientName}/clearEvent | Clear an event for the Client so it can be generated anew.
[**doMsgVpnClientClearStats**](AllApi.md#doMsgVpnClientClearStats) | **PUT** /msgVpns/{msgVpnName}/clients/{clientName}/clearStats | Clear the statistics for the Client.
[**doMsgVpnClientDisconnect**](AllApi.md#doMsgVpnClientDisconnect) | **PUT** /msgVpns/{msgVpnName}/clients/{clientName}/disconnect | Disconnect the Client.
[**doMsgVpnClientTransactedSessionDelete**](AllApi.md#doMsgVpnClientTransactedSessionDelete) | **PUT** /msgVpns/{msgVpnName}/clients/{clientName}/transactedSessions/{sessionName}/delete | Delete the Transacted Session.
[**doMsgVpnDistributedCacheClusterInstanceBackupCachedMsgs**](AllApi.md#doMsgVpnDistributedCacheClusterInstanceBackupCachedMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/backupCachedMsgs | Backup cached messages of the Cache Instance to disk.
[**doMsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs**](AllApi.md#doMsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/cancelBackupCachedMsgs | Cancel the backup of cached messages from the Cache Instance.
[**doMsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs**](AllApi.md#doMsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/cancelRestoreCachedMsgs | Cancel the restore of cached messages to the Cache Instance.
[**doMsgVpnDistributedCacheClusterInstanceClearEvent**](AllApi.md#doMsgVpnDistributedCacheClusterInstanceClearEvent) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/clearEvent | Clear an event for the Cache Instance so it can be generated anew.
[**doMsgVpnDistributedCacheClusterInstanceClearStats**](AllApi.md#doMsgVpnDistributedCacheClusterInstanceClearStats) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/clearStats | Clear the statistics for the Cache Instance.
[**doMsgVpnDistributedCacheClusterInstanceDeleteMsgs**](AllApi.md#doMsgVpnDistributedCacheClusterInstanceDeleteMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/deleteMsgs | Delete messages covered by the given topic in the Cache Instance.
[**doMsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs**](AllApi.md#doMsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/restoreCachedMsgs | Restore cached messages for the Cache Instance from disk.
[**doMsgVpnDistributedCacheClusterInstanceStart**](AllApi.md#doMsgVpnDistributedCacheClusterInstanceStart) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/start | Start the Cache Instance.
[**doMsgVpnMqttSessionClearStats**](AllApi.md#doMsgVpnMqttSessionClearStats) | **PUT** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter}/clearStats | Clear the statistics for the MQTT Session.
[**doMsgVpnQueueCancelReplay**](AllApi.md#doMsgVpnQueueCancelReplay) | **PUT** /msgVpns/{msgVpnName}/queues/{queueName}/cancelReplay | Cancel the replay of messages to the Queue.
[**doMsgVpnQueueClearStats**](AllApi.md#doMsgVpnQueueClearStats) | **PUT** /msgVpns/{msgVpnName}/queues/{queueName}/clearStats | Clear the statistics for the Queue.
[**doMsgVpnQueueMsgDelete**](AllApi.md#doMsgVpnQueueMsgDelete) | **PUT** /msgVpns/{msgVpnName}/queues/{queueName}/msgs/{msgId}/delete | Delete the Message from the Queue.
[**doMsgVpnQueueStartReplay**](AllApi.md#doMsgVpnQueueStartReplay) | **PUT** /msgVpns/{msgVpnName}/queues/{queueName}/startReplay | Start the replay of messages to the Queue.
[**doMsgVpnReplayLogTrimLoggedMsgs**](AllApi.md#doMsgVpnReplayLogTrimLoggedMsgs) | **PUT** /msgVpns/{msgVpnName}/replayLogs/{replayLogName}/trimLoggedMsgs | Trim (delete) messages from the Replay Log.
[**doMsgVpnRestDeliveryPointRestConsumerClearStats**](AllApi.md#doMsgVpnRestDeliveryPointRestConsumerClearStats) | **PUT** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers/{restConsumerName}/clearStats | Clear the statistics for the REST Consumer.
[**doMsgVpnTopicEndpointCancelReplay**](AllApi.md#doMsgVpnTopicEndpointCancelReplay) | **PUT** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/cancelReplay | Cancel the replay of messages to the Topic Endpoint.
[**doMsgVpnTopicEndpointClearStats**](AllApi.md#doMsgVpnTopicEndpointClearStats) | **PUT** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/clearStats | Clear the statistics for the Topic Endpoint.
[**doMsgVpnTopicEndpointMsgDelete**](AllApi.md#doMsgVpnTopicEndpointMsgDelete) | **PUT** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/msgs/{msgId}/delete | Delete the Message from the Topic Endpoint.
[**doMsgVpnTopicEndpointStartReplay**](AllApi.md#doMsgVpnTopicEndpointStartReplay) | **PUT** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/startReplay | Start the replay of messages to the Topic Endpoint.
[**doMsgVpnTransactionCommit**](AllApi.md#doMsgVpnTransactionCommit) | **PUT** /msgVpns/{msgVpnName}/transactions/{xid}/commit | Commit the Transaction.
[**doMsgVpnTransactionDelete**](AllApi.md#doMsgVpnTransactionDelete) | **PUT** /msgVpns/{msgVpnName}/transactions/{xid}/delete | Delete the Transaction.
[**doMsgVpnTransactionRollback**](AllApi.md#doMsgVpnTransactionRollback) | **PUT** /msgVpns/{msgVpnName}/transactions/{xid}/rollback | Rollback the Transaction.
[**doSessionDelete**](AllApi.md#doSessionDelete) | **PUT** /sessions/{sessionUsername},{sessionId}/delete | Delete the session.
[**getAbout**](AllApi.md#getAbout) | **GET** /about | Get an About object.
[**getAboutApi**](AllApi.md#getAboutApi) | **GET** /about/api | Get an API Description object.
[**getAboutUser**](AllApi.md#getAboutUser) | **GET** /about/user | Get a User object.
[**getAboutUserMsgVpn**](AllApi.md#getAboutUserMsgVpn) | **GET** /about/user/msgVpns/{msgVpnName} | Get a User Message VPN object.
[**getAboutUserMsgVpns**](AllApi.md#getAboutUserMsgVpns) | **GET** /about/user/msgVpns | Get a list of User Message VPN objects.
[**getBroker**](AllApi.md#getBroker) | **GET** / | Get a Broker object.
[**getCertAuthorities**](AllApi.md#getCertAuthorities) | **GET** /certAuthorities | Get a list of Certificate Authority objects.
[**getCertAuthority**](AllApi.md#getCertAuthority) | **GET** /certAuthorities/{certAuthorityName} | Get a Certificate Authority object.
[**getClientCertAuthorities**](AllApi.md#getClientCertAuthorities) | **GET** /clientCertAuthorities | Get a list of Client Certificate Authority objects.
[**getClientCertAuthority**](AllApi.md#getClientCertAuthority) | **GET** /clientCertAuthorities/{certAuthorityName} | Get a Client Certificate Authority object.
[**getMsgVpn**](AllApi.md#getMsgVpn) | **GET** /msgVpns/{msgVpnName} | Get a Message VPN object.
[**getMsgVpnAuthenticationOauthProvider**](AllApi.md#getMsgVpnAuthenticationOauthProvider) | **GET** /msgVpns/{msgVpnName}/authenticationOauthProviders/{oauthProviderName} | Get an OAuth Provider object.
[**getMsgVpnAuthenticationOauthProviders**](AllApi.md#getMsgVpnAuthenticationOauthProviders) | **GET** /msgVpns/{msgVpnName}/authenticationOauthProviders | Get a list of OAuth Provider objects.
[**getMsgVpnBridge**](AllApi.md#getMsgVpnBridge) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter} | Get a Bridge object.
[**getMsgVpnBridges**](AllApi.md#getMsgVpnBridges) | **GET** /msgVpns/{msgVpnName}/bridges | Get a list of Bridge objects.
[**getMsgVpnClient**](AllApi.md#getMsgVpnClient) | **GET** /msgVpns/{msgVpnName}/clients/{clientName} | Get a Client object.
[**getMsgVpnClientTransactedSession**](AllApi.md#getMsgVpnClientTransactedSession) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/transactedSessions/{sessionName} | Get a Client Transacted Session object.
[**getMsgVpnClientTransactedSessions**](AllApi.md#getMsgVpnClientTransactedSessions) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/transactedSessions | Get a list of Client Transacted Session objects.
[**getMsgVpnClients**](AllApi.md#getMsgVpnClients) | **GET** /msgVpns/{msgVpnName}/clients | Get a list of Client objects.
[**getMsgVpnDistributedCache**](AllApi.md#getMsgVpnDistributedCache) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName} | Get a Distributed Cache object.
[**getMsgVpnDistributedCacheCluster**](AllApi.md#getMsgVpnDistributedCacheCluster) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName} | Get a Cache Cluster object.
[**getMsgVpnDistributedCacheClusterInstance**](AllApi.md#getMsgVpnDistributedCacheClusterInstance) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName} | Get a Cache Instance object.
[**getMsgVpnDistributedCacheClusterInstances**](AllApi.md#getMsgVpnDistributedCacheClusterInstances) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances | Get a list of Cache Instance objects.
[**getMsgVpnDistributedCacheClusters**](AllApi.md#getMsgVpnDistributedCacheClusters) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters | Get a list of Cache Cluster objects.
[**getMsgVpnDistributedCaches**](AllApi.md#getMsgVpnDistributedCaches) | **GET** /msgVpns/{msgVpnName}/distributedCaches | Get a list of Distributed Cache objects.
[**getMsgVpnMqttSession**](AllApi.md#getMsgVpnMqttSession) | **GET** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter} | Get an MQTT Session object.
[**getMsgVpnMqttSessions**](AllApi.md#getMsgVpnMqttSessions) | **GET** /msgVpns/{msgVpnName}/mqttSessions | Get a list of MQTT Session objects.
[**getMsgVpnQueue**](AllApi.md#getMsgVpnQueue) | **GET** /msgVpns/{msgVpnName}/queues/{queueName} | Get a Queue object.
[**getMsgVpnQueueMsg**](AllApi.md#getMsgVpnQueueMsg) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/msgs/{msgId} | Get a Queue Message object.
[**getMsgVpnQueueMsgs**](AllApi.md#getMsgVpnQueueMsgs) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/msgs | Get a list of Queue Message objects.
[**getMsgVpnQueues**](AllApi.md#getMsgVpnQueues) | **GET** /msgVpns/{msgVpnName}/queues | Get a list of Queue objects.
[**getMsgVpnReplayLog**](AllApi.md#getMsgVpnReplayLog) | **GET** /msgVpns/{msgVpnName}/replayLogs/{replayLogName} | Get a Replay Log object.
[**getMsgVpnReplayLogs**](AllApi.md#getMsgVpnReplayLogs) | **GET** /msgVpns/{msgVpnName}/replayLogs | Get a list of Replay Log objects.
[**getMsgVpnRestDeliveryPoint**](AllApi.md#getMsgVpnRestDeliveryPoint) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName} | Get a REST Delivery Point object.
[**getMsgVpnRestDeliveryPointRestConsumer**](AllApi.md#getMsgVpnRestDeliveryPointRestConsumer) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers/{restConsumerName} | Get a REST Consumer object.
[**getMsgVpnRestDeliveryPointRestConsumers**](AllApi.md#getMsgVpnRestDeliveryPointRestConsumers) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers | Get a list of REST Consumer objects.
[**getMsgVpnRestDeliveryPoints**](AllApi.md#getMsgVpnRestDeliveryPoints) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints | Get a list of REST Delivery Point objects.
[**getMsgVpnTopicEndpoint**](AllApi.md#getMsgVpnTopicEndpoint) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName} | Get a Topic Endpoint object.
[**getMsgVpnTopicEndpointMsg**](AllApi.md#getMsgVpnTopicEndpointMsg) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/msgs/{msgId} | Get a Topic Endpoint Message object.
[**getMsgVpnTopicEndpointMsgs**](AllApi.md#getMsgVpnTopicEndpointMsgs) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/msgs | Get a list of Topic Endpoint Message objects.
[**getMsgVpnTopicEndpoints**](AllApi.md#getMsgVpnTopicEndpoints) | **GET** /msgVpns/{msgVpnName}/topicEndpoints | Get a list of Topic Endpoint objects.
[**getMsgVpnTransaction**](AllApi.md#getMsgVpnTransaction) | **GET** /msgVpns/{msgVpnName}/transactions/{xid} | Get a Replicated Local Transaction or XA Transaction object.
[**getMsgVpnTransactions**](AllApi.md#getMsgVpnTransactions) | **GET** /msgVpns/{msgVpnName}/transactions | Get a list of Replicated Local Transaction or XA Transaction objects.
[**getMsgVpns**](AllApi.md#getMsgVpns) | **GET** /msgVpns | Get a list of Message VPN objects.
[**getSession**](AllApi.md#getSession) | **GET** /sessions/{sessionUsername},{sessionId} | Get a Session object.
[**getSessions**](AllApi.md#getSessions) | **GET** /sessions | Get a list of Session objects.


<a name="doAboutUserLogout"></a>
# **doAboutUserLogout**
> SempMetaOnlyResponse doAboutUserLogout(body)

Logout of the current session.

Logout of the current session.    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation.  This has been available since 2.21.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
AboutUserLogout body = new AboutUserLogout(); // AboutUserLogout | The Logout action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doAboutUserLogout(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doAboutUserLogout");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AboutUserLogout**](AboutUserLogout.md)| The Logout action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doCertAuthorityRefreshCrl"></a>
# **doCertAuthorityRefreshCrl**
> SempMetaOnlyResponse doCertAuthorityRefreshCrl(certAuthorityName, body)

Refresh the CRL file for the Certificate Authority.

Refresh the CRL file for the Certificate Authority.    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been deprecated since 2.19. Replaced by clientCertAuthorities.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String certAuthorityName = "certAuthorityName_example"; // String | The name of the Certificate Authority.
CertAuthorityRefreshCrl body = new CertAuthorityRefreshCrl(); // CertAuthorityRefreshCrl | The Refresh CRL action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doCertAuthorityRefreshCrl(certAuthorityName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doCertAuthorityRefreshCrl");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **certAuthorityName** | **String**| The name of the Certificate Authority. |
 **body** | [**CertAuthorityRefreshCrl**](CertAuthorityRefreshCrl.md)| The Refresh CRL action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String certAuthorityName = "certAuthorityName_example"; // String | The name of the Certificate Authority.
ClientCertAuthorityRefreshCrl body = new ClientCertAuthorityRefreshCrl(); // ClientCertAuthorityRefreshCrl | The Refresh CRL action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doClientCertAuthorityRefreshCrl(certAuthorityName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doClientCertAuthorityRefreshCrl");
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

<a name="doConfigSyncAssertLeaderMsgVpn"></a>
# **doConfigSyncAssertLeaderMsgVpn**
> SempMetaOnlyResponse doConfigSyncAssertLeaderMsgVpn(body)

Assert leadership of the specified Config Sync table, forcing any other leader&#39;s content to be overwritten with our own. Use whenever a High Availability pair fall out of sync. Config Sync must be a leader for the selected table.

Assert leadership of the specified Config Sync table, forcing any other leader&#39;s content to be overwritten with our own. Use whenever a High Availability pair fall out of sync. Config Sync must be a leader for the selected table.   Attribute|Required|Deprecated :---|:---:|:---: msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.22.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
ConfigSyncAssertLeaderMsgVpn body = new ConfigSyncAssertLeaderMsgVpn(); // ConfigSyncAssertLeaderMsgVpn | The Assert Leader Message VPN action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doConfigSyncAssertLeaderMsgVpn(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doConfigSyncAssertLeaderMsgVpn");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ConfigSyncAssertLeaderMsgVpn**](ConfigSyncAssertLeaderMsgVpn.md)| The Assert Leader Message VPN action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doConfigSyncAssertLeaderRouter"></a>
# **doConfigSyncAssertLeaderRouter**
> SempMetaOnlyResponse doConfigSyncAssertLeaderRouter(body)

Assert leadership of the specified Config Sync table, forcing any other leader&#39;s content to be overwritten with our own. Use whenever a High Availability pair fall out of sync. Config Sync must be a leader for the selected table.

Assert leadership of the specified Config Sync table, forcing any other leader&#39;s content to be overwritten with our own. Use whenever a High Availability pair fall out of sync. Config Sync must be a leader for the selected table.    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.22.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
ConfigSyncAssertLeaderRouter body = new ConfigSyncAssertLeaderRouter(); // ConfigSyncAssertLeaderRouter | The Assert Leader Router action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doConfigSyncAssertLeaderRouter(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doConfigSyncAssertLeaderRouter");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ConfigSyncAssertLeaderRouter**](ConfigSyncAssertLeaderRouter.md)| The Assert Leader Router action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doConfigSyncResyncFollowerMsgVpn"></a>
# **doConfigSyncResyncFollowerMsgVpn**
> SempMetaOnlyResponse doConfigSyncResyncFollowerMsgVpn(body)

Resync the selected Config Sync table, forcing this follower&#39;s content to be overwritten with that from a leader. Config Sync must be a follower for the selected table.

Resync the selected Config Sync table, forcing this follower&#39;s content to be overwritten with that from a leader. Config Sync must be a follower for the selected table.   Attribute|Required|Deprecated :---|:---:|:---: msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.22.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
ConfigSyncResyncFollowerMsgVpn body = new ConfigSyncResyncFollowerMsgVpn(); // ConfigSyncResyncFollowerMsgVpn | The Resync Follower Message VPN action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doConfigSyncResyncFollowerMsgVpn(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doConfigSyncResyncFollowerMsgVpn");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ConfigSyncResyncFollowerMsgVpn**](ConfigSyncResyncFollowerMsgVpn.md)| The Resync Follower Message VPN action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doConfigSyncResyncLeaderMsgVpn"></a>
# **doConfigSyncResyncLeaderMsgVpn**
> SempMetaOnlyResponse doConfigSyncResyncLeaderMsgVpn(body)

Resync the selected Config Sync table, forcing this leader&#39;s content to be overwritten with that from a leader. Config Sync must be a leader for the selected table.

Resync the selected Config Sync table, forcing this leader&#39;s content to be overwritten with that from a leader. Config Sync must be a leader for the selected table.   Attribute|Required|Deprecated :---|:---:|:---: msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.22.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
ConfigSyncResyncLeaderMsgVpn body = new ConfigSyncResyncLeaderMsgVpn(); // ConfigSyncResyncLeaderMsgVpn | The Resync Leader Message VPN action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doConfigSyncResyncLeaderMsgVpn(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doConfigSyncResyncLeaderMsgVpn");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ConfigSyncResyncLeaderMsgVpn**](ConfigSyncResyncLeaderMsgVpn.md)| The Resync Leader Message VPN action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doConfigSyncResyncLeaderRouter"></a>
# **doConfigSyncResyncLeaderRouter**
> SempMetaOnlyResponse doConfigSyncResyncLeaderRouter(body)

Resync the selected Config Sync table, forcing this leader&#39;s content to be overwritten with that from a leader. Config Sync must be a leader for the selected table.

Resync the selected Config Sync table, forcing this leader&#39;s content to be overwritten with that from a leader. Config Sync must be a leader for the selected table.    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.22.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
ConfigSyncResyncLeaderRouter body = new ConfigSyncResyncLeaderRouter(); // ConfigSyncResyncLeaderRouter | The Resync Leader Router action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doConfigSyncResyncLeaderRouter(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doConfigSyncResyncLeaderRouter");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ConfigSyncResyncLeaderRouter**](ConfigSyncResyncLeaderRouter.md)| The Resync Leader Router action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doGuaranteedMsgingDefragmentMsgSpoolFilesStart"></a>
# **doGuaranteedMsgingDefragmentMsgSpoolFilesStart**
> SempMetaOnlyResponse doGuaranteedMsgingDefragmentMsgSpoolFilesStart(body)

Start a spool file defragmentation run.

Start a spool file defragmentation run.    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.18.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
GuaranteedMsgingDefragmentMsgSpoolFilesStart body = new GuaranteedMsgingDefragmentMsgSpoolFilesStart(); // GuaranteedMsgingDefragmentMsgSpoolFilesStart | The Start Defragmentation action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doGuaranteedMsgingDefragmentMsgSpoolFilesStart(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doGuaranteedMsgingDefragmentMsgSpoolFilesStart");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GuaranteedMsgingDefragmentMsgSpoolFilesStart**](GuaranteedMsgingDefragmentMsgSpoolFilesStart.md)| The Start Defragmentation action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doGuaranteedMsgingDefragmentMsgSpoolFilesStop"></a>
# **doGuaranteedMsgingDefragmentMsgSpoolFilesStop**
> SempMetaOnlyResponse doGuaranteedMsgingDefragmentMsgSpoolFilesStop(body)

Stop a spool file defragmentation run.

Stop a spool file defragmentation run.    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.18.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
GuaranteedMsgingDefragmentMsgSpoolFilesStop body = new GuaranteedMsgingDefragmentMsgSpoolFilesStop(); // GuaranteedMsgingDefragmentMsgSpoolFilesStop | The Stop Defragmentation action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doGuaranteedMsgingDefragmentMsgSpoolFilesStop(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doGuaranteedMsgingDefragmentMsgSpoolFilesStop");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GuaranteedMsgingDefragmentMsgSpoolFilesStop**](GuaranteedMsgingDefragmentMsgSpoolFilesStop.md)| The Stop Defragmentation action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnAuthenticationOauthProviderClearStats"></a>
# **doMsgVpnAuthenticationOauthProviderClearStats**
> SempMetaOnlyResponse doMsgVpnAuthenticationOauthProviderClearStats(msgVpnName, oauthProviderName, body)

Clear the statistics for the OAuth Provider.

Clear the statistics for the OAuth Provider.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.13.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String oauthProviderName = "oauthProviderName_example"; // String | The name of the OAuth Provider.
MsgVpnAuthenticationOauthProviderClearStats body = new MsgVpnAuthenticationOauthProviderClearStats(); // MsgVpnAuthenticationOauthProviderClearStats | The Clear Stats action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnAuthenticationOauthProviderClearStats(msgVpnName, oauthProviderName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnAuthenticationOauthProviderClearStats");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **oauthProviderName** | **String**| The name of the OAuth Provider. |
 **body** | [**MsgVpnAuthenticationOauthProviderClearStats**](MsgVpnAuthenticationOauthProviderClearStats.md)| The Clear Stats action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String bridgeName = "bridgeName_example"; // String | The name of the Bridge.
String bridgeVirtualRouter = "bridgeVirtualRouter_example"; // String | The virtual router of the Bridge.
MsgVpnBridgeClearEvent body = new MsgVpnBridgeClearEvent(); // MsgVpnBridgeClearEvent | The Clear Event action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnBridgeClearEvent(msgVpnName, bridgeName, bridgeVirtualRouter, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnBridgeClearEvent");
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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String bridgeName = "bridgeName_example"; // String | The name of the Bridge.
String bridgeVirtualRouter = "bridgeVirtualRouter_example"; // String | The virtual router of the Bridge.
MsgVpnBridgeClearStats body = new MsgVpnBridgeClearStats(); // MsgVpnBridgeClearStats | The Clear Stats action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnBridgeClearStats(msgVpnName, bridgeName, bridgeVirtualRouter, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnBridgeClearStats");
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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String bridgeName = "bridgeName_example"; // String | The name of the Bridge.
String bridgeVirtualRouter = "bridgeVirtualRouter_example"; // String | The virtual router of the Bridge.
MsgVpnBridgeDisconnect body = new MsgVpnBridgeDisconnect(); // MsgVpnBridgeDisconnect | The Disconnect action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnBridgeDisconnect(msgVpnName, bridgeName, bridgeVirtualRouter, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnBridgeDisconnect");
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

<a name="doMsgVpnClearMsgSpoolStats"></a>
# **doMsgVpnClearMsgSpoolStats**
> SempMetaOnlyResponse doMsgVpnClearMsgSpoolStats(msgVpnName, body)

Clear the message spool statistics for the Message VPN.

Clear the message spool statistics for the Message VPN.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
MsgVpnClearMsgSpoolStats body = new MsgVpnClearMsgSpoolStats(); // MsgVpnClearMsgSpoolStats | The Clear Stats action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnClearMsgSpoolStats(msgVpnName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnClearMsgSpoolStats");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **body** | [**MsgVpnClearMsgSpoolStats**](MsgVpnClearMsgSpoolStats.md)| The Clear Stats action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnClearReplicationStats"></a>
# **doMsgVpnClearReplicationStats**
> SempMetaOnlyResponse doMsgVpnClearReplicationStats(msgVpnName, body)

Clear the replication statistics for the Message VPN.

Clear the replication statistics for the Message VPN.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
MsgVpnClearReplicationStats body = new MsgVpnClearReplicationStats(); // MsgVpnClearReplicationStats | The Clear Stats action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnClearReplicationStats(msgVpnName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnClearReplicationStats");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **body** | [**MsgVpnClearReplicationStats**](MsgVpnClearReplicationStats.md)| The Clear Stats action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnClearServiceStats"></a>
# **doMsgVpnClearServiceStats**
> SempMetaOnlyResponse doMsgVpnClearServiceStats(msgVpnName, body)

Clear the service statistics for the Message VPN.

Clear the service statistics for the Message VPN.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
MsgVpnClearServiceStats body = new MsgVpnClearServiceStats(); // MsgVpnClearServiceStats | The Clear Stats action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnClearServiceStats(msgVpnName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnClearServiceStats");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **body** | [**MsgVpnClearServiceStats**](MsgVpnClearServiceStats.md)| The Clear Stats action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnClearStats"></a>
# **doMsgVpnClearStats**
> SempMetaOnlyResponse doMsgVpnClearStats(msgVpnName, body)

Clear the client statistics for the Message VPN.

Clear the client statistics for the Message VPN.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
MsgVpnClearStats body = new MsgVpnClearStats(); // MsgVpnClearStats | The Clear Stats action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnClearStats(msgVpnName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnClearStats");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **body** | [**MsgVpnClearStats**](MsgVpnClearStats.md)| The Clear Stats action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnClientClearEvent"></a>
# **doMsgVpnClientClearEvent**
> SempMetaOnlyResponse doMsgVpnClientClearEvent(msgVpnName, clientName, body)

Clear an event for the Client so it can be generated anew.

Clear an event for the Client so it can be generated anew.   Attribute|Required|Deprecated :---|:---:|:---: eventName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String clientName = "clientName_example"; // String | The name of the Client.
MsgVpnClientClearEvent body = new MsgVpnClientClearEvent(); // MsgVpnClientClearEvent | The Clear Event action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnClientClearEvent(msgVpnName, clientName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnClientClearEvent");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **clientName** | **String**| The name of the Client. |
 **body** | [**MsgVpnClientClearEvent**](MsgVpnClientClearEvent.md)| The Clear Event action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnClientClearStats"></a>
# **doMsgVpnClientClearStats**
> SempMetaOnlyResponse doMsgVpnClientClearStats(msgVpnName, clientName, body)

Clear the statistics for the Client.

Clear the statistics for the Client.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String clientName = "clientName_example"; // String | The name of the Client.
MsgVpnClientClearStats body = new MsgVpnClientClearStats(); // MsgVpnClientClearStats | The Clear Stats action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnClientClearStats(msgVpnName, clientName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnClientClearStats");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **clientName** | **String**| The name of the Client. |
 **body** | [**MsgVpnClientClearStats**](MsgVpnClientClearStats.md)| The Clear Stats action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnClientDisconnect"></a>
# **doMsgVpnClientDisconnect**
> SempMetaOnlyResponse doMsgVpnClientDisconnect(msgVpnName, clientName, body)

Disconnect the Client.

Disconnect the Client.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String clientName = "clientName_example"; // String | The name of the Client.
MsgVpnClientDisconnect body = new MsgVpnClientDisconnect(); // MsgVpnClientDisconnect | The Disconnect action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnClientDisconnect(msgVpnName, clientName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnClientDisconnect");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **clientName** | **String**| The name of the Client. |
 **body** | [**MsgVpnClientDisconnect**](MsgVpnClientDisconnect.md)| The Disconnect action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnClientTransactedSessionDelete"></a>
# **doMsgVpnClientTransactedSessionDelete**
> SempMetaOnlyResponse doMsgVpnClientTransactedSessionDelete(msgVpnName, clientName, sessionName, body)

Delete the Transacted Session.

Delete the Transacted Session.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String clientName = "clientName_example"; // String | The name of the Client.
String sessionName = "sessionName_example"; // String | The name of the Transacted Session.
MsgVpnClientTransactedSessionDelete body = new MsgVpnClientTransactedSessionDelete(); // MsgVpnClientTransactedSessionDelete | The Delete action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnClientTransactedSessionDelete(msgVpnName, clientName, sessionName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnClientTransactedSessionDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **clientName** | **String**| The name of the Client. |
 **sessionName** | **String**| The name of the Transacted Session. |
 **body** | [**MsgVpnClientTransactedSessionDelete**](MsgVpnClientTransactedSessionDelete.md)| The Delete action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String cacheName = "cacheName_example"; // String | The name of the Distributed Cache.
String clusterName = "clusterName_example"; // String | The name of the Cache Cluster.
String instanceName = "instanceName_example"; // String | The name of the Cache Instance.
MsgVpnDistributedCacheClusterInstanceBackupCachedMsgs body = new MsgVpnDistributedCacheClusterInstanceBackupCachedMsgs(); // MsgVpnDistributedCacheClusterInstanceBackupCachedMsgs | The Backup Cached Messages action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnDistributedCacheClusterInstanceBackupCachedMsgs(msgVpnName, cacheName, clusterName, instanceName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnDistributedCacheClusterInstanceBackupCachedMsgs");
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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String cacheName = "cacheName_example"; // String | The name of the Distributed Cache.
String clusterName = "clusterName_example"; // String | The name of the Cache Cluster.
String instanceName = "instanceName_example"; // String | The name of the Cache Instance.
MsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs body = new MsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs(); // MsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs | The Cancel Backup Cached Messages action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs(msgVpnName, cacheName, clusterName, instanceName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs");
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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String cacheName = "cacheName_example"; // String | The name of the Distributed Cache.
String clusterName = "clusterName_example"; // String | The name of the Cache Cluster.
String instanceName = "instanceName_example"; // String | The name of the Cache Instance.
MsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs body = new MsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs(); // MsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs | The Cancel Restore Cached Messages action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs(msgVpnName, cacheName, clusterName, instanceName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs");
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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String cacheName = "cacheName_example"; // String | The name of the Distributed Cache.
String clusterName = "clusterName_example"; // String | The name of the Cache Cluster.
String instanceName = "instanceName_example"; // String | The name of the Cache Instance.
MsgVpnDistributedCacheClusterInstanceClearEvent body = new MsgVpnDistributedCacheClusterInstanceClearEvent(); // MsgVpnDistributedCacheClusterInstanceClearEvent | The Clear Event action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnDistributedCacheClusterInstanceClearEvent(msgVpnName, cacheName, clusterName, instanceName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnDistributedCacheClusterInstanceClearEvent");
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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String cacheName = "cacheName_example"; // String | The name of the Distributed Cache.
String clusterName = "clusterName_example"; // String | The name of the Cache Cluster.
String instanceName = "instanceName_example"; // String | The name of the Cache Instance.
MsgVpnDistributedCacheClusterInstanceClearStats body = new MsgVpnDistributedCacheClusterInstanceClearStats(); // MsgVpnDistributedCacheClusterInstanceClearStats | The Clear Stats action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnDistributedCacheClusterInstanceClearStats(msgVpnName, cacheName, clusterName, instanceName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnDistributedCacheClusterInstanceClearStats");
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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String cacheName = "cacheName_example"; // String | The name of the Distributed Cache.
String clusterName = "clusterName_example"; // String | The name of the Cache Cluster.
String instanceName = "instanceName_example"; // String | The name of the Cache Instance.
MsgVpnDistributedCacheClusterInstanceDeleteMsgs body = new MsgVpnDistributedCacheClusterInstanceDeleteMsgs(); // MsgVpnDistributedCacheClusterInstanceDeleteMsgs | The Delete Messages action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnDistributedCacheClusterInstanceDeleteMsgs(msgVpnName, cacheName, clusterName, instanceName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnDistributedCacheClusterInstanceDeleteMsgs");
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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String cacheName = "cacheName_example"; // String | The name of the Distributed Cache.
String clusterName = "clusterName_example"; // String | The name of the Cache Cluster.
String instanceName = "instanceName_example"; // String | The name of the Cache Instance.
MsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs body = new MsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs(); // MsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs | The Restore Cached Messages action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs(msgVpnName, cacheName, clusterName, instanceName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs");
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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String cacheName = "cacheName_example"; // String | The name of the Distributed Cache.
String clusterName = "clusterName_example"; // String | The name of the Cache Cluster.
String instanceName = "instanceName_example"; // String | The name of the Cache Instance.
MsgVpnDistributedCacheClusterInstanceStart body = new MsgVpnDistributedCacheClusterInstanceStart(); // MsgVpnDistributedCacheClusterInstanceStart | The Start Cache Instance action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnDistributedCacheClusterInstanceStart(msgVpnName, cacheName, clusterName, instanceName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnDistributedCacheClusterInstanceStart");
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

<a name="doMsgVpnMqttSessionClearStats"></a>
# **doMsgVpnMqttSessionClearStats**
> SempMetaOnlyResponse doMsgVpnMqttSessionClearStats(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, body)

Clear the statistics for the MQTT Session.

Clear the statistics for the MQTT Session.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String mqttSessionClientId = "mqttSessionClientId_example"; // String | The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet.
String mqttSessionVirtualRouter = "mqttSessionVirtualRouter_example"; // String | The virtual router of the MQTT Session.
MsgVpnMqttSessionClearStats body = new MsgVpnMqttSessionClearStats(); // MsgVpnMqttSessionClearStats | The Clear Stats action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnMqttSessionClearStats(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnMqttSessionClearStats");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **mqttSessionClientId** | **String**| The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet. |
 **mqttSessionVirtualRouter** | **String**| The virtual router of the MQTT Session. |
 **body** | [**MsgVpnMqttSessionClearStats**](MsgVpnMqttSessionClearStats.md)| The Clear Stats action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnQueueCancelReplay"></a>
# **doMsgVpnQueueCancelReplay**
> SempMetaOnlyResponse doMsgVpnQueueCancelReplay(msgVpnName, queueName, body)

Cancel the replay of messages to the Queue.

Cancel the replay of messages to the Queue.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String queueName = "queueName_example"; // String | The name of the Queue.
MsgVpnQueueCancelReplay body = new MsgVpnQueueCancelReplay(); // MsgVpnQueueCancelReplay | The Cancel Replay action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnQueueCancelReplay(msgVpnName, queueName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnQueueCancelReplay");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **queueName** | **String**| The name of the Queue. |
 **body** | [**MsgVpnQueueCancelReplay**](MsgVpnQueueCancelReplay.md)| The Cancel Replay action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnQueueClearStats"></a>
# **doMsgVpnQueueClearStats**
> SempMetaOnlyResponse doMsgVpnQueueClearStats(msgVpnName, queueName, body)

Clear the statistics for the Queue.

Clear the statistics for the Queue.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String queueName = "queueName_example"; // String | The name of the Queue.
MsgVpnQueueClearStats body = new MsgVpnQueueClearStats(); // MsgVpnQueueClearStats | The Clear Stats action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnQueueClearStats(msgVpnName, queueName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnQueueClearStats");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **queueName** | **String**| The name of the Queue. |
 **body** | [**MsgVpnQueueClearStats**](MsgVpnQueueClearStats.md)| The Clear Stats action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnQueueMsgDelete"></a>
# **doMsgVpnQueueMsgDelete**
> SempMetaOnlyResponse doMsgVpnQueueMsgDelete(msgVpnName, queueName, msgId, body)

Delete the Message from the Queue.

Delete the Message from the Queue.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String queueName = "queueName_example"; // String | The name of the Queue.
String msgId = "msgId_example"; // String | The identifier (ID) of the Message.
MsgVpnQueueMsgDelete body = new MsgVpnQueueMsgDelete(); // MsgVpnQueueMsgDelete | The Delete action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnQueueMsgDelete(msgVpnName, queueName, msgId, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnQueueMsgDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **queueName** | **String**| The name of the Queue. |
 **msgId** | **String**| The identifier (ID) of the Message. |
 **body** | [**MsgVpnQueueMsgDelete**](MsgVpnQueueMsgDelete.md)| The Delete action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnQueueStartReplay"></a>
# **doMsgVpnQueueStartReplay**
> SempMetaOnlyResponse doMsgVpnQueueStartReplay(msgVpnName, queueName, body)

Start the replay of messages to the Queue.

Start the replay of messages to the Queue.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String queueName = "queueName_example"; // String | The name of the Queue.
MsgVpnQueueStartReplay body = new MsgVpnQueueStartReplay(); // MsgVpnQueueStartReplay | The Start Replay action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnQueueStartReplay(msgVpnName, queueName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnQueueStartReplay");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **queueName** | **String**| The name of the Queue. |
 **body** | [**MsgVpnQueueStartReplay**](MsgVpnQueueStartReplay.md)| The Start Replay action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnReplayLogTrimLoggedMsgs"></a>
# **doMsgVpnReplayLogTrimLoggedMsgs**
> SempMetaOnlyResponse doMsgVpnReplayLogTrimLoggedMsgs(msgVpnName, replayLogName, body)

Trim (delete) messages from the Replay Log.

Trim (delete) messages from the Replay Log.   Attribute|Required|Deprecated :---|:---:|:---: olderThanTime|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String replayLogName = "replayLogName_example"; // String | The name of the Replay Log.
MsgVpnReplayLogTrimLoggedMsgs body = new MsgVpnReplayLogTrimLoggedMsgs(); // MsgVpnReplayLogTrimLoggedMsgs | The Trim Logged Messages action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnReplayLogTrimLoggedMsgs(msgVpnName, replayLogName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnReplayLogTrimLoggedMsgs");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **replayLogName** | **String**| The name of the Replay Log. |
 **body** | [**MsgVpnReplayLogTrimLoggedMsgs**](MsgVpnReplayLogTrimLoggedMsgs.md)| The Trim Logged Messages action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnRestDeliveryPointRestConsumerClearStats"></a>
# **doMsgVpnRestDeliveryPointRestConsumerClearStats**
> SempMetaOnlyResponse doMsgVpnRestDeliveryPointRestConsumerClearStats(msgVpnName, restDeliveryPointName, restConsumerName, body)

Clear the statistics for the REST Consumer.

Clear the statistics for the REST Consumer.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String restDeliveryPointName = "restDeliveryPointName_example"; // String | The name of the REST Delivery Point.
String restConsumerName = "restConsumerName_example"; // String | The name of the REST Consumer.
MsgVpnRestDeliveryPointRestConsumerClearStats body = new MsgVpnRestDeliveryPointRestConsumerClearStats(); // MsgVpnRestDeliveryPointRestConsumerClearStats | The Clear Stats action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnRestDeliveryPointRestConsumerClearStats(msgVpnName, restDeliveryPointName, restConsumerName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnRestDeliveryPointRestConsumerClearStats");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **restDeliveryPointName** | **String**| The name of the REST Delivery Point. |
 **restConsumerName** | **String**| The name of the REST Consumer. |
 **body** | [**MsgVpnRestDeliveryPointRestConsumerClearStats**](MsgVpnRestDeliveryPointRestConsumerClearStats.md)| The Clear Stats action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnTopicEndpointCancelReplay"></a>
# **doMsgVpnTopicEndpointCancelReplay**
> SempMetaOnlyResponse doMsgVpnTopicEndpointCancelReplay(msgVpnName, topicEndpointName, body)

Cancel the replay of messages to the Topic Endpoint.

Cancel the replay of messages to the Topic Endpoint.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String topicEndpointName = "topicEndpointName_example"; // String | The name of the Topic Endpoint.
MsgVpnTopicEndpointCancelReplay body = new MsgVpnTopicEndpointCancelReplay(); // MsgVpnTopicEndpointCancelReplay | The Cancel Replay action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnTopicEndpointCancelReplay(msgVpnName, topicEndpointName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnTopicEndpointCancelReplay");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **topicEndpointName** | **String**| The name of the Topic Endpoint. |
 **body** | [**MsgVpnTopicEndpointCancelReplay**](MsgVpnTopicEndpointCancelReplay.md)| The Cancel Replay action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnTopicEndpointClearStats"></a>
# **doMsgVpnTopicEndpointClearStats**
> SempMetaOnlyResponse doMsgVpnTopicEndpointClearStats(msgVpnName, topicEndpointName, body)

Clear the statistics for the Topic Endpoint.

Clear the statistics for the Topic Endpoint.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String topicEndpointName = "topicEndpointName_example"; // String | The name of the Topic Endpoint.
MsgVpnTopicEndpointClearStats body = new MsgVpnTopicEndpointClearStats(); // MsgVpnTopicEndpointClearStats | The Clear Stats action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnTopicEndpointClearStats(msgVpnName, topicEndpointName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnTopicEndpointClearStats");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **topicEndpointName** | **String**| The name of the Topic Endpoint. |
 **body** | [**MsgVpnTopicEndpointClearStats**](MsgVpnTopicEndpointClearStats.md)| The Clear Stats action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnTopicEndpointMsgDelete"></a>
# **doMsgVpnTopicEndpointMsgDelete**
> SempMetaOnlyResponse doMsgVpnTopicEndpointMsgDelete(msgVpnName, topicEndpointName, msgId, body)

Delete the Message from the Topic Endpoint.

Delete the Message from the Topic Endpoint.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String topicEndpointName = "topicEndpointName_example"; // String | The name of the Topic Endpoint.
String msgId = "msgId_example"; // String | The identifier (ID) of the Message.
MsgVpnTopicEndpointMsgDelete body = new MsgVpnTopicEndpointMsgDelete(); // MsgVpnTopicEndpointMsgDelete | The Delete action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnTopicEndpointMsgDelete(msgVpnName, topicEndpointName, msgId, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnTopicEndpointMsgDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **topicEndpointName** | **String**| The name of the Topic Endpoint. |
 **msgId** | **String**| The identifier (ID) of the Message. |
 **body** | [**MsgVpnTopicEndpointMsgDelete**](MsgVpnTopicEndpointMsgDelete.md)| The Delete action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnTopicEndpointStartReplay"></a>
# **doMsgVpnTopicEndpointStartReplay**
> SempMetaOnlyResponse doMsgVpnTopicEndpointStartReplay(msgVpnName, topicEndpointName, body)

Start the replay of messages to the Topic Endpoint.

Start the replay of messages to the Topic Endpoint.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String topicEndpointName = "topicEndpointName_example"; // String | The name of the Topic Endpoint.
MsgVpnTopicEndpointStartReplay body = new MsgVpnTopicEndpointStartReplay(); // MsgVpnTopicEndpointStartReplay | The Start Replay action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnTopicEndpointStartReplay(msgVpnName, topicEndpointName, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnTopicEndpointStartReplay");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **topicEndpointName** | **String**| The name of the Topic Endpoint. |
 **body** | [**MsgVpnTopicEndpointStartReplay**](MsgVpnTopicEndpointStartReplay.md)| The Start Replay action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnTransactionCommit"></a>
# **doMsgVpnTransactionCommit**
> SempMetaOnlyResponse doMsgVpnTransactionCommit(msgVpnName, xid, body)

Commit the Transaction.

Commit the Transaction.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.12.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String xid = "xid_example"; // String | The identifier (ID) of the Transaction.
MsgVpnTransactionCommit body = new MsgVpnTransactionCommit(); // MsgVpnTransactionCommit | The Commit action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnTransactionCommit(msgVpnName, xid, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnTransactionCommit");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **xid** | **String**| The identifier (ID) of the Transaction. |
 **body** | [**MsgVpnTransactionCommit**](MsgVpnTransactionCommit.md)| The Commit action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnTransactionDelete"></a>
# **doMsgVpnTransactionDelete**
> SempMetaOnlyResponse doMsgVpnTransactionDelete(msgVpnName, xid, body)

Delete the Transaction.

Delete the Transaction.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.12.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String xid = "xid_example"; // String | The identifier (ID) of the Transaction.
MsgVpnTransactionDelete body = new MsgVpnTransactionDelete(); // MsgVpnTransactionDelete | The Delete action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnTransactionDelete(msgVpnName, xid, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnTransactionDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **xid** | **String**| The identifier (ID) of the Transaction. |
 **body** | [**MsgVpnTransactionDelete**](MsgVpnTransactionDelete.md)| The Delete action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="doMsgVpnTransactionRollback"></a>
# **doMsgVpnTransactionRollback**
> SempMetaOnlyResponse doMsgVpnTransactionRollback(msgVpnName, xid, body)

Rollback the Transaction.

Rollback the Transaction.    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.12.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String xid = "xid_example"; // String | The identifier (ID) of the Transaction.
MsgVpnTransactionRollback body = new MsgVpnTransactionRollback(); // MsgVpnTransactionRollback | The Rollback action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doMsgVpnTransactionRollback(msgVpnName, xid, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doMsgVpnTransactionRollback");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **xid** | **String**| The identifier (ID) of the Transaction. |
 **body** | [**MsgVpnTransactionRollback**](MsgVpnTransactionRollback.md)| The Rollback action&#39;s attributes. |

### Return type

[**SempMetaOnlyResponse**](SempMetaOnlyResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String sessionUsername = "sessionUsername_example"; // String | The username used for authorization.
String sessionId = "sessionId_example"; // String | The unique identifier for the session.
SessionDelete body = new SessionDelete(); // SessionDelete | The Delete action's attributes.
try {
    SempMetaOnlyResponse result = apiInstance.doSessionDelete(sessionUsername, sessionId, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#doSessionDelete");
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

<a name="getAbout"></a>
# **getAbout**
> AboutResponse getAbout(select)

Get an About object.

Get an About object.  This provides metadata about the SEMP API, such as the version of the API supported by the broker.    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation.  This has been available since 2.13.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    AboutResponse result = apiInstance.getAbout(select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getAbout");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**AboutResponse**](AboutResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getAboutApi"></a>
# **getAboutApi**
> AboutApiResponse getAboutApi(select)

Get an API Description object.

Get an API Description object.  The API Description object provides metadata about the SEMP API.    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    AboutApiResponse result = apiInstance.getAboutApi(select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getAboutApi");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**AboutApiResponse**](AboutApiResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getAboutUser"></a>
# **getAboutUser**
> AboutUserResponse getAboutUser(select)

Get a User object.

Get a User object.  Session and access level information about the user accessing the SEMP API.    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    AboutUserResponse result = apiInstance.getAboutUser(select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getAboutUser");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**AboutUserResponse**](AboutUserResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getAboutUserMsgVpn"></a>
# **getAboutUserMsgVpn**
> AboutUserMsgVpnResponse getAboutUserMsgVpn(msgVpnName, select)

Get a User Message VPN object.

Get a User Message VPN object.  This provides information about the Message VPN access level for the username used to access the SEMP API.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    AboutUserMsgVpnResponse result = apiInstance.getAboutUserMsgVpn(msgVpnName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getAboutUserMsgVpn");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**AboutUserMsgVpnResponse**](AboutUserMsgVpnResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getAboutUserMsgVpns"></a>
# **getAboutUserMsgVpns**
> AboutUserMsgVpnsResponse getAboutUserMsgVpns(count, cursor, where, select)

Get a list of User Message VPN objects.

Get a list of User Message VPN objects.  This provides information about the Message VPN access level for the username used to access the SEMP API.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    AboutUserMsgVpnsResponse result = apiInstance.getAboutUserMsgVpns(count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getAboutUserMsgVpns");
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

[**AboutUserMsgVpnsResponse**](AboutUserMsgVpnsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getBroker"></a>
# **getBroker**
> BrokerResponse getBroker(select)

Get a Broker object.

Get a Broker object.  This object contains global configuration for the message broker.    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation.  This has been available since 2.13.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    BrokerResponse result = apiInstance.getBroker(select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getBroker");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**BrokerResponse**](BrokerResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getCertAuthorities"></a>
# **getCertAuthorities**
> CertAuthoritiesResponse getCertAuthorities(count, cursor, where, select)

Get a list of Certificate Authority objects.

Get a list of Certificate Authority objects.  Clients can authenticate with the message broker over TLS by presenting a valid client certificate. The message broker authenticates the client certificate by constructing a full certificate chain (from the client certificate to intermediate CAs to a configured root CA). The intermediate CAs in this chain can be provided by the client, or configured in the message broker. The root CA must be configured on the message broker.   Attribute|Identifying|Deprecated :---|:---:|:---: certAuthorityName|x|x    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.19. Replaced by clientCertAuthorities and domainCertAuthorities.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    CertAuthoritiesResponse result = apiInstance.getCertAuthorities(count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getCertAuthorities");
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

[**CertAuthoritiesResponse**](CertAuthoritiesResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getCertAuthority"></a>
# **getCertAuthority**
> CertAuthorityResponse getCertAuthority(certAuthorityName, select)

Get a Certificate Authority object.

Get a Certificate Authority object.  Clients can authenticate with the message broker over TLS by presenting a valid client certificate. The message broker authenticates the client certificate by constructing a full certificate chain (from the client certificate to intermediate CAs to a configured root CA). The intermediate CAs in this chain can be provided by the client, or configured in the message broker. The root CA must be configured on the message broker.   Attribute|Identifying|Deprecated :---|:---:|:---: certAuthorityName|x|x    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.19. Replaced by clientCertAuthorities and domainCertAuthorities.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String certAuthorityName = "certAuthorityName_example"; // String | The name of the Certificate Authority.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    CertAuthorityResponse result = apiInstance.getCertAuthority(certAuthorityName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getCertAuthority");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **certAuthorityName** | **String**| The name of the Certificate Authority. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**CertAuthorityResponse**](CertAuthorityResponse.md)

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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    ClientCertAuthoritiesResponse result = apiInstance.getClientCertAuthorities(count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getClientCertAuthorities");
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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String certAuthorityName = "certAuthorityName_example"; // String | The name of the Certificate Authority.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    ClientCertAuthorityResponse result = apiInstance.getClientCertAuthority(certAuthorityName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getClientCertAuthority");
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

<a name="getMsgVpn"></a>
# **getMsgVpn**
> MsgVpnResponse getMsgVpn(msgVpnName, select)

Get a Message VPN object.

Get a Message VPN object.  Message VPNs (Virtual Private Networks) allow for the segregation of topic space and clients. They also group clients connecting to a network of message brokers, such that messages published within a particular group are only visible to that group&#39;s clients.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnResponse result = apiInstance.getMsgVpn(msgVpnName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpn");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnResponse**](MsgVpnResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnAuthenticationOauthProvider"></a>
# **getMsgVpnAuthenticationOauthProvider**
> MsgVpnAuthenticationOauthProviderResponse getMsgVpnAuthenticationOauthProvider(msgVpnName, oauthProviderName, select)

Get an OAuth Provider object.

Get an OAuth Provider object.  OAuth Providers contain information about the issuer of an OAuth token that is needed to validate the token and derive a client username from it.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| oauthProviderName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.13.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String oauthProviderName = "oauthProviderName_example"; // String | The name of the OAuth Provider.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnAuthenticationOauthProviderResponse result = apiInstance.getMsgVpnAuthenticationOauthProvider(msgVpnName, oauthProviderName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnAuthenticationOauthProvider");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **oauthProviderName** | **String**| The name of the OAuth Provider. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnAuthenticationOauthProviderResponse**](MsgVpnAuthenticationOauthProviderResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnAuthenticationOauthProviders"></a>
# **getMsgVpnAuthenticationOauthProviders**
> MsgVpnAuthenticationOauthProvidersResponse getMsgVpnAuthenticationOauthProviders(msgVpnName, count, cursor, where, select)

Get a list of OAuth Provider objects.

Get a list of OAuth Provider objects.  OAuth Providers contain information about the issuer of an OAuth token that is needed to validate the token and derive a client username from it.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| oauthProviderName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.13.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnAuthenticationOauthProvidersResponse result = apiInstance.getMsgVpnAuthenticationOauthProviders(msgVpnName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnAuthenticationOauthProviders");
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

[**MsgVpnAuthenticationOauthProvidersResponse**](MsgVpnAuthenticationOauthProvidersResponse.md)

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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String bridgeName = "bridgeName_example"; // String | The name of the Bridge.
String bridgeVirtualRouter = "bridgeVirtualRouter_example"; // String | The virtual router of the Bridge.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnBridgeResponse result = apiInstance.getMsgVpnBridge(msgVpnName, bridgeName, bridgeVirtualRouter, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnBridge");
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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnBridgesResponse result = apiInstance.getMsgVpnBridges(msgVpnName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnBridges");
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

<a name="getMsgVpnClient"></a>
# **getMsgVpnClient**
> MsgVpnClientResponse getMsgVpnClient(msgVpnName, clientName, select)

Get a Client object.

Get a Client object.  Applications or devices that connect to message brokers to send and/or receive messages are represented as Clients.   Attribute|Identifying|Deprecated :---|:---:|:---: clientName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String clientName = "clientName_example"; // String | The name of the Client.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnClientResponse result = apiInstance.getMsgVpnClient(msgVpnName, clientName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnClient");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **clientName** | **String**| The name of the Client. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnClientResponse**](MsgVpnClientResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnClientTransactedSession"></a>
# **getMsgVpnClientTransactedSession**
> MsgVpnClientTransactedSessionResponse getMsgVpnClientTransactedSession(msgVpnName, clientName, sessionName, select)

Get a Client Transacted Session object.

Get a Client Transacted Session object.  Transacted Sessions enable clients to group multiple message send and/or receive operations together in single, atomic units known as local transactions.   Attribute|Identifying|Deprecated :---|:---:|:---: clientName|x| msgVpnName|x| sessionName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String clientName = "clientName_example"; // String | The name of the Client.
String sessionName = "sessionName_example"; // String | The name of the Transacted Session.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnClientTransactedSessionResponse result = apiInstance.getMsgVpnClientTransactedSession(msgVpnName, clientName, sessionName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnClientTransactedSession");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **clientName** | **String**| The name of the Client. |
 **sessionName** | **String**| The name of the Transacted Session. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnClientTransactedSessionResponse**](MsgVpnClientTransactedSessionResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnClientTransactedSessions"></a>
# **getMsgVpnClientTransactedSessions**
> MsgVpnClientTransactedSessionsResponse getMsgVpnClientTransactedSessions(msgVpnName, clientName, count, cursor, where, select)

Get a list of Client Transacted Session objects.

Get a list of Client Transacted Session objects.  Transacted Sessions enable clients to group multiple message send and/or receive operations together in single, atomic units known as local transactions.   Attribute|Identifying|Deprecated :---|:---:|:---: clientName|x| msgVpnName|x| sessionName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String clientName = "clientName_example"; // String | The name of the Client.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnClientTransactedSessionsResponse result = apiInstance.getMsgVpnClientTransactedSessions(msgVpnName, clientName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnClientTransactedSessions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **clientName** | **String**| The name of the Client. |
 **count** | **Integer**| Limit the count of objects in the response. See the documentation for the &#x60;count&#x60; parameter. | [optional] [default to 10]
 **cursor** | **String**| The cursor, or position, for the next page of objects. See the documentation for the &#x60;cursor&#x60; parameter. | [optional]
 **where** | [**List&lt;String&gt;**](String.md)| Include in the response only objects where certain conditions are true. See the the documentation for the &#x60;where&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnClientTransactedSessionsResponse**](MsgVpnClientTransactedSessionsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnClients"></a>
# **getMsgVpnClients**
> MsgVpnClientsResponse getMsgVpnClients(msgVpnName, count, cursor, where, select)

Get a list of Client objects.

Get a list of Client objects.  Applications or devices that connect to message brokers to send and/or receive messages are represented as Clients.   Attribute|Identifying|Deprecated :---|:---:|:---: clientName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnClientsResponse result = apiInstance.getMsgVpnClients(msgVpnName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnClients");
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

[**MsgVpnClientsResponse**](MsgVpnClientsResponse.md)

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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String cacheName = "cacheName_example"; // String | The name of the Distributed Cache.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnDistributedCacheResponse result = apiInstance.getMsgVpnDistributedCache(msgVpnName, cacheName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnDistributedCache");
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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String cacheName = "cacheName_example"; // String | The name of the Distributed Cache.
String clusterName = "clusterName_example"; // String | The name of the Cache Cluster.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnDistributedCacheClusterResponse result = apiInstance.getMsgVpnDistributedCacheCluster(msgVpnName, cacheName, clusterName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnDistributedCacheCluster");
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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String cacheName = "cacheName_example"; // String | The name of the Distributed Cache.
String clusterName = "clusterName_example"; // String | The name of the Cache Cluster.
String instanceName = "instanceName_example"; // String | The name of the Cache Instance.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnDistributedCacheClusterInstanceResponse result = apiInstance.getMsgVpnDistributedCacheClusterInstance(msgVpnName, cacheName, clusterName, instanceName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnDistributedCacheClusterInstance");
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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
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
    System.err.println("Exception when calling AllApi#getMsgVpnDistributedCacheClusterInstances");
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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
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
    System.err.println("Exception when calling AllApi#getMsgVpnDistributedCacheClusters");
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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnDistributedCachesResponse result = apiInstance.getMsgVpnDistributedCaches(msgVpnName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnDistributedCaches");
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

<a name="getMsgVpnMqttSession"></a>
# **getMsgVpnMqttSession**
> MsgVpnMqttSessionResponse getMsgVpnMqttSession(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, select)

Get an MQTT Session object.

Get an MQTT Session object.  An MQTT Session object is a virtual representation of an MQTT client connection. An MQTT session holds the state of an MQTT client (that is, it is used to contain a client&#39;s QoS 0 and QoS 1 subscription sets and any undelivered QoS 1 messages).   Attribute|Identifying|Deprecated :---|:---:|:---: mqttSessionClientId|x| mqttSessionVirtualRouter|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String mqttSessionClientId = "mqttSessionClientId_example"; // String | The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet.
String mqttSessionVirtualRouter = "mqttSessionVirtualRouter_example"; // String | The virtual router of the MQTT Session.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnMqttSessionResponse result = apiInstance.getMsgVpnMqttSession(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnMqttSession");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **mqttSessionClientId** | **String**| The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet. |
 **mqttSessionVirtualRouter** | **String**| The virtual router of the MQTT Session. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnMqttSessionResponse**](MsgVpnMqttSessionResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnMqttSessions"></a>
# **getMsgVpnMqttSessions**
> MsgVpnMqttSessionsResponse getMsgVpnMqttSessions(msgVpnName, count, cursor, where, select)

Get a list of MQTT Session objects.

Get a list of MQTT Session objects.  An MQTT Session object is a virtual representation of an MQTT client connection. An MQTT session holds the state of an MQTT client (that is, it is used to contain a client&#39;s QoS 0 and QoS 1 subscription sets and any undelivered QoS 1 messages).   Attribute|Identifying|Deprecated :---|:---:|:---: mqttSessionClientId|x| mqttSessionVirtualRouter|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnMqttSessionsResponse result = apiInstance.getMsgVpnMqttSessions(msgVpnName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnMqttSessions");
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

[**MsgVpnMqttSessionsResponse**](MsgVpnMqttSessionsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnQueue"></a>
# **getMsgVpnQueue**
> MsgVpnQueueResponse getMsgVpnQueue(msgVpnName, queueName, select)

Get a Queue object.

Get a Queue object.  A Queue acts as both a destination that clients can publish messages to, and as an endpoint that clients can bind consumers to and consume messages from.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| queueName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String queueName = "queueName_example"; // String | The name of the Queue.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnQueueResponse result = apiInstance.getMsgVpnQueue(msgVpnName, queueName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnQueue");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **queueName** | **String**| The name of the Queue. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnQueueResponse**](MsgVpnQueueResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnQueueMsg"></a>
# **getMsgVpnQueueMsg**
> MsgVpnQueueMsgResponse getMsgVpnQueueMsg(msgVpnName, queueName, msgId, select)

Get a Queue Message object.

Get a Queue Message object.  A Queue Message is a packet of information sent from producers to consumers using the Queue.   Attribute|Identifying|Deprecated :---|:---:|:---: msgId|x| msgVpnName|x| queueName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String queueName = "queueName_example"; // String | The name of the Queue.
String msgId = "msgId_example"; // String | The identifier (ID) of the Message.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnQueueMsgResponse result = apiInstance.getMsgVpnQueueMsg(msgVpnName, queueName, msgId, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnQueueMsg");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **queueName** | **String**| The name of the Queue. |
 **msgId** | **String**| The identifier (ID) of the Message. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnQueueMsgResponse**](MsgVpnQueueMsgResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnQueueMsgs"></a>
# **getMsgVpnQueueMsgs**
> MsgVpnQueueMsgsResponse getMsgVpnQueueMsgs(msgVpnName, queueName, count, cursor, where, select)

Get a list of Queue Message objects.

Get a list of Queue Message objects.  A Queue Message is a packet of information sent from producers to consumers using the Queue.   Attribute|Identifying|Deprecated :---|:---:|:---: msgId|x| msgVpnName|x| queueName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String queueName = "queueName_example"; // String | The name of the Queue.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnQueueMsgsResponse result = apiInstance.getMsgVpnQueueMsgs(msgVpnName, queueName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnQueueMsgs");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **queueName** | **String**| The name of the Queue. |
 **count** | **Integer**| Limit the count of objects in the response. See the documentation for the &#x60;count&#x60; parameter. | [optional] [default to 10]
 **cursor** | **String**| The cursor, or position, for the next page of objects. See the documentation for the &#x60;cursor&#x60; parameter. | [optional]
 **where** | [**List&lt;String&gt;**](String.md)| Include in the response only objects where certain conditions are true. See the the documentation for the &#x60;where&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnQueueMsgsResponse**](MsgVpnQueueMsgsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnQueues"></a>
# **getMsgVpnQueues**
> MsgVpnQueuesResponse getMsgVpnQueues(msgVpnName, count, cursor, where, select)

Get a list of Queue objects.

Get a list of Queue objects.  A Queue acts as both a destination that clients can publish messages to, and as an endpoint that clients can bind consumers to and consume messages from.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| queueName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnQueuesResponse result = apiInstance.getMsgVpnQueues(msgVpnName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnQueues");
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

[**MsgVpnQueuesResponse**](MsgVpnQueuesResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnReplayLog"></a>
# **getMsgVpnReplayLog**
> MsgVpnReplayLogResponse getMsgVpnReplayLog(msgVpnName, replayLogName, select)

Get a Replay Log object.

Get a Replay Log object.  When the Message Replay feature is enabled, message brokers store persistent messages in a Replay Log. These messages are kept until the log is full, after which the oldest messages are removed to free up space for new messages.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| replayLogName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String replayLogName = "replayLogName_example"; // String | The name of the Replay Log.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnReplayLogResponse result = apiInstance.getMsgVpnReplayLog(msgVpnName, replayLogName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnReplayLog");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **replayLogName** | **String**| The name of the Replay Log. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnReplayLogResponse**](MsgVpnReplayLogResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnReplayLogs"></a>
# **getMsgVpnReplayLogs**
> MsgVpnReplayLogsResponse getMsgVpnReplayLogs(msgVpnName, count, cursor, where, select)

Get a list of Replay Log objects.

Get a list of Replay Log objects.  When the Message Replay feature is enabled, message brokers store persistent messages in a Replay Log. These messages are kept until the log is full, after which the oldest messages are removed to free up space for new messages.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| replayLogName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnReplayLogsResponse result = apiInstance.getMsgVpnReplayLogs(msgVpnName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnReplayLogs");
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

[**MsgVpnReplayLogsResponse**](MsgVpnReplayLogsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnRestDeliveryPoint"></a>
# **getMsgVpnRestDeliveryPoint**
> MsgVpnRestDeliveryPointResponse getMsgVpnRestDeliveryPoint(msgVpnName, restDeliveryPointName, select)

Get a REST Delivery Point object.

Get a REST Delivery Point object.  A REST Delivery Point manages delivery of messages from queues to a named list of REST Consumers.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| restDeliveryPointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String restDeliveryPointName = "restDeliveryPointName_example"; // String | The name of the REST Delivery Point.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnRestDeliveryPointResponse result = apiInstance.getMsgVpnRestDeliveryPoint(msgVpnName, restDeliveryPointName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnRestDeliveryPoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **restDeliveryPointName** | **String**| The name of the REST Delivery Point. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnRestDeliveryPointResponse**](MsgVpnRestDeliveryPointResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnRestDeliveryPointRestConsumer"></a>
# **getMsgVpnRestDeliveryPointRestConsumer**
> MsgVpnRestDeliveryPointRestConsumerResponse getMsgVpnRestDeliveryPointRestConsumer(msgVpnName, restDeliveryPointName, restConsumerName, select)

Get a REST Consumer object.

Get a REST Consumer object.  REST Consumer objects establish HTTP connectivity to REST consumer applications who wish to receive messages from a broker.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| restConsumerName|x| restDeliveryPointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String restDeliveryPointName = "restDeliveryPointName_example"; // String | The name of the REST Delivery Point.
String restConsumerName = "restConsumerName_example"; // String | The name of the REST Consumer.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnRestDeliveryPointRestConsumerResponse result = apiInstance.getMsgVpnRestDeliveryPointRestConsumer(msgVpnName, restDeliveryPointName, restConsumerName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnRestDeliveryPointRestConsumer");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **restDeliveryPointName** | **String**| The name of the REST Delivery Point. |
 **restConsumerName** | **String**| The name of the REST Consumer. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnRestDeliveryPointRestConsumerResponse**](MsgVpnRestDeliveryPointRestConsumerResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnRestDeliveryPointRestConsumers"></a>
# **getMsgVpnRestDeliveryPointRestConsumers**
> MsgVpnRestDeliveryPointRestConsumersResponse getMsgVpnRestDeliveryPointRestConsumers(msgVpnName, restDeliveryPointName, count, cursor, where, select)

Get a list of REST Consumer objects.

Get a list of REST Consumer objects.  REST Consumer objects establish HTTP connectivity to REST consumer applications who wish to receive messages from a broker.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| restConsumerName|x| restDeliveryPointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String restDeliveryPointName = "restDeliveryPointName_example"; // String | The name of the REST Delivery Point.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnRestDeliveryPointRestConsumersResponse result = apiInstance.getMsgVpnRestDeliveryPointRestConsumers(msgVpnName, restDeliveryPointName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnRestDeliveryPointRestConsumers");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **restDeliveryPointName** | **String**| The name of the REST Delivery Point. |
 **count** | **Integer**| Limit the count of objects in the response. See the documentation for the &#x60;count&#x60; parameter. | [optional] [default to 10]
 **cursor** | **String**| The cursor, or position, for the next page of objects. See the documentation for the &#x60;cursor&#x60; parameter. | [optional]
 **where** | [**List&lt;String&gt;**](String.md)| Include in the response only objects where certain conditions are true. See the the documentation for the &#x60;where&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnRestDeliveryPointRestConsumersResponse**](MsgVpnRestDeliveryPointRestConsumersResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnRestDeliveryPoints"></a>
# **getMsgVpnRestDeliveryPoints**
> MsgVpnRestDeliveryPointsResponse getMsgVpnRestDeliveryPoints(msgVpnName, count, cursor, where, select)

Get a list of REST Delivery Point objects.

Get a list of REST Delivery Point objects.  A REST Delivery Point manages delivery of messages from queues to a named list of REST Consumers.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| restDeliveryPointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnRestDeliveryPointsResponse result = apiInstance.getMsgVpnRestDeliveryPoints(msgVpnName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnRestDeliveryPoints");
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

[**MsgVpnRestDeliveryPointsResponse**](MsgVpnRestDeliveryPointsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnTopicEndpoint"></a>
# **getMsgVpnTopicEndpoint**
> MsgVpnTopicEndpointResponse getMsgVpnTopicEndpoint(msgVpnName, topicEndpointName, select)

Get a Topic Endpoint object.

Get a Topic Endpoint object.  A Topic Endpoint attracts messages published to a topic for which the Topic Endpoint has a matching topic subscription. The topic subscription for the Topic Endpoint is specified in the client request to bind a Flow to that Topic Endpoint. Queues are significantly more flexible than Topic Endpoints and are the recommended approach for most applications. The use of Topic Endpoints should be restricted to JMS applications.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| topicEndpointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String topicEndpointName = "topicEndpointName_example"; // String | The name of the Topic Endpoint.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnTopicEndpointResponse result = apiInstance.getMsgVpnTopicEndpoint(msgVpnName, topicEndpointName, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnTopicEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **topicEndpointName** | **String**| The name of the Topic Endpoint. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnTopicEndpointResponse**](MsgVpnTopicEndpointResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnTopicEndpointMsg"></a>
# **getMsgVpnTopicEndpointMsg**
> MsgVpnTopicEndpointMsgResponse getMsgVpnTopicEndpointMsg(msgVpnName, topicEndpointName, msgId, select)

Get a Topic Endpoint Message object.

Get a Topic Endpoint Message object.  A Topic Endpoint Message is a packet of information sent from producers to consumers using the Topic Endpoint.   Attribute|Identifying|Deprecated :---|:---:|:---: msgId|x| msgVpnName|x| topicEndpointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String topicEndpointName = "topicEndpointName_example"; // String | The name of the Topic Endpoint.
String msgId = "msgId_example"; // String | The identifier (ID) of the Message.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnTopicEndpointMsgResponse result = apiInstance.getMsgVpnTopicEndpointMsg(msgVpnName, topicEndpointName, msgId, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnTopicEndpointMsg");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **topicEndpointName** | **String**| The name of the Topic Endpoint. |
 **msgId** | **String**| The identifier (ID) of the Message. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnTopicEndpointMsgResponse**](MsgVpnTopicEndpointMsgResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnTopicEndpointMsgs"></a>
# **getMsgVpnTopicEndpointMsgs**
> MsgVpnTopicEndpointMsgsResponse getMsgVpnTopicEndpointMsgs(msgVpnName, topicEndpointName, count, cursor, where, select)

Get a list of Topic Endpoint Message objects.

Get a list of Topic Endpoint Message objects.  A Topic Endpoint Message is a packet of information sent from producers to consumers using the Topic Endpoint.   Attribute|Identifying|Deprecated :---|:---:|:---: msgId|x| msgVpnName|x| topicEndpointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String topicEndpointName = "topicEndpointName_example"; // String | The name of the Topic Endpoint.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnTopicEndpointMsgsResponse result = apiInstance.getMsgVpnTopicEndpointMsgs(msgVpnName, topicEndpointName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnTopicEndpointMsgs");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **topicEndpointName** | **String**| The name of the Topic Endpoint. |
 **count** | **Integer**| Limit the count of objects in the response. See the documentation for the &#x60;count&#x60; parameter. | [optional] [default to 10]
 **cursor** | **String**| The cursor, or position, for the next page of objects. See the documentation for the &#x60;cursor&#x60; parameter. | [optional]
 **where** | [**List&lt;String&gt;**](String.md)| Include in the response only objects where certain conditions are true. See the the documentation for the &#x60;where&#x60; parameter. | [optional]
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnTopicEndpointMsgsResponse**](MsgVpnTopicEndpointMsgsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnTopicEndpoints"></a>
# **getMsgVpnTopicEndpoints**
> MsgVpnTopicEndpointsResponse getMsgVpnTopicEndpoints(msgVpnName, count, cursor, where, select)

Get a list of Topic Endpoint objects.

Get a list of Topic Endpoint objects.  A Topic Endpoint attracts messages published to a topic for which the Topic Endpoint has a matching topic subscription. The topic subscription for the Topic Endpoint is specified in the client request to bind a Flow to that Topic Endpoint. Queues are significantly more flexible than Topic Endpoints and are the recommended approach for most applications. The use of Topic Endpoints should be restricted to JMS applications.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| topicEndpointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnTopicEndpointsResponse result = apiInstance.getMsgVpnTopicEndpoints(msgVpnName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnTopicEndpoints");
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

[**MsgVpnTopicEndpointsResponse**](MsgVpnTopicEndpointsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnTransaction"></a>
# **getMsgVpnTransaction**
> MsgVpnTransactionResponse getMsgVpnTransaction(msgVpnName, xid, select)

Get a Replicated Local Transaction or XA Transaction object.

Get a Replicated Local Transaction or XA Transaction object.  Transactions can be used to group a set of Guaranteed messages to be published or consumed or both as an atomic unit of work.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| xid|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
String xid = "xid_example"; // String | The identifier (ID) of the Transaction.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnTransactionResponse result = apiInstance.getMsgVpnTransaction(msgVpnName, xid, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnTransaction");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **msgVpnName** | **String**| The name of the Message VPN. |
 **xid** | **String**| The identifier (ID) of the Transaction. |
 **select** | [**List&lt;String&gt;**](String.md)| Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. | [optional]

### Return type

[**MsgVpnTransactionResponse**](MsgVpnTransactionResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpnTransactions"></a>
# **getMsgVpnTransactions**
> MsgVpnTransactionsResponse getMsgVpnTransactions(msgVpnName, count, cursor, where, select)

Get a list of Replicated Local Transaction or XA Transaction objects.

Get a list of Replicated Local Transaction or XA Transaction objects.  Transactions can be used to group a set of Guaranteed messages to be published or consumed or both as an atomic unit of work.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| xid|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String msgVpnName = "msgVpnName_example"; // String | The name of the Message VPN.
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnTransactionsResponse result = apiInstance.getMsgVpnTransactions(msgVpnName, count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpnTransactions");
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

[**MsgVpnTransactionsResponse**](MsgVpnTransactionsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMsgVpns"></a>
# **getMsgVpns**
> MsgVpnsResponse getMsgVpns(count, cursor, where, select)

Get a list of Message VPN objects.

Get a list of Message VPN objects.  Message VPNs (Virtual Private Networks) allow for the segregation of topic space and clients. They also group clients connecting to a network of message brokers, such that messages published within a particular group are only visible to that group&#39;s clients.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    MsgVpnsResponse result = apiInstance.getMsgVpns(count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getMsgVpns");
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

[**MsgVpnsResponse**](MsgVpnsResponse.md)

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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
String sessionUsername = "sessionUsername_example"; // String | The username used for authorization.
String sessionId = "sessionId_example"; // String | The unique identifier for the session.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    SessionResponse result = apiInstance.getSession(sessionUsername, sessionId, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getSession");
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
//import io.swagger.client.api.AllApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AllApi apiInstance = new AllApi();
Integer count = 10; // Integer | Limit the count of objects in the response. See the documentation for the `count` parameter.
String cursor = "cursor_example"; // String | The cursor, or position, for the next page of objects. See the documentation for the `cursor` parameter.
List<String> where = Arrays.asList("where_example"); // List<String> | Include in the response only objects where certain conditions are true. See the the documentation for the `where` parameter.
List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
try {
    SessionsResponse result = apiInstance.getSessions(count, cursor, where, select);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AllApi#getSessions");
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

