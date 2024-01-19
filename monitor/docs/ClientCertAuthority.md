
# ClientCertAuthority

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**certAuthorityName** | **String** | The name of the Certificate Authority. |  [optional]
**certContent** | **String** | The PEM formatted content for the trusted root certificate of a client Certificate Authority. |  [optional]
**crlDayList** | **String** | The scheduled CRL refresh day(s), specified as \&quot;daily\&quot; or a comma-separated list of days. Days must be specified as \&quot;Sun\&quot;, \&quot;Mon\&quot;, \&quot;Tue\&quot;, \&quot;Wed\&quot;, \&quot;Thu\&quot;, \&quot;Fri\&quot;, or \&quot;Sat\&quot;, with no spaces, and in sorted order from Sunday to Saturday. |  [optional]
**crlLastDownloadTime** | **Integer** | The timestamp of the last successful CRL download. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**crlLastFailureReason** | **String** | The reason for the last CRL failure. |  [optional]
**crlLastFailureTime** | **Integer** | The timestamp of the last CRL failure. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**crlNextDownloadTime** | **Integer** | The scheduled time of the next CRL download. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**crlTimeList** | **String** | The scheduled CRL refresh time(s), specified as \&quot;hourly\&quot; or a comma-separated list of 24-hour times in the form hh:mm, or h:mm. There must be no spaces, and times must be in sorted order from 0:00 to 23:59. |  [optional]
**crlUp** | **Boolean** | Indicates whether CRL revocation checking is operationally up. |  [optional]
**crlUrl** | **String** | The URL for the CRL source. This is a required attribute for CRL to be operational and the URL must be complete with http:// included. |  [optional]
**ocspLastFailureReason** | **String** | The reason for the last OCSP failure. |  [optional]
**ocspLastFailureTime** | **Integer** | The timestamp of the last OCSP failure. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**ocspLastFailureUrl** | **String** | The URL involved in the last OCSP failure. |  [optional]
**ocspNonResponderCertEnabled** | **Boolean** | Indicates whether a non-responder certificate is allowed to sign an OCSP response. Typically used with an OCSP override URL in cases where a single certificate is used to sign client certificates and OCSP responses. |  [optional]
**ocspOverrideUrl** | **String** | The OCSP responder URL to use for overriding the one supplied in the client certificate. The URL must be complete with http:// included. |  [optional]
**ocspTimeout** | **Long** | The timeout in seconds to receive a response from the OCSP responder after sending a request or making the initial connection attempt. |  [optional]
**revocationCheckEnabled** | **Boolean** | Indicates whether Certificate Authority revocation checking is enabled. |  [optional]



