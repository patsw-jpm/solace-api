
# MsgVpnBridgeLocalSubscription

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**bridgeName** | **String** | The name of the Bridge. |  [optional]
**bridgeVirtualRouter** | [**BridgeVirtualRouterEnum**](#BridgeVirtualRouterEnum) | The virtual router of the Bridge. The allowed values and their meaning are:  &lt;pre&gt; \&quot;primary\&quot; - The Bridge is used for the primary virtual router. \&quot;backup\&quot; - The Bridge is used for the backup virtual router. \&quot;auto\&quot; - The Bridge is automatically assigned a virtual router at creation, depending on the broker&#39;s active-standby role. &lt;/pre&gt;  |  [optional]
**dtoPriority** | **String** | The priority of the Bridge local subscription topic for receiving deliver-to-one (DTO) messages. The allowed values and their meaning are:  &lt;pre&gt; \&quot;p1\&quot; - The 1st or highest priority. \&quot;p2\&quot; - The 2nd highest priority. \&quot;p3\&quot; - The 3rd highest priority. \&quot;p4\&quot; - The 4th highest priority. \&quot;da\&quot; - Ignore priority and deliver always. &lt;/pre&gt;  |  [optional]
**localSubscriptionTopic** | **String** | The topic of the Bridge local subscription. |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]


<a name="BridgeVirtualRouterEnum"></a>
## Enum: BridgeVirtualRouterEnum
Name | Value
---- | -----
PRIMARY | &quot;primary&quot;
BACKUP | &quot;backup&quot;
AUTO | &quot;auto&quot;



