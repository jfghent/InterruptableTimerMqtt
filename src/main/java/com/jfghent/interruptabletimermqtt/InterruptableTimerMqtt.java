/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jfghent.interruptabletimermqtt;

import com.jfghent.interruptabletimer.InterruptableTimer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author jon
 */
public class InterruptableTimerMqtt extends InterruptableTimer{
    private final MqttClient _mc;
    private String _stopTopic, _startTopic;
    private MqttMessage _startMsg, _stopMsg; 
    
    public InterruptableTimerMqtt(String TaskName, long Time, MqttClient mc, String StartTopic, String StopTopic, MqttMessage StartMsg, MqttMessage StopMsg) {
        super(TaskName, Time);
        _mc = mc;
   
        _stopTopic = StopTopic;
        _startTopic = StartTopic;
        _startMsg = StartMsg;
        _stopMsg = StopMsg;
        
        super.if_onCancel = () -> {
            SendStopCommand();
        };
        
        super.if_onFinish = () -> {
            SendStopCommand();
        };
        
        super.if_onPause = () -> {
            SendStopCommand();
        };
        
        super.if_onResume = () -> {
            SendStartCommand();
        };
        
        super.if_onStart = () -> {
            SendStartCommand();
        };
    }

    private void SendStopCommand(){
        try {
            _mc.publish(_stopTopic, _stopMsg);
        } catch (MqttException ex) {
            Logger.getLogger(InterruptableTimerMqtt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void SendStartCommand(){
        try {
            _mc.publish(_startTopic,_startMsg);
        } catch (MqttException ex) {
            Logger.getLogger(InterruptableTimerMqtt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
