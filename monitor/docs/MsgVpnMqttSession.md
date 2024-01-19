
# MsgVpnMqttSession

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**clean** | **Boolean** | Indicates whether the Client requested a clean (newly created) MQTT Session when connecting. If not clean (already existing), then previously stored messages for QoS 1 subscriptions are delivered. |  [optional]
**clientName** | **String** | The name of the MQTT Session Client. |  [optional]
**counter** | [**MsgVpnMqttSessionCounter**](MsgVpnMqttSessionCounter.md) |  |  [optional]
**createdByManagement** | **Boolean** | Indicates whether the MQTT Session was created by a Management API. |  [optional]
**durable** | **Boolean** | Indicates whether the MQTT Session is durable. Disconnected durable MQTT Sessions are deleted when their expiry time is reached. Disconnected non-durable MQTT Sessions are deleted immediately. Available since 2.21. |  [optional]
**enabled** | **Boolean** | Indicates whether the MQTT Session is enabled. |  [optional]
**expiryTime** | **Long** | The timestamp of when the disconnected MQTT session expires and is deleted. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). A value of 0 indicates that the session is either connected, or will never expire. Available since 2.21. |  [optional]
**maxPacketSize** | **Long** | The maximum size of a packet, including all headers and payload, that the Client has signaled it is willing to accept. A value of zero indicates no limit. Note that there are other broker settings which may further limit packet size. Available since 2.21. |  [optional]
**mqttConnackErrorTxCount** | **Long** | The number of MQTT connect acknowledgment (CONNACK) refused response packets transmitted to the Client. Available since 2.13. |  [optional]
**mqttConnackTxCount** | **Long** | The number of MQTT connect acknowledgment (CONNACK) accepted response packets transmitted to the Client. Available since 2.13. |  [optional]
**mqttConnectRxCount** | **Long** | The number of MQTT connect (CONNECT) request packets received from the Client. Available since 2.13. |  [optional]
**mqttDisconnectRxCount** | **Long** | The number of MQTT disconnect (DISCONNECT) request packets received from the Client. Available since 2.13. |  [optional]
**mqttPingreqRxCount** | **Long** | The number of MQTT ping request (PINGREQ) packets received from the Client. Available since 2.23. |  [optional]
**mqttPingrespTxCount** | **Long** | The number of MQTT ping response (PINGRESP) packets transmitted to the Client. Available since 2.23. |  [optional]
**mqttPubackRxCount** | **Long** | The number of MQTT publish acknowledgement (PUBACK) response packets received from the Client. Available since 2.23. |  [optional]
**mqttPubackTxCount** | **Long** | The number of MQTT publish acknowledgement (PUBACK) response packets transmitted to the Client. Available since 2.23. |  [optional]
**mqttPubcompTxCount** | **Long** | The number of MQTT publish complete (PUBCOMP) packets transmitted to the Client in response to a PUBREL packet. These packets are the fourth and final packet of a QoS 2 protocol exchange. Available since 2.13. |  [optional]
**mqttPublishQos0RxCount** | **Long** | The number of MQTT publish message (PUBLISH) request packets received from the Client for QoS 0 message delivery. Available since 2.13. |  [optional]
**mqttPublishQos0TxCount** | **Long** | The number of MQTT publish message (PUBLISH) request packets transmitted to the Client for QoS 0 message delivery. Available since 2.13. |  [optional]
**mqttPublishQos1RxCount** | **Long** | The number of MQTT publish message (PUBLISH) request packets received from the Client for QoS 1 message delivery. Available since 2.13. |  [optional]
**mqttPublishQos1TxCount** | **Long** | The number of MQTT publish message (PUBLISH) request packets transmitted to the Client for QoS 1 message delivery. Available since 2.13. |  [optional]
**mqttPublishQos2RxCount** | **Long** | The number of MQTT publish message (PUBLISH) request packets received from the Client for QoS 2 message delivery. Available since 2.13. |  [optional]
**mqttPubrecTxCount** | **Long** | The number of MQTT publish received (PUBREC) packets transmitted to the Client in response to a PUBLISH packet with QoS 2. These packets are the second packet of a QoS 2 protocol exchange. Available since 2.13. |  [optional]
**mqttPubrelRxCount** | **Long** | The number of MQTT publish release (PUBREL) packets received from the Client in response to a PUBREC packet. These packets are the third packet of a QoS 2 protocol exchange. Available since 2.13. |  [optional]
**mqttSessionClientId** | **String** | The Client ID of the MQTT Session, which corresponds to the ClientId provided in the MQTT CONNECT packet. |  [optional]
**mqttSessionVirtualRouter** | [**MqttSessionVirtualRouterEnum**](#MqttSessionVirtualRouterEnum) | The virtual router of the MQTT Session. The allowed values and their meaning are:  &lt;pre&gt; \&quot;primary\&quot; - The MQTT Session belongs to the primary virtual router. \&quot;backup\&quot; - The MQTT Session belongs to the backup virtual router. &lt;/pre&gt;  |  [optional]
**mqttSubackErrorTxCount** | **Long** | The number of MQTT subscribe acknowledgement (SUBACK) failure response packets transmitted to the Client. Available since 2.23. |  [optional]
**mqttSubackTxCount** | **Long** | The number of MQTT subscribe acknowledgement (SUBACK) response packets transmitted to the Client. Available since 2.23. |  [optional]
**mqttSubscribeRxCount** | **Long** | The number of MQTT subscribe (SUBSCRIBE) request packets received from the Client to create one or more topic subscriptions. Available since 2.23. |  [optional]
**mqttUnsubackTxCount** | **Long** | The number of MQTT unsubscribe acknowledgement (UNSUBACK) response packets transmitted to the Client. Available since 2.23. |  [optional]
**mqttUnsubscribeRxCount** | **Long** | The number of MQTT unsubscribe (UNSUBSCRIBE) request packets received from the Client to remove one or more topic subscriptions. Available since 2.23. |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**owner** | **String** | The Client Username which owns the MQTT Session. |  [optional]
**queueConsumerAckPropagationEnabled** | **Boolean** | Indicates whether consumer acknowledgements (ACKs) received on the active replication Message VPN are propagated to the standby replication Message VPN. Available since 2.14. |  [optional]
**queueDeadMsgQueue** | **String** | The name of the Dead Message Queue (DMQ) used by the MQTT Session Queue. Available since 2.14. |  [optional]
**queueEventBindCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**queueEventMsgSpoolUsageThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**queueEventRejectLowPriorityMsgLimitThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**queueMaxBindCount** | **Long** | The maximum number of consumer flows that can bind to the MQTT Session Queue. Available since 2.14. |  [optional]
**queueMaxDeliveredUnackedMsgsPerFlow** | **Long** | The maximum number of messages delivered but not acknowledged per flow for the MQTT Session Queue. Available since 2.14. |  [optional]
**queueMaxMsgSize** | **Integer** | The maximum message size allowed in the MQTT Session Queue, in bytes (B). Available since 2.14. |  [optional]
**queueMaxMsgSpoolUsage** | **Long** | The maximum message spool usage allowed by the MQTT Session Queue, in megabytes (MB). A value of 0 only allows spooling of the last message received and disables quota checking. Available since 2.14. |  [optional]
**queueMaxRedeliveryCount** | **Long** | The maximum number of times the MQTT Session Queue will attempt redelivery of a message prior to it being discarded or moved to the DMQ. A value of 0 means to retry forever. Available since 2.14. |  [optional]
**queueMaxTtl** | **Long** | The maximum time in seconds a message can stay in the MQTT Session Queue when &#x60;queueRespectTtlEnabled&#x60; is &#x60;\&quot;true\&quot;&#x60;. A message expires when the lesser of the sender assigned time-to-live (TTL) in the message and the &#x60;queueMaxTtl&#x60; configured for the MQTT Session Queue, is exceeded. A value of 0 disables expiry. Available since 2.14. |  [optional]
**queueName** | **String** | The name of the MQTT Session Queue. |  [optional]
**queueRejectLowPriorityMsgEnabled** | **Boolean** | Indicates whether to return negative acknowledgements (NACKs) to sending clients on message discards. Note that NACKs cause the message to not be delivered to any destination and Transacted Session commits to fail. Available since 2.14. |  [optional]
**queueRejectLowPriorityMsgLimit** | **Long** | The number of messages of any priority in the MQTT Session Queue above which low priority messages are not admitted but higher priority messages are allowed. Available since 2.14. |  [optional]
**queueRejectMsgToSenderOnDiscardBehavior** | [**QueueRejectMsgToSenderOnDiscardBehaviorEnum**](#QueueRejectMsgToSenderOnDiscardBehaviorEnum) | Indicates whether negative acknowledgements (NACKs) are returned to sending clients on message discards. Note that NACKs cause the message to not be delivered to any destination and Transacted Session commits to fail. The allowed values and their meaning are:  &lt;pre&gt; \&quot;always\&quot; - Always return a negative acknowledgment (NACK) to the sending client on message discard. \&quot;when-queue-enabled\&quot; - Only return a negative acknowledgment (NACK) to the sending client on message discard when the Queue is enabled. \&quot;never\&quot; - Never return a negative acknowledgment (NACK) to the sending client on message discard. &lt;/pre&gt;  Available since 2.14. |  [optional]
**queueRespectTtlEnabled** | **Boolean** | Indicates whether the time-to-live (TTL) for messages in the MQTT Session Queue is respected. When enabled, expired messages are discarded or moved to the DMQ. Available since 2.14. |  [optional]
**rxMax** | **Long** | The maximum number of outstanding QoS1 and QoS2 messages that the Client has signaled it is willing to accept. Note that there are other broker settings which may further limit the number of outstanding messasges. Available since 2.21. |  [optional]
**will** | **Boolean** | Indicates whether the MQTT Session has the Will message specified by the Client. The Will message is published if the Client disconnects without sending the MQTT DISCONNECT packet. |  [optional]


<a name="MqttSessionVirtualRouterEnum"></a>
## Enum: MqttSessionVirtualRouterEnum
Name | Value
---- | -----
PRIMARY | &quot;primary&quot;
BACKUP | &quot;backup&quot;


<a name="QueueRejectMsgToSenderOnDiscardBehaviorEnum"></a>
## Enum: QueueRejectMsgToSenderOnDiscardBehaviorEnum
Name | Value
---- | -----
ALWAYS | &quot;always&quot;
WHEN_QUEUE_ENABLED | &quot;when-queue-enabled&quot;
NEVER | &quot;never&quot;



