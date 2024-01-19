
# MsgVpnDistributedCacheCluster

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**cacheName** | **String** | The name of the Distributed Cache. |  [optional]
**clusterName** | **String** | The name of the Cache Cluster. |  [optional]
**deliverToOneOverrideEnabled** | **Boolean** | Indicates whether deliver-to-one override is enabled for the Cache Cluster. |  [optional]
**enabled** | **Boolean** | Indicates whether the Cache Cluster is enabled. |  [optional]
**eventDataByteRateThreshold** | [**EventThresholdByValue**](EventThresholdByValue.md) |  |  [optional]
**eventDataMsgRateThreshold** | [**EventThresholdByValue**](EventThresholdByValue.md) |  |  [optional]
**eventMaxMemoryThreshold** | [**EventThresholdByPercent**](EventThresholdByPercent.md) |  |  [optional]
**eventMaxTopicsThreshold** | [**EventThresholdByPercent**](EventThresholdByPercent.md) |  |  [optional]
**eventRequestQueueDepthThreshold** | [**EventThresholdByPercent**](EventThresholdByPercent.md) |  |  [optional]
**eventRequestRateThreshold** | [**EventThresholdByValue**](EventThresholdByValue.md) |  |  [optional]
**eventResponseRateThreshold** | [**EventThresholdByValue**](EventThresholdByValue.md) |  |  [optional]
**globalCachingEnabled** | **Boolean** | Indicates whether global caching for the Cache Cluster is enabled, and the Cache Instances will fetch topics from remote Home Cache Clusters when requested, and subscribe to those topics to cache them locally. |  [optional]
**globalCachingHeartbeat** | **Long** | The heartbeat interval, in seconds, used by the Cache Instances to monitor connectivity with the remote Home Cache Clusters. |  [optional]
**globalCachingTopicLifetime** | **Long** | The topic lifetime, in seconds. If no client requests are received for a given global topic over the duration of the topic lifetime, then the Cache Instance will remove the subscription and cached messages for that topic. A value of 0 disables aging. |  [optional]
**maxMemory** | **Long** | The maximum memory usage, in megabytes (MB), for each Cache Instance in the Cache Cluster. |  [optional]
**maxMsgsPerTopic** | **Long** | The maximum number of messages per topic for each Cache Instance in the Cache Cluster. When at the maximum, old messages are removed as new messages arrive. |  [optional]
**maxRequestQueueDepth** | **Long** | The maximum queue depth for cache requests received by the Cache Cluster. |  [optional]
**maxTopicCount** | **Long** | The maximum number of topics for each Cache Instance in the Cache Cluster. |  [optional]
**msgLifetime** | **Long** | The message lifetime, in seconds. If a message remains cached for the duration of its lifetime, the Cache Instance will remove the message. A lifetime of 0 results in the message being retained indefinitely. |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**msgsLost** | **Boolean** | Indicates whether one or more messages were lost by any Cache Instance in the Cache Cluster. |  [optional]
**newTopicAdvertisementEnabled** | **Boolean** | Indicates whether advertising of new topics learned by the Cache Instances in this Cache Cluster is enabled. |  [optional]



