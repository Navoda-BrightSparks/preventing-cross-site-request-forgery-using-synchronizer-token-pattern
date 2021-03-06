package com.synchronizertokenpattern.demo.controllers;

import com.synchronizertokenpattern.demo.models.FundTransfer;
import com.synchronizertokenpattern.demo.services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Handles fund transfer functions
 *
 * Created by navodasathsarani on 9/6/18.
 */
@Controller
public class FundsController {

    private static Logger logger = LoggerFactory.getLogger(FundsController.class);

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/transfer")
    public String transferFunds(@ModelAttribute FundTransfer fundTransfer, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String sessionId = authenticationService.sessionIdFromCookies(cookies);

        logger.info("Request received for transferFunds...");
        logger.info("Validating token...");

        if (authenticationService.isAuthenticated(cookies) &&
                authenticationService.validateCSRFToken(sessionId, fundTransfer.getCsrf())){

            logger.info("Token validated...");
            return "redirect:/home?status=success";
        }
        logger.error("User not authenticated!!!");
        return "redirect:/home?status=failed";
    }


}
