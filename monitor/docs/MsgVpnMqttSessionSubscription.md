
# MsgVpnMqttSessionSubscription

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**mqttSessionClientId** | **String** | The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet. |  [optional]
**mqttSessionVirtualRouter** | [**MqttSessionVirtualRouterEnum**](#MqttSessionVirtualRouterEnum) | The virtual router of the MQTT Session. The allowed values and their meaning are:  &lt;pre&gt; \&quot;primary\&quot; - The MQTT Session belongs to the primary virtual router. \&quot;backup\&quot; - The MQTT Session belongs to the backup virtual router. &lt;/pre&gt;  |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**subscriptionQos** | **Long** | The quality of service (QoS) for the MQTT Session subscription. |  [optional]
**subscriptionTopic** | **String** | The MQTT subscription topic. |  [optional]


<a name="MqttSessionVirtualRouterEnum"></a>
## Enum: MqttSessionVirtualRouterEnum
Name | Value
---- | -----
PRIMARY | &quot;primary&quot;
BACKUP | &quot;backup&quot;



