# swagger-java-client

SEMP (Solace Element Management Protocol)
- API version: 2.23
  - Build date: 2024-01-19T20:58:30.360Z

SEMP (starting in `v2`, see note 1) is a RESTful API for configuring, monitoring, and administering a Solace PubSub+ broker.  SEMP uses URIs to address manageable **resources** of the Solace PubSub+ broker. Resources are individual **objects**, **collections** of objects, or (exclusively in the action API) **actions**. This document applies to the following API:   API|Base Path|Purpose|Comments :---|:---|:---|:--- Monitoring|/SEMP/v2/monitor|Querying operational parameters|See note 2    The following APIs are also available:   API|Base Path|Purpose|Comments :---|:---|:---|:--- Action|/SEMP/v2/action|Performing actions|See note 2 Configuration|/SEMP/v2/config|Reading and writing config state|See note 2    Resources are always nouns, with individual objects being singular and collections being plural.  Objects within a collection are identified by an `obj-id`, which follows the collection name with the form `collection-name/obj-id`.  Actions within an object are identified by an `action-id`, which follows the object name with the form `obj-id/action-id`.  Some examples:  ``` /SEMP/v2/config/msgVpns                        ; MsgVpn collection /SEMP/v2/config/msgVpns/a                      ; MsgVpn object named \"a\" /SEMP/v2/config/msgVpns/a/queues               ; Queue collection in MsgVpn \"a\" /SEMP/v2/config/msgVpns/a/queues/b             ; Queue object named \"b\" in MsgVpn \"a\" /SEMP/v2/action/msgVpns/a/queues/b/startReplay ; Action that starts a replay on Queue \"b\" in MsgVpn \"a\" /SEMP/v2/monitor/msgVpns/a/clients             ; Client collection in MsgVpn \"a\" /SEMP/v2/monitor/msgVpns/a/clients/c           ; Client object named \"c\" in MsgVpn \"a\" ```  ## Collection Resources  Collections are unordered lists of objects (unless described as otherwise), and are described by JSON arrays. Each item in the array represents an object in the same manner as the individual object would normally be represented. In the configuration API, the creation of a new object is done through its collection resource.  ## Object and Action Resources  Objects are composed of attributes, actions, collections, and other objects. They are described by JSON objects as name/value pairs. The collections and actions of an object are not contained directly in the object's JSON content; rather the content includes an attribute containing a URI which points to the collections and actions. These contained resources must be managed through this URI. At a minimum, every object has one or more identifying attributes, and its own `uri` attribute which contains the URI pointing to itself.  Actions are also composed of attributes, and are described by JSON objects as name/value pairs. Unlike objects, however, they are not members of a collection and cannot be retrieved, only performed. Actions only exist in the action API.  Attributes in an object or action may have any combination of the following properties:   Property|Meaning|Comments :---|:---|:--- Identifying|Attribute is involved in unique identification of the object, and appears in its URI| Required|Attribute must be provided in the request| Read-Only|Attribute can only be read, not written.|See note 3 Write-Only|Attribute can only be written, not read, unless the attribute is also opaque|See the documentation for the opaque property Requires-Disable|Attribute can only be changed when object is disabled| Deprecated|Attribute is deprecated, and will disappear in the next SEMP version| Opaque|Attribute can be set or retrieved in opaque form when the `opaquePassword` query parameter is present|See the `opaquePassword` query parameter documentation    In some requests, certain attributes may only be provided in certain combinations with other attributes:   Relationship|Meaning :---|:--- Requires|Attribute may only be changed by a request if a particular attribute or combination of attributes is also provided in the request Conflicts|Attribute may only be provided in a request if a particular attribute or combination of attributes is not also provided in the request    In the monitoring API, any non-identifying attribute may not be returned in a GET.  ## HTTP Methods  The following HTTP methods manipulate resources in accordance with these general principles. Note that some methods are only used in certain APIs:   Method|Resource|Meaning|Request Body|Response Body|Missing Request Attributes :---|:---|:---|:---|:---|:--- POST|Collection|Create object|Initial attribute values|Object attributes and metadata|Set to default PUT|Object|Create or replace object (see note 5)|New attribute values|Object attributes and metadata|Set to default, with certain exceptions (see note 4) PUT|Action|Performs action|Action arguments|Action metadata|N/A PATCH|Object|Update object|New attribute values|Object attributes and metadata|unchanged DELETE|Object|Delete object|Empty|Object metadata|N/A GET|Object|Get object|Empty|Object attributes and metadata|N/A GET|Collection|Get collection|Empty|Object attributes and collection metadata|N/A    ## Common Query Parameters  The following are some common query parameters that are supported by many method/URI combinations. Individual URIs may document additional parameters. Note that multiple query parameters can be used together in a single URI, separated by the ampersand character. For example:  ``` ; Request for the MsgVpns collection using two hypothetical query parameters ; \"q1\" and \"q2\" with values \"val1\" and \"val2\" respectively /SEMP/v2/monitor/msgVpns?q1=val1&q2=val2 ```  ### select  Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. Use this query parameter to limit the size of the returned data for each returned object, return only those fields that are desired, or exclude fields that are not desired.  The value of `select` is a comma-separated list of attribute names. If the list contains attribute names that are not prefaced by `-`, only those attributes are included in the response. If the list contains attribute names that are prefaced by `-`, those attributes are excluded from the response. If the list contains both types, then the difference of the first set of attributes and the second set of attributes is returned. If the list is empty (i.e. `select=`), no attributes are returned.  All attributes that are prefaced by `-` must follow all attributes that are not prefaced by `-`. In addition, each attribute name in the list must match at least one attribute in the object.  Names may include the `*` wildcard (zero or more characters). Nested attribute names are supported using periods (e.g. `parentName.childName`).  Some examples:  ``` ; List of all MsgVpn names /SEMP/v2/monitor/msgVpns?select=msgVpnName ; List of all MsgVpn and their attributes except for their names /SEMP/v2/monitor/msgVpns?select=-msgVpnName ; Authentication attributes of MsgVpn \"finance\" /SEMP/v2/monitor/msgVpns/finance?select=authentication* ; All attributes of MsgVpn \"finance\" except for authentication attributes /SEMP/v2/monitor/msgVpns/finance?select=-authentication* ; Access related attributes of Queue \"orderQ\" of MsgVpn \"finance\" /SEMP/v2/monitor/msgVpns/finance/queues/orderQ?select=owner,permission ```  ### where  Include in the response only objects where certain conditions are true. Use this query parameter to limit which objects are returned to those whose attribute values meet the given conditions.  The value of `where` is a comma-separated list of expressions. All expressions must be true for the object to be included in the response. Each expression takes the form:  ``` expression  = attribute-name OP value OP          = '==' | '!=' | '&lt;' | '&gt;' | '&lt;=' | '&gt;=' ```  `value` may be a number, string, `true`, or `false`, as appropriate for the type of `attribute-name`. Greater-than and less-than comparisons only work for numbers. A `*` in a string `value` is interpreted as a wildcard (zero or more characters). Some examples:  ``` ; Only enabled MsgVpns /SEMP/v2/monitor/msgVpns?where=enabled==true ; Only MsgVpns using basic non-LDAP authentication /SEMP/v2/monitor/msgVpns?where=authenticationBasicEnabled==true,authenticationBasicType!=ldap ; Only MsgVpns that allow more than 100 client connections /SEMP/v2/monitor/msgVpns?where=maxConnectionCount>100 ; Only MsgVpns with msgVpnName starting with \"B\": /SEMP/v2/monitor/msgVpns?where=msgVpnName==B* ```  ### count  Limit the count of objects in the response. This can be useful to limit the size of the response for large collections. The minimum value for `count` is `1` and the default is `10`. There is also a per-collection maximum value to limit request handling time.  `count` does not guarantee that a minimum number of objects will be returned. A page may contain fewer than `count` objects or even be empty. Additional objects may nonetheless be available for retrieval on subsequent pages. See the `cursor` query parameter documentation for more information on paging.  For example: ``` ; Up to 25 MsgVpns /SEMP/v2/monitor/msgVpns?count=25 ```  ### cursor  The cursor, or position, for the next page of objects. Cursors are opaque data that should not be created or interpreted by SEMP clients, and should only be used as described below.  When a request is made for a collection and there may be additional objects available for retrieval that are not included in the initial response, the response will include a `cursorQuery` field containing a cursor. The value of this field can be specified in the `cursor` query parameter of a subsequent request to retrieve the next page of objects. For convenience, an appropriate URI is constructed automatically by the broker and included in the `nextPageUri` field of the response. This URI can be used directly to retrieve the next page of objects.  Applications must continue to follow the `nextPageUri` if one is provided in order to retrieve the full set of objects associated with the request, even if a page contains fewer than the requested number of objects (see the `count` query parameter documentation) or is empty.  ### opaquePassword  Attributes with the opaque property are also write-only and so cannot normally be retrieved in a GET. However, when a password is provided in the `opaquePassword` query parameter, attributes with the opaque property are retrieved in a GET in opaque form, encrypted with this password. The query parameter can also be used on a POST, PATCH, or PUT to set opaque attributes using opaque attribute values retrieved in a GET, so long as:  1. the same password that was used to retrieve the opaque attribute values is provided; and  2. the broker to which the request is being sent has the same major and minor SEMP version as the broker that produced the opaque attribute values.  The password provided in the query parameter must be a minimum of 8 characters and a maximum of 128 characters.  The query parameter can only be used in the configuration API, and only over HTTPS.  ## Authentication  When a client makes its first SEMPv2 request, it must supply a username and password using HTTP Basic authentication.  If authentication is successful, the broker returns a cookie containing a session key. The client can omit the username and password from subsequent requests, because the broker now uses the session cookie for authentication instead. When the session expires or is deleted, the client must provide the username and password again, and the broker creates a new session.  There are a limited number of session slots available on the broker. The broker returns 529 No SEMP Session Available if it is not able to allocate a session. For this reason, all clients that use SEMPv2 should support cookies.  If certain attributes—such as a user's password—are changed, the broker automatically deletes the affected sessions. These attributes are documented below. However, changes in external user configuration data stored on a RADIUS or LDAP server do not trigger the broker to delete the associated session(s), therefore you must do this manually, if required.  A client can retrieve its current session information using the /about/user endpoint, delete its own session using the /about/user/logout endpoint, and manage all sessions using the /sessions endpoint.  ## Help  Visit [our website](https://solace.com) to learn more about Solace.  You can also download the SEMP API specifications by clicking [here](https://solace.com/downloads/).  If you need additional support, please contact us at [support@solace.com](mailto:support@solace.com).  ## Notes  Note|Description :---:|:--- 1|This specification defines SEMP starting in \"v2\", and not the original SEMP \"v1\" interface. Request and response formats between \"v1\" and \"v2\" are entirely incompatible, although both protocols share a common port configuration on the Solace PubSub+ broker. They are differentiated by the initial portion of the URI path, one of either \"/SEMP/\" or \"/SEMP/v2/\" 2|This API is partially implemented. Only a subset of all objects are available. 3|Read-only attributes may appear in POST and PUT/PATCH requests. However, if a read-only attribute is not marked as identifying, it will be ignored during a PUT/PATCH. 4|On a PUT, if the SEMP user is not authorized to modify the attribute, its value is left unchanged rather than set to default. In addition, the values of write-only attributes are not set to their defaults on a PUT, except in the following two cases: there is a mutual requires relationship with another non-write-only attribute, both attributes are absent from the request, and the non-write-only attribute is not currently set to its default value; or the attribute is also opaque and the `opaquePassword` query parameter is provided in the request. 5|On a PUT, if the object does not exist, it is created first.  

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
        List<String> select = Arrays.asList("select_example"); // List<String> | Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the `select` parameter.
        try {
            AboutResponse result = apiInstance.getAbout(select);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling AboutApi#getAbout");
            e.printStackTrace();
        }
    }
}

```

## Documentation for API Endpoints

All URIs are relative to *http://www.solace.com/SEMP/v2/monitor*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*AboutApi* | [**getAbout**](docs/AboutApi.md#getAbout) | **GET** /about | Get an About object.
*AboutApi* | [**getAboutApi**](docs/AboutApi.md#getAboutApi) | **GET** /about/api | Get an API Description object.
*AboutApi* | [**getAboutUser**](docs/AboutApi.md#getAboutUser) | **GET** /about/user | Get a User object.
*AboutApi* | [**getAboutUserMsgVpn**](docs/AboutApi.md#getAboutUserMsgVpn) | **GET** /about/user/msgVpns/{msgVpnName} | Get a User Message VPN object.
*AboutApi* | [**getAboutUserMsgVpns**](docs/AboutApi.md#getAboutUserMsgVpns) | **GET** /about/user/msgVpns | Get a list of User Message VPN objects.
*AclProfileApi* | [**getMsgVpnAclProfile**](docs/AclProfileApi.md#getMsgVpnAclProfile) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName} | Get an ACL Profile object.
*AclProfileApi* | [**getMsgVpnAclProfileClientConnectException**](docs/AclProfileApi.md#getMsgVpnAclProfileClientConnectException) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/clientConnectExceptions/{clientConnectExceptionAddress} | Get a Client Connect Exception object.
*AclProfileApi* | [**getMsgVpnAclProfileClientConnectExceptions**](docs/AclProfileApi.md#getMsgVpnAclProfileClientConnectExceptions) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/clientConnectExceptions | Get a list of Client Connect Exception objects.
*AclProfileApi* | [**getMsgVpnAclProfilePublishException**](docs/AclProfileApi.md#getMsgVpnAclProfilePublishException) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/publishExceptions/{topicSyntax},{publishExceptionTopic} | Get a Publish Topic Exception object.
*AclProfileApi* | [**getMsgVpnAclProfilePublishExceptions**](docs/AclProfileApi.md#getMsgVpnAclProfilePublishExceptions) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/publishExceptions | Get a list of Publish Topic Exception objects.
*AclProfileApi* | [**getMsgVpnAclProfilePublishTopicException**](docs/AclProfileApi.md#getMsgVpnAclProfilePublishTopicException) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/publishTopicExceptions/{publishTopicExceptionSyntax},{publishTopicException} | Get a Publish Topic Exception object.
*AclProfileApi* | [**getMsgVpnAclProfilePublishTopicExceptions**](docs/AclProfileApi.md#getMsgVpnAclProfilePublishTopicExceptions) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/publishTopicExceptions | Get a list of Publish Topic Exception objects.
*AclProfileApi* | [**getMsgVpnAclProfileSubscribeException**](docs/AclProfileApi.md#getMsgVpnAclProfileSubscribeException) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/subscribeExceptions/{topicSyntax},{subscribeExceptionTopic} | Get a Subscribe Topic Exception object.
*AclProfileApi* | [**getMsgVpnAclProfileSubscribeExceptions**](docs/AclProfileApi.md#getMsgVpnAclProfileSubscribeExceptions) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/subscribeExceptions | Get a list of Subscribe Topic Exception objects.
*AclProfileApi* | [**getMsgVpnAclProfileSubscribeShareNameException**](docs/AclProfileApi.md#getMsgVpnAclProfileSubscribeShareNameException) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/subscribeShareNameExceptions/{subscribeShareNameExceptionSyntax},{subscribeShareNameException} | Get a Subscribe Share Name Exception object.
*AclProfileApi* | [**getMsgVpnAclProfileSubscribeShareNameExceptions**](docs/AclProfileApi.md#getMsgVpnAclProfileSubscribeShareNameExceptions) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/subscribeShareNameExceptions | Get a list of Subscribe Share Name Exception objects.
*AclProfileApi* | [**getMsgVpnAclProfileSubscribeTopicException**](docs/AclProfileApi.md#getMsgVpnAclProfileSubscribeTopicException) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/subscribeTopicExceptions/{subscribeTopicExceptionSyntax},{subscribeTopicException} | Get a Subscribe Topic Exception object.
*AclProfileApi* | [**getMsgVpnAclProfileSubscribeTopicExceptions**](docs/AclProfileApi.md#getMsgVpnAclProfileSubscribeTopicExceptions) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/subscribeTopicExceptions | Get a list of Subscribe Topic Exception objects.
*AclProfileApi* | [**getMsgVpnAclProfiles**](docs/AclProfileApi.md#getMsgVpnAclProfiles) | **GET** /msgVpns/{msgVpnName}/aclProfiles | Get a list of ACL Profile objects.
*AllApi* | [**getAbout**](docs/AllApi.md#getAbout) | **GET** /about | Get an About object.
*AllApi* | [**getAboutApi**](docs/AllApi.md#getAboutApi) | **GET** /about/api | Get an API Description object.
*AllApi* | [**getAboutUser**](docs/AllApi.md#getAboutUser) | **GET** /about/user | Get a User object.
*AllApi* | [**getAboutUserMsgVpn**](docs/AllApi.md#getAboutUserMsgVpn) | **GET** /about/user/msgVpns/{msgVpnName} | Get a User Message VPN object.
*AllApi* | [**getAboutUserMsgVpns**](docs/AllApi.md#getAboutUserMsgVpns) | **GET** /about/user/msgVpns | Get a list of User Message VPN objects.
*AllApi* | [**getBroker**](docs/AllApi.md#getBroker) | **GET** / | Get a Broker object.
*AllApi* | [**getCertAuthorities**](docs/AllApi.md#getCertAuthorities) | **GET** /certAuthorities | Get a list of Certificate Authority objects.
*AllApi* | [**getCertAuthority**](docs/AllApi.md#getCertAuthority) | **GET** /certAuthorities/{certAuthorityName} | Get a Certificate Authority object.
*AllApi* | [**getCertAuthorityOcspTlsTrustedCommonName**](docs/AllApi.md#getCertAuthorityOcspTlsTrustedCommonName) | **GET** /certAuthorities/{certAuthorityName}/ocspTlsTrustedCommonNames/{ocspTlsTrustedCommonName} | Get an OCSP Responder Trusted Common Name object.
*AllApi* | [**getCertAuthorityOcspTlsTrustedCommonNames**](docs/AllApi.md#getCertAuthorityOcspTlsTrustedCommonNames) | **GET** /certAuthorities/{certAuthorityName}/ocspTlsTrustedCommonNames | Get a list of OCSP Responder Trusted Common Name objects.
*AllApi* | [**getClientCertAuthorities**](docs/AllApi.md#getClientCertAuthorities) | **GET** /clientCertAuthorities | Get a list of Client Certificate Authority objects.
*AllApi* | [**getClientCertAuthority**](docs/AllApi.md#getClientCertAuthority) | **GET** /clientCertAuthorities/{certAuthorityName} | Get a Client Certificate Authority object.
*AllApi* | [**getClientCertAuthorityOcspTlsTrustedCommonName**](docs/AllApi.md#getClientCertAuthorityOcspTlsTrustedCommonName) | **GET** /clientCertAuthorities/{certAuthorityName}/ocspTlsTrustedCommonNames/{ocspTlsTrustedCommonName} | Get an OCSP Responder Trusted Common Name object.
*AllApi* | [**getClientCertAuthorityOcspTlsTrustedCommonNames**](docs/AllApi.md#getClientCertAuthorityOcspTlsTrustedCommonNames) | **GET** /clientCertAuthorities/{certAuthorityName}/ocspTlsTrustedCommonNames | Get a list of OCSP Responder Trusted Common Name objects.
*AllApi* | [**getConfigSyncLocalDatabaseRow**](docs/AllApi.md#getConfigSyncLocalDatabaseRow) | **GET** /configSyncLocalDatabaseRows/{type},{name} | Get a Config Sync Local Database object.
*AllApi* | [**getConfigSyncLocalDatabaseRows**](docs/AllApi.md#getConfigSyncLocalDatabaseRows) | **GET** /configSyncLocalDatabaseRows | Get a list of Config Sync Local Database objects.
*AllApi* | [**getDmrCluster**](docs/AllApi.md#getDmrCluster) | **GET** /dmrClusters/{dmrClusterName} | Get a Cluster object.
*AllApi* | [**getDmrClusterLink**](docs/AllApi.md#getDmrClusterLink) | **GET** /dmrClusters/{dmrClusterName}/links/{remoteNodeName} | Get a Link object.
*AllApi* | [**getDmrClusterLinkChannel**](docs/AllApi.md#getDmrClusterLinkChannel) | **GET** /dmrClusters/{dmrClusterName}/links/{remoteNodeName}/channels/{msgVpnName} | Get a Cluster Link Channels object.
*AllApi* | [**getDmrClusterLinkChannels**](docs/AllApi.md#getDmrClusterLinkChannels) | **GET** /dmrClusters/{dmrClusterName}/links/{remoteNodeName}/channels | Get a list of Cluster Link Channels objects.
*AllApi* | [**getDmrClusterLinkRemoteAddress**](docs/AllApi.md#getDmrClusterLinkRemoteAddress) | **GET** /dmrClusters/{dmrClusterName}/links/{remoteNodeName}/remoteAddresses/{remoteAddress} | Get a Remote Address object.
*AllApi* | [**getDmrClusterLinkRemoteAddresses**](docs/AllApi.md#getDmrClusterLinkRemoteAddresses) | **GET** /dmrClusters/{dmrClusterName}/links/{remoteNodeName}/remoteAddresses | Get a list of Remote Address objects.
*AllApi* | [**getDmrClusterLinkTlsTrustedCommonName**](docs/AllApi.md#getDmrClusterLinkTlsTrustedCommonName) | **GET** /dmrClusters/{dmrClusterName}/links/{remoteNodeName}/tlsTrustedCommonNames/{tlsTrustedCommonName} | Get a Trusted Common Name object.
*AllApi* | [**getDmrClusterLinkTlsTrustedCommonNames**](docs/AllApi.md#getDmrClusterLinkTlsTrustedCommonNames) | **GET** /dmrClusters/{dmrClusterName}/links/{remoteNodeName}/tlsTrustedCommonNames | Get a list of Trusted Common Name objects.
*AllApi* | [**getDmrClusterLinks**](docs/AllApi.md#getDmrClusterLinks) | **GET** /dmrClusters/{dmrClusterName}/links | Get a list of Link objects.
*AllApi* | [**getDmrClusterTopologyIssue**](docs/AllApi.md#getDmrClusterTopologyIssue) | **GET** /dmrClusters/{dmrClusterName}/topologyIssues/{topologyIssue} | Get a Cluster Topology Issue object.
*AllApi* | [**getDmrClusterTopologyIssues**](docs/AllApi.md#getDmrClusterTopologyIssues) | **GET** /dmrClusters/{dmrClusterName}/topologyIssues | Get a list of Cluster Topology Issue objects.
*AllApi* | [**getDmrClusters**](docs/AllApi.md#getDmrClusters) | **GET** /dmrClusters | Get a list of Cluster objects.
*AllApi* | [**getDomainCertAuthorities**](docs/AllApi.md#getDomainCertAuthorities) | **GET** /domainCertAuthorities | Get a list of Domain Certificate Authority objects.
*AllApi* | [**getDomainCertAuthority**](docs/AllApi.md#getDomainCertAuthority) | **GET** /domainCertAuthorities/{certAuthorityName} | Get a Domain Certificate Authority object.
*AllApi* | [**getGuaranteedMsgingExternalDiskArray**](docs/AllApi.md#getGuaranteedMsgingExternalDiskArray) | **GET** /guaranteedMsgingExternalDiskArrays/{wwn} | Get an External Disk Array object.
*AllApi* | [**getGuaranteedMsgingExternalDiskArrays**](docs/AllApi.md#getGuaranteedMsgingExternalDiskArrays) | **GET** /guaranteedMsgingExternalDiskArrays | Get a list of External Disk Array objects.
*AllApi* | [**getMsgVpn**](docs/AllApi.md#getMsgVpn) | **GET** /msgVpns/{msgVpnName} | Get a Message VPN object.
*AllApi* | [**getMsgVpnAclProfile**](docs/AllApi.md#getMsgVpnAclProfile) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName} | Get an ACL Profile object.
*AllApi* | [**getMsgVpnAclProfileClientConnectException**](docs/AllApi.md#getMsgVpnAclProfileClientConnectException) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/clientConnectExceptions/{clientConnectExceptionAddress} | Get a Client Connect Exception object.
*AllApi* | [**getMsgVpnAclProfileClientConnectExceptions**](docs/AllApi.md#getMsgVpnAclProfileClientConnectExceptions) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/clientConnectExceptions | Get a list of Client Connect Exception objects.
*AllApi* | [**getMsgVpnAclProfilePublishException**](docs/AllApi.md#getMsgVpnAclProfilePublishException) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/publishExceptions/{topicSyntax},{publishExceptionTopic} | Get a Publish Topic Exception object.
*AllApi* | [**getMsgVpnAclProfilePublishExceptions**](docs/AllApi.md#getMsgVpnAclProfilePublishExceptions) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/publishExceptions | Get a list of Publish Topic Exception objects.
*AllApi* | [**getMsgVpnAclProfilePublishTopicException**](docs/AllApi.md#getMsgVpnAclProfilePublishTopicException) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/publishTopicExceptions/{publishTopicExceptionSyntax},{publishTopicException} | Get a Publish Topic Exception object.
*AllApi* | [**getMsgVpnAclProfilePublishTopicExceptions**](docs/AllApi.md#getMsgVpnAclProfilePublishTopicExceptions) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/publishTopicExceptions | Get a list of Publish Topic Exception objects.
*AllApi* | [**getMsgVpnAclProfileSubscribeException**](docs/AllApi.md#getMsgVpnAclProfileSubscribeException) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/subscribeExceptions/{topicSyntax},{subscribeExceptionTopic} | Get a Subscribe Topic Exception object.
*AllApi* | [**getMsgVpnAclProfileSubscribeExceptions**](docs/AllApi.md#getMsgVpnAclProfileSubscribeExceptions) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/subscribeExceptions | Get a list of Subscribe Topic Exception objects.
*AllApi* | [**getMsgVpnAclProfileSubscribeShareNameException**](docs/AllApi.md#getMsgVpnAclProfileSubscribeShareNameException) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/subscribeShareNameExceptions/{subscribeShareNameExceptionSyntax},{subscribeShareNameException} | Get a Subscribe Share Name Exception object.
*AllApi* | [**getMsgVpnAclProfileSubscribeShareNameExceptions**](docs/AllApi.md#getMsgVpnAclProfileSubscribeShareNameExceptions) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/subscribeShareNameExceptions | Get a list of Subscribe Share Name Exception objects.
*AllApi* | [**getMsgVpnAclProfileSubscribeTopicException**](docs/AllApi.md#getMsgVpnAclProfileSubscribeTopicException) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/subscribeTopicExceptions/{subscribeTopicExceptionSyntax},{subscribeTopicException} | Get a Subscribe Topic Exception object.
*AllApi* | [**getMsgVpnAclProfileSubscribeTopicExceptions**](docs/AllApi.md#getMsgVpnAclProfileSubscribeTopicExceptions) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/subscribeTopicExceptions | Get a list of Subscribe Topic Exception objects.
*AllApi* | [**getMsgVpnAclProfiles**](docs/AllApi.md#getMsgVpnAclProfiles) | **GET** /msgVpns/{msgVpnName}/aclProfiles | Get a list of ACL Profile objects.
*AllApi* | [**getMsgVpnAuthenticationOauthProvider**](docs/AllApi.md#getMsgVpnAuthenticationOauthProvider) | **GET** /msgVpns/{msgVpnName}/authenticationOauthProviders/{oauthProviderName} | Get an OAuth Provider object.
*AllApi* | [**getMsgVpnAuthenticationOauthProviders**](docs/AllApi.md#getMsgVpnAuthenticationOauthProviders) | **GET** /msgVpns/{msgVpnName}/authenticationOauthProviders | Get a list of OAuth Provider objects.
*AllApi* | [**getMsgVpnAuthorizationGroup**](docs/AllApi.md#getMsgVpnAuthorizationGroup) | **GET** /msgVpns/{msgVpnName}/authorizationGroups/{authorizationGroupName} | Get an LDAP Authorization Group object.
*AllApi* | [**getMsgVpnAuthorizationGroups**](docs/AllApi.md#getMsgVpnAuthorizationGroups) | **GET** /msgVpns/{msgVpnName}/authorizationGroups | Get a list of LDAP Authorization Group objects.
*AllApi* | [**getMsgVpnBridge**](docs/AllApi.md#getMsgVpnBridge) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter} | Get a Bridge object.
*AllApi* | [**getMsgVpnBridgeLocalSubscription**](docs/AllApi.md#getMsgVpnBridgeLocalSubscription) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/localSubscriptions/{localSubscriptionTopic} | Get a Bridge Local Subscriptions object.
*AllApi* | [**getMsgVpnBridgeLocalSubscriptions**](docs/AllApi.md#getMsgVpnBridgeLocalSubscriptions) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/localSubscriptions | Get a list of Bridge Local Subscriptions objects.
*AllApi* | [**getMsgVpnBridgeRemoteMsgVpn**](docs/AllApi.md#getMsgVpnBridgeRemoteMsgVpn) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/remoteMsgVpns/{remoteMsgVpnName},{remoteMsgVpnLocation},{remoteMsgVpnInterface} | Get a Remote Message VPN object.
*AllApi* | [**getMsgVpnBridgeRemoteMsgVpns**](docs/AllApi.md#getMsgVpnBridgeRemoteMsgVpns) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/remoteMsgVpns | Get a list of Remote Message VPN objects.
*AllApi* | [**getMsgVpnBridgeRemoteSubscription**](docs/AllApi.md#getMsgVpnBridgeRemoteSubscription) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/remoteSubscriptions/{remoteSubscriptionTopic} | Get a Remote Subscription object.
*AllApi* | [**getMsgVpnBridgeRemoteSubscriptions**](docs/AllApi.md#getMsgVpnBridgeRemoteSubscriptions) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/remoteSubscriptions | Get a list of Remote Subscription objects.
*AllApi* | [**getMsgVpnBridgeTlsTrustedCommonName**](docs/AllApi.md#getMsgVpnBridgeTlsTrustedCommonName) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/tlsTrustedCommonNames/{tlsTrustedCommonName} | Get a Trusted Common Name object.
*AllApi* | [**getMsgVpnBridgeTlsTrustedCommonNames**](docs/AllApi.md#getMsgVpnBridgeTlsTrustedCommonNames) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/tlsTrustedCommonNames | Get a list of Trusted Common Name objects.
*AllApi* | [**getMsgVpnBridges**](docs/AllApi.md#getMsgVpnBridges) | **GET** /msgVpns/{msgVpnName}/bridges | Get a list of Bridge objects.
*AllApi* | [**getMsgVpnClient**](docs/AllApi.md#getMsgVpnClient) | **GET** /msgVpns/{msgVpnName}/clients/{clientName} | Get a Client object.
*AllApi* | [**getMsgVpnClientConnection**](docs/AllApi.md#getMsgVpnClientConnection) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/connections/{clientAddress} | Get a Client Connection object.
*AllApi* | [**getMsgVpnClientConnections**](docs/AllApi.md#getMsgVpnClientConnections) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/connections | Get a list of Client Connection objects.
*AllApi* | [**getMsgVpnClientProfile**](docs/AllApi.md#getMsgVpnClientProfile) | **GET** /msgVpns/{msgVpnName}/clientProfiles/{clientProfileName} | Get a Client Profile object.
*AllApi* | [**getMsgVpnClientProfiles**](docs/AllApi.md#getMsgVpnClientProfiles) | **GET** /msgVpns/{msgVpnName}/clientProfiles | Get a list of Client Profile objects.
*AllApi* | [**getMsgVpnClientRxFlow**](docs/AllApi.md#getMsgVpnClientRxFlow) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/rxFlows/{flowId} | Get a Client Receive Flow object.
*AllApi* | [**getMsgVpnClientRxFlows**](docs/AllApi.md#getMsgVpnClientRxFlows) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/rxFlows | Get a list of Client Receive Flow objects.
*AllApi* | [**getMsgVpnClientSubscription**](docs/AllApi.md#getMsgVpnClientSubscription) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/subscriptions/{subscriptionTopic} | Get a Client Subscription object.
*AllApi* | [**getMsgVpnClientSubscriptions**](docs/AllApi.md#getMsgVpnClientSubscriptions) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/subscriptions | Get a list of Client Subscription objects.
*AllApi* | [**getMsgVpnClientTransactedSession**](docs/AllApi.md#getMsgVpnClientTransactedSession) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/transactedSessions/{sessionName} | Get a Client Transacted Session object.
*AllApi* | [**getMsgVpnClientTransactedSessions**](docs/AllApi.md#getMsgVpnClientTransactedSessions) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/transactedSessions | Get a list of Client Transacted Session objects.
*AllApi* | [**getMsgVpnClientTxFlow**](docs/AllApi.md#getMsgVpnClientTxFlow) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/txFlows/{flowId} | Get a Client Transmit Flow object.
*AllApi* | [**getMsgVpnClientTxFlows**](docs/AllApi.md#getMsgVpnClientTxFlows) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/txFlows | Get a list of Client Transmit Flow objects.
*AllApi* | [**getMsgVpnClientUsername**](docs/AllApi.md#getMsgVpnClientUsername) | **GET** /msgVpns/{msgVpnName}/clientUsernames/{clientUsername} | Get a Client Username object.
*AllApi* | [**getMsgVpnClientUsernames**](docs/AllApi.md#getMsgVpnClientUsernames) | **GET** /msgVpns/{msgVpnName}/clientUsernames | Get a list of Client Username objects.
*AllApi* | [**getMsgVpnClients**](docs/AllApi.md#getMsgVpnClients) | **GET** /msgVpns/{msgVpnName}/clients | Get a list of Client objects.
*AllApi* | [**getMsgVpnConfigSyncRemoteNode**](docs/AllApi.md#getMsgVpnConfigSyncRemoteNode) | **GET** /msgVpns/{msgVpnName}/configSyncRemoteNodes/{remoteNodeName} | Get a Config Sync Remote Node object.
*AllApi* | [**getMsgVpnConfigSyncRemoteNodes**](docs/AllApi.md#getMsgVpnConfigSyncRemoteNodes) | **GET** /msgVpns/{msgVpnName}/configSyncRemoteNodes | Get a list of Config Sync Remote Node objects.
*AllApi* | [**getMsgVpnDistributedCache**](docs/AllApi.md#getMsgVpnDistributedCache) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName} | Get a Distributed Cache object.
*AllApi* | [**getMsgVpnDistributedCacheCluster**](docs/AllApi.md#getMsgVpnDistributedCacheCluster) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName} | Get a Cache Cluster object.
*AllApi* | [**getMsgVpnDistributedCacheClusterGlobalCachingHomeCluster**](docs/AllApi.md#getMsgVpnDistributedCacheClusterGlobalCachingHomeCluster) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/globalCachingHomeClusters/{homeClusterName} | Get a Home Cache Cluster object.
*AllApi* | [**getMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefix**](docs/AllApi.md#getMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefix) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/globalCachingHomeClusters/{homeClusterName}/topicPrefixes/{topicPrefix} | Get a Topic Prefix object.
*AllApi* | [**getMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixes**](docs/AllApi.md#getMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixes) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/globalCachingHomeClusters/{homeClusterName}/topicPrefixes | Get a list of Topic Prefix objects.
*AllApi* | [**getMsgVpnDistributedCacheClusterGlobalCachingHomeClusters**](docs/AllApi.md#getMsgVpnDistributedCacheClusterGlobalCachingHomeClusters) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/globalCachingHomeClusters | Get a list of Home Cache Cluster objects.
*AllApi* | [**getMsgVpnDistributedCacheClusterInstance**](docs/AllApi.md#getMsgVpnDistributedCacheClusterInstance) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName} | Get a Cache Instance object.
*AllApi* | [**getMsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeCluster**](docs/AllApi.md#getMsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeCluster) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/remoteGlobalCachingHomeClusters/{homeClusterName} | Get a Remote Home Cache Cluster object.
*AllApi* | [**getMsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeClusters**](docs/AllApi.md#getMsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeClusters) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/remoteGlobalCachingHomeClusters | Get a list of Remote Home Cache Cluster objects.
*AllApi* | [**getMsgVpnDistributedCacheClusterInstanceRemoteTopic**](docs/AllApi.md#getMsgVpnDistributedCacheClusterInstanceRemoteTopic) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/remoteTopics/{topic} | Get a Remote Topic object.
*AllApi* | [**getMsgVpnDistributedCacheClusterInstanceRemoteTopics**](docs/AllApi.md#getMsgVpnDistributedCacheClusterInstanceRemoteTopics) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/remoteTopics | Get a list of Remote Topic objects.
*AllApi* | [**getMsgVpnDistributedCacheClusterInstances**](docs/AllApi.md#getMsgVpnDistributedCacheClusterInstances) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances | Get a list of Cache Instance objects.
*AllApi* | [**getMsgVpnDistributedCacheClusterTopic**](docs/AllApi.md#getMsgVpnDistributedCacheClusterTopic) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/topics/{topic} | Get a Topic object.
*AllApi* | [**getMsgVpnDistributedCacheClusterTopics**](docs/AllApi.md#getMsgVpnDistributedCacheClusterTopics) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/topics | Get a list of Topic objects.
*AllApi* | [**getMsgVpnDistributedCacheClusters**](docs/AllApi.md#getMsgVpnDistributedCacheClusters) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters | Get a list of Cache Cluster objects.
*AllApi* | [**getMsgVpnDistributedCaches**](docs/AllApi.md#getMsgVpnDistributedCaches) | **GET** /msgVpns/{msgVpnName}/distributedCaches | Get a list of Distributed Cache objects.
*AllApi* | [**getMsgVpnDmrBridge**](docs/AllApi.md#getMsgVpnDmrBridge) | **GET** /msgVpns/{msgVpnName}/dmrBridges/{remoteNodeName} | Get a DMR Bridge object.
*AllApi* | [**getMsgVpnDmrBridges**](docs/AllApi.md#getMsgVpnDmrBridges) | **GET** /msgVpns/{msgVpnName}/dmrBridges | Get a list of DMR Bridge objects.
*AllApi* | [**getMsgVpnJndiConnectionFactories**](docs/AllApi.md#getMsgVpnJndiConnectionFactories) | **GET** /msgVpns/{msgVpnName}/jndiConnectionFactories | Get a list of JNDI Connection Factory objects.
*AllApi* | [**getMsgVpnJndiConnectionFactory**](docs/AllApi.md#getMsgVpnJndiConnectionFactory) | **GET** /msgVpns/{msgVpnName}/jndiConnectionFactories/{connectionFactoryName} | Get a JNDI Connection Factory object.
*AllApi* | [**getMsgVpnJndiQueue**](docs/AllApi.md#getMsgVpnJndiQueue) | **GET** /msgVpns/{msgVpnName}/jndiQueues/{queueName} | Get a JNDI Queue object.
*AllApi* | [**getMsgVpnJndiQueues**](docs/AllApi.md#getMsgVpnJndiQueues) | **GET** /msgVpns/{msgVpnName}/jndiQueues | Get a list of JNDI Queue objects.
*AllApi* | [**getMsgVpnJndiTopic**](docs/AllApi.md#getMsgVpnJndiTopic) | **GET** /msgVpns/{msgVpnName}/jndiTopics/{topicName} | Get a JNDI Topic object.
*AllApi* | [**getMsgVpnJndiTopics**](docs/AllApi.md#getMsgVpnJndiTopics) | **GET** /msgVpns/{msgVpnName}/jndiTopics | Get a list of JNDI Topic objects.
*AllApi* | [**getMsgVpnMqttRetainCache**](docs/AllApi.md#getMsgVpnMqttRetainCache) | **GET** /msgVpns/{msgVpnName}/mqttRetainCaches/{cacheName} | Get an MQTT Retain Cache object.
*AllApi* | [**getMsgVpnMqttRetainCaches**](docs/AllApi.md#getMsgVpnMqttRetainCaches) | **GET** /msgVpns/{msgVpnName}/mqttRetainCaches | Get a list of MQTT Retain Cache objects.
*AllApi* | [**getMsgVpnMqttSession**](docs/AllApi.md#getMsgVpnMqttSession) | **GET** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter} | Get an MQTT Session object.
*AllApi* | [**getMsgVpnMqttSessionSubscription**](docs/AllApi.md#getMsgVpnMqttSessionSubscription) | **GET** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter}/subscriptions/{subscriptionTopic} | Get a Subscription object.
*AllApi* | [**getMsgVpnMqttSessionSubscriptions**](docs/AllApi.md#getMsgVpnMqttSessionSubscriptions) | **GET** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter}/subscriptions | Get a list of Subscription objects.
*AllApi* | [**getMsgVpnMqttSessions**](docs/AllApi.md#getMsgVpnMqttSessions) | **GET** /msgVpns/{msgVpnName}/mqttSessions | Get a list of MQTT Session objects.
*AllApi* | [**getMsgVpnQueue**](docs/AllApi.md#getMsgVpnQueue) | **GET** /msgVpns/{msgVpnName}/queues/{queueName} | Get a Queue object.
*AllApi* | [**getMsgVpnQueueMsg**](docs/AllApi.md#getMsgVpnQueueMsg) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/msgs/{msgId} | Get a Queue Message object.
*AllApi* | [**getMsgVpnQueueMsgs**](docs/AllApi.md#getMsgVpnQueueMsgs) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/msgs | Get a list of Queue Message objects.
*AllApi* | [**getMsgVpnQueuePriorities**](docs/AllApi.md#getMsgVpnQueuePriorities) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/priorities | Get a list of Queue Priority objects.
*AllApi* | [**getMsgVpnQueuePriority**](docs/AllApi.md#getMsgVpnQueuePriority) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/priorities/{priority} | Get a Queue Priority object.
*AllApi* | [**getMsgVpnQueueSubscription**](docs/AllApi.md#getMsgVpnQueueSubscription) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/subscriptions/{subscriptionTopic} | Get a Queue Subscription object.
*AllApi* | [**getMsgVpnQueueSubscriptions**](docs/AllApi.md#getMsgVpnQueueSubscriptions) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/subscriptions | Get a list of Queue Subscription objects.
*AllApi* | [**getMsgVpnQueueTemplate**](docs/AllApi.md#getMsgVpnQueueTemplate) | **GET** /msgVpns/{msgVpnName}/queueTemplates/{queueTemplateName} | Get a Queue Template object.
*AllApi* | [**getMsgVpnQueueTemplates**](docs/AllApi.md#getMsgVpnQueueTemplates) | **GET** /msgVpns/{msgVpnName}/queueTemplates | Get a list of Queue Template objects.
*AllApi* | [**getMsgVpnQueueTxFlow**](docs/AllApi.md#getMsgVpnQueueTxFlow) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/txFlows/{flowId} | Get a Queue Transmit Flow object.
*AllApi* | [**getMsgVpnQueueTxFlows**](docs/AllApi.md#getMsgVpnQueueTxFlows) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/txFlows | Get a list of Queue Transmit Flow objects.
*AllApi* | [**getMsgVpnQueues**](docs/AllApi.md#getMsgVpnQueues) | **GET** /msgVpns/{msgVpnName}/queues | Get a list of Queue objects.
*AllApi* | [**getMsgVpnReplayLog**](docs/AllApi.md#getMsgVpnReplayLog) | **GET** /msgVpns/{msgVpnName}/replayLogs/{replayLogName} | Get a Replay Log object.
*AllApi* | [**getMsgVpnReplayLogMsg**](docs/AllApi.md#getMsgVpnReplayLogMsg) | **GET** /msgVpns/{msgVpnName}/replayLogs/{replayLogName}/msgs/{msgId} | Get a Message object.
*AllApi* | [**getMsgVpnReplayLogMsgs**](docs/AllApi.md#getMsgVpnReplayLogMsgs) | **GET** /msgVpns/{msgVpnName}/replayLogs/{replayLogName}/msgs | Get a list of Message objects.
*AllApi* | [**getMsgVpnReplayLogs**](docs/AllApi.md#getMsgVpnReplayLogs) | **GET** /msgVpns/{msgVpnName}/replayLogs | Get a list of Replay Log objects.
*AllApi* | [**getMsgVpnReplicatedTopic**](docs/AllApi.md#getMsgVpnReplicatedTopic) | **GET** /msgVpns/{msgVpnName}/replicatedTopics/{replicatedTopic} | Get a Replicated Topic object.
*AllApi* | [**getMsgVpnReplicatedTopics**](docs/AllApi.md#getMsgVpnReplicatedTopics) | **GET** /msgVpns/{msgVpnName}/replicatedTopics | Get a list of Replicated Topic objects.
*AllApi* | [**getMsgVpnRestDeliveryPoint**](docs/AllApi.md#getMsgVpnRestDeliveryPoint) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName} | Get a REST Delivery Point object.
*AllApi* | [**getMsgVpnRestDeliveryPointQueueBinding**](docs/AllApi.md#getMsgVpnRestDeliveryPointQueueBinding) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/queueBindings/{queueBindingName} | Get a Queue Binding object.
*AllApi* | [**getMsgVpnRestDeliveryPointQueueBindingRequestHeader**](docs/AllApi.md#getMsgVpnRestDeliveryPointQueueBindingRequestHeader) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/queueBindings/{queueBindingName}/requestHeaders/{headerName} | Get a Request Header object.
*AllApi* | [**getMsgVpnRestDeliveryPointQueueBindingRequestHeaders**](docs/AllApi.md#getMsgVpnRestDeliveryPointQueueBindingRequestHeaders) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/queueBindings/{queueBindingName}/requestHeaders | Get a list of Request Header objects.
*AllApi* | [**getMsgVpnRestDeliveryPointQueueBindings**](docs/AllApi.md#getMsgVpnRestDeliveryPointQueueBindings) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/queueBindings | Get a list of Queue Binding objects.
*AllApi* | [**getMsgVpnRestDeliveryPointRestConsumer**](docs/AllApi.md#getMsgVpnRestDeliveryPointRestConsumer) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers/{restConsumerName} | Get a REST Consumer object.
*AllApi* | [**getMsgVpnRestDeliveryPointRestConsumerOauthJwtClaim**](docs/AllApi.md#getMsgVpnRestDeliveryPointRestConsumerOauthJwtClaim) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers/{restConsumerName}/oauthJwtClaims/{oauthJwtClaimName} | Get a Claim object.
*AllApi* | [**getMsgVpnRestDeliveryPointRestConsumerOauthJwtClaims**](docs/AllApi.md#getMsgVpnRestDeliveryPointRestConsumerOauthJwtClaims) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers/{restConsumerName}/oauthJwtClaims | Get a list of Claim objects.
*AllApi* | [**getMsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonName**](docs/AllApi.md#getMsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonName) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers/{restConsumerName}/tlsTrustedCommonNames/{tlsTrustedCommonName} | Get a Trusted Common Name object.
*AllApi* | [**getMsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNames**](docs/AllApi.md#getMsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNames) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers/{restConsumerName}/tlsTrustedCommonNames | Get a list of Trusted Common Name objects.
*AllApi* | [**getMsgVpnRestDeliveryPointRestConsumers**](docs/AllApi.md#getMsgVpnRestDeliveryPointRestConsumers) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers | Get a list of REST Consumer objects.
*AllApi* | [**getMsgVpnRestDeliveryPoints**](docs/AllApi.md#getMsgVpnRestDeliveryPoints) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints | Get a list of REST Delivery Point objects.
*AllApi* | [**getMsgVpnTopicEndpoint**](docs/AllApi.md#getMsgVpnTopicEndpoint) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName} | Get a Topic Endpoint object.
*AllApi* | [**getMsgVpnTopicEndpointMsg**](docs/AllApi.md#getMsgVpnTopicEndpointMsg) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/msgs/{msgId} | Get a Topic Endpoint Message object.
*AllApi* | [**getMsgVpnTopicEndpointMsgs**](docs/AllApi.md#getMsgVpnTopicEndpointMsgs) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/msgs | Get a list of Topic Endpoint Message objects.
*AllApi* | [**getMsgVpnTopicEndpointPriorities**](docs/AllApi.md#getMsgVpnTopicEndpointPriorities) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/priorities | Get a list of Topic Endpoint Priority objects.
*AllApi* | [**getMsgVpnTopicEndpointPriority**](docs/AllApi.md#getMsgVpnTopicEndpointPriority) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/priorities/{priority} | Get a Topic Endpoint Priority object.
*AllApi* | [**getMsgVpnTopicEndpointTemplate**](docs/AllApi.md#getMsgVpnTopicEndpointTemplate) | **GET** /msgVpns/{msgVpnName}/topicEndpointTemplates/{topicEndpointTemplateName} | Get a Topic Endpoint Template object.
*AllApi* | [**getMsgVpnTopicEndpointTemplates**](docs/AllApi.md#getMsgVpnTopicEndpointTemplates) | **GET** /msgVpns/{msgVpnName}/topicEndpointTemplates | Get a list of Topic Endpoint Template objects.
*AllApi* | [**getMsgVpnTopicEndpointTxFlow**](docs/AllApi.md#getMsgVpnTopicEndpointTxFlow) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/txFlows/{flowId} | Get a Topic Endpoint Transmit Flow object.
*AllApi* | [**getMsgVpnTopicEndpointTxFlows**](docs/AllApi.md#getMsgVpnTopicEndpointTxFlows) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/txFlows | Get a list of Topic Endpoint Transmit Flow objects.
*AllApi* | [**getMsgVpnTopicEndpoints**](docs/AllApi.md#getMsgVpnTopicEndpoints) | **GET** /msgVpns/{msgVpnName}/topicEndpoints | Get a list of Topic Endpoint objects.
*AllApi* | [**getMsgVpnTransaction**](docs/AllApi.md#getMsgVpnTransaction) | **GET** /msgVpns/{msgVpnName}/transactions/{xid} | Get a Replicated Local Transaction or XA Transaction object.
*AllApi* | [**getMsgVpnTransactionConsumerMsg**](docs/AllApi.md#getMsgVpnTransactionConsumerMsg) | **GET** /msgVpns/{msgVpnName}/transactions/{xid}/consumerMsgs/{msgId} | Get a Transaction Consumer Message object.
*AllApi* | [**getMsgVpnTransactionConsumerMsgs**](docs/AllApi.md#getMsgVpnTransactionConsumerMsgs) | **GET** /msgVpns/{msgVpnName}/transactions/{xid}/consumerMsgs | Get a list of Transaction Consumer Message objects.
*AllApi* | [**getMsgVpnTransactionPublisherMsg**](docs/AllApi.md#getMsgVpnTransactionPublisherMsg) | **GET** /msgVpns/{msgVpnName}/transactions/{xid}/publisherMsgs/{msgId} | Get a Transaction Publisher Message object.
*AllApi* | [**getMsgVpnTransactionPublisherMsgs**](docs/AllApi.md#getMsgVpnTransactionPublisherMsgs) | **GET** /msgVpns/{msgVpnName}/transactions/{xid}/publisherMsgs | Get a list of Transaction Publisher Message objects.
*AllApi* | [**getMsgVpnTransactions**](docs/AllApi.md#getMsgVpnTransactions) | **GET** /msgVpns/{msgVpnName}/transactions | Get a list of Replicated Local Transaction or XA Transaction objects.
*AllApi* | [**getMsgVpns**](docs/AllApi.md#getMsgVpns) | **GET** /msgVpns | Get a list of Message VPN objects.
*AllApi* | [**getSession**](docs/AllApi.md#getSession) | **GET** /sessions/{sessionUsername},{sessionId} | Get a Session object.
*AllApi* | [**getSessions**](docs/AllApi.md#getSessions) | **GET** /sessions | Get a list of Session objects.
*AllApi* | [**getStandardDomainCertAuthorities**](docs/AllApi.md#getStandardDomainCertAuthorities) | **GET** /standardDomainCertAuthorities | Get a list of Standard Domain Certificate Authority objects.
*AllApi* | [**getStandardDomainCertAuthority**](docs/AllApi.md#getStandardDomainCertAuthority) | **GET** /standardDomainCertAuthorities/{certAuthorityName} | Get a Standard Domain Certificate Authority object.
*AllApi* | [**getVirtualHostname**](docs/AllApi.md#getVirtualHostname) | **GET** /virtualHostnames/{virtualHostname} | Get a Virtual Hostname object.
*AllApi* | [**getVirtualHostnames**](docs/AllApi.md#getVirtualHostnames) | **GET** /virtualHostnames | Get a list of Virtual Hostname objects.
*AuthenticationOauthProviderApi* | [**getMsgVpnAuthenticationOauthProvider**](docs/AuthenticationOauthProviderApi.md#getMsgVpnAuthenticationOauthProvider) | **GET** /msgVpns/{msgVpnName}/authenticationOauthProviders/{oauthProviderName} | Get an OAuth Provider object.
*AuthenticationOauthProviderApi* | [**getMsgVpnAuthenticationOauthProviders**](docs/AuthenticationOauthProviderApi.md#getMsgVpnAuthenticationOauthProviders) | **GET** /msgVpns/{msgVpnName}/authenticationOauthProviders | Get a list of OAuth Provider objects.
*AuthorizationGroupApi* | [**getMsgVpnAuthorizationGroup**](docs/AuthorizationGroupApi.md#getMsgVpnAuthorizationGroup) | **GET** /msgVpns/{msgVpnName}/authorizationGroups/{authorizationGroupName} | Get an LDAP Authorization Group object.
*AuthorizationGroupApi* | [**getMsgVpnAuthorizationGroups**](docs/AuthorizationGroupApi.md#getMsgVpnAuthorizationGroups) | **GET** /msgVpns/{msgVpnName}/authorizationGroups | Get a list of LDAP Authorization Group objects.
*BridgeApi* | [**getMsgVpnBridge**](docs/BridgeApi.md#getMsgVpnBridge) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter} | Get a Bridge object.
*BridgeApi* | [**getMsgVpnBridgeLocalSubscription**](docs/BridgeApi.md#getMsgVpnBridgeLocalSubscription) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/localSubscriptions/{localSubscriptionTopic} | Get a Bridge Local Subscriptions object.
*BridgeApi* | [**getMsgVpnBridgeLocalSubscriptions**](docs/BridgeApi.md#getMsgVpnBridgeLocalSubscriptions) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/localSubscriptions | Get a list of Bridge Local Subscriptions objects.
*BridgeApi* | [**getMsgVpnBridgeRemoteMsgVpn**](docs/BridgeApi.md#getMsgVpnBridgeRemoteMsgVpn) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/remoteMsgVpns/{remoteMsgVpnName},{remoteMsgVpnLocation},{remoteMsgVpnInterface} | Get a Remote Message VPN object.
*BridgeApi* | [**getMsgVpnBridgeRemoteMsgVpns**](docs/BridgeApi.md#getMsgVpnBridgeRemoteMsgVpns) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/remoteMsgVpns | Get a list of Remote Message VPN objects.
*BridgeApi* | [**getMsgVpnBridgeRemoteSubscription**](docs/BridgeApi.md#getMsgVpnBridgeRemoteSubscription) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/remoteSubscriptions/{remoteSubscriptionTopic} | Get a Remote Subscription object.
*BridgeApi* | [**getMsgVpnBridgeRemoteSubscriptions**](docs/BridgeApi.md#getMsgVpnBridgeRemoteSubscriptions) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/remoteSubscriptions | Get a list of Remote Subscription objects.
*BridgeApi* | [**getMsgVpnBridgeTlsTrustedCommonName**](docs/BridgeApi.md#getMsgVpnBridgeTlsTrustedCommonName) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/tlsTrustedCommonNames/{tlsTrustedCommonName} | Get a Trusted Common Name object.
*BridgeApi* | [**getMsgVpnBridgeTlsTrustedCommonNames**](docs/BridgeApi.md#getMsgVpnBridgeTlsTrustedCommonNames) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/tlsTrustedCommonNames | Get a list of Trusted Common Name objects.
*BridgeApi* | [**getMsgVpnBridges**](docs/BridgeApi.md#getMsgVpnBridges) | **GET** /msgVpns/{msgVpnName}/bridges | Get a list of Bridge objects.
*CertAuthorityApi* | [**getCertAuthorities**](docs/CertAuthorityApi.md#getCertAuthorities) | **GET** /certAuthorities | Get a list of Certificate Authority objects.
*CertAuthorityApi* | [**getCertAuthority**](docs/CertAuthorityApi.md#getCertAuthority) | **GET** /certAuthorities/{certAuthorityName} | Get a Certificate Authority object.
*CertAuthorityApi* | [**getCertAuthorityOcspTlsTrustedCommonName**](docs/CertAuthorityApi.md#getCertAuthorityOcspTlsTrustedCommonName) | **GET** /certAuthorities/{certAuthorityName}/ocspTlsTrustedCommonNames/{ocspTlsTrustedCommonName} | Get an OCSP Responder Trusted Common Name object.
*CertAuthorityApi* | [**getCertAuthorityOcspTlsTrustedCommonNames**](docs/CertAuthorityApi.md#getCertAuthorityOcspTlsTrustedCommonNames) | **GET** /certAuthorities/{certAuthorityName}/ocspTlsTrustedCommonNames | Get a list of OCSP Responder Trusted Common Name objects.
*ClientApi* | [**getMsgVpnClient**](docs/ClientApi.md#getMsgVpnClient) | **GET** /msgVpns/{msgVpnName}/clients/{clientName} | Get a Client object.
*ClientApi* | [**getMsgVpnClientConnection**](docs/ClientApi.md#getMsgVpnClientConnection) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/connections/{clientAddress} | Get a Client Connection object.
*ClientApi* | [**getMsgVpnClientConnections**](docs/ClientApi.md#getMsgVpnClientConnections) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/connections | Get a list of Client Connection objects.
*ClientApi* | [**getMsgVpnClientRxFlow**](docs/ClientApi.md#getMsgVpnClientRxFlow) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/rxFlows/{flowId} | Get a Client Receive Flow object.
*ClientApi* | [**getMsgVpnClientRxFlows**](docs/ClientApi.md#getMsgVpnClientRxFlows) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/rxFlows | Get a list of Client Receive Flow objects.
*ClientApi* | [**getMsgVpnClientSubscription**](docs/ClientApi.md#getMsgVpnClientSubscription) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/subscriptions/{subscriptionTopic} | Get a Client Subscription object.
*ClientApi* | [**getMsgVpnClientSubscriptions**](docs/ClientApi.md#getMsgVpnClientSubscriptions) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/subscriptions | Get a list of Client Subscription objects.
*ClientApi* | [**getMsgVpnClientTransactedSession**](docs/ClientApi.md#getMsgVpnClientTransactedSession) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/transactedSessions/{sessionName} | Get a Client Transacted Session object.
*ClientApi* | [**getMsgVpnClientTransactedSessions**](docs/ClientApi.md#getMsgVpnClientTransactedSessions) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/transactedSessions | Get a list of Client Transacted Session objects.
*ClientApi* | [**getMsgVpnClientTxFlow**](docs/ClientApi.md#getMsgVpnClientTxFlow) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/txFlows/{flowId} | Get a Client Transmit Flow object.
*ClientApi* | [**getMsgVpnClientTxFlows**](docs/ClientApi.md#getMsgVpnClientTxFlows) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/txFlows | Get a list of Client Transmit Flow objects.
*ClientApi* | [**getMsgVpnClients**](docs/ClientApi.md#getMsgVpnClients) | **GET** /msgVpns/{msgVpnName}/clients | Get a list of Client objects.
*ClientCertAuthorityApi* | [**getClientCertAuthorities**](docs/ClientCertAuthorityApi.md#getClientCertAuthorities) | **GET** /clientCertAuthorities | Get a list of Client Certificate Authority objects.
*ClientCertAuthorityApi* | [**getClientCertAuthority**](docs/ClientCertAuthorityApi.md#getClientCertAuthority) | **GET** /clientCertAuthorities/{certAuthorityName} | Get a Client Certificate Authority object.
*ClientCertAuthorityApi* | [**getClientCertAuthorityOcspTlsTrustedCommonName**](docs/ClientCertAuthorityApi.md#getClientCertAuthorityOcspTlsTrustedCommonName) | **GET** /clientCertAuthorities/{certAuthorityName}/ocspTlsTrustedCommonNames/{ocspTlsTrustedCommonName} | Get an OCSP Responder Trusted Common Name object.
*ClientCertAuthorityApi* | [**getClientCertAuthorityOcspTlsTrustedCommonNames**](docs/ClientCertAuthorityApi.md#getClientCertAuthorityOcspTlsTrustedCommonNames) | **GET** /clientCertAuthorities/{certAuthorityName}/ocspTlsTrustedCommonNames | Get a list of OCSP Responder Trusted Common Name objects.
*ClientProfileApi* | [**getMsgVpnClientProfile**](docs/ClientProfileApi.md#getMsgVpnClientProfile) | **GET** /msgVpns/{msgVpnName}/clientProfiles/{clientProfileName} | Get a Client Profile object.
*ClientProfileApi* | [**getMsgVpnClientProfiles**](docs/ClientProfileApi.md#getMsgVpnClientProfiles) | **GET** /msgVpns/{msgVpnName}/clientProfiles | Get a list of Client Profile objects.
*ClientUsernameApi* | [**getMsgVpnClientUsername**](docs/ClientUsernameApi.md#getMsgVpnClientUsername) | **GET** /msgVpns/{msgVpnName}/clientUsernames/{clientUsername} | Get a Client Username object.
*ClientUsernameApi* | [**getMsgVpnClientUsernames**](docs/ClientUsernameApi.md#getMsgVpnClientUsernames) | **GET** /msgVpns/{msgVpnName}/clientUsernames | Get a list of Client Username objects.
*ConfigSyncLocalDatabaseRowApi* | [**getConfigSyncLocalDatabaseRow**](docs/ConfigSyncLocalDatabaseRowApi.md#getConfigSyncLocalDatabaseRow) | **GET** /configSyncLocalDatabaseRows/{type},{name} | Get a Config Sync Local Database object.
*ConfigSyncLocalDatabaseRowApi* | [**getConfigSyncLocalDatabaseRows**](docs/ConfigSyncLocalDatabaseRowApi.md#getConfigSyncLocalDatabaseRows) | **GET** /configSyncLocalDatabaseRows | Get a list of Config Sync Local Database objects.
*ConfigSyncRemoteNodeApi* | [**getMsgVpnConfigSyncRemoteNode**](docs/ConfigSyncRemoteNodeApi.md#getMsgVpnConfigSyncRemoteNode) | **GET** /msgVpns/{msgVpnName}/configSyncRemoteNodes/{remoteNodeName} | Get a Config Sync Remote Node object.
*ConfigSyncRemoteNodeApi* | [**getMsgVpnConfigSyncRemoteNodes**](docs/ConfigSyncRemoteNodeApi.md#getMsgVpnConfigSyncRemoteNodes) | **GET** /msgVpns/{msgVpnName}/configSyncRemoteNodes | Get a list of Config Sync Remote Node objects.
*DistributedCacheApi* | [**getMsgVpnDistributedCache**](docs/DistributedCacheApi.md#getMsgVpnDistributedCache) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName} | Get a Distributed Cache object.
*DistributedCacheApi* | [**getMsgVpnDistributedCacheCluster**](docs/DistributedCacheApi.md#getMsgVpnDistributedCacheCluster) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName} | Get a Cache Cluster object.
*DistributedCacheApi* | [**getMsgVpnDistributedCacheClusterGlobalCachingHomeCluster**](docs/DistributedCacheApi.md#getMsgVpnDistributedCacheClusterGlobalCachingHomeCluster) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/globalCachingHomeClusters/{homeClusterName} | Get a Home Cache Cluster object.
*DistributedCacheApi* | [**getMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefix**](docs/DistributedCacheApi.md#getMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefix) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/globalCachingHomeClusters/{homeClusterName}/topicPrefixes/{topicPrefix} | Get a Topic Prefix object.
*DistributedCacheApi* | [**getMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixes**](docs/DistributedCacheApi.md#getMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixes) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/globalCachingHomeClusters/{homeClusterName}/topicPrefixes | Get a list of Topic Prefix objects.
*DistributedCacheApi* | [**getMsgVpnDistributedCacheClusterGlobalCachingHomeClusters**](docs/DistributedCacheApi.md#getMsgVpnDistributedCacheClusterGlobalCachingHomeClusters) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/globalCachingHomeClusters | Get a list of Home Cache Cluster objects.
*DistributedCacheApi* | [**getMsgVpnDistributedCacheClusterInstance**](docs/DistributedCacheApi.md#getMsgVpnDistributedCacheClusterInstance) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName} | Get a Cache Instance object.
*DistributedCacheApi* | [**getMsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeCluster**](docs/DistributedCacheApi.md#getMsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeCluster) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/remoteGlobalCachingHomeClusters/{homeClusterName} | Get a Remote Home Cache Cluster object.
*DistributedCacheApi* | [**getMsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeClusters**](docs/DistributedCacheApi.md#getMsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeClusters) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/remoteGlobalCachingHomeClusters | Get a list of Remote Home Cache Cluster objects.
*DistributedCacheApi* | [**getMsgVpnDistributedCacheClusterInstanceRemoteTopic**](docs/DistributedCacheApi.md#getMsgVpnDistributedCacheClusterInstanceRemoteTopic) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/remoteTopics/{topic} | Get a Remote Topic object.
*DistributedCacheApi* | [**getMsgVpnDistributedCacheClusterInstanceRemoteTopics**](docs/DistributedCacheApi.md#getMsgVpnDistributedCacheClusterInstanceRemoteTopics) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/remoteTopics | Get a list of Remote Topic objects.
*DistributedCacheApi* | [**getMsgVpnDistributedCacheClusterInstances**](docs/DistributedCacheApi.md#getMsgVpnDistributedCacheClusterInstances) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances | Get a list of Cache Instance objects.
*DistributedCacheApi* | [**getMsgVpnDistributedCacheClusterTopic**](docs/DistributedCacheApi.md#getMsgVpnDistributedCacheClusterTopic) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/topics/{topic} | Get a Topic object.
*DistributedCacheApi* | [**getMsgVpnDistributedCacheClusterTopics**](docs/DistributedCacheApi.md#getMsgVpnDistributedCacheClusterTopics) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/topics | Get a list of Topic objects.
*DistributedCacheApi* | [**getMsgVpnDistributedCacheClusters**](docs/DistributedCacheApi.md#getMsgVpnDistributedCacheClusters) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters | Get a list of Cache Cluster objects.
*DistributedCacheApi* | [**getMsgVpnDistributedCaches**](docs/DistributedCacheApi.md#getMsgVpnDistributedCaches) | **GET** /msgVpns/{msgVpnName}/distributedCaches | Get a list of Distributed Cache objects.
*DmrBridgeApi* | [**getMsgVpnDmrBridge**](docs/DmrBridgeApi.md#getMsgVpnDmrBridge) | **GET** /msgVpns/{msgVpnName}/dmrBridges/{remoteNodeName} | Get a DMR Bridge object.
*DmrBridgeApi* | [**getMsgVpnDmrBridges**](docs/DmrBridgeApi.md#getMsgVpnDmrBridges) | **GET** /msgVpns/{msgVpnName}/dmrBridges | Get a list of DMR Bridge objects.
*DmrClusterApi* | [**getDmrCluster**](docs/DmrClusterApi.md#getDmrCluster) | **GET** /dmrClusters/{dmrClusterName} | Get a Cluster object.
*DmrClusterApi* | [**getDmrClusterLink**](docs/DmrClusterApi.md#getDmrClusterLink) | **GET** /dmrClusters/{dmrClusterName}/links/{remoteNodeName} | Get a Link object.
*DmrClusterApi* | [**getDmrClusterLinkChannel**](docs/DmrClusterApi.md#getDmrClusterLinkChannel) | **GET** /dmrClusters/{dmrClusterName}/links/{remoteNodeName}/channels/{msgVpnName} | Get a Cluster Link Channels object.
*DmrClusterApi* | [**getDmrClusterLinkChannels**](docs/DmrClusterApi.md#getDmrClusterLinkChannels) | **GET** /dmrClusters/{dmrClusterName}/links/{remoteNodeName}/channels | Get a list of Cluster Link Channels objects.
*DmrClusterApi* | [**getDmrClusterLinkRemoteAddress**](docs/DmrClusterApi.md#getDmrClusterLinkRemoteAddress) | **GET** /dmrClusters/{dmrClusterName}/links/{remoteNodeName}/remoteAddresses/{remoteAddress} | Get a Remote Address object.
*DmrClusterApi* | [**getDmrClusterLinkRemoteAddresses**](docs/DmrClusterApi.md#getDmrClusterLinkRemoteAddresses) | **GET** /dmrClusters/{dmrClusterName}/links/{remoteNodeName}/remoteAddresses | Get a list of Remote Address objects.
*DmrClusterApi* | [**getDmrClusterLinkTlsTrustedCommonName**](docs/DmrClusterApi.md#getDmrClusterLinkTlsTrustedCommonName) | **GET** /dmrClusters/{dmrClusterName}/links/{remoteNodeName}/tlsTrustedCommonNames/{tlsTrustedCommonName} | Get a Trusted Common Name object.
*DmrClusterApi* | [**getDmrClusterLinkTlsTrustedCommonNames**](docs/DmrClusterApi.md#getDmrClusterLinkTlsTrustedCommonNames) | **GET** /dmrClusters/{dmrClusterName}/links/{remoteNodeName}/tlsTrustedCommonNames | Get a list of Trusted Common Name objects.
*DmrClusterApi* | [**getDmrClusterLinks**](docs/DmrClusterApi.md#getDmrClusterLinks) | **GET** /dmrClusters/{dmrClusterName}/links | Get a list of Link objects.
*DmrClusterApi* | [**getDmrClusterTopologyIssue**](docs/DmrClusterApi.md#getDmrClusterTopologyIssue) | **GET** /dmrClusters/{dmrClusterName}/topologyIssues/{topologyIssue} | Get a Cluster Topology Issue object.
*DmrClusterApi* | [**getDmrClusterTopologyIssues**](docs/DmrClusterApi.md#getDmrClusterTopologyIssues) | **GET** /dmrClusters/{dmrClusterName}/topologyIssues | Get a list of Cluster Topology Issue objects.
*DmrClusterApi* | [**getDmrClusters**](docs/DmrClusterApi.md#getDmrClusters) | **GET** /dmrClusters | Get a list of Cluster objects.
*DomainCertAuthorityApi* | [**getDomainCertAuthorities**](docs/DomainCertAuthorityApi.md#getDomainCertAuthorities) | **GET** /domainCertAuthorities | Get a list of Domain Certificate Authority objects.
*DomainCertAuthorityApi* | [**getDomainCertAuthority**](docs/DomainCertAuthorityApi.md#getDomainCertAuthority) | **GET** /domainCertAuthorities/{certAuthorityName} | Get a Domain Certificate Authority object.
*GuaranteedMsgingExternalDiskArrayApi* | [**getGuaranteedMsgingExternalDiskArray**](docs/GuaranteedMsgingExternalDiskArrayApi.md#getGuaranteedMsgingExternalDiskArray) | **GET** /guaranteedMsgingExternalDiskArrays/{wwn} | Get an External Disk Array object.
*GuaranteedMsgingExternalDiskArrayApi* | [**getGuaranteedMsgingExternalDiskArrays**](docs/GuaranteedMsgingExternalDiskArrayApi.md#getGuaranteedMsgingExternalDiskArrays) | **GET** /guaranteedMsgingExternalDiskArrays | Get a list of External Disk Array objects.
*JndiApi* | [**getMsgVpnJndiConnectionFactories**](docs/JndiApi.md#getMsgVpnJndiConnectionFactories) | **GET** /msgVpns/{msgVpnName}/jndiConnectionFactories | Get a list of JNDI Connection Factory objects.
*JndiApi* | [**getMsgVpnJndiConnectionFactory**](docs/JndiApi.md#getMsgVpnJndiConnectionFactory) | **GET** /msgVpns/{msgVpnName}/jndiConnectionFactories/{connectionFactoryName} | Get a JNDI Connection Factory object.
*JndiApi* | [**getMsgVpnJndiQueue**](docs/JndiApi.md#getMsgVpnJndiQueue) | **GET** /msgVpns/{msgVpnName}/jndiQueues/{queueName} | Get a JNDI Queue object.
*JndiApi* | [**getMsgVpnJndiQueues**](docs/JndiApi.md#getMsgVpnJndiQueues) | **GET** /msgVpns/{msgVpnName}/jndiQueues | Get a list of JNDI Queue objects.
*JndiApi* | [**getMsgVpnJndiTopic**](docs/JndiApi.md#getMsgVpnJndiTopic) | **GET** /msgVpns/{msgVpnName}/jndiTopics/{topicName} | Get a JNDI Topic object.
*JndiApi* | [**getMsgVpnJndiTopics**](docs/JndiApi.md#getMsgVpnJndiTopics) | **GET** /msgVpns/{msgVpnName}/jndiTopics | Get a list of JNDI Topic objects.
*MqttRetainCacheApi* | [**getMsgVpnMqttRetainCache**](docs/MqttRetainCacheApi.md#getMsgVpnMqttRetainCache) | **GET** /msgVpns/{msgVpnName}/mqttRetainCaches/{cacheName} | Get an MQTT Retain Cache object.
*MqttRetainCacheApi* | [**getMsgVpnMqttRetainCaches**](docs/MqttRetainCacheApi.md#getMsgVpnMqttRetainCaches) | **GET** /msgVpns/{msgVpnName}/mqttRetainCaches | Get a list of MQTT Retain Cache objects.
*MqttSessionApi* | [**getMsgVpnMqttSession**](docs/MqttSessionApi.md#getMsgVpnMqttSession) | **GET** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter} | Get an MQTT Session object.
*MqttSessionApi* | [**getMsgVpnMqttSessionSubscription**](docs/MqttSessionApi.md#getMsgVpnMqttSessionSubscription) | **GET** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter}/subscriptions/{subscriptionTopic} | Get a Subscription object.
*MqttSessionApi* | [**getMsgVpnMqttSessionSubscriptions**](docs/MqttSessionApi.md#getMsgVpnMqttSessionSubscriptions) | **GET** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter}/subscriptions | Get a list of Subscription objects.
*MqttSessionApi* | [**getMsgVpnMqttSessions**](docs/MqttSessionApi.md#getMsgVpnMqttSessions) | **GET** /msgVpns/{msgVpnName}/mqttSessions | Get a list of MQTT Session objects.
*MsgVpnApi* | [**getMsgVpn**](docs/MsgVpnApi.md#getMsgVpn) | **GET** /msgVpns/{msgVpnName} | Get a Message VPN object.
*MsgVpnApi* | [**getMsgVpnAclProfile**](docs/MsgVpnApi.md#getMsgVpnAclProfile) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName} | Get an ACL Profile object.
*MsgVpnApi* | [**getMsgVpnAclProfileClientConnectException**](docs/MsgVpnApi.md#getMsgVpnAclProfileClientConnectException) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/clientConnectExceptions/{clientConnectExceptionAddress} | Get a Client Connect Exception object.
*MsgVpnApi* | [**getMsgVpnAclProfileClientConnectExceptions**](docs/MsgVpnApi.md#getMsgVpnAclProfileClientConnectExceptions) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/clientConnectExceptions | Get a list of Client Connect Exception objects.
*MsgVpnApi* | [**getMsgVpnAclProfilePublishException**](docs/MsgVpnApi.md#getMsgVpnAclProfilePublishException) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/publishExceptions/{topicSyntax},{publishExceptionTopic} | Get a Publish Topic Exception object.
*MsgVpnApi* | [**getMsgVpnAclProfilePublishExceptions**](docs/MsgVpnApi.md#getMsgVpnAclProfilePublishExceptions) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/publishExceptions | Get a list of Publish Topic Exception objects.
*MsgVpnApi* | [**getMsgVpnAclProfilePublishTopicException**](docs/MsgVpnApi.md#getMsgVpnAclProfilePublishTopicException) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/publishTopicExceptions/{publishTopicExceptionSyntax},{publishTopicException} | Get a Publish Topic Exception object.
*MsgVpnApi* | [**getMsgVpnAclProfilePublishTopicExceptions**](docs/MsgVpnApi.md#getMsgVpnAclProfilePublishTopicExceptions) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/publishTopicExceptions | Get a list of Publish Topic Exception objects.
*MsgVpnApi* | [**getMsgVpnAclProfileSubscribeException**](docs/MsgVpnApi.md#getMsgVpnAclProfileSubscribeException) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/subscribeExceptions/{topicSyntax},{subscribeExceptionTopic} | Get a Subscribe Topic Exception object.
*MsgVpnApi* | [**getMsgVpnAclProfileSubscribeExceptions**](docs/MsgVpnApi.md#getMsgVpnAclProfileSubscribeExceptions) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/subscribeExceptions | Get a list of Subscribe Topic Exception objects.
*MsgVpnApi* | [**getMsgVpnAclProfileSubscribeShareNameException**](docs/MsgVpnApi.md#getMsgVpnAclProfileSubscribeShareNameException) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/subscribeShareNameExceptions/{subscribeShareNameExceptionSyntax},{subscribeShareNameException} | Get a Subscribe Share Name Exception object.
*MsgVpnApi* | [**getMsgVpnAclProfileSubscribeShareNameExceptions**](docs/MsgVpnApi.md#getMsgVpnAclProfileSubscribeShareNameExceptions) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/subscribeShareNameExceptions | Get a list of Subscribe Share Name Exception objects.
*MsgVpnApi* | [**getMsgVpnAclProfileSubscribeTopicException**](docs/MsgVpnApi.md#getMsgVpnAclProfileSubscribeTopicException) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/subscribeTopicExceptions/{subscribeTopicExceptionSyntax},{subscribeTopicException} | Get a Subscribe Topic Exception object.
*MsgVpnApi* | [**getMsgVpnAclProfileSubscribeTopicExceptions**](docs/MsgVpnApi.md#getMsgVpnAclProfileSubscribeTopicExceptions) | **GET** /msgVpns/{msgVpnName}/aclProfiles/{aclProfileName}/subscribeTopicExceptions | Get a list of Subscribe Topic Exception objects.
*MsgVpnApi* | [**getMsgVpnAclProfiles**](docs/MsgVpnApi.md#getMsgVpnAclProfiles) | **GET** /msgVpns/{msgVpnName}/aclProfiles | Get a list of ACL Profile objects.
*MsgVpnApi* | [**getMsgVpnAuthenticationOauthProvider**](docs/MsgVpnApi.md#getMsgVpnAuthenticationOauthProvider) | **GET** /msgVpns/{msgVpnName}/authenticationOauthProviders/{oauthProviderName} | Get an OAuth Provider object.
*MsgVpnApi* | [**getMsgVpnAuthenticationOauthProviders**](docs/MsgVpnApi.md#getMsgVpnAuthenticationOauthProviders) | **GET** /msgVpns/{msgVpnName}/authenticationOauthProviders | Get a list of OAuth Provider objects.
*MsgVpnApi* | [**getMsgVpnAuthorizationGroup**](docs/MsgVpnApi.md#getMsgVpnAuthorizationGroup) | **GET** /msgVpns/{msgVpnName}/authorizationGroups/{authorizationGroupName} | Get an LDAP Authorization Group object.
*MsgVpnApi* | [**getMsgVpnAuthorizationGroups**](docs/MsgVpnApi.md#getMsgVpnAuthorizationGroups) | **GET** /msgVpns/{msgVpnName}/authorizationGroups | Get a list of LDAP Authorization Group objects.
*MsgVpnApi* | [**getMsgVpnBridge**](docs/MsgVpnApi.md#getMsgVpnBridge) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter} | Get a Bridge object.
*MsgVpnApi* | [**getMsgVpnBridgeLocalSubscription**](docs/MsgVpnApi.md#getMsgVpnBridgeLocalSubscription) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/localSubscriptions/{localSubscriptionTopic} | Get a Bridge Local Subscriptions object.
*MsgVpnApi* | [**getMsgVpnBridgeLocalSubscriptions**](docs/MsgVpnApi.md#getMsgVpnBridgeLocalSubscriptions) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/localSubscriptions | Get a list of Bridge Local Subscriptions objects.
*MsgVpnApi* | [**getMsgVpnBridgeRemoteMsgVpn**](docs/MsgVpnApi.md#getMsgVpnBridgeRemoteMsgVpn) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/remoteMsgVpns/{remoteMsgVpnName},{remoteMsgVpnLocation},{remoteMsgVpnInterface} | Get a Remote Message VPN object.
*MsgVpnApi* | [**getMsgVpnBridgeRemoteMsgVpns**](docs/MsgVpnApi.md#getMsgVpnBridgeRemoteMsgVpns) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/remoteMsgVpns | Get a list of Remote Message VPN objects.
*MsgVpnApi* | [**getMsgVpnBridgeRemoteSubscription**](docs/MsgVpnApi.md#getMsgVpnBridgeRemoteSubscription) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/remoteSubscriptions/{remoteSubscriptionTopic} | Get a Remote Subscription object.
*MsgVpnApi* | [**getMsgVpnBridgeRemoteSubscriptions**](docs/MsgVpnApi.md#getMsgVpnBridgeRemoteSubscriptions) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/remoteSubscriptions | Get a list of Remote Subscription objects.
*MsgVpnApi* | [**getMsgVpnBridgeTlsTrustedCommonName**](docs/MsgVpnApi.md#getMsgVpnBridgeTlsTrustedCommonName) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/tlsTrustedCommonNames/{tlsTrustedCommonName} | Get a Trusted Common Name object.
*MsgVpnApi* | [**getMsgVpnBridgeTlsTrustedCommonNames**](docs/MsgVpnApi.md#getMsgVpnBridgeTlsTrustedCommonNames) | **GET** /msgVpns/{msgVpnName}/bridges/{bridgeName},{bridgeVirtualRouter}/tlsTrustedCommonNames | Get a list of Trusted Common Name objects.
*MsgVpnApi* | [**getMsgVpnBridges**](docs/MsgVpnApi.md#getMsgVpnBridges) | **GET** /msgVpns/{msgVpnName}/bridges | Get a list of Bridge objects.
*MsgVpnApi* | [**getMsgVpnClient**](docs/MsgVpnApi.md#getMsgVpnClient) | **GET** /msgVpns/{msgVpnName}/clients/{clientName} | Get a Client object.
*MsgVpnApi* | [**getMsgVpnClientConnection**](docs/MsgVpnApi.md#getMsgVpnClientConnection) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/connections/{clientAddress} | Get a Client Connection object.
*MsgVpnApi* | [**getMsgVpnClientConnections**](docs/MsgVpnApi.md#getMsgVpnClientConnections) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/connections | Get a list of Client Connection objects.
*MsgVpnApi* | [**getMsgVpnClientProfile**](docs/MsgVpnApi.md#getMsgVpnClientProfile) | **GET** /msgVpns/{msgVpnName}/clientProfiles/{clientProfileName} | Get a Client Profile object.
*MsgVpnApi* | [**getMsgVpnClientProfiles**](docs/MsgVpnApi.md#getMsgVpnClientProfiles) | **GET** /msgVpns/{msgVpnName}/clientProfiles | Get a list of Client Profile objects.
*MsgVpnApi* | [**getMsgVpnClientRxFlow**](docs/MsgVpnApi.md#getMsgVpnClientRxFlow) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/rxFlows/{flowId} | Get a Client Receive Flow object.
*MsgVpnApi* | [**getMsgVpnClientRxFlows**](docs/MsgVpnApi.md#getMsgVpnClientRxFlows) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/rxFlows | Get a list of Client Receive Flow objects.
*MsgVpnApi* | [**getMsgVpnClientSubscription**](docs/MsgVpnApi.md#getMsgVpnClientSubscription) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/subscriptions/{subscriptionTopic} | Get a Client Subscription object.
*MsgVpnApi* | [**getMsgVpnClientSubscriptions**](docs/MsgVpnApi.md#getMsgVpnClientSubscriptions) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/subscriptions | Get a list of Client Subscription objects.
*MsgVpnApi* | [**getMsgVpnClientTransactedSession**](docs/MsgVpnApi.md#getMsgVpnClientTransactedSession) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/transactedSessions/{sessionName} | Get a Client Transacted Session object.
*MsgVpnApi* | [**getMsgVpnClientTransactedSessions**](docs/MsgVpnApi.md#getMsgVpnClientTransactedSessions) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/transactedSessions | Get a list of Client Transacted Session objects.
*MsgVpnApi* | [**getMsgVpnClientTxFlow**](docs/MsgVpnApi.md#getMsgVpnClientTxFlow) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/txFlows/{flowId} | Get a Client Transmit Flow object.
*MsgVpnApi* | [**getMsgVpnClientTxFlows**](docs/MsgVpnApi.md#getMsgVpnClientTxFlows) | **GET** /msgVpns/{msgVpnName}/clients/{clientName}/txFlows | Get a list of Client Transmit Flow objects.
*MsgVpnApi* | [**getMsgVpnClientUsername**](docs/MsgVpnApi.md#getMsgVpnClientUsername) | **GET** /msgVpns/{msgVpnName}/clientUsernames/{clientUsername} | Get a Client Username object.
*MsgVpnApi* | [**getMsgVpnClientUsernames**](docs/MsgVpnApi.md#getMsgVpnClientUsernames) | **GET** /msgVpns/{msgVpnName}/clientUsernames | Get a list of Client Username objects.
*MsgVpnApi* | [**getMsgVpnClients**](docs/MsgVpnApi.md#getMsgVpnClients) | **GET** /msgVpns/{msgVpnName}/clients | Get a list of Client objects.
*MsgVpnApi* | [**getMsgVpnConfigSyncRemoteNode**](docs/MsgVpnApi.md#getMsgVpnConfigSyncRemoteNode) | **GET** /msgVpns/{msgVpnName}/configSyncRemoteNodes/{remoteNodeName} | Get a Config Sync Remote Node object.
*MsgVpnApi* | [**getMsgVpnConfigSyncRemoteNodes**](docs/MsgVpnApi.md#getMsgVpnConfigSyncRemoteNodes) | **GET** /msgVpns/{msgVpnName}/configSyncRemoteNodes | Get a list of Config Sync Remote Node objects.
*MsgVpnApi* | [**getMsgVpnDistributedCache**](docs/MsgVpnApi.md#getMsgVpnDistributedCache) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName} | Get a Distributed Cache object.
*MsgVpnApi* | [**getMsgVpnDistributedCacheCluster**](docs/MsgVpnApi.md#getMsgVpnDistributedCacheCluster) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName} | Get a Cache Cluster object.
*MsgVpnApi* | [**getMsgVpnDistributedCacheClusterGlobalCachingHomeCluster**](docs/MsgVpnApi.md#getMsgVpnDistributedCacheClusterGlobalCachingHomeCluster) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/globalCachingHomeClusters/{homeClusterName} | Get a Home Cache Cluster object.
*MsgVpnApi* | [**getMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefix**](docs/MsgVpnApi.md#getMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefix) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/globalCachingHomeClusters/{homeClusterName}/topicPrefixes/{topicPrefix} | Get a Topic Prefix object.
*MsgVpnApi* | [**getMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixes**](docs/MsgVpnApi.md#getMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixes) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/globalCachingHomeClusters/{homeClusterName}/topicPrefixes | Get a list of Topic Prefix objects.
*MsgVpnApi* | [**getMsgVpnDistributedCacheClusterGlobalCachingHomeClusters**](docs/MsgVpnApi.md#getMsgVpnDistributedCacheClusterGlobalCachingHomeClusters) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/globalCachingHomeClusters | Get a list of Home Cache Cluster objects.
*MsgVpnApi* | [**getMsgVpnDistributedCacheClusterInstance**](docs/MsgVpnApi.md#getMsgVpnDistributedCacheClusterInstance) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName} | Get a Cache Instance object.
*MsgVpnApi* | [**getMsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeCluster**](docs/MsgVpnApi.md#getMsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeCluster) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/remoteGlobalCachingHomeClusters/{homeClusterName} | Get a Remote Home Cache Cluster object.
*MsgVpnApi* | [**getMsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeClusters**](docs/MsgVpnApi.md#getMsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeClusters) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/remoteGlobalCachingHomeClusters | Get a list of Remote Home Cache Cluster objects.
*MsgVpnApi* | [**getMsgVpnDistributedCacheClusterInstanceRemoteTopic**](docs/MsgVpnApi.md#getMsgVpnDistributedCacheClusterInstanceRemoteTopic) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/remoteTopics/{topic} | Get a Remote Topic object.
*MsgVpnApi* | [**getMsgVpnDistributedCacheClusterInstanceRemoteTopics**](docs/MsgVpnApi.md#getMsgVpnDistributedCacheClusterInstanceRemoteTopics) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances/{instanceName}/remoteTopics | Get a list of Remote Topic objects.
*MsgVpnApi* | [**getMsgVpnDistributedCacheClusterInstances**](docs/MsgVpnApi.md#getMsgVpnDistributedCacheClusterInstances) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/instances | Get a list of Cache Instance objects.
*MsgVpnApi* | [**getMsgVpnDistributedCacheClusterTopic**](docs/MsgVpnApi.md#getMsgVpnDistributedCacheClusterTopic) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/topics/{topic} | Get a Topic object.
*MsgVpnApi* | [**getMsgVpnDistributedCacheClusterTopics**](docs/MsgVpnApi.md#getMsgVpnDistributedCacheClusterTopics) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters/{clusterName}/topics | Get a list of Topic objects.
*MsgVpnApi* | [**getMsgVpnDistributedCacheClusters**](docs/MsgVpnApi.md#getMsgVpnDistributedCacheClusters) | **GET** /msgVpns/{msgVpnName}/distributedCaches/{cacheName}/clusters | Get a list of Cache Cluster objects.
*MsgVpnApi* | [**getMsgVpnDistributedCaches**](docs/MsgVpnApi.md#getMsgVpnDistributedCaches) | **GET** /msgVpns/{msgVpnName}/distributedCaches | Get a list of Distributed Cache objects.
*MsgVpnApi* | [**getMsgVpnDmrBridge**](docs/MsgVpnApi.md#getMsgVpnDmrBridge) | **GET** /msgVpns/{msgVpnName}/dmrBridges/{remoteNodeName} | Get a DMR Bridge object.
*MsgVpnApi* | [**getMsgVpnDmrBridges**](docs/MsgVpnApi.md#getMsgVpnDmrBridges) | **GET** /msgVpns/{msgVpnName}/dmrBridges | Get a list of DMR Bridge objects.
*MsgVpnApi* | [**getMsgVpnJndiConnectionFactories**](docs/MsgVpnApi.md#getMsgVpnJndiConnectionFactories) | **GET** /msgVpns/{msgVpnName}/jndiConnectionFactories | Get a list of JNDI Connection Factory objects.
*MsgVpnApi* | [**getMsgVpnJndiConnectionFactory**](docs/MsgVpnApi.md#getMsgVpnJndiConnectionFactory) | **GET** /msgVpns/{msgVpnName}/jndiConnectionFactories/{connectionFactoryName} | Get a JNDI Connection Factory object.
*MsgVpnApi* | [**getMsgVpnJndiQueue**](docs/MsgVpnApi.md#getMsgVpnJndiQueue) | **GET** /msgVpns/{msgVpnName}/jndiQueues/{queueName} | Get a JNDI Queue object.
*MsgVpnApi* | [**getMsgVpnJndiQueues**](docs/MsgVpnApi.md#getMsgVpnJndiQueues) | **GET** /msgVpns/{msgVpnName}/jndiQueues | Get a list of JNDI Queue objects.
*MsgVpnApi* | [**getMsgVpnJndiTopic**](docs/MsgVpnApi.md#getMsgVpnJndiTopic) | **GET** /msgVpns/{msgVpnName}/jndiTopics/{topicName} | Get a JNDI Topic object.
*MsgVpnApi* | [**getMsgVpnJndiTopics**](docs/MsgVpnApi.md#getMsgVpnJndiTopics) | **GET** /msgVpns/{msgVpnName}/jndiTopics | Get a list of JNDI Topic objects.
*MsgVpnApi* | [**getMsgVpnMqttRetainCache**](docs/MsgVpnApi.md#getMsgVpnMqttRetainCache) | **GET** /msgVpns/{msgVpnName}/mqttRetainCaches/{cacheName} | Get an MQTT Retain Cache object.
*MsgVpnApi* | [**getMsgVpnMqttRetainCaches**](docs/MsgVpnApi.md#getMsgVpnMqttRetainCaches) | **GET** /msgVpns/{msgVpnName}/mqttRetainCaches | Get a list of MQTT Retain Cache objects.
*MsgVpnApi* | [**getMsgVpnMqttSession**](docs/MsgVpnApi.md#getMsgVpnMqttSession) | **GET** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter} | Get an MQTT Session object.
*MsgVpnApi* | [**getMsgVpnMqttSessionSubscription**](docs/MsgVpnApi.md#getMsgVpnMqttSessionSubscription) | **GET** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter}/subscriptions/{subscriptionTopic} | Get a Subscription object.
*MsgVpnApi* | [**getMsgVpnMqttSessionSubscriptions**](docs/MsgVpnApi.md#getMsgVpnMqttSessionSubscriptions) | **GET** /msgVpns/{msgVpnName}/mqttSessions/{mqttSessionClientId},{mqttSessionVirtualRouter}/subscriptions | Get a list of Subscription objects.
*MsgVpnApi* | [**getMsgVpnMqttSessions**](docs/MsgVpnApi.md#getMsgVpnMqttSessions) | **GET** /msgVpns/{msgVpnName}/mqttSessions | Get a list of MQTT Session objects.
*MsgVpnApi* | [**getMsgVpnQueue**](docs/MsgVpnApi.md#getMsgVpnQueue) | **GET** /msgVpns/{msgVpnName}/queues/{queueName} | Get a Queue object.
*MsgVpnApi* | [**getMsgVpnQueueMsg**](docs/MsgVpnApi.md#getMsgVpnQueueMsg) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/msgs/{msgId} | Get a Queue Message object.
*MsgVpnApi* | [**getMsgVpnQueueMsgs**](docs/MsgVpnApi.md#getMsgVpnQueueMsgs) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/msgs | Get a list of Queue Message objects.
*MsgVpnApi* | [**getMsgVpnQueuePriorities**](docs/MsgVpnApi.md#getMsgVpnQueuePriorities) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/priorities | Get a list of Queue Priority objects.
*MsgVpnApi* | [**getMsgVpnQueuePriority**](docs/MsgVpnApi.md#getMsgVpnQueuePriority) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/priorities/{priority} | Get a Queue Priority object.
*MsgVpnApi* | [**getMsgVpnQueueSubscription**](docs/MsgVpnApi.md#getMsgVpnQueueSubscription) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/subscriptions/{subscriptionTopic} | Get a Queue Subscription object.
*MsgVpnApi* | [**getMsgVpnQueueSubscriptions**](docs/MsgVpnApi.md#getMsgVpnQueueSubscriptions) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/subscriptions | Get a list of Queue Subscription objects.
*MsgVpnApi* | [**getMsgVpnQueueTemplate**](docs/MsgVpnApi.md#getMsgVpnQueueTemplate) | **GET** /msgVpns/{msgVpnName}/queueTemplates/{queueTemplateName} | Get a Queue Template object.
*MsgVpnApi* | [**getMsgVpnQueueTemplates**](docs/MsgVpnApi.md#getMsgVpnQueueTemplates) | **GET** /msgVpns/{msgVpnName}/queueTemplates | Get a list of Queue Template objects.
*MsgVpnApi* | [**getMsgVpnQueueTxFlow**](docs/MsgVpnApi.md#getMsgVpnQueueTxFlow) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/txFlows/{flowId} | Get a Queue Transmit Flow object.
*MsgVpnApi* | [**getMsgVpnQueueTxFlows**](docs/MsgVpnApi.md#getMsgVpnQueueTxFlows) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/txFlows | Get a list of Queue Transmit Flow objects.
*MsgVpnApi* | [**getMsgVpnQueues**](docs/MsgVpnApi.md#getMsgVpnQueues) | **GET** /msgVpns/{msgVpnName}/queues | Get a list of Queue objects.
*MsgVpnApi* | [**getMsgVpnReplayLog**](docs/MsgVpnApi.md#getMsgVpnReplayLog) | **GET** /msgVpns/{msgVpnName}/replayLogs/{replayLogName} | Get a Replay Log object.
*MsgVpnApi* | [**getMsgVpnReplayLogMsg**](docs/MsgVpnApi.md#getMsgVpnReplayLogMsg) | **GET** /msgVpns/{msgVpnName}/replayLogs/{replayLogName}/msgs/{msgId} | Get a Message object.
*MsgVpnApi* | [**getMsgVpnReplayLogMsgs**](docs/MsgVpnApi.md#getMsgVpnReplayLogMsgs) | **GET** /msgVpns/{msgVpnName}/replayLogs/{replayLogName}/msgs | Get a list of Message objects.
*MsgVpnApi* | [**getMsgVpnReplayLogs**](docs/MsgVpnApi.md#getMsgVpnReplayLogs) | **GET** /msgVpns/{msgVpnName}/replayLogs | Get a list of Replay Log objects.
*MsgVpnApi* | [**getMsgVpnReplicatedTopic**](docs/MsgVpnApi.md#getMsgVpnReplicatedTopic) | **GET** /msgVpns/{msgVpnName}/replicatedTopics/{replicatedTopic} | Get a Replicated Topic object.
*MsgVpnApi* | [**getMsgVpnReplicatedTopics**](docs/MsgVpnApi.md#getMsgVpnReplicatedTopics) | **GET** /msgVpns/{msgVpnName}/replicatedTopics | Get a list of Replicated Topic objects.
*MsgVpnApi* | [**getMsgVpnRestDeliveryPoint**](docs/MsgVpnApi.md#getMsgVpnRestDeliveryPoint) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName} | Get a REST Delivery Point object.
*MsgVpnApi* | [**getMsgVpnRestDeliveryPointQueueBinding**](docs/MsgVpnApi.md#getMsgVpnRestDeliveryPointQueueBinding) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/queueBindings/{queueBindingName} | Get a Queue Binding object.
*MsgVpnApi* | [**getMsgVpnRestDeliveryPointQueueBindingRequestHeader**](docs/MsgVpnApi.md#getMsgVpnRestDeliveryPointQueueBindingRequestHeader) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/queueBindings/{queueBindingName}/requestHeaders/{headerName} | Get a Request Header object.
*MsgVpnApi* | [**getMsgVpnRestDeliveryPointQueueBindingRequestHeaders**](docs/MsgVpnApi.md#getMsgVpnRestDeliveryPointQueueBindingRequestHeaders) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/queueBindings/{queueBindingName}/requestHeaders | Get a list of Request Header objects.
*MsgVpnApi* | [**getMsgVpnRestDeliveryPointQueueBindings**](docs/MsgVpnApi.md#getMsgVpnRestDeliveryPointQueueBindings) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/queueBindings | Get a list of Queue Binding objects.
*MsgVpnApi* | [**getMsgVpnRestDeliveryPointRestConsumer**](docs/MsgVpnApi.md#getMsgVpnRestDeliveryPointRestConsumer) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers/{restConsumerName} | Get a REST Consumer object.
*MsgVpnApi* | [**getMsgVpnRestDeliveryPointRestConsumerOauthJwtClaim**](docs/MsgVpnApi.md#getMsgVpnRestDeliveryPointRestConsumerOauthJwtClaim) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers/{restConsumerName}/oauthJwtClaims/{oauthJwtClaimName} | Get a Claim object.
*MsgVpnApi* | [**getMsgVpnRestDeliveryPointRestConsumerOauthJwtClaims**](docs/MsgVpnApi.md#getMsgVpnRestDeliveryPointRestConsumerOauthJwtClaims) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers/{restConsumerName}/oauthJwtClaims | Get a list of Claim objects.
*MsgVpnApi* | [**getMsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonName**](docs/MsgVpnApi.md#getMsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonName) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers/{restConsumerName}/tlsTrustedCommonNames/{tlsTrustedCommonName} | Get a Trusted Common Name object.
*MsgVpnApi* | [**getMsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNames**](docs/MsgVpnApi.md#getMsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNames) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers/{restConsumerName}/tlsTrustedCommonNames | Get a list of Trusted Common Name objects.
*MsgVpnApi* | [**getMsgVpnRestDeliveryPointRestConsumers**](docs/MsgVpnApi.md#getMsgVpnRestDeliveryPointRestConsumers) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers | Get a list of REST Consumer objects.
*MsgVpnApi* | [**getMsgVpnRestDeliveryPoints**](docs/MsgVpnApi.md#getMsgVpnRestDeliveryPoints) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints | Get a list of REST Delivery Point objects.
*MsgVpnApi* | [**getMsgVpnTopicEndpoint**](docs/MsgVpnApi.md#getMsgVpnTopicEndpoint) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName} | Get a Topic Endpoint object.
*MsgVpnApi* | [**getMsgVpnTopicEndpointMsg**](docs/MsgVpnApi.md#getMsgVpnTopicEndpointMsg) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/msgs/{msgId} | Get a Topic Endpoint Message object.
*MsgVpnApi* | [**getMsgVpnTopicEndpointMsgs**](docs/MsgVpnApi.md#getMsgVpnTopicEndpointMsgs) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/msgs | Get a list of Topic Endpoint Message objects.
*MsgVpnApi* | [**getMsgVpnTopicEndpointPriorities**](docs/MsgVpnApi.md#getMsgVpnTopicEndpointPriorities) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/priorities | Get a list of Topic Endpoint Priority objects.
*MsgVpnApi* | [**getMsgVpnTopicEndpointPriority**](docs/MsgVpnApi.md#getMsgVpnTopicEndpointPriority) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/priorities/{priority} | Get a Topic Endpoint Priority object.
*MsgVpnApi* | [**getMsgVpnTopicEndpointTemplate**](docs/MsgVpnApi.md#getMsgVpnTopicEndpointTemplate) | **GET** /msgVpns/{msgVpnName}/topicEndpointTemplates/{topicEndpointTemplateName} | Get a Topic Endpoint Template object.
*MsgVpnApi* | [**getMsgVpnTopicEndpointTemplates**](docs/MsgVpnApi.md#getMsgVpnTopicEndpointTemplates) | **GET** /msgVpns/{msgVpnName}/topicEndpointTemplates | Get a list of Topic Endpoint Template objects.
*MsgVpnApi* | [**getMsgVpnTopicEndpointTxFlow**](docs/MsgVpnApi.md#getMsgVpnTopicEndpointTxFlow) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/txFlows/{flowId} | Get a Topic Endpoint Transmit Flow object.
*MsgVpnApi* | [**getMsgVpnTopicEndpointTxFlows**](docs/MsgVpnApi.md#getMsgVpnTopicEndpointTxFlows) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/txFlows | Get a list of Topic Endpoint Transmit Flow objects.
*MsgVpnApi* | [**getMsgVpnTopicEndpoints**](docs/MsgVpnApi.md#getMsgVpnTopicEndpoints) | **GET** /msgVpns/{msgVpnName}/topicEndpoints | Get a list of Topic Endpoint objects.
*MsgVpnApi* | [**getMsgVpnTransaction**](docs/MsgVpnApi.md#getMsgVpnTransaction) | **GET** /msgVpns/{msgVpnName}/transactions/{xid} | Get a Replicated Local Transaction or XA Transaction object.
*MsgVpnApi* | [**getMsgVpnTransactionConsumerMsg**](docs/MsgVpnApi.md#getMsgVpnTransactionConsumerMsg) | **GET** /msgVpns/{msgVpnName}/transactions/{xid}/consumerMsgs/{msgId} | Get a Transaction Consumer Message object.
*MsgVpnApi* | [**getMsgVpnTransactionConsumerMsgs**](docs/MsgVpnApi.md#getMsgVpnTransactionConsumerMsgs) | **GET** /msgVpns/{msgVpnName}/transactions/{xid}/consumerMsgs | Get a list of Transaction Consumer Message objects.
*MsgVpnApi* | [**getMsgVpnTransactionPublisherMsg**](docs/MsgVpnApi.md#getMsgVpnTransactionPublisherMsg) | **GET** /msgVpns/{msgVpnName}/transactions/{xid}/publisherMsgs/{msgId} | Get a Transaction Publisher Message object.
*MsgVpnApi* | [**getMsgVpnTransactionPublisherMsgs**](docs/MsgVpnApi.md#getMsgVpnTransactionPublisherMsgs) | **GET** /msgVpns/{msgVpnName}/transactions/{xid}/publisherMsgs | Get a list of Transaction Publisher Message objects.
*MsgVpnApi* | [**getMsgVpnTransactions**](docs/MsgVpnApi.md#getMsgVpnTransactions) | **GET** /msgVpns/{msgVpnName}/transactions | Get a list of Replicated Local Transaction or XA Transaction objects.
*MsgVpnApi* | [**getMsgVpns**](docs/MsgVpnApi.md#getMsgVpns) | **GET** /msgVpns | Get a list of Message VPN objects.
*QueueApi* | [**getMsgVpnQueue**](docs/QueueApi.md#getMsgVpnQueue) | **GET** /msgVpns/{msgVpnName}/queues/{queueName} | Get a Queue object.
*QueueApi* | [**getMsgVpnQueueMsg**](docs/QueueApi.md#getMsgVpnQueueMsg) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/msgs/{msgId} | Get a Queue Message object.
*QueueApi* | [**getMsgVpnQueueMsgs**](docs/QueueApi.md#getMsgVpnQueueMsgs) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/msgs | Get a list of Queue Message objects.
*QueueApi* | [**getMsgVpnQueuePriorities**](docs/QueueApi.md#getMsgVpnQueuePriorities) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/priorities | Get a list of Queue Priority objects.
*QueueApi* | [**getMsgVpnQueuePriority**](docs/QueueApi.md#getMsgVpnQueuePriority) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/priorities/{priority} | Get a Queue Priority object.
*QueueApi* | [**getMsgVpnQueueSubscription**](docs/QueueApi.md#getMsgVpnQueueSubscription) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/subscriptions/{subscriptionTopic} | Get a Queue Subscription object.
*QueueApi* | [**getMsgVpnQueueSubscriptions**](docs/QueueApi.md#getMsgVpnQueueSubscriptions) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/subscriptions | Get a list of Queue Subscription objects.
*QueueApi* | [**getMsgVpnQueueTxFlow**](docs/QueueApi.md#getMsgVpnQueueTxFlow) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/txFlows/{flowId} | Get a Queue Transmit Flow object.
*QueueApi* | [**getMsgVpnQueueTxFlows**](docs/QueueApi.md#getMsgVpnQueueTxFlows) | **GET** /msgVpns/{msgVpnName}/queues/{queueName}/txFlows | Get a list of Queue Transmit Flow objects.
*QueueApi* | [**getMsgVpnQueues**](docs/QueueApi.md#getMsgVpnQueues) | **GET** /msgVpns/{msgVpnName}/queues | Get a list of Queue objects.
*QueueTemplateApi* | [**getMsgVpnQueueTemplate**](docs/QueueTemplateApi.md#getMsgVpnQueueTemplate) | **GET** /msgVpns/{msgVpnName}/queueTemplates/{queueTemplateName} | Get a Queue Template object.
*QueueTemplateApi* | [**getMsgVpnQueueTemplates**](docs/QueueTemplateApi.md#getMsgVpnQueueTemplates) | **GET** /msgVpns/{msgVpnName}/queueTemplates | Get a list of Queue Template objects.
*ReplayLogApi* | [**getMsgVpnReplayLog**](docs/ReplayLogApi.md#getMsgVpnReplayLog) | **GET** /msgVpns/{msgVpnName}/replayLogs/{replayLogName} | Get a Replay Log object.
*ReplayLogApi* | [**getMsgVpnReplayLogMsg**](docs/ReplayLogApi.md#getMsgVpnReplayLogMsg) | **GET** /msgVpns/{msgVpnName}/replayLogs/{replayLogName}/msgs/{msgId} | Get a Message object.
*ReplayLogApi* | [**getMsgVpnReplayLogMsgs**](docs/ReplayLogApi.md#getMsgVpnReplayLogMsgs) | **GET** /msgVpns/{msgVpnName}/replayLogs/{replayLogName}/msgs | Get a list of Message objects.
*ReplayLogApi* | [**getMsgVpnReplayLogs**](docs/ReplayLogApi.md#getMsgVpnReplayLogs) | **GET** /msgVpns/{msgVpnName}/replayLogs | Get a list of Replay Log objects.
*ReplicatedTopicApi* | [**getMsgVpnReplicatedTopic**](docs/ReplicatedTopicApi.md#getMsgVpnReplicatedTopic) | **GET** /msgVpns/{msgVpnName}/replicatedTopics/{replicatedTopic} | Get a Replicated Topic object.
*ReplicatedTopicApi* | [**getMsgVpnReplicatedTopics**](docs/ReplicatedTopicApi.md#getMsgVpnReplicatedTopics) | **GET** /msgVpns/{msgVpnName}/replicatedTopics | Get a list of Replicated Topic objects.
*RestDeliveryPointApi* | [**getMsgVpnRestDeliveryPoint**](docs/RestDeliveryPointApi.md#getMsgVpnRestDeliveryPoint) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName} | Get a REST Delivery Point object.
*RestDeliveryPointApi* | [**getMsgVpnRestDeliveryPointQueueBinding**](docs/RestDeliveryPointApi.md#getMsgVpnRestDeliveryPointQueueBinding) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/queueBindings/{queueBindingName} | Get a Queue Binding object.
*RestDeliveryPointApi* | [**getMsgVpnRestDeliveryPointQueueBindingRequestHeader**](docs/RestDeliveryPointApi.md#getMsgVpnRestDeliveryPointQueueBindingRequestHeader) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/queueBindings/{queueBindingName}/requestHeaders/{headerName} | Get a Request Header object.
*RestDeliveryPointApi* | [**getMsgVpnRestDeliveryPointQueueBindingRequestHeaders**](docs/RestDeliveryPointApi.md#getMsgVpnRestDeliveryPointQueueBindingRequestHeaders) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/queueBindings/{queueBindingName}/requestHeaders | Get a list of Request Header objects.
*RestDeliveryPointApi* | [**getMsgVpnRestDeliveryPointQueueBindings**](docs/RestDeliveryPointApi.md#getMsgVpnRestDeliveryPointQueueBindings) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/queueBindings | Get a list of Queue Binding objects.
*RestDeliveryPointApi* | [**getMsgVpnRestDeliveryPointRestConsumer**](docs/RestDeliveryPointApi.md#getMsgVpnRestDeliveryPointRestConsumer) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers/{restConsumerName} | Get a REST Consumer object.
*RestDeliveryPointApi* | [**getMsgVpnRestDeliveryPointRestConsumerOauthJwtClaim**](docs/RestDeliveryPointApi.md#getMsgVpnRestDeliveryPointRestConsumerOauthJwtClaim) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers/{restConsumerName}/oauthJwtClaims/{oauthJwtClaimName} | Get a Claim object.
*RestDeliveryPointApi* | [**getMsgVpnRestDeliveryPointRestConsumerOauthJwtClaims**](docs/RestDeliveryPointApi.md#getMsgVpnRestDeliveryPointRestConsumerOauthJwtClaims) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers/{restConsumerName}/oauthJwtClaims | Get a list of Claim objects.
*RestDeliveryPointApi* | [**getMsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonName**](docs/RestDeliveryPointApi.md#getMsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonName) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers/{restConsumerName}/tlsTrustedCommonNames/{tlsTrustedCommonName} | Get a Trusted Common Name object.
*RestDeliveryPointApi* | [**getMsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNames**](docs/RestDeliveryPointApi.md#getMsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNames) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers/{restConsumerName}/tlsTrustedCommonNames | Get a list of Trusted Common Name objects.
*RestDeliveryPointApi* | [**getMsgVpnRestDeliveryPointRestConsumers**](docs/RestDeliveryPointApi.md#getMsgVpnRestDeliveryPointRestConsumers) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints/{restDeliveryPointName}/restConsumers | Get a list of REST Consumer objects.
*RestDeliveryPointApi* | [**getMsgVpnRestDeliveryPoints**](docs/RestDeliveryPointApi.md#getMsgVpnRestDeliveryPoints) | **GET** /msgVpns/{msgVpnName}/restDeliveryPoints | Get a list of REST Delivery Point objects.
*SessionApi* | [**getSession**](docs/SessionApi.md#getSession) | **GET** /sessions/{sessionUsername},{sessionId} | Get a Session object.
*SessionApi* | [**getSessions**](docs/SessionApi.md#getSessions) | **GET** /sessions | Get a list of Session objects.
*StandardDomainCertAuthorityApi* | [**getStandardDomainCertAuthorities**](docs/StandardDomainCertAuthorityApi.md#getStandardDomainCertAuthorities) | **GET** /standardDomainCertAuthorities | Get a list of Standard Domain Certificate Authority objects.
*StandardDomainCertAuthorityApi* | [**getStandardDomainCertAuthority**](docs/StandardDomainCertAuthorityApi.md#getStandardDomainCertAuthority) | **GET** /standardDomainCertAuthorities/{certAuthorityName} | Get a Standard Domain Certificate Authority object.
*TopicEndpointApi* | [**getMsgVpnTopicEndpoint**](docs/TopicEndpointApi.md#getMsgVpnTopicEndpoint) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName} | Get a Topic Endpoint object.
*TopicEndpointApi* | [**getMsgVpnTopicEndpointMsg**](docs/TopicEndpointApi.md#getMsgVpnTopicEndpointMsg) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/msgs/{msgId} | Get a Topic Endpoint Message object.
*TopicEndpointApi* | [**getMsgVpnTopicEndpointMsgs**](docs/TopicEndpointApi.md#getMsgVpnTopicEndpointMsgs) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/msgs | Get a list of Topic Endpoint Message objects.
*TopicEndpointApi* | [**getMsgVpnTopicEndpointPriorities**](docs/TopicEndpointApi.md#getMsgVpnTopicEndpointPriorities) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/priorities | Get a list of Topic Endpoint Priority objects.
*TopicEndpointApi* | [**getMsgVpnTopicEndpointPriority**](docs/TopicEndpointApi.md#getMsgVpnTopicEndpointPriority) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/priorities/{priority} | Get a Topic Endpoint Priority object.
*TopicEndpointApi* | [**getMsgVpnTopicEndpointTxFlow**](docs/TopicEndpointApi.md#getMsgVpnTopicEndpointTxFlow) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/txFlows/{flowId} | Get a Topic Endpoint Transmit Flow object.
*TopicEndpointApi* | [**getMsgVpnTopicEndpointTxFlows**](docs/TopicEndpointApi.md#getMsgVpnTopicEndpointTxFlows) | **GET** /msgVpns/{msgVpnName}/topicEndpoints/{topicEndpointName}/txFlows | Get a list of Topic Endpoint Transmit Flow objects.
*TopicEndpointApi* | [**getMsgVpnTopicEndpoints**](docs/TopicEndpointApi.md#getMsgVpnTopicEndpoints) | **GET** /msgVpns/{msgVpnName}/topicEndpoints | Get a list of Topic Endpoint objects.
*TopicEndpointTemplateApi* | [**getMsgVpnTopicEndpointTemplate**](docs/TopicEndpointTemplateApi.md#getMsgVpnTopicEndpointTemplate) | **GET** /msgVpns/{msgVpnName}/topicEndpointTemplates/{topicEndpointTemplateName} | Get a Topic Endpoint Template object.
*TopicEndpointTemplateApi* | [**getMsgVpnTopicEndpointTemplates**](docs/TopicEndpointTemplateApi.md#getMsgVpnTopicEndpointTemplates) | **GET** /msgVpns/{msgVpnName}/topicEndpointTemplates | Get a list of Topic Endpoint Template objects.
*TransactionApi* | [**getMsgVpnTransaction**](docs/TransactionApi.md#getMsgVpnTransaction) | **GET** /msgVpns/{msgVpnName}/transactions/{xid} | Get a Replicated Local Transaction or XA Transaction object.
*TransactionApi* | [**getMsgVpnTransactionConsumerMsg**](docs/TransactionApi.md#getMsgVpnTransactionConsumerMsg) | **GET** /msgVpns/{msgVpnName}/transactions/{xid}/consumerMsgs/{msgId} | Get a Transaction Consumer Message object.
*TransactionApi* | [**getMsgVpnTransactionConsumerMsgs**](docs/TransactionApi.md#getMsgVpnTransactionConsumerMsgs) | **GET** /msgVpns/{msgVpnName}/transactions/{xid}/consumerMsgs | Get a list of Transaction Consumer Message objects.
*TransactionApi* | [**getMsgVpnTransactionPublisherMsg**](docs/TransactionApi.md#getMsgVpnTransactionPublisherMsg) | **GET** /msgVpns/{msgVpnName}/transactions/{xid}/publisherMsgs/{msgId} | Get a Transaction Publisher Message object.
*TransactionApi* | [**getMsgVpnTransactionPublisherMsgs**](docs/TransactionApi.md#getMsgVpnTransactionPublisherMsgs) | **GET** /msgVpns/{msgVpnName}/transactions/{xid}/publisherMsgs | Get a list of Transaction Publisher Message objects.
*TransactionApi* | [**getMsgVpnTransactions**](docs/TransactionApi.md#getMsgVpnTransactions) | **GET** /msgVpns/{msgVpnName}/transactions | Get a list of Replicated Local Transaction or XA Transaction objects.
*VirtualHostnameApi* | [**getVirtualHostname**](docs/VirtualHostnameApi.md#getVirtualHostname) | **GET** /virtualHostnames/{virtualHostname} | Get a Virtual Hostname object.
*VirtualHostnameApi* | [**getVirtualHostnames**](docs/VirtualHostnameApi.md#getVirtualHostnames) | **GET** /virtualHostnames | Get a list of Virtual Hostname objects.


## Documentation for Models

 - [About](docs/About.md)
 - [AboutApi](docs/AboutApi.md)
 - [AboutApiCollections](docs/AboutApiCollections.md)
 - [AboutApiLinks](docs/AboutApiLinks.md)
 - [AboutApiResponse](docs/AboutApiResponse.md)
 - [AboutCollections](docs/AboutCollections.md)
 - [AboutLinks](docs/AboutLinks.md)
 - [AboutResponse](docs/AboutResponse.md)
 - [AboutUser](docs/AboutUser.md)
 - [AboutUserCollections](docs/AboutUserCollections.md)
 - [AboutUserCollectionsMsgvpns](docs/AboutUserCollectionsMsgvpns.md)
 - [AboutUserLinks](docs/AboutUserLinks.md)
 - [AboutUserMsgVpn](docs/AboutUserMsgVpn.md)
 - [AboutUserMsgVpnCollections](docs/AboutUserMsgVpnCollections.md)
 - [AboutUserMsgVpnLinks](docs/AboutUserMsgVpnLinks.md)
 - [AboutUserMsgVpnResponse](docs/AboutUserMsgVpnResponse.md)
 - [AboutUserMsgVpnsResponse](docs/AboutUserMsgVpnsResponse.md)
 - [AboutUserResponse](docs/AboutUserResponse.md)
 - [Broker](docs/Broker.md)
 - [BrokerCollections](docs/BrokerCollections.md)
 - [BrokerCollectionsCertauthorities](docs/BrokerCollectionsCertauthorities.md)
 - [BrokerCollectionsClientcertauthorities](docs/BrokerCollectionsClientcertauthorities.md)
 - [BrokerCollectionsConfigsynclocaldatabaserows](docs/BrokerCollectionsConfigsynclocaldatabaserows.md)
 - [BrokerCollectionsDmrclusters](docs/BrokerCollectionsDmrclusters.md)
 - [BrokerCollectionsDomaincertauthorities](docs/BrokerCollectionsDomaincertauthorities.md)
 - [BrokerCollectionsGuaranteedmsgingexternaldiskarrays](docs/BrokerCollectionsGuaranteedmsgingexternaldiskarrays.md)
 - [BrokerCollectionsMsgvpns](docs/BrokerCollectionsMsgvpns.md)
 - [BrokerCollectionsSessions](docs/BrokerCollectionsSessions.md)
 - [BrokerCollectionsStandarddomaincertauthorities](docs/BrokerCollectionsStandarddomaincertauthorities.md)
 - [BrokerCollectionsVirtualhostnames](docs/BrokerCollectionsVirtualhostnames.md)
 - [BrokerLinks](docs/BrokerLinks.md)
 - [BrokerResponse](docs/BrokerResponse.md)
 - [CertAuthoritiesResponse](docs/CertAuthoritiesResponse.md)
 - [CertAuthority](docs/CertAuthority.md)
 - [CertAuthorityCollections](docs/CertAuthorityCollections.md)
 - [CertAuthorityCollectionsOcsptlstrustedcommonnames](docs/CertAuthorityCollectionsOcsptlstrustedcommonnames.md)
 - [CertAuthorityLinks](docs/CertAuthorityLinks.md)
 - [CertAuthorityOcspTlsTrustedCommonName](docs/CertAuthorityOcspTlsTrustedCommonName.md)
 - [CertAuthorityOcspTlsTrustedCommonNameCollections](docs/CertAuthorityOcspTlsTrustedCommonNameCollections.md)
 - [CertAuthorityOcspTlsTrustedCommonNameLinks](docs/CertAuthorityOcspTlsTrustedCommonNameLinks.md)
 - [CertAuthorityOcspTlsTrustedCommonNameResponse](docs/CertAuthorityOcspTlsTrustedCommonNameResponse.md)
 - [CertAuthorityOcspTlsTrustedCommonNamesResponse](docs/CertAuthorityOcspTlsTrustedCommonNamesResponse.md)
 - [CertAuthorityResponse](docs/CertAuthorityResponse.md)
 - [ClientCertAuthoritiesResponse](docs/ClientCertAuthoritiesResponse.md)
 - [ClientCertAuthority](docs/ClientCertAuthority.md)
 - [ClientCertAuthorityCollections](docs/ClientCertAuthorityCollections.md)
 - [ClientCertAuthorityCollectionsOcsptlstrustedcommonnames](docs/ClientCertAuthorityCollectionsOcsptlstrustedcommonnames.md)
 - [ClientCertAuthorityLinks](docs/ClientCertAuthorityLinks.md)
 - [ClientCertAuthorityOcspTlsTrustedCommonName](docs/ClientCertAuthorityOcspTlsTrustedCommonName.md)
 - [ClientCertAuthorityOcspTlsTrustedCommonNameCollections](docs/ClientCertAuthorityOcspTlsTrustedCommonNameCollections.md)
 - [ClientCertAuthorityOcspTlsTrustedCommonNameLinks](docs/ClientCertAuthorityOcspTlsTrustedCommonNameLinks.md)
 - [ClientCertAuthorityOcspTlsTrustedCommonNameResponse](docs/ClientCertAuthorityOcspTlsTrustedCommonNameResponse.md)
 - [ClientCertAuthorityOcspTlsTrustedCommonNamesResponse](docs/ClientCertAuthorityOcspTlsTrustedCommonNamesResponse.md)
 - [ClientCertAuthorityResponse](docs/ClientCertAuthorityResponse.md)
 - [ConfigSyncLocalDatabaseRow](docs/ConfigSyncLocalDatabaseRow.md)
 - [ConfigSyncLocalDatabaseRowCollections](docs/ConfigSyncLocalDatabaseRowCollections.md)
 - [ConfigSyncLocalDatabaseRowLinks](docs/ConfigSyncLocalDatabaseRowLinks.md)
 - [ConfigSyncLocalDatabaseRowResponse](docs/ConfigSyncLocalDatabaseRowResponse.md)
 - [ConfigSyncLocalDatabaseRowsResponse](docs/ConfigSyncLocalDatabaseRowsResponse.md)
 - [DmrCluster](docs/DmrCluster.md)
 - [DmrClusterCollections](docs/DmrClusterCollections.md)
 - [DmrClusterCollectionsLinks](docs/DmrClusterCollectionsLinks.md)
 - [DmrClusterCollectionsTopologyissues](docs/DmrClusterCollectionsTopologyissues.md)
 - [DmrClusterLink](docs/DmrClusterLink.md)
 - [DmrClusterLinkChannel](docs/DmrClusterLinkChannel.md)
 - [DmrClusterLinkChannelCollections](docs/DmrClusterLinkChannelCollections.md)
 - [DmrClusterLinkChannelLinks](docs/DmrClusterLinkChannelLinks.md)
 - [DmrClusterLinkChannelResponse](docs/DmrClusterLinkChannelResponse.md)
 - [DmrClusterLinkChannelsResponse](docs/DmrClusterLinkChannelsResponse.md)
 - [DmrClusterLinkCollections](docs/DmrClusterLinkCollections.md)
 - [DmrClusterLinkCollectionsChannels](docs/DmrClusterLinkCollectionsChannels.md)
 - [DmrClusterLinkCollectionsRemoteaddresses](docs/DmrClusterLinkCollectionsRemoteaddresses.md)
 - [DmrClusterLinkCollectionsTlstrustedcommonnames](docs/DmrClusterLinkCollectionsTlstrustedcommonnames.md)
 - [DmrClusterLinkLinks](docs/DmrClusterLinkLinks.md)
 - [DmrClusterLinkRemoteAddress](docs/DmrClusterLinkRemoteAddress.md)
 - [DmrClusterLinkRemoteAddressCollections](docs/DmrClusterLinkRemoteAddressCollections.md)
 - [DmrClusterLinkRemoteAddressLinks](docs/DmrClusterLinkRemoteAddressLinks.md)
 - [DmrClusterLinkRemoteAddressResponse](docs/DmrClusterLinkRemoteAddressResponse.md)
 - [DmrClusterLinkRemoteAddressesResponse](docs/DmrClusterLinkRemoteAddressesResponse.md)
 - [DmrClusterLinkResponse](docs/DmrClusterLinkResponse.md)
 - [DmrClusterLinkTlsTrustedCommonName](docs/DmrClusterLinkTlsTrustedCommonName.md)
 - [DmrClusterLinkTlsTrustedCommonNameCollections](docs/DmrClusterLinkTlsTrustedCommonNameCollections.md)
 - [DmrClusterLinkTlsTrustedCommonNameLinks](docs/DmrClusterLinkTlsTrustedCommonNameLinks.md)
 - [DmrClusterLinkTlsTrustedCommonNameResponse](docs/DmrClusterLinkTlsTrustedCommonNameResponse.md)
 - [DmrClusterLinkTlsTrustedCommonNamesResponse](docs/DmrClusterLinkTlsTrustedCommonNamesResponse.md)
 - [DmrClusterLinks](docs/DmrClusterLinks.md)
 - [DmrClusterLinksResponse](docs/DmrClusterLinksResponse.md)
 - [DmrClusterResponse](docs/DmrClusterResponse.md)
 - [DmrClusterTopologyIssue](docs/DmrClusterTopologyIssue.md)
 - [DmrClusterTopologyIssueCollections](docs/DmrClusterTopologyIssueCollections.md)
 - [DmrClusterTopologyIssueLinks](docs/DmrClusterTopologyIssueLinks.md)
 - [DmrClusterTopologyIssueResponse](docs/DmrClusterTopologyIssueResponse.md)
 - [DmrClusterTopologyIssuesResponse](docs/DmrClusterTopologyIssuesResponse.md)
 - [DmrClustersResponse](docs/DmrClustersResponse.md)
 - [DomainCertAuthoritiesResponse](docs/DomainCertAuthoritiesResponse.md)
 - [DomainCertAuthority](docs/DomainCertAuthority.md)
 - [DomainCertAuthorityCollections](docs/DomainCertAuthorityCollections.md)
 - [DomainCertAuthorityLinks](docs/DomainCertAuthorityLinks.md)
 - [DomainCertAuthorityResponse](docs/DomainCertAuthorityResponse.md)
 - [EventThreshold](docs/EventThreshold.md)
 - [EventThresholdByPercent](docs/EventThresholdByPercent.md)
 - [EventThresholdByValue](docs/EventThresholdByValue.md)
 - [GuaranteedMsgingExternalDiskArray](docs/GuaranteedMsgingExternalDiskArray.md)
 - [GuaranteedMsgingExternalDiskArrayCollections](docs/GuaranteedMsgingExternalDiskArrayCollections.md)
 - [GuaranteedMsgingExternalDiskArrayLinks](docs/GuaranteedMsgingExternalDiskArrayLinks.md)
 - [GuaranteedMsgingExternalDiskArrayResponse](docs/GuaranteedMsgingExternalDiskArrayResponse.md)
 - [GuaranteedMsgingExternalDiskArraysResponse](docs/GuaranteedMsgingExternalDiskArraysResponse.md)
 - [MsgVpn](docs/MsgVpn.md)
 - [MsgVpnAclProfile](docs/MsgVpnAclProfile.md)
 - [MsgVpnAclProfileClientConnectException](docs/MsgVpnAclProfileClientConnectException.md)
 - [MsgVpnAclProfileClientConnectExceptionCollections](docs/MsgVpnAclProfileClientConnectExceptionCollections.md)
 - [MsgVpnAclProfileClientConnectExceptionLinks](docs/MsgVpnAclProfileClientConnectExceptionLinks.md)
 - [MsgVpnAclProfileClientConnectExceptionResponse](docs/MsgVpnAclProfileClientConnectExceptionResponse.md)
 - [MsgVpnAclProfileClientConnectExceptionsResponse](docs/MsgVpnAclProfileClientConnectExceptionsResponse.md)
 - [MsgVpnAclProfileCollections](docs/MsgVpnAclProfileCollections.md)
 - [MsgVpnAclProfileCollectionsClientconnectexceptions](docs/MsgVpnAclProfileCollectionsClientconnectexceptions.md)
 - [MsgVpnAclProfileCollectionsPublishexceptions](docs/MsgVpnAclProfileCollectionsPublishexceptions.md)
 - [MsgVpnAclProfileCollectionsPublishtopicexceptions](docs/MsgVpnAclProfileCollectionsPublishtopicexceptions.md)
 - [MsgVpnAclProfileCollectionsSubscribeexceptions](docs/MsgVpnAclProfileCollectionsSubscribeexceptions.md)
 - [MsgVpnAclProfileCollectionsSubscribesharenameexceptions](docs/MsgVpnAclProfileCollectionsSubscribesharenameexceptions.md)
 - [MsgVpnAclProfileCollectionsSubscribetopicexceptions](docs/MsgVpnAclProfileCollectionsSubscribetopicexceptions.md)
 - [MsgVpnAclProfileLinks](docs/MsgVpnAclProfileLinks.md)
 - [MsgVpnAclProfilePublishException](docs/MsgVpnAclProfilePublishException.md)
 - [MsgVpnAclProfilePublishExceptionCollections](docs/MsgVpnAclProfilePublishExceptionCollections.md)
 - [MsgVpnAclProfilePublishExceptionLinks](docs/MsgVpnAclProfilePublishExceptionLinks.md)
 - [MsgVpnAclProfilePublishExceptionResponse](docs/MsgVpnAclProfilePublishExceptionResponse.md)
 - [MsgVpnAclProfilePublishExceptionsResponse](docs/MsgVpnAclProfilePublishExceptionsResponse.md)
 - [MsgVpnAclProfilePublishTopicException](docs/MsgVpnAclProfilePublishTopicException.md)
 - [MsgVpnAclProfilePublishTopicExceptionCollections](docs/MsgVpnAclProfilePublishTopicExceptionCollections.md)
 - [MsgVpnAclProfilePublishTopicExceptionLinks](docs/MsgVpnAclProfilePublishTopicExceptionLinks.md)
 - [MsgVpnAclProfilePublishTopicExceptionResponse](docs/MsgVpnAclProfilePublishTopicExceptionResponse.md)
 - [MsgVpnAclProfilePublishTopicExceptionsResponse](docs/MsgVpnAclProfilePublishTopicExceptionsResponse.md)
 - [MsgVpnAclProfileResponse](docs/MsgVpnAclProfileResponse.md)
 - [MsgVpnAclProfileSubscribeException](docs/MsgVpnAclProfileSubscribeException.md)
 - [MsgVpnAclProfileSubscribeExceptionCollections](docs/MsgVpnAclProfileSubscribeExceptionCollections.md)
 - [MsgVpnAclProfileSubscribeExceptionLinks](docs/MsgVpnAclProfileSubscribeExceptionLinks.md)
 - [MsgVpnAclProfileSubscribeExceptionResponse](docs/MsgVpnAclProfileSubscribeExceptionResponse.md)
 - [MsgVpnAclProfileSubscribeExceptionsResponse](docs/MsgVpnAclProfileSubscribeExceptionsResponse.md)
 - [MsgVpnAclProfileSubscribeShareNameException](docs/MsgVpnAclProfileSubscribeShareNameException.md)
 - [MsgVpnAclProfileSubscribeShareNameExceptionCollections](docs/MsgVpnAclProfileSubscribeShareNameExceptionCollections.md)
 - [MsgVpnAclProfileSubscribeShareNameExceptionLinks](docs/MsgVpnAclProfileSubscribeShareNameExceptionLinks.md)
 - [MsgVpnAclProfileSubscribeShareNameExceptionResponse](docs/MsgVpnAclProfileSubscribeShareNameExceptionResponse.md)
 - [MsgVpnAclProfileSubscribeShareNameExceptionsResponse](docs/MsgVpnAclProfileSubscribeShareNameExceptionsResponse.md)
 - [MsgVpnAclProfileSubscribeTopicException](docs/MsgVpnAclProfileSubscribeTopicException.md)
 - [MsgVpnAclProfileSubscribeTopicExceptionCollections](docs/MsgVpnAclProfileSubscribeTopicExceptionCollections.md)
 - [MsgVpnAclProfileSubscribeTopicExceptionLinks](docs/MsgVpnAclProfileSubscribeTopicExceptionLinks.md)
 - [MsgVpnAclProfileSubscribeTopicExceptionResponse](docs/MsgVpnAclProfileSubscribeTopicExceptionResponse.md)
 - [MsgVpnAclProfileSubscribeTopicExceptionsResponse](docs/MsgVpnAclProfileSubscribeTopicExceptionsResponse.md)
 - [MsgVpnAclProfilesResponse](docs/MsgVpnAclProfilesResponse.md)
 - [MsgVpnAuthenticationOauthProvider](docs/MsgVpnAuthenticationOauthProvider.md)
 - [MsgVpnAuthenticationOauthProviderCollections](docs/MsgVpnAuthenticationOauthProviderCollections.md)
 - [MsgVpnAuthenticationOauthProviderLinks](docs/MsgVpnAuthenticationOauthProviderLinks.md)
 - [MsgVpnAuthenticationOauthProviderResponse](docs/MsgVpnAuthenticationOauthProviderResponse.md)
 - [MsgVpnAuthenticationOauthProvidersResponse](docs/MsgVpnAuthenticationOauthProvidersResponse.md)
 - [MsgVpnAuthorizationGroup](docs/MsgVpnAuthorizationGroup.md)
 - [MsgVpnAuthorizationGroupCollections](docs/MsgVpnAuthorizationGroupCollections.md)
 - [MsgVpnAuthorizationGroupLinks](docs/MsgVpnAuthorizationGroupLinks.md)
 - [MsgVpnAuthorizationGroupResponse](docs/MsgVpnAuthorizationGroupResponse.md)
 - [MsgVpnAuthorizationGroupsResponse](docs/MsgVpnAuthorizationGroupsResponse.md)
 - [MsgVpnBridge](docs/MsgVpnBridge.md)
 - [MsgVpnBridgeCollections](docs/MsgVpnBridgeCollections.md)
 - [MsgVpnBridgeCollectionsLocalsubscriptions](docs/MsgVpnBridgeCollectionsLocalsubscriptions.md)
 - [MsgVpnBridgeCollectionsRemotemsgvpns](docs/MsgVpnBridgeCollectionsRemotemsgvpns.md)
 - [MsgVpnBridgeCollectionsRemotesubscriptions](docs/MsgVpnBridgeCollectionsRemotesubscriptions.md)
 - [MsgVpnBridgeCollectionsTlstrustedcommonnames](docs/MsgVpnBridgeCollectionsTlstrustedcommonnames.md)
 - [MsgVpnBridgeCounter](docs/MsgVpnBridgeCounter.md)
 - [MsgVpnBridgeLinks](docs/MsgVpnBridgeLinks.md)
 - [MsgVpnBridgeLocalSubscription](docs/MsgVpnBridgeLocalSubscription.md)
 - [MsgVpnBridgeLocalSubscriptionCollections](docs/MsgVpnBridgeLocalSubscriptionCollections.md)
 - [MsgVpnBridgeLocalSubscriptionLinks](docs/MsgVpnBridgeLocalSubscriptionLinks.md)
 - [MsgVpnBridgeLocalSubscriptionResponse](docs/MsgVpnBridgeLocalSubscriptionResponse.md)
 - [MsgVpnBridgeLocalSubscriptionsResponse](docs/MsgVpnBridgeLocalSubscriptionsResponse.md)
 - [MsgVpnBridgeRate](docs/MsgVpnBridgeRate.md)
 - [MsgVpnBridgeRemoteMsgVpn](docs/MsgVpnBridgeRemoteMsgVpn.md)
 - [MsgVpnBridgeRemoteMsgVpnCollections](docs/MsgVpnBridgeRemoteMsgVpnCollections.md)
 - [MsgVpnBridgeRemoteMsgVpnLinks](docs/MsgVpnBridgeRemoteMsgVpnLinks.md)
 - [MsgVpnBridgeRemoteMsgVpnResponse](docs/MsgVpnBridgeRemoteMsgVpnResponse.md)
 - [MsgVpnBridgeRemoteMsgVpnsResponse](docs/MsgVpnBridgeRemoteMsgVpnsResponse.md)
 - [MsgVpnBridgeRemoteSubscription](docs/MsgVpnBridgeRemoteSubscription.md)
 - [MsgVpnBridgeRemoteSubscriptionCollections](docs/MsgVpnBridgeRemoteSubscriptionCollections.md)
 - [MsgVpnBridgeRemoteSubscriptionLinks](docs/MsgVpnBridgeRemoteSubscriptionLinks.md)
 - [MsgVpnBridgeRemoteSubscriptionResponse](docs/MsgVpnBridgeRemoteSubscriptionResponse.md)
 - [MsgVpnBridgeRemoteSubscriptionsResponse](docs/MsgVpnBridgeRemoteSubscriptionsResponse.md)
 - [MsgVpnBridgeResponse](docs/MsgVpnBridgeResponse.md)
 - [MsgVpnBridgeTlsTrustedCommonName](docs/MsgVpnBridgeTlsTrustedCommonName.md)
 - [MsgVpnBridgeTlsTrustedCommonNameCollections](docs/MsgVpnBridgeTlsTrustedCommonNameCollections.md)
 - [MsgVpnBridgeTlsTrustedCommonNameLinks](docs/MsgVpnBridgeTlsTrustedCommonNameLinks.md)
 - [MsgVpnBridgeTlsTrustedCommonNameResponse](docs/MsgVpnBridgeTlsTrustedCommonNameResponse.md)
 - [MsgVpnBridgeTlsTrustedCommonNamesResponse](docs/MsgVpnBridgeTlsTrustedCommonNamesResponse.md)
 - [MsgVpnBridgesResponse](docs/MsgVpnBridgesResponse.md)
 - [MsgVpnClient](docs/MsgVpnClient.md)
 - [MsgVpnClientCollections](docs/MsgVpnClientCollections.md)
 - [MsgVpnClientCollectionsConnections](docs/MsgVpnClientCollectionsConnections.md)
 - [MsgVpnClientCollectionsRxflows](docs/MsgVpnClientCollectionsRxflows.md)
 - [MsgVpnClientCollectionsSubscriptions](docs/MsgVpnClientCollectionsSubscriptions.md)
 - [MsgVpnClientCollectionsTransactedsessions](docs/MsgVpnClientCollectionsTransactedsessions.md)
 - [MsgVpnClientCollectionsTxflows](docs/MsgVpnClientCollectionsTxflows.md)
 - [MsgVpnClientConnection](docs/MsgVpnClientConnection.md)
 - [MsgVpnClientConnectionCollections](docs/MsgVpnClientConnectionCollections.md)
 - [MsgVpnClientConnectionLinks](docs/MsgVpnClientConnectionLinks.md)
 - [MsgVpnClientConnectionResponse](docs/MsgVpnClientConnectionResponse.md)
 - [MsgVpnClientConnectionsResponse](docs/MsgVpnClientConnectionsResponse.md)
 - [MsgVpnClientLinks](docs/MsgVpnClientLinks.md)
 - [MsgVpnClientProfile](docs/MsgVpnClientProfile.md)
 - [MsgVpnClientProfileCollections](docs/MsgVpnClientProfileCollections.md)
 - [MsgVpnClientProfileLinks](docs/MsgVpnClientProfileLinks.md)
 - [MsgVpnClientProfileResponse](docs/MsgVpnClientProfileResponse.md)
 - [MsgVpnClientProfilesResponse](docs/MsgVpnClientProfilesResponse.md)
 - [MsgVpnClientResponse](docs/MsgVpnClientResponse.md)
 - [MsgVpnClientRxFlow](docs/MsgVpnClientRxFlow.md)
 - [MsgVpnClientRxFlowCollections](docs/MsgVpnClientRxFlowCollections.md)
 - [MsgVpnClientRxFlowLinks](docs/MsgVpnClientRxFlowLinks.md)
 - [MsgVpnClientRxFlowResponse](docs/MsgVpnClientRxFlowResponse.md)
 - [MsgVpnClientRxFlowsResponse](docs/MsgVpnClientRxFlowsResponse.md)
 - [MsgVpnClientSubscription](docs/MsgVpnClientSubscription.md)
 - [MsgVpnClientSubscriptionCollections](docs/MsgVpnClientSubscriptionCollections.md)
 - [MsgVpnClientSubscriptionLinks](docs/MsgVpnClientSubscriptionLinks.md)
 - [MsgVpnClientSubscriptionResponse](docs/MsgVpnClientSubscriptionResponse.md)
 - [MsgVpnClientSubscriptionsResponse](docs/MsgVpnClientSubscriptionsResponse.md)
 - [MsgVpnClientTransactedSession](docs/MsgVpnClientTransactedSession.md)
 - [MsgVpnClientTransactedSessionCollections](docs/MsgVpnClientTransactedSessionCollections.md)
 - [MsgVpnClientTransactedSessionLinks](docs/MsgVpnClientTransactedSessionLinks.md)
 - [MsgVpnClientTransactedSessionResponse](docs/MsgVpnClientTransactedSessionResponse.md)
 - [MsgVpnClientTransactedSessionsResponse](docs/MsgVpnClientTransactedSessionsResponse.md)
 - [MsgVpnClientTxFlow](docs/MsgVpnClientTxFlow.md)
 - [MsgVpnClientTxFlowCollections](docs/MsgVpnClientTxFlowCollections.md)
 - [MsgVpnClientTxFlowLinks](docs/MsgVpnClientTxFlowLinks.md)
 - [MsgVpnClientTxFlowResponse](docs/MsgVpnClientTxFlowResponse.md)
 - [MsgVpnClientTxFlowsResponse](docs/MsgVpnClientTxFlowsResponse.md)
 - [MsgVpnClientUsername](docs/MsgVpnClientUsername.md)
 - [MsgVpnClientUsernameCollections](docs/MsgVpnClientUsernameCollections.md)
 - [MsgVpnClientUsernameLinks](docs/MsgVpnClientUsernameLinks.md)
 - [MsgVpnClientUsernameResponse](docs/MsgVpnClientUsernameResponse.md)
 - [MsgVpnClientUsernamesResponse](docs/MsgVpnClientUsernamesResponse.md)
 - [MsgVpnClientsResponse](docs/MsgVpnClientsResponse.md)
 - [MsgVpnCollections](docs/MsgVpnCollections.md)
 - [MsgVpnCollectionsAclprofiles](docs/MsgVpnCollectionsAclprofiles.md)
 - [MsgVpnCollectionsAuthenticationoauthproviders](docs/MsgVpnCollectionsAuthenticationoauthproviders.md)
 - [MsgVpnCollectionsAuthorizationgroups](docs/MsgVpnCollectionsAuthorizationgroups.md)
 - [MsgVpnCollectionsBridges](docs/MsgVpnCollectionsBridges.md)
 - [MsgVpnCollectionsClientprofiles](docs/MsgVpnCollectionsClientprofiles.md)
 - [MsgVpnCollectionsClients](docs/MsgVpnCollectionsClients.md)
 - [MsgVpnCollectionsClientusernames](docs/MsgVpnCollectionsClientusernames.md)
 - [MsgVpnCollectionsConfigsyncremotenodes](docs/MsgVpnCollectionsConfigsyncremotenodes.md)
 - [MsgVpnCollectionsDistributedcaches](docs/MsgVpnCollectionsDistributedcaches.md)
 - [MsgVpnCollectionsDmrbridges](docs/MsgVpnCollectionsDmrbridges.md)
 - [MsgVpnCollectionsJndiconnectionfactories](docs/MsgVpnCollectionsJndiconnectionfactories.md)
 - [MsgVpnCollectionsJndiqueues](docs/MsgVpnCollectionsJndiqueues.md)
 - [MsgVpnCollectionsJnditopics](docs/MsgVpnCollectionsJnditopics.md)
 - [MsgVpnCollectionsMqttretaincaches](docs/MsgVpnCollectionsMqttretaincaches.md)
 - [MsgVpnCollectionsMqttsessions](docs/MsgVpnCollectionsMqttsessions.md)
 - [MsgVpnCollectionsQueues](docs/MsgVpnCollectionsQueues.md)
 - [MsgVpnCollectionsQueuetemplates](docs/MsgVpnCollectionsQueuetemplates.md)
 - [MsgVpnCollectionsReplaylogs](docs/MsgVpnCollectionsReplaylogs.md)
 - [MsgVpnCollectionsReplicatedtopics](docs/MsgVpnCollectionsReplicatedtopics.md)
 - [MsgVpnCollectionsRestdeliverypoints](docs/MsgVpnCollectionsRestdeliverypoints.md)
 - [MsgVpnCollectionsTopicendpoints](docs/MsgVpnCollectionsTopicendpoints.md)
 - [MsgVpnCollectionsTopicendpointtemplates](docs/MsgVpnCollectionsTopicendpointtemplates.md)
 - [MsgVpnCollectionsTransactions](docs/MsgVpnCollectionsTransactions.md)
 - [MsgVpnConfigSyncRemoteNode](docs/MsgVpnConfigSyncRemoteNode.md)
 - [MsgVpnConfigSyncRemoteNodeCollections](docs/MsgVpnConfigSyncRemoteNodeCollections.md)
 - [MsgVpnConfigSyncRemoteNodeLinks](docs/MsgVpnConfigSyncRemoteNodeLinks.md)
 - [MsgVpnConfigSyncRemoteNodeResponse](docs/MsgVpnConfigSyncRemoteNodeResponse.md)
 - [MsgVpnConfigSyncRemoteNodesResponse](docs/MsgVpnConfigSyncRemoteNodesResponse.md)
 - [MsgVpnCounter](docs/MsgVpnCounter.md)
 - [MsgVpnDistributedCache](docs/MsgVpnDistributedCache.md)
 - [MsgVpnDistributedCacheCluster](docs/MsgVpnDistributedCacheCluster.md)
 - [MsgVpnDistributedCacheClusterCollections](docs/MsgVpnDistributedCacheClusterCollections.md)
 - [MsgVpnDistributedCacheClusterCollectionsGlobalcachinghomeclusters](docs/MsgVpnDistributedCacheClusterCollectionsGlobalcachinghomeclusters.md)
 - [MsgVpnDistributedCacheClusterCollectionsInstances](docs/MsgVpnDistributedCacheClusterCollectionsInstances.md)
 - [MsgVpnDistributedCacheClusterCollectionsTopics](docs/MsgVpnDistributedCacheClusterCollectionsTopics.md)
 - [MsgVpnDistributedCacheClusterGlobalCachingHomeCluster](docs/MsgVpnDistributedCacheClusterGlobalCachingHomeCluster.md)
 - [MsgVpnDistributedCacheClusterGlobalCachingHomeClusterCollections](docs/MsgVpnDistributedCacheClusterGlobalCachingHomeClusterCollections.md)
 - [MsgVpnDistributedCacheClusterGlobalCachingHomeClusterCollectionsTopicprefixes](docs/MsgVpnDistributedCacheClusterGlobalCachingHomeClusterCollectionsTopicprefixes.md)
 - [MsgVpnDistributedCacheClusterGlobalCachingHomeClusterLinks](docs/MsgVpnDistributedCacheClusterGlobalCachingHomeClusterLinks.md)
 - [MsgVpnDistributedCacheClusterGlobalCachingHomeClusterResponse](docs/MsgVpnDistributedCacheClusterGlobalCachingHomeClusterResponse.md)
 - [MsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefix](docs/MsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefix.md)
 - [MsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixCollections](docs/MsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixCollections.md)
 - [MsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixLinks](docs/MsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixLinks.md)
 - [MsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixResponse](docs/MsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixResponse.md)
 - [MsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixesResponse](docs/MsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixesResponse.md)
 - [MsgVpnDistributedCacheClusterGlobalCachingHomeClustersResponse](docs/MsgVpnDistributedCacheClusterGlobalCachingHomeClustersResponse.md)
 - [MsgVpnDistributedCacheClusterInstance](docs/MsgVpnDistributedCacheClusterInstance.md)
 - [MsgVpnDistributedCacheClusterInstanceCollections](docs/MsgVpnDistributedCacheClusterInstanceCollections.md)
 - [MsgVpnDistributedCacheClusterInstanceCollectionsRemoteglobalcachinghomeclusters](docs/MsgVpnDistributedCacheClusterInstanceCollectionsRemoteglobalcachinghomeclusters.md)
 - [MsgVpnDistributedCacheClusterInstanceCollectionsRemotetopics](docs/MsgVpnDistributedCacheClusterInstanceCollectionsRemotetopics.md)
 - [MsgVpnDistributedCacheClusterInstanceCounter](docs/MsgVpnDistributedCacheClusterInstanceCounter.md)
 - [MsgVpnDistributedCacheClusterInstanceLinks](docs/MsgVpnDistributedCacheClusterInstanceLinks.md)
 - [MsgVpnDistributedCacheClusterInstanceRate](docs/MsgVpnDistributedCacheClusterInstanceRate.md)
 - [MsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeCluster](docs/MsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeCluster.md)
 - [MsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeClusterCollections](docs/MsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeClusterCollections.md)
 - [MsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeClusterLinks](docs/MsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeClusterLinks.md)
 - [MsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeClusterResponse](docs/MsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeClusterResponse.md)
 - [MsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeClustersResponse](docs/MsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeClustersResponse.md)
 - [MsgVpnDistributedCacheClusterInstanceRemoteTopic](docs/MsgVpnDistributedCacheClusterInstanceRemoteTopic.md)
 - [MsgVpnDistributedCacheClusterInstanceRemoteTopicCollections](docs/MsgVpnDistributedCacheClusterInstanceRemoteTopicCollections.md)
 - [MsgVpnDistributedCacheClusterInstanceRemoteTopicLinks](docs/MsgVpnDistributedCacheClusterInstanceRemoteTopicLinks.md)
 - [MsgVpnDistributedCacheClusterInstanceRemoteTopicResponse](docs/MsgVpnDistributedCacheClusterInstanceRemoteTopicResponse.md)
 - [MsgVpnDistributedCacheClusterInstanceRemoteTopicsResponse](docs/MsgVpnDistributedCacheClusterInstanceRemoteTopicsResponse.md)
 - [MsgVpnDistributedCacheClusterInstanceResponse](docs/MsgVpnDistributedCacheClusterInstanceResponse.md)
 - [MsgVpnDistributedCacheClusterInstancesResponse](docs/MsgVpnDistributedCacheClusterInstancesResponse.md)
 - [MsgVpnDistributedCacheClusterLinks](docs/MsgVpnDistributedCacheClusterLinks.md)
 - [MsgVpnDistributedCacheClusterResponse](docs/MsgVpnDistributedCacheClusterResponse.md)
 - [MsgVpnDistributedCacheClusterTopic](docs/MsgVpnDistributedCacheClusterTopic.md)
 - [MsgVpnDistributedCacheClusterTopicCollections](docs/MsgVpnDistributedCacheClusterTopicCollections.md)
 - [MsgVpnDistributedCacheClusterTopicLinks](docs/MsgVpnDistributedCacheClusterTopicLinks.md)
 - [MsgVpnDistributedCacheClusterTopicResponse](docs/MsgVpnDistributedCacheClusterTopicResponse.md)
 - [MsgVpnDistributedCacheClusterTopicsResponse](docs/MsgVpnDistributedCacheClusterTopicsResponse.md)
 - [MsgVpnDistributedCacheClustersResponse](docs/MsgVpnDistributedCacheClustersResponse.md)
 - [MsgVpnDistributedCacheCollections](docs/MsgVpnDistributedCacheCollections.md)
 - [MsgVpnDistributedCacheCollectionsClusters](docs/MsgVpnDistributedCacheCollectionsClusters.md)
 - [MsgVpnDistributedCacheLinks](docs/MsgVpnDistributedCacheLinks.md)
 - [MsgVpnDistributedCacheResponse](docs/MsgVpnDistributedCacheResponse.md)
 - [MsgVpnDistributedCachesResponse](docs/MsgVpnDistributedCachesResponse.md)
 - [MsgVpnDmrBridge](docs/MsgVpnDmrBridge.md)
 - [MsgVpnDmrBridgeCollections](docs/MsgVpnDmrBridgeCollections.md)
 - [MsgVpnDmrBridgeLinks](docs/MsgVpnDmrBridgeLinks.md)
 - [MsgVpnDmrBridgeResponse](docs/MsgVpnDmrBridgeResponse.md)
 - [MsgVpnDmrBridgesResponse](docs/MsgVpnDmrBridgesResponse.md)
 - [MsgVpnJndiConnectionFactoriesResponse](docs/MsgVpnJndiConnectionFactoriesResponse.md)
 - [MsgVpnJndiConnectionFactory](docs/MsgVpnJndiConnectionFactory.md)
 - [MsgVpnJndiConnectionFactoryCollections](docs/MsgVpnJndiConnectionFactoryCollections.md)
 - [MsgVpnJndiConnectionFactoryLinks](docs/MsgVpnJndiConnectionFactoryLinks.md)
 - [MsgVpnJndiConnectionFactoryResponse](docs/MsgVpnJndiConnectionFactoryResponse.md)
 - [MsgVpnJndiQueue](docs/MsgVpnJndiQueue.md)
 - [MsgVpnJndiQueueCollections](docs/MsgVpnJndiQueueCollections.md)
 - [MsgVpnJndiQueueLinks](docs/MsgVpnJndiQueueLinks.md)
 - [MsgVpnJndiQueueResponse](docs/MsgVpnJndiQueueResponse.md)
 - [MsgVpnJndiQueuesResponse](docs/MsgVpnJndiQueuesResponse.md)
 - [MsgVpnJndiTopic](docs/MsgVpnJndiTopic.md)
 - [MsgVpnJndiTopicCollections](docs/MsgVpnJndiTopicCollections.md)
 - [MsgVpnJndiTopicLinks](docs/MsgVpnJndiTopicLinks.md)
 - [MsgVpnJndiTopicResponse](docs/MsgVpnJndiTopicResponse.md)
 - [MsgVpnJndiTopicsResponse](docs/MsgVpnJndiTopicsResponse.md)
 - [MsgVpnLinks](docs/MsgVpnLinks.md)
 - [MsgVpnMqttRetainCache](docs/MsgVpnMqttRetainCache.md)
 - [MsgVpnMqttRetainCacheCollections](docs/MsgVpnMqttRetainCacheCollections.md)
 - [MsgVpnMqttRetainCacheLinks](docs/MsgVpnMqttRetainCacheLinks.md)
 - [MsgVpnMqttRetainCacheResponse](docs/MsgVpnMqttRetainCacheResponse.md)
 - [MsgVpnMqttRetainCachesResponse](docs/MsgVpnMqttRetainCachesResponse.md)
 - [MsgVpnMqttSession](docs/MsgVpnMqttSession.md)
 - [MsgVpnMqttSessionCollections](docs/MsgVpnMqttSessionCollections.md)
 - [MsgVpnMqttSessionCollectionsSubscriptions](docs/MsgVpnMqttSessionCollectionsSubscriptions.md)
 - [MsgVpnMqttSessionCounter](docs/MsgVpnMqttSessionCounter.md)
 - [MsgVpnMqttSessionLinks](docs/MsgVpnMqttSessionLinks.md)
 - [MsgVpnMqttSessionResponse](docs/MsgVpnMqttSessionResponse.md)
 - [MsgVpnMqttSessionSubscription](docs/MsgVpnMqttSessionSubscription.md)
 - [MsgVpnMqttSessionSubscriptionCollections](docs/MsgVpnMqttSessionSubscriptionCollections.md)
 - [MsgVpnMqttSessionSubscriptionLinks](docs/MsgVpnMqttSessionSubscriptionLinks.md)
 - [MsgVpnMqttSessionSubscriptionResponse](docs/MsgVpnMqttSessionSubscriptionResponse.md)
 - [MsgVpnMqttSessionSubscriptionsResponse](docs/MsgVpnMqttSessionSubscriptionsResponse.md)
 - [MsgVpnMqttSessionsResponse](docs/MsgVpnMqttSessionsResponse.md)
 - [MsgVpnQueue](docs/MsgVpnQueue.md)
 - [MsgVpnQueueCollections](docs/MsgVpnQueueCollections.md)
 - [MsgVpnQueueCollectionsMsgs](docs/MsgVpnQueueCollectionsMsgs.md)
 - [MsgVpnQueueCollectionsPriorities](docs/MsgVpnQueueCollectionsPriorities.md)
 - [MsgVpnQueueCollectionsSubscriptions](docs/MsgVpnQueueCollectionsSubscriptions.md)
 - [MsgVpnQueueCollectionsTxflows](docs/MsgVpnQueueCollectionsTxflows.md)
 - [MsgVpnQueueLinks](docs/MsgVpnQueueLinks.md)
 - [MsgVpnQueueMsg](docs/MsgVpnQueueMsg.md)
 - [MsgVpnQueueMsgCollections](docs/MsgVpnQueueMsgCollections.md)
 - [MsgVpnQueueMsgLinks](docs/MsgVpnQueueMsgLinks.md)
 - [MsgVpnQueueMsgResponse](docs/MsgVpnQueueMsgResponse.md)
 - [MsgVpnQueueMsgsResponse](docs/MsgVpnQueueMsgsResponse.md)
 - [MsgVpnQueuePrioritiesResponse](docs/MsgVpnQueuePrioritiesResponse.md)
 - [MsgVpnQueuePriority](docs/MsgVpnQueuePriority.md)
 - [MsgVpnQueuePriorityCollections](docs/MsgVpnQueuePriorityCollections.md)
 - [MsgVpnQueuePriorityLinks](docs/MsgVpnQueuePriorityLinks.md)
 - [MsgVpnQueuePriorityResponse](docs/MsgVpnQueuePriorityResponse.md)
 - [MsgVpnQueueResponse](docs/MsgVpnQueueResponse.md)
 - [MsgVpnQueueSubscription](docs/MsgVpnQueueSubscription.md)
 - [MsgVpnQueueSubscriptionCollections](docs/MsgVpnQueueSubscriptionCollections.md)
 - [MsgVpnQueueSubscriptionLinks](docs/MsgVpnQueueSubscriptionLinks.md)
 - [MsgVpnQueueSubscriptionResponse](docs/MsgVpnQueueSubscriptionResponse.md)
 - [MsgVpnQueueSubscriptionsResponse](docs/MsgVpnQueueSubscriptionsResponse.md)
 - [MsgVpnQueueTemplate](docs/MsgVpnQueueTemplate.md)
 - [MsgVpnQueueTemplateCollections](docs/MsgVpnQueueTemplateCollections.md)
 - [MsgVpnQueueTemplateLinks](docs/MsgVpnQueueTemplateLinks.md)
 - [MsgVpnQueueTemplateResponse](docs/MsgVpnQueueTemplateResponse.md)
 - [MsgVpnQueueTemplatesResponse](docs/MsgVpnQueueTemplatesResponse.md)
 - [MsgVpnQueueTxFlow](docs/MsgVpnQueueTxFlow.md)
 - [MsgVpnQueueTxFlowCollections](docs/MsgVpnQueueTxFlowCollections.md)
 - [MsgVpnQueueTxFlowLinks](docs/MsgVpnQueueTxFlowLinks.md)
 - [MsgVpnQueueTxFlowResponse](docs/MsgVpnQueueTxFlowResponse.md)
 - [MsgVpnQueueTxFlowsResponse](docs/MsgVpnQueueTxFlowsResponse.md)
 - [MsgVpnQueuesResponse](docs/MsgVpnQueuesResponse.md)
 - [MsgVpnRate](docs/MsgVpnRate.md)
 - [MsgVpnReplayLog](docs/MsgVpnReplayLog.md)
 - [MsgVpnReplayLogCollections](docs/MsgVpnReplayLogCollections.md)
 - [MsgVpnReplayLogCollectionsMsgs](docs/MsgVpnReplayLogCollectionsMsgs.md)
 - [MsgVpnReplayLogLinks](docs/MsgVpnReplayLogLinks.md)
 - [MsgVpnReplayLogMsg](docs/MsgVpnReplayLogMsg.md)
 - [MsgVpnReplayLogMsgCollections](docs/MsgVpnReplayLogMsgCollections.md)
 - [MsgVpnReplayLogMsgLinks](docs/MsgVpnReplayLogMsgLinks.md)
 - [MsgVpnReplayLogMsgResponse](docs/MsgVpnReplayLogMsgResponse.md)
 - [MsgVpnReplayLogMsgsResponse](docs/MsgVpnReplayLogMsgsResponse.md)
 - [MsgVpnReplayLogResponse](docs/MsgVpnReplayLogResponse.md)
 - [MsgVpnReplayLogsResponse](docs/MsgVpnReplayLogsResponse.md)
 - [MsgVpnReplicatedTopic](docs/MsgVpnReplicatedTopic.md)
 - [MsgVpnReplicatedTopicCollections](docs/MsgVpnReplicatedTopicCollections.md)
 - [MsgVpnReplicatedTopicLinks](docs/MsgVpnReplicatedTopicLinks.md)
 - [MsgVpnReplicatedTopicResponse](docs/MsgVpnReplicatedTopicResponse.md)
 - [MsgVpnReplicatedTopicsResponse](docs/MsgVpnReplicatedTopicsResponse.md)
 - [MsgVpnResponse](docs/MsgVpnResponse.md)
 - [MsgVpnRestDeliveryPoint](docs/MsgVpnRestDeliveryPoint.md)
 - [MsgVpnRestDeliveryPointCollections](docs/MsgVpnRestDeliveryPointCollections.md)
 - [MsgVpnRestDeliveryPointCollectionsQueuebindings](docs/MsgVpnRestDeliveryPointCollectionsQueuebindings.md)
 - [MsgVpnRestDeliveryPointCollectionsRestconsumers](docs/MsgVpnRestDeliveryPointCollectionsRestconsumers.md)
 - [MsgVpnRestDeliveryPointLinks](docs/MsgVpnRestDeliveryPointLinks.md)
 - [MsgVpnRestDeliveryPointQueueBinding](docs/MsgVpnRestDeliveryPointQueueBinding.md)
 - [MsgVpnRestDeliveryPointQueueBindingCollections](docs/MsgVpnRestDeliveryPointQueueBindingCollections.md)
 - [MsgVpnRestDeliveryPointQueueBindingCollectionsRequestheaders](docs/MsgVpnRestDeliveryPointQueueBindingCollectionsRequestheaders.md)
 - [MsgVpnRestDeliveryPointQueueBindingLinks](docs/MsgVpnRestDeliveryPointQueueBindingLinks.md)
 - [MsgVpnRestDeliveryPointQueueBindingRequestHeader](docs/MsgVpnRestDeliveryPointQueueBindingRequestHeader.md)
 - [MsgVpnRestDeliveryPointQueueBindingRequestHeaderCollections](docs/MsgVpnRestDeliveryPointQueueBindingRequestHeaderCollections.md)
 - [MsgVpnRestDeliveryPointQueueBindingRequestHeaderLinks](docs/MsgVpnRestDeliveryPointQueueBindingRequestHeaderLinks.md)
 - [MsgVpnRestDeliveryPointQueueBindingRequestHeaderResponse](docs/MsgVpnRestDeliveryPointQueueBindingRequestHeaderResponse.md)
 - [MsgVpnRestDeliveryPointQueueBindingRequestHeadersResponse](docs/MsgVpnRestDeliveryPointQueueBindingRequestHeadersResponse.md)
 - [MsgVpnRestDeliveryPointQueueBindingResponse](docs/MsgVpnRestDeliveryPointQueueBindingResponse.md)
 - [MsgVpnRestDeliveryPointQueueBindingsResponse](docs/MsgVpnRestDeliveryPointQueueBindingsResponse.md)
 - [MsgVpnRestDeliveryPointResponse](docs/MsgVpnRestDeliveryPointResponse.md)
 - [MsgVpnRestDeliveryPointRestConsumer](docs/MsgVpnRestDeliveryPointRestConsumer.md)
 - [MsgVpnRestDeliveryPointRestConsumerCollections](docs/MsgVpnRestDeliveryPointRestConsumerCollections.md)
 - [MsgVpnRestDeliveryPointRestConsumerCollectionsOauthjwtclaims](docs/MsgVpnRestDeliveryPointRestConsumerCollectionsOauthjwtclaims.md)
 - [MsgVpnRestDeliveryPointRestConsumerCollectionsTlstrustedcommonnames](docs/MsgVpnRestDeliveryPointRestConsumerCollectionsTlstrustedcommonnames.md)
 - [MsgVpnRestDeliveryPointRestConsumerCounter](docs/MsgVpnRestDeliveryPointRestConsumerCounter.md)
 - [MsgVpnRestDeliveryPointRestConsumerLinks](docs/MsgVpnRestDeliveryPointRestConsumerLinks.md)
 - [MsgVpnRestDeliveryPointRestConsumerOauthJwtClaim](docs/MsgVpnRestDeliveryPointRestConsumerOauthJwtClaim.md)
 - [MsgVpnRestDeliveryPointRestConsumerOauthJwtClaimCollections](docs/MsgVpnRestDeliveryPointRestConsumerOauthJwtClaimCollections.md)
 - [MsgVpnRestDeliveryPointRestConsumerOauthJwtClaimLinks](docs/MsgVpnRestDeliveryPointRestConsumerOauthJwtClaimLinks.md)
 - [MsgVpnRestDeliveryPointRestConsumerOauthJwtClaimResponse](docs/MsgVpnRestDeliveryPointRestConsumerOauthJwtClaimResponse.md)
 - [MsgVpnRestDeliveryPointRestConsumerOauthJwtClaimsResponse](docs/MsgVpnRestDeliveryPointRestConsumerOauthJwtClaimsResponse.md)
 - [MsgVpnRestDeliveryPointRestConsumerResponse](docs/MsgVpnRestDeliveryPointRestConsumerResponse.md)
 - [MsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonName](docs/MsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonName.md)
 - [MsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNameCollections](docs/MsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNameCollections.md)
 - [MsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNameLinks](docs/MsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNameLinks.md)
 - [MsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNameResponse](docs/MsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNameResponse.md)
 - [MsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNamesResponse](docs/MsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNamesResponse.md)
 - [MsgVpnRestDeliveryPointRestConsumersResponse](docs/MsgVpnRestDeliveryPointRestConsumersResponse.md)
 - [MsgVpnRestDeliveryPointsResponse](docs/MsgVpnRestDeliveryPointsResponse.md)
 - [MsgVpnTopicEndpoint](docs/MsgVpnTopicEndpoint.md)
 - [MsgVpnTopicEndpointCollections](docs/MsgVpnTopicEndpointCollections.md)
 - [MsgVpnTopicEndpointCollectionsMsgs](docs/MsgVpnTopicEndpointCollectionsMsgs.md)
 - [MsgVpnTopicEndpointCollectionsPriorities](docs/MsgVpnTopicEndpointCollectionsPriorities.md)
 - [MsgVpnTopicEndpointCollectionsTxflows](docs/MsgVpnTopicEndpointCollectionsTxflows.md)
 - [MsgVpnTopicEndpointLinks](docs/MsgVpnTopicEndpointLinks.md)
 - [MsgVpnTopicEndpointMsg](docs/MsgVpnTopicEndpointMsg.md)
 - [MsgVpnTopicEndpointMsgCollections](docs/MsgVpnTopicEndpointMsgCollections.md)
 - [MsgVpnTopicEndpointMsgLinks](docs/MsgVpnTopicEndpointMsgLinks.md)
 - [MsgVpnTopicEndpointMsgResponse](docs/MsgVpnTopicEndpointMsgResponse.md)
 - [MsgVpnTopicEndpointMsgsResponse](docs/MsgVpnTopicEndpointMsgsResponse.md)
 - [MsgVpnTopicEndpointPrioritiesResponse](docs/MsgVpnTopicEndpointPrioritiesResponse.md)
 - [MsgVpnTopicEndpointPriority](docs/MsgVpnTopicEndpointPriority.md)
 - [MsgVpnTopicEndpointPriorityCollections](docs/MsgVpnTopicEndpointPriorityCollections.md)
 - [MsgVpnTopicEndpointPriorityLinks](docs/MsgVpnTopicEndpointPriorityLinks.md)
 - [MsgVpnTopicEndpointPriorityResponse](docs/MsgVpnTopicEndpointPriorityResponse.md)
 - [MsgVpnTopicEndpointResponse](docs/MsgVpnTopicEndpointResponse.md)
 - [MsgVpnTopicEndpointTemplate](docs/MsgVpnTopicEndpointTemplate.md)
 - [MsgVpnTopicEndpointTemplateCollections](docs/MsgVpnTopicEndpointTemplateCollections.md)
 - [MsgVpnTopicEndpointTemplateLinks](docs/MsgVpnTopicEndpointTemplateLinks.md)
 - [MsgVpnTopicEndpointTemplateResponse](docs/MsgVpnTopicEndpointTemplateResponse.md)
 - [MsgVpnTopicEndpointTemplatesResponse](docs/MsgVpnTopicEndpointTemplatesResponse.md)
 - [MsgVpnTopicEndpointTxFlow](docs/MsgVpnTopicEndpointTxFlow.md)
 - [MsgVpnTopicEndpointTxFlowCollections](docs/MsgVpnTopicEndpointTxFlowCollections.md)
 - [MsgVpnTopicEndpointTxFlowLinks](docs/MsgVpnTopicEndpointTxFlowLinks.md)
 - [MsgVpnTopicEndpointTxFlowResponse](docs/MsgVpnTopicEndpointTxFlowResponse.md)
 - [MsgVpnTopicEndpointTxFlowsResponse](docs/MsgVpnTopicEndpointTxFlowsResponse.md)
 - [MsgVpnTopicEndpointsResponse](docs/MsgVpnTopicEndpointsResponse.md)
 - [MsgVpnTransaction](docs/MsgVpnTransaction.md)
 - [MsgVpnTransactionCollections](docs/MsgVpnTransactionCollections.md)
 - [MsgVpnTransactionCollectionsConsumermsgs](docs/MsgVpnTransactionCollectionsConsumermsgs.md)
 - [MsgVpnTransactionCollectionsPublishermsgs](docs/MsgVpnTransactionCollectionsPublishermsgs.md)
 - [MsgVpnTransactionConsumerMsg](docs/MsgVpnTransactionConsumerMsg.md)
 - [MsgVpnTransactionConsumerMsgCollections](docs/MsgVpnTransactionConsumerMsgCollections.md)
 - [MsgVpnTransactionConsumerMsgLinks](docs/MsgVpnTransactionConsumerMsgLinks.md)
 - [MsgVpnTransactionConsumerMsgResponse](docs/MsgVpnTransactionConsumerMsgResponse.md)
 - [MsgVpnTransactionConsumerMsgsResponse](docs/MsgVpnTransactionConsumerMsgsResponse.md)
 - [MsgVpnTransactionLinks](docs/MsgVpnTransactionLinks.md)
 - [MsgVpnTransactionPublisherMsg](docs/MsgVpnTransactionPublisherMsg.md)
 - [MsgVpnTransactionPublisherMsgCollections](docs/MsgVpnTransactionPublisherMsgCollections.md)
 - [MsgVpnTransactionPublisherMsgLinks](docs/MsgVpnTransactionPublisherMsgLinks.md)
 - [MsgVpnTransactionPublisherMsgResponse](docs/MsgVpnTransactionPublisherMsgResponse.md)
 - [MsgVpnTransactionPublisherMsgsResponse](docs/MsgVpnTransactionPublisherMsgsResponse.md)
 - [MsgVpnTransactionResponse](docs/MsgVpnTransactionResponse.md)
 - [MsgVpnTransactionsResponse](docs/MsgVpnTransactionsResponse.md)
 - [MsgVpnsResponse](docs/MsgVpnsResponse.md)
 - [SempError](docs/SempError.md)
 - [SempMeta](docs/SempMeta.md)
 - [SempMetaOnlyResponse](docs/SempMetaOnlyResponse.md)
 - [SempPaging](docs/SempPaging.md)
 - [SempRequest](docs/SempRequest.md)
 - [Session](docs/Session.md)
 - [SessionCollections](docs/SessionCollections.md)
 - [SessionLinks](docs/SessionLinks.md)
 - [SessionResponse](docs/SessionResponse.md)
 - [SessionsResponse](docs/SessionsResponse.md)
 - [StandardDomainCertAuthoritiesResponse](docs/StandardDomainCertAuthoritiesResponse.md)
 - [StandardDomainCertAuthority](docs/StandardDomainCertAuthority.md)
 - [StandardDomainCertAuthorityCollections](docs/StandardDomainCertAuthorityCollections.md)
 - [StandardDomainCertAuthorityLinks](docs/StandardDomainCertAuthorityLinks.md)
 - [StandardDomainCertAuthorityResponse](docs/StandardDomainCertAuthorityResponse.md)
 - [VirtualHostname](docs/VirtualHostname.md)
 - [VirtualHostnameCollections](docs/VirtualHostnameCollections.md)
 - [VirtualHostnameLinks](docs/VirtualHostnameLinks.md)
 - [VirtualHostnameResponse](docs/VirtualHostnameResponse.md)
 - [VirtualHostnamesResponse](docs/VirtualHostnamesResponse.md)


## Documentation for Authorization

Authentication schemes defined for the API:
### basicAuth

- **Type**: HTTP basic authentication


## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author

support@solace.com

