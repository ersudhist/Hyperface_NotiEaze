package com.NotifEaze.NotifEaze.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NotifEaze.NotifEaze.dto.BaseEmailResponse;
import com.NotifEaze.NotifEaze.dto.BasePushNotificationResponse;
import com.NotifEaze.NotifEaze.dto.BaseSmsResponse;
import com.NotifEaze.NotifEaze.dto.EmailRequest;
import com.NotifEaze.NotifEaze.dto.PushNotificationRequest;
import com.NotifEaze.NotifEaze.dto.SmsRequest;
import com.NotifEaze.NotifEaze.service.EmailService;
import com.NotifEaze.NotifEaze.service.PushNotificationService;
import com.NotifEaze.NotifEaze.service.SmsService;

@RestController
@RequestMapping("/api/communication")
public class CommunicationController {
	
	@Autowired
    private  SmsService smsService;
	@Autowired
    private  EmailService emailService;
	@Autowired
    private  PushNotificationService pushNotificationService;
    
    @PostMapping("/send-sms")
    public ResponseEntity<BaseSmsResponse> sendSms(@RequestBody SmsRequest smsRequest) {
        BaseSmsResponse response = smsService.sendSms(smsRequest.getPhoneNumber(), smsRequest.getMessage());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/send-email")
    public ResponseEntity<BaseEmailResponse> sendEmail(@RequestBody EmailRequest emailRequest) {
        BaseEmailResponse response = emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/send-notification")
    public ResponseEntity<BasePushNotificationResponse> sendNotification(@RequestBody PushNotificationRequest pushNotificationRequest) {
        BasePushNotificationResponse response = pushNotificationService.sendNotification(pushNotificationRequest.getFcmToken(), pushNotificationRequest.getMessage());
        return ResponseEntity.ok(response);
    }
}
