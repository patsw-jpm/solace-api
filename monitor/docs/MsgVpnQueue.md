
# MsgVpnQueue

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**accessType** | [**AccessTypeEnum**](#AccessTypeEnum) | The access type for delivering messages to consumer flows bound to the Queue. The allowed values and their meaning are:  &lt;pre&gt; \&quot;exclusive\&quot; - Exclusive delivery of messages to the first bound consumer flow. \&quot;non-exclusive\&quot; - Non-exclusive delivery of messages to all bound consumer flows in a round-robin fashion. &lt;/pre&gt;  |  [optional]
**alreadyBoundBindFailureCount** | **Long** | The number of Queue bind failures due to being already bound. |  [optional]
**averageRxByteRate** | **Long** | The one minute average of the message rate received by the Queue, in bytes per second (B/sec). |  [optional]
**averageRxMsgRate** | **Long** | The one minute average of the message rate received by the Queue, in messages per second (msg/sec). |  [optional]
**averageTxByteRate** | **Long** | The one minute average of the message rate transmitted by the Queue, in bytes per second (B/sec). |  [optional]
**averageTxMsgRate** | **Long** | The one minute average of the message rate transmitted by the Queue, in messages per second (msg/sec). |  [optional]
**bindRequestCount** | **Long** | The number of consumer requests to bind to the Queue. |  [optional]
**bindSuccessCount** | **Long** | The number of successful consumer requests to bind to the Queue. |  [optional]
**bindTimeForwardingMode** | **String** | The forwarding mode of the Queue at bind time. The allowed values and their meaning are:  &lt;pre&gt; \&quot;store-and-forward\&quot; - Deliver messages using the guaranteed data path. \&quot;cut-through\&quot; - Deliver messages using the direct and guaranteed data paths for lower latency. &lt;/pre&gt;  |  [optional]
**clientProfileDeniedDiscardedMsgCount** | **Long** | The number of guaranteed messages discarded by the Queue due to being denied by the Client Profile. |  [optional]
**consumerAckPropagationEnabled** | **Boolean** | Indicates whether the propagation of consumer acknowledgements (ACKs) received on the active replication Message VPN to the standby replication Message VPN is enabled. |  [optional]
**createdByManagement** | **Boolean** | Indicates whether the Queue was created by a management API (CLI or SEMP). |  [optional]
**deadMsgQueue** | **String** | The name of the Dead Message Queue (DMQ) used by the Queue. |  [optional]
**deletedMsgCount** | **Long** | The number of guaranteed messages deleted from the Queue. |  [optional]
**deliveryCountEnabled** | **Boolean** | Enable or disable the ability for client applications to query the message delivery count of messages received from the Queue. This is a controlled availability feature. Please contact Solace to find out if this feature is supported for your use case. Available since 2.19. |  [optional]
**deliveryDelay** | **Long** | The delay, in seconds, to apply to messages arriving on the Queue before the messages are eligible for delivery. Available since 2.22. |  [optional]
**destinationGroupErrorDiscardedMsgCount** | **Long** | The number of guaranteed messages discarded by the Queue due to a destination group error. |  [optional]
**disabledBindFailureCount** | **Long** | The number of Queue bind failures due to being disabled. |  [optional]
**disabledDiscardedMsgCount** | **Long** | The number of guaranteed messages discarded by the Queue due to it being disabled. |  [optional]
**durable** | **Boolean** | Indicates whether the Queue is durable and not temporary. |  [optional]
**egressEnabled** | **Boolean** | Indicates whether the transmission of messages from the Queue is enabled. |  [optional]
**eventBindCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventMsgSpoolUsageThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventRejectLowPriorityMsgLimitThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**highestAckedMsgId** | **Long** | The highest identifier (ID) of guaranteed messages in the Queue that were acknowledged. |  [optional]
**highestMsgId** | **Long** | The highest identifier (ID) of guaranteed messages in the Queue. |  [optional]
**inProgressAckMsgCount** | **Long** | The number of acknowledgement messages received by the Queue that are in the process of updating and deleting associated guaranteed messages. |  [optional]
**ingressEnabled** | **Boolean** | Indicates whether the reception of messages to the Queue is enabled. |  [optional]
**invalidSelectorBindFailureCount** | **Long** | The number of Queue bind failures due to an invalid selector. |  [optional]
**lastReplayCompleteTime** | **Integer** | The timestamp of the last completed replay for the Queue. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**lastReplayFailureReason** | **String** | The reason for the last replay failure for the Queue. |  [optional]
**lastReplayFailureTime** | **Integer** | The timestamp of the last replay failure for the Queue. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**lastReplayStartTime** | **Integer** | The timestamp of the last replay started for the Queue. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**lastReplayedMsgTxTime** | **Integer** | The timestamp of the last replayed message transmitted by the Queue. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**lastSpooledMsgId** | **Long** | The identifier (ID) of the last guaranteed message spooled in the Queue. |  [optional]
**lowPriorityMsgCongestionDiscardedMsgCount** | **Long** | The number of guaranteed messages discarded by the Queue due to low priority message congestion control. |  [optional]
**lowPriorityMsgCongestionState** | **String** | The state of the low priority message congestion in the Queue. The allowed values and their meaning are:  &lt;pre&gt; \&quot;disabled\&quot; - Messages are not being checked for priority. \&quot;not-congested\&quot; - Low priority messages are being stored and delivered. \&quot;congested\&quot; - Low priority messages are being discarded. &lt;/pre&gt;  |  [optional]
**lowestAckedMsgId** | **Long** | The lowest identifier (ID) of guaranteed messages in the Queue that were acknowledged. |  [optional]
**lowestMsgId** | **Long** | The lowest identifier (ID) of guaranteed messages in the Queue. |  [optional]
**maxBindCount** | **Long** | The maximum number of consumer flows that can bind to the Queue. |  [optional]
**maxBindCountExceededBindFailureCount** | **Long** | The number of Queue bind failures due to the maximum bind count being exceeded. |  [optional]
**maxDeliveredUnackedMsgsPerFlow** | **Long** | The maximum number of messages delivered but not acknowledged per flow for the Queue. |  [optional]
**maxMsgSize** | **Integer** | The maximum message size allowed in the Queue, in bytes (B). |  [optional]
**maxMsgSizeExceededDiscardedMsgCount** | **Long** | The number of guaranteed messages discarded by the Queue due to the maximum message size being exceeded. |  [optional]
**maxMsgSpoolUsage** | **Long** | The maximum message spool usage allowed by the Queue, in megabytes (MB). A value of 0 only allows spooling of the last message received and disables quota checking. |  [optional]
**maxMsgSpoolUsageExceededDiscardedMsgCount** | **Long** | The number of guaranteed messages discarded by the Queue due to the maximum message spool usage being exceeded. |  [optional]
**maxRedeliveryCount** | **Long** | The maximum number of times the Queue will attempt redelivery of a message prior to it being discarded or moved to the DMQ. A value of 0 means to retry forever. |  [optional]
**maxRedeliveryExceededDiscardedMsgCount** | **Long** | The number of guaranteed messages discarded by the Queue due to the maximum redelivery attempts being exceeded. |  [optional]
**maxRedeliveryExceededToDmqFailedMsgCount** | **Long** | The number of guaranteed messages discarded by the Queue due to the maximum redelivery attempts being exceeded and failing to move to the Dead Message Queue (DMQ). |  [optional]
**maxRedeliveryExceededToDmqMsgCount** | **Long** | The number of guaranteed messages moved to the Dead Message Queue (DMQ) by the Queue due to the maximum redelivery attempts being exceeded. |  [optional]
**maxTtl** | **Long** | The maximum time in seconds a message can stay in the Queue when &#x60;respectTtlEnabled&#x60; is &#x60;\&quot;true\&quot;&#x60;. A message expires when the lesser of the sender assigned time-to-live (TTL) in the message and the &#x60;maxTtl&#x60; configured for the Queue, is exceeded. A value of 0 disables expiry. |  [optional]
**maxTtlExceededDiscardedMsgCount** | **Long** | The number of guaranteed messages discarded by the Queue due to the maximum time-to-live (TTL) in hops being exceeded. The TTL hop count is incremented when the message crosses a bridge. |  [optional]
**maxTtlExpiredDiscardedMsgCount** | **Long** | The number of guaranteed messages discarded by the Queue due to the maximum time-to-live (TTL) timestamp expiring. |  [optional]
**maxTtlExpiredToDmqFailedMsgCount** | **Long** | The number of guaranteed messages discarded by the Queue due to the maximum time-to-live (TTL) timestamp expiring and failing to move to the Dead Message Queue (DMQ). |  [optional]
**maxTtlExpiredToDmqMsgCount** | **Long** | The number of guaranteed messages moved to the Dead Message Queue (DMQ) by the Queue due to the maximum time-to-live (TTL) timestamp expiring. |  [optional]
**msgSpoolPeakUsage** | **Long** | The message spool peak usage by the Queue, in bytes (B). |  [optional]
**msgSpoolUsage** | **Long** | The message spool usage by the Queue, in bytes (B). |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**networkTopic** | **String** | The name of the network topic for the Queue. |  [optional]
**noLocalDeliveryDiscardedMsgCount** | **Long** | The number of guaranteed messages discarded by the Queue due to no local delivery being requested. |  [optional]
**otherBindFailureCount** | **Long** | The number of Queue bind failures due to other reasons. |  [optional]
**owner** | **String** | The Client Username that owns the Queue and has permission equivalent to &#x60;\&quot;delete\&quot;&#x60;. |  [optional]
**permission** | [**PermissionEnum**](#PermissionEnum) | The permission level for all consumers of the Queue, excluding the owner. The allowed values and their meaning are:  &lt;pre&gt; \&quot;no-access\&quot; - Disallows all access. \&quot;read-only\&quot; - Read-only access to the messages. \&quot;consume\&quot; - Consume (read and remove) messages. \&quot;modify-topic\&quot; - Consume messages or modify the topic/selector. \&quot;delete\&quot; - Consume messages, modify the topic/selector or delete the Client created endpoint altogether. &lt;/pre&gt;  |  [optional]
**queueName** | **String** | The name of the Queue. |  [optional]
**redeliveredMsgCount** | **Long** | The number of guaranteed messages transmitted by the Queue for redelivery. |  [optional]
**redeliveryEnabled** | **Boolean** | Enable or disable message redelivery. When enabled, the number of redelivery attempts is controlled by maxRedeliveryCount. When disabled, the message will never be delivered from the queue more than once. Available since 2.18. |  [optional]
**rejectLowPriorityMsgEnabled** | **Boolean** | Indicates whether the checking of low priority messages against the &#x60;rejectLowPriorityMsgLimit&#x60; is enabled. |  [optional]
**rejectLowPriorityMsgLimit** | **Long** | The number of messages of any priority in the Queue above which low priority messages are not admitted but higher priority messages are allowed. |  [optional]
**rejectMsgToSenderOnDiscardBehavior** | [**RejectMsgToSenderOnDiscardBehaviorEnum**](#RejectMsgToSenderOnDiscardBehaviorEnum) | Determines when to return negative acknowledgements (NACKs) to sending clients on message discards. Note that NACKs cause the message to not be delivered to any destination and Transacted Session commits to fail. The allowed values and their meaning are:  &lt;pre&gt; \&quot;always\&quot; - Always return a negative acknowledgment (NACK) to the sending client on message discard. \&quot;when-queue-enabled\&quot; - Only return a negative acknowledgment (NACK) to the sending client on message discard when the Queue is enabled. \&quot;never\&quot; - Never return a negative acknowledgment (NACK) to the sending client on message discard. &lt;/pre&gt;  |  [optional]
**replayFailureCount** | **Long** | The number of replays that failed for the Queue. |  [optional]
**replayStartCount** | **Long** | The number of replays started for the Queue. |  [optional]
**replayState** | **String** | The state of replay for the Queue. The allowed values and their meaning are:  &lt;pre&gt; \&quot;initializing\&quot; - All messages are being deleted from the endpoint before replay starts. \&quot;active\&quot; - Subscription matching logged messages are being replayed to the endpoint. \&quot;pending-complete\&quot; - Replay is complete, but final accounting is in progress. \&quot;complete\&quot; - Replay and all related activities are complete. \&quot;failed\&quot; - Replay has failed and is waiting for an unbind response. &lt;/pre&gt;  |  [optional]
**replaySuccessCount** | **Long** | The number of replays that succeeded for the Queue. |  [optional]
**replayedAckedMsgCount** | **Long** | The number of replayed messages transmitted by the Queue and acked by all consumers. |  [optional]
**replayedTxMsgCount** | **Long** | The number of replayed messages transmitted by the Queue. |  [optional]
**replicationActiveAckPropTxMsgCount** | **Long** | The number of acknowledgement messages propagated by the Queue to the replication standby remote Message VPN. |  [optional]
**replicationStandbyAckPropRxMsgCount** | **Long** | The number of propagated acknowledgement messages received by the Queue from the replication active remote Message VPN. |  [optional]
**replicationStandbyAckedByAckPropMsgCount** | **Long** | The number of messages acknowledged in the Queue by acknowledgement propagation from the replication active remote Message VPN. |  [optional]
**replicationStandbyRxMsgCount** | **Long** | The number of messages received by the Queue from the replication active remote Message VPN. |  [optional]
**respectMsgPriorityEnabled** | **Boolean** | Indicates whether message priorities are respected. When enabled, messages contained in the Queue are delivered in priority order, from 9 (highest) to 0 (lowest). |  [optional]
**respectTtlEnabled** | **Boolean** | Indicates whether the the time-to-live (TTL) for messages in the Queue is respected. When enabled, expired messages are discarded or moved to the DMQ. |  [optional]
**rxByteRate** | **Long** | The current message rate received by the Queue, in bytes per second (B/sec). |  [optional]
**rxMsgRate** | **Long** | The current message rate received by the Queue, in messages per second (msg/sec). |  [optional]
**spooledByteCount** | **Long** | The amount of guaranteed messages that were spooled in the Queue, in bytes (B). |  [optional]
**spooledMsgCount** | **Long** | The number of guaranteed messages that were spooled in the Queue. |  [optional]
**transportRetransmitMsgCount** | **Long** | The number of guaranteed messages that were retransmitted by the Queue at the transport layer as part of a single delivery attempt. Available since 2.18. |  [optional]
**txByteRate** | **Long** | The current message rate transmitted by the Queue, in bytes per second (B/sec). |  [optional]
**txMsgRate** | **Long** | The current message rate transmitted by the Queue, in messages per second (msg/sec). |  [optional]
**txSelector** | **Boolean** | Indicates whether the Queue has consumers with selectors to filter transmitted messages. |  [optional]
**txUnackedMsgCount** | **Long** | The number of guaranteed messages in the Queue that have been transmitted but not acknowledged by all consumers. |  [optional]
**virtualRouter** | **String** | The virtual router of the Queue. The allowed values and their meaning are:  &lt;pre&gt; \&quot;primary\&quot; - The endpoint belongs to the primary virtual router. \&quot;backup\&quot; - The endpoint belongs to the backup virtual router. &lt;/pre&gt;  |  [optional]


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
WHEN_QUEUE_ENABLED | &quot;when-queue-enabled&quot;
NEVER | &quot;never&quot;



