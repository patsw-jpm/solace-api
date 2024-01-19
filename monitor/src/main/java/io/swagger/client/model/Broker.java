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


package io.swagger.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.client.model.EventThreshold;
import io.swagger.client.model.EventThresholdByPercent;
import java.io.IOException;

/**
 * Broker
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2024-01-19T20:58:30.360Z")
public class Broker {
  /**
   * The client certificate revocation checking mode used when a client authenticates with a client certificate. The allowed values and their meaning are:  &lt;pre&gt; \&quot;none\&quot; - Do not perform any certificate revocation checking. \&quot;ocsp\&quot; - Use the Open Certificate Status Protcol (OCSP) for certificate revocation checking. \&quot;crl\&quot; - Use Certificate Revocation Lists (CRL) for certificate revocation checking. \&quot;ocsp-crl\&quot; - Use OCSP first, but if OCSP fails to return an unambiguous result, then check via CRL. &lt;/pre&gt; 
   */
  @JsonAdapter(AuthClientCertRevocationCheckModeEnum.Adapter.class)
  public enum AuthClientCertRevocationCheckModeEnum {
    NONE("none"),
    
    OCSP("ocsp"),
    
    CRL("crl"),
    
    OCSP_CRL("ocsp-crl");

    private String value;

    AuthClientCertRevocationCheckModeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static AuthClientCertRevocationCheckModeEnum fromValue(String text) {
      for (AuthClientCertRevocationCheckModeEnum b : AuthClientCertRevocationCheckModeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<AuthClientCertRevocationCheckModeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final AuthClientCertRevocationCheckModeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public AuthClientCertRevocationCheckModeEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return AuthClientCertRevocationCheckModeEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("authClientCertRevocationCheckMode")
  private AuthClientCertRevocationCheckModeEnum authClientCertRevocationCheckMode = null;

  @SerializedName("averageRxByteRate")
  private Long averageRxByteRate = null;

  @SerializedName("averageRxCompressedByteRate")
  private Long averageRxCompressedByteRate = null;

  @SerializedName("averageRxMsgRate")
  private Long averageRxMsgRate = null;

  @SerializedName("averageRxUncompressedByteRate")
  private Long averageRxUncompressedByteRate = null;

  @SerializedName("averageTxByteRate")
  private Long averageTxByteRate = null;

  @SerializedName("averageTxCompressedByteRate")
  private Long averageTxCompressedByteRate = null;

  @SerializedName("averageTxMsgRate")
  private Long averageTxMsgRate = null;

  @SerializedName("averageTxUncompressedByteRate")
  private Long averageTxUncompressedByteRate = null;

  @SerializedName("configSyncAuthenticationClientCertMaxChainDepth")
  private Long configSyncAuthenticationClientCertMaxChainDepth = null;

  @SerializedName("configSyncAuthenticationClientCertValidateDateEnabled")
  private Boolean configSyncAuthenticationClientCertValidateDateEnabled = null;

  @SerializedName("configSyncClientProfileTcpInitialCongestionWindow")
  private Long configSyncClientProfileTcpInitialCongestionWindow = null;

  @SerializedName("configSyncClientProfileTcpKeepaliveCount")
  private Long configSyncClientProfileTcpKeepaliveCount = null;

  @SerializedName("configSyncClientProfileTcpKeepaliveIdle")
  private Long configSyncClientProfileTcpKeepaliveIdle = null;

  @SerializedName("configSyncClientProfileTcpKeepaliveInterval")
  private Long configSyncClientProfileTcpKeepaliveInterval = null;

  @SerializedName("configSyncClientProfileTcpMaxWindow")
  private Long configSyncClientProfileTcpMaxWindow = null;

  @SerializedName("configSyncClientProfileTcpMss")
  private Long configSyncClientProfileTcpMss = null;

  @SerializedName("configSyncEnabled")
  private Boolean configSyncEnabled = null;

  @SerializedName("configSyncLastFailureReason")
  private String configSyncLastFailureReason = null;

  @SerializedName("configSyncSynchronizeUsernameEnabled")
  private Boolean configSyncSynchronizeUsernameEnabled = null;

  @SerializedName("configSyncTlsEnabled")
  private Boolean configSyncTlsEnabled = null;

  @SerializedName("configSyncUp")
  private Boolean configSyncUp = null;

  @SerializedName("cspfVersion")
  private Integer cspfVersion = null;

  @SerializedName("guaranteedMsgingDefragmentationEstimatedFragmentation")
  private Long guaranteedMsgingDefragmentationEstimatedFragmentation = null;

  @SerializedName("guaranteedMsgingDefragmentationEstimatedRecoverableSpace")
  private Integer guaranteedMsgingDefragmentationEstimatedRecoverableSpace = null;

  @SerializedName("guaranteedMsgingDefragmentationLastCompletedOn")
  private Integer guaranteedMsgingDefragmentationLastCompletedOn = null;

  @SerializedName("guaranteedMsgingDefragmentationLastCompletionPercentage")
  private Long guaranteedMsgingDefragmentationLastCompletionPercentage = null;

  @SerializedName("guaranteedMsgingDefragmentationLastExitCondition")
  private String guaranteedMsgingDefragmentationLastExitCondition = null;

  @SerializedName("guaranteedMsgingDefragmentationLastExitConditionInformation")
  private String guaranteedMsgingDefragmentationLastExitConditionInformation = null;

  @SerializedName("guaranteedMsgingDefragmentationStatus")
  private String guaranteedMsgingDefragmentationStatus = null;

  @SerializedName("guaranteedMsgingDefragmentationStatusActiveCompletionPercentage")
  private Long guaranteedMsgingDefragmentationStatusActiveCompletionPercentage = null;

  @SerializedName("guaranteedMsgingDiskArrayWwn")
  private String guaranteedMsgingDiskArrayWwn = null;

  /**
   * The disk location for the the guaranteed message spool (required for high availability with guaranteed messaging). When external is chosen the guaranteed message spool is stored on an external disk array attached to the router. If internal storage is currently used, changing to external causes message spooling on the router to stop and messages spooled on the internal storage to be deleted. If internal is chosen the guaranteed message spool is stored on an external disk array attached to the router. If internal storage is currently used, changing to external causes message spooling on the router to stop and messages spooled on the internal storage to be deleted. The allowed values and their meaning are:  &lt;pre&gt; \&quot;external\&quot; - The guaranteed message spool is stored on an external disk array attached to the appliance. \&quot;internal\&quot; - The guaranteed message spool is stored internally on the appliance. &lt;/pre&gt;  Available since 2.18.
   */
  @JsonAdapter(GuaranteedMsgingDiskLocationEnum.Adapter.class)
  public enum GuaranteedMsgingDiskLocationEnum {
    EXTERNAL("external"),
    
    INTERNAL("internal");

    private String value;

    GuaranteedMsgingDiskLocationEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static GuaranteedMsgingDiskLocationEnum fromValue(String text) {
      for (GuaranteedMsgingDiskLocationEnum b : GuaranteedMsgingDiskLocationEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<GuaranteedMsgingDiskLocationEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final GuaranteedMsgingDiskLocationEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public GuaranteedMsgingDiskLocationEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return GuaranteedMsgingDiskLocationEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("guaranteedMsgingDiskLocation")
  private GuaranteedMsgingDiskLocationEnum guaranteedMsgingDiskLocation = null;

  @SerializedName("guaranteedMsgingEnabled")
  private Boolean guaranteedMsgingEnabled = null;

  @SerializedName("guaranteedMsgingEventCacheUsageThreshold")
  private EventThreshold guaranteedMsgingEventCacheUsageThreshold = null;

  @SerializedName("guaranteedMsgingEventDeliveredUnackedThreshold")
  private EventThresholdByPercent guaranteedMsgingEventDeliveredUnackedThreshold = null;

  @SerializedName("guaranteedMsgingEventDiskUsageThreshold")
  private EventThresholdByPercent guaranteedMsgingEventDiskUsageThreshold = null;

  @SerializedName("guaranteedMsgingEventEgressFlowCountThreshold")
  private EventThreshold guaranteedMsgingEventEgressFlowCountThreshold = null;

  @SerializedName("guaranteedMsgingEventEndpointCountThreshold")
  private EventThreshold guaranteedMsgingEventEndpointCountThreshold = null;

  @SerializedName("guaranteedMsgingEventIngressFlowCountThreshold")
  private EventThreshold guaranteedMsgingEventIngressFlowCountThreshold = null;

  @SerializedName("guaranteedMsgingEventMsgCountThreshold")
  private EventThresholdByPercent guaranteedMsgingEventMsgCountThreshold = null;

  @SerializedName("guaranteedMsgingEventMsgSpoolFileCountThreshold")
  private EventThresholdByPercent guaranteedMsgingEventMsgSpoolFileCountThreshold = null;

  @SerializedName("guaranteedMsgingEventMsgSpoolUsageThreshold")
  private EventThreshold guaranteedMsgingEventMsgSpoolUsageThreshold = null;

  @SerializedName("guaranteedMsgingEventTransactedSessionCountThreshold")
  private EventThreshold guaranteedMsgingEventTransactedSessionCountThreshold = null;

  @SerializedName("guaranteedMsgingEventTransactedSessionResourceCountThreshold")
  private EventThresholdByPercent guaranteedMsgingEventTransactedSessionResourceCountThreshold = null;

  @SerializedName("guaranteedMsgingEventTransactionCountThreshold")
  private EventThreshold guaranteedMsgingEventTransactionCountThreshold = null;

  @SerializedName("guaranteedMsgingMaxCacheUsage")
  private Integer guaranteedMsgingMaxCacheUsage = null;

  @SerializedName("guaranteedMsgingMaxMsgSpoolUsage")
  private Long guaranteedMsgingMaxMsgSpoolUsage = null;

  @SerializedName("guaranteedMsgingOperationalStatus")
  private String guaranteedMsgingOperationalStatus = null;

  /**
   * The replication compatibility mode for the router. The default value is &#x60;\&quot;legacy\&quot;&#x60;. The allowed values and their meaning are:\&quot;legacy\&quot; - All transactions originated by clients are replicated to the standby site without using transactions.\&quot;transacted\&quot; - All transactions originated by clients are replicated to the standby site using transactions. The allowed values and their meaning are:  &lt;pre&gt; \&quot;legacy\&quot; - All transactions originated by clients are replicated to the standby site without using transactions. \&quot;transacted\&quot; - All transactions originated by clients are replicated to the standby site using transactions. &lt;/pre&gt;  Available since 2.18.
   */
  @JsonAdapter(GuaranteedMsgingTransactionReplicationCompatibilityModeEnum.Adapter.class)
  public enum GuaranteedMsgingTransactionReplicationCompatibilityModeEnum {
    LEGACY("legacy"),
    
    TRANSACTED("transacted");

    private String value;

    GuaranteedMsgingTransactionReplicationCompatibilityModeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static GuaranteedMsgingTransactionReplicationCompatibilityModeEnum fromValue(String text) {
      for (GuaranteedMsgingTransactionReplicationCompatibilityModeEnum b : GuaranteedMsgingTransactionReplicationCompatibilityModeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<GuaranteedMsgingTransactionReplicationCompatibilityModeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final GuaranteedMsgingTransactionReplicationCompatibilityModeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public GuaranteedMsgingTransactionReplicationCompatibilityModeEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return GuaranteedMsgingTransactionReplicationCompatibilityModeEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("guaranteedMsgingTransactionReplicationCompatibilityMode")
  private GuaranteedMsgingTransactionReplicationCompatibilityModeEnum guaranteedMsgingTransactionReplicationCompatibilityMode = null;

  /**
   * The High Availability role for this broker if using the legacy Active/Active configuration for high availability (not recommended). Note: for Active/Standby high availability configuration, this setting is ignored. The allowed values and their meaning are:  &lt;pre&gt; \&quot;primary\&quot; - The primary virtual router. \&quot;backup\&quot; - The backup virtual router. &lt;/pre&gt;  Available since 2.18.
   */
  @JsonAdapter(GuaranteedMsgingVirtualRouterWhenActiveActiveEnum.Adapter.class)
  public enum GuaranteedMsgingVirtualRouterWhenActiveActiveEnum {
    PRIMARY("primary"),
    
    BACKUP("backup");

    private String value;

    GuaranteedMsgingVirtualRouterWhenActiveActiveEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static GuaranteedMsgingVirtualRouterWhenActiveActiveEnum fromValue(String text) {
      for (GuaranteedMsgingVirtualRouterWhenActiveActiveEnum b : GuaranteedMsgingVirtualRouterWhenActiveActiveEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<GuaranteedMsgingVirtualRouterWhenActiveActiveEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final GuaranteedMsgingVirtualRouterWhenActiveActiveEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public GuaranteedMsgingVirtualRouterWhenActiveActiveEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return GuaranteedMsgingVirtualRouterWhenActiveActiveEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("guaranteedMsgingVirtualRouterWhenActiveActive")
  private GuaranteedMsgingVirtualRouterWhenActiveActiveEnum guaranteedMsgingVirtualRouterWhenActiveActive = null;

  @SerializedName("rxByteCount")
  private Long rxByteCount = null;

  @SerializedName("rxByteRate")
  private Long rxByteRate = null;

  @SerializedName("rxCompressedByteCount")
  private Long rxCompressedByteCount = null;

  @SerializedName("rxCompressedByteRate")
  private Long rxCompressedByteRate = null;

  @SerializedName("rxCompressionRatio")
  private String rxCompressionRatio = null;

  @SerializedName("rxMsgCount")
  private Long rxMsgCount = null;

  @SerializedName("rxMsgRate")
  private Long rxMsgRate = null;

  @SerializedName("rxUncompressedByteCount")
  private Long rxUncompressedByteCount = null;

  @SerializedName("rxUncompressedByteRate")
  private Long rxUncompressedByteRate = null;

  @SerializedName("serviceAmqpEnabled")
  private Boolean serviceAmqpEnabled = null;

  @SerializedName("serviceAmqpTlsListenPort")
  private Long serviceAmqpTlsListenPort = null;

  @SerializedName("serviceEventConnectionCountThreshold")
  private EventThreshold serviceEventConnectionCountThreshold = null;

  @SerializedName("serviceHealthCheckEnabled")
  private Boolean serviceHealthCheckEnabled = null;

  @SerializedName("serviceHealthCheckListenPort")
  private Long serviceHealthCheckListenPort = null;

  @SerializedName("serviceMqttEnabled")
  private Boolean serviceMqttEnabled = null;

  @SerializedName("serviceMsgBackboneEnabled")
  private Boolean serviceMsgBackboneEnabled = null;

  @SerializedName("serviceRestEventOutgoingConnectionCountThreshold")
  private EventThreshold serviceRestEventOutgoingConnectionCountThreshold = null;

  @SerializedName("serviceRestIncomingEnabled")
  private Boolean serviceRestIncomingEnabled = null;

  @SerializedName("serviceRestOutgoingEnabled")
  private Boolean serviceRestOutgoingEnabled = null;

  @SerializedName("serviceSempLegacyTimeoutEnabled")
  private Boolean serviceSempLegacyTimeoutEnabled = null;

  @SerializedName("serviceSempPlainTextEnabled")
  private Boolean serviceSempPlainTextEnabled = null;

  @SerializedName("serviceSempPlainTextListenPort")
  private Long serviceSempPlainTextListenPort = null;

  @SerializedName("serviceSempSessionIdleTimeout")
  private Integer serviceSempSessionIdleTimeout = null;

  @SerializedName("serviceSempSessionMaxLifetime")
  private Integer serviceSempSessionMaxLifetime = null;

  @SerializedName("serviceSempTlsEnabled")
  private Boolean serviceSempTlsEnabled = null;

  @SerializedName("serviceSempTlsListenPort")
  private Long serviceSempTlsListenPort = null;

  @SerializedName("serviceSmfCompressionListenPort")
  private Long serviceSmfCompressionListenPort = null;

  @SerializedName("serviceSmfEnabled")
  private Boolean serviceSmfEnabled = null;

  @SerializedName("serviceSmfEventConnectionCountThreshold")
  private EventThreshold serviceSmfEventConnectionCountThreshold = null;

  @SerializedName("serviceSmfPlainTextListenPort")
  private Long serviceSmfPlainTextListenPort = null;

  @SerializedName("serviceSmfRoutingControlListenPort")
  private Long serviceSmfRoutingControlListenPort = null;

  @SerializedName("serviceSmfTlsListenPort")
  private Long serviceSmfTlsListenPort = null;

  @SerializedName("serviceTlsEventConnectionCountThreshold")
  private EventThreshold serviceTlsEventConnectionCountThreshold = null;

  @SerializedName("serviceWebTransportEnabled")
  private Boolean serviceWebTransportEnabled = null;

  @SerializedName("serviceWebTransportPlainTextListenPort")
  private Long serviceWebTransportPlainTextListenPort = null;

  @SerializedName("serviceWebTransportTlsListenPort")
  private Long serviceWebTransportTlsListenPort = null;

  @SerializedName("serviceWebTransportWebUrlSuffix")
  private String serviceWebTransportWebUrlSuffix = null;

  @SerializedName("tlsBlockVersion10Enabled")
  private Boolean tlsBlockVersion10Enabled = null;

  @SerializedName("tlsBlockVersion11Enabled")
  private Boolean tlsBlockVersion11Enabled = null;

  @SerializedName("tlsCipherSuiteManagementDefaultList")
  private String tlsCipherSuiteManagementDefaultList = null;

  @SerializedName("tlsCipherSuiteManagementList")
  private String tlsCipherSuiteManagementList = null;

  @SerializedName("tlsCipherSuiteManagementSupportedList")
  private String tlsCipherSuiteManagementSupportedList = null;

  @SerializedName("tlsCipherSuiteMsgBackboneDefaultList")
  private String tlsCipherSuiteMsgBackboneDefaultList = null;

  @SerializedName("tlsCipherSuiteMsgBackboneList")
  private String tlsCipherSuiteMsgBackboneList = null;

  @SerializedName("tlsCipherSuiteMsgBackboneSupportedList")
  private String tlsCipherSuiteMsgBackboneSupportedList = null;

  @SerializedName("tlsCipherSuiteSecureShellDefaultList")
  private String tlsCipherSuiteSecureShellDefaultList = null;

  @SerializedName("tlsCipherSuiteSecureShellList")
  private String tlsCipherSuiteSecureShellList = null;

  @SerializedName("tlsCipherSuiteSecureShellSupportedList")
  private String tlsCipherSuiteSecureShellSupportedList = null;

  @SerializedName("tlsCrimeExploitProtectionEnabled")
  private Boolean tlsCrimeExploitProtectionEnabled = null;

  @SerializedName("tlsStandardDomainCertificateAuthoritiesEnabled")
  private Boolean tlsStandardDomainCertificateAuthoritiesEnabled = null;

  @SerializedName("tlsTicketLifetime")
  private Integer tlsTicketLifetime = null;

  @SerializedName("tlsVersionSupportedList")
  private String tlsVersionSupportedList = null;

  @SerializedName("txByteCount")
  private Long txByteCount = null;

  @SerializedName("txByteRate")
  private Long txByteRate = null;

  @SerializedName("txCompressedByteCount")
  private Long txCompressedByteCount = null;

  @SerializedName("txCompressedByteRate")
  private Long txCompressedByteRate = null;

  @SerializedName("txCompressionRatio")
  private String txCompressionRatio = null;

  @SerializedName("txMsgCount")
  private Long txMsgCount = null;

  @SerializedName("txMsgRate")
  private Long txMsgRate = null;

  @SerializedName("txUncompressedByteCount")
  private Long txUncompressedByteCount = null;

  @SerializedName("txUncompressedByteRate")
  private Long txUncompressedByteRate = null;

  public Broker authClientCertRevocationCheckMode(AuthClientCertRevocationCheckModeEnum authClientCertRevocationCheckMode) {
    this.authClientCertRevocationCheckMode = authClientCertRevocationCheckMode;
    return this;
  }

   /**
   * The client certificate revocation checking mode used when a client authenticates with a client certificate. The allowed values and their meaning are:  &lt;pre&gt; \&quot;none\&quot; - Do not perform any certificate revocation checking. \&quot;ocsp\&quot; - Use the Open Certificate Status Protcol (OCSP) for certificate revocation checking. \&quot;crl\&quot; - Use Certificate Revocation Lists (CRL) for certificate revocation checking. \&quot;ocsp-crl\&quot; - Use OCSP first, but if OCSP fails to return an unambiguous result, then check via CRL. &lt;/pre&gt; 
   * @return authClientCertRevocationCheckMode
  **/
  @ApiModelProperty(value = "The client certificate revocation checking mode used when a client authenticates with a client certificate. The allowed values and their meaning are:  <pre> \"none\" - Do not perform any certificate revocation checking. \"ocsp\" - Use the Open Certificate Status Protcol (OCSP) for certificate revocation checking. \"crl\" - Use Certificate Revocation Lists (CRL) for certificate revocation checking. \"ocsp-crl\" - Use OCSP first, but if OCSP fails to return an unambiguous result, then check via CRL. </pre> ")
  public AuthClientCertRevocationCheckModeEnum getAuthClientCertRevocationCheckMode() {
    return authClientCertRevocationCheckMode;
  }

  public void setAuthClientCertRevocationCheckMode(AuthClientCertRevocationCheckModeEnum authClientCertRevocationCheckMode) {
    this.authClientCertRevocationCheckMode = authClientCertRevocationCheckMode;
  }

  public Broker averageRxByteRate(Long averageRxByteRate) {
    this.averageRxByteRate = averageRxByteRate;
    return this;
  }

   /**
   * The one minute average of the message rate received by the Broker, in bytes per second (B/sec). Available since 2.14.
   * @return averageRxByteRate
  **/
  @ApiModelProperty(value = "The one minute average of the message rate received by the Broker, in bytes per second (B/sec). Available since 2.14.")
  public Long getAverageRxByteRate() {
    return averageRxByteRate;
  }

  public void setAverageRxByteRate(Long averageRxByteRate) {
    this.averageRxByteRate = averageRxByteRate;
  }

  public Broker averageRxCompressedByteRate(Long averageRxCompressedByteRate) {
    this.averageRxCompressedByteRate = averageRxCompressedByteRate;
    return this;
  }

   /**
   * The one minute average of the compressed message rate received by the Broker, in bytes per second (B/sec). Available since 2.14.
   * @return averageRxCompressedByteRate
  **/
  @ApiModelProperty(value = "The one minute average of the compressed message rate received by the Broker, in bytes per second (B/sec). Available since 2.14.")
  public Long getAverageRxCompressedByteRate() {
    return averageRxCompressedByteRate;
  }

  public void setAverageRxCompressedByteRate(Long averageRxCompressedByteRate) {
    this.averageRxCompressedByteRate = averageRxCompressedByteRate;
  }

  public Broker averageRxMsgRate(Long averageRxMsgRate) {
    this.averageRxMsgRate = averageRxMsgRate;
    return this;
  }

   /**
   * The one minute average of the message rate received by the Broker, in messages per second (msg/sec). Available since 2.14.
   * @return averageRxMsgRate
  **/
  @ApiModelProperty(value = "The one minute average of the message rate received by the Broker, in messages per second (msg/sec). Available since 2.14.")
  public Long getAverageRxMsgRate() {
    return averageRxMsgRate;
  }

  public void setAverageRxMsgRate(Long averageRxMsgRate) {
    this.averageRxMsgRate = averageRxMsgRate;
  }

  public Broker averageRxUncompressedByteRate(Long averageRxUncompressedByteRate) {
    this.averageRxUncompressedByteRate = averageRxUncompressedByteRate;
    return this;
  }

   /**
   * The one minute average of the uncompressed message rate received by the Broker, in bytes per second (B/sec). Available since 2.14.
   * @return averageRxUncompressedByteRate
  **/
  @ApiModelProperty(value = "The one minute average of the uncompressed message rate received by the Broker, in bytes per second (B/sec). Available since 2.14.")
  public Long getAverageRxUncompressedByteRate() {
    return averageRxUncompressedByteRate;
  }

  public void setAverageRxUncompressedByteRate(Long averageRxUncompressedByteRate) {
    this.averageRxUncompressedByteRate = averageRxUncompressedByteRate;
  }

  public Broker averageTxByteRate(Long averageTxByteRate) {
    this.averageTxByteRate = averageTxByteRate;
    return this;
  }

   /**
   * The one minute average of the message rate transmitted by the Broker, in bytes per second (B/sec). Available since 2.14.
   * @return averageTxByteRate
  **/
  @ApiModelProperty(value = "The one minute average of the message rate transmitted by the Broker, in bytes per second (B/sec). Available since 2.14.")
  public Long getAverageTxByteRate() {
    return averageTxByteRate;
  }

  public void setAverageTxByteRate(Long averageTxByteRate) {
    this.averageTxByteRate = averageTxByteRate;
  }

  public Broker averageTxCompressedByteRate(Long averageTxCompressedByteRate) {
    this.averageTxCompressedByteRate = averageTxCompressedByteRate;
    return this;
  }

   /**
   * The one minute average of the compressed message rate transmitted by the Broker, in bytes per second (B/sec). Available since 2.14.
   * @return averageTxCompressedByteRate
  **/
  @ApiModelProperty(value = "The one minute average of the compressed message rate transmitted by the Broker, in bytes per second (B/sec). Available since 2.14.")
  public Long getAverageTxCompressedByteRate() {
    return averageTxCompressedByteRate;
  }

  public void setAverageTxCompressedByteRate(Long averageTxCompressedByteRate) {
    this.averageTxCompressedByteRate = averageTxCompressedByteRate;
  }

  public Broker averageTxMsgRate(Long averageTxMsgRate) {
    this.averageTxMsgRate = averageTxMsgRate;
    return this;
  }

   /**
   * The one minute average of the message rate transmitted by the Broker, in messages per second (msg/sec). Available since 2.14.
   * @return averageTxMsgRate
  **/
  @ApiModelProperty(value = "The one minute average of the message rate transmitted by the Broker, in messages per second (msg/sec). Available since 2.14.")
  public Long getAverageTxMsgRate() {
    return averageTxMsgRate;
  }

  public void setAverageTxMsgRate(Long averageTxMsgRate) {
    this.averageTxMsgRate = averageTxMsgRate;
  }

  public Broker averageTxUncompressedByteRate(Long averageTxUncompressedByteRate) {
    this.averageTxUncompressedByteRate = averageTxUncompressedByteRate;
    return this;
  }

   /**
   * The one minute average of the uncompressed message rate transmitted by the Broker, in bytes per second (B/sec). Available since 2.14.
   * @return averageTxUncompressedByteRate
  **/
  @ApiModelProperty(value = "The one minute average of the uncompressed message rate transmitted by the Broker, in bytes per second (B/sec). Available since 2.14.")
  public Long getAverageTxUncompressedByteRate() {
    return averageTxUncompressedByteRate;
  }

  public void setAverageTxUncompressedByteRate(Long averageTxUncompressedByteRate) {
    this.averageTxUncompressedByteRate = averageTxUncompressedByteRate;
  }

  public Broker configSyncAuthenticationClientCertMaxChainDepth(Long configSyncAuthenticationClientCertMaxChainDepth) {
    this.configSyncAuthenticationClientCertMaxChainDepth = configSyncAuthenticationClientCertMaxChainDepth;
    return this;
  }

   /**
   * The maximum depth for a client certificate chain. The depth of a chain is defined as the number of signing CA certificates that are present in the chain back to a trusted self-signed root CA certificate. Available since 2.22.
   * @return configSyncAuthenticationClientCertMaxChainDepth
  **/
  @ApiModelProperty(value = "The maximum depth for a client certificate chain. The depth of a chain is defined as the number of signing CA certificates that are present in the chain back to a trusted self-signed root CA certificate. Available since 2.22.")
  public Long getConfigSyncAuthenticationClientCertMaxChainDepth() {
    return configSyncAuthenticationClientCertMaxChainDepth;
  }

  public void setConfigSyncAuthenticationClientCertMaxChainDepth(Long configSyncAuthenticationClientCertMaxChainDepth) {
    this.configSyncAuthenticationClientCertMaxChainDepth = configSyncAuthenticationClientCertMaxChainDepth;
  }

  public Broker configSyncAuthenticationClientCertValidateDateEnabled(Boolean configSyncAuthenticationClientCertValidateDateEnabled) {
    this.configSyncAuthenticationClientCertValidateDateEnabled = configSyncAuthenticationClientCertValidateDateEnabled;
    return this;
  }

   /**
   * Enable or disable validation of the \&quot;Not Before\&quot; and \&quot;Not After\&quot; validity dates in the authentication certificate(s). Available since 2.22.
   * @return configSyncAuthenticationClientCertValidateDateEnabled
  **/
  @ApiModelProperty(value = "Enable or disable validation of the \"Not Before\" and \"Not After\" validity dates in the authentication certificate(s). Available since 2.22.")
  public Boolean isConfigSyncAuthenticationClientCertValidateDateEnabled() {
    return configSyncAuthenticationClientCertValidateDateEnabled;
  }

  public void setConfigSyncAuthenticationClientCertValidateDateEnabled(Boolean configSyncAuthenticationClientCertValidateDateEnabled) {
    this.configSyncAuthenticationClientCertValidateDateEnabled = configSyncAuthenticationClientCertValidateDateEnabled;
  }

  public Broker configSyncClientProfileTcpInitialCongestionWindow(Long configSyncClientProfileTcpInitialCongestionWindow) {
    this.configSyncClientProfileTcpInitialCongestionWindow = configSyncClientProfileTcpInitialCongestionWindow;
    return this;
  }

   /**
   * The TCP initial congestion window size for Config Sync clients, in multiples of the TCP Maximum Segment Size (MSS). Changing the value from its default of 2 results in non-compliance with RFC 2581. Contact Solace Support before changing this value. Available since 2.22.
   * @return configSyncClientProfileTcpInitialCongestionWindow
  **/
  @ApiModelProperty(value = "The TCP initial congestion window size for Config Sync clients, in multiples of the TCP Maximum Segment Size (MSS). Changing the value from its default of 2 results in non-compliance with RFC 2581. Contact Solace Support before changing this value. Available since 2.22.")
  public Long getConfigSyncClientProfileTcpInitialCongestionWindow() {
    return configSyncClientProfileTcpInitialCongestionWindow;
  }

  public void setConfigSyncClientProfileTcpInitialCongestionWindow(Long configSyncClientProfileTcpInitialCongestionWindow) {
    this.configSyncClientProfileTcpInitialCongestionWindow = configSyncClientProfileTcpInitialCongestionWindow;
  }

  public Broker configSyncClientProfileTcpKeepaliveCount(Long configSyncClientProfileTcpKeepaliveCount) {
    this.configSyncClientProfileTcpKeepaliveCount = configSyncClientProfileTcpKeepaliveCount;
    return this;
  }

   /**
   * The number of TCP keepalive retransmissions to a client using the Client Profile before declaring that it is not available. Available since 2.22.
   * @return configSyncClientProfileTcpKeepaliveCount
  **/
  @ApiModelProperty(value = "The number of TCP keepalive retransmissions to a client using the Client Profile before declaring that it is not available. Available since 2.22.")
  public Long getConfigSyncClientProfileTcpKeepaliveCount() {
    return configSyncClientProfileTcpKeepaliveCount;
  }

  public void setConfigSyncClientProfileTcpKeepaliveCount(Long configSyncClientProfileTcpKeepaliveCount) {
    this.configSyncClientProfileTcpKeepaliveCount = configSyncClientProfileTcpKeepaliveCount;
  }

  public Broker configSyncClientProfileTcpKeepaliveIdle(Long configSyncClientProfileTcpKeepaliveIdle) {
    this.configSyncClientProfileTcpKeepaliveIdle = configSyncClientProfileTcpKeepaliveIdle;
    return this;
  }

   /**
   * The amount of time a client connection using the Client Profile must remain idle before TCP begins sending keepalive probes, in seconds. Available since 2.22.
   * @return configSyncClientProfileTcpKeepaliveIdle
  **/
  @ApiModelProperty(value = "The amount of time a client connection using the Client Profile must remain idle before TCP begins sending keepalive probes, in seconds. Available since 2.22.")
  public Long getConfigSyncClientProfileTcpKeepaliveIdle() {
    return configSyncClientProfileTcpKeepaliveIdle;
  }

  public void setConfigSyncClientProfileTcpKeepaliveIdle(Long configSyncClientProfileTcpKeepaliveIdle) {
    this.configSyncClientProfileTcpKeepaliveIdle = configSyncClientProfileTcpKeepaliveIdle;
  }

  public Broker configSyncClientProfileTcpKeepaliveInterval(Long configSyncClientProfileTcpKeepaliveInterval) {
    this.configSyncClientProfileTcpKeepaliveInterval = configSyncClientProfileTcpKeepaliveInterval;
    return this;
  }

   /**
   * The amount of time between TCP keepalive retransmissions to a client using the Client Profile when no acknowledgement is received, in seconds. Available since 2.22.
   * @return configSyncClientProfileTcpKeepaliveInterval
  **/
  @ApiModelProperty(value = "The amount of time between TCP keepalive retransmissions to a client using the Client Profile when no acknowledgement is received, in seconds. Available since 2.22.")
  public Long getConfigSyncClientProfileTcpKeepaliveInterval() {
    return configSyncClientProfileTcpKeepaliveInterval;
  }

  public void setConfigSyncClientProfileTcpKeepaliveInterval(Long configSyncClientProfileTcpKeepaliveInterval) {
    this.configSyncClientProfileTcpKeepaliveInterval = configSyncClientProfileTcpKeepaliveInterval;
  }

  public Broker configSyncClientProfileTcpMaxWindow(Long configSyncClientProfileTcpMaxWindow) {
    this.configSyncClientProfileTcpMaxWindow = configSyncClientProfileTcpMaxWindow;
    return this;
  }

   /**
   * The TCP maximum window size for clients using the Client Profile, in kilobytes. Changes are applied to all existing connections. Available since 2.22.
   * @return configSyncClientProfileTcpMaxWindow
  **/
  @ApiModelProperty(value = "The TCP maximum window size for clients using the Client Profile, in kilobytes. Changes are applied to all existing connections. Available since 2.22.")
  public Long getConfigSyncClientProfileTcpMaxWindow() {
    return configSyncClientProfileTcpMaxWindow;
  }

  public void setConfigSyncClientProfileTcpMaxWindow(Long configSyncClientProfileTcpMaxWindow) {
    this.configSyncClientProfileTcpMaxWindow = configSyncClientProfileTcpMaxWindow;
  }

  public Broker configSyncClientProfileTcpMss(Long configSyncClientProfileTcpMss) {
    this.configSyncClientProfileTcpMss = configSyncClientProfileTcpMss;
    return this;
  }

   /**
   * The TCP maximum segment size for clients using the Client Profile, in bytes. Changes are applied to all existing connections. Available since 2.22.
   * @return configSyncClientProfileTcpMss
  **/
  @ApiModelProperty(value = "The TCP maximum segment size for clients using the Client Profile, in bytes. Changes are applied to all existing connections. Available since 2.22.")
  public Long getConfigSyncClientProfileTcpMss() {
    return configSyncClientProfileTcpMss;
  }

  public void setConfigSyncClientProfileTcpMss(Long configSyncClientProfileTcpMss) {
    this.configSyncClientProfileTcpMss = configSyncClientProfileTcpMss;
  }

  public Broker configSyncEnabled(Boolean configSyncEnabled) {
    this.configSyncEnabled = configSyncEnabled;
    return this;
  }

   /**
   * Enable or disable configuration synchronization for High Availability or Disaster Recovery. Available since 2.22.
   * @return configSyncEnabled
  **/
  @ApiModelProperty(value = "Enable or disable configuration synchronization for High Availability or Disaster Recovery. Available since 2.22.")
  public Boolean isConfigSyncEnabled() {
    return configSyncEnabled;
  }

  public void setConfigSyncEnabled(Boolean configSyncEnabled) {
    this.configSyncEnabled = configSyncEnabled;
  }

  public Broker configSyncLastFailureReason(String configSyncLastFailureReason) {
    this.configSyncLastFailureReason = configSyncLastFailureReason;
    return this;
  }

   /**
   * The reason for the last transition to a \&quot;Down\&quot; operational status. On transitions to the \&quot;Up\&quot; operational status this value is cleared. Available since 2.22.
   * @return configSyncLastFailureReason
  **/
  @ApiModelProperty(value = "The reason for the last transition to a \"Down\" operational status. On transitions to the \"Up\" operational status this value is cleared. Available since 2.22.")
  public String getConfigSyncLastFailureReason() {
    return configSyncLastFailureReason;
  }

  public void setConfigSyncLastFailureReason(String configSyncLastFailureReason) {
    this.configSyncLastFailureReason = configSyncLastFailureReason;
  }

  public Broker configSyncSynchronizeUsernameEnabled(Boolean configSyncSynchronizeUsernameEnabled) {
    this.configSyncSynchronizeUsernameEnabled = configSyncSynchronizeUsernameEnabled;
    return this;
  }

   /**
   * Enable or disable the synchronizing of usernames within High Availability groups. The transition from not synchronizing to synchronizing will cause the High Availability mate to fall out of sync. Recommendation: leave this as enabled. Available since 2.22.
   * @return configSyncSynchronizeUsernameEnabled
  **/
  @ApiModelProperty(value = "Enable or disable the synchronizing of usernames within High Availability groups. The transition from not synchronizing to synchronizing will cause the High Availability mate to fall out of sync. Recommendation: leave this as enabled. Available since 2.22.")
  public Boolean isConfigSyncSynchronizeUsernameEnabled() {
    return configSyncSynchronizeUsernameEnabled;
  }

  public void setConfigSyncSynchronizeUsernameEnabled(Boolean configSyncSynchronizeUsernameEnabled) {
    this.configSyncSynchronizeUsernameEnabled = configSyncSynchronizeUsernameEnabled;
  }

  public Broker configSyncTlsEnabled(Boolean configSyncTlsEnabled) {
    this.configSyncTlsEnabled = configSyncTlsEnabled;
    return this;
  }

   /**
   * Enable or disable the use of TLS encryption of the configuration synchronization communications between brokers in High Availability groups and/or Disaster Recovery sites. Available since 2.22.
   * @return configSyncTlsEnabled
  **/
  @ApiModelProperty(value = "Enable or disable the use of TLS encryption of the configuration synchronization communications between brokers in High Availability groups and/or Disaster Recovery sites. Available since 2.22.")
  public Boolean isConfigSyncTlsEnabled() {
    return configSyncTlsEnabled;
  }

  public void setConfigSyncTlsEnabled(Boolean configSyncTlsEnabled) {
    this.configSyncTlsEnabled = configSyncTlsEnabled;
  }

  public Broker configSyncUp(Boolean configSyncUp) {
    this.configSyncUp = configSyncUp;
    return this;
  }

   /**
   * Indicates whether the configuration synchronization facility is operational. \&quot;True\&quot; indicates the facility is Up, otherwise it is Down. When \&quot;False\&quot; the configSyncLastFailureReason will provide further detail. Available since 2.22.
   * @return configSyncUp
  **/
  @ApiModelProperty(value = "Indicates whether the configuration synchronization facility is operational. \"True\" indicates the facility is Up, otherwise it is Down. When \"False\" the configSyncLastFailureReason will provide further detail. Available since 2.22.")
  public Boolean isConfigSyncUp() {
    return configSyncUp;
  }

  public void setConfigSyncUp(Boolean configSyncUp) {
    this.configSyncUp = configSyncUp;
  }

  public Broker cspfVersion(Integer cspfVersion) {
    this.cspfVersion = cspfVersion;
    return this;
  }

   /**
   * The current CSPF version. Available since 2.17.
   * @return cspfVersion
  **/
  @ApiModelProperty(value = "The current CSPF version. Available since 2.17.")
  public Integer getCspfVersion() {
    return cspfVersion;
  }

  public void setCspfVersion(Integer cspfVersion) {
    this.cspfVersion = cspfVersion;
  }

  public Broker guaranteedMsgingDefragmentationEstimatedFragmentation(Long guaranteedMsgingDefragmentationEstimatedFragmentation) {
    this.guaranteedMsgingDefragmentationEstimatedFragmentation = guaranteedMsgingDefragmentationEstimatedFragmentation;
    return this;
  }

   /**
   * An approximation of the amount of disk space consumed, but not used, by the persisted data. Calculated as a percentage of total space. Available since 2.18.
   * @return guaranteedMsgingDefragmentationEstimatedFragmentation
  **/
  @ApiModelProperty(value = "An approximation of the amount of disk space consumed, but not used, by the persisted data. Calculated as a percentage of total space. Available since 2.18.")
  public Long getGuaranteedMsgingDefragmentationEstimatedFragmentation() {
    return guaranteedMsgingDefragmentationEstimatedFragmentation;
  }

  public void setGuaranteedMsgingDefragmentationEstimatedFragmentation(Long guaranteedMsgingDefragmentationEstimatedFragmentation) {
    this.guaranteedMsgingDefragmentationEstimatedFragmentation = guaranteedMsgingDefragmentationEstimatedFragmentation;
  }

  public Broker guaranteedMsgingDefragmentationEstimatedRecoverableSpace(Integer guaranteedMsgingDefragmentationEstimatedRecoverableSpace) {
    this.guaranteedMsgingDefragmentationEstimatedRecoverableSpace = guaranteedMsgingDefragmentationEstimatedRecoverableSpace;
    return this;
  }

   /**
   * An approximation of the amount of disk space recovered upon a successfully completed execution of a defragmentation operation. Expressed in MB. Available since 2.18.
   * @return guaranteedMsgingDefragmentationEstimatedRecoverableSpace
  **/
  @ApiModelProperty(value = "An approximation of the amount of disk space recovered upon a successfully completed execution of a defragmentation operation. Expressed in MB. Available since 2.18.")
  public Integer getGuaranteedMsgingDefragmentationEstimatedRecoverableSpace() {
    return guaranteedMsgingDefragmentationEstimatedRecoverableSpace;
  }

  public void setGuaranteedMsgingDefragmentationEstimatedRecoverableSpace(Integer guaranteedMsgingDefragmentationEstimatedRecoverableSpace) {
    this.guaranteedMsgingDefragmentationEstimatedRecoverableSpace = guaranteedMsgingDefragmentationEstimatedRecoverableSpace;
  }

  public Broker guaranteedMsgingDefragmentationLastCompletedOn(Integer guaranteedMsgingDefragmentationLastCompletedOn) {
    this.guaranteedMsgingDefragmentationLastCompletedOn = guaranteedMsgingDefragmentationLastCompletedOn;
    return this;
  }

   /**
   * A timestamp reflecting when the last defragmentation completed. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). Available since 2.18.
   * @return guaranteedMsgingDefragmentationLastCompletedOn
  **/
  @ApiModelProperty(value = "A timestamp reflecting when the last defragmentation completed. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). Available since 2.18.")
  public Integer getGuaranteedMsgingDefragmentationLastCompletedOn() {
    return guaranteedMsgingDefragmentationLastCompletedOn;
  }

  public void setGuaranteedMsgingDefragmentationLastCompletedOn(Integer guaranteedMsgingDefragmentationLastCompletedOn) {
    this.guaranteedMsgingDefragmentationLastCompletedOn = guaranteedMsgingDefragmentationLastCompletedOn;
  }

  public Broker guaranteedMsgingDefragmentationLastCompletionPercentage(Long guaranteedMsgingDefragmentationLastCompletionPercentage) {
    this.guaranteedMsgingDefragmentationLastCompletionPercentage = guaranteedMsgingDefragmentationLastCompletionPercentage;
    return this;
  }

   /**
   * How much of the message spool was visited during the last defragmentation operation. This number reflects the percentage of the message spool visited in terms of disk space (as opposed to, for example, spool files). Available since 2.18.
   * @return guaranteedMsgingDefragmentationLastCompletionPercentage
  **/
  @ApiModelProperty(value = "How much of the message spool was visited during the last defragmentation operation. This number reflects the percentage of the message spool visited in terms of disk space (as opposed to, for example, spool files). Available since 2.18.")
  public Long getGuaranteedMsgingDefragmentationLastCompletionPercentage() {
    return guaranteedMsgingDefragmentationLastCompletionPercentage;
  }

  public void setGuaranteedMsgingDefragmentationLastCompletionPercentage(Long guaranteedMsgingDefragmentationLastCompletionPercentage) {
    this.guaranteedMsgingDefragmentationLastCompletionPercentage = guaranteedMsgingDefragmentationLastCompletionPercentage;
  }

  public Broker guaranteedMsgingDefragmentationLastExitCondition(String guaranteedMsgingDefragmentationLastExitCondition) {
    this.guaranteedMsgingDefragmentationLastExitCondition = guaranteedMsgingDefragmentationLastExitCondition;
    return this;
  }

   /**
   * Reflects how the last defragmentation operation completed. The allowed values and their meaning are:  &lt;pre&gt; \&quot;success\&quot; - Defragmentation completed successfully. \&quot;unmovable-local-transaction\&quot; - Defragmentation stopped after encountering an unmovable local transaction. \&quot;unmovable-xa-transaction\&quot; - Defragmentation stopped after encountering an unmovable XA transaction. \&quot;incomplete\&quot; - Defragmentation stopped prematurely. \&quot;stopped-by-administrator\&quot; - Defragmentation stopped by administrator. &lt;/pre&gt;  Available since 2.18.
   * @return guaranteedMsgingDefragmentationLastExitCondition
  **/
  @ApiModelProperty(value = "Reflects how the last defragmentation operation completed. The allowed values and their meaning are:  <pre> \"success\" - Defragmentation completed successfully. \"unmovable-local-transaction\" - Defragmentation stopped after encountering an unmovable local transaction. \"unmovable-xa-transaction\" - Defragmentation stopped after encountering an unmovable XA transaction. \"incomplete\" - Defragmentation stopped prematurely. \"stopped-by-administrator\" - Defragmentation stopped by administrator. </pre>  Available since 2.18.")
  public String getGuaranteedMsgingDefragmentationLastExitCondition() {
    return guaranteedMsgingDefragmentationLastExitCondition;
  }

  public void setGuaranteedMsgingDefragmentationLastExitCondition(String guaranteedMsgingDefragmentationLastExitCondition) {
    this.guaranteedMsgingDefragmentationLastExitCondition = guaranteedMsgingDefragmentationLastExitCondition;
  }

  public Broker guaranteedMsgingDefragmentationLastExitConditionInformation(String guaranteedMsgingDefragmentationLastExitConditionInformation) {
    this.guaranteedMsgingDefragmentationLastExitConditionInformation = guaranteedMsgingDefragmentationLastExitConditionInformation;
    return this;
  }

   /**
   * Optional additional information regarding the exit condition of the last defragmentation operation. Available since 2.18.
   * @return guaranteedMsgingDefragmentationLastExitConditionInformation
  **/
  @ApiModelProperty(value = "Optional additional information regarding the exit condition of the last defragmentation operation. Available since 2.18.")
  public String getGuaranteedMsgingDefragmentationLastExitConditionInformation() {
    return guaranteedMsgingDefragmentationLastExitConditionInformation;
  }

  public void setGuaranteedMsgingDefragmentationLastExitConditionInformation(String guaranteedMsgingDefragmentationLastExitConditionInformation) {
    this.guaranteedMsgingDefragmentationLastExitConditionInformation = guaranteedMsgingDefragmentationLastExitConditionInformation;
  }

  public Broker guaranteedMsgingDefragmentationStatus(String guaranteedMsgingDefragmentationStatus) {
    this.guaranteedMsgingDefragmentationStatus = guaranteedMsgingDefragmentationStatus;
    return this;
  }

   /**
   * Defragmentation status of guaranteed messaging. The allowed values and their meaning are:  &lt;pre&gt; \&quot;idle\&quot; - Defragmentation is not currently running. \&quot;pending\&quot; - Degfragmentation is preparing to run. \&quot;active\&quot; - Defragmentation is in progress. &lt;/pre&gt;  Available since 2.18.
   * @return guaranteedMsgingDefragmentationStatus
  **/
  @ApiModelProperty(value = "Defragmentation status of guaranteed messaging. The allowed values and their meaning are:  <pre> \"idle\" - Defragmentation is not currently running. \"pending\" - Degfragmentation is preparing to run. \"active\" - Defragmentation is in progress. </pre>  Available since 2.18.")
  public String getGuaranteedMsgingDefragmentationStatus() {
    return guaranteedMsgingDefragmentationStatus;
  }

  public void setGuaranteedMsgingDefragmentationStatus(String guaranteedMsgingDefragmentationStatus) {
    this.guaranteedMsgingDefragmentationStatus = guaranteedMsgingDefragmentationStatus;
  }

  public Broker guaranteedMsgingDefragmentationStatusActiveCompletionPercentage(Long guaranteedMsgingDefragmentationStatusActiveCompletionPercentage) {
    this.guaranteedMsgingDefragmentationStatusActiveCompletionPercentage = guaranteedMsgingDefragmentationStatusActiveCompletionPercentage;
    return this;
  }

   /**
   * The estimated completion percentage of a defragmentation operation currently in progress. Only valid if the defragmentation status is \&quot;Active\&quot;. Available since 2.18.
   * @return guaranteedMsgingDefragmentationStatusActiveCompletionPercentage
  **/
  @ApiModelProperty(value = "The estimated completion percentage of a defragmentation operation currently in progress. Only valid if the defragmentation status is \"Active\". Available since 2.18.")
  public Long getGuaranteedMsgingDefragmentationStatusActiveCompletionPercentage() {
    return guaranteedMsgingDefragmentationStatusActiveCompletionPercentage;
  }

  public void setGuaranteedMsgingDefragmentationStatusActiveCompletionPercentage(Long guaranteedMsgingDefragmentationStatusActiveCompletionPercentage) {
    this.guaranteedMsgingDefragmentationStatusActiveCompletionPercentage = guaranteedMsgingDefragmentationStatusActiveCompletionPercentage;
  }

  public Broker guaranteedMsgingDiskArrayWwn(String guaranteedMsgingDiskArrayWwn) {
    this.guaranteedMsgingDiskArrayWwn = guaranteedMsgingDiskArrayWwn;
    return this;
  }

   /**
   * The WWN number to use when accessing a LUN on an external disk array. Available since 2.18.
   * @return guaranteedMsgingDiskArrayWwn
  **/
  @ApiModelProperty(value = "The WWN number to use when accessing a LUN on an external disk array. Available since 2.18.")
  public String getGuaranteedMsgingDiskArrayWwn() {
    return guaranteedMsgingDiskArrayWwn;
  }

  public void setGuaranteedMsgingDiskArrayWwn(String guaranteedMsgingDiskArrayWwn) {
    this.guaranteedMsgingDiskArrayWwn = guaranteedMsgingDiskArrayWwn;
  }

  public Broker guaranteedMsgingDiskLocation(GuaranteedMsgingDiskLocationEnum guaranteedMsgingDiskLocation) {
    this.guaranteedMsgingDiskLocation = guaranteedMsgingDiskLocation;
    return this;
  }

   /**
   * The disk location for the the guaranteed message spool (required for high availability with guaranteed messaging). When external is chosen the guaranteed message spool is stored on an external disk array attached to the router. If internal storage is currently used, changing to external causes message spooling on the router to stop and messages spooled on the internal storage to be deleted. If internal is chosen the guaranteed message spool is stored on an external disk array attached to the router. If internal storage is currently used, changing to external causes message spooling on the router to stop and messages spooled on the internal storage to be deleted. The allowed values and their meaning are:  &lt;pre&gt; \&quot;external\&quot; - The guaranteed message spool is stored on an external disk array attached to the appliance. \&quot;internal\&quot; - The guaranteed message spool is stored internally on the appliance. &lt;/pre&gt;  Available since 2.18.
   * @return guaranteedMsgingDiskLocation
  **/
  @ApiModelProperty(value = "The disk location for the the guaranteed message spool (required for high availability with guaranteed messaging). When external is chosen the guaranteed message spool is stored on an external disk array attached to the router. If internal storage is currently used, changing to external causes message spooling on the router to stop and messages spooled on the internal storage to be deleted. If internal is chosen the guaranteed message spool is stored on an external disk array attached to the router. If internal storage is currently used, changing to external causes message spooling on the router to stop and messages spooled on the internal storage to be deleted. The allowed values and their meaning are:  <pre> \"external\" - The guaranteed message spool is stored on an external disk array attached to the appliance. \"internal\" - The guaranteed message spool is stored internally on the appliance. </pre>  Available since 2.18.")
  public GuaranteedMsgingDiskLocationEnum getGuaranteedMsgingDiskLocation() {
    return guaranteedMsgingDiskLocation;
  }

  public void setGuaranteedMsgingDiskLocation(GuaranteedMsgingDiskLocationEnum guaranteedMsgingDiskLocation) {
    this.guaranteedMsgingDiskLocation = guaranteedMsgingDiskLocation;
  }

  public Broker guaranteedMsgingEnabled(Boolean guaranteedMsgingEnabled) {
    this.guaranteedMsgingEnabled = guaranteedMsgingEnabled;
    return this;
  }

   /**
   * Enable or disable Guaranteed Messaging. Available since 2.18.
   * @return guaranteedMsgingEnabled
  **/
  @ApiModelProperty(value = "Enable or disable Guaranteed Messaging. Available since 2.18.")
  public Boolean isGuaranteedMsgingEnabled() {
    return guaranteedMsgingEnabled;
  }

  public void setGuaranteedMsgingEnabled(Boolean guaranteedMsgingEnabled) {
    this.guaranteedMsgingEnabled = guaranteedMsgingEnabled;
  }

  public Broker guaranteedMsgingEventCacheUsageThreshold(EventThreshold guaranteedMsgingEventCacheUsageThreshold) {
    this.guaranteedMsgingEventCacheUsageThreshold = guaranteedMsgingEventCacheUsageThreshold;
    return this;
  }

   /**
   * Get guaranteedMsgingEventCacheUsageThreshold
   * @return guaranteedMsgingEventCacheUsageThreshold
  **/
  @ApiModelProperty(value = "")
  public EventThreshold getGuaranteedMsgingEventCacheUsageThreshold() {
    return guaranteedMsgingEventCacheUsageThreshold;
  }

  public void setGuaranteedMsgingEventCacheUsageThreshold(EventThreshold guaranteedMsgingEventCacheUsageThreshold) {
    this.guaranteedMsgingEventCacheUsageThreshold = guaranteedMsgingEventCacheUsageThreshold;
  }

  public Broker guaranteedMsgingEventDeliveredUnackedThreshold(EventThresholdByPercent guaranteedMsgingEventDeliveredUnackedThreshold) {
    this.guaranteedMsgingEventDeliveredUnackedThreshold = guaranteedMsgingEventDeliveredUnackedThreshold;
    return this;
  }

   /**
   * Get guaranteedMsgingEventDeliveredUnackedThreshold
   * @return guaranteedMsgingEventDeliveredUnackedThreshold
  **/
  @ApiModelProperty(value = "")
  public EventThresholdByPercent getGuaranteedMsgingEventDeliveredUnackedThreshold() {
    return guaranteedMsgingEventDeliveredUnackedThreshold;
  }

  public void setGuaranteedMsgingEventDeliveredUnackedThreshold(EventThresholdByPercent guaranteedMsgingEventDeliveredUnackedThreshold) {
    this.guaranteedMsgingEventDeliveredUnackedThreshold = guaranteedMsgingEventDeliveredUnackedThreshold;
  }

  public Broker guaranteedMsgingEventDiskUsageThreshold(EventThresholdByPercent guaranteedMsgingEventDiskUsageThreshold) {
    this.guaranteedMsgingEventDiskUsageThreshold = guaranteedMsgingEventDiskUsageThreshold;
    return this;
  }

   /**
   * Get guaranteedMsgingEventDiskUsageThreshold
   * @return guaranteedMsgingEventDiskUsageThreshold
  **/
  @ApiModelProperty(value = "")
  public EventThresholdByPercent getGuaranteedMsgingEventDiskUsageThreshold() {
    return guaranteedMsgingEventDiskUsageThreshold;
  }

  public void setGuaranteedMsgingEventDiskUsageThreshold(EventThresholdByPercent guaranteedMsgingEventDiskUsageThreshold) {
    this.guaranteedMsgingEventDiskUsageThreshold = guaranteedMsgingEventDiskUsageThreshold;
  }

  public Broker guaranteedMsgingEventEgressFlowCountThreshold(EventThreshold guaranteedMsgingEventEgressFlowCountThreshold) {
    this.guaranteedMsgingEventEgressFlowCountThreshold = guaranteedMsgingEventEgressFlowCountThreshold;
    return this;
  }

   /**
   * Get guaranteedMsgingEventEgressFlowCountThreshold
   * @return guaranteedMsgingEventEgressFlowCountThreshold
  **/
  @ApiModelProperty(value = "")
  public EventThreshold getGuaranteedMsgingEventEgressFlowCountThreshold() {
    return guaranteedMsgingEventEgressFlowCountThreshold;
  }

  public void setGuaranteedMsgingEventEgressFlowCountThreshold(EventThreshold guaranteedMsgingEventEgressFlowCountThreshold) {
    this.guaranteedMsgingEventEgressFlowCountThreshold = guaranteedMsgingEventEgressFlowCountThreshold;
  }

  public Broker guaranteedMsgingEventEndpointCountThreshold(EventThreshold guaranteedMsgingEventEndpointCountThreshold) {
    this.guaranteedMsgingEventEndpointCountThreshold = guaranteedMsgingEventEndpointCountThreshold;
    return this;
  }

   /**
   * Get guaranteedMsgingEventEndpointCountThreshold
   * @return guaranteedMsgingEventEndpointCountThreshold
  **/
  @ApiModelProperty(value = "")
  public EventThreshold getGuaranteedMsgingEventEndpointCountThreshold() {
    return guaranteedMsgingEventEndpointCountThreshold;
  }

  public void setGuaranteedMsgingEventEndpointCountThreshold(EventThreshold guaranteedMsgingEventEndpointCountThreshold) {
    this.guaranteedMsgingEventEndpointCountThreshold = guaranteedMsgingEventEndpointCountThreshold;
  }

  public Broker guaranteedMsgingEventIngressFlowCountThreshold(EventThreshold guaranteedMsgingEventIngressFlowCountThreshold) {
    this.guaranteedMsgingEventIngressFlowCountThreshold = guaranteedMsgingEventIngressFlowCountThreshold;
    return this;
  }

   /**
   * Get guaranteedMsgingEventIngressFlowCountThreshold
   * @return guaranteedMsgingEventIngressFlowCountThreshold
  **/
  @ApiModelProperty(value = "")
  public EventThreshold getGuaranteedMsgingEventIngressFlowCountThreshold() {
    return guaranteedMsgingEventIngressFlowCountThreshold;
  }

  public void setGuaranteedMsgingEventIngressFlowCountThreshold(EventThreshold guaranteedMsgingEventIngressFlowCountThreshold) {
    this.guaranteedMsgingEventIngressFlowCountThreshold = guaranteedMsgingEventIngressFlowCountThreshold;
  }

  public Broker guaranteedMsgingEventMsgCountThreshold(EventThresholdByPercent guaranteedMsgingEventMsgCountThreshold) {
    this.guaranteedMsgingEventMsgCountThreshold = guaranteedMsgingEventMsgCountThreshold;
    return this;
  }

   /**
   * Get guaranteedMsgingEventMsgCountThreshold
   * @return guaranteedMsgingEventMsgCountThreshold
  **/
  @ApiModelProperty(value = "")
  public EventThresholdByPercent getGuaranteedMsgingEventMsgCountThreshold() {
    return guaranteedMsgingEventMsgCountThreshold;
  }

  public void setGuaranteedMsgingEventMsgCountThreshold(EventThresholdByPercent guaranteedMsgingEventMsgCountThreshold) {
    this.guaranteedMsgingEventMsgCountThreshold = guaranteedMsgingEventMsgCountThreshold;
  }

  public Broker guaranteedMsgingEventMsgSpoolFileCountThreshold(EventThresholdByPercent guaranteedMsgingEventMsgSpoolFileCountThreshold) {
    this.guaranteedMsgingEventMsgSpoolFileCountThreshold = guaranteedMsgingEventMsgSpoolFileCountThreshold;
    return this;
  }

   /**
   * Get guaranteedMsgingEventMsgSpoolFileCountThreshold
   * @return guaranteedMsgingEventMsgSpoolFileCountThreshold
  **/
  @ApiModelProperty(value = "")
  public EventThresholdByPercent getGuaranteedMsgingEventMsgSpoolFileCountThreshold() {
    return guaranteedMsgingEventMsgSpoolFileCountThreshold;
  }

  public void setGuaranteedMsgingEventMsgSpoolFileCountThreshold(EventThresholdByPercent guaranteedMsgingEventMsgSpoolFileCountThreshold) {
    this.guaranteedMsgingEventMsgSpoolFileCountThreshold = guaranteedMsgingEventMsgSpoolFileCountThreshold;
  }

  public Broker guaranteedMsgingEventMsgSpoolUsageThreshold(EventThreshold guaranteedMsgingEventMsgSpoolUsageThreshold) {
    this.guaranteedMsgingEventMsgSpoolUsageThreshold = guaranteedMsgingEventMsgSpoolUsageThreshold;
    return this;
  }

   /**
   * Get guaranteedMsgingEventMsgSpoolUsageThreshold
   * @return guaranteedMsgingEventMsgSpoolUsageThreshold
  **/
  @ApiModelProperty(value = "")
  public EventThreshold getGuaranteedMsgingEventMsgSpoolUsageThreshold() {
    return guaranteedMsgingEventMsgSpoolUsageThreshold;
  }

  public void setGuaranteedMsgingEventMsgSpoolUsageThreshold(EventThreshold guaranteedMsgingEventMsgSpoolUsageThreshold) {
    this.guaranteedMsgingEventMsgSpoolUsageThreshold = guaranteedMsgingEventMsgSpoolUsageThreshold;
  }

  public Broker guaranteedMsgingEventTransactedSessionCountThreshold(EventThreshold guaranteedMsgingEventTransactedSessionCountThreshold) {
    this.guaranteedMsgingEventTransactedSessionCountThreshold = guaranteedMsgingEventTransactedSessionCountThreshold;
    return this;
  }

   /**
   * Get guaranteedMsgingEventTransactedSessionCountThreshold
   * @return guaranteedMsgingEventTransactedSessionCountThreshold
  **/
  @ApiModelProperty(value = "")
  public EventThreshold getGuaranteedMsgingEventTransactedSessionCountThreshold() {
    return guaranteedMsgingEventTransactedSessionCountThreshold;
  }

  public void setGuaranteedMsgingEventTransactedSessionCountThreshold(EventThreshold guaranteedMsgingEventTransactedSessionCountThreshold) {
    this.guaranteedMsgingEventTransactedSessionCountThreshold = guaranteedMsgingEventTransactedSessionCountThreshold;
  }

  public Broker guaranteedMsgingEventTransactedSessionResourceCountThreshold(EventThresholdByPercent guaranteedMsgingEventTransactedSessionResourceCountThreshold) {
    this.guaranteedMsgingEventTransactedSessionResourceCountThreshold = guaranteedMsgingEventTransactedSessionResourceCountThreshold;
    return this;
  }

   /**
   * Get guaranteedMsgingEventTransactedSessionResourceCountThreshold
   * @return guaranteedMsgingEventTransactedSessionResourceCountThreshold
  **/
  @ApiModelProperty(value = "")
  public EventThresholdByPercent getGuaranteedMsgingEventTransactedSessionResourceCountThreshold() {
    return guaranteedMsgingEventTransactedSessionResourceCountThreshold;
  }

  public void setGuaranteedMsgingEventTransactedSessionResourceCountThreshold(EventThresholdByPercent guaranteedMsgingEventTransactedSessionResourceCountThreshold) {
    this.guaranteedMsgingEventTransactedSessionResourceCountThreshold = guaranteedMsgingEventTransactedSessionResourceCountThreshold;
  }

  public Broker guaranteedMsgingEventTransactionCountThreshold(EventThreshold guaranteedMsgingEventTransactionCountThreshold) {
    this.guaranteedMsgingEventTransactionCountThreshold = guaranteedMsgingEventTransactionCountThreshold;
    return this;
  }

   /**
   * Get guaranteedMsgingEventTransactionCountThreshold
   * @return guaranteedMsgingEventTransactionCountThreshold
  **/
  @ApiModelProperty(value = "")
  public EventThreshold getGuaranteedMsgingEventTransactionCountThreshold() {
    return guaranteedMsgingEventTransactionCountThreshold;
  }

  public void setGuaranteedMsgingEventTransactionCountThreshold(EventThreshold guaranteedMsgingEventTransactionCountThreshold) {
    this.guaranteedMsgingEventTransactionCountThreshold = guaranteedMsgingEventTransactionCountThreshold;
  }

  public Broker guaranteedMsgingMaxCacheUsage(Integer guaranteedMsgingMaxCacheUsage) {
    this.guaranteedMsgingMaxCacheUsage = guaranteedMsgingMaxCacheUsage;
    return this;
  }

   /**
   * Guaranteed messaging cache usage limit. Expressed as a maximum percentage of the NAB&#39;s egress queueing. resources that the guaranteed message cache is allowed to use. Available since 2.18.
   * @return guaranteedMsgingMaxCacheUsage
  **/
  @ApiModelProperty(value = "Guaranteed messaging cache usage limit. Expressed as a maximum percentage of the NAB's egress queueing. resources that the guaranteed message cache is allowed to use. Available since 2.18.")
  public Integer getGuaranteedMsgingMaxCacheUsage() {
    return guaranteedMsgingMaxCacheUsage;
  }

  public void setGuaranteedMsgingMaxCacheUsage(Integer guaranteedMsgingMaxCacheUsage) {
    this.guaranteedMsgingMaxCacheUsage = guaranteedMsgingMaxCacheUsage;
  }

  public Broker guaranteedMsgingMaxMsgSpoolUsage(Long guaranteedMsgingMaxMsgSpoolUsage) {
    this.guaranteedMsgingMaxMsgSpoolUsage = guaranteedMsgingMaxMsgSpoolUsage;
    return this;
  }

   /**
   * The maximum total message spool usage allowed across all VPNs on this broker, in megabytes. Recommendation: the maximum value should be less than 90% of the disk space allocated for the guaranteed message spool. Available since 2.18.
   * @return guaranteedMsgingMaxMsgSpoolUsage
  **/
  @ApiModelProperty(value = "The maximum total message spool usage allowed across all VPNs on this broker, in megabytes. Recommendation: the maximum value should be less than 90% of the disk space allocated for the guaranteed message spool. Available since 2.18.")
  public Long getGuaranteedMsgingMaxMsgSpoolUsage() {
    return guaranteedMsgingMaxMsgSpoolUsage;
  }

  public void setGuaranteedMsgingMaxMsgSpoolUsage(Long guaranteedMsgingMaxMsgSpoolUsage) {
    this.guaranteedMsgingMaxMsgSpoolUsage = guaranteedMsgingMaxMsgSpoolUsage;
  }

  public Broker guaranteedMsgingOperationalStatus(String guaranteedMsgingOperationalStatus) {
    this.guaranteedMsgingOperationalStatus = guaranteedMsgingOperationalStatus;
    return this;
  }

   /**
   * Operational status of guaranteed messaging. The allowed values and their meaning are:  &lt;pre&gt; \&quot;disabled\&quot; - The operational status of guaranteed messaging is Disabled. \&quot;not-ready\&quot; - The operational status of guaranteed messaging is NotReady. \&quot;standby\&quot; - The operational status of guaranteed messaging is Standby. \&quot;activating\&quot; - The operational status of guaranteed messaging is Activating. \&quot;active\&quot; - The operational status of guaranteed messaging is Active. &lt;/pre&gt;  Available since 2.18.
   * @return guaranteedMsgingOperationalStatus
  **/
  @ApiModelProperty(value = "Operational status of guaranteed messaging. The allowed values and their meaning are:  <pre> \"disabled\" - The operational status of guaranteed messaging is Disabled. \"not-ready\" - The operational status of guaranteed messaging is NotReady. \"standby\" - The operational status of guaranteed messaging is Standby. \"activating\" - The operational status of guaranteed messaging is Activating. \"active\" - The operational status of guaranteed messaging is Active. </pre>  Available since 2.18.")
  public String getGuaranteedMsgingOperationalStatus() {
    return guaranteedMsgingOperationalStatus;
  }

  public void setGuaranteedMsgingOperationalStatus(String guaranteedMsgingOperationalStatus) {
    this.guaranteedMsgingOperationalStatus = guaranteedMsgingOperationalStatus;
  }

  public Broker guaranteedMsgingTransactionReplicationCompatibilityMode(GuaranteedMsgingTransactionReplicationCompatibilityModeEnum guaranteedMsgingTransactionReplicationCompatibilityMode) {
    this.guaranteedMsgingTransactionReplicationCompatibilityMode = guaranteedMsgingTransactionReplicationCompatibilityMode;
    return this;
  }

   /**
   * The replication compatibility mode for the router. The default value is &#x60;\&quot;legacy\&quot;&#x60;. The allowed values and their meaning are:\&quot;legacy\&quot; - All transactions originated by clients are replicated to the standby site without using transactions.\&quot;transacted\&quot; - All transactions originated by clients are replicated to the standby site using transactions. The allowed values and their meaning are:  &lt;pre&gt; \&quot;legacy\&quot; - All transactions originated by clients are replicated to the standby site without using transactions. \&quot;transacted\&quot; - All transactions originated by clients are replicated to the standby site using transactions. &lt;/pre&gt;  Available since 2.18.
   * @return guaranteedMsgingTransactionReplicationCompatibilityMode
  **/
  @ApiModelProperty(value = "The replication compatibility mode for the router. The default value is `\"legacy\"`. The allowed values and their meaning are:\"legacy\" - All transactions originated by clients are replicated to the standby site without using transactions.\"transacted\" - All transactions originated by clients are replicated to the standby site using transactions. The allowed values and their meaning are:  <pre> \"legacy\" - All transactions originated by clients are replicated to the standby site without using transactions. \"transacted\" - All transactions originated by clients are replicated to the standby site using transactions. </pre>  Available since 2.18.")
  public GuaranteedMsgingTransactionReplicationCompatibilityModeEnum getGuaranteedMsgingTransactionReplicationCompatibilityMode() {
    return guaranteedMsgingTransactionReplicationCompatibilityMode;
  }

  public void setGuaranteedMsgingTransactionReplicationCompatibilityMode(GuaranteedMsgingTransactionReplicationCompatibilityModeEnum guaranteedMsgingTransactionReplicationCompatibilityMode) {
    this.guaranteedMsgingTransactionReplicationCompatibilityMode = guaranteedMsgingTransactionReplicationCompatibilityMode;
  }

  public Broker guaranteedMsgingVirtualRouterWhenActiveActive(GuaranteedMsgingVirtualRouterWhenActiveActiveEnum guaranteedMsgingVirtualRouterWhenActiveActive) {
    this.guaranteedMsgingVirtualRouterWhenActiveActive = guaranteedMsgingVirtualRouterWhenActiveActive;
    return this;
  }

   /**
   * The High Availability role for this broker if using the legacy Active/Active configuration for high availability (not recommended). Note: for Active/Standby high availability configuration, this setting is ignored. The allowed values and their meaning are:  &lt;pre&gt; \&quot;primary\&quot; - The primary virtual router. \&quot;backup\&quot; - The backup virtual router. &lt;/pre&gt;  Available since 2.18.
   * @return guaranteedMsgingVirtualRouterWhenActiveActive
  **/
  @ApiModelProperty(value = "The High Availability role for this broker if using the legacy Active/Active configuration for high availability (not recommended). Note: for Active/Standby high availability configuration, this setting is ignored. The allowed values and their meaning are:  <pre> \"primary\" - The primary virtual router. \"backup\" - The backup virtual router. </pre>  Available since 2.18.")
  public GuaranteedMsgingVirtualRouterWhenActiveActiveEnum getGuaranteedMsgingVirtualRouterWhenActiveActive() {
    return guaranteedMsgingVirtualRouterWhenActiveActive;
  }

  public void setGuaranteedMsgingVirtualRouterWhenActiveActive(GuaranteedMsgingVirtualRouterWhenActiveActiveEnum guaranteedMsgingVirtualRouterWhenActiveActive) {
    this.guaranteedMsgingVirtualRouterWhenActiveActive = guaranteedMsgingVirtualRouterWhenActiveActive;
  }

  public Broker rxByteCount(Long rxByteCount) {
    this.rxByteCount = rxByteCount;
    return this;
  }

   /**
   * The amount of messages received from clients by the Broker, in bytes (B). Available since 2.14.
   * @return rxByteCount
  **/
  @ApiModelProperty(value = "The amount of messages received from clients by the Broker, in bytes (B). Available since 2.14.")
  public Long getRxByteCount() {
    return rxByteCount;
  }

  public void setRxByteCount(Long rxByteCount) {
    this.rxByteCount = rxByteCount;
  }

  public Broker rxByteRate(Long rxByteRate) {
    this.rxByteRate = rxByteRate;
    return this;
  }

   /**
   * The current message rate received by the Broker, in bytes per second (B/sec). Available since 2.14.
   * @return rxByteRate
  **/
  @ApiModelProperty(value = "The current message rate received by the Broker, in bytes per second (B/sec). Available since 2.14.")
  public Long getRxByteRate() {
    return rxByteRate;
  }

  public void setRxByteRate(Long rxByteRate) {
    this.rxByteRate = rxByteRate;
  }

  public Broker rxCompressedByteCount(Long rxCompressedByteCount) {
    this.rxCompressedByteCount = rxCompressedByteCount;
    return this;
  }

   /**
   * The amount of compressed messages received by the Broker, in bytes (B). Available since 2.14.
   * @return rxCompressedByteCount
  **/
  @ApiModelProperty(value = "The amount of compressed messages received by the Broker, in bytes (B). Available since 2.14.")
  public Long getRxCompressedByteCount() {
    return rxCompressedByteCount;
  }

  public void setRxCompressedByteCount(Long rxCompressedByteCount) {
    this.rxCompressedByteCount = rxCompressedByteCount;
  }

  public Broker rxCompressedByteRate(Long rxCompressedByteRate) {
    this.rxCompressedByteRate = rxCompressedByteRate;
    return this;
  }

   /**
   * The current compressed message rate received by the Broker, in bytes per second (B/sec). Available since 2.14.
   * @return rxCompressedByteRate
  **/
  @ApiModelProperty(value = "The current compressed message rate received by the Broker, in bytes per second (B/sec). Available since 2.14.")
  public Long getRxCompressedByteRate() {
    return rxCompressedByteRate;
  }

  public void setRxCompressedByteRate(Long rxCompressedByteRate) {
    this.rxCompressedByteRate = rxCompressedByteRate;
  }

  public Broker rxCompressionRatio(String rxCompressionRatio) {
    this.rxCompressionRatio = rxCompressionRatio;
    return this;
  }

   /**
   * The compression ratio for messages received by the Broker. Available since 2.14.
   * @return rxCompressionRatio
  **/
  @ApiModelProperty(value = "The compression ratio for messages received by the Broker. Available since 2.14.")
  public String getRxCompressionRatio() {
    return rxCompressionRatio;
  }

  public void setRxCompressionRatio(String rxCompressionRatio) {
    this.rxCompressionRatio = rxCompressionRatio;
  }

  public Broker rxMsgCount(Long rxMsgCount) {
    this.rxMsgCount = rxMsgCount;
    return this;
  }

   /**
   * The number of messages received from clients by the Broker. Available since 2.14.
   * @return rxMsgCount
  **/
  @ApiModelProperty(value = "The number of messages received from clients by the Broker. Available since 2.14.")
  public Long getRxMsgCount() {
    return rxMsgCount;
  }

  public void setRxMsgCount(Long rxMsgCount) {
    this.rxMsgCount = rxMsgCount;
  }

  public Broker rxMsgRate(Long rxMsgRate) {
    this.rxMsgRate = rxMsgRate;
    return this;
  }

   /**
   * The current message rate received by the Broker, in messages per second (msg/sec). Available since 2.14.
   * @return rxMsgRate
  **/
  @ApiModelProperty(value = "The current message rate received by the Broker, in messages per second (msg/sec). Available since 2.14.")
  public Long getRxMsgRate() {
    return rxMsgRate;
  }

  public void setRxMsgRate(Long rxMsgRate) {
    this.rxMsgRate = rxMsgRate;
  }

  public Broker rxUncompressedByteCount(Long rxUncompressedByteCount) {
    this.rxUncompressedByteCount = rxUncompressedByteCount;
    return this;
  }

   /**
   * The amount of uncompressed messages received by the Broker, in bytes (B). Available since 2.14.
   * @return rxUncompressedByteCount
  **/
  @ApiModelProperty(value = "The amount of uncompressed messages received by the Broker, in bytes (B). Available since 2.14.")
  public Long getRxUncompressedByteCount() {
    return rxUncompressedByteCount;
  }

  public void setRxUncompressedByteCount(Long rxUncompressedByteCount) {
    this.rxUncompressedByteCount = rxUncompressedByteCount;
  }

  public Broker rxUncompressedByteRate(Long rxUncompressedByteRate) {
    this.rxUncompressedByteRate = rxUncompressedByteRate;
    return this;
  }

   /**
   * The current uncompressed message rate received by the Broker, in bytes per second (B/sec). Available since 2.14.
   * @return rxUncompressedByteRate
  **/
  @ApiModelProperty(value = "The current uncompressed message rate received by the Broker, in bytes per second (B/sec). Available since 2.14.")
  public Long getRxUncompressedByteRate() {
    return rxUncompressedByteRate;
  }

  public void setRxUncompressedByteRate(Long rxUncompressedByteRate) {
    this.rxUncompressedByteRate = rxUncompressedByteRate;
  }

  public Broker serviceAmqpEnabled(Boolean serviceAmqpEnabled) {
    this.serviceAmqpEnabled = serviceAmqpEnabled;
    return this;
  }

   /**
   * Enable or disable the AMQP service. When disabled new AMQP Clients may not connect through the global or per-VPN AMQP listen-ports, and all currently connected AMQP Clients are immediately disconnected. Available since 2.17.
   * @return serviceAmqpEnabled
  **/
  @ApiModelProperty(value = "Enable or disable the AMQP service. When disabled new AMQP Clients may not connect through the global or per-VPN AMQP listen-ports, and all currently connected AMQP Clients are immediately disconnected. Available since 2.17.")
  public Boolean isServiceAmqpEnabled() {
    return serviceAmqpEnabled;
  }

  public void setServiceAmqpEnabled(Boolean serviceAmqpEnabled) {
    this.serviceAmqpEnabled = serviceAmqpEnabled;
  }

  public Broker serviceAmqpTlsListenPort(Long serviceAmqpTlsListenPort) {
    this.serviceAmqpTlsListenPort = serviceAmqpTlsListenPort;
    return this;
  }

   /**
   * TCP port number that AMQP clients can use to connect to the broker using raw TCP over TLS. Available since 2.17.
   * @return serviceAmqpTlsListenPort
  **/
  @ApiModelProperty(value = "TCP port number that AMQP clients can use to connect to the broker using raw TCP over TLS. Available since 2.17.")
  public Long getServiceAmqpTlsListenPort() {
    return serviceAmqpTlsListenPort;
  }

  public void setServiceAmqpTlsListenPort(Long serviceAmqpTlsListenPort) {
    this.serviceAmqpTlsListenPort = serviceAmqpTlsListenPort;
  }

  public Broker serviceEventConnectionCountThreshold(EventThreshold serviceEventConnectionCountThreshold) {
    this.serviceEventConnectionCountThreshold = serviceEventConnectionCountThreshold;
    return this;
  }

   /**
   * Get serviceEventConnectionCountThreshold
   * @return serviceEventConnectionCountThreshold
  **/
  @ApiModelProperty(value = "")
  public EventThreshold getServiceEventConnectionCountThreshold() {
    return serviceEventConnectionCountThreshold;
  }

  public void setServiceEventConnectionCountThreshold(EventThreshold serviceEventConnectionCountThreshold) {
    this.serviceEventConnectionCountThreshold = serviceEventConnectionCountThreshold;
  }

  public Broker serviceHealthCheckEnabled(Boolean serviceHealthCheckEnabled) {
    this.serviceHealthCheckEnabled = serviceHealthCheckEnabled;
    return this;
  }

   /**
   * Enable or disable the health-check service. Available since 2.17.
   * @return serviceHealthCheckEnabled
  **/
  @ApiModelProperty(value = "Enable or disable the health-check service. Available since 2.17.")
  public Boolean isServiceHealthCheckEnabled() {
    return serviceHealthCheckEnabled;
  }

  public void setServiceHealthCheckEnabled(Boolean serviceHealthCheckEnabled) {
    this.serviceHealthCheckEnabled = serviceHealthCheckEnabled;
  }

  public Broker serviceHealthCheckListenPort(Long serviceHealthCheckListenPort) {
    this.serviceHealthCheckListenPort = serviceHealthCheckListenPort;
    return this;
  }

   /**
   * The port number for the health-check service. The port must be unique across the message backbone. The health-check service must be disabled to change the port. Available since 2.17.
   * @return serviceHealthCheckListenPort
  **/
  @ApiModelProperty(value = "The port number for the health-check service. The port must be unique across the message backbone. The health-check service must be disabled to change the port. Available since 2.17.")
  public Long getServiceHealthCheckListenPort() {
    return serviceHealthCheckListenPort;
  }

  public void setServiceHealthCheckListenPort(Long serviceHealthCheckListenPort) {
    this.serviceHealthCheckListenPort = serviceHealthCheckListenPort;
  }

  public Broker serviceMqttEnabled(Boolean serviceMqttEnabled) {
    this.serviceMqttEnabled = serviceMqttEnabled;
    return this;
  }

   /**
   * Enable or disable the MQTT service. When disabled new MQTT Clients may not connect through the per-VPN MQTT listen-ports, and all currently connected MQTT Clients are immediately disconnected. Available since 2.17.
   * @return serviceMqttEnabled
  **/
  @ApiModelProperty(value = "Enable or disable the MQTT service. When disabled new MQTT Clients may not connect through the per-VPN MQTT listen-ports, and all currently connected MQTT Clients are immediately disconnected. Available since 2.17.")
  public Boolean isServiceMqttEnabled() {
    return serviceMqttEnabled;
  }

  public void setServiceMqttEnabled(Boolean serviceMqttEnabled) {
    this.serviceMqttEnabled = serviceMqttEnabled;
  }

  public Broker serviceMsgBackboneEnabled(Boolean serviceMsgBackboneEnabled) {
    this.serviceMsgBackboneEnabled = serviceMsgBackboneEnabled;
    return this;
  }

   /**
   * Enable or disable the msg-backbone service. When disabled new Clients may not connect through global or per-VPN listen-ports, and all currently connected Clients are immediately disconnected. Available since 2.17.
   * @return serviceMsgBackboneEnabled
  **/
  @ApiModelProperty(value = "Enable or disable the msg-backbone service. When disabled new Clients may not connect through global or per-VPN listen-ports, and all currently connected Clients are immediately disconnected. Available since 2.17.")
  public Boolean isServiceMsgBackboneEnabled() {
    return serviceMsgBackboneEnabled;
  }

  public void setServiceMsgBackboneEnabled(Boolean serviceMsgBackboneEnabled) {
    this.serviceMsgBackboneEnabled = serviceMsgBackboneEnabled;
  }

  public Broker serviceRestEventOutgoingConnectionCountThreshold(EventThreshold serviceRestEventOutgoingConnectionCountThreshold) {
    this.serviceRestEventOutgoingConnectionCountThreshold = serviceRestEventOutgoingConnectionCountThreshold;
    return this;
  }

   /**
   * Get serviceRestEventOutgoingConnectionCountThreshold
   * @return serviceRestEventOutgoingConnectionCountThreshold
  **/
  @ApiModelProperty(value = "")
  public EventThreshold getServiceRestEventOutgoingConnectionCountThreshold() {
    return serviceRestEventOutgoingConnectionCountThreshold;
  }

  public void setServiceRestEventOutgoingConnectionCountThreshold(EventThreshold serviceRestEventOutgoingConnectionCountThreshold) {
    this.serviceRestEventOutgoingConnectionCountThreshold = serviceRestEventOutgoingConnectionCountThreshold;
  }

  public Broker serviceRestIncomingEnabled(Boolean serviceRestIncomingEnabled) {
    this.serviceRestIncomingEnabled = serviceRestIncomingEnabled;
    return this;
  }

   /**
   * Enable or disable the REST service incoming connections on the router. Available since 2.17.
   * @return serviceRestIncomingEnabled
  **/
  @ApiModelProperty(value = "Enable or disable the REST service incoming connections on the router. Available since 2.17.")
  public Boolean isServiceRestIncomingEnabled() {
    return serviceRestIncomingEnabled;
  }

  public void setServiceRestIncomingEnabled(Boolean serviceRestIncomingEnabled) {
    this.serviceRestIncomingEnabled = serviceRestIncomingEnabled;
  }

  public Broker serviceRestOutgoingEnabled(Boolean serviceRestOutgoingEnabled) {
    this.serviceRestOutgoingEnabled = serviceRestOutgoingEnabled;
    return this;
  }

   /**
   * Enable or disable the REST service outgoing connections on the router. Available since 2.17.
   * @return serviceRestOutgoingEnabled
  **/
  @ApiModelProperty(value = "Enable or disable the REST service outgoing connections on the router. Available since 2.17.")
  public Boolean isServiceRestOutgoingEnabled() {
    return serviceRestOutgoingEnabled;
  }

  public void setServiceRestOutgoingEnabled(Boolean serviceRestOutgoingEnabled) {
    this.serviceRestOutgoingEnabled = serviceRestOutgoingEnabled;
  }

  public Broker serviceSempLegacyTimeoutEnabled(Boolean serviceSempLegacyTimeoutEnabled) {
    this.serviceSempLegacyTimeoutEnabled = serviceSempLegacyTimeoutEnabled;
    return this;
  }

   /**
   * Enable or disable extended SEMP timeouts for paged GETs. When a request times out, it returns the current page of content, even if the page is not full.  When enabled, the timeout is 60 seconds. When disabled, the timeout is 5 seconds.  The recommended setting is disabled (no legacy-timeout).  This parameter is intended as a temporary workaround to be used until SEMP clients can handle short pages.  This setting will be removed in a future release. Available since 2.18.
   * @return serviceSempLegacyTimeoutEnabled
  **/
  @ApiModelProperty(value = "Enable or disable extended SEMP timeouts for paged GETs. When a request times out, it returns the current page of content, even if the page is not full.  When enabled, the timeout is 60 seconds. When disabled, the timeout is 5 seconds.  The recommended setting is disabled (no legacy-timeout).  This parameter is intended as a temporary workaround to be used until SEMP clients can handle short pages.  This setting will be removed in a future release. Available since 2.18.")
  public Boolean isServiceSempLegacyTimeoutEnabled() {
    return serviceSempLegacyTimeoutEnabled;
  }

  public void setServiceSempLegacyTimeoutEnabled(Boolean serviceSempLegacyTimeoutEnabled) {
    this.serviceSempLegacyTimeoutEnabled = serviceSempLegacyTimeoutEnabled;
  }

  public Broker serviceSempPlainTextEnabled(Boolean serviceSempPlainTextEnabled) {
    this.serviceSempPlainTextEnabled = serviceSempPlainTextEnabled;
    return this;
  }

   /**
   * Enable or disable plain-text SEMP service. Available since 2.17.
   * @return serviceSempPlainTextEnabled
  **/
  @ApiModelProperty(value = "Enable or disable plain-text SEMP service. Available since 2.17.")
  public Boolean isServiceSempPlainTextEnabled() {
    return serviceSempPlainTextEnabled;
  }

  public void setServiceSempPlainTextEnabled(Boolean serviceSempPlainTextEnabled) {
    this.serviceSempPlainTextEnabled = serviceSempPlainTextEnabled;
  }

  public Broker serviceSempPlainTextListenPort(Long serviceSempPlainTextListenPort) {
    this.serviceSempPlainTextListenPort = serviceSempPlainTextListenPort;
    return this;
  }

   /**
   * The TCP port for plain-text SEMP client connections. Available since 2.17.
   * @return serviceSempPlainTextListenPort
  **/
  @ApiModelProperty(value = "The TCP port for plain-text SEMP client connections. Available since 2.17.")
  public Long getServiceSempPlainTextListenPort() {
    return serviceSempPlainTextListenPort;
  }

  public void setServiceSempPlainTextListenPort(Long serviceSempPlainTextListenPort) {
    this.serviceSempPlainTextListenPort = serviceSempPlainTextListenPort;
  }

  public Broker serviceSempSessionIdleTimeout(Integer serviceSempSessionIdleTimeout) {
    this.serviceSempSessionIdleTimeout = serviceSempSessionIdleTimeout;
    return this;
  }

   /**
   * The session idle timeout, in minutes. Sessions will be invalidated if there is no activity in this period of time. Available since 2.21.
   * @return serviceSempSessionIdleTimeout
  **/
  @ApiModelProperty(value = "The session idle timeout, in minutes. Sessions will be invalidated if there is no activity in this period of time. Available since 2.21.")
  public Integer getServiceSempSessionIdleTimeout() {
    return serviceSempSessionIdleTimeout;
  }

  public void setServiceSempSessionIdleTimeout(Integer serviceSempSessionIdleTimeout) {
    this.serviceSempSessionIdleTimeout = serviceSempSessionIdleTimeout;
  }

  public Broker serviceSempSessionMaxLifetime(Integer serviceSempSessionMaxLifetime) {
    this.serviceSempSessionMaxLifetime = serviceSempSessionMaxLifetime;
    return this;
  }

   /**
   * The maximum lifetime of a session, in minutes. Sessions will be invalidated after this period of time, regardless of activity. Available since 2.21.
   * @return serviceSempSessionMaxLifetime
  **/
  @ApiModelProperty(value = "The maximum lifetime of a session, in minutes. Sessions will be invalidated after this period of time, regardless of activity. Available since 2.21.")
  public Integer getServiceSempSessionMaxLifetime() {
    return serviceSempSessionMaxLifetime;
  }

  public void setServiceSempSessionMaxLifetime(Integer serviceSempSessionMaxLifetime) {
    this.serviceSempSessionMaxLifetime = serviceSempSessionMaxLifetime;
  }

  public Broker serviceSempTlsEnabled(Boolean serviceSempTlsEnabled) {
    this.serviceSempTlsEnabled = serviceSempTlsEnabled;
    return this;
  }

   /**
   * Enable or disable TLS SEMP service. Available since 2.17.
   * @return serviceSempTlsEnabled
  **/
  @ApiModelProperty(value = "Enable or disable TLS SEMP service. Available since 2.17.")
  public Boolean isServiceSempTlsEnabled() {
    return serviceSempTlsEnabled;
  }

  public void setServiceSempTlsEnabled(Boolean serviceSempTlsEnabled) {
    this.serviceSempTlsEnabled = serviceSempTlsEnabled;
  }

  public Broker serviceSempTlsListenPort(Long serviceSempTlsListenPort) {
    this.serviceSempTlsListenPort = serviceSempTlsListenPort;
    return this;
  }

   /**
   * The TCP port for TLS SEMP client connections. Available since 2.17.
   * @return serviceSempTlsListenPort
  **/
  @ApiModelProperty(value = "The TCP port for TLS SEMP client connections. Available since 2.17.")
  public Long getServiceSempTlsListenPort() {
    return serviceSempTlsListenPort;
  }

  public void setServiceSempTlsListenPort(Long serviceSempTlsListenPort) {
    this.serviceSempTlsListenPort = serviceSempTlsListenPort;
  }

  public Broker serviceSmfCompressionListenPort(Long serviceSmfCompressionListenPort) {
    this.serviceSmfCompressionListenPort = serviceSmfCompressionListenPort;
    return this;
  }

   /**
   * TCP port number that SMF clients can use to connect to the broker using raw compression TCP. Available since 2.17.
   * @return serviceSmfCompressionListenPort
  **/
  @ApiModelProperty(value = "TCP port number that SMF clients can use to connect to the broker using raw compression TCP. Available since 2.17.")
  public Long getServiceSmfCompressionListenPort() {
    return serviceSmfCompressionListenPort;
  }

  public void setServiceSmfCompressionListenPort(Long serviceSmfCompressionListenPort) {
    this.serviceSmfCompressionListenPort = serviceSmfCompressionListenPort;
  }

  public Broker serviceSmfEnabled(Boolean serviceSmfEnabled) {
    this.serviceSmfEnabled = serviceSmfEnabled;
    return this;
  }

   /**
   * Enable or disable the SMF service. When disabled new SMF Clients may not connect through the global listen-ports, and all currently connected SMF Clients are immediately disconnected. Available since 2.17.
   * @return serviceSmfEnabled
  **/
  @ApiModelProperty(value = "Enable or disable the SMF service. When disabled new SMF Clients may not connect through the global listen-ports, and all currently connected SMF Clients are immediately disconnected. Available since 2.17.")
  public Boolean isServiceSmfEnabled() {
    return serviceSmfEnabled;
  }

  public void setServiceSmfEnabled(Boolean serviceSmfEnabled) {
    this.serviceSmfEnabled = serviceSmfEnabled;
  }

  public Broker serviceSmfEventConnectionCountThreshold(EventThreshold serviceSmfEventConnectionCountThreshold) {
    this.serviceSmfEventConnectionCountThreshold = serviceSmfEventConnectionCountThreshold;
    return this;
  }

   /**
   * Get serviceSmfEventConnectionCountThreshold
   * @return serviceSmfEventConnectionCountThreshold
  **/
  @ApiModelProperty(value = "")
  public EventThreshold getServiceSmfEventConnectionCountThreshold() {
    return serviceSmfEventConnectionCountThreshold;
  }

  public void setServiceSmfEventConnectionCountThreshold(EventThreshold serviceSmfEventConnectionCountThreshold) {
    this.serviceSmfEventConnectionCountThreshold = serviceSmfEventConnectionCountThreshold;
  }

  public Broker serviceSmfPlainTextListenPort(Long serviceSmfPlainTextListenPort) {
    this.serviceSmfPlainTextListenPort = serviceSmfPlainTextListenPort;
    return this;
  }

   /**
   * TCP port number that SMF clients can use to connect to the broker using raw TCP. Available since 2.17.
   * @return serviceSmfPlainTextListenPort
  **/
  @ApiModelProperty(value = "TCP port number that SMF clients can use to connect to the broker using raw TCP. Available since 2.17.")
  public Long getServiceSmfPlainTextListenPort() {
    return serviceSmfPlainTextListenPort;
  }

  public void setServiceSmfPlainTextListenPort(Long serviceSmfPlainTextListenPort) {
    this.serviceSmfPlainTextListenPort = serviceSmfPlainTextListenPort;
  }

  public Broker serviceSmfRoutingControlListenPort(Long serviceSmfRoutingControlListenPort) {
    this.serviceSmfRoutingControlListenPort = serviceSmfRoutingControlListenPort;
    return this;
  }

   /**
   * TCP port number that SMF clients can use to connect to the broker using raw routing control TCP. Available since 2.17.
   * @return serviceSmfRoutingControlListenPort
  **/
  @ApiModelProperty(value = "TCP port number that SMF clients can use to connect to the broker using raw routing control TCP. Available since 2.17.")
  public Long getServiceSmfRoutingControlListenPort() {
    return serviceSmfRoutingControlListenPort;
  }

  public void setServiceSmfRoutingControlListenPort(Long serviceSmfRoutingControlListenPort) {
    this.serviceSmfRoutingControlListenPort = serviceSmfRoutingControlListenPort;
  }

  public Broker serviceSmfTlsListenPort(Long serviceSmfTlsListenPort) {
    this.serviceSmfTlsListenPort = serviceSmfTlsListenPort;
    return this;
  }

   /**
   * TCP port number that SMF clients can use to connect to the broker using raw TCP over TLS. Available since 2.17.
   * @return serviceSmfTlsListenPort
  **/
  @ApiModelProperty(value = "TCP port number that SMF clients can use to connect to the broker using raw TCP over TLS. Available since 2.17.")
  public Long getServiceSmfTlsListenPort() {
    return serviceSmfTlsListenPort;
  }

  public void setServiceSmfTlsListenPort(Long serviceSmfTlsListenPort) {
    this.serviceSmfTlsListenPort = serviceSmfTlsListenPort;
  }

  public Broker serviceTlsEventConnectionCountThreshold(EventThreshold serviceTlsEventConnectionCountThreshold) {
    this.serviceTlsEventConnectionCountThreshold = serviceTlsEventConnectionCountThreshold;
    return this;
  }

   /**
   * Get serviceTlsEventConnectionCountThreshold
   * @return serviceTlsEventConnectionCountThreshold
  **/
  @ApiModelProperty(value = "")
  public EventThreshold getServiceTlsEventConnectionCountThreshold() {
    return serviceTlsEventConnectionCountThreshold;
  }

  public void setServiceTlsEventConnectionCountThreshold(EventThreshold serviceTlsEventConnectionCountThreshold) {
    this.serviceTlsEventConnectionCountThreshold = serviceTlsEventConnectionCountThreshold;
  }

  public Broker serviceWebTransportEnabled(Boolean serviceWebTransportEnabled) {
    this.serviceWebTransportEnabled = serviceWebTransportEnabled;
    return this;
  }

   /**
   * Enable or disable the web-transport service. When disabled new web-transport Clients may not connect through the global listen-ports, and all currently connected web-transport Clients are immediately disconnected. Available since 2.17.
   * @return serviceWebTransportEnabled
  **/
  @ApiModelProperty(value = "Enable or disable the web-transport service. When disabled new web-transport Clients may not connect through the global listen-ports, and all currently connected web-transport Clients are immediately disconnected. Available since 2.17.")
  public Boolean isServiceWebTransportEnabled() {
    return serviceWebTransportEnabled;
  }

  public void setServiceWebTransportEnabled(Boolean serviceWebTransportEnabled) {
    this.serviceWebTransportEnabled = serviceWebTransportEnabled;
  }

  public Broker serviceWebTransportPlainTextListenPort(Long serviceWebTransportPlainTextListenPort) {
    this.serviceWebTransportPlainTextListenPort = serviceWebTransportPlainTextListenPort;
    return this;
  }

   /**
   * The TCP port for plain-text WEB client connections. Available since 2.17.
   * @return serviceWebTransportPlainTextListenPort
  **/
  @ApiModelProperty(value = "The TCP port for plain-text WEB client connections. Available since 2.17.")
  public Long getServiceWebTransportPlainTextListenPort() {
    return serviceWebTransportPlainTextListenPort;
  }

  public void setServiceWebTransportPlainTextListenPort(Long serviceWebTransportPlainTextListenPort) {
    this.serviceWebTransportPlainTextListenPort = serviceWebTransportPlainTextListenPort;
  }

  public Broker serviceWebTransportTlsListenPort(Long serviceWebTransportTlsListenPort) {
    this.serviceWebTransportTlsListenPort = serviceWebTransportTlsListenPort;
    return this;
  }

   /**
   * The TCP port for TLS WEB client connections. Available since 2.17.
   * @return serviceWebTransportTlsListenPort
  **/
  @ApiModelProperty(value = "The TCP port for TLS WEB client connections. Available since 2.17.")
  public Long getServiceWebTransportTlsListenPort() {
    return serviceWebTransportTlsListenPort;
  }

  public void setServiceWebTransportTlsListenPort(Long serviceWebTransportTlsListenPort) {
    this.serviceWebTransportTlsListenPort = serviceWebTransportTlsListenPort;
  }

  public Broker serviceWebTransportWebUrlSuffix(String serviceWebTransportWebUrlSuffix) {
    this.serviceWebTransportWebUrlSuffix = serviceWebTransportWebUrlSuffix;
    return this;
  }

   /**
   * Used to specify the Web URL suffix that will be used by Web clients when communicating with the broker. Available since 2.17.
   * @return serviceWebTransportWebUrlSuffix
  **/
  @ApiModelProperty(value = "Used to specify the Web URL suffix that will be used by Web clients when communicating with the broker. Available since 2.17.")
  public String getServiceWebTransportWebUrlSuffix() {
    return serviceWebTransportWebUrlSuffix;
  }

  public void setServiceWebTransportWebUrlSuffix(String serviceWebTransportWebUrlSuffix) {
    this.serviceWebTransportWebUrlSuffix = serviceWebTransportWebUrlSuffix;
  }

  public Broker tlsBlockVersion10Enabled(Boolean tlsBlockVersion10Enabled) {
    this.tlsBlockVersion10Enabled = tlsBlockVersion10Enabled;
    return this;
  }

   /**
   * Indicates whether incoming TLS version 1.0 connections are blocked. When blocked, existing TLS 1.0 connections from Clients and SEMP users remain connected while new connections are blocked. Note that support for TLS 1.0 will eventually be discontinued, at which time TLS 1.0 connections will be blocked regardless of this setting.
   * @return tlsBlockVersion10Enabled
  **/
  @ApiModelProperty(value = "Indicates whether incoming TLS version 1.0 connections are blocked. When blocked, existing TLS 1.0 connections from Clients and SEMP users remain connected while new connections are blocked. Note that support for TLS 1.0 will eventually be discontinued, at which time TLS 1.0 connections will be blocked regardless of this setting.")
  public Boolean isTlsBlockVersion10Enabled() {
    return tlsBlockVersion10Enabled;
  }

  public void setTlsBlockVersion10Enabled(Boolean tlsBlockVersion10Enabled) {
    this.tlsBlockVersion10Enabled = tlsBlockVersion10Enabled;
  }

  public Broker tlsBlockVersion11Enabled(Boolean tlsBlockVersion11Enabled) {
    this.tlsBlockVersion11Enabled = tlsBlockVersion11Enabled;
    return this;
  }

   /**
   * Indicates whether TLS version 1.1 connections are blocked. When blocked, all existing incoming and outgoing TLS 1.1 connections with Clients, SEMP users, and LDAP servers remain connected while new connections are blocked. Note that support for TLS 1.1 will eventually be discontinued, at which time TLS 1.1 connections will be blocked regardless of this setting.
   * @return tlsBlockVersion11Enabled
  **/
  @ApiModelProperty(value = "Indicates whether TLS version 1.1 connections are blocked. When blocked, all existing incoming and outgoing TLS 1.1 connections with Clients, SEMP users, and LDAP servers remain connected while new connections are blocked. Note that support for TLS 1.1 will eventually be discontinued, at which time TLS 1.1 connections will be blocked regardless of this setting.")
  public Boolean isTlsBlockVersion11Enabled() {
    return tlsBlockVersion11Enabled;
  }

  public void setTlsBlockVersion11Enabled(Boolean tlsBlockVersion11Enabled) {
    this.tlsBlockVersion11Enabled = tlsBlockVersion11Enabled;
  }

  public Broker tlsCipherSuiteManagementDefaultList(String tlsCipherSuiteManagementDefaultList) {
    this.tlsCipherSuiteManagementDefaultList = tlsCipherSuiteManagementDefaultList;
    return this;
  }

   /**
   * The colon-separated list of default cipher suites for TLS management connections.
   * @return tlsCipherSuiteManagementDefaultList
  **/
  @ApiModelProperty(value = "The colon-separated list of default cipher suites for TLS management connections.")
  public String getTlsCipherSuiteManagementDefaultList() {
    return tlsCipherSuiteManagementDefaultList;
  }

  public void setTlsCipherSuiteManagementDefaultList(String tlsCipherSuiteManagementDefaultList) {
    this.tlsCipherSuiteManagementDefaultList = tlsCipherSuiteManagementDefaultList;
  }

  public Broker tlsCipherSuiteManagementList(String tlsCipherSuiteManagementList) {
    this.tlsCipherSuiteManagementList = tlsCipherSuiteManagementList;
    return this;
  }

   /**
   * The colon-separated list of cipher suites used for TLS management connections (e.g. SEMP, LDAP). The value \&quot;default\&quot; implies all supported suites ordered from most secure to least secure.
   * @return tlsCipherSuiteManagementList
  **/
  @ApiModelProperty(value = "The colon-separated list of cipher suites used for TLS management connections (e.g. SEMP, LDAP). The value \"default\" implies all supported suites ordered from most secure to least secure.")
  public String getTlsCipherSuiteManagementList() {
    return tlsCipherSuiteManagementList;
  }

  public void setTlsCipherSuiteManagementList(String tlsCipherSuiteManagementList) {
    this.tlsCipherSuiteManagementList = tlsCipherSuiteManagementList;
  }

  public Broker tlsCipherSuiteManagementSupportedList(String tlsCipherSuiteManagementSupportedList) {
    this.tlsCipherSuiteManagementSupportedList = tlsCipherSuiteManagementSupportedList;
    return this;
  }

   /**
   * The colon-separated list of supported cipher suites for TLS management connections.
   * @return tlsCipherSuiteManagementSupportedList
  **/
  @ApiModelProperty(value = "The colon-separated list of supported cipher suites for TLS management connections.")
  public String getTlsCipherSuiteManagementSupportedList() {
    return tlsCipherSuiteManagementSupportedList;
  }

  public void setTlsCipherSuiteManagementSupportedList(String tlsCipherSuiteManagementSupportedList) {
    this.tlsCipherSuiteManagementSupportedList = tlsCipherSuiteManagementSupportedList;
  }

  public Broker tlsCipherSuiteMsgBackboneDefaultList(String tlsCipherSuiteMsgBackboneDefaultList) {
    this.tlsCipherSuiteMsgBackboneDefaultList = tlsCipherSuiteMsgBackboneDefaultList;
    return this;
  }

   /**
   * The colon-separated list of default cipher suites for TLS data connections.
   * @return tlsCipherSuiteMsgBackboneDefaultList
  **/
  @ApiModelProperty(value = "The colon-separated list of default cipher suites for TLS data connections.")
  public String getTlsCipherSuiteMsgBackboneDefaultList() {
    return tlsCipherSuiteMsgBackboneDefaultList;
  }

  public void setTlsCipherSuiteMsgBackboneDefaultList(String tlsCipherSuiteMsgBackboneDefaultList) {
    this.tlsCipherSuiteMsgBackboneDefaultList = tlsCipherSuiteMsgBackboneDefaultList;
  }

  public Broker tlsCipherSuiteMsgBackboneList(String tlsCipherSuiteMsgBackboneList) {
    this.tlsCipherSuiteMsgBackboneList = tlsCipherSuiteMsgBackboneList;
    return this;
  }

   /**
   * The colon-separated list of cipher suites used for TLS data connections (e.g. client pub/sub). The value \&quot;default\&quot; implies all supported suites ordered from most secure to least secure.
   * @return tlsCipherSuiteMsgBackboneList
  **/
  @ApiModelProperty(value = "The colon-separated list of cipher suites used for TLS data connections (e.g. client pub/sub). The value \"default\" implies all supported suites ordered from most secure to least secure.")
  public String getTlsCipherSuiteMsgBackboneList() {
    return tlsCipherSuiteMsgBackboneList;
  }

  public void setTlsCipherSuiteMsgBackboneList(String tlsCipherSuiteMsgBackboneList) {
    this.tlsCipherSuiteMsgBackboneList = tlsCipherSuiteMsgBackboneList;
  }

  public Broker tlsCipherSuiteMsgBackboneSupportedList(String tlsCipherSuiteMsgBackboneSupportedList) {
    this.tlsCipherSuiteMsgBackboneSupportedList = tlsCipherSuiteMsgBackboneSupportedList;
    return this;
  }

   /**
   * The colon-separated list of supported cipher suites for TLS data connections.
   * @return tlsCipherSuiteMsgBackboneSupportedList
  **/
  @ApiModelProperty(value = "The colon-separated list of supported cipher suites for TLS data connections.")
  public String getTlsCipherSuiteMsgBackboneSupportedList() {
    return tlsCipherSuiteMsgBackboneSupportedList;
  }

  public void setTlsCipherSuiteMsgBackboneSupportedList(String tlsCipherSuiteMsgBackboneSupportedList) {
    this.tlsCipherSuiteMsgBackboneSupportedList = tlsCipherSuiteMsgBackboneSupportedList;
  }

  public Broker tlsCipherSuiteSecureShellDefaultList(String tlsCipherSuiteSecureShellDefaultList) {
    this.tlsCipherSuiteSecureShellDefaultList = tlsCipherSuiteSecureShellDefaultList;
    return this;
  }

   /**
   * The colon-separated list of default cipher suites for TLS secure shell connections.
   * @return tlsCipherSuiteSecureShellDefaultList
  **/
  @ApiModelProperty(value = "The colon-separated list of default cipher suites for TLS secure shell connections.")
  public String getTlsCipherSuiteSecureShellDefaultList() {
    return tlsCipherSuiteSecureShellDefaultList;
  }

  public void setTlsCipherSuiteSecureShellDefaultList(String tlsCipherSuiteSecureShellDefaultList) {
    this.tlsCipherSuiteSecureShellDefaultList = tlsCipherSuiteSecureShellDefaultList;
  }

  public Broker tlsCipherSuiteSecureShellList(String tlsCipherSuiteSecureShellList) {
    this.tlsCipherSuiteSecureShellList = tlsCipherSuiteSecureShellList;
    return this;
  }

   /**
   * The colon-separated list of cipher suites used for TLS secure shell connections (e.g. SSH, SFTP, SCP). The value \&quot;default\&quot; implies all supported suites ordered from most secure to least secure.
   * @return tlsCipherSuiteSecureShellList
  **/
  @ApiModelProperty(value = "The colon-separated list of cipher suites used for TLS secure shell connections (e.g. SSH, SFTP, SCP). The value \"default\" implies all supported suites ordered from most secure to least secure.")
  public String getTlsCipherSuiteSecureShellList() {
    return tlsCipherSuiteSecureShellList;
  }

  public void setTlsCipherSuiteSecureShellList(String tlsCipherSuiteSecureShellList) {
    this.tlsCipherSuiteSecureShellList = tlsCipherSuiteSecureShellList;
  }

  public Broker tlsCipherSuiteSecureShellSupportedList(String tlsCipherSuiteSecureShellSupportedList) {
    this.tlsCipherSuiteSecureShellSupportedList = tlsCipherSuiteSecureShellSupportedList;
    return this;
  }

   /**
   * The colon-separated list of supported cipher suites for TLS secure shell connections.
   * @return tlsCipherSuiteSecureShellSupportedList
  **/
  @ApiModelProperty(value = "The colon-separated list of supported cipher suites for TLS secure shell connections.")
  public String getTlsCipherSuiteSecureShellSupportedList() {
    return tlsCipherSuiteSecureShellSupportedList;
  }

  public void setTlsCipherSuiteSecureShellSupportedList(String tlsCipherSuiteSecureShellSupportedList) {
    this.tlsCipherSuiteSecureShellSupportedList = tlsCipherSuiteSecureShellSupportedList;
  }

  public Broker tlsCrimeExploitProtectionEnabled(Boolean tlsCrimeExploitProtectionEnabled) {
    this.tlsCrimeExploitProtectionEnabled = tlsCrimeExploitProtectionEnabled;
    return this;
  }

   /**
   * Indicates whether protection against the CRIME exploit is enabled. When enabled, TLS+compressed messaging performance is degraded. This protection should only be disabled if sufficient ACL and authentication features are being employed such that a potential attacker does not have sufficient access to trigger the exploit.
   * @return tlsCrimeExploitProtectionEnabled
  **/
  @ApiModelProperty(value = "Indicates whether protection against the CRIME exploit is enabled. When enabled, TLS+compressed messaging performance is degraded. This protection should only be disabled if sufficient ACL and authentication features are being employed such that a potential attacker does not have sufficient access to trigger the exploit.")
  public Boolean isTlsCrimeExploitProtectionEnabled() {
    return tlsCrimeExploitProtectionEnabled;
  }

  public void setTlsCrimeExploitProtectionEnabled(Boolean tlsCrimeExploitProtectionEnabled) {
    this.tlsCrimeExploitProtectionEnabled = tlsCrimeExploitProtectionEnabled;
  }

  public Broker tlsStandardDomainCertificateAuthoritiesEnabled(Boolean tlsStandardDomainCertificateAuthoritiesEnabled) {
    this.tlsStandardDomainCertificateAuthoritiesEnabled = tlsStandardDomainCertificateAuthoritiesEnabled;
    return this;
  }

   /**
   * Enable or disable the standard domain certificate authority list. Available since 2.19.
   * @return tlsStandardDomainCertificateAuthoritiesEnabled
  **/
  @ApiModelProperty(value = "Enable or disable the standard domain certificate authority list. Available since 2.19.")
  public Boolean isTlsStandardDomainCertificateAuthoritiesEnabled() {
    return tlsStandardDomainCertificateAuthoritiesEnabled;
  }

  public void setTlsStandardDomainCertificateAuthoritiesEnabled(Boolean tlsStandardDomainCertificateAuthoritiesEnabled) {
    this.tlsStandardDomainCertificateAuthoritiesEnabled = tlsStandardDomainCertificateAuthoritiesEnabled;
  }

  public Broker tlsTicketLifetime(Integer tlsTicketLifetime) {
    this.tlsTicketLifetime = tlsTicketLifetime;
    return this;
  }

   /**
   * The TLS ticket lifetime in seconds. When a client connects with TLS, a session with a session ticket is created using the TLS ticket lifetime which determines how long the client has to resume the session.
   * @return tlsTicketLifetime
  **/
  @ApiModelProperty(value = "The TLS ticket lifetime in seconds. When a client connects with TLS, a session with a session ticket is created using the TLS ticket lifetime which determines how long the client has to resume the session.")
  public Integer getTlsTicketLifetime() {
    return tlsTicketLifetime;
  }

  public void setTlsTicketLifetime(Integer tlsTicketLifetime) {
    this.tlsTicketLifetime = tlsTicketLifetime;
  }

  public Broker tlsVersionSupportedList(String tlsVersionSupportedList) {
    this.tlsVersionSupportedList = tlsVersionSupportedList;
    return this;
  }

   /**
   * The comma-separated list of supported TLS versions.
   * @return tlsVersionSupportedList
  **/
  @ApiModelProperty(value = "The comma-separated list of supported TLS versions.")
  public String getTlsVersionSupportedList() {
    return tlsVersionSupportedList;
  }

  public void setTlsVersionSupportedList(String tlsVersionSupportedList) {
    this.tlsVersionSupportedList = tlsVersionSupportedList;
  }

  public Broker txByteCount(Long txByteCount) {
    this.txByteCount = txByteCount;
    return this;
  }

   /**
   * The amount of messages transmitted to clients by the Broker, in bytes (B). Available since 2.14.
   * @return txByteCount
  **/
  @ApiModelProperty(value = "The amount of messages transmitted to clients by the Broker, in bytes (B). Available since 2.14.")
  public Long getTxByteCount() {
    return txByteCount;
  }

  public void setTxByteCount(Long txByteCount) {
    this.txByteCount = txByteCount;
  }

  public Broker txByteRate(Long txByteRate) {
    this.txByteRate = txByteRate;
    return this;
  }

   /**
   * The current message rate transmitted by the Broker, in bytes per second (B/sec). Available since 2.14.
   * @return txByteRate
  **/
  @ApiModelProperty(value = "The current message rate transmitted by the Broker, in bytes per second (B/sec). Available since 2.14.")
  public Long getTxByteRate() {
    return txByteRate;
  }

  public void setTxByteRate(Long txByteRate) {
    this.txByteRate = txByteRate;
  }

  public Broker txCompressedByteCount(Long txCompressedByteCount) {
    this.txCompressedByteCount = txCompressedByteCount;
    return this;
  }

   /**
   * The amount of compressed messages transmitted by the Broker, in bytes (B). Available since 2.14.
   * @return txCompressedByteCount
  **/
  @ApiModelProperty(value = "The amount of compressed messages transmitted by the Broker, in bytes (B). Available since 2.14.")
  public Long getTxCompressedByteCount() {
    return txCompressedByteCount;
  }

  public void setTxCompressedByteCount(Long txCompressedByteCount) {
    this.txCompressedByteCount = txCompressedByteCount;
  }

  public Broker txCompressedByteRate(Long txCompressedByteRate) {
    this.txCompressedByteRate = txCompressedByteRate;
    return this;
  }

   /**
   * The current compressed message rate transmitted by the Broker, in bytes per second (B/sec). Available since 2.14.
   * @return txCompressedByteRate
  **/
  @ApiModelProperty(value = "The current compressed message rate transmitted by the Broker, in bytes per second (B/sec). Available since 2.14.")
  public Long getTxCompressedByteRate() {
    return txCompressedByteRate;
  }

  public void setTxCompressedByteRate(Long txCompressedByteRate) {
    this.txCompressedByteRate = txCompressedByteRate;
  }

  public Broker txCompressionRatio(String txCompressionRatio) {
    this.txCompressionRatio = txCompressionRatio;
    return this;
  }

   /**
   * The compression ratio for messages transmitted by the Broker. Available since 2.14.
   * @return txCompressionRatio
  **/
  @ApiModelProperty(value = "The compression ratio for messages transmitted by the Broker. Available since 2.14.")
  public String getTxCompressionRatio() {
    return txCompressionRatio;
  }

  public void setTxCompressionRatio(String txCompressionRatio) {
    this.txCompressionRatio = txCompressionRatio;
  }

  public Broker txMsgCount(Long txMsgCount) {
    this.txMsgCount = txMsgCount;
    return this;
  }

   /**
   * The number of messages transmitted to clients by the Broker. Available since 2.14.
   * @return txMsgCount
  **/
  @ApiModelProperty(value = "The number of messages transmitted to clients by the Broker. Available since 2.14.")
  public Long getTxMsgCount() {
    return txMsgCount;
  }

  public void setTxMsgCount(Long txMsgCount) {
    this.txMsgCount = txMsgCount;
  }

  public Broker txMsgRate(Long txMsgRate) {
    this.txMsgRate = txMsgRate;
    return this;
  }

   /**
   * The current message rate transmitted by the Broker, in messages per second (msg/sec). Available since 2.14.
   * @return txMsgRate
  **/
  @ApiModelProperty(value = "The current message rate transmitted by the Broker, in messages per second (msg/sec). Available since 2.14.")
  public Long getTxMsgRate() {
    return txMsgRate;
  }

  public void setTxMsgRate(Long txMsgRate) {
    this.txMsgRate = txMsgRate;
  }

  public Broker txUncompressedByteCount(Long txUncompressedByteCount) {
    this.txUncompressedByteCount = txUncompressedByteCount;
    return this;
  }

   /**
   * The amount of uncompressed messages transmitted by the Broker, in bytes (B). Available since 2.14.
   * @return txUncompressedByteCount
  **/
  @ApiModelProperty(value = "The amount of uncompressed messages transmitted by the Broker, in bytes (B). Available since 2.14.")
  public Long getTxUncompressedByteCount() {
    return txUncompressedByteCount;
  }

  public void setTxUncompressedByteCount(Long txUncompressedByteCount) {
    this.txUncompressedByteCount = txUncompressedByteCount;
  }

  public Broker txUncompressedByteRate(Long txUncompressedByteRate) {
    this.txUncompressedByteRate = txUncompressedByteRate;
    return this;
  }

   /**
   * The current uncompressed message rate transmitted by the Broker, in bytes per second (B/sec). Available since 2.14.
   * @return txUncompressedByteRate
  **/
  @ApiModelProperty(value = "The current uncompressed message rate transmitted by the Broker, in bytes per second (B/sec). Available since 2.14.")
  public Long getTxUncompressedByteRate() {
    return txUncompressedByteRate;
  }

  public void setTxUncompressedByteRate(Long txUncompressedByteRate) {
    this.txUncompressedByteRate = txUncompressedByteRate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Broker broker = (Broker) o;
    return Objects.equals(this.authClientCertRevocationCheckMode, broker.authClientCertRevocationCheckMode) &&
        Objects.equals(this.averageRxByteRate, broker.averageRxByteRate) &&
        Objects.equals(this.averageRxCompressedByteRate, broker.averageRxCompressedByteRate) &&
        Objects.equals(this.averageRxMsgRate, broker.averageRxMsgRate) &&
        Objects.equals(this.averageRxUncompressedByteRate, broker.averageRxUncompressedByteRate) &&
        Objects.equals(this.averageTxByteRate, broker.averageTxByteRate) &&
        Objects.equals(this.averageTxCompressedByteRate, broker.averageTxCompressedByteRate) &&
        Objects.equals(this.averageTxMsgRate, broker.averageTxMsgRate) &&
        Objects.equals(this.averageTxUncompressedByteRate, broker.averageTxUncompressedByteRate) &&
        Objects.equals(this.configSyncAuthenticationClientCertMaxChainDepth, broker.configSyncAuthenticationClientCertMaxChainDepth) &&
        Objects.equals(this.configSyncAuthenticationClientCertValidateDateEnabled, broker.configSyncAuthenticationClientCertValidateDateEnabled) &&
        Objects.equals(this.configSyncClientProfileTcpInitialCongestionWindow, broker.configSyncClientProfileTcpInitialCongestionWindow) &&
        Objects.equals(this.configSyncClientProfileTcpKeepaliveCount, broker.configSyncClientProfileTcpKeepaliveCount) &&
        Objects.equals(this.configSyncClientProfileTcpKeepaliveIdle, broker.configSyncClientProfileTcpKeepaliveIdle) &&
        Objects.equals(this.configSyncClientProfileTcpKeepaliveInterval, broker.configSyncClientProfileTcpKeepaliveInterval) &&
        Objects.equals(this.configSyncClientProfileTcpMaxWindow, broker.configSyncClientProfileTcpMaxWindow) &&
        Objects.equals(this.configSyncClientProfileTcpMss, broker.configSyncClientProfileTcpMss) &&
        Objects.equals(this.configSyncEnabled, broker.configSyncEnabled) &&
        Objects.equals(this.configSyncLastFailureReason, broker.configSyncLastFailureReason) &&
        Objects.equals(this.configSyncSynchronizeUsernameEnabled, broker.configSyncSynchronizeUsernameEnabled) &&
        Objects.equals(this.configSyncTlsEnabled, broker.configSyncTlsEnabled) &&
        Objects.equals(this.configSyncUp, broker.configSyncUp) &&
        Objects.equals(this.cspfVersion, broker.cspfVersion) &&
        Objects.equals(this.guaranteedMsgingDefragmentationEstimatedFragmentation, broker.guaranteedMsgingDefragmentationEstimatedFragmentation) &&
        Objects.equals(this.guaranteedMsgingDefragmentationEstimatedRecoverableSpace, broker.guaranteedMsgingDefragmentationEstimatedRecoverableSpace) &&
        Objects.equals(this.guaranteedMsgingDefragmentationLastCompletedOn, broker.guaranteedMsgingDefragmentationLastCompletedOn) &&
        Objects.equals(this.guaranteedMsgingDefragmentationLastCompletionPercentage, broker.guaranteedMsgingDefragmentationLastCompletionPercentage) &&
        Objects.equals(this.guaranteedMsgingDefragmentationLastExitCondition, broker.guaranteedMsgingDefragmentationLastExitCondition) &&
        Objects.equals(this.guaranteedMsgingDefragmentationLastExitConditionInformation, broker.guaranteedMsgingDefragmentationLastExitConditionInformation) &&
        Objects.equals(this.guaranteedMsgingDefragmentationStatus, broker.guaranteedMsgingDefragmentationStatus) &&
        Objects.equals(this.guaranteedMsgingDefragmentationStatusActiveCompletionPercentage, broker.guaranteedMsgingDefragmentationStatusActiveCompletionPercentage) &&
        Objects.equals(this.guaranteedMsgingDiskArrayWwn, broker.guaranteedMsgingDiskArrayWwn) &&
        Objects.equals(this.guaranteedMsgingDiskLocation, broker.guaranteedMsgingDiskLocation) &&
        Objects.equals(this.guaranteedMsgingEnabled, broker.guaranteedMsgingEnabled) &&
        Objects.equals(this.guaranteedMsgingEventCacheUsageThreshold, broker.guaranteedMsgingEventCacheUsageThreshold) &&
        Objects.equals(this.guaranteedMsgingEventDeliveredUnackedThreshold, broker.guaranteedMsgingEventDeliveredUnackedThreshold) &&
        Objects.equals(this.guaranteedMsgingEventDiskUsageThreshold, broker.guaranteedMsgingEventDiskUsageThreshold) &&
        Objects.equals(this.guaranteedMsgingEventEgressFlowCountThreshold, broker.guaranteedMsgingEventEgressFlowCountThreshold) &&
        Objects.equals(this.guaranteedMsgingEventEndpointCountThreshold, broker.guaranteedMsgingEventEndpointCountThreshold) &&
        Objects.equals(this.guaranteedMsgingEventIngressFlowCountThreshold, broker.guaranteedMsgingEventIngressFlowCountThreshold) &&
        Objects.equals(this.guaranteedMsgingEventMsgCountThreshold, broker.guaranteedMsgingEventMsgCountThreshold) &&
        Objects.equals(this.guaranteedMsgingEventMsgSpoolFileCountThreshold, broker.guaranteedMsgingEventMsgSpoolFileCountThreshold) &&
        Objects.equals(this.guaranteedMsgingEventMsgSpoolUsageThreshold, broker.guaranteedMsgingEventMsgSpoolUsageThreshold) &&
        Objects.equals(this.guaranteedMsgingEventTransactedSessionCountThreshold, broker.guaranteedMsgingEventTransactedSessionCountThreshold) &&
        Objects.equals(this.guaranteedMsgingEventTransactedSessionResourceCountThreshold, broker.guaranteedMsgingEventTransactedSessionResourceCountThreshold) &&
        Objects.equals(this.guaranteedMsgingEventTransactionCountThreshold, broker.guaranteedMsgingEventTransactionCountThreshold) &&
        Objects.equals(this.guaranteedMsgingMaxCacheUsage, broker.guaranteedMsgingMaxCacheUsage) &&
        Objects.equals(this.guaranteedMsgingMaxMsgSpoolUsage, broker.guaranteedMsgingMaxMsgSpoolUsage) &&
        Objects.equals(this.guaranteedMsgingOperationalStatus, broker.guaranteedMsgingOperationalStatus) &&
        Objects.equals(this.guaranteedMsgingTransactionReplicationCompatibilityMode, broker.guaranteedMsgingTransactionReplicationCompatibilityMode) &&
        Objects.equals(this.guaranteedMsgingVirtualRouterWhenActiveActive, broker.guaranteedMsgingVirtualRouterWhenActiveActive) &&
        Objects.equals(this.rxByteCount, broker.rxByteCount) &&
        Objects.equals(this.rxByteRate, broker.rxByteRate) &&
        Objects.equals(this.rxCompressedByteCount, broker.rxCompressedByteCount) &&
        Objects.equals(this.rxCompressedByteRate, broker.rxCompressedByteRate) &&
        Objects.equals(this.rxCompressionRatio, broker.rxCompressionRatio) &&
        Objects.equals(this.rxMsgCount, broker.rxMsgCount) &&
        Objects.equals(this.rxMsgRate, broker.rxMsgRate) &&
        Objects.equals(this.rxUncompressedByteCount, broker.rxUncompressedByteCount) &&
        Objects.equals(this.rxUncompressedByteRate, broker.rxUncompressedByteRate) &&
        Objects.equals(this.serviceAmqpEnabled, broker.serviceAmqpEnabled) &&
        Objects.equals(this.serviceAmqpTlsListenPort, broker.serviceAmqpTlsListenPort) &&
        Objects.equals(this.serviceEventConnectionCountThreshold, broker.serviceEventConnectionCountThreshold) &&
        Objects.equals(this.serviceHealthCheckEnabled, broker.serviceHealthCheckEnabled) &&
        Objects.equals(this.serviceHealthCheckListenPort, broker.serviceHealthCheckListenPort) &&
        Objects.equals(this.serviceMqttEnabled, broker.serviceMqttEnabled) &&
        Objects.equals(this.serviceMsgBackboneEnabled, broker.serviceMsgBackboneEnabled) &&
        Objects.equals(this.serviceRestEventOutgoingConnectionCountThreshold, broker.serviceRestEventOutgoingConnectionCountThreshold) &&
        Objects.equals(this.serviceRestIncomingEnabled, broker.serviceRestIncomingEnabled) &&
        Objects.equals(this.serviceRestOutgoingEnabled, broker.serviceRestOutgoingEnabled) &&
        Objects.equals(this.serviceSempLegacyTimeoutEnabled, broker.serviceSempLegacyTimeoutEnabled) &&
        Objects.equals(this.serviceSempPlainTextEnabled, broker.serviceSempPlainTextEnabled) &&
        Objects.equals(this.serviceSempPlainTextListenPort, broker.serviceSempPlainTextListenPort) &&
        Objects.equals(this.serviceSempSessionIdleTimeout, broker.serviceSempSessionIdleTimeout) &&
        Objects.equals(this.serviceSempSessionMaxLifetime, broker.serviceSempSessionMaxLifetime) &&
        Objects.equals(this.serviceSempTlsEnabled, broker.serviceSempTlsEnabled) &&
        Objects.equals(this.serviceSempTlsListenPort, broker.serviceSempTlsListenPort) &&
        Objects.equals(this.serviceSmfCompressionListenPort, broker.serviceSmfCompressionListenPort) &&
        Objects.equals(this.serviceSmfEnabled, broker.serviceSmfEnabled) &&
        Objects.equals(this.serviceSmfEventConnectionCountThreshold, broker.serviceSmfEventConnectionCountThreshold) &&
        Objects.equals(this.serviceSmfPlainTextListenPort, broker.serviceSmfPlainTextListenPort) &&
        Objects.equals(this.serviceSmfRoutingControlListenPort, broker.serviceSmfRoutingControlListenPort) &&
        Objects.equals(this.serviceSmfTlsListenPort, broker.serviceSmfTlsListenPort) &&
        Objects.equals(this.serviceTlsEventConnectionCountThreshold, broker.serviceTlsEventConnectionCountThreshold) &&
        Objects.equals(this.serviceWebTransportEnabled, broker.serviceWebTransportEnabled) &&
        Objects.equals(this.serviceWebTransportPlainTextListenPort, broker.serviceWebTransportPlainTextListenPort) &&
        Objects.equals(this.serviceWebTransportTlsListenPort, broker.serviceWebTransportTlsListenPort) &&
        Objects.equals(this.serviceWebTransportWebUrlSuffix, broker.serviceWebTransportWebUrlSuffix) &&
        Objects.equals(this.tlsBlockVersion10Enabled, broker.tlsBlockVersion10Enabled) &&
        Objects.equals(this.tlsBlockVersion11Enabled, broker.tlsBlockVersion11Enabled) &&
        Objects.equals(this.tlsCipherSuiteManagementDefaultList, broker.tlsCipherSuiteManagementDefaultList) &&
        Objects.equals(this.tlsCipherSuiteManagementList, broker.tlsCipherSuiteManagementList) &&
        Objects.equals(this.tlsCipherSuiteManagementSupportedList, broker.tlsCipherSuiteManagementSupportedList) &&
        Objects.equals(this.tlsCipherSuiteMsgBackboneDefaultList, broker.tlsCipherSuiteMsgBackboneDefaultList) &&
        Objects.equals(this.tlsCipherSuiteMsgBackboneList, broker.tlsCipherSuiteMsgBackboneList) &&
        Objects.equals(this.tlsCipherSuiteMsgBackboneSupportedList, broker.tlsCipherSuiteMsgBackboneSupportedList) &&
        Objects.equals(this.tlsCipherSuiteSecureShellDefaultList, broker.tlsCipherSuiteSecureShellDefaultList) &&
        Objects.equals(this.tlsCipherSuiteSecureShellList, broker.tlsCipherSuiteSecureShellList) &&
        Objects.equals(this.tlsCipherSuiteSecureShellSupportedList, broker.tlsCipherSuiteSecureShellSupportedList) &&
        Objects.equals(this.tlsCrimeExploitProtectionEnabled, broker.tlsCrimeExploitProtectionEnabled) &&
        Objects.equals(this.tlsStandardDomainCertificateAuthoritiesEnabled, broker.tlsStandardDomainCertificateAuthoritiesEnabled) &&
        Objects.equals(this.tlsTicketLifetime, broker.tlsTicketLifetime) &&
        Objects.equals(this.tlsVersionSupportedList, broker.tlsVersionSupportedList) &&
        Objects.equals(this.txByteCount, broker.txByteCount) &&
        Objects.equals(this.txByteRate, broker.txByteRate) &&
        Objects.equals(this.txCompressedByteCount, broker.txCompressedByteCount) &&
        Objects.equals(this.txCompressedByteRate, broker.txCompressedByteRate) &&
        Objects.equals(this.txCompressionRatio, broker.txCompressionRatio) &&
        Objects.equals(this.txMsgCount, broker.txMsgCount) &&
        Objects.equals(this.txMsgRate, broker.txMsgRate) &&
        Objects.equals(this.txUncompressedByteCount, broker.txUncompressedByteCount) &&
        Objects.equals(this.txUncompressedByteRate, broker.txUncompressedByteRate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authClientCertRevocationCheckMode, averageRxByteRate, averageRxCompressedByteRate, averageRxMsgRate, averageRxUncompressedByteRate, averageTxByteRate, averageTxCompressedByteRate, averageTxMsgRate, averageTxUncompressedByteRate, configSyncAuthenticationClientCertMaxChainDepth, configSyncAuthenticationClientCertValidateDateEnabled, configSyncClientProfileTcpInitialCongestionWindow, configSyncClientProfileTcpKeepaliveCount, configSyncClientProfileTcpKeepaliveIdle, configSyncClientProfileTcpKeepaliveInterval, configSyncClientProfileTcpMaxWindow, configSyncClientProfileTcpMss, configSyncEnabled, configSyncLastFailureReason, configSyncSynchronizeUsernameEnabled, configSyncTlsEnabled, configSyncUp, cspfVersion, guaranteedMsgingDefragmentationEstimatedFragmentation, guaranteedMsgingDefragmentationEstimatedRecoverableSpace, guaranteedMsgingDefragmentationLastCompletedOn, guaranteedMsgingDefragmentationLastCompletionPercentage, guaranteedMsgingDefragmentationLastExitCondition, guaranteedMsgingDefragmentationLastExitConditionInformation, guaranteedMsgingDefragmentationStatus, guaranteedMsgingDefragmentationStatusActiveCompletionPercentage, guaranteedMsgingDiskArrayWwn, guaranteedMsgingDiskLocation, guaranteedMsgingEnabled, guaranteedMsgingEventCacheUsageThreshold, guaranteedMsgingEventDeliveredUnackedThreshold, guaranteedMsgingEventDiskUsageThreshold, guaranteedMsgingEventEgressFlowCountThreshold, guaranteedMsgingEventEndpointCountThreshold, guaranteedMsgingEventIngressFlowCountThreshold, guaranteedMsgingEventMsgCountThreshold, guaranteedMsgingEventMsgSpoolFileCountThreshold, guaranteedMsgingEventMsgSpoolUsageThreshold, guaranteedMsgingEventTransactedSessionCountThreshold, guaranteedMsgingEventTransactedSessionResourceCountThreshold, guaranteedMsgingEventTransactionCountThreshold, guaranteedMsgingMaxCacheUsage, guaranteedMsgingMaxMsgSpoolUsage, guaranteedMsgingOperationalStatus, guaranteedMsgingTransactionReplicationCompatibilityMode, guaranteedMsgingVirtualRouterWhenActiveActive, rxByteCount, rxByteRate, rxCompressedByteCount, rxCompressedByteRate, rxCompressionRatio, rxMsgCount, rxMsgRate, rxUncompressedByteCount, rxUncompressedByteRate, serviceAmqpEnabled, serviceAmqpTlsListenPort, serviceEventConnectionCountThreshold, serviceHealthCheckEnabled, serviceHealthCheckListenPort, serviceMqttEnabled, serviceMsgBackboneEnabled, serviceRestEventOutgoingConnectionCountThreshold, serviceRestIncomingEnabled, serviceRestOutgoingEnabled, serviceSempLegacyTimeoutEnabled, serviceSempPlainTextEnabled, serviceSempPlainTextListenPort, serviceSempSessionIdleTimeout, serviceSempSessionMaxLifetime, serviceSempTlsEnabled, serviceSempTlsListenPort, serviceSmfCompressionListenPort, serviceSmfEnabled, serviceSmfEventConnectionCountThreshold, serviceSmfPlainTextListenPort, serviceSmfRoutingControlListenPort, serviceSmfTlsListenPort, serviceTlsEventConnectionCountThreshold, serviceWebTransportEnabled, serviceWebTransportPlainTextListenPort, serviceWebTransportTlsListenPort, serviceWebTransportWebUrlSuffix, tlsBlockVersion10Enabled, tlsBlockVersion11Enabled, tlsCipherSuiteManagementDefaultList, tlsCipherSuiteManagementList, tlsCipherSuiteManagementSupportedList, tlsCipherSuiteMsgBackboneDefaultList, tlsCipherSuiteMsgBackboneList, tlsCipherSuiteMsgBackboneSupportedList, tlsCipherSuiteSecureShellDefaultList, tlsCipherSuiteSecureShellList, tlsCipherSuiteSecureShellSupportedList, tlsCrimeExploitProtectionEnabled, tlsStandardDomainCertificateAuthoritiesEnabled, tlsTicketLifetime, tlsVersionSupportedList, txByteCount, txByteRate, txCompressedByteCount, txCompressedByteRate, txCompressionRatio, txMsgCount, txMsgRate, txUncompressedByteCount, txUncompressedByteRate);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Broker {\n");
    
    sb.append("    authClientCertRevocationCheckMode: ").append(toIndentedString(authClientCertRevocationCheckMode)).append("\n");
    sb.append("    averageRxByteRate: ").append(toIndentedString(averageRxByteRate)).append("\n");
    sb.append("    averageRxCompressedByteRate: ").append(toIndentedString(averageRxCompressedByteRate)).append("\n");
    sb.append("    averageRxMsgRate: ").append(toIndentedString(averageRxMsgRate)).append("\n");
    sb.append("    averageRxUncompressedByteRate: ").append(toIndentedString(averageRxUncompressedByteRate)).append("\n");
    sb.append("    averageTxByteRate: ").append(toIndentedString(averageTxByteRate)).append("\n");
    sb.append("    averageTxCompressedByteRate: ").append(toIndentedString(averageTxCompressedByteRate)).append("\n");
    sb.append("    averageTxMsgRate: ").append(toIndentedString(averageTxMsgRate)).append("\n");
    sb.append("    averageTxUncompressedByteRate: ").append(toIndentedString(averageTxUncompressedByteRate)).append("\n");
    sb.append("    configSyncAuthenticationClientCertMaxChainDepth: ").append(toIndentedString(configSyncAuthenticationClientCertMaxChainDepth)).append("\n");
    sb.append("    configSyncAuthenticationClientCertValidateDateEnabled: ").append(toIndentedString(configSyncAuthenticationClientCertValidateDateEnabled)).append("\n");
    sb.append("    configSyncClientProfileTcpInitialCongestionWindow: ").append(toIndentedString(configSyncClientProfileTcpInitialCongestionWindow)).append("\n");
    sb.append("    configSyncClientProfileTcpKeepaliveCount: ").append(toIndentedString(configSyncClientProfileTcpKeepaliveCount)).append("\n");
    sb.append("    configSyncClientProfileTcpKeepaliveIdle: ").append(toIndentedString(configSyncClientProfileTcpKeepaliveIdle)).append("\n");
    sb.append("    configSyncClientProfileTcpKeepaliveInterval: ").append(toIndentedString(configSyncClientProfileTcpKeepaliveInterval)).append("\n");
    sb.append("    configSyncClientProfileTcpMaxWindow: ").append(toIndentedString(configSyncClientProfileTcpMaxWindow)).append("\n");
    sb.append("    configSyncClientProfileTcpMss: ").append(toIndentedString(configSyncClientProfileTcpMss)).append("\n");
    sb.append("    configSyncEnabled: ").append(toIndentedString(configSyncEnabled)).append("\n");
    sb.append("    configSyncLastFailureReason: ").append(toIndentedString(configSyncLastFailureReason)).append("\n");
    sb.append("    configSyncSynchronizeUsernameEnabled: ").append(toIndentedString(configSyncSynchronizeUsernameEnabled)).append("\n");
    sb.append("    configSyncTlsEnabled: ").append(toIndentedString(configSyncTlsEnabled)).append("\n");
    sb.append("    configSyncUp: ").append(toIndentedString(configSyncUp)).append("\n");
    sb.append("    cspfVersion: ").append(toIndentedString(cspfVersion)).append("\n");
    sb.append("    guaranteedMsgingDefragmentationEstimatedFragmentation: ").append(toIndentedString(guaranteedMsgingDefragmentationEstimatedFragmentation)).append("\n");
    sb.append("    guaranteedMsgingDefragmentationEstimatedRecoverableSpace: ").append(toIndentedString(guaranteedMsgingDefragmentationEstimatedRecoverableSpace)).append("\n");
    sb.append("    guaranteedMsgingDefragmentationLastCompletedOn: ").append(toIndentedString(guaranteedMsgingDefragmentationLastCompletedOn)).append("\n");
    sb.append("    guaranteedMsgingDefragmentationLastCompletionPercentage: ").append(toIndentedString(guaranteedMsgingDefragmentationLastCompletionPercentage)).append("\n");
    sb.append("    guaranteedMsgingDefragmentationLastExitCondition: ").append(toIndentedString(guaranteedMsgingDefragmentationLastExitCondition)).append("\n");
    sb.append("    guaranteedMsgingDefragmentationLastExitConditionInformation: ").append(toIndentedString(guaranteedMsgingDefragmentationLastExitConditionInformation)).append("\n");
    sb.append("    guaranteedMsgingDefragmentationStatus: ").append(toIndentedString(guaranteedMsgingDefragmentationStatus)).append("\n");
    sb.append("    guaranteedMsgingDefragmentationStatusActiveCompletionPercentage: ").append(toIndentedString(guaranteedMsgingDefragmentationStatusActiveCompletionPercentage)).append("\n");
    sb.append("    guaranteedMsgingDiskArrayWwn: ").append(toIndentedString(guaranteedMsgingDiskArrayWwn)).append("\n");
    sb.append("    guaranteedMsgingDiskLocation: ").append(toIndentedString(guaranteedMsgingDiskLocation)).append("\n");
    sb.append("    guaranteedMsgingEnabled: ").append(toIndentedString(guaranteedMsgingEnabled)).append("\n");
    sb.append("    guaranteedMsgingEventCacheUsageThreshold: ").append(toIndentedString(guaranteedMsgingEventCacheUsageThreshold)).append("\n");
    sb.append("    guaranteedMsgingEventDeliveredUnackedThreshold: ").append(toIndentedString(guaranteedMsgingEventDeliveredUnackedThreshold)).append("\n");
    sb.append("    guaranteedMsgingEventDiskUsageThreshold: ").append(toIndentedString(guaranteedMsgingEventDiskUsageThreshold)).append("\n");
    sb.append("    guaranteedMsgingEventEgressFlowCountThreshold: ").append(toIndentedString(guaranteedMsgingEventEgressFlowCountThreshold)).append("\n");
    sb.append("    guaranteedMsgingEventEndpointCountThreshold: ").append(toIndentedString(guaranteedMsgingEventEndpointCountThreshold)).append("\n");
    sb.append("    guaranteedMsgingEventIngressFlowCountThreshold: ").append(toIndentedString(guaranteedMsgingEventIngressFlowCountThreshold)).append("\n");
    sb.append("    guaranteedMsgingEventMsgCountThreshold: ").append(toIndentedString(guaranteedMsgingEventMsgCountThreshold)).append("\n");
    sb.append("    guaranteedMsgingEventMsgSpoolFileCountThreshold: ").append(toIndentedString(guaranteedMsgingEventMsgSpoolFileCountThreshold)).append("\n");
    sb.append("    guaranteedMsgingEventMsgSpoolUsageThreshold: ").append(toIndentedString(guaranteedMsgingEventMsgSpoolUsageThreshold)).append("\n");
    sb.append("    guaranteedMsgingEventTransactedSessionCountThreshold: ").append(toIndentedString(guaranteedMsgingEventTransactedSessionCountThreshold)).append("\n");
    sb.append("    guaranteedMsgingEventTransactedSessionResourceCountThreshold: ").append(toIndentedString(guaranteedMsgingEventTransactedSessionResourceCountThreshold)).append("\n");
    sb.append("    guaranteedMsgingEventTransactionCountThreshold: ").append(toIndentedString(guaranteedMsgingEventTransactionCountThreshold)).append("\n");
    sb.append("    guaranteedMsgingMaxCacheUsage: ").append(toIndentedString(guaranteedMsgingMaxCacheUsage)).append("\n");
    sb.append("    guaranteedMsgingMaxMsgSpoolUsage: ").append(toIndentedString(guaranteedMsgingMaxMsgSpoolUsage)).append("\n");
    sb.append("    guaranteedMsgingOperationalStatus: ").append(toIndentedString(guaranteedMsgingOperationalStatus)).append("\n");
    sb.append("    guaranteedMsgingTransactionReplicationCompatibilityMode: ").append(toIndentedString(guaranteedMsgingTransactionReplicationCompatibilityMode)).append("\n");
    sb.append("    guaranteedMsgingVirtualRouterWhenActiveActive: ").append(toIndentedString(guaranteedMsgingVirtualRouterWhenActiveActive)).append("\n");
    sb.append("    rxByteCount: ").append(toIndentedString(rxByteCount)).append("\n");
    sb.append("    rxByteRate: ").append(toIndentedString(rxByteRate)).append("\n");
    sb.append("    rxCompressedByteCount: ").append(toIndentedString(rxCompressedByteCount)).append("\n");
    sb.append("    rxCompressedByteRate: ").append(toIndentedString(rxCompressedByteRate)).append("\n");
    sb.append("    rxCompressionRatio: ").append(toIndentedString(rxCompressionRatio)).append("\n");
    sb.append("    rxMsgCount: ").append(toIndentedString(rxMsgCount)).append("\n");
    sb.append("    rxMsgRate: ").append(toIndentedString(rxMsgRate)).append("\n");
    sb.append("    rxUncompressedByteCount: ").append(toIndentedString(rxUncompressedByteCount)).append("\n");
    sb.append("    rxUncompressedByteRate: ").append(toIndentedString(rxUncompressedByteRate)).append("\n");
    sb.append("    serviceAmqpEnabled: ").append(toIndentedString(serviceAmqpEnabled)).append("\n");
    sb.append("    serviceAmqpTlsListenPort: ").append(toIndentedString(serviceAmqpTlsListenPort)).append("\n");
    sb.append("    serviceEventConnectionCountThreshold: ").append(toIndentedString(serviceEventConnectionCountThreshold)).append("\n");
    sb.append("    serviceHealthCheckEnabled: ").append(toIndentedString(serviceHealthCheckEnabled)).append("\n");
    sb.append("    serviceHealthCheckListenPort: ").append(toIndentedString(serviceHealthCheckListenPort)).append("\n");
    sb.append("    serviceMqttEnabled: ").append(toIndentedString(serviceMqttEnabled)).append("\n");
    sb.append("    serviceMsgBackboneEnabled: ").append(toIndentedString(serviceMsgBackboneEnabled)).append("\n");
    sb.append("    serviceRestEventOutgoingConnectionCountThreshold: ").append(toIndentedString(serviceRestEventOutgoingConnectionCountThreshold)).append("\n");
    sb.append("    serviceRestIncomingEnabled: ").append(toIndentedString(serviceRestIncomingEnabled)).append("\n");
    sb.append("    serviceRestOutgoingEnabled: ").append(toIndentedString(serviceRestOutgoingEnabled)).append("\n");
    sb.append("    serviceSempLegacyTimeoutEnabled: ").append(toIndentedString(serviceSempLegacyTimeoutEnabled)).append("\n");
    sb.append("    serviceSempPlainTextEnabled: ").append(toIndentedString(serviceSempPlainTextEnabled)).append("\n");
    sb.append("    serviceSempPlainTextListenPort: ").append(toIndentedString(serviceSempPlainTextListenPort)).append("\n");
    sb.append("    serviceSempSessionIdleTimeout: ").append(toIndentedString(serviceSempSessionIdleTimeout)).append("\n");
    sb.append("    serviceSempSessionMaxLifetime: ").append(toIndentedString(serviceSempSessionMaxLifetime)).append("\n");
    sb.append("    serviceSempTlsEnabled: ").append(toIndentedString(serviceSempTlsEnabled)).append("\n");
    sb.append("    serviceSempTlsListenPort: ").append(toIndentedString(serviceSempTlsListenPort)).append("\n");
    sb.append("    serviceSmfCompressionListenPort: ").append(toIndentedString(serviceSmfCompressionListenPort)).append("\n");
    sb.append("    serviceSmfEnabled: ").append(toIndentedString(serviceSmfEnabled)).append("\n");
    sb.append("    serviceSmfEventConnectionCountThreshold: ").append(toIndentedString(serviceSmfEventConnectionCountThreshold)).append("\n");
    sb.append("    serviceSmfPlainTextListenPort: ").append(toIndentedString(serviceSmfPlainTextListenPort)).append("\n");
    sb.append("    serviceSmfRoutingControlListenPort: ").append(toIndentedString(serviceSmfRoutingControlListenPort)).append("\n");
    sb.append("    serviceSmfTlsListenPort: ").append(toIndentedString(serviceSmfTlsListenPort)).append("\n");
    sb.append("    serviceTlsEventConnectionCountThreshold: ").append(toIndentedString(serviceTlsEventConnectionCountThreshold)).append("\n");
    sb.append("    serviceWebTransportEnabled: ").append(toIndentedString(serviceWebTransportEnabled)).append("\n");
    sb.append("    serviceWebTransportPlainTextListenPort: ").append(toIndentedString(serviceWebTransportPlainTextListenPort)).append("\n");
    sb.append("    serviceWebTransportTlsListenPort: ").append(toIndentedString(serviceWebTransportTlsListenPort)).append("\n");
    sb.append("    serviceWebTransportWebUrlSuffix: ").append(toIndentedString(serviceWebTransportWebUrlSuffix)).append("\n");
    sb.append("    tlsBlockVersion10Enabled: ").append(toIndentedString(tlsBlockVersion10Enabled)).append("\n");
    sb.append("    tlsBlockVersion11Enabled: ").append(toIndentedString(tlsBlockVersion11Enabled)).append("\n");
    sb.append("    tlsCipherSuiteManagementDefaultList: ").append(toIndentedString(tlsCipherSuiteManagementDefaultList)).append("\n");
    sb.append("    tlsCipherSuiteManagementList: ").append(toIndentedString(tlsCipherSuiteManagementList)).append("\n");
    sb.append("    tlsCipherSuiteManagementSupportedList: ").append(toIndentedString(tlsCipherSuiteManagementSupportedList)).append("\n");
    sb.append("    tlsCipherSuiteMsgBackboneDefaultList: ").append(toIndentedString(tlsCipherSuiteMsgBackboneDefaultList)).append("\n");
    sb.append("    tlsCipherSuiteMsgBackboneList: ").append(toIndentedString(tlsCipherSuiteMsgBackboneList)).append("\n");
    sb.append("    tlsCipherSuiteMsgBackboneSupportedList: ").append(toIndentedString(tlsCipherSuiteMsgBackboneSupportedList)).append("\n");
    sb.append("    tlsCipherSuiteSecureShellDefaultList: ").append(toIndentedString(tlsCipherSuiteSecureShellDefaultList)).append("\n");
    sb.append("    tlsCipherSuiteSecureShellList: ").append(toIndentedString(tlsCipherSuiteSecureShellList)).append("\n");
    sb.append("    tlsCipherSuiteSecureShellSupportedList: ").append(toIndentedString(tlsCipherSuiteSecureShellSupportedList)).append("\n");
    sb.append("    tlsCrimeExploitProtectionEnabled: ").append(toIndentedString(tlsCrimeExploitProtectionEnabled)).append("\n");
    sb.append("    tlsStandardDomainCertificateAuthoritiesEnabled: ").append(toIndentedString(tlsStandardDomainCertificateAuthoritiesEnabled)).append("\n");
    sb.append("    tlsTicketLifetime: ").append(toIndentedString(tlsTicketLifetime)).append("\n");
    sb.append("    tlsVersionSupportedList: ").append(toIndentedString(tlsVersionSupportedList)).append("\n");
    sb.append("    txByteCount: ").append(toIndentedString(txByteCount)).append("\n");
    sb.append("    txByteRate: ").append(toIndentedString(txByteRate)).append("\n");
    sb.append("    txCompressedByteCount: ").append(toIndentedString(txCompressedByteCount)).append("\n");
    sb.append("    txCompressedByteRate: ").append(toIndentedString(txCompressedByteRate)).append("\n");
    sb.append("    txCompressionRatio: ").append(toIndentedString(txCompressionRatio)).append("\n");
    sb.append("    txMsgCount: ").append(toIndentedString(txMsgCount)).append("\n");
    sb.append("    txMsgRate: ").append(toIndentedString(txMsgRate)).append("\n");
    sb.append("    txUncompressedByteCount: ").append(toIndentedString(txUncompressedByteCount)).append("\n");
    sb.append("    txUncompressedByteRate: ").append(toIndentedString(txUncompressedByteRate)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

