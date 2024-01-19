
# MsgVpnReplayLogMsg

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**attachmentSize** | **Long** | The size of the message attachment, in bytes (B). |  [optional]
**contentSize** | **Long** | The size of the message content, in bytes (B). |  [optional]
**dmqEligible** | **Boolean** | Indicates whether the message is eligible for the Dead Message Queue (DMQ). |  [optional]
**msgId** | **Long** | The identifier (ID) of the message. |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**priority** | **Integer** | The priority level of the message. |  [optional]
**publisherId** | **Long** | The identifier (ID) of the message publisher. |  [optional]
**replayLogName** | **String** | The name of the Replay Log. |  [optional]
**replicationGroupMsgId** | **String** | An ID that uniquely identifies this Message within this replication group. Available since 2.21. |  [optional]
**sequenceNumber** | **Long** | The sequence number assigned to the message. Applicable only to messages received on sequenced topics. |  [optional]
**spooledTime** | **Integer** | The timestamp of when the message was spooled in the Replay Log. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]



