
# GuaranteedMsgingExternalDiskArray

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**size** | **Long** | The size of the LUN in bytes. |  [optional]
**state** | **String** | The operational state of the LUN. The allowed values and their meaning are:  &lt;pre&gt; \&quot;ready\&quot; - The operational state of the disk array is Ready. \&quot;down\&quot; - The operational state of the disk array is Down. &lt;/pre&gt;  |  [optional]
**wwn** | **String** | The World-Wide Name (WWN) of the disk array to use for storing the guaranteed messaging spool. The disk array must be accessible by this broker and must not already be owned by another broker (unless in an HA pair with this broker). |  [optional]



