
# Broker

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**authClientCertRevocationCheckMode** | [**AuthClientCertRevocationCheckModeEnum**](#AuthClientCertRevocationCheckModeEnum) | The client certificate revocation checking mode used when a client authenticates with a client certificate. The allowed values and their meaning are:  &lt;pre&gt; \&quot;none\&quot; - Do not perform any certificate revocation checking. \&quot;ocsp\&quot; - Use the Open Certificate Status Protcol (OCSP) for certificate revocation checking. \&quot;crl\&quot; - Use Certificate Revocation Lists (CRL) for certificate revocation checking. \&quot;ocsp-crl\&quot; - Use OCSP first, but if OCSP fails to return an unambiguous result, then check via CRL. &lt;/pre&gt;  |  [optional]
**averageRxByteRate** | **Long** | The one minute average of the message rate received by the Broker, in bytes per second (B/sec). Available since 2.14. |  [optional]
**averageRxCompressedByteRate** | **Long** | The one minute average of the compressed message rate received by the Broker, in bytes per second (B/sec). Available since 2.14. |  [optional]
**averageRxMsgRate** | **Long** | The one minute average of the message rate received by the Broker, in messages per second (msg/sec). Available since 2.14. |  [optional]
**averageRxUncompressedByteRate** | **Long** | The one minute average of the uncompressed message rate received by the Broker, in bytes per second (B/sec). Available since 2.14. |  [optional]
**averageTxByteRate** | **Long** | The one minute average of the message rate transmitted by the Broker, in bytes per second (B/sec). Available since 2.14. |  [optional]
**averageTxCompressedByteRate** | **Long** | The one minute average of the compressed message rate transmitted by the Broker, in bytes per second (B/sec). Available since 2.14. |  [optional]
**averageTxMsgRate** | **Long** | The one minute average of the message rate transmitted by the Broker, in messages per second (msg/sec). Available since 2.14. |  [optional]
**averageTxUncompressedByteRate** | **Long** | The one minute average of the uncompressed message rate transmitted by the Broker, in bytes per second (B/sec). Available since 2.14. |  [optional]
**configSyncAuthenticationClientCertMaxChainDepth** | **Long** | The maximum depth for a client certificate chain. The depth of a chain is defined as the number of signing CA certificates that are present in the chain back to a trusted self-signed root CA certificate. Available since 2.22. |  [optional]
**configSyncAuthenticationClientCertValidateDateEnabled** | **Boolean** | Enable or disable validation of the \&quot;Not Before\&quot; and \&quot;Not After\&quot; validity dates in the authentication certificate(s). Available since 2.22. |  [optional]
**configSyncClientProfileTcpInitialCongestionWindow** | **Long** | The TCP initial congestion window size for Config Sync clients, in multiples of the TCP Maximum Segment Size (MSS). Changing the value from its default of 2 results in non-compliance with RFC 2581. Contact Solace Support before changing this value. Available since 2.22. |  [optional]
**configSyncClientProfileTcpKeepaliveCount** | **Long** | The number of TCP keepalive retransmissions to a client using the Client Profile before declaring that it is not available. Available since 2.22. |  [optional]
**configSyncClientProfileTcpKeepaliveIdle** | **Long** | The amount of time a client connection using the Client Profile must remain idle before TCP begins sending keepalive probes, in seconds. Available since 2.22. |  [optional]
**configSyncClientProfileTcpKeepaliveInterval** | **Long** | The amount of time between TCP keepalive retransmissions to a client using the Client Profile when no acknowledgement is received, in seconds. Available since 2.22. |  [optional]
**configSyncClientProfileTcpMaxWindow** | **Long** | The TCP maximum window size for clients using the Client Profile, in kilobytes. Changes are applied to all existing connections. Available since 2.22. |  [optional]
**configSyncClientProfileTcpMss** | **Long** | The TCP maximum segment size for clients using the Client Profile, in bytes. Changes are applied to all existing connections. Available since 2.22. |  [optional]
**configSyncEnabled** | **Boolean** | Enable or disable configuration synchronization for High Availability or Disaster Recovery. Available since 2.22. |  [optional]
**configSyncLastFailureReason** | **String** | The reason for the last transition to a \&quot;Down\&quot; operational status. On transitions to the \&quot;Up\&quot; operational status this value is cleared. Available since 2.22. |  [optional]
**configSyncSynchronizeUsernameEnabled** | **Boolean** | Enable or disable the synchronizing of usernames within High Availability groups. The transition from not synchronizing to synchronizing will cause the High Availability mate to fall out of sync. Recommendation: leave this as enabled. Available since 2.22. |  [optional]
**configSyncTlsEnabled** | **Boolean** | Enable or disable the use of TLS encryption of the configuration synchronization communications between brokers in High Availability groups and/or Disaster Recovery sites. Available since 2.22. |  [optional]
**configSyncUp** | **Boolean** | Indicates whether the configuration synchronization facility is operational. \&quot;True\&quot; indicates the facility is Up, otherwise it is Down. When \&quot;False\&quot; the configSyncLastFailureReason will provide further detail. Available since 2.22. |  [optional]
**cspfVersion** | **Integer** | The current CSPF version. Available since 2.17. |  [optional]
**guaranteedMsgingDefragmentationEstimatedFragmentation** | **Long** | An approximation of the amount of disk space consumed, but not used, by the persisted data. Calculated as a percentage of total space. Available since 2.18. |  [optional]
**guaranteedMsgingDefragmentationEstimatedRecoverableSpace** | **Integer** | An approximation of the amount of disk space recovered upon a successfully completed execution of a defragmentation operation. Expressed in MB. Available since 2.18. |  [optional]
**guaranteedMsgingDefragmentationLastCompletedOn** | **Integer** | A timestamp reflecting when the last defragmentation completed. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). Available since 2.18. |  [optional]
**guaranteedMsgingDefragmentationLastCompletionPercentage** | **Long** | How much of the message spool was visited during the last defragmentation operation. This number reflects the percentage of the message spool visited in terms of disk space (as opposed to, for example, spool files). Available since 2.18. |  [optional]
**guaranteedMsgingDefragmentationLastExitCondition** | **String** | Reflects how the last defragmentation operation completed. The allowed values and their meaning are:  &lt;pre&gt; \&quot;success\&quot; - Defragmentation completed successfully. \&quot;unmovable-local-transaction\&quot; - Defragmentation stopped after encountering an unmovable local transaction. \&quot;unmovable-xa-transaction\&quot; - Defragmentation stopped after encountering an unmovable XA transaction. \&quot;incomplete\&quot; - Defragmentation stopped prematurely. \&quot;stopped-by-administrator\&quot; - Defragmentation stopped by administrator. &lt;/pre&gt;  Available since 2.18. |  [optional]
**guaranteedMsgingDefragmentationLastExitConditionInformation** | **String** | Optional additional information regarding the exit condition of the last defragmentation operation. Available since 2.18. |  [optional]
**guaranteedMsgingDefragmentationStatus** | **String** | Defragmentation status of guaranteed messaging. The allowed values and their meaning are:  &lt;pre&gt; \&quot;idle\&quot; - Defragmentation is not currently running. \&quot;pending\&quot; - Degfragmentation is preparing to run. \&quot;active\&quot; - Defragmentation is in progress. &lt;/pre&gt;  Available since 2.18. |  [optional]
**guaranteedMsgingDefragmentationStatusActiveCompletionPercentage** | **Long** | The estimated completion percentage of a defragmentation operation currently in progress. Only valid if the defragmentation status is \&quot;Active\&quot;. Available since 2.18. |  [optional]
**guaranteedMsgingDiskArrayWwn** | **String** | The WWN number to use when accessing a LUN on an external disk array. Available since 2.18. |  [optional]
**guaranteedMsgingDiskLocation** | [**GuaranteedMsgingDiskLocationEnum**](#GuaranteedMsgingDiskLocationEnum) | The disk location for the the guaranteed message spool (required for high availability with guaranteed messaging). When external is chosen the guaranteed message spool is stored on an external disk array attached to the router. If internal storage is currently used, changing to external causes message spooling on the router to stop and messages spooled on the internal storage to be deleted. If internal is chosen the guaranteed message spool is stored on an external disk array attached to the router. If internal storage is currently used, changing to external causes message spooling on the router to stop and messages spooled on the internal storage to be deleted. The allowed values and their meaning are:  &lt;pre&gt; \&quot;external\&quot; - The guaranteed message spool is stored on an external disk array attached to the appliance. \&quot;internal\&quot; - The guaranteed message spool is stored internally on the appliance. &lt;/pre&gt;  Available since 2.18. |  [optional]
**guaranteedMsgingEnabled** | **Boolean** | Enable or disable Guaranteed Messaging. Available since 2.18. |  [optional]
**guaranteedMsgingEventCacheUsageThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**guaranteedMsgingEventDeliveredUnackedThreshold** | [**EventThresholdByPercent**](EventThresholdByPercent.md) |  |  [optional]
**guaranteedMsgingEventDiskUsageThreshold** | [**EventThresholdByPercent**](EventThresholdByPercent.md) |  |  [optional]
**guaranteedMsgingEventEgressFlowCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**guaranteedMsgingEventEndpointCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**guaranteedMsgingEventIngressFlowCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**guaranteedMsgingEventMsgCountThreshold** | [**EventThresholdByPercent**](EventThresholdByPercent.md) |  |  [optional]
**guaranteedMsgingEventMsgSpoolFileCountThreshold** | [**EventThresholdByPercent**](EventThresholdByPercent.md) |  |  [optional]
**guaranteedMsgingEventMsgSpoolUsageThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**guaranteedMsgingEventTransactedSessionCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**guaranteedMsgingEventTransactedSessionResourceCountThreshold** | [**EventThresholdByPercent**](EventThresholdByPercent.md) |  |  [optional]
**guaranteedMsgingEventTransactionCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**guaranteedMsgingMaxCacheUsage** | **Integer** | Guaranteed messaging cache usage limit. Expressed as a maximum percentage of the NAB&#39;s egress queueing. resources that the guaranteed message cache is allowed to use. Available since 2.18. |  [optional]
**guaranteedMsgingMaxMsgSpoolUsage** | **Long** | The maximum total message spool usage allowed across all VPNs on this broker, in megabytes. Recommendation: the maximum value should be less than 90% of the disk space allocated for the guaranteed message spool. Available since 2.18. |  [optional]
**guaranteedMsgingOperationalStatus** | **String** | Operational status of guaranteed messaging. The allowed values and their meaning are:  &lt;pre&gt; \&quot;disabled\&quot; - The operational status of guaranteed messaging is Disabled. \&quot;not-ready\&quot; - The operational status of guaranteed messaging is NotReady. \&quot;standby\&quot; - The operational status of guaranteed messaging is Standby. \&quot;activating\&quot; - The operational status of guaranteed messaging is Activating. \&quot;active\&quot; - The operational status of guaranteed messaging is Active. &lt;/pre&gt;  Available since 2.18. |  [optional]
**guaranteedMsgingTransactionReplicationCompatibilityMode** | [**GuaranteedMsgingTransactionReplicationCompatibilityModeEnum**](#GuaranteedMsgingTransactionReplicationCompatibilityModeEnum) | The replication compatibility mode for the router. The default value is &#x60;\&quot;legacy\&quot;&#x60;. The allowed values and their meaning are:\&quot;legacy\&quot; - All transactions originated by clients are replicated to the standby site without using transactions.\&quot;transacted\&quot; - All transactions originated by clients are replicated to the standby site using transactions. The allowed values and their meaning are:  &lt;pre&gt; \&quot;legacy\&quot; - All transactions originated by clients are replicated to the standby site without using transactions. \&quot;transacted\&quot; - All transactions originated by clients are replicated to the standby site using transactions. &lt;/pre&gt;  Available since 2.18. |  [optional]
**guaranteedMsgingVirtualRouterWhenActiveActive** | [**GuaranteedMsgingVirtualRouterWhenActiveActiveEnum**](#GuaranteedMsgingVirtualRouterWhenActiveActiveEnum) | The High Availability role for this broker if using the legacy Active/Active configuration for high availability (not recommended). Note: for Active/Standby high availability configuration, this setting is ignored. The allowed values and their meaning are:  &lt;pre&gt; \&quot;primary\&quot; - The primary virtual router. \&quot;backup\&quot; - The backup virtual router. &lt;/pre&gt;  Available since 2.18. |  [optional]
**rxByteCount** | **Long** | The amount of messages received from clients by the Broker, in bytes (B). Available since 2.14. |  [optional]
**rxByteRate** | **Long** | The current message rate received by the Broker, in bytes per second (B/sec). Available since 2.14. |  [optional]
**rxCompressedByteCount** | **Long** | The amount of compressed messages received by the Broker, in bytes (B). Available since 2.14. |  [optional]
**rxCompressedByteRate** | **Long** | The current compressed message rate received by the Broker, in bytes per second (B/sec). Available since 2.14. |  [optional]
**rxCompressionRatio** | **String** | The compression ratio for messages received by the Broker. Available since 2.14. |  [optional]
**rxMsgCount** | **Long** | The number of messages received from clients by the Broker. Available since 2.14. |  [optional]
**rxMsgRate** | **Long** | The current message rate received by the Broker, in messages per second (msg/sec). Available since 2.14. |  [optional]
**rxUncompressedByteCount** | **Long** | The amount of uncompressed messages received by the Broker, in bytes (B). Available since 2.14. |  [optional]
**rxUncompressedByteRate** | **Long** | The current uncompressed message rate received by the Broker, in bytes per second (B/sec). Available since 2.14. |  [optional]
**serviceAmqpEnabled** | **Boolean** | Enable or disable the AMQP service. When disabled new AMQP Clients may not connect through the global or per-VPN AMQP listen-ports, and all currently connected AMQP Clients are immediately disconnected. Available since 2.17. |  [optional]
**serviceAmqpTlsListenPort** | **Long** | TCP port number that AMQP clients can use to connect to the broker using raw TCP over TLS. Available since 2.17. |  [optional]
**serviceEventConnectionCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**serviceHealthCheckEnabled** | **Boolean** | Enable or disable the health-check service. Available since 2.17. |  [optional]
**serviceHealthCheckListenPort** | **Long** | The port number for the health-check service. The port must be unique across the message backbone. The health-check service must be disabled to change the port. Available since 2.17. |  [optional]
**serviceMqttEnabled** | **Boolean** | Enable or disable the MQTT service. When disabled new MQTT Clients may not connect through the per-VPN MQTT listen-ports, and all currently connected MQTT Clients are immediately disconnected. Available since 2.17. |  [optional]
**serviceMsgBackboneEnabled** | **Boolean** | Enable or disable the msg-backbone service. When disabled new Clients may not connect through global or per-VPN listen-ports, and all currently connected Clients are immediately disconnected. Available since 2.17. |  [optional]
**serviceRestEventOutgoingConnectionCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**serviceRestIncomingEnabled** | **Boolean** | Enable or disable the REST service incoming connections on the router. Available since 2.17. |  [optional]
**serviceRestOutgoingEnabled** | **Boolean** | Enable or disable the REST service outgoing connections on the router. Available since 2.17. |  [optional]
**serviceSempLegacyTimeoutEnabled** | **Boolean** | Enable or disable extended SEMP timeouts for paged GETs. When a request times out, it returns the current page of content, even if the page is not full.  When enabled, the timeout is 60 seconds. When disabled, the timeout is 5 seconds.  The recommended setting is disabled (no legacy-timeout).  This parameter is intended as a temporary workaround to be used until SEMP clients can handle short pages.  This setting will be removed in a future release. Available since 2.18. |  [optional]
**serviceSempPlainTextEnabled** | **Boolean** | Enable or disable plain-text SEMP service. Available since 2.17. |  [optional]
**serviceSempPlainTextListenPort** | **Long** | The TCP port for plain-text SEMP client connections. Available since 2.17. |  [optional]
**serviceSempSessionIdleTimeout** | **Integer** | The session idle timeout, in minutes. Sessions will be invalidated if there is no activity in this period of time. Available since 2.21. |  [optional]
**serviceSempSessionMaxLifetime** | **Integer** | The maximum lifetime of a session, in minutes. Sessions will be invalidated after this period of time, regardless of activity. Available since 2.21. |  [optional]
**serviceSempTlsEnabled** | **Boolean** | Enable or disable TLS SEMP service. Available since 2.17. |  [optional]
**serviceSempTlsListenPort** | **Long** | The TCP port for TLS SEMP client connections. Available since 2.17. |  [optional]
**serviceSmfCompressionListenPort** | **Long** | TCP port number that SMF clients can use to connect to the broker using raw compression TCP. Available since 2.17. |  [optional]
**serviceSmfEnabled** | **Boolean** | Enable or disable the SMF service. When disabled new SMF Clients may not connect through the global listen-ports, and all currently connected SMF Clients are immediately disconnected. Available since 2.17. |  [optional]
**serviceSmfEventConnectionCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**serviceSmfPlainTextListenPort** | **Long** | TCP port number that SMF clients can use to connect to the broker using raw TCP. Available since 2.17. |  [optional]
**serviceSmfRoutingControlListenPort** | **Long** | TCP port number that SMF clients can use to connect to the broker using raw routing control TCP. Available since 2.17. |  [optional]
**serviceSmfTlsListenPort** | **Long** | TCP port number that SMF clients can use to connect to the broker using raw TCP over TLS. Available since 2.17. |  [optional]
**serviceTlsEventConnectionCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**serviceWebTransportEnabled** | **Boolean** | Enable or disable the web-transport service. When disabled new web-transport Clients may not connect through the global listen-ports, and all currently connected web-transport Clients are immediately disconnected. Available since 2.17. |  [optional]
**serviceWebTransportPlainTextListenPort** | **Long** | The TCP port for plain-text WEB client connections. Available since 2.17. |  [optional]
**serviceWebTransportTlsListenPort** | **Long** | The TCP port for TLS WEB client connections. Available since 2.17. |  [optional]
**serviceWebTransportWebUrlSuffix** | **String** | Used to specify the Web URL suffix that will be used by Web clients when communicating with the broker. Available since 2.17. |  [optional]
**tlsBlockVersion10Enabled** | **Boolean** | Indicates whether incoming TLS version 1.0 connections are blocked. When blocked, existing TLS 1.0 connections from Clients and SEMP users remain connected while new connections are blocked. Note that support for TLS 1.0 will eventually be discontinued, at which time TLS 1.0 connections will be blocked regardless of this setting. |  [optional]
**tlsBlockVersion11Enabled** | **Boolean** | Indicates whether TLS version 1.1 connections are blocked. When blocked, all existing incoming and outgoing TLS 1.1 connections with Clients, SEMP users, and LDAP servers remain connected while new connections are blocked. Note that support for TLS 1.1 will eventually be discontinued, at which time TLS 1.1 connections will be blocked regardless of this setting. |  [optional]
**tlsCipherSuiteManagementDefaultList** | **String** | The colon-separated list of default cipher suites for TLS management connections. |  [optional]
**tlsCipherSuiteManagementList** | **String** | The colon-separated list of cipher suites used for TLS management connections (e.g. SEMP, LDAP). The value \&quot;default\&quot; implies all supported suites ordered from most secure to least secure. |  [optional]
**tlsCipherSuiteManagementSupportedList** | **String** | The colon-separated list of supported cipher suites for TLS management connections. |  [optional]
**tlsCipherSuiteMsgBackboneDefaultList** | **String** | The colon-separated list of default cipher suites for TLS data connections. |  [optional]
**tlsCipherSuiteMsgBackboneList** | **String** | The colon-separated list of cipher suites used for TLS data connections (e.g. client pub/sub). The value \&quot;default\&quot; implies all supported suites ordered from most secure to least secure. |  [optional]
**tlsCipherSuiteMsgBackboneSupportedList** | **String** | The colon-separated list of supported cipher suites for TLS data connections. |  [optional]
**tlsCipherSuiteSecureShellDefaultList** | **String** | The colon-separated list of default cipher suites for TLS secure shell connections. |  [optional]
**tlsCipherSuiteSecureShellList** | **String** | The colon-separated list of cipher suites used for TLS secure shell connections (e.g. SSH, SFTP, SCP). The value \&quot;default\&quot; implies all supported suites ordered from most secure to least secure. |  [optional]
**tlsCipherSuiteSecureShellSupportedList** | **String** | The colon-separated list of supported cipher suites for TLS secure shell connections. |  [optional]
**tlsCrimeExploitProtectionEnabled** | **Boolean** | Indicates whether protection against the CRIME exploit is enabled. When enabled, TLS+compressed messaging performance is degraded. This protection should only be disabled if sufficient ACL and authentication features are being employed such that a potential attacker does not have sufficient access to trigger the exploit. |  [optional]
**tlsStandardDomainCertificateAuthoritiesEnabled** | **Boolean** | Enable or disable the standard domain certificate authority list. Available since 2.19. |  [optional]
**tlsTicketLifetime** | **Integer** | The TLS ticket lifetime in seconds. When a client connects with TLS, a session with a session ticket is created using the TLS ticket lifetime which determines how long the client has to resume the session. |  [optional]
**tlsVersionSupportedList** | **String** | The comma-separated list of supported TLS versions. |  [optional]
**txByteCount** | **Long** | The amount of messages transmitted to clients by the Broker, in bytes (B). Available since 2.14. |  [optional]
**txByteRate** | **Long** | The current message rate transmitted by the Broker, in bytes per second (B/sec). Available since 2.14. |  [optional]
**txCompressedByteCount** | **Long** | The amount of compressed messages transmitted by the Broker, in bytes (B). Available since 2.14. |  [optional]
**txCompressedByteRate** | **Long** | The current compressed message rate transmitted by the Broker, in bytes per second (B/sec). Available since 2.14. |  [optional]
**txCompressionRatio** | **String** | The compression ratio for messages transmitted by the Broker. Available since 2.14. |  [optional]
**txMsgCount** | **Long** | The number of messages transmitted to clients by the Broker. Available since 2.14. |  [optional]
**txMsgRate** | **Long** | The current message rate transmitted by the Broker, in messages per second (msg/sec). Available since 2.14. |  [optional]
**txUncompressedByteCount** | **Long** | The amount of uncompressed messages transmitted by the Broker, in bytes (B). Available since 2.14. |  [optional]
**txUncompressedByteRate** | **Long** | The current uncompressed message rate transmitted by the Broker, in bytes per second (B/sec). Available since 2.14. |  [optional]


<a name="AuthClientCertRevocationCheckModeEnum"></a>
## Enum: AuthClientCertRevocationCheckModeEnum
Name | Value
---- | -----
NONE | &quot;none&quot;
OCSP | &quot;ocsp&quot;
CRL | &quot;crl&quot;
OCSP_CRL | &quot;ocsp-crl&quot;


<a name="GuaranteedMsgingDiskLocationEnum"></a>
## Enum: GuaranteedMsgingDiskLocationEnum
Name | Value
---- | -----
EXTERNAL | &quot;external&quot;
INTERNAL | &quot;internal&quot;


<a name="GuaranteedMsgingTransactionReplicationCompatibilityModeEnum"></a>
## Enum: GuaranteedMsgingTransactionReplicationCompatibilityModeEnum
Name | Value
---- | -----
LEGACY | &quot;legacy&quot;
TRANSACTED | &quot;transacted&quot;


<a name="GuaranteedMsgingVirtualRouterWhenActiveActiveEnum"></a>
## Enum: GuaranteedMsgingVirtualRouterWhenActiveActiveEnum
Name | Value
---- | -----
PRIMARY | &quot;primary&quot;
BACKUP | &quot;backup&quot;



