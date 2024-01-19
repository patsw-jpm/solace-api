/*
 * SEMP (Solace Element Management Protocol)
 * SEMP (starting in `v2`, see note 1) is a RESTful API for configuring, monitoring, and administering a Solace PubSub+ broker.  SEMP uses URIs to address manageable **resources** of the Solace PubSub+ broker. Resources are individual **objects**, **collections** of objects, or (exclusively in the action API) **actions**. This document applies to the following API:   API|Base Path|Purpose|Comments :---|:---|:---|:--- Action|/SEMP/v2/action|Performing actions|See note 2    The following APIs are also available:   API|Base Path|Purpose|Comments :---|:---|:---|:--- Configuration|/SEMP/v2/config|Reading and writing config state|See note 2 Monitoring|/SEMP/v2/monitor|Querying operational parameters|See note 2    Resources are always nouns, with individual objects being singular and collections being plural.  Objects within a collection are identified by an `obj-id`, which follows the collection name with the form `collection-name/obj-id`.  Actions within an object are identified by an `action-id`, which follows the object name with the form `obj-id/action-id`.  Some examples:  ``` /SEMP/v2/config/msgVpns                        ; MsgVpn collection /SEMP/v2/config/msgVpns/a                      ; MsgVpn object named \"a\" /SEMP/v2/config/msgVpns/a/queues               ; Queue collection in MsgVpn \"a\" /SEMP/v2/config/msgVpns/a/queues/b             ; Queue object named \"b\" in MsgVpn \"a\" /SEMP/v2/action/msgVpns/a/queues/b/startReplay ; Action that starts a replay on Queue \"b\" in MsgVpn \"a\" /SEMP/v2/monitor/msgVpns/a/clients             ; Client collection in MsgVpn \"a\" /SEMP/v2/monitor/msgVpns/a/clients/c           ; Client object named \"c\" in MsgVpn \"a\" ```  ## Collection Resources  Collections are unordered lists of objects (unless described as otherwise), and are described by JSON arrays. Each item in the array represents an object in the same manner as the individual object would normally be represented. In the configuration API, the creation of a new object is done through its collection resource.  ## Object and Action Resources  Objects are composed of attributes, actions, collections, and other objects. They are described by JSON objects as name/value pairs. The collections and actions of an object are not contained directly in the object's JSON content; rather the content includes an attribute containing a URI which points to the collections and actions. These contained resources must be managed through this URI. At a minimum, every object has one or more identifying attributes, and its own `uri` attribute which contains the URI pointing to itself.  Actions are also composed of attributes, and are described by JSON objects as name/value pairs. Unlike objects, however, they are not members of a collection and cannot be retrieved, only performed. Actions only exist in the action API.  Attributes in an object or action may have any combination of the following properties:   Property|Meaning|Comments :---|:---|:--- Identifying|Attribute is involved in unique identification of the object, and appears in its URI| Required|Attribute must be provided in the request| Read-Only|Attribute can only be read, not written.|See note 3 Write-Only|Attribute can only be written, not read, unless the attribute is also opaque|See the documentation for the opaque property Requires-Disable|Attribute can only be changed when object is disabled| Deprecated|Attribute is deprecated, and will disappear in the next SEMP version| Opaque|Attribute can be set or retrieved in opaque form when the `opaquePassword` query parameter is present|See the `opaquePassword` query parameter documentation    In some requests, certain attributes may only be provided in certain combinations with other attributes:   Relationship|Meaning :---|:--- Requires|Attribute may only be changed by a request if a particular attribute or combination of attributes is also provided in the request Conflicts|Attribute may only be provided in a request if a particular attribute or combination of attributes is not also provided in the request    In the monitoring API, any non-identifying attribute may not be returned in a GET.  ## HTTP Methods  The following HTTP methods manipulate resources in accordance with these general principles. Note that some methods are only used in certain APIs:   Method|Resource|Meaning|Request Body|Response Body|Missing Request Attributes :---|:---|:---|:---|:---|:--- POST|Collection|Create object|Initial attribute values|Object attributes and metadata|Set to default PUT|Object|Create or replace object (see note 5)|New attribute values|Object attributes and metadata|Set to default, with certain exceptions (see note 4) PUT|Action|Performs action|Action arguments|Action metadata|N/A PATCH|Object|Update object|New attribute values|Object attributes and metadata|unchanged DELETE|Object|Delete object|Empty|Object metadata|N/A GET|Object|Get object|Empty|Object attributes and metadata|N/A GET|Collection|Get collection|Empty|Object attributes and collection metadata|N/A    ## Common Query Parameters  The following are some common query parameters that are supported by many method/URI combinations. Individual URIs may document additional parameters. Note that multiple query parameters can be used together in a single URI, separated by the ampersand character. For example:  ``` ; Request for the MsgVpns collection using two hypothetical query parameters ; \"q1\" and \"q2\" with values \"val1\" and \"val2\" respectively /SEMP/v2/action/msgVpns?q1=val1&q2=val2 ```  ### select  Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. Use this query parameter to limit the size of the returned data for each returned object, return only those fields that are desired, or exclude fields that are not desired.  The value of `select` is a comma-separated list of attribute names. If the list contains attribute names that are not prefaced by `-`, only those attributes are included in the response. If the list contains attribute names that are prefaced by `-`, those attributes are excluded from the response. If the list contains both types, then the difference of the first set of attributes and the second set of attributes is returned. If the list is empty (i.e. `select=`), no attributes are returned.  All attributes that are prefaced by `-` must follow all attributes that are not prefaced by `-`. In addition, each attribute name in the list must match at least one attribute in the object.  Names may include the `*` wildcard (zero or more characters). Nested attribute names are supported using periods (e.g. `parentName.childName`).  Some examples:  ``` ; List of all MsgVpn names /SEMP/v2/action/msgVpns?select=msgVpnName ; List of all MsgVpn and their attributes except for their names /SEMP/v2/action/msgVpns?select=-msgVpnName ; Authentication attributes of MsgVpn \"finance\" /SEMP/v2/action/msgVpns/finance?select=authentication* ; All attributes of MsgVpn \"finance\" except for authentication attributes /SEMP/v2/action/msgVpns/finance?select=-authentication* ; Access related attributes of Queue \"orderQ\" of MsgVpn \"finance\" /SEMP/v2/action/msgVpns/finance/queues/orderQ?select=owner,permission ```  ### where  Include in the response only objects where certain conditions are true. Use this query parameter to limit which objects are returned to those whose attribute values meet the given conditions.  The value of `where` is a comma-separated list of expressions. All expressions must be true for the object to be included in the response. Each expression takes the form:  ``` expression  = attribute-name OP value OP          = '==' | '!=' | '&lt;' | '&gt;' | '&lt;=' | '&gt;=' ```  `value` may be a number, string, `true`, or `false`, as appropriate for the type of `attribute-name`. Greater-than and less-than comparisons only work for numbers. A `*` in a string `value` is interpreted as a wildcard (zero or more characters). Some examples:  ``` ; Only enabled MsgVpns /SEMP/v2/action/msgVpns?where=enabled==true ; Only MsgVpns using basic non-LDAP authentication /SEMP/v2/action/msgVpns?where=authenticationBasicEnabled==true,authenticationBasicType!=ldap ; Only MsgVpns that allow more than 100 client connections /SEMP/v2/action/msgVpns?where=maxConnectionCount>100 ; Only MsgVpns with msgVpnName starting with \"B\": /SEMP/v2/action/msgVpns?where=msgVpnName==B* ```  ### count  Limit the count of objects in the response. This can be useful to limit the size of the response for large collections. The minimum value for `count` is `1` and the default is `10`. There is also a per-collection maximum value to limit request handling time.  `count` does not guarantee that a minimum number of objects will be returned. A page may contain fewer than `count` objects or even be empty. Additional objects may nonetheless be available for retrieval on subsequent pages. See the `cursor` query parameter documentation for more information on paging.  For example: ``` ; Up to 25 MsgVpns /SEMP/v2/action/msgVpns?count=25 ```  ### cursor  The cursor, or position, for the next page of objects. Cursors are opaque data that should not be created or interpreted by SEMP clients, and should only be used as described below.  When a request is made for a collection and there may be additional objects available for retrieval that are not included in the initial response, the response will include a `cursorQuery` field containing a cursor. The value of this field can be specified in the `cursor` query parameter of a subsequent request to retrieve the next page of objects. For convenience, an appropriate URI is constructed automatically by the broker and included in the `nextPageUri` field of the response. This URI can be used directly to retrieve the next page of objects.  Applications must continue to follow the `nextPageUri` if one is provided in order to retrieve the full set of objects associated with the request, even if a page contains fewer than the requested number of objects (see the `count` query parameter documentation) or is empty.  ### opaquePassword  Attributes with the opaque property are also write-only and so cannot normally be retrieved in a GET. However, when a password is provided in the `opaquePassword` query parameter, attributes with the opaque property are retrieved in a GET in opaque form, encrypted with this password. The query parameter can also be used on a POST, PATCH, or PUT to set opaque attributes using opaque attribute values retrieved in a GET, so long as:  1. the same password that was used to retrieve the opaque attribute values is provided; and  2. the broker to which the request is being sent has the same major and minor SEMP version as the broker that produced the opaque attribute values.  The password provided in the query parameter must be a minimum of 8 characters and a maximum of 128 characters.  The query parameter can only be used in the configuration API, and only over HTTPS.  ## Authentication  When a client makes its first SEMPv2 request, it must supply a username and password using HTTP Basic authentication.  If authentication is successful, the broker returns a cookie containing a session key. The client can omit the username and password from subsequent requests, because the broker now uses the session cookie for authentication instead. When the session expires or is deleted, the client must provide the username and password again, and the broker creates a new session.  There are a limited number of session slots available on the broker. The broker returns 529 No SEMP Session Available if it is not able to allocate a session. For this reason, all clients that use SEMPv2 should support cookies.  If certain attributes—such as a user's password—are changed, the broker automatically deletes the affected sessions. These attributes are documented below. However, changes in external user configuration data stored on a RADIUS or LDAP server do not trigger the broker to delete the associated session(s), therefore you must do this manually, if required.  A client can retrieve its current session information using the /about/user endpoint, delete its own session using the /about/user/logout endpoint, and manage all sessions using the /sessions endpoint.  ## Help  Visit [our website](https://solace.com) to learn more about Solace.  You can also download the SEMP API specifications by clicking [here](https://solace.com/downloads/).  If you need additional support, please contact us at [support@solace.com](mailto:support@solace.com).  ## Notes  Note|Description :---:|:--- 1|This specification defines SEMP starting in \"v2\", and not the original SEMP \"v1\" interface. Request and response formats between \"v1\" and \"v2\" are entirely incompatible, although both protocols share a common port configuration on the Solace PubSub+ broker. They are differentiated by the initial portion of the URI path, one of either \"/SEMP/\" or \"/SEMP/v2/\" 2|This API is partially implemented. Only a subset of all objects are available. 3|Read-only attributes may appear in POST and PUT/PATCH requests. However, if a read-only attribute is not marked as identifying, it will be ignored during a PUT/PATCH. 4|On a PUT, if the SEMP user is not authorized to modify the attribute, its value is left unchanged rather than set to default. In addition, the values of write-only attributes are not set to their defaults on a PUT, except in the following two cases: there is a mutual requires relationship with another non-write-only attribute, both attributes are absent from the request, and the non-write-only attribute is not currently set to its default value; or the attribute is also opaque and the `opaquePassword` query parameter is provided in the request. 5|On a PUT, if the object does not exist, it is created first.  
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
import java.io.IOException;

/**
 * AboutUser
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2024-01-19T18:07:43.924Z")
public class AboutUser {
  /**
   * The global access level of the User. The allowed values and their meaning are:  &lt;pre&gt; \&quot;admin\&quot; - Full administrative access. \&quot;none\&quot; - No access. \&quot;read-only\&quot; - Read only access. \&quot;read-write\&quot; - Read and write access. &lt;/pre&gt; 
   */
  @JsonAdapter(GlobalAccessLevelEnum.Adapter.class)
  public enum GlobalAccessLevelEnum {
    ADMIN("admin"),
    
    NONE("none"),
    
    READ_ONLY("read-only"),
    
    READ_WRITE("read-write");

    private String value;

    GlobalAccessLevelEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static GlobalAccessLevelEnum fromValue(String text) {
      for (GlobalAccessLevelEnum b : GlobalAccessLevelEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<GlobalAccessLevelEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final GlobalAccessLevelEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public GlobalAccessLevelEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return GlobalAccessLevelEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("globalAccessLevel")
  private GlobalAccessLevelEnum globalAccessLevel = null;

  @SerializedName("sessionCreateTime")
  private Integer sessionCreateTime = null;

  @SerializedName("sessionCurrentTime")
  private Integer sessionCurrentTime = null;

  @SerializedName("sessionHardExpiryTime")
  private Integer sessionHardExpiryTime = null;

  @SerializedName("sessionId")
  private String sessionId = null;

  @SerializedName("sessionIdleExpiryTime")
  private Integer sessionIdleExpiryTime = null;

  @SerializedName("username")
  private String username = null;

  public AboutUser globalAccessLevel(GlobalAccessLevelEnum globalAccessLevel) {
    this.globalAccessLevel = globalAccessLevel;
    return this;
  }

   /**
   * The global access level of the User. The allowed values and their meaning are:  &lt;pre&gt; \&quot;admin\&quot; - Full administrative access. \&quot;none\&quot; - No access. \&quot;read-only\&quot; - Read only access. \&quot;read-write\&quot; - Read and write access. &lt;/pre&gt; 
   * @return globalAccessLevel
  **/
  @ApiModelProperty(value = "The global access level of the User. The allowed values and their meaning are:  <pre> \"admin\" - Full administrative access. \"none\" - No access. \"read-only\" - Read only access. \"read-write\" - Read and write access. </pre> ")
  public GlobalAccessLevelEnum getGlobalAccessLevel() {
    return globalAccessLevel;
  }

  public void setGlobalAccessLevel(GlobalAccessLevelEnum globalAccessLevel) {
    this.globalAccessLevel = globalAccessLevel;
  }

  public AboutUser sessionCreateTime(Integer sessionCreateTime) {
    this.sessionCreateTime = sessionCreateTime;
    return this;
  }

   /**
   * The timestamp of when the session was created. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). Available since 2.21.
   * @return sessionCreateTime
  **/
  @ApiModelProperty(value = "The timestamp of when the session was created. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). Available since 2.21.")
  public Integer getSessionCreateTime() {
    return sessionCreateTime;
  }

  public void setSessionCreateTime(Integer sessionCreateTime) {
    this.sessionCreateTime = sessionCreateTime;
  }

  public AboutUser sessionCurrentTime(Integer sessionCurrentTime) {
    this.sessionCurrentTime = sessionCurrentTime;
    return this;
  }

   /**
   * The current server timestamp. This is provided as a reference point for the other timestamps provided. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). Available since 2.21.
   * @return sessionCurrentTime
  **/
  @ApiModelProperty(value = "The current server timestamp. This is provided as a reference point for the other timestamps provided. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). Available since 2.21.")
  public Integer getSessionCurrentTime() {
    return sessionCurrentTime;
  }

  public void setSessionCurrentTime(Integer sessionCurrentTime) {
    this.sessionCurrentTime = sessionCurrentTime;
  }

  public AboutUser sessionHardExpiryTime(Integer sessionHardExpiryTime) {
    this.sessionHardExpiryTime = sessionHardExpiryTime;
    return this;
  }

   /**
   * The hard expiry time for the session. After this time the session will be invalid, regardless of activity. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). Available since 2.21.
   * @return sessionHardExpiryTime
  **/
  @ApiModelProperty(value = "The hard expiry time for the session. After this time the session will be invalid, regardless of activity. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). Available since 2.21.")
  public Integer getSessionHardExpiryTime() {
    return sessionHardExpiryTime;
  }

  public void setSessionHardExpiryTime(Integer sessionHardExpiryTime) {
    this.sessionHardExpiryTime = sessionHardExpiryTime;
  }

  public AboutUser sessionId(String sessionId) {
    this.sessionId = sessionId;
    return this;
  }

   /**
   * An identifier for the session to differentiate this session from other sessions for the same user. This value is not guaranteed to be unique between active sessions for different users. Available since 2.21.
   * @return sessionId
  **/
  @ApiModelProperty(value = "An identifier for the session to differentiate this session from other sessions for the same user. This value is not guaranteed to be unique between active sessions for different users. Available since 2.21.")
  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public AboutUser sessionIdleExpiryTime(Integer sessionIdleExpiryTime) {
    this.sessionIdleExpiryTime = sessionIdleExpiryTime;
    return this;
  }

   /**
   * The session idle expiry time. After this time the session will be invalid if there has been no activity. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). Available since 2.21.
   * @return sessionIdleExpiryTime
  **/
  @ApiModelProperty(value = "The session idle expiry time. After this time the session will be invalid if there has been no activity. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). Available since 2.21.")
  public Integer getSessionIdleExpiryTime() {
    return sessionIdleExpiryTime;
  }

  public void setSessionIdleExpiryTime(Integer sessionIdleExpiryTime) {
    this.sessionIdleExpiryTime = sessionIdleExpiryTime;
  }

  public AboutUser username(String username) {
    this.username = username;
    return this;
  }

   /**
   * The username of the User. Available since 2.17.
   * @return username
  **/
  @ApiModelProperty(value = "The username of the User. Available since 2.17.")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AboutUser aboutUser = (AboutUser) o;
    return Objects.equals(this.globalAccessLevel, aboutUser.globalAccessLevel) &&
        Objects.equals(this.sessionCreateTime, aboutUser.sessionCreateTime) &&
        Objects.equals(this.sessionCurrentTime, aboutUser.sessionCurrentTime) &&
        Objects.equals(this.sessionHardExpiryTime, aboutUser.sessionHardExpiryTime) &&
        Objects.equals(this.sessionId, aboutUser.sessionId) &&
        Objects.equals(this.sessionIdleExpiryTime, aboutUser.sessionIdleExpiryTime) &&
        Objects.equals(this.username, aboutUser.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(globalAccessLevel, sessionCreateTime, sessionCurrentTime, sessionHardExpiryTime, sessionId, sessionIdleExpiryTime, username);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AboutUser {\n");
    
    sb.append("    globalAccessLevel: ").append(toIndentedString(globalAccessLevel)).append("\n");
    sb.append("    sessionCreateTime: ").append(toIndentedString(sessionCreateTime)).append("\n");
    sb.append("    sessionCurrentTime: ").append(toIndentedString(sessionCurrentTime)).append("\n");
    sb.append("    sessionHardExpiryTime: ").append(toIndentedString(sessionHardExpiryTime)).append("\n");
    sb.append("    sessionId: ").append(toIndentedString(sessionId)).append("\n");
    sb.append("    sessionIdleExpiryTime: ").append(toIndentedString(sessionIdleExpiryTime)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
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
