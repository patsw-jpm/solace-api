
# Broker

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**authClientCertRevocationCheckMode** | [**AuthClientCertRevocationCheckModeEnum**](#AuthClientCertRevocationCheckModeEnum) | The client certificate revocation checking mode used when a client authenticates with a client certificate. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;\&quot;none\&quot;&#x60;. The allowed values and their meaning are:  &lt;pre&gt; \&quot;none\&quot; - Do not perform any certificate revocation checking. \&quot;ocsp\&quot; - Use the Open Certificate Status Protcol (OCSP) for certificate revocation checking. \&quot;crl\&quot; - Use Certificate Revocation Lists (CRL) for certificate revocation checking. \&quot;ocsp-crl\&quot; - Use OCSP first, but if OCSP fails to return an unambiguous result, then check via CRL. &lt;/pre&gt;  |  [optional]
**configSyncAuthenticationClientCertMaxChainDepth** | **Long** | The maximum depth for a client certificate chain. The depth of a chain is defined as the number of signing CA certificates that are present in the chain back to a trusted self-signed root CA certificate. The default value is &#x60;3&#x60;. Available since 2.22. |  [optional]
**configSyncAuthenticationClientCertValidateDateEnabled** | **Boolean** | Enable or disable validation of the \&quot;Not Before\&quot; and \&quot;Not After\&quot; validity dates in the authentication certificate(s). The default value is &#x60;true&#x60;. Available since 2.22. |  [optional]
**configSyncClientProfileTcpInitialCongestionWindow** | **Long** | The TCP initial congestion window size for Config Sync clients, in multiples of the TCP Maximum Segment Size (MSS). Changing the value from its default of 2 results in non-compliance with RFC 2581. Contact Solace Support before changing this value. The default value is &#x60;2&#x60;. Available since 2.22. |  [optional]
**configSyncClientProfileTcpKeepaliveCount** | **Long** | The number of TCP keepalive retransmissions to a client using the Client Profile before declaring that it is not available. The default value is &#x60;5&#x60;. Available since 2.22. |  [optional]
**configSyncClientProfileTcpKeepaliveIdle** | **Long** | The amount of time a client connection using the Client Profile must remain idle before TCP begins sending keepalive probes, in seconds. The default value is &#x60;3&#x60;. Available since 2.22. |  [optional]
**configSyncClientProfileTcpKeepaliveInterval** | **Long** | The amount of time between TCP keepalive retransmissions to a client using the Client Profile when no acknowledgement is received, in seconds. The default value is &#x60;1&#x60;. Available since 2.22. |  [optional]
**configSyncClientProfileTcpMaxWindow** | **Long** | The TCP maximum window size for clients using the Client Profile, in kilobytes. Changes are applied to all existing connections. The default value is &#x60;256&#x60;. Available since 2.22. |  [optional]
**configSyncClientProfileTcpMss** | **Long** | The TCP maximum segment size for clients using the Client Profile, in bytes. Changes are applied to all existing connections. The default value is &#x60;1460&#x60;. Available since 2.22. |  [optional]
**configSyncEnabled** | **Boolean** | Enable or disable configuration synchronization for High Availability or Disaster Recovery. The default value is &#x60;false&#x60;. Available since 2.22. |  [optional]
**configSyncSynchronizeUsernameEnabled** | **Boolean** | Enable or disable the synchronizing of usernames within High Availability groups. The transition from not synchronizing to synchronizing will cause the High Availability mate to fall out of sync. Recommendation: leave this as enabled. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;true&#x60;. Available since 2.22. |  [optional]
**configSyncTlsEnabled** | **Boolean** | Enable or disable the use of TLS encryption of the configuration synchronization communications between brokers in High Availability groups and/or Disaster Recovery sites. The default value is &#x60;false&#x60;. Available since 2.22. |  [optional]
**guaranteedMsgingDiskArrayWwn** | **String** | The WWN number to use when accessing a LUN on an external disk array. The default value is &#x60;\&quot;\&quot;&#x60;. Available since 2.18. |  [optional]
**guaranteedMsgingDiskLocation** | [**GuaranteedMsgingDiskLocationEnum**](#GuaranteedMsgingDiskLocationEnum) | The disk location for the the guaranteed message spool (required for high availability with guaranteed messaging). When external is chosen the guaranteed message spool is stored on an external disk array attached to the router. If internal storage is currently used, changing to external causes message spooling on the router to stop and messages spooled on the internal storage to be deleted. If internal is chosen the guaranteed message spool is stored on an external disk array attached to the router. If internal storage is currently used, changing to external causes message spooling on the router to stop and messages spooled on the internal storage to be deleted. The default value is &#x60;\&quot;external\&quot;&#x60;. The allowed values and their meaning are:  &lt;pre&gt; \&quot;external\&quot; - The guaranteed message spool is stored on an external disk array attached to the appliance. \&quot;internal\&quot; - The guaranteed message spool is stored internally on the appliance. &lt;/pre&gt;  Available since 2.18. |  [optional]
**guaranteedMsgingEnabled** | **Boolean** | Enable or disable Guaranteed Messaging. The default value is &#x60;false&#x60;. Available since 2.18. |  [optional]
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
**guaranteedMsgingMaxCacheUsage** | **Integer** | Guaranteed messaging cache usage limit. Expressed as a maximum percentage of the NAB&#39;s egress queueing. resources that the guaranteed message cache is allowed to use. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;10&#x60;. Available since 2.18. |  [optional]
**guaranteedMsgingMaxMsgSpoolUsage** | **Long** | The maximum total message spool usage allowed across all VPNs on this broker, in megabytes. Recommendation: the maximum value should be less than 90% of the disk space allocated for the guaranteed message spool. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;60000&#x60;. Available since 2.18. |  [optional]
**guaranteedMsgingTransactionReplicationCompatibilityMode** | [**GuaranteedMsgingTransactionReplicationCompatibilityModeEnum**](#GuaranteedMsgingTransactionReplicationCompatibilityModeEnum) | The replication compatibility mode for the router. The default value is &#x60;\&quot;legacy\&quot;&#x60;. The allowed values and their meaning are:\&quot;legacy\&quot; - All transactions originated by clients are replicated to the standby site without using transactions.\&quot;transacted\&quot; - All transactions originated by clients are replicated to the standby site using transactions. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;\&quot;legacy\&quot;&#x60;. The allowed values and their meaning are:  &lt;pre&gt; \&quot;legacy\&quot; - All transactions originated by clients are replicated to the standby site without using transactions. \&quot;transacted\&quot; - All transactions originated by clients are replicated to the standby site using transactions. &lt;/pre&gt;  Available since 2.18. |  [optional]
**guaranteedMsgingVirtualRouterWhenActiveActive** | [**GuaranteedMsgingVirtualRouterWhenActiveActiveEnum**](#GuaranteedMsgingVirtualRouterWhenActiveActiveEnum) | The High Availability role for this broker if using the legacy Active/Active configuration for high availability (not recommended). Note: for Active/Standby high availability configuration, this setting is ignored. The default value is &#x60;\&quot;primary\&quot;&#x60;. The allowed values and their meaning are:  &lt;pre&gt; \&quot;primary\&quot; - The primary virtual router. \&quot;backup\&quot; - The backup virtual router. &lt;/pre&gt;  Available since 2.18. |  [optional]
**serviceAmqpEnabled** | **Boolean** | Enable or disable the AMQP service. When disabled new AMQP Clients may not connect through the global or per-VPN AMQP listen-ports, and all currently connected AMQP Clients are immediately disconnected. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;false&#x60;. Available since 2.17. |  [optional]
**serviceAmqpTlsListenPort** | **Long** | TCP port number that AMQP clients can use to connect to the broker using raw TCP over TLS. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;0&#x60;. Available since 2.17. |  [optional]
**serviceEventConnectionCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**serviceHealthCheckEnabled** | **Boolean** | Enable or disable the health-check service. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;false&#x60;. Available since 2.17. |  [optional]
**serviceHealthCheckListenPort** | **Long** | The port number for the health-check service. The port must be unique across the message backbone. The health-check service must be disabled to change the port. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;5550&#x60;. Available since 2.17. |  [optional]
**serviceMqttEnabled** | **Boolean** | Enable or disable the MQTT service. When disabled new MQTT Clients may not connect through the per-VPN MQTT listen-ports, and all currently connected MQTT Clients are immediately disconnected. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;false&#x60;. Available since 2.17. |  [optional]
**serviceMsgBackboneEnabled** | **Boolean** | Enable or disable the msg-backbone service. When disabled new Clients may not connect through global or per-VPN listen-ports, and all currently connected Clients are immediately disconnected. The default value is &#x60;true&#x60;. Available since 2.17. |  [optional]
**serviceRestEventOutgoingConnectionCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**serviceRestIncomingEnabled** | **Boolean** | Enable or disable the REST service incoming connections on the router. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;false&#x60;. Available since 2.17. |  [optional]
**serviceRestOutgoingEnabled** | **Boolean** | Enable or disable the REST service outgoing connections on the router. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;false&#x60;. Available since 2.17. |  [optional]
**serviceSempLegacyTimeoutEnabled** | **Boolean** | Enable or disable extended SEMP timeouts for paged GETs. When a request times out, it returns the current page of content, even if the page is not full.  When enabled, the timeout is 60 seconds. When disabled, the timeout is 5 seconds.  The recommended setting is disabled (no legacy-timeout).  This parameter is intended as a temporary workaround to be used until SEMP clients can handle short pages.  This setting will be removed in a future release. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;false&#x60;. Available since 2.18. |  [optional]
**serviceSempPlainTextEnabled** | **Boolean** | Enable or disable plain-text SEMP service. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;true&#x60;. Available since 2.17. |  [optional]
**serviceSempPlainTextListenPort** | **Long** | The TCP port for plain-text SEMP client connections. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;80&#x60;. Available since 2.17. |  [optional]
**serviceSempSessionIdleTimeout** | **Integer** | The session idle timeout, in minutes. Sessions will be invalidated if there is no activity in this period of time. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;15&#x60;. Available since 2.21. |  [optional]
**serviceSempSessionMaxLifetime** | **Integer** | The maximum lifetime of a session, in minutes. Sessions will be invalidated after this period of time, regardless of activity. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;43200&#x60;. Available since 2.21. |  [optional]
**serviceSempTlsEnabled** | **Boolean** | Enable or disable TLS SEMP service. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;true&#x60;. Available since 2.17. |  [optional]
**serviceSempTlsListenPort** | **Long** | The TCP port for TLS SEMP client connections. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;1943&#x60;. Available since 2.17. |  [optional]
**serviceSmfCompressionListenPort** | **Long** | TCP port number that SMF clients can use to connect to the broker using raw compression TCP. The default value is &#x60;55003&#x60;. Available since 2.17. |  [optional]
**serviceSmfEnabled** | **Boolean** | Enable or disable the SMF service. When disabled new SMF Clients may not connect through the global listen-ports, and all currently connected SMF Clients are immediately disconnected. The default value is &#x60;true&#x60;. Available since 2.17. |  [optional]
**serviceSmfEventConnectionCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**serviceSmfPlainTextListenPort** | **Long** | TCP port number that SMF clients can use to connect to the broker using raw TCP. The default value is &#x60;55555&#x60;. Available since 2.17. |  [optional]
**serviceSmfRoutingControlListenPort** | **Long** | TCP port number that SMF clients can use to connect to the broker using raw routing control TCP. The default value is &#x60;55556&#x60;. Available since 2.17. |  [optional]
**serviceSmfTlsListenPort** | **Long** | TCP port number that SMF clients can use to connect to the broker using raw TCP over TLS. The default value is &#x60;55443&#x60;. Available since 2.17. |  [optional]
**serviceTlsEventConnectionCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**serviceWebTransportEnabled** | **Boolean** | Enable or disable the web-transport service. When disabled new web-transport Clients may not connect through the global listen-ports, and all currently connected web-transport Clients are immediately disconnected. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;false&#x60;. Available since 2.17. |  [optional]
**serviceWebTransportPlainTextListenPort** | **Long** | The TCP port for plain-text WEB client connections. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;80&#x60;. Available since 2.17. |  [optional]
**serviceWebTransportTlsListenPort** | **Long** | The TCP port for TLS WEB client connections. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;443&#x60;. Available since 2.17. |  [optional]
**serviceWebTransportWebUrlSuffix** | **String** | Used to specify the Web URL suffix that will be used by Web clients when communicating with the broker. The default value is &#x60;\&quot;\&quot;&#x60;. Available since 2.17. |  [optional]
**tlsBlockVersion10Enabled** | **Boolean** | Enable or disable the blocking of incoming TLS version 1.0 connections. When blocked, existing TLS 1.0 connections from Clients and SEMP users remain connected while new connections are blocked. Note that support for TLS 1.0 will eventually be discontinued, at which time TLS 1.0 connections will be blocked regardless of this setting. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;true&#x60;. |  [optional]
**tlsBlockVersion11Enabled** | **Boolean** | Enable or disable the blocking of TLS version 1.1 connections. When blocked, all existing incoming and outgoing TLS 1.1 connections with Clients, SEMP users, and LDAP servers remain connected while new connections are blocked. Note that support for TLS 1.1 will eventually be discontinued, at which time TLS 1.1 connections will be blocked regardless of this setting. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;false&#x60;. |  [optional]
**tlsCipherSuiteManagementList** | **String** | The colon-separated list of cipher suites used for TLS management connections (e.g. SEMP, LDAP). The value \&quot;default\&quot; implies all supported suites ordered from most secure to least secure. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;\&quot;default\&quot;&#x60;. |  [optional]
**tlsCipherSuiteMsgBackboneList** | **String** | The colon-separated list of cipher suites used for TLS data connections (e.g. client pub/sub). The value \&quot;default\&quot; implies all supported suites ordered from most secure to least secure. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;\&quot;default\&quot;&#x60;. |  [optional]
**tlsCipherSuiteSecureShellList** | **String** | The colon-separated list of cipher suites used for TLS secure shell connections (e.g. SSH, SFTP, SCP). The value \&quot;default\&quot; implies all supported suites ordered from most secure to least secure. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;\&quot;default\&quot;&#x60;. |  [optional]
**tlsCrimeExploitProtectionEnabled** | **Boolean** | Enable or disable protection against the CRIME exploit. When enabled, TLS+compressed messaging performance is degraded. This protection should only be disabled if sufficient ACL and authentication features are being employed such that a potential attacker does not have sufficient access to trigger the exploit. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;true&#x60;. |  [optional]
**tlsServerCertContent** | **String** | The PEM formatted content for the server certificate used for TLS connections. It must consist of a private key and between one and three certificates comprising the certificate trust chain. This attribute is absent from a GET and not updated when absent in a PUT, subject to the exceptions in note 4. Changing this attribute requires an HTTPS connection. The default value is &#x60;\&quot;\&quot;&#x60;. |  [optional]
**tlsServerCertPassword** | **String** | The password for the server certificate used for TLS connections. This attribute is absent from a GET and not updated when absent in a PUT, subject to the exceptions in note 4. Changing this attribute requires an HTTPS connection. The default value is &#x60;\&quot;\&quot;&#x60;. |  [optional]
**tlsStandardDomainCertificateAuthoritiesEnabled** | **Boolean** | Enable or disable the standard domain certificate authority list. The default value is &#x60;true&#x60;. Available since 2.19. |  [optional]
**tlsTicketLifetime** | **Integer** | The TLS ticket lifetime in seconds. When a client connects with TLS, a session with a session ticket is created using the TLS ticket lifetime which determines how long the client has to resume the session. Changes to this attribute are synchronized to HA mates via config-sync. The default value is &#x60;86400&#x60;. |  [optional]


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



