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
import io.swagger.client.model.MsgVpnMqttSessionCounter;
import java.io.IOException;

/**
 * MsgVpnMqttSession
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2024-01-19T20:58:30.360Z")
public class MsgVpnMqttSession {
  @SerializedName("clean")
  private Boolean clean = null;

  @SerializedName("clientName")
  private String clientName = null;

  @SerializedName("counter")
  private MsgVpnMqttSessionCounter counter = null;

  @SerializedName("createdByManagement")
  private Boolean createdByManagement = null;

  @SerializedName("durable")
  private Boolean durable = null;

  @SerializedName("enabled")
  private Boolean enabled = null;

  @SerializedName("expiryTime")
  private Long expiryTime = null;

  @SerializedName("maxPacketSize")
  private Long maxPacketSize = null;

  @SerializedName("mqttConnackErrorTxCount")
  private Long mqttConnackErrorTxCount = null;

  @SerializedName("mqttConnackTxCount")
  private Long mqttConnackTxCount = null;

  @SerializedName("mqttConnectRxCount")
  private Long mqttConnectRxCount = null;

  @SerializedName("mqttDisconnectRxCount")
  private Long mqttDisconnectRxCount = null;

  @SerializedName("mqttPingreqRxCount")
  private Long mqttPingreqRxCount = null;

  @SerializedName("mqttPingrespTxCount")
  private Long mqttPingrespTxCount = null;

  @SerializedName("mqttPubackRxCount")
  private Long mqttPubackRxCount = null;

  @SerializedName("mqttPubackTxCount")
  private Long mqttPubackTxCount = null;

  @SerializedName("mqttPubcompTxCount")
  private Long mqttPubcompTxCount = null;

  @SerializedName("mqttPublishQos0RxCount")
  private Long mqttPublishQos0RxCount = null;

  @SerializedName("mqttPublishQos0TxCount")
  private Long mqttPublishQos0TxCount = null;

  @SerializedName("mqttPublishQos1RxCount")
  private Long mqttPublishQos1RxCount = null;

  @SerializedName("mqttPublishQos1TxCount")
  private Long mqttPublishQos1TxCount = null;

  @SerializedName("mqttPublishQos2RxCount")
  private Long mqttPublishQos2RxCount = null;

  @SerializedName("mqttPubrecTxCount")
  private Long mqttPubrecTxCount = null;

  @SerializedName("mqttPubrelRxCount")
  private Long mqttPubrelRxCount = null;

  @SerializedName("mqttSessionClientId")
  private String mqttSessionClientId = null;

  /**
   * The virtual router of the MQTT Session. The allowed values and their meaning are:  &lt;pre&gt; \&quot;primary\&quot; - The MQTT Session belongs to the primary virtual router. \&quot;backup\&quot; - The MQTT Session belongs to the backup virtual router. &lt;/pre&gt; 
   */
  @JsonAdapter(MqttSessionVirtualRouterEnum.Adapter.class)
  public enum MqttSessionVirtualRouterEnum {
    PRIMARY("primary"),
    
    BACKUP("backup");

    private String value;

    MqttSessionVirtualRouterEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static MqttSessionVirtualRouterEnum fromValue(String text) {
      for (MqttSessionVirtualRouterEnum b : MqttSessionVirtualRouterEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<MqttSessionVirtualRouterEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final MqttSessionVirtualRouterEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public MqttSessionVirtualRouterEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return MqttSessionVirtualRouterEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("mqttSessionVirtualRouter")
  private MqttSessionVirtualRouterEnum mqttSessionVirtualRouter = null;

  @SerializedName("mqttSubackErrorTxCount")
  private Long mqttSubackErrorTxCount = null;

  @SerializedName("mqttSubackTxCount")
  private Long mqttSubackTxCount = null;

  @SerializedName("mqttSubscribeRxCount")
  private Long mqttSubscribeRxCount = null;

  @SerializedName("mqttUnsubackTxCount")
  private Long mqttUnsubackTxCount = null;

  @SerializedName("mqttUnsubscribeRxCount")
  private Long mqttUnsubscribeRxCount = null;

  @SerializedName("msgVpnName")
  private String msgVpnName = null;

  @SerializedName("owner")
  private String owner = null;

  @SerializedName("queueConsumerAckPropagationEnabled")
  private Boolean queueConsumerAckPropagationEnabled = null;

  @SerializedName("queueDeadMsgQueue")
  private String queueDeadMsgQueue = null;

  @SerializedName("queueEventBindCountThreshold")
  private EventThreshold queueEventBindCountThreshold = null;

  @SerializedName("queueEventMsgSpoolUsageThreshold")
  private EventThreshold queueEventMsgSpoolUsageThreshold = null;

  @SerializedName("queueEventRejectLowPriorityMsgLimitThreshold")
  private EventThreshold queueEventRejectLowPriorityMsgLimitThreshold = null;

  @SerializedName("queueMaxBindCount")
  private Long queueMaxBindCount = null;

  @SerializedName("queueMaxDeliveredUnackedMsgsPerFlow")
  private Long queueMaxDeliveredUnackedMsgsPerFlow = null;

  @SerializedName("queueMaxMsgSize")
  private Integer queueMaxMsgSize = null;

  @SerializedName("queueMaxMsgSpoolUsage")
  private Long queueMaxMsgSpoolUsage = null;

  @SerializedName("queueMaxRedeliveryCount")
  private Long queueMaxRedeliveryCount = null;

  @SerializedName("queueMaxTtl")
  private Long queueMaxTtl = null;

  @SerializedName("queueName")
  private String queueName = null;

  @SerializedName("queueRejectLowPriorityMsgEnabled")
  private Boolean queueRejectLowPriorityMsgEnabled = null;

  @SerializedName("queueRejectLowPriorityMsgLimit")
  private Long queueRejectLowPriorityMsgLimit = null;

  /**
   * Indicates whether negative acknowledgements (NACKs) are returned to sending clients on message discards. Note that NACKs cause the message to not be delivered to any destination and Transacted Session commits to fail. The allowed values and their meaning are:  &lt;pre&gt; \&quot;always\&quot; - Always return a negative acknowledgment (NACK) to the sending client on message discard. \&quot;when-queue-enabled\&quot; - Only return a negative acknowledgment (NACK) to the sending client on message discard when the Queue is enabled. \&quot;never\&quot; - Never return a negative acknowledgment (NACK) to the sending client on message discard. &lt;/pre&gt;  Available since 2.14.
   */
  @JsonAdapter(QueueRejectMsgToSenderOnDiscardBehaviorEnum.Adapter.class)
  public enum QueueRejectMsgToSenderOnDiscardBehaviorEnum {
    ALWAYS("always"),
    
    WHEN_QUEUE_ENABLED("when-queue-enabled"),
    
    NEVER("never");

    private String value;

    QueueRejectMsgToSenderOnDiscardBehaviorEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static QueueRejectMsgToSenderOnDiscardBehaviorEnum fromValue(String text) {
      for (QueueRejectMsgToSenderOnDiscardBehaviorEnum b : QueueRejectMsgToSenderOnDiscardBehaviorEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<QueueRejectMsgToSenderOnDiscardBehaviorEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final QueueRejectMsgToSenderOnDiscardBehaviorEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public QueueRejectMsgToSenderOnDiscardBehaviorEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return QueueRejectMsgToSenderOnDiscardBehaviorEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("queueRejectMsgToSenderOnDiscardBehavior")
  private QueueRejectMsgToSenderOnDiscardBehaviorEnum queueRejectMsgToSenderOnDiscardBehavior = null;

  @SerializedName("queueRespectTtlEnabled")
  private Boolean queueRespectTtlEnabled = null;

  @SerializedName("rxMax")
  private Long rxMax = null;

  @SerializedName("will")
  private Boolean will = null;

  public MsgVpnMqttSession clean(Boolean clean) {
    this.clean = clean;
    return this;
  }

   /**
   * Indicates whether the Client requested a clean (newly created) MQTT Session when connecting. If not clean (already existing), then previously stored messages for QoS 1 subscriptions are delivered.
   * @return clean
  **/
  @ApiModelProperty(value = "Indicates whether the Client requested a clean (newly created) MQTT Session when connecting. If not clean (already existing), then previously stored messages for QoS 1 subscriptions are delivered.")
  public Boolean isClean() {
    return clean;
  }

  public void setClean(Boolean clean) {
    this.clean = clean;
  }

  public MsgVpnMqttSession clientName(String clientName) {
    this.clientName = clientName;
    return this;
  }

   /**
   * The name of the MQTT Session Client.
   * @return clientName
  **/
  @ApiModelProperty(value = "The name of the MQTT Session Client.")
  public String getClientName() {
    return clientName;
  }

  public void setClientName(String clientName) {
    this.clientName = clientName;
  }

  public MsgVpnMqttSession counter(MsgVpnMqttSessionCounter counter) {
    this.counter = counter;
    return this;
  }

   /**
   * Get counter
   * @return counter
  **/
  @ApiModelProperty(value = "")
  public MsgVpnMqttSessionCounter getCounter() {
    return counter;
  }

  public void setCounter(MsgVpnMqttSessionCounter counter) {
    this.counter = counter;
  }

  public MsgVpnMqttSession createdByManagement(Boolean createdByManagement) {
    this.createdByManagement = createdByManagement;
    return this;
  }

   /**
   * Indicates whether the MQTT Session was created by a Management API.
   * @return createdByManagement
  **/
  @ApiModelProperty(value = "Indicates whether the MQTT Session was created by a Management API.")
  public Boolean isCreatedByManagement() {
    return createdByManagement;
  }

  public void setCreatedByManagement(Boolean createdByManagement) {
    this.createdByManagement = createdByManagement;
  }

  public MsgVpnMqttSession durable(Boolean durable) {
    this.durable = durable;
    return this;
  }

   /**
   * Indicates whether the MQTT Session is durable. Disconnected durable MQTT Sessions are deleted when their expiry time is reached. Disconnected non-durable MQTT Sessions are deleted immediately. Available since 2.21.
   * @return durable
  **/
  @ApiModelProperty(value = "Indicates whether the MQTT Session is durable. Disconnected durable MQTT Sessions are deleted when their expiry time is reached. Disconnected non-durable MQTT Sessions are deleted immediately. Available since 2.21.")
  public Boolean isDurable() {
    return durable;
  }

  public void setDurable(Boolean durable) {
    this.durable = durable;
  }

  public MsgVpnMqttSession enabled(Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

   /**
   * Indicates whether the MQTT Session is enabled.
   * @return enabled
  **/
  @ApiModelProperty(value = "Indicates whether the MQTT Session is enabled.")
  public Boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public MsgVpnMqttSession expiryTime(Long expiryTime) {
    this.expiryTime = expiryTime;
    return this;
  }

   /**
   * The timestamp of when the disconnected MQTT session expires and is deleted. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). A value of 0 indicates that the session is either connected, or will never expire. Available since 2.21.
   * @return expiryTime
  **/
  @ApiModelProperty(value = "The timestamp of when the disconnected MQTT session expires and is deleted. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). A value of 0 indicates that the session is either connected, or will never expire. Available since 2.21.")
  public Long getExpiryTime() {
    return expiryTime;
  }

  public void setExpiryTime(Long expiryTime) {
    this.expiryTime = expiryTime;
  }

  public MsgVpnMqttSession maxPacketSize(Long maxPacketSize) {
    this.maxPacketSize = maxPacketSize;
    return this;
  }

   /**
   * The maximum size of a packet, including all headers and payload, that the Client has signaled it is willing to accept. A value of zero indicates no limit. Note that there are other broker settings which may further limit packet size. Available since 2.21.
   * @return maxPacketSize
  **/
  @ApiModelProperty(value = "The maximum size of a packet, including all headers and payload, that the Client has signaled it is willing to accept. A value of zero indicates no limit. Note that there are other broker settings which may further limit packet size. Available since 2.21.")
  public Long getMaxPacketSize() {
    return maxPacketSize;
  }

  public void setMaxPacketSize(Long maxPacketSize) {
    this.maxPacketSize = maxPacketSize;
  }

  public MsgVpnMqttSession mqttConnackErrorTxCount(Long mqttConnackErrorTxCount) {
    this.mqttConnackErrorTxCount = mqttConnackErrorTxCount;
    return this;
  }

   /**
   * The number of MQTT connect acknowledgment (CONNACK) refused response packets transmitted to the Client. Available since 2.13.
   * @return mqttConnackErrorTxCount
  **/
  @ApiModelProperty(value = "The number of MQTT connect acknowledgment (CONNACK) refused response packets transmitted to the Client. Available since 2.13.")
  public Long getMqttConnackErrorTxCount() {
    return mqttConnackErrorTxCount;
  }

  public void setMqttConnackErrorTxCount(Long mqttConnackErrorTxCount) {
    this.mqttConnackErrorTxCount = mqttConnackErrorTxCount;
  }

  public MsgVpnMqttSession mqttConnackTxCount(Long mqttConnackTxCount) {
    this.mqttConnackTxCount = mqttConnackTxCount;
    return this;
  }

   /**
   * The number of MQTT connect acknowledgment (CONNACK) accepted response packets transmitted to the Client. Available since 2.13.
   * @return mqttConnackTxCount
  **/
  @ApiModelProperty(value = "The number of MQTT connect acknowledgment (CONNACK) accepted response packets transmitted to the Client. Available since 2.13.")
  public Long getMqttConnackTxCount() {
    return mqttConnackTxCount;
  }

  public void setMqttConnackTxCount(Long mqttConnackTxCount) {
    this.mqttConnackTxCount = mqttConnackTxCount;
  }

  public MsgVpnMqttSession mqttConnectRxCount(Long mqttConnectRxCount) {
    this.mqttConnectRxCount = mqttConnectRxCount;
    return this;
  }

   /**
   * The number of MQTT connect (CONNECT) request packets received from the Client. Available since 2.13.
   * @return mqttConnectRxCount
  **/
  @ApiModelProperty(value = "The number of MQTT connect (CONNECT) request packets received from the Client. Available since 2.13.")
  public Long getMqttConnectRxCount() {
    return mqttConnectRxCount;
  }

  public void setMqttConnectRxCount(Long mqttConnectRxCount) {
    this.mqttConnectRxCount = mqttConnectRxCount;
  }

  public MsgVpnMqttSession mqttDisconnectRxCount(Long mqttDisconnectRxCount) {
    this.mqttDisconnectRxCount = mqttDisconnectRxCount;
    return this;
  }

   /**
   * The number of MQTT disconnect (DISCONNECT) request packets received from the Client. Available since 2.13.
   * @return mqttDisconnectRxCount
  **/
  @ApiModelProperty(value = "The number of MQTT disconnect (DISCONNECT) request packets received from the Client. Available since 2.13.")
  public Long getMqttDisconnectRxCount() {
    return mqttDisconnectRxCount;
  }

  public void setMqttDisconnectRxCount(Long mqttDisconnectRxCount) {
    this.mqttDisconnectRxCount = mqttDisconnectRxCount;
  }

  public MsgVpnMqttSession mqttPingreqRxCount(Long mqttPingreqRxCount) {
    this.mqttPingreqRxCount = mqttPingreqRxCount;
    return this;
  }

   /**
   * The number of MQTT ping request (PINGREQ) packets received from the Client. Available since 2.23.
   * @return mqttPingreqRxCount
  **/
  @ApiModelProperty(value = "The number of MQTT ping request (PINGREQ) packets received from the Client. Available since 2.23.")
  public Long getMqttPingreqRxCount() {
    return mqttPingreqRxCount;
  }

  public void setMqttPingreqRxCount(Long mqttPingreqRxCount) {
    this.mqttPingreqRxCount = mqttPingreqRxCount;
  }

  public MsgVpnMqttSession mqttPingrespTxCount(Long mqttPingrespTxCount) {
    this.mqttPingrespTxCount = mqttPingrespTxCount;
    return this;
  }

   /**
   * The number of MQTT ping response (PINGRESP) packets transmitted to the Client. Available since 2.23.
   * @return mqttPingrespTxCount
  **/
  @ApiModelProperty(value = "The number of MQTT ping response (PINGRESP) packets transmitted to the Client. Available since 2.23.")
  public Long getMqttPingrespTxCount() {
    return mqttPingrespTxCount;
  }

  public void setMqttPingrespTxCount(Long mqttPingrespTxCount) {
    this.mqttPingrespTxCount = mqttPingrespTxCount;
  }

  public MsgVpnMqttSession mqttPubackRxCount(Long mqttPubackRxCount) {
    this.mqttPubackRxCount = mqttPubackRxCount;
    return this;
  }

   /**
   * The number of MQTT publish acknowledgement (PUBACK) response packets received from the Client. Available since 2.23.
   * @return mqttPubackRxCount
  **/
  @ApiModelProperty(value = "The number of MQTT publish acknowledgement (PUBACK) response packets received from the Client. Available since 2.23.")
  public Long getMqttPubackRxCount() {
    return mqttPubackRxCount;
  }

  public void setMqttPubackRxCount(Long mqttPubackRxCount) {
    this.mqttPubackRxCount = mqttPubackRxCount;
  }

  public MsgVpnMqttSession mqttPubackTxCount(Long mqttPubackTxCount) {
    this.mqttPubackTxCount = mqttPubackTxCount;
    return this;
  }

   /**
   * The number of MQTT publish acknowledgement (PUBACK) response packets transmitted to the Client. Available since 2.23.
   * @return mqttPubackTxCount
  **/
  @ApiModelProperty(value = "The number of MQTT publish acknowledgement (PUBACK) response packets transmitted to the Client. Available since 2.23.")
  public Long getMqttPubackTxCount() {
    return mqttPubackTxCount;
  }

  public void setMqttPubackTxCount(Long mqttPubackTxCount) {
    this.mqttPubackTxCount = mqttPubackTxCount;
  }

  public MsgVpnMqttSession mqttPubcompTxCount(Long mqttPubcompTxCount) {
    this.mqttPubcompTxCount = mqttPubcompTxCount;
    return this;
  }

   /**
   * The number of MQTT publish complete (PUBCOMP) packets transmitted to the Client in response to a PUBREL packet. These packets are the fourth and final packet of a QoS 2 protocol exchange. Available since 2.13.
   * @return mqttPubcompTxCount
  **/
  @ApiModelProperty(value = "The number of MQTT publish complete (PUBCOMP) packets transmitted to the Client in response to a PUBREL packet. These packets are the fourth and final packet of a QoS 2 protocol exchange. Available since 2.13.")
  public Long getMqttPubcompTxCount() {
    return mqttPubcompTxCount;
  }

  public void setMqttPubcompTxCount(Long mqttPubcompTxCount) {
    this.mqttPubcompTxCount = mqttPubcompTxCount;
  }

  public MsgVpnMqttSession mqttPublishQos0RxCount(Long mqttPublishQos0RxCount) {
    this.mqttPublishQos0RxCount = mqttPublishQos0RxCount;
    return this;
  }

   /**
   * The number of MQTT publish message (PUBLISH) request packets received from the Client for QoS 0 message delivery. Available since 2.13.
   * @return mqttPublishQos0RxCount
  **/
  @ApiModelProperty(value = "The number of MQTT publish message (PUBLISH) request packets received from the Client for QoS 0 message delivery. Available since 2.13.")
  public Long getMqttPublishQos0RxCount() {
    return mqttPublishQos0RxCount;
  }

  public void setMqttPublishQos0RxCount(Long mqttPublishQos0RxCount) {
    this.mqttPublishQos0RxCount = mqttPublishQos0RxCount;
  }

  public MsgVpnMqttSession mqttPublishQos0TxCount(Long mqttPublishQos0TxCount) {
    this.mqttPublishQos0TxCount = mqttPublishQos0TxCount;
    return this;
  }

   /**
   * The number of MQTT publish message (PUBLISH) request packets transmitted to the Client for QoS 0 message delivery. Available since 2.13.
   * @return mqttPublishQos0TxCount
  **/
  @ApiModelProperty(value = "The number of MQTT publish message (PUBLISH) request packets transmitted to the Client for QoS 0 message delivery. Available since 2.13.")
  public Long getMqttPublishQos0TxCount() {
    return mqttPublishQos0TxCount;
  }

  public void setMqttPublishQos0TxCount(Long mqttPublishQos0TxCount) {
    this.mqttPublishQos0TxCount = mqttPublishQos0TxCount;
  }

  public MsgVpnMqttSession mqttPublishQos1RxCount(Long mqttPublishQos1RxCount) {
    this.mqttPublishQos1RxCount = mqttPublishQos1RxCount;
    return this;
  }

   /**
   * The number of MQTT publish message (PUBLISH) request packets received from the Client for QoS 1 message delivery. Available since 2.13.
   * @return mqttPublishQos1RxCount
  **/
  @ApiModelProperty(value = "The number of MQTT publish message (PUBLISH) request packets received from the Client for QoS 1 message delivery. Available since 2.13.")
  public Long getMqttPublishQos1RxCount() {
    return mqttPublishQos1RxCount;
  }

  public void setMqttPublishQos1RxCount(Long mqttPublishQos1RxCount) {
    this.mqttPublishQos1RxCount = mqttPublishQos1RxCount;
  }

  public MsgVpnMqttSession mqttPublishQos1TxCount(Long mqttPublishQos1TxCount) {
    this.mqttPublishQos1TxCount = mqttPublishQos1TxCount;
    return this;
  }

   /**
   * The number of MQTT publish message (PUBLISH) request packets transmitted to the Client for QoS 1 message delivery. Available since 2.13.
   * @return mqttPublishQos1TxCount
  **/
  @ApiModelProperty(value = "The number of MQTT publish message (PUBLISH) request packets transmitted to the Client for QoS 1 message delivery. Available since 2.13.")
  public Long getMqttPublishQos1TxCount() {
    return mqttPublishQos1TxCount;
  }

  public void setMqttPublishQos1TxCount(Long mqttPublishQos1TxCount) {
    this.mqttPublishQos1TxCount = mqttPublishQos1TxCount;
  }

  public MsgVpnMqttSession mqttPublishQos2RxCount(Long mqttPublishQos2RxCount) {
    this.mqttPublishQos2RxCount = mqttPublishQos2RxCount;
    return this;
  }

   /**
   * The number of MQTT publish message (PUBLISH) request packets received from the Client for QoS 2 message delivery. Available since 2.13.
   * @return mqttPublishQos2RxCount
  **/
  @ApiModelProperty(value = "The number of MQTT publish message (PUBLISH) request packets received from the Client for QoS 2 message delivery. Available since 2.13.")
  public Long getMqttPublishQos2RxCount() {
    return mqttPublishQos2RxCount;
  }

  public void setMqttPublishQos2RxCount(Long mqttPublishQos2RxCount) {
    this.mqttPublishQos2RxCount = mqttPublishQos2RxCount;
  }

  public MsgVpnMqttSession mqttPubrecTxCount(Long mqttPubrecTxCount) {
    this.mqttPubrecTxCount = mqttPubrecTxCount;
    return this;
  }

   /**
   * The number of MQTT publish received (PUBREC) packets transmitted to the Client in response to a PUBLISH packet with QoS 2. These packets are the second packet of a QoS 2 protocol exchange. Available since 2.13.
   * @return mqttPubrecTxCount
  **/
  @ApiModelProperty(value = "The number of MQTT publish received (PUBREC) packets transmitted to the Client in response to a PUBLISH packet with QoS 2. These packets are the second packet of a QoS 2 protocol exchange. Available since 2.13.")
  public Long getMqttPubrecTxCount() {
    return mqttPubrecTxCount;
  }

  public void setMqttPubrecTxCount(Long mqttPubrecTxCount) {
    this.mqttPubrecTxCount = mqttPubrecTxCount;
  }

  public MsgVpnMqttSession mqttPubrelRxCount(Long mqttPubrelRxCount) {
    this.mqttPubrelRxCount = mqttPubrelRxCount;
    return this;
  }

   /**
   * The number of MQTT publish release (PUBREL) packets received from the Client in response to a PUBREC packet. These packets are the third packet of a QoS 2 protocol exchange. Available since 2.13.
   * @return mqttPubrelRxCount
  **/
  @ApiModelProperty(value = "The number of MQTT publish release (PUBREL) packets received from the Client in response to a PUBREC packet. These packets are the third packet of a QoS 2 protocol exchange. Available since 2.13.")
  public Long getMqttPubrelRxCount() {
    return mqttPubrelRxCount;
  }

  public void setMqttPubrelRxCount(Long mqttPubrelRxCount) {
    this.mqttPubrelRxCount = mqttPubrelRxCount;
  }

  public MsgVpnMqttSession mqttSessionClientId(String mqttSessionClientId) {
    this.mqttSessionClientId = mqttSessionClientId;
    return this;
  }

   /**
   * The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet.
   * @return mqttSessionClientId
  **/
  @ApiModelProperty(value = "The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet.")
  public String getMqttSessionClientId() {
    return mqttSessionClientId;
  }

  public void setMqttSessionClientId(String mqttSessionClientId) {
    this.mqttSessionClientId = mqttSessionClientId;
  }

  public MsgVpnMqttSession mqttSessionVirtualRouter(MqttSessionVirtualRouterEnum mqttSessionVirtualRouter) {
    this.mqttSessionVirtualRouter = mqttSessionVirtualRouter;
    return this;
  }

   /**
   * The virtual router of the MQTT Session. The allowed values and their meaning are:  &lt;pre&gt; \&quot;primary\&quot; - The MQTT Session belongs to the primary virtual router. \&quot;backup\&quot; - The MQTT Session belongs to the backup virtual router. &lt;/pre&gt; 
   * @return mqttSessionVirtualRouter
  **/
  @ApiModelProperty(value = "The virtual router of the MQTT Session. The allowed values and their meaning are:  <pre> \"primary\" - The MQTT Session belongs to the primary virtual router. \"backup\" - The MQTT Session belongs to the backup virtual router. </pre> ")
  public MqttSessionVirtualRouterEnum getMqttSessionVirtualRouter() {
    return mqttSessionVirtualRouter;
  }

  public void setMqttSessionVirtualRouter(MqttSessionVirtualRouterEnum mqttSessionVirtualRouter) {
    this.mqttSessionVirtualRouter = mqttSessionVirtualRouter;
  }

  public MsgVpnMqttSession mqttSubackErrorTxCount(Long mqttSubackErrorTxCount) {
    this.mqttSubackErrorTxCount = mqttSubackErrorTxCount;
    return this;
  }

   /**
   * The number of MQTT subscribe acknowledgement (SUBACK) failure response packets transmitted to the Client. Available since 2.23.
   * @return mqttSubackErrorTxCount
  **/
  @ApiModelProperty(value = "The number of MQTT subscribe acknowledgement (SUBACK) failure response packets transmitted to the Client. Available since 2.23.")
  public Long getMqttSubackErrorTxCount() {
    return mqttSubackErrorTxCount;
  }

  public void setMqttSubackErrorTxCount(Long mqttSubackErrorTxCount) {
    this.mqttSubackErrorTxCount = mqttSubackErrorTxCount;
  }

  public MsgVpnMqttSession mqttSubackTxCount(Long mqttSubackTxCount) {
    this.mqttSubackTxCount = mqttSubackTxCount;
    return this;
  }

   /**
   * The number of MQTT subscribe acknowledgement (SUBACK) response packets transmitted to the Client. Available since 2.23.
   * @return mqttSubackTxCount
  **/
  @ApiModelProperty(value = "The number of MQTT subscribe acknowledgement (SUBACK) response packets transmitted to the Client. Available since 2.23.")
  public Long getMqttSubackTxCount() {
    return mqttSubackTxCount;
  }

  public void setMqttSubackTxCount(Long mqttSubackTxCount) {
    this.mqttSubackTxCount = mqttSubackTxCount;
  }

  public MsgVpnMqttSession mqttSubscribeRxCount(Long mqttSubscribeRxCount) {
    this.mqttSubscribeRxCount = mqttSubscribeRxCount;
    return this;
  }

   /**
   * The number of MQTT subscribe (SUBSCRIBE) request packets received from the Client to create one or more topic subscriptions. Available since 2.23.
   * @return mqttSubscribeRxCount
  **/
  @ApiModelProperty(value = "The number of MQTT subscribe (SUBSCRIBE) request packets received from the Client to create one or more topic subscriptions. Available since 2.23.")
  public Long getMqttSubscribeRxCount() {
    return mqttSubscribeRxCount;
  }

  public void setMqttSubscribeRxCount(Long mqttSubscribeRxCount) {
    this.mqttSubscribeRxCount = mqttSubscribeRxCount;
  }

  public MsgVpnMqttSession mqttUnsubackTxCount(Long mqttUnsubackTxCount) {
    this.mqttUnsubackTxCount = mqttUnsubackTxCount;
    return this;
  }

   /**
   * The number of MQTT unsubscribe acknowledgement (UNSUBACK) response packets transmitted to the Client. Available since 2.23.
   * @return mqttUnsubackTxCount
  **/
  @ApiModelProperty(value = "The number of MQTT unsubscribe acknowledgement (UNSUBACK) response packets transmitted to the Client. Available since 2.23.")
  public Long getMqttUnsubackTxCount() {
    return mqttUnsubackTxCount;
  }

  public void setMqttUnsubackTxCount(Long mqttUnsubackTxCount) {
    this.mqttUnsubackTxCount = mqttUnsubackTxCount;
  }

  public MsgVpnMqttSession mqttUnsubscribeRxCount(Long mqttUnsubscribeRxCount) {
    this.mqttUnsubscribeRxCount = mqttUnsubscribeRxCount;
    return this;
  }

   /**
   * The number of MQTT unsubscribe (UNSUBSCRIBE) request packets received from the Client to remove one or more topic subscriptions. Available since 2.23.
   * @return mqttUnsubscribeRxCount
  **/
  @ApiModelProperty(value = "The number of MQTT unsubscribe (UNSUBSCRIBE) request packets received from the Client to remove one or more topic subscriptions. Available since 2.23.")
  public Long getMqttUnsubscribeRxCount() {
    return mqttUnsubscribeRxCount;
  }

  public void setMqttUnsubscribeRxCount(Long mqttUnsubscribeRxCount) {
    this.mqttUnsubscribeRxCount = mqttUnsubscribeRxCount;
  }

  public MsgVpnMqttSession msgVpnName(String msgVpnName) {
    this.msgVpnName = msgVpnName;
    return this;
  }

   /**
   * The name of the Message VPN.
   * @return msgVpnName
  **/
  @ApiModelProperty(value = "The name of the Message VPN.")
  public String getMsgVpnName() {
    return msgVpnName;
  }

  public void setMsgVpnName(String msgVpnName) {
    this.msgVpnName = msgVpnName;
  }

  public MsgVpnMqttSession owner(String owner) {
    this.owner = owner;
    return this;
  }

   /**
   * The Client Username which owns the MQTT Session.
   * @return owner
  **/
  @ApiModelProperty(value = "The Client Username which owns the MQTT Session.")
  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public MsgVpnMqttSession queueConsumerAckPropagationEnabled(Boolean queueConsumerAckPropagationEnabled) {
    this.queueConsumerAckPropagationEnabled = queueConsumerAckPropagationEnabled;
    return this;
  }

   /**
   * Indicates whether consumer acknowledgements (ACKs) received on the active replication Message VPN are propagated to the standby replication Message VPN. Available since 2.14.
   * @return queueConsumerAckPropagationEnabled
  **/
  @ApiModelProperty(value = "Indicates whether consumer acknowledgements (ACKs) received on the active replication Message VPN are propagated to the standby replication Message VPN. Available since 2.14.")
  public Boolean isQueueConsumerAckPropagationEnabled() {
    return queueConsumerAckPropagationEnabled;
  }

  public void setQueueConsumerAckPropagationEnabled(Boolean queueConsumerAckPropagationEnabled) {
    this.queueConsumerAckPropagationEnabled = queueConsumerAckPropagationEnabled;
  }

  public MsgVpnMqttSession queueDeadMsgQueue(String queueDeadMsgQueue) {
    this.queueDeadMsgQueue = queueDeadMsgQueue;
    return this;
  }

   /**
   * The name of the Dead Message Queue (DMQ) used by the MQTT Session Queue. Available since 2.14.
   * @return queueDeadMsgQueue
  **/
  @ApiModelProperty(value = "The name of the Dead Message Queue (DMQ) used by the MQTT Session Queue. Available since 2.14.")
  public String getQueueDeadMsgQueue() {
    return queueDeadMsgQueue;
  }

  public void setQueueDeadMsgQueue(String queueDeadMsgQueue) {
    this.queueDeadMsgQueue = queueDeadMsgQueue;
  }

  public MsgVpnMqttSession queueEventBindCountThreshold(EventThreshold queueEventBindCountThreshold) {
    this.queueEventBindCountThreshold = queueEventBindCountThreshold;
    return this;
  }

   /**
   * Get queueEventBindCountThreshold
   * @return queueEventBindCountThreshold
  **/
  @ApiModelProperty(value = "")
  public EventThreshold getQueueEventBindCountThreshold() {
    return queueEventBindCountThreshold;
  }

  public void setQueueEventBindCountThreshold(EventThreshold queueEventBindCountThreshold) {
    this.queueEventBindCountThreshold = queueEventBindCountThreshold;
  }

  public MsgVpnMqttSession queueEventMsgSpoolUsageThreshold(EventThreshold queueEventMsgSpoolUsageThreshold) {
    this.queueEventMsgSpoolUsageThreshold = queueEventMsgSpoolUsageThreshold;
    return this;
  }

   /**
   * Get queueEventMsgSpoolUsageThreshold
   * @return queueEventMsgSpoolUsageThreshold
  **/
  @ApiModelProperty(value = "")
  public EventThreshold getQueueEventMsgSpoolUsageThreshold() {
    return queueEventMsgSpoolUsageThreshold;
  }

  public void setQueueEventMsgSpoolUsageThreshold(EventThreshold queueEventMsgSpoolUsageThreshold) {
    this.queueEventMsgSpoolUsageThreshold = queueEventMsgSpoolUsageThreshold;
  }

  public MsgVpnMqttSession queueEventRejectLowPriorityMsgLimitThreshold(EventThreshold queueEventRejectLowPriorityMsgLimitThreshold) {
    this.queueEventRejectLowPriorityMsgLimitThreshold = queueEventRejectLowPriorityMsgLimitThreshold;
    return this;
  }

   /**
   * Get queueEventRejectLowPriorityMsgLimitThreshold
   * @return queueEventRejectLowPriorityMsgLimitThreshold
  **/
  @ApiModelProperty(value = "")
  public EventThreshold getQueueEventRejectLowPriorityMsgLimitThreshold() {
    return queueEventRejectLowPriorityMsgLimitThreshold;
  }

  public void setQueueEventRejectLowPriorityMsgLimitThreshold(EventThreshold queueEventRejectLowPriorityMsgLimitThreshold) {
    this.queueEventRejectLowPriorityMsgLimitThreshold = queueEventRejectLowPriorityMsgLimitThreshold;
  }

  public MsgVpnMqttSession queueMaxBindCount(Long queueMaxBindCount) {
    this.queueMaxBindCount = queueMaxBindCount;
    return this;
  }

   /**
   * The maximum number of consumer flows that can bind to the MQTT Session Queue. Available since 2.14.
   * @return queueMaxBindCount
  **/
  @ApiModelProperty(value = "The maximum number of consumer flows that can bind to the MQTT Session Queue. Available since 2.14.")
  public Long getQueueMaxBindCount() {
    return queueMaxBindCount;
  }

  public void setQueueMaxBindCount(Long queueMaxBindCount) {
    this.queueMaxBindCount = queueMaxBindCount;
  }

  public MsgVpnMqttSession queueMaxDeliveredUnackedMsgsPerFlow(Long queueMaxDeliveredUnackedMsgsPerFlow) {
    this.queueMaxDeliveredUnackedMsgsPerFlow = queueMaxDeliveredUnackedMsgsPerFlow;
    return this;
  }

   /**
   * The maximum number of messages delivered but not acknowledged per flow for the MQTT Session Queue. Available since 2.14.
   * @return queueMaxDeliveredUnackedMsgsPerFlow
  **/
  @ApiModelProperty(value = "The maximum number of messages delivered but not acknowledged per flow for the MQTT Session Queue. Available since 2.14.")
  public Long getQueueMaxDeliveredUnackedMsgsPerFlow() {
    return queueMaxDeliveredUnackedMsgsPerFlow;
  }

  public void setQueueMaxDeliveredUnackedMsgsPerFlow(Long queueMaxDeliveredUnackedMsgsPerFlow) {
    this.queueMaxDeliveredUnackedMsgsPerFlow = queueMaxDeliveredUnackedMsgsPerFlow;
  }

  public MsgVpnMqttSession queueMaxMsgSize(Integer queueMaxMsgSize) {
    this.queueMaxMsgSize = queueMaxMsgSize;
    return this;
  }

   /**
   * The maximum message size allowed in the MQTT Session Queue, in bytes (B). Available since 2.14.
   * @return queueMaxMsgSize
  **/
  @ApiModelProperty(value = "The maximum message size allowed in the MQTT Session Queue, in bytes (B). Available since 2.14.")
  public Integer getQueueMaxMsgSize() {
    return queueMaxMsgSize;
  }

  public void setQueueMaxMsgSize(Integer queueMaxMsgSize) {
    this.queueMaxMsgSize = queueMaxMsgSize;
  }

  public MsgVpnMqttSession queueMaxMsgSpoolUsage(Long queueMaxMsgSpoolUsage) {
    this.queueMaxMsgSpoolUsage = queueMaxMsgSpoolUsage;
    return this;
  }

   /**
   * The maximum message spool usage allowed by the MQTT Session Queue, in megabytes (MB). A value of 0 only allows spooling of the last message received and disables quota checking. Available since 2.14.
   * @return queueMaxMsgSpoolUsage
  **/
  @ApiModelProperty(value = "The maximum message spool usage allowed by the MQTT Session Queue, in megabytes (MB). A value of 0 only allows spooling of the last message received and disables quota checking. Available since 2.14.")
  public Long getQueueMaxMsgSpoolUsage() {
    return queueMaxMsgSpoolUsage;
  }

  public void setQueueMaxMsgSpoolUsage(Long queueMaxMsgSpoolUsage) {
    this.queueMaxMsgSpoolUsage = queueMaxMsgSpoolUsage;
  }

  public MsgVpnMqttSession queueMaxRedeliveryCount(Long queueMaxRedeliveryCount) {
    this.queueMaxRedeliveryCount = queueMaxRedeliveryCount;
    return this;
  }

   /**
   * The maximum number of times the MQTT Session Queue will attempt redelivery of a message prior to it being discarded or moved to the DMQ. A value of 0 means to retry forever. Available since 2.14.
   * @return queueMaxRedeliveryCount
  **/
  @ApiModelProperty(value = "The maximum number of times the MQTT Session Queue will attempt redelivery of a message prior to it being discarded or moved to the DMQ. A value of 0 means to retry forever. Available since 2.14.")
  public Long getQueueMaxRedeliveryCount() {
    return queueMaxRedeliveryCount;
  }

  public void setQueueMaxRedeliveryCount(Long queueMaxRedeliveryCount) {
    this.queueMaxRedeliveryCount = queueMaxRedeliveryCount;
  }

  public MsgVpnMqttSession queueMaxTtl(Long queueMaxTtl) {
    this.queueMaxTtl = queueMaxTtl;
    return this;
  }

   /**
   * The maximum time in seconds a message can stay in the MQTT Session Queue when &#x60;queueRespectTtlEnabled&#x60; is &#x60;\&quot;true\&quot;&#x60;. A message expires when the lesser of the sender assigned time-to-live (TTL) in the message and the &#x60;queueMaxTtl&#x60; configured for the MQTT Session Queue, is exceeded. A value of 0 disables expiry. Available since 2.14.
   * @return queueMaxTtl
  **/
  @ApiModelProperty(value = "The maximum time in seconds a message can stay in the MQTT Session Queue when `queueRespectTtlEnabled` is `\"true\"`. A message expires when the lesser of the sender assigned time-to-live (TTL) in the message and the `queueMaxTtl` configured for the MQTT Session Queue, is exceeded. A value of 0 disables expiry. Available since 2.14.")
  public Long getQueueMaxTtl() {
    return queueMaxTtl;
  }

  public void setQueueMaxTtl(Long queueMaxTtl) {
    this.queueMaxTtl = queueMaxTtl;
  }

  public MsgVpnMqttSession queueName(String queueName) {
    this.queueName = queueName;
    return this;
  }

   /**
   * The name of the MQTT Session Queue.
   * @return queueName
  **/
  @ApiModelProperty(value = "The name of the MQTT Session Queue.")
  public String getQueueName() {
    return queueName;
  }

  public void setQueueName(String queueName) {
    this.queueName = queueName;
  }

  public MsgVpnMqttSession queueRejectLowPriorityMsgEnabled(Boolean queueRejectLowPriorityMsgEnabled) {
    this.queueRejectLowPriorityMsgEnabled = queueRejectLowPriorityMsgEnabled;
    return this;
  }

   /**
   * Indicates whether to return negative acknowledgements (NACKs) to sending clients on message discards. Note that NACKs cause the message to not be delivered to any destination and Transacted Session commits to fail. Available since 2.14.
   * @return queueRejectLowPriorityMsgEnabled
  **/
  @ApiModelProperty(value = "Indicates whether to return negative acknowledgements (NACKs) to sending clients on message discards. Note that NACKs cause the message to not be delivered to any destination and Transacted Session commits to fail. Available since 2.14.")
  public Boolean isQueueRejectLowPriorityMsgEnabled() {
    return queueRejectLowPriorityMsgEnabled;
  }

  public void setQueueRejectLowPriorityMsgEnabled(Boolean queueRejectLowPriorityMsgEnabled) {
    this.queueRejectLowPriorityMsgEnabled = queueRejectLowPriorityMsgEnabled;
  }

  public MsgVpnMqttSession queueRejectLowPriorityMsgLimit(Long queueRejectLowPriorityMsgLimit) {
    this.queueRejectLowPriorityMsgLimit = queueRejectLowPriorityMsgLimit;
    return this;
  }

   /**
   * The number of messages of any priority in the MQTT Session Queue above which low priority messages are not admitted but higher priority messages are allowed. Available since 2.14.
   * @return queueRejectLowPriorityMsgLimit
  **/
  @ApiModelProperty(value = "The number of messages of any priority in the MQTT Session Queue above which low priority messages are not admitted but higher priority messages are allowed. Available since 2.14.")
  public Long getQueueRejectLowPriorityMsgLimit() {
    return queueRejectLowPriorityMsgLimit;
  }

  public void setQueueRejectLowPriorityMsgLimit(Long queueRejectLowPriorityMsgLimit) {
    this.queueRejectLowPriorityMsgLimit = queueRejectLowPriorityMsgLimit;
  }

  public MsgVpnMqttSession queueRejectMsgToSenderOnDiscardBehavior(QueueRejectMsgToSenderOnDiscardBehaviorEnum queueRejectMsgToSenderOnDiscardBehavior) {
    this.queueRejectMsgToSenderOnDiscardBehavior = queueRejectMsgToSenderOnDiscardBehavior;
    return this;
  }

   /**
   * Indicates whether negative acknowledgements (NACKs) are returned to sending clients on message discards. Note that NACKs cause the message to not be delivered to any destination and Transacted Session commits to fail. The allowed values and their meaning are:  &lt;pre&gt; \&quot;always\&quot; - Always return a negative acknowledgment (NACK) to the sending client on message discard. \&quot;when-queue-enabled\&quot; - Only return a negative acknowledgment (NACK) to the sending client on message discard when the Queue is enabled. \&quot;never\&quot; - Never return a negative acknowledgment (NACK) to the sending client on message discard. &lt;/pre&gt;  Available since 2.14.
   * @return queueRejectMsgToSenderOnDiscardBehavior
  **/
  @ApiModelProperty(value = "Indicates whether negative acknowledgements (NACKs) are returned to sending clients on message discards. Note that NACKs cause the message to not be delivered to any destination and Transacted Session commits to fail. The allowed values and their meaning are:  <pre> \"always\" - Always return a negative acknowledgment (NACK) to the sending client on message discard. \"when-queue-enabled\" - Only return a negative acknowledgment (NACK) to the sending client on message discard when the Queue is enabled. \"never\" - Never return a negative acknowledgment (NACK) to the sending client on message discard. </pre>  Available since 2.14.")
  public QueueRejectMsgToSenderOnDiscardBehaviorEnum getQueueRejectMsgToSenderOnDiscardBehavior() {
    return queueRejectMsgToSenderOnDiscardBehavior;
  }

  public void setQueueRejectMsgToSenderOnDiscardBehavior(QueueRejectMsgToSenderOnDiscardBehaviorEnum queueRejectMsgToSenderOnDiscardBehavior) {
    this.queueRejectMsgToSenderOnDiscardBehavior = queueRejectMsgToSenderOnDiscardBehavior;
  }

  public MsgVpnMqttSession queueRespectTtlEnabled(Boolean queueRespectTtlEnabled) {
    this.queueRespectTtlEnabled = queueRespectTtlEnabled;
    return this;
  }

   /**
   * Indicates whether the time-to-live (TTL) for messages in the MQTT Session Queue is respected. When enabled, expired messages are discarded or moved to the DMQ. Available since 2.14.
   * @return queueRespectTtlEnabled
  **/
  @ApiModelProperty(value = "Indicates whether the time-to-live (TTL) for messages in the MQTT Session Queue is respected. When enabled, expired messages are discarded or moved to the DMQ. Available since 2.14.")
  public Boolean isQueueRespectTtlEnabled() {
    return queueRespectTtlEnabled;
  }

  public void setQueueRespectTtlEnabled(Boolean queueRespectTtlEnabled) {
    this.queueRespectTtlEnabled = queueRespectTtlEnabled;
  }

  public MsgVpnMqttSession rxMax(Long rxMax) {
    this.rxMax = rxMax;
    return this;
  }

   /**
   * The maximum number of outstanding QoS1 and QoS2 messages that the Client has signaled it is willing to accept. Note that there are other broker settings which may further limit the number of outstanding messasges. Available since 2.21.
   * @return rxMax
  **/
  @ApiModelProperty(value = "The maximum number of outstanding QoS1 and QoS2 messages that the Client has signaled it is willing to accept. Note that there are other broker settings which may further limit the number of outstanding messasges. Available since 2.21.")
  public Long getRxMax() {
    return rxMax;
  }

  public void setRxMax(Long rxMax) {
    this.rxMax = rxMax;
  }

  public MsgVpnMqttSession will(Boolean will) {
    this.will = will;
    return this;
  }

   /**
   * Indicates whether the MQTT Session has the Will message specified by the Client. The Will message is published if the Client disconnects without sending the MQTT DISCONNECT packet.
   * @return will
  **/
  @ApiModelProperty(value = "Indicates whether the MQTT Session has the Will message specified by the Client. The Will message is published if the Client disconnects without sending the MQTT DISCONNECT packet.")
  public Boolean isWill() {
    return will;
  }

  public void setWill(Boolean will) {
    this.will = will;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MsgVpnMqttSession msgVpnMqttSession = (MsgVpnMqttSession) o;
    return Objects.equals(this.clean, msgVpnMqttSession.clean) &&
        Objects.equals(this.clientName, msgVpnMqttSession.clientName) &&
        Objects.equals(this.counter, msgVpnMqttSession.counter) &&
        Objects.equals(this.createdByManagement, msgVpnMqttSession.createdByManagement) &&
        Objects.equals(this.durable, msgVpnMqttSession.durable) &&
        Objects.equals(this.enabled, msgVpnMqttSession.enabled) &&
        Objects.equals(this.expiryTime, msgVpnMqttSession.expiryTime) &&
        Objects.equals(this.maxPacketSize, msgVpnMqttSession.maxPacketSize) &&
        Objects.equals(this.mqttConnackErrorTxCount, msgVpnMqttSession.mqttConnackErrorTxCount) &&
        Objects.equals(this.mqttConnackTxCount, msgVpnMqttSession.mqttConnackTxCount) &&
        Objects.equals(this.mqttConnectRxCount, msgVpnMqttSession.mqttConnectRxCount) &&
        Objects.equals(this.mqttDisconnectRxCount, msgVpnMqttSession.mqttDisconnectRxCount) &&
        Objects.equals(this.mqttPingreqRxCount, msgVpnMqttSession.mqttPingreqRxCount) &&
        Objects.equals(this.mqttPingrespTxCount, msgVpnMqttSession.mqttPingrespTxCount) &&
        Objects.equals(this.mqttPubackRxCount, msgVpnMqttSession.mqttPubackRxCount) &&
        Objects.equals(this.mqttPubackTxCount, msgVpnMqttSession.mqttPubackTxCount) &&
        Objects.equals(this.mqttPubcompTxCount, msgVpnMqttSession.mqttPubcompTxCount) &&
        Objects.equals(this.mqttPublishQos0RxCount, msgVpnMqttSession.mqttPublishQos0RxCount) &&
        Objects.equals(this.mqttPublishQos0TxCount, msgVpnMqttSession.mqttPublishQos0TxCount) &&
        Objects.equals(this.mqttPublishQos1RxCount, msgVpnMqttSession.mqttPublishQos1RxCount) &&
        Objects.equals(this.mqttPublishQos1TxCount, msgVpnMqttSession.mqttPublishQos1TxCount) &&
        Objects.equals(this.mqttPublishQos2RxCount, msgVpnMqttSession.mqttPublishQos2RxCount) &&
        Objects.equals(this.mqttPubrecTxCount, msgVpnMqttSession.mqttPubrecTxCount) &&
        Objects.equals(this.mqttPubrelRxCount, msgVpnMqttSession.mqttPubrelRxCount) &&
        Objects.equals(this.mqttSessionClientId, msgVpnMqttSession.mqttSessionClientId) &&
        Objects.equals(this.mqttSessionVirtualRouter, msgVpnMqttSession.mqttSessionVirtualRouter) &&
        Objects.equals(this.mqttSubackErrorTxCount, msgVpnMqttSession.mqttSubackErrorTxCount) &&
        Objects.equals(this.mqttSubackTxCount, msgVpnMqttSession.mqttSubackTxCount) &&
        Objects.equals(this.mqttSubscribeRxCount, msgVpnMqttSession.mqttSubscribeRxCount) &&
        Objects.equals(this.mqttUnsubackTxCount, msgVpnMqttSession.mqttUnsubackTxCount) &&
        Objects.equals(this.mqttUnsubscribeRxCount, msgVpnMqttSession.mqttUnsubscribeRxCount) &&
        Objects.equals(this.msgVpnName, msgVpnMqttSession.msgVpnName) &&
        Objects.equals(this.owner, msgVpnMqttSession.owner) &&
        Objects.equals(this.queueConsumerAckPropagationEnabled, msgVpnMqttSession.queueConsumerAckPropagationEnabled) &&
        Objects.equals(this.queueDeadMsgQueue, msgVpnMqttSession.queueDeadMsgQueue) &&
        Objects.equals(this.queueEventBindCountThreshold, msgVpnMqttSession.queueEventBindCountThreshold) &&
        Objects.equals(this.queueEventMsgSpoolUsageThreshold, msgVpnMqttSession.queueEventMsgSpoolUsageThreshold) &&
        Objects.equals(this.queueEventRejectLowPriorityMsgLimitThreshold, msgVpnMqttSession.queueEventRejectLowPriorityMsgLimitThreshold) &&
        Objects.equals(this.queueMaxBindCount, msgVpnMqttSession.queueMaxBindCount) &&
        Objects.equals(this.queueMaxDeliveredUnackedMsgsPerFlow, msgVpnMqttSession.queueMaxDeliveredUnackedMsgsPerFlow) &&
        Objects.equals(this.queueMaxMsgSize, msgVpnMqttSession.queueMaxMsgSize) &&
        Objects.equals(this.queueMaxMsgSpoolUsage, msgVpnMqttSession.queueMaxMsgSpoolUsage) &&
        Objects.equals(this.queueMaxRedeliveryCount, msgVpnMqttSession.queueMaxRedeliveryCount) &&
        Objects.equals(this.queueMaxTtl, msgVpnMqttSession.queueMaxTtl) &&
        Objects.equals(this.queueName, msgVpnMqttSession.queueName) &&
        Objects.equals(this.queueRejectLowPriorityMsgEnabled, msgVpnMqttSession.queueRejectLowPriorityMsgEnabled) &&
        Objects.equals(this.queueRejectLowPriorityMsgLimit, msgVpnMqttSession.queueRejectLowPriorityMsgLimit) &&
        Objects.equals(this.queueRejectMsgToSenderOnDiscardBehavior, msgVpnMqttSession.queueRejectMsgToSenderOnDiscardBehavior) &&
        Objects.equals(this.queueRespectTtlEnabled, msgVpnMqttSession.queueRespectTtlEnabled) &&
        Objects.equals(this.rxMax, msgVpnMqttSession.rxMax) &&
        Objects.equals(this.will, msgVpnMqttSession.will);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clean, clientName, counter, createdByManagement, durable, enabled, expiryTime, maxPacketSize, mqttConnackErrorTxCount, mqttConnackTxCount, mqttConnectRxCount, mqttDisconnectRxCount, mqttPingreqRxCount, mqttPingrespTxCount, mqttPubackRxCount, mqttPubackTxCount, mqttPubcompTxCount, mqttPublishQos0RxCount, mqttPublishQos0TxCount, mqttPublishQos1RxCount, mqttPublishQos1TxCount, mqttPublishQos2RxCount, mqttPubrecTxCount, mqttPubrelRxCount, mqttSessionClientId, mqttSessionVirtualRouter, mqttSubackErrorTxCount, mqttSubackTxCount, mqttSubscribeRxCount, mqttUnsubackTxCount, mqttUnsubscribeRxCount, msgVpnName, owner, queueConsumerAckPropagationEnabled, queueDeadMsgQueue, queueEventBindCountThreshold, queueEventMsgSpoolUsageThreshold, queueEventRejectLowPriorityMsgLimitThreshold, queueMaxBindCount, queueMaxDeliveredUnackedMsgsPerFlow, queueMaxMsgSize, queueMaxMsgSpoolUsage, queueMaxRedeliveryCount, queueMaxTtl, queueName, queueRejectLowPriorityMsgEnabled, queueRejectLowPriorityMsgLimit, queueRejectMsgToSenderOnDiscardBehavior, queueRespectTtlEnabled, rxMax, will);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MsgVpnMqttSession {\n");
    
    sb.append("    clean: ").append(toIndentedString(clean)).append("\n");
    sb.append("    clientName: ").append(toIndentedString(clientName)).append("\n");
    sb.append("    counter: ").append(toIndentedString(counter)).append("\n");
    sb.append("    createdByManagement: ").append(toIndentedString(createdByManagement)).append("\n");
    sb.append("    durable: ").append(toIndentedString(durable)).append("\n");
    sb.append("    enabled: ").append(toIndentedString(enabled)).append("\n");
    sb.append("    expiryTime: ").append(toIndentedString(expiryTime)).append("\n");
    sb.append("    maxPacketSize: ").append(toIndentedString(maxPacketSize)).append("\n");
    sb.append("    mqttConnackErrorTxCount: ").append(toIndentedString(mqttConnackErrorTxCount)).append("\n");
    sb.append("    mqttConnackTxCount: ").append(toIndentedString(mqttConnackTxCount)).append("\n");
    sb.append("    mqttConnectRxCount: ").append(toIndentedString(mqttConnectRxCount)).append("\n");
    sb.append("    mqttDisconnectRxCount: ").append(toIndentedString(mqttDisconnectRxCount)).append("\n");
    sb.append("    mqttPingreqRxCount: ").append(toIndentedString(mqttPingreqRxCount)).append("\n");
    sb.append("    mqttPingrespTxCount: ").append(toIndentedString(mqttPingrespTxCount)).append("\n");
    sb.append("    mqttPubackRxCount: ").append(toIndentedString(mqttPubackRxCount)).append("\n");
    sb.append("    mqttPubackTxCount: ").append(toIndentedString(mqttPubackTxCount)).append("\n");
    sb.append("    mqttPubcompTxCount: ").append(toIndentedString(mqttPubcompTxCount)).append("\n");
    sb.append("    mqttPublishQos0RxCount: ").append(toIndentedString(mqttPublishQos0RxCount)).append("\n");
    sb.append("    mqttPublishQos0TxCount: ").append(toIndentedString(mqttPublishQos0TxCount)).append("\n");
    sb.append("    mqttPublishQos1RxCount: ").append(toIndentedString(mqttPublishQos1RxCount)).append("\n");
    sb.append("    mqttPublishQos1TxCount: ").append(toIndentedString(mqttPublishQos1TxCount)).append("\n");
    sb.append("    mqttPublishQos2RxCount: ").append(toIndentedString(mqttPublishQos2RxCount)).append("\n");
    sb.append("    mqttPubrecTxCount: ").append(toIndentedString(mqttPubrecTxCount)).append("\n");
    sb.append("    mqttPubrelRxCount: ").append(toIndentedString(mqttPubrelRxCount)).append("\n");
    sb.append("    mqttSessionClientId: ").append(toIndentedString(mqttSessionClientId)).append("\n");
    sb.append("    mqttSessionVirtualRouter: ").append(toIndentedString(mqttSessionVirtualRouter)).append("\n");
    sb.append("    mqttSubackErrorTxCount: ").append(toIndentedString(mqttSubackErrorTxCount)).append("\n");
    sb.append("    mqttSubackTxCount: ").append(toIndentedString(mqttSubackTxCount)).append("\n");
    sb.append("    mqttSubscribeRxCount: ").append(toIndentedString(mqttSubscribeRxCount)).append("\n");
    sb.append("    mqttUnsubackTxCount: ").append(toIndentedString(mqttUnsubackTxCount)).append("\n");
    sb.append("    mqttUnsubscribeRxCount: ").append(toIndentedString(mqttUnsubscribeRxCount)).append("\n");
    sb.append("    msgVpnName: ").append(toIndentedString(msgVpnName)).append("\n");
    sb.append("    owner: ").append(toIndentedString(owner)).append("\n");
    sb.append("    queueConsumerAckPropagationEnabled: ").append(toIndentedString(queueConsumerAckPropagationEnabled)).append("\n");
    sb.append("    queueDeadMsgQueue: ").append(toIndentedString(queueDeadMsgQueue)).append("\n");
    sb.append("    queueEventBindCountThreshold: ").append(toIndentedString(queueEventBindCountThreshold)).append("\n");
    sb.append("    queueEventMsgSpoolUsageThreshold: ").append(toIndentedString(queueEventMsgSpoolUsageThreshold)).append("\n");
    sb.append("    queueEventRejectLowPriorityMsgLimitThreshold: ").append(toIndentedString(queueEventRejectLowPriorityMsgLimitThreshold)).append("\n");
    sb.append("    queueMaxBindCount: ").append(toIndentedString(queueMaxBindCount)).append("\n");
    sb.append("    queueMaxDeliveredUnackedMsgsPerFlow: ").append(toIndentedString(queueMaxDeliveredUnackedMsgsPerFlow)).append("\n");
    sb.append("    queueMaxMsgSize: ").append(toIndentedString(queueMaxMsgSize)).append("\n");
    sb.append("    queueMaxMsgSpoolUsage: ").append(toIndentedString(queueMaxMsgSpoolUsage)).append("\n");
    sb.append("    queueMaxRedeliveryCount: ").append(toIndentedString(queueMaxRedeliveryCount)).append("\n");
    sb.append("    queueMaxTtl: ").append(toIndentedString(queueMaxTtl)).append("\n");
    sb.append("    queueName: ").append(toIndentedString(queueName)).append("\n");
    sb.append("    queueRejectLowPriorityMsgEnabled: ").append(toIndentedString(queueRejectLowPriorityMsgEnabled)).append("\n");
    sb.append("    queueRejectLowPriorityMsgLimit: ").append(toIndentedString(queueRejectLowPriorityMsgLimit)).append("\n");
    sb.append("    queueRejectMsgToSenderOnDiscardBehavior: ").append(toIndentedString(queueRejectMsgToSenderOnDiscardBehavior)).append("\n");
    sb.append("    queueRespectTtlEnabled: ").append(toIndentedString(queueRespectTtlEnabled)).append("\n");
    sb.append("    rxMax: ").append(toIndentedString(rxMax)).append("\n");
    sb.append("    will: ").append(toIndentedString(will)).append("\n");
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

