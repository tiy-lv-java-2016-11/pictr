package com.theironyard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacebookService {
    public static final String REDIRECT = "http://localhost:8080/login";

    @Value("${facebook.secret}")
    public String secret;

    @Value("${facebook.appId}")
    public String appId;

    public String appAccessToken;

    @Autowired
    public RestTemplate restTemplate;

    @PostConstruct
    public void init(){
        String appTokenUrl = "https://graph.facebook.com/oauth/access_token?client_id=%s&client_secret=%s&grant_type=client_credentials";
        String appTokenResult = restTemplate.getForObject(String.format(appTokenUrl, appId, secret), String.class);
        appAccessToken = appTokenResult.split("=")[1];
    }

    public String getUserToken(String code){
        String tokenUrl = "https://graph.facebook.com/v2.8/oauth/access_token?client_id=%s&redirect_uri=%s&client_secret=%s&code=%s";
        Map atResult = restTemplate.getForObject(String.format(tokenUrl, appId, REDIRECT, secret, code), HashMap.class);

        return (String)atResult.get("access_token");
    }

    public boolean checkUserToken(String token){
        String atCheckUrl = "https://graph.facebook.com/debug_token?input_token=%s&access_token=%s";
        Map check = restTemplate.getForObject(String.format(atCheckUrl, token, appAccessToken), Map.class);
        Map data = (HashMap)check.get("data");

        return (boolean)data.get("is_valid");
    }

    public String getUsername(String token){
        Map userResult = restTemplate.getForObject("https://graph.facebook.com/v2.8/me?fields=id,name&access_token="+token, HashMap.class);

        return (String)userResult.get("name");
    }
}
