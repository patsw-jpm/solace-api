
# MsgVpnAuthorizationGroup

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**aclProfileName** | **String** | The ACL Profile of the LDAP Authorization Group. |  [optional]
**authorizationGroupName** | **String** | The name of the LDAP Authorization Group. Special care is needed if the group name contains special characters such as &#39;#&#39;, &#39;+&#39;, &#39;;&#39;, &#39;&#x3D;&#39; as the value of the group name returned from the LDAP server might prepend those characters with &#39;\\&#39;. For example a group name called &#39;test#,lab,com&#39; will be returned from the LDAP server as &#39;test\\#,lab,com&#39;. |  [optional]
**clientProfileName** | **String** | The Client Profile of the LDAP Authorization Group. |  [optional]
**enabled** | **Boolean** | Indicates whether the LDAP Authorization Group is enabled. |  [optional]
**msgVpnName** | **String** | The name of the Message VPN. |  [optional]



