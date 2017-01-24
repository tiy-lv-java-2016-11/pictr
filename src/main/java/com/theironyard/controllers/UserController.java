package com.theironyard.controllers;

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

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String getHome(Model model, HttpSession session){

        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("appId", facebook.appId);
        model.addAttribute("redirect", REDIRECT);
        return "home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String fbLogin(String code, HttpSession session){
        String userToken = facebook.getUserToken(code);
        if (!facebook.checkUserToken(userToken)){
            return "redirect:/";
        }
        String username = facebook.getUsername(userToken);
        session.setAttribute(SESSION_USERNAME, username);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

}
