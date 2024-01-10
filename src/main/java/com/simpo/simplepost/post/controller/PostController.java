package com.simpo.simplepost.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
public class PostController {

    @GetMapping("")
    public String springStart(Model model){
        model.addAttribute("data", "Hello, World");
        return "posts";
    }
}
