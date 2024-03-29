
# MsgVpnBridgeRemoteMsgVpn

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**bridgeName** | **String** | The name of the Bridge. |  [optional]
**bridgeVirtualRouter** | [**BridgeVirtualRouterEnum**](#BridgeVirtualRouterEnum) | The virtual router of the Bridge. The allowed values and their meaning are:  &lt;pre&gt; \&quot;primary\&quot; - The Bridge is used for the primary virtual router. \&quot;backup\&quot; - The Bridge is used for the backup virtual router. \&quot;auto\&quot; - The Bridge is automatically assigned a virtual router at creation, depending on the broker&#39;s active-standby role. &lt;/pre&gt;  |  [optional]
**clientUsername** | **String** | The Client Username the Bridge uses to login to the remote Message VPN. This per remote Message VPN value overrides the value provided for the Bridge overall. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;\&quot;\&quot;&#x60;. |  [optional]
**compressedDataEnabled** | **Boolean** | Enable or disable data compression for the remote Message VPN connection. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;false&#x60;. |  [optional]
**connectOrder** | **Integer** | The preference given to incoming connections from remote Message VPN hosts, from 1 (highest priority) to 4 (lowest priority). Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;4&#x60;. |  [optional]
**egressFlowWindowSize** | **Long** | The number of outstanding guaranteed messages that can be transmitted over the remote Message VPN connection before an acknowledgement is received. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;255&#x60;. |  [optional]
**enabled** | **Boolean** | Enable or disable the remote Message VPN. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;false&#x60;. |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**password** | **String** | The password for the Client Username. This attribute is absent from a GET and not updated when absent in a PUT, subject to the exceptions in note 4. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;\&quot;\&quot;&#x60;. |  [optional]
**queueBinding** | **String** | The queue binding of the Bridge in the remote Message VPN. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;\&quot;\&quot;&#x60;. |  [optional]
**remoteMsgVpnInterface** | **String** | The physical interface on the local Message VPN host for connecting to the remote Message VPN. By default, an interface is chosen automatically (recommended), but if specified, &#x60;remoteMsgVpnLocation&#x60; must not be a virtual router name. |  [optional]
**remoteMsgVpnLocation** | **String** | The location of the remote Message VPN as either an FQDN with port, IP address with port, or virtual router name (starting with \&quot;v:\&quot;). |  [optional]
**remoteMsgVpnName** | **String** | The name of the remote Message VPN. |  [optional]
**tlsEnabled** | **Boolean** | Enable or disable encryption (TLS) for the remote Message VPN connection. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;false&#x60;. |  [optional]
**unidirectionalClientProfile** | **String** | The Client Profile for the unidirectional Bridge of the remote Message VPN. The Client Profile must exist in the local Message VPN, and it is used only for the TCP parameters. Note that the default client profile has a TCP maximum window size of 2MB. Changes to this attribute are synchronized to HA mates and replication sites via config-sync. The default value is &#x60;\&quot;#client-profile\&quot;&#x60;. |  [optional]


<a name="BridgeVirtualRouterEnum"></a>
## Enum: BridgeVirtualRouterEnum
Name | Value
---- | -----
PRIMARY | &quot;primary&quot;
BACKUP | &quot;backup&quot;
AUTO | &quot;auto&quot;



