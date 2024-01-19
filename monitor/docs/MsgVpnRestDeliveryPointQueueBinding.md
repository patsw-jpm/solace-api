
# MsgVpnRestDeliveryPointQueueBinding

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**gatewayReplaceTargetAuthorityEnabled** | **Boolean** | Indicates whether the authority for the request-target is replaced with that configured for the REST Consumer remote. |  [optional]
**lastFailureReason** | **String** | The reason for the last REST Delivery Point queue binding failure. |  [optional]
**lastFailureTime** | **Integer** | The timestamp of the last REST Delivery Point queue binding failure. This value represents the number of seconds since 1970-01-01 00:00:00 UTC (Unix time). |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**postRequestTarget** | **String** | The request-target string being used when sending requests to a REST Consumer. |  [optional]
**queueBindingName** | **String** | The name of a queue in the Message VPN. |  [optional]
**requestTargetEvaluation** | [**RequestTargetEvaluationEnum**](#RequestTargetEvaluationEnum) | The type of evaluation to perform on the request target. The allowed values and their meaning are:  &lt;pre&gt; \&quot;none\&quot; - Do not evaluate substitution expressions on the request target. \&quot;substitution-expressions\&quot; - Evaluate substitution expressions on the request target. &lt;/pre&gt;  Available since 2.23. |  [optional]
**restDeliveryPointName** | **String** | The name of the REST Delivery Point. |  [optional]
**up** | **Boolean** | Indicates whether the operational state of the REST Delivery Point queue binding is up. |  [optional]
**uptime** | **Long** | The amount of time in seconds since the REST Delivery Point queue binding was up. |  [optional]


<a name="RequestTargetEvaluationEnum"></a>
## Enum: RequestTargetEvaluationEnum
Name | Value
---- | -----
NONE | &quot;none&quot;
SUBSTITUTION_EXPRESSIONS | &quot;substitution-expressions&quot;



