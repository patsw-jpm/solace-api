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
 * MsgVpnRestDeliveryPointRestConsumer
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2024-01-19T20:45:13.754Z")
public class MsgVpnRestDeliveryPointRestConsumer {
  @SerializedName("authenticationClientCertContent")
  private String authenticationClientCertContent = null;

  @SerializedName("authenticationClientCertPassword")
  private String authenticationClientCertPassword = null;

  @SerializedName("authenticationHttpBasicPassword")
  private String authenticationHttpBasicPassword = null;

  @SerializedName("authenticationHttpBasicUsername")
  private String authenticationHttpBasicUsername = null;

  @SerializedName("authenticationHttpHeaderName")
  private String authenticationHttpHeaderName = null;

  @SerializedName("authenticationHttpHeaderValue")
  private String authenticationHttpHeaderValue = null;

  @SerializedName("authenticationOauthClientId")
  private String authenticationOauthClientId = null;

  @SerializedName("authenticationOauthClientScope")
  private String authenticationOauthClientScope = null;

  @SerializedName("authenticationOauthClientSecret")
  private String authenticationOauthClientSecret = null;

  @SerializedName("authenticationOauthClientTokenEndpoint")
  private String authenticationOauthClientTokenEndpoint = null;

  @SerializedName("authenticationOauthJwtSecretKey")
  private String authenticationOauthJwtSecretKey = null;

  @SerializedName("authenticationOauthJwtTokenEndpoint")
  private String authenticationOauthJwtTokenEndpoint = null;

  /**
   * The authentication scheme used by the REST Consumer to login to the REST host. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;\&quot;none\&quot;&#x60;. The allowed values and their meaning are:  &lt;pre&gt; \&quot;none\&quot; - Login with no authentication. This may be useful for anonymous connections or when a REST Consumer does not require authentication. \&quot;http-basic\&quot; - Login with a username and optional password according to HTTP Basic authentication as per RFC2616. \&quot;client-certificate\&quot; - Login with a client TLS certificate as per RFC5246. Client certificate authentication is only available on TLS connections. \&quot;http-header\&quot; - Login with a specified HTTP header. \&quot;oauth-client\&quot; - Login with OAuth 2.0 client credentials. \&quot;oauth-jwt\&quot; - Login with OAuth (RFC 7523 JWT Profile). \&quot;transparent\&quot; - Login using the Authorization header from the message properties, if present. Transparent authentication passes along existing Authorization header metadata instead of discarding it. Note that if the message is coming from a REST producer, the REST service must be configured to forward the Authorization header. &lt;/pre&gt; 
   */
  @JsonAdapter(AuthenticationSchemeEnum.Adapter.class)
  public enum AuthenticationSchemeEnum {
    NONE("none"),
    
    HTTP_BASIC("http-basic"),
    
    CLIENT_CERTIFICATE("client-certificate"),
    
    HTTP_HEADER("http-header"),
    
    OAUTH_CLIENT("oauth-client"),
    
    OAUTH_JWT("oauth-jwt"),
    
    TRANSPARENT("transparent");

    private String value;

    AuthenticationSchemeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static AuthenticationSchemeEnum fromValue(String text) {
      for (AuthenticationSchemeEnum b : AuthenticationSchemeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<AuthenticationSchemeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final AuthenticationSchemeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public AuthenticationSchemeEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return AuthenticationSchemeEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("authenticationScheme")
  private AuthenticationSchemeEnum authenticationScheme = null;

  @SerializedName("enabled")
  private Boolean enabled = null;

  /**
   * The HTTP method to use (POST or PUT). This is used only when operating in the REST service \&quot;messaging\&quot; mode and is ignored in \&quot;gateway\&quot; mode. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;\&quot;post\&quot;&#x60;. The allowed values and their meaning are:  &lt;pre&gt; \&quot;post\&quot; - Use the POST HTTP method. \&quot;put\&quot; - Use the PUT HTTP method. &lt;/pre&gt;  Available since 2.17.
   */
  @JsonAdapter(HttpMethodEnum.Adapter.class)
  public enum HttpMethodEnum {
    POST("post"),
    
    PUT("put");

    private String value;

    HttpMethodEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static HttpMethodEnum fromValue(String text) {
      for (HttpMethodEnum b : HttpMethodEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<HttpMethodEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final HttpMethodEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public HttpMethodEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return HttpMethodEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("httpMethod")
  private HttpMethodEnum httpMethod = null;

  @SerializedName("localInterface")
  private String localInterface = null;

  @SerializedName("maxPostWaitTime")
  private Integer maxPostWaitTime = null;

  @SerializedName("msgVpnName")
  private String msgVpnName = null;

  @SerializedName("outgoingConnectionCount")
  private Integer outgoingConnectionCount = null;

  @SerializedName("remoteHost")
  private String remoteHost = null;

  @SerializedName("remotePort")
  private Long remotePort = null;

  @SerializedName("restConsumerName")
  private String restConsumerName = null;

  @SerializedName("restDeliveryPointName")
  private String restDeliveryPointName = null;

  @SerializedName("retryDelay")
  private Integer retryDelay = null;

  @SerializedName("tlsCipherSuiteList")
  private String tlsCipherSuiteList = null;

  @SerializedName("tlsEnabled")
  private Boolean tlsEnabled = null;

  public MsgVpnRestDeliveryPointRestConsumer authenticationClientCertContent(String authenticationClientCertContent) {
    this.authenticationClientCertContent = authenticationClientCertContent;
    return this;
  }

   /**
   * The PEM formatted content for the client certificate that the REST Consumer will present to the REST host. It must consist of a private key and between one and three certificates comprising the certificate trust chain. This attribute is absent from a GET and not updated when absent in a PUT, subject to the exceptions in note 4. Changing this attribute requires an HTTPS connection. The default value is &#x60;\&quot;\&quot;&#x60;. Available since 2.9.
   * @return authenticationClientCertContent
  **/
  @ApiModelProperty(value = "The PEM formatted content for the client certificate that the REST Consumer will present to the REST host. It must consist of a private key and between one and three certificates comprising the certificate trust chain. This attribute is absent from a GET and not updated when absent in a PUT, subject to the exceptions in note 4. Changing this attribute requires an HTTPS connection. The default value is `\"\"`. Available since 2.9.")
  public String getAuthenticationClientCertContent() {
    return authenticationClientCertContent;
  }

  public void setAuthenticationClientCertContent(String authenticationClientCertContent) {
    this.authenticationClientCertContent = authenticationClientCertContent;
  }

  public MsgVpnRestDeliveryPointRestConsumer authenticationClientCertPassword(String authenticationClientCertPassword) {
    this.authenticationClientCertPassword = authenticationClientCertPassword;
    return this;
  }

   /**
   * The password for the client certificate. This attribute is absent from a GET and not updated when absent in a PUT, subject to the exceptions in note 4. Changing this attribute requires an HTTPS connection. The default value is &#x60;\&quot;\&quot;&#x60;. Available since 2.9.
   * @return authenticationClientCertPassword
  **/
  @ApiModelProperty(value = "The password for the client certificate. This attribute is absent from a GET and not updated when absent in a PUT, subject to the exceptions in note 4. Changing this attribute requires an HTTPS connection. The default value is `\"\"`. Available since 2.9.")
  public String getAuthenticationClientCertPassword() {
    return authenticationClientCertPassword;
  }

  public void setAuthenticationClientCertPassword(String authenticationClientCertPassword) {
    this.authenticationClientCertPassword = authenticationClientCertPassword;
  }

  public MsgVpnRestDeliveryPointRestConsumer authenticationHttpBasicPassword(String authenticationHttpBasicPassword) {
    this.authenticationHttpBasicPassword = authenticationHttpBasicPassword;
    return this;
  }

   /**
   * The password for the username. This attribute is absent from a GET and not updated when absent in a PUT, subject to the exceptions in note 4. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;\&quot;\&quot;&#x60;.
   * @return authenticationHttpBasicPassword
  **/
  @ApiModelProperty(value = "The password for the username. This attribute is absent from a GET and not updated when absent in a PUT, subject to the exceptions in note 4. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is `\"\"`.")
  public String getAuthenticationHttpBasicPassword() {
    return authenticationHttpBasicPassword;
  }

  public void setAuthenticationHttpBasicPassword(String authenticationHttpBasicPassword) {
    this.authenticationHttpBasicPassword = authenticationHttpBasicPassword;
  }

  public MsgVpnRestDeliveryPointRestConsumer authenticationHttpBasicUsername(String authenticationHttpBasicUsername) {
    this.authenticationHttpBasicUsername = authenticationHttpBasicUsername;
    return this;
  }

   /**
   * The username that the REST Consumer will use to login to the REST host. Normally a username is only configured when basic authentication is selected for the REST Consumer. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;\&quot;\&quot;&#x60;.
   * @return authenticationHttpBasicUsername
  **/
  @ApiModelProperty(value = "The username that the REST Consumer will use to login to the REST host. Normally a username is only configured when basic authentication is selected for the REST Consumer. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is `\"\"`.")
  public String getAuthenticationHttpBasicUsername() {
    return authenticationHttpBasicUsername;
  }

  public void setAuthenticationHttpBasicUsername(String authenticationHttpBasicUsername) {
    this.authenticationHttpBasicUsername = authenticationHttpBasicUsername;
  }

  public MsgVpnRestDeliveryPointRestConsumer authenticationHttpHeaderName(String authenticationHttpHeaderName) {
    this.authenticationHttpHeaderName = authenticationHttpHeaderName;
    return this;
  }

   /**
   * The authentication header name. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;\&quot;\&quot;&#x60;. Available since 2.15.
   * @return authenticationHttpHeaderName
  **/
  @ApiModelProperty(value = "The authentication header name. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is `\"\"`. Available since 2.15.")
  public String getAuthenticationHttpHeaderName() {
    return authenticationHttpHeaderName;
  }

  public void setAuthenticationHttpHeaderName(String authenticationHttpHeaderName) {
    this.authenticationHttpHeaderName = authenticationHttpHeaderName;
  }

  public MsgVpnRestDeliveryPointRestConsumer authenticationHttpHeaderValue(String authenticationHttpHeaderValue) {
    this.authenticationHttpHeaderValue = authenticationHttpHeaderValue;
    return this;
  }

   /**
   * The authentication header value. This attribute is absent from a GET and not updated when absent in a PUT, subject to the exceptions in note 4. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;\&quot;\&quot;&#x60;. Available since 2.15.
   * @return authenticationHttpHeaderValue
  **/
  @ApiModelProperty(value = "The authentication header value. This attribute is absent from a GET and not updated when absent in a PUT, subject to the exceptions in note 4. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is `\"\"`. Available since 2.15.")
  public String getAuthenticationHttpHeaderValue() {
    return authenticationHttpHeaderValue;
  }

  public void setAuthenticationHttpHeaderValue(String authenticationHttpHeaderValue) {
    this.authenticationHttpHeaderValue = authenticationHttpHeaderValue;
  }

  public MsgVpnRestDeliveryPointRestConsumer authenticationOauthClientId(String authenticationOauthClientId) {
    this.authenticationOauthClientId = authenticationOauthClientId;
    return this;
  }

   /**
   * The OAuth client ID. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;\&quot;\&quot;&#x60;. Available since 2.19.
   * @return authenticationOauthClientId
  **/
  @ApiModelProperty(value = "The OAuth client ID. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is `\"\"`. Available since 2.19.")
  public String getAuthenticationOauthClientId() {
    return authenticationOauthClientId;
  }

  public void setAuthenticationOauthClientId(String authenticationOauthClientId) {
    this.authenticationOauthClientId = authenticationOauthClientId;
  }

  public MsgVpnRestDeliveryPointRestConsumer authenticationOauthClientScope(String authenticationOauthClientScope) {
    this.authenticationOauthClientScope = authenticationOauthClientScope;
    return this;
  }

   /**
   * The OAuth scope. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;\&quot;\&quot;&#x60;. Available since 2.19.
   * @return authenticationOauthClientScope
  **/
  @ApiModelProperty(value = "The OAuth scope. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is `\"\"`. Available since 2.19.")
  public String getAuthenticationOauthClientScope() {
    return authenticationOauthClientScope;
  }

  public void setAuthenticationOauthClientScope(String authenticationOauthClientScope) {
    this.authenticationOauthClientScope = authenticationOauthClientScope;
  }

  public MsgVpnRestDeliveryPointRestConsumer authenticationOauthClientSecret(String authenticationOauthClientSecret) {
    this.authenticationOauthClientSecret = authenticationOauthClientSecret;
    return this;
  }

   /**
   * The OAuth client secret. This attribute is absent from a GET and not updated when absent in a PUT, subject to the exceptions in note 4. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;\&quot;\&quot;&#x60;. Available since 2.19.
   * @return authenticationOauthClientSecret
  **/
  @ApiModelProperty(value = "The OAuth client secret. This attribute is absent from a GET and not updated when absent in a PUT, subject to the exceptions in note 4. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is `\"\"`. Available since 2.19.")
  public String getAuthenticationOauthClientSecret() {
    return authenticationOauthClientSecret;
  }

  public void setAuthenticationOauthClientSecret(String authenticationOauthClientSecret) {
    this.authenticationOauthClientSecret = authenticationOauthClientSecret;
  }

  public MsgVpnRestDeliveryPointRestConsumer authenticationOauthClientTokenEndpoint(String authenticationOauthClientTokenEndpoint) {
    this.authenticationOauthClientTokenEndpoint = authenticationOauthClientTokenEndpoint;
    return this;
  }

   /**
   * The OAuth token endpoint URL that the REST Consumer will use to request a token for login to the REST host. Must begin with \&quot;https\&quot;. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;\&quot;\&quot;&#x60;. Available since 2.19.
   * @return authenticationOauthClientTokenEndpoint
  **/
  @ApiModelProperty(value = "The OAuth token endpoint URL that the REST Consumer will use to request a token for login to the REST host. Must begin with \"https\". Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is `\"\"`. Available since 2.19.")
  public String getAuthenticationOauthClientTokenEndpoint() {
    return authenticationOauthClientTokenEndpoint;
  }

  public void setAuthenticationOauthClientTokenEndpoint(String authenticationOauthClientTokenEndpoint) {
    this.authenticationOauthClientTokenEndpoint = authenticationOauthClientTokenEndpoint;
  }

  public MsgVpnRestDeliveryPointRestConsumer authenticationOauthJwtSecretKey(String authenticationOauthJwtSecretKey) {
    this.authenticationOauthJwtSecretKey = authenticationOauthJwtSecretKey;
    return this;
  }

   /**
   * The OAuth secret key used to sign the token request JWT. This attribute is absent from a GET and not updated when absent in a PUT, subject to the exceptions in note 4. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;\&quot;\&quot;&#x60;. Available since 2.21.
   * @return authenticationOauthJwtSecretKey
  **/
  @ApiModelProperty(value = "The OAuth secret key used to sign the token request JWT. This attribute is absent from a GET and not updated when absent in a PUT, subject to the exceptions in note 4. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is `\"\"`. Available since 2.21.")
  public String getAuthenticationOauthJwtSecretKey() {
    return authenticationOauthJwtSecretKey;
  }

  public void setAuthenticationOauthJwtSecretKey(String authenticationOauthJwtSecretKey) {
    this.authenticationOauthJwtSecretKey = authenticationOauthJwtSecretKey;
  }

  public MsgVpnRestDeliveryPointRestConsumer authenticationOauthJwtTokenEndpoint(String authenticationOauthJwtTokenEndpoint) {
    this.authenticationOauthJwtTokenEndpoint = authenticationOauthJwtTokenEndpoint;
    return this;
  }

   /**
   * The OAuth token endpoint URL that the REST Consumer will use to request a token for login to the REST host. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;\&quot;\&quot;&#x60;. Available since 2.21.
   * @return authenticationOauthJwtTokenEndpoint
  **/
  @ApiModelProperty(value = "The OAuth token endpoint URL that the REST Consumer will use to request a token for login to the REST host. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is `\"\"`. Available since 2.21.")
  public String getAuthenticationOauthJwtTokenEndpoint() {
    return authenticationOauthJwtTokenEndpoint;
  }

  public void setAuthenticationOauthJwtTokenEndpoint(String authenticationOauthJwtTokenEndpoint) {
    this.authenticationOauthJwtTokenEndpoint = authenticationOauthJwtTokenEndpoint;
  }

  public MsgVpnRestDeliveryPointRestConsumer authenticationScheme(AuthenticationSchemeEnum authenticationScheme) {
    this.authenticationScheme = authenticationScheme;
    return this;
  }

   /**
   * The authentication scheme used by the REST Consumer to login to the REST host. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;\&quot;none\&quot;&#x60;. The allowed values and their meaning are:  &lt;pre&gt; \&quot;none\&quot; - Login with no authentication. This may be useful for anonymous connections or when a REST Consumer does not require authentication. \&quot;http-basic\&quot; - Login with a username and optional password according to HTTP Basic authentication as per RFC2616. \&quot;client-certificate\&quot; - Login with a client TLS certificate as per RFC5246. Client certificate authentication is only available on TLS connections. \&quot;http-header\&quot; - Login with a specified HTTP header. \&quot;oauth-client\&quot; - Login with OAuth 2.0 client credentials. \&quot;oauth-jwt\&quot; - Login with OAuth (RFC 7523 JWT Profile). \&quot;transparent\&quot; - Login using the Authorization header from the message properties, if present. Transparent authentication passes along existing Authorization header metadata instead of discarding it. Note that if the message is coming from a REST producer, the REST service must be configured to forward the Authorization header. &lt;/pre&gt; 
   * @return authenticationScheme
  **/
  @ApiModelProperty(value = "The authentication scheme used by the REST Consumer to login to the REST host. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is `\"none\"`. The allowed values and their meaning are:  <pre> \"none\" - Login with no authentication. This may be useful for anonymous connections or when a REST Consumer does not require authentication. \"http-basic\" - Login with a username and optional password according to HTTP Basic authentication as per RFC2616. \"client-certificate\" - Login with a client TLS certificate as per RFC5246. Client certificate authentication is only available on TLS connections. \"http-header\" - Login with a specified HTTP header. \"oauth-client\" - Login with OAuth 2.0 client credentials. \"oauth-jwt\" - Login with OAuth (RFC 7523 JWT Profile). \"transparent\" - Login using the Authorization header from the message properties, if present. Transparent authentication passes along existing Authorization header metadata instead of discarding it. Note that if the message is coming from a REST producer, the REST service must be configured to forward the Authorization header. </pre> ")
  public AuthenticationSchemeEnum getAuthenticationScheme() {
    return authenticationScheme;
  }

  public void setAuthenticationScheme(AuthenticationSchemeEnum authenticationScheme) {
    this.authenticationScheme = authenticationScheme;
  }

  public MsgVpnRestDeliveryPointRestConsumer enabled(Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

   /**
   * Enable or disable the REST Consumer. When disabled, no connections are initiated or messages delivered to this particular REST Consumer. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;false&#x60;.
   * @return enabled
  **/
  @ApiModelProperty(value = "Enable or disable the REST Consumer. When disabled, no connections are initiated or messages delivered to this particular REST Consumer. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is `false`.")
  public Boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public MsgVpnRestDeliveryPointRestConsumer httpMethod(HttpMethodEnum httpMethod) {
    this.httpMethod = httpMethod;
    return this;
  }

   /**
   * The HTTP method to use (POST or PUT). This is used only when operating in the REST service \&quot;messaging\&quot; mode and is ignored in \&quot;gateway\&quot; mode. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;\&quot;post\&quot;&#x60;. The allowed values and their meaning are:  &lt;pre&gt; \&quot;post\&quot; - Use the POST HTTP method. \&quot;put\&quot; - Use the PUT HTTP method. &lt;/pre&gt;  Available since 2.17.
   * @return httpMethod
  **/
  @ApiModelProperty(value = "The HTTP method to use (POST or PUT). This is used only when operating in the REST service \"messaging\" mode and is ignored in \"gateway\" mode. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is `\"post\"`. The allowed values and their meaning are:  <pre> \"post\" - Use the POST HTTP method. \"put\" - Use the PUT HTTP method. </pre>  Available since 2.17.")
  public HttpMethodEnum getHttpMethod() {
    return httpMethod;
  }

  public void setHttpMethod(HttpMethodEnum httpMethod) {
    this.httpMethod = httpMethod;
  }

  public MsgVpnRestDeliveryPointRestConsumer localInterface(String localInterface) {
    this.localInterface = localInterface;
    return this;
  }

   /**
   * The interface that will be used for all outgoing connections associated with the REST Consumer. When unspecified, an interface is automatically chosen. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;\&quot;\&quot;&#x60;.
   * @return localInterface
  **/
  @ApiModelProperty(value = "The interface that will be used for all outgoing connections associated with the REST Consumer. When unspecified, an interface is automatically chosen. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is `\"\"`.")
  public String getLocalInterface() {
    return localInterface;
  }

  public void setLocalInterface(String localInterface) {
    this.localInterface = localInterface;
  }

  public MsgVpnRestDeliveryPointRestConsumer maxPostWaitTime(Integer maxPostWaitTime) {
    this.maxPostWaitTime = maxPostWaitTime;
    return this;
  }

   /**
   * The maximum amount of time (in seconds) to wait for an HTTP POST response from the REST Consumer. Once this time is exceeded, the TCP connection is reset. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;30&#x60;.
   * @return maxPostWaitTime
  **/
  @ApiModelProperty(value = "The maximum amount of time (in seconds) to wait for an HTTP POST response from the REST Consumer. Once this time is exceeded, the TCP connection is reset. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is `30`.")
  public Integer getMaxPostWaitTime() {
    return maxPostWaitTime;
  }

  public void setMaxPostWaitTime(Integer maxPostWaitTime) {
    this.maxPostWaitTime = maxPostWaitTime;
  }

  public MsgVpnRestDeliveryPointRestConsumer msgVpnName(String msgVpnName) {
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

  public MsgVpnRestDeliveryPointRestConsumer outgoingConnectionCount(Integer outgoingConnectionCount) {
    this.outgoingConnectionCount = outgoingConnectionCount;
    return this;
  }

   /**
   * The number of concurrent TCP connections open to the REST Consumer. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;3&#x60;.
   * @return outgoingConnectionCount
  **/
  @ApiModelProperty(value = "The number of concurrent TCP connections open to the REST Consumer. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is `3`.")
  public Integer getOutgoingConnectionCount() {
    return outgoingConnectionCount;
  }

  public void setOutgoingConnectionCount(Integer outgoingConnectionCount) {
    this.outgoingConnectionCount = outgoingConnectionCount;
  }

  public MsgVpnRestDeliveryPointRestConsumer remoteHost(String remoteHost) {
    this.remoteHost = remoteHost;
    return this;
  }

   /**
   * The IP address or DNS name to which the broker is to connect to deliver messages for the REST Consumer. A host value must be configured for the REST Consumer to be operationally up. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;\&quot;\&quot;&#x60;.
   * @return remoteHost
  **/
  @ApiModelProperty(value = "The IP address or DNS name to which the broker is to connect to deliver messages for the REST Consumer. A host value must be configured for the REST Consumer to be operationally up. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is `\"\"`.")
  public String getRemoteHost() {
    return remoteHost;
  }

  public void setRemoteHost(String remoteHost) {
    this.remoteHost = remoteHost;
  }

  public MsgVpnRestDeliveryPointRestConsumer remotePort(Long remotePort) {
    this.remotePort = remotePort;
    return this;
  }

   /**
   * The port associated with the host of the REST Consumer. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;8080&#x60;.
   * @return remotePort
  **/
  @ApiModelProperty(value = "The port associated with the host of the REST Consumer. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is `8080`.")
  public Long getRemotePort() {
    return remotePort;
  }

  public void setRemotePort(Long remotePort) {
    this.remotePort = remotePort;
  }

  public MsgVpnRestDeliveryPointRestConsumer restConsumerName(String restConsumerName) {
    this.restConsumerName = restConsumerName;
    return this;
  }

   /**
   * The name of the REST Consumer.
   * @return restConsumerName
  **/
  @ApiModelProperty(value = "The name of the REST Consumer.")
  public String getRestConsumerName() {
    return restConsumerName;
  }

  public void setRestConsumerName(String restConsumerName) {
    this.restConsumerName = restConsumerName;
  }

  public MsgVpnRestDeliveryPointRestConsumer restDeliveryPointName(String restDeliveryPointName) {
    this.restDeliveryPointName = restDeliveryPointName;
    return this;
  }

   /**
   * The name of the REST Delivery Point.
   * @return restDeliveryPointName
  **/
  @ApiModelProperty(value = "The name of the REST Delivery Point.")
  public String getRestDeliveryPointName() {
    return restDeliveryPointName;
  }

  public void setRestDeliveryPointName(String restDeliveryPointName) {
    this.restDeliveryPointName = restDeliveryPointName;
  }

  public MsgVpnRestDeliveryPointRestConsumer retryDelay(Integer retryDelay) {
    this.retryDelay = retryDelay;
    return this;
  }

   /**
   * The number of seconds that must pass before retrying the remote REST Consumer connection. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;3&#x60;.
   * @return retryDelay
  **/
  @ApiModelProperty(value = "The number of seconds that must pass before retrying the remote REST Consumer connection. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is `3`.")
  public Integer getRetryDelay() {
    return retryDelay;
  }

  public void setRetryDelay(Integer retryDelay) {
    this.retryDelay = retryDelay;
  }

  public MsgVpnRestDeliveryPointRestConsumer tlsCipherSuiteList(String tlsCipherSuiteList) {
    this.tlsCipherSuiteList = tlsCipherSuiteList;
    return this;
  }

   /**
   * The colon-separated list of cipher suites the REST Consumer uses in its encrypted connection. The value &#x60;\&quot;default\&quot;&#x60; implies all supported suites ordered from most secure to least secure. The list of default cipher suites is available in the &#x60;tlsCipherSuiteMsgBackboneDefaultList&#x60; attribute of the Broker object in the Monitoring API. The REST Consumer should choose the first suite from this list that it supports. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;\&quot;default\&quot;&#x60;.
   * @return tlsCipherSuiteList
  **/
  @ApiModelProperty(value = "The colon-separated list of cipher suites the REST Consumer uses in its encrypted connection. The value `\"default\"` implies all supported suites ordered from most secure to least secure. The list of default cipher suites is available in the `tlsCipherSuiteMsgBackboneDefaultList` attribute of the Broker object in the Monitoring API. The REST Consumer should choose the first suite from this list that it supports. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is `\"default\"`.")
  public String getTlsCipherSuiteList() {
    return tlsCipherSuiteList;
  }

  public void setTlsCipherSuiteList(String tlsCipherSuiteList) {
    this.tlsCipherSuiteList = tlsCipherSuiteList;
  }

  public MsgVpnRestDeliveryPointRestConsumer tlsEnabled(Boolean tlsEnabled) {
    this.tlsEnabled = tlsEnabled;
    return this;
  }

   /**
   * Enable or disable encryption (TLS) for the REST Consumer. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;false&#x60;.
   * @return tlsEnabled
  **/
  @ApiModelProperty(value = "Enable or disable encryption (TLS) for the REST Consumer. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is `false`.")
  public Boolean isTlsEnabled() {
    return tlsEnabled;
  }

  public void setTlsEnabled(Boolean tlsEnabled) {
    this.tlsEnabled = tlsEnabled;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MsgVpnRestDeliveryPointRestConsumer msgVpnRestDeliveryPointRestConsumer = (MsgVpnRestDeliveryPointRestConsumer) o;
    return Objects.equals(this.authenticationClientCertContent, msgVpnRestDeliveryPointRestConsumer.authenticationClientCertContent) &&
        Objects.equals(this.authenticationClientCertPassword, msgVpnRestDeliveryPointRestConsumer.authenticationClientCertPassword) &&
        Objects.equals(this.authenticationHttpBasicPassword, msgVpnRestDeliveryPointRestConsumer.authenticationHttpBasicPassword) &&
        Objects.equals(this.authenticationHttpBasicUsername, msgVpnRestDeliveryPointRestConsumer.authenticationHttpBasicUsername) &&
        Objects.equals(this.authenticationHttpHeaderName, msgVpnRestDeliveryPointRestConsumer.authenticationHttpHeaderName) &&
        Objects.equals(this.authenticationHttpHeaderValue, msgVpnRestDeliveryPointRestConsumer.authenticationHttpHeaderValue) &&
        Objects.equals(this.authenticationOauthClientId, msgVpnRestDeliveryPointRestConsumer.authenticationOauthClientId) &&
        Objects.equals(this.authenticationOauthClientScope, msgVpnRestDeliveryPointRestConsumer.authenticationOauthClientScope) &&
        Objects.equals(this.authenticationOauthClientSecret, msgVpnRestDeliveryPointRestConsumer.authenticationOauthClientSecret) &&
        Objects.equals(this.authenticationOauthClientTokenEndpoint, msgVpnRestDeliveryPointRestConsumer.authenticationOauthClientTokenEndpoint) &&
        Objects.equals(this.authenticationOauthJwtSecretKey, msgVpnRestDeliveryPointRestConsumer.authenticationOauthJwtSecretKey) &&
        Objects.equals(this.authenticationOauthJwtTokenEndpoint, msgVpnRestDeliveryPointRestConsumer.authenticationOauthJwtTokenEndpoint) &&
        Objects.equals(this.authenticationScheme, msgVpnRestDeliveryPointRestConsumer.authenticationScheme) &&
        Objects.equals(this.enabled, msgVpnRestDeliveryPointRestConsumer.enabled) &&
        Objects.equals(this.httpMethod, msgVpnRestDeliveryPointRestConsumer.httpMethod) &&
        Objects.equals(this.localInterface, msgVpnRestDeliveryPointRestConsumer.localInterface) &&
        Objects.equals(this.maxPostWaitTime, msgVpnRestDeliveryPointRestConsumer.maxPostWaitTime) &&
        Objects.equals(this.msgVpnName, msgVpnRestDeliveryPointRestConsumer.msgVpnName) &&
        Objects.equals(this.outgoingConnectionCount, msgVpnRestDeliveryPointRestConsumer.outgoingConnectionCount) &&
        Objects.equals(this.remoteHost, msgVpnRestDeliveryPointRestConsumer.remoteHost) &&
        Objects.equals(this.remotePort, msgVpnRestDeliveryPointRestConsumer.remotePort) &&
        Objects.equals(this.restConsumerName, msgVpnRestDeliveryPointRestConsumer.restConsumerName) &&
        Objects.equals(this.restDeliveryPointName, msgVpnRestDeliveryPointRestConsumer.restDeliveryPointName) &&
        Objects.equals(this.retryDelay, msgVpnRestDeliveryPointRestConsumer.retryDelay) &&
        Objects.equals(this.tlsCipherSuiteList, msgVpnRestDeliveryPointRestConsumer.tlsCipherSuiteList) &&
        Objects.equals(this.tlsEnabled, msgVpnRestDeliveryPointRestConsumer.tlsEnabled);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authenticationClientCertContent, authenticationClientCertPassword, authenticationHttpBasicPassword, authenticationHttpBasicUsername, authenticationHttpHeaderName, authenticationHttpHeaderValue, authenticationOauthClientId, authenticationOauthClientScope, authenticationOauthClientSecret, authenticationOauthClientTokenEndpoint, authenticationOauthJwtSecretKey, authenticationOauthJwtTokenEndpoint, authenticationScheme, enabled, httpMethod, localInterface, maxPostWaitTime, msgVpnName, outgoingConnectionCount, remoteHost, remotePort, restConsumerName, restDeliveryPointName, retryDelay, tlsCipherSuiteList, tlsEnabled);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MsgVpnRestDeliveryPointRestConsumer {\n");
    
    sb.append("    authenticationClientCertContent: ").append(toIndentedString(authenticationClientCertContent)).append("\n");
    sb.append("    authenticationClientCertPassword: ").append(toIndentedString(authenticationClientCertPassword)).append("\n");
    sb.append("    authenticationHttpBasicPassword: ").append(toIndentedString(authenticationHttpBasicPassword)).append("\n");
    sb.append("    authenticationHttpBasicUsername: ").append(toIndentedString(authenticationHttpBasicUsername)).append("\n");
    sb.append("    authenticationHttpHeaderName: ").append(toIndentedString(authenticationHttpHeaderName)).append("\n");
    sb.append("    authenticationHttpHeaderValue: ").append(toIndentedString(authenticationHttpHeaderValue)).append("\n");
    sb.append("    authenticationOauthClientId: ").append(toIndentedString(authenticationOauthClientId)).append("\n");
    sb.append("    authenticationOauthClientScope: ").append(toIndentedString(authenticationOauthClientScope)).append("\n");
    sb.append("    authenticationOauthClientSecret: ").append(toIndentedString(authenticationOauthClientSecret)).append("\n");
    sb.append("    authenticationOauthClientTokenEndpoint: ").append(toIndentedString(authenticationOauthClientTokenEndpoint)).append("\n");
    sb.append("    authenticationOauthJwtSecretKey: ").append(toIndentedString(authenticationOauthJwtSecretKey)).append("\n");
    sb.append("    authenticationOauthJwtTokenEndpoint: ").append(toIndentedString(authenticationOauthJwtTokenEndpoint)).append("\n");
    sb.append("    authenticationScheme: ").append(toIndentedString(authenticationScheme)).append("\n");
    sb.append("    enabled: ").append(toIndentedString(enabled)).append("\n");
    sb.append("    httpMethod: ").append(toIndentedString(httpMethod)).append("\n");
    sb.append("    localInterface: ").append(toIndentedString(localInterface)).append("\n");
    sb.append("    maxPostWaitTime: ").append(toIndentedString(maxPostWaitTime)).append("\n");
    sb.append("    msgVpnName: ").append(toIndentedString(msgVpnName)).append("\n");
    sb.append("    outgoingConnectionCount: ").append(toIndentedString(outgoingConnectionCount)).append("\n");
    sb.append("    remoteHost: ").append(toIndentedString(remoteHost)).append("\n");
    sb.append("    remotePort: ").append(toIndentedString(remotePort)).append("\n");
    sb.append("    restConsumerName: ").append(toIndentedString(restConsumerName)).append("\n");
    sb.append("    restDeliveryPointName: ").append(toIndentedString(restDeliveryPointName)).append("\n");
    sb.append("    retryDelay: ").append(toIndentedString(retryDelay)).append("\n");
    sb.append("    tlsCipherSuiteList: ").append(toIndentedString(tlsCipherSuiteList)).append("\n");
    sb.append("    tlsEnabled: ").append(toIndentedString(tlsEnabled)).append("\n");
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

