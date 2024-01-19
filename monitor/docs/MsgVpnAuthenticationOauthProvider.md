
# MsgVpnAuthenticationOauthProvider

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**audienceClaimName** | **String** | The audience claim name, indicating which part of the object to use for determining the audience. |  [optional]
**audienceClaimSource** | [**AudienceClaimSourceEnum**](#AudienceClaimSourceEnum) | The audience claim source, indicating where to search for the audience value. The allowed values and their meaning are:  &lt;pre&gt; \&quot;access-token\&quot; - The OAuth v2 access_token. \&quot;id-token\&quot; - The OpenID Connect id_token. \&quot;introspection\&quot; - The result of introspecting the OAuth v2 access_token. &lt;/pre&gt;  |  [optional]
**audienceClaimValue** | **String** | The required audience value for a token to be considered valid. |  [optional]
**audienceValidationEnabled** | **Boolean** | Indicates whether audience validation is enabled. |  [optional]
**authenticationSuccessCount** | **Long** | The number of OAuth Provider client authentications that succeeded. |  [optional]
**authorizationGroupClaimName** | **String** | The authorization group claim name, indicating which part of the object to use for determining the authorization group. |  [optional]
**authorizationGroupClaimSource** | [**AuthorizationGroupClaimSourceEnum**](#AuthorizationGroupClaimSourceEnum) | The authorization group claim source, indicating where to search for the authorization group name. The allowed values and their meaning are:  &lt;pre&gt; \&quot;access-token\&quot; - The OAuth v2 access_token. \&quot;id-token\&quot; - The OpenID Connect id_token. \&quot;introspection\&quot; - The result of introspecting the OAuth v2 access_token. &lt;/pre&gt;  |  [optional]
**authorizationGroupEnabled** | **Boolean** | Indicates whether OAuth based authorization is enabled and the configured authorization type for OAuth clients is overridden. |  [optional]
**disconnectOnTokenExpirationEnabled** | **Boolean** | Indicates whether clients are disconnected when their tokens expire. |  [optional]
**enabled** | **Boolean** | Indicates whether OAuth Provider client authentication is enabled. |  [optional]
**jwksLastRefreshFailureReason** | **String** | The reason for the last JWKS public key refresh failure. |  [optional]
**jwksLastRefreshFailureTime** | **Integer** | The timestamp of the last JWKS public key refresh failure. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**jwksLastRefreshTime** | **Integer** | The timestamp of the last JWKS public key refresh success. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**jwksNextScheduledRefreshTime** | **Integer** | The timestamp of the next scheduled JWKS public key refresh. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**jwksRefreshFailureCount** | **Long** | The number of JWKS public key refresh failures. |  [optional]
**jwksRefreshInterval** | **Integer** | The number of seconds between forced JWKS public key refreshing. |  [optional]
**jwksUri** | **String** | The URI where the OAuth provider publishes its JWKS public keys. |  [optional]
**loginFailureIncorrectAudienceValueCount** | **Long** | The number of login failures due to an incorrect audience value. |  [optional]
**loginFailureInvalidAudienceValueCount** | **Long** | The number of login failures due to an invalid audience value. |  [optional]
**loginFailureInvalidAuthorizationGroupValueCount** | **Long** | The number of login failures due to an invalid authorization group value (zero-length or non-string). |  [optional]
**loginFailureInvalidJwtSignatureCount** | **Long** | The number of login failures due to an invalid JWT signature. |  [optional]
**loginFailureInvalidUsernameValueCount** | **Long** | The number of login failures due to an invalid username value. |  [optional]
**loginFailureMismatchedUsernameCount** | **Long** | The number of login failures due to a mismatched username. |  [optional]
**loginFailureMissingAudienceCount** | **Long** | The number of login failures due to a missing audience claim. |  [optional]
**loginFailureMissingJwkCount** | **Long** | The number of login failures due to a missing JSON Web Key (JWK). |  [optional]
**loginFailureMissingOrInvalidTokenCount** | **Long** | The number of login failures due to a missing or invalid token. |  [optional]
**loginFailureMissingUsernameCount** | **Long** | The number of login failures due to a missing username. |  [optional]
**loginFailureTokenExpiredCount** | **Long** | The number of login failures due to a token being expired. |  [optional]
**loginFailureTokenIntrospectionErroredCount** | **Long** | The number of login failures due to a token introspection error response. |  [optional]
**loginFailureTokenIntrospectionFailureCount** | **Long** | The number of login failures due to a failure to complete the token introspection. |  [optional]
**loginFailureTokenIntrospectionHttpsErrorCount** | **Long** | The number of login failures due to a token introspection HTTPS error. |  [optional]
**loginFailureTokenIntrospectionInvalidCount** | **Long** | The number of login failures due to a token introspection response being invalid. |  [optional]
**loginFailureTokenIntrospectionTimeoutCount** | **Long** | The number of login failures due to a token introspection timeout. |  [optional]
**loginFailureTokenNotValidYetCount** | **Long** | The number of login failures due to a token not being valid yet. |  [optional]
**loginFailureUnsupportedAlgCount** | **Long** | The number of login failures due to an unsupported algorithm. |  [optional]
**missingAuthorizationGroupCount** | **Long** | The number of clients that did not provide an authorization group claim value when expected. |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**oauthProviderName** | **String** | The name of the OAuth Provider. |  [optional]
**tokenIgnoreTimeLimitsEnabled** | **Boolean** | Indicates whether to ignore time limits and accept tokens that are not yet valid or are no longer valid. |  [optional]
**tokenIntrospectionAverageTime** | **Integer** | The one minute average of the time required to complete a token introspection, in milliseconds (ms). |  [optional]
**tokenIntrospectionLastFailureReason** | **String** | The reason for the last token introspection failure. |  [optional]
**tokenIntrospectionLastFailureTime** | **Integer** | The timestamp of the last token introspection failure. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**tokenIntrospectionParameterName** | **String** | The parameter name used to identify the token during access token introspection. A standards compliant OAuth introspection server expects \&quot;token\&quot;. |  [optional]
**tokenIntrospectionSuccessCount** | **Long** | The number of token introspection successes. |  [optional]
**tokenIntrospectionTimeout** | **Integer** | The maximum time in seconds a token introspection is allowed to take. |  [optional]
**tokenIntrospectionUri** | **String** | The token introspection URI of the OAuth authentication server. |  [optional]
**tokenIntrospectionUsername** | **String** | The username to use when logging into the token introspection URI. |  [optional]
**usernameClaimName** | **String** | The username claim name, indicating which part of the object to use for determining the username. |  [optional]
**usernameClaimSource** | [**UsernameClaimSourceEnum**](#UsernameClaimSourceEnum) | The username claim source, indicating where to search for the username value. The allowed values and their meaning are:  &lt;pre&gt; \&quot;access-token\&quot; - The OAuth v2 access_token. \&quot;id-token\&quot; - The OpenID Connect id_token. \&quot;introspection\&quot; - The result of introspecting the OAuth v2 access_token. &lt;/pre&gt;  |  [optional]
**usernameValidateEnabled** | **Boolean** | Indicates whether the API provided username will be validated against the username calculated from the token(s). |  [optional]


<a name="AudienceClaimSourceEnum"></a>
## Enum: AudienceClaimSourceEnum
Name | Value
---- | -----
ACCESS_TOKEN | &quot;access-token&quot;
ID_TOKEN | &quot;id-token&quot;
INTROSPECTION | &quot;introspection&quot;


<a name="AuthorizationGroupClaimSourceEnum"></a>
## Enum: AuthorizationGroupClaimSourceEnum
Name | Value
---- | -----
ACCESS_TOKEN | &quot;access-token&quot;
ID_TOKEN | &quot;id-token&quot;
INTROSPECTION | &quot;introspection&quot;


<a name="UsernameClaimSourceEnum"></a>
## Enum: UsernameClaimSourceEnum
Name | Value
---- | -----
ACCESS_TOKEN | &quot;access-token&quot;
ID_TOKEN | &quot;id-token&quot;
INTROSPECTION | &quot;introspection&quot;



