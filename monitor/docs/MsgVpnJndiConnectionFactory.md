
# MsgVpnJndiConnectionFactory

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**allowDuplicateClientIdEnabled** | **Boolean** | Indicates whether new JMS connections can use the same Client identifier (ID) as an existing connection. |  [optional]
**clientDescription** | **String** | The description of the Client. |  [optional]
**clientId** | **String** | The Client identifier (ID). If not specified, a unique value for it will be generated. |  [optional]
**connectionFactoryName** | **String** | The name of the JMS Connection Factory. |  [optional]
**dtoReceiveOverrideEnabled** | **Boolean** | Indicates whether overriding by the Subscriber (Consumer) of the deliver-to-one (DTO) property on messages is enabled. When enabled, the Subscriber can receive all DTO tagged messages. |  [optional]
**dtoReceiveSubscriberLocalPriority** | **Integer** | The priority for receiving deliver-to-one (DTO) messages by the Subscriber (Consumer) if the messages are published on the local broker that the Subscriber is directly connected to. |  [optional]
**dtoReceiveSubscriberNetworkPriority** | **Integer** | The priority for receiving deliver-to-one (DTO) messages by the Subscriber (Consumer) if the messages are published on a remote broker. |  [optional]
**dtoSendEnabled** | **Boolean** | Indicates whether the deliver-to-one (DTO) property is enabled on messages sent by the Publisher (Producer). |  [optional]
**dynamicEndpointCreateDurableEnabled** | **Boolean** | Indicates whether a durable endpoint will be dynamically created on the broker when the client calls \&quot;Session.createDurableSubscriber()\&quot; or \&quot;Session.createQueue()\&quot;. The created endpoint respects the message time-to-live (TTL) according to the \&quot;dynamicEndpointRespectTtlEnabled\&quot; property. |  [optional]
**dynamicEndpointRespectTtlEnabled** | **Boolean** | Indicates whether dynamically created durable and non-durable endpoints respect the message time-to-live (TTL) property. |  [optional]
**guaranteedReceiveAckTimeout** | **Integer** | The timeout for sending the acknowledgement (ACK) for guaranteed messages received by the Subscriber (Consumer), in milliseconds. |  [optional]
**guaranteedReceiveReconnectRetryCount** | **Integer** | The maximum number of attempts to reconnect to the host or list of hosts after the guaranteed  messaging connection has been lost. The value \&quot;-1\&quot; means to retry forever. Available since 2.14. |  [optional]
**guaranteedReceiveReconnectRetryWait** | **Integer** | The amount of time to wait before making another attempt to connect or reconnect to the host after the guaranteed messaging connection has been lost, in milliseconds. Available since 2.14. |  [optional]
**guaranteedReceiveWindowSize** | **Integer** | The size of the window for guaranteed messages received by the Subscriber (Consumer), in messages. |  [optional]
**guaranteedReceiveWindowSizeAckThreshold** | **Integer** | The threshold for sending the acknowledgement (ACK) for guaranteed messages received by the Subscriber (Consumer) as a percentage of &#x60;guaranteedReceiveWindowSize&#x60;. |  [optional]
**guaranteedSendAckTimeout** | **Integer** | The timeout for receiving the acknowledgement (ACK) for guaranteed messages sent by the Publisher (Producer), in milliseconds. |  [optional]
**guaranteedSendWindowSize** | **Integer** | The size of the window for non-persistent guaranteed messages sent by the Publisher (Producer), in messages. For persistent messages the window size is fixed at 1. |  [optional]
**messagingDefaultDeliveryMode** | [**MessagingDefaultDeliveryModeEnum**](#MessagingDefaultDeliveryModeEnum) | The default delivery mode for messages sent by the Publisher (Producer). The allowed values and their meaning are:  &lt;pre&gt; \&quot;persistent\&quot; - The broker spools messages (persists in the Message Spool) as part of the send operation. \&quot;non-persistent\&quot; - The broker does not spool messages (does not persist in the Message Spool) as part of the send operation. &lt;/pre&gt;  |  [optional]
**messagingDefaultDmqEligibleEnabled** | **Boolean** | Indicates whether messages sent by the Publisher (Producer) are Dead Message Queue (DMQ) eligible by default. |  [optional]
**messagingDefaultElidingEligibleEnabled** | **Boolean** | Indicates whether messages sent by the Publisher (Producer) are Eliding eligible by default. |  [optional]
**messagingJmsxUserIdEnabled** | **Boolean** | Indicates whether to include (add or replace) the JMSXUserID property in messages sent by the Publisher (Producer). |  [optional]
**messagingTextInXmlPayloadEnabled** | **Boolean** | Indicates whether encoding of JMS text messages in Publisher (Producer) messages is as XML payload. When disabled, JMS text messages are encoded as a binary attachment. |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**transportCompressionLevel** | **Integer** | The ZLIB compression level for the connection to the broker. The value \&quot;0\&quot; means no compression, and the value \&quot;-1\&quot; means the compression level is specified in the JNDI Properties file. |  [optional]
**transportConnectRetryCount** | **Integer** | The maximum number of retry attempts to establish an initial connection to the host or list of hosts. The value \&quot;0\&quot; means a single attempt (no retries), and the value \&quot;-1\&quot; means to retry forever. |  [optional]
**transportConnectRetryPerHostCount** | **Integer** | The maximum number of retry attempts to establish an initial connection to each host on the list of hosts. The value \&quot;0\&quot; means a single attempt (no retries), and the value \&quot;-1\&quot; means to retry forever. |  [optional]
**transportConnectTimeout** | **Integer** | The timeout for establishing an initial connection to the broker, in milliseconds. |  [optional]
**transportDirectTransportEnabled** | **Boolean** | Indicates whether usage of the Direct Transport mode for sending non-persistent messages is enabled. When disabled, the Guaranteed Transport mode is used. |  [optional]
**transportKeepaliveCount** | **Integer** | The maximum number of consecutive application-level keepalive messages sent without the broker response before the connection to the broker is closed. |  [optional]
**transportKeepaliveEnabled** | **Boolean** | Indicates whether application-level keepalive messages are used to maintain a connection with the Router. |  [optional]
**transportKeepaliveInterval** | **Integer** | The interval between application-level keepalive messages, in milliseconds. |  [optional]
**transportMsgCallbackOnIoThreadEnabled** | **Boolean** | Indicates whether delivery of asynchronous messages is done directly from the I/O thread. |  [optional]
**transportOptimizeDirectEnabled** | **Boolean** | Indicates whether optimization for the Direct Transport delivery mode is enabled. If enabled, the client application is limited to one Publisher (Producer) and one non-durable Subscriber (Consumer). |  [optional]
**transportPort** | **Integer** | The connection port number on the broker for SMF clients. The value \&quot;-1\&quot; means the port is specified in the JNDI Properties file. |  [optional]
**transportReadTimeout** | **Integer** | The timeout for reading a reply from the broker, in milliseconds. |  [optional]
**transportReceiveBufferSize** | **Integer** | The size of the receive socket buffer, in bytes. It corresponds to the SO_RCVBUF socket option. |  [optional]
**transportReconnectRetryCount** | **Integer** | The maximum number of attempts to reconnect to the host or list of hosts after the connection has been lost. The value \&quot;-1\&quot; means to retry forever. |  [optional]
**transportReconnectRetryWait** | **Integer** | The amount of time before making another attempt to connect or reconnect to the host after the connection has been lost, in milliseconds. |  [optional]
**transportSendBufferSize** | **Integer** | The size of the send socket buffer, in bytes. It corresponds to the SO_SNDBUF socket option. |  [optional]
**transportTcpNoDelayEnabled** | **Boolean** | Indicates whether the TCP_NODELAY option is enabled, which disables Nagle&#39;s algorithm for TCP/IP congestion control (RFC 896). |  [optional]
**xaEnabled** | **Boolean** | Indicates whether this is an XA Connection Factory. When enabled, the Connection Factory can be cast to \&quot;XAConnectionFactory\&quot;, \&quot;XAQueueConnectionFactory\&quot; or \&quot;XATopicConnectionFactory\&quot;. |  [optional]


<a name="MessagingDefaultDeliveryModeEnum"></a>
## Enum: MessagingDefaultDeliveryModeEnum
Name | Value
---- | -----
PERSISTENT | &quot;persistent&quot;
NON_PERSISTENT | &quot;non-persistent&quot;



