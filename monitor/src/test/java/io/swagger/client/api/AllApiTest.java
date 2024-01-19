/*
 * SEMP (Solace Element Management Protocol)
 * SEMP (starting in `v2`, see note 1) is a RESTful API for configuring, monitoring, and administering a Solace PubSub+ broker.  SEMP uses URIs to address manageable **resources** of the Solace PubSub+ broker. Resources are individual **objects**, **collections** of objects, or (exclusively in the action API) **actions**. This document applies to the following API:   API|Base Path|Purpose|Comments :---|:---|:---|:--- Monitoring|/SEMP/v2/monitor|Querying operational parameters|See note 2    The following APIs are also available:   API|Base Path|Purpose|Comments :---|:---|:---|:--- Action|/SEMP/v2/action|Performing actions|See note 2 Configuration|/SEMP/v2/config|Reading and writing config state|See note 2    Resources are always nouns, with individual objects being singular and collections being plural.  Objects within a collection are identified by an `obj-id`, which follows the collection name with the form `collection-name/obj-id`.  Actions within an object are identified by an `action-id`, which follows the object name with the form `obj-id/action-id`.  Some examples:  ``` /SEMP/v2/config/msgVpns                        ; MsgVpn collection /SEMP/v2/config/msgVpns/a                      ; MsgVpn object named \"a\" /SEMP/v2/config/msgVpns/a/queues               ; Queue collection in MsgVpn \"a\" /SEMP/v2/config/msgVpns/a/queues/b             ; Queue object named \"b\" in MsgVpn \"a\" /SEMP/v2/action/msgVpns/a/queues/b/startReplay ; Action that starts a replay on Queue \"b\" in MsgVpn \"a\" /SEMP/v2/monitor/msgVpns/a/clients             ; Client collection in MsgVpn \"a\" /SEMP/v2/monitor/msgVpns/a/clients/c           ; Client object named \"c\" in MsgVpn \"a\" ```  ## Collection Resources  Collections are unordered lists of objects (unless described as otherwise), and are described by JSON arrays. Each item in the array represents an object in the same manner as the individual object would normally be represented. In the configuration API, the creation of a new object is done through its collection resource.  ## Object and Action Resources  Objects are composed of attributes, actions, collections, and other objects. They are described by JSON objects as name/value pairs. The collections and actions of an object are not contained directly in the object's JSON content; rather the content includes an attribute containing a URI which points to the collections and actions. These contained resources must be managed through this URI. At a minimum, every object has one or more identifying attributes, and its own `uri` attribute which contains the URI pointing to itself.  Actions are also composed of attributes, and are described by JSON objects as name/value pairs. Unlike objects, however, they are not members of a collection and cannot be retrieved, only performed. Actions only exist in the action API.  Attributes in an object or action may have any combination of the following properties:   Property|Meaning|Comments :---|:---|:--- Identifying|Attribute is involved in unique identification of the object, and appears in its URI| Required|Attribute must be provided in the request| Read-Only|Attribute can only be read, not written.|See note 3 Write-Only|Attribute can only be written, not read, unless the attribute is also opaque|See the documentation for the opaque property Requires-Disable|Attribute can only be changed when object is disabled| Deprecated|Attribute is deprecated, and will disappear in the next SEMP version| Opaque|Attribute can be set or retrieved in opaque form when the `opaquePassword` query parameter is present|See the `opaquePassword` query parameter documentation    In some requests, certain attributes may only be provided in certain combinations with other attributes:   Relationship|Meaning :---|:--- Requires|Attribute may only be changed by a request if a particular attribute or combination of attributes is also provided in the request Conflicts|Attribute may only be provided in a request if a particular attribute or combination of attributes is not also provided in the request    In the monitoring API, any non-identifying attribute may not be returned in a GET.  ## HTTP Methods  The following HTTP methods manipulate resources in accordance with these general principles. Note that some methods are only used in certain APIs:   Method|Resource|Meaning|Request Body|Response Body|Missing Request Attributes :---|:---|:---|:---|:---|:--- POST|Collection|Create object|Initial attribute values|Object attributes and metadata|Set to default PUT|Object|Create or replace object (see note 5)|New attribute values|Object attributes and metadata|Set to default, with certain exceptions (see note 4) PUT|Action|Performs action|Action arguments|Action metadata|N/A PATCH|Object|Update object|New attribute values|Object attributes and metadata|unchanged DELETE|Object|Delete object|Empty|Object metadata|N/A GET|Object|Get object|Empty|Object attributes and metadata|N/A GET|Collection|Get collection|Empty|Object attributes and collection metadata|N/A    ## Common Query Parameters  The following are some common query parameters that are supported by many method/URI combinations. Individual URIs may document additional parameters. Note that multiple query parameters can be used together in a single URI, separated by the ampersand character. For example:  ``` ; Request for the MsgVpns collection using two hypothetical query parameters ; \"q1\" and \"q2\" with values \"val1\" and \"val2\" respectively /SEMP/v2/monitor/msgVpns?q1=val1&q2=val2 ```  ### select  Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. Use this query parameter to limit the size of the returned data for each returned object, return only those fields that are desired, or exclude fields that are not desired.  The value of `select` is a comma-separated list of attribute names. If the list contains attribute names that are not prefaced by `-`, only those attributes are included in the response. If the list contains attribute names that are prefaced by `-`, those attributes are excluded from the response. If the list contains both types, then the difference of the first set of attributes and the second set of attributes is returned. If the list is empty (i.e. `select=`), no attributes are returned.  All attributes that are prefaced by `-` must follow all attributes that are not prefaced by `-`. In addition, each attribute name in the list must match at least one attribute in the object.  Names may include the `*` wildcard (zero or more characters). Nested attribute names are supported using periods (e.g. `parentName.childName`).  Some examples:  ``` ; List of all MsgVpn names /SEMP/v2/monitor/msgVpns?select=msgVpnName ; List of all MsgVpn and their attributes except for their names /SEMP/v2/monitor/msgVpns?select=-msgVpnName ; Authentication attributes of MsgVpn \"finance\" /SEMP/v2/monitor/msgVpns/finance?select=authentication* ; All attributes of MsgVpn \"finance\" except for authentication attributes /SEMP/v2/monitor/msgVpns/finance?select=-authentication* ; Access related attributes of Queue \"orderQ\" of MsgVpn \"finance\" /SEMP/v2/monitor/msgVpns/finance/queues/orderQ?select=owner,permission ```  ### where  Include in the response only objects where certain conditions are true. Use this query parameter to limit which objects are returned to those whose attribute values meet the given conditions.  The value of `where` is a comma-separated list of expressions. All expressions must be true for the object to be included in the response. Each expression takes the form:  ``` expression  = attribute-name OP value OP          = '==' | '!=' | '&lt;' | '&gt;' | '&lt;=' | '&gt;=' ```  `value` may be a number, string, `true`, or `false`, as appropriate for the type of `attribute-name`. Greater-than and less-than comparisons only work for numbers. A `*` in a string `value` is interpreted as a wildcard (zero or more characters). Some examples:  ``` ; Only enabled MsgVpns /SEMP/v2/monitor/msgVpns?where=enabled==true ; Only MsgVpns using basic non-LDAP authentication /SEMP/v2/monitor/msgVpns?where=authenticationBasicEnabled==true,authenticationBasicType!=ldap ; Only MsgVpns that allow more than 100 client connections /SEMP/v2/monitor/msgVpns?where=maxConnectionCount>100 ; Only MsgVpns with msgVpnName starting with \"B\": /SEMP/v2/monitor/msgVpns?where=msgVpnName==B* ```  ### count  Limit the count of objects in the response. This can be useful to limit the size of the response for large collections. The minimum value for `count` is `1` and the default is `10`. There is also a per-collection maximum value to limit request handling time.  `count` does not guarantee that a minimum number of objects will be returned. A page may contain fewer than `count` objects or even be empty. Additional objects may nonetheless be available for retrieval on subsequent pages. See the `cursor` query parameter documentation for more information on paging.  For example: ``` ; Up to 25 MsgVpns /SEMP/v2/monitor/msgVpns?count=25 ```  ### cursor  The cursor, or position, for the next page of objects. Cursors are opaque data that should not be created or interpreted by SEMP clients, and should only be used as described below.  When a request is made for a collection and there may be additional objects available for retrieval that are not included in the initial response, the response will include a `cursorQuery` field containing a cursor. The value of this field can be specified in the `cursor` query parameter of a subsequent request to retrieve the next page of objects. For convenience, an appropriate URI is constructed automatically by the broker and included in the `nextPageUri` field of the response. This URI can be used directly to retrieve the next page of objects.  Applications must continue to follow the `nextPageUri` if one is provided in order to retrieve the full set of objects associated with the request, even if a page contains fewer than the requested number of objects (see the `count` query parameter documentation) or is empty.  ### opaquePassword  Attributes with the opaque property are also write-only and so cannot normally be retrieved in a GET. However, when a password is provided in the `opaquePassword` query parameter, attributes with the opaque property are retrieved in a GET in opaque form, encrypted with this password. The query parameter can also be used on a POST, PATCH, or PUT to set opaque attributes using opaque attribute values retrieved in a GET, so long as:  1. the same password that was used to retrieve the opaque attribute values is provided; and  2. the broker to which the request is being sent has the same major and minor SEMP version as the broker that produced the opaque attribute values.  The password provided in the query parameter must be a minimum of 8 characters and a maximum of 128 characters.  The query parameter can only be used in the configuration API, and only over HTTPS.  ## Authentication  When a client makes its first SEMPv2 request, it must supply a username and password using HTTP Basic authentication.  If authentication is successful, the broker returns a cookie containing a session key. The client can omit the username and password from subsequent requests, because the broker now uses the session cookie for authentication instead. When the session expires or is deleted, the client must provide the username and password again, and the broker creates a new session.  There are a limited number of session slots available on the broker. The broker returns 529 No SEMP Session Available if it is not able to allocate a session. For this reason, all clients that use SEMPv2 should support cookies.  If certain attributes—such as a user's password—are changed, the broker automatically deletes the affected sessions. These attributes are documented below. However, changes in external user configuration data stored on a RADIUS or LDAP server do not trigger the broker to delete the associated session(s), therefore you must do this manually, if required.  A client can retrieve its current session information using the /about/user endpoint, delete its own session using the /about/user/logout endpoint, and manage all sessions using the /sessions endpoint.  ## Help  Visit [our website](https://solace.com) to learn more about Solace.  You can also download the SEMP API specifications by clicking [here](https://solace.com/downloads/).  If you need additional support, please contact us at [support@solace.com](mailto:support@solace.com).  ## Notes  Note|Description :---:|:--- 1|This specification defines SEMP starting in \"v2\", and not the original SEMP \"v1\" interface. Request and response formats between \"v1\" and \"v2\" are entirely incompatible, although both protocols share a common port configuration on the Solace PubSub+ broker. They are differentiated by the initial portion of the URI path, one of either \"/SEMP/\" or \"/SEMP/v2/\" 2|This API is partially implemented. Only a subset of all objects are available. 3|Read-only attributes may appear in POST and PUT/PATCH requests. However, if a read-only attribute is not marked as identifying, it will be ignored during a PUT/PATCH. 4|On a PUT, if the SEMP user is not authorized to modify the attribute, its value is left unchanged rather than set to default. In addition, the values of write-only attributes are not set to their defaults on a PUT, except in the following two cases: there is a mutual requires relationship with another non-write-only attribute, both attributes are absent from the request, and the non-write-only attribute is not currently set to its default value; or the attribute is also opaque and the `opaquePassword` query parameter is provided in the request. 5|On a PUT, if the object does not exist, it is created first.  
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
import io.swagger.client.model.BrokerResponse;
import io.swagger.client.model.CertAuthoritiesResponse;
import io.swagger.client.model.CertAuthorityOcspTlsTrustedCommonNameResponse;
import io.swagger.client.model.CertAuthorityOcspTlsTrustedCommonNamesResponse;
import io.swagger.client.model.CertAuthorityResponse;
import io.swagger.client.model.ClientCertAuthoritiesResponse;
import io.swagger.client.model.ClientCertAuthorityOcspTlsTrustedCommonNameResponse;
import io.swagger.client.model.ClientCertAuthorityOcspTlsTrustedCommonNamesResponse;
import io.swagger.client.model.ClientCertAuthorityResponse;
import io.swagger.client.model.ConfigSyncLocalDatabaseRowResponse;
import io.swagger.client.model.ConfigSyncLocalDatabaseRowsResponse;
import io.swagger.client.model.DmrClusterLinkChannelResponse;
import io.swagger.client.model.DmrClusterLinkChannelsResponse;
import io.swagger.client.model.DmrClusterLinkRemoteAddressResponse;
import io.swagger.client.model.DmrClusterLinkRemoteAddressesResponse;
import io.swagger.client.model.DmrClusterLinkResponse;
import io.swagger.client.model.DmrClusterLinkTlsTrustedCommonNameResponse;
import io.swagger.client.model.DmrClusterLinkTlsTrustedCommonNamesResponse;
import io.swagger.client.model.DmrClusterLinksResponse;
import io.swagger.client.model.DmrClusterResponse;
import io.swagger.client.model.DmrClusterTopologyIssueResponse;
import io.swagger.client.model.DmrClusterTopologyIssuesResponse;
import io.swagger.client.model.DmrClustersResponse;
import io.swagger.client.model.DomainCertAuthoritiesResponse;
import io.swagger.client.model.DomainCertAuthorityResponse;
import io.swagger.client.model.GuaranteedMsgingExternalDiskArrayResponse;
import io.swagger.client.model.GuaranteedMsgingExternalDiskArraysResponse;
import io.swagger.client.model.MsgVpnAclProfileClientConnectExceptionResponse;
import io.swagger.client.model.MsgVpnAclProfileClientConnectExceptionsResponse;
import io.swagger.client.model.MsgVpnAclProfilePublishExceptionResponse;
import io.swagger.client.model.MsgVpnAclProfilePublishExceptionsResponse;
import io.swagger.client.model.MsgVpnAclProfilePublishTopicExceptionResponse;
import io.swagger.client.model.MsgVpnAclProfilePublishTopicExceptionsResponse;
import io.swagger.client.model.MsgVpnAclProfileResponse;
import io.swagger.client.model.MsgVpnAclProfileSubscribeExceptionResponse;
import io.swagger.client.model.MsgVpnAclProfileSubscribeExceptionsResponse;
import io.swagger.client.model.MsgVpnAclProfileSubscribeShareNameExceptionResponse;
import io.swagger.client.model.MsgVpnAclProfileSubscribeShareNameExceptionsResponse;
import io.swagger.client.model.MsgVpnAclProfileSubscribeTopicExceptionResponse;
import io.swagger.client.model.MsgVpnAclProfileSubscribeTopicExceptionsResponse;
import io.swagger.client.model.MsgVpnAclProfilesResponse;
import io.swagger.client.model.MsgVpnAuthenticationOauthProviderResponse;
import io.swagger.client.model.MsgVpnAuthenticationOauthProvidersResponse;
import io.swagger.client.model.MsgVpnAuthorizationGroupResponse;
import io.swagger.client.model.MsgVpnAuthorizationGroupsResponse;
import io.swagger.client.model.MsgVpnBridgeLocalSubscriptionResponse;
import io.swagger.client.model.MsgVpnBridgeLocalSubscriptionsResponse;
import io.swagger.client.model.MsgVpnBridgeRemoteMsgVpnResponse;
import io.swagger.client.model.MsgVpnBridgeRemoteMsgVpnsResponse;
import io.swagger.client.model.MsgVpnBridgeRemoteSubscriptionResponse;
import io.swagger.client.model.MsgVpnBridgeRemoteSubscriptionsResponse;
import io.swagger.client.model.MsgVpnBridgeResponse;
import io.swagger.client.model.MsgVpnBridgeTlsTrustedCommonNameResponse;
import io.swagger.client.model.MsgVpnBridgeTlsTrustedCommonNamesResponse;
import io.swagger.client.model.MsgVpnBridgesResponse;
import io.swagger.client.model.MsgVpnClientConnectionResponse;
import io.swagger.client.model.MsgVpnClientConnectionsResponse;
import io.swagger.client.model.MsgVpnClientProfileResponse;
import io.swagger.client.model.MsgVpnClientProfilesResponse;
import io.swagger.client.model.MsgVpnClientResponse;
import io.swagger.client.model.MsgVpnClientRxFlowResponse;
import io.swagger.client.model.MsgVpnClientRxFlowsResponse;
import io.swagger.client.model.MsgVpnClientSubscriptionResponse;
import io.swagger.client.model.MsgVpnClientSubscriptionsResponse;
import io.swagger.client.model.MsgVpnClientTransactedSessionResponse;
import io.swagger.client.model.MsgVpnClientTransactedSessionsResponse;
import io.swagger.client.model.MsgVpnClientTxFlowResponse;
import io.swagger.client.model.MsgVpnClientTxFlowsResponse;
import io.swagger.client.model.MsgVpnClientUsernameResponse;
import io.swagger.client.model.MsgVpnClientUsernamesResponse;
import io.swagger.client.model.MsgVpnClientsResponse;
import io.swagger.client.model.MsgVpnConfigSyncRemoteNodeResponse;
import io.swagger.client.model.MsgVpnConfigSyncRemoteNodesResponse;
import io.swagger.client.model.MsgVpnDistributedCacheClusterGlobalCachingHomeClusterResponse;
import io.swagger.client.model.MsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixResponse;
import io.swagger.client.model.MsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixesResponse;
import io.swagger.client.model.MsgVpnDistributedCacheClusterGlobalCachingHomeClustersResponse;
import io.swagger.client.model.MsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeClusterResponse;
import io.swagger.client.model.MsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeClustersResponse;
import io.swagger.client.model.MsgVpnDistributedCacheClusterInstanceRemoteTopicResponse;
import io.swagger.client.model.MsgVpnDistributedCacheClusterInstanceRemoteTopicsResponse;
import io.swagger.client.model.MsgVpnDistributedCacheClusterInstanceResponse;
import io.swagger.client.model.MsgVpnDistributedCacheClusterInstancesResponse;
import io.swagger.client.model.MsgVpnDistributedCacheClusterResponse;
import io.swagger.client.model.MsgVpnDistributedCacheClusterTopicResponse;
import io.swagger.client.model.MsgVpnDistributedCacheClusterTopicsResponse;
import io.swagger.client.model.MsgVpnDistributedCacheClustersResponse;
import io.swagger.client.model.MsgVpnDistributedCacheResponse;
import io.swagger.client.model.MsgVpnDistributedCachesResponse;
import io.swagger.client.model.MsgVpnDmrBridgeResponse;
import io.swagger.client.model.MsgVpnDmrBridgesResponse;
import io.swagger.client.model.MsgVpnJndiConnectionFactoriesResponse;
import io.swagger.client.model.MsgVpnJndiConnectionFactoryResponse;
import io.swagger.client.model.MsgVpnJndiQueueResponse;
import io.swagger.client.model.MsgVpnJndiQueuesResponse;
import io.swagger.client.model.MsgVpnJndiTopicResponse;
import io.swagger.client.model.MsgVpnJndiTopicsResponse;
import io.swagger.client.model.MsgVpnMqttRetainCacheResponse;
import io.swagger.client.model.MsgVpnMqttRetainCachesResponse;
import io.swagger.client.model.MsgVpnMqttSessionResponse;
import io.swagger.client.model.MsgVpnMqttSessionSubscriptionResponse;
import io.swagger.client.model.MsgVpnMqttSessionSubscriptionsResponse;
import io.swagger.client.model.MsgVpnMqttSessionsResponse;
import io.swagger.client.model.MsgVpnQueueMsgResponse;
import io.swagger.client.model.MsgVpnQueueMsgsResponse;
import io.swagger.client.model.MsgVpnQueuePrioritiesResponse;
import io.swagger.client.model.MsgVpnQueuePriorityResponse;
import io.swagger.client.model.MsgVpnQueueResponse;
import io.swagger.client.model.MsgVpnQueueSubscriptionResponse;
import io.swagger.client.model.MsgVpnQueueSubscriptionsResponse;
import io.swagger.client.model.MsgVpnQueueTemplateResponse;
import io.swagger.client.model.MsgVpnQueueTemplatesResponse;
import io.swagger.client.model.MsgVpnQueueTxFlowResponse;
import io.swagger.client.model.MsgVpnQueueTxFlowsResponse;
import io.swagger.client.model.MsgVpnQueuesResponse;
import io.swagger.client.model.MsgVpnReplayLogMsgResponse;
import io.swagger.client.model.MsgVpnReplayLogMsgsResponse;
import io.swagger.client.model.MsgVpnReplayLogResponse;
import io.swagger.client.model.MsgVpnReplayLogsResponse;
import io.swagger.client.model.MsgVpnReplicatedTopicResponse;
import io.swagger.client.model.MsgVpnReplicatedTopicsResponse;
import io.swagger.client.model.MsgVpnResponse;
import io.swagger.client.model.MsgVpnRestDeliveryPointQueueBindingRequestHeaderResponse;
import io.swagger.client.model.MsgVpnRestDeliveryPointQueueBindingRequestHeadersResponse;
import io.swagger.client.model.MsgVpnRestDeliveryPointQueueBindingResponse;
import io.swagger.client.model.MsgVpnRestDeliveryPointQueueBindingsResponse;
import io.swagger.client.model.MsgVpnRestDeliveryPointResponse;
import io.swagger.client.model.MsgVpnRestDeliveryPointRestConsumerOauthJwtClaimResponse;
import io.swagger.client.model.MsgVpnRestDeliveryPointRestConsumerOauthJwtClaimsResponse;
import io.swagger.client.model.MsgVpnRestDeliveryPointRestConsumerResponse;
import io.swagger.client.model.MsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNameResponse;
import io.swagger.client.model.MsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNamesResponse;
import io.swagger.client.model.MsgVpnRestDeliveryPointRestConsumersResponse;
import io.swagger.client.model.MsgVpnRestDeliveryPointsResponse;
import io.swagger.client.model.MsgVpnTopicEndpointMsgResponse;
import io.swagger.client.model.MsgVpnTopicEndpointMsgsResponse;
import io.swagger.client.model.MsgVpnTopicEndpointPrioritiesResponse;
import io.swagger.client.model.MsgVpnTopicEndpointPriorityResponse;
import io.swagger.client.model.MsgVpnTopicEndpointResponse;
import io.swagger.client.model.MsgVpnTopicEndpointTemplateResponse;
import io.swagger.client.model.MsgVpnTopicEndpointTemplatesResponse;
import io.swagger.client.model.MsgVpnTopicEndpointTxFlowResponse;
import io.swagger.client.model.MsgVpnTopicEndpointTxFlowsResponse;
import io.swagger.client.model.MsgVpnTopicEndpointsResponse;
import io.swagger.client.model.MsgVpnTransactionConsumerMsgResponse;
import io.swagger.client.model.MsgVpnTransactionConsumerMsgsResponse;
import io.swagger.client.model.MsgVpnTransactionPublisherMsgResponse;
import io.swagger.client.model.MsgVpnTransactionPublisherMsgsResponse;
import io.swagger.client.model.MsgVpnTransactionResponse;
import io.swagger.client.model.MsgVpnTransactionsResponse;
import io.swagger.client.model.MsgVpnsResponse;
import io.swagger.client.model.SempMetaOnlyResponse;
import io.swagger.client.model.SessionResponse;
import io.swagger.client.model.SessionsResponse;
import io.swagger.client.model.StandardDomainCertAuthoritiesResponse;
import io.swagger.client.model.StandardDomainCertAuthorityResponse;
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
     * Get an About object.
     *
     * Get an About object.  This provides metadata about the SEMP API, such as the version of the API supported by the broker.    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation.  This has been available since 2.13.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getAboutTest() throws Exception {
        List<String> select = null;
        AboutResponse response = api.getAbout(select);

        // TODO: test validations
    }
    
    /**
     * Get an API Description object.
     *
     * Get an API Description object.  The API Description object provides metadata about the SEMP API.    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getAboutApiTest() throws Exception {
        List<String> select = null;
        AboutApiResponse response = api.getAboutApi(select);

        // TODO: test validations
    }
    
    /**
     * Get a User object.
     *
     * Get a User object.  Session and access level information about the user accessing the SEMP API.    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getAboutUserTest() throws Exception {
        List<String> select = null;
        AboutUserResponse response = api.getAboutUser(select);

        // TODO: test validations
    }
    
    /**
     * Get a User Message VPN object.
     *
     * Get a User Message VPN object.  This provides information about the Message VPN access level for the username used to access the SEMP API.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getAboutUserMsgVpnTest() throws Exception {
        String msgVpnName = null;
        List<String> select = null;
        AboutUserMsgVpnResponse response = api.getAboutUserMsgVpn(msgVpnName, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of User Message VPN objects.
     *
     * Get a list of User Message VPN objects.  This provides information about the Message VPN access level for the username used to access the SEMP API.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getAboutUserMsgVpnsTest() throws Exception {
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        AboutUserMsgVpnsResponse response = api.getAboutUserMsgVpns(count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Broker object.
     *
     * Get a Broker object.  This object contains global configuration for the message broker.    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation. Requests which include the following attributes require greater access scope/level:   Attribute|Access Scope/Level :---|:---: averageRxByteRate|global/read-only averageRxCompressedByteRate|global/read-only averageRxMsgRate|global/read-only averageRxUncompressedByteRate|global/read-only averageTxByteRate|global/read-only averageTxCompressedByteRate|global/read-only averageTxMsgRate|global/read-only averageTxUncompressedByteRate|global/read-only configSyncAuthenticationClientCertMaxChainDepth|global/read-only configSyncAuthenticationClientCertValidateDateEnabled|global/read-only configSyncClientProfileTcpInitialCongestionWindow|global/read-only configSyncClientProfileTcpKeepaliveCount|global/read-only configSyncClientProfileTcpKeepaliveIdle|global/read-only configSyncClientProfileTcpKeepaliveInterval|global/read-only configSyncClientProfileTcpMaxWindow|global/read-only configSyncClientProfileTcpMss|global/read-only configSyncEnabled|global/read-only configSyncLastFailureReason|global/read-only configSyncSynchronizeUsernameEnabled|global/read-only configSyncTlsEnabled|global/read-only configSyncUp|global/read-only cspfVersion|global/read-only guaranteedMsgingDefragmentationEstimatedFragmentation|global/read-only guaranteedMsgingDefragmentationEstimatedRecoverableSpace|global/read-only guaranteedMsgingDefragmentationLastCompletedOn|global/read-only guaranteedMsgingDefragmentationLastCompletionPercentage|global/read-only guaranteedMsgingDefragmentationLastExitCondition|global/read-only guaranteedMsgingDefragmentationLastExitConditionInformation|global/read-only guaranteedMsgingDefragmentationStatus|global/read-only guaranteedMsgingDefragmentationStatusActiveCompletionPercentage|global/read-only guaranteedMsgingDiskArrayWwn|global/read-only guaranteedMsgingDiskLocation|global/read-only guaranteedMsgingEnabled|global/read-only guaranteedMsgingEventCacheUsageThreshold.clearPercent|global/read-only guaranteedMsgingEventCacheUsageThreshold.clearValue|global/read-only guaranteedMsgingEventCacheUsageThreshold.setPercent|global/read-only guaranteedMsgingEventCacheUsageThreshold.setValue|global/read-only guaranteedMsgingEventDeliveredUnackedThreshold.clearPercent|global/read-only guaranteedMsgingEventDeliveredUnackedThreshold.setPercent|global/read-only guaranteedMsgingEventDiskUsageThreshold.clearPercent|global/read-only guaranteedMsgingEventDiskUsageThreshold.setPercent|global/read-only guaranteedMsgingEventEgressFlowCountThreshold.clearPercent|global/read-only guaranteedMsgingEventEgressFlowCountThreshold.clearValue|global/read-only guaranteedMsgingEventEgressFlowCountThreshold.setPercent|global/read-only guaranteedMsgingEventEgressFlowCountThreshold.setValue|global/read-only guaranteedMsgingEventEndpointCountThreshold.clearPercent|global/read-only guaranteedMsgingEventEndpointCountThreshold.clearValue|global/read-only guaranteedMsgingEventEndpointCountThreshold.setPercent|global/read-only guaranteedMsgingEventEndpointCountThreshold.setValue|global/read-only guaranteedMsgingEventIngressFlowCountThreshold.clearPercent|global/read-only guaranteedMsgingEventIngressFlowCountThreshold.clearValue|global/read-only guaranteedMsgingEventIngressFlowCountThreshold.setPercent|global/read-only guaranteedMsgingEventIngressFlowCountThreshold.setValue|global/read-only guaranteedMsgingEventMsgCountThreshold.clearPercent|global/read-only guaranteedMsgingEventMsgCountThreshold.setPercent|global/read-only guaranteedMsgingEventMsgSpoolFileCountThreshold.clearPercent|global/read-only guaranteedMsgingEventMsgSpoolFileCountThreshold.setPercent|global/read-only guaranteedMsgingEventMsgSpoolUsageThreshold.clearPercent|global/read-only guaranteedMsgingEventMsgSpoolUsageThreshold.clearValue|global/read-only guaranteedMsgingEventMsgSpoolUsageThreshold.setPercent|global/read-only guaranteedMsgingEventMsgSpoolUsageThreshold.setValue|global/read-only guaranteedMsgingEventTransactedSessionCountThreshold.clearPercent|global/read-only guaranteedMsgingEventTransactedSessionCountThreshold.clearValue|global/read-only guaranteedMsgingEventTransactedSessionCountThreshold.setPercent|global/read-only guaranteedMsgingEventTransactedSessionCountThreshold.setValue|global/read-only guaranteedMsgingEventTransactedSessionResourceCountThreshold.clearPercent|global/read-only guaranteedMsgingEventTransactedSessionResourceCountThreshold.setPercent|global/read-only guaranteedMsgingEventTransactionCountThreshold.clearPercent|global/read-only guaranteedMsgingEventTransactionCountThreshold.clearValue|global/read-only guaranteedMsgingEventTransactionCountThreshold.setPercent|global/read-only guaranteedMsgingEventTransactionCountThreshold.setValue|global/read-only guaranteedMsgingMaxCacheUsage|global/read-only guaranteedMsgingMaxMsgSpoolUsage|global/read-only guaranteedMsgingOperationalStatus|global/read-only guaranteedMsgingTransactionReplicationCompatibilityMode|global/read-only guaranteedMsgingVirtualRouterWhenActiveActive|global/read-only rxByteCount|global/read-only rxByteRate|global/read-only rxCompressedByteCount|global/read-only rxCompressedByteRate|global/read-only rxCompressionRatio|global/read-only rxMsgCount|global/read-only rxMsgRate|global/read-only rxUncompressedByteCount|global/read-only rxUncompressedByteRate|global/read-only serviceAmqpEnabled|global/read-only serviceAmqpTlsListenPort|global/read-only serviceEventConnectionCountThreshold.clearPercent|global/read-only serviceEventConnectionCountThreshold.clearValue|global/read-only serviceEventConnectionCountThreshold.setPercent|global/read-only serviceEventConnectionCountThreshold.setValue|global/read-only serviceHealthCheckEnabled|global/read-only serviceHealthCheckListenPort|global/read-only serviceMqttEnabled|global/read-only serviceMsgBackboneEnabled|global/read-only serviceRestEventOutgoingConnectionCountThreshold.clearPercent|global/read-only serviceRestEventOutgoingConnectionCountThreshold.clearValue|global/read-only serviceRestEventOutgoingConnectionCountThreshold.setPercent|global/read-only serviceRestEventOutgoingConnectionCountThreshold.setValue|global/read-only serviceRestIncomingEnabled|global/read-only serviceRestOutgoingEnabled|global/read-only serviceSempLegacyTimeoutEnabled|global/read-only serviceSempPlainTextEnabled|global/read-only serviceSempPlainTextListenPort|global/read-only serviceSempSessionIdleTimeout|global/read-only serviceSempSessionMaxLifetime|global/read-only serviceSempTlsEnabled|global/read-only serviceSempTlsListenPort|global/read-only serviceSmfCompressionListenPort|global/read-only serviceSmfEnabled|global/read-only serviceSmfEventConnectionCountThreshold.clearPercent|global/read-only serviceSmfEventConnectionCountThreshold.clearValue|global/read-only serviceSmfEventConnectionCountThreshold.setPercent|global/read-only serviceSmfEventConnectionCountThreshold.setValue|global/read-only serviceSmfPlainTextListenPort|global/read-only serviceSmfRoutingControlListenPort|global/read-only serviceSmfTlsListenPort|global/read-only serviceTlsEventConnectionCountThreshold.clearPercent|global/read-only serviceTlsEventConnectionCountThreshold.clearValue|global/read-only serviceTlsEventConnectionCountThreshold.setPercent|global/read-only serviceTlsEventConnectionCountThreshold.setValue|global/read-only serviceWebTransportEnabled|global/read-only serviceWebTransportPlainTextListenPort|global/read-only serviceWebTransportTlsListenPort|global/read-only serviceWebTransportWebUrlSuffix|global/read-only tlsBlockVersion10Enabled|global/read-only tlsBlockVersion11Enabled|global/read-only tlsCipherSuiteManagementDefaultList|global/read-only tlsCipherSuiteManagementList|global/read-only tlsCipherSuiteManagementSupportedList|vpn/read-only tlsCipherSuiteMsgBackboneDefaultList|global/read-only tlsCipherSuiteMsgBackboneList|global/read-only tlsCipherSuiteMsgBackboneSupportedList|vpn/read-only tlsCipherSuiteSecureShellDefaultList|global/read-only tlsCipherSuiteSecureShellList|global/read-only tlsCipherSuiteSecureShellSupportedList|vpn/read-only tlsCrimeExploitProtectionEnabled|global/read-only tlsStandardDomainCertificateAuthoritiesEnabled|vpn/read-only tlsTicketLifetime|global/read-only tlsVersionSupportedList|vpn/read-only txByteCount|global/read-only txByteRate|global/read-only txCompressedByteCount|global/read-only txCompressedByteRate|global/read-only txCompressionRatio|global/read-only txMsgCount|global/read-only txMsgRate|global/read-only txUncompressedByteCount|global/read-only txUncompressedByteRate|global/read-only    This has been available since 2.13.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getBrokerTest() throws Exception {
        List<String> select = null;
        BrokerResponse response = api.getBroker(select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Certificate Authority objects.
     *
     * Get a list of Certificate Authority objects.  Clients can authenticate with the message broker over TLS by presenting a valid client certificate. The message broker authenticates the client certificate by constructing a full certificate chain (from the client certificate to intermediate CAs to a configured root CA). The intermediate CAs in this chain can be provided by the client, or configured in the message broker. The root CA must be configured on the message broker.   Attribute|Identifying|Deprecated :---|:---:|:---: certAuthorityName|x|x certContent||x crlDayList||x crlLastDownloadTime||x crlLastFailureReason||x crlLastFailureTime||x crlNextDownloadTime||x crlTimeList||x crlUp||x crlUrl||x ocspLastFailureReason||x ocspLastFailureTime||x ocspLastFailureUrl||x ocspNonResponderCertEnabled||x ocspOverrideUrl||x ocspTimeout||x revocationCheckEnabled||x    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.19. Replaced by clientCertAuthorities and domainCertAuthorities.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getCertAuthoritiesTest() throws Exception {
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        CertAuthoritiesResponse response = api.getCertAuthorities(count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Certificate Authority object.
     *
     * Get a Certificate Authority object.  Clients can authenticate with the message broker over TLS by presenting a valid client certificate. The message broker authenticates the client certificate by constructing a full certificate chain (from the client certificate to intermediate CAs to a configured root CA). The intermediate CAs in this chain can be provided by the client, or configured in the message broker. The root CA must be configured on the message broker.   Attribute|Identifying|Deprecated :---|:---:|:---: certAuthorityName|x|x certContent||x crlDayList||x crlLastDownloadTime||x crlLastFailureReason||x crlLastFailureTime||x crlNextDownloadTime||x crlTimeList||x crlUp||x crlUrl||x ocspLastFailureReason||x ocspLastFailureTime||x ocspLastFailureUrl||x ocspNonResponderCertEnabled||x ocspOverrideUrl||x ocspTimeout||x revocationCheckEnabled||x    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.19. Replaced by clientCertAuthorities and domainCertAuthorities.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getCertAuthorityTest() throws Exception {
        String certAuthorityName = null;
        List<String> select = null;
        CertAuthorityResponse response = api.getCertAuthority(certAuthorityName, select);

        // TODO: test validations
    }
    
    /**
     * Get an OCSP Responder Trusted Common Name object.
     *
     * Get an OCSP Responder Trusted Common Name object.  When an OCSP override URL is configured, the OCSP responder will be required to sign the OCSP responses with certificates issued to these Trusted Common Names. A maximum of 8 common names can be configured as valid response signers.   Attribute|Identifying|Deprecated :---|:---:|:---: certAuthorityName|x|x ocspTlsTrustedCommonName|x|x    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.19. Replaced by clientCertAuthorities.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getCertAuthorityOcspTlsTrustedCommonNameTest() throws Exception {
        String certAuthorityName = null;
        String ocspTlsTrustedCommonName = null;
        List<String> select = null;
        CertAuthorityOcspTlsTrustedCommonNameResponse response = api.getCertAuthorityOcspTlsTrustedCommonName(certAuthorityName, ocspTlsTrustedCommonName, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of OCSP Responder Trusted Common Name objects.
     *
     * Get a list of OCSP Responder Trusted Common Name objects.  When an OCSP override URL is configured, the OCSP responder will be required to sign the OCSP responses with certificates issued to these Trusted Common Names. A maximum of 8 common names can be configured as valid response signers.   Attribute|Identifying|Deprecated :---|:---:|:---: certAuthorityName|x|x ocspTlsTrustedCommonName|x|x    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.19. Replaced by clientCertAuthorities.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getCertAuthorityOcspTlsTrustedCommonNamesTest() throws Exception {
        String certAuthorityName = null;
        List<String> where = null;
        List<String> select = null;
        CertAuthorityOcspTlsTrustedCommonNamesResponse response = api.getCertAuthorityOcspTlsTrustedCommonNames(certAuthorityName, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Client Certificate Authority objects.
     *
     * Get a list of Client Certificate Authority objects.  Clients can authenticate with the message broker over TLS by presenting a valid client certificate. The message broker authenticates the client certificate by constructing a full certificate chain (from the client certificate to intermediate CAs to a configured root CA). The intermediate CAs in this chain can be provided by the client, or configured in the message broker. The root CA must be configured on the message broker.   Attribute|Identifying|Deprecated :---|:---:|:---: certAuthorityName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.19.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getClientCertAuthoritiesTest() throws Exception {
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        ClientCertAuthoritiesResponse response = api.getClientCertAuthorities(count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Client Certificate Authority object.
     *
     * Get a Client Certificate Authority object.  Clients can authenticate with the message broker over TLS by presenting a valid client certificate. The message broker authenticates the client certificate by constructing a full certificate chain (from the client certificate to intermediate CAs to a configured root CA). The intermediate CAs in this chain can be provided by the client, or configured in the message broker. The root CA must be configured on the message broker.   Attribute|Identifying|Deprecated :---|:---:|:---: certAuthorityName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.19.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getClientCertAuthorityTest() throws Exception {
        String certAuthorityName = null;
        List<String> select = null;
        ClientCertAuthorityResponse response = api.getClientCertAuthority(certAuthorityName, select);

        // TODO: test validations
    }
    
    /**
     * Get an OCSP Responder Trusted Common Name object.
     *
     * Get an OCSP Responder Trusted Common Name object.  When an OCSP override URL is configured, the OCSP responder will be required to sign the OCSP responses with certificates issued to these Trusted Common Names. A maximum of 8 common names can be configured as valid response signers.   Attribute|Identifying|Deprecated :---|:---:|:---: certAuthorityName|x| ocspTlsTrustedCommonName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.19.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getClientCertAuthorityOcspTlsTrustedCommonNameTest() throws Exception {
        String certAuthorityName = null;
        String ocspTlsTrustedCommonName = null;
        List<String> select = null;
        ClientCertAuthorityOcspTlsTrustedCommonNameResponse response = api.getClientCertAuthorityOcspTlsTrustedCommonName(certAuthorityName, ocspTlsTrustedCommonName, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of OCSP Responder Trusted Common Name objects.
     *
     * Get a list of OCSP Responder Trusted Common Name objects.  When an OCSP override URL is configured, the OCSP responder will be required to sign the OCSP responses with certificates issued to these Trusted Common Names. A maximum of 8 common names can be configured as valid response signers.   Attribute|Identifying|Deprecated :---|:---:|:---: certAuthorityName|x| ocspTlsTrustedCommonName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.19.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getClientCertAuthorityOcspTlsTrustedCommonNamesTest() throws Exception {
        String certAuthorityName = null;
        List<String> where = null;
        List<String> select = null;
        ClientCertAuthorityOcspTlsTrustedCommonNamesResponse response = api.getClientCertAuthorityOcspTlsTrustedCommonNames(certAuthorityName, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Config Sync Local Database object.
     *
     * Get a Config Sync Local Database object.  Config Sync Local Database Rows contains information about the status of the table for this Broker or a local Message VPN.   Attribute|Identifying|Deprecated :---|:---:|:---: name|x| type|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation.  This has been available since 2.22.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getConfigSyncLocalDatabaseRowTest() throws Exception {
        String type = null;
        String name = null;
        List<String> select = null;
        ConfigSyncLocalDatabaseRowResponse response = api.getConfigSyncLocalDatabaseRow(type, name, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Config Sync Local Database objects.
     *
     * Get a list of Config Sync Local Database objects.  Config Sync Local Database Rows contains information about the status of the table for this Broker or a local Message VPN.   Attribute|Identifying|Deprecated :---|:---:|:---: name|x| type|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/none\&quot; is required to perform this operation.  This has been available since 2.22.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getConfigSyncLocalDatabaseRowsTest() throws Exception {
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        ConfigSyncLocalDatabaseRowsResponse response = api.getConfigSyncLocalDatabaseRows(count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Cluster object.
     *
     * Get a Cluster object.  A Cluster is a provisioned object on a message broker that contains global DMR configuration parameters.   Attribute|Identifying|Deprecated :---|:---:|:---: dmrClusterName|x| tlsServerCertEnforceTrustedCommonNameEnabled||x    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getDmrClusterTest() throws Exception {
        String dmrClusterName = null;
        List<String> select = null;
        DmrClusterResponse response = api.getDmrCluster(dmrClusterName, select);

        // TODO: test validations
    }
    
    /**
     * Get a Link object.
     *
     * Get a Link object.  A Link connects nodes (either within a Cluster or between two different Clusters) and allows them to exchange topology information, subscriptions and data.   Attribute|Identifying|Deprecated :---|:---:|:---: dmrClusterName|x| remoteNodeName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getDmrClusterLinkTest() throws Exception {
        String dmrClusterName = null;
        String remoteNodeName = null;
        List<String> select = null;
        DmrClusterLinkResponse response = api.getDmrClusterLink(dmrClusterName, remoteNodeName, select);

        // TODO: test validations
    }
    
    /**
     * Get a Cluster Link Channels object.
     *
     * Get a Cluster Link Channels object.  A Channel is a connection between this broker and a remote node in the Cluster.   Attribute|Identifying|Deprecated :---|:---:|:---: dmrClusterName|x| msgVpnName|x| remoteNodeName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getDmrClusterLinkChannelTest() throws Exception {
        String dmrClusterName = null;
        String remoteNodeName = null;
        String msgVpnName = null;
        List<String> select = null;
        DmrClusterLinkChannelResponse response = api.getDmrClusterLinkChannel(dmrClusterName, remoteNodeName, msgVpnName, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Cluster Link Channels objects.
     *
     * Get a list of Cluster Link Channels objects.  A Channel is a connection between this broker and a remote node in the Cluster.   Attribute|Identifying|Deprecated :---|:---:|:---: dmrClusterName|x| msgVpnName|x| remoteNodeName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getDmrClusterLinkChannelsTest() throws Exception {
        String dmrClusterName = null;
        String remoteNodeName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        DmrClusterLinkChannelsResponse response = api.getDmrClusterLinkChannels(dmrClusterName, remoteNodeName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Remote Address object.
     *
     * Get a Remote Address object.  Each Remote Address, consisting of a FQDN or IP address and optional port, is used to connect to the remote node for this Link. Up to 4 addresses may be provided for each Link, and will be tried on a round-robin basis.   Attribute|Identifying|Deprecated :---|:---:|:---: dmrClusterName|x| remoteAddress|x| remoteNodeName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getDmrClusterLinkRemoteAddressTest() throws Exception {
        String dmrClusterName = null;
        String remoteNodeName = null;
        String remoteAddress = null;
        List<String> select = null;
        DmrClusterLinkRemoteAddressResponse response = api.getDmrClusterLinkRemoteAddress(dmrClusterName, remoteNodeName, remoteAddress, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Remote Address objects.
     *
     * Get a list of Remote Address objects.  Each Remote Address, consisting of a FQDN or IP address and optional port, is used to connect to the remote node for this Link. Up to 4 addresses may be provided for each Link, and will be tried on a round-robin basis.   Attribute|Identifying|Deprecated :---|:---:|:---: dmrClusterName|x| remoteAddress|x| remoteNodeName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getDmrClusterLinkRemoteAddressesTest() throws Exception {
        String dmrClusterName = null;
        String remoteNodeName = null;
        List<String> where = null;
        List<String> select = null;
        DmrClusterLinkRemoteAddressesResponse response = api.getDmrClusterLinkRemoteAddresses(dmrClusterName, remoteNodeName, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Trusted Common Name object.
     *
     * Get a Trusted Common Name object.  The Trusted Common Names for the Link are used by encrypted transports to verify the name in the certificate presented by the remote node. They must include the common name of the remote node&#39;s server certificate or client certificate, depending upon the initiator of the connection.   Attribute|Identifying|Deprecated :---|:---:|:---: dmrClusterName|x|x remoteNodeName|x|x tlsTrustedCommonName|x|x    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.18. Common Name validation has been replaced by Server Certificate Name validation.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getDmrClusterLinkTlsTrustedCommonNameTest() throws Exception {
        String dmrClusterName = null;
        String remoteNodeName = null;
        String tlsTrustedCommonName = null;
        List<String> select = null;
        DmrClusterLinkTlsTrustedCommonNameResponse response = api.getDmrClusterLinkTlsTrustedCommonName(dmrClusterName, remoteNodeName, tlsTrustedCommonName, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Trusted Common Name objects.
     *
     * Get a list of Trusted Common Name objects.  The Trusted Common Names for the Link are used by encrypted transports to verify the name in the certificate presented by the remote node. They must include the common name of the remote node&#39;s server certificate or client certificate, depending upon the initiator of the connection.   Attribute|Identifying|Deprecated :---|:---:|:---: dmrClusterName|x|x remoteNodeName|x|x tlsTrustedCommonName|x|x    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.18. Common Name validation has been replaced by Server Certificate Name validation.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getDmrClusterLinkTlsTrustedCommonNamesTest() throws Exception {
        String dmrClusterName = null;
        String remoteNodeName = null;
        List<String> where = null;
        List<String> select = null;
        DmrClusterLinkTlsTrustedCommonNamesResponse response = api.getDmrClusterLinkTlsTrustedCommonNames(dmrClusterName, remoteNodeName, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Link objects.
     *
     * Get a list of Link objects.  A Link connects nodes (either within a Cluster or between two different Clusters) and allows them to exchange topology information, subscriptions and data.   Attribute|Identifying|Deprecated :---|:---:|:---: dmrClusterName|x| remoteNodeName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getDmrClusterLinksTest() throws Exception {
        String dmrClusterName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        DmrClusterLinksResponse response = api.getDmrClusterLinks(dmrClusterName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Cluster Topology Issue object.
     *
     * Get a Cluster Topology Issue object.  A Cluster Topology Issue indicates incorrect or inconsistent configuration within the DMR network. Such issues will cause messages to be misdelivered or lost.   Attribute|Identifying|Deprecated :---|:---:|:---: dmrClusterName|x| topologyIssue|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getDmrClusterTopologyIssueTest() throws Exception {
        String dmrClusterName = null;
        String topologyIssue = null;
        List<String> select = null;
        DmrClusterTopologyIssueResponse response = api.getDmrClusterTopologyIssue(dmrClusterName, topologyIssue, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Cluster Topology Issue objects.
     *
     * Get a list of Cluster Topology Issue objects.  A Cluster Topology Issue indicates incorrect or inconsistent configuration within the DMR network. Such issues will cause messages to be misdelivered or lost.   Attribute|Identifying|Deprecated :---|:---:|:---: dmrClusterName|x| topologyIssue|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getDmrClusterTopologyIssuesTest() throws Exception {
        String dmrClusterName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        DmrClusterTopologyIssuesResponse response = api.getDmrClusterTopologyIssues(dmrClusterName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Cluster objects.
     *
     * Get a list of Cluster objects.  A Cluster is a provisioned object on a message broker that contains global DMR configuration parameters.   Attribute|Identifying|Deprecated :---|:---:|:---: dmrClusterName|x| tlsServerCertEnforceTrustedCommonNameEnabled||x    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getDmrClustersTest() throws Exception {
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        DmrClustersResponse response = api.getDmrClusters(count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Domain Certificate Authority objects.
     *
     * Get a list of Domain Certificate Authority objects.  Certificate Authorities trusted for domain verification.   Attribute|Identifying|Deprecated :---|:---:|:---: certAuthorityName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.19.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getDomainCertAuthoritiesTest() throws Exception {
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        DomainCertAuthoritiesResponse response = api.getDomainCertAuthorities(count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Domain Certificate Authority object.
     *
     * Get a Domain Certificate Authority object.  Certificate Authorities trusted for domain verification.   Attribute|Identifying|Deprecated :---|:---:|:---: certAuthorityName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.19.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getDomainCertAuthorityTest() throws Exception {
        String certAuthorityName = null;
        List<String> select = null;
        DomainCertAuthorityResponse response = api.getDomainCertAuthority(certAuthorityName, select);

        // TODO: test validations
    }
    
    /**
     * Get an External Disk Array object.
     *
     * Get an External Disk Array object.  A customer-provided external disk storage array which can be  configured for guaranteed messaging.   Attribute|Identifying|Deprecated :---|:---:|:---: wwn|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.18.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getGuaranteedMsgingExternalDiskArrayTest() throws Exception {
        String wwn = null;
        List<String> select = null;
        GuaranteedMsgingExternalDiskArrayResponse response = api.getGuaranteedMsgingExternalDiskArray(wwn, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of External Disk Array objects.
     *
     * Get a list of External Disk Array objects.  A customer-provided external disk storage array which can be  configured for guaranteed messaging.   Attribute|Identifying|Deprecated :---|:---:|:---: wwn|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.18.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getGuaranteedMsgingExternalDiskArraysTest() throws Exception {
        List<String> where = null;
        List<String> select = null;
        GuaranteedMsgingExternalDiskArraysResponse response = api.getGuaranteedMsgingExternalDiskArrays(where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Message VPN object.
     *
     * Get a Message VPN object.  Message VPNs (Virtual Private Networks) allow for the segregation of topic space and clients. They also group clients connecting to a network of message brokers, such that messages published within a particular group are only visible to that group&#39;s clients.   Attribute|Identifying|Deprecated :---|:---:|:---: bridgingTlsServerCertEnforceTrustedCommonNameEnabled||x configSyncLocalKey||x configSyncLocalLastResult||x configSyncLocalRole||x configSyncLocalState||x configSyncLocalTimeInState||x counter.controlRxByteCount||x counter.controlRxMsgCount||x counter.controlTxByteCount||x counter.controlTxMsgCount||x counter.dataRxByteCount||x counter.dataRxMsgCount||x counter.dataTxByteCount||x counter.dataTxMsgCount||x counter.discardedRxMsgCount||x counter.discardedTxMsgCount||x counter.loginRxMsgCount||x counter.loginTxMsgCount||x counter.msgSpoolRxMsgCount||x counter.msgSpoolTxMsgCount||x counter.tlsRxByteCount||x counter.tlsTxByteCount||x msgVpnName|x| rate.averageRxByteRate||x rate.averageRxMsgRate||x rate.averageTxByteRate||x rate.averageTxMsgRate||x rate.rxByteRate||x rate.rxMsgRate||x rate.tlsAverageRxByteRate||x rate.tlsAverageTxByteRate||x rate.tlsRxByteRate||x rate.tlsTxByteRate||x rate.txByteRate||x rate.txMsgRate||x restTlsServerCertEnforceTrustedCommonNameEnabled||x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnTest() throws Exception {
        String msgVpnName = null;
        List<String> select = null;
        MsgVpnResponse response = api.getMsgVpn(msgVpnName, select);

        // TODO: test validations
    }
    
    /**
     * Get an ACL Profile object.
     *
     * Get an ACL Profile object.  An ACL Profile controls whether an authenticated client is permitted to establish a connection with the message broker or permitted to publish and subscribe to specific topics.   Attribute|Identifying|Deprecated :---|:---:|:---: aclProfileName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAclProfileTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        List<String> select = null;
        MsgVpnAclProfileResponse response = api.getMsgVpnAclProfile(msgVpnName, aclProfileName, select);

        // TODO: test validations
    }
    
    /**
     * Get a Client Connect Exception object.
     *
     * Get a Client Connect Exception object.  A Client Connect Exception is an exception to the default action to take when a client using the ACL Profile connects to the Message VPN. Exceptions must be expressed as an IP address/netmask in CIDR form.   Attribute|Identifying|Deprecated :---|:---:|:---: aclProfileName|x| clientConnectExceptionAddress|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAclProfileClientConnectExceptionTest() throws Exception {
        String msgVpnName = null;
        String aclProfileName = null;
        String clientConnectExceptionAddress = null;
        List<String> select = null;
        MsgVpnAclProfileClientConnectExceptionResponse response = api.getMsgVpnAclProfileClientConnectException(msgVpnName, aclProfileName, clientConnectExceptionAddress, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Client Connect Exception objects.
     *
     * Get a list of Client Connect Exception objects.  A Client Connect Exception is an exception to the default action to take when a client using the ACL Profile connects to the Message VPN. Exceptions must be expressed as an IP address/netmask in CIDR form.   Attribute|Identifying|Deprecated :---|:---:|:---: aclProfileName|x| clientConnectExceptionAddress|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
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
        List<String> where = null;
        List<String> select = null;
        MsgVpnAclProfileClientConnectExceptionsResponse response = api.getMsgVpnAclProfileClientConnectExceptions(msgVpnName, aclProfileName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Publish Topic Exception object.
     *
     * Get a Publish Topic Exception object.  A Publish Topic Exception is an exception to the default action to take when a client using the ACL Profile publishes to a topic in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Deprecated :---|:---:|:---: aclProfileName|x|x msgVpnName|x|x publishExceptionTopic|x|x topicSyntax|x|x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.14. Replaced by publishTopicExceptions.
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
        List<String> select = null;
        MsgVpnAclProfilePublishExceptionResponse response = api.getMsgVpnAclProfilePublishException(msgVpnName, aclProfileName, topicSyntax, publishExceptionTopic, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Publish Topic Exception objects.
     *
     * Get a list of Publish Topic Exception objects.  A Publish Topic Exception is an exception to the default action to take when a client using the ACL Profile publishes to a topic in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Deprecated :---|:---:|:---: aclProfileName|x|x msgVpnName|x|x publishExceptionTopic|x|x topicSyntax|x|x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.14. Replaced by publishTopicExceptions.
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
        List<String> where = null;
        List<String> select = null;
        MsgVpnAclProfilePublishExceptionsResponse response = api.getMsgVpnAclProfilePublishExceptions(msgVpnName, aclProfileName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Publish Topic Exception object.
     *
     * Get a Publish Topic Exception object.  A Publish Topic Exception is an exception to the default action to take when a client using the ACL Profile publishes to a topic in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Deprecated :---|:---:|:---: aclProfileName|x| msgVpnName|x| publishTopicException|x| publishTopicExceptionSyntax|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.14.
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
        List<String> select = null;
        MsgVpnAclProfilePublishTopicExceptionResponse response = api.getMsgVpnAclProfilePublishTopicException(msgVpnName, aclProfileName, publishTopicExceptionSyntax, publishTopicException, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Publish Topic Exception objects.
     *
     * Get a list of Publish Topic Exception objects.  A Publish Topic Exception is an exception to the default action to take when a client using the ACL Profile publishes to a topic in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Deprecated :---|:---:|:---: aclProfileName|x| msgVpnName|x| publishTopicException|x| publishTopicExceptionSyntax|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.14.
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
        List<String> where = null;
        List<String> select = null;
        MsgVpnAclProfilePublishTopicExceptionsResponse response = api.getMsgVpnAclProfilePublishTopicExceptions(msgVpnName, aclProfileName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Subscribe Topic Exception object.
     *
     * Get a Subscribe Topic Exception object.  A Subscribe Topic Exception is an exception to the default action to take when a client using the ACL Profile subscribes to a topic in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Deprecated :---|:---:|:---: aclProfileName|x|x msgVpnName|x|x subscribeExceptionTopic|x|x topicSyntax|x|x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.14. Replaced by subscribeTopicExceptions.
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
        List<String> select = null;
        MsgVpnAclProfileSubscribeExceptionResponse response = api.getMsgVpnAclProfileSubscribeException(msgVpnName, aclProfileName, topicSyntax, subscribeExceptionTopic, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Subscribe Topic Exception objects.
     *
     * Get a list of Subscribe Topic Exception objects.  A Subscribe Topic Exception is an exception to the default action to take when a client using the ACL Profile subscribes to a topic in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Deprecated :---|:---:|:---: aclProfileName|x|x msgVpnName|x|x subscribeExceptionTopic|x|x topicSyntax|x|x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.14. Replaced by subscribeTopicExceptions.
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
        List<String> where = null;
        List<String> select = null;
        MsgVpnAclProfileSubscribeExceptionsResponse response = api.getMsgVpnAclProfileSubscribeExceptions(msgVpnName, aclProfileName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Subscribe Share Name Exception object.
     *
     * Get a Subscribe Share Name Exception object.  A Subscribe Share Name Exception is an exception to the default action to take when a client using the ACL Profile subscribes to a share-name subscription in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Deprecated :---|:---:|:---: aclProfileName|x| msgVpnName|x| subscribeShareNameException|x| subscribeShareNameExceptionSyntax|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.14.
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
        List<String> select = null;
        MsgVpnAclProfileSubscribeShareNameExceptionResponse response = api.getMsgVpnAclProfileSubscribeShareNameException(msgVpnName, aclProfileName, subscribeShareNameExceptionSyntax, subscribeShareNameException, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Subscribe Share Name Exception objects.
     *
     * Get a list of Subscribe Share Name Exception objects.  A Subscribe Share Name Exception is an exception to the default action to take when a client using the ACL Profile subscribes to a share-name subscription in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Deprecated :---|:---:|:---: aclProfileName|x| msgVpnName|x| subscribeShareNameException|x| subscribeShareNameExceptionSyntax|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.14.
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
        List<String> where = null;
        List<String> select = null;
        MsgVpnAclProfileSubscribeShareNameExceptionsResponse response = api.getMsgVpnAclProfileSubscribeShareNameExceptions(msgVpnName, aclProfileName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Subscribe Topic Exception object.
     *
     * Get a Subscribe Topic Exception object.  A Subscribe Topic Exception is an exception to the default action to take when a client using the ACL Profile subscribes to a topic in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Deprecated :---|:---:|:---: aclProfileName|x| msgVpnName|x| subscribeTopicException|x| subscribeTopicExceptionSyntax|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.14.
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
        List<String> select = null;
        MsgVpnAclProfileSubscribeTopicExceptionResponse response = api.getMsgVpnAclProfileSubscribeTopicException(msgVpnName, aclProfileName, subscribeTopicExceptionSyntax, subscribeTopicException, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Subscribe Topic Exception objects.
     *
     * Get a list of Subscribe Topic Exception objects.  A Subscribe Topic Exception is an exception to the default action to take when a client using the ACL Profile subscribes to a topic in the Message VPN. Exceptions must be expressed as a topic.   Attribute|Identifying|Deprecated :---|:---:|:---: aclProfileName|x| msgVpnName|x| subscribeTopicException|x| subscribeTopicExceptionSyntax|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.14.
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
        List<String> where = null;
        List<String> select = null;
        MsgVpnAclProfileSubscribeTopicExceptionsResponse response = api.getMsgVpnAclProfileSubscribeTopicExceptions(msgVpnName, aclProfileName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of ACL Profile objects.
     *
     * Get a list of ACL Profile objects.  An ACL Profile controls whether an authenticated client is permitted to establish a connection with the message broker or permitted to publish and subscribe to specific topics.   Attribute|Identifying|Deprecated :---|:---:|:---: aclProfileName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAclProfilesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnAclProfilesResponse response = api.getMsgVpnAclProfiles(msgVpnName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get an OAuth Provider object.
     *
     * Get an OAuth Provider object.  OAuth Providers contain information about the issuer of an OAuth token that is needed to validate the token and derive a client username from it.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| oauthProviderName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.13.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAuthenticationOauthProviderTest() throws Exception {
        String msgVpnName = null;
        String oauthProviderName = null;
        List<String> select = null;
        MsgVpnAuthenticationOauthProviderResponse response = api.getMsgVpnAuthenticationOauthProvider(msgVpnName, oauthProviderName, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of OAuth Provider objects.
     *
     * Get a list of OAuth Provider objects.  OAuth Providers contain information about the issuer of an OAuth token that is needed to validate the token and derive a client username from it.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| oauthProviderName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.13.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAuthenticationOauthProvidersTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnAuthenticationOauthProvidersResponse response = api.getMsgVpnAuthenticationOauthProviders(msgVpnName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get an LDAP Authorization Group object.
     *
     * Get an LDAP Authorization Group object.  To use client authorization groups configured on an external LDAP server to provide client authorizations, LDAP Authorization Group objects must be created on the Message VPN that match the authorization groups provisioned on the LDAP server. These objects must be configured with the client profiles and ACL profiles that will be assigned to the clients that belong to those authorization groups. A newly created group is placed at the end of the group list which is the lowest priority.   Attribute|Identifying|Deprecated :---|:---:|:---: authorizationGroupName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAuthorizationGroupTest() throws Exception {
        String msgVpnName = null;
        String authorizationGroupName = null;
        List<String> select = null;
        MsgVpnAuthorizationGroupResponse response = api.getMsgVpnAuthorizationGroup(msgVpnName, authorizationGroupName, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of LDAP Authorization Group objects.
     *
     * Get a list of LDAP Authorization Group objects.  To use client authorization groups configured on an external LDAP server to provide client authorizations, LDAP Authorization Group objects must be created on the Message VPN that match the authorization groups provisioned on the LDAP server. These objects must be configured with the client profiles and ACL profiles that will be assigned to the clients that belong to those authorization groups. A newly created group is placed at the end of the group list which is the lowest priority.   Attribute|Identifying|Deprecated :---|:---:|:---: authorizationGroupName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAuthorizationGroupsTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnAuthorizationGroupsResponse response = api.getMsgVpnAuthorizationGroups(msgVpnName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Bridge object.
     *
     * Get a Bridge object.  Bridges can be used to link two Message VPNs so that messages published to one Message VPN that match the topic subscriptions set for the bridge are also delivered to the linked Message VPN.   Attribute|Identifying|Deprecated :---|:---:|:---: bridgeName|x| bridgeVirtualRouter|x| counter.controlRxByteCount||x counter.controlRxMsgCount||x counter.controlTxByteCount||x counter.controlTxMsgCount||x counter.dataRxByteCount||x counter.dataRxMsgCount||x counter.dataTxByteCount||x counter.dataTxMsgCount||x counter.discardedRxMsgCount||x counter.discardedTxMsgCount||x counter.loginRxMsgCount||x counter.loginTxMsgCount||x counter.msgSpoolRxMsgCount||x counter.rxByteCount||x counter.rxMsgCount||x counter.txByteCount||x counter.txMsgCount||x msgVpnName|x| rate.averageRxByteRate||x rate.averageRxMsgRate||x rate.averageTxByteRate||x rate.averageTxMsgRate||x rate.rxByteRate||x rate.rxMsgRate||x rate.txByteRate||x rate.txMsgRate||x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnBridgeTest() throws Exception {
        String msgVpnName = null;
        String bridgeName = null;
        String bridgeVirtualRouter = null;
        List<String> select = null;
        MsgVpnBridgeResponse response = api.getMsgVpnBridge(msgVpnName, bridgeName, bridgeVirtualRouter, select);

        // TODO: test validations
    }
    
    /**
     * Get a Bridge Local Subscriptions object.
     *
     * Get a Bridge Local Subscriptions object.  A Local Subscription is a topic subscription used by a remote Message VPN Bridge to attract messages from this broker.   Attribute|Identifying|Deprecated :---|:---:|:---: bridgeName|x| bridgeVirtualRouter|x| localSubscriptionTopic|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnBridgeLocalSubscriptionTest() throws Exception {
        String msgVpnName = null;
        String bridgeName = null;
        String bridgeVirtualRouter = null;
        String localSubscriptionTopic = null;
        List<String> select = null;
        MsgVpnBridgeLocalSubscriptionResponse response = api.getMsgVpnBridgeLocalSubscription(msgVpnName, bridgeName, bridgeVirtualRouter, localSubscriptionTopic, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Bridge Local Subscriptions objects.
     *
     * Get a list of Bridge Local Subscriptions objects.  A Local Subscription is a topic subscription used by a remote Message VPN Bridge to attract messages from this broker.   Attribute|Identifying|Deprecated :---|:---:|:---: bridgeName|x| bridgeVirtualRouter|x| localSubscriptionTopic|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnBridgeLocalSubscriptionsTest() throws Exception {
        String msgVpnName = null;
        String bridgeName = null;
        String bridgeVirtualRouter = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnBridgeLocalSubscriptionsResponse response = api.getMsgVpnBridgeLocalSubscriptions(msgVpnName, bridgeName, bridgeVirtualRouter, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Remote Message VPN object.
     *
     * Get a Remote Message VPN object.  The Remote Message VPN is the Message VPN that the Bridge connects to.   Attribute|Identifying|Deprecated :---|:---:|:---: bridgeName|x| bridgeVirtualRouter|x| msgVpnName|x| remoteMsgVpnInterface|x| remoteMsgVpnLocation|x| remoteMsgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
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
        List<String> select = null;
        MsgVpnBridgeRemoteMsgVpnResponse response = api.getMsgVpnBridgeRemoteMsgVpn(msgVpnName, bridgeName, bridgeVirtualRouter, remoteMsgVpnName, remoteMsgVpnLocation, remoteMsgVpnInterface, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Remote Message VPN objects.
     *
     * Get a list of Remote Message VPN objects.  The Remote Message VPN is the Message VPN that the Bridge connects to.   Attribute|Identifying|Deprecated :---|:---:|:---: bridgeName|x| bridgeVirtualRouter|x| msgVpnName|x| remoteMsgVpnInterface|x| remoteMsgVpnLocation|x| remoteMsgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnBridgeRemoteMsgVpnsTest() throws Exception {
        String msgVpnName = null;
        String bridgeName = null;
        String bridgeVirtualRouter = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnBridgeRemoteMsgVpnsResponse response = api.getMsgVpnBridgeRemoteMsgVpns(msgVpnName, bridgeName, bridgeVirtualRouter, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Remote Subscription object.
     *
     * Get a Remote Subscription object.  A Remote Subscription is a topic subscription used by the Message VPN Bridge to attract messages from the remote message broker.   Attribute|Identifying|Deprecated :---|:---:|:---: bridgeName|x| bridgeVirtualRouter|x| msgVpnName|x| remoteSubscriptionTopic|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
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
        List<String> select = null;
        MsgVpnBridgeRemoteSubscriptionResponse response = api.getMsgVpnBridgeRemoteSubscription(msgVpnName, bridgeName, bridgeVirtualRouter, remoteSubscriptionTopic, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Remote Subscription objects.
     *
     * Get a list of Remote Subscription objects.  A Remote Subscription is a topic subscription used by the Message VPN Bridge to attract messages from the remote message broker.   Attribute|Identifying|Deprecated :---|:---:|:---: bridgeName|x| bridgeVirtualRouter|x| msgVpnName|x| remoteSubscriptionTopic|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
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
        List<String> where = null;
        List<String> select = null;
        MsgVpnBridgeRemoteSubscriptionsResponse response = api.getMsgVpnBridgeRemoteSubscriptions(msgVpnName, bridgeName, bridgeVirtualRouter, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Trusted Common Name object.
     *
     * Get a Trusted Common Name object.  The Trusted Common Names for the Bridge are used by encrypted transports to verify the name in the certificate presented by the remote node. They must include the common name of the remote node&#39;s server certificate or client certificate, depending upon the initiator of the connection.   Attribute|Identifying|Deprecated :---|:---:|:---: bridgeName|x|x bridgeVirtualRouter|x|x msgVpnName|x|x tlsTrustedCommonName|x|x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.18. Common Name validation has been replaced by Server Certificate Name validation.
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
        List<String> select = null;
        MsgVpnBridgeTlsTrustedCommonNameResponse response = api.getMsgVpnBridgeTlsTrustedCommonName(msgVpnName, bridgeName, bridgeVirtualRouter, tlsTrustedCommonName, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Trusted Common Name objects.
     *
     * Get a list of Trusted Common Name objects.  The Trusted Common Names for the Bridge are used by encrypted transports to verify the name in the certificate presented by the remote node. They must include the common name of the remote node&#39;s server certificate or client certificate, depending upon the initiator of the connection.   Attribute|Identifying|Deprecated :---|:---:|:---: bridgeName|x|x bridgeVirtualRouter|x|x msgVpnName|x|x tlsTrustedCommonName|x|x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.18. Common Name validation has been replaced by Server Certificate Name validation.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnBridgeTlsTrustedCommonNamesTest() throws Exception {
        String msgVpnName = null;
        String bridgeName = null;
        String bridgeVirtualRouter = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnBridgeTlsTrustedCommonNamesResponse response = api.getMsgVpnBridgeTlsTrustedCommonNames(msgVpnName, bridgeName, bridgeVirtualRouter, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Bridge objects.
     *
     * Get a list of Bridge objects.  Bridges can be used to link two Message VPNs so that messages published to one Message VPN that match the topic subscriptions set for the bridge are also delivered to the linked Message VPN.   Attribute|Identifying|Deprecated :---|:---:|:---: bridgeName|x| bridgeVirtualRouter|x| counter.controlRxByteCount||x counter.controlRxMsgCount||x counter.controlTxByteCount||x counter.controlTxMsgCount||x counter.dataRxByteCount||x counter.dataRxMsgCount||x counter.dataTxByteCount||x counter.dataTxMsgCount||x counter.discardedRxMsgCount||x counter.discardedTxMsgCount||x counter.loginRxMsgCount||x counter.loginTxMsgCount||x counter.msgSpoolRxMsgCount||x counter.rxByteCount||x counter.rxMsgCount||x counter.txByteCount||x counter.txMsgCount||x msgVpnName|x| rate.averageRxByteRate||x rate.averageRxMsgRate||x rate.averageTxByteRate||x rate.averageTxMsgRate||x rate.rxByteRate||x rate.rxMsgRate||x rate.txByteRate||x rate.txMsgRate||x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnBridgesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnBridgesResponse response = api.getMsgVpnBridges(msgVpnName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Client object.
     *
     * Get a Client object.  Applications or devices that connect to message brokers to send and/or receive messages are represented as Clients.   Attribute|Identifying|Deprecated :---|:---:|:---: clientName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnClientTest() throws Exception {
        String msgVpnName = null;
        String clientName = null;
        List<String> select = null;
        MsgVpnClientResponse response = api.getMsgVpnClient(msgVpnName, clientName, select);

        // TODO: test validations
    }
    
    /**
     * Get a Client Connection object.
     *
     * Get a Client Connection object.  A Client Connection represents the Transmission Control Protocol (TCP) connection the Client uses to communicate with the message broker.   Attribute|Identifying|Deprecated :---|:---:|:---: clientAddress|x| clientName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnClientConnectionTest() throws Exception {
        String msgVpnName = null;
        String clientName = null;
        String clientAddress = null;
        List<String> select = null;
        MsgVpnClientConnectionResponse response = api.getMsgVpnClientConnection(msgVpnName, clientName, clientAddress, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Client Connection objects.
     *
     * Get a list of Client Connection objects.  A Client Connection represents the Transmission Control Protocol (TCP) connection the Client uses to communicate with the message broker.   Attribute|Identifying|Deprecated :---|:---:|:---: clientAddress|x| clientName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnClientConnectionsTest() throws Exception {
        String msgVpnName = null;
        String clientName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnClientConnectionsResponse response = api.getMsgVpnClientConnections(msgVpnName, clientName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Client Profile object.
     *
     * Get a Client Profile object.  Client Profiles are used to assign common configuration properties to clients that have been successfully authorized.   Attribute|Identifying|Deprecated :---|:---:|:---: allowCutThroughForwardingEnabled||x apiQueueManagementCopyFromOnCreateName||x apiTopicEndpointManagementCopyFromOnCreateName||x clientProfileName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnClientProfileTest() throws Exception {
        String msgVpnName = null;
        String clientProfileName = null;
        List<String> select = null;
        MsgVpnClientProfileResponse response = api.getMsgVpnClientProfile(msgVpnName, clientProfileName, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Client Profile objects.
     *
     * Get a list of Client Profile objects.  Client Profiles are used to assign common configuration properties to clients that have been successfully authorized.   Attribute|Identifying|Deprecated :---|:---:|:---: allowCutThroughForwardingEnabled||x apiQueueManagementCopyFromOnCreateName||x apiTopicEndpointManagementCopyFromOnCreateName||x clientProfileName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnClientProfilesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnClientProfilesResponse response = api.getMsgVpnClientProfiles(msgVpnName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Client Receive Flow object.
     *
     * Get a Client Receive Flow object.  Client Receive Flows are used by clients to publish Guaranteed messages to a message broker.   Attribute|Identifying|Deprecated :---|:---:|:---: clientName|x| flowId|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnClientRxFlowTest() throws Exception {
        String msgVpnName = null;
        String clientName = null;
        String flowId = null;
        List<String> select = null;
        MsgVpnClientRxFlowResponse response = api.getMsgVpnClientRxFlow(msgVpnName, clientName, flowId, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Client Receive Flow objects.
     *
     * Get a list of Client Receive Flow objects.  Client Receive Flows are used by clients to publish Guaranteed messages to a message broker.   Attribute|Identifying|Deprecated :---|:---:|:---: clientName|x| flowId|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnClientRxFlowsTest() throws Exception {
        String msgVpnName = null;
        String clientName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnClientRxFlowsResponse response = api.getMsgVpnClientRxFlows(msgVpnName, clientName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Client Subscription object.
     *
     * Get a Client Subscription object.  Once clients are authenticated on the message broker they can add and remove Client Subscriptions for Direct messages published to the Message VPN to which they have connected.   Attribute|Identifying|Deprecated :---|:---:|:---: clientName|x| msgVpnName|x| subscriptionTopic|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnClientSubscriptionTest() throws Exception {
        String msgVpnName = null;
        String clientName = null;
        String subscriptionTopic = null;
        List<String> select = null;
        MsgVpnClientSubscriptionResponse response = api.getMsgVpnClientSubscription(msgVpnName, clientName, subscriptionTopic, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Client Subscription objects.
     *
     * Get a list of Client Subscription objects.  Once clients are authenticated on the message broker they can add and remove Client Subscriptions for Direct messages published to the Message VPN to which they have connected.   Attribute|Identifying|Deprecated :---|:---:|:---: clientName|x| msgVpnName|x| subscriptionTopic|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnClientSubscriptionsTest() throws Exception {
        String msgVpnName = null;
        String clientName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnClientSubscriptionsResponse response = api.getMsgVpnClientSubscriptions(msgVpnName, clientName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Client Transacted Session object.
     *
     * Get a Client Transacted Session object.  Transacted Sessions enable clients to group multiple message send and/or receive operations together in single, atomic units known as local transactions.   Attribute|Identifying|Deprecated :---|:---:|:---: clientName|x| msgVpnName|x| sessionName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnClientTransactedSessionTest() throws Exception {
        String msgVpnName = null;
        String clientName = null;
        String sessionName = null;
        List<String> select = null;
        MsgVpnClientTransactedSessionResponse response = api.getMsgVpnClientTransactedSession(msgVpnName, clientName, sessionName, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Client Transacted Session objects.
     *
     * Get a list of Client Transacted Session objects.  Transacted Sessions enable clients to group multiple message send and/or receive operations together in single, atomic units known as local transactions.   Attribute|Identifying|Deprecated :---|:---:|:---: clientName|x| msgVpnName|x| sessionName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnClientTransactedSessionsTest() throws Exception {
        String msgVpnName = null;
        String clientName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnClientTransactedSessionsResponse response = api.getMsgVpnClientTransactedSessions(msgVpnName, clientName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Client Transmit Flow object.
     *
     * Get a Client Transmit Flow object.  Client Transmit Flows are used by clients to consume Guaranteed messages from a message broker.   Attribute|Identifying|Deprecated :---|:---:|:---: clientName|x| flowId|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnClientTxFlowTest() throws Exception {
        String msgVpnName = null;
        String clientName = null;
        String flowId = null;
        List<String> select = null;
        MsgVpnClientTxFlowResponse response = api.getMsgVpnClientTxFlow(msgVpnName, clientName, flowId, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Client Transmit Flow objects.
     *
     * Get a list of Client Transmit Flow objects.  Client Transmit Flows are used by clients to consume Guaranteed messages from a message broker.   Attribute|Identifying|Deprecated :---|:---:|:---: clientName|x| flowId|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnClientTxFlowsTest() throws Exception {
        String msgVpnName = null;
        String clientName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnClientTxFlowsResponse response = api.getMsgVpnClientTxFlows(msgVpnName, clientName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Client Username object.
     *
     * Get a Client Username object.  A client is only authorized to connect to a Message VPN that is associated with a Client Username that the client has been assigned.   Attribute|Identifying|Deprecated :---|:---:|:---: clientUsername|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnClientUsernameTest() throws Exception {
        String msgVpnName = null;
        String clientUsername = null;
        List<String> select = null;
        MsgVpnClientUsernameResponse response = api.getMsgVpnClientUsername(msgVpnName, clientUsername, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Client Username objects.
     *
     * Get a list of Client Username objects.  A client is only authorized to connect to a Message VPN that is associated with a Client Username that the client has been assigned.   Attribute|Identifying|Deprecated :---|:---:|:---: clientUsername|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnClientUsernamesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnClientUsernamesResponse response = api.getMsgVpnClientUsernames(msgVpnName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Client objects.
     *
     * Get a list of Client objects.  Applications or devices that connect to message brokers to send and/or receive messages are represented as Clients.   Attribute|Identifying|Deprecated :---|:---:|:---: clientName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnClientsTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnClientsResponse response = api.getMsgVpnClients(msgVpnName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Config Sync Remote Node object.
     *
     * Get a Config Sync Remote Node object.  A Config Sync Remote Node object contains information about the status of the table for this Message VPN with respect to a remote node.   Attribute|Identifying|Deprecated :---|:---:|:---: lastMsgRxTime||x msgVpnName|x|x remoteNodeName|x|x role||x stale||x state||x timeInState||x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.22. This attribute has been deprecated.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnConfigSyncRemoteNodeTest() throws Exception {
        String msgVpnName = null;
        String remoteNodeName = null;
        List<String> select = null;
        MsgVpnConfigSyncRemoteNodeResponse response = api.getMsgVpnConfigSyncRemoteNode(msgVpnName, remoteNodeName, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Config Sync Remote Node objects.
     *
     * Get a list of Config Sync Remote Node objects.  A Config Sync Remote Node object contains information about the status of the table for this Message VPN with respect to a remote node.   Attribute|Identifying|Deprecated :---|:---:|:---: lastMsgRxTime||x msgVpnName|x|x remoteNodeName|x|x role||x stale||x state||x timeInState||x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.22. This attribute has been deprecated.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnConfigSyncRemoteNodesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnConfigSyncRemoteNodesResponse response = api.getMsgVpnConfigSyncRemoteNodes(msgVpnName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Distributed Cache object.
     *
     * Get a Distributed Cache object.  A Distributed Cache is a collection of one or more Cache Clusters that belong to the same Message VPN. Each Cache Cluster in a Distributed Cache is configured to subscribe to a different set of topics. This effectively divides up the configured topic space, to provide scaling to very large topic spaces or very high cached message throughput.   Attribute|Identifying|Deprecated :---|:---:|:---: cacheName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnDistributedCacheTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        List<String> select = null;
        MsgVpnDistributedCacheResponse response = api.getMsgVpnDistributedCache(msgVpnName, cacheName, select);

        // TODO: test validations
    }
    
    /**
     * Get a Cache Cluster object.
     *
     * Get a Cache Cluster object.  A Cache Cluster is a collection of one or more Cache Instances that subscribe to exactly the same topics. Cache Instances are grouped together in a Cache Cluster for the purpose of fault tolerance and load balancing. As published messages are received, the message broker message bus sends these live data messages to the Cache Instances in the Cache Cluster. This enables client cache requests to be served by any of Cache Instances in the Cache Cluster.   Attribute|Identifying|Deprecated :---|:---:|:---: cacheName|x| clusterName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnDistributedCacheClusterTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterResponse response = api.getMsgVpnDistributedCacheCluster(msgVpnName, cacheName, clusterName, select);

        // TODO: test validations
    }
    
    /**
     * Get a Home Cache Cluster object.
     *
     * Get a Home Cache Cluster object.  A Home Cache Cluster is a Cache Cluster that is the \&quot;definitive\&quot; Cache Cluster for a given topic in the context of the Global Caching feature.   Attribute|Identifying|Deprecated :---|:---:|:---: cacheName|x| clusterName|x| homeClusterName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
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
        List<String> select = null;
        MsgVpnDistributedCacheClusterGlobalCachingHomeClusterResponse response = api.getMsgVpnDistributedCacheClusterGlobalCachingHomeCluster(msgVpnName, cacheName, clusterName, homeClusterName, select);

        // TODO: test validations
    }
    
    /**
     * Get a Topic Prefix object.
     *
     * Get a Topic Prefix object.  A Topic Prefix is a prefix for a global topic that is available from the containing Home Cache Cluster.   Attribute|Identifying|Deprecated :---|:---:|:---: cacheName|x| clusterName|x| homeClusterName|x| msgVpnName|x| topicPrefix|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
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
        List<String> select = null;
        MsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixResponse response = api.getMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefix(msgVpnName, cacheName, clusterName, homeClusterName, topicPrefix, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Topic Prefix objects.
     *
     * Get a list of Topic Prefix objects.  A Topic Prefix is a prefix for a global topic that is available from the containing Home Cache Cluster.   Attribute|Identifying|Deprecated :---|:---:|:---: cacheName|x| clusterName|x| homeClusterName|x| msgVpnName|x| topicPrefix|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
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
        List<String> where = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixesResponse response = api.getMsgVpnDistributedCacheClusterGlobalCachingHomeClusterTopicPrefixes(msgVpnName, cacheName, clusterName, homeClusterName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Home Cache Cluster objects.
     *
     * Get a list of Home Cache Cluster objects.  A Home Cache Cluster is a Cache Cluster that is the \&quot;definitive\&quot; Cache Cluster for a given topic in the context of the Global Caching feature.   Attribute|Identifying|Deprecated :---|:---:|:---: cacheName|x| clusterName|x| homeClusterName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
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
        List<String> where = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterGlobalCachingHomeClustersResponse response = api.getMsgVpnDistributedCacheClusterGlobalCachingHomeClusters(msgVpnName, cacheName, clusterName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Cache Instance object.
     *
     * Get a Cache Instance object.  A Cache Instance is a single Cache process that belongs to a single Cache Cluster. A Cache Instance object provisioned on the broker is used to disseminate configuration information to the Cache process. Cache Instances listen for and cache live data messages that match the topic subscriptions configured for their parent Cache Cluster.   Attribute|Identifying|Deprecated :---|:---:|:---: cacheName|x| clusterName|x| counter.msgCount||x counter.msgPeakCount||x counter.requestQueueDepthCount||x counter.requestQueueDepthPeakCount||x counter.topicCount||x counter.topicPeakCount||x instanceName|x| msgVpnName|x| rate.averageDataRxBytePeakRate||x rate.averageDataRxByteRate||x rate.averageDataRxMsgPeakRate||x rate.averageDataRxMsgRate||x rate.averageDataTxMsgPeakRate||x rate.averageDataTxMsgRate||x rate.averageRequestRxPeakRate||x rate.averageRequestRxRate||x rate.dataRxBytePeakRate||x rate.dataRxByteRate||x rate.dataRxMsgPeakRate||x rate.dataRxMsgRate||x rate.dataTxMsgPeakRate||x rate.dataTxMsgRate||x rate.requestRxPeakRate||x rate.requestRxRate||x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
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
        List<String> select = null;
        MsgVpnDistributedCacheClusterInstanceResponse response = api.getMsgVpnDistributedCacheClusterInstance(msgVpnName, cacheName, clusterName, instanceName, select);

        // TODO: test validations
    }
    
    /**
     * Get a Remote Home Cache Cluster object.
     *
     * Get a Remote Home Cache Cluster object.  A Remote Home Cache Cluster is a Home Cache Cluster that the Cache Instance is communicating with in the context of the Global Caching feature.   Attribute|Identifying|Deprecated :---|:---:|:---: cacheName|x| clusterName|x| homeClusterName|x| instanceName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeClusterTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        String instanceName = null;
        String homeClusterName = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeClusterResponse response = api.getMsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeCluster(msgVpnName, cacheName, clusterName, instanceName, homeClusterName, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Remote Home Cache Cluster objects.
     *
     * Get a list of Remote Home Cache Cluster objects.  A Remote Home Cache Cluster is a Home Cache Cluster that the Cache Instance is communicating with in the context of the Global Caching feature.   Attribute|Identifying|Deprecated :---|:---:|:---: cacheName|x| clusterName|x| homeClusterName|x| instanceName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeClustersTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        String instanceName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeClustersResponse response = api.getMsgVpnDistributedCacheClusterInstanceRemoteGlobalCachingHomeClusters(msgVpnName, cacheName, clusterName, instanceName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Remote Topic object.
     *
     * Get a Remote Topic object.  A Remote Topic is a topic for which the Cache Instance has cached messages.   Attribute|Identifying|Deprecated :---|:---:|:---: cacheName|x| clusterName|x| instanceName|x| msgVpnName|x| topic|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnDistributedCacheClusterInstanceRemoteTopicTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        String instanceName = null;
        String topic = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterInstanceRemoteTopicResponse response = api.getMsgVpnDistributedCacheClusterInstanceRemoteTopic(msgVpnName, cacheName, clusterName, instanceName, topic, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Remote Topic objects.
     *
     * Get a list of Remote Topic objects.  A Remote Topic is a topic for which the Cache Instance has cached messages.   Attribute|Identifying|Deprecated :---|:---:|:---: cacheName|x| clusterName|x| instanceName|x| msgVpnName|x| topic|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnDistributedCacheClusterInstanceRemoteTopicsTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        String clusterName = null;
        String instanceName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterInstanceRemoteTopicsResponse response = api.getMsgVpnDistributedCacheClusterInstanceRemoteTopics(msgVpnName, cacheName, clusterName, instanceName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Cache Instance objects.
     *
     * Get a list of Cache Instance objects.  A Cache Instance is a single Cache process that belongs to a single Cache Cluster. A Cache Instance object provisioned on the broker is used to disseminate configuration information to the Cache process. Cache Instances listen for and cache live data messages that match the topic subscriptions configured for their parent Cache Cluster.   Attribute|Identifying|Deprecated :---|:---:|:---: cacheName|x| clusterName|x| counter.msgCount||x counter.msgPeakCount||x counter.requestQueueDepthCount||x counter.requestQueueDepthPeakCount||x counter.topicCount||x counter.topicPeakCount||x instanceName|x| msgVpnName|x| rate.averageDataRxBytePeakRate||x rate.averageDataRxByteRate||x rate.averageDataRxMsgPeakRate||x rate.averageDataRxMsgRate||x rate.averageDataTxMsgPeakRate||x rate.averageDataTxMsgRate||x rate.averageRequestRxPeakRate||x rate.averageRequestRxRate||x rate.dataRxBytePeakRate||x rate.dataRxByteRate||x rate.dataRxMsgPeakRate||x rate.dataRxMsgRate||x rate.dataTxMsgPeakRate||x rate.dataTxMsgRate||x rate.requestRxPeakRate||x rate.requestRxRate||x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
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
        List<String> where = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterInstancesResponse response = api.getMsgVpnDistributedCacheClusterInstances(msgVpnName, cacheName, clusterName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Topic object.
     *
     * Get a Topic object.  The Cache Instances that belong to the containing Cache Cluster will cache any messages published to topics that match a Topic Subscription.   Attribute|Identifying|Deprecated :---|:---:|:---: cacheName|x| clusterName|x| msgVpnName|x| topic|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
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
        List<String> select = null;
        MsgVpnDistributedCacheClusterTopicResponse response = api.getMsgVpnDistributedCacheClusterTopic(msgVpnName, cacheName, clusterName, topic, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Topic objects.
     *
     * Get a list of Topic objects.  The Cache Instances that belong to the containing Cache Cluster will cache any messages published to topics that match a Topic Subscription.   Attribute|Identifying|Deprecated :---|:---:|:---: cacheName|x| clusterName|x| msgVpnName|x| topic|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
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
        List<String> where = null;
        List<String> select = null;
        MsgVpnDistributedCacheClusterTopicsResponse response = api.getMsgVpnDistributedCacheClusterTopics(msgVpnName, cacheName, clusterName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Cache Cluster objects.
     *
     * Get a list of Cache Cluster objects.  A Cache Cluster is a collection of one or more Cache Instances that subscribe to exactly the same topics. Cache Instances are grouped together in a Cache Cluster for the purpose of fault tolerance and load balancing. As published messages are received, the message broker message bus sends these live data messages to the Cache Instances in the Cache Cluster. This enables client cache requests to be served by any of Cache Instances in the Cache Cluster.   Attribute|Identifying|Deprecated :---|:---:|:---: cacheName|x| clusterName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
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
        List<String> where = null;
        List<String> select = null;
        MsgVpnDistributedCacheClustersResponse response = api.getMsgVpnDistributedCacheClusters(msgVpnName, cacheName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Distributed Cache objects.
     *
     * Get a list of Distributed Cache objects.  A Distributed Cache is a collection of one or more Cache Clusters that belong to the same Message VPN. Each Cache Cluster in a Distributed Cache is configured to subscribe to a different set of topics. This effectively divides up the configured topic space, to provide scaling to very large topic spaces or very high cached message throughput.   Attribute|Identifying|Deprecated :---|:---:|:---: cacheName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnDistributedCachesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnDistributedCachesResponse response = api.getMsgVpnDistributedCaches(msgVpnName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a DMR Bridge object.
     *
     * Get a DMR Bridge object.  A DMR Bridge is required to establish a data channel over a corresponding external link to the remote node for a given Message VPN. Each DMR Bridge identifies which external link the Message VPN should use, and what the name of the equivalent Message VPN at the remote node is.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| remoteNodeName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnDmrBridgeTest() throws Exception {
        String msgVpnName = null;
        String remoteNodeName = null;
        List<String> select = null;
        MsgVpnDmrBridgeResponse response = api.getMsgVpnDmrBridge(msgVpnName, remoteNodeName, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of DMR Bridge objects.
     *
     * Get a list of DMR Bridge objects.  A DMR Bridge is required to establish a data channel over a corresponding external link to the remote node for a given Message VPN. Each DMR Bridge identifies which external link the Message VPN should use, and what the name of the equivalent Message VPN at the remote node is.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| remoteNodeName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnDmrBridgesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnDmrBridgesResponse response = api.getMsgVpnDmrBridges(msgVpnName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of JNDI Connection Factory objects.
     *
     * Get a list of JNDI Connection Factory objects.  The message broker provides an internal JNDI store for provisioned Connection Factory objects that clients can access through JNDI lookups.   Attribute|Identifying|Deprecated :---|:---:|:---: connectionFactoryName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnJndiConnectionFactoriesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnJndiConnectionFactoriesResponse response = api.getMsgVpnJndiConnectionFactories(msgVpnName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a JNDI Connection Factory object.
     *
     * Get a JNDI Connection Factory object.  The message broker provides an internal JNDI store for provisioned Connection Factory objects that clients can access through JNDI lookups.   Attribute|Identifying|Deprecated :---|:---:|:---: connectionFactoryName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnJndiConnectionFactoryTest() throws Exception {
        String msgVpnName = null;
        String connectionFactoryName = null;
        List<String> select = null;
        MsgVpnJndiConnectionFactoryResponse response = api.getMsgVpnJndiConnectionFactory(msgVpnName, connectionFactoryName, select);

        // TODO: test validations
    }
    
    /**
     * Get a JNDI Queue object.
     *
     * Get a JNDI Queue object.  The message broker provides an internal JNDI store for provisioned Queue objects that clients can access through JNDI lookups.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| queueName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnJndiQueueTest() throws Exception {
        String msgVpnName = null;
        String queueName = null;
        List<String> select = null;
        MsgVpnJndiQueueResponse response = api.getMsgVpnJndiQueue(msgVpnName, queueName, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of JNDI Queue objects.
     *
     * Get a list of JNDI Queue objects.  The message broker provides an internal JNDI store for provisioned Queue objects that clients can access through JNDI lookups.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| queueName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnJndiQueuesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnJndiQueuesResponse response = api.getMsgVpnJndiQueues(msgVpnName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a JNDI Topic object.
     *
     * Get a JNDI Topic object.  The message broker provides an internal JNDI store for provisioned Topic objects that clients can access through JNDI lookups.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| topicName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnJndiTopicTest() throws Exception {
        String msgVpnName = null;
        String topicName = null;
        List<String> select = null;
        MsgVpnJndiTopicResponse response = api.getMsgVpnJndiTopic(msgVpnName, topicName, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of JNDI Topic objects.
     *
     * Get a list of JNDI Topic objects.  The message broker provides an internal JNDI store for provisioned Topic objects that clients can access through JNDI lookups.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| topicName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnJndiTopicsTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnJndiTopicsResponse response = api.getMsgVpnJndiTopics(msgVpnName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get an MQTT Retain Cache object.
     *
     * Get an MQTT Retain Cache object.  Using MQTT retained messages allows publishing MQTT clients to indicate that a message must be stored for later delivery to subscribing clients when those subscribing clients add subscriptions matching the retained message&#39;s topic. An MQTT Retain Cache processes all retained messages for a Message VPN.   Attribute|Identifying|Deprecated :---|:---:|:---: cacheName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnMqttRetainCacheTest() throws Exception {
        String msgVpnName = null;
        String cacheName = null;
        List<String> select = null;
        MsgVpnMqttRetainCacheResponse response = api.getMsgVpnMqttRetainCache(msgVpnName, cacheName, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of MQTT Retain Cache objects.
     *
     * Get a list of MQTT Retain Cache objects.  Using MQTT retained messages allows publishing MQTT clients to indicate that a message must be stored for later delivery to subscribing clients when those subscribing clients add subscriptions matching the retained message&#39;s topic. An MQTT Retain Cache processes all retained messages for a Message VPN.   Attribute|Identifying|Deprecated :---|:---:|:---: cacheName|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnMqttRetainCachesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnMqttRetainCachesResponse response = api.getMsgVpnMqttRetainCaches(msgVpnName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get an MQTT Session object.
     *
     * Get an MQTT Session object.  An MQTT Session object is a virtual representation of an MQTT client connection. An MQTT session holds the state of an MQTT client (that is, it is used to contain a client&#39;s QoS 0 and QoS 1 subscription sets and any undelivered QoS 1 messages).   Attribute|Identifying|Deprecated :---|:---:|:---: counter.mqttConnackErrorTxCount||x counter.mqttConnackTxCount||x counter.mqttConnectRxCount||x counter.mqttDisconnectRxCount||x counter.mqttPubcompTxCount||x counter.mqttPublishQos0RxCount||x counter.mqttPublishQos0TxCount||x counter.mqttPublishQos1RxCount||x counter.mqttPublishQos1TxCount||x counter.mqttPublishQos2RxCount||x counter.mqttPubrecTxCount||x counter.mqttPubrelRxCount||x mqttSessionClientId|x| mqttSessionVirtualRouter|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnMqttSessionTest() throws Exception {
        String msgVpnName = null;
        String mqttSessionClientId = null;
        String mqttSessionVirtualRouter = null;
        List<String> select = null;
        MsgVpnMqttSessionResponse response = api.getMsgVpnMqttSession(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, select);

        // TODO: test validations
    }
    
    /**
     * Get a Subscription object.
     *
     * Get a Subscription object.  An MQTT session contains a client&#39;s QoS 0 and QoS 1 subscription sets. On creation, a subscription defaults to QoS 0.   Attribute|Identifying|Deprecated :---|:---:|:---: mqttSessionClientId|x| mqttSessionVirtualRouter|x| msgVpnName|x| subscriptionTopic|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
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
        List<String> select = null;
        MsgVpnMqttSessionSubscriptionResponse response = api.getMsgVpnMqttSessionSubscription(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, subscriptionTopic, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Subscription objects.
     *
     * Get a list of Subscription objects.  An MQTT session contains a client&#39;s QoS 0 and QoS 1 subscription sets. On creation, a subscription defaults to QoS 0.   Attribute|Identifying|Deprecated :---|:---:|:---: mqttSessionClientId|x| mqttSessionVirtualRouter|x| msgVpnName|x| subscriptionTopic|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
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
        List<String> where = null;
        List<String> select = null;
        MsgVpnMqttSessionSubscriptionsResponse response = api.getMsgVpnMqttSessionSubscriptions(msgVpnName, mqttSessionClientId, mqttSessionVirtualRouter, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of MQTT Session objects.
     *
     * Get a list of MQTT Session objects.  An MQTT Session object is a virtual representation of an MQTT client connection. An MQTT session holds the state of an MQTT client (that is, it is used to contain a client&#39;s QoS 0 and QoS 1 subscription sets and any undelivered QoS 1 messages).   Attribute|Identifying|Deprecated :---|:---:|:---: counter.mqttConnackErrorTxCount||x counter.mqttConnackTxCount||x counter.mqttConnectRxCount||x counter.mqttDisconnectRxCount||x counter.mqttPubcompTxCount||x counter.mqttPublishQos0RxCount||x counter.mqttPublishQos0TxCount||x counter.mqttPublishQos1RxCount||x counter.mqttPublishQos1TxCount||x counter.mqttPublishQos2RxCount||x counter.mqttPubrecTxCount||x counter.mqttPubrelRxCount||x mqttSessionClientId|x| mqttSessionVirtualRouter|x| msgVpnName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnMqttSessionsTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnMqttSessionsResponse response = api.getMsgVpnMqttSessions(msgVpnName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Queue object.
     *
     * Get a Queue object.  A Queue acts as both a destination that clients can publish messages to, and as an endpoint that clients can bind consumers to and consume messages from.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| queueName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnQueueTest() throws Exception {
        String msgVpnName = null;
        String queueName = null;
        List<String> select = null;
        MsgVpnQueueResponse response = api.getMsgVpnQueue(msgVpnName, queueName, select);

        // TODO: test validations
    }
    
    /**
     * Get a Queue Message object.
     *
     * Get a Queue Message object.  A Queue Message is a packet of information sent from producers to consumers using the Queue.   Attribute|Identifying|Deprecated :---|:---:|:---: msgId|x| msgVpnName|x| queueName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnQueueMsgTest() throws Exception {
        String msgVpnName = null;
        String queueName = null;
        String msgId = null;
        List<String> select = null;
        MsgVpnQueueMsgResponse response = api.getMsgVpnQueueMsg(msgVpnName, queueName, msgId, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Queue Message objects.
     *
     * Get a list of Queue Message objects.  A Queue Message is a packet of information sent from producers to consumers using the Queue.   Attribute|Identifying|Deprecated :---|:---:|:---: msgId|x| msgVpnName|x| queueName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnQueueMsgsTest() throws Exception {
        String msgVpnName = null;
        String queueName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnQueueMsgsResponse response = api.getMsgVpnQueueMsgs(msgVpnName, queueName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Queue Priority objects.
     *
     * Get a list of Queue Priority objects.  Queues can optionally support priority message delivery; all messages of a higher priority are delivered before any messages of a lower priority. A Priority object contains information about the number and size of the messages with a particular priority in the Queue.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| priority|x| queueName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnQueuePrioritiesTest() throws Exception {
        String msgVpnName = null;
        String queueName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnQueuePrioritiesResponse response = api.getMsgVpnQueuePriorities(msgVpnName, queueName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Queue Priority object.
     *
     * Get a Queue Priority object.  Queues can optionally support priority message delivery; all messages of a higher priority are delivered before any messages of a lower priority. A Priority object contains information about the number and size of the messages with a particular priority in the Queue.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| priority|x| queueName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnQueuePriorityTest() throws Exception {
        String msgVpnName = null;
        String queueName = null;
        String priority = null;
        List<String> select = null;
        MsgVpnQueuePriorityResponse response = api.getMsgVpnQueuePriority(msgVpnName, queueName, priority, select);

        // TODO: test validations
    }
    
    /**
     * Get a Queue Subscription object.
     *
     * Get a Queue Subscription object.  One or more Queue Subscriptions can be added to a durable queue so that Guaranteed messages published to matching topics are also delivered to and spooled by the queue.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| queueName|x| subscriptionTopic|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnQueueSubscriptionTest() throws Exception {
        String msgVpnName = null;
        String queueName = null;
        String subscriptionTopic = null;
        List<String> select = null;
        MsgVpnQueueSubscriptionResponse response = api.getMsgVpnQueueSubscription(msgVpnName, queueName, subscriptionTopic, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Queue Subscription objects.
     *
     * Get a list of Queue Subscription objects.  One or more Queue Subscriptions can be added to a durable queue so that Guaranteed messages published to matching topics are also delivered to and spooled by the queue.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| queueName|x| subscriptionTopic|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
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
        List<String> where = null;
        List<String> select = null;
        MsgVpnQueueSubscriptionsResponse response = api.getMsgVpnQueueSubscriptions(msgVpnName, queueName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Queue Template object.
     *
     * Get a Queue Template object.  A Queue Template provides a mechanism for specifying the initial state for client created queues.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| queueTemplateName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnQueueTemplateTest() throws Exception {
        String msgVpnName = null;
        String queueTemplateName = null;
        List<String> select = null;
        MsgVpnQueueTemplateResponse response = api.getMsgVpnQueueTemplate(msgVpnName, queueTemplateName, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Queue Template objects.
     *
     * Get a list of Queue Template objects.  A Queue Template provides a mechanism for specifying the initial state for client created queues.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| queueTemplateName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnQueueTemplatesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnQueueTemplatesResponse response = api.getMsgVpnQueueTemplates(msgVpnName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Queue Transmit Flow object.
     *
     * Get a Queue Transmit Flow object.  Queue Transmit Flows are used by clients to consume Guaranteed messages from a Queue.   Attribute|Identifying|Deprecated :---|:---:|:---: flowId|x| msgVpnName|x| queueName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnQueueTxFlowTest() throws Exception {
        String msgVpnName = null;
        String queueName = null;
        String flowId = null;
        List<String> select = null;
        MsgVpnQueueTxFlowResponse response = api.getMsgVpnQueueTxFlow(msgVpnName, queueName, flowId, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Queue Transmit Flow objects.
     *
     * Get a list of Queue Transmit Flow objects.  Queue Transmit Flows are used by clients to consume Guaranteed messages from a Queue.   Attribute|Identifying|Deprecated :---|:---:|:---: flowId|x| msgVpnName|x| queueName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnQueueTxFlowsTest() throws Exception {
        String msgVpnName = null;
        String queueName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnQueueTxFlowsResponse response = api.getMsgVpnQueueTxFlows(msgVpnName, queueName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Queue objects.
     *
     * Get a list of Queue objects.  A Queue acts as both a destination that clients can publish messages to, and as an endpoint that clients can bind consumers to and consume messages from.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| queueName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnQueuesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnQueuesResponse response = api.getMsgVpnQueues(msgVpnName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Replay Log object.
     *
     * Get a Replay Log object.  When the Message Replay feature is enabled, message brokers store persistent messages in a Replay Log. These messages are kept until the log is full, after which the oldest messages are removed to free up space for new messages.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| replayLogName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnReplayLogTest() throws Exception {
        String msgVpnName = null;
        String replayLogName = null;
        List<String> select = null;
        MsgVpnReplayLogResponse response = api.getMsgVpnReplayLog(msgVpnName, replayLogName, select);

        // TODO: test validations
    }
    
    /**
     * Get a Message object.
     *
     * Get a Message object.  A Message is a packet of information sent from producers to consumers. Messages are the central units of information that clients exchange using the message broker and which are cached in the Replay Log.   Attribute|Identifying|Deprecated :---|:---:|:---: msgId|x| msgVpnName|x| replayLogName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnReplayLogMsgTest() throws Exception {
        String msgVpnName = null;
        String replayLogName = null;
        String msgId = null;
        List<String> select = null;
        MsgVpnReplayLogMsgResponse response = api.getMsgVpnReplayLogMsg(msgVpnName, replayLogName, msgId, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Message objects.
     *
     * Get a list of Message objects.  A Message is a packet of information sent from producers to consumers. Messages are the central units of information that clients exchange using the message broker and which are cached in the Replay Log.   Attribute|Identifying|Deprecated :---|:---:|:---: msgId|x| msgVpnName|x| replayLogName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnReplayLogMsgsTest() throws Exception {
        String msgVpnName = null;
        String replayLogName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnReplayLogMsgsResponse response = api.getMsgVpnReplayLogMsgs(msgVpnName, replayLogName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Replay Log objects.
     *
     * Get a list of Replay Log objects.  When the Message Replay feature is enabled, message brokers store persistent messages in a Replay Log. These messages are kept until the log is full, after which the oldest messages are removed to free up space for new messages.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| replayLogName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnReplayLogsTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnReplayLogsResponse response = api.getMsgVpnReplayLogs(msgVpnName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Replicated Topic object.
     *
     * Get a Replicated Topic object.  To indicate which messages should be replicated between the active and standby site, a Replicated Topic subscription must be configured on a Message VPN. If a published message matches both a replicated topic and an endpoint on the active site, then the message is replicated to the standby site.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| replicatedTopic|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnReplicatedTopicTest() throws Exception {
        String msgVpnName = null;
        String replicatedTopic = null;
        List<String> select = null;
        MsgVpnReplicatedTopicResponse response = api.getMsgVpnReplicatedTopic(msgVpnName, replicatedTopic, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Replicated Topic objects.
     *
     * Get a list of Replicated Topic objects.  To indicate which messages should be replicated between the active and standby site, a Replicated Topic subscription must be configured on a Message VPN. If a published message matches both a replicated topic and an endpoint on the active site, then the message is replicated to the standby site.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| replicatedTopic|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnReplicatedTopicsTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnReplicatedTopicsResponse response = api.getMsgVpnReplicatedTopics(msgVpnName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a REST Delivery Point object.
     *
     * Get a REST Delivery Point object.  A REST Delivery Point manages delivery of messages from queues to a named list of REST Consumers.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| restDeliveryPointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnRestDeliveryPointTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointResponse response = api.getMsgVpnRestDeliveryPoint(msgVpnName, restDeliveryPointName, select);

        // TODO: test validations
    }
    
    /**
     * Get a Queue Binding object.
     *
     * Get a Queue Binding object.  A Queue Binding for a REST Delivery Point attracts messages to be delivered to REST consumers. If the queue does not exist it can be created subsequently, and once the queue is operational the broker performs the queue binding. Removing the queue binding does not delete the queue itself. Similarly, removing the queue does not remove the queue binding, which fails until the queue is recreated or the queue binding is deleted.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| queueBindingName|x| restDeliveryPointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnRestDeliveryPointQueueBindingTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String queueBindingName = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointQueueBindingResponse response = api.getMsgVpnRestDeliveryPointQueueBinding(msgVpnName, restDeliveryPointName, queueBindingName, select);

        // TODO: test validations
    }
    
    /**
     * Get a Request Header object.
     *
     * Get a Request Header object.  A request header to be added to the HTTP request.   Attribute|Identifying|Deprecated :---|:---:|:---: headerName|x| msgVpnName|x| queueBindingName|x| restDeliveryPointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.23.
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
        List<String> select = null;
        MsgVpnRestDeliveryPointQueueBindingRequestHeaderResponse response = api.getMsgVpnRestDeliveryPointQueueBindingRequestHeader(msgVpnName, restDeliveryPointName, queueBindingName, headerName, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Request Header objects.
     *
     * Get a list of Request Header objects.  A request header to be added to the HTTP request.   Attribute|Identifying|Deprecated :---|:---:|:---: headerName|x| msgVpnName|x| queueBindingName|x| restDeliveryPointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.23.
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
        List<String> where = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointQueueBindingRequestHeadersResponse response = api.getMsgVpnRestDeliveryPointQueueBindingRequestHeaders(msgVpnName, restDeliveryPointName, queueBindingName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Queue Binding objects.
     *
     * Get a list of Queue Binding objects.  A Queue Binding for a REST Delivery Point attracts messages to be delivered to REST consumers. If the queue does not exist it can be created subsequently, and once the queue is operational the broker performs the queue binding. Removing the queue binding does not delete the queue itself. Similarly, removing the queue does not remove the queue binding, which fails until the queue is recreated or the queue binding is deleted.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| queueBindingName|x| restDeliveryPointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
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
        List<String> where = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointQueueBindingsResponse response = api.getMsgVpnRestDeliveryPointQueueBindings(msgVpnName, restDeliveryPointName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a REST Consumer object.
     *
     * Get a REST Consumer object.  REST Consumer objects establish HTTP connectivity to REST consumer applications who wish to receive messages from a broker.   Attribute|Identifying|Deprecated :---|:---:|:---: counter.httpRequestConnectionCloseTxMsgCount||x counter.httpRequestOutstandingTxMsgCount||x counter.httpRequestTimedOutTxMsgCount||x counter.httpRequestTxByteCount||x counter.httpRequestTxMsgCount||x counter.httpResponseErrorRxMsgCount||x counter.httpResponseRxByteCount||x counter.httpResponseRxMsgCount||x counter.httpResponseSuccessRxMsgCount||x msgVpnName|x| restConsumerName|x| restDeliveryPointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnRestDeliveryPointRestConsumerTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String restConsumerName = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointRestConsumerResponse response = api.getMsgVpnRestDeliveryPointRestConsumer(msgVpnName, restDeliveryPointName, restConsumerName, select);

        // TODO: test validations
    }
    
    /**
     * Get a Claim object.
     *
     * Get a Claim object.  A Claim is added to the JWT sent to the OAuth token request endpoint.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| oauthJwtClaimName|x| restConsumerName|x| restDeliveryPointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.21.
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
        List<String> select = null;
        MsgVpnRestDeliveryPointRestConsumerOauthJwtClaimResponse response = api.getMsgVpnRestDeliveryPointRestConsumerOauthJwtClaim(msgVpnName, restDeliveryPointName, restConsumerName, oauthJwtClaimName, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Claim objects.
     *
     * Get a list of Claim objects.  A Claim is added to the JWT sent to the OAuth token request endpoint.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| oauthJwtClaimName|x| restConsumerName|x| restDeliveryPointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.21.
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
        List<String> where = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointRestConsumerOauthJwtClaimsResponse response = api.getMsgVpnRestDeliveryPointRestConsumerOauthJwtClaims(msgVpnName, restDeliveryPointName, restConsumerName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Trusted Common Name object.
     *
     * Get a Trusted Common Name object.  The Trusted Common Names for the REST Consumer are used by encrypted transports to verify the name in the certificate presented by the remote REST consumer. They must include the common name of the remote REST consumer&#39;s server certificate.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x|x restConsumerName|x|x restDeliveryPointName|x|x tlsTrustedCommonName|x|x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.17. Common Name validation has been replaced by Server Certificate Name validation.
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
        List<String> select = null;
        MsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNameResponse response = api.getMsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonName(msgVpnName, restDeliveryPointName, restConsumerName, tlsTrustedCommonName, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Trusted Common Name objects.
     *
     * Get a list of Trusted Common Name objects.  The Trusted Common Names for the REST Consumer are used by encrypted transports to verify the name in the certificate presented by the remote REST consumer. They must include the common name of the remote REST consumer&#39;s server certificate.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x|x restConsumerName|x|x restDeliveryPointName|x|x tlsTrustedCommonName|x|x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.17. Common Name validation has been replaced by Server Certificate Name validation.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNamesTest() throws Exception {
        String msgVpnName = null;
        String restDeliveryPointName = null;
        String restConsumerName = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNamesResponse response = api.getMsgVpnRestDeliveryPointRestConsumerTlsTrustedCommonNames(msgVpnName, restDeliveryPointName, restConsumerName, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of REST Consumer objects.
     *
     * Get a list of REST Consumer objects.  REST Consumer objects establish HTTP connectivity to REST consumer applications who wish to receive messages from a broker.   Attribute|Identifying|Deprecated :---|:---:|:---: counter.httpRequestConnectionCloseTxMsgCount||x counter.httpRequestOutstandingTxMsgCount||x counter.httpRequestTimedOutTxMsgCount||x counter.httpRequestTxByteCount||x counter.httpRequestTxMsgCount||x counter.httpResponseErrorRxMsgCount||x counter.httpResponseRxByteCount||x counter.httpResponseRxMsgCount||x counter.httpResponseSuccessRxMsgCount||x msgVpnName|x| restConsumerName|x| restDeliveryPointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
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
        List<String> where = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointRestConsumersResponse response = api.getMsgVpnRestDeliveryPointRestConsumers(msgVpnName, restDeliveryPointName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of REST Delivery Point objects.
     *
     * Get a list of REST Delivery Point objects.  A REST Delivery Point manages delivery of messages from queues to a named list of REST Consumers.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| restDeliveryPointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnRestDeliveryPointsTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnRestDeliveryPointsResponse response = api.getMsgVpnRestDeliveryPoints(msgVpnName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Topic Endpoint object.
     *
     * Get a Topic Endpoint object.  A Topic Endpoint attracts messages published to a topic for which the Topic Endpoint has a matching topic subscription. The topic subscription for the Topic Endpoint is specified in the client request to bind a Flow to that Topic Endpoint. Queues are significantly more flexible than Topic Endpoints and are the recommended approach for most applications. The use of Topic Endpoints should be restricted to JMS applications.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| topicEndpointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnTopicEndpointTest() throws Exception {
        String msgVpnName = null;
        String topicEndpointName = null;
        List<String> select = null;
        MsgVpnTopicEndpointResponse response = api.getMsgVpnTopicEndpoint(msgVpnName, topicEndpointName, select);

        // TODO: test validations
    }
    
    /**
     * Get a Topic Endpoint Message object.
     *
     * Get a Topic Endpoint Message object.  A Topic Endpoint Message is a packet of information sent from producers to consumers using the Topic Endpoint.   Attribute|Identifying|Deprecated :---|:---:|:---: msgId|x| msgVpnName|x| topicEndpointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnTopicEndpointMsgTest() throws Exception {
        String msgVpnName = null;
        String topicEndpointName = null;
        String msgId = null;
        List<String> select = null;
        MsgVpnTopicEndpointMsgResponse response = api.getMsgVpnTopicEndpointMsg(msgVpnName, topicEndpointName, msgId, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Topic Endpoint Message objects.
     *
     * Get a list of Topic Endpoint Message objects.  A Topic Endpoint Message is a packet of information sent from producers to consumers using the Topic Endpoint.   Attribute|Identifying|Deprecated :---|:---:|:---: msgId|x| msgVpnName|x| topicEndpointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnTopicEndpointMsgsTest() throws Exception {
        String msgVpnName = null;
        String topicEndpointName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnTopicEndpointMsgsResponse response = api.getMsgVpnTopicEndpointMsgs(msgVpnName, topicEndpointName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Topic Endpoint Priority objects.
     *
     * Get a list of Topic Endpoint Priority objects.  Topic Endpoints can optionally support priority message delivery; all messages of a higher priority are delivered before any messages of a lower priority. A Priority object contains information about the number and size of the messages with a particular priority in the Topic Endpoint.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| priority|x| topicEndpointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnTopicEndpointPrioritiesTest() throws Exception {
        String msgVpnName = null;
        String topicEndpointName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnTopicEndpointPrioritiesResponse response = api.getMsgVpnTopicEndpointPriorities(msgVpnName, topicEndpointName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Topic Endpoint Priority object.
     *
     * Get a Topic Endpoint Priority object.  Topic Endpoints can optionally support priority message delivery; all messages of a higher priority are delivered before any messages of a lower priority. A Priority object contains information about the number and size of the messages with a particular priority in the Topic Endpoint.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| priority|x| topicEndpointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnTopicEndpointPriorityTest() throws Exception {
        String msgVpnName = null;
        String topicEndpointName = null;
        String priority = null;
        List<String> select = null;
        MsgVpnTopicEndpointPriorityResponse response = api.getMsgVpnTopicEndpointPriority(msgVpnName, topicEndpointName, priority, select);

        // TODO: test validations
    }
    
    /**
     * Get a Topic Endpoint Template object.
     *
     * Get a Topic Endpoint Template object.  A Topic Endpoint Template provides a mechanism for specifying the initial state for client created topic endpoints.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| topicEndpointTemplateName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnTopicEndpointTemplateTest() throws Exception {
        String msgVpnName = null;
        String topicEndpointTemplateName = null;
        List<String> select = null;
        MsgVpnTopicEndpointTemplateResponse response = api.getMsgVpnTopicEndpointTemplate(msgVpnName, topicEndpointTemplateName, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Topic Endpoint Template objects.
     *
     * Get a list of Topic Endpoint Template objects.  A Topic Endpoint Template provides a mechanism for specifying the initial state for client created topic endpoints.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| topicEndpointTemplateName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.14.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnTopicEndpointTemplatesTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnTopicEndpointTemplatesResponse response = api.getMsgVpnTopicEndpointTemplates(msgVpnName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Topic Endpoint Transmit Flow object.
     *
     * Get a Topic Endpoint Transmit Flow object.  Topic Endpoint Transmit Flows are used by clients to consume Guaranteed messages from a Topic Endpoint.   Attribute|Identifying|Deprecated :---|:---:|:---: flowId|x| msgVpnName|x| topicEndpointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnTopicEndpointTxFlowTest() throws Exception {
        String msgVpnName = null;
        String topicEndpointName = null;
        String flowId = null;
        List<String> select = null;
        MsgVpnTopicEndpointTxFlowResponse response = api.getMsgVpnTopicEndpointTxFlow(msgVpnName, topicEndpointName, flowId, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Topic Endpoint Transmit Flow objects.
     *
     * Get a list of Topic Endpoint Transmit Flow objects.  Topic Endpoint Transmit Flows are used by clients to consume Guaranteed messages from a Topic Endpoint.   Attribute|Identifying|Deprecated :---|:---:|:---: flowId|x| msgVpnName|x| topicEndpointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnTopicEndpointTxFlowsTest() throws Exception {
        String msgVpnName = null;
        String topicEndpointName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnTopicEndpointTxFlowsResponse response = api.getMsgVpnTopicEndpointTxFlows(msgVpnName, topicEndpointName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Topic Endpoint objects.
     *
     * Get a list of Topic Endpoint objects.  A Topic Endpoint attracts messages published to a topic for which the Topic Endpoint has a matching topic subscription. The topic subscription for the Topic Endpoint is specified in the client request to bind a Flow to that Topic Endpoint. Queues are significantly more flexible than Topic Endpoints and are the recommended approach for most applications. The use of Topic Endpoints should be restricted to JMS applications.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| topicEndpointName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnTopicEndpointsTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnTopicEndpointsResponse response = api.getMsgVpnTopicEndpoints(msgVpnName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Replicated Local Transaction or XA Transaction object.
     *
     * Get a Replicated Local Transaction or XA Transaction object.  Transactions can be used to group a set of Guaranteed messages to be published or consumed or both as an atomic unit of work.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| xid|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnTransactionTest() throws Exception {
        String msgVpnName = null;
        String xid = null;
        List<String> select = null;
        MsgVpnTransactionResponse response = api.getMsgVpnTransaction(msgVpnName, xid, select);

        // TODO: test validations
    }
    
    /**
     * Get a Transaction Consumer Message object.
     *
     * Get a Transaction Consumer Message object.  A Transaction Consumer Message is a message that will be consumed as part of this Transaction once the Transaction is committed.   Attribute|Identifying|Deprecated :---|:---:|:---: msgId|x| msgVpnName|x| xid|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnTransactionConsumerMsgTest() throws Exception {
        String msgVpnName = null;
        String xid = null;
        String msgId = null;
        List<String> select = null;
        MsgVpnTransactionConsumerMsgResponse response = api.getMsgVpnTransactionConsumerMsg(msgVpnName, xid, msgId, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Transaction Consumer Message objects.
     *
     * Get a list of Transaction Consumer Message objects.  A Transaction Consumer Message is a message that will be consumed as part of this Transaction once the Transaction is committed.   Attribute|Identifying|Deprecated :---|:---:|:---: msgId|x| msgVpnName|x| xid|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnTransactionConsumerMsgsTest() throws Exception {
        String msgVpnName = null;
        String xid = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnTransactionConsumerMsgsResponse response = api.getMsgVpnTransactionConsumerMsgs(msgVpnName, xid, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Transaction Publisher Message object.
     *
     * Get a Transaction Publisher Message object.  A Transaction Publisher Message is a message that will be published as part of this Transaction once the Transaction is committed.   Attribute|Identifying|Deprecated :---|:---:|:---: msgId|x| msgVpnName|x| xid|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnTransactionPublisherMsgTest() throws Exception {
        String msgVpnName = null;
        String xid = null;
        String msgId = null;
        List<String> select = null;
        MsgVpnTransactionPublisherMsgResponse response = api.getMsgVpnTransactionPublisherMsg(msgVpnName, xid, msgId, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Transaction Publisher Message objects.
     *
     * Get a list of Transaction Publisher Message objects.  A Transaction Publisher Message is a message that will be published as part of this Transaction once the Transaction is committed.   Attribute|Identifying|Deprecated :---|:---:|:---: msgId|x| msgVpnName|x| xid|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnTransactionPublisherMsgsTest() throws Exception {
        String msgVpnName = null;
        String xid = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnTransactionPublisherMsgsResponse response = api.getMsgVpnTransactionPublisherMsgs(msgVpnName, xid, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Replicated Local Transaction or XA Transaction objects.
     *
     * Get a list of Replicated Local Transaction or XA Transaction objects.  Transactions can be used to group a set of Guaranteed messages to be published or consumed or both as an atomic unit of work.   Attribute|Identifying|Deprecated :---|:---:|:---: msgVpnName|x| xid|x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.12.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnTransactionsTest() throws Exception {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnTransactionsResponse response = api.getMsgVpnTransactions(msgVpnName, count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Message VPN objects.
     *
     * Get a list of Message VPN objects.  Message VPNs (Virtual Private Networks) allow for the segregation of topic space and clients. They also group clients connecting to a network of message brokers, such that messages published within a particular group are only visible to that group&#39;s clients.   Attribute|Identifying|Deprecated :---|:---:|:---: bridgingTlsServerCertEnforceTrustedCommonNameEnabled||x configSyncLocalKey||x configSyncLocalLastResult||x configSyncLocalRole||x configSyncLocalState||x configSyncLocalTimeInState||x counter.controlRxByteCount||x counter.controlRxMsgCount||x counter.controlTxByteCount||x counter.controlTxMsgCount||x counter.dataRxByteCount||x counter.dataRxMsgCount||x counter.dataTxByteCount||x counter.dataTxMsgCount||x counter.discardedRxMsgCount||x counter.discardedTxMsgCount||x counter.loginRxMsgCount||x counter.loginTxMsgCount||x counter.msgSpoolRxMsgCount||x counter.msgSpoolTxMsgCount||x counter.tlsRxByteCount||x counter.tlsTxByteCount||x msgVpnName|x| rate.averageRxByteRate||x rate.averageRxMsgRate||x rate.averageTxByteRate||x rate.averageTxMsgRate||x rate.rxByteRate||x rate.rxMsgRate||x rate.tlsAverageRxByteRate||x rate.tlsAverageTxByteRate||x rate.tlsRxByteRate||x rate.tlsTxByteRate||x rate.txByteRate||x rate.txMsgRate||x restTlsServerCertEnforceTrustedCommonNameEnabled||x    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been available since 2.11.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnsTest() throws Exception {
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnsResponse response = api.getMsgVpns(count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Session object.
     *
     * Get a Session object.  Administrative sessions for configuration and monitoring.   Attribute|Identifying|Deprecated :---|:---:|:---: sessionId|x| sessionUsername|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.21.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getSessionTest() throws Exception {
        String sessionUsername = null;
        String sessionId = null;
        List<String> select = null;
        SessionResponse response = api.getSession(sessionUsername, sessionId, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Session objects.
     *
     * Get a list of Session objects.  Administrative sessions for configuration and monitoring.   Attribute|Identifying|Deprecated :---|:---:|:---: sessionId|x| sessionUsername|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.21.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getSessionsTest() throws Exception {
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        SessionsResponse response = api.getSessions(count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Standard Domain Certificate Authority objects.
     *
     * Get a list of Standard Domain Certificate Authority objects.  Standard Certificate Authorities trusted for domain verification.   Attribute|Identifying|Deprecated :---|:---:|:---: certAuthorityName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.19.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getStandardDomainCertAuthoritiesTest() throws Exception {
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        StandardDomainCertAuthoritiesResponse response = api.getStandardDomainCertAuthorities(count, cursor, where, select);

        // TODO: test validations
    }
    
    /**
     * Get a Standard Domain Certificate Authority object.
     *
     * Get a Standard Domain Certificate Authority object.  Standard Certificate Authorities trusted for domain verification.   Attribute|Identifying|Deprecated :---|:---:|:---: certAuthorityName|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.19.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getStandardDomainCertAuthorityTest() throws Exception {
        String certAuthorityName = null;
        List<String> select = null;
        StandardDomainCertAuthorityResponse response = api.getStandardDomainCertAuthority(certAuthorityName, select);

        // TODO: test validations
    }
    
    /**
     * Get a Virtual Hostname object.
     *
     * Get a Virtual Hostname object.  A Virtual Hostname is a provisioned object on a message broker that contains a Virtual Hostname to Message VPN mapping.  Clients which connect to a global (as opposed to per Message VPN) port and provides this hostname will be directed to its corresponding Message VPN. A case-insentive match is performed on the full client-provided hostname against the configured virtual-hostname.  This mechanism is only supported for hostnames provided through the Server Name Indication (SNI) extension of TLS.   Attribute|Identifying|Deprecated :---|:---:|:---: virtualHostname|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.17.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getVirtualHostnameTest() throws Exception {
        String virtualHostname = null;
        List<String> select = null;
        VirtualHostnameResponse response = api.getVirtualHostname(virtualHostname, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of Virtual Hostname objects.
     *
     * Get a list of Virtual Hostname objects.  A Virtual Hostname is a provisioned object on a message broker that contains a Virtual Hostname to Message VPN mapping.  Clients which connect to a global (as opposed to per Message VPN) port and provides this hostname will be directed to its corresponding Message VPN. A case-insentive match is performed on the full client-provided hostname against the configured virtual-hostname.  This mechanism is only supported for hostnames provided through the Server Name Indication (SNI) extension of TLS.   Attribute|Identifying|Deprecated :---|:---:|:---: virtualHostname|x|    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.17.
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getVirtualHostnamesTest() throws Exception {
        Integer count = null;
        String cursor = null;
        List<String> where = null;
        List<String> select = null;
        VirtualHostnamesResponse response = api.getVirtualHostnames(count, cursor, where, select);

        // TODO: test validations
    }
    
}
