
# MsgVpnClientProfile

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**allowBridgeConnectionsEnabled** | **Boolean** | Indicates whether Bridge clients using the Client Profile are allowed to connect. |  [optional]
**allowCutThroughForwardingEnabled** | **Boolean** | Indicates whether clients using the Client Profile are allowed to bind to endpoints with the cut-through forwarding delivery mode. Deprecated since 2.22. This attribute has been deprecated. Please visit the Solace Product Lifecycle Policy web page for details on deprecated features. |  [optional]
**allowGuaranteedEndpointCreateDurability** | [**AllowGuaranteedEndpointCreateDurabilityEnum**](#AllowGuaranteedEndpointCreateDurabilityEnum) | Indicates whether clients using the Client Profile are allowed to create durable and/or non-durable topic endponts or queues. The allowed values and their meaning are:  &lt;pre&gt; \&quot;all\&quot; - Client can create any type of endpoint. \&quot;durable\&quot; - Client can create only durable endpoints. \&quot;non-durable\&quot; - Client can create only non-durable endpoints. &lt;/pre&gt;  Available since 2.14. |  [optional]
**allowGuaranteedEndpointCreateEnabled** | **Boolean** | Indicates whether clients using the Client Profile are allowed to create topic endponts or queues. |  [optional]
**allowGuaranteedMsgReceiveEnabled** | **Boolean** | Indicates whether clients using the Client Profile are allowed to receive guaranteed messages. |  [optional]
**allowGuaranteedMsgSendEnabled** | **Boolean** | Indicates whether clients using the Client Profile are allowed to send guaranteed messages. |  [optional]
**allowSharedSubscriptionsEnabled** | **Boolean** | Indicates whether shared subscriptions are allowed. Changing this setting does not affect existing subscriptions. |  [optional]
**allowTransactedSessionsEnabled** | **Boolean** | Indicates whether clients using the Client Profile are allowed to establish transacted sessions. |  [optional]
**apiQueueManagementCopyFromOnCreateName** | **String** | The name of a queue to copy settings from when a new queue is created by a client using the Client Profile. The referenced queue must exist in the Message VPN. Deprecated since 2.14. This attribute has been replaced with &#x60;apiQueueManagementCopyFromOnCreateTemplateName&#x60;. |  [optional]
**apiQueueManagementCopyFromOnCreateTemplateName** | **String** | The name of a queue template to copy settings from when a new queue is created by a client using the Client Profile. If the referenced queue template does not exist, queue creation will fail when it tries to resolve this template. Available since 2.14. |  [optional]
**apiTopicEndpointManagementCopyFromOnCreateName** | **String** | The name of a topic endpoint to copy settings from when a new topic endpoint is created by a client using the Client Profile. The referenced topic endpoint must exist in the Message VPN. Deprecated since 2.14. This attribute has been replaced with &#x60;apiTopicEndpointManagementCopyFromOnCreateTemplateName&#x60;. |  [optional]
**apiTopicEndpointManagementCopyFromOnCreateTemplateName** | **String** | The name of a topic endpoint template to copy settings from when a new topic endpoint is created by a client using the Client Profile. If the referenced topic endpoint template does not exist, topic endpoint creation will fail when it tries to resolve this template. Available since 2.14. |  [optional]
**clientProfileName** | **String** | The name of the Client Profile. |  [optional]
**compressionEnabled** | **Boolean** | Indicates whether clients using the Client Profile are allowed to use compression. |  [optional]
**elidingDelay** | **Long** | The amount of time to delay the delivery of messages to clients using the Client Profile after the initial message has been delivered (the eliding delay interval), in milliseconds. A value of 0 means there is no delay in delivering messages to clients. |  [optional]
**elidingEnabled** | **Boolean** | Indicates whether message eliding is enabled for clients using the Client Profile. |  [optional]
**elidingMaxTopicCount** | **Long** | The maximum number of topics tracked for message eliding per client connection using the Client Profile. |  [optional]
**eventClientProvisionedEndpointSpoolUsageThreshold** | [**EventThresholdByPercent**](EventThresholdByPercent.md) |  |  [optional]
**eventConnectionCountPerClientUsernameThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventEgressFlowCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventEndpointCountPerClientUsernameThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventIngressFlowCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventServiceSmfConnectionCountPerClientUsernameThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventServiceWebConnectionCountPerClientUsernameThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventSubscriptionCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventTransactedSessionCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventTransactionCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**maxConnectionCountPerClientUsername** | **Long** | The maximum number of client connections per Client Username using the Client Profile. |  [optional]
**maxEffectiveEndpointCount** | **Integer** | The effective maximum number of queues and topic endpoints per Client Username using the Client Profile. |  [optional]
**maxEffectiveRxFlowCount** | **Integer** | The effective maximum number of receive flows per client using the Client Profile. |  [optional]
**maxEffectiveSubscriptionCount** | **Integer** | The effective maximum number of subscriptions per client using the Client Profile. |  [optional]
**maxEffectiveTransactedSessionCount** | **Integer** | The effective maximum number of transacted sessions per client using the Client Profile. |  [optional]
**maxEffectiveTransactionCount** | **Integer** | The effective maximum number of transactions per client using the Client Profile. |  [optional]
**maxEffectiveTxFlowCount** | **Integer** | The effective maximum number of transmit flows per client using the Client Profile. |  [optional]
**maxEgressFlowCount** | **Long** | The maximum number of transmit flows that can be created by one client using the Client Profile. |  [optional]
**maxEndpointCountPerClientUsername** | **Long** | The maximum number of queues and topic endpoints that can be created by clients with the same Client Username using the Client Profile. |  [optional]
**maxIngressFlowCount** | **Long** | The maximum number of receive flows that can be created by one client using the Client Profile. |  [optional]
**maxMsgsPerTransaction** | **Integer** | The maximum number of publisher and consumer messages combined that is allowed within a transaction for each client associated with this client-profile. Exceeding this limit will result in a transaction prepare or commit failure. Changing this value during operation will not affect existing sessions. It is only validated at transaction creation time. Large transactions consume more resources and are more likely to require retrieving messages from the ADB or from disk to process the transaction prepare or commit requests. The transaction processing rate may diminish if a large number of messages must be retrieved from the ADB or from disk. Care should be taken to not use excessively large transactions needlessly to avoid exceeding resource limits and to avoid reducing the overall broker performance. Available since 2.20. |  [optional]
**maxSubscriptionCount** | **Long** | The maximum number of subscriptions per client using the Client Profile. This limit is not enforced when a client adds a subscription to an endpoint, except for MQTT QoS 1 subscriptions. In addition, this limit is not enforced when a subscription is added using a management interface, such as CLI or SEMP. |  [optional]
**maxTransactedSessionCount** | **Long** | The maximum number of transacted sessions that can be created by one client using the Client Profile. |  [optional]
**maxTransactionCount** | **Long** | The maximum number of transactions that can be created by one client using the Client Profile. |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**queueControl1MaxDepth** | **Integer** | The maximum depth of the \&quot;Control 1\&quot; (C-1) priority queue, in work units. Each work unit is 2048 bytes of message data. |  [optional]
**queueControl1MinMsgBurst** | **Integer** | The number of messages that are always allowed entry into the \&quot;Control 1\&quot; (C-1) priority queue, regardless of the &#x60;queueControl1MaxDepth&#x60; value. |  [optional]
**queueDirect1MaxDepth** | **Integer** | The maximum depth of the \&quot;Direct 1\&quot; (D-1) priority queue, in work units. Each work unit is 2048 bytes of message data. |  [optional]
**queueDirect1MinMsgBurst** | **Integer** | The number of messages that are always allowed entry into the \&quot;Direct 1\&quot; (D-1) priority queue, regardless of the &#x60;queueDirect1MaxDepth&#x60; value. |  [optional]
**queueDirect2MaxDepth** | **Integer** | The maximum depth of the \&quot;Direct 2\&quot; (D-2) priority queue, in work units. Each work unit is 2048 bytes of message data. |  [optional]
**queueDirect2MinMsgBurst** | **Integer** | The number of messages that are always allowed entry into the \&quot;Direct 2\&quot; (D-2) priority queue, regardless of the &#x60;queueDirect2MaxDepth&#x60; value. |  [optional]
**queueDirect3MaxDepth** | **Integer** | The maximum depth of the \&quot;Direct 3\&quot; (D-3) priority queue, in work units. Each work unit is 2048 bytes of message data. |  [optional]
**queueDirect3MinMsgBurst** | **Integer** | The number of messages that are always allowed entry into the \&quot;Direct 3\&quot; (D-3) priority queue, regardless of the &#x60;queueDirect3MaxDepth&#x60; value. |  [optional]
**queueGuaranteed1MaxDepth** | **Integer** | The maximum depth of the \&quot;Guaranteed 1\&quot; (G-1) priority queue, in work units. Each work unit is 2048 bytes of message data. |  [optional]
**queueGuaranteed1MinMsgBurst** | **Integer** | The number of messages that are always allowed entry into the \&quot;Guaranteed 1\&quot; (G-3) priority queue, regardless of the &#x60;queueGuaranteed1MaxDepth&#x60; value. |  [optional]
**rejectMsgToSenderOnNoSubscriptionMatchEnabled** | **Boolean** | Indicates whether to send a negative acknowledgement (NACK) to a client using the Client Profile when discarding a guaranteed message due to no matching subscription found. |  [optional]
**replicationAllowClientConnectWhenStandbyEnabled** | **Boolean** | Indicates whether clients using the Client Profile are allowed to connect to the Message VPN when its replication state is standby. |  [optional]
**serviceMinKeepaliveTimeout** | **Integer** | The minimum client keepalive timeout which will be enforced for client connections. Available since 2.19. |  [optional]
**serviceSmfMaxConnectionCountPerClientUsername** | **Long** | The maximum number of SMF client connections per Client Username using the Client Profile. |  [optional]
**serviceSmfMinKeepaliveEnabled** | **Boolean** | Enable or disable the enforcement of a minimum keepalive timeout for SMF clients. Available since 2.19. |  [optional]
**serviceWebInactiveTimeout** | **Long** | The timeout for inactive Web Transport client sessions using the Client Profile, in seconds. |  [optional]
**serviceWebMaxConnectionCountPerClientUsername** | **Long** | The maximum number of Web Transport client connections per Client Username using the Client Profile. |  [optional]
**serviceWebMaxPayload** | **Long** | The maximum Web Transport payload size before fragmentation occurs for clients using the Client Profile, in bytes. The size of the header is not included. |  [optional]
**tcpCongestionWindowSize** | **Long** | The TCP initial congestion window size for clients using the Client Profile, in multiples of the TCP Maximum Segment Size (MSS). Changing the value from its default of 2 results in non-compliance with RFC 2581. Contact Solace Support before changing this value. |  [optional]
**tcpKeepaliveCount** | **Long** | The number of TCP keepalive retransmissions to a client using the Client Profile before declaring that it is not available. |  [optional]
**tcpKeepaliveIdleTime** | **Long** | The amount of time a client connection using the Client Profile must remain idle before TCP begins sending keepalive probes, in seconds. |  [optional]
**tcpKeepaliveInterval** | **Long** | The amount of time between TCP keepalive retransmissions to a client using the Client Profile when no acknowledgement is received, in seconds. |  [optional]
**tcpMaxSegmentSize** | **Long** | The TCP maximum segment size for clients using the Client Profile, in bytes. Changes are applied to all existing connections. |  [optional]
**tcpMaxWindowSize** | **Long** | The TCP maximum window size for clients using the Client Profile, in kilobytes. Changes are applied to all existing connections. |  [optional]
**tlsAllowDowngradeToPlainTextEnabled** | **Boolean** | Indicates whether clients using the Client Profile are allowed to downgrade an encrypted connection to plain text. |  [optional]


<a name="AllowGuaranteedEndpointCreateDurabilityEnum"></a>
## Enum: AllowGuaranteedEndpointCreateDurabilityEnum
Name | Value
---- | -----
ALL | &quot;all&quot;
DURABLE | &quot;durable&quot;
NON_DURABLE | &quot;non-durable&quot;



