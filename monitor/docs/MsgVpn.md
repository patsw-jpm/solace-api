
# MsgVpn

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**alias** | **String** | The name of another Message VPN which this Message VPN is an alias for. Available since 2.14. |  [optional]
**authenticationBasicEnabled** | **Boolean** | Indicates whether basic authentication is enabled for clients connecting to the Message VPN. |  [optional]
**authenticationBasicProfileName** | **String** | The name of the RADIUS or LDAP Profile to use for basic authentication. |  [optional]
**authenticationBasicRadiusDomain** | **String** | The RADIUS domain to use for basic authentication. |  [optional]
**authenticationBasicType** | [**AuthenticationBasicTypeEnum**](#AuthenticationBasicTypeEnum) | The type of basic authentication to use for clients connecting to the Message VPN. The allowed values and their meaning are:  &lt;pre&gt; \&quot;internal\&quot; - Internal database. Authentication is against Client Usernames. \&quot;ldap\&quot; - LDAP authentication. An LDAP profile name must be provided. \&quot;radius\&quot; - RADIUS authentication. A RADIUS profile name must be provided. \&quot;none\&quot; - No authentication. Anonymous login allowed. &lt;/pre&gt;  |  [optional]
**authenticationClientCertAllowApiProvidedUsernameEnabled** | **Boolean** | Indicates whether a client is allowed to specify a Client Username via the API connect method. When disabled, the certificate CN (Common Name) is always used. |  [optional]
**authenticationClientCertEnabled** | **Boolean** | Indicates whether client certificate authentication is enabled in the Message VPN. |  [optional]
**authenticationClientCertMaxChainDepth** | **Long** | The maximum depth for a client certificate chain. The depth of a chain is defined as the number of signing CA certificates that are present in the chain back to a trusted self-signed root CA certificate. |  [optional]
**authenticationClientCertRevocationCheckMode** | [**AuthenticationClientCertRevocationCheckModeEnum**](#AuthenticationClientCertRevocationCheckModeEnum) | The desired behavior for client certificate revocation checking. The allowed values and their meaning are:  &lt;pre&gt; \&quot;allow-all\&quot; - Allow the client to authenticate, the result of client certificate revocation check is ignored. \&quot;allow-unknown\&quot; - Allow the client to authenticate even if the revocation status of his certificate cannot be determined. \&quot;allow-valid\&quot; - Allow the client to authenticate only when the revocation check returned an explicit positive response. &lt;/pre&gt;  |  [optional]
**authenticationClientCertUsernameSource** | [**AuthenticationClientCertUsernameSourceEnum**](#AuthenticationClientCertUsernameSourceEnum) | The field from the client certificate to use as the client username. The allowed values and their meaning are:  &lt;pre&gt; \&quot;certificate-thumbprint\&quot; - The username is computed as the SHA-1 hash over the entire DER-encoded contents of the client certificate. \&quot;common-name\&quot; - The username is extracted from the certificate&#39;s first instance of the Common Name attribute in the Subject DN. \&quot;common-name-last\&quot; - The username is extracted from the certificate&#39;s last instance of the Common Name attribute in the Subject DN. \&quot;subject-alternate-name-msupn\&quot; - The username is extracted from the certificate&#39;s Other Name type of the Subject Alternative Name and must have the msUPN signature. \&quot;uid\&quot; - The username is extracted from the certificate&#39;s first instance of the User Identifier attribute in the Subject DN. \&quot;uid-last\&quot; - The username is extracted from the certificate&#39;s last instance of the User Identifier attribute in the Subject DN. &lt;/pre&gt;  |  [optional]
**authenticationClientCertValidateDateEnabled** | **Boolean** | Indicates whether the \&quot;Not Before\&quot; and \&quot;Not After\&quot; validity dates in the client certificate are checked. |  [optional]
**authenticationKerberosAllowApiProvidedUsernameEnabled** | **Boolean** | Indicates whether a client is allowed to specify a Client Username via the API connect method. When disabled, the Kerberos Principal name is always used. |  [optional]
**authenticationKerberosEnabled** | **Boolean** | Indicates whether Kerberos authentication is enabled in the Message VPN. |  [optional]
**authenticationOauthDefaultProviderName** | **String** | The name of the provider to use when the client does not supply a provider name. Available since 2.13. |  [optional]
**authenticationOauthEnabled** | **Boolean** | Indicates whether OAuth authentication is enabled. Available since 2.13. |  [optional]
**authorizationLdapGroupMembershipAttributeName** | **String** | The name of the attribute that is retrieved from the LDAP server as part of the LDAP search when authorizing a client connecting to the Message VPN. |  [optional]
**authorizationLdapTrimClientUsernameDomainEnabled** | **Boolean** | Indicates whether client-username domain trimming for LDAP lookups of client connections is enabled. Available since 2.13. |  [optional]
**authorizationProfileName** | **String** | The name of the LDAP Profile to use for client authorization. |  [optional]
**authorizationType** | [**AuthorizationTypeEnum**](#AuthorizationTypeEnum) | The type of authorization to use for clients connecting to the Message VPN. The allowed values and their meaning are:  &lt;pre&gt; \&quot;ldap\&quot; - LDAP authorization. \&quot;internal\&quot; - Internal authorization. &lt;/pre&gt;  |  [optional]
**averageRxByteRate** | **Long** | The one minute average of the message rate received by the Message VPN, in bytes per second (B/sec). Available since 2.13. |  [optional]
**averageRxCompressedByteRate** | **Long** | The one minute average of the compressed message rate received by the Message VPN, in bytes per second (B/sec). Available since 2.12. |  [optional]
**averageRxMsgRate** | **Long** | The one minute average of the message rate received by the Message VPN, in messages per second (msg/sec). Available since 2.13. |  [optional]
**averageRxUncompressedByteRate** | **Long** | The one minute average of the uncompressed message rate received by the Message VPN, in bytes per second (B/sec). Available since 2.12. |  [optional]
**averageTxByteRate** | **Long** | The one minute average of the message rate transmitted by the Message VPN, in bytes per second (B/sec). Available since 2.13. |  [optional]
**averageTxCompressedByteRate** | **Long** | The one minute average of the compressed message rate transmitted by the Message VPN, in bytes per second (B/sec). Available since 2.12. |  [optional]
**averageTxMsgRate** | **Long** | The one minute average of the message rate transmitted by the Message VPN, in messages per second (msg/sec). Available since 2.13. |  [optional]
**averageTxUncompressedByteRate** | **Long** | The one minute average of the uncompressed message rate transmitted by the Message VPN, in bytes per second (B/sec). Available since 2.12. |  [optional]
**bridgingTlsServerCertEnforceTrustedCommonNameEnabled** | **Boolean** | Indicates whether the Common Name (CN) in the server certificate from the remote broker is validated for the Bridge. Deprecated since 2.18. Common Name validation has been replaced by Server Certificate Name validation. |  [optional]
**bridgingTlsServerCertMaxChainDepth** | **Long** | The maximum depth for a server certificate chain. The depth of a chain is defined as the number of signing CA certificates that are present in the chain back to a trusted self-signed root CA certificate. |  [optional]
**bridgingTlsServerCertValidateDateEnabled** | **Boolean** | Indicates whether the \&quot;Not Before\&quot; and \&quot;Not After\&quot; validity dates in the server certificate are checked. |  [optional]
**bridgingTlsServerCertValidateNameEnabled** | **Boolean** | Enable or disable the standard TLS authentication mechanism of verifying the name used to connect to the bridge. If enabled, the name used to connect to the bridge is checked against the names specified in the certificate returned by the remote router. Legacy Common Name validation is not performed if Server Certificate Name Validation is enabled, even if Common Name validation is also enabled. Available since 2.18. |  [optional]
**configSyncLocalKey** | **String** | The key for the config sync table of the local Message VPN. Deprecated since 2.22. This attribute has been deprecated. |  [optional]
**configSyncLocalLastResult** | **String** | The result of the last operation on the config sync table of the local Message VPN. Deprecated since 2.22. This attribute has been replaced by &#39;lastResult&#39; in the ConfigSyncLocalDatabaseRow object. |  [optional]
**configSyncLocalRole** | **String** | The role of the config sync table of the local Message VPN. The allowed values and their meaning are:  &lt;pre&gt; \&quot;unknown\&quot; - The role is unknown. \&quot;primary\&quot; - Acts as the primary source of config data. \&quot;replica\&quot; - Acts as a replica of the primary config data. &lt;/pre&gt;  Deprecated since 2.22. This attribute has been replaced by &#39;role&#39; in the ConfigSyncLocalDatabaseRow object. |  [optional]
**configSyncLocalState** | **String** | The state of the config sync table of the local Message VPN. The allowed values and their meaning are:  &lt;pre&gt; \&quot;unknown\&quot; - The state is unknown. \&quot;in-sync\&quot; - The config data is synchronized between Message VPNs. \&quot;reconciling\&quot; - The config data is reconciling between Message VPNs. \&quot;blocked\&quot; - The config data is blocked from reconciling due to an error. \&quot;out-of-sync\&quot; - The config data is out of sync between Message VPNs. \&quot;down\&quot; - The state is down due to configuration. &lt;/pre&gt;  Deprecated since 2.22. This attribute has been replaced by &#39;syncStatus&#39; in the ConfigSyncLocalDatabaseRow object. |  [optional]
**configSyncLocalTimeInState** | **Integer** | The amount of time in seconds the config sync table of the local Message VPN has been in the current state. Deprecated since 2.22. This attribute has been replaced by &#39;timeInState&#39; in the ConfigSyncLocalDatabaseRow object. |  [optional]
**controlRxByteCount** | **Long** | The amount of client control messages received from clients by the Message VPN, in bytes (B). Available since 2.13. |  [optional]
**controlRxMsgCount** | **Long** | The number of client control messages received from clients by the Message VPN. Available since 2.13. |  [optional]
**controlTxByteCount** | **Long** | The amount of client control messages transmitted to clients by the Message VPN, in bytes (B). Available since 2.13. |  [optional]
**controlTxMsgCount** | **Long** | The number of client control messages transmitted to clients by the Message VPN. Available since 2.13. |  [optional]
**counter** | [**MsgVpnCounter**](MsgVpnCounter.md) |  |  [optional]
**dataRxByteCount** | **Long** | The amount of client data messages received from clients by the Message VPN, in bytes (B). Available since 2.13. |  [optional]
**dataRxMsgCount** | **Long** | The number of client data messages received from clients by the Message VPN. Available since 2.13. |  [optional]
**dataTxByteCount** | **Long** | The amount of client data messages transmitted to clients by the Message VPN, in bytes (B). Available since 2.13. |  [optional]
**dataTxMsgCount** | **Long** | The number of client data messages transmitted to clients by the Message VPN. Available since 2.13. |  [optional]
**discardedRxMsgCount** | **Long** | The number of messages discarded during reception by the Message VPN. Available since 2.13. |  [optional]
**discardedTxMsgCount** | **Long** | The number of messages discarded during transmission by the Message VPN. Available since 2.13. |  [optional]
**distributedCacheManagementEnabled** | **Boolean** | Indicates whether managing of cache instances over the message bus is enabled in the Message VPN. |  [optional]
**dmrEnabled** | **Boolean** | Indicates whether Dynamic Message Routing (DMR) is enabled for the Message VPN. |  [optional]
**enabled** | **Boolean** | Indicates whether the Message VPN is enabled. |  [optional]
**eventConnectionCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventEgressFlowCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventEgressMsgRateThreshold** | [**EventThresholdByValue**](EventThresholdByValue.md) |  |  [optional]
**eventEndpointCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventIngressFlowCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventIngressMsgRateThreshold** | [**EventThresholdByValue**](EventThresholdByValue.md) |  |  [optional]
**eventLargeMsgThreshold** | **Long** | Exceeding this message size in kilobytes (KB) triggers a corresponding Event in the Message VPN. |  [optional]
**eventLogTag** | **String** | The value of the prefix applied to all published Events in the Message VPN. |  [optional]
**eventMsgSpoolUsageThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventPublishClientEnabled** | **Boolean** | Indicates whether client Events are published in the Message VPN. |  [optional]
**eventPublishMsgVpnEnabled** | **Boolean** | Indicates whether Message VPN Events are published in the Message VPN. |  [optional]
**eventPublishSubscriptionMode** | [**EventPublishSubscriptionModeEnum**](#EventPublishSubscriptionModeEnum) | The mode of subscription Events published in the Message VPN. The allowed values and their meaning are:  &lt;pre&gt; \&quot;off\&quot; - Disable client level event message publishing. \&quot;on-with-format-v1\&quot; - Enable client level event message publishing with format v1. \&quot;on-with-no-unsubscribe-events-on-disconnect-format-v1\&quot; - As \&quot;on-with-format-v1\&quot;, but unsubscribe events are not generated when a client disconnects. Unsubscribe events are still raised when a client explicitly unsubscribes from its subscriptions. \&quot;on-with-format-v2\&quot; - Enable client level event message publishing with format v2. \&quot;on-with-no-unsubscribe-events-on-disconnect-format-v2\&quot; - As \&quot;on-with-format-v2\&quot;, but unsubscribe events are not generated when a client disconnects. Unsubscribe events are still raised when a client explicitly unsubscribes from its subscriptions. &lt;/pre&gt;  |  [optional]
**eventPublishTopicFormatMqttEnabled** | **Boolean** | Indicates whether Message VPN Events are published in the MQTT format. |  [optional]
**eventPublishTopicFormatSmfEnabled** | **Boolean** | Indicates whether Message VPN Events are published in the SMF format. |  [optional]
**eventServiceAmqpConnectionCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventServiceMqttConnectionCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventServiceRestIncomingConnectionCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventServiceSmfConnectionCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventServiceWebConnectionCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventSubscriptionCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventTransactedSessionCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**eventTransactionCountThreshold** | [**EventThreshold**](EventThreshold.md) |  |  [optional]
**exportSubscriptionsEnabled** | **Boolean** | Indicates whether exports of subscriptions to other routers in the network over neighbour links is enabled in the Message VPN. |  [optional]
**failureReason** | **String** | The reason for the Message VPN failure. |  [optional]
**jndiEnabled** | **Boolean** | Indicates whether the JNDI access for clients is enabled in the Message VPN. |  [optional]
**loginRxMsgCount** | **Long** | The number of login request messages received by the Message VPN. Available since 2.13. |  [optional]
**loginTxMsgCount** | **Long** | The number of login response messages transmitted by the Message VPN. Available since 2.13. |  [optional]
**maxConnectionCount** | **Long** | The maximum number of client connections to the Message VPN. |  [optional]
**maxEffectiveEndpointCount** | **Integer** | The effective maximum number of Queues and Topic Endpoints allowed in the Message VPN. |  [optional]
**maxEffectiveRxFlowCount** | **Integer** | The effective maximum number of receive flows allowed in the Message VPN. |  [optional]
**maxEffectiveSubscriptionCount** | **Long** | The effective maximum number of subscriptions allowed in the Message VPN. |  [optional]
**maxEffectiveTransactedSessionCount** | **Integer** | The effective maximum number of transacted sessions allowed in the Message VPN. |  [optional]
**maxEffectiveTransactionCount** | **Integer** | The effective maximum number of transactions allowed in the Message VPN. |  [optional]
**maxEffectiveTxFlowCount** | **Integer** | The effective maximum number of transmit flows allowed in the Message VPN. |  [optional]
**maxEgressFlowCount** | **Long** | The maximum number of transmit flows that can be created in the Message VPN. |  [optional]
**maxEndpointCount** | **Long** | The maximum number of Queues and Topic Endpoints that can be created in the Message VPN. |  [optional]
**maxIngressFlowCount** | **Long** | The maximum number of receive flows that can be created in the Message VPN. |  [optional]
**maxMsgSpoolUsage** | **Long** | The maximum message spool usage by the Message VPN, in megabytes. |  [optional]
**maxSubscriptionCount** | **Long** | The maximum number of local client subscriptions that can be added to the Message VPN. This limit is not enforced when a subscription is added using a management interface, such as CLI or SEMP. |  [optional]
**maxTransactedSessionCount** | **Long** | The maximum number of transacted sessions that can be created in the Message VPN. |  [optional]
**maxTransactionCount** | **Long** | The maximum number of transactions that can be created in the Message VPN. |  [optional]
**mqttRetainMaxMemory** | **Integer** | The maximum total memory usage of the MQTT Retain feature for this Message VPN, in MB. If the maximum memory is reached, any arriving retain messages that require more memory are discarded. A value of -1 indicates that the memory is bounded only by the global max memory limit. A value of 0 prevents MQTT Retain from becoming operational. |  [optional]
**msgReplayActiveCount** | **Integer** | The number of message replays that are currently active in the Message VPN. |  [optional]
**msgReplayFailedCount** | **Integer** | The number of message replays that are currently failed in the Message VPN. |  [optional]
**msgReplayInitializingCount** | **Integer** | The number of message replays that are currently initializing in the Message VPN. |  [optional]
**msgReplayPendingCompleteCount** | **Integer** | The number of message replays that are pending complete in the Message VPN. |  [optional]
**msgSpoolMsgCount** | **Long** | The current number of messages spooled (persisted in the Message Spool) in the Message VPN. Available since 2.14. |  [optional]
**msgSpoolRxMsgCount** | **Long** | The number of guaranteed messages received by the Message VPN. Available since 2.13. |  [optional]
**msgSpoolTxMsgCount** | **Long** | The number of guaranteed messages transmitted by the Message VPN. One message to multiple clients is counted as one message. Available since 2.13. |  [optional]
**msgSpoolUsage** | **Long** | The current message spool usage by the Message VPN, in bytes (B). |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**preferIpVersion** | [**PreferIpVersionEnum**](#PreferIpVersionEnum) | IP version to use if DNS lookup contains both an IPv4 and IPv6 address. The allowed values and their meaning are:  &lt;pre&gt; \&quot;ipv4\&quot; - Use IPv4 address when DNS lookup contains both an IPv4 and IPv6 address. \&quot;ipv6\&quot; - Use IPv6 address when DNS lookup contains both an IPv4 and IPv6 address. &lt;/pre&gt;  |  [optional]
**rate** | [**MsgVpnRate**](MsgVpnRate.md) |  |  [optional]
**replicationAckPropagationIntervalMsgCount** | **Long** | The acknowledgement (ACK) propagation interval for the replication Bridge, in number of replicated messages. Available since 2.12. |  [optional]
**replicationActiveAckPropTxMsgCount** | **Long** | The number of acknowledgement messages propagated to the replication standby remote Message VPN. Available since 2.12. |  [optional]
**replicationActiveAsyncQueuedMsgCount** | **Long** | The number of async messages queued to the replication standby remote Message VPN. Available since 2.12. |  [optional]
**replicationActiveLocallyConsumedMsgCount** | **Long** | The number of messages consumed in the replication active local Message VPN. Available since 2.12. |  [optional]
**replicationActiveMateFlowCongestedPeakTime** | **Integer** | The peak amount of time in seconds the message flow has been congested to the replication standby remote Message VPN. Available since 2.12. |  [optional]
**replicationActiveMateFlowNotCongestedPeakTime** | **Integer** | The peak amount of time in seconds the message flow has not been congested to the replication standby remote Message VPN. Available since 2.12. |  [optional]
**replicationActivePromotedQueuedMsgCount** | **Long** | The number of promoted messages queued to the replication standby remote Message VPN. Available since 2.12. |  [optional]
**replicationActiveReconcileRequestRxMsgCount** | **Long** | The number of reconcile request messages received from the replication standby remote Message VPN. Available since 2.12. |  [optional]
**replicationActiveSyncEligiblePeakTime** | **Integer** | The peak amount of time in seconds sync replication has been eligible to the replication standby remote Message VPN. Available since 2.12. |  [optional]
**replicationActiveSyncIneligiblePeakTime** | **Integer** | The peak amount of time in seconds sync replication has been ineligible to the replication standby remote Message VPN. Available since 2.12. |  [optional]
**replicationActiveSyncQueuedAsAsyncMsgCount** | **Long** | The number of sync messages queued as async to the replication standby remote Message VPN. Available since 2.12. |  [optional]
**replicationActiveSyncQueuedMsgCount** | **Long** | The number of sync messages queued to the replication standby remote Message VPN. Available since 2.12. |  [optional]
**replicationActiveTransitionToSyncIneligibleCount** | **Long** | The number of sync replication ineligible transitions to the replication standby remote Message VPN. Available since 2.12. |  [optional]
**replicationBridgeAuthenticationBasicClientUsername** | **String** | The Client Username the replication Bridge uses to login to the remote Message VPN. Available since 2.12. |  [optional]
**replicationBridgeAuthenticationScheme** | [**ReplicationBridgeAuthenticationSchemeEnum**](#ReplicationBridgeAuthenticationSchemeEnum) | The authentication scheme for the replication Bridge in the Message VPN. The allowed values and their meaning are:  &lt;pre&gt; \&quot;basic\&quot; - Basic Authentication Scheme (via username and password). \&quot;client-certificate\&quot; - Client Certificate Authentication Scheme (via certificate file or content). &lt;/pre&gt;  Available since 2.12. |  [optional]
**replicationBridgeBoundToQueue** | **Boolean** | Indicates whether the local replication Bridge is bound to the Queue in the remote Message VPN. Available since 2.12. |  [optional]
**replicationBridgeCompressedDataEnabled** | **Boolean** | Indicates whether compression is used for the replication Bridge. Available since 2.12. |  [optional]
**replicationBridgeEgressFlowWindowSize** | **Long** | The size of the window used for guaranteed messages published to the replication Bridge, in messages. Available since 2.12. |  [optional]
**replicationBridgeName** | **String** | The name of the local replication Bridge in the Message VPN. Available since 2.12. |  [optional]
**replicationBridgeRetryDelay** | **Long** | The number of seconds that must pass before retrying the replication Bridge connection. Available since 2.12. |  [optional]
**replicationBridgeTlsEnabled** | **Boolean** | Indicates whether encryption (TLS) is enabled for the replication Bridge connection. Available since 2.12. |  [optional]
**replicationBridgeUnidirectionalClientProfileName** | **String** | The Client Profile for the unidirectional replication Bridge in the Message VPN. It is used only for the TCP parameters. Available since 2.12. |  [optional]
**replicationBridgeUp** | **Boolean** | Indicates whether the local replication Bridge is operationally up in the Message VPN. Available since 2.12. |  [optional]
**replicationEnabled** | **Boolean** | Indicates whether replication is enabled for the Message VPN. Available since 2.12. |  [optional]
**replicationQueueBound** | **Boolean** | Indicates whether the remote replication Bridge is bound to the Queue in the Message VPN. Available since 2.12. |  [optional]
**replicationQueueMaxMsgSpoolUsage** | **Long** | The maximum message spool usage by the replication Bridge local Queue (quota), in megabytes. Available since 2.12. |  [optional]
**replicationQueueRejectMsgToSenderOnDiscardEnabled** | **Boolean** | Indicates whether messages discarded on this replication Bridge Queue are rejected back to the sender. Available since 2.12. |  [optional]
**replicationRejectMsgWhenSyncIneligibleEnabled** | **Boolean** | Indicates whether guaranteed messages published to synchronously replicated Topics are rejected back to the sender when synchronous replication becomes ineligible. Available since 2.12. |  [optional]
**replicationRemoteBridgeName** | **String** | The name of the remote replication Bridge in the Message VPN. Available since 2.12. |  [optional]
**replicationRemoteBridgeUp** | **Boolean** | Indicates whether the remote replication Bridge is operationally up in the Message VPN. Available since 2.12. |  [optional]
**replicationRole** | [**ReplicationRoleEnum**](#ReplicationRoleEnum) | The replication role for the Message VPN. The allowed values and their meaning are:  &lt;pre&gt; \&quot;active\&quot; - Assume the Active role in replication for the Message VPN. \&quot;standby\&quot; - Assume the Standby role in replication for the Message VPN. &lt;/pre&gt;  Available since 2.12. |  [optional]
**replicationStandbyAckPropOutOfSeqRxMsgCount** | **Long** | The number of acknowledgement messages received out of sequence from the replication active remote Message VPN. Available since 2.12. |  [optional]
**replicationStandbyAckPropRxMsgCount** | **Long** | The number of acknowledgement messages received from the replication active remote Message VPN. Available since 2.12. |  [optional]
**replicationStandbyReconcileRequestTxMsgCount** | **Long** | The number of reconcile request messages transmitted to the replication active remote Message VPN. Available since 2.12. |  [optional]
**replicationStandbyRxMsgCount** | **Long** | The number of messages received from the replication active remote Message VPN. Available since 2.12. |  [optional]
**replicationStandbyTransactionRequestCount** | **Long** | The number of transaction requests received from the replication active remote Message VPN. Available since 2.12. |  [optional]
**replicationStandbyTransactionRequestFailureCount** | **Long** | The number of transaction requests received from the replication active remote Message VPN that failed. Available since 2.12. |  [optional]
**replicationStandbyTransactionRequestSuccessCount** | **Long** | The number of transaction requests received from the replication active remote Message VPN that succeeded. Available since 2.12. |  [optional]
**replicationSyncEligible** | **Boolean** | Indicates whether sync replication is eligible in the Message VPN. Available since 2.12. |  [optional]
**replicationTransactionMode** | [**ReplicationTransactionModeEnum**](#ReplicationTransactionModeEnum) | Indicates whether synchronous or asynchronous replication mode is used for all transactions within the Message VPN. The allowed values and their meaning are:  &lt;pre&gt; \&quot;sync\&quot; - Messages are acknowledged when replicated (spooled remotely). \&quot;async\&quot; - Messages are acknowledged when pending replication (spooled locally). &lt;/pre&gt;  Available since 2.12. |  [optional]
**restTlsServerCertEnforceTrustedCommonNameEnabled** | **Boolean** | Indicates whether the Common Name (CN) in the server certificate from the remote REST Consumer is validated. Deprecated since 2.17. Common Name validation has been replaced by Server Certificate Name validation. |  [optional]
**restTlsServerCertMaxChainDepth** | **Long** | The maximum depth for a REST Consumer server certificate chain. The depth of a chain is defined as the number of signing CA certificates that are present in the chain back to a trusted self-signed root CA certificate. |  [optional]
**restTlsServerCertValidateDateEnabled** | **Boolean** | Indicates whether the \&quot;Not Before\&quot; and \&quot;Not After\&quot; validity dates in the REST Consumer server certificate are checked. |  [optional]
**restTlsServerCertValidateNameEnabled** | **Boolean** | Enable or disable the standard TLS authentication mechanism of verifying the name used to connect to the remote REST Consumer. If enabled, the name used to connect to the remote REST Consumer is checked against the names specified in the certificate returned by the remote router. Legacy Common Name validation is not performed if Server Certificate Name Validation is enabled, even if Common Name validation is also enabled. Available since 2.17. |  [optional]
**rxByteCount** | **Long** | The amount of messages received from clients by the Message VPN, in bytes (B). Available since 2.12. |  [optional]
**rxByteRate** | **Long** | The current message rate received by the Message VPN, in bytes per second (B/sec). Available since 2.13. |  [optional]
**rxCompressedByteCount** | **Long** | The amount of compressed messages received by the Message VPN, in bytes (B). Available since 2.12. |  [optional]
**rxCompressedByteRate** | **Long** | The current compressed message rate received by the Message VPN, in bytes per second (B/sec). Available since 2.12. |  [optional]
**rxCompressionRatio** | **String** | The compression ratio for messages received by the message VPN. Available since 2.12. |  [optional]
**rxMsgCount** | **Long** | The number of messages received from clients by the Message VPN. Available since 2.12. |  [optional]
**rxMsgRate** | **Long** | The current message rate received by the Message VPN, in messages per second (msg/sec). Available since 2.13. |  [optional]
**rxUncompressedByteCount** | **Long** | The amount of uncompressed messages received by the Message VPN, in bytes (B). Available since 2.12. |  [optional]
**rxUncompressedByteRate** | **Long** | The current uncompressed message rate received by the Message VPN, in bytes per second (B/sec). Available since 2.12. |  [optional]
**sempOverMsgBusAdminClientEnabled** | **Boolean** | Indicates whether the \&quot;admin\&quot; level \&quot;client\&quot; commands are enabled for SEMP over the message bus in the Message VPN. |  [optional]
**sempOverMsgBusAdminDistributedCacheEnabled** | **Boolean** | Indicates whether the \&quot;admin\&quot; level \&quot;Distributed Cache\&quot; commands are enabled for SEMP over the message bus in the Message VPN. |  [optional]
**sempOverMsgBusAdminEnabled** | **Boolean** | Indicates whether the \&quot;admin\&quot; level commands are enabled for SEMP over the message bus in the Message VPN. |  [optional]
**sempOverMsgBusEnabled** | **Boolean** | Indicates whether SEMP over the message bus is enabled in the Message VPN. |  [optional]
**sempOverMsgBusLegacyShowClearEnabled** | **Boolean** | Indicates whether \&quot;legacy-show-clear\&quot; SEMP over the message bus commands (that is, SEMP show and administration requests published to the topic \&quot;#P2P/[router name]/#client/SEMP\&quot;) are enabled for the current Message VPN. |  [optional]
**sempOverMsgBusShowEnabled** | **Boolean** | Indicates whether the \&quot;show\&quot; level commands are enabled for SEMP over the message bus in the Message VPN. |  [optional]
**serviceAmqpMaxConnectionCount** | **Long** | The maximum number of AMQP client connections that can be simultaneously connected to the Message VPN. This value may be higher than supported by the platform. |  [optional]
**serviceAmqpPlainTextCompressed** | **Boolean** | Indicates whether the AMQP Service is compressed in the Message VPN. |  [optional]
**serviceAmqpPlainTextEnabled** | **Boolean** | Indicates whether the AMQP Service is enabled in the Message VPN. |  [optional]
**serviceAmqpPlainTextFailureReason** | **String** | The reason for the AMQP Service failure in the Message VPN. |  [optional]
**serviceAmqpPlainTextListenPort** | **Long** | The port number for plain-text AMQP clients that connect to the Message VPN. The port must be unique across the message backbone. A value of 0 means that the listen-port is unassigned and cannot be enabled. |  [optional]
**serviceAmqpPlainTextUp** | **Boolean** | Indicates whether the AMQP Service is operationally up in the Message VPN. |  [optional]
**serviceAmqpTlsCompressed** | **Boolean** | Indicates whether the TLS related AMQP Service is compressed in the Message VPN. |  [optional]
**serviceAmqpTlsEnabled** | **Boolean** | Indicates whether encryption (TLS) is enabled for AMQP clients in the Message VPN. |  [optional]
**serviceAmqpTlsFailureReason** | **String** | The reason for the TLS related AMQP Service failure in the Message VPN. |  [optional]
**serviceAmqpTlsListenPort** | **Long** | The port number for AMQP clients that connect to the Message VPN over TLS. The port must be unique across the message backbone. A value of 0 means that the listen-port is unassigned and cannot be enabled. |  [optional]
**serviceAmqpTlsUp** | **Boolean** | Indicates whether the TLS related AMQP Service is operationally up in the Message VPN. |  [optional]
**serviceMqttAuthenticationClientCertRequest** | [**ServiceMqttAuthenticationClientCertRequestEnum**](#ServiceMqttAuthenticationClientCertRequestEnum) | Determines when to request a client certificate from an incoming MQTT client connecting via a TLS port. The allowed values and their meaning are:  &lt;pre&gt; \&quot;always\&quot; - Always ask for a client certificate regardless of the \&quot;message-vpn &gt; authentication &gt; client-certificate &gt; shutdown\&quot; configuration. \&quot;never\&quot; - Never ask for a client certificate regardless of the \&quot;message-vpn &gt; authentication &gt; client-certificate &gt; shutdown\&quot; configuration. \&quot;when-enabled-in-message-vpn\&quot; - Only ask for a client-certificate if client certificate authentication is enabled under \&quot;message-vpn &gt;  authentication &gt; client-certificate &gt; shutdown\&quot;. &lt;/pre&gt;  Available since 2.21. |  [optional]
**serviceMqttMaxConnectionCount** | **Long** | The maximum number of MQTT client connections that can be simultaneously connected to the Message VPN. |  [optional]
**serviceMqttPlainTextCompressed** | **Boolean** | Indicates whether the MQTT Service is compressed in the Message VPN. |  [optional]
**serviceMqttPlainTextEnabled** | **Boolean** | Indicates whether the MQTT Service is enabled in the Message VPN. |  [optional]
**serviceMqttPlainTextFailureReason** | **String** | The reason for the MQTT Service failure in the Message VPN. |  [optional]
**serviceMqttPlainTextListenPort** | **Long** | The port number for plain-text MQTT clients that connect to the Message VPN. The port must be unique across the message backbone. A value of 0 means that the listen-port is unassigned and cannot be enabled. |  [optional]
**serviceMqttPlainTextUp** | **Boolean** | Indicates whether the MQTT Service is operationally up in the Message VPN. |  [optional]
**serviceMqttTlsCompressed** | **Boolean** | Indicates whether the TLS related MQTT Service is compressed in the Message VPN. |  [optional]
**serviceMqttTlsEnabled** | **Boolean** | Indicates whether encryption (TLS) is enabled for MQTT clients in the Message VPN. |  [optional]
**serviceMqttTlsFailureReason** | **String** | The reason for the TLS related MQTT Service failure in the Message VPN. |  [optional]
**serviceMqttTlsListenPort** | **Long** | The port number for MQTT clients that connect to the Message VPN over TLS. The port must be unique across the message backbone. A value of 0 means that the listen-port is unassigned and cannot be enabled. |  [optional]
**serviceMqttTlsUp** | **Boolean** | Indicates whether the TLS related MQTT Service is operationally up in the Message VPN. |  [optional]
**serviceMqttTlsWebSocketCompressed** | **Boolean** | Indicates whether the TLS related Web transport MQTT Service is compressed in the Message VPN. |  [optional]
**serviceMqttTlsWebSocketEnabled** | **Boolean** | Indicates whether encryption (TLS) is enabled for MQTT Web clients in the Message VPN. |  [optional]
**serviceMqttTlsWebSocketFailureReason** | **String** | The reason for the TLS related Web transport MQTT Service failure in the Message VPN. |  [optional]
**serviceMqttTlsWebSocketListenPort** | **Long** | The port number for MQTT clients that connect to the Message VPN using WebSocket over TLS. The port must be unique across the message backbone. A value of 0 means that the listen-port is unassigned and cannot be enabled. |  [optional]
**serviceMqttTlsWebSocketUp** | **Boolean** | Indicates whether the TLS related Web transport MQTT Service is operationally up in the Message VPN. |  [optional]
**serviceMqttWebSocketCompressed** | **Boolean** | Indicates whether the Web transport related MQTT Service is compressed in the Message VPN. |  [optional]
**serviceMqttWebSocketEnabled** | **Boolean** | Indicates whether the Web transport for the SMF Service is enabled in the Message VPN. |  [optional]
**serviceMqttWebSocketFailureReason** | **String** | The reason for the Web transport related MQTT Service failure in the Message VPN. |  [optional]
**serviceMqttWebSocketListenPort** | **Long** | The port number for plain-text MQTT clients that connect to the Message VPN using WebSocket. The port must be unique across the message backbone. A value of 0 means that the listen-port is unassigned and cannot be enabled. |  [optional]
**serviceMqttWebSocketUp** | **Boolean** | Indicates whether the Web transport related MQTT Service is operationally up in the Message VPN. |  [optional]
**serviceRestIncomingAuthenticationClientCertRequest** | [**ServiceRestIncomingAuthenticationClientCertRequestEnum**](#ServiceRestIncomingAuthenticationClientCertRequestEnum) | Determines when to request a client certificate from an incoming REST Producer connecting via a TLS port. The allowed values and their meaning are:  &lt;pre&gt; \&quot;always\&quot; - Always ask for a client certificate regardless of the \&quot;message-vpn &gt; authentication &gt; client-certificate &gt; shutdown\&quot; configuration. \&quot;never\&quot; - Never ask for a client certificate regardless of the \&quot;message-vpn &gt; authentication &gt; client-certificate &gt; shutdown\&quot; configuration. \&quot;when-enabled-in-message-vpn\&quot; - Only ask for a client-certificate if client certificate authentication is enabled under \&quot;message-vpn &gt;  authentication &gt; client-certificate &gt; shutdown\&quot;. &lt;/pre&gt;  Available since 2.21. |  [optional]
**serviceRestIncomingAuthorizationHeaderHandling** | [**ServiceRestIncomingAuthorizationHeaderHandlingEnum**](#ServiceRestIncomingAuthorizationHeaderHandlingEnum) | The handling of Authorization headers for incoming REST connections. The allowed values and their meaning are:  &lt;pre&gt; \&quot;drop\&quot; - Do not attach the Authorization header to the message as a user property. This configuration is most secure. \&quot;forward\&quot; - Forward the Authorization header, attaching it to the message as a user property in the same way as other headers. For best security, use the drop setting. \&quot;legacy\&quot; - If the Authorization header was used for authentication to the broker, do not attach it to the message. If the Authorization header was not used for authentication to the broker, attach it to the message as a user property in the same way as other headers. For best security, use the drop setting. &lt;/pre&gt;  Available since 2.19. |  [optional]
**serviceRestIncomingMaxConnectionCount** | **Long** | The maximum number of REST incoming client connections that can be simultaneously connected to the Message VPN. This value may be higher than supported by the platform. |  [optional]
**serviceRestIncomingPlainTextCompressed** | **Boolean** | Indicates whether the incoming REST Service is compressed in the Message VPN. |  [optional]
**serviceRestIncomingPlainTextEnabled** | **Boolean** | Indicates whether the REST Service is enabled in the Message VPN for incoming clients. |  [optional]
**serviceRestIncomingPlainTextFailureReason** | **String** | The reason for the incoming REST Service failure in the Message VPN. |  [optional]
**serviceRestIncomingPlainTextListenPort** | **Long** | The port number for incoming plain-text REST clients that connect to the Message VPN. The port must be unique across the message backbone. A value of 0 means that the listen-port is unassigned and cannot be enabled. |  [optional]
**serviceRestIncomingPlainTextUp** | **Boolean** | Indicates whether the incoming REST Service is operationally up in the Message VPN. |  [optional]
**serviceRestIncomingTlsCompressed** | **Boolean** | Indicates whether the TLS related incoming REST Service is compressed in the Message VPN. |  [optional]
**serviceRestIncomingTlsEnabled** | **Boolean** | Indicates whether encryption (TLS) is enabled for incoming REST clients in the Message VPN. |  [optional]
**serviceRestIncomingTlsFailureReason** | **String** | The reason for the TLS related incoming REST Service failure in the Message VPN. |  [optional]
**serviceRestIncomingTlsListenPort** | **Long** | The port number for incoming REST clients that connect to the Message VPN over TLS. The port must be unique across the message backbone. A value of 0 means that the listen-port is unassigned and cannot be enabled. |  [optional]
**serviceRestIncomingTlsUp** | **Boolean** | Indicates whether the TLS related incoming REST Service is operationally up in the Message VPN. |  [optional]
**serviceRestMode** | [**ServiceRestModeEnum**](#ServiceRestModeEnum) | The REST service mode for incoming REST clients that connect to the Message VPN. The allowed values and their meaning are:  &lt;pre&gt; \&quot;gateway\&quot; - Act as a message gateway through which REST messages are propagated. \&quot;messaging\&quot; - Act as a message broker on which REST messages are queued. &lt;/pre&gt;  |  [optional]
**serviceRestOutgoingMaxConnectionCount** | **Long** | The maximum number of REST Consumer (outgoing) client connections that can be simultaneously connected to the Message VPN. |  [optional]
**serviceSmfMaxConnectionCount** | **Long** | The maximum number of SMF client connections that can be simultaneously connected to the Message VPN. This value may be higher than supported by the platform. |  [optional]
**serviceSmfPlainTextEnabled** | **Boolean** | Indicates whether the SMF Service is enabled in the Message VPN. |  [optional]
**serviceSmfPlainTextFailureReason** | **String** | The reason for the SMF Service failure in the Message VPN. |  [optional]
**serviceSmfPlainTextUp** | **Boolean** | Indicates whether the SMF Service is operationally up in the Message VPN. |  [optional]
**serviceSmfTlsEnabled** | **Boolean** | Indicates whether encryption (TLS) is enabled for SMF clients in the Message VPN. |  [optional]
**serviceSmfTlsFailureReason** | **String** | The reason for the TLS related SMF Service failure in the Message VPN. |  [optional]
**serviceSmfTlsUp** | **Boolean** | Indicates whether the TLS related SMF Service is operationally up in the Message VPN. |  [optional]
**serviceWebAuthenticationClientCertRequest** | [**ServiceWebAuthenticationClientCertRequestEnum**](#ServiceWebAuthenticationClientCertRequestEnum) | Determines when to request a client certificate from a Web Transport client connecting via a TLS port. The allowed values and their meaning are:  &lt;pre&gt; \&quot;always\&quot; - Always ask for a client certificate regardless of the \&quot;message-vpn &gt; authentication &gt; client-certificate &gt; shutdown\&quot; configuration. \&quot;never\&quot; - Never ask for a client certificate regardless of the \&quot;message-vpn &gt; authentication &gt; client-certificate &gt; shutdown\&quot; configuration. \&quot;when-enabled-in-message-vpn\&quot; - Only ask for a client-certificate if client certificate authentication is enabled under \&quot;message-vpn &gt;  authentication &gt; client-certificate &gt; shutdown\&quot;. &lt;/pre&gt;  Available since 2.21. |  [optional]
**serviceWebMaxConnectionCount** | **Long** | The maximum number of Web Transport client connections that can be simultaneously connected to the Message VPN. This value may be higher than supported by the platform. |  [optional]
**serviceWebPlainTextEnabled** | **Boolean** | Indicates whether the Web transport for the SMF Service is enabled in the Message VPN. |  [optional]
**serviceWebPlainTextFailureReason** | **String** | The reason for the Web transport related SMF Service failure in the Message VPN. |  [optional]
**serviceWebPlainTextUp** | **Boolean** | Indicates whether the Web transport for the SMF Service is operationally up in the Message VPN. |  [optional]
**serviceWebTlsEnabled** | **Boolean** | Indicates whether TLS is enabled for SMF clients in the Message VPN that use the Web transport. |  [optional]
**serviceWebTlsFailureReason** | **String** | The reason for the TLS related Web transport SMF Service failure in the Message VPN. |  [optional]
**serviceWebTlsUp** | **Boolean** | Indicates whether the TLS related Web transport SMF Service is operationally up in the Message VPN. |  [optional]
**state** | **String** | The operational state of the local Message VPN. The allowed values and their meaning are:  &lt;pre&gt; \&quot;up\&quot; - The Message VPN is operationally up. \&quot;down\&quot; - The Message VPN is operationally down. \&quot;standby\&quot; - The Message VPN is operationally replication standby. &lt;/pre&gt;  |  [optional]
**subscriptionExportProgress** | **Long** | The progress of the subscription export task, in percent complete. |  [optional]
**systemManager** | **Boolean** | Indicates whether the Message VPN is the system manager for handling system level SEMP get requests and system level event publishing. |  [optional]
**tlsAllowDowngradeToPlainTextEnabled** | **Boolean** | Indicates whether SMF clients connected to the Message VPN are allowed to downgrade their connections from TLS to plain text. |  [optional]
**tlsAverageRxByteRate** | **Long** | The one minute average of the TLS message rate received by the Message VPN, in bytes per second (B/sec). Available since 2.13. |  [optional]
**tlsAverageTxByteRate** | **Long** | The one minute average of the TLS message rate transmitted by the Message VPN, in bytes per second (B/sec). Available since 2.13. |  [optional]
**tlsRxByteCount** | **Long** | The amount of TLS messages received by the Message VPN, in bytes (B). Available since 2.13. |  [optional]
**tlsRxByteRate** | **Long** | The current TLS message rate received by the Message VPN, in bytes per second (B/sec). Available since 2.13. |  [optional]
**tlsTxByteCount** | **Long** | The amount of TLS messages transmitted by the Message VPN, in bytes (B). Available since 2.13. |  [optional]
**tlsTxByteRate** | **Long** | The current TLS message rate transmitted by the Message VPN, in bytes per second (B/sec). Available since 2.13. |  [optional]
**txByteCount** | **Long** | The amount of messages transmitted to clients by the Message VPN, in bytes (B). Available since 2.12. |  [optional]
**txByteRate** | **Long** | The current message rate transmitted by the Message VPN, in bytes per second (B/sec). Available since 2.13. |  [optional]
**txCompressedByteCount** | **Long** | The amount of compressed messages transmitted by the Message VPN, in bytes (B). Available since 2.12. |  [optional]
**txCompressedByteRate** | **Long** | The current compressed message rate transmitted by the Message VPN, in bytes per second (B/sec). Available since 2.12. |  [optional]
**txCompressionRatio** | **String** | The compression ratio for messages transmitted by the message VPN. Available since 2.12. |  [optional]
**txMsgCount** | **Long** | The number of messages transmitted to clients by the Message VPN. Available since 2.12. |  [optional]
**txMsgRate** | **Long** | The current message rate transmitted by the Message VPN, in messages per second (msg/sec). Available since 2.13. |  [optional]
**txUncompressedByteCount** | **Long** | The amount of uncompressed messages transmitted by the Message VPN, in bytes (B). Available since 2.12. |  [optional]
**txUncompressedByteRate** | **Long** | The current uncompressed message rate transmitted by the Message VPN, in bytes per second (B/sec). Available since 2.12. |  [optional]


<a name="AuthenticationBasicTypeEnum"></a>
## Enum: AuthenticationBasicTypeEnum
Name | Value
---- | -----
INTERNAL | &quot;internal&quot;
LDAP | &quot;ldap&quot;
RADIUS | &quot;radius&quot;
NONE | &quot;none&quot;


<a name="AuthenticationClientCertRevocationCheckModeEnum"></a>
## Enum: AuthenticationClientCertRevocationCheckModeEnum
Name | Value
---- | -----
ALL | &quot;allow-all&quot;
UNKNOWN | &quot;allow-unknown&quot;
VALID | &quot;allow-valid&quot;


<a name="AuthenticationClientCertUsernameSourceEnum"></a>
## Enum: AuthenticationClientCertUsernameSourceEnum
Name | Value
---- | -----
CERTIFICATE_THUMBPRINT | &quot;certificate-thumbprint&quot;
COMMON_NAME | &quot;common-name&quot;
COMMON_NAME_LAST | &quot;common-name-last&quot;
SUBJECT_ALTERNATE_NAME_MSUPN | &quot;subject-alternate-name-msupn&quot;
UID | &quot;uid&quot;
UID_LAST | &quot;uid-last&quot;


<a name="AuthorizationTypeEnum"></a>
## Enum: AuthorizationTypeEnum
Name | Value
---- | -----
LDAP | &quot;ldap&quot;
INTERNAL | &quot;internal&quot;


<a name="EventPublishSubscriptionModeEnum"></a>
## Enum: EventPublishSubscriptionModeEnum
Name | Value
---- | -----
OFF | &quot;off&quot;
ON_WITH_FORMAT_V1 | &quot;on-with-format-v1&quot;
ON_WITH_NO_UNSUBSCRIBE_EVENTS_ON_DISCONNECT_FORMAT_V1 | &quot;on-with-no-unsubscribe-events-on-disconnect-format-v1&quot;
ON_WITH_FORMAT_V2 | &quot;on-with-format-v2&quot;
ON_WITH_NO_UNSUBSCRIBE_EVENTS_ON_DISCONNECT_FORMAT_V2 | &quot;on-with-no-unsubscribe-events-on-disconnect-format-v2&quot;


<a name="PreferIpVersionEnum"></a>
## Enum: PreferIpVersionEnum
Name | Value
---- | -----
IPV4 | &quot;ipv4&quot;
IPV6 | &quot;ipv6&quot;


<a name="ReplicationBridgeAuthenticationSchemeEnum"></a>
## Enum: ReplicationBridgeAuthenticationSchemeEnum
Name | Value
---- | -----
BASIC | &quot;basic&quot;
CLIENT_CERTIFICATE | &quot;client-certificate&quot;


<a name="ReplicationRoleEnum"></a>
## Enum: ReplicationRoleEnum
Name | Value
---- | -----
ACTIVE | &quot;active&quot;
STANDBY | &quot;standby&quot;


<a name="ReplicationTransactionModeEnum"></a>
## Enum: ReplicationTransactionModeEnum
Name | Value
---- | -----
SYNC | &quot;sync&quot;
ASYNC | &quot;async&quot;


<a name="ServiceMqttAuthenticationClientCertRequestEnum"></a>
## Enum: ServiceMqttAuthenticationClientCertRequestEnum
Name | Value
---- | -----
ALWAYS | &quot;always&quot;
NEVER | &quot;never&quot;
WHEN_ENABLED_IN_MESSAGE_VPN | &quot;when-enabled-in-message-vpn&quot;


<a name="ServiceRestIncomingAuthenticationClientCertRequestEnum"></a>
## Enum: ServiceRestIncomingAuthenticationClientCertRequestEnum
Name | Value
---- | -----
ALWAYS | &quot;always&quot;
NEVER | &quot;never&quot;
WHEN_ENABLED_IN_MESSAGE_VPN | &quot;when-enabled-in-message-vpn&quot;


<a name="ServiceRestIncomingAuthorizationHeaderHandlingEnum"></a>
## Enum: ServiceRestIncomingAuthorizationHeaderHandlingEnum
Name | Value
---- | -----
DROP | &quot;drop&quot;
FORWARD | &quot;forward&quot;
LEGACY | &quot;legacy&quot;


<a name="ServiceRestModeEnum"></a>
## Enum: ServiceRestModeEnum
Name | Value
---- | -----
GATEWAY | &quot;gateway&quot;
MESSAGING | &quot;messaging&quot;


<a name="ServiceWebAuthenticationClientCertRequestEnum"></a>
## Enum: ServiceWebAuthenticationClientCertRequestEnum
Name | Value
---- | -----
ALWAYS | &quot;always&quot;
NEVER | &quot;never&quot;
WHEN_ENABLED_IN_MESSAGE_VPN | &quot;when-enabled-in-message-vpn&quot;



