
# MsgVpnClientRxFlow

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**clientName** | **String** | The name of the Client. |  [optional]
**connectTime** | **Integer** | The timestamp of when the Flow from the Client connected. |  [optional]
**destinationGroupErrorDiscardedMsgCount** | **Long** | The number of guaranteed messages from the Flow discarded due to a destination group error. |  [optional]
**duplicateDiscardedMsgCount** | **Long** | The number of guaranteed messages from the Flow discarded due to being a duplicate. |  [optional]
**endpointDisabledDiscardedMsgCount** | **Long** | The number of guaranteed messages from the Flow discarded due to an eligible endpoint destination being disabled. |  [optional]
**endpointUsageExceededDiscardedMsgCount** | **Long** | The number of guaranteed messages from the Flow discarded due to an eligible endpoint destination having its maximum message spool usage exceeded. |  [optional]
**erroredDiscardedMsgCount** | **Long** | The number of guaranteed messages from the Flow discarded due to errors being detected. |  [optional]
**flowId** | **Long** | The identifier (ID) of the flow. |  [optional]
**flowName** | **String** | The name of the Flow. |  [optional]
**guaranteedMsgCount** | **Long** | The number of guaranteed messages from the Flow. |  [optional]
**lastRxMsgId** | **Long** | The identifier (ID) of the last message received on the Flow. |  [optional]
**localMsgCountExceededDiscardedMsgCount** | **Long** | The number of guaranteed messages from the Flow discarded due to the maximum number of messages allowed on the broker being exceeded. |  [optional]
**lowPriorityMsgCongestionDiscardedMsgCount** | **Long** | The number of guaranteed messages from the Flow discarded due to congestion of low priority messages. |  [optional]
**maxMsgSizeExceededDiscardedMsgCount** | **Long** | The number of guaranteed messages from the Flow discarded due to the maximum allowed message size being exceeded. |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**noEligibleDestinationsDiscardedMsgCount** | **Long** | The number of guaranteed messages from the Flow discarded due to there being no eligible endpoint destination. |  [optional]
**noLocalDeliveryDiscardedMsgCount** | **Long** | The number of guaranteed messages from the Flow discarded due to no local delivery being requested. |  [optional]
**notCompatibleWithForwardingModeDiscardedMsgCount** | **Long** | The number of guaranteed messages from the Flow discarded due to being incompatible with the forwarding mode of an eligible endpoint destination. |  [optional]
**outOfOrderDiscardedMsgCount** | **Long** | The number of guaranteed messages from the Flow discarded due to being received out of order. |  [optional]
**publishAclDeniedDiscardedMsgCount** | **Long** | The number of guaranteed messages from the Flow discarded due to being denied by the access control list (ACL) profile for the published topic. |  [optional]
**publisherId** | **Long** | The identifier (ID) of the publisher for the Flow. |  [optional]
**queueNotFoundDiscardedMsgCount** | **Long** | The number of guaranteed messages from the Flow discarded due to the destination queue not being found. |  [optional]
**replicationStandbyDiscardedMsgCount** | **Long** | The number of guaranteed messages from the Flow discarded due to the Message VPN being in the replication standby state. |  [optional]
**sessionName** | **String** | The name of the transacted session on the Flow. |  [optional]
**smfTtlExceededDiscardedMsgCount** | **Long** | The number of guaranteed messages from the Flow discarded due to the message time-to-live (TTL) count being exceeded. The message TTL count is the maximum number of times the message can cross a bridge between Message VPNs. |  [optional]
**spoolFileLimitExceededDiscardedMsgCount** | **Long** | The number of guaranteed messages from the Flow discarded due to all available message spool file resources being used. |  [optional]
**spoolNotReadyDiscardedMsgCount** | **Long** | The number of guaranteed messages from the Flow discarded due to the message spool being not ready. |  [optional]
**spoolToAdbFailDiscardedMsgCount** | **Long** | The number of guaranteed messages from the Flow discarded due to a failure while spooling to the Assured Delivery Blade (ADB). |  [optional]
**spoolToDiskFailDiscardedMsgCount** | **Long** | The number of guaranteed messages from the Flow discarded due to a failure while spooling to the disk. |  [optional]
**spoolUsageExceededDiscardedMsgCount** | **Long** | The number of guaranteed messages from the Flow discarded due to the maximum message spool usage being exceeded. |  [optional]
**syncReplicationIneligibleDiscardedMsgCount** | **Long** | The number of guaranteed messages from the Flow discarded due to synchronous replication being ineligible. |  [optional]
**userProfileDeniedGuaranteedDiscardedMsgCount** | **Long** | The number of guaranteed messages from the Flow discarded due to being denied by the client profile. |  [optional]
**windowSize** | **Integer** | The size of the window used for guaranteed messages sent on the Flow, in messages. |  [optional]



