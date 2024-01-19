
# MsgVpnMqttRetainCache

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**backupCacheInstance** | **String** | The name of the backup Cache Instance associated with this MQTT Retain Cache. |  [optional]
**backupFailureReason** | **String** | The reason why the backup cache associated with this MQTT Retain Cache is operationally down, if any. |  [optional]
**backupUp** | **Boolean** | Indicates whether the backup cache associated with this MQTT Retain Cache is operationally up. |  [optional]
**backupUptime** | **Integer** | The number of seconds that the backup cache associated with this MQTT Retain Cache has been operationally up. |  [optional]
**cacheCluster** | **String** | The name of the Cache Cluster associated with this MQTT Retain Cache. |  [optional]
**cacheName** | **String** | The name of the MQTT Retain Cache. |  [optional]
**distributedCache** | **String** | The name of the Distributed Cache associated with this MQTT Retain Cache. |  [optional]
**enabled** | **Boolean** | Indicates whether this MQTT Retain Cache is enabled. When the cache is disabled, neither retain messages nor retain requests will be delivered by the cache. However, live retain messages will continue to be delivered to currently connected MQTT clients. |  [optional]
**failureReason** | **String** | The reason why this MQTT Retain Cache is operationally down, if any. |  [optional]
**msgLifetime** | **Long** | The message lifetime, in seconds. If a message remains cached for the duration of its lifetime, the cache will remove the message. A lifetime of 0 results in the message being retained indefinitely. |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**primaryCacheInstance** | **String** | The name of the primary Cache Instance associated with this MQTT Retain Cache. |  [optional]
**primaryFailureReason** | **String** | The reason why the primary cache associated with this MQTT Retain Cache is operationally down, if any. |  [optional]
**primaryUp** | **Boolean** | Indicates whether the primary cache associated with this MQTT Retain Cache is operationally up. |  [optional]
**primaryUptime** | **Integer** | The number of seconds that the primary cache associated with this MQTT Retain Cache has been operationally up. |  [optional]
**up** | **Boolean** | Indicates whether this MQTT Retain Cache is operationally up. |  [optional]
**uptime** | **Integer** | The number of seconds that the MQTT Retain Cache has been operationally up. |  [optional]



