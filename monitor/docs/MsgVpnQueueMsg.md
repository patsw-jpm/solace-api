
# MsgVpnQueueMsg

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**attachmentSize** | **Long** | The size of the Message attachment, in bytes (B). |  [optional]
**contentSize** | **Long** | The size of the Message content, in bytes (B). |  [optional]
**deliveryEligibleTime** | **Integer** | The timestamp of when the Message is eligible for delivery. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). Available since 2.22. |  [optional]
**dmqEligible** | **Boolean** | Indicates whether the Message is eligible for the Dead Message Queue (DMQ). |  [optional]
**expiryTime** | **Integer** | The timestamp of when the Message expires. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**msgId** | **Long** | The identifier (ID) of the Message. |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**priority** | **Integer** | The priority level of the Message, from 9 (highest) to 0 (lowest). |  [optional]
**publisherId** | **Long** | The identifier (ID) of the Message publisher. |  [optional]
**queueName** | **String** | The name of the Queue. |  [optional]
**redeliveryCount** | **Integer** | The number of times the Message has been redelivered. |  [optional]
**replicatedMateMsgId** | **Long** | The Message identifier (ID) on the replication mate. Applicable only to replicated messages. |  [optional]
**replicationGroupMsgId** | **String** | An ID that uniquely identifies this Message within this replication group. Available since 2.21. |  [optional]
**replicationState** | **String** | The replication state of the Message. The allowed values and their meaning are:  &lt;pre&gt; \&quot;replicated\&quot; - The Message is replicated to the remote Message VPN. \&quot;not-replicated\&quot; - The Message is not being replicated to the remote Message VPN. \&quot;pending-replication\&quot; - The Message is queued for replication to the remote Message VPN. &lt;/pre&gt;  |  [optional]
**spooledTime** | **Integer** | The timestamp of when the Message was spooled in the Queue. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**undelivered** | **Boolean** | Indicates whether delivery of the Message has never been attempted. |  [optional]



