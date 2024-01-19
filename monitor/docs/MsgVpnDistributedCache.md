
# MsgVpnDistributedCache

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**cacheName** | **String** | The name of the Distributed Cache. |  [optional]
**enabled** | **Boolean** | Indicates whether the Distributed Cache is enabled. |  [optional]
**heartbeat** | **Long** | The heartbeat interval, in seconds, used by the Cache Instances to monitor connectivity with the message broker. |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**msgsLost** | **Boolean** | Indicates whether one or more messages were lost by any Cache Instance in the Distributed Cache. |  [optional]
**scheduledDeleteMsgDayList** | **String** | The scheduled delete message day(s), specified as \&quot;daily\&quot; or a comma-separated list of days. Days must be specified as \&quot;Sun\&quot;, \&quot;Mon\&quot;, \&quot;Tue\&quot;, \&quot;Wed\&quot;, \&quot;Thu\&quot;, \&quot;Fri\&quot;, or \&quot;Sat\&quot;, with no spaces, and in sorted order from Sunday to Saturday. |  [optional]
**scheduledDeleteMsgTimeList** | **String** | The scheduled delete message time(s), specified as \&quot;hourly\&quot; or a comma-separated list of 24-hour times in the form hh:mm, or h:mm. There must be no spaces, and times must be in sorted order from 0:00 to 23:59. |  [optional]



