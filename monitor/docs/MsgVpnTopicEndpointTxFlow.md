
# MsgVpnTopicEndpointTxFlow

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**ackedMsgCount** | **Long** | The number of guaranteed messages delivered and acknowledged by the consumer. |  [optional]
**activityState** | **String** | The activity state of the Flow. The allowed values and their meaning are:  &lt;pre&gt; \&quot;active-browser\&quot; - The Flow is active as a browser. \&quot;active-consumer\&quot; - The Flow is active as a consumer. \&quot;inactive\&quot; - The Flow is inactive. &lt;/pre&gt;  |  [optional]
**bindTime** | **Integer** | The timestamp of when the Flow bound to the Topic Endpoint. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**clientName** | **String** | The name of the Client. |  [optional]
**consumerRedeliveryRequestAllowed** | **Boolean** | Indicates whether redelivery requests can be received as negative acknowledgements (NACKs) from the consumer. Applicable only to REST consumers. |  [optional]
**cutThroughAckedMsgCount** | **Long** | The number of guaranteed messages that used cut-through delivery and are acknowledged by the consumer. |  [optional]
**deliveryState** | **String** | The delivery state of the Flow. The allowed values and their meaning are:  &lt;pre&gt; \&quot;closed\&quot; - The Flow is unbound. \&quot;opened\&quot; - The Flow is bound but inactive. \&quot;unbinding\&quot; - The Flow received an unbind request. \&quot;handshaking\&quot; - The Flow is handshaking to become active. \&quot;deliver-cut-through\&quot; - The Flow is streaming messages using direct+guaranteed delivery. \&quot;deliver-from-input-stream\&quot; - The Flow is streaming messages using guaranteed delivery. \&quot;deliver-from-memory\&quot; - The Flow throttled causing message delivery from memory (RAM). \&quot;deliver-from-spool\&quot; - The Flow stalled causing message delivery from spool (ADB or disk). &lt;/pre&gt;  |  [optional]
**flowId** | **Long** | The identifier (ID) of the Flow. |  [optional]
**highestAckPendingMsgId** | **Long** | The highest identifier (ID) of message transmitted and waiting for acknowledgement. |  [optional]
**lastAckedMsgId** | **Long** | The identifier (ID) of the last message transmitted and acknowledged by the consumer. |  [optional]
**lowestAckPendingMsgId** | **Long** | The lowest identifier (ID) of message transmitted and waiting for acknowledgement. |  [optional]
**maxUnackedMsgsExceededMsgCount** | **Long** | The number of guaranteed messages that exceeded the maximum number of delivered unacknowledged messages. |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**noLocalDelivery** | **Boolean** | Indicates whether not to deliver messages to a consumer that published them. |  [optional]
**redeliveredMsgCount** | **Long** | The number of guaranteed messages that were redelivered. |  [optional]
**redeliveryRequestCount** | **Long** | The number of consumer requests via negative acknowledgements (NACKs) to redeliver guaranteed messages. |  [optional]
**sessionName** | **String** | The name of the Transacted Session for the Flow. |  [optional]
**storeAndForwardAckedMsgCount** | **Long** | The number of guaranteed messages that used store and forward delivery and are acknowledged by the consumer. |  [optional]
**topicEndpointName** | **String** | The name of the Topic Endpoint. |  [optional]
**transportRetransmitMsgCount** | **Long** | The number of guaranteed messages that were retransmitted at the transport layer as part of a single delivery attempt. Available since 2.18. |  [optional]
**unackedMsgCount** | **Long** | The number of guaranteed messages delivered but not yet acknowledged by the consumer. |  [optional]
**usedWindowSize** | **Long** | The number of guaranteed messages using the available window size. |  [optional]
**windowClosedCount** | **Long** | The number of times the window for guaranteed messages was filled and closed before an acknowledgement was received. |  [optional]
**windowSize** | **Long** | The number of outstanding guaranteed messages that can be transmitted over the Flow before an acknowledgement is received. |  [optional]



