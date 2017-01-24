package com.theironyard.controllers;

import com.theironyard.entities.User;
import com.theironyard.repositories.UserRepository;
import com.theironyard.services.FacebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

import static com.theironyard.services.FacebookService.REDIRECT;

@Controller
public class UserController {
    public static final String SESSION_USERNAME = "username";

    @Autowired
    FacebookService facebook;

    @Autowired
    UserRepository userRepo;

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String fbLogin(String code, HttpSession session){
        String userToken = facebook.getUserToken(code);
        if (!facebook.checkUserToken(userToken)){
            return "redirect:/";
        }
        String username = facebook.getUsername(userToken);
        User user = userRepo.findByUsername(username);
        if (user == null) {
            user = new User(username, userToken);
            userRepo.save(user);
        }
        session.setAttribute(SESSION_USERNAME, username);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

}
