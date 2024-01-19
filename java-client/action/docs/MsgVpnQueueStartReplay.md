
# MsgVpnQueueStartReplay

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**afterMsg** | **String** | The Message after which to begin replay, identified by its Replication Group Message ID. This attribute is absent from a GET and not updated when absent in a PUT, subject to the exceptions in note 4. Available since 2.21. |  [optional]
**fromTime** | **Integer** | The time to begin replaying messages from. The value must be no less than the time that the replay log was created. To play back the whole log, this parameter must be omitted. This attribute is absent from a GET and not updated when absent in a PUT, subject to the exceptions in note 4. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**replayLogName** | **String** | The name of the Replay Log. This attribute is absent from a GET and not updated when absent in a PUT, subject to the exceptions in note 4. |  [optional]



