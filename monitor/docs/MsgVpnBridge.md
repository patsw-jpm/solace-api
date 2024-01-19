
# MsgVpnBridge

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**averageRxByteRate** | **Long** | The one minute average of the message rate received from the Bridge, in bytes per second (B/sec). Available since 2.13. |  [optional]
**averageRxMsgRate** | **Long** | The one minute average of the message rate received from the Bridge, in messages per second (msg/sec). Available since 2.13. |  [optional]
**averageTxByteRate** | **Long** | The one minute average of the message rate transmitted to the Bridge, in bytes per second (B/sec). Available since 2.13. |  [optional]
**averageTxMsgRate** | **Long** | The one minute average of the message rate transmitted to the Bridge, in messages per second (msg/sec). Available since 2.13. |  [optional]
**boundToQueue** | **Boolean** | Indicates whether the Bridge is bound to the queue in the remote Message VPN. |  [optional]
**bridgeName** | **String** | The name of the Bridge. |  [optional]
**bridgeVirtualRouter** | [**BridgeVirtualRouterEnum**](#BridgeVirtualRouterEnum) | The virtual router of the Bridge. The allowed values and their meaning are:  &lt;pre&gt; \&quot;primary\&quot; - The Bridge is used for the primary virtual router. \&quot;backup\&quot; - The Bridge is used for the backup virtual router. \&quot;auto\&quot; - The Bridge is automatically assigned a virtual router at creation, depending on the broker&#39;s active-standby role. &lt;/pre&gt;  |  [optional]
**clientName** | **String** | The name of the Client for the Bridge. |  [optional]
**compressed** | **Boolean** | Indicates whether messages transmitted over the Bridge are compressed. |  [optional]
**controlRxByteCount** | **Long** | The amount of client control messages received from the Bridge, in bytes (B). Available since 2.13. |  [optional]
**controlRxMsgCount** | **Long** | The number of client control messages received from the Bridge. Available since 2.13. |  [optional]
**controlTxByteCount** | **Long** | The amount of client control messages transmitted to the Bridge, in bytes (B). Available since 2.13. |  [optional]
**controlTxMsgCount** | **Long** | The number of client control messages transmitted to the Bridge. Available since 2.13. |  [optional]
**counter** | [**MsgVpnBridgeCounter**](MsgVpnBridgeCounter.md) |  |  [optional]
**dataRxByteCount** | **Long** | The amount of client data messages received from the Bridge, in bytes (B). Available since 2.13. |  [optional]
**dataRxMsgCount** | **Long** | The number of client data messages received from the Bridge. Available since 2.13. |  [optional]
**dataTxByteCount** | **Long** | The amount of client data messages transmitted to the Bridge, in bytes (B). Available since 2.13. |  [optional]
**dataTxMsgCount** | **Long** | The number of client data messages transmitted to the Bridge. Available since 2.13. |  [optional]
**discardedRxMsgCount** | **Long** | The number of messages discarded during reception from the Bridge. Available since 2.13. |  [optional]
**discardedTxMsgCount** | **Long** | The number of messages discarded during transmission to the Bridge. Available since 2.13. |  [optional]
**enabled** | **Boolean** | Indicates whether the Bridge is enabled. |  [optional]
**encrypted** | **Boolean** | Indicates whether messages transmitted over the Bridge are encrypted with TLS. |  [optional]
**establisher** | **String** | The establisher of the Bridge connection. The allowed values and their meaning are:  &lt;pre&gt; \&quot;local\&quot; - The Bridge connection was established by the local Message VPN. \&quot;remote\&quot; - The Bridge connection was established by the remote Message VPN. &lt;/pre&gt;  |  [optional]
**inboundFailureReason** | **String** | The reason for the inbound connection failure from the Bridge. If there is no failure reason, an empty string (\&quot;\&quot;) is returned. |  [optional]
**inboundState** | **String** | The state of the inbound connection from the Bridge. The allowed values and their meaning are:  &lt;pre&gt; \&quot;init\&quot; - The connection is initializing. \&quot;disabled\&quot; - The connection is disabled by configuration. \&quot;enabled\&quot; - The connection is enabled by configuration. \&quot;prepare\&quot; - The connection is operationally down. \&quot;prepare-wait-to-connect\&quot; - The connection is waiting to connect. \&quot;prepare-fetching-dns\&quot; - The domain name of the destination node is being resolved. \&quot;not-ready\&quot; - The connection is operationally down. \&quot;not-ready-connecting\&quot; - The connection is trying to connect. \&quot;not-ready-handshaking\&quot; - The connection is handshaking. \&quot;not-ready-wait-next\&quot; - The connection failed to connect and is waiting to retry. \&quot;not-ready-wait-reuse\&quot; - The connection is closing in order to reuse an existing connection. \&quot;not-ready-wait-bridge-version-mismatch\&quot; - The connection is closing because of a version mismatch. \&quot;not-ready-wait-cleanup\&quot; - The connection is closed and cleaning up. \&quot;ready\&quot; - The connection is operationally up. \&quot;ready-subscribing\&quot; - The connection is up and synchronizing subscriptions. \&quot;ready-in-sync\&quot; - The connection is up and subscriptions are synchronized. &lt;/pre&gt;  |  [optional]
**lastTxMsgId** | **Long** | The ID of the last message transmitted to the Bridge. |  [optional]
**localInterface** | **String** | The physical interface on the local Message VPN host for connecting to the remote Message VPN. |  [optional]
**localQueueName** | **String** | The name of the local queue for the Bridge. |  [optional]
**loginRxMsgCount** | **Long** | The number of login request messages received from the Bridge. Available since 2.13. |  [optional]
**loginTxMsgCount** | **Long** | The number of login response messages transmitted to the Bridge. Available since 2.13. |  [optional]
**maxTtl** | **Long** | The maximum time-to-live (TTL) in hops. Messages are discarded if their TTL exceeds this value. |  [optional]
**msgSpoolRxMsgCount** | **Long** | The number of guaranteed messages received from the Bridge. Available since 2.13. |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**outboundState** | **String** | The state of the outbound connection to the Bridge. The allowed values and their meaning are:  &lt;pre&gt; \&quot;init\&quot; - The connection is initializing. \&quot;disabled\&quot; - The connection is disabled by configuration. \&quot;enabled\&quot; - The connection is enabled by configuration. \&quot;prepare\&quot; - The connection is operationally down. \&quot;prepare-wait-to-connect\&quot; - The connection is waiting to connect. \&quot;prepare-fetching-dns\&quot; - The domain name of the destination node is being resolved. \&quot;not-ready\&quot; - The connection is operationally down. \&quot;not-ready-connecting\&quot; - The connection is trying to connect. \&quot;not-ready-handshaking\&quot; - The connection is handshaking. \&quot;not-ready-wait-next\&quot; - The connection failed to connect and is waiting to retry. \&quot;not-ready-wait-reuse\&quot; - The connection is closing in order to reuse an existing connection. \&quot;not-ready-wait-bridge-version-mismatch\&quot; - The connection is closing because of a version mismatch. \&quot;not-ready-wait-cleanup\&quot; - The connection is closed and cleaning up. \&quot;ready\&quot; - The connection is operationally up. \&quot;ready-subscribing\&quot; - The connection is up and synchronizing subscriptions. \&quot;ready-in-sync\&quot; - The connection is up and subscriptions are synchronized. &lt;/pre&gt;  |  [optional]
**rate** | [**MsgVpnBridgeRate**](MsgVpnBridgeRate.md) |  |  [optional]
**remoteAddress** | **String** | The FQDN or IP address of the remote Message VPN. |  [optional]
**remoteAuthenticationBasicClientUsername** | **String** | The Client Username the Bridge uses to login to the remote Message VPN. |  [optional]
**remoteAuthenticationScheme** | [**RemoteAuthenticationSchemeEnum**](#RemoteAuthenticationSchemeEnum) | The authentication scheme for the remote Message VPN. The allowed values and their meaning are:  &lt;pre&gt; \&quot;basic\&quot; - Basic Authentication Scheme (via username and password). \&quot;client-certificate\&quot; - Client Certificate Authentication Scheme (via certificate file or content). &lt;/pre&gt;  |  [optional]
**remoteConnectionRetryCount** | **Long** | The maximum number of retry attempts to establish a connection to the remote Message VPN. A value of 0 means to retry forever. |  [optional]
**remoteConnectionRetryDelay** | **Long** | The number of seconds the broker waits for the bridge connection to be established before attempting a new connection. |  [optional]
**remoteDeliverToOnePriority** | [**RemoteDeliverToOnePriorityEnum**](#RemoteDeliverToOnePriorityEnum) | The priority for deliver-to-one (DTO) messages transmitted from the remote Message VPN. The allowed values and their meaning are:  &lt;pre&gt; \&quot;p1\&quot; - The 1st or highest priority. \&quot;p2\&quot; - The 2nd highest priority. \&quot;p3\&quot; - The 3rd highest priority. \&quot;p4\&quot; - The 4th highest priority. \&quot;da\&quot; - Ignore priority and deliver always. &lt;/pre&gt;  |  [optional]
**remoteMsgVpnName** | **String** | The name of the remote Message VPN. |  [optional]
**remoteRouterName** | **String** | The name of the remote router. |  [optional]
**remoteTxFlowId** | **Integer** | The ID of the transmit flow for the connected remote Message VPN. |  [optional]
**rxByteCount** | **Long** | The amount of messages received from the Bridge, in bytes (B). Available since 2.13. |  [optional]
**rxByteRate** | **Long** | The current message rate received from the Bridge, in bytes per second (B/sec). Available since 2.13. |  [optional]
**rxConnectionFailureCategory** | **String** | The category of the inbound connection failure from the Bridge. The allowed values and their meaning are:  &lt;pre&gt; \&quot;no-failure\&quot; - There is no bridge failure. \&quot;local-configuration-problem\&quot; - The bridge failure is a local configuration problem. \&quot;local-operational-state-problem\&quot; - The bridge failure is an operational state problem. &lt;/pre&gt;  Available since 2.18. |  [optional]
**rxMsgCount** | **Long** | The number of messages received from the Bridge. Available since 2.13. |  [optional]
**rxMsgRate** | **Long** | The current message rate received from the Bridge, in messages per second (msg/sec). Available since 2.13. |  [optional]
**tlsCipherSuiteList** | **String** | The colon-separated list of cipher suites supported for TLS connections to the remote Message VPN. The value \&quot;default\&quot; implies all supported suites ordered from most secure to least secure. |  [optional]
**tlsDefaultCipherSuiteList** | **Boolean** | Indicates whether the Bridge is configured to use the default cipher-suite list. |  [optional]
**ttlExceededEventRaised** | **Boolean** | Indicates whether the TTL (hops) exceeded event has been raised. |  [optional]
**txByteCount** | **Long** | The amount of messages transmitted to the Bridge, in bytes (B). Available since 2.13. |  [optional]
**txByteRate** | **Long** | The current message rate transmitted to the Bridge, in bytes per second (B/sec). Available since 2.13. |  [optional]
**txMsgCount** | **Long** | The number of messages transmitted to the Bridge. Available since 2.13. |  [optional]
**txMsgRate** | **Long** | The current message rate transmitted to the Bridge, in messages per second (msg/sec). Available since 2.13. |  [optional]
**uptime** | **Long** | The amount of time in seconds since the Bridge connected to the remote Message VPN. |  [optional]


<a name="BridgeVirtualRouterEnum"></a>
## Enum: BridgeVirtualRouterEnum
Name | Value
---- | -----
PRIMARY | &quot;primary&quot;
BACKUP | &quot;backup&quot;
AUTO | &quot;auto&quot;


<a name="RemoteAuthenticationSchemeEnum"></a>
## Enum: RemoteAuthenticationSchemeEnum
Name | Value
---- | -----
BASIC | &quot;basic&quot;
CLIENT_CERTIFICATE | &quot;client-certificate&quot;


<a name="RemoteDeliverToOnePriorityEnum"></a>
## Enum: RemoteDeliverToOnePriorityEnum
Name | Value
---- | -----
P1 | &quot;p1&quot;
P2 | &quot;p2&quot;
P3 | &quot;p3&quot;
P4 | &quot;p4&quot;
DA | &quot;da&quot;



