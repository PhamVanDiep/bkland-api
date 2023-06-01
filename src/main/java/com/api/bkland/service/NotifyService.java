package com.api.bkland.service;

import com.api.bkland.entity.UserDeviceToken;
import com.api.bkland.payload.request.notify.Notification;
import com.api.bkland.payload.request.notify.NotifyRequest;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.payload.response.PushNotifyResponse;
import com.api.bkland.repository.PriceFluctuationRepository;
import com.api.bkland.repository.UserDeviceTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotifyService {

    @Autowired
    private UserDeviceTokenRepository userDeviceTokenRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PriceFluctuationRepository priceFluctuationRepository;

    private static final String FIREBASE_FCM = "https://fcm.googleapis.com/fcm/send";

    private static final String FCM_KEY = "AAAAQHd6Jxs:APA91bGAxydZmPaHEfVY7ZM8kh_k6engPxiYImUjZ37B96AfIFH9sW5IheYnjrdzu5DZ76rB2ML8Ul-RI_Ufr3xGOXU7gowMrH0SPMblAHb_RsqSk9C-CNB4AzN1jPosu1k6gZBgQr_w";

    private Logger logger = LoggerFactory.getLogger(NotifyService.class);
    public void notifyToAllUsers(String message) {
        List<PushNotifyResponse> pushNotifyResponses = new ArrayList<>();
        List<UserDeviceToken> userDeviceTokens = userDeviceTokenRepository
                .findAll()
                .stream()
                .filter(e -> e.isEnable() && !e.isLogout())
                .collect(Collectors.toList());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.AUTHORIZATION, "key="+FCM_KEY);
        for (UserDeviceToken userDeviceToken :
                userDeviceTokens) {
            Notification notification = new Notification();
            notification.setTitle("Bkland");
            notification.setBody(message);
            NotifyRequest notifyRequest = new NotifyRequest();
            notifyRequest.setNotification(notification);
            notifyRequest.setTo(userDeviceToken.getNotifyToken());
            HttpEntity<?> request = new HttpEntity<>(notifyRequest, headers);
            PushNotifyResponse pushNotifyResponse = restTemplate
                    .postForObject(FIREBASE_FCM, request, PushNotifyResponse.class);
            pushNotifyResponses.add(pushNotifyResponse);
            logger.info("response: {}", pushNotifyResponse.toString());
        }
    }

    public void notifyPriceFluctuation(String message, String districtCode) {
        List<String> tokens = priceFluctuationRepository.getTokensByDistrict(districtCode);
        sendNotify(tokens, message);
    }

    private void sendNotify(List<String> tokens, String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.AUTHORIZATION, "key="+FCM_KEY);
        for (String token : tokens) {
            Notification notification = new Notification();
            notification.setTitle("Bkland");
            notification.setBody(message);
            NotifyRequest notifyRequest = new NotifyRequest();
            notifyRequest.setNotification(notification);
            notifyRequest.setTo(token);
            HttpEntity<?> request = new HttpEntity<>(notifyRequest, headers);
            restTemplate.postForObject(FIREBASE_FCM, request, PushNotifyResponse.class);
        }
    }
}
