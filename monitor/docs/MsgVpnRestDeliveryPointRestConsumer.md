
# MsgVpnRestDeliveryPointRestConsumer

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**authenticationHttpBasicUsername** | **String** | The username that the REST Consumer will use to login to the REST host. |  [optional]
**authenticationHttpHeaderName** | **String** | The authentication header name. Available since 2.15. |  [optional]
**authenticationOauthClientId** | **String** | The OAuth client ID. Available since 2.19. |  [optional]
**authenticationOauthClientLastFailureReason** | **String** | The reason for the most recent OAuth token retrieval failure. Available since 2.19. |  [optional]
**authenticationOauthClientLastFailureTime** | **Integer** | The time of the last OAuth token retrieval failure. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). Available since 2.19. |  [optional]
**authenticationOauthClientScope** | **String** | The OAuth scope. Available since 2.19. |  [optional]
**authenticationOauthClientTokenEndpoint** | **String** | The OAuth token endpoint URL that the REST Consumer will use to request a token for login to the REST host. Must begin with \&quot;https\&quot;. Available since 2.19. |  [optional]
**authenticationOauthClientTokenLifetime** | **Long** | The validity duration of the OAuth token. Available since 2.19. |  [optional]
**authenticationOauthClientTokenRetrievedTime** | **Integer** | The time at which the broker requested the token from the OAuth token endpoint. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). Available since 2.19. |  [optional]
**authenticationOauthClientTokenState** | **String** | The current state of the current OAuth token. The allowed values and their meaning are:  &lt;pre&gt; \&quot;valid\&quot; - The token is valid. \&quot;invalid\&quot; - The token is invalid. &lt;/pre&gt;  Available since 2.19. |  [optional]
**authenticationOauthJwtLastFailureReason** | **String** | The reason for the most recent OAuth token retrieval failure. Available since 2.21. |  [optional]
**authenticationOauthJwtLastFailureTime** | **Integer** | The time of the last OAuth token retrieval failure. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). Available since 2.21. |  [optional]
**authenticationOauthJwtTokenEndpoint** | **String** | The OAuth token endpoint URL that the REST Consumer will use to request a token for login to the REST host. Available since 2.21. |  [optional]
**authenticationOauthJwtTokenLifetime** | **Long** | The validity duration of the OAuth token. Available since 2.21. |  [optional]
**authenticationOauthJwtTokenRetrievedTime** | **Integer** | The time at which the broker requested the token from the OAuth token endpoint. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). Available since 2.21. |  [optional]
**authenticationOauthJwtTokenState** | **String** | The current state of the current OAuth token. The allowed values and their meaning are:  &lt;pre&gt; \&quot;valid\&quot; - The token is valid. \&quot;invalid\&quot; - The token is invalid. &lt;/pre&gt;  Available since 2.21. |  [optional]
**authenticationScheme** | [**AuthenticationSchemeEnum**](#AuthenticationSchemeEnum) | The authentication scheme used by the REST Consumer to login to the REST host. The allowed values and their meaning are:  &lt;pre&gt; \&quot;none\&quot; - Login with no authentication. This may be useful for anonymous connections or when a REST Consumer does not require authentication. \&quot;http-basic\&quot; - Login with a username and optional password according to HTTP Basic authentication as per RFC2616. \&quot;client-certificate\&quot; - Login with a client TLS certificate as per RFC5246. Client certificate authentication is only available on TLS connections. \&quot;http-header\&quot; - Login with a specified HTTP header. \&quot;oauth-client\&quot; - Login with OAuth 2.0 client credentials. \&quot;oauth-jwt\&quot; - Login with OAuth (RFC 7523 JWT Profile). \&quot;transparent\&quot; - Login using the Authorization header from the message properties, if present. Transparent authentication passes along existing Authorization header metadata instead of discarding it. Note that if the message is coming from a REST producer, the REST service must be configured to forward the Authorization header. &lt;/pre&gt;  |  [optional]
**counter** | [**MsgVpnRestDeliveryPointRestConsumerCounter**](MsgVpnRestDeliveryPointRestConsumerCounter.md) |  |  [optional]
**enabled** | **Boolean** | Indicates whether the REST Consumer is enabled. |  [optional]
**httpMethod** | [**HttpMethodEnum**](#HttpMethodEnum) | The HTTP method to use (POST or PUT). This is used only when operating in the REST service \&quot;messaging\&quot; mode and is ignored in \&quot;gateway\&quot; mode. The allowed values and their meaning are:  &lt;pre&gt; \&quot;post\&quot; - Use the POST HTTP method. \&quot;put\&quot; - Use the PUT HTTP method. &lt;/pre&gt;  Available since 2.17. |  [optional]
**httpRequestConnectionCloseTxMsgCount** | **Long** | The number of HTTP request messages transmitted to the REST Consumer to close the connection. Available since 2.13. |  [optional]
**httpRequestOutstandingTxMsgCount** | **Long** | The number of HTTP request messages transmitted to the REST Consumer that are waiting for a response. Available since 2.13. |  [optional]
**httpRequestTimedOutTxMsgCount** | **Long** | The number of HTTP request messages transmitted to the REST Consumer that have timed out. Available since 2.13. |  [optional]
**httpRequestTxByteCount** | **Long** | The amount of HTTP request messages transmitted to the REST Consumer, in bytes (B). Available since 2.13. |  [optional]
**httpRequestTxMsgCount** | **Long** | The number of HTTP request messages transmitted to the REST Consumer. Available since 2.13. |  [optional]
**httpResponseErrorRxMsgCount** | **Long** | The number of HTTP client/server error response messages received from the REST Consumer. Available since 2.13. |  [optional]
**httpResponseRxByteCount** | **Long** | The amount of HTTP response messages received from the REST Consumer, in bytes (B). Available since 2.13. |  [optional]
**httpResponseRxMsgCount** | **Long** | The number of HTTP response messages received from the REST Consumer. Available since 2.13. |  [optional]
**httpResponseSuccessRxMsgCount** | **Long** | The number of HTTP successful response messages received from the REST Consumer. Available since 2.13. |  [optional]
**lastConnectionFailureLocalEndpoint** | **String** | The local endpoint at the time of the last connection failure. |  [optional]
**lastConnectionFailureReason** | **String** | The reason for the last connection failure between local and remote endpoints. |  [optional]
**lastConnectionFailureRemoteEndpoint** | **String** | The remote endpoint at the time of the last connection failure. |  [optional]
**lastConnectionFailureTime** | **Integer** | The timestamp of the last connection failure between local and remote endpoints. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**lastFailureReason** | **String** | The reason for the last REST Consumer failure. |  [optional]
**lastFailureTime** | **Integer** | The timestamp of the last REST Consumer failure. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**localInterface** | **String** | The interface that will be used for all outgoing connections associated with the REST Consumer. When unspecified, an interface is automatically chosen. |  [optional]
**maxPostWaitTime** | **Integer** | The maximum amount of time (in seconds) to wait for an HTTP POST response from the REST Consumer. Once this time is exceeded, the TCP connection is reset. |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**outgoingConnectionCount** | **Integer** | The number of concurrent TCP connections open to the REST Consumer. |  [optional]
**remoteHost** | **String** | The IP address or DNS name for the REST Consumer. |  [optional]
**remoteOutgoingConnectionUpCount** | **Long** | The number of outgoing connections for the REST Consumer that are up. |  [optional]
**remotePort** | **Long** | The port associated with the host of the REST Consumer. |  [optional]
**restConsumerName** | **String** | The name of the REST Consumer. |  [optional]
**restDeliveryPointName** | **String** | The name of the REST Delivery Point. |  [optional]
**retryDelay** | **Integer** | The number of seconds that must pass before retrying the remote REST Consumer connection. |  [optional]
**tlsCipherSuiteList** | **String** | The colon-separated list of cipher suites the REST Consumer uses in its encrypted connection. The value &#x60;\&quot;default\&quot;&#x60; implies all supported suites ordered from most secure to least secure. The list of default cipher suites is available in the &#x60;tlsCipherSuiteMsgBackboneDefaultList&#x60; attribute of the Broker object in the Monitoring API. The REST Consumer should choose the first suite from this list that it supports. |  [optional]
**tlsEnabled** | **Boolean** | Indicates whether encryption (TLS) is enabled for the REST Consumer. |  [optional]
**up** | **Boolean** | Indicates whether the operational state of the REST Consumer is up. |  [optional]


<a name="AuthenticationSchemeEnum"></a>
## Enum: AuthenticationSchemeEnum
Name | Value
---- | -----
NONE | &quot;none&quot;
HTTP_BASIC | &quot;http-basic&quot;
CLIENT_CERTIFICATE | &quot;client-certificate&quot;
HTTP_HEADER | &quot;http-header&quot;
OAUTH_CLIENT | &quot;oauth-client&quot;
OAUTH_JWT | &quot;oauth-jwt&quot;
TRANSPARENT | &quot;transparent&quot;


<a name="HttpMethodEnum"></a>
## Enum: HttpMethodEnum
Name | Value
---- | -----
POST | &quot;post&quot;
PUT | &quot;put&quot;



