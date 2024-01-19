
# MsgVpnMqttSessionCounter

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**mqttConnackErrorTxCount** | **Long** | The number of MQTT connect acknowledgment (CONNACK) refused response packets transmitted to the Client. Deprecated since 2.13. This attribute has been moved to the MsgVpnMqttSession object. |  [optional]
**mqttConnackTxCount** | **Long** | The number of MQTT connect acknowledgment (CONNACK) accepted response packets transmitted to the Client. Deprecated since 2.13. This attribute has been moved to the MsgVpnMqttSession object. |  [optional]
**mqttConnectRxCount** | **Long** | The number of MQTT connect (CONNECT) request packets received from the Client. Deprecated since 2.13. This attribute has been moved to the MsgVpnMqttSession object. |  [optional]
**mqttDisconnectRxCount** | **Long** | The number of MQTT disconnect (DISCONNECT) request packets received from the Client. Deprecated since 2.13. This attribute has been moved to the MsgVpnMqttSession object. |  [optional]
**mqttPubcompTxCount** | **Long** | The number of MQTT publish complete (PUBCOMP) packets transmitted to the Client in response to a PUBREL packet. These packets are the fourth and final packet of a QoS 2 protocol exchange. Deprecated since 2.13. This attribute has been moved to the MsgVpnMqttSession object. |  [optional]
**mqttPublishQos0RxCount** | **Long** | The number of MQTT publish message (PUBLISH) request packets received from the Client for QoS 0 message delivery. Deprecated since 2.13. This attribute has been moved to the MsgVpnMqttSession object. |  [optional]
**mqttPublishQos0TxCount** | **Long** | The number of MQTT publish message (PUBLISH) request packets transmitted to the Client for QoS 0 message delivery. Deprecated since 2.13. This attribute has been moved to the MsgVpnMqttSession object. |  [optional]
**mqttPublishQos1RxCount** | **Long** | The number of MQTT publish message (PUBLISH) request packets received from the Client for QoS 1 message delivery. Deprecated since 2.13. This attribute has been moved to the MsgVpnMqttSession object. |  [optional]
**mqttPublishQos1TxCount** | **Long** | The number of MQTT publish message (PUBLISH) request packets transmitted to the Client for QoS 1 message delivery. Deprecated since 2.13. This attribute has been moved to the MsgVpnMqttSession object. |  [optional]
**mqttPublishQos2RxCount** | **Long** | The number of MQTT publish message (PUBLISH) request packets received from the Client for QoS 2 message delivery. Deprecated since 2.13. This attribute has been moved to the MsgVpnMqttSession object. |  [optional]
**mqttPubrecTxCount** | **Long** | The number of MQTT publish received (PUBREC) packets transmitted to the Client in response to a PUBLISH packet with QoS 2. These packets are the second packet of a QoS 2 protocol exchange. Deprecated since 2.13. This attribute has been moved to the MsgVpnMqttSession object. |  [optional]
**mqttPubrelRxCount** | **Long** | The number of MQTT publish release (PUBREL) packets received from the Client in response to a PUBREC packet. These packets are the third packet of a QoS 2 protocol exchange. Deprecated since 2.13. This attribute has been moved to the MsgVpnMqttSession object. |  [optional]



