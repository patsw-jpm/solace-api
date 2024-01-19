
# DmrCluster

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**authenticationBasicEnabled** | **Boolean** | Indicates whether basic authentication is enabled for Cluster Links. |  [optional]
**authenticationBasicType** | [**AuthenticationBasicTypeEnum**](#AuthenticationBasicTypeEnum) | The type of basic authentication to use for Cluster Links. The allowed values and their meaning are:  &lt;pre&gt; \&quot;internal\&quot; - Use locally configured password. \&quot;none\&quot; - No authentication. &lt;/pre&gt;  |  [optional]
**authenticationClientCertEnabled** | **Boolean** | Indicates whether client certificate authentication is enabled for Cluster Links. |  [optional]
**directOnlyEnabled** | **Boolean** | Indicates whether this cluster only supports direct messaging. If true, guaranteed messages will not be transmitted through the cluster. |  [optional]
**dmrClusterName** | **String** | The name of the Cluster. |  [optional]
**enabled** | **Boolean** | Indicates whether the Cluster is enabled. |  [optional]
**failureReason** | **String** | The failure reason for the Cluster being down. |  [optional]
**nodeName** | **String** | The name of this node in the Cluster. This is the name that this broker (or redundant group of brokers) is know by to other nodes in the Cluster. The name is chosen automatically to be either this broker&#39;s Router Name or Mate Router Name, depending on which Active Standby Role (primary or backup) this broker plays in its redundancy group. |  [optional]
**subscriptionDbBuildPercentage** | **Long** | Cluster Subscription Database build completion percentage. Available since 2.20. |  [optional]
**tlsServerCertEnforceTrustedCommonNameEnabled** | **Boolean** | Indicates whether the common name provided by the remote broker is enforced against the list of trusted common names configured for the Link. If enabled, the certificate&#39;s common name must match one of the trusted common names for the Link to be accepted. Deprecated since 2.18. Common Name validation has been replaced by Server Certificate Name validation. |  [optional]
**tlsServerCertMaxChainDepth** | **Long** | The maximum allowed depth of a certificate chain. The depth of a chain is defined as the number of signing CA certificates that are present in the chain back to a trusted self-signed root CA certificate. |  [optional]
**tlsServerCertValidateDateEnabled** | **Boolean** | Indicates whether validation of the \&quot;Not Before\&quot; and \&quot;Not After\&quot; validity dates in the certificate is enabled. When disabled, the certificate is accepted even if the certificate is not valid based on these dates. |  [optional]
**tlsServerCertValidateNameEnabled** | **Boolean** | Enable or disable the standard TLS authentication mechanism of verifying the name used to connect to the bridge. If enabled, the name used to connect to the bridge is checked against the names specified in the certificate returned by the remote router. Legacy Common Name validation is not performed if Server Certificate Name Validation is enabled, even if Common Name validation is also enabled. Available since 2.18. |  [optional]
**up** | **Boolean** | Indicates whether the Cluster is operationally up. |  [optional]
**uptime** | **Long** | The amount of time in seconds since the Cluster was up. |  [optional]


<a name="AuthenticationBasicTypeEnum"></a>
## Enum: AuthenticationBasicTypeEnum
Name | Value
---- | -----
INTERNAL | &quot;internal&quot;
NONE | &quot;none&quot;



