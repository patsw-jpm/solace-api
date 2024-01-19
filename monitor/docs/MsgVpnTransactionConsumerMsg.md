
# MsgVpnTransactionConsumerMsg

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**endpointName** | **String** | The name of the Queue or Topic Endpoint source. |  [optional]
**endpointType** | **String** | The type of endpoint source. The allowed values and their meaning are:  &lt;pre&gt; \&quot;queue\&quot; - The Message is from a Queue. \&quot;topic-endpoint\&quot; - The Message is from a Topic Endpoint. &lt;/pre&gt;  |  [optional]
**msgId** | **Long** | The identifier (ID) of the Message. |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**replicationGroupMsgId** | **String** | An ID that uniquely identifies this message within this replication group. Available since 2.21. |  [optional]
**xid** | **String** | The identifier (ID) of the Transaction. |  [optional]



