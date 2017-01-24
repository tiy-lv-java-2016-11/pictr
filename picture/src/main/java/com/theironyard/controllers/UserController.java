package com.theironyard.controllers;

import com.theironyard.entities.User;
import com.theironyard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sparatan117 on 1/23/17.
 */
@Controller
public class UserController {

    public static final String REDIRECT = "http://localhost:8080/login";
    public static final String SESSION_USERNAME = "username";

    @Value("${facebook.secret}")
    public String secret;

    @Value("${facebook.app-id}")
    public String appId;

    public String appAccessToken;

    @Autowired
    UserRepository userRepository;

    @Autowired
    public RestTemplate restTemplate;



    @PostConstruct
    public void init(){
        String appTokenUrl = "https://graph.facebook.com/oauth/" +
                "access_token?client_id=%s&client_secret=%s&grant_type=client_credentials";
        String appTokenResult =restTemplate.getForObject(String.format(appTokenUrl, appId, secret), String.class);
        appAccessToken = appTokenResult.split("=")[1];

    }


    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session){
        model.addAttribute("appId", appId);
        model.addAttribute("redirect", REDIRECT);
        model.addAttribute("username", session.getAttribute("username"));
        return "home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String fbLogin(String code, HttpSession session){
        String tokenUrl ="https://graph.facebook.com/v2.8/oauth/access_token?" +
                "client_id=%s&redirect_uri=%s&client_secret=%s&code=%s";

        Map atResult = restTemplate.getForObject(String.format(tokenUrl, appId, REDIRECT, secret, code), HashMap.class);

        String atCheckUrl = "https://graph.facebook.com/debug_token?input_token=%s&access_token=%s";
        Map check = restTemplate.getForObject(String.format(atCheckUrl, atResult.get("access_token"), appAccessToken),
                Map.class);
        Map userResult = restTemplate.getForObject("https://graph.facebook.com/v2.8/me?fields=id,name&access_token="
                + atResult.get("access_token"), HashMap.class);
        session.setAttribute(SESSION_USERNAME, userResult.get("name"));
        String token = atResult.get("access_token").toString();
        String name = userResult.get("name").toString();
        User sUser = userRepository.findFirstByUsername(name);
        if (sUser == null){
            sUser = new User(name, token);
            userRepository.save(sUser);
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }


}
