package com.facilito.miapp.controller.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Secured("ROLE_ADMIN")
@RequestMapping("admin")
@Controller
public class AdminController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model){
        return "admin/index";
    }

}
