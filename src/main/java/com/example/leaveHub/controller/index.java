package com.example.leaveHub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class index {
    @GetMapping("/")
    public String indexPage() {
        return "/auth/login";
    }

}
