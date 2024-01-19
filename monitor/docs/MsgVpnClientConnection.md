
# MsgVpnClientConnection

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**clientAddress** | **String** | The IP address and TCP port on the Client side of the Client Connection. |  [optional]
**clientName** | **String** | The name of the Client. |  [optional]
**compression** | **Boolean** | Indicates whether compression is enabled for the Client Connection. |  [optional]
**encryption** | **Boolean** | Indicates whether encryption (TLS) is enabled for the Client Connection. |  [optional]
**fastRetransmitCount** | **Integer** | The number of TCP fast retransmits due to duplicate acknowledgments (ACKs). See RFC 5681 for further details. |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**rxQueueByteCount** | **Integer** | The number of bytes currently in the receive queue for the Client Connection. |  [optional]
**segmentReceivedOutOfOrderCount** | **Integer** | The number of TCP segments received from the Client Connection out of order. |  [optional]
**smoothedRoundTripTime** | **Long** | The TCP smoothed round-trip time (SRTT) for the Client Connection, in nanoseconds. See RFC 2988 for further details. |  [optional]
**tcpState** | **String** | The TCP state of the Client Connection. When fully operational, should be: established. See RFC 793 for further details. The allowed values and their meaning are:  &lt;pre&gt; \&quot;closed\&quot; - No connection state at all. \&quot;listen\&quot; - Waiting for a connection request from any remote TCP and port. \&quot;syn-sent\&quot; - Waiting for a matching connection request after having sent a connection request. \&quot;syn-received\&quot; - Waiting for a confirming connection request acknowledgment after having both received and sent a connection request. \&quot;established\&quot; - An open connection, data received can be delivered to the user. \&quot;close-wait\&quot; - Waiting for a connection termination request from the local user. \&quot;fin-wait-1\&quot; - Waiting for a connection termination request from the remote TCP, or an acknowledgment of the connection termination request previously sent. \&quot;closing\&quot; - Waiting for a connection termination request acknowledgment from the remote TCP. \&quot;last-ack\&quot; - Waiting for an acknowledgment of the connection termination request previously sent to the remote TCP. \&quot;fin-wait-2\&quot; - Waiting for a connection termination request from the remote TCP. \&quot;time-wait\&quot; - Waiting for enough time to pass to be sure the remote TCP received the acknowledgment of its connection termination request. &lt;/pre&gt;  |  [optional]
**timedRetransmitCount** | **Integer** | The number of TCP segments retransmitted due to timeout awaiting an acknowledgement (ACK). See RFC 793 for further details. |  [optional]
**txQueueByteCount** | **Integer** | The number of bytes currently in the transmit queue for the Client Connection. |  [optional]
**uptime** | **Long** | The amount of time in seconds since the Client Connection was established. |  [optional]



