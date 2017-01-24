package com.theironyard.controllers;

import com.theironyard.entities.Picture;
import com.theironyard.entities.User;
import com.theironyard.repositories.PictureRepository;
import com.theironyard.repositories.UserRepository;
import com.theironyard.services.FacebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static com.theironyard.services.FacebookService.REDIRECT;

@Controller
public class PictrController {
    public static final String SESSION_USERNAME = "username";

    @Autowired
    FacebookService facebook;

    @Autowired
    UserRepository userRepo;

    @Autowired
    PictureRepository pictureRepo;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String getHome(Model model, HttpSession session){
        String username = (String)session.getAttribute(SESSION_USERNAME);
        List<Picture> pictures = pictureRepo.findAll();

        model.addAttribute("username", username);
        model.addAttribute("appId", facebook.appId);
        model.addAttribute("redirect", REDIRECT);
        model.addAttribute("pictures", pictures);
        return "home";
    }

    @RequestMapping(path = "/add-file", method = RequestMethod.GET)
    public String getAddFileForm(Model model, HttpSession session){
        String username = (String)session.getAttribute(SESSION_USERNAME);
        User user = userRepo.findByUsername(username);
        if (user == null){
            return "/";
        }
        return "add-file";
    }

    @RequestMapping(path = "/add-file", method = RequestMethod.POST)
    public String addFile(HttpSession session, MultipartFile file, String caption) throws IOException {
        String username = (String)session.getAttribute(SESSION_USERNAME);
        User user = userRepo.findByUsername(username);
        if (user == null){
            return "/";
        }

        File dir = new File("public/files");
        dir.mkdirs();

        File f = File.createTempFile("PictR", file.getOriginalFilename(), dir);
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(file.getBytes());

        Picture picture = new Picture(f.getName(), file.getOriginalFilename(), caption, user);
        pictureRepo.save(picture);

        return "redirect:/";
    }
}
