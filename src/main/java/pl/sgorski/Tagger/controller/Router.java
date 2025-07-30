package pl.sgorski.Tagger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Router {

    @GetMapping({"/", "/docs", "/home"})
    public String home() {
        return "redirect:/swagger-ui/index.html";
    }

    @GetMapping("/auth/login")
    public String login() {
        return "redirect:/oauth2/authorization/google";
    }
}
