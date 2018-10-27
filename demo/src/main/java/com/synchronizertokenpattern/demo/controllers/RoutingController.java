package com.synchronizertokenpattern.demo.controllers;


import com.synchronizertokenpattern.demo.models.Credentials;
import com.synchronizertokenpattern.demo.models.FundTransfer;
import com.synchronizertokenpattern.demo.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by navodasathsarani on 9/9/18.
 */

@Controller
public class RoutingController {

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/")
    public String showLoginPage(Model model, HttpServletRequest request){

        if (!authenticationService.isAuthenticated(request.getCookies())){
            model.addAttribute("login", new Credentials());
            return "redirect:/login";
        } else {
            model.addAttribute("home", new FundTransfer());
            return "redirect:/home";
        }

    }

    @GetMapping("/login")
    public String showLogin(Model model){
        model.addAttribute("login", new Credentials());
        return "login";
    }

    @GetMapping("/home")
    public String showHomePage(Model model, HttpServletRequest request){
        return redirect(model, request);
    }

    @GetMapping("/transfer")
    public String showTransferPage(Model model, HttpServletRequest request){
        return redirect(model, request);
    }

    private String redirect(Model model, HttpServletRequest request) {
        if(authenticationService.isAuthenticated(request.getCookies())){
            model.addAttribute("home", new FundTransfer());
            return "home";
        } else {
            model.addAttribute("login", new Credentials());
            return "redirect:/login";
        }
    }
}