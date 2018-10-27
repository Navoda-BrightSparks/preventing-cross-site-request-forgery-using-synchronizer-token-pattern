package com.synchronizertokenpattern.demo.controllers;


import com.synchronizertokenpattern.demo.models.Credentials;
import com.synchronizertokenpattern.demo.services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for handling login requests
 *
 * Created by navodasathsarani on 9/5/18.
 */

@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public String login(@ModelAttribute Credentials credentials, HttpServletResponse response){
        String username = credentials.getUsername();
        System.out.println(username);

        if(authenticationService.isUserAuthenticated(username, credentials.getPassword())){

            logger.debug("Successfully authenticated user...");
            Cookie sessionCookie = new Cookie("sessionID", authenticationService.generateSessionId(username));
            Cookie userCookie = new Cookie("username", username);
            response.addCookie(sessionCookie);
            response.addCookie(userCookie);
            return "redirect:/home";
        }
        logger.debug("Failed to authenticate user...");
        return "redirect:/login?status=failed";
    }
}
