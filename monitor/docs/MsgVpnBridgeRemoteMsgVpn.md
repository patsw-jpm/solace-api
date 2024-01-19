
# MsgVpnBridgeRemoteMsgVpn

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**boundToQueue** | **Boolean** | Indicates whether the Bridge is bound to the queue in the remote Message VPN. |  [optional]
**bridgeName** | **String** | The name of the Bridge. |  [optional]
**bridgeVirtualRouter** | [**BridgeVirtualRouterEnum**](#BridgeVirtualRouterEnum) | The virtual router of the Bridge. The allowed values and their meaning are:  &lt;pre&gt; \&quot;primary\&quot; - The Bridge is used for the primary virtual router. \&quot;backup\&quot; - The Bridge is used for the backup virtual router. \&quot;auto\&quot; - The Bridge is automatically assigned a virtual router at creation, depending on the broker&#39;s active-standby role. &lt;/pre&gt;  |  [optional]
**clientUsername** | **String** | The Client Username the Bridge uses to login to the remote Message VPN. This per remote Message VPN value overrides the value provided for the Bridge overall. |  [optional]
**compressedDataEnabled** | **Boolean** | Indicates whether data compression is enabled for the remote Message VPN connection. |  [optional]
**connectOrder** | **Integer** | The preference given to incoming connections from remote Message VPN hosts, from 1 (highest priority) to 4 (lowest priority). |  [optional]
**egressFlowWindowSize** | **Long** | The number of outstanding guaranteed messages that can be transmitted over the remote Message VPN connection before an acknowledgement is received. |  [optional]
**enabled** | **Boolean** | Indicates whether the remote Message VPN is enabled. |  [optional]
**lastConnectionFailureReason** | **String** | The reason for the last connection failure to the remote Message VPN. |  [optional]
**lastQueueBindFailureReason** | **String** | The reason for the last queue bind failure in the remote Message VPN. |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**queueBinding** | **String** | The queue binding of the Bridge in the remote Message VPN. |  [optional]
**queueBoundUptime** | **Integer** | The amount of time in seconds since the queue was bound in the remote Message VPN. |  [optional]
**remoteMsgVpnInterface** | **String** | The physical interface on the local Message VPN host for connecting to the remote Message VPN. By default, an interface is chosen automatically (recommended), but if specified, &#x60;remoteMsgVpnLocation&#x60; must not be a virtual router name. |  [optional]
**remoteMsgVpnLocation** | **String** | The location of the remote Message VPN as either an FQDN with port, IP address with port, or virtual router name (starting with \&quot;v:\&quot;). |  [optional]
**remoteMsgVpnName** | **String** | The name of the remote Message VPN. |  [optional]
**tlsEnabled** | **Boolean** | Indicates whether encryption (TLS) is enabled for the remote Message VPN connection. |  [optional]
**unidirectionalClientProfile** | **String** | The Client Profile for the unidirectional Bridge of the remote Message VPN. The Client Profile must exist in the local Message VPN, and it is used only for the TCP parameters. Note that the default client profile has a TCP maximum window size of 2MB. |  [optional]
**up** | **Boolean** | Indicates whether the connection to the remote Message VPN is up. |  [optional]


<a name="BridgeVirtualRouterEnum"></a>
## Enum: BridgeVirtualRouterEnum
Name | Value
---- | -----
PRIMARY | &quot;primary&quot;
BACKUP | &quot;backup&quot;
AUTO | &quot;auto&quot;



