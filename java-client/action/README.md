# swagger-java-client

SEMP (Solace Element Management Protocol)
- API version: 2.23
  - Build date: 2024-01-19T18:07:43.924Z

SEMP (starting in `v2`, see note 1) is a RESTful API for configuring, monitoring, and administering a Solace PubSub+ broker.  SEMP uses URIs to address manageable **resources** of the Solace PubSub+ broker. Resources are individual **objects**, **collections** of objects, or (exclusively in the action API) **actions**. This document applies to the following API:   API|Base Path|Purpose|Comments :---|:---|:---|:--- Action|/SEMP/v2/action|Performing actions|See note 2    The following APIs are also available:   API|Base Path|Purpose|Comments :---|:---|:---|:--- Configuration|/SEMP/v2/config|Reading and writing config state|See note 2 Monitoring|/SEMP/v2/monitor|Querying operational parameters|See note 2    Resources are always nouns, with individual objects being singular and collections being plural.  Objects within a collection are identified by an `obj-id`, which follows the collection name with the form `collection-name/obj-id`.  Actions within an object are identified by an `action-id`, which follows the object name with the form `obj-id/action-id`.  Some examples:  ``` /SEMP/v2/config/msgVpns                        ; MsgVpn collection /SEMP/v2/config/msgVpns/a                      ; MsgVpn object named \"a\" /SEMP/v2/config/msgVpns/a/queues               ; Queue collection in MsgVpn \"a\" /SEMP/v2/config/msgVpns/a/queues/b             ; Queue object named \"b\" in MsgVpn \"a\" /SEMP/v2/action/msgVpns/a/queues/b/startReplay ; Action that starts a replay on Queue \"b\" in MsgVpn \"a\" /SEMP/v2/monitor/msgVpns/a/clients             ; Client collection in MsgVpn \"a\" /SEMP/v2/monitor/msgVpns/a/clients/c           ; Client object named \"c\" in MsgVpn \"a\" ```  ## Collection Resources  Collections are unordered lists of objects (unless described as otherwise), and are described by JSON arrays. Each item in the array represents an object in the same manner as the individual object would normally be represented. In the configuration API, the creation of a new object is done through its collection resource.  ## Object and Action Resources  Objects are composed of attributes, actions, collections, and other objects. They are described by JSON objects as name/value pairs. The collections and actions of an object are not contained directly in the object's JSON content; rather the content includes an attribute containing a URI which points to the collections and actions. These contained resources must be managed through this URI. At a minimum, every object has one or more identifying attributes, and its own `uri` attribute which contains the URI pointing to itself.  Actions are also composed of attributes, and are described by JSON objects as name/value pairs. Unlike objects, however, they are not members of a collection and cannot be retrieved, only performed. Actions only exist in the action API.  Attributes in an object or action may have any combination of the following properties:   Property|Meaning|Comments :---|:---|:--- Identifying|Attribute is involved in unique identification of the object, and appears in its URI| Required|Attribute must be provided in the request| Read-Only|Attribute can only be read, not written.|See note 3 Write-Only|Attribute can only be written, not read, unless the attribute is also opaque|See the documentation for the opaque property Requires-Disable|Attribute can only be changed when object is disabled| Deprecated|Attribute is deprecated, and will disappear in the next SEMP version| Opaque|Attribute can be set or retrieved in opaque form when the `opaquePassword` query parameter is present|See the `opaquePassword` query parameter documentation    In some requests, certain attributes may only be provided in certain combinations with other attributes:   Relationship|Meaning :---|:--- Requires|Attribute may only be changed by a request if a particular attribute or combination of attributes is also provided in the request Conflicts|Attribute may only be provided in a request if a particular attribute or combination of attributes is not also provided in the request    In the monitoring API, any non-identifying attribute may not be returned in a GET.  ## HTTP Methods  The following HTTP methods manipulate resources in accordance with these general principles. Note that some methods are only used in certain APIs:   Method|Resource|Meaning|Request Body|Response Body|Missing Request Attributes :---|:---|:---|:---|:---|:--- POST|Collection|Create object|Initial attribute values|Object attributes and metadata|Set to default PUT|Object|Create or replace object (see note 5)|New attribute values|Object attributes and metadata|Set to default, with certain exceptions (see note 4) PUT|Action|Performs action|Action arguments|Action metadata|N/A PATCH|Object|Update object|New attribute values|Object attributes and metadata|unchanged DELETE|Object|Delete object|Empty|Object metadata|N/A GET|Object|Get object|Empty|Object attributes and metadata|N/A GET|Collection|Get collection|Empty|Object attributes and collection metadata|N/A    ## Common Query Parameters  The following are some common query parameters that are supported by many method/URI combinations. Individual URIs may document additional parameters. Note that multiple query parameters can be used together in a single URI, separated by the ampersand character. For example:  ``` ; Request for the MsgVpns collection using two hypothetical query parameters ; \"q1\" and \"q2\" with values \"val1\" and \"val2\" respectively /SEMP/v2/action/msgVpns?q1=val1&q2=val2 ```  ### select  Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. Use this query parameter to limit the size of the returned data for each returned object, return only those fields that are desired, or exclude fields that are not desired.  The value of `select` is a comma-separated list of attribute names. If the list contains attribute names that are not prefaced by `-`, only those attributes are included in the response. If the list contains attribute names that are prefaced by `-`, those attributes are excluded from the response. If the list contains both types, then the difference of the first set of attributes and the second set of attributes is returned. If the list is empty (i.e. `select=`), no attributes are returned.  All attributes that are prefaced by `-` must follow all attributes that are not prefaced by `-`. In addition, each attribute name in the list must match at least one attribute in the object.  Names may include the `*` wildcard (zero or more characters). Nested attribute names are supported using periods (e.g. `parentName.childName`).  Some examples:  ``` ; List of all MsgVpn names /SEMP/v2/action/msgVpns?select=msgVpnName ; List of all MsgVpn and their attributes except for their names /SEMP/v2/action/msgVpns?select=-msgVpnName ; Authentication attributes of MsgVpn \"finance\" /SEMP/v2/action/msgVpns/finance?select=authentication* ; All attributes of MsgVpn \"finance\" except for authentication attributes /SEMP/v2/action/msgVpns/finance?select=-authentication* ; Access related attributes of Queue \"orderQ\" of MsgVpn \"finance\" /SEMP/v2/action/msgVpns/finance/queues/orderQ?select=owner,permission ```  ### where  Include in the response only objects where certain conditions are true. Use this query parameter to limit which objects are returned to those whose attribute values meet the given conditions.  The value of `where` is a comma-separated list of expressions. All expressions must be true for the object to be included in the response. Each expression takes the form:  ``` expression  = attribute-name OP value OP          = '==' | '!=' | '&lt;' | '&gt;' | '&lt;=' | '&gt;=' ```  `value` may be a number, string, `true`, or `false`, as appropriate for the type of `attribute-name`. Greater-than and less-than comparisons only work for numbers. A `*` in a string `value` is interpreted as a wildcard (zero or more characters). Some examples:  ``` ; Only enabled MsgVpns /SEMP/v2/action/msgVpns?where=enabled==true ; Only MsgVpns using basic non-LDAP authentication /SEMP/v2/action/msgVpns?where=authenticationBasicEnabled==true,authenticationBasicType!=ldap ; Only MsgVpns that allow more than 100 client connections /SEMP/v2/action/msgVpns?where=maxConnectionCount>100 ; Only MsgVpns with msgVpnName starting with \"B\": /SEMP/v2/action/msgVpns?where=msgVpnName==B* ```  ### count  Limit the count of objects in the response. This can be useful to limit the size of the response for large collections. The minimum value for `count` is `1` and the default is `10`. There is also a per-collection maximum value to limit request handling time.  `count` does not guarantee that a minimum number of objects will be returned. A page may contain fewer than `count` objects or even be empty. Additional objects may nonetheless be available for retrieval on subsequent pages. See the `cursor` query parameter documentation for more information on paging.  For example: ``` ; Up to 25 MsgVpns /SEMP/v2/action/msgVpns?count=25 ```  ### cursor  The cursor, or position, for the next page of objects. Cursors are opaque data that should not be created or interpreted by SEMP clients, and should only be used as described below.  When a request is made for a collection and there may be additional objects available for retrieval that are not included in the initial response, the response will include a `cursorQuery` field containing a cursor. The value of this field can be specified in the `cursor` query parameter of a subsequent request to retrieve the next page of objects. For convenience, an appropriate URI is constructed automatically by the broker and included in the `nextPageUri` field of the response. This URI can be used directly to retrieve the next page of objects.  Applications must continue to follow the `nextPageUri` if one is provided in order to retrieve the full set of objects associated with the request, even if a page contains fewer than the requested number of objects (see the `count` query parameter documentation) or is empty.  ### opaquePassword  Attributes with the opaque property are also write-only and so cannot normally be retrieved in a GET. However, when a password is provided in the `opaquePassword` query parameter, attributes with the opaque property are retrieved in a GET in opaque form, encrypted with this password. The query parameter can also be used on a POST, PATCH, or PUT to set opaque attributes using opaque attribute values retrieved in a GET, so long as:  1. the same password that was used to retrieve the opaque attribute values is provided; and  2. the broker to which the request is being sent has the same major and minor SEMP version as the broker that produced the opaque attribute values.  The password provided in the query parameter must be a minimum of 8 characters and a maximum of 128 characters.  The query parameter can only be used in the configuration API, and only over HTTPS.  ## Authentication  When a client makes its first SEMPv2 request, it must supply a username and password using HTTP Basic authentication.  If authentication is successful, the broker returns a cookie containing a session key. The client can omit the username and password from subsequent requests, because the broker now uses the session cookie for authentication instead. When the session expires or is deleted, the client must provide the username and password again, and the broker creates a new session.  There are a limited number of session slots available on the broker. The broker returns 529 No SEMP Session Available if it is not able to allocate a session. For this reason, all clients that use SEMPv2 should support cookies.  If certain attributes—such as a user's password—are changed, the broker automatically deletes the affected sessions. These attributes are documented below. However, changes in external user configuration data stored on a RADIUS or LDAP server do not trigger the broker to delete the associated session(s), therefore you must do this manually, if required.  A client can retrieve its current session information using the /about/user endpoint, delete its own session using the /about/user/logout endpoint, and manage all sessions using the /sessions endpoint.  ## Help  Visit [our website](https://solace.com) to learn more about Solace.  You can also download the SEMP API specifications by clicking [here](https://solace.com/downloads/).  If you need additional support, please contact us at [support@solace.com](mailto:support@solace.com).  ## Notes  Note|Description :---:|:--- 1|This specification defines SEMP starting in \"v2\", and not the original SEMP \"v1\" interface. Request and response formats between \"v1\" and \"v2\" are entirely incompatible, although both protocols share a common port configuration on the Solace PubSub+ broker. They are differentiated by the initial portion of the URI path, one of either \"/SEMP/\" or \"/SEMP/v2/\" 2|This API is partially implemented. Only a subset of all objects are available. 3|Read-only attributes may appear in POST and PUT/PATCH requests. However, if a read-only attribute is not marked as identifying, it will be ignored during a PUT/PATCH. 4|On a PUT, if the SEMP user is not authorized to modify the attribute, its value is left unchanged rather than set to default. In addition, the values of write-only attributes are not set to their defaults on a PUT, except in the following two cases: there is a mutual requires relationship with another non-write-only attribute, both attributes are absent from the request, and the non-write-only attribute is not currently set to its default value; or the attribute is also opaque and the `opaquePassword` query parameter is provided in the request. 5|On a PUT, if the object does not exist, it is created first.  

  For more information, please visit [http://www.solace.com](http://www.solace.com)

*Automatically generated by the [Swagger Codegen](https://github.com/swagger-api/swagger-codegen)*


## Requirements

Building the API client library requires:
1. Java 1.7+
2. Maven/Gradle

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn clean install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn clean deploy
```

Refer to the [OSSRH Guide](http://central.sonatype.org/pages/ossrh-guide.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>io.swagger</groupId>
  <artifactId>swagger-java-client</artifactId>
  <version>1.0.0</version>
  <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "io.swagger:swagger-java-client:1.0.0"
```

### Others

At first generate the JAR by executing:

```shell
mvn clean package
```

Then manually install the following JARs:

* `target/swagger-java-client-1.0.0.jar`
* `target/lib/*.jar`

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

import io.swagger.client.*;
import io.swagger.client.auth.*;
import io.swagger.client.model.*;
import io.swagger.client.api.AboutApi;

import java.io.File;
import java.util.*;

public class AboutApiExample {

    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        
        // Configure HTTP basic authorization: basicAuth
        HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
        basicAuth.setUsername("YOUR USERNAME");
        basicAuth.setPassword("YOUR PASSWORD");

        AboutApi apiInstance = new AboutApi();
        AboutUserLogout body = new AboutUserLogout(); // AboutUserLogout | The Logout action's attributes.
        try {
            SempMetaOnlyResponse result = apiInstance.doAboutUserLogout(body);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling AboutApi#doAboutUserLogout");
            e.printStackTrace();
        }
    }
}

```

## Documentation for API Endpoints

All URIs are relative to *http://www.solace.com/SEMP/v2/action*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*AboutApi* | [**doAboutUserLogout**](docs/AboutApi.md#doAboutUserLogout) | **PUT** /about/user/logout | Logout of the current session.
*AboutApi* | [**getAbout**](docs/AboutApi.md#getAbout) | **GET** /about | Get an About object.
*AboutApi* | [**getAboutApi**](docs/AboutApi.md#getAboutApi) | **GET** /about/api | Get an API Description object.
*AboutApi* | [**getAboutUser**](docs/AboutApi.md#getAboutUser) | **GET** /about/user | Get a User object.
*AboutApi* | [**getAboutUserMsgVpn**](docs/AboutApi.md#getAboutUserMsgVpn) | **GET** /about/user/msgVpns/{msgVpnName} | Get a User Message VPN object.
*AboutApi* | [**getAboutUserMsgVpns**](docs/AboutApi.md#getAboutUserMsgVpns) | **GET** /about/user/msgVpns | Get a list of User Message VPN objects.
*AllApi* | [**doAboutUserLogout**](docs/AllApi.md#doAboutUserLogout) | **PUT** /about/user/logout | Logout of the current session.
*AllApi* | [**doCertAuthorityRefreshCrl**](docs/AllApi.md#doCertAuthorityRefreshCrl) | **PUT** /certAuthorities/{certAuthorityName}/refreshCrl | Refresh the CRL file for the Certificate Authority.
*AllApi* | [**doClientCertAuthorityRefreshCrl**](docs/AllApi.md#doClientCertAuthorityRefreshCrl) | **PUT** /clientCertAuthorities/{certAuthorityName}/refreshCrl | Refresh the CRL file for the Client Certificate Authority.
*AllApi* | [**doConfigSyncAssertLeaderMsgVpn**](docs/AllApi.md#doConfigSyncAssertLeaderMsgVpn) | **PUT** /configSyncAssertLeaderMsgVpn | Assert leadership of the specified Config Sync table, forcing any other leader&#39;s content to be overwritten with our own. Use whenever a High Availability pair fall out of sync. Config Sync must be a leader for the selected table.
*AllApi* | [**doConfigSyncAssertLeaderRouter**](docs/AllApi.md#doConfigSyncAssertLeaderRouter) | **PUT** /configSyncAssertLeaderRouter | Assert leadership of the specified Config Sync table, forcing any other leader&#39;s content to be overwritten with our own. Use whenever a High Availability pair fall out of sync. Config Sync must be a leader for the selected table.
*AllApi* | [**doConfigSyncResyncFollowerMsgVpn**](docs/AllApi.md#doConfigSyncResyncFollowerMsgVpn) | **PUT** /configSyncResyncFollowerMsgVpn | Resync the selected Config Sync table, forcing this follower&#39;s content to be overwritten with that from a leader. Config Sync must be a follower for the selected table.
*AllApi* | [**doConfigSyncResyncLeaderMsgVpn**](docs/AllApi.md#doConfigSyncResyncLeaderMsgVpn) | **PUT** /configSyncResyncLeaderMsgVpn | Resync the selected Config Sync table, forcing this leader&#39;s content to be overwritten with that from a leader. Config Sync must be a leader for the selected table.
*AllApi* | [**doConfigSyncResyncLeaderRouter**](docs/AllApi.md#doConfigSyncResyncLeaderRouter) | **PUT** /configSyncResyncLeaderRouter | Resync the selected Config Sync table, forcing this leader&#39;s content to be overwritten with that from a leader. Config Sync must be a leader for the selected table.
*AllApi* | [**doGuaranteedMsgingDefragmentMsgSpoolFilesStart**](docs/AllApi.md#doGuaranteedMsgingDefragmentMsgSpoolFilesStart) | **PUT** /guaranteedMsgingDefragmentMsgSpoolFilesStart | Start a spool file defragmentation run.
*AllApi* | [**doGuaranteedMsgingDefragmentMsgSpoolFilesStop**](docs/AllApi.md#doGuaranteedMsgingDefragmentMsgSpoolFilesStop) | **PUT** /guaranteedMsgingDefragmentMsgSpoolFilesStop | Stop a spool file defragmentation run.
*AllApi* | [**doMsgVpnAuthenticationOauthProviderClearStats**](docs/AllApi.md#doMsgVpnAuthenticationOauthProviderClearStats) | **PUT** /msgVpns/{msgVpnName}/authenticationOauthProviders/{oauthProviderName}/clearStats | Clear the statistics for the OAuth Provider.
*AllApi* | [**doMsgVpnBridgeClearEvent**](docs/AllApi.md#doMsgVpnBridgeClearEvent) | **PUT** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/clearEvent | Clear an event for the Bridge so it can be generated anew.
*AllApi* | [**doMsgVpnBridgeClearStats**](docs/AllApi.md#doMsgVpnBridgeClearStats) | **PUT** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/clearStats | Clear the statistics for the Bridge.
*AllApi* | [**doMsgVpnBridgeDisconnect**](docs/AllApi.md#doMsgVpnBridgeDisconnect) | **PUT** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/disconnect | Disconnect the Bridge.
*AllApi* | [**doMsgVpnClearMsgSpoolStats**](docs/AllApi.md#doMsgVpnClearMsgSpoolStats) | **PUT** /msgVpns/{msgVpnName}/clearMsgSpoolStats | Clear the message spool statistics for the Message VPN.
*AllApi* | [**doMsgVpnClearReplicationStats**](docs/AllApi.md#doMsgVpnClearReplicationStats) | **PUT** /msgVpns/{msgVpnName}/clearReplicationStats | Clear the replication statistics for the Message VPN.
*AllApi* | [**doMsgVpnClearServiceStats**](docs/AllApi.md#doMsgVpnClearServiceStats) | **PUT** /msgVpns/{msgVpnName}/clearServiceStats | Clear the service statistics for the Message VPN.
*AllApi* | [**doMsgVpnClearStats**](docs/AllApi.md#doMsgVpnClearStats) | **PUT** /msgVpns/{msgVpnName}/clearStats | Clear the client statistics for the Message VPN.
*AllApi* | [**doMsgVpnClientClearEvent**](docs/AllApi.md#doMsgVpnClientClearEvent) | **PUT** /msgVpns/{msgVpnName}/clients/{clientName}/clearEvent | Clear an event for the Client so it can be generated anew.
*AllApi* | [**doMsgVpnClientClearStats**](docs/AllApi.md#doMsgVpnClientClearStats) | **PUT** /msgVpns/{msgVpnName}/clients/{clientName}/clearStats | Clear the statistics for the Client.
*AllApi* | [**doMsgVpnClientDisconnect**](docs/AllApi.md#doMsgVpnClientDisconnect) | **PUT** /msgVpns/{msgVpnName}/clients/{clientName}/disconnect | Disconnect the Client.
*AllApi* | [**doMsgVpnClientTransactedSessionDelete**](docs/AllApi.md#doMsgVpnClientTransactedSessionDelete) | **PUT** /msgVpns/{msgVpnName}/clients/{clientName}/transactedSessions/{sessionName}/delete | Delete the Transacted Session.
*AllApi* | [**doMsgVpnDistributedCacheClusterInstanceBackupCachedMsgs**](docs/AllApi.md#doMsgVpnDistributedCacheClusterInstanceBackupCachedMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/backupCachedMsgs | Backup cached messages of the Cache Instance to disk.
*AllApi* | [**doMsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs**](docs/AllApi.md#doMsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/cancelBackupCachedMsgs | Cancel the backup of cached messages from the Cache Instance.
*AllApi* | [**doMsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs**](docs/AllApi.md#doMsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/cancelRestoreCachedMsgs | Cancel the restore of cached messages to the Cache Instance.
*AllApi* | [**doMsgVpnDistributedCacheClusterInstanceClearEvent**](docs/AllApi.md#doMsgVpnDistributedCacheClusterInstanceClearEvent) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/clearEvent | Clear an event for the Cache Instance so it can be generated anew.
*AllApi* | [**doMsgVpnDistributedCacheClusterInstanceClearStats**](docs/AllApi.md#doMsgVpnDistributedCacheClusterInstanceClearStats) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/clearStats | Clear the statistics for the Cache Instance.
*AllApi* | [**doMsgVpnDistributedCacheClusterInstanceDeleteMsgs**](docs/AllApi.md#doMsgVpnDistributedCacheClusterInstanceDeleteMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/deleteMsgs | Delete messages covered by the given topic in the Cache Instance.
*AllApi* | [**doMsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs**](docs/AllApi.md#doMsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/restoreCachedMsgs | Restore cached messages for the Cache Instance from disk.
*AllApi* | [**doMsgVpnDistributedCacheClusterInstanceStart**](docs/AllApi.md#doMsgVpnDistributedCacheClusterInstanceStart) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/start | Start the Cache Instance.
*AllApi* | [**doMsgVpnMqttSessionClearStats**](docs/AllApi.md#doMsgVpnMqttSessionClearStats) | **PUT** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter}/clearStats | Clear the statistics for the MQTT Session.
*AllApi* | [**doMsgVpnQueueCancelReplay**](docs/AllApi.md#doMsgVpnQueueCancelReplay) | **PUT** /msgVpns/{msgVpnName}/queues/{queueName}/cancelReplay | Cancel the replay of messages to the Queue.
*AllApi* | [**doMsgVpnQueueClearStats**](docs/AllApi.md#doMsgVpnQueueClearStats) | **PUT** /msgVpns/{msgVpnName}/queues/{queueName}/clearStats | Clear the statistics for the Queue.
*AllApi* | [**doMsgVpnQueueMsgDelete**](docs/AllApi.md#doMsgVpnQueueMsgDelete) | **PUT** /msgVpns/{msgVpnName}/queues/{queueName}/msgs/{msgId}/delete | Delete the Message from the Queue.
*AllApi* | [**doMsgVpnQueueStartReplay**](docs/AllApi.md#doMsgVpnQueueStartReplay) | **PUT** /msgVpns/{msgVpnName}/queues/{queueName}/startReplay | Start the replay of messages to the Queue.
*AllApi* | [**doMsgVpnReplayLogTrimLoggedMsgs**](docs/AllApi.md#doMsgVpnReplayLogTrimLoggedMsgs) | **PUT** /msgVpns/{msgVpnName}/replayLogs/{replayLogName}/trimLoggedMsgs | Trim (delete) messages from the Replay Log.
*AllApi* | [**doMsgVpnRestDeliveryPointRestConsumerClearStats**](docs/AllApi.md#doMsgVpnRestDeliveryPointRestConsumerClearStats) | **PUT** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers/{restConsumerName}/clearStats | Clear the statistics for the REST Consumer.
*AllApi* | [**doMsgVpnTopicEndpointCancelReplay**](docs/AllApi.md#doMsgVpnTopicEndpointCancelReplay) | **PUT** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/cancelReplay | Cancel the replay of messages to the Topic Endpoint.
*AllApi* | [**doMsgVpnTopicEndpointClearStats**](docs/AllApi.md#doMsgVpnTopicEndpointClearStats) | **PUT** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/clearStats | Clear the statistics for the Topic Endpoint.
*AllApi* | [**doMsgVpnTopicEndpointMsgDelete**](docs/AllApi.md#doMsgVpnTopicEndpointMsgDelete) | **PUT** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/msgs/{msgId}/delete | Delete the Message from the Topic Endpoint.
*AllApi* | [**doMsgVpnTopicEndpointStartReplay**](docs/AllApi.md#doMsgVpnTopicEndpointStartReplay) | **PUT** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/startReplay | Start the replay of messages to the Topic Endpoint.
*AllApi* | [**doMsgVpnTransactionCommit**](docs/AllApi.md#doMsgVpnTransactionCommit) | **PUT** /msgVpns/{msgVpnName}/transactions/{xid}/commit | Commit the Transaction.
*AllApi* | [**doMsgVpnTransactionDelete**](docs/AllApi.md#doMsgVpnTransactionDelete) | **PUT** /msgVpns/{msgVpnName}/transactions/{xid}/delete | Delete the Transaction.
*AllApi* | [**doMsgVpnTransactionRollback**](docs/AllApi.md#doMsgVpnTransactionRollback) | **PUT** /msgVpns/{msgVpnName}/transactions/{xid}/rollback | Rollback the Transaction.
*AllApi* | [**doSessionDelete**](docs/AllApi.md#doSessionDelete) | **PUT** /sessions/{sessionUsername},{sessionId}/delete | Delete the session.
*AllApi* | [**getAbout**](docs/AllApi.md#getAbout) | **GET** /about | Get an About object.
*AllApi* | [**getAboutApi**](docs/AllApi.md#getAboutApi) | **GET** /about/api | Get an API Description object.
*AllApi* | [**getAboutUser**](docs/AllApi.md#getAboutUser) | **GET** /about/user | Get a User object.
*AllApi* | [**getAboutUserMsgVpn**](docs/AllApi.md#getAboutUserMsgVpn) | **GET** /about/user/msgVpns/{msgVpnName} | Get a User Message VPN object.
*AllApi* | [**getAboutUserMsgVpns**](docs/AllApi.md#getAboutUserMsgVpns) | **GET** /about/user/msgVpns | Get a list of User Message VPN objects.
*AllApi* | [**getBroker**](docs/AllApi.md#getBroker) | **GET** / | Get a Broker object.
*AllApi* | [**getCertAuthorities**](docs/AllApi.md#getCertAuthorities) | **GET** /certAuthorities | Get a list of Certificate Authority objects.
*AllApi* | [**getCertAuthority**](docs/AllApi.md#getCertAuthority) | **GET** /certAuthorities/{certAuthorityName} | Get a Certificate Authority object.
*AllApi* | [**getClientCertAuthorities**](docs/AllApi.md#getClientCertAuthorities) | **GET** /clientCertAuthorities | Get a list of Client Certificate Authority objects.
*AllApi* | [**getClientCertAuthority**](docs/AllApi.md#getClientCertAuthority) | **GET** /clientCertAuthorities/{certAuthorityName} | Get a Client Certificate Authority object.
*AllApi* | [**getMsgVpn**](docs/AllApi.md#getMsgVpn) | **GET** /msgVpns/{msgVpnName} | Get a Message VPN object.
*AllApi* | [**getMsgVpnAuthenticationOauthProvider**](docs/AllApi.md#getMsgVpnAuthenticationOauthProvider) | **GET** /msgVpns/{msgVpnName}/authenticationOauthProviders/{oauthProviderName} | Get an OAuth Provider object.
*AllApi* | [**getMsgVpnAuthenticationOauthProviders**](docs/AllApi.md#getMsgVpnAuthenticationOauthProviders) | **GET** /msgVpns/{msgVpnName}/authenticationOauthProviders | Get a list of OAuth Provider objects.
*AllApi* | [**getMsgVpnBridge**](docs/AllApi.md#getMsgVpnBridge) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter} | Get a Bridge object.
*AllApi* | [**getMsgVpnBridges**](docs/AllApi.md#getMsgVpnBridges) | **GET** /msgVpns/{msgVpnName}/bridges | Get a list of Bridge objects.
*AllApi* | [**getMsgVpnClient**](docs/AllApi.md#getMsgVpnClient) | **GET** /msgVpns/{msgVpnName}/clients/{clientName} | Get a Client object.
*AllApi* | [**getMsgVpnClientTransactedSession**](docs/AllApi.md#getMsgVpnClientTransactedSession) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/transactedSessions/{sessionName} | Get a Client Transacted Session object.
*AllApi* | [**getMsgVpnClientTransactedSessions**](docs/AllApi.md#getMsgVpnClientTransactedSessions) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/transactedSessions | Get a list of Client Transacted Session objects.
*AllApi* | [**getMsgVpnClients**](docs/AllApi.md#getMsgVpnClients) | **GET** /msgVpns/{msgVpnName}/clients | Get a list of Client objects.
*AllApi* | [**getMsgVpnDistributedCache**](docs/AllApi.md#getMsgVpnDistributedCache) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName} | Get a Distributed Cache object.
*AllApi* | [**getMsgVpnDistributedCacheCluster**](docs/AllApi.md#getMsgVpnDistributedCacheCluster) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName} | Get a Cache Cluster object.
*AllApi* | [**getMsgVpnDistributedCacheClusterInstance**](docs/AllApi.md#getMsgVpnDistributedCacheClusterInstance) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName} | Get a Cache Instance object.
*AllApi* | [**getMsgVpnDistributedCacheClusterInstances**](docs/AllApi.md#getMsgVpnDistributedCacheClusterInstances) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances | Get a list of Cache Instance objects.
*AllApi* | [**getMsgVpnDistributedCacheClusters**](docs/AllApi.md#getMsgVpnDistributedCacheClusters) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters | Get a list of Cache Cluster objects.
*AllApi* | [**getMsgVpnDistributedCaches**](docs/AllApi.md#getMsgVpnDistributedCaches) | **GET** /msgVpns/{msgVpnName}/distributedCaches | Get a list of Distributed Cache objects.
*AllApi* | [**getMsgVpnMqttSession**](docs/AllApi.md#getMsgVpnMqttSession) | **GET** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter} | Get an MQTT Session object.
*AllApi* | [**getMsgVpnMqttSessions**](docs/AllApi.md#getMsgVpnMqttSessions) | **GET** /msgVpns/{msgVpnName}/mqttSessions | Get a list of MQTT Session objects.
*AllApi* | [**getMsgVpnQueue**](docs/AllApi.md#getMsgVpnQueue) | **GET** /msgVpns/{msgVpnName}/queues/{queueName} | Get a Queue object.
*AllApi* | [**getMsgVpnQueueMsg**](docs/AllApi.md#getMsgVpnQueueMsg) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/msgs/{msgId} | Get a Queue Message object.
*AllApi* | [**getMsgVpnQueueMsgs**](docs/AllApi.md#getMsgVpnQueueMsgs) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/msgs | Get a list of Queue Message objects.
*AllApi* | [**getMsgVpnQueues**](docs/AllApi.md#getMsgVpnQueues) | **GET** /msgVpns/{msgVpnName}/queues | Get a list of Queue objects.
*AllApi* | [**getMsgVpnReplayLog**](docs/AllApi.md#getMsgVpnReplayLog) | **GET** /msgVpns/{msgVpnName}/replayLogs/{replayLogName} | Get a Replay Log object.
*AllApi* | [**getMsgVpnReplayLogs**](docs/AllApi.md#getMsgVpnReplayLogs) | **GET** /msgVpns/{msgVpnName}/replayLogs | Get a list of Replay Log objects.
*AllApi* | [**getMsgVpnRestDeliveryPoint**](docs/AllApi.md#getMsgVpnRestDeliveryPoint) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName} | Get a REST Delivery Point object.
*AllApi* | [**getMsgVpnRestDeliveryPointRestConsumer**](docs/AllApi.md#getMsgVpnRestDeliveryPointRestConsumer) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers/{restConsumerName} | Get a REST Consumer object.
*AllApi* | [**getMsgVpnRestDeliveryPointRestConsumers**](docs/AllApi.md#getMsgVpnRestDeliveryPointRestConsumers) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers | Get a list of REST Consumer objects.
*AllApi* | [**getMsgVpnRestDeliveryPoints**](docs/AllApi.md#getMsgVpnRestDeliveryPoints) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints | Get a list of REST Delivery Point objects.
*AllApi* | [**getMsgVpnTopicEndpoint**](docs/AllApi.md#getMsgVpnTopicEndpoint) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName} | Get a Topic Endpoint object.
*AllApi* | [**getMsgVpnTopicEndpointMsg**](docs/AllApi.md#getMsgVpnTopicEndpointMsg) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/msgs/{msgId} | Get a Topic Endpoint Message object.
*AllApi* | [**getMsgVpnTopicEndpointMsgs**](docs/AllApi.md#getMsgVpnTopicEndpointMsgs) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/msgs | Get a list of Topic Endpoint Message objects.
*AllApi* | [**getMsgVpnTopicEndpoints**](docs/AllApi.md#getMsgVpnTopicEndpoints) | **GET** /msgVpns/{msgVpnName}/topicEndpoints | Get a list of Topic Endpoint objects.
*AllApi* | [**getMsgVpnTransaction**](docs/AllApi.md#getMsgVpnTransaction) | **GET** /msgVpns/{msgVpnName}/transactions/{xid} | Get a Replicated Local Transaction or XA Transaction object.
*AllApi* | [**getMsgVpnTransactions**](docs/AllApi.md#getMsgVpnTransactions) | **GET** /msgVpns/{msgVpnName}/transactions | Get a list of Replicated Local Transaction or XA Transaction objects.
*AllApi* | [**getMsgVpns**](docs/AllApi.md#getMsgVpns) | **GET** /msgVpns | Get a list of Message VPN objects.
*AllApi* | [**getSession**](docs/AllApi.md#getSession) | **GET** /sessions/{sessionUsername},{sessionId} | Get a Session object.
*AllApi* | [**getSessions**](docs/AllApi.md#getSessions) | **GET** /sessions | Get a list of Session objects.
*AuthenticationOauthProviderApi* | [**doMsgVpnAuthenticationOauthProviderClearStats**](docs/AuthenticationOauthProviderApi.md#doMsgVpnAuthenticationOauthProviderClearStats) | **PUT** /msgVpns/{msgVpnName}/authenticationOauthProviders/{oauthProviderName}/clearStats | Clear the statistics for the OAuth Provider.
*AuthenticationOauthProviderApi* | [**getMsgVpnAuthenticationOauthProvider**](docs/AuthenticationOauthProviderApi.md#getMsgVpnAuthenticationOauthProvider) | **GET** /msgVpns/{msgVpnName}/authenticationOauthProviders/{oauthProviderName} | Get an OAuth Provider object.
*AuthenticationOauthProviderApi* | [**getMsgVpnAuthenticationOauthProviders**](docs/AuthenticationOauthProviderApi.md#getMsgVpnAuthenticationOauthProviders) | **GET** /msgVpns/{msgVpnName}/authenticationOauthProviders | Get a list of OAuth Provider objects.
*BridgeApi* | [**doMsgVpnBridgeClearEvent**](docs/BridgeApi.md#doMsgVpnBridgeClearEvent) | **PUT** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/clearEvent | Clear an event for the Bridge so it can be generated anew.
*BridgeApi* | [**doMsgVpnBridgeClearStats**](docs/BridgeApi.md#doMsgVpnBridgeClearStats) | **PUT** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/clearStats | Clear the statistics for the Bridge.
*BridgeApi* | [**doMsgVpnBridgeDisconnect**](docs/BridgeApi.md#doMsgVpnBridgeDisconnect) | **PUT** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/disconnect | Disconnect the Bridge.
*BridgeApi* | [**getMsgVpnBridge**](docs/BridgeApi.md#getMsgVpnBridge) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter} | Get a Bridge object.
*BridgeApi* | [**getMsgVpnBridges**](docs/BridgeApi.md#getMsgVpnBridges) | **GET** /msgVpns/{msgVpnName}/bridges | Get a list of Bridge objects.
*CertAuthorityApi* | [**doCertAuthorityRefreshCrl**](docs/CertAuthorityApi.md#doCertAuthorityRefreshCrl) | **PUT** /certAuthorities/{certAuthorityName}/refreshCrl | Refresh the CRL file for the Certificate Authority.
*CertAuthorityApi* | [**getCertAuthorities**](docs/CertAuthorityApi.md#getCertAuthorities) | **GET** /certAuthorities | Get a list of Certificate Authority objects.
*CertAuthorityApi* | [**getCertAuthority**](docs/CertAuthorityApi.md#getCertAuthority) | **GET** /certAuthorities/{certAuthorityName} | Get a Certificate Authority object.
*ClientApi* | [**doMsgVpnClientClearEvent**](docs/ClientApi.md#doMsgVpnClientClearEvent) | **PUT** /msgVpns/{msgVpnName}/clients/{clientName}/clearEvent | Clear an event for the Client so it can be generated anew.
*ClientApi* | [**doMsgVpnClientClearStats**](docs/ClientApi.md#doMsgVpnClientClearStats) | **PUT** /msgVpns/{msgVpnName}/clients/{clientName}/clearStats | Clear the statistics for the Client.
*ClientApi* | [**doMsgVpnClientDisconnect**](docs/ClientApi.md#doMsgVpnClientDisconnect) | **PUT** /msgVpns/{msgVpnName}/clients/{clientName}/disconnect | Disconnect the Client.
*ClientApi* | [**doMsgVpnClientTransactedSessionDelete**](docs/ClientApi.md#doMsgVpnClientTransactedSessionDelete) | **PUT** /msgVpns/{msgVpnName}/clients/{clientName}/transactedSessions/{sessionName}/delete | Delete the Transacted Session.
*ClientApi* | [**getMsgVpnClient**](docs/ClientApi.md#getMsgVpnClient) | **GET** /msgVpns/{msgVpnName}/clients/{clientName} | Get a Client object.
*ClientApi* | [**getMsgVpnClientTransactedSession**](docs/ClientApi.md#getMsgVpnClientTransactedSession) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/transactedSessions/{sessionName} | Get a Client Transacted Session object.
*ClientApi* | [**getMsgVpnClientTransactedSessions**](docs/ClientApi.md#getMsgVpnClientTransactedSessions) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/transactedSessions | Get a list of Client Transacted Session objects.
*ClientApi* | [**getMsgVpnClients**](docs/ClientApi.md#getMsgVpnClients) | **GET** /msgVpns/{msgVpnName}/clients | Get a list of Client objects.
*ClientCertAuthorityApi* | [**doClientCertAuthorityRefreshCrl**](docs/ClientCertAuthorityApi.md#doClientCertAuthorityRefreshCrl) | **PUT** /clientCertAuthorities/{certAuthorityName}/refreshCrl | Refresh the CRL file for the Client Certificate Authority.
*ClientCertAuthorityApi* | [**getClientCertAuthorities**](docs/ClientCertAuthorityApi.md#getClientCertAuthorities) | **GET** /clientCertAuthorities | Get a list of Client Certificate Authority objects.
*ClientCertAuthorityApi* | [**getClientCertAuthority**](docs/ClientCertAuthorityApi.md#getClientCertAuthority) | **GET** /clientCertAuthorities/{certAuthorityName} | Get a Client Certificate Authority object.
*DistributedCacheApi* | [**doMsgVpnDistributedCacheClusterInstanceBackupCachedMsgs**](docs/DistributedCacheApi.md#doMsgVpnDistributedCacheClusterInstanceBackupCachedMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/backupCachedMsgs | Backup cached messages of the Cache Instance to disk.
*DistributedCacheApi* | [**doMsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs**](docs/DistributedCacheApi.md#doMsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/cancelBackupCachedMsgs | Cancel the backup of cached messages from the Cache Instance.
*DistributedCacheApi* | [**doMsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs**](docs/DistributedCacheApi.md#doMsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/cancelRestoreCachedMsgs | Cancel the restore of cached messages to the Cache Instance.
*DistributedCacheApi* | [**doMsgVpnDistributedCacheClusterInstanceClearEvent**](docs/DistributedCacheApi.md#doMsgVpnDistributedCacheClusterInstanceClearEvent) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/clearEvent | Clear an event for the Cache Instance so it can be generated anew.
*DistributedCacheApi* | [**doMsgVpnDistributedCacheClusterInstanceClearStats**](docs/DistributedCacheApi.md#doMsgVpnDistributedCacheClusterInstanceClearStats) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/clearStats | Clear the statistics for the Cache Instance.
*DistributedCacheApi* | [**doMsgVpnDistributedCacheClusterInstanceDeleteMsgs**](docs/DistributedCacheApi.md#doMsgVpnDistributedCacheClusterInstanceDeleteMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/deleteMsgs | Delete messages covered by the given topic in the Cache Instance.
*DistributedCacheApi* | [**doMsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs**](docs/DistributedCacheApi.md#doMsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/restoreCachedMsgs | Restore cached messages for the Cache Instance from disk.
*DistributedCacheApi* | [**doMsgVpnDistributedCacheClusterInstanceStart**](docs/DistributedCacheApi.md#doMsgVpnDistributedCacheClusterInstanceStart) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/start | Start the Cache Instance.
*DistributedCacheApi* | [**getMsgVpnDistributedCache**](docs/DistributedCacheApi.md#getMsgVpnDistributedCache) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName} | Get a Distributed Cache object.
*DistributedCacheApi* | [**getMsgVpnDistributedCacheCluster**](docs/DistributedCacheApi.md#getMsgVpnDistributedCacheCluster) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName} | Get a Cache Cluster object.
*DistributedCacheApi* | [**getMsgVpnDistributedCacheClusterInstance**](docs/DistributedCacheApi.md#getMsgVpnDistributedCacheClusterInstance) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName} | Get a Cache Instance object.
*DistributedCacheApi* | [**getMsgVpnDistributedCacheClusterInstances**](docs/DistributedCacheApi.md#getMsgVpnDistributedCacheClusterInstances) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances | Get a list of Cache Instance objects.
*DistributedCacheApi* | [**getMsgVpnDistributedCacheClusters**](docs/DistributedCacheApi.md#getMsgVpnDistributedCacheClusters) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters | Get a list of Cache Cluster objects.
*DistributedCacheApi* | [**getMsgVpnDistributedCaches**](docs/DistributedCacheApi.md#getMsgVpnDistributedCaches) | **GET** /msgVpns/{msgVpnName}/distributedCaches | Get a list of Distributed Cache objects.
*MqttSessionApi* | [**doMsgVpnMqttSessionClearStats**](docs/MqttSessionApi.md#doMsgVpnMqttSessionClearStats) | **PUT** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter}/clearStats | Clear the statistics for the MQTT Session.
*MqttSessionApi* | [**getMsgVpnMqttSession**](docs/MqttSessionApi.md#getMsgVpnMqttSession) | **GET** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter} | Get an MQTT Session object.
*MqttSessionApi* | [**getMsgVpnMqttSessions**](docs/MqttSessionApi.md#getMsgVpnMqttSessions) | **GET** /msgVpns/{msgVpnName}/mqttSessions | Get a list of MQTT Session objects.
*MsgVpnApi* | [**doMsgVpnAuthenticationOauthProviderClearStats**](docs/MsgVpnApi.md#doMsgVpnAuthenticationOauthProviderClearStats) | **PUT** /msgVpns/{msgVpnName}/authenticationOauthProviders/{oauthProviderName}/clearStats | Clear the statistics for the OAuth Provider.
*MsgVpnApi* | [**doMsgVpnBridgeClearEvent**](docs/MsgVpnApi.md#doMsgVpnBridgeClearEvent) | **PUT** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/clearEvent | Clear an event for the Bridge so it can be generated anew.
*MsgVpnApi* | [**doMsgVpnBridgeClearStats**](docs/MsgVpnApi.md#doMsgVpnBridgeClearStats) | **PUT** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/clearStats | Clear the statistics for the Bridge.
*MsgVpnApi* | [**doMsgVpnBridgeDisconnect**](docs/MsgVpnApi.md#doMsgVpnBridgeDisconnect) | **PUT** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/disconnect | Disconnect the Bridge.
*MsgVpnApi* | [**doMsgVpnClearMsgSpoolStats**](docs/MsgVpnApi.md#doMsgVpnClearMsgSpoolStats) | **PUT** /msgVpns/{msgVpnName}/clearMsgSpoolStats | Clear the message spool statistics for the Message VPN.
*MsgVpnApi* | [**doMsgVpnClearReplicationStats**](docs/MsgVpnApi.md#doMsgVpnClearReplicationStats) | **PUT** /msgVpns/{msgVpnName}/clearReplicationStats | Clear the replication statistics for the Message VPN.
*MsgVpnApi* | [**doMsgVpnClearServiceStats**](docs/MsgVpnApi.md#doMsgVpnClearServiceStats) | **PUT** /msgVpns/{msgVpnName}/clearServiceStats | Clear the service statistics for the Message VPN.
*MsgVpnApi* | [**doMsgVpnClearStats**](docs/MsgVpnApi.md#doMsgVpnClearStats) | **PUT** /msgVpns/{msgVpnName}/clearStats | Clear the client statistics for the Message VPN.
*MsgVpnApi* | [**doMsgVpnClientClearEvent**](docs/MsgVpnApi.md#doMsgVpnClientClearEvent) | **PUT** /msgVpns/{msgVpnName}/clients/{clientName}/clearEvent | Clear an event for the Client so it can be generated anew.
*MsgVpnApi* | [**doMsgVpnClientClearStats**](docs/MsgVpnApi.md#doMsgVpnClientClearStats) | **PUT** /msgVpns/{msgVpnName}/clients/{clientName}/clearStats | Clear the statistics for the Client.
*MsgVpnApi* | [**doMsgVpnClientDisconnect**](docs/MsgVpnApi.md#doMsgVpnClientDisconnect) | **PUT** /msgVpns/{msgVpnName}/clients/{clientName}/disconnect | Disconnect the Client.
*MsgVpnApi* | [**doMsgVpnClientTransactedSessionDelete**](docs/MsgVpnApi.md#doMsgVpnClientTransactedSessionDelete) | **PUT** /msgVpns/{msgVpnName}/clients/{clientName}/transactedSessions/{sessionName}/delete | Delete the Transacted Session.
*MsgVpnApi* | [**doMsgVpnDistributedCacheClusterInstanceBackupCachedMsgs**](docs/MsgVpnApi.md#doMsgVpnDistributedCacheClusterInstanceBackupCachedMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/backupCachedMsgs | Backup cached messages of the Cache Instance to disk.
*MsgVpnApi* | [**doMsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs**](docs/MsgVpnApi.md#doMsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/cancelBackupCachedMsgs | Cancel the backup of cached messages from the Cache Instance.
*MsgVpnApi* | [**doMsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs**](docs/MsgVpnApi.md#doMsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/cancelRestoreCachedMsgs | Cancel the restore of cached messages to the Cache Instance.
*MsgVpnApi* | [**doMsgVpnDistributedCacheClusterInstanceClearEvent**](docs/MsgVpnApi.md#doMsgVpnDistributedCacheClusterInstanceClearEvent) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/clearEvent | Clear an event for the Cache Instance so it can be generated anew.
*MsgVpnApi* | [**doMsgVpnDistributedCacheClusterInstanceClearStats**](docs/MsgVpnApi.md#doMsgVpnDistributedCacheClusterInstanceClearStats) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/clearStats | Clear the statistics for the Cache Instance.
*MsgVpnApi* | [**doMsgVpnDistributedCacheClusterInstanceDeleteMsgs**](docs/MsgVpnApi.md#doMsgVpnDistributedCacheClusterInstanceDeleteMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/deleteMsgs | Delete messages covered by the given topic in the Cache Instance.
*MsgVpnApi* | [**doMsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs**](docs/MsgVpnApi.md#doMsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/restoreCachedMsgs | Restore cached messages for the Cache Instance from disk.
*MsgVpnApi* | [**doMsgVpnDistributedCacheClusterInstanceStart**](docs/MsgVpnApi.md#doMsgVpnDistributedCacheClusterInstanceStart) | **PUT** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/start | Start the Cache Instance.
*MsgVpnApi* | [**doMsgVpnMqttSessionClearStats**](docs/MsgVpnApi.md#doMsgVpnMqttSessionClearStats) | **PUT** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter}/clearStats | Clear the statistics for the MQTT Session.
*MsgVpnApi* | [**doMsgVpnQueueCancelReplay**](docs/MsgVpnApi.md#doMsgVpnQueueCancelReplay) | **PUT** /msgVpns/{msgVpnName}/queues/{queueName}/cancelReplay | Cancel the replay of messages to the Queue.
*MsgVpnApi* | [**doMsgVpnQueueClearStats**](docs/MsgVpnApi.md#doMsgVpnQueueClearStats) | **PUT** /msgVpns/{msgVpnName}/queues/{queueName}/clearStats | Clear the statistics for the Queue.
*MsgVpnApi* | [**doMsgVpnQueueMsgDelete**](docs/MsgVpnApi.md#doMsgVpnQueueMsgDelete) | **PUT** /msgVpns/{msgVpnName}/queues/{queueName}/msgs/{msgId}/delete | Delete the Message from the Queue.
*MsgVpnApi* | [**doMsgVpnQueueStartReplay**](docs/MsgVpnApi.md#doMsgVpnQueueStartReplay) | **PUT** /msgVpns/{msgVpnName}/queues/{queueName}/startReplay | Start the replay of messages to the Queue.
*MsgVpnApi* | [**doMsgVpnReplayLogTrimLoggedMsgs**](docs/MsgVpnApi.md#doMsgVpnReplayLogTrimLoggedMsgs) | **PUT** /msgVpns/{msgVpnName}/replayLogs/{replayLogName}/trimLoggedMsgs | Trim (delete) messages from the Replay Log.
*MsgVpnApi* | [**doMsgVpnRestDeliveryPointRestConsumerClearStats**](docs/MsgVpnApi.md#doMsgVpnRestDeliveryPointRestConsumerClearStats) | **PUT** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers/{restConsumerName}/clearStats | Clear the statistics for the REST Consumer.
*MsgVpnApi* | [**doMsgVpnTopicEndpointCancelReplay**](docs/MsgVpnApi.md#doMsgVpnTopicEndpointCancelReplay) | **PUT** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/cancelReplay | Cancel the replay of messages to the Topic Endpoint.
*MsgVpnApi* | [**doMsgVpnTopicEndpointClearStats**](docs/MsgVpnApi.md#doMsgVpnTopicEndpointClearStats) | **PUT** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/clearStats | Clear the statistics for the Topic Endpoint.
*MsgVpnApi* | [**doMsgVpnTopicEndpointMsgDelete**](docs/MsgVpnApi.md#doMsgVpnTopicEndpointMsgDelete) | **PUT** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/msgs/{msgId}/delete | Delete the Message from the Topic Endpoint.
*MsgVpnApi* | [**doMsgVpnTopicEndpointStartReplay**](docs/MsgVpnApi.md#doMsgVpnTopicEndpointStartReplay) | **PUT** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/startReplay | Start the replay of messages to the Topic Endpoint.
*MsgVpnApi* | [**doMsgVpnTransactionCommit**](docs/MsgVpnApi.md#doMsgVpnTransactionCommit) | **PUT** /msgVpns/{msgVpnName}/transactions/{xid}/commit | Commit the Transaction.
*MsgVpnApi* | [**doMsgVpnTransactionDelete**](docs/MsgVpnApi.md#doMsgVpnTransactionDelete) | **PUT** /msgVpns/{msgVpnName}/transactions/{xid}/delete | Delete the Transaction.
*MsgVpnApi* | [**doMsgVpnTransactionRollback**](docs/MsgVpnApi.md#doMsgVpnTransactionRollback) | **PUT** /msgVpns/{msgVpnName}/transactions/{xid}/rollback | Rollback the Transaction.
*MsgVpnApi* | [**getMsgVpn**](docs/MsgVpnApi.md#getMsgVpn) | **GET** /msgVpns/{msgVpnName} | Get a Message VPN object.
*MsgVpnApi* | [**getMsgVpnAuthenticationOauthProvider**](docs/MsgVpnApi.md#getMsgVpnAuthenticationOauthProvider) | **GET** /msgVpns/{msgVpnName}/authenticationOauthProviders/{oauthProviderName} | Get an OAuth Provider object.
*MsgVpnApi* | [**getMsgVpnAuthenticationOauthProviders**](docs/MsgVpnApi.md#getMsgVpnAuthenticationOauthProviders) | **GET** /msgVpns/{msgVpnName}/authenticationOauthProviders | Get a list of OAuth Provider objects.
*MsgVpnApi* | [**getMsgVpnBridge**](docs/MsgVpnApi.md#getMsgVpnBridge) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter} | Get a Bridge object.
*MsgVpnApi* | [**getMsgVpnBridges**](docs/MsgVpnApi.md#getMsgVpnBridges) | **GET** /msgVpns/{msgVpnName}/bridges | Get a list of Bridge objects.
*MsgVpnApi* | [**getMsgVpnClient**](docs/MsgVpnApi.md#getMsgVpnClient) | **GET** /msgVpns/{msgVpnName}/clients/{clientName} | Get a Client object.
*MsgVpnApi* | [**getMsgVpnClientTransactedSession**](docs/MsgVpnApi.md#getMsgVpnClientTransactedSession) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/transactedSessions/{sessionName} | Get a Client Transacted Session object.
*MsgVpnApi* | [**getMsgVpnClientTransactedSessions**](docs/MsgVpnApi.md#getMsgVpnClientTransactedSessions) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/transactedSessions | Get a list of Client Transacted Session objects.
*MsgVpnApi* | [**getMsgVpnClients**](docs/MsgVpnApi.md#getMsgVpnClients) | **GET** /msgVpns/{msgVpnName}/clients | Get a list of Client objects.
*MsgVpnApi* | [**getMsgVpnDistributedCache**](docs/MsgVpnApi.md#getMsgVpnDistributedCache) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName} | Get a Distributed Cache object.
*MsgVpnApi* | [**getMsgVpnDistributedCacheCluster**](docs/MsgVpnApi.md#getMsgVpnDistributedCacheCluster) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName} | Get a Cache Cluster object.
*MsgVpnApi* | [**getMsgVpnDistributedCacheClusterInstance**](docs/MsgVpnApi.md#getMsgVpnDistributedCacheClusterInstance) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName} | Get a Cache Instance object.
*MsgVpnApi* | [**getMsgVpnDistributedCacheClusterInstances**](docs/MsgVpnApi.md#getMsgVpnDistributedCacheClusterInstances) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances | Get a list of Cache Instance objects.
*MsgVpnApi* | [**getMsgVpnDistributedCacheClusters**](docs/MsgVpnApi.md#getMsgVpnDistributedCacheClusters) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters | Get a list of Cache Cluster objects.
*MsgVpnApi* | [**getMsgVpnDistributedCaches**](docs/MsgVpnApi.md#getMsgVpnDistributedCaches) | **GET** /msgVpns/{msgVpnName}/distributedCaches | Get a list of Distributed Cache objects.
*MsgVpnApi* | [**getMsgVpnMqttSession**](docs/MsgVpnApi.md#getMsgVpnMqttSession) | **GET** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter} | Get an MQTT Session object.
*MsgVpnApi* | [**getMsgVpnMqttSessions**](docs/MsgVpnApi.md#getMsgVpnMqttSessions) | **GET** /msgVpns/{msgVpnName}/mqttSessions | Get a list of MQTT Session objects.
*MsgVpnApi* | [**getMsgVpnQueue**](docs/MsgVpnApi.md#getMsgVpnQueue) | **GET** /msgVpns/{msgVpnName}/queues/{queueName} | Get a Queue object.
*MsgVpnApi* | [**getMsgVpnQueueMsg**](docs/MsgVpnApi.md#getMsgVpnQueueMsg) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/msgs/{msgId} | Get a Queue Message object.
*MsgVpnApi* | [**getMsgVpnQueueMsgs**](docs/MsgVpnApi.md#getMsgVpnQueueMsgs) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/msgs | Get a list of Queue Message objects.
*MsgVpnApi* | [**getMsgVpnQueues**](docs/MsgVpnApi.md#getMsgVpnQueues) | **GET** /msgVpns/{msgVpnName}/queues | Get a list of Queue objects.
*MsgVpnApi* | [**getMsgVpnReplayLog**](docs/MsgVpnApi.md#getMsgVpnReplayLog) | **GET** /msgVpns/{msgVpnName}/replayLogs/{replayLogName} | Get a Replay Log object.
*MsgVpnApi* | [**getMsgVpnReplayLogs**](docs/MsgVpnApi.md#getMsgVpnReplayLogs) | **GET** /msgVpns/{msgVpnName}/replayLogs | Get a list of Replay Log objects.
*MsgVpnApi* | [**getMsgVpnRestDeliveryPoint**](docs/MsgVpnApi.md#getMsgVpnRestDeliveryPoint) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName} | Get a REST Delivery Point object.
*MsgVpnApi* | [**getMsgVpnRestDeliveryPointRestConsumer**](docs/MsgVpnApi.md#getMsgVpnRestDeliveryPointRestConsumer) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers/{restConsumerName} | Get a REST Consumer object.
*MsgVpnApi* | [**getMsgVpnRestDeliveryPointRestConsumers**](docs/MsgVpnApi.md#getMsgVpnRestDeliveryPointRestConsumers) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers | Get a list of REST Consumer objects.
*MsgVpnApi* | [**getMsgVpnRestDeliveryPoints**](docs/MsgVpnApi.md#getMsgVpnRestDeliveryPoints) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints | Get a list of REST Delivery Point objects.
*MsgVpnApi* | [**getMsgVpnTopicEndpoint**](docs/MsgVpnApi.md#getMsgVpnTopicEndpoint) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName} | Get a Topic Endpoint object.
*MsgVpnApi* | [**getMsgVpnTopicEndpointMsg**](docs/MsgVpnApi.md#getMsgVpnTopicEndpointMsg) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/msgs/{msgId} | Get a Topic Endpoint Message object.
*MsgVpnApi* | [**getMsgVpnTopicEndpointMsgs**](docs/MsgVpnApi.md#getMsgVpnTopicEndpointMsgs) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/msgs | Get a list of Topic Endpoint Message objects.
*MsgVpnApi* | [**getMsgVpnTopicEndpoints**](docs/MsgVpnApi.md#getMsgVpnTopicEndpoints) | **GET** /msgVpns/{msgVpnName}/topicEndpoints | Get a list of Topic Endpoint objects.
*MsgVpnApi* | [**getMsgVpnTransaction**](docs/MsgVpnApi.md#getMsgVpnTransaction) | **GET** /msgVpns/{msgVpnName}/transactions/{xid} | Get a Replicated Local Transaction or XA Transaction object.
*MsgVpnApi* | [**getMsgVpnTransactions**](docs/MsgVpnApi.md#getMsgVpnTransactions) | **GET** /msgVpns/{msgVpnName}/transactions | Get a list of Replicated Local Transaction or XA Transaction objects.
*MsgVpnApi* | [**getMsgVpns**](docs/MsgVpnApi.md#getMsgVpns) | **GET** /msgVpns | Get a list of Message VPN objects.
*QueueApi* | [**doMsgVpnQueueCancelReplay**](docs/QueueApi.md#doMsgVpnQueueCancelReplay) | **PUT** /msgVpns/{msgVpnName}/queues/{queueName}/cancelReplay | Cancel the replay of messages to the Queue.
*QueueApi* | [**doMsgVpnQueueClearStats**](docs/QueueApi.md#doMsgVpnQueueClearStats) | **PUT** /msgVpns/{msgVpnName}/queues/{queueName}/clearStats | Clear the statistics for the Queue.
*QueueApi* | [**doMsgVpnQueueMsgDelete**](docs/QueueApi.md#doMsgVpnQueueMsgDelete) | **PUT** /msgVpns/{msgVpnName}/queues/{queueName}/msgs/{msgId}/delete | Delete the Message from the Queue.
*QueueApi* | [**doMsgVpnQueueStartReplay**](docs/QueueApi.md#doMsgVpnQueueStartReplay) | **PUT** /msgVpns/{msgVpnName}/queues/{queueName}/startReplay | Start the replay of messages to the Queue.
*QueueApi* | [**getMsgVpnQueue**](docs/QueueApi.md#getMsgVpnQueue) | **GET** /msgVpns/{msgVpnName}/queues/{queueName} | Get a Queue object.
*QueueApi* | [**getMsgVpnQueueMsg**](docs/QueueApi.md#getMsgVpnQueueMsg) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/msgs/{msgId} | Get a Queue Message object.
*QueueApi* | [**getMsgVpnQueueMsgs**](docs/QueueApi.md#getMsgVpnQueueMsgs) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/msgs | Get a list of Queue Message objects.
*QueueApi* | [**getMsgVpnQueues**](docs/QueueApi.md#getMsgVpnQueues) | **GET** /msgVpns/{msgVpnName}/queues | Get a list of Queue objects.
*ReplayLogApi* | [**doMsgVpnReplayLogTrimLoggedMsgs**](docs/ReplayLogApi.md#doMsgVpnReplayLogTrimLoggedMsgs) | **PUT** /msgVpns/{msgVpnName}/replayLogs/{replayLogName}/trimLoggedMsgs | Trim (delete) messages from the Replay Log.
*ReplayLogApi* | [**getMsgVpnReplayLog**](docs/ReplayLogApi.md#getMsgVpnReplayLog) | **GET** /msgVpns/{msgVpnName}/replayLogs/{replayLogName} | Get a Replay Log object.
*ReplayLogApi* | [**getMsgVpnReplayLogs**](docs/ReplayLogApi.md#getMsgVpnReplayLogs) | **GET** /msgVpns/{msgVpnName}/replayLogs | Get a list of Replay Log objects.
*RestDeliveryPointApi* | [**doMsgVpnRestDeliveryPointRestConsumerClearStats**](docs/RestDeliveryPointApi.md#doMsgVpnRestDeliveryPointRestConsumerClearStats) | **PUT** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers/{restConsumerName}/clearStats | Clear the statistics for the REST Consumer.
*RestDeliveryPointApi* | [**getMsgVpnRestDeliveryPoint**](docs/RestDeliveryPointApi.md#getMsgVpnRestDeliveryPoint) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName} | Get a REST Delivery Point object.
*RestDeliveryPointApi* | [**getMsgVpnRestDeliveryPointRestConsumer**](docs/RestDeliveryPointApi.md#getMsgVpnRestDeliveryPointRestConsumer) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers/{restConsumerName} | Get a REST Consumer object.
*RestDeliveryPointApi* | [**getMsgVpnRestDeliveryPointRestConsumers**](docs/RestDeliveryPointApi.md#getMsgVpnRestDeliveryPointRestConsumers) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers | Get a list of REST Consumer objects.
*RestDeliveryPointApi* | [**getMsgVpnRestDeliveryPoints**](docs/RestDeliveryPointApi.md#getMsgVpnRestDeliveryPoints) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints | Get a list of REST Delivery Point objects.
*SessionApi* | [**doSessionDelete**](docs/SessionApi.md#doSessionDelete) | **PUT** /sessions/{sessionUsername},{sessionId}/delete | Delete the session.
*SessionApi* | [**getSession**](docs/SessionApi.md#getSession) | **GET** /sessions/{sessionUsername},{sessionId} | Get a Session object.
*SessionApi* | [**getSessions**](docs/SessionApi.md#getSessions) | **GET** /sessions | Get a list of Session objects.
*TopicEndpointApi* | [**doMsgVpnTopicEndpointCancelReplay**](docs/TopicEndpointApi.md#doMsgVpnTopicEndpointCancelReplay) | **PUT** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/cancelReplay | Cancel the replay of messages to the Topic Endpoint.
*TopicEndpointApi* | [**doMsgVpnTopicEndpointClearStats**](docs/TopicEndpointApi.md#doMsgVpnTopicEndpointClearStats) | **PUT** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/clearStats | Clear the statistics for the Topic Endpoint.
*TopicEndpointApi* | [**doMsgVpnTopicEndpointMsgDelete**](docs/TopicEndpointApi.md#doMsgVpnTopicEndpointMsgDelete) | **PUT** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/msgs/{msgId}/delete | Delete the Message from the Topic Endpoint.
*TopicEndpointApi* | [**doMsgVpnTopicEndpointStartReplay**](docs/TopicEndpointApi.md#doMsgVpnTopicEndpointStartReplay) | **PUT** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/startReplay | Start the replay of messages to the Topic Endpoint.
*TopicEndpointApi* | [**getMsgVpnTopicEndpoint**](docs/TopicEndpointApi.md#getMsgVpnTopicEndpoint) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName} | Get a Topic Endpoint object.
*TopicEndpointApi* | [**getMsgVpnTopicEndpointMsg**](docs/TopicEndpointApi.md#getMsgVpnTopicEndpointMsg) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/msgs/{msgId} | Get a Topic Endpoint Message object.
*TopicEndpointApi* | [**getMsgVpnTopicEndpointMsgs**](docs/TopicEndpointApi.md#getMsgVpnTopicEndpointMsgs) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/msgs | Get a list of Topic Endpoint Message objects.
*TopicEndpointApi* | [**getMsgVpnTopicEndpoints**](docs/TopicEndpointApi.md#getMsgVpnTopicEndpoints) | **GET** /msgVpns/{msgVpnName}/topicEndpoints | Get a list of Topic Endpoint objects.
*TransactionApi* | [**doMsgVpnTransactionCommit**](docs/TransactionApi.md#doMsgVpnTransactionCommit) | **PUT** /msgVpns/{msgVpnName}/transactions/{xid}/commit | Commit the Transaction.
*TransactionApi* | [**doMsgVpnTransactionDelete**](docs/TransactionApi.md#doMsgVpnTransactionDelete) | **PUT** /msgVpns/{msgVpnName}/transactions/{xid}/delete | Delete the Transaction.
*TransactionApi* | [**doMsgVpnTransactionRollback**](docs/TransactionApi.md#doMsgVpnTransactionRollback) | **PUT** /msgVpns/{msgVpnName}/transactions/{xid}/rollback | Rollback the Transaction.
*TransactionApi* | [**getMsgVpnTransaction**](docs/TransactionApi.md#getMsgVpnTransaction) | **GET** /msgVpns/{msgVpnName}/transactions/{xid} | Get a Replicated Local Transaction or XA Transaction object.
*TransactionApi* | [**getMsgVpnTransactions**](docs/TransactionApi.md#getMsgVpnTransactions) | **GET** /msgVpns/{msgVpnName}/transactions | Get a list of Replicated Local Transaction or XA Transaction objects.


## Documentation for Models

 - [About](docs/About.md)
 - [AboutApi](docs/AboutApi.md)
 - [AboutApiLinks](docs/AboutApiLinks.md)
 - [AboutApiResponse](docs/AboutApiResponse.md)
 - [AboutLinks](docs/AboutLinks.md)
 - [AboutResponse](docs/AboutResponse.md)
 - [AboutUser](docs/AboutUser.md)
 - [AboutUserLinks](docs/AboutUserLinks.md)
 - [AboutUserLogout](docs/AboutUserLogout.md)
 - [AboutUserMsgVpn](docs/AboutUserMsgVpn.md)
 - [AboutUserMsgVpnLinks](docs/AboutUserMsgVpnLinks.md)
 - [AboutUserMsgVpnResponse](docs/AboutUserMsgVpnResponse.md)
 - [AboutUserMsgVpnsResponse](docs/AboutUserMsgVpnsResponse.md)
 - [AboutUserResponse](docs/AboutUserResponse.md)
 - [Broker](docs/Broker.md)
 - [BrokerLinks](docs/BrokerLinks.md)
 - [BrokerResponse](docs/BrokerResponse.md)
 - [CertAuthoritiesResponse](docs/CertAuthoritiesResponse.md)
 - [CertAuthority](docs/CertAuthority.md)
 - [CertAuthorityLinks](docs/CertAuthorityLinks.md)
 - [CertAuthorityRefreshCrl](docs/CertAuthorityRefreshCrl.md)
 - [CertAuthorityResponse](docs/CertAuthorityResponse.md)
 - [ClientCertAuthoritiesResponse](docs/ClientCertAuthoritiesResponse.md)
 - [ClientCertAuthority](docs/ClientCertAuthority.md)
 - [ClientCertAuthorityLinks](docs/ClientCertAuthorityLinks.md)
 - [ClientCertAuthorityRefreshCrl](docs/ClientCertAuthorityRefreshCrl.md)
 - [ClientCertAuthorityResponse](docs/ClientCertAuthorityResponse.md)
 - [ConfigSyncAssertLeaderMsgVpn](docs/ConfigSyncAssertLeaderMsgVpn.md)
 - [ConfigSyncAssertLeaderRouter](docs/ConfigSyncAssertLeaderRouter.md)
 - [ConfigSyncResyncFollowerMsgVpn](docs/ConfigSyncResyncFollowerMsgVpn.md)
 - [ConfigSyncResyncLeaderMsgVpn](docs/ConfigSyncResyncLeaderMsgVpn.md)
 - [ConfigSyncResyncLeaderRouter](docs/ConfigSyncResyncLeaderRouter.md)
 - [GuaranteedMsgingDefragmentMsgSpoolFilesStart](docs/GuaranteedMsgingDefragmentMsgSpoolFilesStart.md)
 - [GuaranteedMsgingDefragmentMsgSpoolFilesStop](docs/GuaranteedMsgingDefragmentMsgSpoolFilesStop.md)
 - [MsgVpn](docs/MsgVpn.md)
 - [MsgVpnAuthenticationOauthProvider](docs/MsgVpnAuthenticationOauthProvider.md)
 - [MsgVpnAuthenticationOauthProviderClearStats](docs/MsgVpnAuthenticationOauthProviderClearStats.md)
 - [MsgVpnAuthenticationOauthProviderLinks](docs/MsgVpnAuthenticationOauthProviderLinks.md)
 - [MsgVpnAuthenticationOauthProviderResponse](docs/MsgVpnAuthenticationOauthProviderResponse.md)
 - [MsgVpnAuthenticationOauthProvidersResponse](docs/MsgVpnAuthenticationOauthProvidersResponse.md)
 - [MsgVpnBridge](docs/MsgVpnBridge.md)
 - [MsgVpnBridgeClearEvent](docs/MsgVpnBridgeClearEvent.md)
 - [MsgVpnBridgeClearStats](docs/MsgVpnBridgeClearStats.md)
 - [MsgVpnBridgeDisconnect](docs/MsgVpnBridgeDisconnect.md)
 - [MsgVpnBridgeLinks](docs/MsgVpnBridgeLinks.md)
 - [MsgVpnBridgeResponse](docs/MsgVpnBridgeResponse.md)
 - [MsgVpnBridgesResponse](docs/MsgVpnBridgesResponse.md)
 - [MsgVpnClearMsgSpoolStats](docs/MsgVpnClearMsgSpoolStats.md)
 - [MsgVpnClearReplicationStats](docs/MsgVpnClearReplicationStats.md)
 - [MsgVpnClearServiceStats](docs/MsgVpnClearServiceStats.md)
 - [MsgVpnClearStats](docs/MsgVpnClearStats.md)
 - [MsgVpnClient](docs/MsgVpnClient.md)
 - [MsgVpnClientClearEvent](docs/MsgVpnClientClearEvent.md)
 - [MsgVpnClientClearStats](docs/MsgVpnClientClearStats.md)
 - [MsgVpnClientDisconnect](docs/MsgVpnClientDisconnect.md)
 - [MsgVpnClientLinks](docs/MsgVpnClientLinks.md)
 - [MsgVpnClientResponse](docs/MsgVpnClientResponse.md)
 - [MsgVpnClientTransactedSession](docs/MsgVpnClientTransactedSession.md)
 - [MsgVpnClientTransactedSessionDelete](docs/MsgVpnClientTransactedSessionDelete.md)
 - [MsgVpnClientTransactedSessionLinks](docs/MsgVpnClientTransactedSessionLinks.md)
 - [MsgVpnClientTransactedSessionResponse](docs/MsgVpnClientTransactedSessionResponse.md)
 - [MsgVpnClientTransactedSessionsResponse](docs/MsgVpnClientTransactedSessionsResponse.md)
 - [MsgVpnClientsResponse](docs/MsgVpnClientsResponse.md)
 - [MsgVpnDistributedCache](docs/MsgVpnDistributedCache.md)
 - [MsgVpnDistributedCacheCluster](docs/MsgVpnDistributedCacheCluster.md)
 - [MsgVpnDistributedCacheClusterInstance](docs/MsgVpnDistributedCacheClusterInstance.md)
 - [MsgVpnDistributedCacheClusterInstanceBackupCachedMsgs](docs/MsgVpnDistributedCacheClusterInstanceBackupCachedMsgs.md)
 - [MsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs](docs/MsgVpnDistributedCacheClusterInstanceCancelBackupCachedMsgs.md)
 - [MsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs](docs/MsgVpnDistributedCacheClusterInstanceCancelRestoreCachedMsgs.md)
 - [MsgVpnDistributedCacheClusterInstanceClearEvent](docs/MsgVpnDistributedCacheClusterInstanceClearEvent.md)
 - [MsgVpnDistributedCacheClusterInstanceClearStats](docs/MsgVpnDistributedCacheClusterInstanceClearStats.md)
 - [MsgVpnDistributedCacheClusterInstanceDeleteMsgs](docs/MsgVpnDistributedCacheClusterInstanceDeleteMsgs.md)
 - [MsgVpnDistributedCacheClusterInstanceLinks](docs/MsgVpnDistributedCacheClusterInstanceLinks.md)
 - [MsgVpnDistributedCacheClusterInstanceResponse](docs/MsgVpnDistributedCacheClusterInstanceResponse.md)
 - [MsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs](docs/MsgVpnDistributedCacheClusterInstanceRestoreCachedMsgs.md)
 - [MsgVpnDistributedCacheClusterInstanceStart](docs/MsgVpnDistributedCacheClusterInstanceStart.md)
 - [MsgVpnDistributedCacheClusterInstancesResponse](docs/MsgVpnDistributedCacheClusterInstancesResponse.md)
 - [MsgVpnDistributedCacheClusterLinks](docs/MsgVpnDistributedCacheClusterLinks.md)
 - [MsgVpnDistributedCacheClusterResponse](docs/MsgVpnDistributedCacheClusterResponse.md)
 - [MsgVpnDistributedCacheClustersResponse](docs/MsgVpnDistributedCacheClustersResponse.md)
 - [MsgVpnDistributedCacheLinks](docs/MsgVpnDistributedCacheLinks.md)
 - [MsgVpnDistributedCacheResponse](docs/MsgVpnDistributedCacheResponse.md)
 - [MsgVpnDistributedCachesResponse](docs/MsgVpnDistributedCachesResponse.md)
 - [MsgVpnLinks](docs/MsgVpnLinks.md)
 - [MsgVpnMqttSession](docs/MsgVpnMqttSession.md)
 - [MsgVpnMqttSessionClearStats](docs/MsgVpnMqttSessionClearStats.md)
 - [MsgVpnMqttSessionLinks](docs/MsgVpnMqttSessionLinks.md)
 - [MsgVpnMqttSessionResponse](docs/MsgVpnMqttSessionResponse.md)
 - [MsgVpnMqttSessionsResponse](docs/MsgVpnMqttSessionsResponse.md)
 - [MsgVpnQueue](docs/MsgVpnQueue.md)
 - [MsgVpnQueueCancelReplay](docs/MsgVpnQueueCancelReplay.md)
 - [MsgVpnQueueClearStats](docs/MsgVpnQueueClearStats.md)
 - [MsgVpnQueueLinks](docs/MsgVpnQueueLinks.md)
 - [MsgVpnQueueMsg](docs/MsgVpnQueueMsg.md)
 - [MsgVpnQueueMsgDelete](docs/MsgVpnQueueMsgDelete.md)
 - [MsgVpnQueueMsgLinks](docs/MsgVpnQueueMsgLinks.md)
 - [MsgVpnQueueMsgResponse](docs/MsgVpnQueueMsgResponse.md)
 - [MsgVpnQueueMsgsResponse](docs/MsgVpnQueueMsgsResponse.md)
 - [MsgVpnQueueResponse](docs/MsgVpnQueueResponse.md)
 - [MsgVpnQueueStartReplay](docs/MsgVpnQueueStartReplay.md)
 - [MsgVpnQueuesResponse](docs/MsgVpnQueuesResponse.md)
 - [MsgVpnReplayLog](docs/MsgVpnReplayLog.md)
 - [MsgVpnReplayLogLinks](docs/MsgVpnReplayLogLinks.md)
 - [MsgVpnReplayLogResponse](docs/MsgVpnReplayLogResponse.md)
 - [MsgVpnReplayLogTrimLoggedMsgs](docs/MsgVpnReplayLogTrimLoggedMsgs.md)
 - [MsgVpnReplayLogsResponse](docs/MsgVpnReplayLogsResponse.md)
 - [MsgVpnResponse](docs/MsgVpnResponse.md)
 - [MsgVpnRestDeliveryPoint](docs/MsgVpnRestDeliveryPoint.md)
 - [MsgVpnRestDeliveryPointLinks](docs/MsgVpnRestDeliveryPointLinks.md)
 - [MsgVpnRestDeliveryPointResponse](docs/MsgVpnRestDeliveryPointResponse.md)
 - [MsgVpnRestDeliveryPointRestConsumer](docs/MsgVpnRestDeliveryPointRestConsumer.md)
 - [MsgVpnRestDeliveryPointRestConsumerClearStats](docs/MsgVpnRestDeliveryPointRestConsumerClearStats.md)
 - [MsgVpnRestDeliveryPointRestConsumerLinks](docs/MsgVpnRestDeliveryPointRestConsumerLinks.md)
 - [MsgVpnRestDeliveryPointRestConsumerResponse](docs/MsgVpnRestDeliveryPointRestConsumerResponse.md)
 - [MsgVpnRestDeliveryPointRestConsumersResponse](docs/MsgVpnRestDeliveryPointRestConsumersResponse.md)
 - [MsgVpnRestDeliveryPointsResponse](docs/MsgVpnRestDeliveryPointsResponse.md)
 - [MsgVpnTopicEndpoint](docs/MsgVpnTopicEndpoint.md)
 - [MsgVpnTopicEndpointCancelReplay](docs/MsgVpnTopicEndpointCancelReplay.md)
 - [MsgVpnTopicEndpointClearStats](docs/MsgVpnTopicEndpointClearStats.md)
 - [MsgVpnTopicEndpointLinks](docs/MsgVpnTopicEndpointLinks.md)
 - [MsgVpnTopicEndpointMsg](docs/MsgVpnTopicEndpointMsg.md)
 - [MsgVpnTopicEndpointMsgDelete](docs/MsgVpnTopicEndpointMsgDelete.md)
 - [MsgVpnTopicEndpointMsgLinks](docs/MsgVpnTopicEndpointMsgLinks.md)
 - [MsgVpnTopicEndpointMsgResponse](docs/MsgVpnTopicEndpointMsgResponse.md)
 - [MsgVpnTopicEndpointMsgsResponse](docs/MsgVpnTopicEndpointMsgsResponse.md)
 - [MsgVpnTopicEndpointResponse](docs/MsgVpnTopicEndpointResponse.md)
 - [MsgVpnTopicEndpointStartReplay](docs/MsgVpnTopicEndpointStartReplay.md)
 - [MsgVpnTopicEndpointsResponse](docs/MsgVpnTopicEndpointsResponse.md)
 - [MsgVpnTransaction](docs/MsgVpnTransaction.md)
 - [MsgVpnTransactionCommit](docs/MsgVpnTransactionCommit.md)
 - [MsgVpnTransactionDelete](docs/MsgVpnTransactionDelete.md)
 - [MsgVpnTransactionLinks](docs/MsgVpnTransactionLinks.md)
 - [MsgVpnTransactionResponse](docs/MsgVpnTransactionResponse.md)
 - [MsgVpnTransactionRollback](docs/MsgVpnTransactionRollback.md)
 - [MsgVpnTransactionsResponse](docs/MsgVpnTransactionsResponse.md)
 - [MsgVpnsResponse](docs/MsgVpnsResponse.md)
 - [SempError](docs/SempError.md)
 - [SempMeta](docs/SempMeta.md)
 - [SempMetaOnlyResponse](docs/SempMetaOnlyResponse.md)
 - [SempPaging](docs/SempPaging.md)
 - [SempRequest](docs/SempRequest.md)
 - [Session](docs/Session.md)
 - [SessionDelete](docs/SessionDelete.md)
 - [SessionLinks](docs/SessionLinks.md)
 - [SessionResponse](docs/SessionResponse.md)
 - [SessionsResponse](docs/SessionsResponse.md)


## Documentation for Authorization

Authentication schemes defined for the API:
### basicAuth

- **Type**: HTTP basic authentication


## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author

support@solace.com

