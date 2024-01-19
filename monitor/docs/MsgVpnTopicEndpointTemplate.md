
# MsgVpnTopicEndpointTemplate

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**accessType** | [**AccessTypeEnum**](#AccessTypeEnum) | The access type for delivering messages to consumer flows. The allowed values and their meaning are:  &lt;pre&gt; \&quot;exclusive\&quot; - Exclusive delivery of messages to the first bound consumer flow. \&quot;non-exclusive\&quot; - Non-exclusive delivery of messages to all bound consumer flows in a round-robin fashion. &lt;/pre&gt;  |  [optional]
**consumerAckPropagationEnabled** | **Boolean** | Indicates whether the propagation of consumer acknowledgements (ACKs) received on the active replication Message VPN to the standby replication Message VPN is enabled. |  [optional]
**deadMsgQueue** | **String** | The name of the Dead Message Queue (DMQ). |  [optional]
**deliveryDelay** | **Long** | The delay, in seconds, to apply to messages arriving on the Topic Endpoint before the messages are eligible for delivery. Available since 2.22. |  [optional]
**eventBindCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventMsgSpoolUsageThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventRejectLowPriorityMsgLimitThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**maxBindCount** | **Long** | The maximum number of consumer flows that can bind. |  [optional]
**maxDeliveredUnackedMsgsPerFlow** | **Long** | The maximum number of messages delivered but not acknowledged per flow. |  [optional]
**maxMsgSize** | **Integer** | The maximum message size allowed, in bytes (B). |  [optional]
**maxMsgSpoolUsage** | **Long** | The maximum message spool usage allowed, in megabytes (MB). A value of 0 only allows spooling of the last message received and disables quota checking. |  [optional]
**maxRedeliveryCount** | **Long** | The maximum number of message redelivery attempts that will occur prior to the message being discarded or moved to the DMQ. A value of 0 means to retry forever. |  [optional]
**maxTtl** | **Long** | The maximum time in seconds a message can stay in the Topic Endpoint when &#x60;respectTtlEnabled&#x60; is &#x60;\&quot;true\&quot;&#x60;. A message expires when the lesser of the sender assigned time-to-live (TTL) in the message and the &#x60;maxTtl&#x60; configured for the Topic Endpoint, is exceeded. A value of 0 disables expiry. |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**permission** | [**PermissionEnum**](#PermissionEnum) | The permission level for all consumers, excluding the owner. The allowed values and their meaning are:  &lt;pre&gt; \&quot;no-access\&quot; - Disallows all access. \&quot;read-only\&quot; - Read-only access to the messages. \&quot;consume\&quot; - Consume (read and remove) messages. \&quot;modify-topic\&quot; - Consume messages or modify the topic/selector. \&quot;delete\&quot; - Consume messages, modify the topic/selector or delete the Client created endpoint altogether. &lt;/pre&gt;  |  [optional]
**redeliveryEnabled** | **Boolean** | Enable or disable message redelivery. When enabled, the number of redelivery attempts is controlled by maxRedeliveryCount. When disabled, the message will never be delivered from the topic-endpoint more than once. Available since 2.18. |  [optional]
**rejectLowPriorityMsgEnabled** | **Boolean** | Indicates whether the checking of low priority messages against the &#x60;rejectLowPriorityMsgLimit&#x60; is enabled. |  [optional]
**rejectLowPriorityMsgLimit** | **Long** | The number of messages that are permitted before low priority messages are rejected. |  [optional]
**rejectMsgToSenderOnDiscardBehavior** | [**RejectMsgToSenderOnDiscardBehaviorEnum**](#RejectMsgToSenderOnDiscardBehaviorEnum) | Determines when to return negative acknowledgements (NACKs) to sending clients on message discards. Note that NACKs cause the message to not be delivered to any destination and Transacted Session commits to fail. The allowed values and their meaning are:  &lt;pre&gt; \&quot;always\&quot; - Always return a negative acknowledgment (NACK) to the sending client on message discard. \&quot;when-topic-endpoint-enabled\&quot; - Only return a negative acknowledgment (NACK) to the sending client on message discard when the Topic Endpoint is enabled. \&quot;never\&quot; - Never return a negative acknowledgment (NACK) to the sending client on message discard. &lt;/pre&gt;  |  [optional]
**respectMsgPriorityEnabled** | **Boolean** | Indicates whether message priorities are respected. When enabled, messages are delivered in priority order, from 9 (highest) to 0 (lowest). |  [optional]
**respectTtlEnabled** | **Boolean** | Indicates whether the time-to-live (TTL) for messages is respected. When enabled, expired messages are discarded or moved to the DMQ. |  [optional]
**topicEndpointNameFilter** | **String** | A wildcardable pattern used to determine which Topic Endpoints use settings from this Template. Two different wildcards are supported: * and &gt;. Similar to topic filters or subscription patterns, a &gt; matches anything (but only when used at the end), and a * matches zero or more characters but never a slash (/). A &gt; is only a wildcard when used at the end, after a /. A * is only allowed at the end, after a slash (/). |  [optional]
**topicEndpointTemplateName** | **String** | The name of the Topic Endpoint Template. |  [optional]


<a name="AccessTypeEnum"></a>
## Enum: AccessTypeEnum
Name | Value
---- | -----
EXCLUSIVE | &quot;exclusive&quot;
NON_EXCLUSIVE | &quot;non-exclusive&quot;


<a name="PermissionEnum"></a>
## Enum: PermissionEnum
Name | Value
---- | -----
NO_ACCESS | &quot;no-access&quot;
READ_ONLY | &quot;read-only&quot;
CONSUME | &quot;consume&quot;
MODIFY_TOPIC | &quot;modify-topic&quot;
DELETE | &quot;delete&quot;


<a name="RejectMsgToSenderOnDiscardBehaviorEnum"></a>
## Enum: RejectMsgToSenderOnDiscardBehaviorEnum
Name | Value
---- | -----
ALWAYS | &quot;always&quot;
WHEN_TOPIC_ENDPOINT_ENABLED | &quot;when-topic-endpoint-enabled&quot;
NEVER | &quot;never&quot;



