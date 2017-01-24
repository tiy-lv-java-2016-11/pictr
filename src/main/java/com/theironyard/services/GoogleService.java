package com.theironyard.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GoogleService {
    public static final String REDIRECT = "http://localhost:8080/login";

    @Value("${google.secret}")
    public String secret;

    @Value("${google.clientId}")
    public String appId;}
