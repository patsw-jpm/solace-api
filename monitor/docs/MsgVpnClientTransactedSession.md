
# MsgVpnClientTransactedSession

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**clientName** | **String** | The name of the Client. |  [optional]
**commitCount** | **Long** | The number of transactions committed within the Transacted Session. |  [optional]
**commitFailureCount** | **Long** | The number of transaction commit operations that failed. |  [optional]
**commitSuccessCount** | **Long** | The number of transaction commit operations that succeeded. |  [optional]
**consumedMsgCount** | **Long** | The number of messages consumed within the Transacted Session. |  [optional]
**endFailFailureCount** | **Long** | The number of transaction end fail operations that failed. |  [optional]
**endFailSuccessCount** | **Long** | The number of transaction end fail operations that succeeded. |  [optional]
**endFailureCount** | **Long** | The number of transaction end operations that failed. |  [optional]
**endRollbackFailureCount** | **Long** | The number of transaction end rollback operations that failed. |  [optional]
**endRollbackSuccessCount** | **Long** | The number of transaction end rollback operations that succeeded. |  [optional]
**endSuccessCount** | **Long** | The number of transaction end operations that succeeded. |  [optional]
**failureCount** | **Long** | The number of transactions that failed within the Transacted Session. |  [optional]
**forgetFailureCount** | **Long** | The number of transaction forget operations that failed. |  [optional]
**forgetSuccessCount** | **Long** | The number of transaction forget operations that succeeded. |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]
**onePhaseCommitFailureCount** | **Long** | The number of transaction one-phase commit operations that failed. |  [optional]
**onePhaseCommitSuccessCount** | **Long** | The number of transaction one-phase commit operations that succeeded. |  [optional]
**pendingConsumedMsgCount** | **Integer** | The number of messages to be consumed when the transaction is committed. |  [optional]
**pendingPublishedMsgCount** | **Integer** | The number of messages to be published when the transaction is committed. |  [optional]
**prepareFailureCount** | **Long** | The number of transaction prepare operations that failed. |  [optional]
**prepareSuccessCount** | **Long** | The number of transaction prepare operations that succeeded. |  [optional]
**previousTransactionState** | **String** | The state of the previous transaction. The allowed values and their meaning are:  &lt;pre&gt; \&quot;none\&quot; - The previous transaction had no state. \&quot;committed\&quot; - The previous transaction was committed. \&quot;rolled-back\&quot; - The previous transaction was rolled back. \&quot;failed\&quot; - The previous transaction failed. &lt;/pre&gt;  |  [optional]
**publishedMsgCount** | **Long** | The number of messages published within the Transacted Session. |  [optional]
**resumeFailureCount** | **Long** | The number of transaction resume operations that failed. |  [optional]
**resumeSuccessCount** | **Long** | The number of transaction resume operations that succeeded. |  [optional]
**retrievedMsgCount** | **Long** | The number of messages retrieved within the Transacted Session. |  [optional]
**rollbackCount** | **Long** | The number of transactions rolled back within the Transacted Session. |  [optional]
**rollbackFailureCount** | **Long** | The number of transaction rollback operations that failed. |  [optional]
**rollbackSuccessCount** | **Long** | The number of transaction rollback operations that succeeded. |  [optional]
**sessionName** | **String** | The name of the Transacted Session. |  [optional]
**spooledMsgCount** | **Long** | The number of messages spooled within the Transacted Session. |  [optional]
**startFailureCount** | **Long** | The number of transaction start operations that failed. |  [optional]
**startSuccessCount** | **Long** | The number of transaction start operations that succeeded. |  [optional]
**successCount** | **Long** | The number of transactions that succeeded within the Transacted Session. |  [optional]
**suspendFailureCount** | **Long** | The number of transaction suspend operations that failed. |  [optional]
**suspendSuccessCount** | **Long** | The number of transaction suspend operations that succeeded. |  [optional]
**transactionId** | **Integer** | The identifier (ID) of the transaction in the Transacted Session. |  [optional]
**transactionState** | **String** | The state of the current transaction. The allowed values and their meaning are:  &lt;pre&gt; \&quot;in-progress\&quot; - The current transaction is in progress. \&quot;committing\&quot; - The current transaction is committing. \&quot;rolling-back\&quot; - The current transaction is rolling back. \&quot;failing\&quot; - The current transaction is failing. &lt;/pre&gt;  |  [optional]
**twoPhaseCommitFailureCount** | **Long** | The number of transaction two-phase commit operations that failed. |  [optional]
**twoPhaseCommitSuccessCount** | **Long** | The number of transaction two-phase commit operations that succeeded. |  [optional]



