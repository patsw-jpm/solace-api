
# MsgVpnDistributedCacheClusterInstance

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**autoStartEnabled** | **Boolean** | Indicates whether auto-start for the Cache Instance is enabled, and the Cache Instance will automatically attempt to transition from the Stopped operational state to Up whenever it restarts or reconnects to the message broker. |  [optional]
**averageDataRxBytePeakRate** | **Long** | The peak of the one minute average of the data message rate received by the Cache Instance, in bytes per second (B/sec). Available since 2.13. |  [optional]
**averageDataRxByteRate** | **Long** | The one minute average of the data message rate received by the Cache Instance, in bytes per second (B/sec). Available since 2.13. |  [optional]
**averageDataRxMsgPeakRate** | **Long** | The peak of the one minute average of the data message rate received by the Cache Instance, in messages per second (msg/sec). Available since 2.13. |  [optional]
**averageDataRxMsgRate** | **Long** | The one minute average of the data message rate received by the Cache Instance, in messages per second (msg/sec). Available since 2.13. |  [optional]
**averageDataTxMsgPeakRate** | **Long** | The peak of the one minute average of the data message rate transmitted by the Cache Instance, in messages per second (msg/sec). Available since 2.13. |  [optional]
**averageDataTxMsgRate** | **Long** | The one minute average of the data message rate transmitted by the Cache Instance, in messages per second (msg/sec). Available since 2.13. |  [optional]
**averageRequestRxPeakRate** | **Long** | The peak of the one minute average of the request rate received by the Cache Instance, in requests per second (req/sec). Available since 2.13. |  [optional]
**averageRequestRxRate** | **Long** | The one minute average of the request rate received by the Cache Instance, in requests per second (req/sec). Available since 2.13. |  [optional]
**cacheName** | **String** | The name of the Distributed Cache. |  [optional]
**clusterName** | **String** | The name of the Cache Cluster. |  [optional]
**counter** | [**MsgVpnDistributedCacheClusterInstanceCounter**](MsgVpnDistributedCacheClusterInstanceCounter.md) |  |  [optional]
**dataRxBytePeakRate** | **Long** | The data message peak rate received by the Cache Instance, in bytes per second (B/sec). Available since 2.13. |  [optional]
**dataRxByteRate** | **Long** | The data message rate received by the Cache Instance, in bytes per second (B/sec). Available since 2.13. |  [optional]
**dataRxMsgPeakRate** | **Long** | The data message peak rate received by the Cache Instance, in messages per second (msg/sec). Available since 2.13. |  [optional]
**dataRxMsgRate** | **Long** | The data message rate received by the Cache Instance, in messages per second (msg/sec). Available since 2.13. |  [optional]
**dataTxMsgPeakRate** | **Long** | The data message peak rate transmitted by the Cache Instance, in messages per second (msg/sec). Available since 2.13. |  [optional]
**dataTxMsgRate** | **Long** | The data message rate transmitted by the Cache Instance, in messages per second (msg/sec). Available since 2.13. |  [optional]
**enabled** | **Boolean** | Indicates whether the Cache Instance is enabled. |  [optional]
**instanceName** | **String** | The name of the Cache Instance. |  [optional]
**lastRegisteredTime** | **Integer** | The timestamp of when the Cache Instance last registered with the message broker. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**lastRxHeartbeatTime** | **Integer** | The timestamp of the last heartbeat message received from the Cache Instance. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**lastRxSetLostMsgTime** | **Integer** | The timestamp of the last request for setting the lost message indication received from the Cache Instance. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**lastStoppedReason** | **String** | The reason why the Cache Instance was last stopped. |  [optional]
**lastStoppedTime** | **Integer** | The timestamp of when the Cache Instance was last stopped. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**lastTxClearLostMsgTime** | **Integer** | The timestamp of the last request for clearing the lost message indication transmitted to the Cache Instance. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**memoryUsage** | **Integer** | The memory usage of the Cache Instance, in megabytes (MB). |  [optional]
**msgCount** | **Long** | The number of messages cached for the Cache Instance. Available since 2.13. |  [optional]
**msgPeakCount** | **Long** | The number of messages cached peak for the Cache Instance. Available since 2.13. |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**msgsLost** | **Boolean** | Indicates whether one or more messages were lost by the Cache Instance. |  [optional]
**rate** | [**MsgVpnDistributedCacheClusterInstanceRate**](MsgVpnDistributedCacheClusterInstanceRate.md) |  |  [optional]
**requestQueueDepthCount** | **Long** | The received request message queue depth for the Cache Instance. Available since 2.13. |  [optional]
**requestQueueDepthPeakCount** | **Long** | The received request message queue depth peak for the Cache Instance. Available since 2.13. |  [optional]
**requestRxPeakRate** | **Long** | The request peak rate received by the Cache Instance, in requests per second (req/sec). Available since 2.13. |  [optional]
**requestRxRate** | **Long** | The request rate received by the Cache Instance, in requests per second (req/sec). Available since 2.13. |  [optional]
**state** | **String** | The operational state of the Cache Instance. The allowed values and their meaning are:  &lt;pre&gt; \&quot;invalid\&quot; - The Cache Instance state is invalid. \&quot;down\&quot; - The Cache Instance is operationally down. \&quot;stopped\&quot; - The Cache Instance has stopped processing cache requests. \&quot;stopped-lost-msg\&quot; - The Cache Instance has stopped due to a lost message. \&quot;register\&quot; - The Cache Instance is registering with the broker. \&quot;config-sync\&quot; - The Cache Instance is synchronizing its configuration with the broker. \&quot;cluster-sync\&quot; - The Cache Instance is synchronizing its messages with the Cache Cluster. \&quot;up\&quot; - The Cache Instance is operationally up. \&quot;backup\&quot; - The Cache Instance is backing up its messages to disk. \&quot;restore\&quot; - The Cache Instance is restoring its messages from disk. \&quot;not-available\&quot; - The Cache Instance state is not available. &lt;/pre&gt;  |  [optional]
**stopOnLostMsgEnabled** | **Boolean** | Indicates whether stop-on-lost-message is enabled, and the Cache Instance will transition to the Stopped operational state upon losing a message. When Stopped, it cannot accept or respond to cache requests, but continues to cache messages. |  [optional]
**topicCount** | **Long** | The number of topics cached for the Cache Instance. Available since 2.13. |  [optional]
**topicPeakCount** | **Long** | The number of topics cached peak for the Cache Instance. Available since 2.13. |  [optional]



