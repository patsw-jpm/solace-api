
# MsgVpnTransaction

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**clientId** | **Integer** | The identifier (ID) of the Client. |  [optional]
**clientName** | **String** | The name of the Client. |  [optional]
**clientUsername** | **String** | The username of the Client. |  [optional]
**idleTimeout** | **Integer** | The number of seconds before an idle Transaction may be automatically rolled back and freed. |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**replicated** | **Boolean** | Indicates whether the Transaction is replicated. |  [optional]
**sessionName** | **String** | The name of the Transacted Session for the Transaction. |  [optional]
**state** | **String** | The state of the Transaction. The allowed values and their meaning are:  &lt;pre&gt; \&quot;active\&quot; - The Transaction was started. \&quot;suspended\&quot; - The Transaction was suspended. \&quot;idle\&quot; - The Transaction was ended. \&quot;prepared\&quot; - The Transaction was prepared. \&quot;complete\&quot; - The Transaction was committed or rolled back. &lt;/pre&gt;  |  [optional]
**timeInState** | **Integer** | The number of seconds the Transaction has remained in the current state. |  [optional]
**type** | **String** | The type of Transaction. The allowed values and their meaning are:  &lt;pre&gt; \&quot;xa\&quot; - The Transaction is an XA Transaction. \&quot;local\&quot; - The Transaction is a local Transaction. &lt;/pre&gt;  |  [optional]
**xid** | **String** | The identifier (ID) of the Transaction. |  [optional]



