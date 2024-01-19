
# MsgVpnClientClearEvent

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**eventName** | [**EventNameEnum**](#EventNameEnum) | The name of the event. This attribute is absent from a GET and not updated when absent in a PUT, subject to the exceptions in note 4. The allowed values and their meaning are:  &lt;pre&gt; \&quot;large-message\&quot; - The Client large message received event. \&quot;message-too-big\&quot; - The Client message too big event. \&quot;parse-error\&quot; - The Client parse error event. \&quot;max-eliding-topic-count\&quot; - The Client maximum eliding topics reached event. &lt;/pre&gt;  |  [optional]


<a name="EventNameEnum"></a>
## Enum: EventNameEnum
Name | Value
---- | -----
LARGE_MESSAGE | &quot;large-message&quot;
MESSAGE_TOO_BIG | &quot;message-too-big&quot;
PARSE_ERROR | &quot;parse-error&quot;
MAX_ELIDING_TOPIC_COUNT | &quot;max-eliding-topic-count&quot;



