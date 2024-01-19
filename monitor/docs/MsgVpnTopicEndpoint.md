
# MsgVpnTopicEndpoint

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**accessType** | [**AccessTypeEnum**](#AccessTypeEnum) | The access type for delivering messages to consumer flows bound to the Topic Endpoint. The allowed values and their meaning are:  &lt;pre&gt; \&quot;exclusive\&quot; - Exclusive delivery of messages to the first bound consumer flow. \&quot;non-exclusive\&quot; - Non-exclusive delivery of messages to all bound consumer flows in a round-robin fashion. &lt;/pre&gt;  |  [optional]
**alreadyBoundBindFailureCount** | **Long** | The number of Topic Endpoint bind failures due to being already bound. |  [optional]
**averageRxByteRate** | **Long** | The one minute average of the message rate received by the Topic Endpoint, in bytes per second (B/sec). |  [optional]
**averageRxMsgRate** | **Long** | The one minute average of the message rate received by the Topic Endpoint, in messages per second (msg/sec). |  [optional]
**averageTxByteRate** | **Long** | The one minute average of the message rate transmitted by the Topic Endpoint, in bytes per second (B/sec). |  [optional]
**averageTxMsgRate** | **Long** | The one minute average of the message rate transmitted by the Topic Endpoint, in messages per second (msg/sec). |  [optional]
**bindRequestCount** | **Long** | The number of consumer requests to bind to the Topic Endpoint. |  [optional]
**bindSuccessCount** | **Long** | The number of successful consumer requests to bind to the Topic Endpoint. |  [optional]
**bindTimeForwardingMode** | **String** | The forwarding mode of the Topic Endpoint at bind time. The allowed values and their meaning are:  &lt;pre&gt; \&quot;store-and-forward\&quot; - Deliver messages using the guaranteed data path. \&quot;cut-through\&quot; - Deliver messages using the direct and guaranteed data paths for lower latency. &lt;/pre&gt;  |  [optional]
**clientProfileDeniedDiscardedMsgCount** | **Long** | The number of guaranteed messages discarded by the Topic Endpoint due to being denied by the Client Profile. |  [optional]
**consumerAckPropagationEnabled** | **Boolean** | Indicates whether the propagation of consumer acknowledgements (ACKs) received on the active replication Message VPN to the standby replication Message VPN is enabled. |  [optional]
**createdByManagement** | **Boolean** | Indicates whether the Topic Endpoint was created by a management API (CLI or SEMP). |  [optional]
**deadMsgQueue** | **String** | The name of the Dead Message Queue (DMQ) used by the Topic Endpoint. |  [optional]
**deletedMsgCount** | **Long** | The number of guaranteed messages deleted from the Topic Endpoint. |  [optional]
**deliveryCountEnabled** | **Boolean** | Enable or disable the ability for client applications to query the message delivery count of messages received from the Topic Endpoint. This is a controlled availability feature. Please contact Solace to find out if this feature is supported for your use case. Available since 2.19. |  [optional]
**deliveryDelay** | **Long** | The delay, in seconds, to apply to messages arriving on the Topic Endpoint before the messages are eligible for delivery. Available since 2.22. |  [optional]
**destinationGroupErrorDiscardedMsgCount** | **Long** | The number of guaranteed messages discarded by the Topic Endpoint due to a destination group error. |  [optional]
**destinationTopic** | **String** | The destination topic of the Topic Endpoint. |  [optional]
**disabledBindFailureCount** | **Long** | The number of Topic Endpoint bind failures due to being disabled. |  [optional]
**disabledDiscardedMsgCount** | **Long** | The number of guaranteed messages discarded by the Topic Endpoint due to it being disabled. |  [optional]
**durable** | **Boolean** | Indicates whether the Topic Endpoint is durable and not temporary. |  [optional]
**egressEnabled** | **Boolean** | Indicates whether the transmission of messages from the Topic Endpoint is enabled. |  [optional]
**eventBindCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventRejectLowPriorityMsgLimitThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventSpoolUsageThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**highestAckedMsgId** | **Long** | The highest identifier (ID) of guaranteed messages in the Topic Endpoint that were acknowledged. |  [optional]
**highestMsgId** | **Long** | The highest identifier (ID) of guaranteed messages in the Topic Endpoint. |  [optional]
**inProgressAckMsgCount** | **Long** | The number of acknowledgement messages received by the Topic Endpoint that are in the process of updating and deleting associated guaranteed messages. |  [optional]
**ingressEnabled** | **Boolean** | Indicates whether the reception of messages to the Topic Endpoint is enabled. |  [optional]
**invalidSelectorBindFailureCount** | **Long** | The number of Topic Endpoint bind failures due to an invalid selector. |  [optional]
**lastReplayCompleteTime** | **Integer** | The timestamp of the last completed replay for the Topic Endpoint. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**lastReplayFailureReason** | **String** | The reason for the last replay failure for the Topic Endpoint. |  [optional]
**lastReplayFailureTime** | **Integer** | The timestamp of the last replay failure for the Topic Endpoint. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**lastReplayStartTime** | **Integer** | The timestamp of the last replay started for the Topic Endpoint. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**lastReplayedMsgTxTime** | **Integer** | The timestamp of the last replayed message transmitted by the Topic Endpoint. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**lastSelectorExaminedMsgId** | **Long** | The identifier (ID) of the last message examined by the Topic Endpoint selector. |  [optional]
**lastSpooledMsgId** | **Long** | The identifier (ID) of the last guaranteed message spooled in the Topic Endpoint. |  [optional]
**lowPriorityMsgCongestionDiscardedMsgCount** | **Long** | The number of guaranteed messages discarded by the Topic Endpoint due to low priority message congestion control. |  [optional]
**lowPriorityMsgCongestionState** | **String** | The state of the low priority message congestion in the Topic Endpoint. The allowed values and their meaning are:  &lt;pre&gt; \&quot;disabled\&quot; - Messages are not being checked for priority. \&quot;not-congested\&quot; - Low priority messages are being stored and delivered. \&quot;congested\&quot; - Low priority messages are being discarded. &lt;/pre&gt;  |  [optional]
**lowestAckedMsgId** | **Long** | The lowest identifier (ID) of guaranteed messages in the Topic Endpoint that were acknowledged. |  [optional]
**lowestMsgId** | **Long** | The lowest identifier (ID) of guaranteed messages in the Topic Endpoint. |  [optional]
**maxBindCount** | **Long** | The maximum number of consumer flows that can bind to the Topic Endpoint. |  [optional]
**maxBindCountExceededBindFailureCount** | **Long** | The number of Topic Endpoint bind failures due to the maximum bind count being exceeded. |  [optional]
**maxDeliveredUnackedMsgsPerFlow** | **Long** | The maximum number of messages delivered but not acknowledged per flow for the Topic Endpoint. |  [optional]
**maxEffectiveBindCount** | **Integer** | The effective maximum number of consumer flows that can bind to the Topic Endpoint. |  [optional]
**maxMsgSize** | **Integer** | The maximum message size allowed in the Topic Endpoint, in bytes (B). |  [optional]
**maxMsgSizeExceededDiscardedMsgCount** | **Long** | The number of guaranteed messages discarded by the Topic Endpoint due to the maximum message size being exceeded. |  [optional]
**maxMsgSpoolUsageExceededDiscardedMsgCount** | **Long** | The number of guaranteed messages discarded by the Topic Endpoint due to the maximum message spool usage being exceeded. |  [optional]
**maxRedeliveryCount** | **Long** | The maximum number of times the Topic Endpoint will attempt redelivery of a message prior to it being discarded or moved to the DMQ. A value of 0 means to retry forever. |  [optional]
**maxRedeliveryExceededDiscardedMsgCount** | **Long** | The number of guaranteed messages discarded by the Topic Endpoint due to the maximum redelivery attempts being exceeded. |  [optional]
**maxRedeliveryExceededToDmqFailedMsgCount** | **Long** | The number of guaranteed messages discarded by the Topic Endpoint due to the maximum redelivery attempts being exceeded and failing to move to the Dead Message Queue (DMQ). |  [optional]
**maxRedeliveryExceededToDmqMsgCount** | **Long** | The number of guaranteed messages moved to the Dead Message Queue (DMQ) by the Topic Endpoint due to the maximum redelivery attempts being exceeded. |  [optional]
**maxSpoolUsage** | **Long** | The maximum message spool usage allowed by the Topic Endpoint, in megabytes (MB). A value of 0 only allows spooling of the last message received and disables quota checking. |  [optional]
**maxTtl** | **Long** | The maximum time in seconds a message can stay in the Topic Endpoint when &#x60;respectTtlEnabled&#x60; is &#x60;\&quot;true\&quot;&#x60;. A message expires when the lesser of the sender assigned time-to-live (TTL) in the message and the &#x60;maxTtl&#x60; configured for the Topic Endpoint, is exceeded. A value of 0 disables expiry. |  [optional]
**maxTtlExceededDiscardedMsgCount** | **Long** | The number of guaranteed messages discarded by the Topic Endpoint due to the maximum time-to-live (TTL) in hops being exceeded. The TTL hop count is incremented when the message crosses a bridge. |  [optional]
**maxTtlExpiredDiscardedMsgCount** | **Long** | The number of guaranteed messages discarded by the Topic Endpoint due to the maximum time-to-live (TTL) timestamp expiring. |  [optional]
**maxTtlExpiredToDmqFailedMsgCount** | **Long** | The number of guaranteed messages discarded by the Topic Endpoint due to the maximum time-to-live (TTL) timestamp expiring and failing to move to the Dead Message Queue (DMQ). |  [optional]
**maxTtlExpiredToDmqMsgCount** | **Long** | The number of guaranteed messages moved to the Dead Message Queue (DMQ) by the Topic Endpoint due to the maximum time-to-live (TTL) timestamp expiring. |  [optional]
**msgSpoolPeakUsage** | **Long** | The message spool peak usage by the Topic Endpoint, in bytes (B). |  [optional]
**msgSpoolUsage** | **Long** | The message spool usage by the Topic Endpoint, in bytes (B). |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**networkTopic** | **String** | The name of the network topic for the Topic Endpoint. |  [optional]
**noLocalDeliveryDiscardedMsgCount** | **Long** | The number of guaranteed messages discarded by the Topic Endpoint due to no local delivery being requested. |  [optional]
**otherBindFailureCount** | **Long** | The number of Topic Endpoint bind failures due to other reasons. |  [optional]
**owner** | **String** | The Client Username that owns the Topic Endpoint and has permission equivalent to &#x60;\&quot;delete\&quot;&#x60;. |  [optional]
**permission** | [**PermissionEnum**](#PermissionEnum) | The permission level for all consumers of the Topic Endpoint, excluding the owner. The allowed values and their meaning are:  &lt;pre&gt; \&quot;no-access\&quot; - Disallows all access. \&quot;read-only\&quot; - Read-only access to the messages. \&quot;consume\&quot; - Consume (read and remove) messages. \&quot;modify-topic\&quot; - Consume messages or modify the topic/selector. \&quot;delete\&quot; - Consume messages, modify the topic/selector or delete the Client created endpoint altogether. &lt;/pre&gt;  |  [optional]
**redeliveredMsgCount** | **Long** | The number of guaranteed messages transmitted by the Topic Endpoint for redelivery. |  [optional]
**redeliveryEnabled** | **Boolean** | Enable or disable message redelivery. When enabled, the number of redelivery attempts is controlled by maxRedeliveryCount. When disabled, the message will never be delivered from the topic-endpoint more than once. Available since 2.18. |  [optional]
**rejectLowPriorityMsgEnabled** | **Boolean** | Indicates whether the checking of low priority messages against the &#x60;rejectLowPriorityMsgLimit&#x60; is enabled. |  [optional]
**rejectLowPriorityMsgLimit** | **Long** | The number of messages of any priority in the Topic Endpoint above which low priority messages are not admitted but higher priority messages are allowed. |  [optional]
**rejectMsgToSenderOnDiscardBehavior** | [**RejectMsgToSenderOnDiscardBehaviorEnum**](#RejectMsgToSenderOnDiscardBehaviorEnum) | Determines when to return negative acknowledgements (NACKs) to sending clients on message discards. Note that NACKs cause the message to not be delivered to any destination and Transacted Session commits to fail. The allowed values and their meaning are:  &lt;pre&gt; \&quot;always\&quot; - Always return a negative acknowledgment (NACK) to the sending client on message discard. \&quot;when-topic-endpoint-enabled\&quot; - Only return a negative acknowledgment (NACK) to the sending client on message discard when the Topic Endpoint is enabled. \&quot;never\&quot; - Never return a negative acknowledgment (NACK) to the sending client on message discard. &lt;/pre&gt;  |  [optional]
**replayFailureCount** | **Long** | The number of replays that failed for the Topic Endpoint. |  [optional]
**replayStartCount** | **Long** | The number of replays started for the Topic Endpoint. |  [optional]
**replayState** | **String** | The state of replay for the Topic Endpoint. The allowed values and their meaning are:  &lt;pre&gt; \&quot;initializing\&quot; - All messages are being deleted from the endpoint before replay starts. \&quot;active\&quot; - Subscription matching logged messages are being replayed to the endpoint. \&quot;pending-complete\&quot; - Replay is complete, but final accounting is in progress. \&quot;complete\&quot; - Replay and all related activities are complete. \&quot;failed\&quot; - Replay has failed and is waiting for an unbind response. &lt;/pre&gt;  |  [optional]
**replaySuccessCount** | **Long** | The number of replays that succeeded for the Topic Endpoint. |  [optional]
**replayedAckedMsgCount** | **Long** | The number of replayed messages transmitted by the Topic Endpoint and acked by all consumers. |  [optional]
**replayedTxMsgCount** | **Long** | The number of replayed messages transmitted by the Topic Endpoint. |  [optional]
**replicationActiveAckPropTxMsgCount** | **Long** | The number of acknowledgement messages propagated by the Topic Endpoint to the replication standby remote Message VPN. |  [optional]
**replicationStandbyAckPropRxMsgCount** | **Long** | The number of propagated acknowledgement messages received by the Topic Endpoint from the replication active remote Message VPN. |  [optional]
**replicationStandbyAckedByAckPropMsgCount** | **Long** | The number of messages acknowledged in the Topic Endpoint by acknowledgement propagation from the replication active remote Message VPN. |  [optional]
**replicationStandbyRxMsgCount** | **Long** | The number of messages received by the Topic Endpoint from the replication active remote Message VPN. |  [optional]
**respectMsgPriorityEnabled** | **Boolean** | Indicates whether message priorities are respected. When enabled, messages contained in the Topic Endpoint are delivered in priority order, from 9 (highest) to 0 (lowest). |  [optional]
**respectTtlEnabled** | **Boolean** | Indicates whether the time-to-live (TTL) for messages in the Topic Endpoint is respected. When enabled, expired messages are discarded or moved to the DMQ. |  [optional]
**rxByteRate** | **Integer** | The current message rate received by the Topic Endpoint, in bytes per second (B/sec). |  [optional]
**rxMsgRate** | **Long** | The current message rate received by the Topic Endpoint, in messages per second (msg/sec). |  [optional]
**rxSelector** | **Boolean** | Indicates whether the Topic Endpoint has a selector to filter received messages. |  [optional]
**selector** | **String** | The value of the receive selector for the Topic Endpoint. |  [optional]
**selectorExaminedMsgCount** | **Long** | The number of guaranteed messages examined by the Topic Endpoint selector. |  [optional]
**selectorMatchedMsgCount** | **Long** | The number of guaranteed messages for which the Topic Endpoint selector matched. |  [optional]
**selectorNotMatchedMsgCount** | **Long** | The number of guaranteed messages for which the Topic Endpoint selector did not match. |  [optional]
**spooledByteCount** | **Long** | The amount of guaranteed messages that were spooled in the Topic Endpoint, in bytes (B). |  [optional]
**spooledMsgCount** | **Long** | The number of guaranteed messages that were spooled in the Topic Endpoint. |  [optional]
**topicEndpointName** | **String** | The name of the Topic Endpoint. |  [optional]
**transportRetransmitMsgCount** | **Long** | The number of guaranteed messages that were retransmitted by the Topic Endpoint at the transport layer as part of a single delivery attempt. Available since 2.18. |  [optional]
**txByteRate** | **Long** | The current message rate transmitted by the Topic Endpoint, in bytes per second (B/sec). |  [optional]
**txMsgRate** | **Long** | The current message rate transmitted by the Topic Endpoint, in messages per second (msg/sec). |  [optional]
**txUnackedMsgCount** | **Long** | The number of guaranteed messages in the Topic Endpoint that have been transmitted but not acknowledged by all consumers. |  [optional]
**virtualRouter** | **String** | The virtual router used by the Topic Endpoint. The allowed values and their meaning are:  &lt;pre&gt; \&quot;primary\&quot; - The endpoint belongs to the primary virtual router. \&quot;backup\&quot; - The endpoint belongs to the backup virtual router. &lt;/pre&gt;  |  [optional]


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



