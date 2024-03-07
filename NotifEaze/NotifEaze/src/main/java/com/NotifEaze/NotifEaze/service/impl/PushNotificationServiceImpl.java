package com.NotifEaze.NotifEaze.service.impl;

import java.awt.PageAttributes.MediaType;
import java.io.IOException;

import org.springframework.web.bind.annotation.RequestBody;

import com.NotifEaze.NotifEaze.dto.BasePushNotificationResponse;
import com.NotifEaze.NotifEaze.service.PushNotificationService;

import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import okhttp3.OkHttpClient;

public class PushNotificationServiceImpl implements PushNotificationService {

    // FCM server key obtained from Firebase console
    private static final String FCM_SERVER_KEY = "YOUR_FCM_SERVER_KEY";

    @Override
    public BasePushNotificationResponse sendNotification(String fcmToken, String message) {
        // Construct JSON payload for FCM request
        String jsonPayload = "{\"to\": \"" + fcmToken + "\", \"notification\": {\"body\": \"" + message + "\"}}";

        // Create HTTP client
        OkHttpClient client = new OkHttpClient();

        // Create request body
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonPayload);

        // Create HTTP request
        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .post(requestBody)
                .addHeader("Authorization", "key=" + FCM_SERVER_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            // Execute HTTP request
            okhttp3.Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                // Notification sent successfully
                return new BasePushNotificationResponse(true, "Notification sent successfully");
            } else {
                // Failed to send notification
                return new BasePushNotificationResponse(false, "Failed to send notification: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new BasePushNotificationResponse(false, "Failed to send notification: " + e.getMessage());
        }
    }
}
