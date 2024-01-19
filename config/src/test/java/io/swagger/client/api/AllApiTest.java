/*
 * SEMP (Solace Element Management Protocol)
 * SEMP (starting in `v2`, see note 1) is a RESTful API for configuring, monitoring, and administering a Solace PubSub+ broker.  SEMP uses URIs to address manageable **resources** of the Solace PubSub+ broker. Resources are individual **objects**, **collections** of objects, or (exclusively in the action API) **actions**. This document applies to the following API:   API|Base Path|Purpose|Comments :---|:---|:---|:--- Configuration|/SEMP/v2/config|Reading and writing config state|See note 2    The following APIs are also available:   API|Base Path|Purpose|Comments :---|:---|:---|:--- Action|/SEMP/v2/action|Performing actions|See note 2 Monitoring|/SEMP/v2/monitor|Querying operational parameters|See note 2    Resources are always nouns, with individual objects being singular and collections being plural.  Objects within a collection are identified by an `obj-id`, which follows the collection name with the form `collection-name/obj-id`.  Actions within an object are identified by an `action-id`, which follows the object name with the form `obj-id/action-id`.  Some examples:  ``` /SEMP/v2/config/msgVpns                        ; MsgVpn collection /SEMP/v2/config/msgVpns/a                      ; MsgVpn object named \"a\" /SEMP/v2/config/msgVpns/a/queues               ; Queue collection in MsgVpn \"a\" /SEMP/v2/config/msgVpns/a/queues/b             ; Queue object named \"b\" in MsgVpn \"a\" /SEMP/v2/action/msgVpns/a/queues/b/startReplay ; Action that starts a replay on Queue \"b\" in MsgVpn \"a\" /SEMP/v2/monitor/msgVpns/a/clients             ; Client collection in MsgVpn \"a\" /SEMP/v2/monitor/msgVpns/a/clients/c           ; Client object named \"c\" in MsgVpn \"a\" ```  ## Collection Resources  Collections are unordered lists of objects (unless described as otherwise), and are described by JSON arrays. Each item in the array represents an object in the same manner as the individual object would normally be represented. In the configuration API, the creation of a new object is done through its collection resource.  ## Object and Action Resources  Objects are composed of attributes, actions, collections, and other objects. They are described by JSON objects as name/value pairs. The collections and actions of an object are not contained directly in the object's JSON content; rather the content includes an attribute containing a URI which points to the collections and actions. These contained resources must be managed through this URI. At a minimum, every object has one or more identifying attributes, and its own `uri` attribute which contains the URI pointing to itself.  Actions are also composed of attributes, and are described by JSON objects as name/value pairs. Unlike objects, however, they are not members of a collection and cannot be retrieved, only performed. Actions only exist in the action API.  Attributes in an object or action may have any combination of the following properties:   Property|Meaning|Comments :---|:---|:--- Identifying|Attribute is involved in unique identification of the object, and appears in its URI| Required|Attribute must be provided in the request| Read-Only|Attribute can only be read, not written.|See note 3 Write-Only|Attribute can only be written, not read, unless the attribute is also opaque|See the documentation for the opaque property Requires-Disable|Attribute can only be changed when object is disabled| Deprecated|Attribute is deprecated, and will disappear in the next SEMP version| Opaque|Attribute can be set or retrieved in opaque form when the `opaquePassword` query parameter is present|See the `opaquePassword` query parameter documentation    In some requests, certain attributes may only be provided in certain combinations with other attributes:   Relationship|Meaning :---|:--- Requires|Attribute may only be changed by a request if a particular attribute or combination of attributes is also provided in the request Conflicts|Attribute may only be provided in a request if a particular attribute or combination of attributes is not also provided in the request    In the monitoring API, any non-identifying attribute may not be returned in a GET.  ## HTTP Methods  The following HTTP methods manipulate resources in accordance with these general principles. Note that some methods are only used in certain APIs:   Method|Resource|Meaning|Request Body|Response Body|Missing Request Attributes :---|:---|:---|:---|:---|:--- POST|Collection|Create object|Initial attribute values|Object attributes and metadata|Set to default PUT|Object|Create or replace object (see note 5)|New attribute values|Object attributes and metadata|Set to default, with certain exceptions (see note 4) PUT|Action|Performs action|Action arguments|Action metadata|N/A PATCH|Object|Update object|New attribute values|Object attributes and metadata|unchanged DELETE|Object|Delete object|Empty|Object metadata|N/A GET|Object|Get object|Empty|Object attributes and metadata|N/A GET|Collection|Get collection|Empty|Object attributes and collection metadata|N/A    ## Common Query Parameters  The following are some common query parameters that are supported by many method/URI combinations. Individual URIs may document additional parameters. Note that multiple query parameters can be used together in a single URI, separated by the ampersand character. For example:  ``` ; Request for the MsgVpns collection using two hypothetical query parameters ; \"q1\" and \"q2\" with values \"val1\" and \"val2\" respectively /SEMP/v2/config/msgVpns?q1=val1&q2=val2 ```  ### select  Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. Use this query parameter to limit the size of the returned data for each returned object, return only those fields that are desired, or exclude fields that are not desired.  The value of `select` is a comma-separated list of attribute names. If the list contains attribute names that are not prefaced by `-`, only those attributes are included in the response. If the list contains attribute names that are prefaced by `-`, those attributes are excluded from the response. If the list contains both types, then the difference of the first set of attributes and the second set of attributes is returned. If the list is empty (i.e. `select=`), no attributes are returned.  All attributes that are prefaced by `-` must follow all attributes that are not prefaced by `-`. In addition, each attribute name in the list must match at least one attribute in the object.  Names may include the `*` wildcard (zero or more characters). Nested attribute names are supported using periods (e.g. `parentName.childName`).  Some examples:  ``` ; List of all MsgVpn names /SEMP/v2/config/msgVpns?select=msgVpnName ; List of all MsgVpn and their attributes except for their names /SEMP/v2/config/msgVpns?select=-msgVpnName ; Authentication attributes of MsgVpn \"finance\" /SEMP/v2/config/msgVpns/finance?select=authentication* ; All attributes of MsgVpn \"finance\" except for authentication attributes /SEMP/v2/config/msgVpns/finance?select=-authentication* ; Access related attributes of Queue \"orderQ\" of MsgVpn \"finance\" /SEMP/v2/config/msgVpns/finance/queues/orderQ?select=owner,permission ```  ### where  Include in the response only objects where certain conditions are true. Use this query parameter to limit which objects are returned to those whose attribute values meet the given conditions.  The value of `where` is a comma-separated list of expressions. All expressions must be true for the object to be included in the response. Each expression takes the form:  ``` expression  = attribute-name OP value OP          = '==' | '!=' | '&lt;' | '&gt;' | '&lt;=' | '&gt;=' ```  `value` may be a number, string, `true`, or `false`, as appropriate for the type of `attribute-name`. Greater-than and less-than comparisons only work for numbers. A `*` in a string `value` is interpreted as a wildcard (zero or more characters). Some examples:  ``` ; Only enabled MsgVpns /SEMP/v2/config/msgVpns?where=enabled==true ; Only MsgVpns using basic non-LDAP authentication /SEMP/v2/config/msgVpns?where=authenticationBasicEnabled==true,authenticationBasicType!=ldap ; Only MsgVpns that allow more than 100 client connections /SEMP/v2/config/msgVpns?where=maxConnectionCount>100 ; Only MsgVpns with msgVpnName starting with \"B\": /SEMP/v2/config/msgVpns?where=msgVpnName==B* ```  ### count  Limit the count of objects in the response. This can be useful to limit the size of the response for large collections. The minimum value for `count` is `1` and the default is `10`. There is also a per-collection maximum value to limit request handling time.  `count` does not guarantee that a minimum number of objects will be returned. A page may contain fewer than `count` objects or even be empty. Additional objects may nonetheless be available for retrieval on subsequent pages. See the `cursor` query parameter documentation for more information on paging.  For example: ``` ; Up to 25 MsgVpns /SEMP/v2/config/msgVpns?count=25 ```  ### cursor  The cursor, or position, for the next page of objects. Cursors are opaque data that should not be created or interpreted by SEMP clients, and should only be used as described below.  When a request is made for a collection and there may be additional objects available for retrieval that are not included in the initial response, the response will include a `cursorQuery` field containing a cursor. The value of this field can be specified in the `cursor` query parameter of a subsequent request to retrieve the next page of objects. For convenience, an appropriate URI is constructed automatically by the broker and included in the `nextPageUri` field of the response. This URI can be used directly to retrieve the next page of objects.  Applications must continue to follow the `nextPageUri` if one is provided in order to retrieve the full set of objects associated with the request, even if a page contains fewer than the requested number of objects (see the `count` query parameter documentation) or is empty.  ### opaquePassword  Attributes with the opaque property are also write-only and so cannot normally be retrieved in a GET. However, when a password is provided in the `opaquePassword` query parameter, attributes with the opaque property are retrieved in a GET in opaque form, encrypted with this password. The query parameter can also be used on a POST, PATCH, or PUT to set opaque attributes using opaque attribute values retrieved in a GET, so long as:  1. the same password that was used to retrieve the opaque attribute values is provided; and  2. the broker to which the request is being sent has the same major and minor SEMP version as the broker that produced the opaque attribute values.  The password provided in the query parameter must be a minimum of 8 characters and a maximum of 128 characters.  The query parameter can only be used in the configuration API, and only over HTTPS.  ## Authentication  When a client makes its first SEMPv2 request, it must supply a username and password using HTTP Basic authentication.  If authentication is successful, the broker returns a cookie containing a session key. The client can omit the username and password from subsequent requests, because the broker now uses the session cookie for authentication instead. When the session expires or is deleted, the client must provide the username and password again, and the broker creates a new session.  There are a limited number of session slots available on the broker. The broker returns 529 No SEMP Session Available if it is not able to allocate a session. For this reason, all clients that use SEMPv2 should support cookies.  If certain attributes—such as a user's password—are changed, the broker automatically deletes the affected sessions. These attributes are documented below. However, changes in external user configuration data stored on a RADIUS or LDAP server do not trigger the broker to delete the associated session(s), therefore you must do this manually, if required.  A client can retrieve its current session information using the /about/user endpoint, delete its own session using the /about/user/logout endpoint, and manage all sessions using the /sessions endpoint.  ## Help  Visit [our website](https://solace.com) to learn more about Solace.  You can also download the SEMP API specifications by clicking [here](https://solace.com/downloads/).  If you need additional support, please contact us at [support@solace.com](mailto:support@solace.com).  ## Notes  Note|Description :---:|:--- 1|This specification defines SEMP starting in \"v2\", and not the original SEMP \"v1\" interface. Request and response formats between \"v1\" and \"v2\" are entirely incompatible, although both protocols share a common port configuration on the Solace PubSub+ broker. They are differentiated by the initial portion of the URI path, one of either \"/SEMP/\" or \"/SEMP/v2/\" 2|This API is partially implemented. Only a subset of all objects are available. 3|Read-only attributes may appear in POST and PUT/PATCH requests. However, if a read-only attribute is not marked as identifying, it will be ignored during a PUT/PATCH. 4|On a PUT, if the SEMP user is not authorized to modify the attribute, its value is left unchanged rather than set to default. In addition, the values of write-only attributes are not set to their defaults on a PUT, except in the following two cases: there is a mutual requires relationship with another non-write-only attribute, both attributes are absent from the request, and the non-write-only attribute is not currently set to its default value; or the attribute is also opaque and the `opaquePassword` query parameter is provided in the request. 5|On a PUT, if the object does not exist, it is created first.  
 *
 * OpenAPI spec version: 2.23
 * Contact: support@solace.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.client.api;

import io.swagger.client.model.AboutApiResponse;
import io.swagger.client.model.AboutResponse;
import io.swagger.client.model.AboutUserMsgVpnResponse;
import io.swagger.client.model.AboutUserMsgVpnsResponse;
import io.swagger.client.model.AboutUserResponse;
import io.swagger.client.model.Broker;
import io.swagger.client.model.BrokerResponse;
import io.swagger.client.model.CertAuthoritiesResponse;
import io.swagger.client.model.CertAuthority;
import io.swagger.client.model.CertAuthorityOcspTlsTrustedCommonName;
import io.swagger.client.model.CertAuthorityOcspTlsTrustedCommonNameResponse;
import io.swagger.client.model.CertAuthorityOcspTlsTrustedCommonNamesResponse;
import io.swagger.client.model.CertAuthorityResponse;
import io.swagger.client.model.ClientCertAuthoritiesResponse;
import io.swagger.client.model.ClientCertAuthority;
import io.swagger.client.model.ClientCertAuthorityOcspTlsTrustedCommonName;
import io.swagger.client.model.ClientCertAuthorityOcspTlsTrustedCommonNameResponse;
import io.swagger.client.model.ClientCertAuthorityOcspTlsTrustedCommonNamesResponse;
import io.swagger.client.model.ClientCertAuthorityResponse;
import io.swagger.client.model.DmrCluster;
import io.swagger.client.model.DmrClusterLink;
import io.swagger.client.model.DmrClusterLinkRemoteAddress;
import io.swagger.client.model.DmrClusterLinkRemoteAddressResponse;
import io.swagger.client.model.DmrClusterLinkRemoteAddressesResponse;
import io.swagger.client.model.DmrClusterLinkResponse;
import io.swagger.client.model.DmrClusterLinkTlsTrustedCommonName;
import io.swagger.client.model.DmrClusterLinkTlsTrustedCommonNameResponse;
import io.swagger.client.model.DmrClusterLinkTlsTrustedCommonNamesResponse;
import io.swagger.client.model.DmrClusterLinksResponse;
import io.swagger.client.model.DmrClusterResponse;
import io.swagger.client.model.DmrClustersResponse;
import io.swagger.client.model.DomainCertAuthoritiesResponse;
import io.swagger.client.model.DomainCertAuthority;
import io.swagger.client.model.DomainCertAuthorityResponse;
import io.swagger.client.model.MsgVpn;
import io.swagger.client.model.MsgVpnAclProfile;
import io.swagger.client.model.MsgVpnAclProfileClientConnectException;
import io.swagger.client.model.MsgVpnAclProfileClientConnectExceptionResponse;
import io.swagger.client.model.MsgVpnAclProfileClientConnectExceptionsResponse;
import io.swagger.client.model.MsgVpnAclProfilePublishException;
import io.swagger.client.model.MsgVpnAclProfilePublishExceptionResponse;
import io.swagger.client.model.MsgVpnAclProfilePublishExceptionsResponse;
import io.swagger.client.model.MsgVpnAclProfilePublishTopicException;
import io.swagger.client.model.MsgVpnAclProfilePublishTopicExceptionResponse;
import io.swagger.client.model.MsgVpnAclProfilePublishTopicExceptionsResponse;
import io.swagger.client.model.MsgVpnAclProfileResponse;
import io.swagger.client.model.MsgVpnAclProfileSubscribeException;
import io.swagger.client.model.MsgVpnAclProfileSubscribeExceptionResponse;
import io.swagger.client.model.MsgVpnAclProfileSubscribeExceptionsResponse;
import io.swagger.client.model.MsgVpnAclProfileSubscribeShareNameException;
import io.swagger.client.model.MsgVpnAclProfileSubscribeShareNameExceptionResponse;
import io.swagger.client.model.MsgVpnAclProfileSubscribeShareNameExceptionsResponse;
import io.swagger.client.model.MsgVpnAclProfileSubscribeTopicException;
import io.swagger.client.model.MsgVpnAclProfileSubscribeTopicExceptionResponse;
import io.swagger.client.model.MsgVpnAclProfileSubscribeTopicExceptionsResponse;
import io.swagger.client.model.MsgVpnAclProfilesResponse;
import io.swagger.client.model.MsgVpnAuthenticationOauthProvider;
import io.swagger.client.model.MsgVpnAuthenticationOauthProviderResponse;
import io.swagger.client.model.MsgVpnAuthenticationOauthProvidersResponse;
import io.swagger.client.model.MsgVpnAuthorizationGroup;
import io.swagger.client.model.MsgVpnAuthorizationGroupResponse;
import io.swagger.client.model.MsgVpnAuthorizationGroupsResponse;
import io.swagger.client.model.MsgVpnBridge;
import io.swagger.client.model.MsgVpnBridgeRemoteMsgVpn;
import io.swagger.client.model.MsgVpnBridgeRemoteMsgVpnResponse;
import io.swagger.client.model.MsgVpnBridgeRemoteMsgVpnsResponse;
import io.swagger.client.model.MsgVpnBridgeRemoteSubscription;
import io.swagger.client.model.MsgVpnBridgeRemoteSubscriptionResponse;
import io.swagger.client.model.MsgVpnBridgeRemoteSubscriptionsResponse;
import io.swagger.client.model.MsgVpnBridgeResponse;
import io.swagger.client.model.MsgVpnBridgeTlsTrustedCommonName;
import io.swagger.client.model.MsgVpnBridgeTlsTrustedCommonNameResponse;
import io.swagger.client.model.MsgVpnBridgeTlsTrustedCommonNamesResponse;
import io.swagger.client.model.MsgVpnBridgesResponse;
import io.swagger.client.model.MsgVpnClientProfile;
import io.swagger.client.model.MsgVpnClientProfileResponse;
import io.swagger.client.model.MsgVpnClientProfilesResponse;
import io.swagger.client.model.MsgVpnClientUsername;
import io.swagger.client.model.MsgVpnClientUsernameResponse;
import io.swagger.client.model.MsgVpnClientUsernamesResponse;
import io.swagger.client.model.MsgVpnDistributedCache;
import io.swagger.client.model.MsgVpnDistributedCacheCluster;
import io.swagger.client.model.MsgVpnDistributedCacheClusterGlobalCachingHomeCluster;
import io.swagger.client.model.MsgVpnDistributedCacheClusterGlobalCachingHomeClusterResponse;
import io.swagger.client.model.MsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefix;
import io.swagger.client.model.MsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixResponse;
import io.swagger.client.model.MsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixesResponse;
import io.swagger.client.model.MsgVpnDistributedCacheClusterGlobalCachingHomeClustersResponse;
import io.swagger.client.model.MsgVpnDistributedCacheClusterInstance;
import io.swagger.client.model.MsgVpnDistributedCacheClusterInstanceResponse;
import io.swagger.client.model.MsgVpnDistributedCacheClusterInstancesResponse;
import io.swagger.client.model.MsgVpnDistributedCacheClusterResponse;
import io.swagger.client.model.MsgVpnDistributedCacheClusterTopic;
import io.swagger.client.model.MsgVpnDistributedCacheClusterTopicResponse;
import io.swagger.client.model.MsgVpnDistributedCacheClusterTopicsResponse;
import io.swagger.client.model.MsgVpnDistributedCacheClustersResponse;
import io.swagger.client.model.MsgVpnDistributedCacheResponse;
import io.swagger.client.model.MsgVpnDistributedCachesResponse;
import io.swagger.client.model.MsgVpnDmrBridge;
import io.swagger.client.model.MsgVpnDmrBridgeResponse;
import io.swagger.client.model.MsgVpnDmrBridgesResponse;
import io.swagger.client.model.MsgVpnJndiConnectionFactoriesResponse;
import io.swagger.client.model.MsgVpnJndiConnectionFactory;
import io.swagger.client.model.MsgVpnJndiConnectionFactoryResponse;
import io.swagger.client.model.MsgVpnJndiQueue;
import io.swagger.client.model.MsgVpnJndiQueueResponse;
import io.swagger.client.model.MsgVpnJndiQueuesResponse;
import io.swagger.client.model.MsgVpnJndiTopic;
import io.swagger.client.model.MsgVpnJndiTopicResponse;
import io.swagger.client.model.MsgVpnJndiTopicsResponse;
import io.swagger.client.model.MsgVpnMqttRetainCache;
import io.swagger.client.model.MsgVpnMqttRetainCacheResponse;
import io.swagger.client.model.MsgVpnMqttRetainCachesResponse;
import io.swagger.client.model.MsgVpnMqttSession;
import io.swagger.client.model.MsgVpnMqttSessionResponse;
import io.swagger.client.model.MsgVpnMqttSessionSubscription;
import io.swagger.client.model.MsgVpnMqttSessionSubscriptionResponse;
import io.swagger.client.model.MsgVpnMqttSessionSubscriptionsResponse;
import io.swagger.client.model.MsgVpnMqttSessionsResponse;
import io.swagger.client.model.MsgVpnQueue;
import io.swagger.client.model.MsgVpnQueueResponse;
import io.swagger.client.model.MsgVpnQueueSubscription;
import io.swagger.client.model.MsgVpnQueueSubscriptionResponse;
import io.swagger.client.model.MsgVpnQueueSubscriptionsResponse;
import io.swagger.client.model.MsgVpnQueueTemplate;
import io.swagger.client.model.MsgVpnQueueTemplateResponse;
import io.swagger.client.model.MsgVpnQueueTemplatesResponse;
import io.swagger.client.model.MsgVpnQueuesResponse;
import io.swagger.client.model.MsgVpnReplayLog;
import io.swagger.client.model.MsgVpnReplayLogResponse;
import io.swagger.client.model.MsgVpnReplayLogsResponse;
import io.swagger.client.model.MsgVpnReplicatedTopic;
import io.swagger.client.model.MsgVpnReplicatedTopicResponse;
import io.swagger.client.model.MsgVpnReplicatedTopicsResponse;
import io.swagger.client.model.MsgVpnResponse;
import io.swagger.client.model.MsgVpnRestDeliveryPoint;
import io.swagger.client.model.MsgVpnRestDeliveryPointQueueBinding;
import io.swagger.client.model.MsgVpnRestDeliveryPointQueueBindingRequestHeader;
import io.swagger.client.model.MsgVpnRestDeliveryPointQueueBindingRequestHeaderResponse;
import io.swagger.client.model.MsgVpnRestDeliveryPointQueueBindingRequestHeadersResponse;
import io.swagger.client.model.MsgVpnRestDeliveryPointQueueBindingResponse;
import io.swagger.client.model.MsgVpnRestDeliveryPointQueueBindingsResponse;
import io.swagger.client.model.MsgVpnRestDeliveryPointResponse;
import io.swagger.client.model.MsgVpnRestDeliveryPointRestConsumer;
import io.swagger.client.model.MsgVpnRestDeliveryPointRestConsumerOauthJwtClaim;
import io.swagger.client.model.MsgVpnRestDeliveryPointRestConsumerOauthJwtClaimResponse;
import io.swagger.client.model.MsgVpnRestDeliveryPointRestConsumerOauthJwtClaimsResponse;
import io.swagger.client.model.MsgVpnRestDeliveryPointRestConsumerResponse;
import io.swagger.client.model.MsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonName;
import io.swagger.client.model.MsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNameResponse;
import io.swagger.client.model.MsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNamesResponse;
import io.swagger.client.model.MsgVpnRestDeliveryPointRestConsumersResponse;
import io.swagger.client.model.MsgVpnRestDeliveryPointsResponse;
import io.swagger.client.model.MsgVpnSequencedTopic;
import io.swagger.client.model.MsgVpnSequencedTopicResponse;
import io.swagger.client.model.MsgVpnSequencedTopicsResponse;
import io.swagger.client.model.MsgVpnTopicEndpoint;
import io.swagger.client.model.MsgVpnTopicEndpointResponse;
import io.swagger.client.model.MsgVpnTopicEndpointTemplate;
import io.swagger.client.model.MsgVpnTopicEndpointTemplateResponse;
import io.swagger.client.model.MsgVpnTopicEndpointTemplatesResponse;
import io.swagger.client.model.MsgVpnTopicEndpointsResponse;
import io.swagger.client.model.MsgVpnsResponse;
import io.swagger.client.model.SempMetaOnlyResponse;
import io.swagger.client.model.SystemInformationResponse;
import io.swagger.client.model.VirtualHostname;
import io.swagger.client.model.VirtualHostnameResponse;
import io.swagger.client.model.VirtualHostnamesResponse;
import org.junit.Test;
import org.junit.Ignore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for AllApi
 */
@Ignore
public class AllApiTest {

    private final AllApi api = new AllApi();

    
    /**
     * Create a Certificate Authority object.
     *
     * Create a Certificate Authority object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates via config-sync.  Clients can authenticate with the message broker over TLS by presenting a valid client certificate. The message broker authenticates the client certificate by constructing a full certificate chain (from the client certificate to intermediate CAs to a configured root CA). The intermediate CAs in this chain can be provided by the client, or configured in the message broker. The root CA must be configured on the message broker.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: certAuthorityName|x|x|||x| certContent|||||x| crlDayList|||||x| crlTimeList|||||x| crlUrl|||||x| ocspNonResponderCertEnabled|||||x| ocspOverrideUrl|||||x| ocspTimeout|||||x| revocationCheckEnabled|||||x|    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- CertAuthority|crlDayList|crlTimeList| CertAuthority|crlTimeList|crlDayList|    A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been deprecated since 2.19. Replaced by clientCertAuthorities and domainCertAuthorities.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createCertAuthorityTest() throws Exception {
        CertAuthority body = null;
        String opaquePassword = null;
        List<String> select = null;
        CertAuthorityResponse response = api.createCertAuthority(body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create an OCSP Responder Trusted Common Name object.
     *
     * Create an OCSP Responder Trusted Common Name object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates via config-sync.  When an OCSP override URL is configured, the OCSP responder will be required to sign the OCSP responses with certificates issued to these Trusted Common Names. A maximum of 8 common names can be configured as valid response signers.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: certAuthorityName|x||x||x| ocspTlsTrustedCommonName|x|x|||x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been deprecated since 2.19. Replaced by clientCertAuthorities.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createCertAuthorityOcspTlsTrustedCommonNameTest() throws Exception {
        String certAuthorityName = null;
        CertAuthorityOcspTlsTrustedCommonName body = null;
        String opaquePassword = null;
        List<String> select = null;
        CertAuthorityOcspTlsTrustedCommonNameResponse response = api.createCertAuthorityOcspTlsTrustedCommonName(certAuthorityName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Client Certificate Authority object.
     *
     * Create a Client Certificate Authority object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates via config-sync.  Clients can authenticate with the message broker over TLS by presenting a valid client certificate. The message broker authenticates the client certificate by constructing a full certificate chain (from the client certificate to intermediate CAs to a configured root CA). The intermediate CAs in this chain can be provided by the client, or configured in the message broker. The root CA must be configured on the message broker.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: certAuthorityName|x|x||||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- ClientCertAuthority|crlDayList|crlTimeList| ClientCertAuthority|crlTimeList|crlDayList|    A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been available since 2.19.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createClientCertAuthorityTest() throws Exception {
        ClientCertAuthority body = null;
        String opaquePassword = null;
        List<String> select = null;
        ClientCertAuthorityResponse response = api.createClientCertAuthority(body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create an OCSP Responder Trusted Common Name object.
     *
     * Create an OCSP Responder Trusted Common Name object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates via config-sync.  When an OCSP override URL is configured, the OCSP responder will be required to sign the OCSP responses with certificates issued to these Trusted Common Names. A maximum of 8 common names can be configured as valid response signers.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: certAuthorityName|x||x||| ocspTlsTrustedCommonName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been available since 2.19.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createClientCertAuthorityOcspTlsTrustedCommonNameTest() throws Exception {
        String certAuthorityName = null;
        ClientCertAuthorityOcspTlsTrustedCommonName body = null;
        String opaquePassword = null;
        List<String> select = null;
        ClientCertAuthorityOcspTlsTrustedCommonNameResponse response = api.createClientCertAuthorityOcspTlsTrustedCommonName(certAuthorityName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Cluster object.
     *
     * Create a Cluster object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates via config-sync.  A Cluster is a provisioned object on a message broker that contains global DMR configuration parameters.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: authenticationBasicPassword||||x||x authenticationClientCertContent||||x||x authenticationClientCertPassword||||x|| dmrClusterName|x|x|||| nodeName|||x||| tlsServerCertEnforceTrustedCommonNameEnabled|||||x|    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- DmrCluster|authenticationClientCertPassword|authenticationClientCertContent|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createDmrClusterTest() throws Exception {
        DmrCluster body = null;
        String opaquePassword = null;
        List<String> select = null;
        DmrClusterResponse response = api.createDmrCluster(body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Link object.
     *
     * Create a Link object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates via config-sync.  A Link connects nodes (either within a Cluster or between two different Clusters) and allows them to exchange topology information, subscriptions and data.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: authenticationBasicPassword||||x||x dmrClusterName|x||x||| remoteNodeName|x|x||||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createDmrClusterLinkTest() throws Exception {
        String dmrClusterName = null;
        DmrClusterLink body = null;
        String opaquePassword = null;
        List<String> select = null;
        DmrClusterLinkResponse response = api.createDmrClusterLink(dmrClusterName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Remote Address object.
     *
     * Create a Remote Address object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates via config-sync.  Each Remote Address, consisting of a FQDN or IP address and optional port, is used to connect to the remote node for this Link. Up to 4 addresses may be provided for each Link, and will be tried on a round-robin basis.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: dmrClusterName|x||x||| remoteAddress|x|x|||| remoteNodeName|x||x|||    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createDmrClusterLinkRemoteAddressTest() throws Exception {
        String dmrClusterName = null;
        String remoteNodeName = null;
        DmrClusterLinkRemoteAddress body = null;
        String opaquePassword = null;
        List<String> select = null;
        DmrClusterLinkRemoteAddressResponse response = api.createDmrClusterLinkRemoteAddress(dmrClusterName, remoteNodeName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Trusted Common Name object.
     *
     * Create a Trusted Common Name object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates via config-sync.  The Trusted Common Names for the Link are used by encrypted transports to verify the name in the certificate presented by the remote node. They must include the common name of the remote node&#39;s server certificate or client certificate, depending upon the initiator of the connection.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: dmrClusterName|x||x||x| remoteNodeName|x||x||x| tlsTrustedCommonName|x|x|||x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been deprecated since 2.18. Common Name validation has been replaced by Server Certificate Name validation.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createDmrClusterLinkTlsTrustedCommonNameTest() throws Exception {
        String dmrClusterName = null;
        String remoteNodeName = null;
        DmrClusterLinkTlsTrustedCommonName body = null;
        String opaquePassword = null;
        List<String> select = null;
        DmrClusterLinkTlsTrustedCommonNameResponse response = api.createDmrClusterLinkTlsTrustedCommonName(dmrClusterName, remoteNodeName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Domain Certificate Authority object.
     *
     * Create a Domain Certificate Authority object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates via config-sync.  Certificate Authorities trusted for domain verification.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: certAuthorityName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been available since 2.19.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createDomainCertAuthorityTest() throws Exception {
        DomainCertAuthority body = null;
        String opaquePassword = null;
        List<String> select = null;
        DomainCertAuthorityResponse response = api.createDomainCertAuthority(body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Message VPN object.
     *
     * Create a Message VPN object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates via config-sync.  Message VPNs (Virtual Private Networks) allow for the segregation of topic space and clients. They also group clients connecting to a network of message brokers, such that messages published within a particular group are only visible to that group&#39;s clients.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: bridgingTlsServerCertEnforceTrustedCommonNameEnabled|||||x| msgVpnName|x|x|||| replicationBridgeAuthenticationBasicPassword||||x||x replicationBridgeAuthenticationClientCertContent||||x||x replicationBridgeAuthenticationClientCertPassword||||x|| replicationEnabledQueueBehavior||||x|| restTlsServerCertEnforceTrustedCommonNameEnabled|||||x|    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent EventThresholdByValue|clearValue|setValue| EventThresholdByValue|setValue|clearValue| MsgVpn|authenticationBasicProfileName|authenticationBasicType| MsgVpn|authorizationProfileName|authorizationType| MsgVpn|eventPublishTopicFormatMqttEnabled|eventPublishTopicFormatSmfEnabled| MsgVpn|eventPublishTopicFormatSmfEnabled|eventPublishTopicFormatMqttEnabled| MsgVpn|replicationBridgeAuthenticationBasicClientUsername|replicationBridgeAuthenticationBasicPassword| MsgVpn|replicationBridgeAuthenticationBasicPassword|replicationBridgeAuthenticationBasicClientUsername| MsgVpn|replicationBridgeAuthenticationClientCertPassword|replicationBridgeAuthenticationClientCertContent| MsgVpn|replicationEnabledQueueBehavior|replicationEnabled|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnTest() throws Exception {
        MsgVpn body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnResponse response = api.createMsgVpn(body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create an ACL Profile object.
     *
     * Create an ACL Profile object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  An ACL Profile controls whether an authenticated client is permitted to establish a connection with the message broker or permitted to publish and subscribe to specific topics.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: aclProfileName|x|x|||| msgVpnName|x||x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnAclProfileTest() throws Exception {
        String msgVpnName = null;
        MsgVpnAclProfile body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAclProfileResponse response = api.createMsgVpnAclProfile(msgVpnName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Client Connect Exception object.
     *
     * Create a Client Connect Exception object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Client Connect Exception is an exception to the default action to take when a client using the ACL Profile connects to the Message VPN. Exceptions must be expressed as an IP address/netmask in CIDR form.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: aclProfileName|x||x||| clientConnectExceptionAddress|x|x|||| msgVpnName|x||x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnAclProfileClientConnectExceptionTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        MsgVpnAclProfileClientConnectException body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAclProfileClientConnectExceptionResponse response = api.createMsgVpnAclProfileClientConnectException(msgVpnName, aclProfileName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Publish Topic Exception object.
     *
     * Create a Publish Topic Exception object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Publish Topic Exception is an exception to the default action to take when a client using the ACL Profile publishes to a topic in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: aclProfileName|x||x||x| msgVpnName|x||x||x| publishExceptionTopic|x|x|||x| topicSyntax|x|x|||x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been deprecated since 2.14. Replaced by publishTopicExceptions.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnAclProfilePublishExceptionTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        MsgVpnAclProfilePublishException body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAclProfilePublishExceptionResponse response = api.createMsgVpnAclProfilePublishException(msgVpnName, aclProfileName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Publish Topic Exception object.
     *
     * Create a Publish Topic Exception object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Publish Topic Exception is an exception to the default action to take when a client using the ACL Profile publishes to a topic in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: aclProfileName|x||x||| msgVpnName|x||x||| publishTopicException|x|x|||| publishTopicExceptionSyntax|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnAclProfilePublishTopicExceptionTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        MsgVpnAclProfilePublishTopicException body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAclProfilePublishTopicExceptionResponse response = api.createMsgVpnAclProfilePublishTopicException(msgVpnName, aclProfileName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Subscribe Topic Exception object.
     *
     * Create a Subscribe Topic Exception object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Subscribe Topic Exception is an exception to the default action to take when a client using the ACL Profile subscribes to a topic in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: aclProfileName|x||x||x| msgVpnName|x||x||x| subscribeExceptionTopic|x|x|||x| topicSyntax|x|x|||x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been deprecated since 2.14. Replaced by subscribeTopicExceptions.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnAclProfileSubscribeExceptionTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        MsgVpnAclProfileSubscribeException body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAclProfileSubscribeExceptionResponse response = api.createMsgVpnAclProfileSubscribeException(msgVpnName, aclProfileName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Subscribe Share Name Exception object.
     *
     * Create a Subscribe Share Name Exception object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Subscribe Share Name Exception is an exception to the default action to take when a client using the ACL Profile subscribes to a share-name subscription in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: aclProfileName|x||x||| msgVpnName|x||x||| subscribeShareNameException|x|x|||| subscribeShareNameExceptionSyntax|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnAclProfileSubscribeShareNameExceptionTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        MsgVpnAclProfileSubscribeShareNameException body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAclProfileSubscribeShareNameExceptionResponse response = api.createMsgVpnAclProfileSubscribeShareNameException(msgVpnName, aclProfileName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Subscribe Topic Exception object.
     *
     * Create a Subscribe Topic Exception object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Subscribe Topic Exception is an exception to the default action to take when a client using the ACL Profile subscribes to a topic in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: aclProfileName|x||x||| msgVpnName|x||x||| subscribeTopicException|x|x|||| subscribeTopicExceptionSyntax|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnAclProfileSubscribeTopicExceptionTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        MsgVpnAclProfileSubscribeTopicException body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAclProfileSubscribeTopicExceptionResponse response = api.createMsgVpnAclProfileSubscribeTopicException(msgVpnName, aclProfileName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create an OAuth Provider object.
     *
     * Create an OAuth Provider object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  OAuth Providers contain information about the issuer of an OAuth token that is needed to validate the token and derive a client username from it.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x||x||| oauthProviderName|x|x|||| tokenIntrospectionPassword||||x||x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.13.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnAuthenticationOauthProviderTest() throws Exception {
        String msgVpnName = null;
        MsgVpnAuthenticationOauthProvider body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAuthenticationOauthProviderResponse response = api.createMsgVpnAuthenticationOauthProvider(msgVpnName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create an LDAP Authorization Group object.
     *
     * Create an LDAP Authorization Group object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  To use client authorization groups configured on an external LDAP server to provide client authorizations, LDAP Authorization Group objects must be created on the Message VPN that match the authorization groups provisioned on the LDAP server. These objects must be configured with the client profiles and ACL profiles that will be assigned to the clients that belong to those authorization groups. A newly created group is placed at the end of the group list which is the lowest priority.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: authorizationGroupName|x|x|||| msgVpnName|x||x||| orderAfterAuthorizationGroupName||||x|| orderBeforeAuthorizationGroupName||||x||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- MsgVpnAuthorizationGroup|orderAfterAuthorizationGroupName||orderBeforeAuthorizationGroupName MsgVpnAuthorizationGroup|orderBeforeAuthorizationGroupName||orderAfterAuthorizationGroupName    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnAuthorizationGroupTest() throws Exception {
        String msgVpnName = null;
        MsgVpnAuthorizationGroup body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAuthorizationGroupResponse response = api.createMsgVpnAuthorizationGroup(msgVpnName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Bridge object.
     *
     * Create a Bridge object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  Bridges can be used to link two Message VPNs so that messages published to one Message VPN that match the topic subscriptions set for the bridge are also delivered to the linked Message VPN.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: bridgeName|x|x|||| bridgeVirtualRouter|x|x|||| msgVpnName|x||x||| remoteAuthenticationBasicPassword||||x||x remoteAuthenticationClientCertContent||||x||x remoteAuthenticationClientCertPassword||||x||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- MsgVpnBridge|remoteAuthenticationBasicClientUsername|remoteAuthenticationBasicPassword| MsgVpnBridge|remoteAuthenticationBasicPassword|remoteAuthenticationBasicClientUsername| MsgVpnBridge|remoteAuthenticationClientCertPassword|remoteAuthenticationClientCertContent|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnBridgeTest() throws Exception {
        String msgVpnName = null;
        MsgVpnBridge body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnBridgeResponse response = api.createMsgVpnBridge(msgVpnName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Remote Message VPN object.
     *
     * Create a Remote Message VPN object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  The Remote Message VPN is the Message VPN that the Bridge connects to.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: bridgeName|x||x||| bridgeVirtualRouter|x||x||| msgVpnName|x||x||| password||||x||x remoteMsgVpnInterface|x||||| remoteMsgVpnLocation|x|x|||| remoteMsgVpnName|x|x||||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- MsgVpnBridgeRemoteMsgVpn|clientUsername|password| MsgVpnBridgeRemoteMsgVpn|password|clientUsername|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnBridgeRemoteMsgVpnTest() throws Exception {
        String msgVpnName = null;
        String bridgeName = null;
        String bridgeVirtualRouter = null;
        MsgVpnBridgeRemoteMsgVpn body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnBridgeRemoteMsgVpnResponse response = api.createMsgVpnBridgeRemoteMsgVpn(msgVpnName, bridgeName, bridgeVirtualRouter, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Remote Subscription object.
     *
     * Create a Remote Subscription object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Remote Subscription is a topic subscription used by the Message VPN Bridge to attract messages from the remote message broker.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: bridgeName|x||x||| bridgeVirtualRouter|x||x||| deliverAlwaysEnabled||x|||| msgVpnName|x||x||| remoteSubscriptionTopic|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnBridgeRemoteSubscriptionTest() throws Exception {
        String msgVpnName = null;
        String bridgeName = null;
        String bridgeVirtualRouter = null;
        MsgVpnBridgeRemoteSubscription body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnBridgeRemoteSubscriptionResponse response = api.createMsgVpnBridgeRemoteSubscription(msgVpnName, bridgeName, bridgeVirtualRouter, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Trusted Common Name object.
     *
     * Create a Trusted Common Name object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  The Trusted Common Names for the Bridge are used by encrypted transports to verify the name in the certificate presented by the remote node. They must include the common name of the remote node&#39;s server certificate or client certificate, depending upon the initiator of the connection.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: bridgeName|x||x||x| bridgeVirtualRouter|x||x||x| msgVpnName|x||x||x| tlsTrustedCommonName|x|x|||x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been deprecated since 2.18. Common Name validation has been replaced by Server Certificate Name validation.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnBridgeTlsTrustedCommonNameTest() throws Exception {
        String msgVpnName = null;
        String bridgeName = null;
        String bridgeVirtualRouter = null;
        MsgVpnBridgeTlsTrustedCommonName body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnBridgeTlsTrustedCommonNameResponse response = api.createMsgVpnBridgeTlsTrustedCommonName(msgVpnName, bridgeName, bridgeVirtualRouter, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Client Profile object.
     *
     * Create a Client Profile object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  Client Profiles are used to assign common configuration properties to clients that have been successfully authorized.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: allowCutThroughForwardingEnabled|||||x| apiQueueManagementCopyFromOnCreateName|||||x| apiTopicEndpointManagementCopyFromOnCreateName|||||x| clientProfileName|x|x|||| msgVpnName|x||x|||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent EventThresholdByPercent|clearPercent|setPercent| EventThresholdByPercent|setPercent|clearPercent|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnClientProfileTest() throws Exception {
        String msgVpnName = null;
        MsgVpnClientProfile body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnClientProfileResponse response = api.createMsgVpnClientProfile(msgVpnName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Client Username object.
     *
     * Create a Client Username object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  A client is only authorized to connect to a Message VPN that is associated with a Client Username that the client has been assigned.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: clientUsername|x|x|||| msgVpnName|x||x||| password||||x||x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnClientUsernameTest() throws Exception {
        String msgVpnName = null;
        MsgVpnClientUsername body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnClientUsernameResponse response = api.createMsgVpnClientUsername(msgVpnName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Distributed Cache object.
     *
     * Create a Distributed Cache object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Distributed Cache is a collection of one or more Cache Clusters that belong to the same Message VPN. Each Cache Cluster in a Distributed Cache is configured to subscribe to a different set of topics. This effectively divides up the configured topic space, to provide scaling to very large topic spaces or very high cached message throughput.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: cacheName|x|x|||| msgVpnName|x||x|||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- MsgVpnDistributedCache|scheduledDeleteMsgDayList|scheduledDeleteMsgTimeList| MsgVpnDistributedCache|scheduledDeleteMsgTimeList|scheduledDeleteMsgDayList|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnDistributedCacheTest() throws Exception {
        String msgVpnName = null;
        MsgVpnDistributedCache body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnDistributedCacheResponse response = api.createMsgVpnDistributedCache(msgVpnName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Cache Cluster object.
     *
     * Create a Cache Cluster object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Cache Cluster is a collection of one or more Cache Instances that subscribe to exactly the same topics. Cache Instances are grouped together in a Cache Cluster for the purpose of fault tolerance and load balancing. As published messages are received, the message broker message bus sends these live data messages to the Cache Instances in the Cache Cluster. This enables client cache requests to be served by any of Cache Instances in the Cache Cluster.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: cacheName|x||x||| clusterName|x|x|||| msgVpnName|x||x|||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThresholdByPercent|clearPercent|setPercent| EventThresholdByPercent|setPercent|clearPercent| EventThresholdByValue|clearValue|setValue| EventThresholdByValue|setValue|clearValue|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnDistributedCacheClusterTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        MsgVpnDistributedCacheCluster body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterResponse response = api.createMsgVpnDistributedCacheCluster(msgVpnName, cacheName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Home Cache Cluster object.
     *
     * Create a Home Cache Cluster object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Home Cache Cluster is a Cache Cluster that is the \&quot;definitive\&quot; Cache Cluster for a given topic in the context of the Global Caching feature.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: cacheName|x||x||| clusterName|x||x||| homeClusterName|x|x|||| msgVpnName|x||x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        MsgVpnDistributedCacheClusterGlobalCachingHomeCluster body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterGlobalCachingHomeClusterResponse response = api.createMsgVpnDistributedCacheClusterGlobalCachingHomeCluster(msgVpnName, cacheName, clusterName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Topic Prefix object.
     *
     * Create a Topic Prefix object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Topic Prefix is a prefix for a global topic that is available from the containing Home Cache Cluster.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: cacheName|x||x||| clusterName|x||x||| homeClusterName|x||x||| msgVpnName|x||x||| topicPrefix|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        String homeClusterName = null;
        MsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefix body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixResponse response = api.createMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefix(msgVpnName, cacheName, clusterName, homeClusterName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Cache Instance object.
     *
     * Create a Cache Instance object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Cache Instance is a single Cache process that belongs to a single Cache Cluster. A Cache Instance object provisioned on the broker is used to disseminate configuration information to the Cache process. Cache Instances listen for and cache live data messages that match the topic subscriptions configured for their parent Cache Cluster.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: cacheName|x||x||| clusterName|x||x||| instanceName|x|x|||| msgVpnName|x||x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnDistributedCacheClusterInstanceTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        MsgVpnDistributedCacheClusterInstance body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterInstanceResponse response = api.createMsgVpnDistributedCacheClusterInstance(msgVpnName, cacheName, clusterName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Topic object.
     *
     * Create a Topic object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  The Cache Instances that belong to the containing Cache Cluster will cache any messages published to topics that match a Topic Subscription.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: cacheName|x||x||| clusterName|x||x||| msgVpnName|x||x||| topic|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnDistributedCacheClusterTopicTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        MsgVpnDistributedCacheClusterTopic body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterTopicResponse response = api.createMsgVpnDistributedCacheClusterTopic(msgVpnName, cacheName, clusterName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a DMR Bridge object.
     *
     * Create a DMR Bridge object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  A DMR Bridge is required to establish a data channel over a corresponding external link to the remote node for a given Message VPN. Each DMR Bridge identifies which external link the Message VPN should use, and what the name of the equivalent Message VPN at the remote node is.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x||x||| remoteNodeName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnDmrBridgeTest() throws Exception {
        String msgVpnName = null;
        MsgVpnDmrBridge body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnDmrBridgeResponse response = api.createMsgVpnDmrBridge(msgVpnName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a JNDI Connection Factory object.
     *
     * Create a JNDI Connection Factory object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  The message broker provides an internal JNDI store for provisioned Connection Factory objects that clients can access through JNDI lookups.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: connectionFactoryName|x|x|||| msgVpnName|x||x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnJndiConnectionFactoryTest() throws Exception {
        String msgVpnName = null;
        MsgVpnJndiConnectionFactory body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnJndiConnectionFactoryResponse response = api.createMsgVpnJndiConnectionFactory(msgVpnName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a JNDI Queue object.
     *
     * Create a JNDI Queue object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  The message broker provides an internal JNDI store for provisioned Queue objects that clients can access through JNDI lookups.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x||x||| queueName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnJndiQueueTest() throws Exception {
        String msgVpnName = null;
        MsgVpnJndiQueue body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnJndiQueueResponse response = api.createMsgVpnJndiQueue(msgVpnName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a JNDI Topic object.
     *
     * Create a JNDI Topic object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  The message broker provides an internal JNDI store for provisioned Topic objects that clients can access through JNDI lookups.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x||x||| topicName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnJndiTopicTest() throws Exception {
        String msgVpnName = null;
        MsgVpnJndiTopic body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnJndiTopicResponse response = api.createMsgVpnJndiTopic(msgVpnName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create an MQTT Retain Cache object.
     *
     * Create an MQTT Retain Cache object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  Using MQTT retained messages allows publishing MQTT clients to indicate that a message must be stored for later delivery to subscribing clients when those subscribing clients add subscriptions matching the retained message&#39;s topic. An MQTT Retain Cache processes all retained messages for a Message VPN.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: cacheName|x|x|||| msgVpnName|x||x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnMqttRetainCacheTest() throws Exception {
        String msgVpnName = null;
        MsgVpnMqttRetainCache body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnMqttRetainCacheResponse response = api.createMsgVpnMqttRetainCache(msgVpnName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create an MQTT Session object.
     *
     * Create an MQTT Session object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  An MQTT Session object is a virtual representation of an MQTT client connection. An MQTT session holds the state of an MQTT client (that is, it is used to contain a client&#39;s QoS 0 and QoS 1 subscription sets and any undelivered QoS 1 messages).   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: mqttSessionClientId|x|x|||| mqttSessionVirtualRouter|x|x|||| msgVpnName|x||x|||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnMqttSessionTest() throws Exception {
        String msgVpnName = null;
        MsgVpnMqttSession body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnMqttSessionResponse response = api.createMsgVpnMqttSession(msgVpnName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Subscription object.
     *
     * Create a Subscription object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  An MQTT session contains a client&#39;s QoS 0 and QoS 1 subscription sets. On creation, a subscription defaults to QoS 0.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: mqttSessionClientId|x||x||| mqttSessionVirtualRouter|x||x||| msgVpnName|x||x||| subscriptionTopic|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnMqttSessionSubscriptionTest() throws Exception {
        String msgVpnName = null;
        String mqttSessionClientId = null;
        String mqttSessionVirtualRouter = null;
        MsgVpnMqttSessionSubscription body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnMqttSessionSubscriptionResponse response = api.createMsgVpnMqttSessionSubscription(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Queue object.
     *
     * Create a Queue object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Queue acts as both a destination that clients can publish messages to, and as an endpoint that clients can bind consumers to and consume messages from.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x||x||| queueName|x|x||||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnQueueTest() throws Exception {
        String msgVpnName = null;
        MsgVpnQueue body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnQueueResponse response = api.createMsgVpnQueue(msgVpnName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Queue Subscription object.
     *
     * Create a Queue Subscription object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  One or more Queue Subscriptions can be added to a durable queue so that Guaranteed messages published to matching topics are also delivered to and spooled by the queue.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x||x||| queueName|x||x||| subscriptionTopic|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnQueueSubscriptionTest() throws Exception {
        String msgVpnName = null;
        String queueName = null;
        MsgVpnQueueSubscription body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnQueueSubscriptionResponse response = api.createMsgVpnQueueSubscription(msgVpnName, queueName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Queue Template object.
     *
     * Create a Queue Template object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Queue Template provides a mechanism for specifying the initial state for client created queues.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x||x||| queueTemplateName|x|x||||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnQueueTemplateTest() throws Exception {
        String msgVpnName = null;
        MsgVpnQueueTemplate body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnQueueTemplateResponse response = api.createMsgVpnQueueTemplate(msgVpnName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Replay Log object.
     *
     * Create a Replay Log object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  When the Message Replay feature is enabled, message brokers store persistent messages in a Replay Log. These messages are kept until the log is full, after which the oldest messages are removed to free up space for new messages.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x||x||| replayLogName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.10.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnReplayLogTest() throws Exception {
        String msgVpnName = null;
        MsgVpnReplayLog body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnReplayLogResponse response = api.createMsgVpnReplayLog(msgVpnName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Replicated Topic object.
     *
     * Create a Replicated Topic object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  To indicate which messages should be replicated between the active and standby site, a Replicated Topic subscription must be configured on a Message VPN. If a published message matches both a replicated topic and an endpoint on the active site, then the message is replicated to the standby site.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x||x||| replicatedTopic|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnReplicatedTopicTest() throws Exception {
        String msgVpnName = null;
        MsgVpnReplicatedTopic body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnReplicatedTopicResponse response = api.createMsgVpnReplicatedTopic(msgVpnName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a REST Delivery Point object.
     *
     * Create a REST Delivery Point object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  A REST Delivery Point manages delivery of messages from queues to a named list of REST Consumers.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x||x||| restDeliveryPointName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnRestDeliveryPointTest() throws Exception {
        String msgVpnName = null;
        MsgVpnRestDeliveryPoint body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointResponse response = api.createMsgVpnRestDeliveryPoint(msgVpnName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Queue Binding object.
     *
     * Create a Queue Binding object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Queue Binding for a REST Delivery Point attracts messages to be delivered to REST consumers. If the queue does not exist it can be created subsequently, and once the queue is operational the broker performs the queue binding. Removing the queue binding does not delete the queue itself. Similarly, removing the queue does not remove the queue binding, which fails until the queue is recreated or the queue binding is deleted.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x||x||| queueBindingName|x|x|||| restDeliveryPointName|x||x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnRestDeliveryPointQueueBindingTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        MsgVpnRestDeliveryPointQueueBinding body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointQueueBindingResponse response = api.createMsgVpnRestDeliveryPointQueueBinding(msgVpnName, restDeliveryPointName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Request Header object.
     *
     * Create a Request Header object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  A request header to be added to the HTTP request.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: headerName|x|x|||| msgVpnName|x||x||| queueBindingName|x||x||| restDeliveryPointName|x||x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.23.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnRestDeliveryPointQueueBindingRequestHeaderTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String queueBindingName = null;
        MsgVpnRestDeliveryPointQueueBindingRequestHeader body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointQueueBindingRequestHeaderResponse response = api.createMsgVpnRestDeliveryPointQueueBindingRequestHeader(msgVpnName, restDeliveryPointName, queueBindingName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a REST Consumer object.
     *
     * Create a REST Consumer object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  REST Consumer objects establish HTTP connectivity to REST consumer applications who wish to receive messages from a broker.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: authenticationClientCertContent||||x||x authenticationClientCertPassword||||x|| authenticationHttpBasicPassword||||x||x authenticationHttpHeaderValue||||x||x authenticationOauthClientSecret||||x||x authenticationOauthJwtSecretKey||||x||x msgVpnName|x||x||| restConsumerName|x|x|||| restDeliveryPointName|x||x|||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- MsgVpnRestDeliveryPointRestConsumer|authenticationClientCertPassword|authenticationClientCertContent| MsgVpnRestDeliveryPointRestConsumer|authenticationHttpBasicPassword|authenticationHttpBasicUsername| MsgVpnRestDeliveryPointRestConsumer|authenticationHttpBasicUsername|authenticationHttpBasicPassword| MsgVpnRestDeliveryPointRestConsumer|remotePort|tlsEnabled| MsgVpnRestDeliveryPointRestConsumer|tlsEnabled|remotePort|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnRestDeliveryPointRestConsumerTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        MsgVpnRestDeliveryPointRestConsumer body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointRestConsumerResponse response = api.createMsgVpnRestDeliveryPointRestConsumer(msgVpnName, restDeliveryPointName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Claim object.
     *
     * Create a Claim object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Claim is added to the JWT sent to the OAuth token request endpoint.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x||x||| oauthJwtClaimName|x|x|||| oauthJwtClaimValue||x|||| restConsumerName|x||x||| restDeliveryPointName|x||x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.21.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnRestDeliveryPointRestConsumerOauthJwtClaimTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String restConsumerName = null;
        MsgVpnRestDeliveryPointRestConsumerOauthJwtClaim body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointRestConsumerOauthJwtClaimResponse response = api.createMsgVpnRestDeliveryPointRestConsumerOauthJwtClaim(msgVpnName, restDeliveryPointName, restConsumerName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Trusted Common Name object.
     *
     * Create a Trusted Common Name object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  The Trusted Common Names for the REST Consumer are used by encrypted transports to verify the name in the certificate presented by the remote REST consumer. They must include the common name of the remote REST consumer&#39;s server certificate.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x||x||x| restConsumerName|x||x||x| restDeliveryPointName|x||x||x| tlsTrustedCommonName|x|x|||x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been deprecated since 2.17. Common Name validation has been replaced by Server Certificate Name validation.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNameTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String restConsumerName = null;
        MsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonName body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNameResponse response = api.createMsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonName(msgVpnName, restDeliveryPointName, restConsumerName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Sequenced Topic object.
     *
     * Create a Sequenced Topic object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Sequenced Topic is a topic subscription for which any matching messages received on the Message VPN are assigned a sequence number that is monotonically increased by a value of one per message.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x||x||| sequencedTopic|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnSequencedTopicTest() throws Exception {
        String msgVpnName = null;
        MsgVpnSequencedTopic body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnSequencedTopicResponse response = api.createMsgVpnSequencedTopic(msgVpnName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Topic Endpoint object.
     *
     * Create a Topic Endpoint object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Topic Endpoint attracts messages published to a topic for which the Topic Endpoint has a matching topic subscription. The topic subscription for the Topic Endpoint is specified in the client request to bind a Flow to that Topic Endpoint. Queues are significantly more flexible than Topic Endpoints and are the recommended approach for most applications. The use of Topic Endpoints should be restricted to JMS applications.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x||x||| topicEndpointName|x|x||||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnTopicEndpointTest() throws Exception {
        String msgVpnName = null;
        MsgVpnTopicEndpoint body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnTopicEndpointResponse response = api.createMsgVpnTopicEndpoint(msgVpnName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Topic Endpoint Template object.
     *
     * Create a Topic Endpoint Template object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Topic Endpoint Template provides a mechanism for specifying the initial state for client created topic endpoints.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x||x||| topicEndpointTemplateName|x|x||||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnTopicEndpointTemplateTest() throws Exception {
        String msgVpnName = null;
        MsgVpnTopicEndpointTemplate body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnTopicEndpointTemplateResponse response = api.createMsgVpnTopicEndpointTemplate(msgVpnName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Create a Virtual Hostname object.
     *
     * Create a Virtual Hostname object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates via config-sync.  A Virtual Hostname is a provisioned object on a message broker that contains a Virtual Hostname to Message VPN mapping.  Clients which connect to a global (as opposed to per Message VPN) port and provides this hostname will be directed to its corresponding Message VPN. A case-insentive match is performed on the full client-provided hostname against the configured virtual-hostname.  This mechanism is only supported for hostnames provided through the Server Name Indication (SNI) extension of TLS.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: virtualHostname|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.17.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createVirtualHostnameTest() throws Exception {
        VirtualHostname body = null;
        String opaquePassword = null;
        List<String> select = null;
        VirtualHostnameResponse response = api.createVirtualHostname(body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Delete a Certificate Authority object.
     *
     * Delete a Certificate Authority object. The deletion of instances of this object are synchronized to HA mates via config-sync.  Clients can authenticate with the message broker over TLS by presenting a valid client certificate. The message broker authenticates the client certificate by constructing a full certificate chain (from the client certificate to intermediate CAs to a configured root CA). The intermediate CAs in this chain can be provided by the client, or configured in the message broker. The root CA must be configured on the message broker.  A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been deprecated since 2.19. Replaced by clientCertAuthorities and domainCertAuthorities.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteCertAuthorityTest() throws Exception {
        String certAuthorityName = null;
        SempMetaOnlyResponse response = api.deleteCertAuthority(certAuthorityName);

        // TODO: test validations
    }
    
    /**
     * Delete an OCSP Responder Trusted Common Name object.
     *
     * Delete an OCSP Responder Trusted Common Name object. The deletion of instances of this object are synchronized to HA mates via config-sync.  When an OCSP override URL is configured, the OCSP responder will be required to sign the OCSP responses with certificates issued to these Trusted Common Names. A maximum of 8 common names can be configured as valid response signers.  A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been deprecated since 2.19. Replaced by clientCertAuthorities.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteCertAuthorityOcspTlsTrustedCommonNameTest() throws Exception {
        String certAuthorityName = null;
        String ocspTlsTrustedCommonName = null;
        SempMetaOnlyResponse response = api.deleteCertAuthorityOcspTlsTrustedCommonName(certAuthorityName, ocspTlsTrustedCommonName);

        // TODO: test validations
    }
    
    /**
     * Delete a Client Certificate Authority object.
     *
     * Delete a Client Certificate Authority object. The deletion of instances of this object are synchronized to HA mates via config-sync.  Clients can authenticate with the message broker over TLS by presenting a valid client certificate. The message broker authenticates the client certificate by constructing a full certificate chain (from the client certificate to intermediate CAs to a configured root CA). The intermediate CAs in this chain can be provided by the client, or configured in the message broker. The root CA must be configured on the message broker.  A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been available since 2.19.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteClientCertAuthorityTest() throws Exception {
        String certAuthorityName = null;
        SempMetaOnlyResponse response = api.deleteClientCertAuthority(certAuthorityName);

        // TODO: test validations
    }
    
    /**
     * Delete an OCSP Responder Trusted Common Name object.
     *
     * Delete an OCSP Responder Trusted Common Name object. The deletion of instances of this object are synchronized to HA mates via config-sync.  When an OCSP override URL is configured, the OCSP responder will be required to sign the OCSP responses with certificates issued to these Trusted Common Names. A maximum of 8 common names can be configured as valid response signers.  A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been available since 2.19.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteClientCertAuthorityOcspTlsTrustedCommonNameTest() throws Exception {
        String certAuthorityName = null;
        String ocspTlsTrustedCommonName = null;
        SempMetaOnlyResponse response = api.deleteClientCertAuthorityOcspTlsTrustedCommonName(certAuthorityName, ocspTlsTrustedCommonName);

        // TODO: test validations
    }
    
    /**
     * Delete a Cluster object.
     *
     * Delete a Cluster object. The deletion of instances of this object are synchronized to HA mates via config-sync.  A Cluster is a provisioned object on a message broker that contains global DMR configuration parameters.  A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteDmrClusterTest() throws Exception {
        String dmrClusterName = null;
        SempMetaOnlyResponse response = api.deleteDmrCluster(dmrClusterName);

        // TODO: test validations
    }
    
    /**
     * Delete a Link object.
     *
     * Delete a Link object. The deletion of instances of this object are synchronized to HA mates via config-sync.  A Link connects nodes (either within a Cluster or between two different Clusters) and allows them to exchange topology information, subscriptions and data.  A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteDmrClusterLinkTest() throws Exception {
        String dmrClusterName = null;
        String remoteNodeName = null;
        SempMetaOnlyResponse response = api.deleteDmrClusterLink(dmrClusterName, remoteNodeName);

        // TODO: test validations
    }
    
    /**
     * Delete a Remote Address object.
     *
     * Delete a Remote Address object. The deletion of instances of this object are synchronized to HA mates via config-sync.  Each Remote Address, consisting of a FQDN or IP address and optional port, is used to connect to the remote node for this Link. Up to 4 addresses may be provided for each Link, and will be tried on a round-robin basis.  A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteDmrClusterLinkRemoteAddressTest() throws Exception {
        String dmrClusterName = null;
        String remoteNodeName = null;
        String remoteAddress = null;
        SempMetaOnlyResponse response = api.deleteDmrClusterLinkRemoteAddress(dmrClusterName, remoteNodeName, remoteAddress);

        // TODO: test validations
    }
    
    /**
     * Delete a Trusted Common Name object.
     *
     * Delete a Trusted Common Name object. The deletion of instances of this object are synchronized to HA mates via config-sync.  The Trusted Common Names for the Link are used by encrypted transports to verify the name in the certificate presented by the remote node. They must include the common name of the remote node&#39;s server certificate or client certificate, depending upon the initiator of the connection.  A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been deprecated since 2.18. Common Name validation has been replaced by Server Certificate Name validation.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteDmrClusterLinkTlsTrustedCommonNameTest() throws Exception {
        String dmrClusterName = null;
        String remoteNodeName = null;
        String tlsTrustedCommonName = null;
        SempMetaOnlyResponse response = api.deleteDmrClusterLinkTlsTrustedCommonName(dmrClusterName, remoteNodeName, tlsTrustedCommonName);

        // TODO: test validations
    }
    
    /**
     * Delete a Domain Certificate Authority object.
     *
     * Delete a Domain Certificate Authority object. The deletion of instances of this object are synchronized to HA mates via config-sync.  Certificate Authorities trusted for domain verification.  A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been available since 2.19.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteDomainCertAuthorityTest() throws Exception {
        String certAuthorityName = null;
        SempMetaOnlyResponse response = api.deleteDomainCertAuthority(certAuthorityName);

        // TODO: test validations
    }
    
    /**
     * Delete a Message VPN object.
     *
     * Delete a Message VPN object. The deletion of instances of this object are synchronized to HA mates via config-sync.  Message VPNs (Virtual Private Networks) allow for the segregation of topic space and clients. They also group clients connecting to a network of message brokers, such that messages published within a particular group are only visible to that group&#39;s clients.  A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnTest() throws Exception {
        String msgVpnName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpn(msgVpnName);

        // TODO: test validations
    }
    
    /**
     * Delete an ACL Profile object.
     *
     * Delete an ACL Profile object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  An ACL Profile controls whether an authenticated client is permitted to establish a connection with the message broker or permitted to publish and subscribe to specific topics.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnAclProfileTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnAclProfile(msgVpnName, aclProfileName);

        // TODO: test validations
    }
    
    /**
     * Delete a Client Connect Exception object.
     *
     * Delete a Client Connect Exception object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Client Connect Exception is an exception to the default action to take when a client using the ACL Profile connects to the Message VPN. Exceptions must be expressed as an IP address/netmask in CIDR form.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnAclProfileClientConnectExceptionTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        String clientConnectExceptionAddress = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnAclProfileClientConnectException(msgVpnName, aclProfileName, clientConnectExceptionAddress);

        // TODO: test validations
    }
    
    /**
     * Delete a Publish Topic Exception object.
     *
     * Delete a Publish Topic Exception object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Publish Topic Exception is an exception to the default action to take when a client using the ACL Profile publishes to a topic in the Message VPN. Exceptions must be expressed as a topic.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been deprecated since 2.14. Replaced by publishTopicExceptions.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnAclProfilePublishExceptionTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        String topicSyntax = null;
        String publishExceptionTopic = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnAclProfilePublishException(msgVpnName, aclProfileName, topicSyntax, publishExceptionTopic);

        // TODO: test validations
    }
    
    /**
     * Delete a Publish Topic Exception object.
     *
     * Delete a Publish Topic Exception object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Publish Topic Exception is an exception to the default action to take when a client using the ACL Profile publishes to a topic in the Message VPN. Exceptions must be expressed as a topic.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnAclProfilePublishTopicExceptionTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        String publishTopicExceptionSyntax = null;
        String publishTopicException = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnAclProfilePublishTopicException(msgVpnName, aclProfileName, publishTopicExceptionSyntax, publishTopicException);

        // TODO: test validations
    }
    
    /**
     * Delete a Subscribe Topic Exception object.
     *
     * Delete a Subscribe Topic Exception object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Subscribe Topic Exception is an exception to the default action to take when a client using the ACL Profile subscribes to a topic in the Message VPN. Exceptions must be expressed as a topic.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been deprecated since 2.14. Replaced by subscribeTopicExceptions.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnAclProfileSubscribeExceptionTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        String topicSyntax = null;
        String subscribeExceptionTopic = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnAclProfileSubscribeException(msgVpnName, aclProfileName, topicSyntax, subscribeExceptionTopic);

        // TODO: test validations
    }
    
    /**
     * Delete a Subscribe Share Name Exception object.
     *
     * Delete a Subscribe Share Name Exception object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Subscribe Share Name Exception is an exception to the default action to take when a client using the ACL Profile subscribes to a share-name subscription in the Message VPN. Exceptions must be expressed as a topic.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnAclProfileSubscribeShareNameExceptionTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        String subscribeShareNameExceptionSyntax = null;
        String subscribeShareNameException = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnAclProfileSubscribeShareNameException(msgVpnName, aclProfileName, subscribeShareNameExceptionSyntax, subscribeShareNameException);

        // TODO: test validations
    }
    
    /**
     * Delete a Subscribe Topic Exception object.
     *
     * Delete a Subscribe Topic Exception object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Subscribe Topic Exception is an exception to the default action to take when a client using the ACL Profile subscribes to a topic in the Message VPN. Exceptions must be expressed as a topic.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnAclProfileSubscribeTopicExceptionTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        String subscribeTopicExceptionSyntax = null;
        String subscribeTopicException = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnAclProfileSubscribeTopicException(msgVpnName, aclProfileName, subscribeTopicExceptionSyntax, subscribeTopicException);

        // TODO: test validations
    }
    
    /**
     * Delete an OAuth Provider object.
     *
     * Delete an OAuth Provider object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  OAuth Providers contain information about the issuer of an OAuth token that is needed to validate the token and derive a client username from it.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.13.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnAuthenticationOauthProviderTest() throws Exception {
        String msgVpnName = null;
        String oauthProviderName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnAuthenticationOauthProvider(msgVpnName, oauthProviderName);

        // TODO: test validations
    }
    
    /**
     * Delete an LDAP Authorization Group object.
     *
     * Delete an LDAP Authorization Group object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  To use client authorization groups configured on an external LDAP server to provide client authorizations, LDAP Authorization Group objects must be created on the Message VPN that match the authorization groups provisioned on the LDAP server. These objects must be configured with the client profiles and ACL profiles that will be assigned to the clients that belong to those authorization groups. A newly created group is placed at the end of the group list which is the lowest priority.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnAuthorizationGroupTest() throws Exception {
        String msgVpnName = null;
        String authorizationGroupName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnAuthorizationGroup(msgVpnName, authorizationGroupName);

        // TODO: test validations
    }
    
    /**
     * Delete a Bridge object.
     *
     * Delete a Bridge object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  Bridges can be used to link two Message VPNs so that messages published to one Message VPN that match the topic subscriptions set for the bridge are also delivered to the linked Message VPN.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnBridgeTest() throws Exception {
        String msgVpnName = null;
        String bridgeName = null;
        String bridgeVirtualRouter = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnBridge(msgVpnName, bridgeName, bridgeVirtualRouter);

        // TODO: test validations
    }
    
    /**
     * Delete a Remote Message VPN object.
     *
     * Delete a Remote Message VPN object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  The Remote Message VPN is the Message VPN that the Bridge connects to.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnBridgeRemoteMsgVpnTest() throws Exception {
        String msgVpnName = null;
        String bridgeName = null;
        String bridgeVirtualRouter = null;
        String remoteMsgVpnName = null;
        String remoteMsgVpnLocation = null;
        String remoteMsgVpnInterface = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnBridgeRemoteMsgVpn(msgVpnName, bridgeName, bridgeVirtualRouter, remoteMsgVpnName, remoteMsgVpnLocation, remoteMsgVpnInterface);

        // TODO: test validations
    }
    
    /**
     * Delete a Remote Subscription object.
     *
     * Delete a Remote Subscription object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Remote Subscription is a topic subscription used by the Message VPN Bridge to attract messages from the remote message broker.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnBridgeRemoteSubscriptionTest() throws Exception {
        String msgVpnName = null;
        String bridgeName = null;
        String bridgeVirtualRouter = null;
        String remoteSubscriptionTopic = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnBridgeRemoteSubscription(msgVpnName, bridgeName, bridgeVirtualRouter, remoteSubscriptionTopic);

        // TODO: test validations
    }
    
    /**
     * Delete a Trusted Common Name object.
     *
     * Delete a Trusted Common Name object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  The Trusted Common Names for the Bridge are used by encrypted transports to verify the name in the certificate presented by the remote node. They must include the common name of the remote node&#39;s server certificate or client certificate, depending upon the initiator of the connection.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been deprecated since 2.18. Common Name validation has been replaced by Server Certificate Name validation.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnBridgeTlsTrustedCommonNameTest() throws Exception {
        String msgVpnName = null;
        String bridgeName = null;
        String bridgeVirtualRouter = null;
        String tlsTrustedCommonName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnBridgeTlsTrustedCommonName(msgVpnName, bridgeName, bridgeVirtualRouter, tlsTrustedCommonName);

        // TODO: test validations
    }
    
    /**
     * Delete a Client Profile object.
     *
     * Delete a Client Profile object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  Client Profiles are used to assign common configuration properties to clients that have been successfully authorized.  A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnClientProfileTest() throws Exception {
        String msgVpnName = null;
        String clientProfileName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnClientProfile(msgVpnName, clientProfileName);

        // TODO: test validations
    }
    
    /**
     * Delete a Client Username object.
     *
     * Delete a Client Username object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  A client is only authorized to connect to a Message VPN that is associated with a Client Username that the client has been assigned.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnClientUsernameTest() throws Exception {
        String msgVpnName = null;
        String clientUsername = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnClientUsername(msgVpnName, clientUsername);

        // TODO: test validations
    }
    
    /**
     * Delete a Distributed Cache object.
     *
     * Delete a Distributed Cache object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Distributed Cache is a collection of one or more Cache Clusters that belong to the same Message VPN. Each Cache Cluster in a Distributed Cache is configured to subscribe to a different set of topics. This effectively divides up the configured topic space, to provide scaling to very large topic spaces or very high cached message throughput.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnDistributedCacheTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnDistributedCache(msgVpnName, cacheName);

        // TODO: test validations
    }
    
    /**
     * Delete a Cache Cluster object.
     *
     * Delete a Cache Cluster object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Cache Cluster is a collection of one or more Cache Instances that subscribe to exactly the same topics. Cache Instances are grouped together in a Cache Cluster for the purpose of fault tolerance and load balancing. As published messages are received, the message broker message bus sends these live data messages to the Cache Instances in the Cache Cluster. This enables client cache requests to be served by any of Cache Instances in the Cache Cluster.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnDistributedCacheClusterTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnDistributedCacheCluster(msgVpnName, cacheName, clusterName);

        // TODO: test validations
    }
    
    /**
     * Delete a Home Cache Cluster object.
     *
     * Delete a Home Cache Cluster object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Home Cache Cluster is a Cache Cluster that is the \&quot;definitive\&quot; Cache Cluster for a given topic in the context of the Global Caching feature.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        String homeClusterName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnDistributedCacheClusterGlobalCachingHomeCluster(msgVpnName, cacheName, clusterName, homeClusterName);

        // TODO: test validations
    }
    
    /**
     * Delete a Topic Prefix object.
     *
     * Delete a Topic Prefix object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Topic Prefix is a prefix for a global topic that is available from the containing Home Cache Cluster.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        String homeClusterName = null;
        String topicPrefix = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefix(msgVpnName, cacheName, clusterName, homeClusterName, topicPrefix);

        // TODO: test validations
    }
    
    /**
     * Delete a Cache Instance object.
     *
     * Delete a Cache Instance object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Cache Instance is a single Cache process that belongs to a single Cache Cluster. A Cache Instance object provisioned on the broker is used to disseminate configuration information to the Cache process. Cache Instances listen for and cache live data messages that match the topic subscriptions configured for their parent Cache Cluster.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnDistributedCacheClusterInstanceTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        String instanceName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnDistributedCacheClusterInstance(msgVpnName, cacheName, clusterName, instanceName);

        // TODO: test validations
    }
    
    /**
     * Delete a Topic object.
     *
     * Delete a Topic object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  The Cache Instances that belong to the containing Cache Cluster will cache any messages published to topics that match a Topic Subscription.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnDistributedCacheClusterTopicTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        String topic = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnDistributedCacheClusterTopic(msgVpnName, cacheName, clusterName, topic);

        // TODO: test validations
    }
    
    /**
     * Delete a DMR Bridge object.
     *
     * Delete a DMR Bridge object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  A DMR Bridge is required to establish a data channel over a corresponding external link to the remote node for a given Message VPN. Each DMR Bridge identifies which external link the Message VPN should use, and what the name of the equivalent Message VPN at the remote node is.  A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnDmrBridgeTest() throws Exception {
        String msgVpnName = null;
        String remoteNodeName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnDmrBridge(msgVpnName, remoteNodeName);

        // TODO: test validations
    }
    
    /**
     * Delete a JNDI Connection Factory object.
     *
     * Delete a JNDI Connection Factory object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  The message broker provides an internal JNDI store for provisioned Connection Factory objects that clients can access through JNDI lookups.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnJndiConnectionFactoryTest() throws Exception {
        String msgVpnName = null;
        String connectionFactoryName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnJndiConnectionFactory(msgVpnName, connectionFactoryName);

        // TODO: test validations
    }
    
    /**
     * Delete a JNDI Queue object.
     *
     * Delete a JNDI Queue object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  The message broker provides an internal JNDI store for provisioned Queue objects that clients can access through JNDI lookups.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnJndiQueueTest() throws Exception {
        String msgVpnName = null;
        String queueName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnJndiQueue(msgVpnName, queueName);

        // TODO: test validations
    }
    
    /**
     * Delete a JNDI Topic object.
     *
     * Delete a JNDI Topic object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  The message broker provides an internal JNDI store for provisioned Topic objects that clients can access through JNDI lookups.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnJndiTopicTest() throws Exception {
        String msgVpnName = null;
        String topicName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnJndiTopic(msgVpnName, topicName);

        // TODO: test validations
    }
    
    /**
     * Delete an MQTT Retain Cache object.
     *
     * Delete an MQTT Retain Cache object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  Using MQTT retained messages allows publishing MQTT clients to indicate that a message must be stored for later delivery to subscribing clients when those subscribing clients add subscriptions matching the retained message&#39;s topic. An MQTT Retain Cache processes all retained messages for a Message VPN.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnMqttRetainCacheTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnMqttRetainCache(msgVpnName, cacheName);

        // TODO: test validations
    }
    
    /**
     * Delete an MQTT Session object.
     *
     * Delete an MQTT Session object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  An MQTT Session object is a virtual representation of an MQTT client connection. An MQTT session holds the state of an MQTT client (that is, it is used to contain a client&#39;s QoS 0 and QoS 1 subscription sets and any undelivered QoS 1 messages).  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnMqttSessionTest() throws Exception {
        String msgVpnName = null;
        String mqttSessionClientId = null;
        String mqttSessionVirtualRouter = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnMqttSession(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter);

        // TODO: test validations
    }
    
    /**
     * Delete a Subscription object.
     *
     * Delete a Subscription object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  An MQTT session contains a client&#39;s QoS 0 and QoS 1 subscription sets. On creation, a subscription defaults to QoS 0.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnMqttSessionSubscriptionTest() throws Exception {
        String msgVpnName = null;
        String mqttSessionClientId = null;
        String mqttSessionVirtualRouter = null;
        String subscriptionTopic = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnMqttSessionSubscription(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, subscriptionTopic);

        // TODO: test validations
    }
    
    /**
     * Delete a Queue object.
     *
     * Delete a Queue object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Queue acts as both a destination that clients can publish messages to, and as an endpoint that clients can bind consumers to and consume messages from.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnQueueTest() throws Exception {
        String msgVpnName = null;
        String queueName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnQueue(msgVpnName, queueName);

        // TODO: test validations
    }
    
    /**
     * Delete a Queue Subscription object.
     *
     * Delete a Queue Subscription object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  One or more Queue Subscriptions can be added to a durable queue so that Guaranteed messages published to matching topics are also delivered to and spooled by the queue.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnQueueSubscriptionTest() throws Exception {
        String msgVpnName = null;
        String queueName = null;
        String subscriptionTopic = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnQueueSubscription(msgVpnName, queueName, subscriptionTopic);

        // TODO: test validations
    }
    
    /**
     * Delete a Queue Template object.
     *
     * Delete a Queue Template object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Queue Template provides a mechanism for specifying the initial state for client created queues.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnQueueTemplateTest() throws Exception {
        String msgVpnName = null;
        String queueTemplateName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnQueueTemplate(msgVpnName, queueTemplateName);

        // TODO: test validations
    }
    
    /**
     * Delete a Replay Log object.
     *
     * Delete a Replay Log object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  When the Message Replay feature is enabled, message brokers store persistent messages in a Replay Log. These messages are kept until the log is full, after which the oldest messages are removed to free up space for new messages.  A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.10.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnReplayLogTest() throws Exception {
        String msgVpnName = null;
        String replayLogName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnReplayLog(msgVpnName, replayLogName);

        // TODO: test validations
    }
    
    /**
     * Delete a Replicated Topic object.
     *
     * Delete a Replicated Topic object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  To indicate which messages should be replicated between the active and standby site, a Replicated Topic subscription must be configured on a Message VPN. If a published message matches both a replicated topic and an endpoint on the active site, then the message is replicated to the standby site.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnReplicatedTopicTest() throws Exception {
        String msgVpnName = null;
        String replicatedTopic = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnReplicatedTopic(msgVpnName, replicatedTopic);

        // TODO: test validations
    }
    
    /**
     * Delete a REST Delivery Point object.
     *
     * Delete a REST Delivery Point object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  A REST Delivery Point manages delivery of messages from queues to a named list of REST Consumers.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnRestDeliveryPointTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnRestDeliveryPoint(msgVpnName, restDeliveryPointName);

        // TODO: test validations
    }
    
    /**
     * Delete a Queue Binding object.
     *
     * Delete a Queue Binding object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Queue Binding for a REST Delivery Point attracts messages to be delivered to REST consumers. If the queue does not exist it can be created subsequently, and once the queue is operational the broker performs the queue binding. Removing the queue binding does not delete the queue itself. Similarly, removing the queue does not remove the queue binding, which fails until the queue is recreated or the queue binding is deleted.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnRestDeliveryPointQueueBindingTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String queueBindingName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnRestDeliveryPointQueueBinding(msgVpnName, restDeliveryPointName, queueBindingName);

        // TODO: test validations
    }
    
    /**
     * Delete a Request Header object.
     *
     * Delete a Request Header object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  A request header to be added to the HTTP request.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.23.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnRestDeliveryPointQueueBindingRequestHeaderTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String queueBindingName = null;
        String headerName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnRestDeliveryPointQueueBindingRequestHeader(msgVpnName, restDeliveryPointName, queueBindingName, headerName);

        // TODO: test validations
    }
    
    /**
     * Delete a REST Consumer object.
     *
     * Delete a REST Consumer object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  REST Consumer objects establish HTTP connectivity to REST consumer applications who wish to receive messages from a broker.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnRestDeliveryPointRestConsumerTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String restConsumerName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnRestDeliveryPointRestConsumer(msgVpnName, restDeliveryPointName, restConsumerName);

        // TODO: test validations
    }
    
    /**
     * Delete a Claim object.
     *
     * Delete a Claim object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Claim is added to the JWT sent to the OAuth token request endpoint.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.21.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnRestDeliveryPointRestConsumerOauthJwtClaimTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String restConsumerName = null;
        String oauthJwtClaimName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnRestDeliveryPointRestConsumerOauthJwtClaim(msgVpnName, restDeliveryPointName, restConsumerName, oauthJwtClaimName);

        // TODO: test validations
    }
    
    /**
     * Delete a Trusted Common Name object.
     *
     * Delete a Trusted Common Name object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  The Trusted Common Names for the REST Consumer are used by encrypted transports to verify the name in the certificate presented by the remote REST consumer. They must include the common name of the remote REST consumer&#39;s server certificate.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been deprecated since 2.17. Common Name validation has been replaced by Server Certificate Name validation.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNameTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String restConsumerName = null;
        String tlsTrustedCommonName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonName(msgVpnName, restDeliveryPointName, restConsumerName, tlsTrustedCommonName);

        // TODO: test validations
    }
    
    /**
     * Delete a Sequenced Topic object.
     *
     * Delete a Sequenced Topic object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Sequenced Topic is a topic subscription for which any matching messages received on the Message VPN are assigned a sequence number that is monotonically increased by a value of one per message.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnSequencedTopicTest() throws Exception {
        String msgVpnName = null;
        String sequencedTopic = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnSequencedTopic(msgVpnName, sequencedTopic);

        // TODO: test validations
    }
    
    /**
     * Delete a Topic Endpoint object.
     *
     * Delete a Topic Endpoint object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Topic Endpoint attracts messages published to a topic for which the Topic Endpoint has a matching topic subscription. The topic subscription for the Topic Endpoint is specified in the client request to bind a Flow to that Topic Endpoint. Queues are significantly more flexible than Topic Endpoints and are the recommended approach for most applications. The use of Topic Endpoints should be restricted to JMS applications.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnTopicEndpointTest() throws Exception {
        String msgVpnName = null;
        String topicEndpointName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnTopicEndpoint(msgVpnName, topicEndpointName);

        // TODO: test validations
    }
    
    /**
     * Delete a Topic Endpoint Template object.
     *
     * Delete a Topic Endpoint Template object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  A Topic Endpoint Template provides a mechanism for specifying the initial state for client created topic endpoints.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnTopicEndpointTemplateTest() throws Exception {
        String msgVpnName = null;
        String topicEndpointTemplateName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnTopicEndpointTemplate(msgVpnName, topicEndpointTemplateName);

        // TODO: test validations
    }
    
    /**
     * Delete a Virtual Hostname object.
     *
     * Delete a Virtual Hostname object. The deletion of instances of this object are synchronized to HA mates via config-sync.  A Virtual Hostname is a provisioned object on a message broker that contains a Virtual Hostname to Message VPN mapping.  Clients which connect to a global (as opposed to per Message VPN) port and provides this hostname will be directed to its corresponding Message VPN. A case-insentive match is performed on the full client-provided hostname against the configured virtual-hostname.  This mechanism is only supported for hostnames provided through the Server Name Indication (SNI) extension of TLS.  A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.17.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteVirtualHostnameTest() throws Exception {
        String virtualHostname = null;
        SempMetaOnlyResponse response = api.deleteVirtualHostname(virtualHostname);

        // TODO: test validations
    }
    
    /**
     * Get an About object.
     *
     * Get an About object.  This provides metadata about the SEMP API, such as the version of the API supported by the broker.    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation.  This has been available since 2.13.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getAboutTest() throws Exception {
        String opaquePassword = null;
        List<String> select = null;
        AboutResponse response = api.getAbout(opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get an API Description object.
     *
     * Get an API Description object.  The API Description object provides metadata about the SEMP API.    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getAboutApiTest() throws Exception {
        String opaquePassword = null;
        List<String> select = null;
        AboutApiResponse response = api.getAboutApi(opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a User object.
     *
     * Get a User object.  Session and access level information about the user accessing the SEMP API.    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getAboutUserTest() throws Exception {
        String opaquePassword = null;
        List<String> select = null;
        AboutUserResponse response = api.getAboutUser(opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a User Message VPN object.
     *
     * Get a User Message VPN object.  This provides information about the Message VPN access level for the username used to access the SEMP API.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getAboutUserMsgVpnTest() throws Exception {
        String msgVpnName = null;
        String opaquePassword = null;
        List<String> select = null;
        AboutUserMsgVpnResponse response = api.getAboutUserMsgVpn(msgVpnName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of User Message VPN objects.
     *
     * Get a list of User Message VPN objects.  This provides information about the Message VPN access level for the username used to access the SEMP API.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getAboutUserMsgVpnsTest() throws Exception {
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        AboutUserMsgVpnsResponse response = api.getAboutUserMsgVpns(count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Broker object.
     *
     * Get a Broker object.  This object contains global configuration for the message broker.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: tlsServerCertContent||x||x tlsServerCertPassword||x||    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation. Requests which include the following attributes require greater access scope/level:   Attribute|Access Scope/Level :---|:---: configSyncAuthenticationClientCertMaxChainDepth|global/read-only configSyncAuthenticationClientCertValidateDateEnabled|global/read-only configSyncClientProfileTcpInitialCongestionWindow|global/read-only configSyncClientProfileTcpKeepaliveCount|global/read-only configSyncClientProfileTcpKeepaliveIdle|global/read-only configSyncClientProfileTcpKeepaliveInterval|global/read-only configSyncClientProfileTcpMaxWindow|global/read-only configSyncClientProfileTcpMss|global/read-only configSyncEnabled|global/read-only configSyncSynchronizeUsernameEnabled|global/read-only configSyncTlsEnabled|global/read-only guaranteedMsgingDiskArrayWwn|global/read-only guaranteedMsgingDiskLocation|global/read-only guaranteedMsgingEnabled|global/read-only guaranteedMsgingEventCacheUsageThreshold.clearPercent|global/read-only guaranteedMsgingEventCacheUsageThreshold.clearValue|global/read-only guaranteedMsgingEventCacheUsageThreshold.setPercent|global/read-only guaranteedMsgingEventCacheUsageThreshold.setValue|global/read-only guaranteedMsgingEventDeliveredUnackedThreshold.clearPercent|global/read-only guaranteedMsgingEventDeliveredUnackedThreshold.setPercent|global/read-only guaranteedMsgingEventDiskUsageThreshold.clearPercent|global/read-only guaranteedMsgingEventDiskUsageThreshold.setPercent|global/read-only guaranteedMsgingEventEgressFlowCountThreshold.clearPercent|global/read-only guaranteedMsgingEventEgressFlowCountThreshold.clearValue|global/read-only guaranteedMsgingEventEgressFlowCountThreshold.setPercent|global/read-only guaranteedMsgingEventEgressFlowCountThreshold.setValue|global/read-only guaranteedMsgingEventEndpointCountThreshold.clearPercent|global/read-only guaranteedMsgingEventEndpointCountThreshold.clearValue|global/read-only guaranteedMsgingEventEndpointCountThreshold.setPercent|global/read-only guaranteedMsgingEventEndpointCountThreshold.setValue|global/read-only guaranteedMsgingEventIngressFlowCountThreshold.clearPercent|global/read-only guaranteedMsgingEventIngressFlowCountThreshold.clearValue|global/read-only guaranteedMsgingEventIngressFlowCountThreshold.setPercent|global/read-only guaranteedMsgingEventIngressFlowCountThreshold.setValue|global/read-only guaranteedMsgingEventMsgCountThreshold.clearPercent|global/read-only guaranteedMsgingEventMsgCountThreshold.setPercent|global/read-only guaranteedMsgingEventMsgSpoolFileCountThreshold.clearPercent|global/read-only guaranteedMsgingEventMsgSpoolFileCountThreshold.setPercent|global/read-only guaranteedMsgingEventMsgSpoolUsageThreshold.clearPercent|global/read-only guaranteedMsgingEventMsgSpoolUsageThreshold.clearValue|global/read-only guaranteedMsgingEventMsgSpoolUsageThreshold.setPercent|global/read-only guaranteedMsgingEventMsgSpoolUsageThreshold.setValue|global/read-only guaranteedMsgingEventTransactedSessionCountThreshold.clearPercent|global/read-only guaranteedMsgingEventTransactedSessionCountThreshold.clearValue|global/read-only guaranteedMsgingEventTransactedSessionCountThreshold.setPercent|global/read-only guaranteedMsgingEventTransactedSessionCountThreshold.setValue|global/read-only guaranteedMsgingEventTransactedSessionResourceCountThreshold.clearPercent|global/read-only guaranteedMsgingEventTransactedSessionResourceCountThreshold.setPercent|global/read-only guaranteedMsgingEventTransactionCountThreshold.clearPercent|global/read-only guaranteedMsgingEventTransactionCountThreshold.clearValue|global/read-only guaranteedMsgingEventTransactionCountThreshold.setPercent|global/read-only guaranteedMsgingEventTransactionCountThreshold.setValue|global/read-only guaranteedMsgingMaxCacheUsage|global/read-only guaranteedMsgingMaxMsgSpoolUsage|global/read-only guaranteedMsgingTransactionReplicationCompatibilityMode|global/read-only guaranteedMsgingVirtualRouterWhenActiveActive|global/read-only serviceAmqpEnabled|global/read-only serviceAmqpTlsListenPort|global/read-only serviceEventConnectionCountThreshold.clearPercent|global/read-only serviceEventConnectionCountThreshold.clearValue|global/read-only serviceEventConnectionCountThreshold.setPercent|global/read-only serviceEventConnectionCountThreshold.setValue|global/read-only serviceHealthCheckEnabled|global/read-only serviceHealthCheckListenPort|global/read-only serviceMqttEnabled|global/read-only serviceMsgBackboneEnabled|global/read-only serviceRestEventOutgoingConnectionCountThreshold.clearPercent|global/read-only serviceRestEventOutgoingConnectionCountThreshold.clearValue|global/read-only serviceRestEventOutgoingConnectionCountThreshold.setPercent|global/read-only serviceRestEventOutgoingConnectionCountThreshold.setValue|global/read-only serviceRestIncomingEnabled|global/read-only serviceRestOutgoingEnabled|global/read-only serviceSempLegacyTimeoutEnabled|global/read-only serviceSempPlainTextEnabled|global/read-only serviceSempPlainTextListenPort|global/read-only serviceSempSessionIdleTimeout|global/read-only serviceSempSessionMaxLifetime|global/read-only serviceSempTlsEnabled|global/read-only serviceSempTlsListenPort|global/read-only serviceSmfCompressionListenPort|global/read-only serviceSmfEnabled|global/read-only serviceSmfEventConnectionCountThreshold.clearPercent|global/read-only serviceSmfEventConnectionCountThreshold.clearValue|global/read-only serviceSmfEventConnectionCountThreshold.setPercent|global/read-only serviceSmfEventConnectionCountThreshold.setValue|global/read-only serviceSmfPlainTextListenPort|global/read-only serviceSmfRoutingControlListenPort|global/read-only serviceSmfTlsListenPort|global/read-only serviceTlsEventConnectionCountThreshold.clearPercent|global/read-only serviceTlsEventConnectionCountThreshold.clearValue|global/read-only serviceTlsEventConnectionCountThreshold.setPercent|global/read-only serviceTlsEventConnectionCountThreshold.setValue|global/read-only serviceWebTransportEnabled|global/read-only serviceWebTransportPlainTextListenPort|global/read-only serviceWebTransportTlsListenPort|global/read-only serviceWebTransportWebUrlSuffix|global/read-only tlsBlockVersion10Enabled|global/read-only tlsBlockVersion11Enabled|global/read-only tlsCipherSuiteManagementList|global/read-only tlsCipherSuiteMsgBackboneList|global/read-only tlsCipherSuiteSecureShellList|global/read-only tlsCrimeExploitProtectionEnabled|global/read-only tlsServerCertContent|global/read-only tlsStandardDomainCertificateAuthoritiesEnabled|vpn/read-only tlsTicketLifetime|global/read-only    This has been available since 2.13.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getBrokerTest() throws Exception {
        String opaquePassword = null;
        List<String> select = null;
        BrokerResponse response = api.getBroker(opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Certificate Authority objects.
     *
     * Get a list of Certificate Authority objects.  Clients can authenticate with the message broker over TLS by presenting a valid client certificate. The message broker authenticates the client certificate by constructing a full certificate chain (from the client certificate to intermediate CAs to a configured root CA). The intermediate CAs in this chain can be provided by the client, or configured in the message broker. The root CA must be configured on the message broker.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: certAuthorityName|x||x| certContent|||x| crlDayList|||x| crlTimeList|||x| crlUrl|||x| ocspNonResponderCertEnabled|||x| ocspOverrideUrl|||x| ocspTimeout|||x| revocationCheckEnabled|||x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.19. Replaced by clientCertAuthorities and domainCertAuthorities.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getCertAuthoritiesTest() throws Exception {
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        CertAuthoritiesResponse response = api.getCertAuthorities(count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Certificate Authority object.
     *
     * Get a Certificate Authority object.  Clients can authenticate with the message broker over TLS by presenting a valid client certificate. The message broker authenticates the client certificate by constructing a full certificate chain (from the client certificate to intermediate CAs to a configured root CA). The intermediate CAs in this chain can be provided by the client, or configured in the message broker. The root CA must be configured on the message broker.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: certAuthorityName|x||x| certContent|||x| crlDayList|||x| crlTimeList|||x| crlUrl|||x| ocspNonResponderCertEnabled|||x| ocspOverrideUrl|||x| ocspTimeout|||x| revocationCheckEnabled|||x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.19. Replaced by clientCertAuthorities and domainCertAuthorities.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getCertAuthorityTest() throws Exception {
        String certAuthorityName = null;
        String opaquePassword = null;
        List<String> select = null;
        CertAuthorityResponse response = api.getCertAuthority(certAuthorityName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get an OCSP Responder Trusted Common Name object.
     *
     * Get an OCSP Responder Trusted Common Name object.  When an OCSP override URL is configured, the OCSP responder will be required to sign the OCSP responses with certificates issued to these Trusted Common Names. A maximum of 8 common names can be configured as valid response signers.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: certAuthorityName|x||x| ocspTlsTrustedCommonName|x||x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.19. Replaced by clientCertAuthorities.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getCertAuthorityOcspTlsTrustedCommonNameTest() throws Exception {
        String certAuthorityName = null;
        String ocspTlsTrustedCommonName = null;
        String opaquePassword = null;
        List<String> select = null;
        CertAuthorityOcspTlsTrustedCommonNameResponse response = api.getCertAuthorityOcspTlsTrustedCommonName(certAuthorityName, ocspTlsTrustedCommonName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of OCSP Responder Trusted Common Name objects.
     *
     * Get a list of OCSP Responder Trusted Common Name objects.  When an OCSP override URL is configured, the OCSP responder will be required to sign the OCSP responses with certificates issued to these Trusted Common Names. A maximum of 8 common names can be configured as valid response signers.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: certAuthorityName|x||x| ocspTlsTrustedCommonName|x||x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.19. Replaced by clientCertAuthorities.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getCertAuthorityOcspTlsTrustedCommonNamesTest() throws Exception {
        String certAuthorityName = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        CertAuthorityOcspTlsTrustedCommonNamesResponse response = api.getCertAuthorityOcspTlsTrustedCommonNames(certAuthorityName, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Client Certificate Authority objects.
     *
     * Get a list of Client Certificate Authority objects.  Clients can authenticate with the message broker over TLS by presenting a valid client certificate. The message broker authenticates the client certificate by constructing a full certificate chain (from the client certificate to intermediate CAs to a configured root CA). The intermediate CAs in this chain can be provided by the client, or configured in the message broker. The root CA must be configured on the message broker.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: certAuthorityName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.19.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getClientCertAuthoritiesTest() throws Exception {
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        ClientCertAuthoritiesResponse response = api.getClientCertAuthorities(count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Client Certificate Authority object.
     *
     * Get a Client Certificate Authority object.  Clients can authenticate with the message broker over TLS by presenting a valid client certificate. The message broker authenticates the client certificate by constructing a full certificate chain (from the client certificate to intermediate CAs to a configured root CA). The intermediate CAs in this chain can be provided by the client, or configured in the message broker. The root CA must be configured on the message broker.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: certAuthorityName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.19.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getClientCertAuthorityTest() throws Exception {
        String certAuthorityName = null;
        String opaquePassword = null;
        List<String> select = null;
        ClientCertAuthorityResponse response = api.getClientCertAuthority(certAuthorityName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get an OCSP Responder Trusted Common Name object.
     *
     * Get an OCSP Responder Trusted Common Name object.  When an OCSP override URL is configured, the OCSP responder will be required to sign the OCSP responses with certificates issued to these Trusted Common Names. A maximum of 8 common names can be configured as valid response signers.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: certAuthorityName|x||| ocspTlsTrustedCommonName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.19.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getClientCertAuthorityOcspTlsTrustedCommonNameTest() throws Exception {
        String certAuthorityName = null;
        String ocspTlsTrustedCommonName = null;
        String opaquePassword = null;
        List<String> select = null;
        ClientCertAuthorityOcspTlsTrustedCommonNameResponse response = api.getClientCertAuthorityOcspTlsTrustedCommonName(certAuthorityName, ocspTlsTrustedCommonName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of OCSP Responder Trusted Common Name objects.
     *
     * Get a list of OCSP Responder Trusted Common Name objects.  When an OCSP override URL is configured, the OCSP responder will be required to sign the OCSP responses with certificates issued to these Trusted Common Names. A maximum of 8 common names can be configured as valid response signers.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: certAuthorityName|x||| ocspTlsTrustedCommonName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.19.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getClientCertAuthorityOcspTlsTrustedCommonNamesTest() throws Exception {
        String certAuthorityName = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        ClientCertAuthorityOcspTlsTrustedCommonNamesResponse response = api.getClientCertAuthorityOcspTlsTrustedCommonNames(certAuthorityName, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Cluster object.
     *
     * Get a Cluster object.  A Cluster is a provisioned object on a message broker that contains global DMR configuration parameters.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: authenticationBasicPassword||x||x authenticationClientCertContent||x||x authenticationClientCertPassword||x|| dmrClusterName|x||| tlsServerCertEnforceTrustedCommonNameEnabled|||x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getDmrClusterTest() throws Exception {
        String dmrClusterName = null;
        String opaquePassword = null;
        List<String> select = null;
        DmrClusterResponse response = api.getDmrCluster(dmrClusterName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a Link object.
     *
     * Get a Link object.  A Link connects nodes (either within a Cluster or between two different Clusters) and allows them to exchange topology information, subscriptions and data.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: authenticationBasicPassword||x||x dmrClusterName|x||| remoteNodeName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getDmrClusterLinkTest() throws Exception {
        String dmrClusterName = null;
        String remoteNodeName = null;
        String opaquePassword = null;
        List<String> select = null;
        DmrClusterLinkResponse response = api.getDmrClusterLink(dmrClusterName, remoteNodeName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a Remote Address object.
     *
     * Get a Remote Address object.  Each Remote Address, consisting of a FQDN or IP address and optional port, is used to connect to the remote node for this Link. Up to 4 addresses may be provided for each Link, and will be tried on a round-robin basis.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: dmrClusterName|x||| remoteAddress|x||| remoteNodeName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getDmrClusterLinkRemoteAddressTest() throws Exception {
        String dmrClusterName = null;
        String remoteNodeName = null;
        String remoteAddress = null;
        String opaquePassword = null;
        List<String> select = null;
        DmrClusterLinkRemoteAddressResponse response = api.getDmrClusterLinkRemoteAddress(dmrClusterName, remoteNodeName, remoteAddress, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Remote Address objects.
     *
     * Get a list of Remote Address objects.  Each Remote Address, consisting of a FQDN or IP address and optional port, is used to connect to the remote node for this Link. Up to 4 addresses may be provided for each Link, and will be tried on a round-robin basis.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: dmrClusterName|x||| remoteAddress|x||| remoteNodeName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getDmrClusterLinkRemoteAddressesTest() throws Exception {
        String dmrClusterName = null;
        String remoteNodeName = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        DmrClusterLinkRemoteAddressesResponse response = api.getDmrClusterLinkRemoteAddresses(dmrClusterName, remoteNodeName, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Trusted Common Name object.
     *
     * Get a Trusted Common Name object.  The Trusted Common Names for the Link are used by encrypted transports to verify the name in the certificate presented by the remote node. They must include the common name of the remote node&#39;s server certificate or client certificate, depending upon the initiator of the connection.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: dmrClusterName|x||x| remoteNodeName|x||x| tlsTrustedCommonName|x||x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.18. Common Name validation has been replaced by Server Certificate Name validation.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getDmrClusterLinkTlsTrustedCommonNameTest() throws Exception {
        String dmrClusterName = null;
        String remoteNodeName = null;
        String tlsTrustedCommonName = null;
        String opaquePassword = null;
        List<String> select = null;
        DmrClusterLinkTlsTrustedCommonNameResponse response = api.getDmrClusterLinkTlsTrustedCommonName(dmrClusterName, remoteNodeName, tlsTrustedCommonName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Trusted Common Name objects.
     *
     * Get a list of Trusted Common Name objects.  The Trusted Common Names for the Link are used by encrypted transports to verify the name in the certificate presented by the remote node. They must include the common name of the remote node&#39;s server certificate or client certificate, depending upon the initiator of the connection.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: dmrClusterName|x||x| remoteNodeName|x||x| tlsTrustedCommonName|x||x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.18. Common Name validation has been replaced by Server Certificate Name validation.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getDmrClusterLinkTlsTrustedCommonNamesTest() throws Exception {
        String dmrClusterName = null;
        String remoteNodeName = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        DmrClusterLinkTlsTrustedCommonNamesResponse response = api.getDmrClusterLinkTlsTrustedCommonNames(dmrClusterName, remoteNodeName, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Link objects.
     *
     * Get a list of Link objects.  A Link connects nodes (either within a Cluster or between two different Clusters) and allows them to exchange topology information, subscriptions and data.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: authenticationBasicPassword||x||x dmrClusterName|x||| remoteNodeName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getDmrClusterLinksTest() throws Exception {
        String dmrClusterName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        DmrClusterLinksResponse response = api.getDmrClusterLinks(dmrClusterName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Cluster objects.
     *
     * Get a list of Cluster objects.  A Cluster is a provisioned object on a message broker that contains global DMR configuration parameters.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: authenticationBasicPassword||x||x authenticationClientCertContent||x||x authenticationClientCertPassword||x|| dmrClusterName|x||| tlsServerCertEnforceTrustedCommonNameEnabled|||x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getDmrClustersTest() throws Exception {
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        DmrClustersResponse response = api.getDmrClusters(count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Domain Certificate Authority objects.
     *
     * Get a list of Domain Certificate Authority objects.  Certificate Authorities trusted for domain verification.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: certAuthorityName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.19.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getDomainCertAuthoritiesTest() throws Exception {
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        DomainCertAuthoritiesResponse response = api.getDomainCertAuthorities(count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Domain Certificate Authority object.
     *
     * Get a Domain Certificate Authority object.  Certificate Authorities trusted for domain verification.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: certAuthorityName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.19.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getDomainCertAuthorityTest() throws Exception {
        String certAuthorityName = null;
        String opaquePassword = null;
        List<String> select = null;
        DomainCertAuthorityResponse response = api.getDomainCertAuthority(certAuthorityName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a Message VPN object.
     *
     * Get a Message VPN object.  Message VPNs (Virtual Private Networks) allow for the segregation of topic space and clients. They also group clients connecting to a network of message brokers, such that messages published within a particular group are only visible to that group&#39;s clients.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: bridgingTlsServerCertEnforceTrustedCommonNameEnabled|||x| msgVpnName|x||| replicationBridgeAuthenticationBasicPassword||x||x replicationBridgeAuthenticationClientCertContent||x||x replicationBridgeAuthenticationClientCertPassword||x|| replicationEnabledQueueBehavior||x|| restTlsServerCertEnforceTrustedCommonNameEnabled|||x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnTest() throws Exception {
        String msgVpnName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnResponse response = api.getMsgVpn(msgVpnName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get an ACL Profile object.
     *
     * Get an ACL Profile object.  An ACL Profile controls whether an authenticated client is permitted to establish a connection with the message broker or permitted to publish and subscribe to specific topics.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: aclProfileName|x||| msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAclProfileTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAclProfileResponse response = api.getMsgVpnAclProfile(msgVpnName, aclProfileName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a Client Connect Exception object.
     *
     * Get a Client Connect Exception object.  A Client Connect Exception is an exception to the default action to take when a client using the ACL Profile connects to the Message VPN. Exceptions must be expressed as an IP address/netmask in CIDR form.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: aclProfileName|x||| clientConnectExceptionAddress|x||| msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAclProfileClientConnectExceptionTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        String clientConnectExceptionAddress = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAclProfileClientConnectExceptionResponse response = api.getMsgVpnAclProfileClientConnectException(msgVpnName, aclProfileName, clientConnectExceptionAddress, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Client Connect Exception objects.
     *
     * Get a list of Client Connect Exception objects.  A Client Connect Exception is an exception to the default action to take when a client using the ACL Profile connects to the Message VPN. Exceptions must be expressed as an IP address/netmask in CIDR form.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: aclProfileName|x||| clientConnectExceptionAddress|x||| msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAclProfileClientConnectExceptionsTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnAclProfileClientConnectExceptionsResponse response = api.getMsgVpnAclProfileClientConnectExceptions(msgVpnName, aclProfileName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Publish Topic Exception object.
     *
     * Get a Publish Topic Exception object.  A Publish Topic Exception is an exception to the default action to take when a client using the ACL Profile publishes to a topic in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: aclProfileName|x||x| msgVpnName|x||x| publishExceptionTopic|x||x| topicSyntax|x||x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.14. Replaced by publishTopicExceptions.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAclProfilePublishExceptionTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        String topicSyntax = null;
        String publishExceptionTopic = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAclProfilePublishExceptionResponse response = api.getMsgVpnAclProfilePublishException(msgVpnName, aclProfileName, topicSyntax, publishExceptionTopic, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Publish Topic Exception objects.
     *
     * Get a list of Publish Topic Exception objects.  A Publish Topic Exception is an exception to the default action to take when a client using the ACL Profile publishes to a topic in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: aclProfileName|x||x| msgVpnName|x||x| publishExceptionTopic|x||x| topicSyntax|x||x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.14. Replaced by publishTopicExceptions.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAclProfilePublishExceptionsTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnAclProfilePublishExceptionsResponse response = api.getMsgVpnAclProfilePublishExceptions(msgVpnName, aclProfileName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Publish Topic Exception object.
     *
     * Get a Publish Topic Exception object.  A Publish Topic Exception is an exception to the default action to take when a client using the ACL Profile publishes to a topic in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: aclProfileName|x||| msgVpnName|x||| publishTopicException|x||| publishTopicExceptionSyntax|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAclProfilePublishTopicExceptionTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        String publishTopicExceptionSyntax = null;
        String publishTopicException = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAclProfilePublishTopicExceptionResponse response = api.getMsgVpnAclProfilePublishTopicException(msgVpnName, aclProfileName, publishTopicExceptionSyntax, publishTopicException, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Publish Topic Exception objects.
     *
     * Get a list of Publish Topic Exception objects.  A Publish Topic Exception is an exception to the default action to take when a client using the ACL Profile publishes to a topic in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: aclProfileName|x||| msgVpnName|x||| publishTopicException|x||| publishTopicExceptionSyntax|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAclProfilePublishTopicExceptionsTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnAclProfilePublishTopicExceptionsResponse response = api.getMsgVpnAclProfilePublishTopicExceptions(msgVpnName, aclProfileName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Subscribe Topic Exception object.
     *
     * Get a Subscribe Topic Exception object.  A Subscribe Topic Exception is an exception to the default action to take when a client using the ACL Profile subscribes to a topic in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: aclProfileName|x||x| msgVpnName|x||x| subscribeExceptionTopic|x||x| topicSyntax|x||x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.14. Replaced by subscribeTopicExceptions.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAclProfileSubscribeExceptionTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        String topicSyntax = null;
        String subscribeExceptionTopic = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAclProfileSubscribeExceptionResponse response = api.getMsgVpnAclProfileSubscribeException(msgVpnName, aclProfileName, topicSyntax, subscribeExceptionTopic, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Subscribe Topic Exception objects.
     *
     * Get a list of Subscribe Topic Exception objects.  A Subscribe Topic Exception is an exception to the default action to take when a client using the ACL Profile subscribes to a topic in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: aclProfileName|x||x| msgVpnName|x||x| subscribeExceptionTopic|x||x| topicSyntax|x||x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.14. Replaced by subscribeTopicExceptions.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAclProfileSubscribeExceptionsTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnAclProfileSubscribeExceptionsResponse response = api.getMsgVpnAclProfileSubscribeExceptions(msgVpnName, aclProfileName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Subscribe Share Name Exception object.
     *
     * Get a Subscribe Share Name Exception object.  A Subscribe Share Name Exception is an exception to the default action to take when a client using the ACL Profile subscribes to a share-name subscription in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: aclProfileName|x||| msgVpnName|x||| subscribeShareNameException|x||| subscribeShareNameExceptionSyntax|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAclProfileSubscribeShareNameExceptionTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        String subscribeShareNameExceptionSyntax = null;
        String subscribeShareNameException = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAclProfileSubscribeShareNameExceptionResponse response = api.getMsgVpnAclProfileSubscribeShareNameException(msgVpnName, aclProfileName, subscribeShareNameExceptionSyntax, subscribeShareNameException, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Subscribe Share Name Exception objects.
     *
     * Get a list of Subscribe Share Name Exception objects.  A Subscribe Share Name Exception is an exception to the default action to take when a client using the ACL Profile subscribes to a share-name subscription in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: aclProfileName|x||| msgVpnName|x||| subscribeShareNameException|x||| subscribeShareNameExceptionSyntax|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAclProfileSubscribeShareNameExceptionsTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnAclProfileSubscribeShareNameExceptionsResponse response = api.getMsgVpnAclProfileSubscribeShareNameExceptions(msgVpnName, aclProfileName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Subscribe Topic Exception object.
     *
     * Get a Subscribe Topic Exception object.  A Subscribe Topic Exception is an exception to the default action to take when a client using the ACL Profile subscribes to a topic in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: aclProfileName|x||| msgVpnName|x||| subscribeTopicException|x||| subscribeTopicExceptionSyntax|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAclProfileSubscribeTopicExceptionTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        String subscribeTopicExceptionSyntax = null;
        String subscribeTopicException = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAclProfileSubscribeTopicExceptionResponse response = api.getMsgVpnAclProfileSubscribeTopicException(msgVpnName, aclProfileName, subscribeTopicExceptionSyntax, subscribeTopicException, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Subscribe Topic Exception objects.
     *
     * Get a list of Subscribe Topic Exception objects.  A Subscribe Topic Exception is an exception to the default action to take when a client using the ACL Profile subscribes to a topic in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: aclProfileName|x||| msgVpnName|x||| subscribeTopicException|x||| subscribeTopicExceptionSyntax|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAclProfileSubscribeTopicExceptionsTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnAclProfileSubscribeTopicExceptionsResponse response = api.getMsgVpnAclProfileSubscribeTopicExceptions(msgVpnName, aclProfileName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of ACL Profile objects.
     *
     * Get a list of ACL Profile objects.  An ACL Profile controls whether an authenticated client is permitted to establish a connection with the message broker or permitted to publish and subscribe to specific topics.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: aclProfileName|x||| msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAclProfilesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnAclProfilesResponse response = api.getMsgVpnAclProfiles(msgVpnName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get an OAuth Provider object.
     *
     * Get an OAuth Provider object.  OAuth Providers contain information about the issuer of an OAuth token that is needed to validate the token and derive a client username from it.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| oauthProviderName|x||| tokenIntrospectionPassword||x||x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.13.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAuthenticationOauthProviderTest() throws Exception {
        String msgVpnName = null;
        String oauthProviderName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAuthenticationOauthProviderResponse response = api.getMsgVpnAuthenticationOauthProvider(msgVpnName, oauthProviderName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of OAuth Provider objects.
     *
     * Get a list of OAuth Provider objects.  OAuth Providers contain information about the issuer of an OAuth token that is needed to validate the token and derive a client username from it.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| oauthProviderName|x||| tokenIntrospectionPassword||x||x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.13.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAuthenticationOauthProvidersTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnAuthenticationOauthProvidersResponse response = api.getMsgVpnAuthenticationOauthProviders(msgVpnName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get an LDAP Authorization Group object.
     *
     * Get an LDAP Authorization Group object.  To use client authorization groups configured on an external LDAP server to provide client authorizations, LDAP Authorization Group objects must be created on the Message VPN that match the authorization groups provisioned on the LDAP server. These objects must be configured with the client profiles and ACL profiles that will be assigned to the clients that belong to those authorization groups. A newly created group is placed at the end of the group list which is the lowest priority.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: authorizationGroupName|x||| msgVpnName|x||| orderAfterAuthorizationGroupName||x|| orderBeforeAuthorizationGroupName||x||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAuthorizationGroupTest() throws Exception {
        String msgVpnName = null;
        String authorizationGroupName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAuthorizationGroupResponse response = api.getMsgVpnAuthorizationGroup(msgVpnName, authorizationGroupName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of LDAP Authorization Group objects.
     *
     * Get a list of LDAP Authorization Group objects.  To use client authorization groups configured on an external LDAP server to provide client authorizations, LDAP Authorization Group objects must be created on the Message VPN that match the authorization groups provisioned on the LDAP server. These objects must be configured with the client profiles and ACL profiles that will be assigned to the clients that belong to those authorization groups. A newly created group is placed at the end of the group list which is the lowest priority.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: authorizationGroupName|x||| msgVpnName|x||| orderAfterAuthorizationGroupName||x|| orderBeforeAuthorizationGroupName||x||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAuthorizationGroupsTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnAuthorizationGroupsResponse response = api.getMsgVpnAuthorizationGroups(msgVpnName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Bridge object.
     *
     * Get a Bridge object.  Bridges can be used to link two Message VPNs so that messages published to one Message VPN that match the topic subscriptions set for the bridge are also delivered to the linked Message VPN.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: bridgeName|x||| bridgeVirtualRouter|x||| msgVpnName|x||| remoteAuthenticationBasicPassword||x||x remoteAuthenticationClientCertContent||x||x remoteAuthenticationClientCertPassword||x||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnBridgeTest() throws Exception {
        String msgVpnName = null;
        String bridgeName = null;
        String bridgeVirtualRouter = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnBridgeResponse response = api.getMsgVpnBridge(msgVpnName, bridgeName, bridgeVirtualRouter, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a Remote Message VPN object.
     *
     * Get a Remote Message VPN object.  The Remote Message VPN is the Message VPN that the Bridge connects to.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: bridgeName|x||| bridgeVirtualRouter|x||| msgVpnName|x||| password||x||x remoteMsgVpnInterface|x||| remoteMsgVpnLocation|x||| remoteMsgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnBridgeRemoteMsgVpnTest() throws Exception {
        String msgVpnName = null;
        String bridgeName = null;
        String bridgeVirtualRouter = null;
        String remoteMsgVpnName = null;
        String remoteMsgVpnLocation = null;
        String remoteMsgVpnInterface = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnBridgeRemoteMsgVpnResponse response = api.getMsgVpnBridgeRemoteMsgVpn(msgVpnName, bridgeName, bridgeVirtualRouter, remoteMsgVpnName, remoteMsgVpnLocation, remoteMsgVpnInterface, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Remote Message VPN objects.
     *
     * Get a list of Remote Message VPN objects.  The Remote Message VPN is the Message VPN that the Bridge connects to.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: bridgeName|x||| bridgeVirtualRouter|x||| msgVpnName|x||| password||x||x remoteMsgVpnInterface|x||| remoteMsgVpnLocation|x||| remoteMsgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnBridgeRemoteMsgVpnsTest() throws Exception {
        String msgVpnName = null;
        String bridgeName = null;
        String bridgeVirtualRouter = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnBridgeRemoteMsgVpnsResponse response = api.getMsgVpnBridgeRemoteMsgVpns(msgVpnName, bridgeName, bridgeVirtualRouter, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Remote Subscription object.
     *
     * Get a Remote Subscription object.  A Remote Subscription is a topic subscription used by the Message VPN Bridge to attract messages from the remote message broker.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: bridgeName|x||| bridgeVirtualRouter|x||| msgVpnName|x||| remoteSubscriptionTopic|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnBridgeRemoteSubscriptionTest() throws Exception {
        String msgVpnName = null;
        String bridgeName = null;
        String bridgeVirtualRouter = null;
        String remoteSubscriptionTopic = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnBridgeRemoteSubscriptionResponse response = api.getMsgVpnBridgeRemoteSubscription(msgVpnName, bridgeName, bridgeVirtualRouter, remoteSubscriptionTopic, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Remote Subscription objects.
     *
     * Get a list of Remote Subscription objects.  A Remote Subscription is a topic subscription used by the Message VPN Bridge to attract messages from the remote message broker.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: bridgeName|x||| bridgeVirtualRouter|x||| msgVpnName|x||| remoteSubscriptionTopic|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnBridgeRemoteSubscriptionsTest() throws Exception {
        String msgVpnName = null;
        String bridgeName = null;
        String bridgeVirtualRouter = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnBridgeRemoteSubscriptionsResponse response = api.getMsgVpnBridgeRemoteSubscriptions(msgVpnName, bridgeName, bridgeVirtualRouter, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Trusted Common Name object.
     *
     * Get a Trusted Common Name object.  The Trusted Common Names for the Bridge are used by encrypted transports to verify the name in the certificate presented by the remote node. They must include the common name of the remote node&#39;s server certificate or client certificate, depending upon the initiator of the connection.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: bridgeName|x||x| bridgeVirtualRouter|x||x| msgVpnName|x||x| tlsTrustedCommonName|x||x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.18. Common Name validation has been replaced by Server Certificate Name validation.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnBridgeTlsTrustedCommonNameTest() throws Exception {
        String msgVpnName = null;
        String bridgeName = null;
        String bridgeVirtualRouter = null;
        String tlsTrustedCommonName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnBridgeTlsTrustedCommonNameResponse response = api.getMsgVpnBridgeTlsTrustedCommonName(msgVpnName, bridgeName, bridgeVirtualRouter, tlsTrustedCommonName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Trusted Common Name objects.
     *
     * Get a list of Trusted Common Name objects.  The Trusted Common Names for the Bridge are used by encrypted transports to verify the name in the certificate presented by the remote node. They must include the common name of the remote node&#39;s server certificate or client certificate, depending upon the initiator of the connection.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: bridgeName|x||x| bridgeVirtualRouter|x||x| msgVpnName|x||x| tlsTrustedCommonName|x||x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.18. Common Name validation has been replaced by Server Certificate Name validation.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnBridgeTlsTrustedCommonNamesTest() throws Exception {
        String msgVpnName = null;
        String bridgeName = null;
        String bridgeVirtualRouter = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnBridgeTlsTrustedCommonNamesResponse response = api.getMsgVpnBridgeTlsTrustedCommonNames(msgVpnName, bridgeName, bridgeVirtualRouter, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Bridge objects.
     *
     * Get a list of Bridge objects.  Bridges can be used to link two Message VPNs so that messages published to one Message VPN that match the topic subscriptions set for the bridge are also delivered to the linked Message VPN.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: bridgeName|x||| bridgeVirtualRouter|x||| msgVpnName|x||| remoteAuthenticationBasicPassword||x||x remoteAuthenticationClientCertContent||x||x remoteAuthenticationClientCertPassword||x||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnBridgesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnBridgesResponse response = api.getMsgVpnBridges(msgVpnName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Client Profile object.
     *
     * Get a Client Profile object.  Client Profiles are used to assign common configuration properties to clients that have been successfully authorized.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: allowCutThroughForwardingEnabled|||x| apiQueueManagementCopyFromOnCreateName|||x| apiTopicEndpointManagementCopyFromOnCreateName|||x| clientProfileName|x||| msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnClientProfileTest() throws Exception {
        String msgVpnName = null;
        String clientProfileName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnClientProfileResponse response = api.getMsgVpnClientProfile(msgVpnName, clientProfileName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Client Profile objects.
     *
     * Get a list of Client Profile objects.  Client Profiles are used to assign common configuration properties to clients that have been successfully authorized.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: allowCutThroughForwardingEnabled|||x| apiQueueManagementCopyFromOnCreateName|||x| apiTopicEndpointManagementCopyFromOnCreateName|||x| clientProfileName|x||| msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnClientProfilesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnClientProfilesResponse response = api.getMsgVpnClientProfiles(msgVpnName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Client Username object.
     *
     * Get a Client Username object.  A client is only authorized to connect to a Message VPN that is associated with a Client Username that the client has been assigned.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: clientUsername|x||| msgVpnName|x||| password||x||x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnClientUsernameTest() throws Exception {
        String msgVpnName = null;
        String clientUsername = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnClientUsernameResponse response = api.getMsgVpnClientUsername(msgVpnName, clientUsername, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Client Username objects.
     *
     * Get a list of Client Username objects.  A client is only authorized to connect to a Message VPN that is associated with a Client Username that the client has been assigned.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: clientUsername|x||| msgVpnName|x||| password||x||x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnClientUsernamesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnClientUsernamesResponse response = api.getMsgVpnClientUsernames(msgVpnName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Distributed Cache object.
     *
     * Get a Distributed Cache object.  A Distributed Cache is a collection of one or more Cache Clusters that belong to the same Message VPN. Each Cache Cluster in a Distributed Cache is configured to subscribe to a different set of topics. This effectively divides up the configured topic space, to provide scaling to very large topic spaces or very high cached message throughput.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: cacheName|x||| msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnDistributedCacheTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnDistributedCacheResponse response = api.getMsgVpnDistributedCache(msgVpnName, cacheName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a Cache Cluster object.
     *
     * Get a Cache Cluster object.  A Cache Cluster is a collection of one or more Cache Instances that subscribe to exactly the same topics. Cache Instances are grouped together in a Cache Cluster for the purpose of fault tolerance and load balancing. As published messages are received, the message broker message bus sends these live data messages to the Cache Instances in the Cache Cluster. This enables client cache requests to be served by any of Cache Instances in the Cache Cluster.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: cacheName|x||| clusterName|x||| msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnDistributedCacheClusterTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterResponse response = api.getMsgVpnDistributedCacheCluster(msgVpnName, cacheName, clusterName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a Home Cache Cluster object.
     *
     * Get a Home Cache Cluster object.  A Home Cache Cluster is a Cache Cluster that is the \&quot;definitive\&quot; Cache Cluster for a given topic in the context of the Global Caching feature.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: cacheName|x||| clusterName|x||| homeClusterName|x||| msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        String homeClusterName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterGlobalCachingHomeClusterResponse response = api.getMsgVpnDistributedCacheClusterGlobalCachingHomeCluster(msgVpnName, cacheName, clusterName, homeClusterName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a Topic Prefix object.
     *
     * Get a Topic Prefix object.  A Topic Prefix is a prefix for a global topic that is available from the containing Home Cache Cluster.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: cacheName|x||| clusterName|x||| homeClusterName|x||| msgVpnName|x||| topicPrefix|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        String homeClusterName = null;
        String topicPrefix = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixResponse response = api.getMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefix(msgVpnName, cacheName, clusterName, homeClusterName, topicPrefix, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Topic Prefix objects.
     *
     * Get a list of Topic Prefix objects.  A Topic Prefix is a prefix for a global topic that is available from the containing Home Cache Cluster.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: cacheName|x||| clusterName|x||| homeClusterName|x||| msgVpnName|x||| topicPrefix|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixesTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        String homeClusterName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixesResponse response = api.getMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixes(msgVpnName, cacheName, clusterName, homeClusterName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Home Cache Cluster objects.
     *
     * Get a list of Home Cache Cluster objects.  A Home Cache Cluster is a Cache Cluster that is the \&quot;definitive\&quot; Cache Cluster for a given topic in the context of the Global Caching feature.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: cacheName|x||| clusterName|x||| homeClusterName|x||| msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnDistributedCacheClusterGlobalCachingHomeClustersTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterGlobalCachingHomeClustersResponse response = api.getMsgVpnDistributedCacheClusterGlobalCachingHomeClusters(msgVpnName, cacheName, clusterName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Cache Instance object.
     *
     * Get a Cache Instance object.  A Cache Instance is a single Cache process that belongs to a single Cache Cluster. A Cache Instance object provisioned on the broker is used to disseminate configuration information to the Cache process. Cache Instances listen for and cache live data messages that match the topic subscriptions configured for their parent Cache Cluster.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: cacheName|x||| clusterName|x||| instanceName|x||| msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnDistributedCacheClusterInstanceTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        String instanceName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterInstanceResponse response = api.getMsgVpnDistributedCacheClusterInstance(msgVpnName, cacheName, clusterName, instanceName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Cache Instance objects.
     *
     * Get a list of Cache Instance objects.  A Cache Instance is a single Cache process that belongs to a single Cache Cluster. A Cache Instance object provisioned on the broker is used to disseminate configuration information to the Cache process. Cache Instances listen for and cache live data messages that match the topic subscriptions configured for their parent Cache Cluster.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: cacheName|x||| clusterName|x||| instanceName|x||| msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnDistributedCacheClusterInstancesTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterInstancesResponse response = api.getMsgVpnDistributedCacheClusterInstances(msgVpnName, cacheName, clusterName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Topic object.
     *
     * Get a Topic object.  The Cache Instances that belong to the containing Cache Cluster will cache any messages published to topics that match a Topic Subscription.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: cacheName|x||| clusterName|x||| msgVpnName|x||| topic|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnDistributedCacheClusterTopicTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        String topic = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterTopicResponse response = api.getMsgVpnDistributedCacheClusterTopic(msgVpnName, cacheName, clusterName, topic, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Topic objects.
     *
     * Get a list of Topic objects.  The Cache Instances that belong to the containing Cache Cluster will cache any messages published to topics that match a Topic Subscription.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: cacheName|x||| clusterName|x||| msgVpnName|x||| topic|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnDistributedCacheClusterTopicsTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterTopicsResponse response = api.getMsgVpnDistributedCacheClusterTopics(msgVpnName, cacheName, clusterName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Cache Cluster objects.
     *
     * Get a list of Cache Cluster objects.  A Cache Cluster is a collection of one or more Cache Instances that subscribe to exactly the same topics. Cache Instances are grouped together in a Cache Cluster for the purpose of fault tolerance and load balancing. As published messages are received, the message broker message bus sends these live data messages to the Cache Instances in the Cache Cluster. This enables client cache requests to be served by any of Cache Instances in the Cache Cluster.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: cacheName|x||| clusterName|x||| msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnDistributedCacheClustersTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnDistributedCacheClustersResponse response = api.getMsgVpnDistributedCacheClusters(msgVpnName, cacheName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Distributed Cache objects.
     *
     * Get a list of Distributed Cache objects.  A Distributed Cache is a collection of one or more Cache Clusters that belong to the same Message VPN. Each Cache Cluster in a Distributed Cache is configured to subscribe to a different set of topics. This effectively divides up the configured topic space, to provide scaling to very large topic spaces or very high cached message throughput.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: cacheName|x||| msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnDistributedCachesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnDistributedCachesResponse response = api.getMsgVpnDistributedCaches(msgVpnName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a DMR Bridge object.
     *
     * Get a DMR Bridge object.  A DMR Bridge is required to establish a data channel over a corresponding external link to the remote node for a given Message VPN. Each DMR Bridge identifies which external link the Message VPN should use, and what the name of the equivalent Message VPN at the remote node is.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| remoteNodeName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnDmrBridgeTest() throws Exception {
        String msgVpnName = null;
        String remoteNodeName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnDmrBridgeResponse response = api.getMsgVpnDmrBridge(msgVpnName, remoteNodeName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of DMR Bridge objects.
     *
     * Get a list of DMR Bridge objects.  A DMR Bridge is required to establish a data channel over a corresponding external link to the remote node for a given Message VPN. Each DMR Bridge identifies which external link the Message VPN should use, and what the name of the equivalent Message VPN at the remote node is.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| remoteNodeName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnDmrBridgesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnDmrBridgesResponse response = api.getMsgVpnDmrBridges(msgVpnName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of JNDI Connection Factory objects.
     *
     * Get a list of JNDI Connection Factory objects.  The message broker provides an internal JNDI store for provisioned Connection Factory objects that clients can access through JNDI lookups.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: connectionFactoryName|x||| msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnJndiConnectionFactoriesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnJndiConnectionFactoriesResponse response = api.getMsgVpnJndiConnectionFactories(msgVpnName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a JNDI Connection Factory object.
     *
     * Get a JNDI Connection Factory object.  The message broker provides an internal JNDI store for provisioned Connection Factory objects that clients can access through JNDI lookups.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: connectionFactoryName|x||| msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnJndiConnectionFactoryTest() throws Exception {
        String msgVpnName = null;
        String connectionFactoryName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnJndiConnectionFactoryResponse response = api.getMsgVpnJndiConnectionFactory(msgVpnName, connectionFactoryName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a JNDI Queue object.
     *
     * Get a JNDI Queue object.  The message broker provides an internal JNDI store for provisioned Queue objects that clients can access through JNDI lookups.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| queueName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnJndiQueueTest() throws Exception {
        String msgVpnName = null;
        String queueName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnJndiQueueResponse response = api.getMsgVpnJndiQueue(msgVpnName, queueName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of JNDI Queue objects.
     *
     * Get a list of JNDI Queue objects.  The message broker provides an internal JNDI store for provisioned Queue objects that clients can access through JNDI lookups.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| queueName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnJndiQueuesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnJndiQueuesResponse response = api.getMsgVpnJndiQueues(msgVpnName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a JNDI Topic object.
     *
     * Get a JNDI Topic object.  The message broker provides an internal JNDI store for provisioned Topic objects that clients can access through JNDI lookups.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| topicName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnJndiTopicTest() throws Exception {
        String msgVpnName = null;
        String topicName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnJndiTopicResponse response = api.getMsgVpnJndiTopic(msgVpnName, topicName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of JNDI Topic objects.
     *
     * Get a list of JNDI Topic objects.  The message broker provides an internal JNDI store for provisioned Topic objects that clients can access through JNDI lookups.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| topicName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnJndiTopicsTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnJndiTopicsResponse response = api.getMsgVpnJndiTopics(msgVpnName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get an MQTT Retain Cache object.
     *
     * Get an MQTT Retain Cache object.  Using MQTT retained messages allows publishing MQTT clients to indicate that a message must be stored for later delivery to subscribing clients when those subscribing clients add subscriptions matching the retained message&#39;s topic. An MQTT Retain Cache processes all retained messages for a Message VPN.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: cacheName|x||| msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnMqttRetainCacheTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnMqttRetainCacheResponse response = api.getMsgVpnMqttRetainCache(msgVpnName, cacheName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of MQTT Retain Cache objects.
     *
     * Get a list of MQTT Retain Cache objects.  Using MQTT retained messages allows publishing MQTT clients to indicate that a message must be stored for later delivery to subscribing clients when those subscribing clients add subscriptions matching the retained message&#39;s topic. An MQTT Retain Cache processes all retained messages for a Message VPN.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: cacheName|x||| msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnMqttRetainCachesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnMqttRetainCachesResponse response = api.getMsgVpnMqttRetainCaches(msgVpnName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get an MQTT Session object.
     *
     * Get an MQTT Session object.  An MQTT Session object is a virtual representation of an MQTT client connection. An MQTT session holds the state of an MQTT client (that is, it is used to contain a client&#39;s QoS 0 and QoS 1 subscription sets and any undelivered QoS 1 messages).   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: mqttSessionClientId|x||| mqttSessionVirtualRouter|x||| msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnMqttSessionTest() throws Exception {
        String msgVpnName = null;
        String mqttSessionClientId = null;
        String mqttSessionVirtualRouter = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnMqttSessionResponse response = api.getMsgVpnMqttSession(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a Subscription object.
     *
     * Get a Subscription object.  An MQTT session contains a client&#39;s QoS 0 and QoS 1 subscription sets. On creation, a subscription defaults to QoS 0.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: mqttSessionClientId|x||| mqttSessionVirtualRouter|x||| msgVpnName|x||| subscriptionTopic|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnMqttSessionSubscriptionTest() throws Exception {
        String msgVpnName = null;
        String mqttSessionClientId = null;
        String mqttSessionVirtualRouter = null;
        String subscriptionTopic = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnMqttSessionSubscriptionResponse response = api.getMsgVpnMqttSessionSubscription(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, subscriptionTopic, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Subscription objects.
     *
     * Get a list of Subscription objects.  An MQTT session contains a client&#39;s QoS 0 and QoS 1 subscription sets. On creation, a subscription defaults to QoS 0.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: mqttSessionClientId|x||| mqttSessionVirtualRouter|x||| msgVpnName|x||| subscriptionTopic|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnMqttSessionSubscriptionsTest() throws Exception {
        String msgVpnName = null;
        String mqttSessionClientId = null;
        String mqttSessionVirtualRouter = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnMqttSessionSubscriptionsResponse response = api.getMsgVpnMqttSessionSubscriptions(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of MQTT Session objects.
     *
     * Get a list of MQTT Session objects.  An MQTT Session object is a virtual representation of an MQTT client connection. An MQTT session holds the state of an MQTT client (that is, it is used to contain a client&#39;s QoS 0 and QoS 1 subscription sets and any undelivered QoS 1 messages).   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: mqttSessionClientId|x||| mqttSessionVirtualRouter|x||| msgVpnName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnMqttSessionsTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnMqttSessionsResponse response = api.getMsgVpnMqttSessions(msgVpnName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Queue object.
     *
     * Get a Queue object.  A Queue acts as both a destination that clients can publish messages to, and as an endpoint that clients can bind consumers to and consume messages from.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| queueName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnQueueTest() throws Exception {
        String msgVpnName = null;
        String queueName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnQueueResponse response = api.getMsgVpnQueue(msgVpnName, queueName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a Queue Subscription object.
     *
     * Get a Queue Subscription object.  One or more Queue Subscriptions can be added to a durable queue so that Guaranteed messages published to matching topics are also delivered to and spooled by the queue.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| queueName|x||| subscriptionTopic|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnQueueSubscriptionTest() throws Exception {
        String msgVpnName = null;
        String queueName = null;
        String subscriptionTopic = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnQueueSubscriptionResponse response = api.getMsgVpnQueueSubscription(msgVpnName, queueName, subscriptionTopic, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Queue Subscription objects.
     *
     * Get a list of Queue Subscription objects.  One or more Queue Subscriptions can be added to a durable queue so that Guaranteed messages published to matching topics are also delivered to and spooled by the queue.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| queueName|x||| subscriptionTopic|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnQueueSubscriptionsTest() throws Exception {
        String msgVpnName = null;
        String queueName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnQueueSubscriptionsResponse response = api.getMsgVpnQueueSubscriptions(msgVpnName, queueName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Queue Template object.
     *
     * Get a Queue Template object.  A Queue Template provides a mechanism for specifying the initial state for client created queues.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| queueTemplateName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnQueueTemplateTest() throws Exception {
        String msgVpnName = null;
        String queueTemplateName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnQueueTemplateResponse response = api.getMsgVpnQueueTemplate(msgVpnName, queueTemplateName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Queue Template objects.
     *
     * Get a list of Queue Template objects.  A Queue Template provides a mechanism for specifying the initial state for client created queues.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| queueTemplateName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnQueueTemplatesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnQueueTemplatesResponse response = api.getMsgVpnQueueTemplates(msgVpnName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Queue objects.
     *
     * Get a list of Queue objects.  A Queue acts as both a destination that clients can publish messages to, and as an endpoint that clients can bind consumers to and consume messages from.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| queueName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnQueuesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnQueuesResponse response = api.getMsgVpnQueues(msgVpnName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Replay Log object.
     *
     * Get a Replay Log object.  When the Message Replay feature is enabled, message brokers store persistent messages in a Replay Log. These messages are kept until the log is full, after which the oldest messages are removed to free up space for new messages.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| replayLogName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.10.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnReplayLogTest() throws Exception {
        String msgVpnName = null;
        String replayLogName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnReplayLogResponse response = api.getMsgVpnReplayLog(msgVpnName, replayLogName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Replay Log objects.
     *
     * Get a list of Replay Log objects.  When the Message Replay feature is enabled, message brokers store persistent messages in a Replay Log. These messages are kept until the log is full, after which the oldest messages are removed to free up space for new messages.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| replayLogName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.10.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnReplayLogsTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnReplayLogsResponse response = api.getMsgVpnReplayLogs(msgVpnName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Replicated Topic object.
     *
     * Get a Replicated Topic object.  To indicate which messages should be replicated between the active and standby site, a Replicated Topic subscription must be configured on a Message VPN. If a published message matches both a replicated topic and an endpoint on the active site, then the message is replicated to the standby site.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| replicatedTopic|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnReplicatedTopicTest() throws Exception {
        String msgVpnName = null;
        String replicatedTopic = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnReplicatedTopicResponse response = api.getMsgVpnReplicatedTopic(msgVpnName, replicatedTopic, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Replicated Topic objects.
     *
     * Get a list of Replicated Topic objects.  To indicate which messages should be replicated between the active and standby site, a Replicated Topic subscription must be configured on a Message VPN. If a published message matches both a replicated topic and an endpoint on the active site, then the message is replicated to the standby site.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| replicatedTopic|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnReplicatedTopicsTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnReplicatedTopicsResponse response = api.getMsgVpnReplicatedTopics(msgVpnName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a REST Delivery Point object.
     *
     * Get a REST Delivery Point object.  A REST Delivery Point manages delivery of messages from queues to a named list of REST Consumers.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| restDeliveryPointName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnRestDeliveryPointTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointResponse response = api.getMsgVpnRestDeliveryPoint(msgVpnName, restDeliveryPointName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a Queue Binding object.
     *
     * Get a Queue Binding object.  A Queue Binding for a REST Delivery Point attracts messages to be delivered to REST consumers. If the queue does not exist it can be created subsequently, and once the queue is operational the broker performs the queue binding. Removing the queue binding does not delete the queue itself. Similarly, removing the queue does not remove the queue binding, which fails until the queue is recreated or the queue binding is deleted.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| queueBindingName|x||| restDeliveryPointName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnRestDeliveryPointQueueBindingTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String queueBindingName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointQueueBindingResponse response = api.getMsgVpnRestDeliveryPointQueueBinding(msgVpnName, restDeliveryPointName, queueBindingName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a Request Header object.
     *
     * Get a Request Header object.  A request header to be added to the HTTP request.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: headerName|x||| msgVpnName|x||| queueBindingName|x||| restDeliveryPointName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.23.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnRestDeliveryPointQueueBindingRequestHeaderTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String queueBindingName = null;
        String headerName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointQueueBindingRequestHeaderResponse response = api.getMsgVpnRestDeliveryPointQueueBindingRequestHeader(msgVpnName, restDeliveryPointName, queueBindingName, headerName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Request Header objects.
     *
     * Get a list of Request Header objects.  A request header to be added to the HTTP request.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: headerName|x||| msgVpnName|x||| queueBindingName|x||| restDeliveryPointName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.23.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnRestDeliveryPointQueueBindingRequestHeadersTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String queueBindingName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointQueueBindingRequestHeadersResponse response = api.getMsgVpnRestDeliveryPointQueueBindingRequestHeaders(msgVpnName, restDeliveryPointName, queueBindingName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Queue Binding objects.
     *
     * Get a list of Queue Binding objects.  A Queue Binding for a REST Delivery Point attracts messages to be delivered to REST consumers. If the queue does not exist it can be created subsequently, and once the queue is operational the broker performs the queue binding. Removing the queue binding does not delete the queue itself. Similarly, removing the queue does not remove the queue binding, which fails until the queue is recreated or the queue binding is deleted.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| queueBindingName|x||| restDeliveryPointName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnRestDeliveryPointQueueBindingsTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointQueueBindingsResponse response = api.getMsgVpnRestDeliveryPointQueueBindings(msgVpnName, restDeliveryPointName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a REST Consumer object.
     *
     * Get a REST Consumer object.  REST Consumer objects establish HTTP connectivity to REST consumer applications who wish to receive messages from a broker.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: authenticationClientCertContent||x||x authenticationClientCertPassword||x|| authenticationHttpBasicPassword||x||x authenticationHttpHeaderValue||x||x authenticationOauthClientSecret||x||x authenticationOauthJwtSecretKey||x||x msgVpnName|x||| restConsumerName|x||| restDeliveryPointName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnRestDeliveryPointRestConsumerTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String restConsumerName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointRestConsumerResponse response = api.getMsgVpnRestDeliveryPointRestConsumer(msgVpnName, restDeliveryPointName, restConsumerName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a Claim object.
     *
     * Get a Claim object.  A Claim is added to the JWT sent to the OAuth token request endpoint.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| oauthJwtClaimName|x||| restConsumerName|x||| restDeliveryPointName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.21.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnRestDeliveryPointRestConsumerOauthJwtClaimTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String restConsumerName = null;
        String oauthJwtClaimName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointRestConsumerOauthJwtClaimResponse response = api.getMsgVpnRestDeliveryPointRestConsumerOauthJwtClaim(msgVpnName, restDeliveryPointName, restConsumerName, oauthJwtClaimName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Claim objects.
     *
     * Get a list of Claim objects.  A Claim is added to the JWT sent to the OAuth token request endpoint.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| oauthJwtClaimName|x||| restConsumerName|x||| restDeliveryPointName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.21.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnRestDeliveryPointRestConsumerOauthJwtClaimsTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String restConsumerName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointRestConsumerOauthJwtClaimsResponse response = api.getMsgVpnRestDeliveryPointRestConsumerOauthJwtClaims(msgVpnName, restDeliveryPointName, restConsumerName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Trusted Common Name object.
     *
     * Get a Trusted Common Name object.  The Trusted Common Names for the REST Consumer are used by encrypted transports to verify the name in the certificate presented by the remote REST consumer. They must include the common name of the remote REST consumer&#39;s server certificate.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||x| restConsumerName|x||x| restDeliveryPointName|x||x| tlsTrustedCommonName|x||x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.17. Common Name validation has been replaced by Server Certificate Name validation.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNameTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String restConsumerName = null;
        String tlsTrustedCommonName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNameResponse response = api.getMsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonName(msgVpnName, restDeliveryPointName, restConsumerName, tlsTrustedCommonName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Trusted Common Name objects.
     *
     * Get a list of Trusted Common Name objects.  The Trusted Common Names for the REST Consumer are used by encrypted transports to verify the name in the certificate presented by the remote REST consumer. They must include the common name of the remote REST consumer&#39;s server certificate.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||x| restConsumerName|x||x| restDeliveryPointName|x||x| tlsTrustedCommonName|x||x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.17. Common Name validation has been replaced by Server Certificate Name validation.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNamesTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String restConsumerName = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNamesResponse response = api.getMsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNames(msgVpnName, restDeliveryPointName, restConsumerName, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of REST Consumer objects.
     *
     * Get a list of REST Consumer objects.  REST Consumer objects establish HTTP connectivity to REST consumer applications who wish to receive messages from a broker.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: authenticationClientCertContent||x||x authenticationClientCertPassword||x|| authenticationHttpBasicPassword||x||x authenticationHttpHeaderValue||x||x authenticationOauthClientSecret||x||x authenticationOauthJwtSecretKey||x||x msgVpnName|x||| restConsumerName|x||| restDeliveryPointName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnRestDeliveryPointRestConsumersTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointRestConsumersResponse response = api.getMsgVpnRestDeliveryPointRestConsumers(msgVpnName, restDeliveryPointName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of REST Delivery Point objects.
     *
     * Get a list of REST Delivery Point objects.  A REST Delivery Point manages delivery of messages from queues to a named list of REST Consumers.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| restDeliveryPointName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnRestDeliveryPointsTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointsResponse response = api.getMsgVpnRestDeliveryPoints(msgVpnName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Sequenced Topic object.
     *
     * Get a Sequenced Topic object.  A Sequenced Topic is a topic subscription for which any matching messages received on the Message VPN are assigned a sequence number that is monotonically increased by a value of one per message.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| sequencedTopic|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnSequencedTopicTest() throws Exception {
        String msgVpnName = null;
        String sequencedTopic = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnSequencedTopicResponse response = api.getMsgVpnSequencedTopic(msgVpnName, sequencedTopic, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Sequenced Topic objects.
     *
     * Get a list of Sequenced Topic objects.  A Sequenced Topic is a topic subscription for which any matching messages received on the Message VPN are assigned a sequence number that is monotonically increased by a value of one per message.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| sequencedTopic|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnSequencedTopicsTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnSequencedTopicsResponse response = api.getMsgVpnSequencedTopics(msgVpnName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Topic Endpoint object.
     *
     * Get a Topic Endpoint object.  A Topic Endpoint attracts messages published to a topic for which the Topic Endpoint has a matching topic subscription. The topic subscription for the Topic Endpoint is specified in the client request to bind a Flow to that Topic Endpoint. Queues are significantly more flexible than Topic Endpoints and are the recommended approach for most applications. The use of Topic Endpoints should be restricted to JMS applications.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| topicEndpointName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnTopicEndpointTest() throws Exception {
        String msgVpnName = null;
        String topicEndpointName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnTopicEndpointResponse response = api.getMsgVpnTopicEndpoint(msgVpnName, topicEndpointName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a Topic Endpoint Template object.
     *
     * Get a Topic Endpoint Template object.  A Topic Endpoint Template provides a mechanism for specifying the initial state for client created topic endpoints.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| topicEndpointTemplateName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnTopicEndpointTemplateTest() throws Exception {
        String msgVpnName = null;
        String topicEndpointTemplateName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnTopicEndpointTemplateResponse response = api.getMsgVpnTopicEndpointTemplate(msgVpnName, topicEndpointTemplateName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Topic Endpoint Template objects.
     *
     * Get a list of Topic Endpoint Template objects.  A Topic Endpoint Template provides a mechanism for specifying the initial state for client created topic endpoints.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| topicEndpointTemplateName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnTopicEndpointTemplatesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnTopicEndpointTemplatesResponse response = api.getMsgVpnTopicEndpointTemplates(msgVpnName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Topic Endpoint objects.
     *
     * Get a list of Topic Endpoint objects.  A Topic Endpoint attracts messages published to a topic for which the Topic Endpoint has a matching topic subscription. The topic subscription for the Topic Endpoint is specified in the client request to bind a Flow to that Topic Endpoint. Queues are significantly more flexible than Topic Endpoints and are the recommended approach for most applications. The use of Topic Endpoints should be restricted to JMS applications.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: msgVpnName|x||| topicEndpointName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnTopicEndpointsTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnTopicEndpointsResponse response = api.getMsgVpnTopicEndpoints(msgVpnName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Message VPN objects.
     *
     * Get a list of Message VPN objects.  Message VPNs (Virtual Private Networks) allow for the segregation of topic space and clients. They also group clients connecting to a network of message brokers, such that messages published within a particular group are only visible to that group&#39;s clients.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: bridgingTlsServerCertEnforceTrustedCommonNameEnabled|||x| msgVpnName|x||| replicationBridgeAuthenticationBasicPassword||x||x replicationBridgeAuthenticationClientCertContent||x||x replicationBridgeAuthenticationClientCertPassword||x|| replicationEnabledQueueBehavior||x|| restTlsServerCertEnforceTrustedCommonNameEnabled|||x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnsTest() throws Exception {
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnsResponse response = api.getMsgVpns(count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a System Information object.
     *
     * Get a System Information object.  The System Information object provides metadata about the SEMP API.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: platform|||x| sempVersion|||x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation.  This has been deprecated since 2.4. /systemInformation was replaced by /about/api.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getSystemInformationTest() throws Exception {
        String opaquePassword = null;
        List<String> select = null;
        SystemInformationResponse response = api.getSystemInformation(opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a Virtual Hostname object.
     *
     * Get a Virtual Hostname object.  A Virtual Hostname is a provisioned object on a message broker that contains a Virtual Hostname to Message VPN mapping.  Clients which connect to a global (as opposed to per Message VPN) port and provides this hostname will be directed to its corresponding Message VPN. A case-insentive match is performed on the full client-provided hostname against the configured virtual-hostname.  This mechanism is only supported for hostnames provided through the Server Name Indication (SNI) extension of TLS.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: virtualHostname|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.17.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getVirtualHostnameTest() throws Exception {
        String virtualHostname = null;
        String opaquePassword = null;
        List<String> select = null;
        VirtualHostnameResponse response = api.getVirtualHostname(virtualHostname, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Virtual Hostname objects.
     *
     * Get a list of Virtual Hostname objects.  A Virtual Hostname is a provisioned object on a message broker that contains a Virtual Hostname to Message VPN mapping.  Clients which connect to a global (as opposed to per Message VPN) port and provides this hostname will be directed to its corresponding Message VPN. A case-insentive match is performed on the full client-provided hostname against the configured virtual-hostname.  This mechanism is only supported for hostnames provided through the Server Name Indication (SNI) extension of TLS.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: virtualHostname|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.17.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getVirtualHostnamesTest() throws Exception {
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        VirtualHostnamesResponse response = api.getVirtualHostnames(count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Replace a Certificate Authority object.
     *
     * Replace a Certificate Authority object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  Clients can authenticate with the message broker over TLS by presenting a valid client certificate. The message broker authenticates the client certificate by constructing a full certificate chain (from the client certificate to intermediate CAs to a configured root CA). The intermediate CAs in this chain can be provided by the client, or configured in the message broker. The root CA must be configured on the message broker.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: certAuthorityName|x|x|||x| certContent|||||x| crlDayList|||||x| crlTimeList|||||x| crlUrl||||x|x| ocspNonResponderCertEnabled|||||x| ocspOverrideUrl|||||x| ocspTimeout|||||x| revocationCheckEnabled|||||x|    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- CertAuthority|crlDayList|crlTimeList| CertAuthority|crlTimeList|crlDayList|    A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been deprecated since 2.19. Replaced by clientCertAuthorities and domainCertAuthorities.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceCertAuthorityTest() throws Exception {
        String certAuthorityName = null;
        CertAuthority body = null;
        String opaquePassword = null;
        List<String> select = null;
        CertAuthorityResponse response = api.replaceCertAuthority(certAuthorityName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a Client Certificate Authority object.
     *
     * Replace a Client Certificate Authority object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  Clients can authenticate with the message broker over TLS by presenting a valid client certificate. The message broker authenticates the client certificate by constructing a full certificate chain (from the client certificate to intermediate CAs to a configured root CA). The intermediate CAs in this chain can be provided by the client, or configured in the message broker. The root CA must be configured on the message broker.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: certAuthorityName|x|x|||| crlUrl||||x||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- ClientCertAuthority|crlDayList|crlTimeList| ClientCertAuthority|crlTimeList|crlDayList|    A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been available since 2.19.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceClientCertAuthorityTest() throws Exception {
        String certAuthorityName = null;
        ClientCertAuthority body = null;
        String opaquePassword = null;
        List<String> select = null;
        ClientCertAuthorityResponse response = api.replaceClientCertAuthority(certAuthorityName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a Cluster object.
     *
     * Replace a Cluster object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  A Cluster is a provisioned object on a message broker that contains global DMR configuration parameters.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: authenticationBasicPassword|||x|x||x authenticationClientCertContent|||x|x||x authenticationClientCertPassword|||x|x|| directOnlyEnabled||x|||| dmrClusterName|x|x|||| nodeName||x|||| tlsServerCertEnforceTrustedCommonNameEnabled|||||x|    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- DmrCluster|authenticationClientCertPassword|authenticationClientCertContent|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceDmrClusterTest() throws Exception {
        String dmrClusterName = null;
        DmrCluster body = null;
        String opaquePassword = null;
        List<String> select = null;
        DmrClusterResponse response = api.replaceDmrCluster(dmrClusterName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a Link object.
     *
     * Replace a Link object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  A Link connects nodes (either within a Cluster or between two different Clusters) and allows them to exchange topology information, subscriptions and data.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: authenticationBasicPassword|||x|x||x authenticationScheme||||x|| dmrClusterName|x|x|||| egressFlowWindowSize||||x|| initiator||||x|| remoteNodeName|x|x|||| span||||x|| transportCompressedEnabled||||x|| transportTlsEnabled||||x||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceDmrClusterLinkTest() throws Exception {
        String dmrClusterName = null;
        String remoteNodeName = null;
        DmrClusterLink body = null;
        String opaquePassword = null;
        List<String> select = null;
        DmrClusterLinkResponse response = api.replaceDmrClusterLink(dmrClusterName, remoteNodeName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a Domain Certificate Authority object.
     *
     * Replace a Domain Certificate Authority object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  Certificate Authorities trusted for domain verification.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: certAuthorityName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been available since 2.19.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceDomainCertAuthorityTest() throws Exception {
        String certAuthorityName = null;
        DomainCertAuthority body = null;
        String opaquePassword = null;
        List<String> select = null;
        DomainCertAuthorityResponse response = api.replaceDomainCertAuthority(certAuthorityName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a Message VPN object.
     *
     * Replace a Message VPN object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  Message VPNs (Virtual Private Networks) allow for the segregation of topic space and clients. They also group clients connecting to a network of message brokers, such that messages published within a particular group are only visible to that group&#39;s clients.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: bridgingTlsServerCertEnforceTrustedCommonNameEnabled|||||x| msgVpnName|x|x|||| replicationBridgeAuthenticationBasicPassword|||x|||x replicationBridgeAuthenticationClientCertContent|||x|||x replicationBridgeAuthenticationClientCertPassword|||x||| replicationEnabledQueueBehavior|||x||| restTlsServerCertEnforceTrustedCommonNameEnabled|||||x|    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent EventThresholdByValue|clearValue|setValue| EventThresholdByValue|setValue|clearValue| MsgVpn|authenticationBasicProfileName|authenticationBasicType| MsgVpn|authorizationProfileName|authorizationType| MsgVpn|eventPublishTopicFormatMqttEnabled|eventPublishTopicFormatSmfEnabled| MsgVpn|eventPublishTopicFormatSmfEnabled|eventPublishTopicFormatMqttEnabled| MsgVpn|replicationBridgeAuthenticationBasicClientUsername|replicationBridgeAuthenticationBasicPassword| MsgVpn|replicationBridgeAuthenticationBasicPassword|replicationBridgeAuthenticationBasicClientUsername| MsgVpn|replicationBridgeAuthenticationClientCertPassword|replicationBridgeAuthenticationClientCertContent| MsgVpn|replicationEnabledQueueBehavior|replicationEnabled|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation. Requests which include the following attributes require greater access scope/level:   Attribute|Access Scope/Level :---|:---: alias|global/read-write authenticationBasicEnabled|global/read-write authenticationBasicProfileName|global/read-write authenticationBasicRadiusDomain|global/read-write authenticationBasicType|global/read-write authenticationClientCertAllowApiProvidedUsernameEnabled|global/read-write authenticationClientCertEnabled|global/read-write authenticationClientCertMaxChainDepth|global/read-write authenticationClientCertRevocationCheckMode|global/read-write authenticationClientCertUsernameSource|global/read-write authenticationClientCertValidateDateEnabled|global/read-write authenticationKerberosAllowApiProvidedUsernameEnabled|global/read-write authenticationKerberosEnabled|global/read-write authenticationOauthEnabled|global/read-write bridgingTlsServerCertEnforceTrustedCommonNameEnabled|global/read-write bridgingTlsServerCertMaxChainDepth|global/read-write bridgingTlsServerCertValidateDateEnabled|global/read-write bridgingTlsServerCertValidateNameEnabled|global/read-write dmrEnabled|global/read-write exportSubscriptionsEnabled|global/read-write maxConnectionCount|global/read-write maxEgressFlowCount|global/read-write maxEndpointCount|global/read-write maxIngressFlowCount|global/read-write maxMsgSpoolUsage|global/read-write maxSubscriptionCount|global/read-write maxTransactedSessionCount|global/read-write maxTransactionCount|global/read-write mqttRetainMaxMemory|global/read-write replicationBridgeAuthenticationBasicClientUsername|global/read-write replicationBridgeAuthenticationBasicPassword|global/read-write replicationBridgeAuthenticationClientCertContent|global/read-write replicationBridgeAuthenticationClientCertPassword|global/read-write replicationBridgeAuthenticationScheme|global/read-write replicationBridgeCompressedDataEnabled|global/read-write replicationBridgeEgressFlowWindowSize|global/read-write replicationBridgeRetryDelay|global/read-write replicationBridgeTlsEnabled|global/read-write replicationBridgeUnidirectionalClientProfileName|global/read-write replicationEnabled|global/read-write replicationEnabledQueueBehavior|global/read-write replicationQueueMaxMsgSpoolUsage|global/read-write replicationRole|global/read-write restTlsServerCertEnforceTrustedCommonNameEnabled|global/read-write restTlsServerCertMaxChainDepth|global/read-write restTlsServerCertValidateDateEnabled|global/read-write restTlsServerCertValidateNameEnabled|global/read-write sempOverMsgBusAdminClientEnabled|global/read-write sempOverMsgBusAdminDistributedCacheEnabled|global/read-write sempOverMsgBusAdminEnabled|global/read-write sempOverMsgBusEnabled|global/read-write sempOverMsgBusLegacyShowClearEnabled|global/read-write sempOverMsgBusShowEnabled|global/read-write serviceAmqpMaxConnectionCount|global/read-write serviceAmqpPlainTextListenPort|global/read-write serviceAmqpTlsListenPort|global/read-write serviceMqttMaxConnectionCount|global/read-write serviceMqttPlainTextListenPort|global/read-write serviceMqttTlsListenPort|global/read-write serviceMqttTlsWebSocketListenPort|global/read-write serviceMqttWebSocketListenPort|global/read-write serviceRestIncomingMaxConnectionCount|global/read-write serviceRestIncomingPlainTextListenPort|global/read-write serviceRestIncomingTlsListenPort|global/read-write serviceRestOutgoingMaxConnectionCount|global/read-write serviceSmfMaxConnectionCount|global/read-write serviceWebMaxConnectionCount|global/read-write    This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnTest() throws Exception {
        String msgVpnName = null;
        MsgVpn body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnResponse response = api.replaceMsgVpn(msgVpnName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace an ACL Profile object.
     *
     * Replace an ACL Profile object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  An ACL Profile controls whether an authenticated client is permitted to establish a connection with the message broker or permitted to publish and subscribe to specific topics.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: aclProfileName|x|x|||| msgVpnName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnAclProfileTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        MsgVpnAclProfile body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAclProfileResponse response = api.replaceMsgVpnAclProfile(msgVpnName, aclProfileName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace an OAuth Provider object.
     *
     * Replace an OAuth Provider object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  OAuth Providers contain information about the issuer of an OAuth token that is needed to validate the token and derive a client username from it.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x|x|||| oauthProviderName|x|x|||| tokenIntrospectionPassword|||x|||x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.13.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnAuthenticationOauthProviderTest() throws Exception {
        String msgVpnName = null;
        String oauthProviderName = null;
        MsgVpnAuthenticationOauthProvider body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAuthenticationOauthProviderResponse response = api.replaceMsgVpnAuthenticationOauthProvider(msgVpnName, oauthProviderName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace an LDAP Authorization Group object.
     *
     * Replace an LDAP Authorization Group object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  To use client authorization groups configured on an external LDAP server to provide client authorizations, LDAP Authorization Group objects must be created on the Message VPN that match the authorization groups provisioned on the LDAP server. These objects must be configured with the client profiles and ACL profiles that will be assigned to the clients that belong to those authorization groups. A newly created group is placed at the end of the group list which is the lowest priority.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: aclProfileName||||x|| authorizationGroupName|x|x|||| clientProfileName||||x|| msgVpnName|x|x|||| orderAfterAuthorizationGroupName|||x||| orderBeforeAuthorizationGroupName|||x|||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- MsgVpnAuthorizationGroup|orderAfterAuthorizationGroupName||orderBeforeAuthorizationGroupName MsgVpnAuthorizationGroup|orderBeforeAuthorizationGroupName||orderAfterAuthorizationGroupName    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnAuthorizationGroupTest() throws Exception {
        String msgVpnName = null;
        String authorizationGroupName = null;
        MsgVpnAuthorizationGroup body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAuthorizationGroupResponse response = api.replaceMsgVpnAuthorizationGroup(msgVpnName, authorizationGroupName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a Bridge object.
     *
     * Replace a Bridge object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  Bridges can be used to link two Message VPNs so that messages published to one Message VPN that match the topic subscriptions set for the bridge are also delivered to the linked Message VPN.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: bridgeName|x|x|||| bridgeVirtualRouter|x|x|||| maxTtl||||x|| msgVpnName|x|x|||| remoteAuthenticationBasicClientUsername||||x|| remoteAuthenticationBasicPassword|||x|x||x remoteAuthenticationClientCertContent|||x|x||x remoteAuthenticationClientCertPassword|||x|x|| remoteAuthenticationScheme||||x|| remoteDeliverToOnePriority||||x||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- MsgVpnBridge|remoteAuthenticationBasicClientUsername|remoteAuthenticationBasicPassword| MsgVpnBridge|remoteAuthenticationBasicPassword|remoteAuthenticationBasicClientUsername| MsgVpnBridge|remoteAuthenticationClientCertPassword|remoteAuthenticationClientCertContent|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnBridgeTest() throws Exception {
        String msgVpnName = null;
        String bridgeName = null;
        String bridgeVirtualRouter = null;
        MsgVpnBridge body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnBridgeResponse response = api.replaceMsgVpnBridge(msgVpnName, bridgeName, bridgeVirtualRouter, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a Remote Message VPN object.
     *
     * Replace a Remote Message VPN object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  The Remote Message VPN is the Message VPN that the Bridge connects to.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: bridgeName|x|x|||| bridgeVirtualRouter|x|x|||| clientUsername||||x|| compressedDataEnabled||||x|| egressFlowWindowSize||||x|| msgVpnName|x|x|||| password|||x|x||x remoteMsgVpnInterface|x|x|||| remoteMsgVpnLocation|x|x|||| remoteMsgVpnName|x|x|||| tlsEnabled||||x||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- MsgVpnBridgeRemoteMsgVpn|clientUsername|password| MsgVpnBridgeRemoteMsgVpn|password|clientUsername|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnBridgeRemoteMsgVpnTest() throws Exception {
        String msgVpnName = null;
        String bridgeName = null;
        String bridgeVirtualRouter = null;
        String remoteMsgVpnName = null;
        String remoteMsgVpnLocation = null;
        String remoteMsgVpnInterface = null;
        MsgVpnBridgeRemoteMsgVpn body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnBridgeRemoteMsgVpnResponse response = api.replaceMsgVpnBridgeRemoteMsgVpn(msgVpnName, bridgeName, bridgeVirtualRouter, remoteMsgVpnName, remoteMsgVpnLocation, remoteMsgVpnInterface, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a Client Profile object.
     *
     * Replace a Client Profile object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  Client Profiles are used to assign common configuration properties to clients that have been successfully authorized.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: allowCutThroughForwardingEnabled|||||x| apiQueueManagementCopyFromOnCreateName|||||x| apiTopicEndpointManagementCopyFromOnCreateName|||||x| clientProfileName|x|x|||| msgVpnName|x|x||||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent EventThresholdByPercent|clearPercent|setPercent| EventThresholdByPercent|setPercent|clearPercent|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnClientProfileTest() throws Exception {
        String msgVpnName = null;
        String clientProfileName = null;
        MsgVpnClientProfile body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnClientProfileResponse response = api.replaceMsgVpnClientProfile(msgVpnName, clientProfileName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a Client Username object.
     *
     * Replace a Client Username object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  A client is only authorized to connect to a Message VPN that is associated with a Client Username that the client has been assigned.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: aclProfileName||||x|| clientProfileName||||x|| clientUsername|x|x|||| msgVpnName|x|x|||| password|||x|||x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnClientUsernameTest() throws Exception {
        String msgVpnName = null;
        String clientUsername = null;
        MsgVpnClientUsername body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnClientUsernameResponse response = api.replaceMsgVpnClientUsername(msgVpnName, clientUsername, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a Distributed Cache object.
     *
     * Replace a Distributed Cache object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  A Distributed Cache is a collection of one or more Cache Clusters that belong to the same Message VPN. Each Cache Cluster in a Distributed Cache is configured to subscribe to a different set of topics. This effectively divides up the configured topic space, to provide scaling to very large topic spaces or very high cached message throughput.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: cacheName|x|x|||| msgVpnName|x|x||||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- MsgVpnDistributedCache|scheduledDeleteMsgDayList|scheduledDeleteMsgTimeList| MsgVpnDistributedCache|scheduledDeleteMsgTimeList|scheduledDeleteMsgDayList|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnDistributedCacheTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        MsgVpnDistributedCache body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnDistributedCacheResponse response = api.replaceMsgVpnDistributedCache(msgVpnName, cacheName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a Cache Cluster object.
     *
     * Replace a Cache Cluster object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  A Cache Cluster is a collection of one or more Cache Instances that subscribe to exactly the same topics. Cache Instances are grouped together in a Cache Cluster for the purpose of fault tolerance and load balancing. As published messages are received, the message broker message bus sends these live data messages to the Cache Instances in the Cache Cluster. This enables client cache requests to be served by any of Cache Instances in the Cache Cluster.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: cacheName|x|x|||| clusterName|x|x|||| msgVpnName|x|x||||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThresholdByPercent|clearPercent|setPercent| EventThresholdByPercent|setPercent|clearPercent| EventThresholdByValue|clearValue|setValue| EventThresholdByValue|setValue|clearValue|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnDistributedCacheClusterTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        MsgVpnDistributedCacheCluster body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterResponse response = api.replaceMsgVpnDistributedCacheCluster(msgVpnName, cacheName, clusterName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a Cache Instance object.
     *
     * Replace a Cache Instance object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  A Cache Instance is a single Cache process that belongs to a single Cache Cluster. A Cache Instance object provisioned on the broker is used to disseminate configuration information to the Cache process. Cache Instances listen for and cache live data messages that match the topic subscriptions configured for their parent Cache Cluster.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: cacheName|x|x|||| clusterName|x|x|||| instanceName|x|x|||| msgVpnName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnDistributedCacheClusterInstanceTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        String instanceName = null;
        MsgVpnDistributedCacheClusterInstance body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterInstanceResponse response = api.replaceMsgVpnDistributedCacheClusterInstance(msgVpnName, cacheName, clusterName, instanceName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a DMR Bridge object.
     *
     * Replace a DMR Bridge object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  A DMR Bridge is required to establish a data channel over a corresponding external link to the remote node for a given Message VPN. Each DMR Bridge identifies which external link the Message VPN should use, and what the name of the equivalent Message VPN at the remote node is.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x|x|||| remoteNodeName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnDmrBridgeTest() throws Exception {
        String msgVpnName = null;
        String remoteNodeName = null;
        MsgVpnDmrBridge body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnDmrBridgeResponse response = api.replaceMsgVpnDmrBridge(msgVpnName, remoteNodeName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a JNDI Connection Factory object.
     *
     * Replace a JNDI Connection Factory object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  The message broker provides an internal JNDI store for provisioned Connection Factory objects that clients can access through JNDI lookups.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: connectionFactoryName|x|x|||| msgVpnName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnJndiConnectionFactoryTest() throws Exception {
        String msgVpnName = null;
        String connectionFactoryName = null;
        MsgVpnJndiConnectionFactory body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnJndiConnectionFactoryResponse response = api.replaceMsgVpnJndiConnectionFactory(msgVpnName, connectionFactoryName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a JNDI Queue object.
     *
     * Replace a JNDI Queue object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  The message broker provides an internal JNDI store for provisioned Queue objects that clients can access through JNDI lookups.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x|x|||| queueName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnJndiQueueTest() throws Exception {
        String msgVpnName = null;
        String queueName = null;
        MsgVpnJndiQueue body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnJndiQueueResponse response = api.replaceMsgVpnJndiQueue(msgVpnName, queueName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a JNDI Topic object.
     *
     * Replace a JNDI Topic object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  The message broker provides an internal JNDI store for provisioned Topic objects that clients can access through JNDI lookups.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x|x|||| topicName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnJndiTopicTest() throws Exception {
        String msgVpnName = null;
        String topicName = null;
        MsgVpnJndiTopic body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnJndiTopicResponse response = api.replaceMsgVpnJndiTopic(msgVpnName, topicName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace an MQTT Retain Cache object.
     *
     * Replace an MQTT Retain Cache object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  Using MQTT retained messages allows publishing MQTT clients to indicate that a message must be stored for later delivery to subscribing clients when those subscribing clients add subscriptions matching the retained message&#39;s topic. An MQTT Retain Cache processes all retained messages for a Message VPN.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: cacheName|x|x|||| msgVpnName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnMqttRetainCacheTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        MsgVpnMqttRetainCache body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnMqttRetainCacheResponse response = api.replaceMsgVpnMqttRetainCache(msgVpnName, cacheName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace an MQTT Session object.
     *
     * Replace an MQTT Session object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  An MQTT Session object is a virtual representation of an MQTT client connection. An MQTT session holds the state of an MQTT client (that is, it is used to contain a client&#39;s QoS 0 and QoS 1 subscription sets and any undelivered QoS 1 messages).   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: mqttSessionClientId|x|x|||| mqttSessionVirtualRouter|x|x|||| msgVpnName|x|x|||| owner||||x||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnMqttSessionTest() throws Exception {
        String msgVpnName = null;
        String mqttSessionClientId = null;
        String mqttSessionVirtualRouter = null;
        MsgVpnMqttSession body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnMqttSessionResponse response = api.replaceMsgVpnMqttSession(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a Subscription object.
     *
     * Replace a Subscription object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  An MQTT session contains a client&#39;s QoS 0 and QoS 1 subscription sets. On creation, a subscription defaults to QoS 0.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: mqttSessionClientId|x|x|||| mqttSessionVirtualRouter|x|x|||| msgVpnName|x|x|||| subscriptionTopic|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnMqttSessionSubscriptionTest() throws Exception {
        String msgVpnName = null;
        String mqttSessionClientId = null;
        String mqttSessionVirtualRouter = null;
        String subscriptionTopic = null;
        MsgVpnMqttSessionSubscription body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnMqttSessionSubscriptionResponse response = api.replaceMsgVpnMqttSessionSubscription(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, subscriptionTopic, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a Queue object.
     *
     * Replace a Queue object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  A Queue acts as both a destination that clients can publish messages to, and as an endpoint that clients can bind consumers to and consume messages from.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: accessType||||x|| msgVpnName|x|x|||| owner||||x|| permission||||x|| queueName|x|x|||| respectMsgPriorityEnabled||||x||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnQueueTest() throws Exception {
        String msgVpnName = null;
        String queueName = null;
        MsgVpnQueue body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnQueueResponse response = api.replaceMsgVpnQueue(msgVpnName, queueName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a Queue Template object.
     *
     * Replace a Queue Template object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  A Queue Template provides a mechanism for specifying the initial state for client created queues.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x|x|||| queueTemplateName|x|x||||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnQueueTemplateTest() throws Exception {
        String msgVpnName = null;
        String queueTemplateName = null;
        MsgVpnQueueTemplate body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnQueueTemplateResponse response = api.replaceMsgVpnQueueTemplate(msgVpnName, queueTemplateName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a Replay Log object.
     *
     * Replace a Replay Log object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  When the Message Replay feature is enabled, message brokers store persistent messages in a Replay Log. These messages are kept until the log is full, after which the oldest messages are removed to free up space for new messages.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x|x|||| replayLogName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.10.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnReplayLogTest() throws Exception {
        String msgVpnName = null;
        String replayLogName = null;
        MsgVpnReplayLog body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnReplayLogResponse response = api.replaceMsgVpnReplayLog(msgVpnName, replayLogName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a Replicated Topic object.
     *
     * Replace a Replicated Topic object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  To indicate which messages should be replicated between the active and standby site, a Replicated Topic subscription must be configured on a Message VPN. If a published message matches both a replicated topic and an endpoint on the active site, then the message is replicated to the standby site.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x|x|||| replicatedTopic|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnReplicatedTopicTest() throws Exception {
        String msgVpnName = null;
        String replicatedTopic = null;
        MsgVpnReplicatedTopic body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnReplicatedTopicResponse response = api.replaceMsgVpnReplicatedTopic(msgVpnName, replicatedTopic, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a REST Delivery Point object.
     *
     * Replace a REST Delivery Point object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  A REST Delivery Point manages delivery of messages from queues to a named list of REST Consumers.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: clientProfileName||||x|| msgVpnName|x|x|||| restDeliveryPointName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnRestDeliveryPointTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        MsgVpnRestDeliveryPoint body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointResponse response = api.replaceMsgVpnRestDeliveryPoint(msgVpnName, restDeliveryPointName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a Queue Binding object.
     *
     * Replace a Queue Binding object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  A Queue Binding for a REST Delivery Point attracts messages to be delivered to REST consumers. If the queue does not exist it can be created subsequently, and once the queue is operational the broker performs the queue binding. Removing the queue binding does not delete the queue itself. Similarly, removing the queue does not remove the queue binding, which fails until the queue is recreated or the queue binding is deleted.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x|x|||| queueBindingName|x|x|||| restDeliveryPointName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnRestDeliveryPointQueueBindingTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String queueBindingName = null;
        MsgVpnRestDeliveryPointQueueBinding body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointQueueBindingResponse response = api.replaceMsgVpnRestDeliveryPointQueueBinding(msgVpnName, restDeliveryPointName, queueBindingName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a Request Header object.
     *
     * Replace a Request Header object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  A request header to be added to the HTTP request.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: headerName|x|x|||| msgVpnName|x|x|||| queueBindingName|x|x|||| restDeliveryPointName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.23.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnRestDeliveryPointQueueBindingRequestHeaderTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String queueBindingName = null;
        String headerName = null;
        MsgVpnRestDeliveryPointQueueBindingRequestHeader body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointQueueBindingRequestHeaderResponse response = api.replaceMsgVpnRestDeliveryPointQueueBindingRequestHeader(msgVpnName, restDeliveryPointName, queueBindingName, headerName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a REST Consumer object.
     *
     * Replace a REST Consumer object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  REST Consumer objects establish HTTP connectivity to REST consumer applications who wish to receive messages from a broker.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: authenticationClientCertContent|||x|x||x authenticationClientCertPassword|||x|x|| authenticationHttpBasicPassword|||x|x||x authenticationHttpBasicUsername||||x|| authenticationHttpHeaderValue|||x|||x authenticationOauthClientId||||x|| authenticationOauthClientScope||||x|| authenticationOauthClientSecret|||x|x||x authenticationOauthClientTokenEndpoint||||x|| authenticationOauthJwtSecretKey|||x|x||x authenticationOauthJwtTokenEndpoint||||x|| authenticationScheme||||x|| msgVpnName|x|x|||| outgoingConnectionCount||||x|| remoteHost||||x|| remotePort||||x|| restConsumerName|x|x|||| restDeliveryPointName|x|x|||| tlsCipherSuiteList||||x|| tlsEnabled||||x||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- MsgVpnRestDeliveryPointRestConsumer|authenticationClientCertPassword|authenticationClientCertContent| MsgVpnRestDeliveryPointRestConsumer|authenticationHttpBasicPassword|authenticationHttpBasicUsername| MsgVpnRestDeliveryPointRestConsumer|authenticationHttpBasicUsername|authenticationHttpBasicPassword| MsgVpnRestDeliveryPointRestConsumer|remotePort|tlsEnabled| MsgVpnRestDeliveryPointRestConsumer|tlsEnabled|remotePort|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnRestDeliveryPointRestConsumerTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String restConsumerName = null;
        MsgVpnRestDeliveryPointRestConsumer body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointRestConsumerResponse response = api.replaceMsgVpnRestDeliveryPointRestConsumer(msgVpnName, restDeliveryPointName, restConsumerName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a Topic Endpoint object.
     *
     * Replace a Topic Endpoint object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  A Topic Endpoint attracts messages published to a topic for which the Topic Endpoint has a matching topic subscription. The topic subscription for the Topic Endpoint is specified in the client request to bind a Flow to that Topic Endpoint. Queues are significantly more flexible than Topic Endpoints and are the recommended approach for most applications. The use of Topic Endpoints should be restricted to JMS applications.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: accessType||||x|| msgVpnName|x|x|||| owner||||x|| permission||||x|| respectMsgPriorityEnabled||||x|| topicEndpointName|x|x||||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnTopicEndpointTest() throws Exception {
        String msgVpnName = null;
        String topicEndpointName = null;
        MsgVpnTopicEndpoint body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnTopicEndpointResponse response = api.replaceMsgVpnTopicEndpoint(msgVpnName, topicEndpointName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a Topic Endpoint Template object.
     *
     * Replace a Topic Endpoint Template object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  A Topic Endpoint Template provides a mechanism for specifying the initial state for client created topic endpoints.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x|x|||| topicEndpointTemplateName|x|x||||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnTopicEndpointTemplateTest() throws Exception {
        String msgVpnName = null;
        String topicEndpointTemplateName = null;
        MsgVpnTopicEndpointTemplate body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnTopicEndpointTemplateResponse response = api.replaceMsgVpnTopicEndpointTemplate(msgVpnName, topicEndpointTemplateName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Replace a Virtual Hostname object.
     *
     * Replace a Virtual Hostname object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  A Virtual Hostname is a provisioned object on a message broker that contains a Virtual Hostname to Message VPN mapping.  Clients which connect to a global (as opposed to per Message VPN) port and provides this hostname will be directed to its corresponding Message VPN. A case-insentive match is performed on the full client-provided hostname against the configured virtual-hostname.  This mechanism is only supported for hostnames provided through the Server Name Indication (SNI) extension of TLS.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: virtualHostname|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.17.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void replaceVirtualHostnameTest() throws Exception {
        String virtualHostname = null;
        VirtualHostname body = null;
        String opaquePassword = null;
        List<String> select = null;
        VirtualHostnameResponse response = api.replaceVirtualHostname(virtualHostname, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a Broker object.
     *
     * Update a Broker object. Any attribute missing from the request will be left unchanged.  This object contains global configuration for the message broker.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: guaranteedMsgingDiskLocation||||x|| guaranteedMsgingVirtualRouterWhenActiveActive||||x|| serviceAmqpTlsListenPort||||x|| serviceHealthCheckListenPort||||x|| serviceSempPlainTextListenPort||||x|| serviceSempTlsListenPort||||x|| serviceSmfCompressionListenPort||||x|| serviceSmfPlainTextListenPort||||x|| serviceSmfRoutingControlListenPort||||x|| serviceSmfTlsListenPort||||x|| serviceWebTransportPlainTextListenPort||||x|| serviceWebTransportTlsListenPort||||x|| serviceWebTransportWebUrlSuffix||||x|| tlsServerCertContent|||x|||x tlsServerCertPassword|||x|||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- Broker|tlsServerCertPassword|tlsServerCertContent| EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent EventThresholdByPercent|clearPercent|setPercent| EventThresholdByPercent|setPercent|clearPercent|    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation. Requests which include the following attributes require greater access scope/level:   Attribute|Access Scope/Level :---|:---: authClientCertRevocationCheckMode|global/admin configSyncAuthenticationClientCertMaxChainDepth|global/read-write configSyncAuthenticationClientCertValidateDateEnabled|global/read-write configSyncClientProfileTcpInitialCongestionWindow|global/read-write configSyncClientProfileTcpKeepaliveCount|global/read-write configSyncClientProfileTcpKeepaliveIdle|global/read-write configSyncClientProfileTcpKeepaliveInterval|global/read-write configSyncClientProfileTcpMaxWindow|global/read-write configSyncClientProfileTcpMss|global/read-write configSyncEnabled|global/read-write configSyncSynchronizeUsernameEnabled|global/read-write configSyncTlsEnabled|global/read-write guaranteedMsgingDiskArrayWwn|global/read-write guaranteedMsgingDiskLocation|global/read-write guaranteedMsgingEnabled|global/read-write guaranteedMsgingEventCacheUsageThreshold.clearPercent|global/read-write guaranteedMsgingEventCacheUsageThreshold.clearValue|global/read-write guaranteedMsgingEventCacheUsageThreshold.setPercent|global/read-write guaranteedMsgingEventCacheUsageThreshold.setValue|global/read-write guaranteedMsgingEventDeliveredUnackedThreshold.clearPercent|global/read-write guaranteedMsgingEventDeliveredUnackedThreshold.setPercent|global/read-write guaranteedMsgingEventDiskUsageThreshold.clearPercent|global/read-write guaranteedMsgingEventDiskUsageThreshold.setPercent|global/read-write guaranteedMsgingEventEgressFlowCountThreshold.clearPercent|global/read-write guaranteedMsgingEventEgressFlowCountThreshold.clearValue|global/read-write guaranteedMsgingEventEgressFlowCountThreshold.setPercent|global/read-write guaranteedMsgingEventEgressFlowCountThreshold.setValue|global/read-write guaranteedMsgingEventEndpointCountThreshold.clearPercent|global/read-write guaranteedMsgingEventEndpointCountThreshold.clearValue|global/read-write guaranteedMsgingEventEndpointCountThreshold.setPercent|global/read-write guaranteedMsgingEventEndpointCountThreshold.setValue|global/read-write guaranteedMsgingEventIngressFlowCountThreshold.clearPercent|global/read-write guaranteedMsgingEventIngressFlowCountThreshold.clearValue|global/read-write guaranteedMsgingEventIngressFlowCountThreshold.setPercent|global/read-write guaranteedMsgingEventIngressFlowCountThreshold.setValue|global/read-write guaranteedMsgingEventMsgCountThreshold.clearPercent|global/read-write guaranteedMsgingEventMsgCountThreshold.setPercent|global/read-write guaranteedMsgingEventMsgSpoolFileCountThreshold.clearPercent|global/read-write guaranteedMsgingEventMsgSpoolFileCountThreshold.setPercent|global/read-write guaranteedMsgingEventMsgSpoolUsageThreshold.clearPercent|global/read-write guaranteedMsgingEventMsgSpoolUsageThreshold.clearValue|global/read-write guaranteedMsgingEventMsgSpoolUsageThreshold.setPercent|global/read-write guaranteedMsgingEventMsgSpoolUsageThreshold.setValue|global/read-write guaranteedMsgingEventTransactedSessionCountThreshold.clearPercent|global/read-write guaranteedMsgingEventTransactedSessionCountThreshold.clearValue|global/read-write guaranteedMsgingEventTransactedSessionCountThreshold.setPercent|global/read-write guaranteedMsgingEventTransactedSessionCountThreshold.setValue|global/read-write guaranteedMsgingEventTransactedSessionResourceCountThreshold.clearPercent|global/read-write guaranteedMsgingEventTransactedSessionResourceCountThreshold.setPercent|global/read-write guaranteedMsgingEventTransactionCountThreshold.clearPercent|global/read-write guaranteedMsgingEventTransactionCountThreshold.clearValue|global/read-write guaranteedMsgingEventTransactionCountThreshold.setPercent|global/read-write guaranteedMsgingEventTransactionCountThreshold.setValue|global/read-write guaranteedMsgingMaxCacheUsage|global/read-write guaranteedMsgingMaxMsgSpoolUsage|global/read-write guaranteedMsgingTransactionReplicationCompatibilityMode|global/read-write guaranteedMsgingVirtualRouterWhenActiveActive|global/read-write serviceAmqpEnabled|global/read-write serviceAmqpTlsListenPort|global/read-write serviceEventConnectionCountThreshold.clearPercent|global/read-write serviceEventConnectionCountThreshold.clearValue|global/read-write serviceEventConnectionCountThreshold.setPercent|global/read-write serviceEventConnectionCountThreshold.setValue|global/read-write serviceHealthCheckEnabled|global/read-write serviceHealthCheckListenPort|global/read-write serviceMqttEnabled|global/read-write serviceMsgBackboneEnabled|global/read-write serviceRestEventOutgoingConnectionCountThreshold.clearPercent|global/read-write serviceRestEventOutgoingConnectionCountThreshold.clearValue|global/read-write serviceRestEventOutgoingConnectionCountThreshold.setPercent|global/read-write serviceRestEventOutgoingConnectionCountThreshold.setValue|global/read-write serviceRestIncomingEnabled|global/read-write serviceRestOutgoingEnabled|global/read-write serviceSempLegacyTimeoutEnabled|global/read-write serviceSempPlainTextEnabled|global/read-write serviceSempPlainTextListenPort|global/read-write serviceSempSessionIdleTimeout|global/read-write serviceSempSessionMaxLifetime|global/read-write serviceSempTlsEnabled|global/read-write serviceSempTlsListenPort|global/read-write serviceSmfCompressionListenPort|global/read-write serviceSmfEnabled|global/read-write serviceSmfEventConnectionCountThreshold.clearPercent|global/read-write serviceSmfEventConnectionCountThreshold.clearValue|global/read-write serviceSmfEventConnectionCountThreshold.setPercent|global/read-write serviceSmfEventConnectionCountThreshold.setValue|global/read-write serviceSmfPlainTextListenPort|global/read-write serviceSmfRoutingControlListenPort|global/read-write serviceSmfTlsListenPort|global/read-write serviceTlsEventConnectionCountThreshold.clearPercent|global/read-write serviceTlsEventConnectionCountThreshold.clearValue|global/read-write serviceTlsEventConnectionCountThreshold.setPercent|global/read-write serviceTlsEventConnectionCountThreshold.setValue|global/read-write serviceWebTransportEnabled|global/read-write serviceWebTransportPlainTextListenPort|global/read-write serviceWebTransportTlsListenPort|global/read-write serviceWebTransportWebUrlSuffix|global/read-write tlsBlockVersion10Enabled|global/read-write tlsBlockVersion11Enabled|global/read-write tlsCipherSuiteManagementList|global/read-write tlsCipherSuiteMsgBackboneList|global/read-write tlsCipherSuiteSecureShellList|global/read-write tlsCrimeExploitProtectionEnabled|global/read-write tlsServerCertContent|global/read-write tlsServerCertPassword|global/read-write tlsStandardDomainCertificateAuthoritiesEnabled|global/read-write tlsTicketLifetime|global/read-write    This has been available since 2.13.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateBrokerTest() throws Exception {
        Broker body = null;
        String opaquePassword = null;
        List<String> select = null;
        BrokerResponse response = api.updateBroker(body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a Certificate Authority object.
     *
     * Update a Certificate Authority object. Any attribute missing from the request will be left unchanged.  Clients can authenticate with the message broker over TLS by presenting a valid client certificate. The message broker authenticates the client certificate by constructing a full certificate chain (from the client certificate to intermediate CAs to a configured root CA). The intermediate CAs in this chain can be provided by the client, or configured in the message broker. The root CA must be configured on the message broker.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: certAuthorityName|x|x|||x| certContent|||||x| crlDayList|||||x| crlTimeList|||||x| crlUrl||||x|x| ocspNonResponderCertEnabled|||||x| ocspOverrideUrl|||||x| ocspTimeout|||||x| revocationCheckEnabled|||||x|    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- CertAuthority|crlDayList|crlTimeList| CertAuthority|crlTimeList|crlDayList|    A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been deprecated since 2.19. Replaced by clientCertAuthorities and domainCertAuthorities.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateCertAuthorityTest() throws Exception {
        String certAuthorityName = null;
        CertAuthority body = null;
        String opaquePassword = null;
        List<String> select = null;
        CertAuthorityResponse response = api.updateCertAuthority(certAuthorityName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a Client Certificate Authority object.
     *
     * Update a Client Certificate Authority object. Any attribute missing from the request will be left unchanged.  Clients can authenticate with the message broker over TLS by presenting a valid client certificate. The message broker authenticates the client certificate by constructing a full certificate chain (from the client certificate to intermediate CAs to a configured root CA). The intermediate CAs in this chain can be provided by the client, or configured in the message broker. The root CA must be configured on the message broker.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: certAuthorityName|x|x|||| crlUrl||||x||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- ClientCertAuthority|crlDayList|crlTimeList| ClientCertAuthority|crlTimeList|crlDayList|    A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been available since 2.19.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateClientCertAuthorityTest() throws Exception {
        String certAuthorityName = null;
        ClientCertAuthority body = null;
        String opaquePassword = null;
        List<String> select = null;
        ClientCertAuthorityResponse response = api.updateClientCertAuthority(certAuthorityName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a Cluster object.
     *
     * Update a Cluster object. Any attribute missing from the request will be left unchanged.  A Cluster is a provisioned object on a message broker that contains global DMR configuration parameters.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: authenticationBasicPassword|||x|x||x authenticationClientCertContent|||x|x||x authenticationClientCertPassword|||x|x|| directOnlyEnabled||x|||| dmrClusterName|x|x|||| nodeName||x|||| tlsServerCertEnforceTrustedCommonNameEnabled|||||x|    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- DmrCluster|authenticationClientCertPassword|authenticationClientCertContent|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateDmrClusterTest() throws Exception {
        String dmrClusterName = null;
        DmrCluster body = null;
        String opaquePassword = null;
        List<String> select = null;
        DmrClusterResponse response = api.updateDmrCluster(dmrClusterName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a Link object.
     *
     * Update a Link object. Any attribute missing from the request will be left unchanged.  A Link connects nodes (either within a Cluster or between two different Clusters) and allows them to exchange topology information, subscriptions and data.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: authenticationBasicPassword|||x|x||x authenticationScheme||||x|| dmrClusterName|x|x|||| egressFlowWindowSize||||x|| initiator||||x|| remoteNodeName|x|x|||| span||||x|| transportCompressedEnabled||||x|| transportTlsEnabled||||x||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateDmrClusterLinkTest() throws Exception {
        String dmrClusterName = null;
        String remoteNodeName = null;
        DmrClusterLink body = null;
        String opaquePassword = null;
        List<String> select = null;
        DmrClusterLinkResponse response = api.updateDmrClusterLink(dmrClusterName, remoteNodeName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a Domain Certificate Authority object.
     *
     * Update a Domain Certificate Authority object. Any attribute missing from the request will be left unchanged.  Certificate Authorities trusted for domain verification.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: certAuthorityName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been available since 2.19.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateDomainCertAuthorityTest() throws Exception {
        String certAuthorityName = null;
        DomainCertAuthority body = null;
        String opaquePassword = null;
        List<String> select = null;
        DomainCertAuthorityResponse response = api.updateDomainCertAuthority(certAuthorityName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a Message VPN object.
     *
     * Update a Message VPN object. Any attribute missing from the request will be left unchanged.  Message VPNs (Virtual Private Networks) allow for the segregation of topic space and clients. They also group clients connecting to a network of message brokers, such that messages published within a particular group are only visible to that group&#39;s clients.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: bridgingTlsServerCertEnforceTrustedCommonNameEnabled|||||x| msgVpnName|x|x|||| replicationBridgeAuthenticationBasicPassword|||x|||x replicationBridgeAuthenticationClientCertContent|||x|||x replicationBridgeAuthenticationClientCertPassword|||x||| replicationEnabledQueueBehavior|||x||| restTlsServerCertEnforceTrustedCommonNameEnabled|||||x|    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent EventThresholdByValue|clearValue|setValue| EventThresholdByValue|setValue|clearValue| MsgVpn|authenticationBasicProfileName|authenticationBasicType| MsgVpn|authorizationProfileName|authorizationType| MsgVpn|eventPublishTopicFormatMqttEnabled|eventPublishTopicFormatSmfEnabled| MsgVpn|eventPublishTopicFormatSmfEnabled|eventPublishTopicFormatMqttEnabled| MsgVpn|replicationBridgeAuthenticationBasicClientUsername|replicationBridgeAuthenticationBasicPassword| MsgVpn|replicationBridgeAuthenticationBasicPassword|replicationBridgeAuthenticationBasicClientUsername| MsgVpn|replicationBridgeAuthenticationClientCertPassword|replicationBridgeAuthenticationClientCertContent| MsgVpn|replicationEnabledQueueBehavior|replicationEnabled|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation. Requests which include the following attributes require greater access scope/level:   Attribute|Access Scope/Level :---|:---: alias|global/read-write authenticationBasicEnabled|global/read-write authenticationBasicProfileName|global/read-write authenticationBasicRadiusDomain|global/read-write authenticationBasicType|global/read-write authenticationClientCertAllowApiProvidedUsernameEnabled|global/read-write authenticationClientCertEnabled|global/read-write authenticationClientCertMaxChainDepth|global/read-write authenticationClientCertRevocationCheckMode|global/read-write authenticationClientCertUsernameSource|global/read-write authenticationClientCertValidateDateEnabled|global/read-write authenticationKerberosAllowApiProvidedUsernameEnabled|global/read-write authenticationKerberosEnabled|global/read-write authenticationOauthEnabled|global/read-write bridgingTlsServerCertEnforceTrustedCommonNameEnabled|global/read-write bridgingTlsServerCertMaxChainDepth|global/read-write bridgingTlsServerCertValidateDateEnabled|global/read-write bridgingTlsServerCertValidateNameEnabled|global/read-write dmrEnabled|global/read-write exportSubscriptionsEnabled|global/read-write maxConnectionCount|global/read-write maxEgressFlowCount|global/read-write maxEndpointCount|global/read-write maxIngressFlowCount|global/read-write maxMsgSpoolUsage|global/read-write maxSubscriptionCount|global/read-write maxTransactedSessionCount|global/read-write maxTransactionCount|global/read-write mqttRetainMaxMemory|global/read-write replicationBridgeAuthenticationBasicClientUsername|global/read-write replicationBridgeAuthenticationBasicPassword|global/read-write replicationBridgeAuthenticationClientCertContent|global/read-write replicationBridgeAuthenticationClientCertPassword|global/read-write replicationBridgeAuthenticationScheme|global/read-write replicationBridgeCompressedDataEnabled|global/read-write replicationBridgeEgressFlowWindowSize|global/read-write replicationBridgeRetryDelay|global/read-write replicationBridgeTlsEnabled|global/read-write replicationBridgeUnidirectionalClientProfileName|global/read-write replicationEnabled|global/read-write replicationEnabledQueueBehavior|global/read-write replicationQueueMaxMsgSpoolUsage|global/read-write replicationRole|global/read-write restTlsServerCertEnforceTrustedCommonNameEnabled|global/read-write restTlsServerCertMaxChainDepth|global/read-write restTlsServerCertValidateDateEnabled|global/read-write restTlsServerCertValidateNameEnabled|global/read-write sempOverMsgBusAdminClientEnabled|global/read-write sempOverMsgBusAdminDistributedCacheEnabled|global/read-write sempOverMsgBusAdminEnabled|global/read-write sempOverMsgBusEnabled|global/read-write sempOverMsgBusLegacyShowClearEnabled|global/read-write sempOverMsgBusShowEnabled|global/read-write serviceAmqpMaxConnectionCount|global/read-write serviceAmqpPlainTextListenPort|global/read-write serviceAmqpTlsListenPort|global/read-write serviceMqttMaxConnectionCount|global/read-write serviceMqttPlainTextListenPort|global/read-write serviceMqttTlsListenPort|global/read-write serviceMqttTlsWebSocketListenPort|global/read-write serviceMqttWebSocketListenPort|global/read-write serviceRestIncomingMaxConnectionCount|global/read-write serviceRestIncomingPlainTextListenPort|global/read-write serviceRestIncomingTlsListenPort|global/read-write serviceRestOutgoingMaxConnectionCount|global/read-write serviceSmfMaxConnectionCount|global/read-write serviceWebMaxConnectionCount|global/read-write    This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnTest() throws Exception {
        String msgVpnName = null;
        MsgVpn body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnResponse response = api.updateMsgVpn(msgVpnName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update an ACL Profile object.
     *
     * Update an ACL Profile object. Any attribute missing from the request will be left unchanged.  An ACL Profile controls whether an authenticated client is permitted to establish a connection with the message broker or permitted to publish and subscribe to specific topics.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: aclProfileName|x|x|||| msgVpnName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnAclProfileTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        MsgVpnAclProfile body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAclProfileResponse response = api.updateMsgVpnAclProfile(msgVpnName, aclProfileName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update an OAuth Provider object.
     *
     * Update an OAuth Provider object. Any attribute missing from the request will be left unchanged.  OAuth Providers contain information about the issuer of an OAuth token that is needed to validate the token and derive a client username from it.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x|x|||| oauthProviderName|x|x|||| tokenIntrospectionPassword|||x|||x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.13.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnAuthenticationOauthProviderTest() throws Exception {
        String msgVpnName = null;
        String oauthProviderName = null;
        MsgVpnAuthenticationOauthProvider body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAuthenticationOauthProviderResponse response = api.updateMsgVpnAuthenticationOauthProvider(msgVpnName, oauthProviderName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update an LDAP Authorization Group object.
     *
     * Update an LDAP Authorization Group object. Any attribute missing from the request will be left unchanged.  To use client authorization groups configured on an external LDAP server to provide client authorizations, LDAP Authorization Group objects must be created on the Message VPN that match the authorization groups provisioned on the LDAP server. These objects must be configured with the client profiles and ACL profiles that will be assigned to the clients that belong to those authorization groups. A newly created group is placed at the end of the group list which is the lowest priority.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: aclProfileName||||x|| authorizationGroupName|x|x|||| clientProfileName||||x|| msgVpnName|x|x|||| orderAfterAuthorizationGroupName|||x||| orderBeforeAuthorizationGroupName|||x|||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- MsgVpnAuthorizationGroup|orderAfterAuthorizationGroupName||orderBeforeAuthorizationGroupName MsgVpnAuthorizationGroup|orderBeforeAuthorizationGroupName||orderAfterAuthorizationGroupName    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnAuthorizationGroupTest() throws Exception {
        String msgVpnName = null;
        String authorizationGroupName = null;
        MsgVpnAuthorizationGroup body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAuthorizationGroupResponse response = api.updateMsgVpnAuthorizationGroup(msgVpnName, authorizationGroupName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a Bridge object.
     *
     * Update a Bridge object. Any attribute missing from the request will be left unchanged.  Bridges can be used to link two Message VPNs so that messages published to one Message VPN that match the topic subscriptions set for the bridge are also delivered to the linked Message VPN.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: bridgeName|x|x|||| bridgeVirtualRouter|x|x|||| maxTtl||||x|| msgVpnName|x|x|||| remoteAuthenticationBasicClientUsername||||x|| remoteAuthenticationBasicPassword|||x|x||x remoteAuthenticationClientCertContent|||x|x||x remoteAuthenticationClientCertPassword|||x|x|| remoteAuthenticationScheme||||x|| remoteDeliverToOnePriority||||x||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- MsgVpnBridge|remoteAuthenticationBasicClientUsername|remoteAuthenticationBasicPassword| MsgVpnBridge|remoteAuthenticationBasicPassword|remoteAuthenticationBasicClientUsername| MsgVpnBridge|remoteAuthenticationClientCertPassword|remoteAuthenticationClientCertContent|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnBridgeTest() throws Exception {
        String msgVpnName = null;
        String bridgeName = null;
        String bridgeVirtualRouter = null;
        MsgVpnBridge body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnBridgeResponse response = api.updateMsgVpnBridge(msgVpnName, bridgeName, bridgeVirtualRouter, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a Remote Message VPN object.
     *
     * Update a Remote Message VPN object. Any attribute missing from the request will be left unchanged.  The Remote Message VPN is the Message VPN that the Bridge connects to.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: bridgeName|x|x|||| bridgeVirtualRouter|x|x|||| clientUsername||||x|| compressedDataEnabled||||x|| egressFlowWindowSize||||x|| msgVpnName|x|x|||| password|||x|x||x remoteMsgVpnInterface|x|x|||| remoteMsgVpnLocation|x|x|||| remoteMsgVpnName|x|x|||| tlsEnabled||||x||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- MsgVpnBridgeRemoteMsgVpn|clientUsername|password| MsgVpnBridgeRemoteMsgVpn|password|clientUsername|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnBridgeRemoteMsgVpnTest() throws Exception {
        String msgVpnName = null;
        String bridgeName = null;
        String bridgeVirtualRouter = null;
        String remoteMsgVpnName = null;
        String remoteMsgVpnLocation = null;
        String remoteMsgVpnInterface = null;
        MsgVpnBridgeRemoteMsgVpn body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnBridgeRemoteMsgVpnResponse response = api.updateMsgVpnBridgeRemoteMsgVpn(msgVpnName, bridgeName, bridgeVirtualRouter, remoteMsgVpnName, remoteMsgVpnLocation, remoteMsgVpnInterface, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a Client Profile object.
     *
     * Update a Client Profile object. Any attribute missing from the request will be left unchanged.  Client Profiles are used to assign common configuration properties to clients that have been successfully authorized.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: allowCutThroughForwardingEnabled|||||x| apiQueueManagementCopyFromOnCreateName|||||x| apiTopicEndpointManagementCopyFromOnCreateName|||||x| clientProfileName|x|x|||| msgVpnName|x|x||||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent EventThresholdByPercent|clearPercent|setPercent| EventThresholdByPercent|setPercent|clearPercent|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnClientProfileTest() throws Exception {
        String msgVpnName = null;
        String clientProfileName = null;
        MsgVpnClientProfile body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnClientProfileResponse response = api.updateMsgVpnClientProfile(msgVpnName, clientProfileName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a Client Username object.
     *
     * Update a Client Username object. Any attribute missing from the request will be left unchanged.  A client is only authorized to connect to a Message VPN that is associated with a Client Username that the client has been assigned.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: aclProfileName||||x|| clientProfileName||||x|| clientUsername|x|x|||| msgVpnName|x|x|||| password|||x|||x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnClientUsernameTest() throws Exception {
        String msgVpnName = null;
        String clientUsername = null;
        MsgVpnClientUsername body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnClientUsernameResponse response = api.updateMsgVpnClientUsername(msgVpnName, clientUsername, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a Distributed Cache object.
     *
     * Update a Distributed Cache object. Any attribute missing from the request will be left unchanged.  A Distributed Cache is a collection of one or more Cache Clusters that belong to the same Message VPN. Each Cache Cluster in a Distributed Cache is configured to subscribe to a different set of topics. This effectively divides up the configured topic space, to provide scaling to very large topic spaces or very high cached message throughput.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: cacheName|x|x|||| msgVpnName|x|x||||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- MsgVpnDistributedCache|scheduledDeleteMsgDayList|scheduledDeleteMsgTimeList| MsgVpnDistributedCache|scheduledDeleteMsgTimeList|scheduledDeleteMsgDayList|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnDistributedCacheTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        MsgVpnDistributedCache body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnDistributedCacheResponse response = api.updateMsgVpnDistributedCache(msgVpnName, cacheName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a Cache Cluster object.
     *
     * Update a Cache Cluster object. Any attribute missing from the request will be left unchanged.  A Cache Cluster is a collection of one or more Cache Instances that subscribe to exactly the same topics. Cache Instances are grouped together in a Cache Cluster for the purpose of fault tolerance and load balancing. As published messages are received, the message broker message bus sends these live data messages to the Cache Instances in the Cache Cluster. This enables client cache requests to be served by any of Cache Instances in the Cache Cluster.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: cacheName|x|x|||| clusterName|x|x|||| msgVpnName|x|x||||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThresholdByPercent|clearPercent|setPercent| EventThresholdByPercent|setPercent|clearPercent| EventThresholdByValue|clearValue|setValue| EventThresholdByValue|setValue|clearValue|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnDistributedCacheClusterTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        MsgVpnDistributedCacheCluster body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterResponse response = api.updateMsgVpnDistributedCacheCluster(msgVpnName, cacheName, clusterName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a Cache Instance object.
     *
     * Update a Cache Instance object. Any attribute missing from the request will be left unchanged.  A Cache Instance is a single Cache process that belongs to a single Cache Cluster. A Cache Instance object provisioned on the broker is used to disseminate configuration information to the Cache process. Cache Instances listen for and cache live data messages that match the topic subscriptions configured for their parent Cache Cluster.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: cacheName|x|x|||| clusterName|x|x|||| instanceName|x|x|||| msgVpnName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnDistributedCacheClusterInstanceTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        String instanceName = null;
        MsgVpnDistributedCacheClusterInstance body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterInstanceResponse response = api.updateMsgVpnDistributedCacheClusterInstance(msgVpnName, cacheName, clusterName, instanceName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a DMR Bridge object.
     *
     * Update a DMR Bridge object. Any attribute missing from the request will be left unchanged.  A DMR Bridge is required to establish a data channel over a corresponding external link to the remote node for a given Message VPN. Each DMR Bridge identifies which external link the Message VPN should use, and what the name of the equivalent Message VPN at the remote node is.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x|x|||| remoteNodeName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnDmrBridgeTest() throws Exception {
        String msgVpnName = null;
        String remoteNodeName = null;
        MsgVpnDmrBridge body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnDmrBridgeResponse response = api.updateMsgVpnDmrBridge(msgVpnName, remoteNodeName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a JNDI Connection Factory object.
     *
     * Update a JNDI Connection Factory object. Any attribute missing from the request will be left unchanged.  The message broker provides an internal JNDI store for provisioned Connection Factory objects that clients can access through JNDI lookups.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: connectionFactoryName|x|x|||| msgVpnName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnJndiConnectionFactoryTest() throws Exception {
        String msgVpnName = null;
        String connectionFactoryName = null;
        MsgVpnJndiConnectionFactory body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnJndiConnectionFactoryResponse response = api.updateMsgVpnJndiConnectionFactory(msgVpnName, connectionFactoryName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a JNDI Queue object.
     *
     * Update a JNDI Queue object. Any attribute missing from the request will be left unchanged.  The message broker provides an internal JNDI store for provisioned Queue objects that clients can access through JNDI lookups.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x|x|||| queueName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnJndiQueueTest() throws Exception {
        String msgVpnName = null;
        String queueName = null;
        MsgVpnJndiQueue body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnJndiQueueResponse response = api.updateMsgVpnJndiQueue(msgVpnName, queueName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a JNDI Topic object.
     *
     * Update a JNDI Topic object. Any attribute missing from the request will be left unchanged.  The message broker provides an internal JNDI store for provisioned Topic objects that clients can access through JNDI lookups.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x|x|||| topicName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnJndiTopicTest() throws Exception {
        String msgVpnName = null;
        String topicName = null;
        MsgVpnJndiTopic body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnJndiTopicResponse response = api.updateMsgVpnJndiTopic(msgVpnName, topicName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update an MQTT Retain Cache object.
     *
     * Update an MQTT Retain Cache object. Any attribute missing from the request will be left unchanged.  Using MQTT retained messages allows publishing MQTT clients to indicate that a message must be stored for later delivery to subscribing clients when those subscribing clients add subscriptions matching the retained message&#39;s topic. An MQTT Retain Cache processes all retained messages for a Message VPN.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: cacheName|x|x|||| msgVpnName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnMqttRetainCacheTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        MsgVpnMqttRetainCache body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnMqttRetainCacheResponse response = api.updateMsgVpnMqttRetainCache(msgVpnName, cacheName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update an MQTT Session object.
     *
     * Update an MQTT Session object. Any attribute missing from the request will be left unchanged.  An MQTT Session object is a virtual representation of an MQTT client connection. An MQTT session holds the state of an MQTT client (that is, it is used to contain a client&#39;s QoS 0 and QoS 1 subscription sets and any undelivered QoS 1 messages).   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: mqttSessionClientId|x|x|||| mqttSessionVirtualRouter|x|x|||| msgVpnName|x|x|||| owner||||x||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnMqttSessionTest() throws Exception {
        String msgVpnName = null;
        String mqttSessionClientId = null;
        String mqttSessionVirtualRouter = null;
        MsgVpnMqttSession body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnMqttSessionResponse response = api.updateMsgVpnMqttSession(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a Subscription object.
     *
     * Update a Subscription object. Any attribute missing from the request will be left unchanged.  An MQTT session contains a client&#39;s QoS 0 and QoS 1 subscription sets. On creation, a subscription defaults to QoS 0.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: mqttSessionClientId|x|x|||| mqttSessionVirtualRouter|x|x|||| msgVpnName|x|x|||| subscriptionTopic|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnMqttSessionSubscriptionTest() throws Exception {
        String msgVpnName = null;
        String mqttSessionClientId = null;
        String mqttSessionVirtualRouter = null;
        String subscriptionTopic = null;
        MsgVpnMqttSessionSubscription body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnMqttSessionSubscriptionResponse response = api.updateMsgVpnMqttSessionSubscription(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, subscriptionTopic, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a Queue object.
     *
     * Update a Queue object. Any attribute missing from the request will be left unchanged.  A Queue acts as both a destination that clients can publish messages to, and as an endpoint that clients can bind consumers to and consume messages from.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: accessType||||x|| msgVpnName|x|x|||| owner||||x|| permission||||x|| queueName|x|x|||| respectMsgPriorityEnabled||||x||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnQueueTest() throws Exception {
        String msgVpnName = null;
        String queueName = null;
        MsgVpnQueue body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnQueueResponse response = api.updateMsgVpnQueue(msgVpnName, queueName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a Queue Template object.
     *
     * Update a Queue Template object. Any attribute missing from the request will be left unchanged.  A Queue Template provides a mechanism for specifying the initial state for client created queues.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x|x|||| queueTemplateName|x|x||||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnQueueTemplateTest() throws Exception {
        String msgVpnName = null;
        String queueTemplateName = null;
        MsgVpnQueueTemplate body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnQueueTemplateResponse response = api.updateMsgVpnQueueTemplate(msgVpnName, queueTemplateName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a Replay Log object.
     *
     * Update a Replay Log object. Any attribute missing from the request will be left unchanged.  When the Message Replay feature is enabled, message brokers store persistent messages in a Replay Log. These messages are kept until the log is full, after which the oldest messages are removed to free up space for new messages.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x|x|||| replayLogName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.10.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnReplayLogTest() throws Exception {
        String msgVpnName = null;
        String replayLogName = null;
        MsgVpnReplayLog body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnReplayLogResponse response = api.updateMsgVpnReplayLog(msgVpnName, replayLogName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a Replicated Topic object.
     *
     * Update a Replicated Topic object. Any attribute missing from the request will be left unchanged.  To indicate which messages should be replicated between the active and standby site, a Replicated Topic subscription must be configured on a Message VPN. If a published message matches both a replicated topic and an endpoint on the active site, then the message is replicated to the standby site.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x|x|||| replicatedTopic|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnReplicatedTopicTest() throws Exception {
        String msgVpnName = null;
        String replicatedTopic = null;
        MsgVpnReplicatedTopic body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnReplicatedTopicResponse response = api.updateMsgVpnReplicatedTopic(msgVpnName, replicatedTopic, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a REST Delivery Point object.
     *
     * Update a REST Delivery Point object. Any attribute missing from the request will be left unchanged.  A REST Delivery Point manages delivery of messages from queues to a named list of REST Consumers.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: clientProfileName||||x|| msgVpnName|x|x|||| restDeliveryPointName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnRestDeliveryPointTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        MsgVpnRestDeliveryPoint body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointResponse response = api.updateMsgVpnRestDeliveryPoint(msgVpnName, restDeliveryPointName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a Queue Binding object.
     *
     * Update a Queue Binding object. Any attribute missing from the request will be left unchanged.  A Queue Binding for a REST Delivery Point attracts messages to be delivered to REST consumers. If the queue does not exist it can be created subsequently, and once the queue is operational the broker performs the queue binding. Removing the queue binding does not delete the queue itself. Similarly, removing the queue does not remove the queue binding, which fails until the queue is recreated or the queue binding is deleted.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x|x|||| queueBindingName|x|x|||| restDeliveryPointName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnRestDeliveryPointQueueBindingTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String queueBindingName = null;
        MsgVpnRestDeliveryPointQueueBinding body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointQueueBindingResponse response = api.updateMsgVpnRestDeliveryPointQueueBinding(msgVpnName, restDeliveryPointName, queueBindingName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a Request Header object.
     *
     * Update a Request Header object. Any attribute missing from the request will be left unchanged.  A request header to be added to the HTTP request.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: headerName|x|x|||| msgVpnName|x|x|||| queueBindingName|x|x|||| restDeliveryPointName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.23.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnRestDeliveryPointQueueBindingRequestHeaderTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String queueBindingName = null;
        String headerName = null;
        MsgVpnRestDeliveryPointQueueBindingRequestHeader body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointQueueBindingRequestHeaderResponse response = api.updateMsgVpnRestDeliveryPointQueueBindingRequestHeader(msgVpnName, restDeliveryPointName, queueBindingName, headerName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a REST Consumer object.
     *
     * Update a REST Consumer object. Any attribute missing from the request will be left unchanged.  REST Consumer objects establish HTTP connectivity to REST consumer applications who wish to receive messages from a broker.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: authenticationClientCertContent|||x|x||x authenticationClientCertPassword|||x|x|| authenticationHttpBasicPassword|||x|x||x authenticationHttpBasicUsername||||x|| authenticationHttpHeaderValue|||x|||x authenticationOauthClientId||||x|| authenticationOauthClientScope||||x|| authenticationOauthClientSecret|||x|x||x authenticationOauthClientTokenEndpoint||||x|| authenticationOauthJwtSecretKey|||x|x||x authenticationOauthJwtTokenEndpoint||||x|| authenticationScheme||||x|| msgVpnName|x|x|||| outgoingConnectionCount||||x|| remoteHost||||x|| remotePort||||x|| restConsumerName|x|x|||| restDeliveryPointName|x|x|||| tlsCipherSuiteList||||x|| tlsEnabled||||x||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- MsgVpnRestDeliveryPointRestConsumer|authenticationClientCertPassword|authenticationClientCertContent| MsgVpnRestDeliveryPointRestConsumer|authenticationHttpBasicPassword|authenticationHttpBasicUsername| MsgVpnRestDeliveryPointRestConsumer|authenticationHttpBasicUsername|authenticationHttpBasicPassword| MsgVpnRestDeliveryPointRestConsumer|remotePort|tlsEnabled| MsgVpnRestDeliveryPointRestConsumer|tlsEnabled|remotePort|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.0.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnRestDeliveryPointRestConsumerTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String restConsumerName = null;
        MsgVpnRestDeliveryPointRestConsumer body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointRestConsumerResponse response = api.updateMsgVpnRestDeliveryPointRestConsumer(msgVpnName, restDeliveryPointName, restConsumerName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a Topic Endpoint object.
     *
     * Update a Topic Endpoint object. Any attribute missing from the request will be left unchanged.  A Topic Endpoint attracts messages published to a topic for which the Topic Endpoint has a matching topic subscription. The topic subscription for the Topic Endpoint is specified in the client request to bind a Flow to that Topic Endpoint. Queues are significantly more flexible than Topic Endpoints and are the recommended approach for most applications. The use of Topic Endpoints should be restricted to JMS applications.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: accessType||||x|| msgVpnName|x|x|||| owner||||x|| permission||||x|| respectMsgPriorityEnabled||||x|| topicEndpointName|x|x||||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.4.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnTopicEndpointTest() throws Exception {
        String msgVpnName = null;
        String topicEndpointName = null;
        MsgVpnTopicEndpoint body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnTopicEndpointResponse response = api.updateMsgVpnTopicEndpoint(msgVpnName, topicEndpointName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a Topic Endpoint Template object.
     *
     * Update a Topic Endpoint Template object. Any attribute missing from the request will be left unchanged.  A Topic Endpoint Template provides a mechanism for specifying the initial state for client created topic endpoints.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: msgVpnName|x|x|||| topicEndpointTemplateName|x|x||||    The following attributes in the request may only be provided in certain combinations with other attributes:   Class|Attribute|Requires|Conflicts :---|:---|:---|:--- EventThreshold|clearPercent|setPercent|clearValue, setValue EventThreshold|clearValue|setValue|clearPercent, setPercent EventThreshold|setPercent|clearPercent|clearValue, setValue EventThreshold|setValue|clearValue|clearPercent, setPercent    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnTopicEndpointTemplateTest() throws Exception {
        String msgVpnName = null;
        String topicEndpointTemplateName = null;
        MsgVpnTopicEndpointTemplate body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnTopicEndpointTemplateResponse response = api.updateMsgVpnTopicEndpointTemplate(msgVpnName, topicEndpointTemplateName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update a Virtual Hostname object.
     *
     * Update a Virtual Hostname object. Any attribute missing from the request will be left unchanged.  A Virtual Hostname is a provisioned object on a message broker that contains a Virtual Hostname to Message VPN mapping.  Clients which connect to a global (as opposed to per Message VPN) port and provides this hostname will be directed to its corresponding Message VPN. A case-insentive match is performed on the full client-provided hostname against the configured virtual-hostname.  This mechanism is only supported for hostnames provided through the Server Name Indication (SNI) extension of TLS.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: virtualHostname|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-write\&quot; is required to perform this operation.  This has been available since 2.17.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateVirtualHostnameTest() throws Exception {
        String virtualHostname = null;
        VirtualHostname body = null;
        String opaquePassword = null;
        List<String> select = null;
        VirtualHostnameResponse response = api.updateVirtualHostname(virtualHostname, body, opaquePassword, select);

        // TODO: test validations
    }
    
}
